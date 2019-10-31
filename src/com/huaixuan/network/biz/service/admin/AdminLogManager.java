package com.huaixuan.network.biz.service.admin;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.admin.Admin;
import com.huaixuan.network.biz.domain.admin.AdminLog;
import com.huaixuan.network.biz.query.QueryPage;

public interface AdminLogManager {
 	/**
 	 * ����ϵͳ������־
 	 * @param adminLog
 	 */
 	public void addAdminLog(AdminLog adminLog); 
  
 	/**
 	 * �޸�ϵͳ������־
 	 * @param adminLog
 	 */
 	public void editAdminLog(AdminLog adminLog); 
  
 	/**
 	 * ɾ��ϵͳ������־
 	 * @param adminLogId
 	 */
 	public void removeAdminLog(Long adminLogId); 
  
 	/**
 	 * ����id��ȡϵͳ������־
 	 * @param adminLogId
 	 * @return AdminLog
 	 */
 	public AdminLog getAdminLog(Long adminLogId); 
  
 	/**
 	 * ��ȡȫ����ϵͳ������־
 	 * @return List<AdminLog>
 	 */
 	public List<AdminLog> getAdminLogs(); 
 	
 	/**
 	 * ����������ȡList
 	 * @param parMap
 	 * @param page
 	 * @return list<AdminLog>
 	 */
 //	public List<AdminLog> getAdminLogsByCondition(Map<String,String> parMap,Page page);
 	
	public QueryPage getAdminLogListByConditionWithPage(AdminLog adminLog, int currPage, int pageSize);
}
