package com.huaixuan.network.biz.service.admin;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.admin.Admin;
import com.huaixuan.network.biz.domain.admin.AdminLog;
import com.huaixuan.network.biz.query.QueryPage;

public interface AdminLogManager {
 	/**
 	 * 增加系统管理日志
 	 * @param adminLog
 	 */
 	public void addAdminLog(AdminLog adminLog); 
  
 	/**
 	 * 修改系统管理日志
 	 * @param adminLog
 	 */
 	public void editAdminLog(AdminLog adminLog); 
  
 	/**
 	 * 删除系统管理日志
 	 * @param adminLogId
 	 */
 	public void removeAdminLog(Long adminLogId); 
  
 	/**
 	 * 根据id获取系统管理日志
 	 * @param adminLogId
 	 * @return AdminLog
 	 */
 	public AdminLog getAdminLog(Long adminLogId); 
  
 	/**
 	 * 获取全部的系统管理日志
 	 * @return List<AdminLog>
 	 */
 	public List<AdminLog> getAdminLogs(); 
 	
 	/**
 	 * 根据条件获取List
 	 * @param parMap
 	 * @param page
 	 * @return list<AdminLog>
 	 */
 //	public List<AdminLog> getAdminLogsByCondition(Map<String,String> parMap,Page page);
 	
	public QueryPage getAdminLogListByConditionWithPage(AdminLog adminLog, int currPage, int pageSize);
}
