/**
 * @Title: ShoppingCartService.java
 * @Package com.huaixuan.network.biz.service
 * @Description: TODO
 * @author liuwd weida-liu@foxmail.com
 * @date 2011-3-4 ����07:38:24
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
 * @date 2011-3-4 ����07:38:24
 *
 */
public interface ShoppingCartService {
    /* @interface model: ���һ��ShoppingCart��¼ */
    public void addShoppingCart(ShoppingCart shoppingCart);

    /* @interface model: ����һ��ShoppingCart��¼ */
    public void editShoppingCart(ShoppingCart shoppingCart);

    /* @interface model: ɾ��һ��ShoppingCart��¼ */
    public void removeShoppingCart(Long shoppingCartId);

    /* @interface model: ��ѯһ��ShoppingCart�����,����ShoppingCart���� */
    public ShoppingCart getShoppingCart(Long shoppingCartId);

    /* @interface model: ��ѯ����ShoppingCart�����,����ShoppingCart����ļ��� */
    public List<ShoppingCart> getShoppingCarts();

    /**
     * ��ȡĳ���û����ﳵ����Ʒ������
     * @param userId
     * @return
     */
    public Integer getShoppingCartCount(Long userId);

    /**
     * �ϲ����ﳵ����Ҫ���ڵ�¼ʱ����Cookie�еĹ��ﳵ���û����ݿ��еĹ��ﳵ�ϲ������ϲ���Ĺ��ﳵ���浽���ݿ⣬���������µĹ��ﳵ��
     * @return
     */
    public List<ShoppingCart> mergerShoppingCart(List<ShoppingCart> cookieShoppingCartList,
                                                 Long userId);

    /**
     * ��shoppingCartList�б�Ĺ��ﳵ������ӵ����ݿ��С�
     * @param shoppingCartList
     */
    public void addShoppingCarts(List<ShoppingCart> shoppingCartList);

    /**
     * ��ѯĳ�û�������ShoppingCart���ﳵ�����
     * @param userId �û�ID
     * @return ����ShoppingCart����ļ���
     */
    public List<ShoppingCart> getShoppingCartsByUserId(Long userId);

    /**
     * ��ѯĳ�û���ĳ����Ʒ�Ĺ��ﳵ��Ϣ
     * @param userId �û�ID
     * @param goodId ��ƷID
     * @return ����ShoppingCart����
     */
    public ShoppingCart getShoppingCartByUserIdAndGoodId(Long userId, Long goodId);

    public List<ShoppingCart> getGoodsShoppingCartByIds(List ids);

    /**
     * ��ѯĳ�û���ĳ���ײ���Ʒ�Ĺ��ﳵ�б���Ϣ
     * @param userId �û�ID
     * @param goodId ��ƷID
     * @param promationId �ײ�ID
     * @return ����ShoppingCartList����
     */
    public List<ShoppingCart> getShoppingCartListByUserIdAndGoodId(Long userId, Long goodsId,
                                                                   Long promationId, String timeTag);

    public List<ShoppingCart> getShoppingCartListByGoodIdAndTimeTag(ShoppingCart shoppingCart);

    /**
     * ��ѯ���ϲ�����ParameterMapҪ���ShoppingCart���ﳵ�����
     * @param parameterMap ������
     * @return ���ϲ�����ParameterMapҪ���ShoppingCart���ﳵ�����
     */
    public List<ShoppingCart> getShoppingCartsByParameterMap(Map parameterMap);

    /**
     * ��������ShoppingCart��
     * @param shoppingCartList
     */
    public void editShoppingCarts(List<ShoppingCart> shoppingCartList);

    /**
     * ����ɾ��ShoppingCart��
     * @param shoppingCartList
     */
    public void removeShoppingCarts(List<ShoppingCart> shoppingCartList);

    /**
     * ����ɾ��ShoppingCart����ͨ��ShoppingCart��Id����ɾ����
     * @param shoppingCartList
     */
    public void removeShoppingCartsByIdList(List<Long> shoppingCartIdList);

    /**
     * ����ɾ��ShoppingCart����ͨ���û���Id��ɾ����
     * @param shoppingCartList
     */
    public void removeShoppingCartsByUserId(Long userId);

    public void removeShoppingCartPromation(Long userId, Long promationId, String timeTag);

    /**
     * ����û����ﳵ�й��ڵ���Ʒ
     * @return
     */
    public void removeOutShoppingCart(Long userId);

    /**
     * ��½����Ʒ����ҳ�棬�����Ʒ�����ﳵ,�������ѡ��������ͬ��ͬһ����Ʒ���޸ĸ���Ʒ�Ĺ�������
     * @param shoppingCart
     * @param userId
     * @throws Exception
     */
    public void addGoodsShoppingCart(ShoppingCart shoppingCart, Long userId) throws Exception;

    /**
     * �û��ķ��ײ͡���Ӧ��ĳ����Ʒ��ĳ��ѡ�����ԵĹ��ﳵ
     * @param userId
     * @param goodsId
     * @param attrIds
     * @return
     */
    public ShoppingCart getShoppingCartOfCommonGoods(Long userId, Long goodsId, String attrIds);

    /**
     * ��¼������ײ�����Ʒ�����ﳵ
     * @param shoppingCart
     */
    public void addPromationToShoppingCart(ShoppingCart shoppingCart) throws Exception;

    /**���������¼ ��ҵ���������manager��
     * @param goodsId��ƷID
     * @param goodsInstaceId��ƷID
     * @param goodsNum��Ʒ����
     * @param goodsAttrIds��Ʒ����
     * @param isAdd�Ƿ�����
     * @param request
     * @param response
     * @param isLoged�Ƿ��½
     * @param userId�û�ID
     * @return
     */
    public Result<Object> addGoodsToShoppingCarts(Long goodsId, Integer goodsNum,
                                                  String goodsAttrIds, boolean isAdd,
                                                  HttpServletRequest request,
                                                  HttpServletResponse response, boolean isLoged,
                                                  Long userId);

    /**�ײ�����ҳ�棬����ײ͵����ﳵ
     * @param goodsIds��ƷID
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

    /**�޸Ĺ��ﳵ��Ʒ������(����������Ʒ)
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

    /**ȱ���Ǽ�
     * @return
     */
    public Result<Object> createStockOutGoods(User user,Long goodsId,String goodsAttrIds);

    /**ͨ����ƷID�õ���ͬ�ֿ�Ŀ��
     * @param goodsId ��ƷID
     * @param goodsAttrIds ��Ʒ����
     * ��ƷID �� ��Ʒ����ȷ�� ��ƷID
     * @return
     */
    List<AvailableStock> getAvailableStockList(Long goodsId,String goodsAttrIds);

    /**
     * ������Ʒid��ȡ�����ܱ��۵�����
     * @param goodsId
     * @return
     */
    List<ShoppingCart> getShoppingCartListByGoodsId(Long goodsId);

    /**
     * ���ӵ������ܱ��۵�
     * @param goodsId
     * @param allNums
     * @param isLoged
     * @param userId
     * @return
     * @author zhangwy
     */
    Result<Object> addPfGoods(Long goodsId, String[] allNums, boolean isLoged, Long userId);

    /**
     * �����û�Id��ȡ���е�������Ʒ����
     * @param userId
     * @return
     */
    public int getGoodsUserNum(Long userId);
}

