package com.huaixuan.network.biz.dao.goods;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.goods.GoodsInstance;
import com.huaixuan.network.biz.domain.storage.StockAge;
import com.huaixuan.network.biz.domain.taobao.ProductForTaobaoFxAdd;
import com.huaixuan.network.biz.query.QueryPage;

public interface GoodsInstanceDao {

	List<GoodsInstance> findGoodsInstancesByGoodsId(Long goodsId);

	void createGoodsInstance(GoodsInstance gi);

	GoodsInstance getInstance(Long id);

	GoodsInstance getClientInstance(Map pramas);
	
	void updateGoodsInstance(GoodsInstance gi);

//	List<GoodsInstance> searchGoodsInstances(GoodsInstance search, Page page);

	QueryPage searchGoodsInstancesHasStorage(GoodsInstance search, int currentPage, int pageSize);

	void updateGoodsInstanceLocations(Long id, List<Long> los);

	List<Long> findGoodsInstanceLocations(Long id);

    /**
     * ��id
     * @param locId
     * @return
     */
    List<Long> findGoodsInstanceInLocations(Long locId);

	List<GoodsInstance> findGoodsInstancesByCode(String code);

	/**
	 * ������Ʒʵ����Ŀ��ÿ������
	 * @param id Long
	 * @param count int
	 * @return Long
	 * @author chenyan 2009/07/25
	 */
	int updateGoodsInstanceExistNumById(Long id, Long count);
	
	int updateGoodsInstanceSaleNumberById(Long id, Long count);

	int updateGoodsInstanceHKExistNumById(Long id, Long count);
	
	int updateGoodsInstanceNumberZero();
	
	
	/**
	 * ������Ʒʵ�������17������17
	 * @param id Long
	 * @param count Long
	 * @return int
	 * @author chenyan 2009/07/25
	 */
	int updateWayNumById(Long id, Long count);

	void updateGoodsUnit(long id, String goodsUnit);

//	List<GoodsInstance> searchSupplierGoodsInstances(GoodsInstance search,
//			Page page, String supplierId);

	/**���������Ʒ��
	 * @param goodsList
	 */
	public void insertBatch(final List<GoodsInstance> goodsList);

	 /**
     *
     * ��Ʒʵ���޸�
     * @author lincf 2009/12/14
     */
    void updateGoodsInstanceByMap(Map map);

    void moveGoodsInstanceAttrs(long id);


    public int getStockNumByInstanceId(Map map);

    public List<StockAge> getStockSupplierByInstanceId(Long id);

    public long getStockAgeListByInstanceId(Long locId);

    public void insertStockAgeBySa(StockAge sa);

    public int updateStockAgeBysa(StockAge sa);

    public List<GoodsInstance> getGoodsInstances();

//    public List<GoodsInstance> searchBackGoodsInstances(GoodsInstance search,Page page);

    public GoodsInstance getInstanceByCode(String code);

//    public List<ProductForTaobaoFxAdd> getProductsForTaobaoFxAdd(
//			ProductForTaobaoFxAdd productForTaobaoFxAdd, Page page);

    public int getProductsForTaobaoFxAddCount(
			ProductForTaobaoFxAdd productForTaobaoFxAdd);

    public GoodsInstance getInstanceTaobaoSkuPropById(Long id);

    public List<String> getSkuPropertyListByGoodsId(Long goodsId);

    /**
     * ��ȡ�������Ʒ�б�
     * @author zhangwy
     * @return
     */
//    List<GoodsInstance> getSaleGiftGoodsInstance(Map parMap, Page page);

    /**
     * ���ݲ�ƷID��ѯ�����Ʒ
     * @param idList
     * @return
     */
    List<GoodsInstance> getFullGiveGoodsInstance(Map map);
    
    /**
     * ��ȡ��Ʒ�б�����
     * @param parMap
     * @return
     */
    Integer getInstanceListByConditionWithPageCount(Map parMap);
    
    /**
     * ��ȡ��Ʒ�б�
     * @param parMap
     * @return
     */
    List<GoodsInstance> getInstanceListByConditionWithPage(Map parMap);
    
    
    /**
     * ��ȡ��Ӧ�̲�Ʒ�б�����
     * @param parMap
     * @return
     */
    Integer getSupplierInstanceListByConditionWithPageCount(Map parMap);
    
    /**
     * ��ȡ��Ӧ�̲�Ʒ�б�
     * @param parMap
     * @return
     */
    List<GoodsInstance> getSupplierInstanceListByConditionWithPage(Map parMap);
    
    /**
     * ��ȡ���Ͳ�Ʒ�б�����
     * @param parMap
     * @return
     */
    Integer getSaleGiftInstanceListByConditionWithPageCount(Map parMap);
    
    /**
     * ��ȡ���Ͳ�Ʒ�б�
     * @param parMap
     * @return
     */
    List<GoodsInstance> getSaleGiftInstanceListByConditionWithPage(Map parMap);
    
    
    /**
     * ��ȡ��̨������Ʒ�б�����
     * @param parMap
     * @return
     */
    Integer getBackInstanceListByConditionWithPageCount(Map parMap);
    
    /**
     * ��ȡ��̨������Ʒ�б�
     * @param parMap
     * @return
     */
    List<GoodsInstance> getBackInstanceListByConditionWithPage(Map parMap);

    List<GoodsInstance> searchGoodsInstancesHasStorage(Map<String, String> pramas);
    
    
    }
