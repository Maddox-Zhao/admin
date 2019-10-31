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
 * @version $Id: TradeTimetaskAction.java,v 0.1 2011-5-24 上午09:02:39 shengyong Exp $
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
     * 自动取消订单
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
                //产品出库单，修改状态
                if (StringUtil.isNotBlank(trade.getBuyerNote())) {
                    continue;
                }
                if (StringUtil.isNotBlank(trade.getSeviceNote())) {
                    continue;
                }
                //分销判断物流
                if (trade.getTradeType() == EnumTradeType.trade_type_4.getValue()) {
                    if (trade.getExpressId() == null) {
                        log.error("autoComfirmTrade---" + trade.getTid() + "请在选择物流配送后,再进行配货操作!");
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
                        //产品出库单，修改状态
                        if (StringUtil.isNotBlank(trade.getBuyerNote())) {
                            continue;
                        }
                        if (StringUtil.isNotBlank(trade.getSeviceNote())) {
                            continue;
                        }
                        //分销判断物流
                        if (trade.getTradeType() == EnumTradeType.trade_type_4.getValue()) {
                            if (trade.getExpressId() == null) {
                                log.error("autoComfirmTrade---" + trade.getTid()
                                          + "请在选择物流配送后,再进行配货操作!");
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

            //判定有效地实际库存是否足够分配 zhangwy 2011/05/16
            boolean isfull = inAndOutDepositoryManager.judgeFactStorage(tIds[0]);
            if (!isfull) {
                message = message + "产品实际库存不足,不能配货！";
                log.error(message);
                return false;
            }

            Long outId = new Long(0);
            try {
                outId = inAndOutDepositoryManager.addOutDepository(tIds, agent);
                trade.setStatus(EnumTradeStatus.WAIT_DISTRIBUTION.getKey());
                trade.setGmtModify(new Date());
                tradeManager.editTrade(trade);

                if ("2".equals(trade.getTradeType())) {// 调用拍拍接口修改订单状态
                    this.updateInterfaceTrade(trade);
                }
                if ("3".equals(trade.getTradeType())) {// 调用淘宝接口修改订单状态
                    this.updateInterfaceTradeForTaobao(trade);
                }

            } catch (Exception e) {
                message = message + "配货失败!";
                log.error(message);
                return false;
            }

            String re = this.OutDepositoryAuto(outId);
            if ("error".equals(re)) {
                return false;
            }
        } catch (Exception e) {
            log.error(trade.getTid() + "配货失败!");
            return false;
        }

        return true;
    }

    /*
     * 提取的校验订单方法
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

        // 只有等待发货状态的订单才可以发
        if (!trade.getStatus().trim().equals(EnumTradeStatus.WAIT_SELLER_SEND_GOODS.getKey())) {
            message = message + "交易状态不符合，不允许操作。 ["
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
                message = message + "已经退款，不能配货，配货不成功";
                log.error(message);
                isVaild = false;
                return isVaild;
            }
        }
        // 加入判断，若订单中有商品的实际库存不够时，不允许配货 zhangwy
        List<Order> orderList = orderManager.getOrdersNotInPackage(trade.getTid());
        if (orderList != null) {
            for (Order temp : orderList) {
                int factStorageNum = 0;
                Map tempMap = new HashMap();
                tempMap.put("goodsInstanceId", temp.getGoodsInstanceId());
                tempMap.put("depfirstId", trade.getDepFirstId());
                factStorageNum = storageManager.getStorageNumBySend(tempMap);
                if (factStorageNum < temp.getGoodsNumber()) {
                    message = message + "订单中 [" + temp.getGoodsTitle() + "] 的实际库存不够, 不能进行配货!";
                    log.error(message);
                    isVaild = false;
                    return isVaild;
                }
            }
        } else {
            message = message + "订单内容为空!";
            log.error(message);
            isVaild = false;
            return isVaild;
        }

        // 加入判断，如果有库位在盘点，则不允许配货 chenhang
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
                    // 盘点中，不记录库存数
                } else {
                    compare = compare + sTemp.getStorageNum();
                }
            }
            if (compare < temp.getGoodsNumber()) {
                // 盘点中
                message = message + "订单中[" + temp.getGoodsTitle() + "]有库位在盘点";
                log.error(message);
                isVaild = false;
                return isVaild;
            }
        }
        return isVaild;
    }

    /*
     * 通过接口修改订单信息 @param parMap @return
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
                // 卖家标记订单配货中
                itemUrl = Billing_Deal_DetailsUtil.CreateDealPreparingUrl(signParams,
                    interfaceUserTrade.getPaipaiTradeId());
                resultMap = Billing_Deal_DetailsUtil.parseInterfaceDealStatusXml(itemUrl);
                // 如果修改成功
                if ("0".equals(resultMap.get("errorCode"))) {
                    return true;
                } else {
                    log.error("修改接口订单[" + interfaceUserTrade.getPaipaiTradeId() + "]失败！",
                        new Exception(resultMap.get("errorMessage")));
                }
            }
        }
        return false;
    }

    /**
     * 通过淘宝接口修改订单信息
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
            /* 调用淘宝接口 */
            /* 淘宝未提供该接口 */
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
     * @author chenhang 2010/11/08 销售订单自动入库
     * @throws
     */
    private String OutDepositoryAuto(Long outId) throws Exception {
        // added by chenhang 2010/11/04 入库 销售订单自动分配库位
        if (outId != null && outId != 0) {
            OutDepository outDep = outDepositoryManager.getOutDepository(outId);// 获得出库单
            Long outDepId = outDep.getId();

            String batchNums = null;
            // 取得出库单主表信息
            OutDepository outDepositoryDispaly = outDepositoryManager.getOutDepository(outDepId);
            // 取得出库单详情信息
            List<OutDetailGoods> outDetailGoodsLists = outDetailManager
                .getOutDetailGoodsLists(outDepositoryDispaly.getId());

            Long otpId;
            if (outDetailGoodsLists != null && outDetailGoodsLists.size() > 0) {
                for (OutDetailGoods outDetailGoodsInfo : outDetailGoodsLists) {
                    outDetailGoodsInfo.setCatName(categoryManager
                        .getCatFullNameByCatcode(outDetailGoodsInfo.getCatCode()));
                    outDetailGoodsInfo.setAttributeName(attributeManager
                        .getFullAttributeStringByAttrs(outDetailGoodsInfo.getAttrs()));
                    Boolean f = true;// 用于判定是否是第一次进入循环，以利用差值进行计算
                    otpId = outDetailGoodsInfo.getId();
                    // 取得出库主表信息
                    OutDepository outDeposioryTemp = outDepositoryManager
                        .getOutDepositoryByDetailId(otpId);
                    // 取得基本信息
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

                    List<String> storageIdList = new ArrayList<String>();// 仓库ID
                    List<String> disCountList = new ArrayList<String>();// 出库数量
                    List<String> locIdList = new ArrayList<String>();// 库位

                    // 取得库存数据
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
                    int i = 0;// 变量 用于循环
                    List<OutDepositoryStorage> outStorageList = outDetailManager
                        .getOutStorageList(mapSearch);
                    long[] x = new long[outStorageList.size()];// 如果库位库存不足，则保留差值，从下一个库位出货
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
                            continue;// 判断该库位是否正在盘点，如果是，进入下一次循环
                        }

                        if (f) {// 如果是第一次进循环，用需要的库存数判断，如果不是用差值判断
                            if (outDetailGoodsInfo.getOutNum() <= outDepositoryStorageInfo
                                .getStorageNum()) {
                                disCountList.add(outDetailGoodsInfo.getOutNum().toString());
                                fl = true;// 如果第一个库位就满足分配，则在最后判断跳出循环
                            } else {
                                disCountList.add(outDepositoryStorageInfo.getStorageNum()
                                    .toString());
                                x[i] = outDetailGoodsInfo.getOutNum()
                                       - outDepositoryStorageInfo.getStorageNum();
                            }
                        } else {
                            if (x[i] <= outDepositoryStorageInfo.getStorageNum()) {
                                disCountList.add(String.valueOf(x[i]));
                                fl = true;// 如果当前库位就满足分配，则在最后判断跳出循环
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
                            break;// 如果第一个库位就满足分配，则跳出循环
                        }

                    }
                    String allowedInfo = outDepositoryAllowed(outDetailBaseInfo, storageIdList,
                        disCountList, locIdList);

                    String errorInfo;
                    Boolean succFlag;

                    if (StringUtil.isBlank(allowedInfo)) {
                        // 商品出库
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
                            errorInfo = "库存商品出库分配失败";
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
        // ended by chenhang 2010/11/04 入库 销售订单自动分配库位
    }

    /**
     * 判断是否可以出库
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
        // 出库基本信息丢失则直接返回错误信息
        if (outDetailBaseInfoForOut == null) {
            return "出库基本信息丢失";
        }
        // 输入的出库总数
        Long inputCount = 0L;
        for (int i = 0; i < disCountList.size(); i++) {
            if (StringUtil.isNotBlank(disCountList.get(i))) {
                inputCount = inputCount + new Long(disCountList.get(i));
            }
        }
        return null;
    }

}
