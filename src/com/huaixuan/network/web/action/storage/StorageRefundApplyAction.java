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
	 * 库存退货申请单查询 zhangwy
	 * @return
	 */
    @RequestMapping(value = "/list_storage_refund_apply")
	public String storageRefundApplySearch(
			@ModelAttribute("storageRefundApplyQuery") StorageRefundApplyQuery storageRefundApplyQuery,
			@RequestParam(value = "message", required = false) String message,
			@RequestParam(value = "init", required = false) String init,
			@RequestParam(value = "page", required = false, defaultValue = "1") int currPage,
			AdminAgent adminAgent, Model model){

    	//初始化仓库信息
		this.initDepository(storageRefundApplyQuery, adminAgent, model);

		// 默认查询30天的数据
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
	 * 库存退货申请单详细 zhangwy
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
	 * 库存退货申请单审核通过 zhangwy
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
				model.addAttribute("message", "库存退货申请状态不是新建，不能进行审核操作！");
				return "/error";
			}
		}
		shoppingRefId = storageManager.refundApplyStorages(storageRefundApplyList, adminAgent.getUsername());
        if (shoppingRefId.intValue() > 0) {
            message = "库存退货申请单审核成功！";
        }else {
        	model.addAttribute("message", "库存退货申请单审核操作失败！");
            return "/error";
        }
		return "redirect:/storage/detail_storage_refund_apply.html?applyRelationNum=" + relationNum + "&message=" + message;
	}

	/**
	 * 库存退货申请单审核不通过修改 zhangwy
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
				model.addAttribute("message", "库存退货申请状态不是新建，不能进行修改操作！");
				return "/error";
			}
		}
        for(String meo:memo){
        	if(meo!=null&&meo.length()>100){
        		model.addAttribute("message", "库存退货申请中填写的备注信息过长，超出100个字符！");
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
		message = "申请单备注修改成功！";
		return "redirect:/storage/detail_storage_refund_apply.html?applyRelationNum=" + relationNum + "&message=" + message;
	}

	/**
	 * 库存退货申请单重新提交 zhangwy
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
		map.put("id", ids);// 申请表ID
		map.put("refNum", refNum);// 退货数量
		map.put("refPrice", refPrice);// 单位成本
		map.put("factFee", factFee);// 实收金额
        map.put("reason", reason);// 报残原因
        map.put("remark", remark);// 备注
        map.put("user", adminAgent.getUsername());// 重新申请人

        for(int i=0;i<ids.length;i++){
        	StorageRefundApply storageRefundApply = new StorageRefundApply();
        	storageRefundApply = this.storageRefundApplyManager.getStorageRefundApplyById(Long.parseLong(ids[i]));
			if(!EnumStorageRefundApply.FAIL.getKey().equals(storageRefundApply.getStatus())){
				model.addAttribute("message", "库存退货申请不是退回状态，不能进行重新提交操作！");
				return "/error";
			}
        }
        result = this.storageRefundApplyManager.modifyRefundApply(map);
        if(result.equals("realwrong")){
        	model.addAttribute("message", "库存退货数量大于实际库存数量！");
			return "/error";
        }else if(result.equals("norealwrong")){
        	model.addAttribute("message", "库存退货数量大于可用库存数量！");
			return "/error";
        }else if(result.equals("success")){
        	message = "库存退货申请重新提交成功！";
        }
        return "redirect:/storage/detail_storage_refund_apply.html?applyRelationNum=" + applyRelationNum + "&message=" + message;
	}

	/**
	 * 关闭申请单
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
				model.addAttribute("message", "库存退货申请状态为关闭，或此申请已经完成审核，不能关闭！");
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
     * 初始化仓库信息
     *
     * @param storageRefundApplyQuery
     * @param adminAgent
     * @param model
     */
 	private void initDepository(StorageRefundApplyQuery storageRefundApplyQuery,AdminAgent adminAgent, Model model) {
 		//加入全部的一级仓库ID
 		storageRefundApplyQuery.setDepfirstIds(getDepfirstIdForQuery(adminAgent));
     	depositoryFirstList = depositoryFirstManager.getDepositoryFirstListByIds(getDepfirstIdForQuery(adminAgent));
 		if (depositoryFirstList == null || depositoryFirstList.size() == 0) {
 			model.addAttribute("message", "一级仓库记录为空！");
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
