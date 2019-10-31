package com.huaixuan.network.biz.dao.shop.ibatis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.shop.AdDao;
import com.huaixuan.network.biz.domain.shop.Ad;

@Service("adDao")
public class AdDaoiBatis implements AdDao {

	@Autowired
	private SqlMapClientTemplate sqlMapClient;

	@Override
	public Long addAd(Ad ad) throws Exception {
		Long id = (Long) this.sqlMapClient.insert("addAd", ad);
		return id;
	}

	@Override
	public void editAd(Ad ad) throws Exception {
		this.sqlMapClient.update("editAd", ad);
	}

	@Override
	public void removeAd(Ad ad) throws Exception {
		this.sqlMapClient.update("removeAd", ad);
	}

	@Override
	public Ad getAd(Long adId) throws Exception {
		return (Ad) this.sqlMapClient.queryForObject("getAd", adId);
	}

	@Override
	public List<Ad> getAds() throws Exception {
		return this.sqlMapClient.queryForList("getAds", null);
	}

	@Override
	public void updateAd(Ad ad) throws Exception {
		this.sqlMapClient.update("updateAd", ad);
	}

	@Override
	public Integer getAdsCount(Ad ad) throws Exception {
		Map map = new HashMap();
		map.put("adType", ad.getAdType());
		return (Integer) this.sqlMapClient.queryForObject("getAdsCount", map,
				null);
	}

	@Override
	public List<Ad> getAdsPage(Map<String, String> pramas) throws Exception {
		return this.sqlMapClient.queryForList("getAdsPage", pramas);
	}

	@Override
	public List<Ad> getAddInfo(Map parMap) throws Exception {
		return this.sqlMapClient.queryForList("getAdInfo", parMap);
	}

	@Override
	public Integer getAdMaxSort() throws Exception {
		return (Integer) this.sqlMapClient.queryForObject("getAdMaxSort", null);
	}

	@Override
	public void updateAdSortUpdtae(int maxid, int maxid2) throws Exception {
		Map param = new HashMap();
		param.put("maxid", maxid);
		param.put("maxid2", maxid2);
		this.sqlMapClient.update("updateAdSortUpdtae", param);
	}

	@Override
	public void updateAdSortlow(int maxid, int maxid2) throws Exception {
		Map param = new HashMap();
		param.put("maxid", maxid);
		param.put("maxid2", maxid2);
		this.sqlMapClient.update("updateAdSortlow", param);
	}
}
