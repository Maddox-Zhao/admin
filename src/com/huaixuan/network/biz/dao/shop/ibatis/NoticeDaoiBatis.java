package com.huaixuan.network.biz.dao.shop.ibatis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.shop.NoticeDao;
import com.huaixuan.network.biz.domain.shop.Notice;

@Service("noticeDao")
public class NoticeDaoiBatis implements NoticeDao {
	@Autowired
	private SqlMapClientTemplate sqlMapClient;

	@Override
	public void addNotice(Notice notice) throws Exception {
		this.sqlMapClient.insert("addNotice", notice);
	}

	@Override
	public void editNotice(Notice notice) throws Exception {
		this.sqlMapClient.update("editNotice", notice);
	}

	@Override
	public void removeNotice(Long noticeId) throws Exception {
		this.sqlMapClient.update("removeNotice", noticeId);
	}

	@Override
	public Notice getNotice(Long noticeId) throws Exception {
		return (Notice) this.sqlMapClient.queryForObject("getNotice", noticeId);
	}

	@Override
	public List<Notice> getNotices() throws Exception {
		return this.sqlMapClient.queryForList("getNotices", null);
	}

	@Override
	public void deleteNotices(List<Long> ids) throws Exception {
		Map param = new HashMap();
		param.put("ids", ids);
		this.sqlMapClient.update("deleteNotices", param);
	}

	@Override
	public void isshowNotice(Notice newnotice) throws Exception {
		this.sqlMapClient.update("isshowNotice", newnotice);
	}

	@Override
	public void updateNotice(Notice notice) throws Exception {
		this.sqlMapClient.update("updateNotice", notice);
	}

	@Override
	public Integer getNoticesCount(long shopId) throws Exception {
		return (Integer) this.sqlMapClient.queryForObject("getNoticesCount",
				shopId);
	}

	@Override
	public List<Notice> getNoticesPage(Map<String, String> pramas)
			throws Exception {
		return this.sqlMapClient.queryForList("getNoticesPage", pramas);
	}

	@Override
	public List<Notice> getNoticeInfo() throws Exception {
		return this.sqlMapClient.queryForList("getNoticesInfo");
	}

	/**
	 * 根据类型取得公告信息
	 *
	 * @param type
	 *            String
	 * @return List
	 * @throws Exception
	 * @author chenyan 2010/04/01
	 */
	public List<Notice> getNoticeInfoByType(String type) throws Exception {
		return this.sqlMapClient.queryForList("getNoticesInfoByType", type);
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
	public int getNoticeNewsCount(String type) throws Exception {
		return (Integer) this.sqlMapClient.queryForObject("getNoticeNewsCount",
				type);
	}

//	/**
//	 * 取得本网动态的有效数据
//	 *
//	 * @param type
//	 *            String
//	 * @param page
//	 *            Page
//	 * @return List
//	 * @author chenyan 2010/06/18
//	 * @throws Exception
//	 */
//	@SuppressWarnings("unchecked")
//	public List<Notice> getNoticeNewsList(String type, Page page)
//			throws Exception {
//		Map map = new HashMap();
//		map.put("type", type);
//		if (page == null) {
//			return this.sqlMapClient.queryForList(
//					"getNoticeNewsList", map);
//		} else {
//			return this.findQueryPage("getNoticeNewsList", map, page);
//		}
//	}

	/**
	 * 取得本网动态和行业新闻的有效条数
	 *
	 * @param page
	 *            Page
	 * @return List
	 * @author chenyan 2011/01/10
	 * @throws Exception
	 */
	public int getNoticeNewsAndIndustryCount() throws Exception {
		return (Integer) this.sqlMapClient
				.queryForObject("getNoticeNewsAndIndustryCount");
	}

//	/**
//	 * 取得本网动态和行业新闻的有效数据
//	 *
//	 * @param page
//	 *            Page
//	 * @return List
//	 * @author chenyan 2011/01/10
//	 * @throws Exception
//	 */
//	public List<Notice> getNoticeNewsAndIndustryList(Page page)
//			throws Exception {
//		Map map = new HashMap();
//		if (page == null) {
//			return this.sqlMapClient
//					.queryForList("getNoticeNewsAndIndustryList");
//		} else {
//			return this
//					.findQueryPage("getNoticeNewsAndIndustryList", map, page);
//		}
//	}

//	/**
//	 * 根据条件获取不同类型的公告
//	 *
//	 * @param parMap
//	 * @param page
//	 * @return
//	 * @author zhangwy
//	 */
//	public List<Notice> getNoticeListByCondition(Map parMap, Page page) {
//		if (page == null) {
//			return this.sqlMapClient.queryForList("getNoticeListByCondition",
//					parMap);
//		} else {
//			return this.findQueryPage("getNoticeListByCondition",
//					"getNoticeListByConditionCount", parMap, page);
//		}
//	}
}
