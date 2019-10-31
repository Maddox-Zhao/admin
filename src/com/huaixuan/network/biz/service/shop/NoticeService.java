package com.huaixuan.network.biz.service.shop;

import java.util.List;

import com.huaixuan.network.biz.domain.shop.Notice;
import com.huaixuan.network.biz.query.QueryPage;

public interface NoticeService {
	/**
	 *
	 * @author chenhang 2011-2-28
	 * @description 根据查询条件获得公告信息
	 * @param shopId
	 * @param currPage
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public QueryPage getByMapByConditionWithPage(long shopId, int currPage,
			int pageSize) throws Exception;

	/**
	 *
	 * @author chenhang 2011-2-28
	 * @description 新增公告
	 * @param notice
	 */
	public void addNotice(Notice notice);

	 /* @interface model: 更新公告*/
	 public void editNotice(Notice notice);

	/**
	 * @author chenhang 2011-2-28
	 * @description 删除公告
	 * @param noticeId
	 */
	public void removeNotice(Long noticeId);

	/**
	 * @author chenhang 2011-2-28
	 * @description 根据公告ID获得公告
	 */
	public Notice getNotice(Long noticeId);


	 /* @interface model: 获取所有公告 */
	 public List<Notice> getNotices();

	/**
	 * @author chenhang 2011-3-1
	 * @description 批量删除公告
	 */
	public void deleteNotices(List<Long> ids);

	/**
	 * @author chenhang 2011-2-28
	 * @description 修改公告显示
	 */
	public void isshowNotice(Notice newnotice);

	/**
	 *
	 * @author chenhang 2011-2-28
	 * @description 修改公告
	 * @param notice
	 */
	public void updateNotice(Notice notice);

//	 /* @interface model:获取公告分页列表 */
//	 public PageUtil<Notice> getNoticesPage(long shopId,int currentPage, int
//	 pageSize);

	 /**
	 * 根据类型取得公告信息
	 * @param type String
	 * @return List
	 * @throws Exception
	 * @author chenyan 2010/06/18
	 */
	 public List<Notice> getNoticeInfoByType(String type) throws Exception;

	 /**
	 * 取得本网动态的有效条数
	 * @param type String
	 * @return int
	 * @author chenyan 2010/06/18
	 * @throws Exception
	 */
	 int getNoticeNewsCount(String type) throws Exception;

	 /**
	 * 取得本网动态和行业新闻的有效条数
	 * @param page Page
	 * @return List
	 * @author chenyan 2011/01/10
	 * @throws Exception
	 */
	 int getNoticeNewsAndIndustryCount() throws Exception;

//	 /**
//	 * 取得本网动态和行业新闻的有效数据
//	 * @param page Page
//	 * @return List
//	 * @author chenyan 2011/01/10
//	 * @throws Exception
//	 */
//	 List<Notice> getNoticeNewsAndIndustryList(Page page) throws Exception;


	// /**
	// * 取得本网动态的有效数据
	// * @param type String
	// * @param page Page
	// * @return List
	// * @author chenyan 2010/06/18
	// * @throws Exception
	// */
	// List<Notice> getNoticeNewsList(String type, Page page) throws Exception;

	 /**
	 * 根据条件获取不同类型的公告
	 * @param parMap
	 * @param page
	 * @return
	 * @author zhangwy
	 */
	// List<Notice> getNoticeListByCondition(Map parMap, Page page);
}
