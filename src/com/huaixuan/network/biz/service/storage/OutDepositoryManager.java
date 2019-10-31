package com.huaixuan.network.biz.service.storage;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.storage.OutDepository;
import com.huaixuan.network.biz.domain.storage.query.GatherQuery;
import com.huaixuan.network.biz.query.QueryPage;

public interface OutDepositoryManager {
	/**
	 * 鏂板鍑哄簱鍗曞熀鏈俊鎭
	 *
	 * @param outDepository
	 * @return
	 */
	public Long addOutDepository(OutDepository outDepository);

	/**
	 * 缂栬緫鍑哄簱鍗曞熀鏈俊鎭
	 *
	 * @param outDepository
	 */
	public void editOutDepository(OutDepository outDepository);

	// /**
	// * 鍒犻櫎鍑哄簱鍗曞熀鏈俊鎭
	// *
	// * @param outDepositoryId
	// */
	// public void removeOutDepository(Long outDepositoryId);

	/**
	 * 鏍规嵁ID鏌ヨ鍑哄簱鍗曞熀鏈俊鎭
	 *
	 * @param outDepositoryId
	 * @return
	 */
	public OutDepository getOutDepository(Long outDepositoryId);

	QueryPage getActualInventoryLists(Map<String, String> parMap,
			int currentPage, int pageSize, boolean isPage);

	//
	// /**
	// * 鏌ヨ鍏ㄩ儴鍑哄簱鍗曞熀鏈俊鎭
	// *
	// * @return
	// */
	// public List<OutDepository> getOutDepositorys();

	/**
	 * 妫绱㈠緟鍑哄簱鐨勫嚭搴撳崟鎬绘暟
	 *
	 * @return int
	 * @author chenyan 2009/07/28
	 */
	int getOutDepositoryListsCount(Map<String, String> parMap);

	/**
	 * 妫绱㈠緟鍑哄簱鐨勫嚭搴撳崟鍒楄〃淇℃伅
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
	 * 瀹屾垚鍑哄簱鐨勪簨鍔℃搷浣
	 *
	 * @param map
	 *            Map
	 * @return Boolean 鎿嶄綔鎴愬姛鏍囪瘑
	 * @author chenyan 2009/07/29 @
	 */
	@SuppressWarnings("unchecked")
	Boolean removeStorageOpt(Map map) throws Exception;

	// /**
	// * 鏍规嵁鍑哄簱璇﹀崟ID妫绱㈠嚭搴撳崟鐘舵
	// *
	// * @param outDetailId
	// * Long
	// * @return String
	// * @author chenyan 2009/08/11
	// */
	// String getOutDepositoryStatusByDetailId(Long outDetailId);

	/**
	 * 鏍规嵁鍑哄簱涓诲崟淇℃伅鏇存柊鐗╂祦鍗曞彿
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
	 * 鑾峰彇鍏ㄩ儴鍑哄簱璇﹀崟
	 *
	 * @return list
	 * @author zhangwy 2009/09/10
	 */
	public List<OutDepository> getOutDepositorysWithDetail(Map parmap);

	// /**
	// * 鑾峰彇鍏ㄩ儴鍑哄簱璇﹀崟鏁伴噺
	// *
	// * @param parmap
	// * @return int
	// * @author zhangwy 2009/09/15
	// */
	// int getOutDepositorysWithDetailCount(Map parmap);
	//
	// /**
	// * 鏍规嵁鏉′欢寰楀埌姹囨诲嚭搴撳崟鍒楄〃鏁伴噺
	// *
	// * @param parMap
	// * @return
	// */
	// public int gatherOutDepositoryListsCount(Map<String, String> parMap);

	/**
	 * 鏍规嵁鏉′欢寰楀埌姹囨诲嚭搴撳崟鍒楄〃
	 *
	 * @param parMap
	 * @return
	 */
	public QueryPage gatherOutDepositoryLists(GatherQuery gatherQuery,
			int currPage, int pageSize, boolean isPage);

	/**
	 * 鏍规嵁tid鑾峰彇鐗╂祦鍗曞彿
	 *
	 * @param outDepositoryId
	 * @return
	 */
	public OutDepository getOutDepositoryByTid(String tid);

	// /**
	// * 鏍规嵁expressCode鑾峰彇OutDepository
	// *
	// * @param outDepositoryId
	// * @return
	// */
	// public List<OutDepository> getOutDepositoryByExpressCode(String
	// expressCode);

	/**
	 * 鏍规嵁璇﹀崟id鑾峰彇涓昏〃鏁版嵁
	 *
	 * @param detailId
	 * @return
	 */
	public OutDepository getOutDepositoryByDetailId(Long detailId);

	/**
	 * @Title: updateIsOutDepositoryPrintedById
	 * @Description: 更新isOutDepositoryPrinted字段
	 * @param ids
	 * @return int @
	 */
	public int updateIsOutDepositoryPrintedById(String[] ids);

	/**
	 * @Title: updateIsExpressPrintedById
	 * @Description: 更新isExpressPrinted字段
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
	 * 出库统计总量
	 *
	 * @return int
	 * @author chenhang 2011/01/17
	 */
	public int getOutDepAnalysisCount(Map outDepParam);
	
    /**
     * 更新出库单处理人
     * @param handleAdminId Long
     * @param outDepositoryId Long
     * @return int 处理成功数量
     * @author chenyan 2011/03/25
     */
    int updateHandleAdminIdByUser(Long handleAdminId, Long outDepositoryId);
}
