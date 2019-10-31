/**
 * @Title: DepositoryServiceImpl.java
 * @Package com.huaixuan.network.biz.service.storage.impl
 * @Description: TODO
 * @author liuwd weida-liu@foxmail.com
 * @date 2011-3-4 下午07:16:46
 * @version V1.0
 */
package com.huaixuan.network.biz.service.storage.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.storage.DepositoryDao;
import com.huaixuan.network.biz.domain.storage.Depository;
import com.huaixuan.network.biz.query.DepositoryQuery;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.service.storage.DepositoryService;

/**
 * @ClassName: DepositoryServiceImpl
 * @Description: TODO
 * @author liuwd weida-liu@foxmail.com
 * @date 2011-3-4 下午07:16:46
 *
 */
@Service("depositoryService")
public class DepositoryServiceImpl implements
		DepositoryService {

	@Autowired
	DepositoryDao depositoryDao;


	public Long addDepository(Depository depositoryDao) {
//		log.info("DepositoryManagerImpl.addDepository method");
		try {
			return this.depositoryDao.addDepository(depositoryDao);
		} catch (Exception e) {
//			log.error(e.getMessage());
		}
		return null;
	}

	/* @model: */
	public void editDepository(Depository depository) {
//		log.info("DepositoryManagerImpl.editDepository method");
		try {
			this.depositoryDao.editDepository(depository);
		} catch (Exception e) {
//			log.error(e.getMessage());
		}
	}

	/* @model: */
	public void removeDepository(Long depositoryId) {
//		log.info("DepositoryManagerImpl.removeDepository method");
		try {
			this.depositoryDao.removeDepository(depositoryId);
		} catch (Exception e) {
//			log.error(e.getMessage());
		}
	}

	/* @model: */
	public Depository getDepository(Long depositoryId) {
//		log.info("DepositoryManagerImpl.getDepository method");
		try {
			return this.depositoryDao.getDepository(depositoryId);
		} catch (Exception e) {
//			log.error(e.getMessage());
		}
		return null;
	}

	/*
	 * （非 Javadoc）
	 *
	 * @see com.hundsun.bible.facade.ios.DepositoryManager#getDepositorys()
	 */
	public List<Depository> getDepositorys() {
//		log.info("DepositoryManagerImpl.getDepositorys method");
		try {
			return this.depositoryDao.getDepositorys();
		} catch (Exception e) {
//			log.error(e.getMessage());
		}
		return null;
	}

	/* @model: */
	public QueryPage getDepositorysByParMap(DepositoryQuery query, int currPage, int pageSize, boolean isPage) {
//		log.info("DepositoryManagerImpl.getDepositorys method");
		try {
			return this.depositoryDao.getDepositorysByParMap(query, currPage, pageSize, isPage);
		} catch (Exception e) {
//			log.error(e.getMessage());
		}
		return null;
	}

	/*
	 * （非 Javadoc）
	 *
	 * @see
	 * com.hundsun.bible.facade.ios.DepositoryManager#getCountByParMap(java.
	 * util.Map)
	 */
	public int getCountByParMap(Map parMap) {
//		log.info("DepositoryManagerImpl.getCountByParMap method");
		try {
			return depositoryDao.getCountByParMap(parMap);
		} catch (Exception e) {
//			log.error(e.getMessage());
		}
		return 0;
	}

	public List<Depository> getDeplistByFirstDepId(Long depfirstId) {
//		log.info("DepositoryManagerImpl.getDeplistByFirstDepId method");
		try {
			return depositoryDao.getDeplistByFirstDepId(depfirstId);
		} catch (Exception e) {
//			log.error(e.getMessage());
		}
		return null;
	}

	public List<Depository> getDepositorysByDepFirstId(
			Map<String, Object> paramMap) {
//		log.info("DepositoryManagerImpl.getDepositorysByDepFirstId method");
		try {
			return depositoryDao.getDepositorysByDepFirstId(paramMap);
		} catch (Exception e) {
//			log.error(e.getMessage());
		}
		return null;
	}

	public List<Depository> getRightDeplistByFirstDepId(Long depfirstId) {
//		log.info("DepositoryManagerImpl.getRightDeplistByFirstDepId method");
		try {
			return depositoryDao.getRightDeplistByFirstDepId(depfirstId);
		} catch (Exception e) {
//			log.error(e.getMessage());
		}
		return null;
	}
}
