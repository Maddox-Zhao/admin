package com.huaixuan.network.biz.dao.admin.ibatis;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientCallback;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.huaixuan.network.biz.dao.admin.AdminRoleDao;
import com.huaixuan.network.biz.domain.admin.AdminRole;
import com.ibatis.sqlmap.client.SqlMapExecutor;

@Repository("adminRoleDao")
public class AdminRoleDaoiBatis implements AdminRoleDao {
	@Autowired
	private SqlMapClientTemplate sqlMapClient;
	
	protected Log logger = LogFactory.getLog(this.getClass());
	
	@Override
	public List<AdminRole> getRoleByAdminId(Long adminId) {
		return sqlMapClient.queryForList("AdminRoleDao.getRoleByAdminId", adminId);
	}

	@Override
	public void deleteAdminRoleByAdminId(Long adminId) {
		sqlMapClient.delete("AdminRoleDao.deleteAdminRoleByAdminId", adminId);
	}

	@Override
	public void insertAdminRoleBatch(final List<AdminRole> AdminRoleList) {
        sqlMapClient.execute(new SqlMapClientCallback<Boolean>() {
            public Boolean doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
                try {
                    executor.startBatch();
                    for (int i = 0; i < AdminRoleList.size(); i++) {
                        executor.insert("AdminRoleDao.insertAdminRole", AdminRoleList.get(i));
                    }
                    executor.executeBatch();
                    return true;
                } catch (Exception e) {
                	logger.error(e.getMessage());
                    return false;
                }
            }
        });
	}

}
