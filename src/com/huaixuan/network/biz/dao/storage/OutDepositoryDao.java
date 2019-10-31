package com.huaixuan.network.biz.dao.storage;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.storage.OutDepository;
import com.huaixuan.network.biz.query.QueryPage;

/**
 * @version 3.2.0
 */
public interface OutDepositoryDao {
	Long addOutDepository(OutDepository outDepository);

	void editOutDepository(OutDepository outDepository);

	void removeOutDepository(Long outDepositoryId);

	OutDepository getOutDepository(Long outDepositoryId);
	OutDepository getOutDepository(Map map);

	List<OutDepository> getOutDepositorys();

	/**
	 * ����������ĳ��ⵥ����
	 *
	 * @param parMap
	 *            Map
	 * @return int
	 * @author chenyan 2009/07/28
	 */
	int getOutDepositoryListsCount(Map<String, String> parMap);

	/**
	 * ����������ĳ��ⵥ�б���Ϣ
	 *
	 * @param parMap
	 *            Map
	 * @param page
	 *            Page
	 * @return List
	 * @author chenyan 2009/07/28
	 */
	QueryPage getOutDepositoryLists(Map parMap,
			int currentPage, int pageSize, boolean isPage);

	QueryPage getActualInventoryLists(Map parMap,
			int currentPage, int pageSize, boolean isPage);

	/**
	 * ����ID����״̬
	 *
	 * @param id
	 *            Long
	 * @param status
	 *            String
	 * @param gmtOutDep
	 *            Date
	 * @return int
	 * @author chenyan 2009/07/29
	 */
	int updateOutDepositoryStatusById(Long id, String status, Date gmtOutDep,
			String modifier);

	/**
	 * ����ID����ʵ������
	 *
	 * @param actualWeight
	 *            Double
	 * @param id
	 *            String
	 * @return int
	 * @author chenhang 2010/11/18
	 */
	int updateActualWeightById(Double actualWeight, String id);

	/**
	 * ����ID���¼�������
	 *
	 * @param castWeight
	 *            Double
	 * @param id
	 *            String
	 * @return int
	 * @author chenhang 2010/11/18
	 */
	int updateCastWeightById(Double castWeight, String id);

	/**
	 * ���ݳ����굥ID�������ⵥ״̬
	 *
	 * @param outDetailId
	 *            Long
	 * @return String
	 * @author chenyan 2009/08/11
	 */
	String getOutDepositoryStatusByDetailId(Long outDetailId);

	/**
	 * ���ݳ���������Ϣ������������
	 *
	 * @param expressCode
	 *            String
	 * @param outDepId
	 *            Long
	 * @return int
	 * @author chenyan 2009/08/18
	 */
	int updateExpressCodeById(String expressCode, Long outDepId);

	/**
	 * ��ȡȫ���ĳ����굥
	 *
	 * @return list
	 * @author zhangwy 2009/09/10
	 *
	 */
	List<OutDepository> getOutDepositorysWithDetail(Map parmap);

	/**
	 * ��ȡȫ�������굥����
	 *
	 * @param parmap
	 * @return int
	 * @author zhangwy 2009/09/16
	 */
	int getOutDepositorysWithDetailCount(Map parmap);

	/**
	 * ���������õ����ܳ��ⵥ�б�����
	 *
	 * @param parMap
	 * @return
	 */
	int gatherOutDepositoryListsCount(Map parMap);

	/**
	 * ���������õ����ܳ��ⵥ�б�
	 *
	 * @param parMap
	 * @return
	 */
	QueryPage gatherOutDepositoryLists(Map parMap, int currentPage,
			int pageSize, boolean isPage);

	OutDepository getOutDepositoryByTid(String tid);

	/**
	 * �����굥id��ȡ��������
	 *
	 * @param detailId
	 * @return
	 */
	OutDepository getOutDepositoryByDetailId(Long detailId);

	List<OutDepository> getOutDepositoryByExpressCode(String expressCode);

	/**
	 * @Title: updateIsOutDepositoryPrintedById
	 * @Description: ����isOutDepositoryPrinted�ֶ�
	 * @param ids
	 * @return int @
	 */
	int updateIsOutDepositoryPrintedById(String[] ids);

    /**
    * @Title: updateIsExpressPrintedById
    * @Description: ����isExpressPrinted�ֶ�
    * @param ids
    * @return int
    * @
     */
    int updateIsExpressPrintedById(String[] ids) ;
    
    /**
     * ���³��ⵥ������
     * @param handleAdminId Long
     * @param outDepositoryId Long
     * @return int ����ɹ�����
     * @author chenyan 2011/03/25
     */
    int updateHandleAdminIdByUser(Long handleAdminId, Long outDepositoryId);
}
