package com.huaixuan.network.biz.dao.express;

 import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.express.ExpressDist;
import com.huaixuan.network.biz.domain.express.query.ExpressDistQuery;
import com.huaixuan.network.biz.query.QueryPage;

 /**
  * @version 3.2.0
  */
 public interface ExpressDistDao {
 	/* @interface model: ���һ��ExpressDist��¼ */
 	void addExpressDist(ExpressDist expressDist) throws Exception;

 	/* @interface model: ����һ��ExpressDist��¼ */
 	void editExpressDist(ExpressDist expressDist) throws Exception;

 	/* @interface model: ɾ��һ��ExpressDist��¼ */
 	void removeExpressDist(Long expressDistId) throws Exception;

 	ExpressDist getExpressDist(Long expressDistId) throws Exception;

 	List <ExpressDist> getExpressDists() throws Exception;

 	/**
 	 * ȡ��������Χ��Ϣ
 	 * @param expressDistId
 	 * @return ExpressDist
 	 * @author chenyan 2009/08/07
 	 */
 	ExpressDist getExpressDistById(Long expressDistId);

 	/**
 	 * ��������ID����ʼ�غ�Ŀ�ĵ�Ψһȷ��һ����¼������ɾ������
 	 * @param expressId Long
 	 * @param regionCodeStart String
 	 * @param regionCodeEnd String
 	 * @return int
 	 * @author chenyan 2009/08/10
 	 */
 	int removeExpressDistByRegion(Long expressId, String regionCodeStart, String regionCodeEnd);

 	/**
 	 * ����������Χ����
 	 * @param map Map
 	 * @return int
 	 * @author chenyan 2009/08/11
 	 */
 	int getExpressDistCountByCond(Map map);

 	/**
 	 * ����������Χ��Ϣ
 	 * @param map Map
 	 * @param page Page
 	 * @return QueryPage
 	 * @author chenyan 2009/08/11
 	 */
 	QueryPage getExpressDistByCond(Map parMap, int currentPage, final int pageSize);

 	/**
 	 * �����������¶���ʱ���������Ϣ�б�
 	 * @return List
 	 * @author chenyan 2009/08/20
 	 */
 	List<ExpressDist> listExpressDistForTrade();

 	/**
 	 * ��������ID����ʼ�غ�Ŀ�ĵغ�֧����ʽȡ�ü�¼����
 	 * @param expressId Long
 	 * @param regionCodeStart String
 	 * @param regionCodeEnd String
 	 * @param payment String
 	 * @return int
 	 * @author chenyan 2009/08/27
 	 */
 	int getExpressDistByRegion(Long expressId, String regionCodeStart, String regionCodeEnd, String payment);

 	/**
 	 * ����Ŀ�ĵغ�֧����ʽȡ�ü�¼����
 	 * @param regionCodeEnd String
 	 * @param payment String
 	 * @return int
 	 * @author chenyan 2009/09/04
 	 */
 	int getCountByRegionCodeEnd(String regionCodeEnd, String payment);

 	/**
 	 * ����Ŀ�ĵغ�֧����ʽȡ�ü�¼��Ϣ
 	 * @param regionCodeStart String
 	 * @param regionCodeEnd String
 	 * @param payment String
 	  @param expressId Long
 	 * @return List
 	 * @author chenyan 2009/09/04 modified by chenyan 2009/09/07 modified by chenyan 2011/04/11
 	 */
 	List<ExpressDist> listExpressDistByRegionCodeEnd(String regionCodeStart, String regionCodeEnd, String payment, Long expressId);

 	/**
 	 * ȡ�ô����µ������Ƿ��ַ�ظ�
 	 * @param regionCodeStart String
 	 * @param regionCodeEnd String
 	 * @param expressId Long
 	 * @param id Long
 	 * @param payment String
 	 * @return int
 	 * @author chenyan 2009/09/08
 	 */
 	int getCountByFourForUpdate(String regionCodeStart, String regionCodeEnd, Long expressId, Long id, String payment);

 	/**
 	 * ��������ID����ʼ�أ�Ŀ�ĵأ�֧����ʽ���и���������Χ
 	 * @param expressDist ExpressDist
 	 * @return int
 	 * @author chenyan 2009/11/10
 	 */
 	int editExpressDistByFourCond(ExpressDist expressDist);

 	/**
 	 * �ϲ�������ʱ��������ԭ�ж���������
 	 * @param regionCodeStart
 	 * @param regionCodeEnd
 	 * @param payment
 	 * @param expressId
 	 * @return
 	 * @author zhangwy
 	 */
 	ExpressDist getExpressDistByRegionCodeEnd(String regionCodeStart, String regionCodeEnd, String payment, Long expressId);

 	/**
 	 * ��������������Χ״̬
 	 * @param ids
 	 * @param status
 	 * @return
 	 */
 	public int bathUpdateStatus(List<Long> expressDistIds,String status);
 }
