package com.huaixuan.network.biz.dao.shop.ibatis;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.shop.AdDetailDao;
import com.huaixuan.network.biz.domain.shop.AdDetail;

@Service("adDetailDao")
public class AdDetailDaoiBatis implements AdDetailDao {
	@Autowired
	private SqlMapClientTemplate sqlMapClient;

	@Override
	public Long addAdDetail(AdDetail adDetail) throws Exception {
		Long id = (Long) this.sqlMapClient.insert("addAdDetail", adDetail);
		return id;
	}

	@Override
	public void editAdDetail(AdDetail adDetail) throws Exception {
		this.sqlMapClient.update("editAdDetail", adDetail);
	}

	@Override
	public void removeAdDetail(Long adDetailId) throws Exception {
		this.sqlMapClient.delete("removeAdDetail", adDetailId);
	}

	@Override
	public AdDetail getAdDetail(Long adDetailId) throws Exception {
		return (AdDetail) this.sqlMapClient.queryForObject("getAdDetail",
				adDetailId);
	}

	@Override
	public List<AdDetail> getAdDetails() throws Exception {
		return this.sqlMapClient.queryForList("getAdDetails", null);
	}

	@Override
	public void updateAdDetail(AdDetail adDetail) throws Exception {
		this.sqlMapClient.update("updateAdDetail", adDetail);
	}

	@Override
	public AdDetail getAdDetailByAdId(Long adId) throws Exception {
		return (AdDetail) this.sqlMapClient.queryForObject("getAdDetailByAdId",
				adId);
	}

}
