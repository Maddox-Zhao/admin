package com.huaixuan.network.biz.service.storage;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.storage.InDepository;
import com.huaixuan.network.biz.query.InDepositoryQuery;
import com.huaixuan.network.biz.query.QueryPage;

/**
 * ��ⵥ������Ϣ(bibleUtil auto code generation)
 *
 * @version 3.2.0
 */
public interface InDepositoryManager {
	/**
	 * ������ⵥ������Ϣ
	 *
	 * @param inDepository
	 * @return
	 */
	// public long addInDepository(InDepository inDepository);

	/**
	 * �༭��ⵥ������Ϣ
	 *
	 * @param inDepository
	 */
	 public void editInDepository(InDepository inDepository);

	/**
	 * ɾ����ⵥ������Ϣ
	 *
	 * @param inDepositoryId
	 */
	// public void removeInDepository(Long inDepositoryId);

	/**
	 * ����ID�õ���ⵥ������Ϣ
	 *
	 * @param inDepositoryId
	 * @return
	 */
	public InDepository getInDepository(Long inDepositoryId);

	/**
	 * ����������ѯ��ⵥ������Ϣ����
	 *
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public InDepository getInDepository(Map map) throws Exception;

	/**
	 * ��ѯȫ����ⵥ������Ϣ�б�
	 *
	 * @return
	 */
	// public List<InDepository> getInDepositorys();

	/**
	 * ������������ⵥ����
	 *
	 * @return int
	 * @author chenyan 2009/07/21
	 */
	// int getInDepositoryListsCount(Map<String, String> parMap);

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
	// int updateInDepositoryStatusById(Long id, String status, Date gmtInDep);

	/**
	 * ��������������
	 *
	 * @param map
	 *            Map
	 * @return Boolean �����ɹ���ʶ
	 * @author chenyan 2009/07/25
	 * @throws Exception
	 */
	Boolean addStorageOpt(Map<String, Object> map) throws Exception;

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
	// int getFinishedInDepByRelNum(String relationNum);

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
	 * ��ȡ��������굥��Ϣ
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
	 * @author zhangwy 2009/09/16
	 */
	// int getInDepositorysWithDetailCount(Map parmap);

	/**
	 * ��ȡ�����������
	 *
	 * @return list
	 * @author zhangwy 2009/09/18
	 */
	// List<String> getAlltypes();

	/**
	 * ��ⵥId��ѯ��Ӧ������
	 *
	 * @param inDepositoryIds
	 * @return
	 */
	Map getSupplierNameById(List<String> inDepositoryIds);
}
