package com.huaixuan.network.biz.dao.shop;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.Feedback;
import com.huaixuan.network.biz.domain.hx.Insertcontent;
import com.huaixuan.network.biz.domain.hx.Leaveword;

/**
 * ï¿½ï¿½ï¿½ï¿½ï¿½Ô¶ï¿½ï¿½ï¿½ï¿(bibleUtil auto code generation)
 *
 * @version 3.2.0
 */
public interface FeedbackDao {
	/* @interface model: ï¿½ï¿½ï¿½Ò»ï¿½ï¿½Feedbackï¿½ï¿½Â¼ */
	void addFeedback(Feedback feedback) throws Exception;

	/* @interface model: ï¿½ï¿½ï¿½ï¿½Ò»ï¿½ï¿½Feedbackï¿½ï¿½Â¼ */
	void editFeedback(Feedback feedback) throws Exception;
	
	void addInsertcontent(Insertcontent insertcontent)throws Exception;

	/* @interface model: É¾ï¿½ï¿½Ò»ï¿½ï¿½Feedbackï¿½ï¿½Â¼ */
	void removeFeedback(Long feedbackId) throws Exception;

	/* @interface model: ï¿½ï¿½Ñ¯Ò»ï¿½ï¿½Feedbackï¿½ï¿½ï¿,ï¿½ï¿½ï¿½ï¿½Feedbackï¿½ï¿½ï¿½ï¿½ */
	Leaveword getFeedback(Long feedbackId) throws Exception;

	/* @interface model: ï¿½ï¿½Ñ¯Ò»ï¿½ï¿½Feedbackï¿½ï¿½ï¿,ï¿½ï¿½ï¿½ï¿½Feedbackï¿½ï¿½ï¿½ï¿½ */
//	List<Feedback> getFeedbackByUserId(Long userId) throws Exception;

	/* @interface model: ï¿½ï¿½Ñ¯ï¿½ï¿½ï¿½ï¿½Feedbackï¿½ï¿½ï¿,ï¿½ï¿½ï¿½ï¿½Feedbackï¿½ï¿½ï¿½ï¿½Ä¼ï¿½ï¿½ï¿ */
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
     * ¸ù¾Ý»Ø¸´×´Ì¬·ÖÀàÍ³¼Æ·´À¡ÊýÁ¿
     * @param feedback Feedback
     * @return List
     * @author chenyan 2011/04/19
     */
    List<Feedback> feedbackCountWithReplyFlag(Feedback feedback);

}
