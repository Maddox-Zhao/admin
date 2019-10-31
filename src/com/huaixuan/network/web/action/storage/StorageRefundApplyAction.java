package com.huaixuan.network.web.action.storage;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.huaixuan.network.biz.domain.admin.AdminAgent;
import com.huaixuan.network.biz.domain.goods.GoodsInstance;
import com.huaixuan.network.biz.domain.storage.Depository;
import com.huaixuan.network.biz.domain.storage.DepositoryFirst;
import com.huaixuan.network.biz.domain.storage.Storage;
import com.huaixuan.network.biz.domain.storage.StorageRefundApply;
import com.huaixuan.network.biz.domain.storage.query.StorageRefundApplyQuery;
import com.huaixuan.network.biz.domain.supplier.Supplier;
import com.huaixuan.network.biz.enums.EnumShoppingRefundReason;
import com.huaixuan.network.biz.enums.EnumStorType;
import com.huaixuan.network.biz.enums.EnumStorageRefundApply;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.service.goods.AttributeManager;
import com.huaixuan.network.biz.service.goods.CategoryManager;
import com.huaixuan.network.biz.service.goods.GoodsInstanceManager;
import com.huaixuan.network.biz.service.storage.DepLocationManager;
import com.huaixuan.network.biz.service.storage.DepositoryFirstManager;
import com.huaixuan.network.biz.service.storage.DepositoryService;
import com.huaixuan.network.biz.service.storage.StorageManager;
import com.huaixuan.network.biz.service.storage.StorageRefundApplyManager;
import com.huaixuan.network.biz.service.supplier.SupplierService;
import com.huaixuan.network.common.util.DateUtil;
import com.huaixuan.network.web.action.BaseAction;

@Controller
@RequestMapping(value = "/storage" )
public class StorageRefundApplyAction extends BaseAction {

	@Autowired
    StorageRefundApplyManager storageRefundApplyManager;
	@Autowired
	DepLocationManager       depLocationManager;
	@Autowired
	DepositoryFirstManager   depositoryFirstManager;
	@Autowired
	DepositoryService        depositoryService;
	@Autowired
    StorageManager           storageManager;
	@Autowired
    GoodsInstanceManager     goodsInstanceManager;
	@Autowired
    CategoryManager          categoryManager;
	@Autowired
    AttributeManager         attributeManager;
	@Autowired
	SupplierService          supplierService;

    private List<DepositoryFirst>    depositoryFirstList;
    private List<StorageRefundApply> storageRefundApplyList;
    private Map<String, String>      storageRefundApplyStatusMap    = EnumStorageRefundApply.toMap();
    private Map<String, String>      storTypeMap              = EnumStorType.toMap();
    private Map<String, String>      enumShoppingRefundReason = EnumShoppingRefundReason.toMap();
    private Long                     shoppingRefId;
    private String                   message;
    private String                   result;

	/**
	 * ����˻����뵥��ѯ zhangwy
	 * @return
	 */
    @RequestMapping(value = "/list_storage_refund_apply")
	public String storageRefundApplySearch(
			@ModelAttribute("storageRefundApplyQuery") StorageRefundApplyQuery storageRefundApplyQuery,
			@RequestParam(value = "message", required = false) String message,
			@RequestParam(value = "init", required = false) String init,
			@RequestParam(value = "page", required = false, defaultValue = "1") int currPage,
			AdminAgent adminAgent, Model model){

    	//��ʼ���ֿ���Ϣ
		this.initDepository(storageRefundApplyQuery, adminAgent, model);

		// Ĭ�ϲ�ѯ30�������
        if ("true".equals(init)) {
        	storageRefundApplyQuery.setApplyTimeStart(DateUtil.getDiffDate(new Date(), -30));
        	storageRefundApplyQuery.setApplyTimeEnd(DateUtil.getDateToString(new Date()));
		}

        QueryPage queryPage = storageRefundApplyManager.getStorageRefundApplyListByCondition(storageRefundApplyQuery, currPage, pageSize);
        model.addAttribute("storageRefundApplyStatusMap", storageRefundApplyStatusMap);
        model.addAttribute("queryObject", storageRefundApplyQuery);
        model.addAttribute("query", queryPage);
        model.addAttribute("message", message);
        return "/storage/list_storage_refund_apply";
	}

	/**
	 * ����˻����뵥��ϸ zhangwy
	 * @return
	 */
    @RequestMapping(value = "/detail_storage_refund_apply")
	public String storageRefundApplyDetail(
			@RequestParam(value = "applyRelationNum", required = false, defaultValue = "") String relationNum,
			@RequestParam(value = "flag", required = false, defaultValue = "") String flag,
			Model model){
		storageRefundApplyList = storageRefundApplyManager.getStorageRefundApplyDetail(relationNum);
		if(storageRefundApplyList!=null && storageRefundApplyList.size() > 0){
			StorageRefundApply storageRefundApply = storageRefundApplyList.get(0);
			Storage storage = this.storageManager.getStorage(storageRefundApply.getStorageId());
			Depository dp = depLocationManager.getDepositoryByLocationId(storageRefundApply.getLocId());
			if(dp!=null){
				storageRefundApply.setDepositoryName(dp.getName());
			}
            if(storage!=null){
            	Supplier supplier = this.supplierService.selectSupplierById(storage.getSupplierId());
                if(supplier!=null){
                	storageRefundApply.setSupplierName(supplier.getName());
                }
            	GoodsInstance goodsInstance = this.goodsInstanceManager.getInstance(storage.getGoodsInstanceId());
            	if(goodsInstance!=null){
            		storageRefundApply.setCatCode(goodsInstance.getCatCode());
            		storageRefundApply.setAttrs(goodsInstance.getAttrs());
            		storageRefundApply.setGoodsUnit(goodsInstance.getGoodsUnit());
            	}
            }
            model.addAttribute("storageRefundApply", storageRefundApply);
		}
		model.addAttribute("relationNum", relationNum);
		model.addAttribute("storageRefundApplyList", storageRefundApplyList);
		model.addAttribute("enumShoppingRefundReason", enumShoppingRefundReason);
		model.addAttribute("categoryManager", categoryManager);
		model.addAttribute("attributeManager", attributeManager);
		model.addAttribute("storTypeMap", storTypeMap);
		if("check".equals(flag)){
			return "/storage/list_storage_refund_apply_check";
		}else if("repeat".equals(flag)){
			return "/storage/repeat_storage_refund_apply";
		}else if("edit".equals(flag)){
			return "/storage/edit_storage_refund_apply";
		}else{
			return "/storage/detail_storage_refund_apply";
		}
	}

	/**
	 * ����˻����뵥���ͨ�� zhangwy
	 * @return
	 */
    @RequestMapping(value = "/refundApplySuccess")
	public String refundApplySuccess(
			@RequestParam(value = "applyRelationNum", required = false, defaultValue = "") String relationNum,
			AdminAgent adminAgent,Model model){
		storageRefundApplyList = storageRefundApplyManager.getStorageRefundApplyDetail(relationNum);
		if(storageRefundApplyList!=null && storageRefundApplyList.size() > 0){
			StorageRefundApply storageRefundApply = storageRefundApplyList.get(0);
			if(!EnumStorageRefundApply.INIT.getKey().equals(storageRefundApply.getStatus())){
				model.addAttribute("message", "����˻�����״̬�����½������ܽ�����˲�����");
				return "/error";
			}
		}
		shoppingRefId = storageManager.refundApplyStorages(storageRefundApplyList, adminAgent.getUsername());
        if (shoppingRefId.intValue() > 0) {
            message = "����˻����뵥��˳ɹ���";
        }else {
        	model.addAttribute("message", "����˻����뵥��˲���ʧ�ܣ�");
            return "/error";
        }
		return "redirect:/storage/detail_storage_refund_apply.html?applyRelationNum=" + relationNum + "&message=" + message;
	}

	/**
	 * ����˻����뵥��˲�ͨ���޸� zhangwy
	 * @return
	 */
    @RequestMapping(value = "/refundApplyFailModify")
	public String refundApplyFailModify(
			@RequestParam(value = "applyRelationNum", required = false, defaultValue = "") String relationNum,
			@RequestParam(value = "id", required = false, defaultValue = "") String[] ids,
			@RequestParam(value = "remark", required = false, defaultValue = "") String[] memo,
			AdminAgent adminAgent,Model model){
		storageRefundApplyList = storageRefundApplyManager.getStorageRefundApplyDetail(relationNum);
		if(storageRefundApplyList!=null && storageRefundApplyList.size() > 0){
			StorageRefundApply storageRefundApply = storageRefundApplyList.get(0);
			if(!EnumStorageRefundApply.INIT.getKey().equals(storageRefundApply.getStatus())){
				model.addAttribute("message", "����˻�����״̬�����½������ܽ����޸Ĳ�����");
				return "/error";
			}
		}
        for(String meo:memo){
        	if(meo!=null&&meo.length()>100){
        		model.addAttribute("message", "����˻���������д�ı�ע��Ϣ����������100���ַ���");
				return "/error";
        	}
        }
		for(int i = 0;i<ids.length;i++){
			StorageRefundApply storageRefundApply = this.storageRefundApplyManager.getStorageRefundApplyById(Long.parseLong(ids[i]));
			storageRefundApply.setDisposeUserName(adminAgent.getUsername());
			storageRefundApply.setStatus(EnumStorageRefundApply.FAIL.getKey());
			storageRefundApply.setMemo(memo[i]);
			this.storageRefundApplyManager.updateStorageRefundApply(storageRefundApply);
		}
		message = "���뵥��ע�޸ĳɹ���";
		return "redirect:/storage/detail_storage_refund_apply.html?applyRelationNum=" + relationNum + "&message=" + message;
	}

	/**
	 * ����˻����뵥�����ύ zhangwy
	 * @return
	 */
	@RequestMapping(value = "/repeat_storage_refund_apply")
	public String refundApplyRepeat(
			@RequestParam(value = "applyRelationNum", required = false, defaultValue = "") String applyRelationNum,
			@RequestParam(value = "id", required = false, defaultValue = "") String[] ids,
			@RequestParam(value = "refNum", required = false, defaultValue = "") String[] refNum,
			@RequestParam(value = "refPrice", required = false, defaultValue = "") String[] refPrice,
			@RequestParam(value = "factFee", required = false, defaultValue = "") String[] factFee,
			@RequestParam(value = "reason", required = false, defaultValue = "") String[] reason,
			@RequestParam(value = "remark", required = false, defaultValue = "") String[] remark,
			AdminAgent adminAgent,Model model){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", ids);// �����ID
		map.put("refNum", refNum);// �˻�����
		map.put("refPrice", refPrice);// ��λ�ɱ�
		map.put("factFee", factFee);// ʵ�ս��
        map.put("reason", reason);// ����ԭ��
        map.put("remark", remark);// ��ע
        map.put("user", adminAgent.getUsername());// ����������

        for(int i=0;i<ids.length;i++){
        	StorageRefundApply storageRefundApply = new StorageRefundApply();
        	storageRefundApply = this.storageRefundApplyManager.getStorageRefundApplyById(Long.parseLong(ids[i]));
			if(!EnumStorageRefundApply.FAIL.getKey().equals(storageRefundApply.getStatus())){
				model.addAttribute("message", "����˻����벻���˻�״̬�����ܽ��������ύ������");
				return "/error";
			}
        }
        result = this.storageRefundApplyManager.modifyRefundApply(map);
        if(result.equals("realwrong")){
        	model.addAttribute("message", "����˻���������ʵ�ʿ��������");
			return "/error";
        }else if(result.equals("norealwrong")){
        	model.addAttribute("message", "����˻��������ڿ��ÿ��������");
			return "/error";
        }else if(result.equals("success")){
        	message = "����˻����������ύ�ɹ���";
        }
        return "redirect:/storage/detail_storage_refund_apply.html?applyRelationNum=" + applyRelationNum + "&message=" + message;
	}

	/**
	 * �ر����뵥
	 * @return
	 */
	@RequestMapping(value = "/refundApplyClose")
	public String refundApplyClose(
			@RequestParam(value = "applyRelationNum", required = false, defaultValue = "") String relationNum,
			AdminAgent adminAgent,Model model){
		storageRefundApplyList = storageRefundApplyManager.getStorageRefundApplyDetail(relationNum);
		if(storageRefundApplyList!=null&&storageRefundApplyList.size()>0){
		    StorageRefundApply storageRefundApply = storageRefundApplyList.get(0);
			if(EnumStorageRefundApply.CLOSE.getKey().equals(storageRefundApply.getStatus())||
			   EnumStorageRefundApply.SUCCESS.getKey().equals(storageRefundApply.getStatus())){
				model.addAttribute("message", "����˻�����״̬Ϊ�رգ���������Ѿ������ˣ����ܹرգ�");
				return "/error";
			}
			for(StorageRefundApply temp:storageRefundApplyList){
				temp.setStatus(EnumStorageRefundApply.CLOSE.getKey());
				temp.setDisposeUserName(adminAgent.getUsername());
				this.storageRefundApplyManager.updateStorageRefundApply(temp);
			}
		}
		model.addAttribute("message", "closeSuccess");
		return "redirect:/storage/list_storage_refund_apply.html";
	}

	//=====================================================================================================================================
    /*
     * ��ʼ���ֿ���Ϣ
     *
     * @param storageRefundApplyQuery
     * @param adminAgent
     * @param model
     */
 	private void initDepository(StorageRefundApplyQuery storageRefundApplyQuery,AdminAgent adminAgent, Model model) {
 		//����ȫ����һ���ֿ�ID
 		storageRefundApplyQuery.setDepfirstIds(getDepfirstIdForQuery(adminAgent));
     	depositoryFirstList = depositoryFirstManager.getDepositoryFirstListByIds(getDepfirstIdForQuery(adminAgent));
 		if (depositoryFirstList == null || depositoryFirstList.size() == 0) {
 			model.addAttribute("message", "һ���ֿ��¼Ϊ�գ�");
 		}else{
 			model.addAttribute("depositoryFirstList", depositoryFirstList);
 			if (StringUtils.isNotBlank(storageRefundApplyQuery.getDepfirstId())
 					&& StringUtils.isNumeric(storageRefundApplyQuery.getDepfirstId())) {
 				model.addAttribute("depositoryList", depositoryService.getDeplistByFirstDepId(Long.valueOf(storageRefundApplyQuery.getDepfirstId())));
 			}
 			if (StringUtils.isNotBlank(storageRefundApplyQuery.getDepId())
 					&& StringUtils.isNumeric(storageRefundApplyQuery.getDepId())) {
 				model.addAttribute("depLocationLists", depLocationManager.getLocationsByDepositoryId(Long.valueOf(storageRefundApplyQuery.getDepId())));
 			}
 		}
 	}
}
