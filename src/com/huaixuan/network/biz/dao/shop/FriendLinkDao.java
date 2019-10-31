package com.huaixuan.network.biz.dao.shop;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.shop.FriendLink;

public interface FriendLinkDao {
	Long addFriendLink(FriendLink friendLink) throws Exception;

	void editFriendLink(FriendLink friendLink) throws Exception;

	void removeFriendLink(Long friendLinkId) throws Exception;

	FriendLink getFriendLink(Long friendLinkId) throws Exception;

	List<FriendLink> getFriendLinks() throws Exception;

	public Integer getFriendLinksCount(long shopId) throws Exception;

	List<FriendLink> getFriendLinksPage(Map<String, String> conditions);

	Integer getFriendLinkMaxSort(long shopId) throws Exception;

	void updateFriendLinkSort(int sort, long shopId) throws Exception;

	List<FriendLink> getFriendLinksByShopId(Long shopId);

	void updateFriendLinkSortUpdtae(int maxid, int maxid2) throws Exception;

	void updateFriendLinkSortlow(int maxid, int maxid2) throws Exception;

}
