package com.huaixuan.network.biz.service.storage.impl;

 import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.storage.StorageCheckDetailDao;
import com.huaixuan.network.biz.domain.storage.StorageCheckDetail;
import com.huaixuan.network.biz.service.storage.StorageCheckDetailManager;

 /**
  * @version 3.2.0
  */
@Service("storageCheckDetailManager")
 public class StorageCheckDetailManagerImpl implements StorageCheckDetailManager {
	/**
     * Log instance for all child classes. Uses LogFactory.getLog(getClass()) from Commons Logging
     */
    protected final Log    log = LogFactory.getLog(getClass());

	@Autowired
 	StorageCheckDetailDao storageCheckDetailDao;

 	/* @model: */
 	public void addStorageCheckDetail(StorageCheckDetail storageCheckDetailDao) {
 		log.info("StorageCheckDetailManagerImpl.addStorageCheckDetail method");
 		try {
 			 this.storageCheckDetailDao.addStorageCheckDetail(storageCheckDetailDao);
 		} catch (Exception e) {
 			log.error(e.getMessage());
 		}
 	}

 	/* @model: */
 	public void editStorageCheckDetail(StorageCheckDetail storageCheckDetail) {
 		log.info("StorageCheckDetailManagerImpl.editStorageCheckDetail method");
 		try {
 			this.storageCheckDetailDao.editStorageCheckDetail(storageCheckDetail);
 		} catch (Exception e) {
 			log.error(e.getMessage());
 		}
 	}

 	/* @model: */
 	public void removeStorageCheckDetail(Long storageCheckDetailId) {
 		log.info("StorageCheckDetailManagerImpl.removeStorageCheckDetail method");
 		try {
 			this.storageCheckDetailDao.removeStorageCheckDetail(storageCheckDetailId);
 		} catch (Exception e) {
 			log.error(e.getMessage());
 		}
 	}
/* @model: */
 	public StorageCheckDetail getStorageCheckDetail(Long storageCheckDetailId) {
 		log.info("StorageCheckDetailManagerImpl.getStorageCheckDetail method");
 		try {
 			return this.storageCheckDetailDao.getStorageCheckDetail(storageCheckDetailId);
 		} catch (Exception e) {
 			log.error(e.getMessage());
 		}
 		return null;
 	}

 	/* @model: */public List<StorageCheckDetail> getStorageCheckDetails() {
 		log.info("StorageCheckDetailManagerImpl.getStorageCheckDetails method");
 		try {
 			return this.storageCheckDetailDao.getStorageCheckDetails();
 		} catch (Exception e) {
 			log.error(e.getMessage());
 		}
 		return null;
 	}
 }
