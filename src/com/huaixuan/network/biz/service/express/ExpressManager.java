package com.huaixuan.network.biz.service.express;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.express.Express;
import com.huaixuan.network.biz.domain.express.MotorTransInfo;
import com.huaixuan.network.biz.domain.express.query.ExpressDistQuery;
import com.huaixuan.network.biz.domain.express.query.ExpressQuery;
import com.huaixuan.network.biz.domain.express.query.FreightStatisticsQuery;
import com.huaixuan.network.biz.domain.express.query.MotorTransQuery;
import com.huaixuan.network.biz.query.QueryPage;

/**
 * �����Զ����(bibleUtil auto code generation)
 *
 * @version 3.2.0
 */
public interface ExpressManager {
	/* @interface model: ���һ��Express��¼ */
	public void addExpress(Express express);

	/* @interface model: ����һ��Express��¼ */
	public void editExpress(Express express);

	/* @interface model: ɾ��һ��Express��¼ */
	public void removeExpress(Long expressId);

	/* @interface model: ��ѯһ��Express���,����Express���� */
	public Express getExpress(Long expressId);

	/* @interface model: ��ѯ����Express���,����Express����ļ��� */
	public List<Express> getExpresss();

	/**
	 * �����������Ʋ���������Ϣ
	 *
	 * @param expressName
	 *            String
	 * @return List
	 * @author chenyan 2009/08/07
	 */
	List<Express> getExpressByName(String expressName);

	/**
	 * ���������������������Ϣ
	 *
	 * @param expressName
	 *            String
	 * @return List
	 * @author fanyj 2009/12/07
	 */
	List<Express> getExpressByCode(String expressCode);

	/**
	 * @Title: getMotorTransInfoById
	 * @Description:����id��ȡ������Ϣ
	 * @param @param id
	 * @param @return
	 * @param @
	 * @return MotorTransInfo
	 * @throws
	 */
	MotorTransInfo getMotorTransInfoById(Long id);

	/**
	 * @Title: addInterfaceUserTrade
	 * @Description: ����������Ϣ
	 * @param @param interfaceUserTrade
	 * @param @
	 * @return void
	 * @throws
	 */
	void addMotorTransInfo(MotorTransInfo motorTransInfo);

	/**
	 * @Title: editMotorTransInfo
	 * @Description: �༭������Ϣ
	 * @param @param motorTransInfo
	 * @param @
	 * @return void
	 * @throws
	 */
	void editMotorTransInfo(MotorTransInfo motorTransInfo);

	/**
	 * @Title: delMotorTransInfo
	 * @Description: ɾ��������Ϣ
	 * @param @param id
	 * @return void
	 * @throws
	 */
	void delMotorTransInfo(Long id);

	/**
	 * ����������Ϣ����
	 *
	 * @param parMap
	 *            Map
	 * @return int
	 * @author chenhang 2010/12/16
	 */
	int getMotorCountByCond(Map<String, String> parMap);

	/**
	 * ����������Ϣ
	 *
	 * @param parMap
	 *            Map
	 * @param page
	 *            Page
	 * @return List
	 * @author chenhang 2010/12/16
	 */
	QueryPage listMotorByCond(MotorTransQuery motorTransQuery, int currPage, int pageSize);

	/**
	 * ����������Ϣ����
	 *
	 * @param parMap
	 *            Map
	 * @return int
	 * @author chenyan 2009/08/10
	 */
	int getExpressCountByCond(Map<String, String> parMap);

	/**
	 * ����������Ϣ
	 *
	 * @param parMap
	 *            Map
	 * @param page
	 *            Page
	 * @return List
	 * @author chenyan 2009/08/10
	 */
	QueryPage listExpressByCond(ExpressQuery expressQuery, int currPage, int pageSize);

	/**
	 * ����ID����������Ϣ״̬
	 *
	 * @param status
	 *            String
	 * @param id
	 *            Long
	 * @return int
	 * @author chenyan 2009/08/10
	 */
	int updateExpressStatusById(String status, Long id);

	/**
	 *
	 * @author chenhang 2011-5-5
	 * @description ��������ID�޸��Ա�ͬ������
	 * @param id
	 * @return
	 */
	String getInterfaceExpressCodeByExpressId(Long id);

	/**
	 * ����״̬ȡ��������Ϣ
	 *
	 * @param status
	 *            String
	 * @return List
	 * @author chenyan 2009/08/17
	 */
	List<Express> listExpressByStatus(String status);

	/**
	 * ��������ͳ������
	 *
	 * @param parMap
	 * @author zhangwy
	 * @return
	 */
	int getFreightCountByParameterMap(Map<String, String> parMap);

	/**
	 * ���������б�
	 *
	 * @param parMap
	 * @author zhangwy
	 * @param page
	 * @return
	 */
	QueryPage getFreightListsByParameterMap(FreightStatisticsQuery freightStatisticsQuery, int currPage, int pageSize);

	/**
	 * ���������ܼ�
	 *
	 * @param parMap
	 * @return
	 */
	double getFreightAmountByParameterMap(FreightStatisticsQuery freightStatisticsQuery);

	/**
	 * ��ȡ��Ʒ����������˾
	 *
	 * @param parMap
	 * @return
	 */
	List<Express> getGoodsExpressRelation(Map parMap);

	/**
	 * ��ȡ����Ʒ����������˾
	 *
	 * @param parMap
	 * @return
	 */
	List<Express> getNotGoodsExpressRelation(Map parMap);

	/**
	 * ����������������˾������
	 *
	 * @param parMap
	 * @return
	 * @author chenhang 2011/01/17
	 */
	int getExpressAnalysisByExpCount(Map parMap);

	/**
	 * ����������������˾��
	 *
	 * @param expressDistQuery
	 * @param currPage
	 * @param pageSize
	 * @param isPage  �Ƿ��ҳ
	 * @return
	 */
	QueryPage getExpressAnalysisByExp(ExpressDistQuery expressDistQuery, int currPage, int pageSize, boolean isPage);

	/**
	 * ����������ʡ�ݣ�����
	 *
	 * @param parMap
	 * @return
	 * @author chenhang 2011/01/17
	 */
	int getExpressAnalysisByProCount(Map parMap);

	/**
	 * ����������ʡ�ݣ�
	 *
	 * @param parMap
	 * @return
	 * @author chenhang 2011/01/17
	 */
	QueryPage getExpressAnalysisByPro(ExpressDistQuery expressDistQuery, int currPage, int pageSize, boolean isPage);
}
