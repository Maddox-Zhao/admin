package com.huaixuan.network.biz.service.express;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.express.Region;

/**
 * 锟斤拷锟斤拷锟皆讹拷锟斤拷锟(bibleUtil auto code generation)
 * 
 * @version 3.2.0
 */
public interface RegionManager {

	/**
	 * 根据CODE获取区域名称
	 * 
	 * @param code
	 * @return
	 */
	public String getCodeName(String code);

	/**
	 * 根据Code 获取孩子区域
	 * 
	 * @param code
	 * @return
	 */
	public List<Region> getRegionChilds(String code);

	/**
	 * 根据类型获取区域集合
	 * 
	 * @param type
	 * @return
	 */
	public List<Region> getRegionByType(int type);

	/**
	 * 
	 * @param type
	 * @return
	 */
	public List<Region> getRegion(Region region);

	/**
	 * 根据省代码，获取地址名称
	 * 
	 * @param provinceCode
	 * @return
	 */
	public String getAddressInfo(String provinceCode);

	/**
	 * 根据省、市代码，获取地址名称
	 * 
	 * @param provinceCode
	 * @param cityCode
	 * @return
	 */
	public String getAddressInfo(String provinceCode, String cityCode);

	/**
	 * 根据省、市、县场代码，获取地址名称
	 * 
	 * @param provinceCode
	 * @param cityCode
	 * @param districtCode
	 * @return
	 */
	public String getAddressInfo(String provinceCode, String cityCode, String districtCode);

	/**
	 * 根据省、市、县场代码，获取地址名称 并用contactStr来连接
	 * 
	 * @param provinceCode
	 * @param cityCode
	 * @param districtCode
	 * @param contactStr
	 * @return
	 */
	public String getAddressInfo(String provinceCode, String cityCode, String districtCode, String contactStr);

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
	 * 根据id取得地区信息
	 * 
	 * @param regionId
	 * @return @
	 */
	Region getRegion(Long regionId);

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
	 * 根据一级仓库ID得到地区信息
	 * 
	 * @param depositoryId
	 * @return Region Object @
	 */
	List<Region> getRegionByFirstDepositoryId(String depositoryId);
	
	/**
	 * 获取特殊省市的二级县市
	 * @param specialCode
	 * @return
	 */
	List<Region> getSpecialRegion(Map parMap);
}
