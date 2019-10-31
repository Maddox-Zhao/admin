package com.huaixuan.network.biz.dao.express;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.express.Express;
import com.huaixuan.network.biz.domain.express.query.FreightStatisticsQuery;
import com.huaixuan.network.biz.query.QueryPage;

/**
 *
 * @version 3.2.0
 */
public interface ExpressDao {
	void addExpress(Express express);

	void editExpress(Express express);

	void removeExpress(Long expressId);

	Express getExpress(Long expressId);

	List<Express> getExpresss();

	/**
	 * �����������Ʋ���������Ϣ(�����������Ʋ����ظ���������list.size���ֻ��Ϊ1)
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
	 * @return QueryPage
	 * @author chenyan 2009/08/10
	 */
	QueryPage listExpressByCond(Map parMap, int currentPage, int pageSize);

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
	 *
	 * @author chenhang 2011-5-17
	 * @description
	 * @param id
	 * @return
	 */
	Express getExpressIdByInterfaceExpressCode(String interfaceExpressCode);

	/**
	 *
	 * @author chenhang 2011-5-18
	 * @description
	 * @param id
	 * @return
	 */
	Express getExpressIdByExpressCode(String expressCode);

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
	 * @param QueryPage
	 * @return
	 */
	QueryPage getFreightListsByParameterMap(
			FreightStatisticsQuery freightStatisticsQuery, int currentPage,
			int pageSize);

	/**
	 * �����ܷ���
	 *
	 * @param parMap
	 * @return
	 */
	double getFreightAmountByParameterMap(
			FreightStatisticsQuery freightStatisticsQuery);

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
}
