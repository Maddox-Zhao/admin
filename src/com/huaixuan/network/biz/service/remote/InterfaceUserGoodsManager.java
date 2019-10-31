package com.huaixuan.network.biz.service.remote;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.remote.InterfaceUserGoods;
import com.huaixuan.network.biz.query.QueryPage;

public interface InterfaceUserGoodsManager {

    public InterfaceUserGoods getInterfaceUserGoods(Map parMap);

    public void updateInterfaceUserGoodsStatus(InterfaceUserGoods interfaceUserGoods);

    public void addInterfaceUserGoods(InterfaceUserGoods interfaceUserGoods);

    public int getInterfaceUserGoodsCountByCondition(Map parMap);

    public List<InterfaceUserGoods> getInterfaceUserGoodsByCondition(Map parMap);

    public QueryPage getInterfaceUserGoodsByConditionQuery(Map parMap, int currPage, int pageSize);

    public boolean updateBatchPaipaiRelation(List<String> relationIds);

    public void insertBatchInterfaceGoods(List<String> goodsIds, Long userId);

    public List<InterfaceUserGoods> getInterfaceUserGoodsList(Map parMap);
}
