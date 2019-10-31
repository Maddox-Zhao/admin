package com.huaixuan.network.biz.service.goods;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.goods.GoodsAgent;

public interface GoodsAgentManager {

 	/**����userAgentId,goodsName,GoodsCodeͳ��
 	 * @param paramMap
 	 * @return
 	 */
 	int getGoodsAgentCountByParameterMap(Map<String,String> paramMap);

 	/**����goods_agent
 	 * @param agent
 	 */
 	boolean insertGoodsAgent(GoodsAgent agent);

	/**
	 * @param parMap
	 * @return
	 */
	GoodsAgent getGoodsAgent(Map<String,Object> parMap);


	void updateAgentStatus(Map<String,Object> parMap);

	/**��������Ʒ��Ϊ�û��Ĵ�����Ʒ
	 * @param ids
	 * @param userId
	 * @return
	 */
	boolean insertaUserGoodsAgentList(List<String> ids,Long userId);

	/**ͨ��userId,����goodsId�õ�GoodsAgent�б�
	 * @param parMap
	 * @return
	 */
	List<GoodsAgent> getGoodsAgentList(Map<String,Object> parMap);

    /**
     * �û�����������Ʒ��ѯ
     * @param parMap
     * @param page
     * @return
     */
//	List<GoodsAgent> getGoodsAgentByParameterMap(Map<String,String> parMap,Page page);

	/**
	 * �û��쳣������Ʒ��ѯ
	 * @param parMap
	 * @param page
	 * @return
	 */
//	List<GoodsAgent> getErrorGoodsAgentByParameterMap(Map<String,String> parMap,Page page);

	/**
	 * �û��쳣������Ʒ����
	 * @param paramMap
	 * @return
	 */
	int getErrorGoodsAgentCountByParameterMap(Map<String,String> paramMap);

	/**
     * ȡ�ô�����Ʒ������д��ڵ����ͨ���Ĵ�����ԱID
     * @return List
     * @author chenyan 2009/11/12
     */
    List<Long> getPastUserIdForAddGoodsRelation();

    /**
     * ȡ�ô�����Ʒ������в����ڵ����ͨ���Ĵ�����ԱID
     * @param userId Long
     * @return List
     * @author chenyan 2009/11/12
     */
    List<Long> getCurrUserIdForAddGoodsRelation(Long userId);

    /**
     * �����û�ID����ƷID����״̬
     * @param goodsAgent GoodsAgent
     * @return int
     * @author chenyan 2009/11/13
     */
    int editStatusByUserIdGoodsId(GoodsAgent goodsAgent);

    /**
     * ������ƷID����ȡ��������Ա���������롣
     * @param goodsId Long
     * @author chenyan 2009/12/04
     */
    void insertBatchAgentUserForGoodsId(Long goodsId);


    /**�������ô�����Ʒ������
     * @param agent
     * @return
     */
    boolean insertBatchGoodsAgent(List<String> goodsIds,Long userId,Long agentCount);

    /**ͳ���û�����Ʒ����������
     * @param userId
     * @return
     */
    int countGoodsAgentByUserId(String userId);

    /**ͳ�ƹ���������
     * @param paramMap
     * @return
     */
    int countGoodsAgentByMap(Map<String,Object> paramMap);

    /**����ȡ��������Ʒ������
     * @param goodsIds
     * @param userId
     * @return
     */
    boolean updateBatchGoodsAgent(List<String> goodsIds,Long userId);
}
