/**
 * @Title: ShoppingCartServiceImpl.java
 * @Package com.huaixuan.network.biz.service.impl
 * @Description: TODO
 * @author liuwd weida-liu@foxmail.com
 * @date 2011-3-4 下午07:38:39
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
 * @date 2011-3-4 下午07:38:39
 *
 */
public class ShoppingCartServiceImpl implements ShoppingCartService {
    /* @model: 注入ShoppingCartDao */

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

    /**根据产品的属性确定产品id
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

    /**缺货登记
     * @return
     */
    public Result<Object> createStockOutGoods(User user, Long goodsId, String goodsAttrIds) {
        Result<Object> result = new Result<Object>();
//        Stockout stockeout = new Stockout();
//        if (null != user) {//用户没有登陆则用户名，邮件默认为空
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
     * 比较所选择的属性是否相同
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
     * 根据商品选购属性id list得到商品选购属性id string,以“*”分隔
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
     * 套餐详情页面，添加套餐到购物车
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
//            //cookie中购物车的个数
//            int count = ShoppingCartCookieUtil.getShoppingCartCount(request);
//            //限制购物车的个数不能超过最大值
//            if (count >= Constants.SHOPPINGCART_MAXNUM) {
//                result.setMessage(getText("errors.count.shoppingCart_maxnum"));
//                result.setResult(1);
//                return result;
//
//            }
//            //套餐中商品id
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
//                //商品库存数量是否足够，商品是否销售中
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
//                    //用户是否登录
//                    //登录用户，保存套餐商品信息到db
//                    if (isLoged) {
//                        //数据库中购物车的个数
//                        int dbcount = getShoppingCartCount(userId);
//                        //限制数据库中购物车的个数不能超过最大值
//                        if (dbcount >= Constants.SHOPPINGCART_MAXNUM) {
//                            result.setMessage(getText("errors.count.shoppingCart_maxnum"));
//                            result.setResult(1);
//                            return result;
//
//                        }
//                        //将当前需要添加到购物车的商品信息组成一个购物车
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
//                    //未登录的游客
//                    if (!isLoged) {
//                        //添加商品信息到cookie
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
     * 修改购物车商品的数量(操作单个商品)
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
            return result;// 没有传入购物车ID时返回
        }
        if (isLoged) {//登陆用户
            if (goodsNumber == null || shoppingCartId == null) {
                result.setResult(1);
                result.setMessage("cartId is null or goodsNumber is null");
                return result;// 没有传入购物车ID时返回
            }
            ShoppingCart shoppingCart = getShoppingCart(shoppingCartId);
            if (shoppingCart == null || !userId.equals(shoppingCart.getUserId())) {
                result.setResult(1);
                result.setMessage("the shoppingCart is not exist or it is not yours");
                return result;// 传入购物车ID对应的购物车不存在或者不属于这个用户时返回
            }
            if (goodsNumber == null || goodsNumber < 1) {
                goodsNumber = 1;
            }

            //若有套餐，则加入套餐 zhangwy
            if(isPromation){
            	shoppingCart.setPromationId(new Long(1)); //1标识有套餐
            }
            shoppingCart.setGmtModify(new Date());
            shoppingCart.setGoodsNumber(goodsNumber);
            editShoppingCart(shoppingCart);
        } else {
            if (goodsNumber == null || goodsNumber < 1) {
                goodsNumber = Integer.parseInt("1");
            }
            //修改客户端的Cookies
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
     * @param isAdd 是否新增
     * @param request
     * @param response
     * @param isLoged是否登陆
     * @param userId用户id
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
//            //商品库存数量是否足够，商品是否销售中
//            if (goodsTmpInstance.getExistNum() < goodsNum
//                || !StringUtil.equalsIgnoreCase(EnumGoodsStatus.ON_SALE.getKey(), good
//                    .getGoodsStatus())) {
//                result.setResult(2);
//                result.setMessage(getText("nopopedom.goods.addGoodToShoppingCart.wrong"));
//                //            result.setMessage("NoGoodsNumber");
//                return result;
//            }
//            //输入的购买数量不合法
//            if (goodsNum <= 0) {
//                result.setResult(1);
//                result.setMessage(getText("nopopedom.goods.addGoodToShoppingCart.goodsNum.wrong"));
//                return result;
//            }
//            //cookie中购物车的个数
//            int count = ShoppingCartCookieUtil.getShoppingCartCount(request);
//            //限制购物车的个数不能超过最大值
//            if (count >= Constants.SHOPPINGCART_MAXNUM) {
//                result.setResult(1);
//                result.setMessage(getText("errors.count.shoppingCart_maxnum"));
//                return result;
//            }
//            String s = this.generateAttrIds(goodsAttrIds);
//
//            //判断该商品是否有套餐和获取套餐信息 zhangwy
//            boolean isPromation = false;
//            List<PromationGive> newPromationGiveList = promationManager.getNewPromationGiveList(goodsId);
//            if(newPromationGiveList!=null && newPromationGiveList.size()>0){
//            	isPromation = true;
//            }
//
//            //用户是否登录
//            //登录用户，保存商品信息到db
//            if (isLoged) {
//                //通过产品ID和当前登陆用户判断是当前记录是否存在购物车表中
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
//                //数据库中购物车的个数
//                int dbcount = shoppingCartDao.getPtGoodsUserNum(userId);
//                //限制数据库中购物车的个数不能超过最大值
//                if (dbcount >= Constants.SHOPPINGCART_MAXNUM) {
//                    result.setResult(1);
//                    result.setMessage(getText("errors.count.shoppingCart_maxnum"));
//                    return result;
//                }
//                //将当前需要添加到购物车的商品信息组成一个购物车
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
//                	spTmp.setPromationId(new Long(1)); //有套餐默认为1
//                }
//                if (isAdd) {
//                    addGoodsShoppingCart(spTmp, userId);
//                }
//            }
//            //未登录的游客
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
//                //添加商品信息到cookie
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

    /**通过产品ID得到不同仓库的库存
     * @param goodsId 商品ID
     * @param goodsAttrIds 商品属性
     * 商品ID 和 商品属性确定 产品ID
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
     * 合并购物车，主要用于登录时，将Cookie中的购物车与用户数据库中的购物车合并，将合并后的购物车保存到数据库，清除cookie中购物车，并返回最新的购物车。
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
                        //合并数量 zhangwy
                        long goodsNumber = cookieShoppingCart.getGoodsNumber()
                                           + dbShoppingCart.getGoodsNumber();
                        GoodsInstance goodsInstance = goodsInstanceManager
                            .getInstance(cookieShoppingCart.getGoodsInstanceId());
                        //如果大于商品可用库存,就把可用库存全部赋给购物车
                        if ((cookieShoppingCart.getIsWholesale().equals("n"))&& (goodsNumber > goodsInstance.getExistNum())) {
                            goodsNumber = goodsInstance.getExistNum();
                        }
                        shoppingCartTmp.setGoodsNumber(Integer.parseInt(goodsNumber + ""));
                        shoppingCartTmp.setGmtModify(cookieShoppingCart.getGmtModify());
                        //将已经合并的移出原队列。
                        cookieShoppingCartList.remove(cookieShoppingCart);
                        break;
                    }
                }
                newShoppingCartList.add(shoppingCartTmp);
            }
            //将剩下的cookieShoppingCartList全部直接添加到新的列表中。
            for (ShoppingCart cookieShoppingCart : cookieShoppingCartList) {
                newShoppingCartList.add(cookieShoppingCart);
            }
        }
        //将新的购物车保存到数据库。
        //先清空数据库中的购物车
        removeShoppingCarts(dbShoppingCartList);
        //保存新的购物车
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
     * 登陆后，商品详情页面，添加商品到购物车,如果存在选购属性相同的同一个商品，修改该商品的购买数量
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
     * 用户的非套餐、对应于某个商品、某个选购属性的购物车
     * @param userId
     * @param goodsId
     * @param attrIds
     * @return
     */
    public ShoppingCart getShoppingCartOfCommonGoods(Long userId, Long goodsId, String attrIds) {
        return shoppingCartDao.getShoppingCartOfCommonGoods(userId, goodsId, attrIds);
    }

    /**
     * 登录后，添加套餐中商品到购物车
     * @param shoppingCart
     * @throws Exception
     * @see com.hundsun.bible.facade.trade.ShoppingCartManager#addPromationToShoppingCart(com.hundsun.bible.domain.model.ShoppingCart)
     */
    public void addPromationToShoppingCart(ShoppingCart shoppingCart) throws Exception {
        shoppingCartDao.addShoppingCart(shoppingCart);
    }

    /**
     * 查询某用户的所有ShoppingCart购物车结果集
     * @param userId 用户ID
     * @return 返回ShoppingCart对象的集合
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
     * 查询某用户的某个商品的购物车信息
     * @param userId 用户ID
     * @param goodId 商品ID
     * @return 返回ShoppingCart对象
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
     * 查询符合参数集ParameterMap要求的ShoppingCart购物车结果集
     * @param parameterMap 参数集
     * @return 符合参数集ParameterMap要求的ShoppingCart购物车结果集
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
     * 批量更新ShoppingCart集
     * @param shoppingCartList
     */
    public void editShoppingCarts(List<ShoppingCart> shoppingCartList) {
        if (null == shoppingCartList || shoppingCartList.size() < 1) {
            return;
        }
        this.shoppingCartDao.editShoppingCarts(shoppingCartList);
    }

    /**
     * 批量删除ShoppingCart集
     * @param shoppingCartList
     */
    public void removeShoppingCarts(List<ShoppingCart> shoppingCartList) {
        if (null == shoppingCartList || shoppingCartList.size() < 1) {
            return;
        }
        this.shoppingCartDao.removeShoppingCarts(shoppingCartList);
    }

    /**
     * 批量删除ShoppingCart集，通过ShoppingCart的Id集来删除。
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
     * 批量删除ShoppingCart集，通过用户的Id来删除。
     * @param shoppingCartList
     */
    public void removeShoppingCartsByUserId(Long userId) {
        Map parameterMap = new HashMap();
        parameterMap.put("userId", userId);
        List<ShoppingCart> shoppingCartList = shoppingCartDao
            .getShoppingCartsByParameterMap(parameterMap);
        shoppingCartDao.removeShoppingCarts(shoppingCartList);
    }

    /* @model: 添加一条ShoppingCart记录 */
    public void addShoppingCart(ShoppingCart shoppingCartDao) {
//        log.info("ShoppingCartManagerImpl.addShoppingCart method");
        try {
            this.shoppingCartDao.addShoppingCart(shoppingCartDao);
        } catch (Exception e) {
//            log.error(e.getMessage());
        }
    }

    /* @model: 更新一条ShoppingCart记录 */
    public void editShoppingCart(ShoppingCart shoppingCart) {
//        log.info("ShoppingCartManagerImpl.editShoppingCart method");
        try {
            this.shoppingCartDao.editShoppingCart(shoppingCart);
        } catch (Exception e) {
//            log.error(e.getMessage());
        }
    }

    /* @model: 删除一条ShoppingCart记录 */
    public void removeShoppingCart(Long shoppingCartId) {
//        log.info("ShoppingCartManagerImpl.removeShoppingCart method");
        try {
            this.shoppingCartDao.removeShoppingCart(shoppingCartId);
        } catch (Exception e) {
//            log.error(e.getMessage());
        }
    }

    /* @model: 查询一个ShoppingCart结果集,返回ShoppingCart对象 */
    public ShoppingCart getShoppingCart(Long shoppingCartId) {
//        log.info("ShoppingCartManagerImpl.getShoppingCart method");
        try {
            return this.shoppingCartDao.getShoppingCart(shoppingCartId);
        } catch (Exception e) {
//            log.error(e.getMessage());
        }
        return null;
    }

    /* @model: 查询所有ShoppingCart结果集,返回ShoppingCart对象的集合 */
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
     * 获取某个用户购物车中商品的数量
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
     * 清空用户购物车中过期的商品
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
     * 批发总报价单添加和合并
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

    	//数量为0和空的不要添加进来
    	for(int i=0;i<goodsInstanceList.size();i++){
    		if(!allNums[i].equals("0")){
    			goodsInstanceList.get(i).setWholeNum(Integer.parseInt(allNums[i]));
    			goodsInstanceRealList.add(goodsInstanceList.get(i));
    		}
    	}

    	//查找并合并
    	for(GoodsInstance tmp : goodsInstanceRealList){
    		Map parMap =new HashMap();
    		parMap.put("userId", userId);
    		parMap.put("goodsId", tmp.getGoodsId());
    		parMap.put("goodsInstanceId", tmp.getId());
    		parMap.put("isWholesale", "w");
    		List<ShoppingCart> shoppingCartList = shoppingCartDao.getShoppingCartsByParameterMap(parMap);
    		//有则合并
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
