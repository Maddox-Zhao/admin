package com.huaixuan.network.biz.dao.agent;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.remote.InterfaceSync;

public interface InterfaceSyncDao {
    void addInterfaceSync(InterfaceSync interfaceSync);

    List<InterfaceSync> getInterfaceSyncList(Map parMap);

    int getInterfaceSyncListCount(Map parMap);

    void updateInterfaceSync(InterfaceSync interfaceSync);

    void insertBatchSyncList(List<InterfaceSync> interfaceList);
}
