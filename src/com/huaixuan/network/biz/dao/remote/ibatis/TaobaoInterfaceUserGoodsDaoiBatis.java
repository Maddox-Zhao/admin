package com.huaixuan.network.biz.dao.remote.ibatis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.huaixuan.network.biz.dao.remote.TaobaoInterfaceUserGoodsDao;
import com.huaixuan.network.biz.domain.taobao.TaobaoInterfaceUserGoods;

@Repository("taobaoInterfaceUserGoodsDao")
public class TaobaoInterfaceUserGoodsDaoiBatis implements TaobaoInterfaceUserGoodsDao {

    protected final Log          log = LogFactory.getLog(this.getClass());

    @Autowired
    private SqlMapClientTemplate sqlMapClient;

    public TaobaoInterfaceUserGoods getInterfaceUserGoods(Map parMap) {
        return (TaobaoInterfaceUserGoods) this.sqlMapClient.queryForObject(
            "getInterfaceUserGoodsTaobao", parMap);
    }

    public void addInterfaceUserGoods(TaobaoInterfaceUserGoods interfaceUserGoods) {
        this.sqlMapClient.insert("addInterfaceUserGoodsTaobao", interfaceUserGoods);
    }

    public void updateInterfaceUserGoodsStatus(TaobaoInterfaceUserGoods interfaceUserGoods) {
        this.sqlMapClient.update("updateInterfaceUserGoodsStatusTaobao", interfaceUserGoods);
    }

    public List<TaobaoInterfaceUserGoods> getInterfaceUserGoodsByCondition(Map parMap) {
        return this.sqlMapClient.queryForList("getInterfaceUserGoodsByConditionTaobao", parMap);
    }

    public int getInterfaceUserGoodsCountByCondition(Map parMap) {
        return (Integer) this.sqlMapClient.queryForObject(
            "getInterfaceUserGoodsCountByConditionTaobao", parMap);
    }

    public void updateBatchTaobaoRelation(List<Map> taobaoUpdateList) {
        if (taobaoUpdateList != null && taobaoUpdateList.size() > 0) {
            for (Map taobaoMap : taobaoUpdateList) {
                this.sqlMapClient.update("updateTaobaoStatusById", taobaoMap);
            }
        }
    }

    public void insertBatchInterfaceGoods(List<TaobaoInterfaceUserGoods> interfaceUserGoodsList,
                                          List<Map> paiPaiUpdateList) {
        if (interfaceUserGoodsList != null && interfaceUserGoodsList.size() > 0) {
            for (TaobaoInterfaceUserGoods interfaceUserGoods : interfaceUserGoodsList) {
                this.sqlMapClient.insert("addInterfaceUserGoodsTaobao", interfaceUserGoods);
            }
        }
        if (paiPaiUpdateList != null && paiPaiUpdateList.size() > 0) {
            for (Map paiPaiMap : paiPaiUpdateList) {
                this.sqlMapClient.update("updateTaobaoStatusById", paiPaiMap);
            }
        }
    }

    public List<TaobaoInterfaceUserGoods> getInterfaceUserGoodsList(Map parMap) {
        return this.sqlMapClient.queryForList("getInterfaceUserGoodsListTaobao", parMap);
    }

    public List<TaobaoInterfaceUserGoods> getInterfaceUserGoodsListByIds(List ids) {
        if (ids == null || ids.size() < 1) {
            return null;
        }
        Map map = new HashMap();
        map.put("ids", ids);
        return this.sqlMapClient.queryForList("getInterfaceUserGoodsListByIdsTaobao", map);
    }
}
