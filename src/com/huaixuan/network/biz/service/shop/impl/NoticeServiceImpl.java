package com.huaixuan.network.biz.service.shop.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.shop.NoticeDao;
import com.huaixuan.network.biz.domain.shop.Notice;
import com.huaixuan.network.biz.exception.ManagerException;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.service.shop.NoticeService;

@Service("noticeService")
public class NoticeServiceImpl implements NoticeService {
	Log log = LogFactory.getLog(this.getClass());

	@Autowired
	public NoticeDao noticeDao;

	@SuppressWarnings("unchecked")
	@Override
	public QueryPage getByMapByConditionWithPage(long shopId, int currPage,
			int pageSize) throws Exception {
		// log.info("NoticeManagerImpl.addNotice method");
		try {
			QueryPage queryPage = new QueryPage(shopId);
			Map pramas = queryPage.getParameters();

			int count = noticeDao.getNoticesCount(shopId);

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
				List<Notice> noticeList = noticeDao.getNoticesPage(pramas);
				if (noticeList != null && noticeList.size() > 0) {
					queryPage.setItems(noticeList);
				}
			}
			return queryPage;
		} catch (Exception e) {
			// log.error(e.getMessage());
			// throw new ManagerException(e);
			return null;
		}
	}

	@Override
	public void addNotice(Notice noticeDao) {
		log.info("NoticeManagerImpl.addNotice method");
		try {
			this.noticeDao.addNotice(noticeDao);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new ManagerException(e);
		}
	}

	@Override
	public void editNotice(Notice notice) {
		log.info("NoticeManagerImpl.editNotice method");
		try {
			this.noticeDao.editNotice(notice);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new ManagerException(e);
		}
	}

	@Override
	public void removeNotice(Long noticeId) {
		log.info("NoticeServiceImpl.removeNotice method");
		try {
			this.noticeDao.removeNotice(noticeId);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new ManagerException(e);
		}
	}

	//
	@Override
	public Notice getNotice(Long noticeId) {
		log.info("NoticeManagerImpl.getNotice method");
		try {
			return this.noticeDao.getNotice(noticeId);
		} catch (Exception e) {
			log.error(e.getMessage());
			// throw new ManagerException(e);
			return null;
		}
	}

	@Override
	public List<Notice> getNotices() {
		log.info("NoticeManagerImpl.getNotices method");
		try {
			return this.noticeDao.getNotices();
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new ManagerException(e);
		}
	}

	@Override
	public void deleteNotices(List<Long> ids) {
		// log.info("NoticeManagerImpl.getNotices method");
		try {
			this.noticeDao.deleteNotices(ids);
		} catch (Exception e) {
			// log.error(e.getMessage());
			// throw new ManagerException(e);
		}
	}

	@Override
	public void isshowNotice(Notice newnotice) {
		// log.info("NoticeManagerImpl.getNotices method");
		try {
			this.noticeDao.isshowNotice(newnotice);
		} catch (Exception e) {
			// log.error(e.getMessage());
			// throw new ManagerException(e);
		}
	}

	@Override
	public void updateNotice(Notice notice) {
		// log.info("NoticeManagerImpl.getNotices method");
		try {
			this.noticeDao.updateNotice(notice);
		} catch (Exception e) {
			// log.error(e.getMessage());
			// throw new ManagerException(e);
		}
	}

	//
	// public PageUtil<Notice> getNoticesPage(long shopId,int currentPage, int
	// pageSize){
	// try {
	// Integer count = this.noticeDao.getNoticesCount(shopId);
	// Map prama = new HashMap();
	// prama.put("shopId", shopId);
	// return this.noticeDao.getNoticesPage(prama, currentPage,
	// pageSize, count.intValue());
	// } catch (Exception e) {
	// log.error(e.getMessage());
	// throw new ManagerException(e);
	// }
	//
	// }
	//
	/**
	 * 根据类型取得公告信息
	 *
	 * @param type
	 *            String
	 * @return List
	 * @throws Exception
	 * @author chenyan 2010/04/01
	 */
	@Override
	public List<Notice> getNoticeInfoByType(String type) throws Exception {
		log.info("NoticeManagerImpl.getNoticeInfoByType method");
		try {
			return this.noticeDao.getNoticeInfoByType(type);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new ManagerException(e);
		}
	}

	/**
	 * 取得本网动态的有效条数
	 *
	 * @param type
	 *            String
	 * @return int
	 * @author chenyan 2010/06/18
	 * @throws Exception
	 */
	@Override
	public int getNoticeNewsCount(String type) throws Exception {
		log.info("NoticeManagerImpl.getNoticeNewsCount method");
		try {
			return this.noticeDao.getNoticeNewsCount(type);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new ManagerException(e);
		}
	}

	//
	// /**
	// * 取得本网动态的有效数据
	// * @param type String
	// * @param page Page
	// * @return List
	// * @author chenyan 2010/06/18
	// * @throws Exception
	// */
	// public List<Notice> getNoticeNewsList(String type, Page page) throws
	// Exception {
	// log.info("NoticeManagerImpl.getNoticeNewsList method");
	// try {
	// return this.noticeDao.getNoticeNewsList(type, page);
	// } catch (Exception e) {
	// log.error(e.getMessage());
	// throw new ManagerException(e);
	// }
	// }
	//
	/**
	 * 取得本网动态和行业新闻的有效条数
	 *
	 * @param page
	 *            Page
	 * @return List
	 * @author chenyan 2011/01/10
	 * @throws Exception
	 */
	@Override
	public int getNoticeNewsAndIndustryCount() throws Exception {
		log.info("NoticeManagerImpl.getNoticeNewsAndIndustryCount method");
		try {
			return this.noticeDao.getNoticeNewsAndIndustryCount();
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new ManagerException(e);
		}
	}
	//
	// /**
	// * 取得本网动态和行业新闻的有效数据
	// * @param page Page
	// * @return List
	// * @author chenyan 2011/01/10
	// * @throws Exception
	// */
	// public List<Notice> getNoticeNewsAndIndustryList(Page page) throws
	// Exception {
	// log.info("NoticeManagerImpl.getNoticeNewsAndIndustryList method");
	// try {
	// return this.noticeDao.getNoticeNewsAndIndustryList(page);
	// } catch (Exception e) {
	// log.error(e.getMessage());
	// throw new ManagerException(e);
	// }
	// }
	//
	// /**
	// * 根据条件获取不同类型的公告
	// * @param parMap
	// * @param page
	// * @return
	// * @author zhangwy
	// */
	// public List<Notice> getNoticeListByCondition(Map parMap, Page page) {
	// return this.noticeDao.getNoticeListByCondition(parMap,page);
	// }
}
