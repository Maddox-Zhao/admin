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
	 * 检索待出库的出库单总数
	 *
	 * @param parMap
	 *            Map
	 * @return int
	 * @author chenyan 2009/07/28
	 */
	int getOutDepositoryListsCount(Map<String, String> parMap);

	/**
	 * 检索待出库的出库单列表信息
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
	 * 根据ID更新状态
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
	 * 根据ID更新实际重量
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
	 * 根据ID更新计抛重量
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
	 * 根据出库详单ID检索出库单状态
	 *
	 * @param outDetailId
	 *            Long
	 * @return String
	 * @author chenyan 2009/08/11
	 */
	String getOutDepositoryStatusByDetailId(Long outDetailId);

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
	 * 获取全部的出库详单
	 *
	 * @return list
	 * @author zhangwy 2009/09/10
	 *
	 */
	List<OutDepository> getOutDepositorysWithDetail(Map parmap);

	/**
	 * 获取全部出库详单总数
	 *
	 * @param parmap
	 * @return int
	 * @author zhangwy 2009/09/16
	 */
	int getOutDepositorysWithDetailCount(Map parmap);

	/**
	 * 根据条件得到汇总出库单列表数量
	 *
	 * @param parMap
	 * @return
	 */
	int gatherOutDepositoryListsCount(Map parMap);

	/**
	 * 根据条件得到汇总出库单列表
	 *
	 * @param parMap
	 * @return
	 */
	QueryPage gatherOutDepositoryLists(Map parMap, int currentPage,
			int pageSize, boolean isPage);

	OutDepository getOutDepositoryByTid(String tid);

	/**
	 * 根据详单id获取主表数据
	 *
	 * @param detailId
	 * @return
	 */
	OutDepository getOutDepositoryByDetailId(Long detailId);

	List<OutDepository> getOutDepositoryByExpressCode(String expressCode);

	/**
	 * @Title: updateIsOutDepositoryPrintedById
	 * @Description: 更新isOutDepositoryPrinted字段
	 * @param ids
	 * @return int @
	 */
	int updateIsOutDepositoryPrintedById(String[] ids);

    /**
    * @Title: updateIsExpressPrintedById
    * @Description: 更新isExpressPrinted字段
    * @param ids
    * @return int
    * @
     */
    int updateIsExpressPrintedById(String[] ids) ;
    
    /**
     * 更新出库单处理人
     * @param handleAdminId Long
     * @param outDepositoryId Long
     * @return int 处理成功数量
     * @author chenyan 2011/03/25
     */
    int updateHandleAdminIdByUser(Long handleAdminId, Long outDepositoryId);
}
