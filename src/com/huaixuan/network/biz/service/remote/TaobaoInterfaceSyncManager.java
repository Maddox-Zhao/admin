package com.huaixuan.network.biz.service.remote;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.taobao.TaobaoInterfaceSync;
import com.huaixuan.network.biz.query.QueryPage;

public interface TaobaoInterfaceSyncManager {

    public void addInterfaceSync(TaobaoInterfaceSync interfaceSync);

    public List<TaobaoInterfaceSync> getInterfaceSyncList(Map parMap);

    public void updateInterfaceSync(TaobaoInterfaceSync interfaceSync);

    public void insertBatchSyncList(List<TaobaoInterfaceSync> interfaceList);

    public int getTaobaoInterfaceSyncSearchCount(Map<String, String> parMap);

    public List<TaobaoInterfaceSync> getTaobaoInterfaceSyncListSearch(Map parMap);

    public int getInterfaceSyncListForCheck(Map parMap);

    public void updateSync(TaobaoInterfaceSync interfaceSync);

    public QueryPage getInterfaceSyncListQuery(Map parMap, int currPage, int pageSize);
}
