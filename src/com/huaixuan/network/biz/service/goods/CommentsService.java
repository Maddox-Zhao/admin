/**
 * @Title: CommentsService.java
 * @Package com.huaixuan.network.biz.service
 * @Description: TODO
 * @author liuwd weida-liu@foxmail.com
 * @date 2011-3-4 下午05:16:10
 * @version V1.0
 */
package com.huaixuan.network.biz.service.goods;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.Comments;
import com.huaixuan.network.biz.query.QueryPage;

/**
 * @ClassName: CommentsService
 * @Description: TODO
 * @author liuwd weida-liu@foxmail.com
 * @date 2011-3-4 下午05:16:10
 *
 */
public interface CommentsService {
 	public void addComments(Comments comments);

 	public void editComments(Comments comments);

 	public void removeComments(Long commentsId);

 	public Comments getComments(Long commentsId);

 	public List<Comments> getCommentss();

 	//if commented on goods, set the goods name to the comment.
 	public void setGoodsNameCommented(Comments comment);

 	/**
     * 查询符合参数集ParameterMap要求的Comments结果集
     * @param parameterMap 参数集
     * @return 符合参数集ParameterMap要求的Comments结果集
     */
    public List<Comments> getCommentsByParameterMap(Map parameterMap);

    /**
     * 查询某个商品的评价列表
     * @param goodsId
     * @return
     */
    public List<Comments> getCommentsByGoodsId(Long goodsId);
    /**
     * 查询某个商品的评价列表
     * @param goodsId
     * @return
     */
    public List<Comments> getAllCommentsByGoodsId(Long goodsId);

    public List<Comments> getCommentsByCondition(Comments comment);

//    public List<Comments> getCommentsByCondition(Comments comment,Page page);
    public QueryPage getCommentsByCondition(Comments comment,int currPage, int pageSize)throws Exception;

//    public List<Comments> getCommentsByUserId(Long userId,Page page);
    public List<Comments> getCommentsByUserId(Long userId,int currPage, int pageSize);

    public void replyComments(Map<String,Object> reply);

    public void changeStatus(Map<String,Object> status);

//    public int getCommentsCountByCondition(Comments comment);

    public int getCommentsCountByUserId(Long userId);
    
    
    /**
     * 按回复状态分类取得所有评论
     * @return List
     * @author chenyan 2011/04/19
     */
    List<Comments> commentsCountWithReplyFlag();
 }
