package com.huaixuan.network.biz.dao.storage;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.storage.DepLocation;
import com.huaixuan.network.biz.query.QueryPage;

public interface DepLocationDao {

	/* @interface model: 新增库位信息 */
	Long addDepLocation(DepLocation depLocation);

	/* @interface model: 编辑库位信息 */
	void editDepLocation(DepLocation depLocation);

	public List<DepLocation> getAll();

	public List<DepLocation> getAllDepLocationByMap(Map<String, String> parMap);

	/*
	 * 根据条件查询仓位记录集合
	 */
	public QueryPage getDepLocationsByParMap(Map parameterMap, int currentPage, int pageSize);

	/*
	 * 根据条件查询仓位记录数量
	 */
	public int getCountByParMap(Map<String, Object> parMap);

	/*
	 * 根据仓库ID得到这个仓库的所有库佄1717
	 */
	List<DepLocation> getLocationsByDepositoryId(Long depositoryId);

	DepLocation getLocationsById(Long id);

	DepLocation getLocationsByName(String name);

	/**
	 * 根据商品实例ID取得库位表信恄17
	 * 
	 * @param goodsInstanceId
	 *            Long
	 * @param isCheck
	 *            String
	 * @param depFirstId
	 *            Long
	 * @return List
	 * @author chenyan 2009/07/27 modified by chenyan 2010/03/18
	 */
	List<DepLocation> getCheckLocationInfo(Long goodsInstanceId, String isCheck, Long depFirstId);

	/**
	 * 取得是否在盘点中的数釄17
	 * 
	 * @param id
	 *            Long
	 * @param isCheck
	 *            String
	 * @return int
	 * @author chenyan 2009/07/27
	 */
	int getIsCheckCountById(Long id, String isCheck);

	/**
	 *  ischeck
	 * 
	 * @param depLocation
	 */
	void lockDepLocation(DepLocation depLocation);

	/**
	 * 取得可用的库位信息列表
	 * 
	 * @param depFirstId
	 *            Long
	 * @return List
	 * @author chenyan 2009/08/13 modified by chenyan 2010/03/18
	 */
	List<DepLocation> listUseabledLocInfo(Long depFirstId);

	/**
	 * 判断某库位上是否有货物
	 * 
	 * @param depLocId
	 * @return
	 */
	int countStorageByDepLocId(Long depLocId);

	/**
	 * 根据仓库ID得到这个仓库的所有没有被删除的库位
	 * 
	 * @param depositoryId
	 * @return
	 */
	List<DepLocation> getRightLocationsByDepositoryId(Long depositoryId);

	/**
	 * 获取所有的非删除的库位名称(包含盘点)
	 * 
	 * @return
	 */
	List<DepLocation> getAllRightLocations();
	
	/**
	 * 获取配货的时候能用的库位
	 * @param depFirstId
	 * @return
	 */
    List<DepLocation> getRightDeplocationByDepfirstId(Long depFirstId);
}
