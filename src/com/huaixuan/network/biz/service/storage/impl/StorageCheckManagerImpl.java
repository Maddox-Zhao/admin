package com.huaixuan.network.biz.service.storage.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.huaixuan.network.biz.dao.goods.GoodsDao;
import com.huaixuan.network.biz.dao.storage.DepLocationDao;
import com.huaixuan.network.biz.dao.storage.DepositoryDao;
import com.huaixuan.network.biz.dao.storage.InDepositoryDao;
import com.huaixuan.network.biz.dao.storage.InDetailDao;
import com.huaixuan.network.biz.dao.storage.OutDepositoryDao;
import com.huaixuan.network.biz.dao.storage.OutDetailDao;
import com.huaixuan.network.biz.dao.storage.ProdRelationInDao;
import com.huaixuan.network.biz.dao.storage.ProdRelationOutDao;
import com.huaixuan.network.biz.dao.storage.StorageCheckDao;
import com.huaixuan.network.biz.dao.storage.StorageCheckDetailDao;
import com.huaixuan.network.biz.dao.storage.StorageCheckListDao;
import com.huaixuan.network.biz.dao.storage.StorageDao;
import com.huaixuan.network.biz.domain.storage.DepLocation;
import com.huaixuan.network.biz.domain.storage.Depository;
import com.huaixuan.network.biz.domain.storage.DepositoryFirst;
import com.huaixuan.network.biz.domain.storage.InDepository;
import com.huaixuan.network.biz.domain.storage.InDetail;
import com.huaixuan.network.biz.domain.storage.InDetailGb;
import com.huaixuan.network.biz.domain.storage.OutDepository;
import com.huaixuan.network.biz.domain.storage.OutDetail;
import com.huaixuan.network.biz.domain.storage.OutDetailGoods;
import com.huaixuan.network.biz.domain.storage.ProdRelationIn;
import com.huaixuan.network.biz.domain.storage.ProdRelationInGb;
import com.huaixuan.network.biz.domain.storage.ProdRelationOut;
import com.huaixuan.network.biz.domain.storage.StockAge;
import com.huaixuan.network.biz.domain.storage.Storage;
import com.huaixuan.network.biz.domain.storage.StorageCheck;
import com.huaixuan.network.biz.domain.storage.StorageCheckDetail;
import com.huaixuan.network.biz.domain.storage.StorageCheckList;
import com.huaixuan.network.biz.domain.storage.query.StorageCheckQuery;
import com.huaixuan.network.biz.enums.EnumDepLocationIsCheck;
import com.huaixuan.network.biz.enums.EnumDepositoryType;
import com.huaixuan.network.biz.enums.EnumFinanceStatus;
import com.huaixuan.network.biz.enums.EnumInDepository;
import com.huaixuan.network.biz.enums.EnumInDetailStatus;
import com.huaixuan.network.biz.enums.EnumInStatus;
import com.huaixuan.network.biz.enums.EnumOutDepository;
import com.huaixuan.network.biz.enums.EnumOutDetailStatus;
import com.huaixuan.network.biz.enums.EnumOutStatus;
import com.huaixuan.network.biz.enums.EnumStorType;
import com.huaixuan.network.biz.exception.ManagerException;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.service.common.CodeManager;
import com.huaixuan.network.biz.service.goods.GoodsInstanceManager;
import com.huaixuan.network.biz.service.storage.DepositoryFirstManager;
import com.huaixuan.network.biz.service.storage.InDetailGbManager;
import com.huaixuan.network.biz.service.storage.OutDepositoryManager;
import com.huaixuan.network.biz.service.storage.ProdRelationInGbManager;
import com.huaixuan.network.biz.service.storage.StorageCheckManager;
import com.hundsun.network.melody.common.util.StringUtil;

/**
 * @version 3.2.0
 */
@Service("storageCheckManager")
public class StorageCheckManagerImpl implements StorageCheckManager {
	/**
     * Log instance for all child classes. Uses LogFactory.getLog(getClass()) from Commons Logging
     */
    protected final Log    log = LogFactory.getLog(getClass());

	@Autowired
    StorageCheckDao        storageCheckDao;
	@Autowired
    StorageCheckListDao   storageCheckListDao;
	@Autowired
    StorageCheckDetailDao storageCheckDetailDao;
	@Autowired
    StorageDao            storageDao;
	@Autowired
    CodeManager           codeManager;
	
    /**
     * 事务模板
     */
    @Autowired
    protected TransactionTemplate transactionTemplate;
    
    @Autowired
    InDepositoryDao inDepositoryDao;
    
    @Autowired
    InDetailDao inDetailDao;
    
    @Autowired
    ProdRelationInDao prodRelationInDao;
    
    @Autowired
    DepLocationDao depLocationDao;
    
    @Autowired
    DepositoryDao depositoryDao;
    
    @Autowired
    GoodsInstanceManager goodsInstanceManager;
    
    @Autowired
    OutDepositoryDao outDepositoryDao;
    
    @Autowired
    OutDetailDao outDetailDao;
    
    @Autowired
    ProdRelationOutDao prodRelationOutDao;
    
    @Autowired
    OutDepositoryManager outDepositoryManager;
    
	//TODO  ==============================涓存椂娉ㄩ噴鎺??============================
    /*
	@Autowired
    InDepositoryDao       inDepositoryDao;
	@Autowired
    InDetailDao           inDetailDao;
	@Autowired
    DepLocationDao        depLocationDao;
	@Autowired
    GoodsInstanceManager  goodsInstanceManager;
	@Autowired
    ProdRelationInDao     prodRelationInDao;
	@Autowired
    DepositoryDao         depositoryDao;*/
    @Autowired
    DepositoryFirstManager depositoryFirstManager;
	@Autowired
	ProdRelationInGbManager prodRelationInGbManager;
	@Autowired
    InDetailGbManager inDetailGbManager;
	@Autowired
	GoodsDao goodsDao;

    private Map<String, String>   storTypeMap = EnumStorType.toMap();


    /* @model: */
    public Long addStorageCheck(StorageCheck storageCheckDao) {
        log.info("StorageCheckManagerImpl.addStorageCheck method");
        try {
            return this.storageCheckDao.addStorageCheck(storageCheckDao);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    /* @model: */
    public void editStorageCheck(StorageCheck storageCheck) {
        log.info("StorageCheckManagerImpl.editStorageCheck method");
        try {
            this.storageCheckDao.editStorageCheck(storageCheck);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    /* @model: */
    public void removeStorageCheck(Long storageCheckId) {
        log.info("StorageCheckManagerImpl.removeStorageCheck method");
        try {
            this.storageCheckDao.removeStorageCheck(storageCheckId);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    /* @model: */
    public StorageCheck getStorageCheck(Long storageCheckId) {
        log.info("StorageCheckManagerImpl.getStorageCheck method");
        try {
            return this.storageCheckDao.getStorageCheck(storageCheckId);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    /* @model: */
    public List<StorageCheck> getStorageChecks() {
        log.info("StorageCheckManagerImpl.getStorageChecks method");
        try {
            return this.storageCheckDao.getStorageChecks();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    public int getStorageChecksByCountParameterMap(Map parameterMap) {
        return storageCheckDao.getStorageChecksByCountParameterMap(parameterMap);
    }

    @SuppressWarnings("unchecked")
	public QueryPage getStorageChecksByParameterMap(StorageCheckQuery storageCheckQuery, int currentPage, int pageSize) {
    	QueryPage queryPage = new QueryPage(storageCheckQuery);
        Map parMap = queryPage.getParameters();
        if(storageCheckQuery.getDepfirstIds() != null && storageCheckQuery.getDepfirstIds().size() > 0){
        	parMap.put("depfirstIds", storageCheckQuery.getDepfirstIds());
        }
        return storageCheckDao.getStorageChecksByParameterMap(parMap, currentPage, pageSize);
    }

    public QueryPage getStorageCheckListsByParameterMap(StorageCheckQuery storageCheckQuery, int currentPage, int pageSize, boolean isPage) {
        return storageCheckListDao.getStorageCheckListsByParameterMap(storageCheckQuery, currentPage, pageSize, isPage);
    }

    public int getStorageCheckListsByCountParameterMap(Map parameterMap) {
        return storageCheckListDao.getStorageCheckListsByCountParameterMap(parameterMap);
    }

    public List<Storage> getStorageByParameterMap(Map parameterMap) {
        return storageDao.getStorageByParameterMap(parameterMap);
    }

    public void updateStorage(Map parameterMap) {
        String[] storageIds = (String[]) parameterMap.get("storageIds");
        String[] checkNums = (String[]) parameterMap.get("checkNums");
        String checkId = (String) parameterMap.get("checkId");
        boolean isupdate = false;
        long totalcheckNum = 0;
        try {
            if (storageIds != null && checkNums != null && storageIds.length == checkNums.length
                && StringUtil.isNotBlank(checkId)) {
                for (int i = 0; i < storageIds.length; i++) {
                    String storageId = storageIds[i];
                    String checkNum = checkNums[i];
                    if (StringUtil.isNotBlank(storageId) && StringUtil.isNotBlank(checkNum)) {
                        //锟斤拷锟饺革拷锟铰匡拷锟斤拷锟较??
                        Storage store = (Storage) storageDao.getStorage(new Long(storageId));
                        if (store != null) {
                            long sn = store.getExistNum();
                            long cn = (new Long(checkNum)).longValue();
                            totalcheckNum = totalcheckNum + cn;
                            if (sn != cn) {
                                isupdate = true;
                                store.setExistNum(new Long(checkNum));
                                //                                storageDao.editStorage(store);
                                //锟斤拷锟铰匡拷锟斤拷锟较拷螅锟斤拷锟斤拷檀锟斤拷锟较革拷锟??

                                StorageCheckDetail storageCheckDetail = new StorageCheckDetail();
                                storageCheckDetail.setGoodsInstanceId(store.getGoodsInstanceId());
                                storageCheckDetail.setGoodsId(store.getGoodsId());
                                storageCheckDetail.setStorId(store.getId());
                                storageCheckDetail.setSupplierId(store.getSupplierId());
                                storageCheckDetail.setCheckId(new Long(checkId));
                                storageCheckDetail.setStorNumber(sn);
                                storageCheckDetail.setCheckNum(cn);
                                storageCheckDetail.setProfitNum(cn - sn);
                                if (cn - sn > 0) {
                                    storageCheckDetail.setStorType("y");
                                } else {
                                    storageCheckDetail.setStorType("k");
                                }
                                Map detailMap = new HashMap();
                                detailMap.put("checkId", new Long(checkId));
                                detailMap.put("storId", store.getId());
                                detailMap.put("goodsInstanceId", store.getGoodsInstanceId());
                                detailMap.put("supplierId", store.getSupplierId());
                                List<StorageCheckDetail> storeDetail = storageCheckDetailDao.getAllStorageCheckDetailsByParameterMap(detailMap);
                                if (storeDetail == null) {
                                    storageCheckDetailDao.addStorageCheckDetail(storageCheckDetail);
                                } else {
                                    if (storeDetail.size() == 1) {
                                        storageCheckDetail.setId(((StorageCheckDetail) storeDetail
                                            .get(0)).getId());
                                        storageCheckDetailDao
                                            .editStorageCheckDetail(storageCheckDetail);
                                    } else {
                                        throw new ManagerException("data error");
                                    }
                                }
                            }

                        }
                    }
                }
                if (isupdate) {
                    List<StorageCheckList> cl = storageCheckListDao.getAllStorageCheckListsByParameterMap(parameterMap);
                    if (cl != null && cl.size() == 1) {
                        StorageCheckList scList = (StorageCheckList) cl.get(0);
                        scList.setCheckNum(totalcheckNum);
                        storageCheckListDao.editStorageCheckList(scList);
                    }
                }

            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }

    }

    public List<Storage> getStorageAndCheckDetailByParameterMap(Map parameterMap) {
        return storageDao.getStorageAndCheckDetailByParameterMap(parameterMap);
    }


    @SuppressWarnings("unchecked")
    public Map finishZeroStorageCheck(final Map parMap) {
        log.info("StorageCheckManagerImpl.finishZeroStorageCheck method");
        Map returnMap = transactionTemplate.execute(new TransactionCallback<Map>() {
        	public Map doInTransaction(TransactionStatus status) {
        Map errorMap = new HashMap();
        errorMap.put("flag", "true");
        //库存ID
        String[] storageIds = (String[]) parMap.get("storageIds");
        //盘盈数量
        String[] checkNums = (String[]) parMap.get("checkNums");

        try {
            if (checkNums != null && storageIds != null) {
                InDepository inDepository = new InDepository();
                inDepository.setBillNum(codeManager.buildCodeByDateAndType(CodeManager.RUKU_CODE,
                    6, "RK"));
                inDepository.setCreater((String) parMap.get("creater"));
                inDepository.setStatus(EnumInStatus.IN_FINISHED.getKey());
                inDepository.setType(EnumInDepository.IN_ZERO_CHECK_MORE.getKey());
                inDepository.setRelationNum("LKCPY");
                inDepository.setGmtInDep(new Date());
                inDepository.setFinanceStatus(EnumFinanceStatus.NO_SURE.getKey());

                Long inId = inDepositoryDao.addInDepository(inDepository);

                Storage storage = null;
                Long depFirstId = null;
                for (int i = 0; i < checkNums.length; i++) {
                    if (StringUtil.isBlank(checkNums[i])) {
                        continue;
                    }
                    if (StringUtil.isNumeric(storageIds[i]) && StringUtil.isNumeric(checkNums[i])) {
                        storage = storageDao.getStorage(Long.parseLong(storageIds[i]));
                        if (storage == null) {
                            continue;
                        }
                        //商品开票billId
        				Long billId = goodsDao.getBillId(storage.getGoodsId());
                        depFirstId = storage.getDepFirstId();
                        //入库单明细表添加记录
                        InDetail inDetail = new InDetail();
                        inDetail.setAmount(Long.parseLong(checkNums[i]));
                        inDetail.setGoodsId(storage.getGoodsId());
                        inDetail.setGoodsInstanceId(storage.getGoodsInstanceId());
                        inDetail.setInDepositoryId(inId);
                        inDetail.setStorId(storage.getId());
                        inDetail.setStatus(EnumInDetailStatus.IN_FINISHED.getKey());
                        inDetail.setUnitPrice(storage.getPrice());
                        inDetail.setDueFee((storage.getPrice()) * Long.parseLong(checkNums[i]));
                        inDetail.setFactFee(inDetail.getDueFee());

                        if (storage.getDepFirstId() != null) {
                            inDetail.setDepFirstId(storage.getDepFirstId());
                            long leftDepNum = storageDao.sumStorageByGoodsInstanceId(storage
                                .getGoodsInstanceId(), storage.getDepFirstId());
                            inDetail.setLeftDepNum(leftDepNum + Long.parseLong(checkNums[i]));
                        }
                        inDetail.setStorType(storage.getStorType());
                        long leftNum = storageDao.sumStorageByGoodsInstanceId(storage
                            .getGoodsInstanceId(), null);
                        inDetail.setLeftNum(leftNum + Long.parseLong(checkNums[i]));

                        long inDetailId = inDetailDao.addInDetail(inDetail);
                        
                        //开票公司入库单详情表加入 zhangwy
                        if(billId!=null){
                    		InDetailGb inDetailGb = new InDetailGb();
                    		inDetailGb.setRelationId(inDetailId);
                    		inDetailGb.setGoodsInstanceId(storage.getGoodsInstanceId());
                    		inDetailGb.setGoodsId(storage.getGoodsId());
                    		inDetailGb.setInDepositoryId(inId);
                    		inDetailGb.setAmount(Long.parseLong(checkNums[i]));
                    		inDetailGb.setUnitPrice(storage.getPrice());
                    		inDetailGb.setDueFee((storage.getPrice()) * Long.parseLong(checkNums[i]));
                    		inDetailGb.setFactFee(inDetail.getDueFee());
                    		inDetailGb.setStatus(EnumInDetailStatus.IN_FINISHED.getKey());
                    		inDetailGb.setStorId(storage.getId());
                    		inDetailGb.setLeftNum(inDetail.getLeftNum());
                    		inDetailGb.setStorType(inDetail.getStorType());
                    		inDetailGb.setDepFirstId(inDetail.getDepFirstId());
                    		inDetailGb.setLeftDepNum(inDetail.getLeftDepNum());
                    		inDetailGb.setBillId(billId);
                    		inDetailGbManager.addInDetailGb(inDetailGb);
                        }

                        //入库临时表中添加记录
                        ProdRelationIn prodRelationInInfo = new ProdRelationIn();
                        prodRelationInInfo.setInDepId(inId);
                        prodRelationInInfo.setGoodsId(storage.getGoodsId());
                        prodRelationInInfo.setGoodsInstanceId(storage.getGoodsInstanceId());
                        prodRelationInInfo.setInDetailId(inDetailId);
                        prodRelationInInfo.setAmount(Long.parseLong(checkNums[i]));
                        prodRelationInInfo.setLocId(storage.getLocId());
                        prodRelationInInfo.setSupplierId(storage.getSupplierId());
                        prodRelationInInfo.setBatchNum(storage.getBatchNum());
                        long prodInId = prodRelationInDao.addProdRelationIn(prodRelationInInfo);
                        
                        //开票公司入库分配表加入 zhangwy
                        if(billId!=null){
                    		ProdRelationInGb prodRelationInGb = new ProdRelationInGb();
                    		prodRelationInGb.setRelationId(prodInId);
                    		prodRelationInGb.setInDepId(inId);
                    		prodRelationInGb.setGoodsInstanceId(storage.getGoodsInstanceId());
                    		prodRelationInGb.setAmount(Long.parseLong(checkNums[i]));
                    		prodRelationInGb.setGoodsId(storage.getGoodsId());
                    		prodRelationInGb.setInDetailId(inDetailId);
                    		prodRelationInGb.setLocId(storage.getLocId());
                    		prodRelationInGb.setSupplierId(storage.getSupplierId());
                    		prodRelationInGb.setBatchNum(storage.getBatchNum());
                    		prodRelationInGb.setBillId(billId);
                    		prodRelationInGbManager.addProdRelationInGb(prodRelationInGb);
                        }
                        
                        //更新商品实例表和商品表的库存数量,现有规则:1.如果一级仓库为批发虚拟库时，则不加减可用库存;2.仓库类型是次品库的时候不加减可用库存 zhangwy
                        DepositoryFirst depositoryFirst = depositoryFirstManager.getDepositoryById(depFirstId);
                        if(depositoryFirst!=null&&(!depositoryFirst.getType().equals("w"))){
                        	DepLocation depLocation = depLocationDao.getLocationsById(storage.getLocId());
                        	Depository depository = depositoryDao.getDepository(depLocation.getDepId());
                        	if(depository!=null && (!EnumDepositoryType.DEFECT_DEP.getKey().equals(depository.getType()))){
                                goodsInstanceManager.updateAmountForTwo(storage.getGoodsInstanceId(),
                                        storage.getGoodsId(), Long.parseLong(checkNums[i]), storage
                                            .getDepFirstId(),true);	
                        	}
                        }

                        //更新库存数量
                        storageDao.updateStorageStorageNumById(Long.parseLong(checkNums[i]),
                            storage.getId());
                    }
                }
                inDepository.setDepFirstId(depFirstId);
                inDepositoryDao.editInDepository(inDepository);

            } else {
                errorMap.put("flag", "true");
                errorMap.put("errorMsg", "盘盈数量或库存ID为空！");
            }
        } catch (Exception e) {
            errorMap.put("flag", "true");
            errorMap.put("errorMsg", e.getMessage());
            log.error(e.getMessage());
        }
        return errorMap;
        };
       });
       return returnMap;
    }


    public Map finishStoreCheck(final Map parameterMap) {
      Map returnMap = transactionTemplate.execute(new TransactionCallback<Map>() {
        public Map doInTransaction(TransactionStatus status) {
        Map errorMap = new HashMap();
        errorMap.put("flag", "true");
        String checkId = (String) parameterMap.get("checkId");
        try {
            //????????????盘点明细
            //首先处理亏损的记录
            parameterMap.put("storType", "k");
            List<StorageCheckDetail> storeDetail = storageCheckDetailDao.getStorageCheckDetailsByParameterMap(parameterMap);

            //取得一级仓库ID
            Long depFirstId = storageCheckDao.getDepFirstIdByCheckId(Long.parseLong(checkId));

            if (storeDetail != null && storeDetail.size() > 0) {
                //插入主表
                OutDepository outDepository = new OutDepository();
                //zhangwy 修改操作人
                outDepository.setCreater((String) parameterMap.get("creater"));
                outDepository.setBillNum(codeManager.buildCodeByDateAndType(CodeManager.CHUKU_CODE,
                    6, "CK"));
                outDepository.setRelationNum((String) parameterMap.get("checkId"));
                outDepository.setType(EnumOutDepository.OUT_CHECK_DAMAGED.getKey());
                outDepository.setStatus(EnumOutStatus.OUT_NEW.getKey());
                //设置一级仓库ID
                outDepository.setDepFirstId(depFirstId);
                Long outId = outDepositoryDao.addOutDepository(outDepository);
                List<OutDetailGoods> outGoods = new ArrayList();
                for (StorageCheckDetail scd : storeDetail) {

                    OutDetail outDetail = new OutDetail();
                    outDetail.setGoodsId(scd.getGoodsId());
                    outDetail.setGoodsInstanceId(scd.getGoodsInstanceId());
                    outDetail.setOutDepositoryId(outId.intValue());
                    outDetail.setOutNum(scd.getProfitNum());
                    Storage s = storageDao.getStorage(scd.getStorId());
                    if (s != null) {
                        outDetail.setUnitPrice(s.getPrice());
                        outDetail.setDueFee((s.getPrice()) * scd.getProfitNum());
                        outDetail.setFactFee(outDetail.getDueFee());
                        outDetail.setDepFirstId(s.getDepFirstId());
                        outDetail.setStorType(s.getStorType());
                    }
                    Long detailId = outDetailDao.addOutDetail(outDetail);

                    OutDetailGoods outGood = new OutDetailGoods();
                    outGood.setGoodsId(scd.getGoodsId());
                    outGood.setGoodsInstanceId(scd.getGoodsInstanceId());
                    outGood.setId(detailId);
                    outGood.setDepFirstId(outDetail.getDepFirstId());
                    outGoods.add(outGood);

                    ProdRelationOut prodRelationOutInfo = new ProdRelationOut();
                    prodRelationOutInfo.setOutDepId(outId);
                    prodRelationOutInfo.setGoodsInstanceId(scd.getGoodsInstanceId());
                    prodRelationOutInfo.setAmount(scd.getProfitNum());
                    prodRelationOutInfo.setGoodsId(scd.getGoodsId());
                    prodRelationOutInfo.setOutDetailId(detailId);
                    prodRelationOutInfo.setStorageId(scd.getStorId());
                    prodRelationOutInfo.setLocId(scd.getLocId());
                    prodRelationOutInfo.setGmtCreate(new Date());
                    prodRelationOutInfo.setGmtModify(new Date());
                    //添加到商品出库关联表中
                    prodRelationOutDao.addProdRelationOut(prodRelationOutInfo);
                    //更新出库详单状态
                    outDetailDao.updateOutDetailStatusById(detailId,
                        EnumOutDetailStatus.OUT_FINISHED.getKey());

                }

                //出库单直接出库
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date gmtOutDep = df.parse(df.format(new Date()));
                Map outDep = new HashMap();
                outDep.put("outDepId", outId);
                outDep.put("gmtOutDep", gmtOutDep);
                outDep.put("outDepType", EnumOutDepository.OUT_CHECK_DAMAGED.getKey());
                outDep.put("relationNum", checkId);
                outDep.put("outDetailGoodsLists", outGoods);
                outDepositoryManager.removeStorageOpt(outDep);
            }
            //首先处理盈余的记录
            parameterMap.put("storType", "y");
            
            storeDetail = storageCheckDetailDao.getStorageCheckDetailsByParameterMap(parameterMap);

            if (storeDetail != null && storeDetail.size() > 0) {
                InDepository inDepository = new InDepository();
                inDepository.setBillNum(codeManager.buildCodeByDateAndType(CodeManager.RUKU_CODE,
                    6, "RK"));
                //zhangwy 修改操作人
                inDepository.setCreater((String) parameterMap.get("creater"));
                inDepository.setRelationNum((String) parameterMap.get("checkId"));
                inDepository.setStatus(EnumInStatus.IN_NEW.getKey());
                inDepository.setType(EnumInDepository.IN_CHECK_MORE.getKey());
                //设置一级仓库ID
                inDepository.setDepFirstId(depFirstId);
                Long inId = inDepositoryDao.addInDepository(inDepository);
                inDepository.setId(inId.longValue());
                for (StorageCheckDetail scd : storeDetail) {
                    InDetail inDetail = new InDetail();
                    inDetail.setAmount(scd.getProfitNum());
                    inDetail.setGoodsId(scd.getGoodsId());
                    inDetail.setGoodsInstanceId(scd.getGoodsInstanceId());
                    inDetail.setInDepositoryId(inDepository.getId());
                    inDetail.setStorId(scd.getStorId());
                    inDetail.setStatus(EnumInDetailStatus.IN_NEW.getKey());
                    Storage s = storageDao.getStorage(scd.getStorId());
                    if (s != null) {
                        inDetail.setUnitPrice(s.getPrice());
                        inDetail.setDueFee((s.getPrice()) * scd.getProfitNum());
                        inDetail.setFactFee(inDetail.getDueFee());
                        inDetail.setDepFirstId(s.getDepFirstId());
                        inDetail.setStorType(s.getStorType());
                    }
                    inDetailDao.addInDetail(inDetail);
                }

            }
            parameterMap.put("storType", null);

            List<StorageCheckList> checkLists = storageCheckListDao.getStorageCheckListsByParameterMap(parameterMap);
            for (StorageCheckList list : checkLists) {
                if (list.getCheckNum().longValue() != list.getStorNum().longValue()) {
                    parameterMap.put("locId", list.getLocId());
                    parameterMap.put("goodsInstanceId", list.getGoodsInstanceId());
                    int checkNum = storageCheckDetailDao
                        .sumStorageCheckDetailByParameterMap(parameterMap);
                    list.setCheckNum(new Long(checkNum));
                    storageCheckListDao.editStorageCheckList(list);
                }

            }

            //盘点完成??
            StorageCheck sc = (StorageCheck) storageCheckDao.getStorageCheck(new Long(checkId));
            //解锁库位表
            if (sc.getCheckType().equals("d")) {
                List<DepLocation> deps = depLocationDao.getLocationsByDepositoryId(sc.getDepId());
                for (DepLocation dep : deps) {
                    dep.setIsCheck(EnumDepLocationIsCheck.N.getKey());
                    depLocationDao.lockDepLocation(dep);
                }
            } else if (sc.getCheckType().equals("l")) {
                DepLocation dep = new DepLocation();
                dep.setId(sc.getLocId());
                dep.setIsCheck(EnumDepLocationIsCheck.N.getKey());
                depLocationDao.lockDepLocation(dep);
            }
            sc.setStatus("f");
            storageCheckDao.editStorageCheck(sc);
        } catch (Exception e) {
            errorMap.put("flag", "true");
            errorMap.put("errorMsg", e.getMessage());
            log.error(e.getMessage());
        }
        return errorMap;
            };
        });
        return returnMap;
    }

    public int getStorageCheckDetailByCountParameterMap(Map parameterMap) {
        return storageCheckDetailDao.getStorageCheckDetailsCountByParameterMap(parameterMap);
    }

    @SuppressWarnings("unchecked")
	public QueryPage getStorageCheckDetailByParameterMap(StorageCheckQuery storageCheckQuery, int currentPage, int pageSize, boolean isPage) {
    	QueryPage queryPage = new QueryPage(storageCheckQuery);
        Map parMap = queryPage.getParameters();
        queryPage = storageCheckDetailDao.getStorageCheckDetailsByParameterMap(parMap, currentPage, pageSize, isPage);
        List<StorageCheckDetail> storageCheckDetails = (List<StorageCheckDetail>) queryPage.getItems();
        try {
            for (StorageCheckDetail sd : storageCheckDetails) {
                Storage stor = storageDao.getStorage(sd.getStorId());
                if (stor != null) {
                    sd.setBatchNum(stor.getBatchNum());
                    sd.setStorType(stor.getStorType());
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        queryPage.setItems(storageCheckDetails);
        return queryPage;
    }

    public void addStorCheckDetail(long locId, long goodsInsId, long checkId) {
        Map parMap = new HashMap();
        parMap.put("locId", locId);
        parMap.put("goodsInstanceId", goodsInsId);
        try {
            List<Storage> storages = storageDao.getStorageByParameterMap(parMap);
            for (Storage s : storages) {
                if (s.getStorageNum() > 0) {
                    StorageCheckDetail storageCheckDetail = new StorageCheckDetail();
                    storageCheckDetail.setGoodsInstanceId(s.getGoodsInstanceId());
                    storageCheckDetail.setGoodsId(s.getGoodsId());
                    storageCheckDetail.setStorId(s.getId());
                    storageCheckDetail.setSupplierId(s.getSupplierId());
                    storageCheckDetail.setCheckId(new Long(checkId));
                    storageCheckDetail.setStorNumber(s.getStorageNum());
                    //锟斤拷锟斤拷募锟铰硷拷锟矫伙拷薪锟斤拷锟斤拷魏未锟斤拷?锟斤拷志为u
                    storageCheckDetail.setStorType("u");
                    storageCheckDetail.setLocId(s.getLocId());

                    Map detailMap = new HashMap();
                    detailMap.put("checkId", new Long(checkId));
                    detailMap.put("storId", s.getId());
                    detailMap.put("goodsInstanceId", s.getGoodsInstanceId());
                    detailMap.put("supplierId", s.getSupplierId());
                    List<StorageCheckDetail> storeDetail = storageCheckDetailDao.getAllStorageCheckDetailsByParameterMap(detailMap);
                    if (storeDetail != null && storeDetail.size() == 0) {
                        storageCheckDetailDao.addStorageCheckDetail(storageCheckDetail);
                    }
                    if (storeDetail != null && storeDetail.size() == 1) {
                        storageCheckDetail.setId(((StorageCheckDetail) storeDetail.get(0)).getId());
                        storageCheckDetailDao.editStorageCheckDetail(storageCheckDetail);
                    }

                }
            }

        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ManagerException("data error");
        }

    }

    /* (non-Javadoc)
     * @see com.hundsun.bible.facade.ios.StorageCheckManager#getCheckDetailCountByMap(java.util.Map)
     */
    public List<StorageCheckDetail> getCheckDetailCountByMap(Map parameterMap) {
        return storageCheckDetailDao.getCheckDetailCountByMap(parameterMap);
    }

    public List<StockAge> getStockAgeAnalysisDataListsByParameterMap(Map  parMap)
    {
    	return storageCheckDetailDao.getStockAgeAnalysisDataListsByParameterMap(parMap);
    }
}
