package com.huaixuan.network.biz.dao.admin;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.admin.Admin;
import com.huaixuan.network.biz.domain.admin.AdminLog;

public interface AdminLogDao {
 	void addAdminLog(AdminLog adminLog) throws Exception;

 	void editAdminLog(AdminLog adminLog) throws Exception;

 	void removeAdminLog(Long adminLogId) throws Exception;

 	AdminLog getAdminLog(Long adminLogId) throws Exception;

 	List <AdminLog> getAdminLogs() throws Exception;

 	/**
 	 * 根据条件获取List
 	 * @param parMap
 	 * @param page
 	 * @return list<AdminLog>
 	 */
// 	List <AdminLog> getAdminLogsByCondition(Map parMap,Page page);
 	
	public int getAdminLogListByConditionWithPageCount(Map parMap);

	public List<AdminLog> getAdminLogListByConditionWithPage(Map parMap);
}
