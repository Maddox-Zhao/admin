package com.huaixuan.network.biz.service.storage.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.huaixuan.network.biz.dao.goods.AvailableStockDao;
import com.huaixuan.network.biz.dao.goods.GoodsInstanceDao;
import com.huaixuan.network.biz.dao.storage.DepLocationDao;
import com.huaixuan.network.biz.dao.storage.DepositoryFirstDao;
import com.huaixuan.network.biz.dao.storage.StorageDao;
import com.huaixuan.network.biz.dao.storage.StorageRefundApplyDao;
import com.huaixuan.network.biz.domain.goods.AvailableStock;
import com.huaixuan.network.biz.domain.goods.GoodsInstance;
import com.huaixuan.network.biz.domain.storage.DepLocation;
import com.huaixuan.network.biz.domain.storage.DepositoryFirst;
import com.huaixuan.network.biz.domain.storage.Storage;
import com.huaixuan.network.biz.domain.storage.StorageRefundApply;
import com.huaixuan.network.biz.domain.storage.query.StorageRefundApplyQuery;
import com.huaixuan.network.biz.enums.EnumStorageRefundApply;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.service.common.CodeManager;
import com.huaixuan.network.biz.service.storage.StorageRefundApplyManager;

@Service("storageRefundApplyManager")
public class StorageRefundApplyManagerImpl implements StorageRefundApplyManager {

	Log log = LogFactory.getLog(this.getClass());

	@Autowired
    StorageRefundApplyDao storageRefundApplyDao;
	@Autowired
    DepositoryFirstDao  depositoryFirstDao;
	@Autowired
	DepLocationDao      depLocationDao;
	@Autowired
	CodeManager             codeManager;
	@Autowired
	StorageDao              storageDao;
	@Autowired
	AvailableStockDao       availableStockDao;
	@Autowired
	GoodsInstanceDao        goodsInstanceDao;

	@SuppressWarnings("unchecked")
	public QueryPage getStorageRefundApplyListByCondition(StorageRefundApplyQuery storageRefundApplyQuery, int currentPage, int pageSize) {
		log.info("StorageRefundApplyManagerImpl getStorageRefundApplyListByCondition method");
		QueryPage queryPage = new QueryPage(storageRefundApplyQuery);
        Map parMap = queryPage.getParameters();
        parMap.put("depfirstIds", storageRefundApplyQuery.getDepfirstIds());
		try{
			queryPage = this.storageRefundApplyDao.getStorageRefundApplyListByCondition(parMap, currentPage, pageSize);
			List<StorageRefundApply> tempList = (List<StorageRefundApply>)queryPage.getItems();
			for(StorageRefundApply tmp:tempList){
		        if (tmp.getDepFirstId() != null) {
		            DepositoryFirst depositoryFirst = depositoryFirstDao.getDepositoryById(tmp
		                .getDepFirstId());
		            if (depositoryFirst != null) {
		                tmp.setDepFirstName(depositoryFirst.getDepFirstName());
		            }
		        }
		        if (tmp.getLocId() != null) {
		            DepLocation depLocation = depLocationDao.getLocationsById(tmp.getLocId());
//		            Depository depository = depLocationManager.getDepositoryByLocationId(tmp
//		                .getLocId());
		            if (depLocation != null) {
		                tmp.setDepositoryName(depLocation.getDepName());
		                tmp.setDepLocationName(depLocation.getLocName());
		            }
		        }
			}
			queryPage.setItems(tempList);
	        return queryPage;
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return null;
	  }

	  public String addStorageRefundApply(Map<String, Object> parMap) {
//	      Date nowDate = new Date();
//	      SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	      String cutuiCode = codeManager.buildCodeByDateAndType(CodeManager.KUAPP_CODE, 6, "KA");

	      String user = (String) parMap.get("user");// ������
	      String[] ids = (String[]) parMap.get("id");// ����¼ID
	      String[] refNum = (String[]) parMap.get("refNum");// �˻�����
	      String[] refPrice = (String[]) parMap.get("refPrice");// �ɱ�����
	      String[] factFee = (String[]) parMap.get("factFee");// ʵ�ս��
	      String[] reason = (String[]) parMap.get("reason");// �˻�ԭ��
	      String[] remark = (String[]) parMap.get("remark");// ��ע

	      try{
	          int i = 0;
	          for (String id : ids) {
	             if (StringUtils.isBlank(refNum[i])) {
	                 i++;
	                 continue;
	             }
	             Storage storage = storageDao.getStorage(Long.parseLong(id));
	             if (storage.getStorageNum() < Integer.parseInt(refNum[i])) {
	                 throw new Exception();
	             }
	             //�жϿ��ÿ��
	             AvailableStock availableStockTmp = availableStockDao.getAvailableStock(storage
	                 .getGoodsInstanceId(), storage.getDepFirstId());
	             if (availableStockTmp != null) {
	                 if (availableStockTmp.getGoodsNumber().longValue() < Long.parseLong(refNum[i])) {
	                     GoodsInstance gi = (GoodsInstance) goodsInstanceDao.getInstance(storage
	                         .getGoodsInstanceId());
	                     throw new Exception(gi.getInstanceName() + "���ÿ��С�����������������������");
	                 }
	             }
	             if (Integer.parseInt(refNum[i]) > 0) {
	            	 StorageRefundApply storageRefundApply = new StorageRefundApply();
	            	 storageRefundApply.setRelationNum(cutuiCode);
	            	 storageRefundApply.setStorageId(storage.getId());
	            	 storageRefundApply.setRefundNum(Long.parseLong(refNum[i]));
	            	 storageRefundApply.setRefPrice(Double.parseDouble(refPrice[i]));
	            	 storageRefundApply.setFactFee(Double.parseDouble(factFee[i]));
	            	 storageRefundApply.setReason(reason[i]);
	            	 storageRefundApply.setApplyUserName(user);
	            	 storageRefundApply.setMemo(remark[i]);
	            	 storageRefundApply.setStatus(EnumStorageRefundApply.INIT.getKey());
	            	 storageRefundApplyDao.addStorageRefundApply(storageRefundApply);
	             }
	             i++;
	          }
	      }catch(Exception e){
	    	  log.error(e.getMessage());
	      }
		  return cutuiCode;
	  }

	   public List<StorageRefundApply> getStorageRefundApplyDetail(String relationNum) {
		   log.info("StorageRefundApplyManagerImpl getStorageRefundApplyDetail method");
		   try{
			   return this.storageRefundApplyDao.getStorageRefundApplyDetail(relationNum);
		   }catch(Exception e){
			   log.error(e.getMessage());
		   }
		   return null;
	   }

	   public int getStorageRefundApplyCountByRelationNum(String relationNum) {
		   log.info("StorageRefundApplyManagerImpl getStorageRefundApplyCountByRelationNum method");
		   try{
			   return this.storageRefundApplyDao.getStorageRefundApplyCountByRelationNum(relationNum);
		   }catch(Exception e){
			   log.error(e.getMessage());
		   }
		   return 0;
	   }

	   /**
		 * ���ݿ��ID�����Ƿ����û�д�����ɵĵ���
		 * @author ����
		 * @param storageId
		 * @return
		 */
		public int getStorageRefundApplyCountByStorageId(String storageId){
		   log.info("StorageRefundApplyManagerImpl getStorageRefundApplyCountByStorageId method");
		   try{
			    String[] storageIds = null;
			     if(storageId!=null && storageId.length() >0){
			    	 storageId = storageId.substring(0, storageId.length()-1);
			    	 storageIds = storageId.split(",");
			     }
				 return this.storageRefundApplyDao.getStorageRefundApplyCountByStorageId(storageIds);
			 }catch(Exception e){
				   log.error(e.getMessage());
			 }
		   return 0;
		}

	   /* (non-Javadoc)
	    * @see com.hundsun.bible.facade.ios.StorageRefundApplyManagerImpl#modifyRefundApply(java.util.Map)
	    */
		@SuppressWarnings("unchecked")
		@Transactional
	   public String modifyRefundApply(Map parMap) {
	       String user = (String) parMap.get("user");// ����������
	       String[] ids = (String[]) parMap.get("id");// ����¼ID
	       String[] refNum = (String[]) parMap.get("refNum");// �˻�����
	       String[] refPrice = (String[]) parMap.get("refPrice");// ��λ�ɱ�
	       String[] factFee = (String[]) parMap.get("factFee");// ʵ�ս��
	       String[] reason = (String[]) parMap.get("reason");// ����ԭ��
	       String[] remark = (String[]) parMap.get("remark");// ��ע

	       try{
	    	   for(int i=0;i<ids.length;i++){
	    		   StorageRefundApply storageRefundApply = this.storageRefundApplyDao.getStorageRefundApplyById(Long.parseLong(ids[i]));
	    		   Storage storage = this.storageDao.getStorage(storageRefundApply.getStorageId());
//	    		   GoodsInstance gi = goodsInstanceDao.getInstance(storage.getGoodsInstanceId());
	    		   if(storage!=null){
	       			  if(storage.getStorageNum() < Long.parseLong(refNum[i])){
	    				 return "realwrong";
	    			  }
	    		   }
	               AvailableStock availableStockTmp = availableStockDao.getAvailableStock(storage.getGoodsInstanceId(), storage.getDepFirstId());
	               if (availableStockTmp != null) {
	               	  if (availableStockTmp.getGoodsNumber().longValue() < Long.parseLong(refNum[i])){
	               		return "norealwrong";
	               	  }
	               }

	               storageRefundApply.setRefundNum(Long.parseLong(refNum[i]));
	               storageRefundApply.setRefPrice(Double.parseDouble(refPrice[i]));
	               storageRefundApply.setFactFee(Double.parseDouble(factFee[i]));
	               storageRefundApply.setReason(reason[i]);
	               storageRefundApply.setMemo(remark[i]);
	               storageRefundApply.setApplyUserName(user);
	               storageRefundApply.setStatus(EnumStorageRefundApply.INIT.getKey());

	               this.storageRefundApplyDao.updateStorageRefundApply(storageRefundApply);
	    	   }
	       }catch(Exception e){
	    	   log.error(e.getMessage());
	       }
		   return "success";
	   }

		public StorageRefundApply getStorageRefundApplyById(Long id) {
			   return this.storageRefundApplyDao.getStorageRefundApplyById(id);
		   }

		public void updateStorageRefundApply(StorageRefundApply storageRefundApply) {
			this.storageRefundApplyDao.updateStorageRefundApply(storageRefundApply);
		}
}
