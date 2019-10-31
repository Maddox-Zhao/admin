/**
 * @Title: ShoppingCartServiceImpl.java
 * @Package com.huaixuan.network.biz.service.impl
 * @Description: TODO
 * @author liuwd weida-liu@foxmail.com
 * @date 2011-3-4 ����07:38:39
 * @version V1.0
 */
package com.huaixuan.network.biz.service.trade;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.userdetails.User;

import com.huaixuan.network.biz.dao.ShoppingCartDao;
import com.huaixuan.network.biz.domain.ShoppingCart;
import com.huaixuan.network.biz.domain.goods.AvailableStock;
import com.huaixuan.network.biz.domain.goods.GoodsAttr;
import com.huaixuan.network.biz.domain.goods.GoodsInstance;
import com.huaixuan.network.biz.service.goods.GoodsAttrManager;
import com.huaixuan.network.biz.service.goods.GoodsInstanceManager;
import com.huaixuan.network.biz.service.goods.GoodsManager;
import com.huaixuan.network.biz.service.goods.PromationManager;
import com.huaixuan.network.common.util.DateUtil;
import com.huaixuan.network.common.util.Result;
import com.hundsun.network.melody.common.util.StringUtil;

/**
 * @ClassName: ShoppingCartServiceImpl
 * @Description: TODO
 * @author liuwd weida-liu@foxmail.com
 * @date 2011-3-4 ����07:38:39
 *
 */
public class ShoppingCartServiceImpl implements ShoppingCartService {
    /* @model: ע��ShoppingCartDao */

	@Autowired
    ShoppingCartDao      shoppingCartDao;

	@Autowired
    GoodsInstanceManager goodsInstanceManager;

	@Autowired
    GoodsManager        goodsManager;

    private String              SUCCESS = "success";

    @Autowired
    GoodsAttrManager    goodsAttrManager;

//    @Autowired
//    StockoutManager     stockoutManager;

    @Autowired
    PromationManager    promationManager;

    /**���ݲ�Ʒ������ȷ����Ʒid
     * @return
     */
    public Long confirmGoodsInstanceId(String goodsAttrIds, Long goodsId) {
        String[] goods;
        StringBuilder sb = new StringBuilder();
        if (StringUtil.isNotEmpty(goodsAttrIds)) {
            goods = goodsAttrIds.split(",");
            for (String attr : goods) {
                if (!attr.equalsIgnoreCase("*")) {
                    GoodsAttr goodsAttr = goodsAttrManager.getGoodsAttr(Long.parseLong(attr));
                    if (null != goodsAttr) {
                        sb.append(goodsAttr.getAttrCode()).append("=").append(
                            goodsAttr.getAttrValue()).append(";");
                    }
                }
            }
        }
        Long instanceId = null;
        List<GoodsInstance> goodsInstance = goodsInstanceManager.findGoodsInstances(goodsId);
        for (GoodsInstance instance : goodsInstance) {
            if (StringUtil.isEmpty(instance.getAttrs()) && StringUtil.isEmpty(sb.toString())) {
                instanceId = instance.getId();
                return instanceId;
            }
            String attrs = null;
            if (StringUtil.isNotBlank(instance.getAttrs())) {
                attrs = instance.getAttrs().replaceAll("\\r", "").replaceAll("\\n", "");
                if (compareInstanceDesc(sb.toString(), attrs)) {
                    instanceId = instance.getId();
                    return instanceId;
                }
            }
        }
        return instanceId;
    }

    /**ȱ���Ǽ�
     * @return
     */
    public Result<Object> createStockOutGoods(User user, Long goodsId, String goodsAttrIds) {
        Result<Object> result = new Result<Object>();
//        Stockout stockeout = new Stockout();
//        if (null != user) {//�û�û�е�½���û������ʼ�Ĭ��Ϊ��
//            stockeout.setUserId(user.getId());
//            stockeout.setUserEmail(user.getEmail());
//        }
//        try {
//            Long goodsInstaceId = confirmGoodsInstanceId(goodsAttrIds, goodsId);
//            if(goodsInstaceId==null){
//                result.setResult(1);
//                return result;
//            }
//            GoodsInstance goodsTmpInstance = goodsInstanceManager.getInstance(goodsInstaceId);
//            stockeout.setGoodsId(goodsTmpInstance.getGoodsId());
//            stockeout.setGoodsInstanceId(goodsTmpInstance.getId());
//            stockeout.setGoodsInstanceName(goodsTmpInstance.getInstanceName());
//            stockeout.setNotifyStatus("init");
//            Date date = new Date();
//            stockeout.setGmtCreate(date);
//            long obj = stockoutManager.insertStockout(stockeout);
//            if (obj > 0) {
//                result.setResult(0);
//            } else {
//                result.setResult(1);
//            }
//        } catch (Exception ex) {
////            this.log.error(ex);
//            result.setResult(1);
//        }
        return result;
    }

    /**
     * �Ƚ���ѡ��������Ƿ���ͬ
     */
    public boolean compareInstanceDesc(String attrs, String instanceAttrs) {
        String[] attr = attrs.split(";");
        String[] instanceAttr = instanceAttrs.split(";");
        boolean attrFlag = true;
        for (String attrVale : attr) {
            if (StringUtil.isNotEmpty(attrVale)) {
                boolean flag = false;
                for (String instanceVale : instanceAttr) {
                    if (StringUtil.isNotEmpty(instanceVale)) {
                        if (instanceVale.equalsIgnoreCase(attrVale)) {
                            flag = true;
                            break;
                        }
                    }
                }
                if (!flag) {
                    attrFlag = false;
                    return false;
                }
            }
        }
        if (attrFlag) {
            return true;
        } else {
            return false;
        }

    }

    /**
     * ������Ʒѡ������id list�õ���Ʒѡ������id string,�ԡ�*���ָ�
     * @param goodsAttrIds
     * @return
     */
    private String generateAttrIds(String goodsAttrIds) {
        StringBuilder sb = new StringBuilder();
        if (StringUtil.isEmpty(goodsAttrIds) || StringUtil.equalsIgnoreCase("*", goodsAttrIds)) {
            sb.append("*");
            return sb.toString();
        }
        String[] ss = goodsAttrIds.split(",");
        for (String s : ss) {
            sb.append(s);
            sb.append("*");
        }
        return sb.toString();
    }

    /**
     * �ײ�����ҳ�棬����ײ͵����ﳵ
     * @return
     * @throws Exception
     */
    public Result<Object> addPromationToShoppingCart(String goodsIds, Integer goodsNum,
                                                     String goodsAttrIds,
                                                     HttpServletRequest request,
                                                     HttpServletResponse response, boolean isLoged,
                                                     Long userId, String promationId) {
        Result<Object> result = new Result<Object>();
//        try {
//            //cookie�й��ﳵ�ĸ���
//            int count = ShoppingCartCookieUtil.getShoppingCartCount(request);
//            //���ƹ��ﳵ�ĸ������ܳ������ֵ
//            if (count >= Constants.SHOPPINGCART_MAXNUM) {
//                result.setMessage(getText("errors.count.shoppingCart_maxnum"));
//                result.setResult(1);
//                return result;
//
//            }
//            //�ײ�����Ʒid
//            String[] gIds = StringUtil.split(goodsIds, ";");
//            String[] gAttrIds = StringUtil.split(goodsAttrIds, ";");
//            List<Long> goodsInstanceList = new ArrayList<Long>();
//            for (int i = 0; i < gIds.length; i++) {
//                String gAttrId = gAttrIds[i];
//                Long goodsInstaceId = confirmGoodsInstanceId(gAttrId, Long.parseLong(gIds[i]));
//                if (null == goodsInstaceId) {
//                    result.setMessage(getText("errors.count.shoppingCart_nonentity"));
//                    result.setResult(1);
//                    return result;
//                }
//                GoodsInstance goodsTmpInstance = goodsInstanceManager.getInstance(goodsInstaceId);
//                //��Ʒ��������Ƿ��㹻����Ʒ�Ƿ�������
//                if (goodsTmpInstance.getExistNum() < goodsNum) {
//                    result.setMessage(this.getText("nopopedom.goods.addGoodToShoppingCart.wrong"));
//                    result.setResult(1);
//                    return result;
//                }
//                goodsInstanceList.add(goodsInstaceId);
//            }
//
//            if (null != gIds && gIds.length > 0) {
//                int i = 0;
//                Date date = new Date();
//                String timetag = "" + date.getTime();
//                for (String gId : gIds) {
//                    String gAttrId = gAttrIds[i];
//                    Long goodsInstaceId = goodsInstanceList.get(i);
//                    String s = this.generateAttrIds(gAttrId);
//
//                    //�û��Ƿ��¼
//                    //��¼�û��������ײ���Ʒ��Ϣ��db
//                    if (isLoged) {
//                        //���ݿ��й��ﳵ�ĸ���
//                        int dbcount = getShoppingCartCount(userId);
//                        //�������ݿ��й��ﳵ�ĸ������ܳ������ֵ
//                        if (dbcount >= Constants.SHOPPINGCART_MAXNUM) {
//                            result.setMessage(getText("errors.count.shoppingCart_maxnum"));
//                            result.setResult(1);
//                            return result;
//
//                        }
//                        //����ǰ��Ҫ��ӵ����ﳵ����Ʒ��Ϣ���һ�����ﳵ
//                        ShoppingCart spTmp = new ShoppingCart();
//                        spTmp.setUserId(userId);
//                        spTmp.setGoodsId(Long.parseLong(gId));
//                        spTmp.setGoodsNumber(goodsNum);
//                        if (StringUtil.isNotEmpty(gAttrId)
//                            && !StringUtil.equalsIgnoreCase("*", gAttrId)) {
//                            spTmp.setGoodsAttrIds(StringUtil.replace(gAttrId, ",", ";"));
//                        }
//                        spTmp.setPromationId(Long.parseLong(promationId));
//                        spTmp.setTimeTag(timetag);
//                        spTmp.setGmtCreate(new Date());
//                        spTmp.setGmtModify(new Date());
//                        spTmp.setGoodsInstanceId(goodsInstaceId);
//                        addPromationToShoppingCart(spTmp);
//                    }
//                    //δ��¼���ο�
//                    if (!isLoged) {
//                        //�����Ʒ��Ϣ��cookie
//                        ShoppingCartCookieUtil.addPromationToShoppingCart(request, response,
//                            promationId, timetag, gId, goodsInstaceId.toString(), s, goodsNum);
//                    }
//                    i++;
//                }
//            }
//            result.setResult(0);
//            result.setMessage(SUCCESS);
//        } catch (Exception e) {
//            this.log.error("error :" + e);
//            result.setMessage(getText("errors.count.shoppingCart_maxnum"));
//            result.setResult(1);
//        }
        return result;
    }

    /**
     * �޸Ĺ��ﳵ��Ʒ������(����������Ʒ)
     * @return
     */
    public Result<Object> editShoppingCartNum(Long shoppingCartId, Long goodsInstanceId,
                                              Integer goodsNumber, boolean isLoged, Long userId,
                                              HttpServletRequest request,
                                              HttpServletResponse response, boolean isPromation) {
        Result<Object> result = new Result<Object>();

        if (goodsInstanceId == null) {
            result.setResult(1);
            result.setMessage("goodsId is null");
            return result;// û�д��빺�ﳵIDʱ����
        }
        if (isLoged) {//��½�û�
            if (goodsNumber == null || shoppingCartId == null) {
                result.setResult(1);
                result.setMessage("cartId is null or goodsNumber is null");
                return result;// û�д��빺�ﳵIDʱ����
            }
            ShoppingCart shoppingCart = getShoppingCart(shoppingCartId);
            if (shoppingCart == null || !userId.equals(shoppingCart.getUserId())) {
                result.setResult(1);
                result.setMessage("the shoppingCart is not exist or it is not yours");
                return result;// ���빺�ﳵID��Ӧ�Ĺ��ﳵ�����ڻ��߲���������û�ʱ����
            }
            if (goodsNumber == null || goodsNumber < 1) {
                goodsNumber = 1;
            }

            //�����ײͣ�������ײ� zhangwy
            if(isPromation){
            	shoppingCart.setPromationId(new Long(1)); //1��ʶ���ײ�
            }
            shoppingCart.setGmtModify(new Date());
            shoppingCart.setGoodsNumber(goodsNumber);
            editShoppingCart(shoppingCart);
        } else {
            if (goodsNumber == null || goodsNumber < 1) {
                goodsNumber = Integer.parseInt("1");
            }
            //�޸Ŀͻ��˵�Cookies
//            ShoppingCartCookieUtil.saveShoppingCartCookie(request, response, goodsInstanceId, Long
//                .parseLong(goodsNumber + ""));
        }
        result.setResult(0);
        return result;
    }

    /**
     * @param goodsId
     * @param goodsInstaceId
     * @param goodsNum
     * @param goodsAttrIds
     * @param isAdd �Ƿ�����
     * @param request
     * @param response
     * @param isLoged�Ƿ��½
     * @param userId�û�id
     * @return
     * @throws Exception
     */
    public Result<Object> addGoodsToShoppingCarts(Long goodsId, Integer goodsNum,
                                                  String goodsAttrIds, boolean isAdd,
                                                  HttpServletRequest request,
                                                  HttpServletResponse response, boolean isLoged,
                                                  Long userId) {
        Result<Object> result = new Result<Object>();
//        try {
//            if (null == goodsId) {
//                result.setResult(1);
//                result.setMessage(getText("nopopedom.goods.id.null"));
//                return result;
//            }
//            Goods good = this.goodsManager.getGoods(goodsId);
//            Long goodsInstaceId = confirmGoodsInstanceId(goodsAttrIds, goodsId);
//            if (null == goodsInstaceId) {
//                result.setResult(1);
//                result.setMessage(getText("errors.count.shoppingCart_nonentity"));
//                return result;
//            }
//            GoodsInstance goodsTmpInstance = goodsInstanceManager.getInstance(goodsInstaceId);
//            //��Ʒ��������Ƿ��㹻����Ʒ�Ƿ�������
//            if (goodsTmpInstance.getExistNum() < goodsNum
//                || !StringUtil.equalsIgnoreCase(EnumGoodsStatus.ON_SALE.getKey(), good
//                    .getGoodsStatus())) {
//                result.setResult(2);
//                result.setMessage(getText("nopopedom.goods.addGoodToShoppingCart.wrong"));
//                //            result.setMessage("NoGoodsNumber");
//                return result;
//            }
//            //����Ĺ����������Ϸ�
//            if (goodsNum <= 0) {
//                result.setResult(1);
//                result.setMessage(getText("nopopedom.goods.addGoodToShoppingCart.goodsNum.wrong"));
//                return result;
//            }
//            //cookie�й��ﳵ�ĸ���
//            int count = ShoppingCartCookieUtil.getShoppingCartCount(request);
//            //���ƹ��ﳵ�ĸ������ܳ������ֵ
//            if (count >= Constants.SHOPPINGCART_MAXNUM) {
//                result.setResult(1);
//                result.setMessage(getText("errors.count.shoppingCart_maxnum"));
//                return result;
//            }
//            String s = this.generateAttrIds(goodsAttrIds);
//
//            //�жϸ���Ʒ�Ƿ����ײͺͻ�ȡ�ײ���Ϣ zhangwy
//            boolean isPromation = false;
//            List<PromationGive> newPromationGiveList = promationManager.getNewPromationGiveList(goodsId);
//            if(newPromationGiveList!=null && newPromationGiveList.size()>0){
//            	isPromation = true;
//            }
//
//            //�û��Ƿ��¼
//            //��¼�û���������Ʒ��Ϣ��db
//            if (isLoged) {
//                //ͨ����ƷID�͵�ǰ��½�û��ж��ǵ�ǰ��¼�Ƿ���ڹ��ﳵ����
//                Map<String, Object> parameterMap = new HashMap<String, Object>();
//                parameterMap.put("goodsInstanceId", goodsInstaceId);
//                parameterMap.put("userId", userId);
//               // parameterMap.put("promationId", -1);
//                parameterMap.put("isWholesale", "n");
//                List<ShoppingCart> shopList = getShoppingCartsByParameterMap(parameterMap);
//                if (null != shopList && shopList.size() == 1) {
//                    ShoppingCart shopCart = shopList.get(0);
//                    if (null != shopCart) {
//                        goodsNum = shopCart.getGoodsNumber() + goodsNum;
//                        if (goodsTmpInstance.getExistNum() < goodsNum) {
//                            result.setResult(2);
//                            result
//                                .setMessage(getText("nopopedom.goods.addGoodToShoppingCart.wrong"));
//                            return result;
//                        }
//                        Result<Object> editRs = null;
//                        if (isAdd) {
//                            editRs = editShoppingCartNum(shopCart.getId(), goodsInstaceId,
//                                goodsNum, isLoged, userId, request, response,isPromation);
//                            if (editRs.getResult() == 0) {
//                                result.setResult(0);
//                                result.setMessage(SUCCESS);
//                                return result;
//                            }
//                        }
//
//                    }
//                }
//                //���ݿ��й��ﳵ�ĸ���
//                int dbcount = shoppingCartDao.getPtGoodsUserNum(userId);
//                //�������ݿ��й��ﳵ�ĸ������ܳ������ֵ
//                if (dbcount >= Constants.SHOPPINGCART_MAXNUM) {
//                    result.setResult(1);
//                    result.setMessage(getText("errors.count.shoppingCart_maxnum"));
//                    return result;
//                }
//                //����ǰ��Ҫ��ӵ����ﳵ����Ʒ��Ϣ���һ�����ﳵ
//                ShoppingCart spTmp = new ShoppingCart();
//                spTmp.setUserId(userId);
//                spTmp.setGoodsId(goodsId);
//                spTmp.setGoodsNumber(goodsNum);
//                if (StringUtil.isNotEmpty(goodsAttrIds)
//                    && !StringUtil.equalsIgnoreCase("*", goodsAttrIds)) {
//                    spTmp.setGoodsAttrIds(StringUtil.replace(goodsAttrIds, ",", ";"));
//                }
//                spTmp.setGoodsInstanceId(goodsInstaceId);
//                spTmp.setGmtCreate(new Date());
//                spTmp.setGmtModify(new Date());
//                spTmp.setIsWholesale("n");
//                if(isPromation){
//                	spTmp.setPromationId(new Long(1)); //���ײ�Ĭ��Ϊ1
//                }
//                if (isAdd) {
//                    addGoodsShoppingCart(spTmp, userId);
//                }
//            }
//            //δ��¼���ο�
//            if (!isLoged) {
//                List<ShoppingCart> shopList = ShoppingCartCookieUtil.getShoppingCartsList(request);
//                for (ShoppingCart shopCart : shopList) {
//                    if (null != shopCart && shopCart.getGoodsInstanceId().equals(goodsInstaceId)) {
//                        if (goodsTmpInstance.getExistNum() < (shopCart.getGoodsNumber() + goodsNum)) {
//                            result.setResult(2);
//                            result
//                                .setMessage(getText("nopopedom.goods.addGoodToShoppingCart.wrong"));
//                            return result;
//                        }
//                    }
//                }
//                //�����Ʒ��Ϣ��cookie
//                if (isAdd) {
//                    ShoppingCartCookieUtil.addGoodsShoppingCartCookie(request, response,
//                        goodsInstaceId, good.getId(), s, goodsNum);
//                }
//            }
//            result.setResult(0);
//            result.setMessage(SUCCESS);
//        } catch (Exception e) {
////            this.log.error("error :  " + e);
//            result.setResult(1);
//            result.setMessage(getText("nopopedom.goods.addGoodToShoppingCart.goodsNum.wrong"));
//        }
        return result;
    }

    /**ͨ����ƷID�õ���ͬ�ֿ�Ŀ��
     * @param goodsId ��ƷID
     * @param goodsAttrIds ��Ʒ����
     * ��ƷID �� ��Ʒ����ȷ�� ��ƷID
     * @return
     */
    public List<AvailableStock> getAvailableStockList(Long goodsId,String goodsAttrIds){
        Long instanceId=confirmGoodsInstanceId(goodsAttrIds,goodsId);
        if(null!=instanceId){
          return  goodsInstanceManager.getAvailableStockListByInstanceId(instanceId);
        }
        return null;
    }

    /**
     * �ϲ����ﳵ����Ҫ���ڵ�¼ʱ����Cookie�еĹ��ﳵ���û����ݿ��еĹ��ﳵ�ϲ������ϲ���Ĺ��ﳵ���浽���ݿ⣬���cookie�й��ﳵ�����������µĹ��ﳵ��
     * @return
     */
    public List<ShoppingCart> mergerShoppingCart(List<ShoppingCart> cookieShoppingCartList,
                                                 Long userId) {
        List<ShoppingCart> newShoppingCartList = new ArrayList<ShoppingCart>();
        if (cookieShoppingCartList != null && cookieShoppingCartList.size() > 0) {
            for (ShoppingCart cookieShoppingCart : cookieShoppingCartList) {
                cookieShoppingCart.setUserId(userId);
            }
        }
        List<ShoppingCart> dbShoppingCartList = getShoppingCartsByUserId(userId);
        if (dbShoppingCartList == null || dbShoppingCartList.size() < 1) {
            newShoppingCartList = cookieShoppingCartList;
        } else if (cookieShoppingCartList == null || cookieShoppingCartList.size() < 1) {
            newShoppingCartList = dbShoppingCartList;
        } else {
            for (ShoppingCart dbShoppingCart : dbShoppingCartList) {
                ShoppingCart shoppingCartTmp = dbShoppingCart;
                for (ShoppingCart cookieShoppingCart : cookieShoppingCartList) {
                    if (null == cookieShoppingCart.getPromationId()
                        && null == dbShoppingCart.getPromationId()
                        && cookieShoppingCart.getGoodsInstanceId().equals(
                            dbShoppingCart.getGoodsInstanceId())) {
                        //�ϲ����� zhangwy
                        long goodsNumber = cookieShoppingCart.getGoodsNumber()
                                           + dbShoppingCart.getGoodsNumber();
                        GoodsInstance goodsInstance = goodsInstanceManager
                            .getInstance(cookieShoppingCart.getGoodsInstanceId());
                        //���������Ʒ���ÿ��,�Ͱѿ��ÿ��ȫ���������ﳵ
                        if ((cookieShoppingCart.getIsWholesale().equals("n"))&& (goodsNumber > goodsInstance.getExistNum())) {
                            goodsNumber = goodsInstance.getExistNum();
                        }
                        shoppingCartTmp.setGoodsNumber(Integer.parseInt(goodsNumber + ""));
                        shoppingCartTmp.setGmtModify(cookieShoppingCart.getGmtModify());
                        //���Ѿ��ϲ����Ƴ�ԭ���С�
                        cookieShoppingCartList.remove(cookieShoppingCart);
                        break;
                    }
                }
                newShoppingCartList.add(shoppingCartTmp);
            }
            //��ʣ�µ�cookieShoppingCartListȫ��ֱ����ӵ��µ��б��С�
            for (ShoppingCart cookieShoppingCart : cookieShoppingCartList) {
                newShoppingCartList.add(cookieShoppingCart);
            }
        }
        //���µĹ��ﳵ���浽���ݿ⡣
        //��������ݿ��еĹ��ﳵ
        removeShoppingCarts(dbShoppingCartList);
        //�����µĹ��ﳵ
        addShoppingCarts(newShoppingCartList);
        return newShoppingCartList;
    }

    public void addShoppingCarts(List<ShoppingCart> shoppingCartList) {
        if (null == shoppingCartList || shoppingCartList.size() < 1) {
            return;
        }
        shoppingCartDao.addShoppingCarts(shoppingCartList);
    }

    /**
     * ��½����Ʒ����ҳ�棬�����Ʒ�����ﳵ,�������ѡ��������ͬ��ͬһ����Ʒ���޸ĸ���Ʒ�Ĺ�������
     * @param shoppingCart
     * @param userId
     * @throws Exception
     */
    public void addGoodsShoppingCart(ShoppingCart shoppingCart, Long userId) throws Exception {
        Long goodsId = shoppingCart.getGoodsId();
        String attrIds = shoppingCart.getGoodsAttrIds();
        ShoppingCart sc = getShoppingCartOfCommonGoods(userId, goodsId, attrIds);
        if (null == sc) {
            shoppingCartDao.addShoppingCart(shoppingCart);
        } else {
            int num = shoppingCart.getGoodsNumber();
            int oldNum = sc.getGoodsNumber();
            sc.setGoodsNumber(num + oldNum);
            shoppingCartDao.editShoppingCart(sc);
        }
    }

    /**
     * �û��ķ��ײ͡���Ӧ��ĳ����Ʒ��ĳ��ѡ�����ԵĹ��ﳵ
     * @param userId
     * @param goodsId
     * @param attrIds
     * @return
     */
    public ShoppingCart getShoppingCartOfCommonGoods(Long userId, Long goodsId, String attrIds) {
        return shoppingCartDao.getShoppingCartOfCommonGoods(userId, goodsId, attrIds);
    }

    /**
     * ��¼������ײ�����Ʒ�����ﳵ
     * @param shoppingCart
     * @throws Exception
     * @see com.hundsun.bible.facade.trade.ShoppingCartManager#addPromationToShoppingCart(com.hundsun.bible.domain.model.ShoppingCart)
     */
    public void addPromationToShoppingCart(ShoppingCart shoppingCart) throws Exception {
        shoppingCartDao.addShoppingCart(shoppingCart);
    }

    /**
     * ��ѯĳ�û�������ShoppingCart���ﳵ�����
     * @param userId �û�ID
     * @return ����ShoppingCart����ļ���
     */
    public List<ShoppingCart> getShoppingCartsByUserId(Long userId) {
        if (null == userId) {
            return null;
        }
        Map parameterMap = new HashMap();
        parameterMap.put("userId", userId);
        List<ShoppingCart> shoppingCartList = shoppingCartDao
            .getShoppingCartsByParameterMap(parameterMap);
        return shoppingCartList;
    }

    /**
     * ��ѯĳ�û���ĳ����Ʒ�Ĺ��ﳵ��Ϣ
     * @param userId �û�ID
     * @param goodId ��ƷID
     * @return ����ShoppingCart����
     */
    @SuppressWarnings("unchecked")
    public ShoppingCart getShoppingCartByUserIdAndGoodId(Long userId, Long goodId) {
        if (null == userId || null == goodId) {
            return null;
        }
        Map parameterMap = new HashMap();
        parameterMap.put("userId", userId);
        parameterMap.put("goodsId", goodId);
        List<ShoppingCart> shoppingCartList = shoppingCartDao
            .getShoppingCartsByParameterMap(parameterMap);
        if (null == shoppingCartList || shoppingCartList.size() < 1) {
            return null;
        }
        return shoppingCartList.get(0);
    }

    @SuppressWarnings("unchecked")
    public List<ShoppingCart> getShoppingCartListByUserIdAndGoodId(Long userId, Long goodsId,
                                                                   Long promationId, String timeTag) {
        if (null == userId || null == promationId) {
            return null;
        }
        Map parameterMap = new HashMap();
        parameterMap.put("userId", userId);
        parameterMap.put("goodsId", goodsId);
        parameterMap.put("promationId", promationId);
        parameterMap.put("timeTag", timeTag);
        List<ShoppingCart> shoppingCartList = shoppingCartDao
            .getShoppingCartListByUserIdAndGoodId(parameterMap);
        if (null == shoppingCartList || shoppingCartList.size() < 1) {
            return null;
        }
        return shoppingCartList;
    }

    public List<ShoppingCart> getShoppingCartListByGoodIdAndTimeTag(ShoppingCart shoppingCart) {
        if (null == shoppingCart) {
            return null;
        }
        Map parameterMap = new HashMap();
        parameterMap.put("userId", shoppingCart.getUserId());
        parameterMap.put("promationId", shoppingCart.getPromationId());
        parameterMap.put("timeTag", shoppingCart.getTimeTag());
        List<ShoppingCart> shoppingCartList = shoppingCartDao
            .getShoppingCartListByGoodIdAndTimeTag(parameterMap);
        if (null == shoppingCartList || shoppingCartList.size() < 1) {
            return null;
        }
        return shoppingCartList;
    }

    /**
     * ��ѯ���ϲ�����ParameterMapҪ���ShoppingCart���ﳵ�����
     * @param parameterMap ������
     * @return ���ϲ�����ParameterMapҪ���ShoppingCart���ﳵ�����
     */
    public List<ShoppingCart> getShoppingCartsByParameterMap(Map parameterMap) {
        if (null == parameterMap || parameterMap.size() < 1) {
            return null;
        }
        List<ShoppingCart> shoppingCartList = shoppingCartDao
            .getShoppingCartsByParameterMap(parameterMap);
        return shoppingCartList;
    }

    /**
     * ��������ShoppingCart��
     * @param shoppingCartList
     */
    public void editShoppingCarts(List<ShoppingCart> shoppingCartList) {
        if (null == shoppingCartList || shoppingCartList.size() < 1) {
            return;
        }
        this.shoppingCartDao.editShoppingCarts(shoppingCartList);
    }

    /**
     * ����ɾ��ShoppingCart��
     * @param shoppingCartList
     */
    public void removeShoppingCarts(List<ShoppingCart> shoppingCartList) {
        if (null == shoppingCartList || shoppingCartList.size() < 1) {
            return;
        }
        this.shoppingCartDao.removeShoppingCarts(shoppingCartList);
    }

    /**
     * ����ɾ��ShoppingCart����ͨ��ShoppingCart��Id����ɾ����
     * @param shoppingCartList
     */
    public void removeShoppingCartsByIdList(List<Long> shoppingCartIdList) {
        if (null == shoppingCartIdList || shoppingCartIdList.size() < 1) {
            return;
        }
        List<ShoppingCart> shoppingCartList = new ArrayList<ShoppingCart>();
        for (Long id : shoppingCartIdList) {
            ShoppingCart tmpCart = new ShoppingCart();
            tmpCart.setId(id);
            shoppingCartList.add(tmpCart);
        }
        removeShoppingCarts(shoppingCartList);
    }

    /**
     * ����ɾ��ShoppingCart����ͨ���û���Id��ɾ����
     * @param shoppingCartList
     */
    public void removeShoppingCartsByUserId(Long userId) {
        Map parameterMap = new HashMap();
        parameterMap.put("userId", userId);
        List<ShoppingCart> shoppingCartList = shoppingCartDao
            .getShoppingCartsByParameterMap(parameterMap);
        shoppingCartDao.removeShoppingCarts(shoppingCartList);
    }

    /* @model: ���һ��ShoppingCart��¼ */
    public void addShoppingCart(ShoppingCart shoppingCartDao) {
//        log.info("ShoppingCartManagerImpl.addShoppingCart method");
        try {
            this.shoppingCartDao.addShoppingCart(shoppingCartDao);
        } catch (Exception e) {
//            log.error(e.getMessage());
        }
    }

    /* @model: ����һ��ShoppingCart��¼ */
    public void editShoppingCart(ShoppingCart shoppingCart) {
//        log.info("ShoppingCartManagerImpl.editShoppingCart method");
        try {
            this.shoppingCartDao.editShoppingCart(shoppingCart);
        } catch (Exception e) {
//            log.error(e.getMessage());
        }
    }

    /* @model: ɾ��һ��ShoppingCart��¼ */
    public void removeShoppingCart(Long shoppingCartId) {
//        log.info("ShoppingCartManagerImpl.removeShoppingCart method");
        try {
            this.shoppingCartDao.removeShoppingCart(shoppingCartId);
        } catch (Exception e) {
//            log.error(e.getMessage());
        }
    }

    /* @model: ��ѯһ��ShoppingCart�����,����ShoppingCart���� */
    public ShoppingCart getShoppingCart(Long shoppingCartId) {
//        log.info("ShoppingCartManagerImpl.getShoppingCart method");
        try {
            return this.shoppingCartDao.getShoppingCart(shoppingCartId);
        } catch (Exception e) {
//            log.error(e.getMessage());
        }
        return null;
    }

    /* @model: ��ѯ����ShoppingCart�����,����ShoppingCart����ļ��� */
    public List<ShoppingCart> getShoppingCarts() {
//        log.info("ShoppingCartManagerImpl.getShoppingCarts method");
        try {
            return this.shoppingCartDao.getShoppingCarts();
        } catch (Exception e) {
//            log.error(e.getMessage());
        }
        return null;
    }

    /**
     * ��ȡĳ���û����ﳵ����Ʒ������
     * @param userId
     * @return
     */
    public Integer getShoppingCartCount(Long userId) {
        Integer num = 0;
        List<ShoppingCart> shoppingCartList = getShoppingCartsByUserId(userId);
        List<ShoppingCart> shoppingCartListNew = new ArrayList<ShoppingCart>();
        if (shoppingCartList != null && shoppingCartList.size() > 0) {
            for (ShoppingCart shopCart : shoppingCartList) {
                if (shoppingCartListNew != null && shoppingCartListNew.size() > 0) {
                    for (ShoppingCart newCart : shoppingCartListNew) {
                        if (shopCart.getPromationId() != null
                            && StringUtil.isNotBlank(shopCart.getTimeTag())
                            && newCart.getPromationId() == shopCart.getPromationId()
                            && newCart.getTimeTag().equals(shopCart.getTimeTag())) {
                            num = num - 1;
                            break;
                        }
                    }
                }
                if (shopCart.getPromationId() == null || shopCart.getPromationId() == 0) {
                    num = num + 1;
                } else {
                    num = num + 1;
                    long promationId = shopCart.getPromationId();
                    String timeTag = shopCart.getTimeTag();
                    long goodsId = shopCart.getGoodsId();
                    for (ShoppingCart sCart : shoppingCartList) {
                        if (sCart.getPromationId() != null && sCart.getPromationId() == promationId
                            && StringUtil.isNotBlank(timeTag)
                            && timeTag.equalsIgnoreCase(sCart.getTimeTag())
                            && goodsId != sCart.getGoodsId()) {
                            shoppingCartListNew.add(sCart);
                        }
                    }

                }
            }
        }

        return num;
    }

    /**
     * ����û����ﳵ�й��ڵ���Ʒ
     * @return
     */
    public void removeOutShoppingCart(Long userId) {
        List<ShoppingCart> shoppingCartList = shoppingCartDao.removeOutShoppingCartselect(userId);
        for (ShoppingCart shoppingCart : shoppingCartList) {
            if (shoppingCart.getPromationId() != null && shoppingCart.getPromationId() != 0
                && StringUtil.isNotBlank(shoppingCart.getTimeTag())) {
                Map<String, Object> pramaselect = new HashMap<String, Object>();
                pramaselect.put("userId", userId);
                pramaselect.put("promationId", shoppingCart.getPromationId());
                pramaselect.put("timeTag", shoppingCart.getTimeTag());
                shoppingCartDao.removeOutShoppingCartPDateselect(pramaselect);
            }
        }

        shoppingCartDao.removeOutShoppingCart(userId);
        Map<String, Object> prama = new HashMap<String, Object>();
        prama.put("userId", userId);
        prama.put("curTime", DateUtil.convertDateToString("yyyy-MM-dd HH", new Date()));
        shoppingCartDao.removeOutShoppingCartPDate(prama);

    }

    public void removeShoppingCartPromation(Long userId, Long promationId, String timeTag) {
        Map<String, Object> pramas = new HashMap<String, Object>();
        pramas.put("promationId", promationId);
        pramas.put("timeTag", timeTag);
        pramas.put("userId", userId);
        this.shoppingCartDao.removeShoppingCartPromation(pramas);
    }

    public List<ShoppingCart> getGoodsShoppingCartByIds(List ids) {
        if (ids == null || ids.size() < 1) {
            return null;
        }
        return this.shoppingCartDao.getGoodsShoppingCartByIds(ids);
    }

    /**
     * �����ܱ��۵���Ӻͺϲ�
     */
    public Result<Object> addPfGoods(Long goodsId, String[] allNums,
			boolean isLoged, Long userId) {
        Result<Object> result = new Result<Object>();

    	List<GoodsInstance> goodsInstanceRealList =  new ArrayList<GoodsInstance>();
    	List<GoodsInstance> goodsInstanceList = goodsInstanceManager.findGoodsInstances(goodsId);
    	if(goodsInstanceList == null){
    		result.setResult(0);
    		return result;
    	}

    	//����Ϊ0�ͿյĲ�Ҫ��ӽ���
    	for(int i=0;i<goodsInstanceList.size();i++){
    		if(!allNums[i].equals("0")){
    			goodsInstanceList.get(i).setWholeNum(Integer.parseInt(allNums[i]));
    			goodsInstanceRealList.add(goodsInstanceList.get(i));
    		}
    	}

    	//���Ҳ��ϲ�
    	for(GoodsInstance tmp : goodsInstanceRealList){
    		Map parMap =new HashMap();
    		parMap.put("userId", userId);
    		parMap.put("goodsId", tmp.getGoodsId());
    		parMap.put("goodsInstanceId", tmp.getId());
    		parMap.put("isWholesale", "w");
    		List<ShoppingCart> shoppingCartList = shoppingCartDao.getShoppingCartsByParameterMap(parMap);
    		//����ϲ�
    		if(shoppingCartList!=null && shoppingCartList.size() > 0){
    			ShoppingCart shoppingCartTmp = shoppingCartList.get(0);
    			shoppingCartTmp.setGoodsNumber(shoppingCartTmp.getGoodsNumber()+ tmp.getWholeNum());
    			try{
    			    shoppingCartDao.editShoppingCart(shoppingCartTmp);
    			}catch(Exception e){
//    				log.error(e.getMessage());
    			}
    		}else{
    			ShoppingCart newShoppingCart = new ShoppingCart();
    			newShoppingCart.setUserId(userId);
    			newShoppingCart.setGoodsId(tmp.getGoodsId());
    			newShoppingCart.setGoodsNumber(tmp.getWholeNum());
    			if(tmp.getAttrs()!=null){
    				StringBuffer attrbuffer = new StringBuffer();
    				String[] tmpString = tmp.getAttrs().split(";");
    				for(String tmpAttr: tmpString){
    					String[] tmpAttrString = tmpAttr.split("=");
    					Map tempMap = new HashMap();
    					tempMap.put("attrCode", tmpAttrString[0]);
    					tempMap.put("attrValue", tmpAttrString[1]);
    					tempMap.put("goodsId", tmp.getGoodsId());
    					GoodsAttr goodsAttr = goodsAttrManager.getGoodsAttrByMap(tempMap);
    					attrbuffer.append(goodsAttr.getId()).append(";");
    				}
    				newShoppingCart.setGoodsAttrIds(attrbuffer.toString());
    			}
    			newShoppingCart.setGoodsInstanceId(tmp.getId());
    			newShoppingCart.setIsWholesale("w");
    			try{
    			 shoppingCartDao.addShoppingCart(newShoppingCart);
    			}catch(Exception e){
//    				log.error(e.getMessage());
    			}
    		}
    	}

    	result.setResult(1);
		return result;
	}

	public int getGoodsUserNum(Long userId) {
		return shoppingCartDao.getGoodsUserNum(userId);
	}

	public List<ShoppingCart> getShoppingCartListByGoodsId(Long goodsId) {
    	return shoppingCartDao.getShoppingCartListByGoodsId(goodsId);
	}

}
