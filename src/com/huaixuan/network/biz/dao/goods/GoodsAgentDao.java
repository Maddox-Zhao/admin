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
	 * ����
	 *
	 * @param goodsAgent
	 */
	void insertGoodsAgent(GoodsAgent goodsAgent);

	/**
	 * userId,goodsIdһ��Ҫ��(userId,goodsId������ȷ��һ����¼)
	 *
	 * @param parMap
	 * @return
	 */
	GoodsAgent getGoodsAgent(Map<String, Object> parMap);

	/**
	 * ͨ��userId,����goodsId�õ�GoodsAgent�б�
	 *
	 * @param parMap
	 * @return
	 */
	List<GoodsAgent> getGoodsAgentList(Map<String, Object> parMap);

	/**
	 * ȡ�ô�����Ʒ������д��ڵ����ͨ���Ĵ�����ԱID
	 *
	 * @return List
	 * @author chenyan 2009/11/12
	 */
	List<Long> getPastUserIdForAddGoodsRelation();

	/**
	 * ȡ�ô�����Ʒ������в����ڵ����ͨ���Ĵ�����ԱID
	 *
	 * @param userId
	 *            Long
	 * @return List
	 * @author chenyan 2009/11/12
	 */
	List<Long> getCurrUserIdForAddGoodsRelation(Long userId);

	/**
	 * �����û�ID����ƷID����״̬
	 *
	 * @param goodsAgent
	 *            GoodsAgent
	 * @return int
	 * @author chenyan 2009/11/13
	 */
	int editStatusByUserIdGoodsId(GoodsAgent goodsAgent);

	/**
	 * ������ƷID����ȡ��������Ա���������롣
	 *
	 * @param goodsId
	 *            Long
	 * @author chenyan 2009/12/04
	 */
	void insertBatchAgentUserForGoodsId(Long goodsId);

	/**
	 * ���������û���Ʒ�Ĵ�������
	 *
	 * @param agent
	 * @param agentList
	 */
	void insertBatchGoodsAgent(final List<GoodsAgent> agent,
			final List<Map> agentList);

	/**ͳ���û�����Ʒ����������
	 * @param paramMap
	 * @return
	 */
	int countGoodsAgentByUserId(Map paramMap);

	/**����ȡ���û���Ʒ�Ĵ�������
	 * @param agentList
	 */
	void updateBatchGoodsAgent(final List<Map> agentList);
}
