/**
 * @Title: ShoppingRefundDetailService.java
 * @Package com.huaixuan.network.biz.service.stock
 * @Description: TODO
 * @author liuwd weida-liu@foxmail.com
 * @date 2011-3-3 ����05:12:28
 * @version V1.0
 */
package com.huaixuan.network.biz.service.stock;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.stock.ShoppingRefundDetail;

/**
 * @ClassName: ShoppingRefundDetailService
 * @Description: TODO
 * @author liuwd weida-liu@foxmail.com
 * @date 2011-3-3 ����05:12:28
 *
 */
/**
 * ShoppingRefundDetailManager
 * @version 3.2.0
 */
public interface ShoppingRefundDetailService {
    /**
     *
     * @param shoppingRefundDetail
     * @return
     */
    public long addShoppingRefundDetail(ShoppingRefundDetail shoppingRefundDetail);

    /**
     *
     * @param shoppingRefundDetail
     */
    public void editShoppingRefundDetail(ShoppingRefundDetail shoppingRefundDetail);

    /**
     *
     * @param shoppingRefundDetailId
     */
    public void removeShoppingRefundDetail(Long shoppingRefundDetailId);

    /**
     *
     * @param shoppingRefundDetailId
     * @return
     */
    public ShoppingRefundDetail getShoppingRefundDetail(Long shoppingRefundDetailId);

    /**
     *
     * @return
     */
    public List<ShoppingRefundDetail> getShoppingRefundDetails();

    /**
     *
     * @param shoppingRefundDetailId
     * @return
     */
    public List<ShoppingRefundDetail> getShoppingRefundDetailsId(Long shoppingRefundDetailId);

    /**
     * �����˻����ͺͲɹ�����ID��ѯ��¼����
     * @param parMap
     * @return
     */
    public int getCountRefundByShoppingIdAndStatus(Map<String, String> parMap);

    /**
     * �ɹ��˻�����ϸ
     * @return
     */
    public List<ShoppingRefundDetail> getRefundDetail(Map<String, Object> parMap);

    /**
     * �ɹ��˻�����ϸ
     * @return
     */
    public List<ShoppingRefundDetail> getStorageRefundDetails(Map<String, Object> parMap);

    /**
     * ��������ѯ�˻���ϸ���ͳ��
     * @param paramMap
     * @return
     */
    public int getCountRefundDetail(Map<String, Object> paramMap);

    /**
     * ���ݲɹ���id,��Ʒidͳ���˻�������
     * @param param
     * @return
     */
    public ShoppingRefundDetail sumRefundDetailByShoppingId(Map<String, Object> param);

    /**
     * ��������id����ϸ���¼
     * @param shoppingRefundId
     * @return
     */
    public List<ShoppingRefundDetail> getShoppingRefundDetailByShoppingRefundId(
                                                                                Long shoppingRefundId);
}