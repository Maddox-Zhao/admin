package com.huaixuan.network.biz.dao.remote;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.taobao.TaobaoInterfaceSync;

public interface TaobaoInterfaceSyncDao {

    void addInterfaceSync(TaobaoInterfaceSync interfaceSync);

    List<TaobaoInterfaceSync> getInterfaceSyncList(Map parMap);

    public int getInterfaceSyncListCount(Map parMap);

    void updateInterfaceSync(TaobaoInterfaceSync interfaceSync);

    void insertBatchSyncList(List<TaobaoInterfaceSync> interfaceList);

    int getTaobaoInterfaceSyncSearchCount(Map<String, String> parMap);

    List<TaobaoInterfaceSync> getTaobaoInterfaceSyncListSearch(Map parMap);

    int getInterfaceSyncListForCheck(Map parMap);

    void updateSync(TaobaoInterfaceSync interfaceSync);
}
