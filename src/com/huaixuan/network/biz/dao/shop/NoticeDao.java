package com.huaixuan.network.biz.dao.shop;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.shop.Notice;

public interface NoticeDao {
	/**
	 *
	 * @author chenhang 2011-2-28
	 * @description 新增公告
	 * @param notice
	 * @throws Exception
	 */
	void addNotice(Notice notice) throws Exception;

	void editNotice(Notice notice) throws Exception;

	/**
	 * @author chenhang 2011-2-28
	 * @description 删除单个公告
	 * @param notice
	 * @throws Exception
	 */
	void removeNotice(Long noticeId) throws Exception;

	/**
	 *
	 * @author chenhang 2011-2-28
	 * @description 根据单个公告ID获取公告
	 * @param noticeId
	 * @return
	 * @throws Exception
	 */
	Notice getNotice(Long noticeId) throws Exception;

	List<Notice> getNotices() throws Exception;

	/**
	 * @author chenhang 2011-3-1
	 * @description 批量删除公告
	 */
	public void deleteNotices(List<Long> ids) throws Exception;

	/**
	 * @author chenhang 2011-2-28
	 * @description 修改公告显示
	 */
	public void isshowNotice(Notice newnotice) throws Exception;

	/**
	 *
	 * @author chenhang 2011-2-28
	 * @description 修改公告
	 * @param notice
	 * @throws Exception
	 */
	public void updateNotice(Notice notice) throws Exception;

	/**
	 * @author chenhang 2011-2-28
	 * @description 根据查询条件获得公告信息
	 * @param shopId
	 * @param currPage
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public Integer getNoticesCount(long shopId) throws Exception;

	/**
	 *
	 * @author chenhang 2011-2-28
	 * @description 获得notice列表
	 * @param pramas
	 * @return
	 * @throws Exception
	 */
	public List<Notice> getNoticesPage(Map<String, String> pramas)
			throws Exception;

	List<Notice> getNoticeInfo() throws Exception;

	 /**
	 * 根据类型取得公告信息
	 * @param type String
	 * @return List
	 * @throws Exception
	 * @author chenyan 2010/04/01
	 */
	 List<Notice> getNoticeInfoByType(String type) throws Exception;

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
//	 List<Notice> getNoticeNewsAndIndustryList(Page page) throws Exception ;

//	 /**
//	 * 取得本网动态的有效数据
//	 * @param type String
//	 * @param page Page
//	 * @return List
//	 * @author chenyan 2010/06/18
//	 * @throws Exception
//	 */
//	 List<Notice> getNoticeNewsList(String type, Page page) throws Exception;

//	 /**
//	 * 根据条件获取不同类型的公告
//	 * @param parMap
//	 * @param page
//	 * @return
//	 * @author zhangwy
//	 */
//	 List<Notice> getNoticeListByCondition(Map parMap, Page page);
}
