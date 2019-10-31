package com.huaixuan.network.biz.dao.shop;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.Feedback;
import com.huaixuan.network.biz.domain.hx.Insertcontent;
import com.huaixuan.network.biz.domain.hx.Leaveword;

/**
 * �����Զ����(bibleUtil auto code generation)
 *
 * @version 3.2.0
 */
public interface FeedbackDao {
	/* @interface model: ���һ��Feedback��¼ */
	void addFeedback(Feedback feedback) throws Exception;

	/* @interface model: ����һ��Feedback��¼ */
	void editFeedback(Feedback feedback) throws Exception;
	
	void addInsertcontent(Insertcontent insertcontent)throws Exception;

	/* @interface model: ɾ��һ��Feedback��¼ */
	void removeFeedback(Long feedbackId) throws Exception;

	/* @interface model: ��ѯһ��Feedback���,����Feedback���� */
	Leaveword getFeedback(Long feedbackId) throws Exception;

	/* @interface model: ��ѯһ��Feedback���,����Feedback���� */
//	List<Feedback> getFeedbackByUserId(Long userId) throws Exception;

	/* @interface model: ��ѯ����Feedback���,����Feedback����ļ��� */
	List<Feedback> getFeedbacks() throws Exception;

	// List<Feedback> getFeedbackByUserId(Long userId, Page page) throws
	// Exception;

	// List<Feedback> getFeedbackByToUserId(Long toUserId, Page page)
	// throws Exception;

	List<Feedback> getFeedbacksByCondition(Map map) throws Exception;

	 List<Feedback> getFeedbacksByCondition(Feedback feedback) throws Exception;

	void replyFeedbacks(Map<String, Object> reply) throws Exception;

	int getFeedbackCountByCondition(Map map) throws Exception;

	int getFeedbacksCountByToUserId(Long userId);

	int getFeedbacksCountByUserId(Long userId);
	
    /**
     * ���ݻظ�״̬����ͳ�Ʒ�������
     * @param feedback Feedback
     * @return List
     * @author chenyan 2011/04/19
     */
    List<Feedback> feedbackCountWithReplyFlag(Feedback feedback);

}
