/*
 * Hundsun Inc.
 * Copyright (c) 2006-2009 All Rights Reserved.
 *
 * Author     :shengyong
 * Version    :1.0
 * Create Date:2011-5-24
 */
package com.huaixuan.network.web.action.timetask;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.huaixuan.network.biz.domain.admin.AdminAgent;
import com.huaixuan.network.biz.domain.agent.InterfaceUserTrade;
import com.huaixuan.network.biz.domain.remote.InterfaceApply;
import com.huaixuan.network.biz.domain.storage.DepLocation;
import com.huaixuan.network.biz.domain.storage.Depository;
import com.huaixuan.network.biz.domain.storage.DepositoryFirst;
import com.huaixuan.network.biz.domain.storage.OutDepository;
import com.huaixuan.network.biz.domain.storage.OutDepositoryStorage;
import com.huaixuan.network.biz.domain.storage.OutDetailBaseInfo;
import com.huaixuan.network.biz.domain.storage.OutDetailGoods;
import com.huaixuan.network.biz.domain.storage.Storage;
import com.huaixuan.network.biz.domain.taobao.TaobaoApply;
import com.huaixuan.network.biz.domain.trade.Order;
import com.huaixuan.network.biz.domain.trade.Refund;
import com.huaixuan.network.biz.domain.trade.Trade;
import com.huaixuan.network.biz.enums.EnumDepLocationIsCheck;
import com.huaixuan.network.biz.enums.EnumDepositoryType;
import com.huaixuan.network.biz.enums.EnumInterfaceType;
import com.huaixuan.network.biz.enums.EnumRefundStatus;
import com.huaixuan.network.biz.enums.EnumTradeStatus;
import com.huaixuan.network.biz.enums.EnumTradeType;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.query.TradeListQuery;
import com.huaixuan.network.biz.service.agent.InterfaceUserTradeManager;
import com.huaixuan.network.biz.service.goods.AttributeManager;
import com.huaixuan.network.biz.service.goods.CategoryManager;
import com.huaixuan.network.biz.service.remote.InterfaceApplyManager;
import com.huaixuan.network.biz.service.remote.TaobaoInterfaceApplyManager;
import com.huaixuan.network.biz.service.storage.DepLocationManager;
import com.huaixuan.network.biz.service.storage.DepositoryFirstManager;
import com.huaixuan.network.biz.service.storage.DepositoryService;
import com.huaixuan.network.biz.service.storage.InAndOutDepositoryManager;
import com.huaixuan.network.biz.service.storage.OutDepositoryManager;
import com.huaixuan.network.biz.service.storage.OutDetailManager;
import com.huaixuan.network.biz.service.storage.ProdRelationOutManager;
import com.huaixuan.network.biz.service.storage.StorageManager;
import com.huaixuan.network.biz.service.trade.OrderManager;
import com.huaixuan.network.biz.service.trade.RefundManager;
import com.huaixuan.network.biz.service.trade.TradeManager;
import com.huaixuan.network.common.util.Billing_Deal_DetailsUtil;
import com.huaixuan.network.common.util.DateUtil;
import com.huaixuan.network.web.action.BaseAction;
import com.hundsun.common.lang.StringUtil;

/**
 * @author shengyong
 * @version $Id: TradeTimetaskAction.java,v 0.1 2011-5-24 ����09:02:39 shengyong Exp $
 */
@Controller
public class TradeTimetaskAction extends BaseAction {
    protected final Log         log = LogFactory.getLog(this.getClass());

    @Autowired
    TradeManager                tradeManager;
    @Autowired
    RefundManager               refundManager;
    @Autowired
    OrderManager                orderManager;
    @Autowired
    StorageManager              storageManager;

    @Autowired
    DepLocationManager          depLocationManager;

    @Autowired
    DepositoryService           depositoryService;
    @Autowired
    InAndOutDepositoryManager   inAndOutDepositoryManager;
    @Autowired
    InterfaceApplyManager       interfaceApplyManager;
    @Autowired
    InterfaceUserTradeManager   interfaceUserTradeManager;

    @Autowired
    TaobaoInterfaceApplyManager taobaoInterfaceApplyManager;

    @Autowired
    OutDetailManager            outDetailManager;
    @Autowired
    OutDepositoryManager        outDepositoryManager;
    @Autowired
    CategoryManager             categoryManager;
    @Autowired
    AttributeManager            attributeManager;
    @Autowired
    DepositoryFirstManager      depositoryFirstManager;
    @Autowired
    ProdRelationOutManager      prodRelationOutManager;

    /**
     * �Զ�ȡ������
     */
    @RequestMapping(value = "/timetask/autoComfirmTrade")
    public String closeTrade(Model model) throws Exception {

        model.addAttribute("result", "success");
        try {
            TradeListQuery query = new TradeListQuery();
            query.setStatus(EnumTradeStatus.WAIT_SELLER_SEND_GOODS.getKey());
            query.setIsWholesale("n");
            List<String> tradeTypes = new ArrayList<String>();
            tradeTypes.add(EnumTradeType.trade_type_0.getValue().toString());
            tradeTypes.add(EnumTradeType.trade_type_2.getValue().toString());
            tradeTypes.add(EnumTradeType.trade_type_3.getValue().toString());
            tradeTypes.add(EnumTradeType.trade_type_4.getValue().toString());
            tradeTypes.add(EnumTradeType.trade_type_5.getValue().toString());
            tradeTypes.add(EnumTradeType.trade_type_6.getValue().toString());
            tradeTypes.add(EnumTradeType.trade_type_7.getValue().toString());
            tradeTypes.add(EnumTradeType.trade_type_8.getValue().toString());
            tradeTypes.add(EnumTradeType.trade_type_9.getValue().toString());
            tradeTypes.add(EnumTradeType.trade_type_10.getValue().toString());
            query.setTradeTypes(tradeTypes);
            String gmtCreateStart = DateUtil.getDiffDate(new Date(), -7);
            String gmtCreateEnd = DateUtil.dtSimpleFormat(new Date());
            query.setGmtCreateStart(gmtCreateStart);
            query.setGmtCreateEnd(gmtCreateEnd);
            QueryPage querypage = tradeManager.getTradesWithNote(query, 1, 100);
            List<Trade> tradeList = (List<Trade>) querypage.getItems();
            for (Trade trade : tradeList) {
                //��Ʒ���ⵥ���޸�״̬
                if (StringUtil.isNotBlank(trade.getBuyerNote())) {
                    continue;
                }
                if (StringUtil.isNotBlank(trade.getSeviceNote())) {
                    continue;
                }
                //�����ж�����
                if (trade.getTradeType() == EnumTradeType.trade_type_4.getValue()) {
                    if (trade.getExpressId() == null) {
                        log.error("autoComfirmTrade---" + trade.getTid() + "����ѡ���������ͺ�,�ٽ����������!");
                        continue;
                    }
                }
                autoSendGoods(trade);

            }
            if (querypage.getTotalPage() > 1) {
                for (int i = 2; i < 10; i++) {
                    querypage = tradeManager.getTradesWithNote(query, i, 100);
                    tradeList = (List<Trade>) querypage.getItems();
                    for (Trade trade : tradeList) {
                        //��Ʒ���ⵥ���޸�״̬
                        if (StringUtil.isNotBlank(trade.getBuyerNote())) {
                            continue;
                        }
                        if (StringUtil.isNotBlank(trade.getSeviceNote())) {
                            continue;
                        }
                        //�����ж�����
                        if (trade.getTradeType() == EnumTradeType.trade_type_4.getValue()) {
                            if (trade.getExpressId() == null) {
                                log.error("autoComfirmTrade---" + trade.getTid()
                                          + "����ѡ���������ͺ�,�ٽ����������!");
                                continue;
                            }
                        }
                        autoSendGoods(trade);
                    }
                }
            }
        } catch (Exception ex) {
            log.error(ex);
            model.addAttribute("result", "fail");
        }
        return "/timetask/time_task_result";
    }

    private boolean autoSendGoods(Trade trade) {
        try {
            AdminAgent agent = new AdminAgent();
            agent.setUsername("system");
            agent.setId(new Long(88888888));
            String message = trade.getTid();
            String[] tIds = new String[1];
            tIds[0] = trade.getTid();
            if (!checkTrade(trade)) {
                return false;
            }

            //�ж���Ч��ʵ�ʿ���Ƿ��㹻���� zhangwy 2011/05/16
            boolean isfull = inAndOutDepositoryManager.judgeFactStorage(tIds[0]);
            if (!isfull) {
                message = message + "��Ʒʵ�ʿ�治��,���������";
                log.error(message);
                return false;
            }

            Long outId = new Long(0);
            try {
                outId = inAndOutDepositoryManager.addOutDepository(tIds, agent);
                trade.setStatus(EnumTradeStatus.WAIT_DISTRIBUTION.getKey());
                trade.setGmtModify(new Date());
                tradeManager.editTrade(trade);

                if ("2".equals(trade.getTradeType())) {// �������Ľӿ��޸Ķ���״̬
                    this.updateInterfaceTrade(trade);
                }
                if ("3".equals(trade.getTradeType())) {// �����Ա��ӿ��޸Ķ���״̬
                    this.updateInterfaceTradeForTaobao(trade);
                }

            } catch (Exception e) {
                message = message + "���ʧ��!";
                log.error(message);
                return false;
            }

            String re = this.OutDepositoryAuto(outId);
            if ("error".equals(re)) {
                return false;
            }
        } catch (Exception e) {
            log.error(trade.getTid() + "���ʧ��!");
            return false;
        }

        return true;
    }

    /*
     * ��ȡ��У�鶩������
     * 
     * @param trade
     * 
     * @return
     * 
     * @throws Exception
     */
    private boolean checkTrade(Trade trade) {
        String message = trade.getTid();
        boolean isVaild = true;

        // ֻ�еȴ�����״̬�Ķ����ſ��Է�
        if (!trade.getStatus().trim().equals(EnumTradeStatus.WAIT_SELLER_SEND_GOODS.getKey())) {
            message = message + "����״̬�����ϣ������������ ["
                      + EnumTradeStatus.WAIT_SELLER_SEND_GOODS.getValue() + "] !";
            log.error(message);
            isVaild = false;
            return isVaild;
        }

        Refund refund = refundManager.getRefundByOrder(trade.getTid());
        if (refund != null) {
            if (EnumRefundStatus.Goods_Confirm_Success.getKey().equals(refund.getStatus())
                || EnumRefundStatus.Refund_Close.getKey().equals(refund.getStatus())
                || EnumRefundStatus.Seller_Refuse_Refund.getKey().equals(refund.getStatus())) {

            } else {
                message = message + "�Ѿ��˿���������������ɹ�";
                log.error(message);
                isVaild = false;
                return isVaild;
            }
        }
        // �����жϣ�������������Ʒ��ʵ�ʿ�治��ʱ����������� zhangwy
        List<Order> orderList = orderManager.getOrdersNotInPackage(trade.getTid());
        if (orderList != null) {
            for (Order temp : orderList) {
                int factStorageNum = 0;
                Map tempMap = new HashMap();
                tempMap.put("goodsInstanceId", temp.getGoodsInstanceId());
                tempMap.put("depfirstId", trade.getDepFirstId());
                factStorageNum = storageManager.getStorageNumBySend(tempMap);
                if (factStorageNum < temp.getGoodsNumber()) {
                    message = message + "������ [" + temp.getGoodsTitle() + "] ��ʵ�ʿ�治��, ���ܽ������!";
                    log.error(message);
                    isVaild = false;
                    return isVaild;
                }
            }
        } else {
            message = message + "��������Ϊ��!";
            log.error(message);
            isVaild = false;
            return isVaild;
        }

        // �����жϣ�����п�λ���̵㣬��������� chenhang
        for (Order temp : orderList) {
            Map tempMap = new HashMap();
            tempMap.put("goodsInstanceId", temp.getGoodsInstanceId());
            tempMap.put("depfirstId", trade.getDepFirstId());
            List<Storage> sList = storageManager.getStorageListsByMap(tempMap);
            long compare = 0;
            for (Storage sTemp : sList) {
                DepLocation depLocation = depLocationManager.getDepLocationByLocationId(sTemp
                    .getLocId());
                Depository depository = depositoryService.getDepository(depLocation.getDepId());
                if (depLocationManager.getIsCheckCountById(new Long(sTemp.getLocId()),
                    EnumDepLocationIsCheck.Y.getKey()) > 0
                    || !(EnumDepositoryType.COMMON_DEP.getKey().equals(depository.getType()))) {
                    // �̵��У�����¼�����
                } else {
                    compare = compare + sTemp.getStorageNum();
                }
            }
            if (compare < temp.getGoodsNumber()) {
                // �̵���
                message = message + "������[" + temp.getGoodsTitle() + "]�п�λ���̵�";
                log.error(message);
                isVaild = false;
                return isVaild;
            }
        }
        return isVaild;
    }

    /*
     * ͨ���ӿ��޸Ķ�����Ϣ @param parMap @return
     */
    private boolean updateInterfaceTrade(Trade trade) {
        InterfaceApply interfaceApply = interfaceApplyManager.getInterfaceApplyByUserId(
            trade.getBuyId(), EnumInterfaceType.PAIPAI.getKey());
        InterfaceUserTrade interfaceUserTrade = interfaceUserTradeManager
            .getInterfaceUserTradeByTid(trade.getTid());

        if (interfaceApply != null) {
            TreeMap<String, String> signParams = new TreeMap<String, String>();
            signParams.put("uin", interfaceApply.getParamOne());
            signParams.put("token", interfaceApply.getParamTwo());
            signParams.put("spid", interfaceApply.getParamThree());
            signParams.put("seckey", interfaceApply.getParamFour());
            signParams.put("sellerUin", interfaceApply.getParamOne());

            if (interfaceUserTrade != null) {
                String itemUrl = "";
                Map<String, String> resultMap = null;
                // ���ұ�Ƕ��������
                itemUrl = Billing_Deal_DetailsUtil.CreateDealPreparingUrl(signParams,
                    interfaceUserTrade.getPaipaiTradeId());
                resultMap = Billing_Deal_DetailsUtil.parseInterfaceDealStatusXml(itemUrl);
                // ����޸ĳɹ�
                if ("0".equals(resultMap.get("errorCode"))) {
                    return true;
                } else {
                    log.error("�޸Ľӿڶ���[" + interfaceUserTrade.getPaipaiTradeId() + "]ʧ�ܣ�",
                        new Exception(resultMap.get("errorMessage")));
                }
            }
        }
        return false;
    }

    /**
     * ͨ���Ա��ӿ��޸Ķ�����Ϣ
     * 
     * @param parMap
     * @return
     */
    private boolean updateInterfaceTradeForTaobao(Trade trade) {
        TaobaoApply taobaoApply = taobaoInterfaceApplyManager.getInterfaceApplyByUserId(
            trade.getBuyId(), EnumInterfaceType.TAOBAO.getKey());
        InterfaceUserTrade interfaceUserTrade = interfaceUserTradeManager
            .getInterfaceUserTradeByTid(trade.getTid());

        if (taobaoApply != null) {
            /* �����Ա��ӿ� */
            /* �Ա�δ�ṩ�ýӿ� */
        }
        return false;
    }

    /**
     * @throws Exception
     * 
     * @Title: OutDepositoryAuto
     * @Description: TODO
     * @param @param trade
     * @param @return
     * @param @throws Exception
     * @return String
     * @author chenhang 2010/11/08 ���۶����Զ����
     * @throws
     */
    private String OutDepositoryAuto(Long outId) throws Exception {
        // added by chenhang 2010/11/04 ��� ���۶����Զ������λ
        if (outId != null && outId != 0) {
            OutDepository outDep = outDepositoryManager.getOutDepository(outId);// ��ó��ⵥ
            Long outDepId = outDep.getId();

            String batchNums = null;
            // ȡ�ó��ⵥ������Ϣ
            OutDepository outDepositoryDispaly = outDepositoryManager.getOutDepository(outDepId);
            // ȡ�ó��ⵥ������Ϣ
            List<OutDetailGoods> outDetailGoodsLists = outDetailManager
                .getOutDetailGoodsLists(outDepositoryDispaly.getId());

            Long otpId;
            if (outDetailGoodsLists != null && outDetailGoodsLists.size() > 0) {
                for (OutDetailGoods outDetailGoodsInfo : outDetailGoodsLists) {
                    outDetailGoodsInfo.setCatName(categoryManager
                        .getCatFullNameByCatcode(outDetailGoodsInfo.getCatCode()));
                    outDetailGoodsInfo.setAttributeName(attributeManager
                        .getFullAttributeStringByAttrs(outDetailGoodsInfo.getAttrs()));
                    Boolean f = true;// �����ж��Ƿ��ǵ�һ�ν���ѭ���������ò�ֵ���м���
                    otpId = outDetailGoodsInfo.getId();
                    // ȡ�ó���������Ϣ
                    OutDepository outDeposioryTemp = outDepositoryManager
                        .getOutDepositoryByDetailId(otpId);
                    // ȡ�û�����Ϣ
                    OutDetailBaseInfo outDetailBaseInfo = outDetailManager.getOutDetailBaseInfo(
                        otpId, outDeposioryTemp.getType());
                    if (outDetailBaseInfo == null) {
                        return "error";
                    }

                    if (StringUtil.isNotBlank(outDetailBaseInfo.getDepFirstId())) {
                        DepositoryFirst df = depositoryFirstManager.getDepositoryById(new Long(
                            outDetailBaseInfo.getDepFirstId()));
                        if (df != null) {
                            outDetailBaseInfo.setDepFirstName(df.getDepFirstName());
                        }
                    }

                    List<String> storageIdList = new ArrayList<String>();// �ֿ�ID
                    List<String> disCountList = new ArrayList<String>();// ��������
                    List<String> locIdList = new ArrayList<String>();// ��λ

                    // ȡ�ÿ������
                    if (StringUtil.isBlank(outDetailBaseInfo.getDepFirstId())) {
                        return "success";
                    }

                    Map mapSearch = new HashMap();
                    mapSearch.put("goodsInstanceId", outDetailBaseInfo.getGoodsInstanceId());
                    mapSearch.put("supplierId", outDetailBaseInfo.getSupplierId());
                    mapSearch.put("batchNums", batchNums);
                    mapSearch.put("depFirstId", new Long(outDetailBaseInfo.getDepFirstId()));
                    mapSearch.put("isWholesale", outDeposioryTemp.getIsWholesale());
                    mapSearch.put("tid", outDeposioryTemp.getTid());
                    int i = 0;// ���� ����ѭ��
                    List<OutDepositoryStorage> outStorageList = outDetailManager
                        .getOutStorageList(mapSearch);
                    long[] x = new long[outStorageList.size()];// �����λ��治�㣬������ֵ������һ����λ����
                    for (OutDepositoryStorage outDepositoryStorageInfo : outStorageList) {
                        Map getAmountMap = new HashMap();
                        getAmountMap.put("outDepId", outDetailBaseInfo.getOutDepositoryId());
                        getAmountMap.put("goodsInstanceId", outDetailBaseInfo.getGoodsInstanceId());
                        getAmountMap.put("goodsId", outDetailBaseInfo.getGoodsId());
                        getAmountMap.put("outDetailId", outDetailBaseInfo.getOutDetailId());
                        getAmountMap.put("storageId", outDepositoryStorageInfo.getId());
                        Long oriCount = prodRelationOutManager.getDistributedAmount(getAmountMap);
                        outDepositoryStorageInfo.setOriCount(oriCount == null ? "" : String
                            .valueOf(oriCount));
                        Boolean fl = false;

                        if (depLocationManager
                            .getIsCheckCountById(new Long(outDepositoryStorageInfo.getLocId()),
                                EnumDepLocationIsCheck.Y.getKey()) > 0) {
                            continue;// �жϸÿ�λ�Ƿ������̵㣬����ǣ�������һ��ѭ��
                        }

                        if (f) {// ����ǵ�һ�ν�ѭ��������Ҫ�Ŀ�����жϣ���������ò�ֵ�ж�
                            if (outDetailGoodsInfo.getOutNum() <= outDepositoryStorageInfo
                                .getStorageNum()) {
                                disCountList.add(outDetailGoodsInfo.getOutNum().toString());
                                fl = true;// �����һ����λ��������䣬��������ж�����ѭ��
                            } else {
                                disCountList.add(outDepositoryStorageInfo.getStorageNum()
                                    .toString());
                                x[i] = outDetailGoodsInfo.getOutNum()
                                       - outDepositoryStorageInfo.getStorageNum();
                            }
                        } else {
                            if (x[i] <= outDepositoryStorageInfo.getStorageNum()) {
                                disCountList.add(String.valueOf(x[i]));
                                fl = true;// �����ǰ��λ��������䣬��������ж�����ѭ��
                            } else {
                                disCountList.add(outDepositoryStorageInfo.getStorageNum()
                                    .toString());
                                x[i] = x[i] - outDepositoryStorageInfo.getStorageNum();
                            }
                        }
                        f = false;
                        storageIdList.add(outDepositoryStorageInfo.getId().toString());
                        locIdList.add(outDepositoryStorageInfo.getLocId().toString());
                        if (fl == true) {
                            break;// �����һ����λ��������䣬������ѭ��
                        }

                    }
                    String allowedInfo = outDepositoryAllowed(outDetailBaseInfo, storageIdList,
                        disCountList, locIdList);

                    String errorInfo;
                    Boolean succFlag;

                    if (StringUtil.isBlank(allowedInfo)) {
                        // ��Ʒ����
                        Map map = new HashMap();
                        map.put("outDetailBaseInfo", outDetailBaseInfo);
                        map.put("storageIdList", storageIdList);
                        map.put("disCountList", disCountList);
                        map.put("locIdList", locIdList);
                        map.put("isWholesale", outDeposioryTemp.getIsWholesale());
                        map.put("tid", outDeposioryTemp.getTid());
                        Boolean optFlag = outDetailManager.outDepositoryOptAuto(map,
                            outDeposioryTemp.getType());
                        if (optFlag) {
                            errorInfo = null;
                            succFlag = Boolean.TRUE;
                        } else {
                            errorInfo = "�����Ʒ�������ʧ��";
                            succFlag = Boolean.FALSE;
                        }
                    } else {
                        errorInfo = allowedInfo;
                        succFlag = Boolean.FALSE;
                    }
                    i = i + 1;
                }
            }
            return "";
        } else {
            return "error";
        }
        // ended by chenhang 2010/11/04 ��� ���۶����Զ������λ
    }

    /**
     * �ж��Ƿ���Գ���
     * 
     * @param outDetailBaseInfoForOut
     *            OutDetailBaseInfo
     * @param storageIdArray
     *            String[]
     * @param disCountArray
     *            String[]
     * @param locIdArray
     *            String[]
     * @return String
     * @author chenhang 2010/10/04
     */
    private String outDepositoryAllowed(OutDetailBaseInfo outDetailBaseInfoForOut,
                                        List<String> storageIdList, List<String> disCountList,
                                        List<String> locIdList) {
        // ���������Ϣ��ʧ��ֱ�ӷ��ش�����Ϣ
        if (outDetailBaseInfoForOut == null) {
            return "���������Ϣ��ʧ";
        }
        // ����ĳ�������
        Long inputCount = 0L;
        for (int i = 0; i < disCountList.size(); i++) {
            if (StringUtil.isNotBlank(disCountList.get(i))) {
                inputCount = inputCount + new Long(disCountList.get(i));
            }
        }
        return null;
    }

}
