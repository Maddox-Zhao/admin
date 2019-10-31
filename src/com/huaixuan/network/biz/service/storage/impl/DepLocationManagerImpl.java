package com.huaixuan.network.biz.service.storage.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gargoylesoftware.htmlunit.Page;
import com.huaixuan.network.biz.dao.storage.DepLocationDao;
import com.huaixuan.network.biz.dao.storage.DepositoryDao;
import com.huaixuan.network.biz.domain.storage.DepLocation;
import com.huaixuan.network.biz.domain.storage.Depository;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.service.storage.DepLocationManager;

@Service("depLocationManager")
public class DepLocationManagerImpl implements DepLocationManager {
	Log log = LogFactory.getLog(this.getClass());

	@Autowired
	private DepLocationDao depLocationDao;
	@Autowired
	private DepositoryDao depositoryDao;

	public Long addDepLocation(DepLocation depLocation) {
		log.info("DepLocationManagerImpl.addDepLocation method");
		try {
			return this.depLocationDao.addDepLocation(depLocation);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see
	 * com.hundsun.bible.facade.ios.DepLocationManager#editDepLocation(com.hundsun.bible.domain.model.ios.DepLocation)
	 */
	public void editDepLocation(DepLocation depLocation) {
		log.info("DepLocationManagerImpl.editDepLocation method");
		try {
			this.depLocationDao.editDepLocation(depLocation);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}

	public List<DepLocation> getAllLocations() {
		try {
			return this.depLocationDao.getAll();
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.hundsun.bible.facade.ios.DepLocationManager#getAllDepLocationByMap()
	 */
	public List<DepLocation> getAllDepLocationByMap(Map<String, String> parMap) {
		try {
			return this.depLocationDao.getAllDepLocationByMap(parMap);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see com.hundsun.bible.facade.ios.DepLocationManager#getCountByParMap(java.util.Map)
	 */
	public int getCountByParMap(Map parMap) {
		log.info("DepLocationManagerImpl.getCountByParMap method");
		try {
			return depLocationDao.getCountByParMap(parMap);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return 0;
	}

	public QueryPage getDepLocationsByParMap(Map parameterMap, int currPage, int pageSize) {
		return this.depLocationDao.getDepLocationsByParMap(parameterMap, currPage, pageSize);
	}

	/*
	 * （非 Javadoc＄1717
	 * 
	 * @see com.hundsun.bible.facade.ios.DepLocationManager#getLocationsByDepositoryId()
	 */
	public List<DepLocation> getLocationsByDepositoryId(Long depositoryId) {
		log.info("DepLocationManagerImpl.getLocationsByDepositoryId method");
		try {
			return this.depLocationDao.getLocationsByDepositoryId(depositoryId);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}

	public Depository getDepositoryByLocationId(Long locationId) {
		log.info("DepLocationManagerImpl.getDepositoryByLocationId method");
		try {
			DepLocation dl = depLocationDao.getLocationsById(locationId);
			if (dl != null) {
				Long depId = dl.getDepId();
				Depository result = depositoryDao.getDepository(depId);
				//modified by chenyan 2011/03/03 增加了对库位名称的赋值
                result.setLocName(dl.getLocName());
				return result;
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}

	public DepLocation getDepLocationByLocationId(Long locationId) {
		log.info("DepLocationManagerImpl.getDepLocationByLocationId method");
		try {
			DepLocation dl = depLocationDao.getLocationsById(locationId);
			return dl;
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}

	public DepLocation getDepLocationByLocationName(String locationName) {
		log.info("DepLocationManagerImpl.getDepLocationByLocationName method");
		try {
			DepLocation dl = depLocationDao.getLocationsByName(locationName);
			return dl;
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}

	public List<DepLocation> getCheckLocationInfo(Long goodsInstanceId, String isCheck, Long depFirstId) {
		log.info("DepLocationManagerImpl.getCheckLocationInfo method");
		try {
			return depLocationDao.getCheckLocationInfo(goodsInstanceId, isCheck, depFirstId);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}

	public int getIsCheckCountById(Long id, String isCheck) {
		log.info("DepLocationManagerImpl.getIsCheckCountById method");
		try {
			return depLocationDao.getIsCheckCountById(id, isCheck);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return 0;
	}

	public void lockDepLocation(DepLocation depLocation) {
		log.info("DepLocationManagerImpl.lockDepLocation method");
		try {
			depLocationDao.lockDepLocation(depLocation);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}

	public List<DepLocation> listUseabledLocInfo(Long depFirstId) {
		log.info("DepLocationManagerImpl.listUseabledLocInfo method");
		try {
			return depLocationDao.listUseabledLocInfo(depFirstId);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see com.hundsun.bible.facade.ios.DepLocationManager#countStorageByDepLocId(java.lang.Long)
	 */
	public int countStorageByDepLocId(Long depLocId) {
		log.info("DepLocationManagerImpl.countStorageByDepLocId method");
		try {
			return depLocationDao.countStorageByDepLocId(depLocId);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return 0;
	}

	public List<DepLocation> getRightLocationsByDepositoryId(Long depositoryId) {
		log.info("DepLocationManagerImpl.getRightLocationsByDepositoryId method");
		try {
			return this.depLocationDao.getRightLocationsByDepositoryId(depositoryId);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}

	public List<DepLocation> getAllRightLocations() {
		log.info("DepLocationManagerImpl.getAllRightLocations method");
		try {
			return depLocationDao.getAllRightLocations();
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}

	@Override
	public List<DepLocation> getRightDeplocationByDepfirstId(Long depFirstId) {
		return depLocationDao.getRightDeplocationByDepfirstId(depFirstId);
	}

}
