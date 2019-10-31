package com.huaixuan.network.biz.service.remote.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.agent.InterfaceSyncDao;
import com.huaixuan.network.biz.domain.remote.InterfaceSync;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.service.remote.InterfaceSyncManager;

@Service("interfaceSyncManager")
public class InterfaceSyncManagerImpl implements InterfaceSyncManager {

    protected final Log log = LogFactory.getLog(this.getClass());

    @Autowired
    InterfaceSyncDao    interfaceSyncDao;

    public void addInterfaceSync(InterfaceSync interfaceSync) {
        this.interfaceSyncDao.addInterfaceSync(interfaceSync);
    }

    public List<InterfaceSync> getInterfaceSyncList(Map parMap) {
        return this.interfaceSyncDao.getInterfaceSyncList(parMap);
    }

    public QueryPage getInterfaceSyncListQuery(Map parMap, int currPage, int pageSize) {
        QueryPage queryPage = new QueryPage(parMap);

        try {
            int count = interfaceSyncDao.getInterfaceSyncListCount(parMap);

            if (count > 0) {

                /* 当前页 */
                queryPage.setCurrentPage(currPage);
                /* 每页总数 */
                queryPage.setPageSize(pageSize);
                queryPage.setTotalItem(count);

                parMap.put("startRow", queryPage.getPageFristItem());
                parMap.put("endRow", queryPage.getPageLastItem());

                List<InterfaceSync> interfaceSyncList = interfaceSyncDao
                    .getInterfaceSyncList(parMap);
                if (interfaceSyncList != null && interfaceSyncList.size() > 0) {
                    queryPage.setItems(interfaceSyncList);
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return queryPage;

    }

    public void updateInterfaceSync(InterfaceSync interfaceSync) {
        this.interfaceSyncDao.updateInterfaceSync(interfaceSync);
    }

    public void insertBatchSyncList(List<InterfaceSync> interfaceList) {
        this.interfaceSyncDao.insertBatchSyncList(interfaceList);
    }

}
