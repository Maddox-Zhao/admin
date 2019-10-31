package com.huaixuan.network.biz.dao.express;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.express.Region;

/**
 *
 * @version 3.2.0
 */
public interface RegionDao {
	void addRegion(Region region);

	void editRegion(Region region);

	void removeRegion(Long regionId);

	Region getRegion(Long regionId);

	List<Region> getRegions();

	/**
	 *
	 * @param region
	 * @return @
	 */
	List<Region> getRegionByParams(Region region);

	/**
	 * 根据code取得区位信息
	 *
	 * @param code
	 *            String
	 * @return Region @
	 * @author chenyan 2009/08/07
	 */
	Region getRegionByCode(String code);

	/**
	 * 根据上一级地区编码查找地区信息
	 *
	 * @param parentCode
	 *            String
	 * @return List @
	 * @author chenyan 2009/08/07
	 */
	List<String> listRegionCodeByParentCode(String parentCode);

	/**
	 * 根据city取得子地区信息
	 *
	 * @param parentCode
	 *            String
	 * @return List @
	 * @author chenyan 2009/08/07
	 */
	List<String> listTwoRegionCodeByParentCode(String parentCode);

	/**
	 * 根据region取得地区信息
	 *
	 * @param region
	 * @return @
	 */
	List<Region> getRegion(Region region);

	/**
	 * 根据code取得子地区信息或父地区信息
	 *
	 * @param parentCode
	 *            Map
	 * @return List @
	 * @author wangkun 2010/08/07
	 */
	List<Region> getRegionByName(Map region);

	/**
	 * 根据名称得到地区信息
	 *
	 * @param name
	 * @return @
	 */
	Region getRegionByName(String regionName);

	Region getRegionByNameAndType(String regionName, Integer regionType);

	/**
	 * 根据名称和类型获得地区信息
	 *
	 * @author chenhang 2011-5-13
	 * @description
	 * @return
	 */
	Region getRegionByNameAndTypeAndCode(String regionName, Integer regionType,
			String parentCode);

	/**
	 * 根据一级仓库ID得到地区信息
	 *
	 * @param depositoryId
	 * @return Region Object @
	 */
	List<Region> getRegionByFirstDepositoryId(String depositoryId);

	/**
	 * 获取特殊省市的二级县市
	 *
	 * @param specialCode
	 * @return
	 */
	List<Region> getSpecialRegion(Map parMap);

}
