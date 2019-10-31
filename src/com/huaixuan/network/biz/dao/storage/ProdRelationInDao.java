package com.huaixuan.network.biz.dao.storage;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.storage.ProdRelationIn;

/**
 * (bibleUtil auto code generation)
 * 
 * @version 3.2.0
 */
public interface ProdRelationInDao {
	/* @interface model: ProdRelationIn */
	long addProdRelationIn(ProdRelationIn prodRelationIn);

	/* @interface model: ProdRelationIn */
	void editProdRelationIn(ProdRelationIn prodRelationIn);

	/* @interface model: ProdRelationIn */
	void removeProdRelationIn(Long prodRelationInId);

	/* @interface model: ProdRelationIn,ProdRelationIn */
	ProdRelationIn getProdRelationIn(Long prodRelationInId);

	/* @interface model: ProdRelationIn,ProdRelationIn */
	List<ProdRelationIn> getProdRelationIns();

	/**
	 * 得到打印入库单结果集合
	 * 
	 * @param inDepId
	 * @return @
	 */
	List<ProdRelationIn> getPrintList(Long inDepId);

	/**
	 * 在数据添加前的删除操作
	 * 
	 * @param prodRelationIn
	 *            ProdRelationIn
	 * @author chenyan 2009/07/23
	 */
	void removeProdRelationInForAdd(ProdRelationIn prodRelationIn);

	/**
	 * 取得被分配的库位ID和数量，用于更新库存表
	 * 
	 * @param prodRelationIn
	 *            ProdRelationIn
	 * @return List
	 * @author chenyan 2009/07/25
	 */
	List<ProdRelationIn> getLocIdAndAmountForStorage(ProdRelationIn prodRelationIn);

	/**
	 * 取得已分配过的产品数量（销售退换货）
	 * 
	 * @param map
	 *            Map
	 * @return List
	 * @author chenyan 2009/08/03
	 */
	@SuppressWarnings("unchecked")
	List<ProdRelationIn> getSalesDistributedAmount(Map map);

	/**
	 * 取得已分配过的产品数量（采购和盘点）
	 * 
	 * @param map
	 *            Map
	 * @return Long
	 * @author chenyan 2009/08/03
	 */
	@SuppressWarnings("unchecked")
	Long getShoppingAndCheckDistributedAmount(Map map);
}
