package com.huaixuan.network.biz.service.shop;

import java.util.List;

import com.huaixuan.network.biz.domain.Feedback;
import com.huaixuan.network.biz.domain.hx.Insertcontent;
import com.huaixuan.network.biz.domain.hx.Leaveword;
import com.huaixuan.network.biz.query.QueryPage;

/**
 * ï¿½ï¿½ï¿½ï¿½ï¿½Ô¶ï¿½ï¿½ï¿½ï¿(bibleUtil auto code generation)
 *
 * @version 3.2.0
 */
public interface FeedbackService {
	
	public void addFeedback(Feedback feedback);

	public void editFeedback(Feedback feedback);

	public void removeFeedback(Long feedbackId);
	
	public void addInsertcontent(Insertcontent insertcontent);

	public Leaveword getFeedback(Long feedbackId);

	public List<Feedback> getFeedbacks();

//	public List<Feedback> getFeedbacksByUserId(Long userId);

	// public List<Feedback> getFeedbacksByUserId(Long userId, Page page);

	// search the messages the user receives in page.
	// public List<Feedback> getFeedbacksByToUserId(Long toUserId, Page page);

 	public List<Feedback> getFeedbacksByCondition(Feedback feedback);

	// search the messages by condition
	public QueryPage getFeedbacksByCondition(Leaveword leaveword,int currPage, int pageSize)throws Exception;

	// search the messages by condition in page
	// public List<Feedback> getFeedbacksByCondition(Feedback feedback, Page
	// page);

	// change the names of types saved in database to the names showed in pages.
	public void changeTheTypeNames(Feedback feedback);

	public int getFeedbackCountByCondition(Feedback feedback);

	public int getFeedbacksCountByToUserId(Long userId);

	public int getFeedbacksCountByUserId(Long userId);
	
    /**
     * ¸ù¾Ý»Ø¸´×´Ì¬·ÖÀàÍ³¼Æ·´À¡ÊýÁ¿
     * @param feedback Feedback
     * @return List
     * @author chenyan 2011/04/19
     */
    List<Feedback> feedbackCountWithReplyFlag(Feedback feedback);

}
