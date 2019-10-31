package com.huaixuan.network.biz.dao.goods;

 import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.goods.Promation;
import com.huaixuan.network.biz.domain.goods.PromationGive;
import com.huaixuan.network.biz.domain.goods.PromationVO;
import com.huaixuan.network.biz.query.QueryPage;


 public interface PromationDao {
	 	/* @interface model: Promation */
	 	long addPromation(Promation promation) throws Exception;

	 	/* @interface model: Promation */
	 	void editPromation(Promation promation) throws Exception;

	 	/* @interface model: Promation */
	 	void removePromation(Long promationId) throws Exception;

	 	/* @interface model: Promation,Promation */
	 	Promation getPromation(Long promationId) throws Exception;

	 	/*
	     * �����ײ����Ͳ�ѯ��Ч���ײͼ�¼
	     * @param context
	     * @return
	     */
	 	public Promation getPromationByModeCode(String modeCode) throws Exception;
	 	
	 	/* @interface model: Promation,Promation */
	 	List<Promation> getPromationByName(String name) throws Exception;

	    /* @interface model: Promation,Promation */
	 	List <Promation> getPromations() throws Exception;

	 	List<Promation> getPromationsByIds(List ids);

	 	/**
	 	 * ���ݲ�������ȡ�Ż��б�
	 	 * @param map
	 	 * @return
	 	 * @throws Exception
	 	 */

	 	List<PromationVO> getPromationsByParams(Map map)throws Exception;
	 	/**
	     * ���ݷ�ҳ�������Ϣ��ѯPromationList
	     * ʹ�÷�Χ��Promation��ѯ
	     * @param context
	     * @return List<Promation>
	     */

//	 	List<Promation> getPromationsByPage(Map<String, Object> conditions,Page page) throws Exception;


	 	/**
	 	 * ��̬�޸Ĳ���
	 	 * @param promation
	 	 * @throws Exception
	 	 */
	 	void editPromationByDynamic(Promation promation) throws Exception;


	 	List<PromationVO> getGivePromationList(Map<String, Object> params);
	 	/**
	     * ��ʱ���Զ�ȡ������
	     *
	     * @param promationId
	     * @param promContext
	     * @return
	     */
	    void autoCanelFreeze();

	    /**
	     * ��ȡȫ�����õ�������ײ�
	     * @return
	     */
	    List<PromationGive> getNewPromationVOListGive(String nowDate);
	    
	    /**
	     * ��ȡ��ǰ��Ʒ��������ײ�
	     * @param promationId
	     * @return
	     */
	    List<PromationGive> getGivePromation(Long promationId);
	    
	    /**
	     * ��ȡ�����͵��ײͼ���
	     * @param scope
	     * @return
	     */
	    List<Promation> getFullGivePromationList(Map parMap);
	    
	    /**
	     * ��ȡ�ײ��б�����
	     * @param parMap
	     * @return
	     */
	    Integer getPromationListByConditionWithPageCount(Map parMap);
	    
	    /**
	     * ��ȡ�ײ��б�
	     * @param parMap
	     * @return
	     */
	    List<Promation> getPromationListByConditionWithPage(Map parMap);
 }
