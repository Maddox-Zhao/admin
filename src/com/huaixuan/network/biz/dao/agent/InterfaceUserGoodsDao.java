package com.huaixuan.network.biz.dao.agent;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.remote.InterfaceUserGoods;

public interface InterfaceUserGoodsDao {
    InterfaceUserGoods getInterfaceUserGoods(Map parMap);

    void addInterfaceUserGoods(InterfaceUserGoods interfaceUserGoods);

    void updateInterfaceUserGoodsStatus(InterfaceUserGoods interfaceUserGoods);

    int getInterfaceUserGoodsCountByCondition(Map parMap);

    List<InterfaceUserGoods> getInterfaceUserGoodsByCondition(Map parMap);

    void updateBatchPaipaiRelation(List<Map> paiPaiUpdateList);

    void insertBatchInterfaceGoods(List<InterfaceUserGoods> interfaceUserGoodsList,
                                   List<Map> paiPaiUpdateList);

    List<InterfaceUserGoods> getInterfaceUserGoodsList(Map parMap);
}
