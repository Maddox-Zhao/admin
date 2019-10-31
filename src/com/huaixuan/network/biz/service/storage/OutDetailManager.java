package com.huaixuan.network.biz.service.storage;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.storage.GatherOutDepository;
import com.huaixuan.network.biz.domain.storage.OutDepositoryStorage;
import com.huaixuan.network.biz.domain.storage.OutDetail;
import com.huaixuan.network.biz.domain.storage.OutDetailBaseInfo;
import com.huaixuan.network.biz.domain.storage.OutDetailGoods;
import com.huaixuan.network.biz.domain.storage.query.FinanceDepositoryQuery;
import com.huaixuan.network.biz.query.QueryPage;

/**
 * ���ⵥ������Ϣ(bibleUtil auto code generation)
 *
 * @version 3.2.0
 */
public interface OutDetailManager {
	/**
	 * �������ⵥ������Ϣ
	 *
	 * @param outDetail
	 * @return
	 */
	public Long addOutDetail(OutDetail outDetail);

	/**
	 * �༭���ⵥ������Ϣ
	 *
	 * @param outDetail
	 */
	public void editOutDetail(OutDetail outDetail);

	/**
	 * ɾ�����ⵥ������Ϣ
	 *
	 * @param outDetailId
	 */
	public void removeOutDetail(Long outDetailId);

	/**
	 * ����ID�õ����ⵥ������Ϣ
	 *
	 * @param outDetailId
	 * @return
	 */
	public OutDetail getOutDetail(Long outDetailId);

	/**
	 * ��ѯ���г��ⵥ������Ϣ
	 *
	 * @return
	 */
	public List<OutDetail> getOutDetails();

	/**
	 * ���ݳ��ⵥIDȡ�ö�Ӧ��Ʒ������Ϣ
	 *
	 * @param id
	 *            Long
	 * @param type
	 *            String
	 * @parm relationNum String
	 * @return List
	 * @author chenyan 2009/07/28
	 */
	List<OutDetailGoods> getOutDetailGoodsLists(Long id);

	/**
	 * ���ݳ��ⵥ����IDȡ�û�����Ϣ
	 *
	 * @param outDetailId
	 *            Long
	 * @param type
	 *            String
	 * @return OutDetailBaseInfo
	 * @author chenyan 2009/07/29
	 */
	OutDetailBaseInfo getOutDetailBaseInfo(Long outDetailId, String type);

	/**
	 * ������Ʒʵ��ID�͹�Ӧ�̲�ѯ�������
	 *
	 * @param mapSearch
	 *            map
	 * @return List
	 * @author chenyan 2009/07/29 modified by chenyan 2010/06/13
	 */
	List<OutDepositoryStorage> getOutStorageList(Map mapSearch);

	/**
	 * ������Ʒ�����Ӧ�������Լ������굥״̬
	 *
	 * @param map
	 *            Map
	 * @param type
	 *            String
	 * @return Boolean
	 * @author chenyan 2009/07/29
	 */
	@SuppressWarnings("unchecked")
	Boolean outDepositoryOpt(Map map, String type);

	// added by chenhang �Զ����
	Boolean outDepositoryOptAuto(Map map, String type);

	/**
	 * ���ݳ��ⵥ����IDȡ��δ��ɷ���Ĳ�Ʒ��Ϣ
	 *
	 * @param outDepId
	 *            Long
	 * @param status
	 *            String
	 * @return OutDetailGoods
	 * @author chenyan 2009/07/29 modified by chenyan 2009/09/06
	 */
	List<OutDetailGoods> listOutDetailNotFinish(Long outDepId, String status);

	GatherOutDepository gatherFinanceOutDepositoryCount(
			FinanceDepositoryQuery financeDepositoryQuery);

	QueryPage gatherFinanceOutDepositoryLists(
			FinanceDepositoryQuery financeDepositoryQuery, int currPage,
			int pageSize);

	/**
	 * ��������ݹ����ⵥ����ͳ������
	 *
	 * @author zhangwy
	 * @param parMap
	 * @return
	 */
	GatherOutDepository estimateFinanceOutDepositoryCount(
			FinanceDepositoryQuery financeDepositoryQuery);

	/**
	 * ��������ݹ����ⵥ����ͳ���б�
	 *
	 * @author zhangwy
	 * @param parMap
	 * @param page
	 * @return
	 */
	QueryPage estimateFinanceOutDepositoryLists(
			FinanceDepositoryQuery financeDepositoryQuery, int currPage,
			int pageSize);

	/**
	 * ��������Id��ȡȫ������ϸ�б�
	 *
	 * @author zhangwy
	 * @param outDepositoryId
	 * @return
	 */
	List<OutDetail> getOutDetailListByOutDepositoryId(Long outDepositoryId);
}
