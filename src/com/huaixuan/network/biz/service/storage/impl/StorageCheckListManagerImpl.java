package com.huaixuan.network.biz.service.storage.impl;

 import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.storage.StorageCheckListDao;
import com.huaixuan.network.biz.domain.storage.StorageCheckList;
import com.huaixuan.network.biz.service.storage.StorageCheckListManager;

 /**
  * @version 3.2.0
  */
@Service("storageCheckListManager")
 public class StorageCheckListManagerImpl implements StorageCheckListManager {
	/**
     * Log instance for all child classes. Uses LogFactory.getLog(getClass()) from Commons Logging
     */
    protected final Log    log = LogFactory.getLog(getClass());

	@Autowired
 	StorageCheckListDao storageCheckListDao;


 	/* @model: */
 	public void addStorageCheckList(StorageCheckList storageCheckListDao) {
 		log.info("StorageCheckListManagerImpl.addStorageCheckList method");
 		try {
 			 this.storageCheckListDao.addStorageCheckList(storageCheckListDao);
 		} catch (Exception e) {
 			log.error(e.getMessage());
 		}
 	}

 	/* @model: */
 	public void editStorageCheckList(StorageCheckList storageCheckList) {
 		log.info("StorageCheckListManagerImpl.editStorageCheckList method");
 		try {
 			this.storageCheckListDao.editStorageCheckList(storageCheckList);
 		} catch (Exception e) {
 			log.error(e.getMessage());
 		}
 	}

 	/* @model: */
 	public void removeStorageCheckList(Long storageCheckListId) {
 		log.info("StorageCheckListManagerImpl.removeStorageCheckList method");
 		try {
 			this.storageCheckListDao.removeStorageCheckList(storageCheckListId);
 		} catch (Exception e) {
 			log.error(e.getMessage());
 		}
 	}
/* @model: */
 	public StorageCheckList getStorageCheckList(Long storageCheckListId) {
 		log.info("StorageCheckListManagerImpl.getStorageCheckList method");
 		try {
 			return this.storageCheckListDao.getStorageCheckList(storageCheckListId);
 		} catch (Exception e) {
 			log.error(e.getMessage());
 		}
 		return null;
 	}

 	/* @model: */public List<StorageCheckList> getStorageCheckLists() {
 		log.info("StorageCheckListManagerImpl.getStorageCheckLists method");
 		try {
 			return this.storageCheckListDao.getStorageCheckLists();
 		} catch (Exception e) {
 			log.error(e.getMessage());
 		}
 		return null;
 	}
 }
