package com.huaixuan.network.biz.service.goods.impl;

 import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.goods.AttrGroupDao;
import com.huaixuan.network.biz.domain.goods.AttrGroup;
import com.huaixuan.network.biz.service.goods.AttrGroupManager;


@Service("attrGroupManager")
 public class AttrGroupManagerImpl implements AttrGroupManager {

	protected Log  log = LogFactory.getLog(this.getClass());
	
	@Autowired
 	AttrGroupDao attrGroupDao;
  
 	/* @model: */ 
 	public void addAttrGroup(AttrGroup attrGroupDao) { 
 		log.info("AttrGroupManagerImpl.addAttrGroup method"); 
 		try { 
 			 this.attrGroupDao.addAttrGroup(attrGroupDao); 
 		} catch (Exception e) { 
 			log.error(e.getMessage()); 
 		} 
 	} 
  
 	/* @model: */ 
 	public void editAttrGroup(AttrGroup attrGroup) { 
 		log.info("AttrGroupManagerImpl.editAttrGroup method"); 
 		try { 
 			this.attrGroupDao.editAttrGroup(attrGroup); 
 		} catch (Exception e) { 
 			log.error(e.getMessage()); 
 		} 
 	} 
  
 	/* @model: */ 
 	public void removeAttrGroup(Long attrGroupId) { 
 		log.info("AttrGroupManagerImpl.removeAttrGroup method"); 
 		try { 
 			this.attrGroupDao.removeAttrGroup(attrGroupId); 
 		} catch (Exception e) { 
 			log.error(e.getMessage()); 
 		} 
 	} 
  
 	/* @model: */ 
 	public AttrGroup getAttrGroup(Long attrGroupId) { 
 		log.info("AttrGroupManagerImpl.getAttrGroup method"); 
 		try { 
 			return this.attrGroupDao.getAttrGroup(attrGroupId); 
 		} catch (Exception e) { 
 			log.error(e.getMessage()); 
 		} 
 		return null; 
 	} 
  
 	/* @model: */ 
 	public List<AttrGroup> getAttrGroups() { 
 		log.info("AttrGroupManagerImpl.getAttrGroups method"); 
 		try { 
 			return this.attrGroupDao.getAttrGroups(); 
 		} catch (Exception e) { 
 			log.error(e.getMessage()); 
 		} 
 		return null; 
 	} 
 }
