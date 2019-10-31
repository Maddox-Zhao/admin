package com.huaixuan.network.biz.dao.shop;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.shop.Notice;

public interface NoticeDao {
	/**
	 *
	 * @author chenhang 2011-2-28
	 * @description ��������
	 * @param notice
	 * @throws Exception
	 */
	void addNotice(Notice notice) throws Exception;

	void editNotice(Notice notice) throws Exception;

	/**
	 * @author chenhang 2011-2-28
	 * @description ɾ����������
	 * @param notice
	 * @throws Exception
	 */
	void removeNotice(Long noticeId) throws Exception;

	/**
	 *
	 * @author chenhang 2011-2-28
	 * @description ���ݵ�������ID��ȡ����
	 * @param noticeId
	 * @return
	 * @throws Exception
	 */
	Notice getNotice(Long noticeId) throws Exception;

	List<Notice> getNotices() throws Exception;

	/**
	 * @author chenhang 2011-3-1
	 * @description ����ɾ������
	 */
	public void deleteNotices(List<Long> ids) throws Exception;

	/**
	 * @author chenhang 2011-2-28
	 * @description �޸Ĺ�����ʾ
	 */
	public void isshowNotice(Notice newnotice) throws Exception;

	/**
	 *
	 * @author chenhang 2011-2-28
	 * @description �޸Ĺ���
	 * @param notice
	 * @throws Exception
	 */
	public void updateNotice(Notice notice) throws Exception;

	/**
	 * @author chenhang 2011-2-28
	 * @description ���ݲ�ѯ������ù�����Ϣ
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
	 * @description ���notice�б�
	 * @param pramas
	 * @return
	 * @throws Exception
	 */
	public List<Notice> getNoticesPage(Map<String, String> pramas)
			throws Exception;

	List<Notice> getNoticeInfo() throws Exception;

	 /**
	 * ��������ȡ�ù�����Ϣ
	 * @param type String
	 * @return List
	 * @throws Exception
	 * @author chenyan 2010/04/01
	 */
	 List<Notice> getNoticeInfoByType(String type) throws Exception;

	 /**
	 * ȡ�ñ�����̬����Ч����
	 * @param type String
	 * @return int
	 * @author chenyan 2010/06/18
	 * @throws Exception
	 */
	 int getNoticeNewsCount(String type) throws Exception;

	 /**
	 * ȡ�ñ�����̬����ҵ���ŵ���Ч����
	 * @param page Page
	 * @return List
	 * @author chenyan 2011/01/10
	 * @throws Exception
	 */
	 int getNoticeNewsAndIndustryCount() throws Exception;

//	 /**
//	 * ȡ�ñ�����̬����ҵ���ŵ���Ч����
//	 * @param page Page
//	 * @return List
//	 * @author chenyan 2011/01/10
//	 * @throws Exception
//	 */
//	 List<Notice> getNoticeNewsAndIndustryList(Page page) throws Exception ;

//	 /**
//	 * ȡ�ñ�����̬����Ч����
//	 * @param type String
//	 * @param page Page
//	 * @return List
//	 * @author chenyan 2010/06/18
//	 * @throws Exception
//	 */
//	 List<Notice> getNoticeNewsList(String type, Page page) throws Exception;

//	 /**
//	 * ����������ȡ��ͬ���͵Ĺ���
//	 * @param parMap
//	 * @param page
//	 * @return
//	 * @author zhangwy
//	 */
//	 List<Notice> getNoticeListByCondition(Map parMap, Page page);
}
