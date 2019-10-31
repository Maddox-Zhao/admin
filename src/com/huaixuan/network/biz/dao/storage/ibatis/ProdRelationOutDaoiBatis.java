package com.huaixuan.network.biz.dao.storage.ibatis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.storage.ProdRelationOutDao;
import com.huaixuan.network.biz.domain.storage.ProdRelationOut;

/**
 *
 * @version 3.2.0
 */
@Service("prodRelationOutDao")
public class ProdRelationOutDaoiBatis implements ProdRelationOutDao {

	@Autowired
	private SqlMapClientTemplate sqlMapClient;

	/* @model: */
	public long addProdRelationOut(ProdRelationOut prodRelationOut)
			throws Exception {
		return (Long)this.sqlMapClient.insert("addProdRelationOut", prodRelationOut);
	}

	/* @model: */
	public void editProdRelationOut(ProdRelationOut prodRelationOut)
			throws Exception {
		this.sqlMapClient.update("editProdRelationOut", prodRelationOut);
	}

	/* @model: */
	public void removeProdRelationOut(Long prodRelationOutId) throws Exception {
		this.sqlMapClient.delete("removeProdRelationOut", prodRelationOutId);
	}

	/* @model: */
	public ProdRelationOut getProdRelationOut(Long prodRelationOutId)
			throws Exception {
		return (ProdRelationOut) this.sqlMapClient.queryForObject(
				"getProdRelationOut", prodRelationOutId);
	}

	/* @model: */
	public List<ProdRelationOut> getProdRelationOuts() throws Exception {
		return this.sqlMapClient.queryForList("getProdRelationOuts", null);
	}

	/*
	 *
	 * @see
	 * com.hundsun.bible.dao.ios.ProdRelationOutDao#getPrintList(java.lang.Long)
	 */
	public List<ProdRelationOut> getPrintList(Long outDepId) throws Exception {
		return this.sqlMapClient.queryForList("getPrintOutDepList", outDepId);
	}

	@SuppressWarnings("unchecked")
	public void removeProdRelationOutForAdd(Long outDepId,
			Long goodsInstanceId, Long outDetailId, Long goodsId) {
		Map map = new HashMap();
		map.put("outDepId", outDepId);
		map.put("goodsInstanceId", goodsInstanceId);
		map.put("outDetailId", outDetailId);
		map.put("goodsId", goodsId);
		this.sqlMapClient.delete("removeProdRelationOutForAdd", map);
	}

	@SuppressWarnings("unchecked")
	public List<ProdRelationOut> getAmountAndStorageId(Long outDepId,
			Long goodsInstanceId, Long goodsId, Long outDetailId) {
		Map map = new HashMap();
		map.put("outDepId", outDepId);
		map.put("goodsInstanceId", goodsInstanceId);
		map.put("goodsId", goodsId);
		map.put("outDetailId", outDetailId);
		return this.sqlMapClient.queryForList("getAmountAndStorageId", map);
	}

	public Long getDistributedAmount(Map map) {
		return (Long) this.sqlMapClient.queryForObject("getDistributedAmount",
				map);
	}
}
