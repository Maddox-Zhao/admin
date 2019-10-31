package com.huaixuan.network.biz.service.express.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.express.RegionDao;
import com.huaixuan.network.biz.domain.express.Region;
import com.huaixuan.network.biz.service.express.RegionManager;

/**
 * ï¿½ï¿½ï¿½ï¿½ï¿½Ô¶ï¿½ï¿½ï¿½ï¿(bibleUtil auto code generation)
 * 
 * @version 3.2.0
 */
@Service("regionManager")
public class RegionManagerImpl implements RegionManager {

	@Autowired
	RegionDao regionDao;

	private final static String CONTACT_STR = "";

	public String getAddressInfo(String provinceCode) {

		return getAddressInfo(provinceCode, null, null, CONTACT_STR);

	}

	public String getAddressInfo(String provinceCode, String cityCode) {
		return getAddressInfo(provinceCode, cityCode, null, CONTACT_STR);
	}

	public String getAddressInfo(String provinceCode, String cityCode, String districtCode) {
		return getAddressInfo(provinceCode, cityCode, districtCode, CONTACT_STR);
	}

	public String getAddressInfo(String provinceCode, String cityCode, String districtCode, String contactStr) {
		// log.info("RegionManagerImpl.getAddressInfo method");

		String addressInfo = "";
		try {
			if (provinceCode != null) {
				Region params = new Region();
				params.setCode(provinceCode);
				Region region = this.regionDao.getRegionByParams(params).get(0);
				addressInfo = region.getRegionName();
			}
			if (cityCode != null) {
				Region params = new Region();
				params.setCode(cityCode);
				Region region = this.regionDao.getRegionByParams(params).get(0);
				addressInfo = addressInfo + contactStr + region.getRegionName();
			}
			if (districtCode != null && !"".equals(districtCode)) {
				Region params = new Region();
				params.setCode(districtCode);
				Region region = this.regionDao.getRegionByParams(params).get(0);
				addressInfo = addressInfo + contactStr + region.getRegionName();
			}

		} catch (Exception e) {
			// log.error(e.getMessage());
		}
		return addressInfo;
	}

	public String getCodeName(String code) {
		// log.info("RegionManagerImpl.getCodeName method");
		if (code == null || code.equals(""))
			return "";
		String name = "";
		try {
			Region params = new Region();
			params.setCode(code);
			Region region = this.regionDao.getRegionByParams(params).get(0);
			if (region != null)
				name = region.getRegionName();
		} catch (Exception e) {
			// log.error(e.getMessage());
		}
		return name;
	}

	public List<Region> getRegionByType(int type) {
		// log.info("RegionManagerImpl.getRegionByType method");

		List<Region> list = null;
		try {
			Region params = new Region();
			params.setRegionType(type);
			list = this.regionDao.getRegionByParams(params);
		} catch (Exception e) {
			// log.error(e.getMessage());
		}
		return list;
	}

	public List<Region> getRegionChilds(String code) {
		// log.info("RegionManagerImpl.getRegionChilds method");
		List<Region> list = null;
		try {
			Region params = new Region();
			params.setParentCode(code);
			list = this.regionDao.getRegionByParams(params);
		} catch (Exception e) {
			// log.error(e.getMessage());
		}
		return list;
	}

	public Region getRegionByCode(String code) {
		// log.info("RegionManagerImpl.getRegionByCode method");
		try {
			return this.regionDao.getRegionByCode(code);
		} catch (Exception e) {
			// log.error(e.getMessage());
		}
		return null;
	}

	public List<String> listRegionCodeByParentCode(String parentCode) {
		// log.info("RegionManagerImpl.listRegionCodeByParentCode method");
		try {
			return this.regionDao.listRegionCodeByParentCode(parentCode);
		} catch (Exception e) {
			// log.error(e.getMessage());
		}
		return null;
	}

	public List<String> listTwoRegionCodeByParentCode(String parentCode) {
		// log.info("RegionManagerImpl.listTwoRegionCodeByParentCode method");
		try {
			return this.regionDao.listTwoRegionCodeByParentCode(parentCode);
		} catch (Exception e) {
			// log.error(e.getMessage());
		}
		return null;
	}

	public Region getRegion(Long regionId) {
		// log.info("RegionManagerImpl.getRegion method");
		try {
			return this.regionDao.getRegion(regionId);
		} catch (Exception e) {
			// log.error(e.getMessage());
		}
		return null;
	}

	public List<Region> getRegion(Region region) {
		return this.regionDao.getRegion(region);
	}

	public List<Region> getRegionByName(Map region) {
		// log.info("RegionManagerImpl.getRegionByName method");
		return this.regionDao.getRegionByName(region);
	}

	public List<Region> getRegionByFirstDepositoryId(String depositoryId) {
		return this.regionDao.getRegionByFirstDepositoryId(depositoryId);
	}

	@Override
	public List<Region> getSpecialRegion(Map parMap) {
		return regionDao.getSpecialRegion(parMap);
	}

}
