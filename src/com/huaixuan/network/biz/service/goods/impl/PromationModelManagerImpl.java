package com.huaixuan.network.biz.service.goods.impl;

 import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.goods.PromationModelDao;
import com.huaixuan.network.biz.domain.goods.PromationModel;
import com.huaixuan.network.biz.service.goods.PromationModelManager;

@Service("promationModelManager")
 public class PromationModelManagerImpl implements PromationModelManager {

	@Autowired
 	public PromationModelDao promationModelDao;
	
	protected Log  log = LogFactory.getLog(this.getClass());

 	/* @model: */
 	public void addPromationModel(PromationModel promationModelDao) {
 		log.info("PromationModelManagerImpl.addPromationModel method");
 		try {
 			 this.promationModelDao.addPromationModel(promationModelDao);
 		} catch (Exception e) {
 			log.error(e.getMessage());
 		}
 	}

 	/* @model: */
 	public void editPromationModel(PromationModel promationModel) {
 		log.info("PromationModelManagerImpl.editPromationModel method");
 		try {
 			this.promationModelDao.editPromationModel(promationModel);
 		} catch (Exception e) {
 			log.error(e.getMessage());
 		}
 	}

 	/* @model: */
 	public void removePromationModel(Long promationModelId) {
 		log.info("PromationModelManagerImpl.removePromationModel method");
 		try {
 			this.promationModelDao.removePromationModel(promationModelId);
 		} catch (Exception e) {
 			log.error(e.getMessage());
 		}
 	}

 	/* @model: */
 	public PromationModel getPromationModel(Long promationModelId) {
 		log.info("PromationModelManagerImpl.getPromationModel method");
 		try {
 			return this.promationModelDao.getPromationModel(promationModelId);
 		} catch (Exception e) {
 			log.error(e.getMessage());
 		}
 		return null;
 	}

 	/* @model: */
 	public List<PromationModel> getPromationModels() {
 		log.info("PromationModelManagerImpl.getPromationModels method");
 		try {
 			return this.promationModelDao.getPromationModels();
 		} catch (Exception e) {
 			log.error(e.getMessage());
 		}
 		return null;
 	}
 }
