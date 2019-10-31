package com.huaixuan.network.biz.dao.agent.ibatis;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.huaixuan.network.biz.dao.agent.InterfaceSyncDao;
import com.huaixuan.network.biz.domain.remote.InterfaceSync;

@Repository("interfaceSyncDao")
public class InterfaceSyncDaoiBatis implements InterfaceSyncDao {
    protected final Log          log = LogFactory.getLog(this.getClass());

    @Autowired
    private SqlMapClientTemplate sqlMapClient;

    public void addInterfaceSync(InterfaceSync interfaceSync) {
        this.sqlMapClient.insert("addInterfaceSync", interfaceSync);
    }

    public List<InterfaceSync> getInterfaceSyncList(Map parMap) {
        return this.sqlMapClient.queryForList("getInterfaceSyncList", parMap);
    }

    public int getInterfaceSyncListCount(Map parMap) {
        return (Integer) this.sqlMapClient.queryForObject("getInterfaceSyncCount", parMap);
    }

    public void updateInterfaceSync(InterfaceSync interfaceSync) {
        this.sqlMapClient.update("updateInterfaceSync", interfaceSync);
    }

    public void insertBatchSyncList(List<InterfaceSync> interfaceList) {
        if (interfaceList != null && interfaceList.size() > 0) {
            for (InterfaceSync interfaceSync : interfaceList) {
                this.sqlMapClient.insert("addInterfaceSync", interfaceSync);
            }
        }
    }
}
