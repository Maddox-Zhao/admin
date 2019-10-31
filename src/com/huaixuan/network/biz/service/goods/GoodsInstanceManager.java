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

    /**ͨ��goodsInstanceId  �õ���ͬ�ֿ�ļ�¼
     * @param goodsInstanceId��ƷID
     * @return
     */
    List<AvailableStock> getAvailableStockListByInstanceId(Long goodsInstanceId);;

	/**
	 * ���code�Ƿ����
	 * @param code
	 * @return
	 */
	public boolean checkCode(GoodsInstance gi);

	/**
	 * ������Ʒʵ����Ŀ��ÿ������
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
	 * ������Ʒʵ�������;�������
	 * @param id Long
	 * @param count Long
	 * @return int
	 * @author chenyan 2009/07/25
	 */
	int updateWayNumById(Long id, Long count);

//	public List<GoodsInstance> searchSupplierGoodsInstances(
//			GoodsInstance search, Page page, String supplierId);

    /**
     * ������Ʒ���������emall_goods,ioss_goods_instance��
     * @param goodsInstanceId Long ��Ʒʵ��ID
     * @param goodsId Long ��ƷID
     * @param amount Long ��������Ҫ��ȥ��棬ֻ�踺�ţ�
     * @return Boolean ���³ɹ���ʶ
     * @author chenyan 2009/07/30
     */
    Boolean updateAmountForTwo(Long goodsInstanceId, Long goodsId, Long amount);


    /**������Ʒ���������emall_goods,ioss_goods_instance�� availableStock��
     * @param goodsInstanceId��Ʒʵ��ID
     * @param goodsId��ƷID
     * @param amount��������Ҫ��ȥ��棬ֻ�踺�ţ�
     * @param depFirstId һ���ֿ�ID
     * @param paipaiRelation �Ƿ�Ҫ������ͬ������
     * @param taobaoRelation �Ƿ�Ҫ���Ա�ͬ������
     * @return
     */
    public Boolean updateAmountForTaobao(Long goodsInstanceId, Long goodsId, Long amount,Long depFirstId,boolean taobaoRelation);


    /**������Ʒ���������emall_goods,ioss_goods_instance�� availableStock��
     * @param goodsInstanceId��Ʒʵ��ID
     * @param goodsId��ƷID
     * @param amount��������Ҫ��ȥ��棬ֻ�踺�ţ�
     * @param depFirstId һ���ֿ�ID
     * @param paipaiRelation �Ƿ�Ҫ������ͬ������
     */
    public Boolean updateAmountForTwo(Long goodsInstanceId, Long goodsId, Long amount,Long depFirstId, boolean paipaiRelation);

    /**ͨ����ƷID�͵�һ���ֿ�ID��ѯAvailableStock����
     * @param goodsInstanceId
     * @param depFirstId
     * @return
     */
    AvailableStock getAvailableStock(Long goodsInstanceId, Long depFirstId);

    /**
     * ��ȡ��������
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
    * ��̨�¶�������
    * @param search
    * @param page
    * @return
    */
//   public List<GoodsInstance> searchBackGoodsInstances(GoodsInstance search,
//			Page page);

//   public List<ProductForTaobaoFxAdd> getProductsForTaobaoFxAdd(ProductForTaobaoFxAdd productForTaobaoFxAdd, Page page);

   public int getProductsForTaobaoFxAddCount(ProductForTaobaoFxAdd productForTaobaoFxAdd);

	/**
	 * ���ݲ�Ʒ�����ѯ��Ʒ����
	 * @param code
	 * @return
	 */
	public GoodsInstance getInstanceByCode(String code);

	   /**
     * ����goodsInsId��ѯ�Ա�����key
     * @param id
     * @return
     * @see com.hundsun.bible.dao.ios.GoodsInstanceDao#getInstanceTaobaoSkuPropById(java.lang.Long)
     */
    public GoodsInstance getInstanceTaobaoSkuPropById(Long id) ;

    public List<String> getSkuPropertyListByGoodsId(Long goodsId);

    /**
     * ��ȡ�������Ʒ�б�
     * @author zhangwy
     * @return
     */
//    public List<GoodsInstance> getSaleGiftGoodsInstance(Map parMap,Page page);

    /**
     * ���ݲ�ƷID��ѯ�����Ʒ
     * @param idList
     * @return
     */
    public List<GoodsInstance> getFullGiveGoodsInstance(List idList);

    /**
     * ���ݲ�ƷID��ѯ�����Ʒ���������ͬ�Ĳ�ƷID����ȥ�أ�
     * @param idList
     * @return
     */
    public List<GoodsInstance> getAllFullGiveGoodsInstance(List<String> idList);
    
	/**
	 * ��ҳ��ѯ��ȡ��Ʒ�б�
	 *
	 * @param instanceList
	 * @return
	 */
	public QueryPage getInstanceListByConditionWithPage(GoodsInstance instance, int currPage, int pageSize);
	
	/**
	 * ��ȡ��Ʒ�б�
	 * @param instance
	 * @return
	 */
	public List<GoodsInstance> getInstanceListByConditionWithPage(GoodsInstance instance);
	
	/**
	 * ��ȡ��Ӧ�̲�Ʒ�б�
	 */
	public QueryPage getSupplierInstanceListByConditionWithPage(GoodsInstance instance, int currPage, int pageSize);
	
	/**
	 * ��ȡ���Ͳ�Ʒ�б�
	 * @param instance
	 * @param currPage
	 * @param pageSize
	 * @return
	 */
	public QueryPage getSaleGiftInstanceListByConditionWithPage(GoodsInstance instance, int currPage, int pageSize);
	
	/**
	 * ��ȡ��̨�¶�����Ʒ�б�
	 * @param instance
	 * @param currPage
	 * @param pageSize
	 * @return
	 */
	public QueryPage getBackInstanceListByConditionWithPage(GoodsInstance instance, int currPage, int pageSize);

    public List<GoodsInstance> searchGoodsInstancesHasStorage(GoodsInstance search);
}
