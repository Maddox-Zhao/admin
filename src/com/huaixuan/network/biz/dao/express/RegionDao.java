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
	 * ����codeȡ����λ��Ϣ
	 *
	 * @param code
	 *            String
	 * @return Region @
	 * @author chenyan 2009/08/07
	 */
	Region getRegionByCode(String code);

	/**
	 * ������һ������������ҵ�����Ϣ
	 *
	 * @param parentCode
	 *            String
	 * @return List @
	 * @author chenyan 2009/08/07
	 */
	List<String> listRegionCodeByParentCode(String parentCode);

	/**
	 * ����cityȡ���ӵ�����Ϣ
	 *
	 * @param parentCode
	 *            String
	 * @return List @
	 * @author chenyan 2009/08/07
	 */
	List<String> listTwoRegionCodeByParentCode(String parentCode);

	/**
	 * ����regionȡ�õ�����Ϣ
	 *
	 * @param region
	 * @return @
	 */
	List<Region> getRegion(Region region);

	/**
	 * ����codeȡ���ӵ�����Ϣ�򸸵�����Ϣ
	 *
	 * @param parentCode
	 *            Map
	 * @return List @
	 * @author wangkun 2010/08/07
	 */
	List<Region> getRegionByName(Map region);

	/**
	 * �������Ƶõ�������Ϣ
	 *
	 * @param name
	 * @return @
	 */
	Region getRegionByName(String regionName);

	Region getRegionByNameAndType(String regionName, Integer regionType);

	/**
	 * �������ƺ����ͻ�õ�����Ϣ
	 *
	 * @author chenhang 2011-5-13
	 * @description
	 * @return
	 */
	Region getRegionByNameAndTypeAndCode(String regionName, Integer regionType,
			String parentCode);

	/**
	 * ����һ���ֿ�ID�õ�������Ϣ
	 *
	 * @param depositoryId
	 * @return Region Object @
	 */
	List<Region> getRegionByFirstDepositoryId(String depositoryId);

	/**
	 * ��ȡ����ʡ�еĶ�������
	 *
	 * @param specialCode
	 * @return
	 */
	List<Region> getSpecialRegion(Map parMap);

}
