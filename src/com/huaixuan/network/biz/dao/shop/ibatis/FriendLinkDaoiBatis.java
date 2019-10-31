package com.huaixuan.network.biz.dao.shop.ibatis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.shop.FriendLinkDao;
import com.huaixuan.network.biz.domain.shop.FriendLink;

@Service("friendLinkDao")
public class FriendLinkDaoiBatis implements FriendLinkDao {
	@Autowired
	private SqlMapClientTemplate sqlMapClient;

	@Override
	public Long addFriendLink(FriendLink friendLink) throws Exception {
		Long id = (Long) this.sqlMapClient.insert("addFriendLink", friendLink);
		return id;
	}

	@Override
	public void editFriendLink(FriendLink friendLink) throws Exception {
		this.sqlMapClient.update("editFriendLink", friendLink);
	}

	@Override
	public void removeFriendLink(Long friendLinkId) throws Exception {
		this.sqlMapClient.delete("removeFriendLink", friendLinkId);
	}

	@Override
	public FriendLink getFriendLink(Long friendLinkId) throws Exception {
		return (FriendLink) this.sqlMapClient.queryForObject("getFriendLink",
				friendLinkId);
	}

	@Override
	public List<FriendLink> getFriendLinks() throws Exception {
		return this.sqlMapClient.queryForList("getFriendLinks", null);
	}

	@Override
	public Integer getFriendLinksCount(long shopId) throws Exception {
		return (Integer) this.sqlMapClient.queryForObject(
				"getFriendLinksCount", shopId);
	}

	@Override
	public List<FriendLink> getFriendLinksPage(Map<String, String> conditions) {
		return this.sqlMapClient.queryForList("getFriendLinksPage", conditions);
	}

	@Override
	public Integer getFriendLinkMaxSort(long shopId) throws Exception {

		return (Integer) this.sqlMapClient.queryForObject(
				"getFriendLinkMaxSort", shopId);
	}

	@Override
	public void updateFriendLinkSort(int sort, long shopId) throws Exception {
		Map prama = new HashMap();
		prama.put("sort", sort);
		prama.put("shopId", shopId);
		this.sqlMapClient.update("updateFriendLinkSort", prama);
	}

	@SuppressWarnings("unchecked")
	public List<FriendLink> getFriendLinksByShopId(Long shopId) {
		return this.sqlMapClient.queryForList("getFriendLinksByShopId", shopId);
	}

	public void updateFriendLinkSortUpdtae(int maxid, int maxid2)
			throws Exception {
		Map param = new HashMap();
		param.put("maxid", maxid);
		param.put("maxid2", maxid2);
		this.sqlMapClient.update("updateFriendLinkSortUpdtae", param);

	}

	public void updateFriendLinkSortlow(int maxid, int maxid2) throws Exception {
		Map param = new HashMap();
		param.put("maxid", maxid);
		param.put("maxid2", maxid2);
		this.sqlMapClient.update("updateFriendLinkSortlow", param);
	}
}
