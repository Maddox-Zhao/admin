package com.huaixuan.network.biz.dao.storage;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.storage.GatherOutDepository;
import com.huaixuan.network.biz.domain.storage.OutDetail;
import com.huaixuan.network.biz.domain.storage.OutDetailBaseInfo;
import com.huaixuan.network.biz.domain.storage.OutDetailGoods;
import com.huaixuan.network.biz.domain.storage.query.FinanceDepositoryQuery;
import com.huaixuan.network.biz.query.QueryPage;

/**
 * (bibleUtil auto code generation)
 *
 * @version 3.2.0
 */
public interface OutDetailDao {
	/* @interface model: IossOutDetail */
	Long addOutDetail(OutDetail outDetail) throws Exception;

	/* @interface model: IossOutDetail */
	void editOutDetail(OutDetail outDetail) throws Exception;

	/* @interface model: IossOutDetail */
	void removeOutDetail(Long outDetailId) throws Exception;

	/* @interface model: IossOutDetail,IossOutDetail */
	OutDetail getOutDetail(Long outDetailId) throws Exception;

	/* @interface model: IossOutDetail,IossOutDetail */
	List<OutDetail> getOutDetails() throws Exception;

	void addOutDetails(List<OutDetail> outDetails);

	/**
	 * ���ݳ��ⵥIDȡ�ö�Ӧ��Ʒ������Ϣ
	 *
	 * @param id
	 *            Long
	 * @return List
	 * @author chenyan 2009/07/28
	 */
	List<OutDetailGoods> getOutDetailGoodsLists(Long id);

	/**
	 * ���ݳ��ⵥ����IDȡ�����ۻ���������Ϣ
	 *
	 * @param outDetailId
	 *            Long
	 * @return OutDetailBaseInfo
	 * @author chenyan 2009/07/29
	 */
	OutDetailBaseInfo getOutDetailSalesChangeBaseInfo(Long outDetailId);

	/**
	 * ���ݳ��ⵥ����IDȡ�û�����Ϣ�����ۻ������⣩
	 *
	 * @param outDetailId
	 *            Long
	 * @param type
	 *            String
	 * @return OutDetailBaseInfo
	 * @author chenyan 2009/07/29
	 */
	OutDetailBaseInfo getOutDetailShoppingBaseInfo(Long outDetailId, String type);

	/**
	 * ����ID���³����굥״̬
	 *
	 * @param outDetailId
	 *            Long
	 * @param status
	 *            String
	 * @author chenyan 2009/07/29
	 */
	void updateOutDetailStatusById(Long outDetailId, String status);

	/**
	 * ����ID���³����굥�������
	 *
	 * @param outDetailId
	 *            Long
	 * @param storType
	 *            String
	 * @author shengyong 2010/03/26
	 */
	public void updateOutDetailStorTypeById(Long outDetailId, String storType);

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

	/**
	 * ����ʣ����
	 *
	 * @param outDetailGoodsInfo
	 * @throws Exception
	 */
	void editOutOutDetailGoods(OutDetailGoods outDetailGoodsInfo)
			throws Exception;

	GatherOutDepository gatherFinanceOutDepositoryCount(
			FinanceDepositoryQuery financeDepositoryQuery) throws Exception;

	QueryPage gatherFinanceOutDepositoryLists(FinanceDepositoryQuery financeDepositoryQuery,
			int currentPage, int pageSize) throws Exception;

	/**
	 * ��������ݹ����ⵥ����ͳ������
	 *
	 * @author zhangwy
	 * @param parMap
	 * @return
	 */
	GatherOutDepository estimateFinanceOutDepositoryCount(
			FinanceDepositoryQuery financeDepositoryQuery) throws Exception;

	/**
	 * ��������ݹ����ⵥ����ͳ���б�
	 *
	 * @author zhangwy
	 * @param parMap
	 * @param page
	 * @return
	 */
	QueryPage estimateFinanceOutDepositoryLists(FinanceDepositoryQuery financeDepositoryQuery,
			int currentPage, int pageSize) throws Exception;

	/**
	 * ��������Id��ȡȫ������ϸ�б�
	 *
	 * @param outDepositoryId
	 * @return
	 * @throws Exception
	 */
	List<OutDetail> getOutDetailListByOutDepositoryId(Long outDepositoryId)
			throws Exception;
}
