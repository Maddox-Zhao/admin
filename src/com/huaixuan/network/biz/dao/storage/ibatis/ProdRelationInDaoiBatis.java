package com.huaixuan.network.biz.dao.storage.ibatis;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.storage.ProdRelationInDao;
import com.huaixuan.network.biz.domain.storage.ProdRelationIn;

/**
 * 
 * @version 3.2.0
 */
@Service("prodRelationInDao")
public class ProdRelationInDaoiBatis implements ProdRelationInDao {

	@Autowired
	private SqlMapClientTemplate sqlMapClient;
/* @model: */
	public long addProdRelationIn(ProdRelationIn prodRelationIn) {
		return (Long)this.sqlMapClient.insert("addProdRelationIn", prodRelationIn);
	}

	/* @model: */
	public void editProdRelationIn(ProdRelationIn prodRelationIn) {
		this.sqlMapClient.update("editProdRelationIn", prodRelationIn);
	}

	/* @model: */
	public void removeProdRelationIn(Long prodRelationInId) {
		this.sqlMapClient.delete("removeProdRelationIn", prodRelationInId);
	}
/* @model: */
	public ProdRelationIn getProdRelationIn(Long prodRelationInId) {
		return (ProdRelationIn) this.sqlMapClient.queryForObject("getProdRelationIn", prodRelationInId);
	}
/* @model: */
	public List<ProdRelationIn> getProdRelationIns() {
		return this.sqlMapClient.queryForList("getProdRelationIns", null);
	}

	/*
	 * 
	 * @see com.hundsun.bible.dao.ios.ProdRelationInDao#getPrintList()
	 */
	public List<ProdRelationIn> getPrintList(Long inDepId) {
		return this.sqlMapClient.queryForList("getPrintInDepList", inDepId);
	}

	public void removeProdRelationInForAdd(ProdRelationIn prodRelationIn) {
		this.sqlMapClient.delete("removeProdRelationInForAdd", prodRelationIn);
	}

	@SuppressWarnings("unchecked")
	public List<ProdRelationIn> getLocIdAndAmountForStorage(ProdRelationIn prodRelationIn) {
		return this.sqlMapClient.queryForList("getLocIdAndAmountForStorage", prodRelationIn);
	}

	@SuppressWarnings("unchecked")
	public List<ProdRelationIn> getSalesDistributedAmount(Map map) {
		return (List<ProdRelationIn>) this.sqlMapClient.queryForList("getSalesDistributedAmount", map);
	}

	public Long getShoppingAndCheckDistributedAmount(Map map) {
		return (Long) this.sqlMapClient.queryForObject("getShoppingAndCheckDistributedAmount", map);
	}
}
