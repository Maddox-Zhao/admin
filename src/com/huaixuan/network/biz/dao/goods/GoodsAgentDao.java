package com.huaixuan.network.biz.dao.goods;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.goods.GoodsAgent;
import com.huaixuan.network.biz.query.QueryPage;


public interface GoodsAgentDao {

//	List<GoodsAgent> getGoodsAgentByParameterMap(Map parameterMap, Page page);

	int getGoodsAgentCountByParameterMap(Map<String, String> parMap);

//	List<GoodsAgent> getErrorGoodsAgentByParameterMap(Map parameterMap,
//			Page page);

	int getErrorGoodsAgentCountByParameterMap(Map<String, String> parMap);

	void updateAgentStatus(Map<String, Object> parMap);

	/**
	 * 新增
	 *
	 * @param goodsAgent
	 */
	void insertGoodsAgent(GoodsAgent goodsAgent);

	/**
	 * userId,goodsId一定要传(userId,goodsId两条件确定一条记录)
	 *
	 * @param parMap
	 * @return
	 */
	GoodsAgent getGoodsAgent(Map<String, Object> parMap);

	/**
	 * 通过userId,或者goodsId得到GoodsAgent列表
	 *
	 * @param parMap
	 * @return
	 */
	List<GoodsAgent> getGoodsAgentList(Map<String, Object> parMap);

	/**
	 * 取得代销商品管理表中存在的审核通过的代销会员ID
	 *
	 * @return List
	 * @author chenyan 2009/11/12
	 */
	List<Long> getPastUserIdForAddGoodsRelation();

	/**
	 * 取得代销商品管理表中不存在的审核通过的代销会员ID
	 *
	 * @param userId
	 *            Long
	 * @return List
	 * @author chenyan 2009/11/12
	 */
	List<Long> getCurrUserIdForAddGoodsRelation(Long userId);

	/**
	 * 根据用户ID和商品ID更新状态
	 *
	 * @param goodsAgent
	 *            GoodsAgent
	 * @return int
	 * @author chenyan 2009/11/13
	 */
	int editStatusByUserIdGoodsId(GoodsAgent goodsAgent);

	/**
	 * 根据商品ID，再取出代销会员，批量加入。
	 *
	 * @param goodsId
	 *            Long
	 * @author chenyan 2009/12/04
	 */
	void insertBatchAgentUserForGoodsId(Long goodsId);

	/**
	 * 批量设置用户商品的代销关联
	 *
	 * @param agent
	 * @param agentList
	 */
	void insertBatchGoodsAgent(final List<GoodsAgent> agent,
			final List<Map> agentList);

	/**统计用户与商品关联的数量
	 * @param paramMap
	 * @return
	 */
	int countGoodsAgentByUserId(Map paramMap);

	/**批量取消用户商品的代销关联
	 * @param agentList
	 */
	void updateBatchGoodsAgent(final List<Map> agentList);
}
