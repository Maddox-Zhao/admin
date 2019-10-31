package com.huaixuan.network.biz.dao.user.iBatis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.huaixuan.network.biz.dao.user.CommentsDao;
import com.huaixuan.network.biz.domain.Comments;

/**
 * @version 3.2.0
 */
@Repository("commentsDao")
public class CommentsDaoiBatis implements CommentsDao {

	@Autowired
	private SqlMapClientTemplate sqlMapClient;

    /* @model: */
    public void addComments(Comments comments) throws Exception {
        sqlMapClient.insert("addComments", comments);
    }

    /* @model: */
    public void editComments(Comments comments) throws Exception {
        sqlMapClient.update("editComments", comments);
    }

    /* @model: */
    public void removeComments(Long commentsId) throws Exception {
        sqlMapClient.delete("removeComments", commentsId);
    }
/* @model: */
    public Comments getComments(Long commentsId) throws Exception {
        return (Comments) sqlMapClient.queryForObject("getComments", commentsId);
    }
/* @model: */
    public List<Comments> getCommentss() throws Exception {
        return sqlMapClient.queryForList("getCommentss", null);
    }

    /**
     */
    @SuppressWarnings("unchecked")
    public List<Comments> getCommentsByParameterMap(Map parameterMap) {
        return sqlMapClient.queryForList("getCommentsByParameterMap",
            parameterMap);
    }

    public int getCommentsCountByCondition(Map map) {
        return (Integer) sqlMapClient.queryForObject(
            "getCommentsCountByCondition", map);
    }

    public List<Comments> getCommentsByCondition(Comments comment) {
        Map parm = new HashMap();
        if (comment == null) {
            return null;
        }
        parm.put("userId", comment.getUserId());
        parm.put("userNickname", comment.getUserNickname());
        parm.put("goodsName", comment.getGoodsName());
        parm.put("gmtCreateStart", comment.getGmtCreateStart());
        parm.put("gmtCreateEnd", comment.getGmtCreateEnd());
        parm.put("status", comment.getStatus());
        parm.put("replyFlag", comment.getReplyFlag());
        return sqlMapClient.queryForList("getCommentsByCondition", parm);
    }

    @SuppressWarnings("unchecked")
    public List<Comments> getCommentsByCondition(Map map) {
        return sqlMapClient.queryForList("getCommentsByConditionTwo", map);
    }

    public List<Comments> getCommentsByUserId(Long userId, int currPage,
			int pageSize) throws Exception {
        Map map = new HashMap<String, Object>();
        map.put("userId", userId);
//        return this.findQueryPage("getCommentsByUserId", "getCommentsCountByUserId", map, page);
        return null;
    }

    public void replyComments(Map<String, Object> reply) {
        sqlMapClient.update("replyComments", reply);
    }

    public void changeStatus(Map<String, Object> status) {
        sqlMapClient.update("changeStatus", status);
    }

	public int getCommentsCountByUserId(Long userId) {
        Map map = new HashMap<String, Object>();
        map.put("userId", userId);
        return (Integer)sqlMapClient.queryForObject("getCommentsCountByUserId", map);
	}
	
	   
    public List<Comments> commentsCountWithReplyFlag() {
        return this.sqlMapClient.queryForList("commentsCountWithReplyFlag");
    }
}
