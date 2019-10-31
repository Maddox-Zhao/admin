package com.huaixuan.network.biz.dao.remote;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.taobao.TaobaoInterfaceUserGoods;

public interface TaobaoInterfaceUserGoodsDao {

    TaobaoInterfaceUserGoods getInterfaceUserGoods(Map parMap);

    void addInterfaceUserGoods(TaobaoInterfaceUserGoods interfaceUserGoods);

    void updateInterfaceUserGoodsStatus(TaobaoInterfaceUserGoods interfaceUserGoods);

    int getInterfaceUserGoodsCountByCondition(Map parMap);

    List<TaobaoInterfaceUserGoods> getInterfaceUserGoodsByCondition(Map parMap);

    void updateBatchTaobaoRelation(List<Map> taobaoUpdateList);

    void insertBatchInterfaceGoods(List<TaobaoInterfaceUserGoods> interfaceUserGoodsList,
                                   List<Map> paiPaiUpdateList);

    List<TaobaoInterfaceUserGoods> getInterfaceUserGoodsList(Map parMap);

    List<TaobaoInterfaceUserGoods> getInterfaceUserGoodsListByIds(List ids);
}
