/**
 * @Title: ShoppingRefundDetailService.java
 * @Package com.huaixuan.network.biz.service.stock
 * @Description: TODO
 * @author liuwd weida-liu@foxmail.com
 * @date 2011-3-3 下午05:12:28
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
 * @date 2011-3-3 下午05:12:28
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
     * 根据退货类型和采购订单ID查询记录个数
     * @param parMap
     * @return
     */
    public int getCountRefundByShoppingIdAndStatus(Map<String, String> parMap);

    /**
     * 采购退货单明细
     * @return
     */
    public List<ShoppingRefundDetail> getRefundDetail(Map<String, Object> parMap);

    /**
     * 采购退货单明细
     * @return
     */
    public List<ShoppingRefundDetail> getStorageRefundDetails(Map<String, Object> parMap);

    /**
     * 按条件查询退货明细表的统计
     * @param paramMap
     * @return
     */
    public int getCountRefundDetail(Map<String, Object> paramMap);

    /**
     * 根据采购单id,产品id统计退货的数量
     * @param param
     * @return
     */
    public ShoppingRefundDetail sumRefundDetailByShoppingId(Map<String, Object> param);

    /**
     * 根据主表id找明细表记录
     * @param shoppingRefundId
     * @return
     */
    public List<ShoppingRefundDetail> getShoppingRefundDetailByShoppingRefundId(
                                                                                Long shoppingRefundId);
}