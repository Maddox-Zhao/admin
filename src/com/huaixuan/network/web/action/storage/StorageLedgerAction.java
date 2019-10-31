package com.huaixuan.network.web.action.storage;

import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.huaixuan.network.biz.dao.storage.DepositoryFirstDao;
import com.huaixuan.network.biz.domain.admin.AdminAgent;
import com.huaixuan.network.biz.domain.storage.DepositoryFirst;
import com.huaixuan.network.biz.domain.storage.InDepository;
import com.huaixuan.network.biz.domain.storage.InOutDepository;
import com.huaixuan.network.biz.domain.storage.OutDepository;
import com.huaixuan.network.biz.domain.storage.query.StorageLedgerQuery;
import com.huaixuan.network.biz.domain.trade.TradePackage;
import com.huaixuan.network.biz.enums.EnumAdminPermission;
import com.huaixuan.network.biz.enums.EnumInDepository;
import com.huaixuan.network.biz.enums.EnumInOutDepository;
import com.huaixuan.network.biz.enums.EnumInOutThreeDepository;
import com.huaixuan.network.biz.enums.EnumInOutTwoDepository;
import com.huaixuan.network.biz.enums.EnumOutDepository;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.service.goods.AttributeManager;
import com.huaixuan.network.biz.service.goods.GoodsBatchManager;
import com.huaixuan.network.biz.service.storage.InDepositoryManager;
import com.huaixuan.network.biz.service.storage.OutDepositoryManager;
import com.huaixuan.network.common.util.DateUtil;
import com.huaixuan.network.common.util.Page;
import com.huaixuan.network.common.util.PageUtils;
import com.huaixuan.network.web.action.BaseAction;
import com.huaixuan.network.web.action.interceptor.AdminAccess;
import com.hundsun.network.melody.common.util.StringUtil;

@Controller
@RequestMapping(value = "/storage" )
public class StorageLedgerAction extends BaseAction {

    
    @Autowired
    private DepositoryFirstDao depositoryFirstDao;
    
    @Autowired
    private OutDepositoryManager outDepositoryManager;
    
    @Autowired
    private InDepositoryManager inDepositoryManager;
    
    @Autowired
    private AttributeManager attributeManager;
    
    @Autowired
    private GoodsBatchManager goodsBatch;
    
    protected Log  log = LogFactory.getLog(this.getClass());

	/**
	 * 库存台帐查询
	 *
	 * @return
	 *
	 */
    @AdminAccess({EnumAdminPermission.A_STORAGE_LEDGER_USER})
    @RequestMapping(value = "/list_storage_ledger")
	public String searchStockLedger(
			@ModelAttribute("inOutDepository") InOutDepository inOutDepository,
			@RequestParam(value = "page", required = false, defaultValue = "1") int currPage,
			@RequestParam(value = "initFlag", required = false, defaultValue = "false") String initFlag,
			AdminAgent adminAgent,Model model,HttpServletRequest request) {
    	if(StringUtils.isBlank(inOutDepository.getOptTimeStart())
        		&& StringUtils.isBlank(inOutDepository.getOptTimeEnd())){
    		inOutDepository.setOptTimeStart(DateUtil.getDiffDate(new Date(), -30));
    		inOutDepository.setOptTimeEnd(DateUtil.getDateToString(new Date()));
        }

    	inOutDepository.setTypea("hidden");
		String[] ins = null;
		String[] outs = null;
		String instr = EnumInOutTwoDepository.IN_TYPE.getValue();
		String outstr = EnumInOutTwoDepository.OUT_TYPE.getValue();
		if (StringUtils.isNotBlank(inOutDepository.getType1())) {
			if (inOutDepository.getType1().equals(instr)) {
				inOutDepository.setTypea("hiddenIn");
				ins = EnumInOutTwoDepository.IN_TYPE.getKey().split(",");
				outs = new String[] { "null" };
			} else if (inOutDepository.getType1().equals(outstr)) {
				inOutDepository.setTypea("hiddenOut");
				outs = EnumInOutTwoDepository.OUT_TYPE.getKey().split(",");
				ins = new String[] { "null" };
			}
		}
		
		model.addAttribute("instr", instr);
		model.addAttribute("outstr", outstr);
		
		Map parMap = new HashMap();

		parMap.put("ins", ins);
		parMap.put("outs", outs);
		parMap.put("type2", inOutDepository.getType2());
		parMap.put("type3", inOutDepository.getType3());
		parMap.put("optTimeStart", inOutDepository.getOptTimeStart());
		parMap.put("optTimeEnd", inOutDepository.getOptTimeEnd());
		parMap.put("code", inOutDepository.getCode());
		parMap.put("instanceName", inOutDepository.getInstanceName());
		parMap.put("depfirstId", inOutDepository.getDepfirstId());

		List<DepositoryFirst> depositoryFirstList = depositoryFirstDao.getDepositoryFirstListByIds(getDepfirstIdForQuery(adminAgent));
        if (depositoryFirstList == null||depositoryFirstList.size() == 0) {
            return "/error";
        }else{
        	parMap.put("depfirstIds", getDepfirstIdForQuery(adminAgent));
        }
        
        model.addAttribute("depositoryFirstList", depositoryFirstList);
        
        //added by chenyan 2011/03/23 start 若为初始化进来，则不进行检索直接跳转到页面
        if (StringUtil.isNotBlank(initFlag) && initFlag.equals("true")) {
            return "/storage/list_storage_ledger";
        }
        //added by chenyan 2011/03/23 end
        
		List<InDepository> inDepositoryList = new ArrayList<InDepository>();
		List<OutDepository> outDepositoryList = new ArrayList<OutDepository>();
		List<InOutDepository> inOutDepositoryList = new ArrayList<InOutDepository>();
        
		inDepositoryList = inDepositoryManager.getInDepositorysWithDetail(parMap);
		outDepositoryList = outDepositoryManager.getOutDepositorysWithDetail(parMap);
		
		for (InDepository temp1 : inDepositoryList) {
			InOutDepository temp3 = new InOutDepository();
			temp3.setCode(temp1.getGoodCode());
			temp3.setName(temp1.getInstanceName());
			temp3.setType(temp1.getType());
			temp3.setAmount(temp1.getAmount());
			temp3.setGmtDate(temp1.getGmtInDep());
			temp3.setAttrs(temp1.getAttrs());
			temp3.setBillNum(temp1.getBillNum());
			temp3.setInid(temp1.getId());
			temp3.setLeftNum(temp1.getLeftNum());
			temp3.setRelationNum(temp1.getRelationNum());
			temp3.setDepFirstName(temp1.getDepFirstName());
			temp3.setLeftDepNum(temp1.getLeftDepNum());
			temp3.setGmtModify(temp1.getGmtModify());
			inOutDepositoryList.add(temp3);
		}
		for (OutDepository temp2 : outDepositoryList) {
			InOutDepository temp3 = new InOutDepository();
			temp3.setCode(temp2.getGoodCode());
			temp3.setName(temp2.getInstanceName());
			temp3.setType(temp2.getType());
			temp3.setAmount(temp2.getAmount());
			temp3.setGmtDate(temp2.getGmtOutDep());
			temp3.setAttrs(temp2.getAttrs());
			temp3.setBillNum(temp2.getBillNum());
			temp3.setOutid(temp2.getId());
			temp3.setLeftNum(temp2.getLeftNum());
			temp3.setRelationNum(temp2.getRelationNum());
			temp3.setTradePackageList(temp2.getTradePackageList());
			temp3.setDepFirstName(temp2.getDepFirstName());
			temp3.setLeftDepNum(temp2.getLeftDepNum());
			temp3.setGmtModify(temp2.getGmtModify());
			inOutDepositoryList.add(temp3);
		}
		Collections.sort(inOutDepositoryList, Collections.reverseOrder());

		List<InOutDepository> inOutDepositoryListTwo = new ArrayList<InOutDepository>();
		
		PageUtils pageUtils = new PageUtils();
		Page page = new Page();
		page.setCurrentPage(currPage);
		page.setPageSize(this.pageSize);
		page.setTotalRowsAmount(inOutDepositoryList.size());
		inOutDepositoryListTwo = pageUtils.doPage(inOutDepositoryList, page);
		
		QueryPage query = new QueryPage(inOutDepository);
		query.setCurrentPage(page.getCurrentPage());
		query.setPageSize(page.getPageSize());
		query.setItems(inOutDepositoryListTwo);
		query.setTotalItem(page.getTotalRowsAmount());
		
		if(query != null){
			model.addAttribute("query", query);
		}
		
		Map<String, String> inoutDepositoryTypeMap = EnumInOutDepository.toMap();
		Map<String, String> inDepositoryTypeMap = EnumInDepository.toMap();
		Map<String, String> outDepositoryTypeMap = EnumOutDepository.toMap();
		Map<String, String> inoutTwoDepositoryTypeMap = EnumInOutTwoDepository.toMap();
		Map<String,String> inoutThreeDepositoryTypeMap = EnumInOutThreeDepository.toMap();
		model.addAttribute("inDepositoryTypeMap", inDepositoryTypeMap);
		model.addAttribute("outDepositoryTypeMap", outDepositoryTypeMap);
		model.addAttribute("inoutTwoDepositoryTypeMap", inoutTwoDepositoryTypeMap);
		model.addAttribute("inoutDepositoryTypeMap", inoutDepositoryTypeMap);
		model.addAttribute("inoutThreeDepositoryTypeMap", inoutThreeDepositoryTypeMap);
		model.addAttribute("attributeManager", attributeManager);
		return "/storage/list_storage_ledger";
	}


	/**
	 * 库存台帐导出
	 *
	 * @return
	 *
	 */
    @RequestMapping(value = "/doExportStockLedger")
	public String exportStockLedger(@ModelAttribute("inOutDepository") InOutDepository inOutDepository,
			AdminAgent adminAgent,Model model,HttpServletRequest request, HttpServletResponse res) throws Exception {
		OutputStream outwt = null;
		try {
			//检索待导出的数据
	    	if(StringUtils.isBlank(inOutDepository.getOptTimeStart())
	        		&& StringUtils.isBlank(inOutDepository.getOptTimeEnd())){
	    		inOutDepository.setOptTimeStart(DateUtil.getDiffDate(new Date(), -30));
	    		inOutDepository.setOptTimeEnd(DateUtil.getDateToString(new Date()));
	        }

	    	inOutDepository.setTypea("hidden");
			String[] ins = null;
			String[] outs = null;
			String instr = EnumInOutTwoDepository.IN_TYPE.getValue();
			String outstr = EnumInOutTwoDepository.OUT_TYPE.getValue();
			if (StringUtils.isNotBlank(inOutDepository.getType1())) {
				if (inOutDepository.getType1().equals(instr)) {
					inOutDepository.setTypea("hiddenIn");
					ins = EnumInOutTwoDepository.IN_TYPE.getKey().split(",");
					outs = new String[] { "null" };
				} else if (inOutDepository.getType1().equals(outstr)) {
					inOutDepository.setTypea("hiddenOut");
					outs = EnumInOutTwoDepository.OUT_TYPE.getKey().split(",");
					ins = new String[] { "null" };
				}
			}
			
			model.addAttribute("instr", instr);
			model.addAttribute("outstr", outstr);
			
			Map parMap = new HashMap();

			parMap.put("ins", ins);
			parMap.put("outs", outs);
			parMap.put("type2", inOutDepository.getType2());
			parMap.put("type3", inOutDepository.getType3());
			parMap.put("optTimeStart", inOutDepository.getOptTimeStart());
			parMap.put("optTimeEnd", inOutDepository.getOptTimeEnd());
			parMap.put("code", inOutDepository.getCode());
			parMap.put("instanceName", inOutDepository.getInstanceName());
			parMap.put("depfirstId", inOutDepository.getDepfirstId());

			List<DepositoryFirst> depositoryFirstList = depositoryFirstDao.getDepositoryFirstListByIds(getDepfirstIdForQuery(adminAgent));
	        if (depositoryFirstList == null||depositoryFirstList.size() == 0) {
	            return "/error";
	        }else{
	        	parMap.put("depfirstIds", getDepfirstIdForQuery(adminAgent));
	        }
	        
	        model.addAttribute("depositoryFirstList", depositoryFirstList);
	        
			List<InDepository> inDepositoryList = new ArrayList<InDepository>();
			List<OutDepository> outDepositoryList = new ArrayList<OutDepository>();
			List<InOutDepository> inOutDepositoryList = new ArrayList<InOutDepository>();

			inDepositoryList = inDepositoryManager.getInDepositorysWithDetail(parMap);
			outDepositoryList = outDepositoryManager.getOutDepositorysWithDetail(parMap);
			
			for (InDepository temp1 : inDepositoryList) {
				InOutDepository temp3 = new InOutDepository();
				temp3.setCode(temp1.getGoodCode());
				temp3.setName(temp1.getInstanceName());
				temp3.setType(temp1.getType());
				temp3.setAmount(temp1.getAmount());
				temp3.setGmtDate(temp1.getGmtInDep());
				temp3.setAttrs(temp1.getAttrs());
				temp3.setBillNum(temp1.getBillNum());
				temp3.setInid(temp1.getId());
				temp3.setLeftNum(temp1.getLeftNum());
				temp3.setRelationNum(temp1.getRelationNum());
				temp3.setDepFirstName(temp1.getDepFirstName());
				temp3.setLeftDepNum(temp1.getLeftDepNum());
				inOutDepositoryList.add(temp3);
			}
			for (OutDepository temp2 : outDepositoryList) {
				InOutDepository temp3 = new InOutDepository();
				temp3.setCode(temp2.getGoodCode());
				temp3.setName(temp2.getInstanceName());
				temp3.setType(temp2.getType());
				temp3.setAmount(temp2.getAmount());
				temp3.setGmtDate(temp2.getGmtOutDep());
				temp3.setAttrs(temp2.getAttrs());
				temp3.setBillNum(temp2.getBillNum());
				temp3.setOutid(temp2.getId());
				temp3.setLeftNum(temp2.getLeftNum());
				if(EnumOutDepository.OUT_SALES.getKey().equals(temp2.getType())){
					List<TradePackage> list = temp2.getTradePackageList();
					String relationNum = "";
					if(list != null){
						for(TradePackage obj:list){
							relationNum += obj.getTid() + ",";
						}
					}
					temp3.setRelationNum(relationNum);
				}else{
					temp3.setRelationNum(temp2.getRelationNum());
				}	
				temp3.setDepFirstName(temp2.getDepFirstName());
				temp3.setLeftDepNum(temp2.getLeftDepNum());
				inOutDepositoryList.add(temp3);
			}
//			Collections.sort(inOutDepositoryList, Collections.reverseOrder());
			Date da = new Date();
			outwt = res.getOutputStream();
			//取得导出excel的时间，用于文件名中
			SimpleDateFormat df = new SimpleDateFormat("yyyy_MM_dd");
			String date = df.format(da);
			res.setHeader("Content-disposition", "attachment; filename=InAndOutDepository"
					+ date + ".xls");
			res.setContentType("application/octet-stream;charset=utf-8");

			List<Object[]> inoutDepositoryExportList = new ArrayList<Object[]>();
			String[] title = { "产品编码", "产品名称", "属性", "一级仓库", "单据类型", "单据编号", "关联单据号", "出入库类型", "数量", "剩余一级仓库库存", "剩余库存", "时间"};
			inoutDepositoryExportList.add(title);
			SimpleDateFormat dfuse = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			if (inOutDepositoryList != null) {
				for (InOutDepository tmp : inOutDepositoryList) {
					Object[] data = {
							tmp.getCode() + "",
							tmp.getName() + "",
							attributeManager.getFullAttributeStringByAttrs(tmp.getAttrs()) + "",
							tmp.getDepFirstName() + "",
							StringUtil.isBlank(EnumInOutDepository.toMap().get((tmp.getType()))) ? "" : EnumInOutDepository.toMap().get((tmp.getType())) + "",
							tmp.getBillNum() + "",
							tmp.getRelationNum() + "",
							StringUtil.isBlank(EnumInOutThreeDepository.toMap().get((tmp.getType()))) ? "" : EnumInOutThreeDepository.toMap().get((tmp.getType())) + "" + "",
							tmp.getAmount(),
							tmp.getLeftDepNum(),
							tmp.getLeftNum(),
							dfuse.format(tmp.getGmtDate())+ ""};
					inoutDepositoryExportList.add(data);
				}
			}
			goodsBatch.exportExcelByObject(outwt, inoutDepositoryExportList);
			outwt.flush();
		} catch (Exception e) {
			request.setAttribute("errorMessage", "导出失败！");
			log.error(e);
		} finally {
			outwt.close();
		}
		return "/storage/list_storage_ledger";
	}

}
