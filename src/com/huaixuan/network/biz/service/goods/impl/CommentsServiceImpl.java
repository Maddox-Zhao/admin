/**
 * @Title: CommentsServiceImpl.java
 * @Package com.huaixuan.network.biz.service.impl
 * @Description: TODO
 * @author liuwd weida-liu@foxmail.com
 * @date 2011-3-4 ����05:18:02
 * @version V1.0
 */
package com.huaixuan.network.biz.service.goods.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.goods.GoodsDao;
import com.huaixuan.network.biz.dao.user.CommentsDao;
import com.huaixuan.network.biz.domain.Comments;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.service.goods.CommentsService;

/**
 * @ClassName: CommentsServiceImpl
 * @Description: TODO
 * @author liuwd weida-liu@foxmail.com
 * @date 2011-3-4 ����05:18:02
 *
 */
@Service("commentsService")
public class CommentsServiceImpl implements CommentsService {

    Log log = LogFactory.getLog(this.getClass());
	@Autowired
	CommentsDao commentsDao;

	@Autowired
	GoodsDao goodsDao;

	/* @model: */
	public void addComments(Comments commentsDao) {
		// log.info("CommentsManagerImpl.addComments method");
		try {
			this.commentsDao.addComments(commentsDao);
		} catch (Exception e) {
			// log.error(e.getMessage());
		}
	}

	/* @model: */
	public void editComments(Comments comments) {
		// log.info("CommentsManagerImpl.editComments method");
		try {
			this.commentsDao.editComments(comments);
		} catch (Exception e) {
			// log.error(e.getMessage());
		}
	}

	/* @model: */
	public void removeComments(Long commentsId) {
		// log.info("CommentsManagerImpl.removeComments method");
		try {
			this.commentsDao.removeComments(commentsId);
		} catch (Exception e) {
			// log.error(e.getMessage());
		}
	}

	/* @model: */
	public Comments getComments(Long commentsId) {
		// log.info("CommentsManagerImpl.getComments method");
		try {
			return this.commentsDao.getComments(commentsId);
		} catch (Exception e) {
			// log.error(e.getMessage());
		}
		return null;
	}

	/* @model: */
	public List<Comments> getCommentss() {
		// log.info("CommentsManagerImpl.getCommentss method");
		try {
			return this.commentsDao.getCommentss();
		} catch (Exception e) {
			// log.error(e.getMessage());
		}
		return null;
	}

	public void setGoodsNameCommented(Comments comment) {
		// log.info("CommentsManagerImpl.getGoodsNameCommented method");

		String goodsName = null;
		try {
			goodsName = goodsDao.getGoods(comment.getIdValue()).getTitle();
		} catch (Exception e) {
			// log.error(e.getMessage());
		}
		comment.setGoodsName(goodsName);
	}

	/**
	 * ��ѯ���ϲ�����ParameterMapҪ���Comments�����1??7
	 *
	 * @param parameterMap
	 *            ������1??7
	 * @return ���ϲ�����ParameterMapҪ���Comments�����1??7
	 */
	@SuppressWarnings("unchecked")
	public List<Comments> getCommentsByParameterMap(Map parameterMap) {
		return this.commentsDao.getCommentsByParameterMap(parameterMap);
	}

	/**
	 * ��ѯĳ����Ʒ����������1??7
	 *
	 * @param goodsId
	 * @return ����ĳ����Ʒ�ı�����Ա��׼��ʾ�������б�
	 * @see com.hundsun.bible.facade.user.CommentsManager#getCommentsByGoodsId(java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	public List<Comments> getCommentsByGoodsId(Long goodsId) {
		Map parameterMap = new HashMap();
		parameterMap.put("idValue", goodsId);
		parameterMap.put("status", 1);
		return getCommentsByParameterMap(parameterMap);
	}

	/**
	 * ��ѯĳ����Ʒ����������1??7
	 *
	 * @param goodsId
	 * @return ����ĳ����Ʒ����������1??7
	 * @see com.hundsun.bible.facade.user.CommentsManager#getAllCommentsByGoodsId(java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	public List<Comments> getAllCommentsByGoodsId(Long goodsId) {
		Map parameterMap = new HashMap();
		parameterMap.put("idValue", goodsId);
		return getCommentsByParameterMap(parameterMap);
	}

	public List<Comments> getCommentsByCondition(Comments comment) {
		// log.info("CommentsManagerImpl.getCommentsByCondition method");

		try {
			return this.commentsDao.getCommentsByCondition(comment);
		} catch (Exception e) {
			// log.error(e.getMessage());
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public QueryPage getCommentsByCondition(Comments comment, int currPage, int pageSize)throws Exception{
		// log.info("CommentsManagerImpl.getCommentsByCondition method");
//		try {
//			return commentsDao.getCommentsByCondition(map);
//		} catch (Exception e) {
//			// log.error(e.getMessage());
//		}
//		return null;


		QueryPage queryPage = new QueryPage(comment);
		Map pramas = queryPage.getParameters();

		int count = commentsDao.getCommentsCountByCondition(pramas);

		if (count > 0) {

			/* ��ǰҳ */
			queryPage.setCurrentPage(currPage);
			/* ÿҳ���� */
			queryPage.setPageSize(pageSize);
			queryPage.setTotalItem(count);

			pramas.put("startRow", queryPage.getPageFristItem());
			pramas.put("endRow", queryPage.getPageLastItem());

			/* ��ҳ��ѯ����Ա��¼ */
			List<Comments> comments = commentsDao.getCommentsByCondition(pramas);
			if (comments != null && comments.size() > 0) {
				queryPage.setItems(comments);
			}
		}
		return queryPage;

	}

	public List<Comments> getCommentsByUserId(Long userId,int currPage, int pageSize) {
		// log.info("CommentsManagerImpl.getCommentsByCondition method");

		try {
//			return this.commentsDao.getCommentsByUserId(userId, page);
			return null;
		} catch (Exception e) {
			// log.error(e.getMessage());
		}
		return null;
	}

	public void replyComments(Map<String, Object> reply) {
		// log.info("CommentsManagerImpl.replyComments method");
		try {
			this.commentsDao.replyComments(reply);
		} catch (Exception e) {
			// log.error(e.getMessage());
		}
	}

	public void changeStatus(Map<String, Object> status) {
		// log.info("CommentsManagerImpl.changeStatus method");
		try {
			this.commentsDao.changeStatus(status);
		} catch (Exception e) {
			// log.error(e.getMessage());
		}
	}

//	public int getCommentsCountByCondition(Comments comment) {
//		// log.info("CommentsManagerImpl.getCommentsCountByCondition method");
//		try {
//			return this.commentsDao.getCommentsCountByCondition(comment);
//		} catch (Exception e) {
//			// log.error(e.getMessage());
//		}
//		return -1;
//	}

	public int getCommentsCountByUserId(Long userId) {
		// log.info("CommentsManagerImpl.getCommentsCountByUserId method");
		try {
			return this.commentsDao.getCommentsCountByUserId(userId);
		} catch (Exception e) {
			// log.error(e.getMessage());
		}
		return 0;
	}
	
    public List<Comments> commentsCountWithReplyFlag(){
        log.info("CommentsManagerImpl.commentsCountWithReplyFlag method");
        try{
            return this.commentsDao.commentsCountWithReplyFlag();
        }catch(Exception e){
            log.error(e.getMessage());
        }
        return null;
    }
}