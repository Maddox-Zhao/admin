package com.huaixuan.network.biz.service.storage.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.storage.DepositoryFirstDao;
import com.huaixuan.network.biz.domain.storage.DepositoryFirst;
import com.huaixuan.network.biz.service.storage.DepositoryFirstManager;

@Service("depositoryFirstManager")
public class DepositoryFirstManagerImpl implements DepositoryFirstManager {

	Log log = LogFactory.getLog(this.getClass());

	@Autowired
	private DepositoryFirstDao depositoryFirstDao;

	public void setDepositoryFirstDao(DepositoryFirstDao depositoryFirstDao) {
		this.depositoryFirstDao = depositoryFirstDao;
	}

	public List<DepositoryFirst> getDepositoryFirstList() {
		return this.depositoryFirstDao.getDepositoryFirstList();
	}

	public List<DepositoryFirst> getDepositoryFirstListByIds(List<Long> ids) {
		return depositoryFirstDao.getDepositoryFirstListByIds(ids);
	}

	public DepositoryFirst getDepositoryById(Long id) {
		try {
			return depositoryFirstDao.getDepositoryById(id);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}

	public boolean updateDepositoryFirst(DepositoryFirst depositryFirst) {
		log.info("DepositoryFirstManager.updateDepositoryFirst method");
		try {
			depositoryFirstDao.updateDepositoryFirst(depositryFirst);
			return true;
		} catch (Exception e) {
			log.error(e.getMessage());
			return false;
		}
	}

	public DepositoryFirst getDepositoryByName(String depFirstName) {
		try {
			return depositoryFirstDao.getDepositoryByName(depFirstName);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}

	public Long insertDepositoryFirst(DepositoryFirst depositryFirst) {
		log.info("DepositoryFirstManager.insertDepositoryFirst method");
		try {
			return depositoryFirstDao.insertDepositoryFirst(depositryFirst);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}

	public List<DepositoryFirst> getDepositoryFirstListByParMap(Map parMap) {
		log.info("DepositoryFirstManager.getDepositoryFirstListByParMap method");
		try {
			return depositoryFirstDao.getDepositoryFirstListByParMap(parMap);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}

}
