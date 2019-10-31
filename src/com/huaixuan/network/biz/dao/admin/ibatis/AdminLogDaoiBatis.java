package com.huaixuan.network.biz.dao.admin.ibatis;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.huaixuan.network.biz.dao.admin.AdminLogDao;
import com.huaixuan.network.biz.domain.admin.AdminLog;

@Repository("adminLogDao")
public class AdminLogDaoiBatis implements AdminLogDao {

	@Autowired
	private SqlMapClientTemplate sqlMapClient;
	
	 public void addAdminLog(AdminLog adminLog)throws Exception{
		 this.sqlMapClient.insert("addAdminLog", adminLog);
	 }

	 public void editAdminLog(AdminLog adminLog)throws Exception{
		 this.sqlMapClient.update("editAdminLog", adminLog);
	 }

	 public void removeAdminLog(Long adminLogId)throws Exception{
		 this.sqlMapClient.delete("removeAdminLog",adminLogId);
	 }

	 public AdminLog getAdminLog(Long adminLogId)throws Exception{
		 return (AdminLog)this.sqlMapClient.queryForObject("getAdminLog", adminLogId);
	 }

	 public List<AdminLog> getAdminLogs()throws Exception{
		 return this.sqlMapClient.queryForList("getAdminLogs", null);
	 }

	@Override
	public int getAdminLogListByConditionWithPageCount(Map parMap) {
		return (Integer)sqlMapClient.queryForObject("getAdminLogsCountByCondition", parMap);
	}

	@Override
	public List<AdminLog> getAdminLogListByConditionWithPage(Map parMap) {
		return sqlMapClient.queryForList("getAdminLogsByCondition", parMap);
	}

//	public List<AdminLog> getAdminLogsByCondition(Map parMap, Page page) {
//		if(page == null){
//			return this.sqlMapClient.queryForList("getAdminLogsByCondition", null);
//		}
//		return findQueryPage("getAdminLogsByCondition", "getAdminLogsCountByCondition", parMap, page);
//	}

}
