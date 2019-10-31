package com.huaixuan.network.biz.dao.shop.ibatis;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.shop.AdPositionDao;
import com.huaixuan.network.biz.domain.shop.AdPosition;

@Service("adPositionDao")
public class AdPositionDaoiBatis implements AdPositionDao {
	@Autowired
	private SqlMapClientTemplate sqlMapClient;

	@Override
	public Long addAdPosition(AdPosition adPosition) throws Exception {
		Long id = (Long) this.sqlMapClient.insert("addAdPosition", adPosition);
		return id;
	}

	@Override
	public void editAdPosition(AdPosition adPosition) throws Exception {
		this.sqlMapClient.update("editAdPosition", adPosition);
	}

	@Override
	public void removeAdPosition(Long adPositionId) throws Exception {
		this.sqlMapClient.delete("removeAdPosition", adPositionId);
	}

	public AdPosition getAdPosition(Long adPositionId) throws Exception {
		return (AdPosition) this.sqlMapClient.queryForObject("getAdPosition",
				adPositionId);
	}

	@Override
	public List<AdPosition> getAdPositions() throws Exception {
		return this.sqlMapClient.queryForList("getAdPositions", null);
	}

	@Override
	public Integer getAdPositionsCount() throws Exception {
		return (Integer) this.sqlMapClient.queryForObject(
				"getAdPositionsCount", null);
	}

	// public PageUtil<AdPosition> getAdPositionsPage(Map<String, Object>
	// conditions,
	// int currentPage, int pageSize, int totalCount) {
	// Page page = new Page();
	// page.setPageSize(pageSize);
	// page.setTotalRowsAmount(totalCount);
	// page.setCurrentPage(currentPage);
	// List<AdPosition> list = this.sqlMapClient.queryForList(
	// "getAdPositionsPage", conditions, page.getPageStartRow() - 1,
	// page.getPageSize());
	// return new PageUtil<AdPosition>(list, page);
	// }
}
