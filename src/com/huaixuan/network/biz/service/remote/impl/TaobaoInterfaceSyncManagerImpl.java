package com.huaixuan.network.biz.service.remote.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.remote.TaobaoInterfaceSyncDao;
import com.huaixuan.network.biz.domain.remote.InterfaceUserGoods;
import com.huaixuan.network.biz.domain.taobao.TaobaoInterfaceSync;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.service.remote.TaobaoInterfaceSyncManager;

@Service("taobaoInterfaceSyncManager")
public class TaobaoInterfaceSyncManagerImpl implements TaobaoInterfaceSyncManager {
    protected final Log    log = LogFactory.getLog(this.getClass());
    @Autowired
    TaobaoInterfaceSyncDao taobaoInterfaceSyncDao;

    public void addInterfaceSync(TaobaoInterfaceSync interfaceSync) {
        this.taobaoInterfaceSyncDao.addInterfaceSync(interfaceSync);
    }

    public List<TaobaoInterfaceSync> getInterfaceSyncList(Map parMap) {
        return this.taobaoInterfaceSyncDao.getInterfaceSyncList(parMap);
    }

    public void updateInterfaceSync(TaobaoInterfaceSync interfaceSync) {
        this.taobaoInterfaceSyncDao.updateInterfaceSync(interfaceSync);
    }

    public void insertBatchSyncList(List<TaobaoInterfaceSync> interfaceList) {
        this.taobaoInterfaceSyncDao.insertBatchSyncList(interfaceList);
    }

    /**
     * @author chenhang 2011-01-05
     */
    public int getTaobaoInterfaceSyncSearchCount(Map<String, String> parMap) {
        return this.taobaoInterfaceSyncDao.getTaobaoInterfaceSyncSearchCount(parMap);
    }

    /**
     * @author chenhang 2011-01-06
     */
    public List<TaobaoInterfaceSync> getTaobaoInterfaceSyncListSearch(Map parMap) {
        return this.taobaoInterfaceSyncDao.getTaobaoInterfaceSyncListSearch(parMap);
    }

    /**
     * @author chenhang 2011-01-10
     */
    public int getInterfaceSyncListForCheck(Map parMap) {
        return this.taobaoInterfaceSyncDao.getInterfaceSyncListForCheck(parMap);
    }

    /**
     * @author chenhang 2011-01-10
     */
    public void updateSync(TaobaoInterfaceSync interfaceSync) {
        this.taobaoInterfaceSyncDao.updateSync(interfaceSync);
    }

    @Override
    public QueryPage getInterfaceSyncListQuery(Map parMap, int currPage, int pageSize) {
        QueryPage queryPage = new QueryPage(parMap);

        try {
            int count = taobaoInterfaceSyncDao.getInterfaceSyncListCount(parMap);

            if (count > 0) {

                /* 当前页 */
                queryPage.setCurrentPage(currPage);
                /* 每页总数 */
                queryPage.setPageSize(pageSize);
                queryPage.setTotalItem(count);

                parMap.put("startRow", queryPage.getPageFristItem());
                parMap.put("endRow", queryPage.getPageLastItem());

                List<TaobaoInterfaceSync> interfaceSyncList = taobaoInterfaceSyncDao
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
}
