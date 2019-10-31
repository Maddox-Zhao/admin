package com.huaixuan.network.biz.service.remote;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.taobao.TaobaoInterfaceUserGoods;

public interface TaobaoInterfaceUserGoodsManager {

    public TaobaoInterfaceUserGoods getInterfaceUserGoods(Map parMap);

    public void updateInterfaceUserGoodsStatus(TaobaoInterfaceUserGoods interfaceUserGoods);

    public void addInterfaceUserGoods(TaobaoInterfaceUserGoods interfaceUserGoods);

    public int getInterfaceUserGoodsCountByCondition(Map parMap);

    //    public List<TaobaoInterfaceUserGoods> getInterfaceUserGoodsByCondition(Map parMap);

    public boolean updateBatchTaobaoRelation(List<String> relationIds);

    public void insertBatchInterfaceGoods(List<String> goodsIds, Long userId);

    public List<TaobaoInterfaceUserGoods> getInterfaceUserGoodsList(Map parMap);

    public List<TaobaoInterfaceUserGoods> getInterfaceUserGoodsListByIds(List ids);

    public List<TaobaoInterfaceUserGoods> getInterfaceUserGoodsByCondition(Map parMap);
}
