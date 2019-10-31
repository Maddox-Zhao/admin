/**
 * @Title: ShoppingCartDao.java
 * @Package com.huaixuan.network.biz.dao
 * @Description: TODO
 * @author liuwd weida-liu@foxmail.com
 * @date 2011-3-4 ����05:13:14
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
 * @date 2011-3-4 ����05:13:14
 *
 */
public interface ShoppingCartDao  {
    /* @interface model: ���һ��ShoppingCart��¼ */
    void addShoppingCart(ShoppingCart shoppingCart) throws Exception;

    /* @interface model: ����һ��ShopInfo��¼ */
    void editShoppingCart(ShoppingCart shoppingCart) throws Exception;

    /* @interface model: ɾ��һ��ShopInfo��¼ */
    void removeShoppingCart(Long shoppingCartId) throws Exception;

    /* @interface model: ��ѯһ��ShopInfo�����,����ShopInfo���� */
    ShoppingCart getShoppingCart(Long shoppingCartId) throws Exception;

    /* @interface model: ��ѯ����ShopInfo�����,����ShopInfo����ļ��� */
    List<ShoppingCart> getShoppingCarts() throws Exception;

    List<ShoppingCart> getGoodsShoppingCartByIds(List ids);

    /**
     * ����ɾ��ShoppingCart����ͨ��ShoppingCart����ɾ����
     * @param shoppingCartList
     */
    void removeShoppingCarts(List<ShoppingCart> shoppingCartList);

    void removeOutShoppingCartPDate(Map<String, Object> prama);

    /**
     * ��������ShoppingCart��
     * @param shoppingCartList
     */
    void editShoppingCarts(List<ShoppingCart> shoppingCartList);

    /**
     * ��ѯ���ϲ�����ParameterMapҪ���ShoppingCart���ﳵ�����
     * @param parameterMap ������
     * @return ���ϲ�����ParameterMapҪ���ShoppingCart���ﳵ�����
     */
    List<ShoppingCart> getShoppingCartsByParameterMap(Map parameterMap);

    /**
     * ��ѯ���ϲ�����ParameterMapҪ���ShoppingCart���ﳵ�����
     * @param parameterMap ������
     * @return ���ϲ�����ParameterMapҪ���ShoppingCart���ﳵ�����
     */
    List<ShoppingCart> getShoppingCartListByUserIdAndGoodId(Map parameterMap);

    List<ShoppingCart> getShoppingCartListByGoodIdAndTimeTag(Map parameterMap);

    /**
     * ��shoppingCartList�б�Ĺ��ﳵ������ӵ����ݿ��С�
     * @param shoppingCartList
     */
    void addShoppingCarts(List<ShoppingCart> shoppingCartList);

    /**
     * ��shoppingCartList�б�Ĺ��ﳵ������ӵ����ݿ��С�
     * @param shoppingCartList
     */
    void removeShoppingCartPromation(Map<String, Object> pramas);

    /**
     * ����Ѿ����ڵĹ��ﳵ
     * @param userId
     */
    void removeOutShoppingCart(Long userId);

    public List<ShoppingCart> removeOutShoppingCartselect(Long userId);

    public void removeOutShoppingCartPDateselect(Map<String, Object> pramaselect);

    /**
     * �û��ķ��ײ͡���Ӧ��ĳ����Ʒ��ĳ��ѡ�����ԵĹ��ﳵ
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