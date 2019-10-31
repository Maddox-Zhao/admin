/**
 * @Title: ShoppingCartService.java
 * @Package com.huaixuan.network.biz.service
 * @Description: TODO
 * @author liuwd weida-liu@foxmail.com
 * @date 2011-3-4 下午07:38:24
 * @version V1.0
 */
package com.huaixuan.network.biz.service.trade;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.userdetails.User;

import com.huaixuan.network.biz.domain.ShoppingCart;
import com.huaixuan.network.biz.domain.goods.AvailableStock;
import com.huaixuan.network.common.util.Result;


/**
 * @ClassName: ShoppingCartService
 * @Description: TODO
 * @author liuwd weida-liu@foxmail.com
 * @date 2011-3-4 下午07:38:24
 *
 */
public interface ShoppingCartService {
    /* @interface model: 添加一条ShoppingCart记录 */
    public void addShoppingCart(ShoppingCart shoppingCart);

    /* @interface model: 更新一条ShoppingCart记录 */
    public void editShoppingCart(ShoppingCart shoppingCart);

    /* @interface model: 删除一条ShoppingCart记录 */
    public void removeShoppingCart(Long shoppingCartId);

    /* @interface model: 查询一个ShoppingCart结果集,返回ShoppingCart对象 */
    public ShoppingCart getShoppingCart(Long shoppingCartId);

    /* @interface model: 查询所有ShoppingCart结果集,返回ShoppingCart对象的集合 */
    public List<ShoppingCart> getShoppingCarts();

    /**
     * 获取某个用户购物车中商品的数量
     * @param userId
     * @return
     */
    public Integer getShoppingCartCount(Long userId);

    /**
     * 合并购物车，主要用于登录时，将Cookie中的购物车与用户数据库中的购物车合并，将合并后的购物车保存到数据库，并返回最新的购物车。
     * @return
     */
    public List<ShoppingCart> mergerShoppingCart(List<ShoppingCart> cookieShoppingCartList,
                                                 Long userId);

    /**
     * 将shoppingCartList列表的购物车批量添加到数据库中。
     * @param shoppingCartList
     */
    public void addShoppingCarts(List<ShoppingCart> shoppingCartList);

    /**
     * 查询某用户的所有ShoppingCart购物车结果集
     * @param userId 用户ID
     * @return 返回ShoppingCart对象的集合
     */
    public List<ShoppingCart> getShoppingCartsByUserId(Long userId);

    /**
     * 查询某用户的某个商品的购物车信息
     * @param userId 用户ID
     * @param goodId 商品ID
     * @return 返回ShoppingCart对象
     */
    public ShoppingCart getShoppingCartByUserIdAndGoodId(Long userId, Long goodId);

    public List<ShoppingCart> getGoodsShoppingCartByIds(List ids);

    /**
     * 查询某用户的某个套餐商品的购物车列表信息
     * @param userId 用户ID
     * @param goodId 商品ID
     * @param promationId 套餐ID
     * @return 返回ShoppingCartList对象
     */
    public List<ShoppingCart> getShoppingCartListByUserIdAndGoodId(Long userId, Long goodsId,
                                                                   Long promationId, String timeTag);

    public List<ShoppingCart> getShoppingCartListByGoodIdAndTimeTag(ShoppingCart shoppingCart);

    /**
     * 查询符合参数集ParameterMap要求的ShoppingCart购物车结果集
     * @param parameterMap 参数集
     * @return 符合参数集ParameterMap要求的ShoppingCart购物车结果集
     */
    public List<ShoppingCart> getShoppingCartsByParameterMap(Map parameterMap);

    /**
     * 批量更新ShoppingCart集
     * @param shoppingCartList
     */
    public void editShoppingCarts(List<ShoppingCart> shoppingCartList);

    /**
     * 批量删除ShoppingCart集
     * @param shoppingCartList
     */
    public void removeShoppingCarts(List<ShoppingCart> shoppingCartList);

    /**
     * 批量删除ShoppingCart集，通过ShoppingCart的Id集来删除。
     * @param shoppingCartList
     */
    public void removeShoppingCartsByIdList(List<Long> shoppingCartIdList);

    /**
     * 批量删除ShoppingCart集，通过用户的Id来删除。
     * @param shoppingCartList
     */
    public void removeShoppingCartsByUserId(Long userId);

    public void removeShoppingCartPromation(Long userId, Long promationId, String timeTag);

    /**
     * 清空用户购物车中过期的商品
     * @return
     */
    public void removeOutShoppingCart(Long userId);

    /**
     * 登陆后，商品详情页面，添加商品到购物车,如果存在选购属性相同的同一个商品，修改该商品的购买数量
     * @param shoppingCart
     * @param userId
     * @throws Exception
     */
    public void addGoodsShoppingCart(ShoppingCart shoppingCart, Long userId) throws Exception;

    /**
     * 用户的非套餐、对应于某个商品、某个选购属性的购物车
     * @param userId
     * @param goodsId
     * @param attrIds
     * @return
     */
    public ShoppingCart getShoppingCartOfCommonGoods(Long userId, Long goodsId, String attrIds);

    /**
     * 登录后，添加套餐中商品到购物车
     * @param shoppingCart
     */
    public void addPromationToShoppingCart(ShoppingCart shoppingCart) throws Exception;

    /**新增购物记录 （业务操作都放manager）
     * @param goodsId商品ID
     * @param goodsInstaceId产品ID
     * @param goodsNum商品数量
     * @param goodsAttrIds商品属性
     * @param isAdd是否新增
     * @param request
     * @param response
     * @param isLoged是否登陆
     * @param userId用户ID
     * @return
     */
    public Result<Object> addGoodsToShoppingCarts(Long goodsId, Integer goodsNum,
                                                  String goodsAttrIds, boolean isAdd,
                                                  HttpServletRequest request,
                                                  HttpServletResponse response, boolean isLoged,
                                                  Long userId);

    /**套餐详情页面，添加套餐到购物车
     * @param goodsIds商品ID
     * @param goodsNum
     * @param goodsAttrIds
     * @param isAdd
     * @param request
     * @param response
     * @param isLoged
     * @param userId
     * @param promationId
     * @return
     */
    public Result<Object> addPromationToShoppingCart(String goodsIds, Integer goodsNum,
                                                     String goodsAttrIds,
                                                     HttpServletRequest request,
                                                     HttpServletResponse response, boolean isLoged,
                                                     Long userId, String promationId);

    /**修改购物车商品的数量(操作单个商品)
     * @param shoppingCartId
     * @param goodsInstanceId
     * @param goodsNumber
     * @param isLoged
     * @param userId
     * @param request
     * @param response
     * @param isPromation
     * @return
     */
    Result<Object> editShoppingCartNum(Long shoppingCartId, Long goodsInstanceId,
                                       Integer goodsNumber, boolean isLoged, Long userId,
                                       HttpServletRequest request, HttpServletResponse response, boolean isPromation);

    /**缺货登记
     * @return
     */
    public Result<Object> createStockOutGoods(User user,Long goodsId,String goodsAttrIds);

    /**通过产品ID得到不同仓库的库存
     * @param goodsId 商品ID
     * @param goodsAttrIds 商品属性
     * 商品ID 和 商品属性确定 产品ID
     * @return
     */
    List<AvailableStock> getAvailableStockList(Long goodsId,String goodsAttrIds);

    /**
     * 根据商品id获取批发总报价单数据
     * @param goodsId
     * @return
     */
    List<ShoppingCart> getShoppingCartListByGoodsId(Long goodsId);

    /**
     * 增加到批发总报价单
     * @param goodsId
     * @param allNums
     * @param isLoged
     * @param userId
     * @return
     * @author zhangwy
     */
    Result<Object> addPfGoods(Long goodsId, String[] allNums, boolean isLoged, Long userId);

    /**
     * 根据用户Id获取已有的批发商品数量
     * @param userId
     * @return
     */
    public int getGoodsUserNum(Long userId);
}

