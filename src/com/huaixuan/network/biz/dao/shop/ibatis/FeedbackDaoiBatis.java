package com.huaixuan.network.biz.dao.shop.ibatis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.huaixuan.network.biz.dao.shop.FeedbackDao;
import com.huaixuan.network.biz.domain.Feedback;
import com.huaixuan.network.biz.domain.hx.Insertcontent;
import com.huaixuan.network.biz.domain.hx.Leaveword;

/**
 *
 * @version 3.2.0
 */
@Repository("feedbackDao")
public class FeedbackDaoiBatis implements FeedbackDao {
	@Autowired
	private SqlMapClientTemplate sqlMapClient;

	@Override
	public void addFeedback(Feedback feedback) throws Exception {
		this.sqlMapClient.insert("addFeedback", feedback);
	}

	@Override
	public void addInsertcontent(Insertcontent insertcontent)throws Exception{
		this.sqlMapClient.insert("addInsertcontent", insertcontent);
	}
	
	@Override
	public void editFeedback(Feedback feedback) throws Exception {
		this.sqlMapClient.update("editFeedback", feedback);
	}

	@Override
	public void removeFeedback(Long feedbackId) throws Exception {
		this.sqlMapClient.delete("removeFeedback", feedbackId);
	}

	@Override
	public Leaveword getFeedback(Long feedbackId) throws Exception {
		return (Leaveword) this.sqlMapClient.queryForObject("getFeedback",
				feedbackId);
	}

	@Override
	public List<Feedback> getFeedbacks() throws Exception {
		return this.sqlMapClient.queryForList("getFeedbacks", null);
	}

	
	// public List<Feedback> getFeedbackByUserId(Long userId) throws Exception {
	// Map map = new HashMap();
	// map.put("value", userId);
	// return this.findQueryPage("getFeedbackByUserId", map, null);
	// }

	// public List<Feedback> getFeedbackByUserId(Long userId, Page page)
	// throws Exception {
	// Map map = new HashMap();
	// map.put("value", userId);
	// return this.findQueryPage("getFeedbackByUserId",
	// "getFeedbacksCountByUserId", map, page);
	// }

	// public List<Feedback> getFeedbackByToUserId(Long toUserId) throws
	// Exception {
	// Map map = new HashMap();
	// map.put("value", toUserId);
	// return this.findQueryPage("getFeedbackByToUserId", map, null);
	// }

	// public List<Feedback> getFeedbackByToUserId(Long toUserId, Page page)
	// throws Exception {
	// Map map = new HashMap();
	// map.put("userId", toUserId);
	// return this.findQueryPage("getFeedbackByToUserId",
	// "getFeedbacksCountByToUserId", map, page);
	// }
	@Override
	public int getFeedbacksCountByToUserId(Long userId) {
		Map map = new HashMap();
		map.put("userId", userId);
		return (Integer) this.sqlMapClient.queryForObject(
				"getFeedbacksCountByToUserId", map);
	}

	@Override
	public int getFeedbacksCountByUserId(Long userId) {
		Map map = new HashMap();
		map.put("userId", userId);
		return (Integer) this.sqlMapClient.queryForObject(
				"getFeedbacksCountByUserId", map);
	}

	 public List<Feedback> getFeedbacksByCondition(Map map)
	 throws Exception {
//	 Map map = new HashMap();
//	 map.put("userId", feedback.getUserId());
//	 map.put("userNickname", feedback.getUserNickname());
//	 map.put("toUserNick", feedback.getToUserNick());
//	 map.put("toUserType", feedback.getToUserType());
//	 map.put("toUserId", feedback.getToUserId());
//	 map.put("msgType", feedback.getMsgType());
//	 map.put("gmtCreateStart", feedback.getGmtCreateStart());
//	 map.put("gmtCreateEnd", feedback.getGmtCreateEnd());
//	 map.put("replyFlag", feedback.getReplyFlag());
	 return this.sqlMapClient.queryForList("getFeedbacksByCondition", map);
	 }

	 public List<Feedback> getFeedbacksByCondition(Feedback feedback )
	 throws Exception {
	 Map map = new HashMap();
	 map.put("userId", feedback.getUserId());
	 map.put("userNickname", feedback.getUserNickname());
	 map.put("toUserNick", feedback.getToUserNick());
	 map.put("toUserType", feedback.getToUserType());
	 map.put("toUserId", feedback.getToUserId());
	 map.put("msgType", feedback.getMsgType());
	 map.put("gmtCreateStart", feedback.getGmtCreateStart());
	 map.put("gmtCreateEnd", feedback.getGmtCreateEnd());
	 map.put("replyFlag", feedback.getReplyFlag());
	 return sqlMapClient.queryForList("getFeedbacksByCondition", map);
	 }
	@Override
	public void replyFeedbacks(Map<String, Object> reply) throws Exception {
		this.sqlMapClient.queryForList("replyFeedback", reply);
	}

	@Override
	public int getFeedbackCountByCondition(Map map) throws Exception {
		return (Integer) this.sqlMapClient.queryForObject(
				"getFeedbackCountByCondition", map);
	}
	
	@Override
	public List<Feedback> feedbackCountWithReplyFlag(Feedback feedback) {
        Map map = new HashMap();
        map.put("toUserType", feedback.getToUserType());
        return this.sqlMapClient.queryForList("feedbackCountWithReplyFlag", map);
    }
}
