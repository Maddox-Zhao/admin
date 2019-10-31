package com.huaixuan.network.biz.service.goods;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.goods.GoodsAgent;

public interface GoodsAgentManager {

 	/**根据userAgentId,goodsName,GoodsCode统计
 	 * @param paramMap
 	 * @return
 	 */
 	int getGoodsAgentCountByParameterMap(Map<String,String> paramMap);

 	/**新增goods_agent
 	 * @param agent
 	 */
 	boolean insertGoodsAgent(GoodsAgent agent);

	/**
	 * @param parMap
	 * @return
	 */
	GoodsAgent getGoodsAgent(Map<String,Object> parMap);


	void updateAgentStatus(Map<String,Object> parMap);

	/**批量将商品加为用户的代销产品
	 * @param ids
	 * @param userId
	 * @return
	 */
	boolean insertaUserGoodsAgentList(List<String> ids,Long userId);

	/**通过userId,或者goodsId得到GoodsAgent列表
	 * @param parMap
	 * @return
	 */
	List<GoodsAgent> getGoodsAgentList(Map<String,Object> parMap);

    /**
     * 用户正常代销商品查询
     * @param parMap
     * @param page
     * @return
     */
//	List<GoodsAgent> getGoodsAgentByParameterMap(Map<String,String> parMap,Page page);

	/**
	 * 用户异常代销商品查询
	 * @param parMap
	 * @param page
	 * @return
	 */
//	List<GoodsAgent> getErrorGoodsAgentByParameterMap(Map<String,String> parMap,Page page);

	/**
	 * 用户异常代销商品数量
	 * @param paramMap
	 * @return
	 */
	int getErrorGoodsAgentCountByParameterMap(Map<String,String> paramMap);

	/**
     * 取得代销商品管理表中存在的审核通过的代销会员ID
     * @return List
     * @author chenyan 2009/11/12
     */
    List<Long> getPastUserIdForAddGoodsRelation();

    /**
     * 取得代销商品管理表中不存在的审核通过的代销会员ID
     * @param userId Long
     * @return List
     * @author chenyan 2009/11/12
     */
    List<Long> getCurrUserIdForAddGoodsRelation(Long userId);

    /**
     * 根据用户ID和商品ID更新状态
     * @param goodsAgent GoodsAgent
     * @return int
     * @author chenyan 2009/11/13
     */
    int editStatusByUserIdGoodsId(GoodsAgent goodsAgent);

    /**
     * 根据商品ID，再取出代销会员，批量加入。
     * @param goodsId Long
     * @author chenyan 2009/12/04
     */
    void insertBatchAgentUserForGoodsId(Long goodsId);


    /**批量设置代销商品关联表
     * @param agent
     * @return
     */
    boolean insertBatchGoodsAgent(List<String> goodsIds,Long userId,Long agentCount);

    /**统计用户与商品关联的数量
     * @param userId
     * @return
     */
    int countGoodsAgentByUserId(String userId);

    /**统计关联的数量
     * @param paramMap
     * @return
     */
    int countGoodsAgentByMap(Map<String,Object> paramMap);

    /**批量取消代销商品关联表
     * @param goodsIds
     * @param userId
     * @return
     */
    boolean updateBatchGoodsAgent(List<String> goodsIds,Long userId);
}
