package com.huaixuan.network.biz.service.shop.impl;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.huaixuan.network.biz.dao.shop.FriendLinkDao;
import com.huaixuan.network.biz.domain.base.Constants;
import com.huaixuan.network.biz.domain.shop.FriendLink;
import com.huaixuan.network.biz.exception.ManagerException;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.service.goods.UploadUtil;
import com.huaixuan.network.biz.service.shop.FriendLinkService;
import com.huaixuan.network.common.util.DateUtil;

@Service("friendLinkService")
public class FriendLinkServiceImpl implements FriendLinkService {
	Log log = LogFactory.getLog(this.getClass());
	@Autowired
	public FriendLinkDao friendLinkDao;
	@Autowired
	private UploadUtil uploadUtil;

	@Override
	public Long addFriendLink(FriendLink friendLink, List<MultipartFile> files) {
		log.info("FriendLinkManagerImpl.addFriendLink method");
		try {
			Long id = null;

			String goodsPicPath = "friendlink" + Constants.FILE_SEP
					+ DateUtil.getDateTime("yyyyMM", new Date());
			// String realPathPre = uploadUtil.getRealUpload() +
			// Constants.FILE_SEP
			// + uploadUtil.getUploadRootPath();

			int i = 0;
			if (files != null && files.size() > 0) {
				for (MultipartFile file : files) {
					if (file.getSize() > 0) {
						String fileName = uploadUtil.newUpload(file,
								goodsPicPath);
						if (i == 0) {
							friendLink.setLinkLogo(goodsPicPath
									+ Constants.FILE_SEP + fileName);
						}
						i++;
					}
				}
			}

			int sort = 0;
			Integer max = this.friendLinkDao.getFriendLinkMaxSort(friendLink
					.getShopId());
			if (max != null) {
				sort = max.intValue();
			}
			friendLink.setSort(sort + 1);

			id = this.friendLinkDao.addFriendLink(friendLink);
			return id;
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new ManagerException(e);
		}
	}

	@Override
	public void editFriendLink(FriendLink friendLink, List<MultipartFile> files) {
		log.info("FriendLinkManagerImpl.editFriendLink method");
		try {
			String goodsPicPath = "friendlink" + Constants.FILE_SEP
					+ DateUtil.getDateTime("yyyyMM", new Date());
			// String realPathPre = uploadUtil.getRealUpload() +
			// Constants.FILE_SEP
			// + uploadUtil.getUploadRootPath();
			int i = 0;
			if (files != null && files.size() > 0) {
				for (MultipartFile file : files) {
					if (file.getSize() > 0) {
						String fileName = uploadUtil.newUpload(file,
								goodsPicPath);
						if (i == 0) {
							friendLink.setLinkLogo(goodsPicPath
									+ Constants.FILE_SEP + fileName);
						}
						i++;
					}
				}
			}

			// sort
			FriendLink friendLinknew = this.friendLinkDao
					.getFriendLink(friendLink.getId());
			if (friendLinknew != null) {
				Integer sort = this.friendLinkDao
						.getFriendLinkMaxSort(friendLinknew.getShopId());
				int max = 0;
				if (sort != null) {
					max = sort.intValue();
				}

				if (friendLink.getSort() != friendLinknew.getSort()) {
					if (friendLink.getSort() > max) {
						friendLink.setSort(max);
						this.friendLinkDao.updateFriendLinkSortlow(
								friendLink.getSort(), friendLinknew.getSort());
					} else {
						if (friendLink.getSort() < friendLinknew.getSort()) {
							this.friendLinkDao.updateFriendLinkSortUpdtae(
									friendLink.getSort(),
									friendLinknew.getSort());
						}
						if (friendLink.getSort() > friendLinknew.getSort()) {
							this.friendLinkDao.updateFriendLinkSortlow(
									friendLink.getSort(),
									friendLinknew.getSort());
						}
					}
				}
			}

			this.friendLinkDao.editFriendLink(friendLink);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new ManagerException(e);
		}
	}

	@Override
	public void removeFriendLink(Long friendLinkId) {
		log.info("FriendLinkManagerImpl.removeFriendLink method");
		try {
			FriendLink friendLinknew = this.friendLinkDao
					.getFriendLink(friendLinkId);
			Integer max = this.friendLinkDao.getFriendLinkMaxSort(friendLinknew
					.getShopId());
			if (max != null) {
				if (friendLinknew.getSort() < max) {
					this.friendLinkDao.updateFriendLinkSort(
							friendLinknew.getSort(), friendLinknew.getShopId());
				}
			}
			this.friendLinkDao.removeFriendLink(friendLinkId);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new ManagerException(e);
		}
	}

	@Override
	public FriendLink getFriendLink(Long friendLinkId) {
		log.info("FriendLinkServiceImpl.getFriendLink method");
		try {
			return this.friendLinkDao.getFriendLink(friendLinkId);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new ManagerException(e);
		}
	}

	@Override
	public List<FriendLink> getFriendLinks() {
		log.info("FriendLinkManagerImpl.getFriendLinks method");
		try {
			return this.friendLinkDao.getFriendLinks();
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new ManagerException(e);
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public QueryPage getFriendLinksPage(long shopId, int currPage, int pageSize) {
		try {
			QueryPage queryPage = new QueryPage(shopId);
			Map pramas = queryPage.getParameters();

			Integer count = this.friendLinkDao.getFriendLinksCount(shopId);
			if (count > 0) {

				/* 当前页 */
				queryPage.setCurrentPage(currPage);
				/* 每页总数 */
				queryPage.setPageSize(pageSize);
				queryPage.setTotalItem(count);

				pramas.put("startRow",
						queryPage.getPageFristItem());
				pramas.put("endRow",
						queryPage.getPageLastItem());

				/* 分页查询操作员记录 */
				List<FriendLink> friendLinkList = this.friendLinkDao
						.getFriendLinksPage(pramas);
				if (friendLinkList != null && friendLinkList.size() > 0) {
					queryPage.setItems(friendLinkList);
				}
			}
			return queryPage;
		} catch (Exception e) {
			// log.error(e.getMessage());
			// throw new ManagerException(e);
			return null;
		}

	}

}
