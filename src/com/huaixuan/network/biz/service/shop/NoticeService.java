package com.huaixuan.network.biz.service.shop;

import java.util.List;

import com.huaixuan.network.biz.domain.shop.Notice;
import com.huaixuan.network.biz.query.QueryPage;

public interface NoticeService {
	/**
	 *
	 * @author chenhang 2011-2-28
	 * @description ���ݲ�ѯ������ù�����Ϣ
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
	 * @description ��������
	 * @param notice
	 */
	public void addNotice(Notice notice);

	 /* @interface model: ���¹���*/
	 public void editNotice(Notice notice);

	/**
	 * @author chenhang 2011-2-28
	 * @description ɾ������
	 * @param noticeId
	 */
	public void removeNotice(Long noticeId);

	/**
	 * @author chenhang 2011-2-28
	 * @description ���ݹ���ID��ù���
	 */
	public Notice getNotice(Long noticeId);


	 /* @interface model: ��ȡ���й��� */
	 public List<Notice> getNotices();

	/**
	 * @author chenhang 2011-3-1
	 * @description ����ɾ������
	 */
	public void deleteNotices(List<Long> ids);

	/**
	 * @author chenhang 2011-2-28
	 * @description �޸Ĺ�����ʾ
	 */
	public void isshowNotice(Notice newnotice);

	/**
	 *
	 * @author chenhang 2011-2-28
	 * @description �޸Ĺ���
	 * @param notice
	 */
	public void updateNotice(Notice notice);

//	 /* @interface model:��ȡ�����ҳ�б� */
//	 public PageUtil<Notice> getNoticesPage(long shopId,int currentPage, int
//	 pageSize);

	 /**
	 * ��������ȡ�ù�����Ϣ
	 * @param type String
	 * @return List
	 * @throws Exception
	 * @author chenyan 2010/06/18
	 */
	 public List<Notice> getNoticeInfoByType(String type) throws Exception;

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
//	 List<Notice> getNoticeNewsAndIndustryList(Page page) throws Exception;


	// /**
	// * ȡ�ñ�����̬����Ч����
	// * @param type String
	// * @param page Page
	// * @return List
	// * @author chenyan 2010/06/18
	// * @throws Exception
	// */
	// List<Notice> getNoticeNewsList(String type, Page page) throws Exception;

	 /**
	 * ����������ȡ��ͬ���͵Ĺ���
	 * @param parMap
	 * @param page
	 * @return
	 * @author zhangwy
	 */
	// List<Notice> getNoticeListByCondition(Map parMap, Page page);
}
