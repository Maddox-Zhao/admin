/**
 * @Title: CommentsService.java
 * @Package com.huaixuan.network.biz.service
 * @Description: TODO
 * @author liuwd weida-liu@foxmail.com
 * @date 2011-3-4 ����05:16:10
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
 * @date 2011-3-4 ����05:16:10
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
     * ��ѯ���ϲ�����ParameterMapҪ���Comments�����
     * @param parameterMap ������
     * @return ���ϲ�����ParameterMapҪ���Comments�����
     */
    public List<Comments> getCommentsByParameterMap(Map parameterMap);

    /**
     * ��ѯĳ����Ʒ�������б�
     * @param goodsId
     * @return
     */
    public List<Comments> getCommentsByGoodsId(Long goodsId);
    /**
     * ��ѯĳ����Ʒ�������б�
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
     * ���ظ�״̬����ȡ����������
     * @return List
     * @author chenyan 2011/04/19
     */
    List<Comments> commentsCountWithReplyFlag();
 }
