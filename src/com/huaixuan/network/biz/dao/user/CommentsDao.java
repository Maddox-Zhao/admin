/**
 * @Title: CommentDao.java
 * @Package com.huaixuan.network.biz.dao
 * @Description: TODO
 * @author liuwd weida-liu@foxmail.com
 * @date 2011-3-4 下午05:08:34
 * @version V1.0
 */
package com.huaixuan.network.biz.dao.user;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.Comments;

/**
 * @ClassName: CommentDao
 * @Description: TODO
 * @author liuwd weida-liu@foxmail.com
 * @date 2011-3-4 下午05:08:34
 *
 */
public interface CommentsDao  {
 	void addComments(Comments comments) throws Exception;

 	void editComments(Comments comments) throws Exception;

 	void removeComments(Long commentsId) throws Exception;

 	Comments getComments(Long commentsId) throws Exception;

 	List <Comments> getCommentss() throws Exception;

 	/**
     * 查询符合参数集ParameterMap要求的Comments结果集
     * @param parameterMap 参数集
     * @return 符合参数集ParameterMap要求的Comments结果集
     */
    List<Comments> getCommentsByParameterMap(Map parameterMap);

    List<Comments> getCommentsByCondition(Comments comment);

//    List<Comments> getCommentsByCondition(Comments comment,Page page);
    List<Comments> getCommentsByCondition(Map map);

//    List <Comments> getCommentsByUserId(Long userId,Page page) throws Exception;
    List <Comments> getCommentsByUserId(Long userId,int currPage, int pageSize) throws Exception;

    void replyComments(Map<String,Object> reply) throws Exception;

    void changeStatus(Map<String,Object> status) throws Exception;

    int getCommentsCountByCondition(Map map) throws Exception;

    int getCommentsCountByUserId(Long userId);
    
    /**
     * 按回复状态分类取得所有评论
     * @return List
     * @author chenyan 2011/04/19
     */
    List<Comments> commentsCountWithReplyFlag();
 }
