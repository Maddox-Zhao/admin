package com.huaixuan.network.biz.dao.storage;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.storage.InDepository;
import com.huaixuan.network.biz.query.InDepositoryQuery;
import com.huaixuan.network.biz.query.QueryPage;

/**
 * (bibleUtil auto code generation)
 * 
 * @version 3.2.0
 */
public interface InDepositoryDao {
	/* @interface model: InDepository */
	Long addInDepository(InDepository inDepository);

	/* @interface model: InDepository */
	void editInDepository(InDepository inDepository);

	/* @interface model: InDepository */
	void removeInDepository(Long inDepositoryId);

	/* @interface model: InDepository,InDepository */
	InDepository getInDepository(Long inDepositoryId);

	/* @interface model: InDepository,InDepository */
	List<InDepository> getInDepository(Map map);

	/* @interface model: InDepository,InDepository */
	List<InDepository> getInDepositorys();

	/**
	 * ������������ⵥ����
	 * 
	 * @param parMap
	 *            Map
	 * @return int
	 * @author chenyan 2009/07/21
	 */
	int getInDepositoryListsCount(Map<String, String> parMap);

	/**
	 * ������������ⵥ�б���Ϣ
	 * 
	 * @param parMap
	 *            Map
	 * @param page
	 *            Page
	 * @return List
	 * @author chenyan 2009/07/21
	 */
	QueryPage getInDepositoryLists(InDepositoryQuery query, int currPage, int pageSize, boolean isPage);

	/**
	 * ����ID����״̬
	 * 
	 * @param id
	 *            Long
	 * @param status
	 *            String
	 * @param gmtInDep
	 *            Date
	 * @return int
	 * @author chenyan 2009/07/25
	 */
	int updateInDepositoryStatusById(Long id, String status, Date gmtInDep);

	/**
	 * ����IDȡ������
	 * 
	 * @param id
	 *            Long
	 * @return String
	 * @author chenyan 2009/07/27
	 */
	String getBatchNumById(Long id);

	/**
	 * ���ݹ������ݺ�ȡ��δ��ɵ���ⵥ��
	 * 
	 * @param relationNum
	 *            String
	 * @return int
	 * @author chenyan 2009/08/05
	 */
	int getUnFinishedInDepByRelNum(String relationNum);

	/**
	 * ���ݹ������ݺ�ȡ����ɵ���ⵥ������
	 * 
	 * @param relationNum
	 *            String
	 * @return int
	 * @author fanyj 2010/05/25
	 */
	int getFinishedInDepByRelNum(String relationNum);

	/**
	 * ��������굥ID������ⵥ״̬
	 * 
	 * @param inDetailId
	 *            Long
	 * @return String
	 * @author chenyan 2009/08/11
	 */
	String getInDepositoryStatusByDetailId(Long inDetailId);

	/**
	 * ��ȡ��������굥
	 * 
	 * @return list
	 * @author zhangwy 2009/09/10
	 */
	List<InDepository> getInDepositorysWithDetail(Map parmap);

	/**
	 * ��ȡ��������굥����
	 * 
	 * @param parmap
	 * @return int
	 * @author zhangwy 2009/9/16
	 */
	int getInDepositorysWithDetailCount(Map parmap);

	/**
	 * ��ȡ�����������
	 * 
	 * @return list
	 * @author zhangwy 2009/09/18
	 */
	List<String> getAlltypes();

	/**
	 * ���ݹ������Ż�ȡ��ⵥ��Ϣ
	 * 
	 * @param relationNum
	 * @author zhangwy
	 * @return
	 */
	InDepository getInDepByRelNum(String relationNum);

	/**
	 * ������ⵥId��ѯ��Ӧ������
	 * 
	 * @param inDepositoryIds
	 * @return
	 */
	Map getSupplierNameById(List<String> inDepositoryIds);
}
