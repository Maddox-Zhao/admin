package com.huaixuan.network.biz.service.goods;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.admin.Admin;
import com.huaixuan.network.biz.domain.goods.AvailableStock;
import com.huaixuan.network.biz.domain.goods.Goods;
import com.huaixuan.network.biz.domain.goods.GoodsInstance;
import com.huaixuan.network.biz.domain.storage.StockAge;
import com.huaixuan.network.biz.domain.taobao.ProductForTaobaoFxAdd;
import com.huaixuan.network.biz.query.QueryPage;

public interface GoodsInstanceManager {


	public void createBlankInstance(Long goodsId);

	public void createGoodsInstance(GoodsInstance gi);

	public void updateGoodsInstance(GoodsInstance gi);

	public void removeGoodsInstance(GoodsInstance gi);

	public List<GoodsInstance> findGoodsInstances(Long goodsId);

	public GoodsInstance getInstance(Long id);
	
	public GoodsInstance getClientInstance(Map pramas);

	public QueryPage searchGoodsInstancesHasStorage(GoodsInstance search, int currentPage, int pageSize);

	public void updateGoodsInstanceLocations(Long id, List<Long> los);

	public List<Long> getGoodsInstanceLocations(Long id);

    public List<Long> getGoodsInstanceInLocations(Long locId);

	public String createNewInstanceCode(Goods goods);

    /**通过goodsInstanceId  得到不同仓库的记录
     * @param goodsInstanceId产品ID
     * @return
     */
    List<AvailableStock> getAvailableStockListByInstanceId(Long goodsInstanceId);;

	/**
	 * 检查code是否可用
	 * @param code
	 * @return
	 */
	public boolean checkCode(GoodsInstance gi);

	/**
	 * 更新商品实例表的可用库存数量
	 * @param id Long
	 * @param count Long
	 * @return int
	 * @author chenyan 2009/07/25
	 */
	int updateGoodsInstanceExistNumById(Long id, Long count);
	
	int updateGoodsInstanceSaleNumberById(Long id, Long count);
	
	int updateGoodsInstanceHKExistNumById(Long id, Long count);

	int updateGoodsInstanceNumberZero();
	/**
	 * 更新商品实例表的在途库存数量
	 * @param id Long
	 * @param count Long
	 * @return int
	 * @author chenyan 2009/07/25
	 */
	int updateWayNumById(Long id, Long count);

//	public List<GoodsInstance> searchSupplierGoodsInstances(
//			GoodsInstance search, Page page, String supplierId);

    /**
     * 更新商品库存数量（emall_goods,ioss_goods_instance表）
     * @param goodsInstanceId Long 商品实例ID
     * @param goodsId Long 商品ID
     * @param amount Long 数量（若要减去库存，只需负号）
     * @return Boolean 更新成功标识
     * @author chenyan 2009/07/30
     */
    Boolean updateAmountForTwo(Long goodsInstanceId, Long goodsId, Long amount);


    /**更新商品库存数量（emall_goods,ioss_goods_instance表） availableStock表
     * @param goodsInstanceId商品实例ID
     * @param goodsId商品ID
     * @param amount数量（若要减去库存，只需负号）
     * @param depFirstId 一级仓库ID
     * @param paipaiRelation 是否要与拍拍同步数量
     * @param taobaoRelation 是否要与淘宝同步数量
     * @return
     */
    public Boolean updateAmountForTaobao(Long goodsInstanceId, Long goodsId, Long amount,Long depFirstId,boolean taobaoRelation);


    /**更新商品库存数量（emall_goods,ioss_goods_instance表） availableStock表
     * @param goodsInstanceId商品实例ID
     * @param goodsId商品ID
     * @param amount数量（若要减去库存，只需负号）
     * @param depFirstId 一级仓库ID
     * @param paipaiRelation 是否要与拍拍同步数量
     */
    public Boolean updateAmountForTwo(Long goodsInstanceId, Long goodsId, Long amount,Long depFirstId, boolean paipaiRelation);

    /**通过产品ID和第一级仓库ID查询AvailableStock对象
     * @param goodsInstanceId
     * @param depFirstId
     * @return
     */
    AvailableStock getAvailableStock(Long goodsInstanceId, Long depFirstId);

    /**
     * 获取库龄数量
     * @param id Long
     * @param supplierId Long
     * @param locId Long
     * @param storType String
     * @param days int
     * @param afterDays int
     * @return int
     * @modified by chenyan 2011/03/02
     */
   public int getStockNumByInstanceId(Long id,Long supplierId,Long locId,String storType,int days, int afterDays);

   public List<StockAge> getStockSupplierByInstanceId(Long id);

   public long getStockAgeListByInstanceId(Long locId);

   public void  insertStockAgeBySa(StockAge sa);

   public int updateStockAgeBysa(StockAge sa);

   public List<GoodsInstance> getGoodsInstances();

   /**
    * 后台下订单搜索
    * @param search
    * @param page
    * @return
    */
//   public List<GoodsInstance> searchBackGoodsInstances(GoodsInstance search,
//			Page page);

//   public List<ProductForTaobaoFxAdd> getProductsForTaobaoFxAdd(ProductForTaobaoFxAdd productForTaobaoFxAdd, Page page);

   public int getProductsForTaobaoFxAddCount(ProductForTaobaoFxAdd productForTaobaoFxAdd);

	/**
	 * 根据产品编码查询产品对象
	 * @param code
	 * @return
	 */
	public GoodsInstance getInstanceByCode(String code);

	   /**
     * 根据goodsInsId查询淘宝属性key
     * @param id
     * @return
     * @see com.hundsun.bible.dao.ios.GoodsInstanceDao#getInstanceTaobaoSkuPropById(java.lang.Long)
     */
    public GoodsInstance getInstanceTaobaoSkuPropById(Long id) ;

    public List<String> getSkuPropertyListByGoodsId(Long goodsId);

    /**
     * 获取买就赠产品列表
     * @author zhangwy
     * @return
     */
//    public List<GoodsInstance> getSaleGiftGoodsInstance(Map parMap,Page page);

    /**
     * 根据产品ID查询多个产品
     * @param idList
     * @return
     */
    public List<GoodsInstance> getFullGiveGoodsInstance(List idList);

    /**
     * 根据产品ID查询多个产品（如果有相同的产品ID，不去重）
     * @param idList
     * @return
     */
    public List<GoodsInstance> getAllFullGiveGoodsInstance(List<String> idList);
    
	/**
	 * 分页查询获取产品列表
	 *
	 * @param instanceList
	 * @return
	 */
	public QueryPage getInstanceListByConditionWithPage(GoodsInstance instance, int currPage, int pageSize);
	
	/**
	 * 获取产品列表
	 * @param instance
	 * @return
	 */
	public List<GoodsInstance> getInstanceListByConditionWithPage(GoodsInstance instance);
	
	/**
	 * 获取供应商产品列表
	 */
	public QueryPage getSupplierInstanceListByConditionWithPage(GoodsInstance instance, int currPage, int pageSize);
	
	/**
	 * 获取赠送产品列表
	 * @param instance
	 * @param currPage
	 * @param pageSize
	 * @return
	 */
	public QueryPage getSaleGiftInstanceListByConditionWithPage(GoodsInstance instance, int currPage, int pageSize);
	
	/**
	 * 获取后台下订单产品列表
	 * @param instance
	 * @param currPage
	 * @param pageSize
	 * @return
	 */
	public QueryPage getBackInstanceListByConditionWithPage(GoodsInstance instance, int currPage, int pageSize);

    public List<GoodsInstance> searchGoodsInstancesHasStorage(GoodsInstance search);
}
