package com.huaixuan.network.biz.dao.shop.ibatis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.shop.ShowcaseDao;
import com.huaixuan.network.biz.domain.shop.Showcase;

@Service("showcaseDao")
public class ShowcaseDaoiBatis implements ShowcaseDao {
	@Autowired
	private SqlMapClientTemplate sqlMapClient;

	@Override
	public Long addShowcase(Showcase showcase) throws Exception {
		Long id = (Long) this.sqlMapClient.insert("addShowcase", showcase);
		return id;
	}

	@Override
	public void editShowcase(Showcase showcase) throws Exception {
		this.sqlMapClient.update("editShowcase", showcase);
	}

	@Override
	public void removeShowcase(Long showcaseId) throws Exception {
		this.sqlMapClient.delete("removeShowcase", showcaseId);
	}

	@Override
	public Showcase getShowcase(Long showcaseId) throws Exception {
		return (Showcase) this.sqlMapClient.queryForObject("getShowcase",
				showcaseId);
	}

	@Override
	public List<Showcase> getShowcases() throws Exception {
		return this.sqlMapClient.queryForList("getShowcases", null);
	}

	@Override
	public List<Showcase> getShowcasesByCabinetId(Long cabinetId)
			throws Exception {
		return this.sqlMapClient.queryForList("getShowcasesByCabinetId",
				cabinetId);
	}

	@Override
	public Integer getShowcaseMaxSortByCabinetId(Long cabinetId)
			throws Exception {
		return (Integer) this.sqlMapClient.queryForObject(
				"getShowcaseMaxSortByCabinetId", cabinetId);
	}

	@Override
	public void updateShowcaseSortByCabinetId(Showcase showcase)
			throws Exception {
		this.sqlMapClient.update("updateShowcaseSortByCabinetId", showcase);
	}

	@Override
	public void showcaseUpBig(Long showcaseId) throws Exception {
		this.sqlMapClient.update("showcaseUpBig", showcaseId);
	}

	@Override
	public void showcaseUpSamll(Long cabinetId, int sort) throws Exception {
		Map prama = new HashMap();
		prama.put("cabinetId", cabinetId);
		prama.put("sort", sort);
		this.sqlMapClient.update("showcaseUpSamll", prama);
	}

	@Override
	public void showcaseDownBig(Long showcaseId) throws Exception {
		this.sqlMapClient.update("showcaseDownBig", showcaseId);
	}

	@Override
	public void showcaseDownSamll(Long cabinetId, int sort) throws Exception {
		Map prama = new HashMap();
		prama.put("cabinetId", cabinetId);
		prama.put("sort", sort);
		this.sqlMapClient.update("showcaseDownSamll", prama);
	}

	@Override
	public Showcase getShowcaseByGoodsIdAndCabId(Long goodsId, Long cabId)
			throws Exception {
		Map prama = new HashMap();
		prama.put("goodsId", goodsId);
		prama.put("cabId", cabId);
		return (Showcase) this.sqlMapClient.queryForObject(
				"getShowcaseByGoodsIdAndCabId", prama);
	}

	@Override
	public Integer getShowcasesCount(long cabinetId) throws Exception {
		return (Integer) this.sqlMapClient.queryForObject("getShowcasesCount",
				cabinetId);
	}

	@Override
	public List<Showcase> getShowcasesPage(Map<String, String> conditions) {
		return this.sqlMapClient.queryForList("getShowcasesPage", conditions);
	}

}
