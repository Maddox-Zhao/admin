package com.huaixuan.network.biz.dao.shop.ibatis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.shop.CabinetDao;
import com.huaixuan.network.biz.domain.goods.Goods;
import com.huaixuan.network.biz.domain.shop.Cabinet;

@Service("cabinetDao")
public class CabinetDaoiBatis implements CabinetDao {
	@Autowired
	private SqlMapClientTemplate sqlMapClient;

	@Override
	public Long addCabinet(Cabinet cabinet) throws Exception {
		Long id = (Long) this.sqlMapClient.insert("addCabinet", cabinet);
		return id;
	}

	@Override
	public void editCabinet(Cabinet cabinet) throws Exception {
		this.sqlMapClient.update("editCabinet", cabinet);
	}

	@Override
	public void removeCabinet(Long cabinetId) throws Exception {
		this.sqlMapClient.delete("removeCabinet", cabinetId);
	}

	@Override
	public Cabinet getCabinet(Long cabinetId) throws Exception {
		return (Cabinet) this.sqlMapClient.queryForObject("getCabinet",
				cabinetId);
	}

	@Override
	public List<Cabinet> getCabinets() throws Exception {
		return this.sqlMapClient.queryForList("getCabinets", null);
	}

	@Override
	public Integer getCabinetMaxSort() throws Exception {
		return (Integer) this.sqlMapClient.queryForObject("getCabinetMaxSort",
				null);
	}

	@Override
	public void updateCabinetSortUpdtae(int maxid, int maxid2) throws Exception {
		Map param = new HashMap();
		param.put("maxid", maxid);
		param.put("maxid2", maxid2);
		this.sqlMapClient.update("updateCabinetSortUpdtae", param);
	}

	@Override
	public void updateCabinetSortlow(int maxid, int maxid2) throws Exception {
		Map param = new HashMap();
		param.put("maxid", maxid);
		param.put("maxid2", maxid2);
		this.sqlMapClient.update("updateCabinetSortlow", param);
	}

	@Override
	public List<Cabinet> getCabinetForSort() throws Exception {
		return this.sqlMapClient.queryForList("getCabinetAvailable");
	}

	@Override
	public int getGoodsAmountOfTheCabinet(Long id) throws Exception {
		return (Integer) this.sqlMapClient.queryForObject(
				"getCabinetAvailableCount", id);
	}

	@Override
	public Cabinet getCabinetByName(String name) throws Exception {
		return (Cabinet) this.sqlMapClient.queryForObject("getCabinetByName",
				name);
	}

	@Override
	public List<Goods> getCabGoodsByName(String name) {
		return this.sqlMapClient.queryForList("getCabGoodsByName", name);
	}
}
