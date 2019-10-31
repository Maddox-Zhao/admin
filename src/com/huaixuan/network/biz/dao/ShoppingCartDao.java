/**
 * @Title: ShoppingCartDao.java
 * @Package com.huaixuan.network.biz.dao
 * @Description: TODO
 * @author liuwd weida-liu@foxmail.com
 * @date 2011-3-4 下午05:13:14
 * @version V1.0
 */
package com.huaixuan.network.biz.dao;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.ShoppingCart;

/**
 * @ClassName: ShoppingCartDao
 * @Description: TODO
 * @author liuwd weida-liu@foxmail.com
 * @date 2011-3-4 下午05:13:14
 *
 */
public interface ShoppingCartDao  {
    /* @interface model: 添加一条ShoppingCart记录 */
    void addShoppingCart(ShoppingCart shoppingCart) throws Exception;

    /* @interface model: 更新一条ShopInfo记录 */
    void editShoppingCart(ShoppingCart shoppingCart) throws Exception;

    /* @interface model: 删除一条ShopInfo记录 */
    void removeShoppingCart(Long shoppingCartId) throws Exception;

    /* @interface model: 查询一个ShopInfo结果集,返回ShopInfo对象 */
    ShoppingCart getShoppingCart(Long shoppingCartId) throws Exception;

    /* @interface model: 查询所有ShopInfo结果集,返回ShopInfo对象的集合 */
    List<ShoppingCart> getShoppingCarts() throws Exception;

    List<ShoppingCart> getGoodsShoppingCartByIds(List ids);

    /**
     * 批量删除ShoppingCart集，通过ShoppingCart集来删除。
     * @param shoppingCartList
     */
    void removeShoppingCarts(List<ShoppingCart> shoppingCartList);

    void removeOutShoppingCartPDate(Map<String, Object> prama);

    /**
     * 批量更新ShoppingCart集
     * @param shoppingCartList
     */
    void editShoppingCarts(List<ShoppingCart> shoppingCartList);

    /**
     * 查询符合参数集ParameterMap要求的ShoppingCart购物车结果集
     * @param parameterMap 参数集
     * @return 符合参数集ParameterMap要求的ShoppingCart购物车结果集
     */
    List<ShoppingCart> getShoppingCartsByParameterMap(Map parameterMap);

    /**
     * 查询符合参数集ParameterMap要求的ShoppingCart购物车结果集
     * @param parameterMap 参数集
     * @return 符合参数集ParameterMap要求的ShoppingCart购物车结果集
     */
    List<ShoppingCart> getShoppingCartListByUserIdAndGoodId(Map parameterMap);

    List<ShoppingCart> getShoppingCartListByGoodIdAndTimeTag(Map parameterMap);

    /**
     * 将shoppingCartList列表的购物车批量添加到数据库中。
     * @param shoppingCartList
     */
    void addShoppingCarts(List<ShoppingCart> shoppingCartList);

    /**
     * 将shoppingCartList列表的购物车批量添加到数据库中。
     * @param shoppingCartList
     */
    void removeShoppingCartPromation(Map<String, Object> pramas);

    /**
     * 清空已经过期的购物车
     * @param userId
     */
    void removeOutShoppingCart(Long userId);

    public List<ShoppingCart> removeOutShoppingCartselect(Long userId);

    public void removeOutShoppingCartPDateselect(Map<String, Object> pramaselect);

    /**
     * 用户的非套餐、对应于某个商品、某个选购属性的购物车
     * @param userId
     * @param goodsId
     * @param attrIds
     * @return
     */
    ShoppingCart getShoppingCartOfCommonGoods(Long userId,Long goodsId,String attrIds);

    List<ShoppingCart>  getShoppingCartListByGoodsId(Long goodsId);

    int getGoodsUserNum(Long userId);

    int getPtGoodsUserNum(Long userId);
}