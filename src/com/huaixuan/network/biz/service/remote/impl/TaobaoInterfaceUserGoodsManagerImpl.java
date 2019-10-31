package com.huaixuan.network.biz.service.remote.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.remote.TaobaoInterfaceSyncDao;
import com.huaixuan.network.biz.dao.remote.TaobaoInterfaceUserGoodsDao;
import com.huaixuan.network.biz.domain.taobao.TaobaoInterfaceSync;
import com.huaixuan.network.biz.domain.taobao.TaobaoInterfaceUserGoods;
import com.huaixuan.network.biz.enums.EnumInterfaceType;
import com.huaixuan.network.biz.enums.EnumTaobaoInterfaceName;
import com.huaixuan.network.biz.service.remote.TaobaoInterfaceUserGoodsManager;

@Service("taobaoInterfaceUserGoodsManager")
public class TaobaoInterfaceUserGoodsManagerImpl implements TaobaoInterfaceUserGoodsManager {
    protected final Log         log = LogFactory.getLog(this.getClass());
    @Autowired
    TaobaoInterfaceUserGoodsDao taobaoInterfaceUserGoodsDao;
    //add by jinxx 2010/10/27 start
    @Autowired
    TaobaoInterfaceSyncDao      taobaoInterfaceSyncDao;

    //add by jinxx 2010/10/27 end
    public List<TaobaoInterfaceUserGoods> getInterfaceUserGoodsByCondition(Map parMap) {
        return this.taobaoInterfaceUserGoodsDao.getInterfaceUserGoodsList(parMap);
    }

    public TaobaoInterfaceUserGoods getInterfaceUserGoods(Map parMap) {
        return this.taobaoInterfaceUserGoodsDao.getInterfaceUserGoods(parMap);
    }

    public void addInterfaceUserGoods(TaobaoInterfaceUserGoods interfaceUserGoods) {
        this.taobaoInterfaceUserGoodsDao.addInterfaceUserGoods(interfaceUserGoods);
    }

    public void updateInterfaceUserGoodsStatus(TaobaoInterfaceUserGoods interfaceUserGoods) {
        this.taobaoInterfaceUserGoodsDao.updateInterfaceUserGoodsStatus(interfaceUserGoods);
    }

    //    public List<TaobaoInterfaceUserGoods> getInterfaceUserGoodsByCondition(Map parMap) {
    //        return this.taobaoInterfaceUserGoodsDao.getInterfaceUserGoodsByCondition(parMap);
    //    }

    public int getInterfaceUserGoodsCountByCondition(Map parMap) {
        return this.taobaoInterfaceUserGoodsDao.getInterfaceUserGoodsCountByCondition(parMap);
    }

    public boolean updateBatchTaobaoRelation(List<String> relationIds) {
        if (null == relationIds)
            return false;
        else {
            try {
                List<Map> taobaoUpdateList = new ArrayList<Map>();
                for (String id : relationIds) {
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("id", id);
                    map.put("status", "n");
                    taobaoUpdateList.add(map);
                }
                taobaoInterfaceUserGoodsDao.updateBatchTaobaoRelation(taobaoUpdateList);
                return true;
            } catch (Exception e) {
                this.log.error(e);
                return false;
            }
        }
    }

    public void insertBatchInterfaceGoods(List<String> goodsIds, Long userId) {
        List<TaobaoInterfaceUserGoods> interfaceUserGoodsList = new ArrayList<TaobaoInterfaceUserGoods>();
        List<Map> paiPaiUpdateList = new ArrayList<Map>();
        List<TaobaoInterfaceSync> taobaoSyncList = new ArrayList<TaobaoInterfaceSync>();
        for (String goodsId : goodsIds) {
            Map parMap = new HashMap();
            parMap.put("goodsId", goodsId);
            parMap.put("userId", userId);
            parMap.put("type", EnumInterfaceType.TAOBAO.getKey());
            TaobaoInterfaceUserGoods interfaceUserGoodsTemp = taobaoInterfaceUserGoodsDao
                .getInterfaceUserGoods(parMap);
            if (interfaceUserGoodsTemp == null) {
                TaobaoInterfaceUserGoods interfaceUserGoods = new TaobaoInterfaceUserGoods();
                interfaceUserGoods.setGoodsId(Long.parseLong(goodsId));
                interfaceUserGoods.setUserId(userId);
                interfaceUserGoods.setType(EnumInterfaceType.TAOBAO.getKey());
                interfaceUserGoods.setStatus("z");
                interfaceUserGoodsList.add(interfaceUserGoods);
                //加到同步�
                TaobaoInterfaceSync interfaceSync = new TaobaoInterfaceSync();
                interfaceSync.setGoodsId(Long.parseLong(goodsId));
                interfaceSync.setUserId(userId);
                interfaceSync.setInterfaceType(EnumTaobaoInterfaceName.TAOBAOPUBLISHGOODS.getKey());
                interfaceSync.setStatus("n");
                //modified by chenhang 2010-01-10 同步数据唯一 started
                if (taobaoInterfaceSyncDao.getInterfaceSyncListForCheck(parMap) > 0) {
                    taobaoInterfaceSyncDao.updateSync(interfaceSync);
                } else {
                    taobaoSyncList.add(interfaceSync);
                }
                //modified by chenhang 2010-01-10 同步数据唯一 ended
            } else if (interfaceUserGoodsTemp.getStatus().equals("n")) {
                Map updateMap = new HashMap();
                updateMap.put("id", interfaceUserGoodsTemp.getId());
                updateMap.put("status", "z");
                paiPaiUpdateList.add(updateMap);
                //加到同步�
                TaobaoInterfaceSync interfaceSync = new TaobaoInterfaceSync();
                interfaceSync.setGoodsId(Long.parseLong(goodsId));
                interfaceSync.setUserId(userId);
                interfaceSync.setInterfaceType(EnumTaobaoInterfaceName.TAOBAOPUBLISHGOODS.getKey());
                interfaceSync.setStatus("n");
                //modified by chenhang 2010-01-10 同步数据唯一 started
                if (taobaoInterfaceSyncDao.getInterfaceSyncListForCheck(parMap) > 0) {
                    taobaoInterfaceSyncDao.updateSync(interfaceSync);
                } else {
                    taobaoSyncList.add(interfaceSync);
                }
                //modified by chenhang 2010-01-10 同步数据唯一 ended
            } else {
                continue;
            }
        }
        //add by jinxx 2010/10/27 start for 批量插入interface_sync�
        if (taobaoSyncList != null && taobaoSyncList.size() > 0) {
            taobaoInterfaceSyncDao.insertBatchSyncList(taobaoSyncList);
        }
        //add by jinxx 2010/10/27 end for 批量插入interface_sync�
        taobaoInterfaceUserGoodsDao.insertBatchInterfaceGoods(interfaceUserGoodsList,
            paiPaiUpdateList);
    }

    public List<TaobaoInterfaceUserGoods> getInterfaceUserGoodsList(Map parMap) {
        return this.taobaoInterfaceUserGoodsDao.getInterfaceUserGoodsList(parMap);
    }

    public List<TaobaoInterfaceUserGoods> getInterfaceUserGoodsListByIds(List ids) {
        return this.taobaoInterfaceUserGoodsDao.getInterfaceUserGoodsListByIds(ids);
    }

}
