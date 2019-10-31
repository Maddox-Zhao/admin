package com.huaixuan.network.biz.dao.agent.ibatis;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.huaixuan.network.biz.dao.agent.InterfaceUserGoodsDao;
import com.huaixuan.network.biz.domain.remote.InterfaceUserGoods;

@Repository("interfaceUserGoodsDao")
public class InterfaceUserGoodsDaoiBatis implements InterfaceUserGoodsDao {
    protected final Log          log = LogFactory.getLog(this.getClass());

    @Autowired
    private SqlMapClientTemplate sqlMapClient;

    public InterfaceUserGoods getInterfaceUserGoods(Map parMap) {
        return (InterfaceUserGoods) this.sqlMapClient.queryForObject("getInterfaceUserGoods",
            parMap);
    }

    public void addInterfaceUserGoods(InterfaceUserGoods interfaceUserGoods) {
        this.sqlMapClient.insert("addInterfaceUserGoods", interfaceUserGoods);
    }

    public void updateInterfaceUserGoodsStatus(InterfaceUserGoods interfaceUserGoods) {
        this.sqlMapClient.update("updateInterfaceUserGoodsStatus", interfaceUserGoods);
    }

    public List<InterfaceUserGoods> getInterfaceUserGoodsByCondition(Map parMap) {
        return this.sqlMapClient.queryForList("getInterfaceUserGoodsByCondition", parMap);
    }

    public int getInterfaceUserGoodsCountByCondition(Map parMap) {
        return (Integer) this.sqlMapClient.queryForObject("getInterfaceUserGoodsCountByCondition",
            parMap);
    }

    public void updateBatchPaipaiRelation(List<Map> paiPaiUpdateList) {
        if (paiPaiUpdateList != null && paiPaiUpdateList.size() > 0) {
            for (Map paiPaiMap : paiPaiUpdateList) {
                this.sqlMapClient.update("updatePaipaiStatusById", paiPaiMap);
            }
        }
    }

    public void insertBatchInterfaceGoods(List<InterfaceUserGoods> interfaceUserGoodsList,
                                          List<Map> paiPaiUpdateList) {
        if (interfaceUserGoodsList != null && interfaceUserGoodsList.size() > 0) {
            for (InterfaceUserGoods interfaceUserGoods : interfaceUserGoodsList) {
                this.sqlMapClient.insert("addInterfaceUserGoods", interfaceUserGoods);
            }
        }
        if (paiPaiUpdateList != null && paiPaiUpdateList.size() > 0) {
            for (Map paiPaiMap : paiPaiUpdateList) {
                this.sqlMapClient.update("updatePaipaiStatusById", paiPaiMap);
            }
        }
    }

    public List<InterfaceUserGoods> getInterfaceUserGoodsList(Map parMap) {
        return this.sqlMapClient.queryForList("getInterfaceUserGoodsList", parMap);
    }
}
