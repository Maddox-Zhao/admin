package com.huaixuan.network.biz.service.storage;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.storage.GatherInDepository;
import com.huaixuan.network.biz.domain.storage.GoodsForLocation;
import com.huaixuan.network.biz.domain.storage.InDetail;
import com.huaixuan.network.biz.domain.storage.InDetailBaseInfo;
import com.huaixuan.network.biz.domain.storage.InDetailGoods;
import com.huaixuan.network.biz.domain.storage.query.FinanceDepositoryQuery;
import com.huaixuan.network.biz.domain.storage.query.GatherQuery;
import com.huaixuan.network.biz.query.QueryPage;

/**
 * ��ⵥ����(bibleUtil auto code generation)
 *
 * @version 3.2.0
 */
public interface InDetailManager {
	/**
	 * ������ⵥ������Ϣ
	 *
	 * @param inDetail
	 * @return
	 */
	public long addInDetail(InDetail inDetail);

	/**
	 * �༭��ⵥ������Ϣ
	 *
	 * @param inDetail
	 */
	public void editInDetail(InDetail inDetail);

	/**
	 * ɾ����ⵥ������Ϣ
	 *
	 * @param inDetailId
	 */
	public void removeInDetail(Long inDetailId);

	/**
	 * ����ID��ѯ��ⵥ������Ϣ
	 *
	 * @param inDetailId
	 * @return
	 */
	public InDetail getInDetail(Long inDetailId);

	/**
	 * ��ѯ������ⵥ������Ϣ
	 *
	 * @return
	 */
	public List<InDetail> getInDetails();

	/**
	 * ������ⵥIDȡ�ö�Ӧ��Ʒ������Ϣ
	 *
	 * @param id
	 *            Long
	 * @param type
	 *            String
	 * @parm relationNum String
	 * @return List
	 * @author chenyan 2009/07/22
	 */
	List<InDetailGoods> getInDetailGoodsLists(Long id, String type, String relationNum);

	/**
	 * ������Ʒ�ɷ���Ŀ�λ
	 *
	 * @param id
	 *            Long
	 * @param depFirstId
	 *            Long
	 * @return List
	 * @author chenyan 2009/07/22 modified by chenyan 2010/03/16
	 */
	List<GoodsForLocation> getLocationForGoods(Long id, Long depFirstId);

	/**
	 * ��ѯ�ɷ���Ŀ�λ��������Ʒ��Ӧ��
	 *
	 * @param id
	 *            Long
	 * @param depFirstId
	 *            Long
	 * @return List
	 * @author chenyan 2009/07/22 modified by chenyan 2010/03/16
	 */
	List<GoodsForLocation> getLocationForGoodsNoMatch(Long id, Long depFirstId);

	/**
	 * ���������������ݿ�����
	 *
	 * @param inDetailInfo
	 *            InDetail
	 * @param mapForUpdate
	 *            Map
	 * @param inDetailBaseInfo
	 *            InDetailBaseInfo
	 * @author chenyan 2009/07/23 modified by chenyan 2010/03/17
	 */
	void updateInDetailRelationIn(InDetail inDetailInfo, Map<String, String[]> mapForUpdate,
			InDetailBaseInfo inDetailBaseInfo);

	/**
	 * ������ⵥ����IDȡ��δ��ɷ���Ĳ�Ʒ��
	 *
	 * @param inDepId
	 *            Long
	 * @param status
	 *            String
	 * @return List
	 * @author chenyan 2009/07/23
	 */
	List<InDetailGoods> listInDetailNotFinish(Long inDepId, String status);

	/**
	 * ȡ���̵����ʱ��Ļ�����Ϣ
	 *
	 * @param inDetailId
	 *            Long
	 * @return InDetailBaseInfo
	 * @author chenyan 2009/07/24
	 */
	InDetailBaseInfo getCheckInDetailBaseInfo(Long inDetailId);

	/**
	 * ȡ�òɹ������˻������ʱ��Ļ�����Ϣ
	 *
	 * @param inDetailId
	 *            Long
	 * @return InDetailBaseInfo
	 * @author chenyan 2009/07/24
	 */
	InDetailBaseInfo getShoppingOrSalesInDetailBaseInfo(Long inDetailId, String type);

	/**
	 * ����������Ʒ�ɷ���Ŀ�λ
	 *
	 * @param id
	 *            Long
	 * @return List
	 * @author chenyan 2009/07/25
	 */
	List<GoodsForLocation> getSalesLocationForGoods(Long id);

	/**
	 * ��������굥IDȡ�÷���õĲ�Ʒ�б�
	 *
	 * @param inDetailId
	 *            Long
	 * @return List
	 * @author chenyan 2009/08/12
	 */
	List<GoodsForLocation> listInDetailForDisByDetailId(Long inDetailId);

	/**
	 * ��������굥IDȡ���ѽ�����λ�����Ķ�Ӧ��Ʒʵ������
	 *
	 * @param inDetailId
	 *            Long
	 * @return int
	 * @author chenyan 2009/08/13
	 */
	int getGoodsLocationCountByInDetailId(Long inDetailId);

	/**
	 * ���������õ�������ⵥ�б�����
	 *
	 * @param parMap
	 * @return
	 */
	public int gatherInDepositoryListsCount(Map<String, String> parMap);

	 /**
	 * ���������õ�������ⵥ�б�
	 *
	 * @param parMap
	 * @return
	 */
	 public QueryPage gatherInDepositoryLists(GatherQuery gatherQuery, int currPage, int pageSize, boolean isPage);

	/**
	 * ���������õ�������ⵥ�����б�����
	 *
	 * @param parMap
	 * @return
	 */
	public GatherInDepository gatherFinanceInDepositoryCount(FinanceDepositoryQuery financeDepositoryQuery);

	 /**
	 * ���������õ�������ⵥ�����б�
	 *
	 * @param parMap
	 * @return
	 */
	 public QueryPage gatherFinanceInDepositoryLists(FinanceDepositoryQuery financeDepositoryQuery, int currPage, int pageSize);

	/**
	 * ���������õ��ݹ���ⵥ�����б�����
	 *
	 * @param parMap
	 * @return
	 */
	public GatherInDepository estimateFinanceInDepositoryCount(FinanceDepositoryQuery financeDepositoryQuery);

	 /**
	 * ���������õ��ݹ���ⵥ�����б�
	 *
	 * @param parMap
	 * @return
	 */
	 public QueryPage estimateFinanceInDepositoryLists(FinanceDepositoryQuery financeDepositoryQuery, int currPage, int pageSize);

	/**
	 * ��������굥IDȡ��һ���ֿ�Ϳ�����ͣ��ɹ�������ͣ�
	 *
	 * @param inDetailId
	 *            Long
	 * @return InDetailBaseInfo
	 * @author chenyan 2010/03/17
	 */
	InDetailBaseInfo getInfoByInDetailIdForShoppingList(Long inDetailId);

	/**
	 * ��ѯ�ɷ���Ŀ�λ����Ʒ�⣩
	 *
	 * @param id
	 * @param depFirstId
	 * @param loc_id
	 * @return List
	 * @author zhangwy
	 */
	List<GoodsForLocation> getLocationForDefect(Long id, Long locId);
}
