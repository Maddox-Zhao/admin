package com.huaixuan.network.biz.service.shop;

import java.io.File;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.huaixuan.network.biz.domain.shop.FriendLink;
import com.huaixuan.network.biz.query.QueryPage;

public interface FriendLinkService {
	public Long addFriendLink(FriendLink friendLink,List<MultipartFile> files);

	public void editFriendLink(FriendLink friendLink, List<MultipartFile> files);

	public void removeFriendLink(Long friendLinkId);

	public FriendLink getFriendLink(Long friendLinkId);

	public List<FriendLink> getFriendLinks();

	/**
	 *
	 * @author chenhang 2011-3-8
	 * @description 友情链接列表页面
	 * @param shopId
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	public QueryPage getFriendLinksPage(long shopId, int currentPage,
			int pageSize);
}
