package com.huaixuan.network.biz.service.storage;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.storage.OutDepository;
import com.huaixuan.network.biz.domain.storage.query.GatherQuery;
import com.huaixuan.network.biz.query.QueryPage;

public interface OutDepositoryManager {
	/**
	 * 新增出库单基本信�
	 *
	 * @param outDepository
	 * @return
	 */
	public Long addOutDepository(OutDepository outDepository);

	/**
	 * 编辑出库单基本信�
	 *
	 * @param outDepository
	 */
	public void editOutDepository(OutDepository outDepository);

	// /**
	// * 删除出库单基本信�
	// *
	// * @param outDepositoryId
	// */
	// public void removeOutDepository(Long outDepositoryId);

	/**
	 * 根据ID查询出库单基本信�
	 *
	 * @param outDepositoryId
	 * @return
	 */
	public OutDepository getOutDepository(Long outDepositoryId);

	QueryPage getActualInventoryLists(Map<String, String> parMap,
			int currentPage, int pageSize, boolean isPage);

	//
	// /**
	// * 查询全部出库单基本信�
	// *
	// * @return
	// */
	// public List<OutDepository> getOutDepositorys();

	/**
	 * �索待出库的出库单总数
	 *
	 * @return int
	 * @author chenyan 2009/07/28
	 */
	int getOutDepositoryListsCount(Map<String, String> parMap);

	/**
	 * �索待出库的出库单列表信息
	 *
	 * @param parMap
	 *            Map
	 * @param page
	 *            Page
	 * @return List
	 * @author chenyan 2009/07/28
	 */
	QueryPage getOutDepositoryLists(Map<String, String> parMap,
			int currentPage, int pageSize, boolean isPage);

	/**
	 * 完成出库的事务操�
	 *
	 * @param map
	 *            Map
	 * @return Boolean 操作成功标识
	 * @author chenyan 2009/07/29 @
	 */
	@SuppressWarnings("unchecked")
	Boolean removeStorageOpt(Map map) throws Exception;

	// /**
	// * 根据出库详单ID�索出库单状�
	// *
	// * @param outDetailId
	// * Long
	// * @return String
	// * @author chenyan 2009/08/11
	// */
	// String getOutDepositoryStatusByDetailId(Long outDetailId);

	/**
	 * 根据出库主单信息更新物流单号
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
	 * @param actualInventory
	 *            Double
	 * @param reNum
	 *            String
	 * @return int
	 * @author chenhang 2010/11/18
	 */
	int updateActualInventoryById(Double actualInventory, String reNum);

	/**
	 * @param actualWeight
	 *            Double
	 * @param id
	 *            String
	 * @return int
	 * @author chenhang 2010/11/18
	 */
	int updateActualWeightById(Double actualWeight, String id);

	/**
	 * @param castWeight
	 *            Double
	 * @param id
	 *            String
	 * @return int
	 * @author chenhang 2010/11/18
	 */
	int updateCastWeightById(Double castWeight, String id);

	/**
	 * @param actualInventory
	 *            Double
	 * @param reNum
	 *            String
	 * @return int
	 * @author chenhang 2010/11/18
	 */
	int updateActualInventoryByIdRe(Double actualInventory, String reNum);

	/**
	 * 获取全部出库详单
	 *
	 * @return list
	 * @author zhangwy 2009/09/10
	 */
	public List<OutDepository> getOutDepositorysWithDetail(Map parmap);

	// /**
	// * 获取全部出库详单数量
	// *
	// * @param parmap
	// * @return int
	// * @author zhangwy 2009/09/15
	// */
	// int getOutDepositorysWithDetailCount(Map parmap);
	//
	// /**
	// * 根据条件得到汇�出库单列表数量
	// *
	// * @param parMap
	// * @return
	// */
	// public int gatherOutDepositoryListsCount(Map<String, String> parMap);

	/**
	 * 根据条件得到汇�出库单列表
	 *
	 * @param parMap
	 * @return
	 */
	public QueryPage gatherOutDepositoryLists(GatherQuery gatherQuery,
			int currPage, int pageSize, boolean isPage);

	/**
	 * 根据tid获取物流单号
	 *
	 * @param outDepositoryId
	 * @return
	 */
	public OutDepository getOutDepositoryByTid(String tid);

	// /**
	// * 根据expressCode获取OutDepository
	// *
	// * @param outDepositoryId
	// * @return
	// */
	// public List<OutDepository> getOutDepositoryByExpressCode(String
	// expressCode);

	/**
	 * 根据详单id获取主表数据
	 *
	 * @param detailId
	 * @return
	 */
	public OutDepository getOutDepositoryByDetailId(Long detailId);

	/**
	 * @Title: updateIsOutDepositoryPrintedById
	 * @Description: ����isOutDepositoryPrinted�ֶ�
	 * @param ids
	 * @return int @
	 */
	public int updateIsOutDepositoryPrintedById(String[] ids);

	/**
	 * @Title: updateIsExpressPrintedById
	 * @Description: ����isExpressPrinted�ֶ�
	 * @param ids
	 * @return int @
	 */
	public int updateIsExpressPrintedById(String[] ids);

	/**
	 * @return OutDepAnalysis
	 * @author chenhang 2011/01/11
	 */
	public QueryPage getOutDepAnalysis(Map<String, String> outDepParam,
			int currPage, int pageSize, boolean isPage);

	/**
	 * ����ͳ������
	 *
	 * @return int
	 * @author chenhang 2011/01/17
	 */
	public int getOutDepAnalysisCount(Map outDepParam);
	
    /**
     * ���³��ⵥ������
     * @param handleAdminId Long
     * @param outDepositoryId Long
     * @return int ����ɹ�����
     * @author chenyan 2011/03/25
     */
    int updateHandleAdminIdByUser(Long handleAdminId, Long outDepositoryId);
}
