package com.huaixuan.network.web.action.storage;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import com.huaixuan.network.biz.domain.goods.Goods;
import com.huaixuan.network.biz.domain.storage.DepLocation;
import com.huaixuan.network.biz.domain.storage.Depository;
import com.huaixuan.network.biz.domain.storage.DepositoryFirst;
import com.huaixuan.network.biz.domain.storage.InDepository;
import com.huaixuan.network.biz.domain.storage.Storage;
import com.huaixuan.network.biz.domain.storage.query.StorageQuery;
import com.huaixuan.network.biz.enums.EnumAdminPermission;
import com.huaixuan.network.biz.enums.EnumDepositoryType;
import com.huaixuan.network.biz.enums.EnumShoppingRefundReason;
import com.huaixuan.network.biz.enums.EnumStorType;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.service.goods.AttributeManager;
import com.huaixuan.network.biz.service.goods.CategoryManager;
import com.huaixuan.network.biz.service.goods.GoodsBatchManager;
import com.huaixuan.network.biz.service.goods.GoodsManager;
import com.huaixuan.network.biz.service.storage.DepLocationManager;
import com.huaixuan.network.biz.service.storage.DepositoryFirstManager;
import com.huaixuan.network.biz.service.storage.DepositoryService;
import com.huaixuan.network.biz.service.storage.StorageManager;
import com.huaixuan.network.biz.service.storage.StorageRefundApplyManager;
import com.huaixuan.network.common.util.DoubleUtil;
import com.huaixuan.network.web.action.BaseAction;
import com.huaixuan.network.web.action.interceptor.AdminAccess;
import com.hundsun.network.melody.common.util.StringUtil;

@Controller
@RequestMapping(value = "/storage" )
public class StorageAction extends BaseAction {

    private static final long      serialVersionUID = 7308220377080047045L;
    // Springע��
    @Autowired
    CategoryManager        categoryManager;
    @Autowired
    StorageManager         storageManager;
    @Autowired
    AttributeManager       attributeManager;
    @Autowired
    DepositoryFirstManager depositoryFirstManager;
    @Autowired
    DepositoryService      depositoryService;
    @Autowired
    GoodsBatchManager      goodsBatch;
    @Autowired
    DepLocationManager    depLocationManager;
    @Autowired
    GoodsManager           goodsManager;
    @Autowired
    StorageRefundApplyManager storageRefundApplyManager;

    protected Log  log = LogFactory.getLog(this.getClass());

//    private DepLocationManager     depLocationManager;
//    private GoodsBatchManager      goodsBatch;
//    private ProdRelationInManager  prodRelationInManager;
//    private ShoppingListManager    shoppingListManager;
//    private AdminManager           adminManager;
//    private Long                   shoppingRefId;
//    private String                 aType;
//    private String                 cType;
//    private RoleManager            roleManager;
//    private AuthorityManager       authorityManager;
//    private RefundManager          refundManager;

//    private GoodsInstanceManager   goodsInstanceManager;
//    private SupplierManager supplierManager;
//    //�Ƿ���ʾ��ҳ��
//    private Boolean                disPage;
//    private GoodsManager           goodsManager;
//    private String                 applyRelationNum;
//    private StorageRefundApplyManager storageRefundApplyManager;
//
//    // ����
//    private InDepository             inDepositoryDispaly;
//    private Map<String, String>      inDepositoryTypeMap      = EnumInDepository.toMap();
//    private Map<String, String>      inDepositoryStatusMap    = EnumInStatus.toMap();
//    private Map<Long, String>        depMap                   = new HashMap<Long, String>();
//    private Map<String, String>      enumShoppingRefundReason = EnumShoppingRefundReason.toMap();
//    private Page                     page;
//    private List<InDepository>       inDepositoryDisLists;
//    private List<User>               userLists;
//    private List<InDetailGoods>      inDetailGoodsLists;
//    private List<GatherInDepository> gatherInDepositoryLists;
//    private List<GoodsForLocation>   goodsForLocationLists;
//    private String                   optId;
//    private String                   optType;
//    private String                   dfi;
//    private String                   selectDepId;
//    private String                   errorInfo;
//    private Boolean                  succFlag                 = Boolean.FALSE;
//    private String                   iId;
//    private Date                     currDate;
//    private InDetailBaseInfo         inDetailBaseInfo;
//    private List<Storage>            storageList;
    private List<Depository>         depositoryList;
//    private Boolean                  notAllowEdit;
//    private List<DepLocation>        depLocationInfoList;
//    private List<GoodsForLocation>   inDetailInfoForDisList;
//    private List<ProdRelationIn>     prodRelationInLists;
    private List<DepLocation>        depLocationLists;
    private List<DepLocation>        depLocationListInit;
//    private List<Category>           catList;                                                    //һ����Ŀ
//    private List<Category>           twocatList;                                                 //������Ŀ
//    private List<Category>           twocatListInit;                                             //������Ŀ����
//    private Map<String, String>      stockAgeMap              = EnumStockAge.toMap();            //����ö��
//    private Storage                  storage;
    private List<DepositoryFirst>    depositoryFirstList;                                        //һ���ֿ��б�
//    private List<Depository>         selectdeplist;                                              //Json���صĲֿ��б�
//    private List<DepLocation>        selectloclist;
//    private List<GoodsInstance>      goodsInstanceList;
//    //Json���صĿ�λ�б�
//    private Map<String, String>      storTypeMap              = EnumStorType.toMap();
//    private String                   message;

    /**
     * ȡ��List�е�һ���ֿ�ID������һ���ֿ�����
     * @param inDepositoryList List
     * @author chenyan 2010/03/15
     */
    private void setDepFirstNameList(List<InDepository> inDepositoryList) {
        //����id����һ���ֿ����ƣ��ظ���id������ѯ��ֱ����ʾ��һ�λ�õ�����
        Long depFirstId = null;
        String depFirstName = "";
        for (InDepository inDepositoryTmp : inDepositoryList) {
            if (inDepositoryTmp == null || inDepositoryTmp.getDepFirstId() == null) {
                continue;
            }
            if (inDepositoryTmp.getDepFirstId() == depFirstId) {
                inDepositoryTmp.setDepFirstName(depFirstName);
            } else {
                depFirstId = inDepositoryTmp.getDepFirstId();
                if (inDepositoryTmp.getDepFirstId() != null) {
                    DepositoryFirst df = depositoryFirstManager.getDepositoryById(inDepositoryTmp
                        .getDepFirstId());
                    if (df != null) {
                        inDepositoryTmp.setDepFirstName(df.getDepFirstName());
                        depFirstName = df.getDepFirstName();
                    } else {
                        depFirstName = "";
                    }
                }
            }
        }
    }
//
//    /**
//     * ��������ѡ������Ʒ (add by fanyj 2010-10-15) TODO
//     * @return
//     * @throws Exception
//     */
//    public String selectStorageGoodsByMap() throws Exception {
//    	ActionContext context = ActionContext.getContext();
//        HttpServletRequest request = getRequest();
//        String supplierId = request.getParameter("supplierId");//��Ӧ��ID
//        String locId = request.getParameter("locId");//��λID
//        String storType = request.getParameter("storType");//��λID
//
//        parMap.put("supplierId", supplierId);
//        parMap.put("locId", locId);
//        storageList = storageManager.getStoragesByCondition(parMap, null, true);
//        if(storageList != null && storageList.size() > 0){
//        	storage = storageList.get(0);
//        }
//        context.put("supplierId", supplierId);
//        context.put("locId", locId);
//        context.put("storType", storType);
//    	return SUCCESS;
//    }
//
//    /**
//     * ѡ������Ʒ
//     * @return
//     * @throws Exception
//     */
//    public String selectStorageGoods() throws Exception {
//        ActionContext context = ActionContext.getContext();
//        HttpServletRequest request = getRequest();
//        // ��ҳ����ʧ��ѡ����
//        String selectGoodsCount = request.getParameter("selectGoodsCount");
//        String sIds = request.getParameter("selectedIds");
//        String[] goodsIds = request.getParameterValues("sid");
//        List<String> selectList = new ArrayList<String>();
//        Map<String, String> selectMap = new HashMap<String, String>();
//        if (goodsIds != null) {
//            // ������ѡ���
//            for (String goodsId : goodsIds) {
//                sIds += goodsId + ",";
//            }
//            // ����¾�ѡ��Ķ�PUT��MAP��
//            if (sIds != null) {
//                String[] seleIds2 = sIds.split(",");
//                for (String sid : seleIds2) {
//                    selectMap.put(sid, sid);
//                    selectList.add(sid);
//                }
//            }
//
//        }
//        context.put("selectGoodsCount", selectGoodsCount);
//        context.put("selectedIds", sIds == null ? "" : sIds);
//        context.put("selectList", selectList);
//        context.put("selectMap", selectMap);
//
//        //����ȫ����һ���ֿ�ID
//        depositoryFirstList = getDepositoryFirstInit(parMap);
//        if (depositoryFirstList == null||depositoryFirstList.size() == 0) {
//        	return SUCCESS;
//        }
//        if (StringUtil.isNotBlank(parMap.get("depfirstId"))) {
//            depositoryList = getDeplistInit(parMap);
//        }
//        if (StringUtil.isNotBlank(parMap.get("depId"))) {
//            depLocationLists = getLocationsInit(parMap);
//        }
//
//        catList = categoryManager.getCatInfoByDepth(1);
//        twocatList = categoryManager.getCatInfoByDepth(2);
//        if (StringUtil.isNotBlank(parMap.get("catCode"))) {
//            twocatListInit = categoryManager.getRightChildInfoOfTheParent(parMap.get("catCode"));
//        }
//        // ���δ�ҳ�治���в�ѯ
//        if("true".equals(this.getRequest().getParameter("isSearch"))){
//        	page = new Page();
//            page.setPageSize(pageSize);
//            page.setCurrentPage(currentPage);
//            parMap.put("conditionTwo", "conditionTwo"); //��conditionOne:û������Ŀ conditionTwo��������Ŀ
////            parMap.put("depType", EnumDepositoryType.COMMON_DEP.getKey());// ֻ��ѯ��ͨ���е���Ʒ
//            if (StringUtil.isNotBlank(parMap.get("twoCatCode"))) {
//                {
//                    List<Category> tempCategoryList = new ArrayList<Category>();
//                    tempCategoryList = categoryManager.getRightChildInfoOfTheParent(parMap
//                        .get("twoCatCode"));
//                    if (tempCategoryList.isEmpty()) {
//                        parMap.put("conditionOne", "conditionOne");
//                        parMap.remove("conditionTwo");
//                    }
//                }
//            }
//        	storageList = storageManager.getStoragesByCondition(parMap, page, false);
//        }
//        return SUCCESS;
//    }

    /***************************************************************************
     * ����ѯ
     *
     * @return String �ɹ����
     * @throws Exception
     * @author xieyx 2009/07/24
     */
	@SuppressWarnings("unchecked")
	@AdminAccess({EnumAdminPermission.A_STORAGE_VIEW_USER, EnumAdminPermission.A_STORAGE_DEPOTVIEW_USER})
	@RequestMapping(value = "/list_storage")
	public String searchStorage(
			@ModelAttribute("storageQuery") StorageQuery storageQuery,
			@RequestParam(value = "page", required = false, defaultValue = "1") int currPage,
			AdminAgent adminAgent, Model model) throws Exception {

		// ����ȫ����һ���ֿ�ID
     	List<DepositoryFirst> depositoryFirstList = depositoryFirstManager.getDepositoryFirstListByIds(getDepfirstIdForQuery(adminAgent));
 		if (depositoryFirstList == null || depositoryFirstList.size() == 0) {
 			model.addAttribute("message", "û��һ���ֿ�Ȩ��");
 			return "/error";
 		}else{
 			model.addAttribute("depositoryFirstList", depositoryFirstList);
 			if (StringUtils.isNotBlank(storageQuery.getDepfirstId())
 					&& StringUtils.isNumeric(storageQuery.getDepfirstId())) {
 				model.addAttribute("depositoryList", depositoryService.getDeplistByFirstDepId(Long.valueOf(storageQuery.getDepfirstId())));
 			}
 			if (StringUtils.isNotBlank(storageQuery.getDepId())
 					&& StringUtils.isNumeric(storageQuery.getDepId())) {
 				model.addAttribute("depLocationLists", depLocationManager.getRightLocationsByDepositoryId(Long.parseLong(storageQuery.getDepId())));
 			}
 			storageQuery.setDepfirstIds(getDepfirstIdForQuery(adminAgent));
 		}
		model.addAttribute("catList", categoryManager.getCatInfoByDepth(1));
		model.addAttribute("twocatList", categoryManager.getCatInfoByDepth(2));
		model.addAttribute("categoryManager", categoryManager);
		model.addAttribute("attributeManager", attributeManager);
		if (StringUtil.isNotBlank(storageQuery.getCatCode())) {
			model.addAttribute("twocatListInit", categoryManager.getRightChildInfoOfTheParent(storageQuery.getCatCode()));
		}
		// ��conditionOne:û������Ŀ conditionTwo��������Ŀ
		storageQuery.setConditionTwo("conditionTwo");
		if (StringUtil.isNotBlank(storageQuery.getTwoCatCode())) {
			{
				List<Category> tempCategoryList = new ArrayList<Category>();
				tempCategoryList = categoryManager.getRightChildInfoOfTheParent(storageQuery.getTwoCatCode());
				if (tempCategoryList.isEmpty()) {
					storageQuery.setConditionOne("conditionOne");
					storageQuery.setConditionTwo(null);
				}
			}
		}
		model.addAttribute("query", storageManager.getStoragesByCondition(storageQuery, currPage, pageSize, true, true));
		model.addAttribute("storage", storageManager.getStorageAmountByCondition(storageQuery));

		return "/storage/list_storage";
	}

    /**
     * ��浼��
     *
     * @return String �ɹ����
     * @throws Exception
     * @author xieyx 2009/07/25
     */
    @SuppressWarnings("unchecked")
	@RequestMapping(value = "/do_export_storage" )
    public String exportStorage(
    		@ModelAttribute("storageQuery") StorageQuery storageQuery,
    		AdminAgent adminAgent,@RequestParam(value = "page", required = false, defaultValue = "1") int currPage,
    		HttpServletResponse response, Model model) throws Exception {

        OutputStream outwt = null;
        try {
            Date da = new Date();
            outwt = response.getOutputStream();
            SimpleDateFormat df = new SimpleDateFormat("yyyy_MM_dd");
            String date = df.format(da);
            response.setHeader("Content-disposition", "attachment; filename=storage" + date + ".xls");
            response.setContentType("application/octet-stream;charset=utf-8");

    		// ����ȫ����һ���ֿ�ID
         	List<DepositoryFirst> depositoryFirstList = depositoryFirstManager.getDepositoryFirstListByIds(getDepfirstIdForQuery(adminAgent));
     		if (depositoryFirstList == null || depositoryFirstList.size() == 0) {
     			model.addAttribute("message", "û��һ���ֿ�Ȩ��");
     			return "/error";
     		}else{
     			model.addAttribute("depositoryFirstList", depositoryFirstList);
     			if (StringUtils.isNotBlank(storageQuery.getDepfirstId())
     					&& StringUtils.isNumeric(storageQuery.getDepfirstId())) {
     				model.addAttribute("depositoryList", depositoryService.getDeplistByFirstDepId(Long.valueOf(storageQuery.getDepfirstId())));
     			}
     			if (StringUtils.isNotBlank(storageQuery.getDepId())
     					&& StringUtils.isNumeric(storageQuery.getDepId())) {
     				model.addAttribute("depLocationLists", depLocationManager.getRightLocationsByDepositoryId(Long.parseLong(storageQuery.getDepId())));
     			}
     			storageQuery.setDepfirstIds(getDepfirstIdForQuery(adminAgent));
     		}

    		if (StringUtil.isNotBlank(storageQuery.getCatCode())) {
    			model.addAttribute("twocatListInit", categoryManager.getRightChildInfoOfTheParent(storageQuery.getCatCode()));
    		}
    		// ��conditionOne:û������Ŀ conditionTwo��������Ŀ
    		storageQuery.setConditionTwo("conditionTwo");
    		if (StringUtil.isNotBlank(storageQuery.getTwoCatCode())) {
    			{
    				List<Category> tempCategoryList = new ArrayList<Category>();
    				tempCategoryList = categoryManager.getRightChildInfoOfTheParent(storageQuery.getTwoCatCode());
    				if (tempCategoryList.isEmpty()) {
    					storageQuery.setConditionOne("conditionOne");
    					storageQuery.setConditionTwo("");
    				}
    			}
    		}
            // ȫ��ѯ
            if(depositoryFirstList != null && depositoryFirstList.size() > 0){
            	QueryPage queryPage = storageManager.getStoragesByCondition(storageQuery, currPage, pageSize, true, false);
                List<Storage> storageListAll = (List<Storage>)queryPage.getItems();
                List<Object[]> storageExportList = new ArrayList<Object[]>();
                boolean flag = false;

                // Ȩ�� δ���  TODO
                if(adminAgent.havePermission(EnumAdminPermission.A_STORAGE_VIEW_USER)){
                	flag = true;
                }

                if (flag) {
                    String[] title = { "��Ʒ����", "��Ʒ����", "��Ŀ", "����", "��λ", "һ���ֿ�", "�ֿ�", "��λ", "��Ӧ��",
                            "���", "��;���", "���ÿ��", "����", "�ɱ�����", "���ɱ�", "�̳Ǽ�", "������" };
                    storageExportList.add(title);
                } else {
                    String[] title = { "��Ʒ����", "��Ʒ����", "��Ŀ", "����", "��λ", "һ���ֿ�", "�ֿ�", "��λ", "��Ӧ��",
                            "���", "��;���", "���ÿ��", "����" };
                    storageExportList.add(title);
                }

                if (storageListAll != null) {
                    for (Storage tmp : storageListAll) {
                        if (tmp.getStorType()!=null&&tmp.getStorType().equals("v")) {
                            tmp.setStorType("�ݹ�");
                        } else {
                            tmp.setStorType("ʵ��");
                        }
                        if (flag) {
                        	Goods goodsTmp = this.goodsManager.getGoods(tmp.getGoodsId());
                        	if(goodsTmp!=null){
                        	    tmp.setGoodsPrice(goodsTmp.getGoodsPrice());
                        	    tmp.setAgentPrice(goodsTmp.getAgentPrice());
                        	}

                            Object[] data = { tmp.getCode(), tmp.getInstanceName(),
                                    categoryManager.getCatFullNameByCatcode(tmp.getCatCode()),
                                    attributeManager.getFullAttributeStringByAttrs(tmp.getAttrs()),
                                    tmp.getGoodsUnit(), tmp.getDepFirstName() + "",
                                    tmp.getDepositoryName() + "", tmp.getDepLocationName() + "",
                                    tmp.getSupplierName() + "", tmp.getStorageNumSum(),
                                    tmp.getWayNum(), tmp.getInstanceExistNum(),
                                    tmp.getStorType() + "",DoubleUtil.round(tmp.getAveragePrice(), 2),
                                    DoubleUtil.round(tmp.getStorageCost(), 2),DoubleUtil.round(tmp.getGoodsPrice(), 2),
                                    DoubleUtil.round(tmp.getAgentPrice(), 2)};
                            storageExportList.add(data);
                        } else {
                        	Object[] data = { tmp.getCode(), tmp.getInstanceName(),
                                    categoryManager.getCatFullNameByCatcode(tmp.getCatCode()),
                                    attributeManager.getFullAttributeStringByAttrs(tmp.getAttrs()),
                                    tmp.getGoodsUnit(), tmp.getDepFirstName() + "",
                                    tmp.getDepositoryName() + "", tmp.getDepLocationName() + "",
                                    tmp.getSupplierName() + "", tmp.getStorageNumSum(),
                                    tmp.getWayNum(), tmp.getInstanceExistNum(),
                                    tmp.getStorType() + "" };
                            storageExportList.add(data);
                        }
                    }
                }
                goodsBatch.exportExcelByObject(outwt, storageExportList);
                outwt.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(outwt);
        }
         return "/storage/list_storage";
    }

//    /**
//     * ���ݣ������ѯ
//     *
//     * @author zhangwy
//     * @return
//     * @throws Exception
//     */
//    public String searchStockAge() throws Exception {
//        catList = categoryManager.getCatInfoByDepth(1);
//        twocatList = categoryManager.getCatInfoByDepth(2);
//        if (StringUtil.isNotBlank(parMap.get("catCode"))) {
//            twocatListInit = categoryManager.getRightChildInfoOfTheParent(parMap.get("catCode"));
//        }
//        depositoryList = depositoryService.getDepositorys();
//        depLocationLists = depLocationManager.getAllRightLocations();
//        if (StringUtil.isNotBlank(parMap.get("depId"))) {
//            depLocationListInit = depLocationManager.getRightLocationsByDepositoryId(Long
//                .parseLong(parMap.get("depId")));
//        }
//        page = new Page();
//        page.setPageSize(pageSize);
//        page.setCurrentPage(currentPage);
//        parMap.put("conditionTwo", "conditionTwo"); //��conditionOne:û������Ŀ conditionTwo��������Ŀ
//        if (StringUtil.isNotBlank(parMap.get("twoCatCode"))) {
//            {
//                List<Category> tempCategoryList = new ArrayList<Category>();
//                tempCategoryList = categoryManager.getRightChildInfoOfTheParent(parMap
//                    .get("twoCatCode"));
//                if (tempCategoryList.isEmpty()) {
//                    parMap.put("conditionOne", "conditionOne");
//                    parMap.remove("conditionTwo");
//                }
//            }
//        }
//        storage = storageManager.getStorageAmountByCondition(parMap);
//        storageList = storageManager.getStockAgeByCondition(parMap, page);
//        return SUCCESS;
//    }

    /**
     * ���ݣ������ѯ
     *
     * @param out
     * @return ���������б�
     */
    @AdminAccess({EnumAdminPermission.A_ZERO_STORAGE_VIEW_USER})
	@RequestMapping(value = "/list_zero_storage")
    public String searchZeroStorage(
    		@ModelAttribute("storageQuery") StorageQuery storageQuery,
    		@RequestParam(value = "flag", required = false, defaultValue = "") String flag,
			@RequestParam(value = "page", required = false, defaultValue = "1") int currPage,
			Model model) throws Exception {

        model.addAttribute("catList", categoryManager.getCatInfoByDepth(1));
        model.addAttribute("depositoryList", depositoryService.getDepositorys());
        model.addAttribute("categoryManager", categoryManager);
		model.addAttribute("attributeManager", attributeManager);
        QueryPage query = null;
        if(flag!=null && flag.equals("true")){
        	model.addAttribute("flag", "false");
        }else{
        	query = storageManager.searchZeroStock(storageQuery, currPage, pageSize, true);
        }
        model.addAttribute("query", query);
        return "/storage/list_zero_storage";
    }

    /**
     * ����Excel����
     *
     * @param out
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/export_zero_storage")
	public void exportZeroStorage(
    		@ModelAttribute("storageQuery") StorageQuery storageQuery,
    		@RequestParam(value = "flag", required = false, defaultValue = "") String flag,
    		@RequestParam(value = "page", required = false, defaultValue = "1") int currPage,
    		HttpServletResponse res) throws Exception {

        OutputStream outwt = null;
        try {
            Date da = new Date();
            outwt = res.getOutputStream();
            SimpleDateFormat df = new SimpleDateFormat("yyyy_MM_dd");
            String date = df.format(da);
            res.setHeader("Content-disposition", "attachment; filename=Zero_Storage" + date + ".xls");
            res.setContentType("application/octet-stream;charset=utf-8");

            List<Object[]> storageExportList = new ArrayList<Object[]>();
            String[] title = { "��Ʒ����", "��Ӧ�̲�Ʒ����", "��Ʒ����", "��Ŀ", "����", "��λ", "���", "��;���", "���ÿ��",
                    "��������", "ʱ��" };
            storageExportList.add(title);
            // ���ѯ
            QueryPage queryPage = storageManager.searchZeroStock(storageQuery, currPage, pageSize, false);
            List<Storage> storageListAll = (List<Storage>)queryPage.getItems();
            if(flag!=null&&flag.equals("false")){
            	storageListAll = null;
            }
            SimpleDateFormat usedf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            if (storageListAll != null) {
                for (Storage tmp : storageListAll) {
                	Object[] data = { tmp.getCode(), tmp.getSupplier_code(), tmp.getInstanceName(),
                            categoryManager.getCatFullNameByCatcode(tmp.getCatCode()),
                            attributeManager.getFullAttributeStringByAttrs(tmp.getAttrs()),
                            tmp.getGoodsUnit(), 0, tmp.getWayNum(),
                            tmp.getInstanceExistNum(), DoubleUtil.round(tmp.getSalesSum(), 2),
                            usedf.format(tmp.getGmtModify()) + "" };
                    storageExportList.add(data);
                }
            }
            goodsBatch.exportExcelByObject(outwt, storageExportList);
            outwt.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(outwt);
        }
    }

    /**
     * ����˻�ҳ��
     * @return TODO
     */
    @RequestMapping(value = "/storageRefundPage")
    public String storageRefundPage(Model model, HttpServletRequest request) {
        String ids = request.getParameter("ids");
        String avPrices = request.getParameter("avPrices");
        String[] goodsInstanceIds = null;
        String[] averagePriceArray = null;
        Map<String, String> parMap = new HashMap<String,String>();
    	Storage storage = new Storage();
    	List<Storage> storageList = new ArrayList<Storage>();

        try {
        	if(StringUtils.isNotBlank(ids)){
        		goodsInstanceIds = ids.split(",");
        		averagePriceArray = avPrices.split(",");
        	}else{
        		goodsInstanceIds = new String[1];
        		goodsInstanceIds[0] = request.getParameter("goodsInstanceId");
        		ids = request.getParameter("goodsInstanceId");
        		averagePriceArray = new String[1];
        		averagePriceArray[0] = request.getParameter("averagePrice");
        		avPrices = request.getParameter("averagePrice");
        	}
        	// ȥ���ظ��ļ�¼
        	Map<String, String> map = new HashMap<String, String>();
        	if(goodsInstanceIds != null && goodsInstanceIds.length > 0){
        		for(int j=0;j<goodsInstanceIds.length;j++){
        			map.put(goodsInstanceIds[j], averagePriceArray[j]);
        		}
        		List keyList = new ArrayList(map.keySet());
        		List valueList = new ArrayList(map.values());
        		goodsInstanceIds = (String[]) keyList.toArray(new String[keyList.size()]);
        		averagePriceArray = (String[]) valueList.toArray(new String[valueList.size()]);
        		ids = StringUtils.join(goodsInstanceIds, ",");
        		avPrices = StringUtils.join(averagePriceArray, ",");
        	}
        	// ѭ����ȡ����¼
        	List<Storage> list;
        	parMap.put("supplierId", request.getParameter("supplierId"));
            parMap.put("locId", request.getParameter("locId"));
        	for(int i=0;i<goodsInstanceIds.length;i++){
        		if(StringUtils.isBlank(goodsInstanceIds[i])
        				|| !StringUtils.isNumeric(goodsInstanceIds[i])){
        			continue;
        		}
        		parMap.put("goodsInstanceId", goodsInstanceIds[i]);
        		parMap.put("averagePrice", averagePriceArray[i]);
        		list = storageManager.getRefundStoragesByMap(parMap);
                storageList.addAll(list);
        	}
        	// ��ȡһЩ������Ϣ
        	if(storageList != null && storageList.size() > 0){
        		storage = storageList.get(0);
        		Depository dp = depLocationManager.getDepositoryByLocationId(storage.getLocId());
                if (dp != null) {
                    storage.setDepositoryName(dp.getName());
                    if (EnumDepositoryType.OUT_BORROW_DEP.getKey().equals(dp.getType())) {
                        model.addAttribute("message", "waijie");
                    }
                }
        	}
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        Map<String, String> enumShoppingRefundReason = EnumShoppingRefundReason.toMap();

        model.addAttribute("supplierId", request.getParameter("supplierId"));
        model.addAttribute("locId", request.getParameter("locId"));
        model.addAttribute("ids",ids);
        model.addAttribute("storTypeMap",EnumStorType.toMap());
        model.addAttribute("avPrices", avPrices);
        model.addAttribute("reasonSet", enumShoppingRefundReason.entrySet());
        model.addAttribute("errorInfo", request.getParameter("errorInfo"));
        model.addAttribute("storage", storage);
        model.addAttribute("storageList", storageList);
        return "/storage/storageRefundPage";
    }
//
//
    /**
     * ����˻���Ҫ�ж��˻���������Ƿ���δ��ɵ�
     * fangqing
     * @return
     */
	@RequestMapping(value = "/validateRefund")
	public @ResponseBody
	String validateRefund(
			@RequestParam(value = "param1", required = false, defaultValue = "") String storageId,
			@RequestParam(value = "param2", required = false, defaultValue = "") String goodsIds,
			@RequestParam(value = "param3", required = false, defaultValue = "") String refNum
			) {
		String message = "";
		if (storageId != null && storageId.length() > 0) {
			// ͬһ����Ʒ���˻������뵥�д���û�����״̬
			int count = storageRefundApplyManager.getStorageRefundApplyCountByStorageId(storageId);
			if (count > 0) {
				message = "errorIsHas";
				return message;
			}
			// ���ÿ������С���˻�����
			String[] goodsIdArray = goodsIds.split(",");
			String[] refNumArray = refNum.split(",");
			Map<String,Long> map = new HashMap<String,Long>();
			if(goodsIdArray.length > 0){
				for(int i = 0;i < goodsIdArray.length;i++){
					if(map.containsKey(goodsIdArray[i])){
						map.put(goodsIdArray[i], Long.valueOf(refNumArray[i])+map.get(goodsIdArray[i]));
					}else{
						map.put(goodsIdArray[i], Long.valueOf(refNumArray[i]));
					}
				}
				Set set = map.keySet();
				String gIdStr = set.toString();
				gIdStr = gIdStr.substring(1, gIdStr.length()-1);
				goodsIdArray = gIdStr.split(",");
				for(String gId : goodsIdArray){
					long sumNo = storageManager.getSumGoodsNumberByGoodsId(Long.valueOf(gId.trim()));
					if(map.get(gId.trim()) != null && sumNo < map.get(gId.trim())){
						message = "errorNum";
						break;
					}
				}
			}else{
				message = "error";
				return message;
			}
		}
		return message;
	}

    /**
	 * ����˻�����
	 *
	 * @return
	 */
	@RequestMapping(value = "/refundStorages")
	public String refundStorages(Model model, HttpServletRequest request, AdminAgent adminAgent) {

		Map<String, Object> map = new HashMap<String, Object>();
		String storageId = request.getParameter("storageId");

		map.put("goodsUnit", request.getParameter("goodsUnit"));// ��λ
		map.put("goodsInstanceId", request.getParameter("goodsInstanceId"));// ��ƷID
		map.put("goodsId", request.getParameter("goodsId"));// ��ƷID
		map.put("goodsCode", request.getParameter("goodsCode"));// ��Ʒ����
		map.put("supplierId", request.getParameter("supplierId"));// ��Ӧ��ID
		map.put("user", adminAgent.getUsername());// ������

		map.put("id", request.getParameterValues("id"));// ����¼ID
		// map.put("batchNum", request.getParameterValues("batchNum"));// ����
		map.put("locId", request.getParameterValues("locId"));// ��λID
		// map.put("storageNum", request.getParameterValues("storageNum"));// �������
		map.put("refNum", request.getParameterValues("refNum"));// �˻�����
		map.put("refPrice", request.getParameterValues("refPrice"));// ��λ�ɱ�
		map.put("dueFee", request.getParameterValues("dueFee"));// Ӧ�ս��
		map.put("factFee", request.getParameterValues("factFee"));// ʵ�ս��
		map.put("reason", request.getParameterValues("reason"));// ����ԭ��
		map.put("remark", request.getParameterValues("remark"));// ��ע

		model.addAttribute("supplierId", request.getParameter("supplierId"));
		model.addAttribute("locId", request.getParameter("locId"));
		model.addAttribute("ids", request.getParameter("ids"));
		model.addAttribute("avPrices", request.getParameter("avPrices"));
		model.addAttribute("storType", request.getParameter("storType"));

		// ͬһ����Ʒ���˻������뵥�д���û�����״̬
		int count = storageRefundApplyManager.getStorageRefundApplyCountByStorageId(storageId);
		if (count > 0) {
			model.addAttribute("errorInfo", "errorIsHas");
			return "redirect:/storage/storageRefundPage.html";
		}

		boolean isEmpty = true;
		String[] refNums = request.getParameterValues("refNum");
		for (String refNum : refNums) {
			if (StringUtil.isNotBlank(refNum) && StringUtils.isNotEmpty(refNum)) {
				isEmpty = false;
			}
		}

		if (isEmpty) {
			model.addAttribute("errorInfo", "allempty");
			return "redirect:/storage/storageRefundPage.html";
		}

		String[] remarks = request.getParameterValues("remark");
		for (String remark : remarks) {
			if (remark != null && remark.length() > 100) {
				model.addAttribute("errorInfo", "longerror");
				return "redirect:/storage/storageRefundPage.html";
			}
		}

		String applyRelationNum = storageRefundApplyManager.addStorageRefundApply(map);
		int storageRefundApplyCount = storageRefundApplyManager.getStorageRefundApplyCountByRelationNum(applyRelationNum);

		if (storageRefundApplyCount == 0) {
			model.addAttribute("errorInfo", "error");
			return "redirect:/storage/storageRefundPage.html";
		}

		model.addAttribute("applyRelationNum", applyRelationNum);
		return "redirect:/storage/detail_storage_refund_apply.html";
	}

    public static void close(OutputStream out) {

        try {
            if (out != null) {
                out.close();
            }
        } catch (IOException ioe) {
            // ignore
        }
    }

  //=====================================================================================================================================
    /*
     * ��ʼ���ֿ���Ϣ
     *
     * @param storageQuery
     * @param adminAgent
     * @param model
     */
 	private void initDepository(StorageQuery storageQuery,AdminAgent adminAgent, Model model) {
 		//����ȫ����һ���ֿ�ID
     	depositoryFirstList = depositoryFirstManager.getDepositoryFirstListByIds(getDepfirstIdForQuery(adminAgent));
 		if (depositoryFirstList == null || depositoryFirstList.size() == 0) {
 			model.addAttribute("message", "һ���ֿ��¼Ϊ�գ�");
 		}else{
 			model.addAttribute("depositoryFirstList", depositoryFirstList);
 			if (StringUtils.isNotBlank(storageQuery.getDepfirstId())
 					&& StringUtils.isNumeric(storageQuery.getDepfirstId())) {
 				model.addAttribute("depositoryList", depositoryService.getDeplistByFirstDepId(Long.valueOf(storageQuery.getDepfirstId())));
 			}
 			if (StringUtils.isNotBlank(storageQuery.getDepId())
 					&& StringUtils.isNumeric(storageQuery.getDepId())) {
 				model.addAttribute("depLocationLists", depLocationManager.getLocationsByDepositoryId(Long.valueOf(storageQuery.getDepId())));
 			}
 		}
 	}
}
