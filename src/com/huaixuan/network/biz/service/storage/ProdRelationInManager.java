package com.huaixuan.network.biz.service.storage;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.storage.ProdRelationIn;

/**
 * 入库单中间表基本信息(bibleUtil auto code generation)
 * 
 * @version 3.2.0
 */
public interface ProdRelationInManager {
	/**
	 * 新增
	 * 
	 * @param prodRelationIn
	 */
	public void addProdRelationIn(ProdRelationIn prodRelationIn);

	/**
	 * 编辑
	 * 
	 * @param prodRelationIn
	 */
	public void editProdRelationIn(ProdRelationIn prodRelationIn);

	/**
	 * 删除
	 * 
	 * @param prodRelationInId
	 */
	public void removeProdRelationIn(Long prodRelationInId);

	/**
	 * 根据ID查询对象
	 * 
	 * @param prodRelationInId
	 * @return
	 */
	public ProdRelationIn getProdRelationIn(Long prodRelationInId);

	/**
	 * 查询所有对象
	 * 
	 * @return
	 */
	public List<ProdRelationIn> getProdRelationIns();

	/**
	 * 得到打印入库单结果集合
	 * 
	 * @param inDepId
	 * @return
	 */
	public List<ProdRelationIn> getPrintList(Long inDepId);

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
