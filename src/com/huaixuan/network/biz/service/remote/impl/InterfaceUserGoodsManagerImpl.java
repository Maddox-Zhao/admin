package com.huaixuan.network.biz.service.remote.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.agent.InterfaceSyncDao;
import com.huaixuan.network.biz.dao.agent.InterfaceUserGoodsDao;
import com.huaixuan.network.biz.domain.remote.InterfaceSync;
import com.huaixuan.network.biz.domain.remote.InterfaceUserGoods;
import com.huaixuan.network.biz.enums.EnumInterfaceName;
import com.huaixuan.network.biz.enums.EnumInterfaceType;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.service.remote.InterfaceUserGoodsManager;

@Service("interfaceUserGoodsManager")
public class InterfaceUserGoodsManagerImpl implements InterfaceUserGoodsManager {

    protected final Log   log = LogFactory.getLog(this.getClass());

    @Autowired
    InterfaceUserGoodsDao interfaceUserGoodsDao;

    @Autowired
    InterfaceSyncDao      interfaceSyncDao;

    public void addInterfaceUserGoods(InterfaceUserGoods interfaceUserGoods) {
        this.interfaceUserGoodsDao.addInterfaceUserGoods(interfaceUserGoods);
    }

    public void updateInterfaceUserGoodsStatus(InterfaceUserGoods interfaceUserGoods) {
        this.interfaceUserGoodsDao.updateInterfaceUserGoodsStatus(interfaceUserGoods);
    }

    public List<InterfaceUserGoods> getInterfaceUserGoodsByCondition(Map parMap) {
        return this.interfaceUserGoodsDao.getInterfaceUserGoodsByCondition(parMap);
    }

    public int getInterfaceUserGoodsCountByCondition(Map parMap) {
        return this.interfaceUserGoodsDao.getInterfaceUserGoodsCountByCondition(parMap);
    }

    public boolean updateBatchPaipaiRelation(List<String> relationIds) {
        if (null == relationIds)
            return false;
        else {
            try {
                List<Map> paiPaiUpdateList = new ArrayList<Map>();
                for (String id : relationIds) {
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("id", id);
                    map.put("status", "n");
                    paiPaiUpdateList.add(map);
                }
                interfaceUserGoodsDao.updateBatchPaipaiRelation(paiPaiUpdateList);
                return true;
            } catch (Exception e) {
                this.log.error(e);
                return false;
            }
        }
    }

    public void insertBatchInterfaceGoods(List<String> goodsIds, Long userId) {
        List<InterfaceUserGoods> interfaceUserGoodsList = new ArrayList<InterfaceUserGoods>();
        List<Map> paiPaiUpdateList = new ArrayList<Map>();
        List<InterfaceSync> paiPaiSyncList = new ArrayList<InterfaceSync>();
        for (String goodsId : goodsIds) {
            Map parMap = new HashMap();
            parMap.put("goodsId", goodsId);
            parMap.put("userId", userId);
            parMap.put("type", EnumInterfaceType.PAIPAI.getKey());
            InterfaceUserGoods interfaceUserGoodsTemp = interfaceUserGoodsDao
                .getInterfaceUserGoods(parMap);
            if (interfaceUserGoodsTemp == null) {
                InterfaceUserGoods interfaceUserGoods = new InterfaceUserGoods();
                interfaceUserGoods.setGoodsId(Long.parseLong(goodsId));
                interfaceUserGoods.setUserId(userId);
                interfaceUserGoods.setType(EnumInterfaceType.PAIPAI.getKey());
                interfaceUserGoods.setStatus("z");
                interfaceUserGoodsList.add(interfaceUserGoods);
                //
                InterfaceSync interfaceSync = new InterfaceSync();
                interfaceSync.setGoodsId(Long.parseLong(goodsId));
                interfaceSync.setUserId(userId);
                interfaceSync.setInterfaceType(EnumInterfaceName.PAIPAIPUBLISHGOODS.getKey());
                interfaceSync.setStatus("n");
                paiPaiSyncList.add(interfaceSync);
            } else if (interfaceUserGoodsTemp.getStatus().equals("n")) {
                Map updateMap = new HashMap();
                updateMap.put("id", interfaceUserGoodsTemp.getId());
                updateMap.put("status", "z");
                paiPaiUpdateList.add(updateMap);
                //
                InterfaceSync interfaceSync = new InterfaceSync();
                interfaceSync.setGoodsId(Long.parseLong(goodsId));
                interfaceSync.setUserId(userId);
                interfaceSync.setInterfaceType(EnumInterfaceName.PAIPAIPUBLISHGOODS.getKey());
                interfaceSync.setStatus("n");
                paiPaiSyncList.add(interfaceSync);
            } else {
                continue;
            }
        }
        interfaceUserGoodsDao.insertBatchInterfaceGoods(interfaceUserGoodsList, paiPaiUpdateList);
        interfaceSyncDao.insertBatchSyncList(paiPaiSyncList);
    }

    public List<InterfaceUserGoods> getInterfaceUserGoodsList(Map parMap) {
        return this.interfaceUserGoodsDao.getInterfaceUserGoodsList(parMap);
    }

    public QueryPage getInterfaceUserGoodsByConditionQuery(Map parMap, int currPage, int pageSize) {
        QueryPage queryPage = new QueryPage(parMap);

        try {
            int count = interfaceUserGoodsDao.getInterfaceUserGoodsCountByCondition(parMap);

            if (count > 0) {

                /* 当前页 */
                queryPage.setCurrentPage(currPage);
                /* 每页总数 */
                queryPage.setPageSize(pageSize);
                queryPage.setTotalItem(count);

                parMap.put("startRow", queryPage.getPageFristItem());
                parMap.put("endRow", queryPage.getPageLastItem());

                List<InterfaceUserGoods> interfaceSyncList = interfaceUserGoodsDao
                    .getInterfaceUserGoodsByCondition(parMap);
                if (interfaceSyncList != null && interfaceSyncList.size() > 0) {
                    queryPage.setItems(interfaceSyncList);
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return queryPage;
    }

    @Override
    public InterfaceUserGoods getInterfaceUserGoods(Map parMap) {
        return this.interfaceUserGoodsDao.getInterfaceUserGoods(parMap);
    }

    //    @Override
    //    public InterfaceUserGoods getInterfaceUserGoods(Map parMap) {
    //        return null;
    //    }

}
