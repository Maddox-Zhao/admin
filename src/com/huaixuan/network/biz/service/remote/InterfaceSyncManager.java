package com.huaixuan.network.biz.service.remote;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.remote.InterfaceSync;
import com.huaixuan.network.biz.query.QueryPage;

public interface InterfaceSyncManager {

    public void addInterfaceSync(InterfaceSync interfaceSync);

    public List<InterfaceSync> getInterfaceSyncList(Map parMap);

    public void updateInterfaceSync(InterfaceSync interfaceSync);

    public void insertBatchSyncList(List<InterfaceSync> interfaceList);

    public QueryPage getInterfaceSyncListQuery(Map parMap, int currPage, int pageSize);
}
