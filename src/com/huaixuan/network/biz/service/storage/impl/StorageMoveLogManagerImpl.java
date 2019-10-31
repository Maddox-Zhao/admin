package com.huaixuan.network.biz.service.storage.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.huaixuan.network.biz.dao.goods.GoodsDao;
import com.huaixuan.network.biz.dao.storage.DepLocationDao;
import com.huaixuan.network.biz.dao.storage.DepositoryDao;
import com.huaixuan.network.biz.dao.storage.DepositoryFirstDao;
import com.huaixuan.network.biz.dao.storage.InDepositoryDao;
import com.huaixuan.network.biz.dao.storage.InDetailDao;
import com.huaixuan.network.biz.dao.storage.OutDepositoryDao;
import com.huaixuan.network.biz.dao.storage.OutDetailDao;
import com.huaixuan.network.biz.dao.storage.ProdRelationInDao;
import com.huaixuan.network.biz.dao.storage.ProdRelationOutDao;
import com.huaixuan.network.biz.dao.storage.StorageDao;
import com.huaixuan.network.biz.dao.storage.StorageMoveLogDao;
import com.huaixuan.network.biz.domain.goods.AvailableStock;
import com.huaixuan.network.biz.domain.storage.DepLocation;
import com.huaixuan.network.biz.domain.storage.Depository;
import com.huaixuan.network.biz.domain.storage.DepositoryFirst;
import com.huaixuan.network.biz.domain.storage.InDepository;
import com.huaixuan.network.biz.domain.storage.InDetail;
import com.huaixuan.network.biz.domain.storage.InDetailGb;
import com.huaixuan.network.biz.domain.storage.OutDepository;
import com.huaixuan.network.biz.domain.storage.OutDetail;
import com.huaixuan.network.biz.domain.storage.OutDetailGb;
import com.huaixuan.network.biz.domain.storage.ProdRelationIn;
import com.huaixuan.network.biz.domain.storage.ProdRelationInGb;
import com.huaixuan.network.biz.domain.storage.ProdRelationOut;
import com.huaixuan.network.biz.domain.storage.ProdRelationOutGb;
import com.huaixuan.network.biz.domain.storage.Storage;
import com.huaixuan.network.biz.domain.storage.StorageMoveLog;
import com.huaixuan.network.biz.domain.storage.query.MoveStorageQuery;
import com.huaixuan.network.biz.enums.EnumDepositoryType;
import com.huaixuan.network.biz.enums.EnumFinanceStatus;
import com.huaixuan.network.biz.enums.EnumInDepository;
import com.huaixuan.network.biz.enums.EnumInDetailStatus;
import com.huaixuan.network.biz.enums.EnumInStatus;
import com.huaixuan.network.biz.enums.EnumOutDepository;
import com.huaixuan.network.biz.enums.EnumOutDetailStatus;
import com.huaixuan.network.biz.enums.EnumOutStatus;
import com.huaixuan.network.biz.enums.EnumStorType;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.service.common.CodeManager;
import com.huaixuan.network.biz.service.goods.GoodsInstanceManager;
import com.huaixuan.network.biz.service.storage.InDetailGbManager;
import com.huaixuan.network.biz.service.storage.OutDetailGbManager;
import com.huaixuan.network.biz.service.storage.ProdRelationInGbManager;
import com.huaixuan.network.biz.service.storage.ProdRelationOutGbManager;
import com.huaixuan.network.biz.service.storage.StorageMoveLogManager;
import com.huaixuan.network.common.util.DoubleUtil;
import com.hundsun.network.melody.common.util.StringUtil;

/**
 * ??????????(bibleUtil auto code generation)
 * @version 3.2.0
 */

@Service("storageMoveLogManager")
public class StorageMoveLogManagerImpl implements StorageMoveLogManager {

	private final Log log = LogFactory.getLog(this.getClass());

	@Autowired
    StorageMoveLogDao storageMoveLogDao;
	@Autowired
    StorageDao        storageDao;
	@Autowired
    DepLocationDao    depLocationDao;
	@Autowired
    DepositoryDao     depositoryDao;
	@Autowired
    GoodsInstanceManager goodsInstanceManager;
	@Autowired
    CodeManager      codeManager;
	@Autowired
    DepositoryFirstDao   depositoryFirstDao;
	@Autowired
    InDepositoryDao       inDepositoryDao;
	@Autowired
    InDetailDao           inDetailDao;
	@Autowired
    ProdRelationInDao     prodRelationInDao;
	@Autowired
    OutDepositoryDao      outDepositoryDao;
	@Autowired
    OutDetailDao     outDetailDao;
	@Autowired
    ProdRelationOutDao   prodRelationOutDao;
	@Autowired
	GoodsDao        goodsDao;
	@Autowired
	ProdRelationInGbManager prodRelationInGbManager;
	@Autowired
    InDetailGbManager inDetailGbManager;
	@Autowired
	OutDetailGbManager outDetailGbManager;
	@Autowired
	ProdRelationOutGbManager prodRelationOutGbManager;


    /* @model: */
    public void addStorageMoveLog(StorageMoveLog storageMoveLogDao) {
        log.info("StorageMoveLogManagerImpl.addStorageMoveLog method");
        try {
            this.storageMoveLogDao.addStorageMoveLog(storageMoveLogDao);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    /* @model: */
    public void editStorageMoveLog(StorageMoveLog storageMoveLog) {
        log.info("StorageMoveLogManagerImpl.editStorageMoveLog method");
        try {
            this.storageMoveLogDao.editStorageMoveLog(storageMoveLog);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    /* @model: */
    public void removeStorageMoveLog(Long storageMoveLogId) {
        log.info("StorageMoveLogManagerImpl.removeStorageMoveLog method");
        try {
            this.storageMoveLogDao.removeStorageMoveLog(storageMoveLogId);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    /* @model: */
    public StorageMoveLog getStorageMoveLog(Long storageMoveLogId) {
        log.info("StorageMoveLogManagerImpl.getStorageMoveLog method");
        try {
            return this.storageMoveLogDao.getStorageMoveLog(storageMoveLogId);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    /* @model: */
    @SuppressWarnings("unchecked")
	public QueryPage getStorageMoveLogsByMap(MoveStorageQuery moveStorageQuery, int currentPage, int pageSize) {
        log.info("StorageMoveLogManagerImpl.getStorageMoveLogs method");
        QueryPage queryPage = new QueryPage(moveStorageQuery);
		Map parMap = queryPage.getParameters();
		parMap.put("depfirstIds", moveStorageQuery.getDepfirstIds());
        if ("0".equals(parMap.get("status"))) {
            parMap.put("moveNum", "true");
        }
        queryPage = storageMoveLogDao.getStorageMoveLogsByMap(parMap, currentPage, pageSize);
    	List<StorageMoveLog> storageMoveLogList = (List<StorageMoveLog>)queryPage.getItems();
    	if(storageMoveLogList != null){
	        for(StorageMoveLog tmp:storageMoveLogList){
	        	if(tmp.getDepFirstId()!=null){
	        		DepositoryFirst depositoryFirst = depositoryFirstDao.getDepositoryById(tmp.getDepFirstId());
	        		if(depositoryFirst!=null){
	        			tmp.setDepFirstName(depositoryFirst.getDepFirstName());
	        		}
	        	}
	        }
    	}
        queryPage.setItems(storageMoveLogList);
        return queryPage;
    }

    public int getStorageMoveLogsCountByMap(Map<String, String> parMap) {
        if ("0".equals(parMap.get("status"))) {
            parMap.put("moveNum", "true");
        }
        return this.storageMoveLogDao.getStorageMoveLogsCountByMap(parMap);
    }

    /* (non-Javadoc)
     * @see com.hundsun.bible.facade.ios.StorageMoveLogManager#getStorageMoveLogsByMoveCode(java.lang.String)
     */
    @SuppressWarnings("unchecked")
	public QueryPage getStorageMoveLogsByMoveCode(Map<String, String> parMap, int currentPage, int pageSize) {
        log.info("StorageMoveLogManagerImpl.getStorageMoveLogsByMoveCode method");
        try {
        	QueryPage queryPage = storageMoveLogDao.getStorageMoveLogsByMoveCode(parMap, currentPage, pageSize);
        	List<StorageMoveLog> storageMoveLogList = (List<StorageMoveLog>)queryPage.getItems();
        	if(storageMoveLogList != null){
	            for(StorageMoveLog tmp:storageMoveLogList){
	            	if(tmp.getDepFirstId()!=null){
	            		DepositoryFirst depositoryFirst = depositoryFirstDao.getDepositoryById(tmp.getDepFirstId());
	            		if(depositoryFirst!=null){
	            			tmp.setDepFirstName(depositoryFirst.getDepFirstName());
	            		}
	            	}
	                Storage oldstorage = storageDao.getStorage(tmp.getOldStorageId());
	                if(oldstorage!=null){
	                	DepositoryFirst depositoryFirst = depositoryFirstDao.getDepositoryById(oldstorage.getDepFirstId());
	                	tmp.setOldDepFirstName(depositoryFirst.getDepFirstName());
	                }

	            }
	            queryPage.setItems(storageMoveLogList);
        	}

            return queryPage;
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    /* (non-Javadoc)
     * @see com.hundsun.bible.facade.ios.StorageMoveLogManager#getStorageMoveLogsCountByMoveCode(java.lang.String)
     */
    public int getStorageMoveLogsCountByMoveCode(Map<String, String> parMap) {
        log.info("StorageMoveLogManagerImpl.getStorageMoveLogsCountByMoveCode method");
        try {
            return this.storageMoveLogDao.getStorageMoveLogsCountByMoveCode(parMap);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return 0;
    }

    /* (non-Javadoc)
     * @see com.hundsun.bible.facade.ios.StorageMoveLogManager#sumMoveNumByMap(java.util.Map)
     */
    public int sumMoveNumByMap(Map<String, String> parMap) {
        log.info("StorageMoveLogManagerImpl.sumMoveNumByMap method");
        try {
            return this.storageMoveLogDao.sumMoveNumByMap(parMap);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return 0;
    }

    /* (non-Javadoc)
     * @see com.hundsun.bible.facade.ios.StorageMoveLogManager#getMoreDetailByMap(java.util.Map, com.hundsun.bible.Page)
     */
    @SuppressWarnings("unchecked")
	public QueryPage getMoreDetailByMap(MoveStorageQuery moveStorageQuery, int currentPage, int pageSize, boolean isPage) {
        log.info("StorageMoveLogManagerImpl.getMoreDetailByMap method");
        try {
        	Map parMap = new QueryPage(moveStorageQuery).getParameters();
        	parMap.put("depfirstIds", moveStorageQuery.getDepfirstIds());
        	QueryPage queryPage = storageMoveLogDao.getMoreDetailByMap(parMap, currentPage, pageSize, isPage);
        	List<StorageMoveLog> movelogList = (List<StorageMoveLog>)queryPage.getItems();
        	for(StorageMoveLog tmp:movelogList){
            	if(tmp.getDepFirstId()!=null){
            		DepositoryFirst depositoryFirst = depositoryFirstDao.getDepositoryById(tmp.getDepFirstId());
            		if(depositoryFirst!=null){
            			tmp.setDepFirstName(depositoryFirst.getDepFirstName());
            		}
            	}
                Storage oldstorage = storageDao.getStorage(tmp.getOldStorageId());
                if(oldstorage!=null){
                	DepositoryFirst depositoryFirst = depositoryFirstDao.getDepositoryById(oldstorage.getDepFirstId());
                	tmp.setOldDepFirstName(depositoryFirst.getDepFirstName());
                }
        	}
        	queryPage.setItems(movelogList);
            return queryPage;
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    @Transactional
    @SuppressWarnings("unchecked")
    public Map backToStorage(List<StorageMoveLog> storageMoveLogList, String user) {
        log.info("StorageMoveLogManagerImpl.backToStorage method");
        Map errorMap = new HashMap();
        errorMap.put("flag", "true");
        try {
            List failList = new ArrayList();
            int i = 0;
            if (storageMoveLogList == null || storageMoveLogList.size() == 0) {
                errorMap.put("flag", "false");
                errorMap.put("message", "empty");
            } else {
                // ����������Ϣ zhangwy
            	Storage storageTemp= storageDao.getStorage(storageMoveLogList.get(0).getOldStorageId());
                InDepository inDepository = new InDepository();
                inDepository.setBillNum(codeManager.buildCodeByDateAndType(CodeManager.RUKU_CODE, 6, "RK"));
                inDepository.setCreater(user);
                inDepository.setStatus(EnumInStatus.IN_FINISHED.getKey());
                inDepository.setRelationNum(storageMoveLogList.get(0).getMoveCode());
                inDepository.setGmtInDep(new Date());
                inDepository.setFinanceStatus(EnumFinanceStatus.NO_SURE.getKey());
                inDepository.setDepFirstId(storageTemp.getDepFirstId());
                inDepository.setType(EnumInDepository.IN_BACK_STORAGE.getKey());//�黹���
                Long inId = inDepositoryDao.addInDepository(inDepository);

                OutDepository outDepository = new OutDepository();
                outDepository.setBillNum(codeManager.buildCodeByDateAndType(CodeManager.CHUKU_CODE, 6, "CK"));
                outDepository.setCreater(user);
                outDepository.setStatus(EnumOutStatus.OUT_FINISHED.getKey());
                outDepository.setRelationNum(storageMoveLogList.get(0).getMoveCode());
                outDepository.setGmtOutDep(new Date());
                outDepository.setFinanceStatus(EnumFinanceStatus.NO_SURE.getKey());
                outDepository.setType(EnumOutDepository.OUT_BACK_STORAGE.getKey()); //�黹����
                outDepository.setDepFirstId(storageMoveLogList.get(0).getDepFirstId());
                Long outId = outDepositoryDao.addOutDepository(outDepository);

                for (StorageMoveLog obj : storageMoveLogList) {

                    Storage oldStorage = this.storageDao.getStorage(obj.getOldStorageId());
                    DepositoryFirst oldDepositoryFirst = this.depositoryFirstDao.getDepositoryById(oldStorage.getDepFirstId());//�ϵ�һ���ֿ�
                    DepositoryFirst newDepositoryFirst = this.depositoryFirstDao.getDepositoryById(obj.getDepFirstId());//�µ�һ���ֿ�
                    DepLocation newDepLocation = this.depLocationDao.getLocationsById(obj
                        .getNewLocId());
                    DepLocation oldDepLocation = this.depLocationDao.getLocationsById(oldStorage
                        .getLocId());
                    //�жϾɵ�һ���ֿ��Ƿ����
                    if(oldDepositoryFirst == null){
                    	Map failMap = new HashMap();
                    	failMap.put("id", obj.getId());
                    	failMap.put("message", "olddepfirst");
                        failList.add(failMap);
                        continue;
                    }
                    //�ж��µ�һ���ֿ��Ƿ����
                    if(newDepositoryFirst == null){
                    	Map failMap = new HashMap();
                    	failMap.put("id", obj.getId());
                    	failMap.put("message", "newdepfirst");
                    	failList.add(failMap);
                    	continue;
                    }
                    // �ж��¾ɿ�λ�Ƿ����жϻ�ɾ��
                    if (oldDepLocation == null || "0".equals(oldDepLocation.getStatus())
                        || "Y".equals(oldDepLocation.getIsCheck())) {
                    	Map failMap = new HashMap();
                    	failMap.put("id", obj.getId());
                    	failMap.put("message", "check");
                        failList.add(failMap);
                        continue;
                    }
                    if (newDepLocation == null || "0".equals(newDepLocation.getStatus())
                        || "Y".equals(newDepLocation.getIsCheck())) {
                    	Map failMap = new HashMap();
                    	failMap.put("id", obj.getId());
                    	failMap.put("message", "check");
                        failList.add(failMap);
                        continue;
                    }
                    // �ж��¾ɲֿ��Ƿ񼤻�
                    if (oldDepLocation != null) {
                        Depository depository = this.depositoryDao.getDepository(oldDepLocation
                            .getDepId());
                        if (depository == null || "i".equals(depository.getStatus())) {
                        	Map failMap = new HashMap();
                        	failMap.put("id", obj.getId());
                        	failMap.put("message", "dep");
                            failList.add(failMap);
                            continue;
                        }
                    }
                    if (newDepLocation != null) {
                        Depository depository = this.depositoryDao.getDepository(newDepLocation
                            .getDepId());
                        if (depository == null || "i".equals(depository.getStatus())) {
                        	Map failMap = new HashMap();
                        	failMap.put("id", obj.getId());
                        	failMap.put("message", "dep");
                            failList.add(failMap);
                            continue;
                        }
                    }
                    //  �ж��Ƿ���й���������
                    if ("0".equals(obj.getStatus())) {
                        // �����¾ɿ�λ�еĿ������
                        Storage newStorage = this.storageDao.getStorage(obj.getNewStorageId());
                        if (newStorage == null || newStorage.getStorageNum() < obj.getThisReturnNum()) {
                        	Map failMap = new HashMap();
                        	failMap.put("id", obj.getId());
                        	failMap.put("message", "number");
                            failList.add(failMap);
                            continue;
                        }
                        this.storageDao.updateStorageStorageNumById(0 - obj.getThisReturnNum(), obj
                            .getNewStorageId());
                        this.storageDao.updateStorageStorageNumById(obj.getThisReturnNum(), obj
                            .getOldStorageId());
                        //����黹�����Ϣ zhangwy
                        storMoveInDepository(obj.getThisReturnNum(),oldStorage,inId, oldStorage.getDepFirstId(),oldStorage.getLocId());
                        //����黹������Ϣ zhangwy
                        storMoveOutDepository(obj.getThisReturnNum(),newStorage, outId);
                        // ����ǽ���״̬���黹��Ʒ����ÿ��Ҳ�����Ӧ������
                        if("3".equals(obj.getType())){
                            goodsInstanceManager.updateAmountForTwo(
                                new Long(oldStorage.getGoodsInstanceId().longValue()), oldStorage.getGoodsId(), new Long(obj.getThisReturnNum()), oldDepositoryFirst.getId(),true);
                        }
                        if(obj.getMoveNum()==(obj.getReturnNum()+obj.getThisReturnNum())){
                            obj.setStatus("1");// ���µ���״̬
                        }
                        obj.setReturnNum(obj.getReturnNum()+obj.getThisReturnNum());
                        obj.setGmtModify(new Date());
                        this.storageMoveLogDao.editStorageMoveLog(obj);
                        i++;
                    }
                }
                List<InDetail> inDetailList = inDetailDao.getInDetailListByInDepositoryId(inId);
                List<OutDetail> outDetailList = outDetailDao.getOutDetailListByOutDepositoryId(outId);
                if(inDetailList == null || inDetailList.size() == 0){
                	inDepositoryDao.removeInDepository(inId);
                }
                if(outDetailList == null || outDetailList.size() == 0){
                	outDepositoryDao.removeOutDepository(outId);
                }
            }
            if (failList != null && failList.size() > 0) {
                errorMap.put("flag", "false");
                errorMap.put("failList", failList);
            }
            errorMap.put("success", i);
        } catch (Exception e) {
            log.error(e.getMessage());
            errorMap.put("flag", "false");
            errorMap.put("message", "db");
        }
        return errorMap;
    }

    @Transactional
    @SuppressWarnings("unchecked")
    public Map moveToOtherLoc(List<Storage> storageList, Long newdepfirstId, Long newDepId, Long newLocId,
    		String[] moveNum, String user, String actionType,String memo) {
        List failList = new ArrayList();
        Map errorMap = new HashMap();
        errorMap.put("flag", "true");
        try {
        	if(memo.length() > 255){
            	errorMap.put("flag", "false");
                errorMap.put("message", "checkMemoLength");
                return errorMap;
        	}

        	DepositoryFirst depositoryFirst = this.depositoryFirstDao.getDepositoryById(newdepfirstId);
            DepLocation depLocation = this.depLocationDao.getLocationsById(newLocId);
            Depository depository = this.depositoryDao.getDepository(newDepId);
            if(moveNum == null || moveNum.length == 0){
            	errorMap.put("flag", "false");
                errorMap.put("message", "empty");
                return errorMap;
            }
            if (storageList == null || storageList.size() == 0) {
                errorMap.put("flag", "false");
                errorMap.put("message", "empty");
                return errorMap;
            }
            // �ж�һ���ֿ��Ƿ���� zhangwy
            if (depositoryFirst == null) {
                errorMap.put("flag", "false");
                errorMap.put("message", "depfirst");
                return errorMap;
            }
            // �жϲֿ��Ƿ��Ǽ���״̬
            if (depository == null || "i".equals(depository.getStatus())) {
                errorMap.put("flag", "false");
                errorMap.put("message", "dep");
                return errorMap;
            }
            // �ж��¿�λ�Ƿ����жϻ�ɾ��
            if (depLocation == null || "0".equals(depLocation.getStatus())
                || "Y".equals(depLocation.getIsCheck())) {
                errorMap.put("flag", "false");
                errorMap.put("message", "check");
                return errorMap;
            }
            // �����������ж��Ƿ���赽��ͨ�ֿ���ߴ�Ʒ�ֿ� zhangwy
            if("3".equals(actionType) && (EnumDepositoryType.COMMON_DEP.getKey().equals(depository.getType())||EnumDepositoryType.DEFECT_DEP.getKey().equals(depository.getType()))){
                errorMap.put("flag", "false");
                errorMap.put("message", "type1");
                return errorMap;
            }
            // ������ƿ����ж��Ƿ��ƶ������ֿ�
            if("1".equals(actionType) && EnumDepositoryType.OUT_BORROW_DEP.getKey().equals(depository.getType())){
                errorMap.put("flag", "false");
                errorMap.put("message", "type3");
                return errorMap;
            }

            Long num = new Long(0);
            DepLocation oldDepLocation = null;
            int i = 0;
            // ���ɱ��
            String moveCode = "";
            if("3".equals(actionType)){
                moveCode = codeManager.buildCodeByDateAndType(CodeManager.WAIJIE_CODE, 6, "WJ");
            }else if("1".equals(actionType)){
                moveCode = codeManager.buildCodeByDateAndType(CodeManager.YIKU_CODE, 6, "YK");
            }
            errorMap.put("moveCode", moveCode);
            // ����������Ϣ zhangwy
            InDepository inDepository = new InDepository();
            inDepository.setBillNum(codeManager.buildCodeByDateAndType(CodeManager.RUKU_CODE, 6, "RK"));
            inDepository.setCreater(user);
            inDepository.setStatus(EnumInStatus.IN_FINISHED.getKey());
            inDepository.setRelationNum(moveCode);
            inDepository.setGmtInDep(new Date());
            inDepository.setFinanceStatus(EnumFinanceStatus.NO_SURE.getKey());
            inDepository.setDepFirstId(depositoryFirst.getId());//Ŀ��һ���ֿ�

            OutDepository outDepository = new OutDepository();
            outDepository.setBillNum(codeManager.buildCodeByDateAndType(CodeManager.CHUKU_CODE, 6, "CK"));
            outDepository.setCreater(user);
            outDepository.setStatus(EnumOutStatus.OUT_FINISHED.getKey());
            outDepository.setRelationNum(moveCode);
            outDepository.setGmtOutDep(new Date());
            outDepository.setFinanceStatus(EnumFinanceStatus.NO_SURE.getKey());
            outDepository.setDepFirstId(storageList.get(0).getDepFirstId());

            if("3".equals(actionType)){//�������
            	inDepository.setType(EnumInDepository.IN_BORROW_STORAGE.getKey());
            	outDepository.setType(EnumOutDepository.OUT_BORROW_STORAGE.getKey());
            }else if("1".equals(actionType)){ //�ƿ�����
            	inDepository.setType(EnumInDepository.IN_MOVE_STORAGE.getKey());
            	outDepository.setType(EnumOutDepository.OUT_MOVE_STORAGE.getKey());
            }
            Long inId = inDepositoryDao.addInDepository(inDepository);
            Long outId = outDepositoryDao.addOutDepository(outDepository);

            int j = 0;
            for (Storage obj : storageList) {
            	if (moveNum[j] != null) {
            		if(moveNum[j].length() <= 0){
            			j++;
            			continue;
            		}
            		if(StringUtil.isNumeric(moveNum[j])) {
                        num = Long.parseLong(moveNum[j]);
                        j++;
                    } else {
                        errorMap.put("flag", "false");
                        errorMap.put("message", "number");
                        j++;
                        return errorMap;
                    }
                } else{
                	j++;
                	continue;
                }
                // �����Ѿ����Ĳ�Ʒ�����������ƿ�
                if("3".equals(actionType) || "1".equals(actionType)){
                    Map parMap = new HashMap();
                    parMap.put("type", "3");
                    parMap.put("status", "0");
                    parMap.put("newStorageId", obj.getId());
                    int count = this.storageMoveLogDao.getBorrowedMoveLogsCountByMap(parMap);
                    if(count > 0){
                        Map failMap = new HashMap();
                        failMap.put("id", obj.getId());
                        failMap.put("message", "ishas");
                        failList.add(failMap);
                        continue;
                    }
                }

                oldDepLocation = this.depLocationDao.getLocationsById(obj.getLocId());
                // �жϾɿ�λ�Ƿ����жϻ�ɾ��
                if (oldDepLocation == null || "0".equals(oldDepLocation.getStatus())
                    || "Y".equals(oldDepLocation.getIsCheck())) {
                	Map failMap = new HashMap();
                	failMap.put("id", obj.getId());
                	failMap.put("message", "check");
                    failList.add(failMap);
                    continue;
                }
                // �жϿ�λ�Ƿ���Դ�ֿ�Ŀ�λ
                if (oldDepLocation != null
                    && oldDepLocation.getDepId().intValue() == newDepId.intValue()
                    && obj.getLocId().intValue() == newLocId.intValue()) {
                	Map failMap = new HashMap();
                	failMap.put("id", obj.getId());
                	failMap.put("message", "same");
                    failList.add(failMap);
                    continue;
                }
                Storage storage = this.storageDao.getStorage(obj.getId());
                // �ж��ƶ������Ƿ�С�ڿ������
                if (storage != null && storage.getStorageNum() >= num) {
                    // ������һ���¼�¼
                    Storage newStorage = new Storage();
                    newStorage.setGoodsId(obj.getGoodsId());
                    newStorage.setGoodsInstanceId(obj.getGoodsInstanceId());
                    newStorage.setPrice(obj.getPrice());
                    newStorage.setSupplierId(obj.getSupplierId());
                    newStorage.setBatchNum(obj.getBatchNum());
                    newStorage.setLocId(newLocId);
                    newStorage.setStorageNum(num);
                    newStorage.setDepFirstId(newdepfirstId);
                    newStorage.setStorType(obj.getStorType());
                    if(depositoryFirst.getType().equals("w")){
                    	newStorage.setIsWholesale("w");
                    } else {
                        //��������������⣬������Ϊ��ͨ�� modified by chenyan
                        newStorage.setIsWholesale("n");
                    }

                    // �����ĸ������޸Ŀ�������
                    Map parameterMap = new HashMap();
                    parameterMap.put("goodsInstanceId", String.valueOf(obj.getGoodsInstanceId()));
                    parameterMap.put("supplierId", String.valueOf(obj.getSupplierId()));
                    parameterMap.put("locId", String.valueOf(newLocId));
                    parameterMap.put("batchNum", obj.getBatchNum());
                    parameterMap.put("storageNum", String.valueOf(num));
                    Storage storage2 = this.storageDao.getStorageByMap(parameterMap);

                  	Depository oldDepository = depositoryDao.getDepository(oldDepLocation.getDepId());//Դ�ֿ�
                  	DepositoryFirst oldDepositoryFirst = depositoryFirstDao.getDepositoryById(obj.getDepFirstId());//Դһ���ֿ�

                    if("3".equals(actionType)){
                      if(!oldDepositoryFirst.getType().equals("w")){
                    	if(!EnumDepositoryType.DEFECT_DEP.getKey().equals(oldDepository.getType())){
                    	     AvailableStock availableStock = goodsInstanceManager.getAvailableStock(new Long(obj.getGoodsInstanceId().longValue()), obj.getDepFirstId());
                              // �ж�һ���ֿ���Ʒ�еĿ��ÿ���Ƿ�Ϊ�㣬���Ϊ��������������
                             if(availableStock!=null&&availableStock.getGoodsNumber() - num >= 0){
                        	        this.updateAndInsert(newdepfirstId, newDepId, newLocId, user, actionType, num, moveCode, obj, newStorage, parameterMap, storage2,memo);
                                     // ����ǽ���״̬�������Ʒ����ÿ��Ҳ������Ӧ����(�Ӳ���һ���ֿ�)
                                    goodsInstanceManager.updateAmountForTwo(new Long(obj.getGoodsInstanceId().longValue()), obj.getGoodsId(), new Long(0 - num), obj.getDepFirstId(),true);
                                    //���뵽�����̨���� zhangwy 2010/12/16
                                    storMoveInDepository(num,storage,inId, newdepfirstId,newLocId);
                                    storMoveOutDepository(num,storage,outId);
                             }else{
                                    Map failMap = new HashMap();
                                    failMap.put("id", obj.getId());
                                    failMap.put("message", "goodsnumber");
                                    failList.add(failMap);
                                    continue;
                             }
                    	}else{
                            Map failMap = new HashMap();
                            failMap.put("id", obj.getId());
                            failMap.put("message", "typewrong");
                            failList.add(failMap);
                            continue;
                    	}
                      }else{
                          Map failMap = new HashMap();
                          failMap.put("id", obj.getId());
                          failMap.put("message", "depfirstwrong");
                          failList.add(failMap);
                          continue;
                      }
                    }else if("1".equals(actionType)){//�ƿ⵽��Ʒ�ֿ�Ĺ��� zhangwy
                       AvailableStock availableStock = goodsInstanceManager.getAvailableStock(new Long(obj.getGoodsInstanceId().longValue()), obj.getDepFirstId());
                       if(oldDepositoryFirst.getType().equals("n")&&depositoryFirst.getType().equals("w")){//Դһ���ֿ�����ͨ��,Ŀ��һ���ֿ������������
                    	     // �ж�һ���ֿ���Ʒ�еĿ��ÿ���Ƿ�Ϊ�㣬���Ϊ������������ƿ�
                             if(availableStock!=null&&availableStock.getGoodsNumber() - num >= 0){
          		                  this.updateAndInsert(newdepfirstId, newDepId, newLocId, user, actionType, num, moveCode, obj, newStorage, parameterMap, storage2,memo);
                                  if(!EnumDepositoryType.DEFECT_DEP.getKey().equals(oldDepository.getType())){
                                	  goodsInstanceManager.updateAmountForTwo(new Long(obj.getGoodsInstanceId().longValue()), obj.getGoodsId(), new Long(0 - num), obj.getDepFirstId(),true);
                                  }
                                  //���뵽�����̨���� zhangwy 2010/12/16
                                  storMoveInDepository(num,storage,inId, newdepfirstId,newLocId);
                                  storMoveOutDepository(num,storage,outId);
                             }else{
                  	              Map failMap = new HashMap();
                  	              failMap.put("id", obj.getId());
                  	              failMap.put("message", "movenumber");
                  	              failList.add(failMap);
                  	              continue;
                             }
                       }else if(oldDepositoryFirst.getType().equals("w")&&depositoryFirst.getType().equals("n")){//Դһ���ֿ������������,Ŀ��һ���ֿ�����ͨ��
                             this.updateAndInsert(newdepfirstId, newDepId, newLocId, user, actionType, num, moveCode, obj, newStorage, parameterMap, storage2,memo);
                             if(!EnumDepositoryType.DEFECT_DEP.getKey().equals(depository.getType())){
                            	 goodsInstanceManager.updateAmountForTwo(new Long(obj.getGoodsInstanceId().longValue()), obj.getGoodsId(), num, newdepfirstId,true);
                             }
                             //���뵽�����̨���� zhangwy 2010/12/16
                             storMoveInDepository(num,storage,inId, newdepfirstId,newLocId);
                             storMoveOutDepository(num,storage,outId);
                       }else if(oldDepositoryFirst.getType().equals("w")&&depositoryFirst.getType().equals("w")){//Դһ���ֿ�,Ŀ��һ���ֿⶼ�����������
                             this.updateAndInsert(newdepfirstId, newDepId, newLocId, user, actionType, num, moveCode, obj, newStorage, parameterMap, storage2,memo);
                             //���뵽�����̨���� zhangwy 2010/12/16
                             storMoveInDepository(num,storage,inId, newdepfirstId,newLocId);
                             storMoveOutDepository(num,storage,outId);
                       }else{//Դһ���ֿ��Ŀ��һ���ֿⶼ����ͨһ���ֿ�
                    	         //�ƿ�ʱ�����Ŀ���Ǵ�Ʒ�⣬���ٿ��ÿ��
                   	         if(EnumDepositoryType.DEFECT_DEP.getKey().equals(depository.getType())&& EnumDepositoryType.COMMON_DEP.getKey().equals(oldDepository.getType())){
                                       // �ж�һ���ֿ���Ʒ�еĿ��ÿ���Ƿ�Ϊ�㣬���Ϊ������������ƿ�
                                    if(availableStock!=null&&availableStock.getGoodsNumber() - num >= 0){
                   		                     this.updateAndInsert(newdepfirstId, newDepId, newLocId, user, actionType, num, moveCode, obj, newStorage, parameterMap, storage2,memo);
                                             goodsInstanceManager.updateAmountForTwo(new Long(obj.getGoodsInstanceId().longValue()), obj.getGoodsId(), new Long(0 - num), obj.getDepFirstId(),true);
                                             //���뵽�����̨���� zhangwy 2010/12/16
                                             storMoveInDepository(num,storage,inId, newdepfirstId,newLocId);
                                             storMoveOutDepository(num,storage,outId);
                                    }else{
                           	                 Map failMap = new HashMap();
                           	                 failMap.put("id", obj.getId());
                           	                 failMap.put("message", "movenumber");
                           	                 failList.add(failMap);
                           	                 continue;
                                   }
                             }
                   	           //�ƿ�ʱ�����Դ���Ǵ�Ʒ�⣬��Ҫ���ӿ��ÿ��
                   	        else if(EnumDepositoryType.DEFECT_DEP.getKey().equals(oldDepository.getType())&& EnumDepositoryType.COMMON_DEP.getKey().equals(depository.getType())){
                                   this.updateAndInsert(newdepfirstId, newDepId, newLocId, user, actionType, num, moveCode, obj, newStorage, parameterMap, storage2,memo);
                                   goodsInstanceManager.updateAmountForTwo(new Long(obj.getGoodsInstanceId().longValue()), obj.getGoodsId(), num, newdepfirstId,true);
                                   //���뵽�����̨���� zhangwy 2010/12/16
                                   storMoveInDepository(num,storage,inId, newdepfirstId,newLocId);
                                   storMoveOutDepository(num,storage,outId);
                   	        }else{
                   		       //�ƿ�ı���ÿ�� zhangwy ��һ��һ������
                   		           if(availableStock!=null&&availableStock.getGoodsNumber() - num >= 0){
                   			            //1.ԭ���Ŀ��ÿ����������
                   			            goodsInstanceManager.updateAmountForTwo(new Long(obj.getGoodsInstanceId().longValue()), obj.getGoodsId(), new Long(0 - num), obj.getDepFirstId(),true);
                   			            //2.�µĿ��ÿ�����Ӳ���
                   			            goodsInstanceManager.updateAmountForTwo(new Long(obj.getGoodsInstanceId().longValue()), obj.getGoodsId(), num, newdepfirstId,true);
                           	            this.updateAndInsert(newdepfirstId, newDepId, newLocId, user, actionType, num, moveCode, obj,newStorage, parameterMap, storage2,memo);
                                        //���뵽�����̨���� zhangwy 2010/12/16
                                        storMoveInDepository(num,storage,inId, newdepfirstId,newLocId);
                                        storMoveOutDepository(num,storage,outId);
                   		           }else{
                     	                Map failMap = new HashMap();
                   	                    failMap.put("id", obj.getId());
                   	                    failMap.put("message", "movenumber");
                   	                    failList.add(failMap);
                   	                    continue;
                   		           }
                   	       }
                         }
                    }else{
                    	this.updateAndInsert(newdepfirstId, newDepId, newLocId, user, actionType, num, moveCode, obj,
								newStorage, parameterMap, storage2,memo);
                        //���뵽�����̨���� zhangwy 2010/12/16
                        storMoveInDepository(num,storage,inId, newdepfirstId,newLocId);
                        storMoveOutDepository(num,storage,outId);
                    }
                    i++;
                } else {
                	Map failMap = new HashMap();
                	failMap.put("id", obj.getId());
                	failMap.put("message", "number");
                    failList.add(failMap);
                    continue;
                }
            }
            if (failList != null && failList.size() > 0) {
                errorMap.put("flag", "false");
                errorMap.put("failList", failList);
            }
            errorMap.put("success", i);

            List<InDetail> inDetailList = inDetailDao.getInDetailListByInDepositoryId(inId);
            List<OutDetail> outDetailList = outDetailDao.getOutDetailListByOutDepositoryId(outId);
            if(inDetailList == null || inDetailList.size() == 0){
            	inDepositoryDao.removeInDepository(inId);
            }
            if(outDetailList == null || outDetailList.size() == 0){
            	outDepositoryDao.removeOutDepository(outId);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            errorMap.put("flag", "false");
            errorMap.put("message", "db");
        }
        return errorMap;
    }

	@SuppressWarnings("unchecked")
	private void updateAndInsert(Long newdepfirstId, Long newDepId, Long newLocId, String user, String actionType, Long num,
			String moveCode, Storage obj, Storage newStorage, Map parameterMap, Storage storage2,String memo) throws Exception {
		StorageMoveLog storageMoveLog;
		long newId;
		if (storage2 != null) {
		    // ������ڼ�¼���������
		    this.storageDao.updateStorageStoNumByMap(parameterMap);
		    newId = storage2.getId();
		}else{
		    //�������ݵ�����
		    newId = storageDao.addStorage(newStorage);
		}

		// �����ƿ������־��
		storageMoveLog = new StorageMoveLog();
		storageMoveLog.setMoveCode(moveCode);
		storageMoveLog.setCreater(user);
		storageMoveLog.setOldStorageId(obj.getId());
		storageMoveLog.setNewStorageId(new Long(newId));
		storageMoveLog.setNewDepId(newDepId);
		storageMoveLog.setNewLocId(newLocId);
		storageMoveLog.setMoveNum(num);
		storageMoveLog.setType(actionType);
		storageMoveLog.setDepFirstId(newdepfirstId);
		storageMoveLog.setMemo(memo);
		if ("1".equals(actionType)) {
		    storageMoveLog.setStatus("3");
		} else {
		    storageMoveLog.setStatus("0");
		}

		this.storageMoveLogDao.addStorageMoveLog(storageMoveLog);
		// ���¿������
		this.storageDao.updateStorageStorageNumById(0 - num, obj.getId());
	}

	/**
	 * �ƿ����黹�����ϸ����
	 * @author zhangwy
	 * @param num ���仯����
	 * @param storage �����Ϣ
	 * @param inId ��ⵥid
	 * @param newdepfirstId �µ�һ���ֿ�id
	 * @param newLocId �µĲֿ�id
	 */
	private void storMoveInDepository(Long num, Storage storage, Long inId, Long newdepfirstId, Long newLocId){
        try{
            //��Ʒ��ƱbillId
			Long billId = goodsDao.getBillId(storage.getGoodsId());
    		//��ⵥ��ϸ����Ӽ�¼
            InDetail inDetail = new InDetail();
            inDetail.setAmount(num);
            inDetail.setGoodsId(storage.getGoodsId());
            inDetail.setGoodsInstanceId(storage.getGoodsInstanceId());
            inDetail.setInDepositoryId(inId);
            inDetail.setStorId(storage.getId());
            inDetail.setStatus(EnumInDetailStatus.IN_FINISHED.getKey());
            inDetail.setUnitPrice(storage.getPrice());
            inDetail.setDueFee(DoubleUtil.round(storage.getPrice() * num, 4));
            inDetail.setFactFee(DoubleUtil.round(storage.getPrice() * num, 4));
            inDetail.setDepFirstId(newdepfirstId);
            inDetail.setStorType(storage.getStorType());
            long leftDepNum = storageDao.sumStorageByGoodsInstanceId(storage.getGoodsInstanceId(), newdepfirstId);
            inDetail.setLeftDepNum(leftDepNum);
            long leftNum = this.storageDao.sumStorageByGoodsInstanceId(storage.getGoodsInstanceId(), null);
            inDetail.setLeftNum(leftNum);
            long inDetailId = inDetailDao.addInDetail(inDetail);
            
            //��Ʊ��˾��ⵥ�������� zhangwy
            if(billId!=null){
        		InDetailGb inDetailGb = new InDetailGb();
        		inDetailGb.setRelationId(inDetailId);
        		inDetailGb.setGoodsInstanceId(storage.getGoodsInstanceId());
        		inDetailGb.setGoodsId(storage.getGoodsId());
        		inDetailGb.setInDepositoryId(inId);
        		inDetailGb.setAmount(num);
        		inDetailGb.setUnitPrice(storage.getPrice());
        		inDetailGb.setDueFee(DoubleUtil.round(storage.getPrice() * num, 4));
        		inDetailGb.setFactFee(DoubleUtil.round(storage.getPrice() * num, 4));
        		inDetailGb.setStatus(EnumInDetailStatus.IN_FINISHED.getKey());
        		inDetailGb.setStorId(storage.getId());
        		inDetailGb.setLeftNum(inDetail.getLeftNum());
        		inDetailGb.setStorType(storage.getStorType());
        		inDetailGb.setDepFirstId(newdepfirstId);
        		inDetailGb.setLeftDepNum(inDetail.getLeftDepNum());
        		inDetailGb.setBillId(billId);
        		inDetailGbManager.addInDetailGb(inDetailGb);
            }

            //�����ʱ������Ӽ�¼
            ProdRelationIn prodRelationInInfo = new ProdRelationIn();
            prodRelationInInfo.setInDepId(inId);
            prodRelationInInfo.setGoodsId(storage.getGoodsId());
            prodRelationInInfo.setGoodsInstanceId(storage.getGoodsInstanceId());
            prodRelationInInfo.setInDetailId(inDetailId);
            prodRelationInInfo.setAmount(num);
            prodRelationInInfo.setLocId(newLocId);
            prodRelationInInfo.setSupplierId(storage.getSupplierId());
            prodRelationInInfo.setBatchNum(storage.getBatchNum());
            long prodInId = prodRelationInDao.addProdRelationIn(prodRelationInInfo);
            
            //��Ʊ��˾���������� zhangwy
            if(billId!=null){
        		ProdRelationInGb prodRelationInGb = new ProdRelationInGb();
        		prodRelationInGb.setRelationId(prodInId);
        		prodRelationInGb.setInDepId(inId);
        		prodRelationInGb.setGoodsInstanceId(storage.getGoodsInstanceId());
        		prodRelationInGb.setAmount(num);
        		prodRelationInGb.setGoodsId(storage.getGoodsId());
        		prodRelationInGb.setInDetailId(inDetailId);
        		prodRelationInGb.setLocId(newLocId);
        		prodRelationInGb.setSupplierId(storage.getSupplierId());
        		prodRelationInGb.setBatchNum(storage.getBatchNum());
        		prodRelationInGb.setBillId(billId);
        		prodRelationInGbManager.addProdRelationInGb(prodRelationInGb);
            }
        }catch(Exception e){
        	log.error(e.getMessage());
        }
	}

	/**
	 * �ƿ����黹������ϸ����
	 * @author zhangwy
	 * @param num ���仯����
	 * @param storage �ƶ��Ŀ����Ϣ
	 * @param outId ��ⵥid
	 */
	private void storMoveOutDepository(Long num, Storage storage, Long outId){
        try{
            //��Ʒ��ƱbillId
			Long billId = goodsDao.getBillId(storage.getGoodsId());
    		// ���ɳ��ⵥ
            OutDetail outDetail = new OutDetail();
            outDetail.setGoodsInstanceId(storage.getGoodsInstanceId());
            outDetail.setGoodsId(storage.getGoodsId());
            outDetail.setOutDepositoryId(outId);
            outDetail.setOutNum(num);
            outDetail.setUnitPrice(storage.getPrice());
            outDetail.setDueFee(DoubleUtil.round(storage.getPrice() * num, 4));
            outDetail.setFactFee(DoubleUtil.round(storage.getPrice() * num, 4));
            outDetail.setStatus(EnumOutDetailStatus.OUT_FINISHED.getKey());
            outDetail.setDepFirstId(storage.getDepFirstId());
            if (EnumStorType.STOR_TYPE_V.getKey().equals(storage.getStorType())) {
                outDetail.setOutVirtualNum(num);
            }
            outDetail.setStorType(storage.getStorType());
            long leftDepNum = storageDao.sumStorageByGoodsInstanceId(storage.getGoodsInstanceId(), storage.getDepFirstId());
            outDetail.setLeftDepNum(leftDepNum);
            long leftNum = storageDao.sumStorageByGoodsInstanceId(storage.getGoodsInstanceId(), null);
            outDetail.setLeftNum(leftNum);
            Long detailId = outDetailDao.addOutDetail(outDetail);
            
            if(billId !=null){
            	OutDetailGb outDetailGb = new OutDetailGb();
            	outDetailGb.setRelationId(detailId);
            	outDetailGb.setGoodsInstanceId(storage.getGoodsInstanceId());
            	outDetailGb.setGoodsId(storage.getGoodsId());
            	outDetailGb.setOutDepositoryId(outId);
            	outDetailGb.setOutNum(num);
            	outDetailGb.setUnitPrice(storage.getPrice());
            	outDetailGb.setDueFee(DoubleUtil.round(storage.getPrice() * num, 4));
            	outDetailGb.setFactFee(DoubleUtil.round(storage.getPrice() * num, 4));
            	outDetailGb.setStatus(EnumOutDetailStatus.OUT_FINISHED.getKey());
            	outDetailGb.setLeftNum(outDetail.getLeftNum());
            	outDetailGb.setRelationNum(outDetail.getRelationNum());
            	outDetailGb.setDepFirstId(storage.getDepFirstId());
            	outDetailGb.setStorType(storage.getStorType());
            	outDetailGb.setOutVirtualNum(outDetail.getOutVirtualNum());
            	outDetailGb.setLeftDepNum(outDetail.getLeftDepNum());
            	outDetailGb.setBillId(billId);
            	outDetailGbManager.addOutDetailGb(outDetailGb);
            }

            // ��ӵ���Ʒ�����������
            ProdRelationOut prodRelationOutInfo = new ProdRelationOut();
            prodRelationOutInfo.setOutDepId(outId);
            prodRelationOutInfo.setGoodsInstanceId(storage.getGoodsInstanceId());
            prodRelationOutInfo.setAmount(num);
            prodRelationOutInfo.setGoodsId(storage.getGoodsId());
            prodRelationOutInfo.setOutDetailId(detailId);
            prodRelationOutInfo.setStorageId(storage.getId());
            prodRelationOutInfo.setLocId(storage.getLocId());
            prodRelationOutInfo.setSelfCost(storage.getPrice());
            long prodOutId = prodRelationOutDao.addProdRelationOut(prodRelationOutInfo);
            
            if(billId !=null){
            	ProdRelationOutGb prodRelationOutGb = new ProdRelationOutGb();
            	prodRelationOutGb.setRelationId(prodOutId);
            	prodRelationOutGb.setGoodsInstanceId(storage.getGoodsInstanceId());
            	prodRelationOutGb.setGoodsId(storage.getGoodsId());
            	prodRelationOutGb.setOutDepId(outId);
            	prodRelationOutGb.setAmount(num);
            	prodRelationOutGb.setOutDetailId(detailId);
            	prodRelationOutGb.setStorageId(storage.getId());
            	prodRelationOutGb.setLocId(storage.getLocId());
            	prodRelationOutGb.setSelfCost(storage.getPrice());
            	prodRelationOutGbManager.addProdRelationOutGb(prodRelationOutGb);
            }
        }catch(Exception e){
        	log.error(e.getMessage());
        }
	}

	public Integer getMoveOrdersByMapCount(Map<String, String> parMap)
	{
	    log.info("StorageMoveLogManagerImpl.getMoveOrdersCountByMap method");
	    try {
	    return (Integer)this.storageMoveLogDao.getMoveOrdersCountByMap(parMap);
	    }catch(Exception e)
	    {
	        log.error(e.getMessage());
	    }
	    return 0;
	}
	@SuppressWarnings("unchecked")
	public QueryPage getMoveOrdersDetailByMap(MoveStorageQuery moveStorageQuery, int currentPage, int pageSize)
	{
	    log.info("StorageMoveLogManagerImpl.getMoveOrdersDetailByMa method");
	    QueryPage queryPage = new QueryPage(moveStorageQuery);
		Map parMap = queryPage.getParameters();
		parMap.put("depfirstIds", moveStorageQuery.getDepfirstIds());
	    try{
	    	queryPage = storageMoveLogDao.getMoveOrdersDetailByMap(parMap, currentPage, pageSize);
	    	List<StorageMoveLog> storageMoveLogList = (List<StorageMoveLog>)queryPage.getItems();
            for(StorageMoveLog tmp:storageMoveLogList){
            	if(tmp.getDepFirstId()!=null){
            		DepositoryFirst depositoryFirst = depositoryFirstDao.getDepositoryById(tmp.getDepFirstId());
            		if(depositoryFirst!=null){
            			tmp.setDepFirstName(depositoryFirst.getDepFirstName());
            		}
            	}
            }
            queryPage.setItems(storageMoveLogList);
	        return queryPage;
	    }catch(Exception e)
	    {
	        log.error(e.getMessage());
	    }
	    return null;
	}

	@SuppressWarnings("unchecked")
	public StorageMoveLog getSumNumByMap(MoveStorageQuery moveStorageQuery) {
		log.info("StorageMoveLogManagerImpl.getSumNumByMap method");
		QueryPage queryPage = new QueryPage(moveStorageQuery);
		Map parMap = queryPage.getParameters();
		parMap.put("depfirstIds", moveStorageQuery.getDepfirstIds());
        try {
            return this.storageMoveLogDao.getSumNumByMap(parMap);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
	}

}
