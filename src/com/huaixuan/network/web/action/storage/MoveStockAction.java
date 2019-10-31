package com.huaixuan.network.web.action.storage;

import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
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
import org.springframework.web.bind.annotation.ResponseBody;

import com.huaixuan.network.biz.domain.admin.AdminAgent;
import com.huaixuan.network.biz.domain.goods.Category;
import com.huaixuan.network.biz.domain.storage.DepLocation;
import com.huaixuan.network.biz.domain.storage.Depository;
import com.huaixuan.network.biz.domain.storage.DepositoryFirst;
import com.huaixuan.network.biz.domain.storage.Storage;
import com.huaixuan.network.biz.domain.storage.StorageMoveLog;
import com.huaixuan.network.biz.domain.storage.query.MoveStorageQuery;
import com.huaixuan.network.biz.enums.EnumDepositoryType;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.service.common.CodeManager;
import com.huaixuan.network.biz.service.goods.AttributeManager;
import com.huaixuan.network.biz.service.goods.CategoryManager;
import com.huaixuan.network.biz.service.goods.GoodsBatchManager;
import com.huaixuan.network.biz.service.storage.DepLocationManager;
import com.huaixuan.network.biz.service.storage.DepositoryFirstManager;
import com.huaixuan.network.biz.service.storage.DepositoryService;
import com.huaixuan.network.biz.service.storage.StorageManager;
import com.huaixuan.network.biz.service.storage.StorageMoveLogManager;
import com.huaixuan.network.biz.service.user.UserManager;
import com.huaixuan.network.common.util.DateUtil;
import com.huaixuan.network.common.util.json.JSONObject;
import com.huaixuan.network.web.action.BaseAction;

@Controller
@RequestMapping(value = "/storage" )
public class MoveStockAction extends BaseAction {

    protected static Log          log                   = LogFactory.getLog(MoveStockAction.class);
    /**
     *
     */
    private static final long     serialVersionUID      = 1L;
    @Autowired
    StorageManager        storageManager;
    @Autowired
    DepositoryService     depositoryService;
    @Autowired
    DepLocationManager    depLocationManager;
    @Autowired
	DepositoryFirstManager depositoryFirstManager;
    @Autowired
    UserManager           userManager;
    @Autowired
    CategoryManager       categoryManager;
    @Autowired
    StorageMoveLogManager storageMoveLogManager;
    @Autowired
    CodeManager           codeManager;
    @Autowired
    GoodsBatchManager     goodsBatch;
    @Autowired
    AttributeManager      attributeManager;

//    private List<Depository>      depositoryList;
//    private List<Storage>         storageList;
//    private Map<String, String>   parMap                = new HashMap<String, String>();
//    private Map<String, Object>   parMapTwo             = new HashMap<String, Object>();
//    private List<DepLocation>     depLocationLists;
//    private List<DepLocation>     depLocationListInit;
//    private List<Depository>      depositoryLists;
//    private String                id;                                                              // 单个id
//    private String                flag;                                                            // 标识
//    private String                account;                                                         // 数量
//    private String                newdepId;                                                        // 新库存Id
//    private String                newdepLocId;                                                     // 新库位Id
//    private String                memo;
//    private long                  countNum;
//    private Map                   returnMap             = new HashMap();                           // 返回Map
//    private List<StorageMoveLog>  storageMoveLogList    = new ArrayList<StorageMoveLog>();
////    private List<StorageMoveLog>  storageMoveList    = new ArrayList<StorageMoveLog>();
////    private List<StorageMoveLog>  storageMoveLogListOne = new ArrayList<StorageMoveLog>();
////    private StorageMoveLog        storageMoveLog;
//    private Storage               storageOld;
//    private Storage               storageNew;
//    private String                logId;
//    private String                message;
//
//    private List<Storage>         returnMessage         = new ArrayList<Storage>();
//    private List<Storage>         storageListOne        = new ArrayList<Storage>();
//    private String                typea;
//    private String                status;
//    private String                jump;
//    private String                moveCode;
    final static private String   move                  = "1";                                     //传入类型为移库
    final static private String   oneaccount            = "1";                                     //单条调拨、移库时，记录返回数量为1
    final static private int      pageaccountsize       = 100;                                     //单页不能超过100条记录
//    private List<Category>        catList;                                                         //一级类目
//    private List<Category>        twocatList;                                                      //二级类目
//    private List<Category>        twocatListInit;                                                  //二级类目联动
//    private String                curPage;
//    private Map<String, List>     mapsId;
//    private List<DepositoryFirst>    depositoryFirstList;                                       //一级仓库列表


    /**
     * 查询移库外借记录列表
     *
     * @param moveStorageQuery
     * @param currPage
     * @param model
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
	@RequestMapping(value = "/list_move_storage")
    public String moveStock(
    		@ModelAttribute("moveStorageQuery") MoveStorageQuery moveStorageQuery,
    		@RequestParam(value = "selectedIds", required = true, defaultValue = "") String selectedIds,
    		@RequestParam(value = "selectIds", required = true, defaultValue = "") String selectIds,
    		@RequestParam(value = "ids", required = true, defaultValue = "") String ids,
			@RequestParam(value = "page", required = false, defaultValue = "1") int currPage,
			@RequestParam(value = "message", required = false, defaultValue = "") String message,
			AdminAgent adminAgent, Model model) throws Exception {

    	//初始化仓库信息
        this.initDepository(moveStorageQuery, adminAgent, model);
        moveStorageQuery.setDepfirstIds(this.getDepfirstIdForQuery(adminAgent));

        model.addAttribute("catList", categoryManager.getCatInfoByDepth(1));
        model.addAttribute("twocatList", categoryManager.getCatInfoByDepth(2));
        if (StringUtils.isNotBlank(moveStorageQuery.getCatCode())) {
        	model.addAttribute("twocatListInit", categoryManager.getRightChildInfoOfTheParent(moveStorageQuery.getCatCode()));
        }
        moveStorageQuery.setConditionTwo("conditionTwo");//分conditionOne:没有子类目 conditionTwo：有子类目
        if (StringUtils.isNotBlank(moveStorageQuery.getTwoCatCode())) {
            {
                List<Category> tempCategoryList = new ArrayList<Category>();
                tempCategoryList = categoryManager.getRightChildInfoOfTheParent(moveStorageQuery.getTwoCatCode());
                if (tempCategoryList.isEmpty()) {
                	moveStorageQuery.setConditionOne("conditionOne");
                    moveStorageQuery.setConditionTwo("");
                }
            }
        }

        //翻页不丢失
        Map<String, String> selectMap = new HashMap<String, String>();

        Map<String, List> map;
        if (StringUtils.isNotBlank(selectedIds)) {
            map = getMap4Json(selectedIds);
        } else {
            map = new HashMap<String, List>();
        }

        String[] idsArray = null;
        if (ids != null) {
        	idsArray = ids.split(",");
            List<Object> temp = new ArrayList<Object>();
            for (String goodsId : idsArray) {
                temp.add(goodsId);
            }
            map.put(currPage+"", temp);
        }

        Map<String, List> mapsId = map;
        //前台选择项
        for (Map.Entry<String, List> selectList : map.entrySet()) {
            List listValue = selectList.getValue();
            for (Object value : listValue) {
                selectMap.put(value.toString(), value.toString());
            }
        }

        if (selectIds == null) {
        	selectIds = "";
        }
        if (idsArray != null) {
            // 增加新选择的
            for (String goodsId : idsArray) {
            	selectIds += goodsId + ",";
            }
        }
        QueryPage queryPage = storageManager.getStoragesWithMove(moveStorageQuery, currPage, pageSize);

        model.addAttribute("queryObject", moveStorageQuery);
        model.addAttribute("query", queryPage);
        model.addAttribute("selectIds", mapsId);
        model.addAttribute("selectMap", selectMap);
        model.addAttribute("categoryManager", categoryManager);
        model.addAttribute("attributeManager", attributeManager);
        model.addAttribute("count", storageManager.getStoragesCountWithMove(moveStorageQuery));
        if(message != null)
        {
        	model.addAttribute("message", message);
        }

        return "/storage/list_move_storage";
    }

    public static Map<String, List> getMap4Json(String jsonString) {
        JSONObject jsonObject;
        Map<String, List> valueMap = new HashMap<String, List>();
        try {
            jsonObject = new JSONObject(jsonString);
            Iterator keyIter = jsonObject.keys();
            String key;
            String value;
            while (keyIter.hasNext()) {
                key = (String) keyIter.next();
                value = jsonObject.get(key).toString();
                value = value.substring(1, value.length() - 1);
                List list = Arrays.asList(value.split(","));
                valueMap.put(key, list);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return valueMap;
    }


    @RequestMapping(value="/doMoveAndTransferPage")
    public String doMoveAndTransferPage(
    		@ModelAttribute("moveStorageQuery") MoveStorageQuery moveStorageQuery,
//    		@RequestParam(value = "operate", required = false, defaultValue = " ") String operate,
//    		@RequestParam(value = "flag") String flag,
//    		@RequestParam(value = "id", required = false, defaultValue = " ") String id,
//    		@RequestParam(value = "newamount", required = false, defaultValue = " ") String[] newamount,
//    		@RequestParam(value = "memo", required = false, defaultValue = " ") String memo,
//    		@RequestParam(value = "ids", required = false, defaultValue = " ") String ids,
//    		@RequestParam(value = "actionType", required = false, defaultValue = " ") String actionType,
    		@RequestParam(value = "page", required = false, defaultValue = "1") int currPage,
//    		@RequestParam(value = "jump", required = false, defaultValue = " ")String jump,
//    		@RequestParam(value = "account", required = false, defaultValue = " ") String account,
    		HttpServletRequest request,
			AdminAgent adminAgent,
			Model model
    	) throws Exception {

//
    	String operate = this.getParameter(request,"operate");
		String flag = this.getParameter(request,"flag");
		String id = this.getParameter(request,"id");
		String[] newamount = request.getParameterValues("newamount");
		String memo = this.getParameter(request,"memo");
		String ids = this.getParameter(request,"ids");
		String actionType = this.getParameter(request,"actionType");
		String jump = this.getParameter(request,"jump");
		String account = this.getParameter(request,"account");

    	model.addAttribute("flag", flag);

    	Map returnMap = new HashMap();
    	QueryPage queryPage = null;

    	String[] arrIds = null;
    	if(StringUtils.indexOf(ids, ",") > 0){
    		arrIds = ids.split(",");
    	}else{
    		arrIds = request.getParameterValues("arrIds");
    	}

        List<DepositoryFirst> depositoryFirstList = this.initDepository(moveStorageQuery, adminAgent, model);

        Map<String,Object> parMapTwo = new HashMap<String,Object>();

        //做移库操作
        if (StringUtils.isNotBlank(operate) && "move".equals(operate)) {

        	List<Storage> storageListOne = new ArrayList<Storage>();

        	List<Storage> storageList = new ArrayList<Storage>();
            // move
        	Long newdepfirstId = Long.parseLong(moveStorageQuery.getDepfirstId());
            Long newdepIdmove = Long.parseLong(moveStorageQuery.getDepId());
            Long newdepLocIdmove = Long.parseLong(moveStorageQuery.getLocId());
            account = adminAgent.getUsername();
            //取出单条记录移库
            if (id != null && !"".equals(id)) {
            	parMapTwo.clear();
                parMapTwo.put("id", id);
                storageListOne = storageManager.getStorageByIds(parMapTwo);
            }
            //取出多条记录移库
            if (!(ids != null && "".equals(ids))) {
            	parMapTwo.clear();
                String[] idstr = ids.split(",");
                parMapTwo.put("ids", idstr);
                storageListOne = storageManager.getStorageByIds(parMapTwo);
            }
            //移库操作
            if (flag.equals("move")) {
                flag = move;
                boolean sameDepfirst = true;
                if(storageListOne!=null && storageListOne.size() > 0){
                	Long depfirstTemp = storageListOne.get(0).getDepFirstId();
                	for(Storage storage : storageListOne){
                		if(!(storage.getDepFirstId().equals(depfirstTemp))){
                			sameDepfirst = false;
                			break;
                		}
                	}
                }
                if(sameDepfirst){
                    returnMap = storageMoveLogManager.moveToOtherLoc(storageListOne, newdepfirstId, newdepIdmove,
                            newdepLocIdmove, newamount, account, flag,memo);
                }else{
                	returnMap.put("flag", "false");
                	returnMap.put("message", "sameDepfirst");
                }
            }
            //回调操作
            else {
                flag = "3";
                boolean sameDepfirst = true;
                if(storageListOne!=null && storageListOne.size() > 0){
                	Long depfirstTemp = storageListOne.get(0).getDepFirstId();
                	for(Storage storage : storageListOne){
                		if(!(storage.getDepFirstId().equals(depfirstTemp))){
                			sameDepfirst = false;
                			break;
                		}
                	}
                }
                if(sameDepfirst){
                	returnMap = storageMoveLogManager.moveToOtherLoc(storageListOne, newdepfirstId, newdepIdmove,
                            newdepLocIdmove, newamount, account, flag,memo);
                }else{
                	returnMap.put("flag", "false");
                	returnMap.put("message", "sameDepfirst");
                }

            }
            //单条移库、回调后的回显
            if (id != null && !"".equals(id)) {
                account = oneaccount;
                Storage storage = new Storage();
                storage.setId(Long.parseLong(id));
                queryPage = storageManager.getStoragesWithAll(storage, 1, Integer.MAX_VALUE);
            }
            //多条移库、回调后的回显
            if (ids != null && !"".equals(ids)) {
                account = String.valueOf(arrIds.length);
                Storage storage = new Storage();
                storage.setStorageids(ids.split(","));
                queryPage = storageManager.getStoragesWithMoveAllTwo(storage, 1, Integer.MAX_VALUE);
            }

            model.addAttribute("account", account);
            model.addAttribute("idstring", ids);

        }
        //转移到移库、回调页面
        else {
        	String message ="";

            if (StringUtils.isNotBlank(jump) && "move".equals(jump)) {
                //单条到移库、回调页面
                if (null != id) {
                    account = oneaccount;
                    moveStorageQuery.setId(id);
                    queryPage = storageManager.getStoragesWithMove(moveStorageQuery, currPage, pageSize);
                    List storageList = queryPage.getItems();
                    if (storageList.size() == 0) {
                        message = "no_result";
                        return "redirect:/storage/list_move_storage.html";
                    }
                }
                if(flag.equals("transfer")){
                	for(int i=0;i<depositoryFirstList.size();i++){
                		DepositoryFirst tmp = depositoryFirstList.get(i);
                    	if(tmp.getType().equals("w")){
                    	    depositoryFirstList.remove(tmp);
                    	}
                	}
                }
            }
            //批量记录到移库、回调页面
            if (StringUtils.isNotBlank(jump) && "movebat".equals(jump)) {

                if (null != ids && arrIds.length > 0) {

                    if (arrIds.length > pageaccountsize) {
                        message = "overpage";
                        model.addAttribute("message", message);
                        return "redirect:/storage/list_move_storage.html";
                    }

                    account = String.valueOf(arrIds.length);
                    moveStorageQuery.setStorageids(arrIds);
                    queryPage = storageManager.getStoragesWithMoveTwo(moveStorageQuery, currPage, pageSize);
                    List<Storage> storageList = (List<Storage>) queryPage.getItems();
                    List<Storage> newStorageList = new ArrayList<Storage>();// 新的结果集

                    if(flag.equals("transfer")){
                    	for(Storage tmp:storageList){
                    		if(EnumDepositoryType.DEFECT_DEP.getKey().equals(tmp.getDepType())||tmp.getDepfirstType().equals("w")){
                    			message = "wrongtype";
                    			model.addAttribute("message", message);
                    			// 去掉不满足条件的商品
                    			return  "redirect:/storage/list_move_storage.html";
                    		}else{
                    			newStorageList.add(tmp);// 将符合条件的对象放入新集合中
                    		}
                    	}
                    	storageList = newStorageList;
                       	for(int i=0;i<depositoryFirstList.size();i++){
                    		DepositoryFirst tmp = depositoryFirstList.get(i);
                        	if(tmp.getType().equals("w")){
                        	    depositoryFirstList.remove(tmp);
                        	}
                    	}
                    }
//                    if (storageList.size() == 0 || storageList.size() != arrIds.length) {
//                        message = "no_result_one";
//                        return  "redirect:/storage/list_move_storage.html";
//                    }
                    StringBuffer s = new StringBuffer();
                    for (String tempstr : arrIds) {
                        s.append(tempstr).append(",");
                    }
                    model.addAttribute("idstring", s.toString());
                }
            }
            //按条件查询记录到移库、回调页面
            if (StringUtils.isNotBlank(jump) && "movearea".equals(jump)) {
            	moveStorageQuery.setConditionTwo("conditionTwo");//分conditionOne:没有子类目 conditionTwo：有子类目
                if (StringUtils.isNotBlank(moveStorageQuery.getTwoCatCode())) {
                    {
                        List<Category> tempCategoryList = new ArrayList<Category>();
                        tempCategoryList = categoryManager.getRightChildInfoOfTheParent(moveStorageQuery.getTwoCatCode());
                        if (tempCategoryList.isEmpty()) {
                        	moveStorageQuery.setConditionOne("conditionOne");
                        	moveStorageQuery.setConditionTwo("");
                        }
                    }
                }
                queryPage = storageManager.getStoragesWithMove(moveStorageQuery, 1, Integer.MAX_VALUE);
                List<Storage> storageList = (List<Storage>) queryPage.getItems();
                if(flag.equals("transfer")){
                	for(Storage tmp:storageList){
                		if(EnumDepositoryType.DEFECT_DEP.getKey().equals(tmp.getDepType())||tmp.getDepfirstType().equals("w")){
                			message = "wrongtype";
                			model.addAttribute("message", message);
                			return  "redirect:/storage/list_move_storage.html";
                		}
                	}
                   	for(int i=0;i<depositoryFirstList.size();i++){
                		DepositoryFirst tmp = depositoryFirstList.get(i);
                    	if(tmp.getType().equals("w")){
                    	    depositoryFirstList.remove(tmp);
                    	}
                	}
                }
                account = String.valueOf(storageList.size());
                StringBuffer s = new StringBuffer();
                for (int k = 0; k < storageList.size(); k++) {
                    s.append(storageList.get(k).getId().toString()).append(",");
                }
                model.addAttribute("idstring", s.toString());
            }

            model.addAttribute("account", account);

        }
		if(id != null && id.length()>0)
		{
			model.addAttribute("id",id);
		}
		model.addAttribute("returnMap", returnMap);
        model.addAttribute("jump", jump);
        model.addAttribute("allShow", true);
        model.addAttribute("query", queryPage);
        model.addAttribute("categoryManager", categoryManager);
        model.addAttribute("attributeManager", attributeManager);
        return "/storage/move_and_transfer_page";
    }

    /**
     * 外借回调操作页面
     * @return
     */
    @RequestMapping(value="/moveLogReturn")
    public String moveLogReturn(
    		@RequestParam(value = "isPrint", required = false, defaultValue = "")String isPrint,
    		@RequestParam(value = "moveCode", required = false, defaultValue = "")String moveCode,
    		@RequestParam(value = "page", required = false, defaultValue = "1") int currPage,
    		@RequestParam(value = "pageNum", required = false, defaultValue = "")String pageNum,
    		@RequestParam(value = "fontSize", required = false, defaultValue = "")String fontSize,
    		@RequestParam(value = "message", required = false, defaultValue = "")String message,
    		Model model
    	)throws Exception {
        Map<String,String> parMap = new HashMap<String,String>();
    	parMap.put("moveCode", moveCode);
        searchMoveLogByMap(parMap, currPage, pageSize, model);
        model.addAttribute("sumNum", storageMoveLogManager.sumMoveNumByMap(parMap));
        // 打印外借归还单
        if(StringUtils.isNotBlank(isPrint)){

            if (StringUtils.isBlank(pageNum)) {
                pageNum = "8";
            }
            if (StringUtils.isBlank(fontSize)) {
                fontSize = "3";
            }

            model.addAttribute("pageNum", Integer.valueOf(pageNum).intValue());
            model.addAttribute("fontSize", Integer.valueOf(fontSize).intValue());

        	return "/storage/printReturn";
    	}
        model.addAttribute("message", message);
        return "/storage/moveLogReturn";
    }
//
    /**
     * 打印外借单
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/printBorrow")
    public String printBorrow(
    		@RequestParam("moveCode")String moveCode,
    		@RequestParam(value = "fontSize", required = false, defaultValue = "")String fontSize,//字体大小
    		@RequestParam(value = "pageNum", required = false, defaultValue = "") String pageNum,//每页显示条数
    		@RequestParam(value = "countNum", required = false, defaultValue = "") String countNum,//合计总数
    		@RequestParam(value= "printType")String printType,
    		Model model,
    		AdminAgent adminAgent
    	) throws Exception {
        if (StringUtils.isBlank(pageNum)) {
            pageNum = "10";
        }
        if (StringUtils.isBlank(fontSize)) {
            fontSize = "3";
        }
        Map<String,String> parMap = new HashMap();
        parMap.put("moveCode", moveCode);
        //全部打印
        QueryPage queryPage = storageMoveLogManager.getStorageMoveLogsByMoveCode(parMap, 1, Integer.MAX_VALUE);
        refreshList(queryPage,model);

        model.addAttribute("pageNum", Integer.valueOf(pageNum).intValue());
        model.addAttribute("fontSize", Integer.valueOf(fontSize).intValue());
        model.addAttribute("countNum", countNum);
        model.addAttribute("moveCode", moveCode);
        model.addAttribute("userName", adminAgent.getUsername());
        model.addAttribute("printType", printType);
        model.addAttribute("attributeManager", attributeManager);

        return "/storage/printBorrow";
    }

    /**
     * 导出移库外借查询结果
     * @return
     */
    @SuppressWarnings("unchecked")
	@RequestMapping(value = "/exportReturnStockList")
    public void exportReturnStockList(
    		@ModelAttribute("moveStorageQuery") MoveStorageQuery moveStorageQuery,
//    		@RequestParam(value = "moveType", required = false, defaultValue = "") String moveType,
    		AdminAgent adminAgent,
    		Model model,
    		HttpServletResponse res) throws Exception {
        OutputStream outwt = null;
        try {
            Date da = new Date();
            outwt = res.getOutputStream();
            SimpleDateFormat df = new SimpleDateFormat("yyyy_MM_dd");
            String date = df.format(da);
            res.setContentType("application/octet-stream;charset=utf-8");
            List<Object[]> storageExportList = new ArrayList<Object[]>();
            res.setHeader("Content-disposition", "attachment; filename=StorageMoveLogsList_" + date + ".xls");
            moveStorageQuery.setDepfirstIds(this.getDepfirstIdForQuery(adminAgent));
            QueryPage queryPage = storageMoveLogManager.getMoreDetailByMap(moveStorageQuery, 1, Integer.MAX_VALUE,false);
            List<StorageMoveLog> stMoveLogList = (List<StorageMoveLog>)queryPage.getItems();
            for (StorageMoveLog tmp : stMoveLogList) {
                Depository olddp = depLocationManager.getDepositoryByLocationId(tmp.getOldLocId());
                DepLocation olddpl = depLocationManager.getDepLocationByLocationId(tmp.getOldLocId());
                tmp.setOldDepositoryName(olddp.getName());
                tmp.setOldDepLocationName(olddpl.getLocName());
            }
            String[] title = new String[] { "外借编号", "产品编码", "产品名称", "供应商", "类 型", "调出一级仓库", "调出仓库", "调出库位",
                    "移动数量", "调入一级仓库", "调入仓库", "调入库位", "归还状态", "操作人", "操作时间" };
//            if ("1".equals(moveType)) {
//                title = new String[] { "移库编号", "产品编码", "产品名称", "供应商", "类 型", "调出一级仓库", "调出仓库", "调出库位",
//                        "移动数量", "调入一级仓库", "调入仓库", "调入库位", "操作人", "操作时间" };
//            } else if ("3".equals(moveType)) {
//                title = new String[] { "外借编号", "产品编码", "产品名称", "供应商", "类 型", "调出一级仓库", "调出仓库", "调出库位",
//                        "移动数量", "调入一级仓库", "调入仓库", "调入库位", "归还状态", "操作人", "操作时间" };
//            }
            storageExportList.add(title);
            String optType = "";
            String statusStr = "";
            if (stMoveLogList != null) {
                for (StorageMoveLog obj : stMoveLogList) {
                	Object[] data = null;
                	if("1".equals(obj.getType())){
                		optType = "移库";
                		statusStr = "";
                	}else{
                		optType = "外借";
                		statusStr = ("0".equals(obj.getStatus()) ? "未归还" : "已归还");
                	}
                	data = new Object[] { obj.getMoveCode(), obj.getCode(),
                            obj.getInstanceName(), obj.getSupplierName(), optType,
                            obj.getOldDepFirstName() + "",
                            obj.getOldDepositoryName(), obj.getOldDepLocationName(),
                            obj.getMoveNum(), obj.getDepFirstName() + "", obj.getDepositoryName(),
                            obj.getDepLocationName(), statusStr, obj.getCreater(), obj.getGmtCreate_str() };

//                    if ("1".equals(moveType)) {
//                        data = new Object[] { obj.getMoveCode(), obj.getCode(),
//                                obj.getInstanceName(), obj.getSupplierName(), optType,
//                                obj.getOldDepFirstName() + "",
//                                obj.getOldDepositoryName(), obj.getOldDepLocationName(),
//                                obj.getMoveNum(), obj.getDepFirstName() + "", obj.getDepositoryName(),
//                                obj.getDepLocationName(), obj.getCreater(), obj.getGmtCreate_str() };
//                    } else if ("3".equals(moveType)) {
//                        data = new Object[] { obj.getMoveCode(), obj.getCode(),
//                                obj.getInstanceName(), obj.getSupplierName(), optType,
//                                obj.getOldDepFirstName() + "",
//                                obj.getOldDepositoryName(), obj.getOldDepLocationName(),
//                                obj.getMoveNum(), obj.getDepFirstName() + "", obj.getDepositoryName(),
//                                obj.getDepLocationName(),
//                                ("0".equals(obj.getStatus()) ? "未归还" : "已归还"), obj.getCreater(),
//                                obj.getGmtCreate_str() };
//                    }
                    storageExportList.add(data);
                }
            }
            goodsBatch.exportExcelByObject(outwt, storageExportList);
            outwt.flush();
        } catch (Exception e) {
            log.error(e);
        } finally {
            outwt.close();
        }
    }

    /**
     * 库存归还记录查询
     */
	@RequestMapping(value = "/list_return_storage")
    public String returnStock(
    		@ModelAttribute("moveStorageQuery") MoveStorageQuery moveStorageQuery,
			@RequestParam(value = "page", required = false, defaultValue = "1") int currPage,
			AdminAgent adminAgent, Model model) {
        //加入全部的一级仓库ID
		initDepository(moveStorageQuery, adminAgent, model);

        moveStorageQuery.setDepfirstIds(this.getDepfirstIdForQuery(adminAgent));
        QueryPage queryPage = storageMoveLogManager.getStorageMoveLogsByMap(moveStorageQuery, currPage, pageSize);
        this.refreshList(queryPage, model);
        model.addAttribute("categoryManager", categoryManager);
        model.addAttribute("attributeManager", attributeManager);
        model.addAttribute("queryObject", moveStorageQuery);
        return "/storage/list_return_storage";
    }

//    /**
//     * 单个记录做归还
//     * @return
//     */
//    public String returnOneStock() {
//        ActionContext context = ActionContext.getContext();
//        String logIdstr = getRequest().getParameter("id");
//        moveCode = getRequest().getParameter("moveCode");
//        message = "success";
//        //获取记录表中记录
//        storageMoveLog = storageMoveLogManager.getStorageMoveLog(Long.parseLong(logIdstr));
//        //归还数量判断
//        storageNew = storageManager.getStorage(storageMoveLog.getNewStorageId());
//        if (storageNew == null || storageMoveLog.getMoveNum() > storageNew.getStorageNum()) {
//            message = "amount_error";
//            return SUCCESS;
//        }
//        storageMoveLogListOne.add(storageMoveLog);
//        //做归还
//        returnMap = storageMoveLogManager.backToStorage(storageMoveLogListOne,getLoginAdminUser().getUsername());
//
//        typea = "normal"; //normal表示联动时为移库
//        depositoryLists = depositoryService.getDepositorys();
//        depLocationLists = depLocationManager.getAllLocations();
//        if (StringUtils.isNotBlank(parMap.get("newDepId"))) {
//            depLocationListInit = depLocationManager.getLocationsByDepositoryId(Long
//                .parseLong(parMap.get("newDepId")));
//        }
//        if (StringUtils.isNotBlank(parMap.get("type")) && parMap.get("type").equals("3")) {
//            typea = "huidiao";//huidiao表示联动时为归还
//            parMap.put("status", status);
//        }
//        parMap.put("id", logIdstr);
//        searchListByMap();
//        context.put("message", message);
//        context.put("returnMap", returnMap);
//        context.put("moveCode", moveCode);
//        return SUCCESS;
//    }
//
    /**
     * 批量记录做归还
     * @return
     */
	@RequestMapping(value="/doReturnBat")
    public String doReturnBat(
    		@RequestParam("moveCode")String moveCode,
    		@RequestParam("ids")String[] ids,
    		@RequestParam("returnNum")String[] returnNum,
    		AdminAgent adminAgent,
    		Model model
    	)throws Exception {

        String message = "success";

        //批量操作单页不能超过100个
        if (ids.length > 100) {
            message = "returnpageover";
            model.addAttribute("message", message);
            return "redirect:/storage/moveLogReturn.html?moveCode="+moveCode;
        }
        List<StorageMoveLog> storageMoveLogListOne = new ArrayList<StorageMoveLog>();
        for (int i = 0; i < ids.length; i++) {
			if (returnNum[i] != null && StringUtils.isNotBlank(returnNum[i].trim())
					&& !StringUtils.isNumeric(returnNum[i].trim())) {
				message = "not_number";
				model.addAttribute("message", message);
				return "redirect:/storage/moveLogReturn.html?moveCode=" + moveCode;
			}
			if (returnNum[i] == null || StringUtils.isBlank(returnNum[i].trim())) {
				continue;
			}
            StorageMoveLog storageMoveLog = storageMoveLogManager.getStorageMoveLog(Long.parseLong(ids[i]));
            storageMoveLog.setThisReturnNum(Long.parseLong(returnNum[i].trim()));
            Storage storageNew = storageManager.getStorage(storageMoveLog.getNewStorageId());
            //判断类型是否正确
            if (!(storageMoveLog.getType().equals("3"))) {
                message = "not_right";
                model.addAttribute("message", message);
                return "redirect:/storage/moveLogReturn.html?moveCode="+moveCode;
            }
            //判断状态是否正确
            if (!(storageMoveLog.getStatus().equals("0"))) {
                message = "stauts_error";
                model.addAttribute("message", message);
                return "redirect:/storage/moveLogReturn.html?moveCode="+moveCode;
            }
            //归还数量判断
            if (storageNew == null
      || Long.parseLong(returnNum[i].trim()) > storageNew.getStorageNum()) {
                message = "amount_error";
                model.addAttribute("message", message);
                return "redirect:/storage/moveLogReturn.html?moveCode="+moveCode;
            }
            storageMoveLogListOne.add(storageMoveLog);
        }
        Map returnMap = storageMoveLogManager.backToStorage(storageMoveLogListOne, adminAgent.getUsername());
        message = "success";
        model.addAttribute("message", message);
        model.addAttribute("returnMap", returnMap);
        model.addAttribute("moveCode", moveCode);
        return "redirect:/storage/moveLogReturn.html";
    }

    /*
     * 查询符合条件的移库外借记录
     */
    private void searchMoveLogByMap(Map parMap, int currPage, int pageSize, Model model) {
        //取记录表中的记录
        QueryPage queryPage = storageMoveLogManager.getStorageMoveLogsByMoveCode(parMap, currPage, pageSize);
        refreshList(queryPage,model);
    }

    /*
     * 重新组织查询记录集合
     */
    @SuppressWarnings("unchecked")
	private void refreshList(QueryPage queryPage,Model model) {
    	List<StorageMoveLog> storageMoveLogList = (List<StorageMoveLog>)queryPage.getItems();
    	long countNum = 0L;
        if (storageMoveLogList != null) {
            for (StorageMoveLog tmp : storageMoveLogList) {
                Depository olddp = depLocationManager.getDepositoryByLocationId(tmp.getOldLocId());
                DepLocation olddpl = depLocationManager.getDepLocationByLocationId(tmp
                    .getOldLocId());
                tmp.setDepType(olddp.getType());
                tmp.setOldDepositoryName(olddp.getName());
                tmp.setOldDepLocationName(olddpl.getLocName());
                countNum += tmp.getMoveNum();
            }
            StorageMoveLog storageMoveLog = storageMoveLogList.get(0);
            model.addAttribute("storageMoveLog", storageMoveLog);
            queryPage.setItems(storageMoveLogList);
        }
        model.addAttribute("query", queryPage);
    }

    /*
     * 根据产品编号或者产品名称来查移库外借记录    modify by yangak
     */
    @RequestMapping(value = "/list_move_storage_detail")
    public String moveStorageDetail(
    		@ModelAttribute("moveStorageQuery") MoveStorageQuery moveStorageQuery,
			@RequestParam(value = "page", required = false, defaultValue = "1") int currPage,
			AdminAgent adminAgent, Model model) {

    	//初始化仓库信息
        this.initDepository(moveStorageQuery, adminAgent, model);

		if (StringUtils.isBlank(moveStorageQuery.getGmtCreateStart())
				&& StringUtils.isBlank(moveStorageQuery.getGmtCreateEnd())) {
			moveStorageQuery.setGmtCreateStart(DateUtil.getDiffDate(new Date(), -7));
			moveStorageQuery.setGmtCreateEnd(DateUtil.getDateToString(new Date()));

		}
		QueryPage queryPage = storageMoveLogManager.getMoveOrdersDetailByMap(moveStorageQuery, currPage, pageSize);
		model.addAttribute("storageMoveLog",storageMoveLogManager.getSumNumByMap(moveStorageQuery));
		model.addAttribute("attributeManager", attributeManager);
		model.addAttribute("queryObject", moveStorageQuery);
		model.addAttribute("query",queryPage);
		return "/storage/list_move_storage_detail";

	}

    /**
     * Json根据一级仓库获取全部有效仓库
     */
    @RequestMapping(value = "/selectDep")
    public @ResponseBody List<Depository> selectDep(
    		@RequestParam(value = "param", required = false) String depfirstId) {
    	List<Depository> selectdeplist = null;
        if (StringUtils.isNotBlank(depfirstId) && StringUtils.isNumeric(depfirstId)) {
            selectdeplist = depositoryService.getDeplistByFirstDepId(Long.parseLong(depfirstId));
        }
        return selectdeplist;
    }

    /**
     * Json根据仓库获取全部有效库位
     */
    @RequestMapping(value = "/selectLoc")
    @ResponseBody
    public List<DepLocation> selectLoc(
    		@RequestParam(value = "param", required = false) String depId) {
    	List<DepLocation> selectloclist = null;
        if (StringUtils.isNotBlank(depId) && StringUtils.isNumeric(depId)) {
            selectloclist = depLocationManager.getRightLocationsByDepositoryId(Long.parseLong(depId));
        }
        return selectloclist;
    }

    //=====================================================================================================================================
    /*
     * 初始化仓库信息
     *
     * @param moveStorageQuery
     * @param adminAgent
     * @param model
     */
 	private List<DepositoryFirst> initDepository(MoveStorageQuery moveStorageQuery,
 			AdminAgent adminAgent, Model model) {
 		//加入全部的一级仓库ID
     	List<DepositoryFirst> depositoryFirstList = depositoryFirstManager.getDepositoryFirstListByIds(getDepfirstIdForQuery(adminAgent));
     	moveStorageQuery.setDepfirstIds(getDepfirstIdForQuery(adminAgent));
 		if (depositoryFirstList == null || depositoryFirstList.size() == 0) {
 			model.addAttribute("message", "一级仓库记录为空！");
 		}else{
 			model.addAttribute("depositoryFirstList", depositoryFirstList);
 			if (StringUtils.isNotBlank(moveStorageQuery.getDepfirstId())
 					&& StringUtils.isNumeric(moveStorageQuery.getDepfirstId())) {
 				model.addAttribute("depositoryList", depositoryService.getDeplistByFirstDepId(Long.valueOf(moveStorageQuery.getDepfirstId())));
 			}
 			if (StringUtils.isNotBlank(moveStorageQuery.getDepId())
 					&& StringUtils.isNumeric(moveStorageQuery.getDepId())) {
 				model.addAttribute("depLocationLists", depLocationManager.getLocationsByDepositoryId(Long.valueOf(moveStorageQuery.getDepId())));
 			}
 		}
 		return depositoryFirstList;
 	}
}
