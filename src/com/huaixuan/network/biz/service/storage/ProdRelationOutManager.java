package com.huaixuan.network.biz.service.storage;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.storage.ProdRelationOut;

/**
 * 出库中间数据表(bibleUtil auto code generation)
 * 
 * @version 3.2.0
 */
public interface ProdRelationOutManager {
	/**
	 * 新增
	 * 
	 * @param prodRelationOut
	 */
	public void addProdRelationOut(ProdRelationOut prodRelationOut);

	/**
	 * 编辑
	 * 
	 * @param prodRelationOut
	 */
	public void editProdRelationOut(ProdRelationOut prodRelationOut);

	/**
	 * 删除
	 * 
	 * @param prodRelationOutId
	 */
	public void removeProdRelationOut(Long prodRelationOutId);

	/**
	 * 根据ID查询对象
	 * 
	 * @param prodRelationOutId
	 * @return
	 */
	public ProdRelationOut getProdRelationOut(Long prodRelationOutId);

	/**
	 * 查询所有对象
	 * 
	 * @return
	 */
	public List<ProdRelationOut> getProdRelationOuts();

	/**
	 * 得到打印出库单结果集合
	 * 
	 * @param outDepId
	 * @return
	 */
	public List<ProdRelationOut> getPrintList(Long outDepId);

	/**
	 * 取得已分配过的产品数量
	 * 
	 * @param map
	 *            Map
	 * @return Long
	 * @author chenyan 2009/08/02
	 */
	Long getDistributedAmount(Map map);
}
