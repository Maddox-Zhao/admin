package com.huaixuan.network.biz.service.shop.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.shop.FeedbackDao;
import com.huaixuan.network.biz.domain.Feedback;
import com.huaixuan.network.biz.domain.hx.Insertcontent;
import com.huaixuan.network.biz.domain.hx.Leaveword;
import com.huaixuan.network.biz.enums.EnumFeedbackType;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.service.shop.FeedbackService;

@Service("feedbackService")
public class FeedbackServiceImpl implements FeedbackService {
	Log log = LogFactory.getLog(this.getClass());
	@Autowired
	public FeedbackDao feedbackDao;

	@Override
	public void addFeedback(Feedback feedbackDao) {
		log.info("FeedbackManagerImpl.addFeedback method");
		try {
			this.feedbackDao.addFeedback(feedbackDao);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}

	@Override
	public void addInsertcontent(Insertcontent insertcontent) {
        log.info("FeedbackManagerImpl.addInsertcontent method");
        try {
             this.feedbackDao.addInsertcontent(insertcontent);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
	
	@Override
	public void editFeedback(Feedback feedback) {
		log.info("FeedbackManagerImpl.editFeedback method");
		try {
			this.feedbackDao.editFeedback(feedback);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}

	@Override
	public void removeFeedback(Long feedbackId) {
		log.info("FeedbackManagerImpl.removeFeedback method");
		try {
			this.feedbackDao.removeFeedback(feedbackId);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}

	@Override
	public Leaveword getFeedback(Long feedbackId) {
		log.info("FeedbackManagerImpl.getFeedback method");
		try {
			return this.feedbackDao.getFeedback(feedbackId);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}

	@Override
	public List<Feedback> getFeedbacks() {
		log.info("FeedbackManagerImpl.getFeedbacks method");
		try {
			return this.feedbackDao.getFeedbacks();
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}

	// public List<Feedback> getFeedbacksByUserId(Long userId) {
	// log.info("FeedbackManagerImpl.getFeedbacksByUserId method");
	//
	// try {
	// return this.feedbackDao.getFeedbackByUserId(userId);
	// } catch (Exception e) {
	// log.error(e.getMessage());
	// }
	// return null;
	// }

	// public List<Feedback> getFeedbacksByUserId(Long userId, Page page) {
	// log.info("FeedbackManagerImpl.getFeedbacksByUserId method");
	//
	// try {
	// return this.feedbackDao.getFeedbackByUserId(userId, page);
	// } catch (Exception e) {
	// log.error(e.getMessage());
	// }
	// return null;
	// }

	// public List<Feedback> getFeedbacksByToUserId(Long toUserId, Page page) {
	// log.info("FeedbackManagerImpl.getFeedbacksByToUserId method");
	//
	// try {
	// return this.feedbackDao.getFeedbackByToUserId(toUserId, page);
	// } catch (Exception e) {
	// log.error(e.getMessage());
	// }
	// return null;
	// }

	 @SuppressWarnings("unchecked")
	public QueryPage getFeedbacksByCondition(Leaveword leaveword, int currPage, int pageSize)throws Exception {
//	 log.info("FeedbackManagerImpl.getFeedbacksByCondition method");
//
//	 try {
//	 return this.feedbackDao.getFeedbacksByCondition(feedback);
//	 } catch (Exception e) {
//	 log.error(e.getMessage());
//	 }
//	 return null;

		 QueryPage queryPage = new QueryPage(leaveword);
			Map pramas = queryPage.getParameters();

			int count = feedbackDao.getFeedbackCountByCondition(pramas);

			if (count > 0) {

				/* 当前页 */
				queryPage.setCurrentPage(currPage);
				/* 每页总数 */
				queryPage.setPageSize(pageSize);
				queryPage.setTotalItem(count);

				pramas.put("startRow", queryPage.getPageFristItem());
				pramas.put("endRow", queryPage.getPageLastItem());

				/* 分页查询操作员记录 */
				List<Feedback> feedbacks = feedbackDao.getFeedbacksByCondition(pramas);
				if (feedbacks != null && feedbacks.size() > 0) {
					queryPage.setItems(feedbacks);
				}
			}
			return queryPage;
	 }

	 public List<Feedback> getFeedbacksByCondition(Feedback feedback ) {
		 log.info("FeedbackManagerImpl.getFeedbacksByCondition method");

		 	 try {
		 	 return this.feedbackDao.getFeedbacksByCondition(feedback);
		 	 } catch (Exception e) {
		 	 log.error(e.getMessage());
		 	 }
		 	 return null;
	 }

	@Override
	public void changeTheTypeNames(Feedback feedback) {
		feedback.setMsgType(getNameByKey(feedback.getMsgType()));
	}

	private String getNameByKey(String code) {
		String typeName = null;
		for (EnumFeedbackType type : EnumFeedbackType.values()) {
			if (type.getKey().equals(code)) {
				typeName = type.getValue();
			}
		}
		return typeName;
	}

	public void replyTheFeedback(Map<String, Object> reply) {
		log.info("FeedbackManagerImpl.replyTheFeedback method");

		try {
			this.feedbackDao.replyFeedbacks(reply);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public int getFeedbackCountByCondition(Feedback feedback) {
		log.info("FeedbackManagerImpl.getFeedbackCountByCondition method");
		QueryPage queryPage = new QueryPage(feedback);
		Map param = queryPage.getParameters();
		try {
			return this.feedbackDao.getFeedbackCountByCondition(param);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return -1;
	}

	@Override
	public int getFeedbacksCountByToUserId(Long userId) {
		log.info("FeedbackManagerImpl.getFeedbacksCountByToUserId method");
		try {
			return this.feedbackDao.getFeedbacksCountByToUserId(userId);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return 0;
	}

	@Override
	public int getFeedbacksCountByUserId(Long userId) {
		log.info("FeedbackManagerImpl.getFeedbacksCountByUserId method");
		try {
			return this.feedbackDao.getFeedbacksCountByUserId(userId);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return 0;
	}
	
    public List<Feedback> feedbackCountWithReplyFlag(Feedback feedback){
        log.info("FeedbackManagerImpl.feedbackCountWithReplyFlag method");
        try{
            return this.feedbackDao.feedbackCountWithReplyFlag(feedback);
        }catch(Exception e){
            log.error(e.getMessage());
        }
        return null;
    }
}
