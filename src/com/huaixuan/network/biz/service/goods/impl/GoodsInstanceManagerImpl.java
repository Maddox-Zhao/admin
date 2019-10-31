package com.huaixuan.network.biz.service.goods.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.admin.AdminLogDao;
import com.huaixuan.network.biz.dao.goods.AvailableStockDao;
import com.huaixuan.network.biz.dao.goods.GoodsDao;
import com.huaixuan.network.biz.dao.goods.GoodsInstanceDao;
import com.huaixuan.network.biz.domain.admin.Admin;
import com.huaixuan.network.biz.domain.admin.AdminLog;
import com.huaixuan.network.biz.domain.admin.Resources;
import com.huaixuan.network.biz.domain.goods.AvailableStock;
import com.huaixuan.network.biz.domain.goods.Goods;
import com.huaixuan.network.biz.domain.goods.GoodsInstance;
import com.huaixuan.network.biz.domain.storage.StockAge;
import com.huaixuan.network.biz.domain.taobao.ProductForTaobaoFxAdd;
import com.huaixuan.network.biz.enums.EnumInterfaceType;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.service.admin.ResourcesManager;
import com.huaixuan.network.biz.service.goods.GoodsInstanceManager;

@Service("goodsInstanceManager")
public class GoodsInstanceManagerImpl implements GoodsInstanceManager {

    protected Log             log = LogFactory.getLog(this.getClass());

    @Autowired
    private GoodsDao          goodsDao;

    @Autowired
    private GoodsInstanceDao  goodsInstanceDao;

    @Autowired
    private AvailableStockDao availableStockDao;

    @Autowired
    private AdminLogDao       adminLogDao;

    @Autowired
    private ResourcesManager  resourcesManager;

    //	@Autowired
    //	private TaobaoInterfaceUserGoodsManager  taobaoInterfaceUserGoodsManager;
    //
    //	@Autowired
    //	private InterfaceUserGoodsManager   interfaceUserGoodsManager;
    //
    //	@Autowired
    //	private InterfaceSyncManager   interfaceSyncManager;

    public List<AvailableStock> getAvailableStockListByInstanceId(Long goodsInstanceId) {
        return this.availableStockDao.getAvailableStockListByInstanceId(goodsInstanceId);
    }

    public AvailableStock getAvailableStock(Long goodsInstanceId, Long depFirstId) {
        return this.availableStockDao.getAvailableStock(goodsInstanceId, depFirstId);
    }

    public List<GoodsInstance> findGoodsInstances(Long goodsId) {
        if (goodsId == null) {
            throw new NullPointerException("goods id can't be null.");
        }
        return this.goodsInstanceDao.findGoodsInstancesByGoodsId(goodsId);
    }

    public void createBlankInstance(Long goodsId) {
        if (goodsId == null) {
            throw new NullPointerException("goods id can't be null.");
        }
        Goods g = null;
        try {
            g = this.goodsDao.getGoods(goodsId);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        if (g == null) {
            throw new IllegalArgumentException("goods with id" + goodsId + " not find.");
        }
        GoodsInstance gi = new GoodsInstance(g);
        createGoodsInstance(gi);
    }

    public void createGoodsInstance(GoodsInstance gi) {
        if (gi == null) {
            throw new NullPointerException("GoodsInstance can't be null.");
        }
        this.goodsInstanceDao.createGoodsInstance(gi);
    }

    public void removeGoodsInstance(GoodsInstance gi) {
        // TODO Auto-generated method stub

    }

    public void updateGoodsInstance(GoodsInstance gi) {
        if (gi == null) {
            throw new NullPointerException("GoodsInstance can't be null.");
        }
        this.goodsInstanceDao.updateGoodsInstance(gi);
    }

    public GoodsInstance getInstance(Long id) {
        if (id == null) {
            throw new NullPointerException("goods instance id can't be null.");
        }
        return this.goodsInstanceDao.getInstance(id);
    }

    public GoodsInstance getClientInstance(Map pramas) {
        return this.goodsInstanceDao.getClientInstance(pramas);
    }
    
    //	public List<GoodsInstance> searchGoodsInstances(GoodsInstance search, Page page) {
    //        if (search == null) {
    //            throw new NullPointerException("goods instance can't be null.");
    //        }
    //        List<GoodsInstance> list = this.goodsInstanceDao.searchGoodsInstances(search, page);
    //        if (list != null) {
    //            for (GoodsInstance tmp : list) {
    //                try {
    //                    Goods goods = goodsDao.getGoods(tmp.getGoodsId());
    //                    List<AvailableStock> availableStockList = availableStockDao
    //                        .getAvailableStockListByInstanceId(tmp.getId());
    //                    if (goods != null) {
    //                        tmp.setAgentPrice(goods.getAgentPrice());
    //                    }
    //                    if (availableStockList != null) {
    //                        tmp.setAvailableStockList(availableStockList);
    //                    }
    //                } catch (Exception e) {
    //                    log.error(e.getMessage());
    //                }
    //            }
    //        }
    //        return list;
    //    }

    public QueryPage searchGoodsInstancesHasStorage(GoodsInstance search, int currentPage,
                                                    int pageSize) {
        if (search == null) {
            throw new NullPointerException("goods instance can't be null.");
        }
        return this.goodsInstanceDao.searchGoodsInstancesHasStorage(search, currentPage, pageSize);
    }

    public void updateGoodsInstanceLocations(Long id, List<Long> los) {
        if (id == null) {
            throw new NullPointerException("goods instance id can't be null.");
        }
        if (los == null) {
            throw new NullPointerException("location ids can't be null.");
        }
        try {
            this.goodsInstanceDao.updateGoodsInstanceLocations(id, los);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    public List<Long> getGoodsInstanceLocations(Long id) {
        if (id == null) {
            throw new NullPointerException("goods instance id can't be null.");
        }
        return this.goodsInstanceDao.findGoodsInstanceLocations(id);
    }

    public List<Long> getGoodsInstanceInLocations(Long locId) {
        if (locId == null) {
            throw new NullPointerException("goods instance id can't be null.");
        }
        return this.goodsInstanceDao.findGoodsInstanceInLocations(locId);
    }

    public String createNewInstanceCode(Goods goods) {
        return "" + System.currentTimeMillis();
    }

    public boolean checkCode(GoodsInstance gi) {
        String code = gi.getCode();
        if (StringUtils.isBlank(code)) {
            return true;
        }
        List<GoodsInstance> find = this.goodsInstanceDao.findGoodsInstancesByCode(code);
        Long giid = gi.getId();
        if (giid == null) {
            return find == null || find.isEmpty();
        }
        for (GoodsInstance g : find) {
            if (!g.getId().equals(giid)) {
                return false;
            }
        }
        return true;
    }

    public int updateGoodsInstanceExistNumById(Long id, Long count) {
        log.info("GoodsInstanceManagerImpl.updateGoodsInstanceExistNumById method");
        try {
            return this.goodsInstanceDao.updateGoodsInstanceExistNumById(id, count);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return 0;
    }
    
    public int updateGoodsInstanceSaleNumberById(Long id, Long count) {
        log.info("GoodsInstanceManagerImpl.updateGoodsInstanceSaleNumberById method");
        try {
            return this.goodsInstanceDao.updateGoodsInstanceSaleNumberById(id, count);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return 0;
    }
    
    
    public int updateGoodsInstanceHKExistNumById(Long id, Long count) {
        log.info("GoodsInstanceManagerImpl.updateGoodsInstanceHKExistNumById method");
        try {
            return this.goodsInstanceDao.updateGoodsInstanceHKExistNumById(id, count);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return 0;
    }
    
    public int updateGoodsInstanceNumberZero() {
        log.info("GoodsInstanceManagerImpl.updateGoodsInstanceNumberZero method");
        try {
            return this.goodsInstanceDao.updateGoodsInstanceNumberZero();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return 0;
    }
    

    /*
     * （非 Javadoc＄1??7
     *
     * @see com.hundsun.bible.facade.ios.GoodsInstanceManager#updateWayNumById(java.lang.Long,
     *      java.lang.Long)
     */
    public int updateWayNumById(Long id, Long count) {
        log.info("GoodsInstanceManagerImpl.updateWayNumById method");
        try {
            return this.goodsInstanceDao.updateWayNumById(id, count);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return 0;
    }

    //    public List<GoodsInstance> searchSupplierGoodsInstances(GoodsInstance search, Page page,
    //                                                            String supplierId) {
    //        if (search == null) {
    //            throw new NullPointerException("goods instance can't be null.");
    //        }
    //        if (page == null) {
    //            throw new NullPointerException("page can't be null.");
    //        }
    //        if (supplierId == null) {
    //            throw new NullPointerException("supplier id can't be null.");
    //        }
    //        return this.goodsInstanceDao.searchSupplierGoodsInstances(search, page, supplierId);
    //    }

    public Boolean updateAmountForTwo(Long goodsInstanceId, Long goodsId, Long amount,
                                      Long depFirstId, boolean paipaiRelation) {
        log.info("GoodsInstanceManagerImpl.updateAmountForThree method");
        try {

            if (goodsInstanceId != null && goodsId != null && depFirstId != null) {

                //              记录可用数量变更
                GoodsInstance gi = (GoodsInstance) goodsInstanceDao.getInstance(goodsInstanceId);

                //查看可用库存表中记录是否存在
//                AvailableStock availableStockTmp = availableStockDao.getAvailableStock(
//                    goodsInstanceId, depFirstId);
//                if (null == availableStockTmp) {//新增
//                    AvailableStock availableStock = new AvailableStock();
//                    availableStock.setDepFirstId(depFirstId);
//                    availableStock.setGoodsId(goodsId);
//                    availableStock.setGoodsInstanceId(goodsInstanceId);
//                    availableStock.setGoodsNumber(amount);
//                    availableStockDao.addAvailableStock(availableStock);
//                } else {//更新库存数量
//                    availableStockDao.updateAvailableStockGoodsNumber(goodsInstanceId, depFirstId,
//                        amount);
//                }
                // 更新商品实例表的库存数量
                this.goodsInstanceDao.updateGoodsInstanceExistNumById(goodsInstanceId, amount);
                // 更新商品表的库存数量以及销售数量
                this.goodsDao.updateGoodsGoodsNumberById(goodsId, amount,false);

                //记录日志 shengy
                if (gi != null) {
                    AdminLog adminLog = new AdminLog();
                    adminLog.setAccount("updateAmountForTwo");
                    adminLog.setIp("");
                    adminLog.setOperationType("goodsInstanceId:" + goodsInstanceId + "goodsCode:"
                                              + gi.getCode());
                    int eNum = 0;
                    int nNum = 0;
                    if (gi.getExistNum() != null) {
                        eNum = gi.getExistNum().intValue();
                    }
                    if (amount != null) {
                        nNum = amount.intValue();
                    }
                    //记录可用数量变更
                    GoodsInstance giAfter = (GoodsInstance) goodsInstanceDao
                        .getInstance(goodsInstanceId);

                    adminLog.setContent("now:" + eNum + ";after" + (eNum + nNum) + "fact:"
                                        + giAfter.getExistNum().toString());
                    adminLogDao.addAdminLog(adminLog);
                }

            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return Boolean.FALSE;
    }

    public Boolean updateAmountForTwo(Long goodsInstanceId, Long goodsId, Long amount) {
        log.info("GoodsInstanceManagerImpl.updateAmountForThree method");
        try {

            if (goodsInstanceId != null && goodsId != null) {
                //              记录可用数量变更
                GoodsInstance gi = (GoodsInstance) goodsInstanceDao.getInstance(goodsInstanceId);

                // 更新商品实例表的库存数量
                this.goodsInstanceDao.updateGoodsInstanceExistNumById(goodsInstanceId, amount);
                // 更新商品表的库存数量以及销售数量
                this.goodsDao.updateGoodsGoodsNumberById(goodsId, amount,false);

                if (gi != null) {
                    AdminLog adminLog = new AdminLog();
                    adminLog.setAccount("updateAmountForTwo");
                    adminLog.setIp("");
                    adminLog.setOperationType("goodsInstanceId:" + goodsInstanceId + "goodsCode:"
                                              + gi.getCode());
                    int eNum = 0;
                    int nNum = 0;
                    if (gi.getExistNum() != null) {
                        eNum = gi.getExistNum().intValue();
                    }
                    if (amount != null) {
                        nNum = amount.intValue();
                    }
                    //记录可用数量变更
                    GoodsInstance giAfter = (GoodsInstance) goodsInstanceDao
                        .getInstance(goodsInstanceId);

                    adminLog.setContent("now:" + eNum + ";after" + (eNum + nNum) + "fact:"
                                        + giAfter.getExistNum().toString());
                    adminLogDao.addAdminLog(adminLog);
                }

                return Boolean.TRUE;
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return Boolean.FALSE;
    }

    public int getStockNumByInstanceId(Long id, Long supplierId, Long locId, String storType,
                                       int days , int afterDays) {
        try {
            if (id != null && supplierId != null && storType != null) {
                Map map = new HashMap();
                map.put("id", id);
                map.put("supplierId", supplierId);
                map.put("locId", locId);
                map.put("storType", storType);
                if (days != 0) {
                    map.put("days", days);
                }
                if (afterDays != 0) {
                    map.put("afterDays", afterDays);
                }
                return (Integer) this.goodsInstanceDao.getStockNumByInstanceId(map);
            }

        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return 0;
    }

    public long getStockAgeListByInstanceId(Long locId) {
        log.info("GoodsInstanceManagerImpl.getStockAgeListByInstanceId method");
        try {
            if (locId != null) {

                return this.goodsInstanceDao.getStockAgeListByInstanceId(locId);

            }

        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return 0;
    }

    public List<StockAge> getStockSupplierByInstanceId(Long id) {
        log.info("GoodsInstanceManagerImpl.getStockSupplierByInstanceId method");
        try {
            return this.goodsInstanceDao.getStockSupplierByInstanceId(id);

        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    public void insertStockAgeBySa(StockAge sa) {
        try {
            this.goodsInstanceDao.insertStockAgeBySa(sa);
        } catch (Exception e) {
            log.error(e.getMessage());
        }

    }

    public int updateStockAgeBysa(StockAge sa) {
        try {
            return this.goodsInstanceDao.updateStockAgeBysa(sa);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return 0;
    }

    //    public List<ProductForTaobaoFxAdd> getProductsForTaobaoFxAdd(ProductForTaobaoFxAdd productForTaobaoFxAdd, Page page) {
    //    	log.info("GoodsInstanceManagerImpl.getProductsForTaobaoFxAdd method");
    //        try {
    //    	  return goodsInstanceDao.getProductsForTaobaoFxAdd(productForTaobaoFxAdd, page);
    //        }catch(Exception e){
    //        	log.error(e.getMessage());
    //        }
    //		return null;
    //	}

    public int getProductsForTaobaoFxAddCount(ProductForTaobaoFxAdd productForTaobaoFxAdd) {
        log.info("GoodsInstanceManagerImpl.getProductsForTaobaoFxAddCount method");
        try {
            return goodsInstanceDao.getProductsForTaobaoFxAddCount(productForTaobaoFxAdd);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return 0;
    }

    public List<GoodsInstance> getGoodsInstances() {
        return this.goodsInstanceDao.getGoodsInstances();
    }

    //-- add by wangkun 2010-09-28 start 添加按产品编码查询方法 --//
    public GoodsInstance getInstanceByCode(String code) {
        if (StringUtils.isBlank(code)) {
            throw new NullPointerException("goods instance code can't be null.");
        }
        return this.goodsInstanceDao.getInstanceByCode(code);
    }

    //-- add by wangkun 2010-09-28 end --//
    //该方法已经不使用
    public Boolean updateAmountForTaobao(Long goodsInstanceId, Long goodsId, Long amount,
                                         Long depFirstId, boolean taobaoRelation) {
        log.info("GoodsInstanceManagerImpl.updateAmountForTaobao method");
        try {

            if (goodsInstanceId != null && goodsId != null && depFirstId != null) {

                //              记录可用数量变更
                GoodsInstance gi = (GoodsInstance) goodsInstanceDao.getInstance(goodsInstanceId);

                //查看可用库存表中记录是否存在
                AvailableStock availableStockTmp = availableStockDao.getAvailableStock(
                    goodsInstanceId, depFirstId);
                if (null == availableStockTmp) {//新增
                    AvailableStock availableStock = new AvailableStock();
                    availableStock.setDepFirstId(depFirstId);
                    availableStock.setGoodsId(goodsId);
                    availableStock.setGoodsInstanceId(goodsInstanceId);
                    availableStock.setGoodsNumber(amount);
                    availableStockDao.addAvailableStock(availableStock);
                } else {//更新库存数量
                    availableStockDao.updateAvailableStockGoodsNumber(goodsInstanceId, depFirstId,
                        amount);
                }
                // 更新商品实例表的库存数量
                this.goodsInstanceDao.updateGoodsInstanceExistNumById(goodsInstanceId, amount);
                // 更新商品表的库存数量以及销售数量
                this.goodsDao.updateGoodsGoodsNumberById(goodsId, amount,false);

                //记录日志 shengy
                if (gi != null) {
                    AdminLog adminLog = new AdminLog();
                    adminLog.setAccount("updateAmountTaobao");
                    adminLog.setIp("");
                    adminLog.setOperationType("goodsInstanceId:" + goodsInstanceId + "goodsCode:"
                                              + gi.getCode());
                    int eNum = 0;
                    int nNum = 0;
                    if (gi.getExistNum() != null) {
                        eNum = gi.getExistNum().intValue();
                    }
                    if (amount != null) {
                        nNum = amount.intValue();
                    }
                    //记录可用数量变更
                    GoodsInstance giAfter = (GoodsInstance) goodsInstanceDao
                        .getInstance(goodsInstanceId);

                    adminLog.setContent("now:" + eNum + ";after" + (eNum + nNum) + "fact:"
                                        + giAfter.getExistNum().toString());
                    adminLogDao.addAdminLog(adminLog);
                }

                //是否与淘宝网关联数量
                if (taobaoRelation) {
                    Resources resources = resourcesManager.getResourcesByTypeAndName("taobao",
                        "depFirstId");
                    Goods goods = goodsDao.getGoods(goodsId);
                    if (depFirstId != null && Long.parseLong(resources.getValue()) == depFirstId) {
                        if (goodsId != null && "y".equals(goods.getIsTaobao())) {
                            Map parMap = new HashMap();
                            parMap.put("goodsId", goodsId);
                            parMap.put("status", "y");
                            parMap.put("type", EnumInterfaceType.TAOBAO.getKey());
                            //                            List<TaobaoInterfaceUserGoods> interfaceUserGoodsList = taobaoInterfaceUserGoodsManager.getInterfaceUserGoodsList(parMap);
                            //                            if(interfaceUserGoodsList!=null && interfaceUserGoodsList.size() > 0){
                            //                                List<InterfaceSync> taobaoInterfaceList = new ArrayList<InterfaceSync>();
                            //                                for(TaobaoInterfaceUserGoods interfaceUserGoods :interfaceUserGoodsList){
                            //                                    InterfaceSync interfaceSync = new InterfaceSync();
                            //                                    interfaceSync.setInterfaceType(EnumInterfaceName.TAOBAOSTOCKSYNC.getKey());
                            //                                    interfaceSync.setGoodsId(goodsId);
                            //                                    interfaceSync.setGoodsInstanceId(goodsInstanceId);
                            //                                    interfaceSync.setUserId(interfaceUserGoods.getUserId());
                            //                                    interfaceSync.setStatus("n");
                            //                                    taobaoInterfaceList.add(interfaceSync);
                            //                                }
                            //                                interfaceSyncManager.insertBatchSyncList(taobaoInterfaceList);
                            //                            }
                        }
                    }
                }

                return Boolean.TRUE;
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return Boolean.FALSE;
    }

    /**
     * 根据goodsInsId查询淘宝属性key
     * @param id
     * @return
     * @see com.hundsun.bible.dao.ios.GoodsInstanceDao#getInstanceByCode(java.lang.Long)
     */
    public GoodsInstance getInstanceTaobaoSkuPropById(Long id) {
        return (GoodsInstance) goodsInstanceDao.getInstanceTaobaoSkuPropById(id);
    }

    public List<String> getSkuPropertyListByGoodsId(Long goodsId) {
        return goodsInstanceDao.getSkuPropertyListByGoodsId(goodsId);

    }

    //	public List<GoodsInstance> getSaleGiftGoodsInstance(Map parMap, Page page) {
    //		return goodsInstanceDao.getSaleGiftGoodsInstance(parMap,page);
    //	}

    public List<GoodsInstance> getFullGiveGoodsInstance(List idList) {
        Map map = new HashMap();
        map.put("idList", idList);
        return goodsInstanceDao.getFullGiveGoodsInstance(map);
    }

    public List<GoodsInstance> getAllFullGiveGoodsInstance(List<String> idList) {
        List<GoodsInstance> resultList = new ArrayList<GoodsInstance>();
        for (String obj : idList) {
            if (StringUtils.isNotBlank(obj) && StringUtils.isNumeric(obj)) {
                resultList.add(goodsInstanceDao.getInstance(Long.parseLong(obj)));
            } else {
                resultList.add(null);
            }
        }
        return resultList;
    }

    @SuppressWarnings("unchecked")
	@Override
    public QueryPage getInstanceListByConditionWithPage(GoodsInstance instance, int currPage,
                                                        int pageSize) {
        QueryPage queryPage = new QueryPage(instance);
        Map pramas = queryPage.getParameters();

        int count = goodsInstanceDao.getInstanceListByConditionWithPageCount(pramas);

        if (count > 0) {

            /* 当前页 */
            queryPage.setCurrentPage(currPage);
            /* 每页总数 */
            queryPage.setPageSize(pageSize);
            queryPage.setTotalItem(count);

            pramas.put("startRow", queryPage.getPageFristItem());
            pramas.put("endRow", queryPage.getPageLastItem());

            List<GoodsInstance> instanceList = goodsInstanceDao
                .getInstanceListByConditionWithPage(pramas);
            if (instanceList != null && instanceList.size() > 0) {
                if (instanceList != null) {
                    for (GoodsInstance tmp : instanceList) {
                        try {
                            Goods goods = goodsDao.getGoods(tmp.getGoodsId());
                            List<AvailableStock> availableStockList = availableStockDao.getAvailableStockListByInstanceId(tmp.getId());
                            if (goods != null) {
                                tmp.setAgentPrice(goods.getAgentPrice());
                            }
                            if (availableStockList != null) {
                                tmp.setAvailableStockList(availableStockList);
                            }
                        } catch (Exception e) {
                            log.error(e.getMessage());
                        }
                    }
                }
            	
                queryPage.setItems(instanceList);
            }
        }
        return queryPage;
    }

    @SuppressWarnings("unchecked")
	@Override
    public List<GoodsInstance> getInstanceListByConditionWithPage(GoodsInstance instance) {
        QueryPage queryPage = new QueryPage(instance);
        Map pramas = queryPage.getParameters();
        List<GoodsInstance> instanceList = goodsInstanceDao
            .getInstanceListByConditionWithPage(pramas);
        return instanceList;
    }

    @SuppressWarnings("unchecked")
	public QueryPage getSupplierInstanceListByConditionWithPage(GoodsInstance instance,
                                                                int currPage, int pageSize) {
        QueryPage queryPage = new QueryPage(instance);
        Map pramas = queryPage.getParameters();

        int count = goodsInstanceDao.getSupplierInstanceListByConditionWithPageCount(pramas);

        if (count > 0) {

            /* 当前页 */
            queryPage.setCurrentPage(currPage);
            /* 每页总数 */
            queryPage.setPageSize(pageSize);
            queryPage.setTotalItem(count);

            pramas.put("startRow", queryPage.getPageFristItem());
            pramas.put("endRow", queryPage.getPageLastItem());

            List<GoodsInstance> instanceList = goodsInstanceDao
                .getSupplierInstanceListByConditionWithPage(pramas);
            if (instanceList != null && instanceList.size() > 0) {
                queryPage.setItems(instanceList);
            }
        }
        return queryPage;
    }

    @SuppressWarnings("unchecked")
	@Override
    public QueryPage getSaleGiftInstanceListByConditionWithPage(GoodsInstance instance,
                                                                int currPage, int pageSize) {
        QueryPage queryPage = new QueryPage(instance);
        Map pramas = queryPage.getParameters();

        int count = goodsInstanceDao.getSaleGiftInstanceListByConditionWithPageCount(pramas);

        if (count > 0) {

            /* 当前页 */
            queryPage.setCurrentPage(currPage);
            /* 每页总数 */
            queryPage.setPageSize(pageSize);
            queryPage.setTotalItem(count);

            pramas.put("startRow", queryPage.getPageFristItem());
            pramas.put("endRow", queryPage.getPageLastItem());

            List<GoodsInstance> instanceList = goodsInstanceDao
                .getSaleGiftInstanceListByConditionWithPage(pramas);
            if (instanceList != null && instanceList.size() > 0) {
                queryPage.setItems(instanceList);
            }
        }
        return queryPage;
    }

    @SuppressWarnings("unchecked")
	@Override
    public QueryPage getBackInstanceListByConditionWithPage(GoodsInstance instance, int currPage,
                                                            int pageSize) {
        QueryPage queryPage = new QueryPage(instance);
        Map pramas = queryPage.getParameters();

        int count = goodsInstanceDao.getBackInstanceListByConditionWithPageCount(pramas);

        if (count > 0) {

            /* 当前页 */
            queryPage.setCurrentPage(currPage);
            /* 每页总数 */
            queryPage.setPageSize(pageSize);
            queryPage.setTotalItem(count);

            pramas.put("startRow", queryPage.getPageFristItem());
            pramas.put("endRow", queryPage.getPageLastItem());

            List<GoodsInstance> instanceList = goodsInstanceDao
                .getBackInstanceListByConditionWithPage(pramas);
            if (instanceList != null && instanceList.size() > 0) {
                for (GoodsInstance tmp : instanceList) {
                    Goods goods = goodsDao.getGoods(tmp.getGoodsId());
                    List<AvailableStock> availableStockList = availableStockDao
                        .getAvailableStockListByInstanceId(tmp.getId());
                    if (goods != null) {
                        tmp.setAgentPrice(goods.getAgentPrice());
                        tmp.setGoodsWeight(goods.getGoodsWeight());
                    }
                    if (availableStockList != null) {
                        tmp.setAvailableStockList(availableStockList);
                    }
                }
                queryPage.setItems(instanceList);
            }
        }
        return queryPage;
    }

    @SuppressWarnings("unchecked")
	@Override
    public List<GoodsInstance> searchGoodsInstancesHasStorage(GoodsInstance search) {
        QueryPage queryPage = new QueryPage(search);
        Map pramas = queryPage.getParameters();
        return goodsInstanceDao.searchGoodsInstancesHasStorage(pramas);
    }

}
