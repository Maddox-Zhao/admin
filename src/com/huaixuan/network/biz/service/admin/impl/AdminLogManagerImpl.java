package com.huaixuan.network.biz.service.admin.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.admin.AdminLogDao;
import com.huaixuan.network.biz.domain.admin.Admin;
import com.huaixuan.network.biz.domain.admin.AdminLog;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.service.admin.AdminLogManager;

@Service("adminLogManager")
public class AdminLogManagerImpl implements AdminLogManager {

	protected Log  log = LogFactory.getLog(this.getClass());
 	 
	@Autowired
	private AdminLogDao adminLogDao;
 	
  
 	/* @model: */ 
 	public void addAdminLog(AdminLog adminLogDao) { 
 		log.info("AdminLogManagerImpl.addAdminLog method"); 
 		try { 
 			 this.adminLogDao.addAdminLog(adminLogDao); 
 		} catch (Exception e) { 
 			log.error(e.getMessage()); 
 		} 
 	} 
  
 	/* @model: */ 
 	public void editAdminLog(AdminLog adminLog) { 
 		log.info("AdminLogManagerImpl.editAdminLog method"); 
 		try { 
 			this.adminLogDao.editAdminLog(adminLog); 
 		} catch (Exception e) { 
 			log.error(e.getMessage()); 
 		} 
 	} 
  
 	/* @model: */ 
 	public void removeAdminLog(Long adminLogId) { 
 		log.info("AdminLogManagerImpl.removeAdminLog method"); 
 		try { 
 			this.adminLogDao.removeAdminLog(adminLogId); 
 		} catch (Exception e) { 
 			log.error(e.getMessage()); 
 		} 
 	} 
  
 	/* @model: */ 
 	public AdminLog getAdminLog(Long adminLogId) { 
 		log.info("AdminLogManagerImpl.getAdminLog method"); 
 		try { 
 			return this.adminLogDao.getAdminLog(adminLogId); 
 		} catch (Exception e) { 
 			log.error(e.getMessage()); 
 		} 
 		return null; 
 	} 
  
 	/* @model: */ 
 	public List<AdminLog> getAdminLogs() { 
 		log.info("AdminLogManagerImpl.getAdminLogs method"); 
 		try { 
 			return this.adminLogDao.getAdminLogs(); 
 		} catch (Exception e) { 
 			log.error(e.getMessage()); 
 		} 
 		return null; 
 	}

	@SuppressWarnings("unchecked")
	@Override
	public QueryPage getAdminLogListByConditionWithPage(AdminLog adminLog,int currPage, int pageSize) {
		QueryPage queryPage = new QueryPage(adminLog);
		Map pramas = queryPage.getParameters();

		int count = adminLogDao.getAdminLogListByConditionWithPageCount(pramas);

		if (count > 0) {

			/* 当前页 */
			queryPage.setCurrentPage(currPage);
			/* 每页总数 */
			queryPage.setPageSize(pageSize);
			queryPage.setTotalItem(count);

			pramas.put("startRow", queryPage.getPageFristItem());
			pramas.put("endRow", queryPage.getPageLastItem());

			List<AdminLog> adminLogList = adminLogDao.getAdminLogListByConditionWithPage(pramas);
			if (adminLogList != null && adminLogList.size() > 0) {
				queryPage.setItems(adminLogList);
			}
		}
		return queryPage;
	}

//    public List<AdminLog> getAdminLogsByCondition(Map<String, String> parMap, Page page) {
//       log.info("AdminLogManagerImpl.getAdminLogsByCondition method");
//       try{
//           return adminLogDao.getAdminLogsByCondition(parMap,page);
//       }catch(Exception e){
//           log.error(e.getMessage());
//       }
//       return null;
//    } 

}
