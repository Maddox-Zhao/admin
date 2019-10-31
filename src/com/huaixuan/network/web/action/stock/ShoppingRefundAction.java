/**
 * @Title: ShoppingRefundAction.java
 * @Package com.huaixuan.network.web.action.stock
 * @Description: TODO
 * @author liuwd weida-liu@foxmail.com
 * @date 2011-3-7 下午04:24:05
 * @version V1.0
 */
package com.huaixuan.network.web.action.stock;

import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.huaixuan.network.biz.dao.goods.AvailableStockDao;
import com.huaixuan.network.biz.dao.goods.GoodsInstanceDao;
import com.huaixuan.network.biz.dao.storage.OutDepositoryDao;
import com.huaixuan.network.biz.dao.storage.OutDetailDao;
import com.huaixuan.network.biz.domain.admin.Admin;
import com.huaixuan.network.biz.domain.admin.AdminAgent;
import com.huaixuan.network.biz.domain.goods.AvailableStock;
import com.huaixuan.network.biz.domain.goods.GoodsInstance;
import com.huaixuan.network.biz.domain.stock.ShoppingDetailSearch;
import com.huaixuan.network.biz.domain.stock.ShoppingList;
import com.huaixuan.network.biz.domain.stock.ShoppingRefund;
import com.huaixuan.network.biz.domain.stock.ShoppingRefundDetail;
import com.huaixuan.network.biz.domain.stock.ShoppingRefundDetailSearch;
import com.huaixuan.network.biz.domain.stock.ShoppingRefundGatherSearch;
import com.huaixuan.network.biz.domain.stock.ShoppingRefundSearch;
import com.huaixuan.network.biz.domain.stock.query.RefundDetailSearchQuery;
import com.huaixuan.network.biz.domain.stock.query.ShoppingRefundSearchQuery;
import com.huaixuan.network.biz.domain.storage.DepositoryFirst;
import com.huaixuan.network.biz.domain.storage.OutDepository;
import com.huaixuan.network.biz.domain.storage.OutDetail;
import com.huaixuan.network.biz.domain.supplier.Supplier;
import com.huaixuan.network.biz.enums.EnumOutDepository;
import com.huaixuan.network.biz.enums.EnumOutDetailStatus;
import com.huaixuan.network.biz.enums.EnumOutStatus;
import com.huaixuan.network.biz.enums.EnumShoppingListType;
import com.huaixuan.network.biz.enums.EnumShoppingRefund;
import com.huaixuan.network.biz.enums.EnumShoppingRefundReason;
import com.huaixuan.network.biz.enums.EnumShoppingRefundStatus;
import com.huaixuan.network.biz.enums.EnumStockStatus;
import com.huaixuan.network.biz.enums.EnumStorType;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.service.common.CodeManager;
import com.huaixuan.network.biz.service.goods.AttributeManager;
import com.huaixuan.network.biz.service.goods.CategoryManager;
import com.huaixuan.network.biz.service.goods.GoodsBatchManager;
import com.huaixuan.network.biz.service.goods.GoodsInstanceManager;
import com.huaixuan.network.biz.service.goods.GoodsManager;
import com.huaixuan.network.biz.service.stock.ShoppingListService;
import com.huaixuan.network.biz.service.stock.ShoppingRefundDetailService;
import com.huaixuan.network.biz.service.stock.ShoppingRefundService;
import com.huaixuan.network.biz.service.storage.DepositoryFirstManager;
import com.huaixuan.network.biz.service.supplier.SupplierService;
import com.huaixuan.network.common.util.DateUtil;
import com.huaixuan.network.web.action.BaseAction;
import com.hundsun.network.melody.common.util.StringUtil;

/**
 * @ClassName: ShoppingRefundAction
 * @Description: TODO
 * @author liuwd weida-liu@foxmail.com
 * @date 2011-3-7 下午04:24:05
 *
 */
@Controller
public class ShoppingRefundAction extends BaseAction {

    private static final long                serialVersionUID       = 5719534543822243219L;

    @Autowired
    ShoppingListService              shoppingListService;

    @Autowired
    ShoppingRefundService            shoppingRefundService;

    @Autowired
    GoodsManager                     goodsManager;

    @Autowired
    GoodsInstanceManager             goodsInstanceManager;

    @Autowired
    ShoppingRefundDetailService      shoppingRefundDetailService;

    @Autowired
    SupplierService                  supplierService;

    @Autowired
    GoodsBatchManager                goodsBatch;

    @Autowired
    DepositoryFirstManager           depositoryFirstManager;                                //一级仓库

    @Autowired
    CategoryManager					 categoryManager;

    @Autowired
    AttributeManager 				attributeManager;

    @Autowired
    CodeManager						codeManager;

    @Autowired
    AvailableStockDao			availableStockDao;

    @Autowired
    OutDepositoryDao				outDepositoryDao;

    @Autowired
    OutDetailDao 					outDetailDao;
//    private OutDetailManager                 outDetailService;

//    private String                           shoppingId;
//    private String                           errorMsg;
//    private String                           shoppingRefId;
//    private CodeManager                      codeManager;
//
//    private List<ShoppingRefundGatherSearch> shoppingRefundGSList;
//
//    private CategoryManager                  categoryManager;
//
//    private AttributeManager                 attributeManager;
//
//    private ShoppingListService              shoppingListService;
//
//    private OutDepositoryManager             outDepositoryManager;

//    private Map<String, String>              parMap                 = new HashMap<String, String>();

    private Map<String, String>              enumShoppingRefundMap  = EnumShoppingRefund.toMap();   // 采购订单状态
    private Map<String, String>              enumShoppingRefundRMap = EnumShoppingRefundReason
                                                                        .toMap();                   // 采购退货原因

//    private List<User>                       userLists;

    private Map<String, String>              enumStockStatusMap     = EnumStockStatus.toMap();      // 采购订单状态

    private Map<String, String>              enumShopRefundMap      = EnumShoppingRefundStatus
                                                                        .toMap();                   // 采购退货状态

//    private ShoppingRefund                   shoppingRefund;
//
//    private ShoppingRefundDetail             shoppingRefundDetail;
//
//    private ShoppingRefundDSearch            shoppingRefundDSearch;
//
//    private ShoppingDetailSearch             shoppingDetailSearch;
//
//    private List<Supplier>                   supplierLists;
//
//    private Map<Long, String>                supplierMap            = new HashMap<Long, String>();  // 供应商id,name
//
//    private List<ShoppingRefund>             shoppingRefundList;
//
//    private List<ShoppingRefundDetail>       shoppingRefundDetailList;
//
//    private List<Goods>                      goodsList;
//
//    private List<ShoppingRefundSearch>       shoppingRefundSearchList;
//
//    private List<ShoppingRefundDSearch>      shoppingRefundDList;
//
//    private List<ShoppingRefundDetailSearch> shoppingRefundDSList;
//
//    private List<ShoppingDetailSearch>       shoppingDetailSearchLists;
//
//
//
//    private AdminService						adminService;
//
//    private Set<String>                      shoppingNumSet         = new HashSet<String>();
//
//    private String                           message;
//
//    private List<DepositoryFirst>            depositoryFirstList;
//    private Map<Long, String>                depositoryNameMap      = new HashMap<Long, String>();
//    private Map<String, String>              storTypeMap            = EnumStorType.toMap();
//    private AvailableStockDao                availableStockDao;
    @Autowired
    GoodsInstanceDao                 goodsInstanceDao;



    /**
     * 新增shoppingRefund页面跳转初始化
     *
     * @return "success" if no exceptions thrown
     */
    @RequestMapping(value = "/stock/refund/add_shopping_refund_page")
    public String addShoppingRefundPage(
    		Model model,
    		AdminAgent adminAgent
    	) throws Exception {
        QueryPage query= supplierService.searchSupplierListWithPage(new Supplier(), 1,Integer.MAX_VALUE);
        List<Long> depFirst = getDepfirstIdForQuery(adminAgent);
        List<DepositoryFirst> depositoryFirstList = null;
        if (null != depFirst) {
            depositoryFirstList = depositoryFirstManager.getDepositoryFirstListByIds(depFirst);
        } else {
            depositoryFirstList = new ArrayList<DepositoryFirst>();
        }

        model.addAttribute("stockId", codeManager.buildCodeByDateAndType(CodeManager.CAITUI_CODE, 6,
                CAITUI));

        model.addAttribute("supplierLists", query.getItems());
        model.addAttribute("depositoryFirstList", depositoryFirstList);

        return "/stock/refund/add_shopping_refund";
    }

    /**
     * 添加shoppingRefund页面 主表
     *
     * @return "success" if no exceptions thrown
     */
    @RequestMapping(value = "/stock/refund/add_shopping_refund")
    public String addShoppingRefund(
    		@RequestParam("supplierId")String supplierId,
    		@RequestParam("shoppingId")String shoppingId,
    		@RequestParam("depFirstId")String depFirstId,
    		@RequestParam("gmtStart_str")String gmtStart_str,
    		Model model,
    		AdminAgent adminAgent
    	) throws Exception {

        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("refNum", shoppingId);

        int count = shoppingRefundService.getShoppingCountByParameterMap(paramMap);
        if (count > 0) {
//            this.setErrorMsg(this.getText("ios.shoppingrefund.add.refNum.repeat"));
            QueryPage query= supplierService.searchSupplierListWithPage(new Supplier(), 1,Integer.MAX_VALUE);
            model.addAttribute("supplierLists", query.getItems());
            model.addAttribute("stockId", codeManager.buildCodeByDateAndType(CodeManager.CAITUI_CODE, 6,
                CAITUI));
            return "redirect:/stock/refund/add_shopping_refund_page";
        }
        String userName = adminAgent.getUsername();
        ShoppingRefund shoppingRefund = new ShoppingRefund();
        Map depositoryNameMap = new HashMap();
        if ((supplierId != null) && (shoppingId != null) && (gmtStart_str != null)
            & (userName != null)) {
            shoppingRefund.setSupplierId(Long.parseLong(supplierId));
            shoppingRefund.setRefNum(shoppingId);
            shoppingRefund.setCreater(userName);
            shoppingRefund.setType(EnumShoppingRefund.REFUND_CAIGOU.getKey());
            SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = dateformat.parse(gmtStart_str);
            shoppingRefund.setRefTime(date);
            shoppingRefund.setStatus(EnumShoppingRefundStatus.STOCK_NEW.getKey());
            shoppingRefund.setGmtCreate(new Date());
            shoppingRefund.setDepFirstId(Long.parseLong(depFirstId));
            shoppingId = String.valueOf(shoppingRefundService.addShoppingRefund(shoppingRefund));
            shoppingRefund.setId(Long.parseLong(shoppingId));
            shoppingRefund = shoppingRefundService.getShoppingRefund(Long.parseLong(shoppingId));
            List<ShoppingRefundDetail> shoppingRefundDetailList = shoppingRefundDetailService.getShoppingRefundDetailsId(Long
                .parseLong(shoppingId));
            List<DepositoryFirst> depositoryFirstList = depositoryFirstManager
                .getDepositoryFirstList();
            for (DepositoryFirst depositoryFirst : depositoryFirstList) {
                depositoryNameMap.put(depositoryFirst.getId(), depositoryFirst.getDepFirstName());
            }
        }
        model.addAttribute("shoppingRefund", shoppingRefund);
        model.addAttribute("enumShopRefundMap", EnumShoppingRefundStatus.toMap());
        model.addAttribute("enumShoppingRefundMap", EnumShoppingRefund.toMap());
        model.addAttribute("depositoryNameMap", depositoryNameMap);
        return "/stock/refund/add_shopping_refunds";
    }

    /**
     * 查询shoppingRefundList页面 主表
     *
     * @return "success" if no exceptions thrown
     */
    @RequestMapping(value = "/stock/refund/search_shopping_refund_list")
    public String searchShoppingRefundList(
    		@ModelAttribute("shoppingRefundSearchQuery")ShoppingRefundSearchQuery shoppingRefundSearchQuery,
    		@RequestParam(value = "isFirst", required = false, defaultValue = "false") String isFirst,
    		@RequestParam(value = "page", required = false, defaultValue = "1") int currPage,
    		Model model,
    		AdminAgent adminAgent
    	) throws Exception {

        if("true".equals(isFirst)){
        	String gmtCreateStart = shoppingRefundSearchQuery.getRefTimeStart();
            String gmtCreateEnd = shoppingRefundSearchQuery.getRefTimeEnd();
	        if (StringUtil.isBlank(gmtCreateStart) && StringUtil.isBlank(gmtCreateEnd)) {
	            gmtCreateStart = DateUtil.getDiffDate(new Date(), -30);
	            shoppingRefundSearchQuery.setRefTimeStart(gmtCreateStart);
	            gmtCreateEnd = DateUtil.getDateToString(new Date());
	            shoppingRefundSearchQuery.setRefTimeEnd(gmtCreateEnd);
	        }
        }

        shoppingRefundSearchQuery.setDepfirstIds(this.getDepfirstIdForQuery(adminAgent));

        QueryPage query = null;
            query = shoppingRefundService.getShoppingRefundListByParameterMap(shoppingRefundSearchQuery ,currPage ,pageSize);

        List<ShoppingRefund> shoppingRefundList = (List<ShoppingRefund>)query.getItems();
        if (shoppingRefundList != null && shoppingRefundList.size() > 0) {
            Iterator iter = shoppingRefundList.iterator();

            while (iter.hasNext()) {
                StringBuffer stringbuff = new StringBuffer();
                ShoppingRefund sr = (ShoppingRefund) iter.next();
                long id = sr.getId();
                List<ShoppingRefundSearch> shoppingRefundSearchList = shoppingRefundService.getShoppingRefundNum(id);
                if ((shoppingRefundSearchList != null) && (shoppingRefundSearchList.size() > 0)) {
                    Iterator iters = shoppingRefundSearchList.iterator();
                    while (iters.hasNext()) {
                        stringbuff.append(((ShoppingRefundSearch) iters.next()).getShoppingNum())
                            .append(";");
                    }
                    stringbuff.delete(stringbuff.length() - 1, stringbuff.length());
                    sr.setShoppingNum(stringbuff.toString());
                }
            }
        }
//
        Map depositoryNameMap = new HashMap();
        List<DepositoryFirst> depositoryFirstList = depositoryFirstManager.getDepositoryFirstList();
        for (DepositoryFirst depositoryFirst : depositoryFirstList) {
            depositoryNameMap.put(depositoryFirst.getId(), depositoryFirst.getDepFirstName());
        }
        // 取得管理员权限的会员列表
        // userLists = userManager.getUserByIsAdmin(1);
        // TODO 这里获取了后台所有用户，这个以后可以按照客户需求来改掉

        List<Admin> userList = adminService.getAdminUserList();
        model.addAttribute("userList", userList);


        model.addAttribute("enumShoppingRefundMap", EnumShoppingRefund.toMap());
        model.addAttribute("enumShopRefundMap", EnumShoppingRefundStatus.toMap());

        model.addAttribute("queryObject", shoppingRefundSearchQuery);
        model.addAttribute("query", query);

        model.addAttribute("depositoryNameMap", depositoryNameMap);

        return "/stock/refund/search_shopping_refund";
    }

    /**
     * 根据退货主表id查询shoppingRefundDetail详细页面
     *
     * @return "success" if no exceptions thrown
     */
    @RequestMapping(value = "/stock/refund/search_sr_detail_list")
    public String searchSRDetailList(
    			@RequestParam(value = "shoppingRefId", required = false, defaultValue = "") String shoppingId,
    			@RequestParam(value = "refNum", required = false, defaultValue = "") String refNum,
    			@RequestParam(value = "message", required = false, defaultValue = "") String message,
    			HttpServletRequest request,
    			Model model
    ) throws Exception {

        String shoppingNum = "";
        if (StringUtil.isEmpty(shoppingId)) {
            Map<String, String> paramMap = new HashMap<String, String>();
            paramMap.put("refNum", refNum);
            ShoppingRefund shoppingRd = shoppingRefundService.getShoppingRefunds(paramMap);
            if (null != shoppingRd) {
                shoppingId = String.valueOf(shoppingRd.getId());
            }
        }

        Map<Long,String> depositoryNameMap = new HashMap<Long,String>();
        ShoppingRefund shoppingRefund = shoppingRefundService.getShoppingRefund(Long.parseLong(shoppingId));
        List<DepositoryFirst> depositoryFirstList = depositoryFirstManager.getDepositoryFirstList();
        for (DepositoryFirst depositoryFirst : depositoryFirstList) {
            depositoryNameMap.put(depositoryFirst.getId(), depositoryFirst.getDepFirstName());
        }

        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("shopRefId", Long.parseLong(shoppingId));
        List<ShoppingRefundDetail> shoppingRefundDetailList = shoppingRefundDetailService.getRefundDetail(paramMap);
        Set<String> shoppingNumSet = new HashSet<String>();
        for (ShoppingRefundDetail tmp : shoppingRefundDetailList) {
            shoppingNumSet.add(tmp.getShoppingNum());
            shoppingNum = tmp.getShoppingNum();
            model.addAttribute("shoppingNumSet", shoppingNumSet);
        }
        
        double sumFactFee = 0;
        double sumDueFee = 0;
        int sumRefNum = 0;
        int sumDamagedNum = 0;
        for (ShoppingRefundDetail obj : shoppingRefundDetailList) {
            sumFactFee += obj.getFactFee();
            sumDueFee += obj.getDueFee();
            sumRefNum += obj.getRefNum();
            sumDamagedNum += obj.getDamagedNum();
        }

        if(null != message)
        {
        	model.addAttribute("message", message);
        }

        model.addAttribute("aType", request.getParameter("aType"));
        model.addAttribute("cType", request.getParameter("cType"));
        model.addAttribute("sumFactFee", sumFactFee);
        model.addAttribute("sumDueFee", sumDueFee);
        model.addAttribute("sumRefNum", sumRefNum);
        model.addAttribute("sumDamagedNum", sumDamagedNum);
        model.addAttribute("shoppingNum", shoppingNum);

        model.addAttribute("depositoryNameMap", depositoryNameMap);
        model.addAttribute("detailList",shoppingRefundDetailList);
        model.addAttribute("shoppingRefund", shoppingRefund);

        model.addAttribute("enumShopRefundMap", EnumShoppingRefundStatus.toMap());
        model.addAttribute("enumShoppingRefundRMap", EnumShoppingRefundReason.toMap());
        model.addAttribute("enumShoppingRefundMap", EnumShoppingRefund.toMap());

        model.addAttribute("shoppingRefundDetailList", shoppingRefundDetailList);

        return "/stock/refund/add_shopping_refunds";
    }

    /**
     * （库存退货）根据退货主表id查询shoppingRefundDetail详细页面
     *
     * @return "success" if no exceptions thrown
     */
    @RequestMapping("/storage/searchKTDetail")
    public String searchKTDetailList(HttpServletRequest request, Model model) throws Exception {

        String srefundId = request.getParameter("shoppingRefId");
        if (StringUtil.isEmpty(srefundId)) {
            String refNum = request.getParameter("refNum");
            Map<String, String> parMap = new HashMap<String, String>();
            parMap.put("refNum", refNum);
            ShoppingRefund shoppingRd = shoppingRefundService.getShoppingRefunds(parMap);
            if (null != shoppingRd) {
                srefundId = String.valueOf(shoppingRd.getId());
            }
        }
        List<DepositoryFirst> depositoryFirstList = depositoryFirstManager.getDepositoryFirstList();
        Map<Long,String> depositoryNameMap = new HashMap<Long,String>();
        for (DepositoryFirst depositoryFirst : depositoryFirstList) {
            depositoryNameMap.put(depositoryFirst.getId(), depositoryFirst.getDepFirstName());
        }
        Map<String, Object> paramMap = new HashMap<String, Object>();
        ShoppingRefund shoppingRefund = shoppingRefundService.getShoppingRefund(Long.parseLong(srefundId));
        paramMap.put("shopRefId", Long.parseLong(srefundId));
        List<ShoppingRefundDetail> shoppingRefundDetailList = shoppingRefundDetailService.getStorageRefundDetails(paramMap);
        double sumFactFee = 0;
        double sumDueFee = 0;
        int sumRefNum = 0;
        int sumDamagedNum = 0;
        for (ShoppingRefundDetail obj : shoppingRefundDetailList) {
            sumFactFee += obj.getFactFee();
            sumDueFee += obj.getDueFee();
            sumRefNum += obj.getRefNum();
            sumDamagedNum += obj.getDamagedNum();
        }

        model.addAttribute("aType", request.getParameter("aType"));
        model.addAttribute("cType", request.getParameter("cType"));
        model.addAttribute("succFlag", request.getParameter("succFlag"));
        model.addAttribute("sumFactFee", sumFactFee);
        model.addAttribute("sumDueFee", sumDueFee);
        model.addAttribute("sumRefNum", sumRefNum);
        model.addAttribute("sumDamagedNum", sumDamagedNum);
        model.addAttribute("depositoryNameMap", depositoryNameMap);
        model.addAttribute("shoppingRefund", shoppingRefund);
        model.addAttribute("shoppingRefundDetailList", shoppingRefundDetailList);

        model.addAttribute("enumShoppingRefundMap", EnumShoppingRefund.toMap());
        model.addAttribute("enumShopRefundMap", EnumShoppingRefundStatus.toMap());
        model.addAttribute("enumShoppingRefundRMap", EnumShoppingRefundReason.toMap());
        return "/storage/addShoppingRefunds";
    }

    /**
     * 打印退货单
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/stock/refund/printRefundBill")
    public String printRefundBill(HttpServletRequest request, Model model) throws Exception {
        String id = request.getParameter("id");// 退货单ID
        String type = request.getParameter("type");// “ct”:采购退货打印;"kt":库存退货打印
        String shoppingNum = request.getParameter("shoppingNum");
        String pageNum = request.getParameter("pageNum");//每页显示条数
        String fontSize = request.getParameter("fontSize");//字体大小

        String shoppingRefId = "";
        if (StringUtil.isBlank(pageNum)) {
            pageNum = "8";
        }
        if (StringUtil.isBlank(fontSize)) {
            fontSize = "3";
        }
        if (id != null) {
            shoppingRefId = id;
        }
        // 取得退货单主表信息
        ShoppingRefund shoppingRefund = shoppingRefundService.getShoppingRefund(Long.parseLong(shoppingRefId));
        List<DepositoryFirst> depositoryFirstList = depositoryFirstManager.getDepositoryFirstList();
        Map<Long,String> depositoryNameMap = new HashMap<Long,String>();
        List<ShoppingRefundDetail> shoppingRefundDetailList = null;
        for (DepositoryFirst depositoryFirst : depositoryFirstList) {
            depositoryNameMap.put(depositoryFirst.getId(), depositoryFirst.getDepFirstName());
        }
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("shopRefId", Long.parseLong(shoppingRefId));
        paramMap.put("shoppingNum", shoppingNum);
        if ("refund_storage".equals(type)) {
           shoppingRefundDetailList = shoppingRefundDetailService.getStorageRefundDetails(paramMap);
        } else {
           shoppingRefundDetailList = shoppingRefundDetailService.getRefundDetail(paramMap);
        }
        double sumFactFee = 0;
        double sumDueFee = 0;
        long shoppingId = 0;
        for (ShoppingRefundDetail obj : shoppingRefundDetailList) {
            sumFactFee += obj.getFactFee();
            sumDueFee += obj.getDueFee();
            shoppingId = obj.getShoppingId();
        }
        ShoppingList shoppingList = shoppingListService.getShoppingList(shoppingId);
        if (shoppingList != null) {
            model.addAttribute("shoppingNum", shoppingNum);
            model.addAttribute("shoppingCreater",  shoppingList.getCreater());
        }
        Map<String, String>   enumShoppingRefundMap  = EnumShoppingRefund.toMap();
        model.addAttribute("id", id);
        model.addAttribute("type", type);
        model.addAttribute("shoppingNum", shoppingNum);
        model.addAttribute("pageNum",  Integer.valueOf(pageNum).intValue());
        model.addAttribute("fontSize", Integer.valueOf(fontSize).intValue());
        model.addAttribute("sumFactFee", sumFactFee);
        model.addAttribute("sumDueFee", sumDueFee);
        model.addAttribute("shoppingRefId", shoppingRefId);
        model.addAttribute("shoppingRefund", shoppingRefund);
        model.addAttribute("depositoryNameMap", depositoryNameMap);
        model.addAttribute("shoppingRefundDetailList", shoppingRefundDetailList);
        model.addAttribute("enumShoppingRefundMap", enumShoppingRefundMap);
        model.addAttribute("enumShoppingRefundRMap", EnumShoppingRefundReason.toMap());
        return "/storage/printRefundBill";
    }

    /**
     * excel导出采购退货单明细
     */
    @RequestMapping("/stock/refund/exportShoppingRefund")
    public void exportShoppingRefund(
    		@ModelAttribute("refundDetailSearchQuery")RefundDetailSearchQuery refundDetailSearchQuery,
    		HttpServletResponse res,
    		AdminAgent adminAgent
    	) throws Exception {
        OutputStream outwt = null;
        try {
            Date da = new Date();
            outwt = res.getOutputStream();
            SimpleDateFormat df = new SimpleDateFormat("yyyy_MM_dd");
            String date = df.format(da);
            res.setHeader("Content-disposition", "attachment; filename=shoppingRefund" + date
                                                 + ".xls");
            res.setContentType("application/octet-stream;charset=utf-8");

            refundDetailSearchQuery.setDepfirstIds(getDepfirstIdForQuery(adminAgent));

            Map<Long,String> depositoryNameMap = new HashMap<Long,String>();
//            if (null != tmpMap) {
                QueryPage queryPage = shoppingRefundService
                    .getShoppingRefundDetailSearchRusultByParameterMap(refundDetailSearchQuery, 1,Integer.MAX_VALUE);

                List<ShoppingRefundDetailSearch> shoppingRefundDSList = (List<ShoppingRefundDetailSearch>) queryPage.getItems();
                if(shoppingRefundDSList != null){
                	List<Long> ids = this.getDepfirstIdForQuery(adminAgent);
                if (null != ids && ids.size() > 0) {
                    List<DepositoryFirst> depositoryFirstList = depositoryFirstManager
                        .getDepositoryFirstListByIds(this.getDepfirstIdForQuery(adminAgent));
                    for (DepositoryFirst depositoryFirst : depositoryFirstList) {
                        depositoryNameMap.put(depositoryFirst.getId(), depositoryFirst
                            .getDepFirstName());
                    }
                }
            } else {
                shoppingRefundDSList = new ArrayList<ShoppingRefundDetailSearch>();
            }

            List<String[]> shoppingRefundList = new ArrayList<String[]>();
            String[] title = { "退货单号", "仓库名称", "供应商名称", "采购类型", "退货时间", "产品编码", "产品名称", "类目", "属性",
                    "单位", "退货数量", "单价", "应付金额", "实付金额" };
            shoppingRefundList.add(title);
            Map<String, String> storTypeMap = EnumStorType.toMap();
            for (ShoppingRefundDetailSearch shRefund : shoppingRefundDSList) {

                String[] shoppingRefund = { shRefund.getRefundNum(),
                        depositoryNameMap.get(shRefund.getDepFirstId()),
                        shRefund.getSupplierName(), storTypeMap.get(shRefund.getStorType()),
                        df.format(shRefund.getRefTime()), shRefund.getGoodsCode(),
                        shRefund.getInstanceName(),
                        categoryManager.getCatFullNameByCatcode(shRefund.getCatCode()),
                        attributeManager.getFullAttributeStringByAttrs(shRefund.getAttrs()),
                        shRefund.getUnits(), String.valueOf(shRefund.getRefNum()),
                        String.valueOf(shRefund.getRefPrice()),
                        String.valueOf(shRefund.getDueFee()), String.valueOf(shRefund.getFactFee()) };
                shoppingRefundList.add(shoppingRefund);
            }
            goodsBatch.exportExcel(outwt, shoppingRefundList);
            outwt.flush();
        } catch (Exception e) {
//            request.setAttribute("errorMessage", getText("admin.goods.title.templetpath.fail"));
//            log.error(e);
        } finally {
            outwt.close();
        }
    }

    /**
     * 判断一个退货单中针对同一个采购单只能有对应一种产品
     *
     * @return
     */
    @RequestMapping(value="/stock/refund/validateGoodInstanceId")
    public @ResponseBody String validateGoodInstanceId(
    			HttpServletRequest request
    		) {
        String shopRefId = request.getParameter("param1");
        String shoppingId = request.getParameter("param2");
        String goodsInstanceId = request.getParameter("param3");
        String goodsCode = request.getParameter("param4");
        String total = request.getParameter("param5");
        String receiveNumstr = request.getParameter("param6");
        String existNumstr = request.getParameter("param7");
        int receiveNum = Integer.parseInt(receiveNumstr);
        int existNum = Integer.parseInt(existNumstr);
        String message = "";
        if (null != shoppingId && null != goodsInstanceId && null != shopRefId) {
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("shoppingId", shoppingId);
            paramMap.put("shopRefId", shopRefId);
            paramMap.put("goodsInstanceId", goodsInstanceId);
            int count = shoppingRefundDetailService.getCountRefundDetail(paramMap);
            if (count > 0) {
                message = "产品编码为" + goodsCode + "在数据库中已经存在,不能重复添加";
                return message;
            }
        }
        if (null != shoppingId && null != goodsInstanceId) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("shoppingId", shoppingId);
            map.put("goodsInstanceId", goodsInstanceId);
            map.put("type", EnumShoppingRefund.REFUND_CAIGOU.getKey());// 只统计采购退货的数量
            ShoppingRefundDetail rd = shoppingRefundDetailService.sumRefundDetailByShoppingId(map);
            if (null != rd && StringUtil.isNotEmpty(total)) {
                long refundTotal = Long.parseLong(total) + rd.getRefNum() + rd.getDamagedNum();
                if (receiveNum < refundTotal || existNum < refundTotal) {
                    message = "产品编码为 " + goodsCode + " 的退货数量已经大于库存数或者大于采购验收数量";
                    return message;
                }
            }
        }
        message = "success";
        return message;
    }

    /**
     * 添加shoppingRefundDetail页面 明细表
     *
     * @return "success" if no exceptions thrown
     */
    @RequestMapping(value="/stock/refund/addSRDetails")
    public String addShoppingRefundDetail(
    		HttpServletRequest request
    	) throws Exception {

        String shop_ref_id = request.getParameter("id");
        String[] shoppingIdArray = request.getParameterValues("shoppingId");
        String[] goodsIdArray = request.getParameterValues("goodsId");
        String[] goodsInstanceIdArray = request.getParameterValues("goodsInstanceId");
        // String[] instanceNameArray = request.getParameterValues(
        // "instanceName");
        String[] instanceCodeArray = request.getParameterValues("instanceCode");

        String[] ref_numArray = request.getParameterValues("ref_num");
        String[] damaged_numArray = request.getParameterValues("damaged_num");
        String[] ref_priceArray = request.getParameterValues("ref_price");
        String[] reasonArray = request.getParameterValues("reason");
        String[] remarkArray = request.getParameterValues("remark");
        // String[] receive_numArray =
        // request.getParameterValues("receiveNum");
        String[] unitsArray = request.getParameterValues("units");
        String[] dueFeeArray = request.getParameterValues("due_fee");
        String[] factFeeArray = request.getParameterValues("fact_fee");

        String message = "";

        //库存数量
        String[] existNumArray = request.getParameterValues("existNum");

        for (int i = 0; i < goodsIdArray.length; i++) {
            if (!ref_numArray[i].equals("") || !damaged_numArray[i].equals("")
                || !ref_priceArray[i].equals("")) {

                String shoppingId = shoppingIdArray[i];
                String goodsId = goodsIdArray[i];
                String goodsInstanceId = goodsInstanceIdArray[i];
                String goodsCode = instanceCodeArray[i];
                Long ref_num, damaged_num,exist_num;

                if (StringUtil.isNotEmpty(ref_numArray[i])) {
                    ref_num = Long.parseLong(ref_numArray[i]);
                } else {
                    ref_num = new Long(0);
                }
                if (StringUtil.isNotEmpty(damaged_numArray[i])) {
                    damaged_num = Long.parseLong(damaged_numArray[i]);
                } else {
                    damaged_num = new Long(0);
                }
                if(StringUtil.isNotEmpty(existNumArray[i])){
                	exist_num = Long.parseLong(existNumArray[i]);
                }else{
                	exist_num = new Long(0);
                }
                // String ref_num = ref_numArray[i];
                // String damaged_num = damaged_numArray[i];
                String ref_price = ref_priceArray[i];
                String reason = reasonArray[i];
                String remark = remarkArray[i];
                String units = unitsArray[i];
                // if()
                Double dueFee = Double.parseDouble(ref_price) * (ref_num + damaged_num);
                String dueFeeTemp = dueFeeArray[i];
                String factFee = factFeeArray[i];

                //做库存判断 zhangwy
                long userdNum = 0;
                Map parMap = new HashMap();
                parMap.put("shopRefId", Long.parseLong(shop_ref_id));
                parMap.put("goodsInstanceId", Long.parseLong(goodsInstanceId));
                List<ShoppingRefundDetail> shoppingRefundDetailList = shoppingRefundDetailService.getStorageRefundDetails(parMap);
                if(shoppingRefundDetailList!=null &&shoppingRefundDetailList.size() > 0){
                    for(ShoppingRefundDetail temp :shoppingRefundDetailList){
                    	if(temp.getRefNum()!=null){
                    		userdNum+=temp.getRefNum();
                    	}
                    	if(temp.getDamagedNum()!=null){
                    		userdNum+=temp.getDamagedNum();
                    	}
                    }
                }
                GoodsInstance gi = goodsInstanceManager.getInstance(Long.parseLong(goodsInstanceId));
                if(userdNum + ref_num + damaged_num > exist_num){
                	message = "编号为: "+gi.getCode()+" 的产品累计退货数量超过库存数量，请重新操作！";
                	return "redirect:/stock/refund/search_sr_detail_list.html?shoppingRefId="+shop_ref_id+"&message="+message;
                }

                ShoppingRefundDetail shoppingRefundDetail = new ShoppingRefundDetail();
                shoppingRefundDetail.setGoodsId(Long.parseLong(goodsId));
                shoppingRefundDetail.setGoodsInstanceId(Long.parseLong(goodsInstanceId));
                shoppingRefundDetail.setShopRefId(Long.parseLong(shop_ref_id));
                shoppingRefundDetail.setShoppingId(Long.parseLong(shoppingId));
                shoppingRefundDetail.setLocId(Long.parseLong(shoppingId));
                shoppingRefundDetail.setGoodsCode(goodsCode);
                shoppingRefundDetail.setRefPrice(Double.parseDouble(ref_price));
                shoppingRefundDetail.setRefNum(ref_num);
                shoppingRefundDetail.setDamagedNum(damaged_num);
                if (StringUtil.isNotEmpty(dueFeeTemp)) {
                    shoppingRefundDetail.setDueFee(Double.parseDouble(dueFeeTemp));
                } else {
                    shoppingRefundDetail.setDueFee(dueFee);
                }
                if (StringUtil.isNotEmpty(factFee)) {
                    shoppingRefundDetail.setFactFee(Double.parseDouble(factFee));
                } else {
                    shoppingRefundDetail.setFactFee(dueFee);
                }

                shoppingRefundDetail.setReason(reason);
                shoppingRefundDetail.setRemark(remark);
                shoppingRefundDetail.setUnits(units);

                shoppingRefundDetailService.addShoppingRefundDetail(shoppingRefundDetail);
            }
        }
//        this.setShoppingRefId(shop_ref_id);
        return "redirect:/stock/refund/search_sr_detail_list.html?shoppingRefId="+shop_ref_id;
    }

    /**
     * 查询采购订单表 shoppingRefundDetail页面 明细表
     *
     * @return "success" if no exceptions thrown
     */
    @RequestMapping(value = "/stock/refund/search_shopping_rd")
    public String searchShoppingRefundDetail(
    		@RequestParam("id")String shoppingId,
    		@RequestParam("sn")String shoppingNum,
    		@RequestParam("in")String instanceName,
    		Model model
    	) throws Exception {

        if (StringUtil.isNotBlank(shoppingId)) {
            Map<String, String> paramMap = new HashMap<String, String>();
            paramMap.put("id", shoppingId);
            ShoppingRefund shoppingRefund = shoppingRefundService.getShoppingRefunds(paramMap);
            // 将同一供应商条件加进去
            paramMap.put("shoppingNum", shoppingNum);
            paramMap.put("instanceName", instanceName);
            paramMap.put("supplierId", String.valueOf(shoppingRefund.getSupplierId()));
            paramMap.put("status", "stock_finished");// 状态为完成的
            paramMap.put("depFirstId", shoppingRefund.getDepFirstId().toString());// 状态为完成的

            List<ShoppingDetailSearch> shoppingDetailSearchLists = shoppingListService.getShoppingDetailStorageNum(paramMap);
            List<DepositoryFirst> depositoryFirstList = depositoryFirstManager
                .getDepositoryFirstList();
            Map depositoryNameMap = new HashMap();
            for (DepositoryFirst depositoryFirst : depositoryFirstList) {
                depositoryNameMap.put(depositoryFirst.getId(), depositoryFirst.getDepFirstName());
            }
            if (null != shoppingDetailSearchLists) {
                Iterator iter = shoppingDetailSearchLists.iterator();
                ShoppingDetailSearch shoppingDetailSearch = null;
                while (iter.hasNext()) {
                    shoppingDetailSearch = (ShoppingDetailSearch) iter.next();
                    if (shoppingDetailSearch.getReceiveNum() <= 0)
                        iter.remove();
                }
            }
            model.addAttribute("shoppingDetailSearchLists", shoppingDetailSearchLists);
            model.addAttribute("depositoryNameMap", depositoryNameMap);
        }

	        model.addAttribute("id", shoppingId);
	        model.addAttribute("shoppingNum", shoppingNum);
	        model.addAttribute("instanceName", instanceName);
	        model.addAttribute("enumShoppingRefundRMap", EnumShoppingRefundReason.toMap());

        return "/stock/refund/add_sr_detail";
    }

    /**
     * 删除shoppingRefundDetail页面 明细表
     *
     * @return "success" if no exceptions thrown
     */

    @RequestMapping("/stock/refund/rmSRDetail")
    public String removeShoppingRefundDetail(
    		@RequestParam(value = "shoppingRefId", required = false, defaultValue = "") String shoppingId,
    		@RequestParam(value = "refNum", required = false, defaultValue = "") String refNum,
    		@RequestParam(value = "message", required = false, defaultValue = "") String message,
    		HttpServletRequest request,
    		Model model
    		) throws Exception {
        shoppingRefundDetailService.removeShoppingRefundDetail(Long.parseLong(request.getParameter("refid")));
        return searchSRDetailList(shoppingId,refNum,message,request,model);
    }

    public String editShoppingRefundDetail() throws Exception {

        return "";
    }

    /**
     * 新增shoppingRefundDetail页面跳转初始化
     *
     * @return "success" if no exceptions thrown
     */
    @RequestMapping(value = "/stock/refund/add_sr_detail_page")
    public String addSRDetail(
    		@RequestParam("shoppingId")String shoppingId,
    		@RequestParam("depFirstId")String depFirstId,
    		Model model
    	) throws Exception {
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("id", shoppingId);
        paramMap.put("depFirstId", depFirstId);
        ShoppingRefund shoppingRd = shoppingRefundService.getShoppingRefunds(paramMap);
        //判断已经关闭的退货单,不能出库 zhangwy
        if (EnumShoppingRefundStatus.STOCK_CLOSE.getKey().equalsIgnoreCase(shoppingRd.getStatus())) {
//            this.setErrorMsg(this.getText("ios.shoppingrefund.detail.close"));
            return "redericet:/stock/refund/search_refund_list";
        }
        ShoppingRefund shoppingRefund = new ShoppingRefund();
        shoppingRefund.setId(Long.parseLong(shoppingId));

        model.addAttribute("id", shoppingId);
        model.addAttribute("status", shoppingRefund.getStatus());

        return "/stock/refund/add_sr_detail";
    }

    /**
     * 生成出库单
     *
     * @return
     */
    @RequestMapping("addOutDepository")
    public String addOutDepository(
    		HttpServletRequest request,
    		AdminAgent adminAgent,
    		Model model
    	)throws Exception {
        Date gmtOutDep = new Date();
        String shoppingId = request.getParameter("shoppingRefId");
        // if(StringUtil.isNotEmpty(shoppingId) ){
        // shoppingId = getRequest().getParameter("id");
        // }

        String message = "";
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("id", shoppingId);
        ShoppingRefund shoppingRd = shoppingRefundService.getShoppingRefunds(paramMap);
        List<ShoppingRefundDetail> shoppingList = shoppingRefundDetailService
            .getShoppingRefundDetailsId(Long.parseLong(shoppingId));

        if (null == shoppingList || shoppingList.size() == 0) {
//            message = getText("ios.shoppingrefund.detail.isnull");
            model.addAttribute("message", "ios.shoppingrefund.detail.isnull");
            return "redirect:/stock/refund/search_sr_detail_list.html?shoppingRefId="+shoppingId;
        }
        // 判断已经生成出库单了的刚不能再生成
        if (EnumShoppingRefundStatus.STOCK_FINISHED.getKey().equalsIgnoreCase(
            shoppingRd.getStatus())) {
//            message = this.getText("ios.shoppingrefund.repeat.outdepository");
            model.addAttribute("message", "ios.shoppingrefund.repeat.outdepository");
            return "redirect:/stock/refund/search_sr_detail_list.html?shoppingRefId="+shoppingId;
        }
        //判断已经关闭的退货单,不能出库 zhangwy
        if (EnumShoppingRefundStatus.STOCK_CLOSE.getKey().equalsIgnoreCase(shoppingRd.getStatus())) {
//            message = this.getText("ios.shoppingrefund.close.outdepository");
            model.addAttribute("message", "ios.shoppingrefund.repeat.outdepository");
            return "redirect:/stock/refund/search_sr_detail_list.html?shoppingRefId="+shoppingId;
        }

        //zhangwy 获取一级仓库信息
        DepositoryFirst depositoryFirst = depositoryFirstManager.getDepositoryById(shoppingRd.getDepFirstId());

        OutDepository out = new OutDepository();
        out.setBillNum(codeManager.buildCodeByDateAndType(CodeManager.CHUKU_CODE, 6, CHUKU));
        out.setType(EnumOutDepository.OUT_SHOPPING.getKey());
        out.setRelationNum(shoppingRd.getRefNum());
        out.setCreater(adminAgent.getUsername());
        out.setStatus(EnumOutStatus.OUT_NEW.getKey());
        out.setGmtCreate(gmtOutDep);
        out.setDepFirstId(shoppingRd.getDepFirstId());

        List<OutDetail> outDetails = new ArrayList<OutDetail>();

        for (ShoppingRefundDetail shList : shoppingList) {
            OutDetail outDetail = new OutDetail();
            ShoppingList shopListObj = shoppingListService.getShoppingList(shList.getShoppingId());
            if (shopListObj != null) {
                outDetail.setRelationNum(shopListObj.getShoppingNum());
                outDetail.setStorType(shopListObj.getStorType());//实际， 暂估
            }
            outDetail.setGoodsId(shList.getGoodsId());
            outDetail.setGoodsInstanceId(shList.getGoodsInstanceId());

            long outnum = 0;
            if (null != shList.getRefNum()) {
                outnum = shList.getRefNum();
            }
            if (null != shList.getDamagedNum()) {
                outnum = outnum + shList.getDamagedNum();
            }
            outDetail.setOutNum(outnum);
            if (null != shList.getRefPrice()) {
                outDetail.setUnitPrice(shList.getRefPrice());
            } else {
                outDetail.setUnitPrice(0);
            }
            if (null != shList.getDueFee()) {
                outDetail.setDueFee(shList.getDueFee());
            } else {
                outDetail.setDueFee(0);
            }
            if (null != shList.getFactFee()) {
                outDetail.setFactFee(shList.getFactFee());
            } else {
                outDetail.setFactFee(0);
            }
            outDetail.setStatus(EnumOutDetailStatus.OUT_NEW.getKey());
            outDetail.setDepFirstId(shoppingRd.getDepFirstId());

            //          判断可用库存
            AvailableStock availableStockTmp = availableStockDao.getAvailableStock(outDetail
                .getGoodsInstanceId(), outDetail.getDepFirstId());
            if (availableStockTmp != null) {
                if (availableStockTmp.getGoodsNumber().longValue() < outDetail.getOutNum()) {
                    GoodsInstance gi = (GoodsInstance) goodsInstanceDao.getInstance(outDetail
                        .getGoodsInstanceId());
//                    message = gi.getInstanceName()
//                                     + this.getText("ios.availablestock.cannotupdate");
                    model.addAttribute("message", "ios.availablestock.cannotupdate");
                 return "redirect:/stock/refund/search_sr_detail_list.html?shoppingRefId="+shoppingId;
                }
            }
            outDetails.add(outDetail);

        }

        Long outId = outDepositoryDao.addOutDepository(out);
        for (OutDetail o : outDetails) {
            o.setOutDepositoryId(outId);
            outDetailDao.addOutDetail(o);
            // 出库后相应的减掉商品表，产品表中的库存
            if(depositoryFirst!=null&&(!depositoryFirst.getType().equals("w"))){
                goodsInstanceManager.updateAmountForTwo(o.getGoodsInstanceId(), o.getGoodsId(), 0 - o
                        .getOutNum(), shoppingRd.getDepFirstId(),true);
            }
        }

        request.setAttribute("shoppingRefId", shoppingId);
        if (null != shoppingList && null != shoppingRd) {
            shoppingRd.setStatus(EnumShoppingRefundStatus.STOCK_FINISHED.getKey());
            shoppingRefundService.editShoppingRefund(shoppingRd);
        }
//        model.addAttribute("message", message);
        return "redirect:/stock/refund/search_sr_detail_list.html?shoppingRefId="+shoppingId;
    }

    /**
     * 采购退货订单明细查询
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/stock/refund/refund_detail_search")
    public String refundDetailSearch(
    		@ModelAttribute("refundDetailSearchQuery")RefundDetailSearchQuery refundDetailSearchQuery,
    		@RequestParam(value = "page", required = false, defaultValue = "1") int currPage,
    		Model model,
    		AdminAgent adminAgent) throws Exception {
        String gmtCreateStart = refundDetailSearchQuery.getStartTime();
        String gmtCreateEnd = refundDetailSearchQuery.getEndTime();
        if (StringUtil.isBlank(gmtCreateStart) && StringUtil.isBlank(gmtCreateEnd)) {
            gmtCreateStart = DateUtil.getDiffDate(new Date(), -30);
            gmtCreateEnd = DateUtil.getDateToString(new Date());
            refundDetailSearchQuery.setStartTime(gmtCreateStart);
            refundDetailSearchQuery.setEndTime(gmtCreateEnd);
        }

        List<Long> depFirstIdsList = getDepfirstIdForQuery(adminAgent);
        if(depFirstIdsList != null){
        	refundDetailSearchQuery.setDepfirstIds(depFirstIdsList);
        }
        QueryPage query = shoppingRefundService.getShoppingRefundDetailSearchRusultByParameterMap(refundDetailSearchQuery,currPage,pageSize);

        Map<Long,String> depositoryNameMap = new HashMap<Long,String>();
        List<DepositoryFirst> depositoryFirstList = depositoryFirstManager.getDepositoryFirstListByIds(depFirstIdsList);
        for (DepositoryFirst depositoryFirst : depositoryFirstList) {
            depositoryNameMap.put(depositoryFirst.getId(), depositoryFirst.getDepFirstName());
        }

        QueryPage supplierQueryPage = supplierService.searchSupplierListWithPage(new Supplier(), 1,Integer.MAX_VALUE);

        //一级仓库列表
        model.addAttribute("depFirstList", depositoryFirstList);
        model.addAttribute("depositoryNameMap", depositoryNameMap);
        model.addAttribute("supplierList", supplierQueryPage.getItems());

        model.addAttribute("storTypeMap", EnumStorType.toMap());


        model.addAttribute("queryObject", refundDetailSearchQuery);
        model.addAttribute("query", query);

        model.addAttribute("categoryManager", categoryManager);
        model.addAttribute("attributeManager", attributeManager);

        return "/stock/refund/refund_detail_search";
    }

    /**
     * 采购退货订单汇总excel导出
     *
     * @throws Exception
     */
    @RequestMapping(value = "/stock/refund/export_refund_detail")
    public void doExportRefundDetail(
    		@ModelAttribute("refundDetailSearchQuery")RefundDetailSearchQuery refundDetailSearchQuery,
			HttpServletResponse res,
			AdminAgent adminAgent
    ) throws Exception {
            OutputStream outwt = null;
            try {
                Date da = new Date();
                outwt = res.getOutputStream();
                SimpleDateFormat df = new SimpleDateFormat("yyyy_MM_dd");
                String date = df.format(da);
                res.setHeader("Content-disposition", "attachment; filename=shoppingRefundDetail" + date
                                                     + ".xls");
                res.setContentType("application/octet-stream;charset=utf-8");

                List<Long> depfirstIdsList = getDepfirstIdForQuery(adminAgent);
                if(depfirstIdsList != null){
                	refundDetailSearchQuery.setDepfirstIds(depfirstIdsList);
                }

                Map<Long, String> depositoryNameMap = new HashMap<Long, String>();

                QueryPage query = shoppingRefundService
                        .getShoppingRefundGatherSearchRusultByParameterMap(refundDetailSearchQuery, 1,Integer.MAX_VALUE);

                List<DepositoryFirst> depositoryFirstList = depositoryFirstManager
                            .getDepositoryFirstListByIds(depfirstIdsList);
                        for (DepositoryFirst depositoryFirst : depositoryFirstList) {
                            depositoryNameMap.put(depositoryFirst.getId(), depositoryFirst
                                .getDepFirstName());
                        }

                List<String[]> shoppingRefundList = new ArrayList<String[]>();

                String[] title = { "产品编码", "仓库名称", "产品名称", "采购类型", "类目", "属性", "单位", "退货数量", "应付金额",
                        "实付金额" };
                shoppingRefundList.add(title);

                for (ShoppingRefundGatherSearch shRefund : (List<ShoppingRefundGatherSearch>) query.getItems()) {

                    String[] data = { shRefund.getGoodsCode(),
                            depositoryNameMap.get(shRefund.getDepFirstId()),
                            shRefund.getInstanceName(), EnumStorType.toMap().get(shRefund.getStorType()),
                            categoryManager.getCatFullNameByCatcode(shRefund.getCatCode()),
                            attributeManager.getFullAttributeStringByAttrs(shRefund.getAttrs()),
                            shRefund.getUnits(), String.valueOf(shRefund.getRefNum()),
                            String.valueOf(shRefund.getDueFee()), String.valueOf(shRefund.getFactFee()) };
                    shoppingRefundList.add(data);
                }
                goodsBatch.exportExcel(outwt, shoppingRefundList);
                outwt.flush();
            } catch (Exception e) {
//                request.setAttribute("errorMessage", getText("admin.goods.title.templetpath.fail"));
//                log.error(e);
            	System.out.println(e.toString());
            } finally {
                outwt.close();
            }

        }

    /**
     * 采购退货单汇总查询
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/stock/refund/refund_gather_search")
    public String refundGatherSearch(
    		@ModelAttribute("refundDetailSearchQuery")RefundDetailSearchQuery refundDetailSearchQuery,
    		@RequestParam(value = "page", required = false, defaultValue = "1") int currPage,
    		Model model,
    		AdminAgent adminAgent) throws Exception {

    	String gmtCreateStart = refundDetailSearchQuery.getStartTime();
        String gmtCreateEnd = refundDetailSearchQuery.getEndTime();

        if (StringUtil.isBlank(gmtCreateStart) && StringUtil.isBlank(gmtCreateEnd)) {
            gmtCreateStart = DateUtil.getDiffDate(new Date(), -30);
            gmtCreateEnd = DateUtil.getDateToString(new Date());
            refundDetailSearchQuery.setStartTime(gmtCreateStart);
            refundDetailSearchQuery.setEndTime(gmtCreateEnd);
        }

        List<Long> depfirstIdsList = getDepfirstIdForQuery(adminAgent);

        if(depfirstIdsList != null){
        	refundDetailSearchQuery.setDepfirstIds(depfirstIdsList);
        }

	    QueryPage query = shoppingRefundService
	        .getShoppingRefundGatherSearchRusultByParameterMap(refundDetailSearchQuery , currPage, pageSize);

	    List<DepositoryFirst> depositoryFirstList = null;
	    Map<Long,String> depositoryNameMap = new HashMap<Long,String>();
        if (null != depfirstIdsList && depfirstIdsList.size() > 0) {
        	depositoryFirstList = depositoryFirstManager.getDepositoryFirstListByIds(depfirstIdsList);
            for (DepositoryFirst depositoryFirst : depositoryFirstList) {
                depositoryNameMap.put(depositoryFirst.getId(), depositoryFirst.getDepFirstName());
            }
        } else {
            depositoryFirstList = new ArrayList<DepositoryFirst>();
        }

        QueryPage supplierQueryPage = supplierService.searchSupplierListWithPage(new Supplier(), 1,Integer.MAX_VALUE);



        //一级仓库列表
        model.addAttribute("depFirstList", depositoryFirstList);
        model.addAttribute("depositoryNameMap", depositoryNameMap);

        model.addAttribute("supplierList", supplierQueryPage.getItems());
        model.addAttribute("storTypeMap", EnumStorType.toMap());

        model.addAttribute("query", query);
        model.addAttribute("queryObject", refundDetailSearchQuery);

        model.addAttribute("attributeManager", attributeManager);
        model.addAttribute("categoryManager", categoryManager);

        return "/stock/refund/refund_gather_search";
    }

    /**
     * 关闭退货单 zhangwy
     * @return
     */
    @RequestMapping(value = "/stock/refund/close_dwr_shopping_refund")
    public @ResponseBody String closeDwrShoppingRefund(
    		@RequestParam("param")String id,
    		Model model,
    		AdminAgent adminAgent
    		) throws Exception{
        ShoppingRefund shoppingRefund = shoppingRefundService.getShoppingRefund(Long.parseLong(id));
        String message = "";
        if (!(shoppingRefund.getStatus().equals(EnumShoppingRefundStatus.STOCK_NEW.getKey()))) {
            message = "statuswrong";
            return message;
        }
        if (!(adminAgent.getUsername().equals(shoppingRefund.getCreater()))) {
            message = "userwrong";
            return message;
        }
        //更改状态,并取消下面的退货单详细信息
        shoppingRefund.setStatus(EnumShoppingRefundStatus.STOCK_CLOSE.getKey());
        List<ShoppingRefundDetail> shoppingRefundDetailList = shoppingRefundDetailService
            .getShoppingRefundDetailByShoppingRefundId(shoppingRefund.getId());
        for (ShoppingRefundDetail tmp : shoppingRefundDetailList) {
            shoppingRefundDetailService.removeShoppingRefundDetail(tmp.getId());
        }
        shoppingRefundService.editShoppingRefund(shoppingRefund);
        message = "success";
        return message;
    }

}