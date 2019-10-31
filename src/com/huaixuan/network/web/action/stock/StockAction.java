/**
 *
 */
package com.huaixuan.network.web.action.stock;

import java.io.OutputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.huaixuan.network.biz.domain.admin.Admin;
import com.huaixuan.network.biz.domain.admin.AdminAgent;
import com.huaixuan.network.biz.domain.goods.Goods;
import com.huaixuan.network.biz.domain.goods.GoodsInstance;
import com.huaixuan.network.biz.domain.goods.GoodsInstanceSupplier;
import com.huaixuan.network.biz.domain.stock.ShoppingDetail;
import com.huaixuan.network.biz.domain.stock.ShoppingDetailSearch;
import com.huaixuan.network.biz.domain.stock.ShoppingGatherSearch;
import com.huaixuan.network.biz.domain.stock.ShoppingList;
import com.huaixuan.network.biz.domain.stock.ShoppingMoreDetail;
import com.huaixuan.network.biz.domain.stock.ShoppingRefund;
import com.huaixuan.network.biz.domain.stock.ShoppingRefundDetail;
import com.huaixuan.network.biz.domain.stock.query.ShoppingListQuery;
import com.huaixuan.network.biz.domain.stock.query.StockDetailSearchQuery;
import com.huaixuan.network.biz.domain.storage.DepositoryFirst;
import com.huaixuan.network.biz.domain.storage.InDepository;
import com.huaixuan.network.biz.domain.storage.Storage;
import com.huaixuan.network.biz.domain.supplier.Supplier;
import com.huaixuan.network.biz.domain.trade.Order;
import com.huaixuan.network.biz.domain.trade.Trade;
import com.huaixuan.network.biz.domain.trade.WholesaleApply;
import com.huaixuan.network.biz.enums.EnumFinanceStatus;
import com.huaixuan.network.biz.enums.EnumInDepository;
import com.huaixuan.network.biz.enums.EnumInStatus;
import com.huaixuan.network.biz.enums.EnumShoppingListType;
import com.huaixuan.network.biz.enums.EnumShoppingRefund;
import com.huaixuan.network.biz.enums.EnumShoppingRefundReason;
import com.huaixuan.network.biz.enums.EnumShoppingRefundStatus;
import com.huaixuan.network.biz.enums.EnumStockStatus;
import com.huaixuan.network.biz.enums.EnumStorType;
import com.huaixuan.network.biz.enums.EnumWholesaleStatus;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.service.admin.AdminService;
import com.huaixuan.network.biz.service.common.CodeManager;
import com.huaixuan.network.biz.service.goods.AttributeManager;
import com.huaixuan.network.biz.service.goods.CategoryManager;
import com.huaixuan.network.biz.service.goods.GoodsBatchManager;
import com.huaixuan.network.biz.service.goods.GoodsInstanceManager;
import com.huaixuan.network.biz.service.goods.GoodsInstanceSupplierManager;
import com.huaixuan.network.biz.service.goods.GoodsManager;
import com.huaixuan.network.biz.service.stock.ShoppingDetailService;
import com.huaixuan.network.biz.service.stock.ShoppingListService;
import com.huaixuan.network.biz.service.stock.ShoppingRefundDetailService;
import com.huaixuan.network.biz.service.stock.ShoppingRefundService;
import com.huaixuan.network.biz.service.storage.DepositoryFirstManager;
import com.huaixuan.network.biz.service.storage.InDepositoryManager;
import com.huaixuan.network.biz.service.storage.StorageManager;
import com.huaixuan.network.biz.service.supplier.SupplierService;
import com.huaixuan.network.biz.service.trade.OrderManager;
import com.huaixuan.network.biz.service.trade.TradeManager;
import com.huaixuan.network.biz.service.trade.WholesaleApplyManager;
import com.huaixuan.network.common.util.DateUtil;
import com.huaixuan.network.web.action.BaseAction;
import com.huaixuan.network.web.action.interceptor.AdminDeniedException;
import com.hundsun.network.melody.common.util.StringUtil;

/**
 * @ClassName: StockAction
 * @Description: �ɹ�ģ��
 * @author liuwd weida-liu@foxmail.com
 * @date 2011-2-24 ����05:03:20
 */
@Controller
public class StockAction extends BaseAction {

	private static final long serialVersionUID = 4946660868685947645L;

	// private ShoppingListManager shoppingListManager;
	// private ShoppingDetailManager shoppingDetailManager;
	// private SupplierManager supplierManager;
	// private UserManager userManager;
	// private CategoryManager categoryManager;
	// private AttributeManager attributeManager;
	// private DepositoryFirstManager depositoryFirstManager;
	@Autowired
	ShoppingRefundService shoppingRefundService;
	@Autowired
	ShoppingRefundDetailService shoppingRefundDetailService;
	// private StorageManager storageManager;
	// private InDepositoryManager inDepositoryManager;
	// private InDetailManager inDetailManager;
	// private GoodsInstanceManager goodsInstanceManager;
	// private GoodsInstanceSupplierManager goodsInstanceSupplierManager;
	// private GoodsBatchManager goodsBatch;
	@Autowired
	GoodsManager goodsManager;
	// private OrderManager orderManager;
	@Autowired
	WholesaleApplyManager wholesaleApplyManager;
	// private TradeManager tradeManager;
	// /* Spring 变量 */
	// private ShoppingList shoppingList;
	// private ShoppingDetail shoppingDetail;
	// private ShoppingRefund shoppingRefund;
	// private ShoppingRefundDetail shoppingRefundDetail;
	// private Boolean succFlag = Boolean.FALSE;
	//
	// private AdminManager adminManager;
	//
	// private DepositoryManager depositoryManager;
//	private long totalNum;
//	private String shoppingOriCount;
	//
	/* @property: */
//	private double factFee;

	// private String shoppingstatus; //状�
	// private String shoppingtime; //时间
	// public List<DayStock> orderList;
	// private List<Storage> storageList = new ArrayList<Storage>();
	// private List<DepositoryFirst> depositoryFirstList; //�级仓库列�
	//
	// /* 变量 */
	// private Long stockId;
	// private Long refundId;
	// private List<ShoppingList> shoppingLists;
	// private List<ShoppingMoreDetail> shoppingMoreDetails;
	// private List<ShoppingDetail> shoppingDetails;
	// private List<ShoppingDetailSearch> shoppingDetailSearchLists;
	// private List<ShoppingGatherSearch> shoppingGatherSearchLists;
	// private List<ShoppingRefundDetail> shoppingRefundDetailLists;
	// private List<GoodsInstanceSupplier> goodsInstanceSuppliers;
	// private List<User> userLists;
	//
	// private Map parMap = new HashMap();
	// private Map<String, String> enumStockStatusMap = EnumStockStatus.toMap();
	// private Map<String, String> enumShoppingRefundReason =
	// EnumShoppingRefundReason.toMap();
	// private Map<String, String> enumShoppingRefund =
	// EnumShoppingRefund.toMap();
	// private Map<String, String> enumShopRefundMap =
	// EnumShoppingRefundStatus.toMap(); // 采购�货状�
	// private Map<String, String> enumStockTypeMap =
	// EnumShoppingListType.toMap(); // 采购订单类型
	// private Map<String, String> storTypeMap = EnumStorType.toMap();
	// private CodeManager codeManager;
	// private String message;
	// private List<DayStock> dayStockSearchLists;
	// private List<Depository> depositoryLists;

	@Autowired
	ShoppingListService shoppingListService;

	@Autowired
	ShoppingDetailService shoppingDetailService;

	@Autowired
	CodeManager codeManager;

	@Autowired
	CategoryManager categoryManager;

	@Autowired
	AttributeManager attributeManager;

	@Autowired
	GoodsInstanceManager goodsInstanceManager;
	//
	 @Autowired
	 GoodsInstanceSupplierManager goodsInstanceSupplierManager;

	@Autowired
	SupplierService supplierService;

	@Autowired
	OrderManager orderManager;

	@Autowired
	StorageManager storageManager;

	@Autowired
	GoodsBatchManager goodsBatch;

	@Autowired
	DepositoryFirstManager depositoryFirstManager;

	@Autowired
	InDepositoryManager inDepositoryManager;

	// @Autowired
	// ShoppingRefundDetailService shoppingRefundDetailService;

	 @Autowired
	 AdminService adminService;

	 @Autowired
	 TradeManager tradeManager;

	/**
	 * ��ѯ�ɹ������б�
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/stock/stock_search")
	public String searchStockList(
			@ModelAttribute("shoppingListQuery") ShoppingListQuery shoppingListQuery,
			@RequestParam(value = "isCheck", required = false, defaultValue = "false") String isCheck,
			@RequestParam(value = "isFirst", required = false, defaultValue = "false") String isFirst,
			@RequestParam(value = "page", required = false, defaultValue = "1") int currPage,
			Model model,
			AdminAgent adminAgent) {

		if ("true".equals(isFirst)) {
			Date now = new Date();
			Timestamp nowTs = new Timestamp(now.getTime());
			Date before30 = DateUtil.getDate(now, -30);
			Timestamp beforeTs = new Timestamp(before30.getTime());
			if (StringUtils.isBlank((String) shoppingListQuery
					.getShoppingTimeStart())) {
				shoppingListQuery.setShoppingTimeStart(DateUtil
						.getTimestampToString(beforeTs));
			}
			if (StringUtils.isBlank((String) shoppingListQuery
					.getShoppingTimeEnd())) {
				shoppingListQuery.setShoppingTimeEnd(DateUtil
						.getTimestampToString(nowTs));
			}
		}
		if("true".equals(isCheck))
		{
			shoppingListQuery.setStatus(EnumStockStatus.STOCK_WAIT_CHECK.getKey());
		}
		// ȡ�ù���ԱȨ�޵Ļ�Ա�б�
		// TODO �����ȡ�˺�̨�����û�������Ժ���԰��տͻ��������ĵ�
		List<Admin> userList = adminService.getAdminUserList();
		model.addAttribute("userList", userList);

		model.addAttribute("storTypeMap", EnumStorType.toMap());
		model.addAttribute("financeStatusMap", EnumFinanceStatus.toMap());
		model.addAttribute("stockStatusMap", EnumStockStatus.toMap());
		model.addAttribute("stockTypeMap", EnumShoppingListType.toMap());
		//
		// ��������Ϣ��������
		if (StringUtil.isEmpty((String) shoppingListQuery.getCreater())) {
			if (this.isHasTeam(adminAgent)) {
				shoppingListQuery.setSameTeamUsers(this.getSameTeamUsers(adminAgent));
			}
		} else if (this.isHasTeam(adminAgent)
				&& !this.getSameTeamUsers(adminAgent).contains(shoppingListQuery.getCreater())) {
			if ("true".equals(isCheck)) {
				model.addAttribute("isCheck", isCheck);
				return "/stock/stock_check_search";
			} else {
				String message = "Ҫ��ѯ�ļ�¼�����˺��㲻��ͬһ�飬���ܲ鿴�����ݣ�";
				model.addAttribute("message", message);
				return "/stock/stock_search";
			}
		}

		QueryPage queryPage = null;
		try {
			queryPage = shoppingListService.getShoppingListsByParameterMap(
					shoppingListQuery, currPage, pageSize);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (queryPage != null) {
			model.addAttribute("query", queryPage);
		}

		if ("true".equals(isCheck)) {
			model.addAttribute("isCheck", isCheck);
			return "/stock/stock_check_search";
		}
		return "/stock/stock_search";
	}

	/**
     * ��ѯӦ��������б�
     *
     * @return
     * @throws Exception
     */

	@RequestMapping(value = "/stock/due_search")
    public String dueSearch(
    		@ModelAttribute("shoppingListQuery") ShoppingListQuery shoppingListQuery,
    		@RequestParam(value = "isFirst", required = false, defaultValue = "false") String isFirst,
    		@RequestParam(value = "page", required = false, defaultValue = "1") int currPage,
    		Model model
    	) throws Exception {
        double gatherSumDueFee = 0;
        double gatherSumFactFee = 0;

        if ("true".equals(isFirst)) {
            Date now = new Date();
            Timestamp nowTs = new Timestamp(now.getTime());
            Date before7 = DateUtil.getDate(now, -7);
            Timestamp beforeTs = new Timestamp(before7.getTime());
            if (StringUtils.isBlank((String) shoppingListQuery.getShoppingTimeStart())) {
            	shoppingListQuery.setShoppingTimeStart(DateUtil.getTimestampToString(beforeTs));
            }
            if (StringUtils.isBlank((String) shoppingListQuery.getShoppingTimeEnd())) {
            	shoppingListQuery.setShoppingTimeEnd(DateUtil.getTimestampToString(nowTs));
            }
        }
        // ȡ�ù���ԱȨ�޵Ļ�Ա�б�
        // TODO �����ȡ�˺�̨�����û�������Ժ���԰��տͻ��������ĵ�
        List<Admin> userList = adminService.getAdminUserList();
        model.addAttribute("userList", userList);

        QueryPage queryPage = shoppingListService.getDueSearchListsByParameterMap(shoppingListQuery, currPage, pageSize);

        List<ShoppingList> shoppingLists = (List<ShoppingList>)queryPage.getItems();
        if (shoppingLists !=null && shoppingLists.size() > 0) {
            if (shoppingLists != null && shoppingLists.size() > 0) {
                for (ShoppingList obj : shoppingLists) {
                    gatherSumDueFee += (obj.getSumDueFee() == null ? 0 : obj.getSumDueFee()
                        .doubleValue());
                    gatherSumFactFee += (obj.getSumFactFee() == null ? 0 : obj.getSumFactFee()
                        .doubleValue());
                }
            }
        }
        model.addAttribute("query", queryPage);
        model.addAttribute("queryObject", shoppingListQuery);
        model.addAttribute("gatherSumDueFee", gatherSumDueFee);
        model.addAttribute("gatherSumFactFee", gatherSumFactFee);
        model.addAttribute("enumStockStatusMap", EnumStockStatus.toMap());
        return "/stock/due_search";
    }

	/**
	 * ��ѯ�ɹ���������
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/stock/search_stock_detail", method = RequestMethod.GET)
	public String searchStockDetail(
			@RequestParam(value = "stockId", required = false, defaultValue = "") String id,
			@RequestParam(value = "shoppingNum", required = false, defaultValue = "") String shoppingNum,
			@RequestParam(value = "message", required = false, defaultValue = "") String message,
			Model model,
			AdminAgent adminAgent
			) throws Exception {

		long rejectNum = 0;
		long missingNum = 0;
		long receiveNum = 0;

		ShoppingList shoppingList = null;
		List<ShoppingMoreDetail> shoppingMoreDetails = null;

		if (StringUtils.isEmpty(id)) {
			shoppingList = shoppingListService
					.getShoppingListByShoppingNum(shoppingNum);
		} else {
			shoppingList = shoppingListService.getShoppingList(new Long(id));
		}
		model.addAttribute("shoppingList", shoppingList);

		if (shoppingList != null) {
			// �ж��Ƿ�������
			Map parMap = new HashMap();
			parMap.put("relationNum", shoppingList.getShoppingNum());
			parMap.put("type", EnumInDepository.IN_SHOPPING.getKey());
			 InDepository obj = inDepositoryManager.getInDepository(parMap);
			 if(obj == null ||EnumInStatus.IN_FINISHED.getKey().equals(obj.getStatus())){
				 model.addAttribute("isInFinished", true);
			 }else{
				 model.addAttribute("isInFinished", false);
			 }
			 //��ȡһ���ֿ�����
			 if (shoppingList.getDepFirstId() != null) {
				 DepositoryFirst depositoryFirstTmp = depositoryFirstManager
				 		.getDepositoryById(shoppingList.getDepFirstId());
				 if (depositoryFirstTmp != null) {
				 shoppingList.setDepFirstName(depositoryFirstTmp.getDepFirstName());
				 }
			 }
			 //�ж��Ƿ���ͬ��ļ�¼
			 if (this.isHasTeam(adminAgent) && !this.isSameTeam(shoppingList.getCreater(),adminAgent)) {
				 throw new AdminDeniedException("���ܲ�ѯ�������Լ�ͬ���Ա�Ĳɹ���Ϣ��");
			 }
			shoppingMoreDetails = this
					.getNewShoppingMoreDetailLists(shoppingDetailService
							.getShoppingDetailsByShoppingListId(shoppingList
									.getId()));

			long totalNum = 0;

			double factFee = 0.0;

			for (int i = 0; i < shoppingMoreDetails.size(); i++) {
				totalNum += shoppingMoreDetails.get(i).getAmount();

				missingNum += shoppingMoreDetails.get(i).getMissingNum();
				factFee += shoppingMoreDetails.get(i).getFactFee();
				rejectNum += shoppingMoreDetails.get(i).getRejectNum();
				receiveNum += shoppingMoreDetails.get(i).getReceiveNum();
			}
			model.addAttribute("totalNum", totalNum);
			model.addAttribute("factFee", factFee);
			model.addAttribute("receiveNum", receiveNum);
		}

		if (shoppingMoreDetails != null && shoppingMoreDetails.size() > 0) {
			// ȱ������
			missingNum = shoppingDetailService
					.getMissingNumByShoppingListId(shoppingList.getId());
			// ��������
			rejectNum = shoppingDetailService
					.getRejectNumByShoppingListId(shoppingList.getId());

			// ����Ƿ��������ղ���
			 Map parMap = new HashMap();
			 parMap.put("shoppingId", String.valueOf(shoppingList.getId()));
			 parMap.put("type", EnumShoppingRefund.REFUND_JUSHOU.getKey());
			 int jushouNum =
			 shoppingRefundDetailService.getCountRefundByShoppingIdAndStatus(parMap);//
			// ����
			 model.addAttribute("jushouNum", jushouNum);

			 parMap.put("type", EnumShoppingRefund.REFUND_DINGDAN.getKey());
			 int danjuNum =
			 shoppingRefundDetailService.getCountRefundByShoppingIdAndStatus(parMap);//
			// ����
			 model.addAttribute("danjuNum", danjuNum);
		}
		model.addAttribute("stockNew", EnumStockStatus.STOCK_NEW.getKey());
		model.addAttribute("stockChecked",
				EnumStockStatus.STOCK_CHECKED.getKey());
		model.addAttribute("stockWaitCheck",
				EnumStockStatus.STOCK_WAIT_CHECK.getKey());
		model.addAttribute("stockFinished",
				EnumStockStatus.STOCK_FINISHED.getKey());
		model.addAttribute("enumStockStatusMap", EnumStockStatus.toMap());
		model.addAttribute("storTypeMap", EnumStorType.toMap());
		model.addAttribute("enumStockTypeMap", EnumShoppingListType.toMap());

		model.addAttribute("shoppingMoreDetails", shoppingMoreDetails);

		model.addAttribute("missingNum", missingNum);
		model.addAttribute("rejectNum", rejectNum);
		model.addAttribute("message", message);

		return "/stock/stock_detail";
	}

	/**
	 * �ɹ�������ϸ��ѯ
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/stock/stock_detail_search")
	public String stockDetailSearch(
			@ModelAttribute("stockDetailSearchQuery") StockDetailSearchQuery stockDetailSearchQuery,
			@RequestParam(value = "isFirst", required = false, defaultValue = "false") String isFirst,
			@RequestParam(value = "page", required = false, defaultValue = "1") int currPage,
			Model model) throws Exception {

		if ("true".equals(isFirst)) {
			Date now = new Date();
			Timestamp nowTs = new Timestamp(now.getTime());
			Date before30 = DateUtil.getDate(now, -30);
			Timestamp beforeTs = new Timestamp(before30.getTime());

			if (StringUtils.isBlank((String) stockDetailSearchQuery
					.getStartTime())) {
				stockDetailSearchQuery.setStartTime(DateUtil
						.getTimestampToString(beforeTs));
			}
			if (StringUtils.isBlank((String) stockDetailSearchQuery
					.getEndTime())) {
				stockDetailSearchQuery.setEndTime(DateUtil
						.getTimestampToString(nowTs));
			}
		}

		QueryPage query = shoppingListService
				.getShoppingDetailSearchRusult(
						stockDetailSearchQuery, currPage, pageSize);
		if (query.getItems() !=null && query.getItems().size() > 0) {
			List<ShoppingDetailSearch> detailSearchLists = new ArrayList<ShoppingDetailSearch>();
			for (ShoppingDetailSearch detailSearch : (List<ShoppingDetailSearch>) query
					.getItems()) {
				detailSearch.setCatCode(categoryManager
						.getCatFullNameByCatcodeSimple(
								detailSearch.getCatCode(), ">"));
				detailSearch
						.setAttrs(attributeManager
								.getFullAttributeStringByAttrs(detailSearch
										.getAttrs()));
				detailSearchLists.add(detailSearch);

			}
			query.setItems(detailSearchLists);
		}

		model.addAttribute("storTypeMap", EnumStorType.toMap());
		model.addAttribute("queryObject", stockDetailSearchQuery);
		model.addAttribute("query", query);
		return "/stock/stock_detail_search";
	}

	/**
	 * �ɹ��������ܲ�ѯ
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/stock/stock_gather_search")
	public String stockGatherSearch(
			@ModelAttribute("stockDetailSearchQuery") StockDetailSearchQuery stockDetailSearchQuery,
			@RequestParam(value = "isFirst", required = false, defaultValue = "false") String isFirst,
			@RequestParam(value = "page", required = false, defaultValue = "1") int currPage,
			Model model) throws Exception {
		String gmtCreateStart = stockDetailSearchQuery.getStartTime();
		String gmtCreateEnd = stockDetailSearchQuery.getEndTime();
		if (StringUtil.isBlank(gmtCreateStart)
				&& StringUtil.isBlank(gmtCreateEnd)) {
			gmtCreateStart = DateUtil.getDiffDate(new Date(), -30);
			gmtCreateEnd = DateUtil.getDateToString(new Date());
			stockDetailSearchQuery.setStartTime(gmtCreateStart);
			stockDetailSearchQuery.setEndTime(gmtCreateEnd);
		}
		QueryPage queryPage = shoppingListService
				.getShoppingGatherSearchRusult(
						stockDetailSearchQuery, currPage, pageSize);
		if (queryPage.getItems() != null && queryPage.getItems().size() > 0) {
			List<ShoppingGatherSearch> gatherSearchLists = new ArrayList<ShoppingGatherSearch>();
			for (ShoppingGatherSearch gatherSearch : (List<ShoppingGatherSearch>) queryPage
					.getItems()) {
				gatherSearch.setCatCode(categoryManager
						.getCatFullNameByCatcodeSimple(
								gatherSearch.getCatCode(), ">"));
				gatherSearch
						.setAttrs(attributeManager
								.getFullAttributeStringByAttrs(gatherSearch
										.getAttrs()));
				gatherSearchLists.add(gatherSearch);
			}
			queryPage.setItems(gatherSearchLists);
		}

		model.addAttribute("storTypeMap", EnumStorType.toMap());
		model.addAttribute("queryObject", stockDetailSearchQuery);
		model.addAttribute("query", queryPage);

		return "/stock/stock_gather_search";
	}

	/**
	 * �����ɹ�������ʼҳ��
	 */
	@RequestMapping(value = "/stock/stock_add_page", method = RequestMethod.GET)
	public String addStockPage(
			@RequestParam(value = "tid", required = false, defaultValue = "") String tid,// ���׶�������
			@RequestParam(value = "depFirstId", required = false, defaultValue = "") String depFirstId,// һ���ֿ�
			@RequestParam(value = "addType", required = false, defaultValue = "") String addType,// ��������
			AdminAgent adminAgent,
			Model model) {

		 List<DepositoryFirst> depositoryFirstList =depositoryFirstManager.getDepositoryFirstListByIds(getDepfirstIdForQuery(adminAgent));//һ���ֿ��б�

		model.addAttribute("stockId", codeManager.buildCodeByDateAndType(
				CodeManager.CAIGOU_CODE, 6, CAIGOU));

		model.addAttribute("addType", addType);
		model.addAttribute("tid", tid);
		model.addAttribute("depFirstId", depFirstId);

		model.addAttribute("storTypeMap", EnumStorType.toMap());
		 model.addAttribute("depositoryFirstList", depositoryFirstList);

		return "/stock/stock_add";
	}

	/**
	 * �����ɹ�����������Ϣ
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/stock/add_stock", method = RequestMethod.POST)
	public String addStock(
			@ModelAttribute("shoppingList") ShoppingList shoppingList,
			@RequestParam("flag") String flag,
			Model model,
			AdminAgent adminAgent
		)throws Exception {

		//�����˲ɹ����ŵ��ظ����жϡ�
        String shoppingNum = shoppingList.getShoppingNum();
        int shoppingNumCount = shoppingListService.getCountByShoppingNum(shoppingNum);
        if (shoppingNumCount > 0) {
            model.addAttribute("stockId", codeManager.buildCodeByDateAndType(CodeManager.CAIGOU_CODE, 6,CAIGOU));
            model.addAttribute("errorInfo", "�ɹ������ظ��������������ɣ�");
            model.addAttribute("tid", shoppingList.getTid());

            List<DepositoryFirst> depositoryFirstList =depositoryFirstManager.getDepositoryFirstListByIds(getDepfirstIdForQuery(adminAgent));//һ���ֿ��б�
            model.addAttribute("depositoryFirstList", depositoryFirstList);

            return "/stock/stock_add";
        }
        // ��֤�����ɹ���һ���ֿ��Ƿ�Ϊ��
        if ("w".equals(shoppingList.getIsWholesale())
        		&& StringUtil.isBlank(shoppingList.getDepFirstId().toString())) {
        	model.addAttribute("errorInfo", "һ�������ֿⲻ��Ϊ�գ�");
        	model.addAttribute("tid", shoppingList.getTid());
        	List<DepositoryFirst> depositoryFirstList =depositoryFirstManager.getDepositoryFirstListByIds(getDepfirstIdForQuery(adminAgent));//һ���ֿ��б�
            model.addAttribute("depositoryFirstList", depositoryFirstList);
            return "/stock/stock_add";
        }

        shoppingList.setCreater(adminAgent.getUsername());
        shoppingList.setGmtCreate(new Date());
        shoppingList.setStatus(EnumStockStatus.STOCK_NEW.getKey());
        Long stockId = shoppingListService.addShoppingList(shoppingList);

        model.addAttribute("stockId", stockId);

		// ��ת����Ӳ�Ʒ��ҳ��
		return "redirect:/stock/stock_goods_add_page.html?id="
				+ stockId.toString();
	}

	/**
	 * �����ɹ�������Ʒ��ʼҳ��
	 *
	 * @return "success" if no exceptions thrown
	 */
	@RequestMapping(value = "/stock/stock_goods_add_page")
	public String addStockGoodsPage(@RequestParam("id") String stockId,
			Model model) throws Exception {

		ShoppingList shoppingList = null;
		List<ShoppingMoreDetail> shoppingMoreDetails = null;
		if (null == stockId) {
			// this.addActionError(getText("nopopedom.stock.id.null"));
			return "nopopedom";
		} else {
			shoppingList = shoppingListService.getShoppingList(Long
					.parseLong(stockId));

			if (shoppingList != null) {
				shoppingMoreDetails = this
						.getNewShoppingMoreDetailLists(shoppingDetailService
								.getShoppingDetailsByShoppingListId(Long
										.parseLong(stockId)));
			}
		}

		model.addAttribute("enumStockTypeMap", EnumShoppingListType.toMap());
		model.addAttribute("enumStockStatusMap", EnumStockStatus.toMap());

		model.addAttribute("storTypeMap", EnumStorType.toMap());
		model.addAttribute("stockNew", EnumStockStatus.STOCK_NEW.getKey());

		model.addAttribute("shoppingList", shoppingList);
		model.addAttribute("shoppingMoreDetails", shoppingMoreDetails);

		return "/stock/stock_goods_add";
	}

	/**
	 * ���Ӳɹ�������Ʒ��Ϣ
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/stock/stock_goods_add", method = RequestMethod.POST)
	public String addStockGoods(
			@RequestParam("actionType") String actionType,
//			@RequestParam("sCount") String shoppingOriCount,
			@RequestParam("goodsIds") String goodsIdStr,
			@RequestParam("stockId") String id) throws Exception {
		List<GoodsInstanceSupplier> goodsInstanceSuppliers;
		GoodsInstance goodsInstance = null;
		GoodsInstanceSupplier goodsInstanceSupplier = null;

		ShoppingDetail shopDetail = null;
		String goodsIds[] = null;
		Long stockId = 0L;
		ShoppingList shoppingList = null;
		if (id == null || id.length() == 0) {
			return "/stock/stock_goods_add_page";
		} else {
			stockId = Long.parseLong(id);
			shoppingList = shoppingListService.getShoppingList(new Long(id));
			// �жϹ�Ӧ��״̬
			// Supplier supplier =
			// supplierManager.selectSupplierById(shoppingList.getSupplierId());
			// if (supplier == null || ("d").equals(supplier.getStatus())) {
			// // message = "notCopy";
			// return "redirect:/stock/search_stock_detail";
			// }
		}

        if (shoppingList != null) {
            if (StringUtils.isNotEmpty(goodsIdStr)) {
                if (EnumStockStatus.STOCK_NEW.getKey().equals(shoppingList.getStatus())) {
                    goodsIds = goodsIdStr.split(",");
                    if (goodsIds != null && goodsIds.length > 0) {
                        Date now = new Date();
                        for (int i = 0; i < goodsIds.length; i++) {
                            goodsInstance = goodsInstanceManager.getInstance(Long
                                .parseLong(goodsIds[i]));
                            if (goodsInstance != null) {
                                Map<String, String> parm_map = new HashMap<String, String>();
                                parm_map.put("shoppingId", String.valueOf(shoppingList.getId()));
                                parm_map.put("goodsInstanceId", String.valueOf(goodsInstance
                                    .getId()));
                                int count = shoppingDetailService
                                    .getCountByShoppingIdAndGoodsInsId(parm_map);
                                if (count <= 0) {
                                	Map parMap = new HashMap();
                                    parMap.put("goodsInstanceId", goodsInstance.getId().toString());
                                    parMap.put("supplierId", String.valueOf(shoppingList
                                        .getSupplierId()));
                                    goodsInstanceSuppliers = goodsInstanceSupplierManager
                                        .getGoodsInstanceSuppliersByParameterMap(parMap, 1,Integer.MAX_VALUE);
                                    if (goodsInstanceSuppliers != null
                                        && goodsInstanceSuppliers.size() > 0) {
                                        goodsInstanceSupplier = goodsInstanceSuppliers.get(0);
                                    }
                                    shopDetail = new ShoppingDetail();
                                    if (goodsInstanceSupplier != null) {
                                        if (goodsInstanceSupplier.getConsultPrice() == null) {
                                            shopDetail.setUnitPrice(0);
                                        } else {
                                            shopDetail.setUnitPrice(goodsInstanceSupplier
                                                .getConsultPrice().doubleValue());
                                        }
                                    }
                                    shopDetail.setGoodsId(goodsInstance.getGoodsId());
                                    shopDetail.setGoodsInstanceId(goodsInstance.getId());
                                    shopDetail.setShoppingId(stockId);
                                    shopDetail.setUnits(goodsInstance.getGoodsUnit());
                                    shoppingDetailService.addShoppingDetail(shopDetail);
                                }
                            }
                        }
                    }
                } else {
//                    message = "notAdd";
                }
            }
        }

		if ("edit".equals(actionType)) {
			return "redirect:/stock/edit_stock_page.html?stockId="+stockId;
		}
		return "redirect:/stock/stock_goods_add_page.html?id="+stockId;
	}

	/**
     * ���������ɹ�������Ϣ
     *
     * @return
     * @throws Exception
     */
	@RequestMapping("/stock/copyAdd")
    public String copyAdd(
    		@RequestParam("stockId")String id,
    		AdminAgent adminAgent,
    		Model model
    	) throws Exception {

		Long stockId = new Long(id);

        ShoppingList shoppingList = shoppingListService.getShoppingList(stockId);
        // �жϹ�Ӧ��״̬
        Supplier supplier = supplierService.selectSupplierById(shoppingList.getSupplierId());
        if (supplier == null || ("d").equals(supplier.getStatus())
        		|| EnumShoppingListType.SHOPPING_TYPE_W.getKey().equals(shoppingList.getIsWholesale())) {
        	model.addAttribute("message","notCopy");
            return "redirect:/stock/search_stock_detail.html?stockId="+stockId;
        }
        List<ShoppingMoreDetail> shoppingMoreDetails = this.getNewShoppingMoreDetailLists(shoppingDetailService
            .getShoppingDetailsByShoppingListId(new Long(id)));
        // ���������ɹ�����������Ϣ
        shoppingList.setShoppingNum(codeManager.buildCodeByDateAndType(CodeManager.CAIGOU_CODE, 6,
            CAIGOU));
        shoppingList.setCreater(adminAgent.getUsername());
        shoppingList.setStatus(EnumStockStatus.STOCK_NEW.getKey());
        shoppingList.setId(0);
        shoppingList.setShoppingTime_str(DateUtil.getFormatDate(new Date(), "yyyy-MM-dd"));
        shoppingList.setArriveTime_str(DateUtil.getFormatDate(new Date(), "yyyy-MM-dd"));
        Long shoppingId = shoppingListService.addShoppingList(shoppingList);
        stockId = shoppingId;
        // ��Ʒ��Ϣ
        ShoppingDetail shopDetail = null;
        for (ShoppingMoreDetail shopMoreDetail : shoppingMoreDetails) {
        	Map parMap = new HashMap();
            parMap.put("goodsInstanceId", String.valueOf(shopMoreDetail.getGoodsInstanceId()));
            parMap.put("supplierId", String.valueOf(shoppingList.getSupplierId()));
            int count = goodsInstanceSupplierManager
                .getGoodsInstanceSuppliersCountByParameterMap(parMap);
            if (count > 0) { // �ж������Ӧ���Ƿ񻹹�Ӧ����Ʒ
                shopDetail = new ShoppingDetail();
                shopDetail.setShoppingId(shoppingId);
                shopDetail.setGoodsId(shopMoreDetail.getGoodsId());
                shopDetail.setGoodsInstanceId(shopMoreDetail.getGoodsInstanceId());
                shopDetail.setUnits(shopMoreDetail.getUnits());
                shopDetail.setUnitPrice(shopMoreDetail.getUnitPrice());
                shopDetail.setAmount(shopMoreDetail.getAmount());
                shopDetail.setDueFee(shopMoreDetail.getDueFee());
                shopDetail.setFactFee(shopMoreDetail.getFactFee());
                shoppingDetailService.addShoppingDetail(shopDetail);
            }
        }
        return "redirect:/stock/edit_stock_page.html?stockId="+stockId;
    }

    /**
     * ���ղɹ�������ʼ��ҳ��
     *
     * @return
     * @throws Exception
     */
	@RequestMapping(value="/stock/stock_check_page")
    public String stockCheckPage(
    		@RequestParam("stockId")String id,
    		@RequestParam("type")String type,
    		AdminAgent adminAgent,
    		Model model
    	) throws Exception {

		Long stockId = Long.parseLong(id);
        ShoppingList shoppingList = shoppingListService.getShoppingList(stockId);
        if (!shoppingList.getStatus().equals(EnumStockStatus.STOCK_WAIT_CHECK.getKey())) {
//            message = "notright";
//            String shoppingstatus = EnumStockStatus.STOCK_WAIT_CHECK.getKey();
//            String shoppingtime = "true";
            return "redirect:/stock/stock_search.html?isCheck=true&staut="+EnumStockStatus.STOCK_WAIT_CHECK.getKey();
        }
        shoppingList.setFactArriveTime(new Date());// ʵ�ʵ���ʱ��Ĭ��Ϊϵͳʱ��
        if (shoppingList != null) {
            List<ShoppingMoreDetail> shoppingMoreDetails = getNewShoppingMoreDetailLists(shoppingDetailService
                .getShoppingDetailsByShoppingListId(stockId));
            model.addAttribute("shoppingMoreDetails", shoppingMoreDetails);
        }

        model.addAttribute("shoppingList", shoppingList);
        model.addAttribute("stockWaitCheck", EnumStockStatus.STOCK_WAIT_CHECK.getKey());
        model.addAttribute("type", type);
        List<DepositoryFirst> depositoryFirstList =depositoryFirstManager.getDepositoryFirstListByIds(getDepfirstIdForQuery(adminAgent));//һ���ֿ��б�
        model.addAttribute("depFirstIdInfoList", depositoryFirstList);

        model.addAttribute("stockStatusMap", EnumStockStatus.toMap());
        model.addAttribute("storTypeMap", EnumStorType.toMap());

        return "/stock/stock_check";
    }

	/**
     * �����޸Ĳɹ�����������Ϣ
     *
     * @return
     * @throws Exception
     */
	@RequestMapping(value="/stock/check_stock")
    public String checkStock(
    		@RequestParam("stockId")String id,
    		@RequestParam("factArriveTime_str")String factArriveTime,
    		@RequestParam("id")String[] ids,
    		@RequestParam("amount")String[] amount,
    		@RequestParam("newMissingNum")String[] newMissingNum,
    		@RequestParam("newRejectNum")String[] newRejectNum,
    		@RequestParam("newReceiveNum")String[] newReceiveNum,
    		@RequestParam("goodsFactFee")String[] newFactFee,
    		@RequestParam("depFirstId")String depFirstId,
    		AdminAgent adminAgent
    	) throws Exception {

		String message = "";
		Long stockId = Long.parseLong(id);
        ShoppingList shoppingList = shoppingListService.getShoppingList(stockId);
        // ��鶩���Ƿ��ǵȴ�����״̬
        if (!EnumStockStatus.STOCK_WAIT_CHECK.getKey().equals(shoppingList.getStatus())) {
            message = "yssb";
            return "redirect:/stock/search_stock_detail.html?stockId="+stockId+"&message="+message;
        }
        if (ids != null && ids.length > 0) {
            for (int i = 0; i < ids.length; i++) {
                if (Long.parseLong(newMissingNum[i]) + Long.parseLong(newRejectNum[i])
                    + Long.parseLong(newReceiveNum[i]) != Long.parseLong(amount[i])) {
                    message = "slbd";
                    return "stockDetail";
                } else {
                    ShoppingDetail shoppingDetail = shoppingDetailService
                        .getShoppingDetail(Long.parseLong(ids[i]));
                    if (shoppingDetail != null) {
                        shoppingDetail.setMissingNum(Long.parseLong(newMissingNum[i]));
                        shoppingDetail.setRejectNum(Long.parseLong(newRejectNum[i]));
                        shoppingDetail.setReceiveNum(Long.parseLong(newReceiveNum[i]));
                        shoppingDetail.setFactFee(Double.parseDouble(newFactFee[i]));
                        shoppingDetailService.editShoppingDetail(shoppingDetail);
                    }
                }
            }
        }
        //ȡ��ѡ���һ���ֿ�ID
        Long depFirstIdL = null;
        if (StringUtil.isNotBlank(depFirstId)) {
            depFirstIdL = Long.parseLong(depFirstId);
        }else{
        	message = "yjck";
        	return "redirect:/stock/search_stock_detail.html?stockId="+stockId+"&message="+message;
        }
        shoppingList.setDepFirstId(depFirstIdL);

        // ������������������Ӧ����ⵥ
        if (shoppingList != null) {
            List<ShoppingDetail> shoppingDetails = shoppingDetailService.getShopDetailsByShopListId(stockId);
            // ��������������Ĳ�Ʒ���
            List<ShoppingDetail> shopDetailList = new ArrayList<ShoppingDetail>();
            if (shoppingDetails.size() > 0) {
                for (ShoppingDetail detail : shoppingDetails) {
                    if (detail.getReceiveNum() > 0) {
                        shopDetailList.add(detail);
                    }
                }

                Map map = new HashMap();
                map.put("billNum", codeManager.buildCodeByDateAndType(CodeManager.RUKU_CODE, 6,
                    RUKU));
                map.put("shoppingList", shoppingList);
                map.put("userName", adminAgent.getUsername());
                map.put("shopDetailList", shopDetailList);
                map.put("shoppingDetails", shoppingDetails);
                boolean succFlag = shoppingListService.checkShoppingList(map);
            }
        }
        // ���ղ�Ʒ�����޸Ĳɹ�����״̬
        shoppingList.setFactArriveTime_str(factArriveTime);
        shoppingList.setStatus(EnumStockStatus.STOCK_CHECKED.getKey());
        shoppingListService.editShoppingList(shoppingList,null,null);
        return "redirect:/stock/search_stock_detail.html?stockId="+stockId+"&message="+message;
    }

	/**
     * �˻�����
     *
     * @return
     * @throws Exception
     */
	@RequestMapping(value="/stock/refund")
    public String refund(
    		HttpServletRequest request,
    		AdminAgent adminAgent
    ) throws Exception {
        String refundType = request.getParameter("type");
        String id = request.getParameter("stockId");

        String message = "";
        Long stockId = 0L;
        Long refundId = 0L;
        if (id != null || id.length() != 0) {
            stockId = Long.parseLong(id);
        }
        ShoppingList shoppingList = shoppingListService.getShoppingList(stockId);
        if (shoppingList != null) {
        	Map parMap = new HashMap();
            // ����Ƿ��������ղ���
            parMap.put("shoppingId", String.valueOf(shoppingList.getId()));
            if ("jushou".equals(refundType)) {
                parMap.put("type", EnumShoppingRefund.REFUND_JUSHOU.getKey());
                int jushouNum = shoppingRefundDetailService
                    .getCountRefundByShoppingIdAndStatus(parMap);// ����
                if (jushouNum > 0) {
                    message = "jsth";
                    return "redirect:/stock/search_stock_detail.html?stockId="+stockId+"&message="+message;
                }
            } else if ("danju".equals(refundType)) {
                parMap.put("type", EnumShoppingRefund.REFUND_DINGDAN.getKey());
                int danjuNum = shoppingRefundDetailService
                    .getCountRefundByShoppingIdAndStatus(parMap);// ����
                if (danjuNum > 0) {
                    message = "djth";
                    return "redirect:/stock/search_stock_detail.html?stockId="+stockId+"&message="+message;
                }
            }
            // �˻�������Ϣ
            ShoppingRefund shoppingRefund = new ShoppingRefund();
            shoppingRefund.setRefNum(codeManager.buildCodeByDateAndType(CodeManager.CAITUI_CODE, 6,
                CAITUI));
            shoppingRefund.setSupplierId(shoppingList.getSupplierId());
            if ("jushou".endsWith(refundType)) {
                shoppingRefund.setType(EnumShoppingRefund.REFUND_JUSHOU.getKey());// �����˻�
            } else {
                shoppingRefund.setType(EnumShoppingRefund.REFUND_DINGDAN.getKey());// �����˻�
            }
            shoppingRefund.setRefTime(new Date());
            shoppingRefund.setGmtCreate(new Date());
            shoppingRefund.setCreater(adminAgent.getUsername());
            shoppingRefund.setStatus(EnumShoppingRefundStatus.STOCK_FINISHED.getKey());
            refundId = shoppingRefundService.addShoppingRefund(shoppingRefund);
            if (refundId > 0) {
                List<ShoppingDetail> shoppingDetails = shoppingDetailService.getShopDetailsByShopListId(stockId);
                // �˻���Ʒ��Ϣ
                ShoppingRefundDetail shoppingRefundDetail;
                for (ShoppingDetail shopDetail : shoppingDetails) {
                    shoppingRefundDetail = new ShoppingRefundDetail();
                    shoppingRefundDetail.setGoodsInstanceId(shopDetail.getGoodsInstanceId());
                    if (shopDetail.getGoodsInstanceId() > 0) {
                        GoodsInstance g = goodsInstanceManager.getInstance(shopDetail
                            .getGoodsInstanceId());
                        shoppingRefundDetail.setGoodsCode(g.getCode());
                        shoppingRefundDetail.setUnits(g.getGoodsUnit());
                    }
                    shoppingRefundDetail.setGoodsId(shopDetail.getGoodsId());
                    shoppingRefundDetail.setShopRefId(refundId);
                    shoppingRefundDetail.setShoppingId(shopDetail.getShoppingId());
                    shoppingRefundDetail.setRefPrice(shopDetail.getUnitPrice());
                    shoppingRefundDetail.setReason(EnumShoppingRefundReason.REASON_OTHER.getKey());
                    if ("jushou".endsWith(refundType)) {
                        shoppingRefundDetail.setRefNum(shopDetail.getRejectNum());// �����˻�
                        shoppingRefundDetail.setDueFee(shopDetail.getRejectNum()
                                                       * shopDetail.getUnitPrice());
                        shoppingRefundDetail.setFactFee(shopDetail.getRejectNum()
                                                        * shopDetail.getUnitPrice());
                        if (shopDetail.getRejectNum() > 0) {
                            shoppingRefundDetailService
                                .addShoppingRefundDetail(shoppingRefundDetail);
                        }
                    } else {
                        shoppingRefundDetail.setRefNum(shopDetail.getMissingNum());// ��������
                        shoppingRefundDetail.setDueFee(shopDetail.getMissingNum()
                                                       * shopDetail.getUnitPrice());
                        shoppingRefundDetail.setFactFee(shopDetail.getMissingNum()
                                                        * shopDetail.getUnitPrice());
                        if (shopDetail.getMissingNum() > 0) {
                            shoppingRefundDetailService
                                .addShoppingRefundDetail(shoppingRefundDetail);
                        }
                    }
                }
            }
        }

        return "redirect:/stock/refundDetail.html?stockId="+stockId+"&refundId="+refundId;
    }

	/**
     * �˻�������ҳ��
     *
     * @return
     * @throws Exception
     */
	@RequestMapping(value="/stock/refundDetail")
    public String refundDetail(
    		HttpServletRequest request,
    		Model model
    	) throws Exception {
        String actionType = request.getParameter("actionType");
        String id = request.getParameter("stockId");

        Long stockId = 0L;
        Long refundId = null;
        if (id != null || id.length() != 0) {
            stockId = Long.parseLong(id);
        }
        if (refundId == null) {
            refundId = Long.parseLong(request.getParameter("refundId"));
        }

        ShoppingRefund shoppingRefund = shoppingRefundService.getShoppingRefund(refundId);
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("shopRefId", refundId);

        if (shoppingRefund != null) {
        	 List<ShoppingRefundDetail> shoppingRefundDetailLists = shoppingRefundDetailService.getRefundDetail(paramMap);
        	 model.addAttribute("shoppingRefundDetailLists", shoppingRefundDetailLists);
        }
        model.addAttribute("enumShoppingRefundReason", EnumShoppingRefundReason.toMap());
        model.addAttribute("shoppingRefund", shoppingRefund);
        model.addAttribute("enumShoppingRefund", EnumShoppingRefund.toMap());
        model.addAttribute("enumShopRefundMap", EnumShoppingRefundStatus.toMap());
        model.addAttribute("stockId", stockId);
        model.addAttribute("refundId", refundId);

        if ("detail".equals(actionType)) {
            return "stock/stock_refund_detail";
        }
        return "stock/stock_refund";
    }

	/**
	 * �༭�ɹ�������Ʒ��Ϣ��ʼ��ҳ��
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/stock/edit_stock_page")
	public String editStockPage(@RequestParam("stockId") String id, Model model)
			throws Exception {

		Long stockId = Long.parseLong(id);

		// if (id == null || id.length() == 0) {
		// if (stockId == null) {
		// return ERROR;
		// } else {
		// id = stockId.toString();
		// }
		// }

		// �༭�Ĳɹ���������
		ShoppingList shoppingList = shoppingListService
				.getShoppingList(stockId);
		if (shoppingList != null) {
			List<ShoppingMoreDetail> shoppingMoreDetails = getNewShoppingMoreDetailLists(shoppingDetailService
					.getShoppingDetailsByShoppingListId(stockId));
			model.addAttribute("shoppingMoreDetails", shoppingMoreDetails);
		}

		model.addAttribute("shoppingList", shoppingList);
		model.addAttribute("enumStockStatusMap", EnumStockStatus.toMap());
		model.addAttribute("storTypeMap",  EnumStorType.toMap());
		model.addAttribute("stockNew", EnumStockStatus.STOCK_NEW.getKey());

		return "/stock/stock_edit";
	}

	/**
     * �༭�ɹ�������Ϣ
     *
     * @return
     * @throws Exception
     */
	@RequestMapping(value="/stock/stock_edit")
    public String editStock(
    		@ModelAttribute("ShoppingList")ShoppingList shoppingList
    	) throws Exception {
        Long stockId = shoppingList.getId();
        shoppingListService.editShoppingListAllInfo(shoppingList);
        return "redirect:/stock/search_stock_detail.html?stockId="+stockId;
    }

	/**
     * �༭�ɹ���������״̬
     *
     * @return
     * @throws Exception
     */
	@RequestMapping(value = "/stock/editFinanceStatus")
    public @ResponseBody String editFinanceStatus(
    		HttpServletRequest request
    	) throws Exception {
		String message = "";
        String shopId = request.getParameter("param");
        if (shopId == null || shopId.length() == 0) {
            message = "nothing";
            return message;
        }
        ShoppingList shoppingList = shoppingListService.getShoppingList(Long.parseLong(shopId));
        if (shoppingList == null) {
            message = "nothing";
            return message;
        } else if (shoppingList.getFinanceStatus().equals("y")) {
            message = "already true";
            return message;
        } else if (!EnumStockStatus.STOCK_FINISHED.getKey().equals(shoppingList.getStatus())) {
            message = "wrong";
            return message;
        } else if (!shoppingList.getStorType().equals("f")) {
            message = "notype";
            return message;
        } else {
            shoppingList.setFinanceStatus("y");
            shoppingListService.editShoppingList(shoppingList,null,null);
            message = "true";
        }
        return message;
    }

	/**
     * �༭�ɹ�����״̬
     *
     * @return
     * @throws Exception
     */
	@RequestMapping(value = "/stock/edit_stock_attribute")
    public String editStockAttribute(
    		@RequestParam("actionType")String actionType,
    		@RequestParam("stockId")String id
    	) throws Exception {
		String message = "";
		Long stockId = Long.parseLong(id);
		ShoppingList shoppingList = null;

        if (shoppingList == null || shoppingList.getId() <= 0) {
            shoppingList = shoppingListService.getShoppingList(stockId);
        }
        // �ȴ�����״̬
        if ("waitCheck".equals(actionType)) {
            if (EnumStockStatus.STOCK_NEW.getKey().equals(shoppingList.getStatus())) {
                shoppingList.setStatus(EnumStockStatus.STOCK_WAIT_CHECK.getKey());
                // �༭����״̬
                shoppingListService.editShoppingList(shoppingList,null,null);
                List<ShoppingDetail> shoppingDetails = shoppingDetailService.getShopDetailsByShopListId(stockId);
                // �ɹ�����������Ĳ�Ʒ�޸���;���
                if (shoppingDetails.size() > 0) {
                    for (ShoppingDetail detail : shoppingDetails) {
                        if (detail.getAmount() > 0 && detail.getGoodsId() > 0) {
                            goodsInstanceManager.updateWayNumById(detail.getGoodsInstanceId(),
                                detail.getAmount());
                        }
                    }
                }
            } else {
                message = "bjsb";
            }
        }
        // �ɹ����״̬
        else if ("finished".equals(actionType)) {
        	Map parMap = new HashMap();
        	// �ж��Ƿ�������
        	parMap.put("relationNum", shoppingList.getShoppingNum());
        	parMap.put("type", EnumInDepository.IN_SHOPPING.getKey());
        	InDepository obj = inDepositoryManager.getInDepository(parMap);
        	if(obj == null || EnumInStatus.IN_FINISHED.getKey().equals(obj.getStatus())){
        		shoppingList.setStatus(EnumStockStatus.STOCK_FINISHED.getKey());
                // �༭����״̬
                shoppingListService.editShoppingList(shoppingList,null, actionType);
                
        	}else{
        		message = "wrk";
        	}
        }
        return "redirect:/stock/search_stock_detail.html?stockId="+stockId+"&shoppingNum="+shoppingList.getShoppingNum();
    }

	/**
     * ɾ���ɹ���Ʒ
     *
     * @return "success" if no exceptions thrown
     */
	@RequestMapping(value = "/stock/delete_stock_goods")
    public String deleteStockGoods(
    		@RequestParam("goodsId")String goodsId,
    		@RequestParam("shopListId")String shoppListId,
    		@RequestParam("actionType")String actionType
    	) throws Exception{

		Long stockId = 0L;
        // ��ȡɾ����Ad��id����
        Long shoppingDetailId = null;
        if (StringUtils.isNotBlank(goodsId)) {
            shoppingDetailId = Long.parseLong(goodsId);
        }
        if (StringUtils.isNotBlank(shoppListId)) {
            stockId = Long.parseLong(shoppListId);
        }
        ShoppingList shoppingList = shoppingListService.getShoppingList(stockId);
        if (shoppingList != null && EnumStockStatus.STOCK_NEW.getKey().equals(shoppingList.getStatus())) {
            // ����ɾ������
            shoppingDetailService.removeShoppingDetail(shoppingDetailId);
        }




        if ("add".equals(actionType)) {
        	return "redirect:/stock/stock_goods_add_page.html?id="+stockId;
        }
        return "redirect:/stock/edit_stock_page.html?stockId="+stockId;
    }

	/**
     * �ɹ�����Ӧ�����ѯ�������
     *
     * @return String �ɹ����
     * @throws Exception
     * @author
     */
	@RequestMapping(value="/stock/exportDueSearchList")
    public void exportDueSearchList(
    		@ModelAttribute("shoppingListQuery") ShoppingListQuery shoppingListQuery,
    		@RequestParam(value = "page", required = false, defaultValue = "1") int currPage,
    		HttpServletResponse res,
    		Model model
    	) throws Exception {
        OutputStream outwt = null;
        try {
            Date da = new Date();
            outwt = res.getOutputStream();
            SimpleDateFormat df = new SimpleDateFormat("yyyy_MM_dd");
            String date = df.format(da);
            res.setContentType("application/octet-stream;charset=utf-8");
            List<String[]> storageExportList = new ArrayList<String[]>();
            res.setHeader("Content-disposition", "attachment; filename=Finance_Shopping_Search_"
                                                 + date + ".xls");
            List<Admin> userList = adminService.getAdminUserList();
            model.addAttribute("userList", userList);

            QueryPage queryPage = shoppingListService.getDueSearchListsByParameterMap(shoppingListQuery, 1, Integer.MAX_VALUE);
            List<ShoppingList> shoppingLists = (List<ShoppingList>) queryPage.getItems();
            String[] title = { "�ɹ�������", "��ⵥ���", "��Ӧ������", "�ɹ�ʱ��", "��ͬ���", "Ӧ�����(��)", "ʵ�����(��)", "������",
                    "����״̬", "����״̬" };
            storageExportList.add(title);
            if (shoppingLists != null) {
            	Map enumStockStatusMap = EnumStockStatus.toMap();
                for (ShoppingList shopList : shoppingLists) {
                    String[] data = {
                            shopList.getShoppingNum(),
                            shopList.getBillNum(),
                            shopList.getSupplierName(),
                            shopList.getShoppingTime_str(),
                            shopList.getPrimitiveNum(),
                            (shopList.getSumDueFee() == null ? "0.0" : shopList.getSumDueFee() + ""),
                            (shopList.getSumFactFee() == null ? "0.0" : shopList.getSumFactFee()
                                                                       + ""),
                            shopList.getCreater(), (String) enumStockStatusMap.get(shopList.getStatus()),
                            ("y".equals(shopList.getFinanceStatus()) ? "��ȷ��" : "δȷ��") };
                    storageExportList.add(data);
                }
            }
            goodsBatch.exportExcel(outwt, storageExportList);
            outwt.flush();
        } catch (Exception e) {
//            request.setAttribute("errorMessage", "����ʧ�ܣ�");
//            log.error(e);
        	System.out.println(e);
        } finally {
//            close(outwt);
            outwt.close();
        }
    }

	/**
     * �ɹ���������
     *
     * @return String �ɹ����
     * @throws Exception
     * @author
     */
	@RequestMapping(value = "/stock/export_shopping_list")
    public String exportShoppingList(
    		@ModelAttribute("shoppingListQuery") ShoppingListQuery shoppingListQuery,
			HttpServletResponse response,
			Model model,
			AdminAgent adminAgent
    	) throws Exception {
        OutputStream outwt = null;
        try {
            Date da = new Date();
            outwt = response.getOutputStream();
            SimpleDateFormat df = new SimpleDateFormat("yyyy_MM_dd");
            String date = df.format(da);
            response.setContentType("application/octet-stream;charset=utf-8");
            List<String[]> storageExportList = new ArrayList<String[]>();
            response.setHeader("Content-disposition", "attachment; filename=ShoppingList_" + date
                                                 + ".xls");

            List<Admin> userList = adminService.getAdminUserList();
            model.addAttribute("userList", userList);

            // ��������Ϣ��������
            if (StringUtil.isEmpty((String) shoppingListQuery.getCreater())) {
                if (this.isHasTeam(adminAgent)) {
                    shoppingListQuery.setSameTeamUsers(this.getSameTeamUsers(adminAgent));
                }
            } else if (this.isHasTeam(adminAgent) && !this.getSameTeamUsers(adminAgent).contains(shoppingListQuery.getCreater())) {
                model.addAttribute("message","Ҫ��ѯ�ļ�¼�����˺��㲻��ͬһ�飬���ܲ鿴�����ݣ�");
                model.addAttribute("queryObject", shoppingListQuery);
                return "/stock/stock_search";
            }

            QueryPage query = shoppingListService.getShoppingListsByParameterMap(shoppingListQuery, 1,Integer.MAX_VALUE);
            String[] title = { "�ɹ�������", "��Ӧ������", "�ɹ�ʱ��", "Ԥ�ڵ���ʱ��", "ʵ�ʵ���ʱ��", "��ͬ���", "������", "�������",
                    "����״̬", "����״̬" };
            storageExportList.add(title);

            List<ShoppingList> shoppingLists = (List<ShoppingList>)query.getItems();
            Map<String,String> storTypeMap = EnumStorType.toMap();
            Map<String,String> enumStockStatusMap = EnumStockStatus.toMap();

            if (shoppingLists != null) {
                for (ShoppingList shopList : shoppingLists) {
                    String[] data = { shopList.getShoppingNum(), shopList.getSupplierName(),
                            shopList.getShoppingTime_str(), shopList.getArriveTime_str(),
                            shopList.getFactArriveTime_str(), shopList.getPrimitiveNum(),
                            shopList.getCreater(), storTypeMap.get(shopList.getStorType()),
                            enumStockStatusMap.get(shopList.getStatus()),
                            ("y".equals(shopList.getFinanceStatus()) ? "��ȷ��" : "δȷ��") };
                    storageExportList.add(data);
                }
            }
            goodsBatch.exportExcel(outwt, storageExportList);
            outwt.flush();
        } catch (Exception e) {
//            request.setAttribute("errorMessage", "����ʧ�ܣ�");
//            log.error(e);
        } finally {
//            close(outwt);
            outwt.close();
        }
        return "/stock/stock_search";
    }

	/**
     * �ɹ�������ϸ����
     *
     * @return String �ɹ����
     * @throws Exception
     * @author xieyx 2009/07/25
     */
	@RequestMapping(value = "/stock/export_shopping_detail_search")
    public void exportShoppingDetailSearch(
    		@ModelAttribute("stockDetailSearchQuery") StockDetailSearchQuery stockDetailSearchQuery,
			HttpServletResponse response
    			) throws Exception {
        OutputStream outwt = null;
        try {
            Date da = new Date();
            outwt = response.getOutputStream();
            SimpleDateFormat df = new SimpleDateFormat("yyyy_MM_dd");
            String date = df.format(da);
            response.setContentType("application/octet-stream;charset=utf-8");
            List<String[]> storageExportList = new ArrayList<String[]>();
            response.setHeader("Content-disposition", "attachment; filename=ShoppingDetailSearch_"
                                                 + date + ".xls");
            QueryPage query = shoppingListService
                .getShoppingDetailSearchRusult(stockDetailSearchQuery, 1,Integer.MAX_VALUE);
            List<ShoppingDetailSearch> shoppingDetailSearchLists = (List<ShoppingDetailSearch>)query.getItems();
            if (shoppingDetailSearchLists != null && shoppingDetailSearchLists.size() > 0) {
                List<ShoppingDetailSearch> detailSearchLists = new ArrayList<ShoppingDetailSearch>();
                for (ShoppingDetailSearch detailSearch : shoppingDetailSearchLists) {
                    detailSearch.setCatCode(categoryManager.getCatFullNameByCatcodeSimple(
                        detailSearch.getCatCode(), ">"));
                    detailSearch.setAttrs(attributeManager
                        .getFullAttributeStringByAttrs(detailSearch.getAttrs()));
                    detailSearchLists.add(detailSearch);

                }
                shoppingDetailSearchLists = detailSearchLists;
            }
            String[] title = { "�ɹ�������", "��Ӧ������", "�ɹ�ʱ��", "��Ʒ����", "��Ʒ����", "��Ŀ", "����", "�������", "��λ",
                    "�ɹ�����", "��������", "����", "Ӧ�����", "ʵ�����" };
            storageExportList.add(title);
            Map<String,String> storTypeMap = EnumStorType.toMap();

            if (shoppingDetailSearchLists != null) {
                for (ShoppingDetailSearch obj : shoppingDetailSearchLists) {
                    String[] data = { obj.getShoppingNum(), obj.getSupplierName(),
                            obj.getShoppingTime_str(), obj.getGoodsInstanceCode(),
                            obj.getInstanceName(), obj.getCatCode(), obj.getAttrs(),
                            storTypeMap.get(obj.getStorType()), obj.getUnits(),
                            obj.getAmount() + "", obj.getReceiveNum() + "",
                            obj.getUnitPrice() + "", obj.getDueFee() + "", obj.getFactFee() + "" };
                    storageExportList.add(data);
                }
            }
            goodsBatch.exportExcel(outwt, storageExportList);
            outwt.flush();
        } catch (Exception e) {
//            request.setAttribute("errorMessage", "����ʧ�ܣ�");
//            log.error(e);
        } finally {
//            close(outwt);
            outwt.close();
        }
    }

	/**
     * �ɹ��������ܵ���
     *
     * @return String �ɹ����
     * @throws Exception
     * @author xieyx 2009/07/25
     */
	@RequestMapping(value = "/stock/export_shopping_gather_search")
    public void exportShoppingGatherSearch(
    		@ModelAttribute("stockDetailSearchQuery") StockDetailSearchQuery stockDetailSearchQuery,
			HttpServletResponse response
    	) throws Exception {
        OutputStream outwt = null;
        try {
            Date da = new Date();
            outwt = response.getOutputStream();
            SimpleDateFormat df = new SimpleDateFormat("yyyy_MM_dd");
            String date = df.format(da);
            response.setContentType("application/octet-stream;charset=utf-8");
            response.setHeader("Content-disposition", "attachment; filename=ShoppingGatherSearch_"
                                                 + date + ".xls");
            List<String[]> storageExportList = new ArrayList<String[]>();
            QueryPage query = shoppingListService.getShoppingGatherSearchRusult(stockDetailSearchQuery, 1,Integer.MAX_VALUE);

            List<ShoppingGatherSearch> shoppingGatherSearchLists = (List<ShoppingGatherSearch>)query.getItems();

            if (shoppingGatherSearchLists != null && shoppingGatherSearchLists.size() > 0) {
                List<ShoppingGatherSearch> gatherSearchLists = new ArrayList<ShoppingGatherSearch>();
                for (ShoppingGatherSearch gatherSearch : shoppingGatherSearchLists) {
                    gatherSearch.setCatCode(categoryManager.getCatFullNameByCatcodeSimple(
                        gatherSearch.getCatCode(), ">"));
                    gatherSearch.setAttrs(attributeManager
                        .getFullAttributeStringByAttrs(gatherSearch.getAttrs()));
                    gatherSearchLists.add(gatherSearch);
                }
                shoppingGatherSearchLists = gatherSearchLists;
            }

            Map storTypeMap = EnumStorType.toMap();

            String[] title = { "��Ʒ����", "��Ʒ����", "��Ŀ", "����", "�������", "��λ", "����", "Ӧ�����", "ʵ�����" };
            storageExportList.add(title);
            if (shoppingGatherSearchLists != null) {
                for (ShoppingGatherSearch obj : shoppingGatherSearchLists) {
                    String[] data = { obj.getGoodsInstanceCode(), obj.getInstanceName(),
                            obj.getCatCode(), obj.getAttrs(), (String)storTypeMap.get(obj.getStorType()),
                            obj.getUnits(), obj.getAmount() + "", obj.getDueFee() + "",
                            obj.getFactFee() + "" };
                    storageExportList.add(data);
                }
            }
            goodsBatch.exportExcel(outwt, storageExportList);
            outwt.flush();
        } catch (Exception e) {
//            request.setAttribute("errorMessage", "����ʧ�ܣ�");
//            log.error(e);
        } finally {
//            close(outwt);
            outwt.close();
        }
    }

	/**
     * �ɹ��������鵼��
     *
     * @return String �ɹ����
     * @throws Exception
     * @author fanyj 2009/07/25
     */
	@RequestMapping(value = "/stock/exportShoppingDetail")
    public void exportShoppingDetail(
    		@ModelAttribute("stockDetailSearchQuery") StockDetailSearchQuery stockDetailSearchQuery,
    		@RequestParam("stockId") String id,
			HttpServletResponse response
			) throws Exception {
        OutputStream outwt = null;

        ShoppingList shoppingList = null;
        if (StringUtils.isNotBlank(id)) {
            shoppingList = shoppingListService.getShoppingList(new Long(id));
        }

        List<ShoppingMoreDetail> shoppingMoreDetails = null;
        if (shoppingList != null) {
            shoppingMoreDetails = this.getNewShoppingMoreDetailLists(shoppingDetailService
                .getShoppingDetailsByShoppingListId(shoppingList.getId()));
        }
        try {
            Date da = new Date();
            outwt = response.getOutputStream();
            SimpleDateFormat df = new SimpleDateFormat("yyyy_MM_dd");
            String date = df.format(da);
            response.setContentType("application/octet-stream;charset=utf-8");
            List<Object[]> storageExportList = new ArrayList<Object[]>();
            response.setHeader("Content-disposition", "attachment; filename=ShoppingDetail_" + date
                                                 + ".xls");
            String[] title = { "�ɹ�������", "��Ӧ������", "��Ʒ����", "��Ʒ����", "��Ŀ", "����", "��λ", "�ɹ�����", "ȱ������",
                    "��������", "��������"
//                    , "Ӧ�����", "ʵ�����"
                    };
            storageExportList.add(title);
            if (shoppingMoreDetails != null) {
                for (ShoppingMoreDetail obj : shoppingMoreDetails) {
                    Object[] data = { shoppingList.getShoppingNum(),
                            shoppingList.getSupplierName(), obj.getGoodsInstanceCode(),
                            obj.getInstanceName(), obj.getCatCode(), obj.getAttrs(),
                            obj.getUnits(), obj.getAmount(), obj.getMissingNum(),
                            obj.getRejectNum(), obj.getReceiveNum()
//                            ,DoubleUtil.round(obj.getDueFee(), 2), DoubleUtil.round(obj.getFactFee(),2)
                            };
                    storageExportList.add(data);
                }
            }
            goodsBatch.exportExcelByObject(outwt, storageExportList);
            outwt.flush();
        } catch (Exception e) {
//            request.setAttribute("errorMessage", "����ʧ�ܣ�");
//            log.error(e);
        } finally {
//            close(outwt);
            outwt.close();
        }
    }

	/**
     * �����뵼��
     *
     * @return String �ɹ����
     * @throws Exception
     * @author xieyx 2009/07/25
     */
	@RequestMapping(value="/stock/exportBarCode")
    public void exportBarCode(
    		@RequestParam("stockId")String id,
    		HttpServletResponse res
    	) throws Exception {
        OutputStream outwt = null;

        try {
            Date da = new Date();
            String batchNum = DateUtil.convertDateToBatch(da).toString()
                              + codeManager.buildCode(CodeManager.PICI_CODE, 4, "");
            outwt = res.getOutputStream();
            res.setContentType("application/octet-stream;charset=utf-8");
            res.setHeader("Content-disposition", "attachment; filename=BarCode_" + batchNum
                                                 + ".xls");
            List<String[]> storageExportList = new ArrayList<String[]>();
            ShoppingList shoppingList = shoppingListService.getShoppingList(new Long(id));
            List<ShoppingMoreDetail> shoppingMoreDetails = shoppingDetailService
                .getShoppingDetailsByShoppingListId(new Long(id));
            String[] title = { "��һ��", "�ڶ���", "������", "����" };
            storageExportList.add(title);
            if (shoppingMoreDetails != null) {
                Goods goods = null;
                String goodsSn = "";
                String attrsValue = "";
                String supplierId = "0000" + shoppingList.getSupplierId();
                supplierId = supplierId.substring(supplierId.length() - 4, supplierId.length());// ��Ӧ��ID
                for (ShoppingMoreDetail obj : shoppingMoreDetails) {
                    goods = goodsManager.getGoods(obj.getGoodsId());
                    attrsValue = attributeManager.getAllAttributeValueByAttrs(obj.getAttrs());// ����ֵ
                    if (goods != null) {
                        goodsSn = goods.getGoodsSn();// ��Ʒ����
                    }
                    String[] data = { (supplierId + batchNum).replaceAll("\r", ""),
                            (goodsSn + attrsValue).replaceAll("\r", ""),
                            obj.getGoodsInstanceCode(),
                            String.valueOf(obj.getAmount()) };
                    storageExportList.add(data);
                }
            }
            goodsBatch.exportExcel(outwt, storageExportList);
            outwt.flush();
        } catch (Exception e) {
//            request.setAttribute("errorMessage", "����ʧ�ܣ�");
//            log.error(e);
        } finally {
//            close(outwt);
            outwt.close();
        }
    }

	/**
     * JQUERY��ʽ�޸�ʵ�����
     *
     * @param shoppingDetailId �ɹ���Ʒ��ID
     * @param factFee �µ�ʵ�����
     * @return ���ز�����Ϣ
     */
	@RequestMapping(value="/stock/editDwrFactFee")
    public String editDwrFactFee(
    		@RequestParam("param1")String shoppingDetailIdstr,
    		@RequestParam("param2")String factFeestr
    	)throws Exception {
		String message = "";
        Long shoppingDetailId = Long.parseLong(shoppingDetailIdstr);
        Double factFee = Double.valueOf(factFeestr);
        ShoppingDetail shoppingDetail = shoppingDetailService.getShoppingDetail(shoppingDetailId);
        String result = checkBeforeEdit(shoppingDetailId, factFee.toString(), "add");
        if (result.length()>0) {
            message = result;
            return message;
        }
        shoppingDetail.setFactFee(factFee);
        shoppingDetailService.editShoppingDetail(shoppingDetail);
        message = "['true','edit success!']";
        return message;
    }

    /**
     * JQUERY��ʽ�޸ĵ���
     *
     * @param shoppingDetailId �ɹ���Ʒ��ID
     * @param factFee �µ�ʵ�����
     * @return ���ز�����Ϣ
     */
	@RequestMapping(value="/stock/editDwrUnitPrice")
    public @ResponseBody String editDwrUnitPrice(
    		@RequestParam("param1")String shoppingDetailIdstr,
    		@RequestParam("param2")String nitPricestr
    	)throws Exception {
		String message = "";
        Long shoppingDetailId = Long.parseLong(shoppingDetailIdstr);
        Double nitPrice = Double.valueOf(nitPricestr);
        ShoppingDetail shoppingDetail = shoppingDetailService.getShoppingDetail(shoppingDetailId);
        String result = checkBeforeEdit(shoppingDetailId, nitPrice.toString(), "add");
        if (result.length()>0) {
            message = result;
            return message;
        }
        shoppingDetail.setUnitPrice(nitPrice);
        shoppingDetail.setDueFee(nitPrice * shoppingDetail.getAmount());
        shoppingDetail.setFactFee(nitPrice * shoppingDetail.getAmount());
        shoppingDetailService.editShoppingDetail(shoppingDetail);
        message = "['true','edit success!']";
        return message;
    }

	/**
     * JQUERY��ʽ�޸Ĳɹ�����
     *
     * @param shoppingDetailId �ɹ���Ʒ��ID
     * @param amount �µĲɹ�����
     * @return ���ز�����Ϣ
     */
	@RequestMapping(value = "/stock/editDwrAmount")
    public @ResponseBody String editDwrAmount(
    		@RequestParam("param1")String shoppingDetailIdstr,
    		@RequestParam("param2")String amountstr
    	) throws Exception{
        Long shoppingDetailId = Long.parseLong(shoppingDetailIdstr);
        Long amount = Long.parseLong(amountstr);
        ShoppingDetail shoppingDetail = shoppingDetailService.getShoppingDetail(shoppingDetailId);
        String result = checkBeforeEdit(shoppingDetailId, amount.toString(), "add");

        String message = "";
        if (result.length()>0) {
            message = result;
            return message;
        }
        shoppingDetail.setAmount(amount);
        shoppingDetail.setDueFee(amount * shoppingDetail.getUnitPrice());
        shoppingDetail.setFactFee(amount * shoppingDetail.getUnitPrice());
        shoppingDetailService.editShoppingDetail(shoppingDetail);
        message = "['true','edit success!']";
        return message;
    }

	/**
     * JQUERY��ʽ�������ɹ������Ƿ����
     *
     * @return
     * @throws Exception
     */
	@RequestMapping("/stock/checkStockByDwr")
    public @ResponseBody String checkStockByDwr(
    		@RequestParam("param")String relationShoppingNum
    	)throws Exception {
		String message = "";
        if (relationShoppingNum == null) {
            message = "['false','relationShoppingNum must be not null!']";
            return message;
        }
        // У������ɹ������Ƿ����
        int shoppintCount = shoppingListService.getCountByShoppingNum(relationShoppingNum);
        if (shoppintCount == 0) {
            message = "['false','the relationShopping is not exist!']";
            return message;
        }
        message = "['true','edit success!']";
        return message;
    }

	/**
     * JQUERY��ʽ��������������Ƿ����
     *
     * @return
     * @throws Exception
     */
	@RequestMapping(value="/stock/checkTidByJQuery")
    public @ResponseBody String checkTidByJQuery(
    		@RequestParam("param")String tid
    	)throws Exception {
		String message = "";
        if (tid == null) {
            message = "['false','tid must be not null!']";
            return message;
        }
        // У����������������Ƿ����
        Map parMap = new HashMap();
        parMap.put("tid", tid.trim());
        parMap.put("isWholesale", "w");
        Trade trade = tradeManager.getTradeByMap(parMap);
        if (trade == null) {
            message = "['false','the tid is not exist!']";
            return message;
        }
        message = "['true','edit success!']";
        return message;
    }

	/**
     * JQUERY��ʽ�޸�ʵ�����
     *
     * @param refDetailId �˻���Ʒ��ID
     * @param factFee �µ�ʵ�����
     * @return ���ز�����Ϣ
     */
	@RequestMapping(value="/stock/editDwrRefFactFee")
    public @ResponseBody String editDwrRefFactFee(
    		@RequestParam("param1")String refDetailIdstr,
    		@RequestParam("param2")String factFeestr
    	)throws Exception {
		String message = "";
        Long refDetailId = Long.parseLong(refDetailIdstr);
        Double factFee = Double.valueOf(factFeestr);

        if (refDetailId == null || factFee == null) {
            return "['false','shoppingDetailId and factFee must be not null!']";
        }
        // Ȩ�޹���
        /*
         * User user = this.getLoginUser(); if (user.getIsAdmin().intValue() != 1) { return
         * "['false','only admin can edit it!']"; }
         */
        ShoppingRefundDetail shoppingRefundDetail = shoppingRefundDetailService.getShoppingRefundDetail(refDetailId);
        if (shoppingRefundDetail == null) {
            return "['false','the shoppingRefundDetail is not exist!']";
        }

        shoppingRefundDetail.setFactFee(factFee);
        shoppingRefundDetailService.editShoppingRefundDetail(shoppingRefundDetail);
        message = "['true','edit success!']";
        return message;
    }

    /**
     * JQUERY��ʽ�޸��˻�����
     *
     * @param refDetailId �˻���Ʒ��ID
     * @param factFee �µ��˻�����
     * @return ���ز�����Ϣ
     */
	@RequestMapping(value="/stock/editDwrRefPrice")
    public @ResponseBody String editDwrRefPrice(
    		@RequestParam("param1")String refDetailIdstr,
    		@RequestParam("param2")String refPricestr
    		)throws Exception {
    	String message = "";
        Long refDetailId = Long.parseLong(refDetailIdstr);
        Double refPrice = Double.valueOf(refPricestr);
        if (refDetailId == null || refPrice == null) {
            return "['false','shoppingDetailId and factFee must be not null!']";
        }
        // Ȩ�޹���
        /*
         * User user = this.getLoginUser(); if (user.getIsAdmin().intValue() != 1) { return
         * "['false','only admin can edit it!']"; }
         */
        ShoppingRefundDetail shoppingRefundDetail = shoppingRefundDetailService.getShoppingRefundDetail(refDetailId);
        if (shoppingRefundDetail == null) {
            return "['false','the shoppingRefundDetail is not exist!']";
        }

        shoppingRefundDetail.setRefPrice(refPrice);
        shoppingRefundDetail.setDueFee(refPrice * shoppingRefundDetail.getRefNum());
        shoppingRefundDetail.setFactFee(refPrice * shoppingRefundDetail.getRefNum());
        shoppingRefundDetailService.editShoppingRefundDetail(shoppingRefundDetail);
        message = "['true','edit success!']";
        return message;
    }


    /**
     * JQUERY��ʽ�޸��˻�ԭ��
     *
     * @param refDetailId �˻���Ʒ��ID
     * @param reason �µ�ʵ�����
     * @return ���ز�����Ϣ
     */
    @RequestMapping(value="/stock/editDwrReason")
    public @ResponseBody String editDwrReason(
    		@RequestParam("param1")String refDetailIdstr,
    		@RequestParam("param2")String reason
    	) throws Exception{
    	String message = "";
        Long refDetailId = Long.parseLong(refDetailIdstr);
        if (refDetailId == null || reason == null) {
            return "['false','shoppingDetailId and factFee must be not null!']";
        }
        // Ȩ�޹���
        /*
         * User user = this.getLoginUser(); if (user.getIsAdmin().intValue() != 1) { return
         * "['false','only admin can edit it!']"; }
         */
        ShoppingRefundDetail shoppingRefundDetail = shoppingRefundDetailService.getShoppingRefundDetail(refDetailId);
        if (shoppingRefundDetail == null) {
            return "['false','the shoppingRefundDetail is not exist!']";
        }

        shoppingRefundDetail.setReason(reason);
        shoppingRefundDetailService.editShoppingRefundDetail(shoppingRefundDetail);
        message = "['true','edit success!']";
        return message;
    }

	/*
     * �޸�ǰ�ļ��
     * @param shoppingDetailId
     * @param factFee
     */
    private String checkBeforeEdit(Long shoppingDetailId, String factFee, String type)throws Exception {
        // if (!this.isLoged()) {
        //        if (isAdminUserLoged()) {
        //            return "['false','you have not login!']";
        //        }
        if (shoppingDetailId == null || factFee == null) {
            return "['false','shoppingDetailId and factFee must be not null!']";
        }
        // Ȩ�޹���
        /*
         * User user = this.getLoginUser(); if (user.getIsAdmin().intValue() != 1) { return
         * "['false','only admin can edit it!']"; }
         */
        ShoppingDetail shoppingDetail = shoppingDetailService.getShoppingDetail(shoppingDetailId);
        if (shoppingDetail == null) {
            return "['false','the shoppingDetail is not exist!']";
        }
        ShoppingList shoppingList = shoppingListService.getShoppingList(shoppingDetail.getShoppingId());
        if ("check".equals(type)) {
            // �жϲɹ������Ƿ����½�״̬
            if (!shoppingList.getStatus().trim().equals(EnumStockStatus.STOCK_WAIT_CHECK.getKey())) {
                return "['false','notCheck']";
            }
        } else {
            // �жϲɹ������Ƿ����½�״̬
            if (!shoppingList.getStatus().trim().equals(EnumStockStatus.STOCK_NEW.getKey())) {
                return "['false','notEdit']";
            }
        }
        return "";
    }

//    /*
//     * �޸��˻���¼ǰ�ļ��
//     * @param shoppingDetailId
//     * @param factFee
//     */
//    private String checkRefundBeforeEdit(Long refDetailId, String newValue) {
//        //        if (!this.isLoged()) {
//        //            return "['false','you have not login!']";
//        //        }
//        if (refDetailId == null || newValue == null) {
//            return "['false','shoppingDetailId and factFee must be not null!']";
//        }
//        // Ȩ�޹���
//        /*
//         * User user = this.getLoginUser(); if (user.getIsAdmin().intValue() != 1) { return
//         * "['false','only admin can edit it!']"; }
//         */
//        ShoppingRefundDetail shoppingRefundDetail = shoppingRefundDetailService.getShoppingRefundDetail(refDetailId);
//        if (shoppingRefundDetail == null) {
//            return "['false','the shoppingRefundDetail is not exist!']";
//        }
//
//        return "";
//    }

	/*
	 * �õ�����װ�ļ�����
	 *
	 * @param shoppingMoreDetailLists
	 *
	 * @return
	 */
	private List<ShoppingMoreDetail> getNewShoppingMoreDetailLists(
			List<ShoppingMoreDetail> shoppingMoreDetailLists) throws Exception {
		List<ShoppingMoreDetail> resultList = new ArrayList<ShoppingMoreDetail>();
		if (shoppingMoreDetailLists != null
				&& shoppingMoreDetailLists.size() > 0) {
			for (ShoppingMoreDetail detail : shoppingMoreDetailLists) {
				 detail.setCatCode(categoryManager.getCatFullNameByCatcodeSimple(
				 detail.getCatCode(), ">"));
				 detail.setAttrs(attributeManager.getFullAttributeStringByAttrs(detail.getAttrs()));
				resultList.add(detail);

			}
		}
		return resultList;
	}

	/**
     * �رղɹ���
     * @param id
     * @return
     */
	@RequestMapping(value="/stock/closeDwrShopping")
    public @ResponseBody String closeDwrShopping(
    		@RequestParam("param")String id,
    		AdminAgent adminAgent
    	)throws Exception {

		String message = "";
        ShoppingList shoppingList = shoppingListService.getShoppingList(Long.parseLong(id));
        String oldStatus = shoppingList.getStatus();
        if (!(shoppingList.getStatus().equals(EnumStockStatus.STOCK_NEW.getKey()) || shoppingList
            .getStatus().equals(EnumStockStatus.STOCK_WAIT_CHECK.getKey()))) {
            message = "statuswrong";
            return message;
        }
        if (!(adminAgent.getUsername().equals(shoppingList.getCreater()))) {
            message = "userwrong";
            return message;
        }
        shoppingList.setStatus(EnumStockStatus.STOCK_CLOSE.getKey());
        shoppingListService.editShoppingList(shoppingList,oldStatus,null);
        // �޸Ĺ����������뵥״̬
        if(StringUtil.isNotBlank(shoppingList.getTid())){
            WholesaleApply wholesaleApply = wholesaleApplyManager.getWholesaleApplyByTid(shoppingList.getTid());
            if(wholesaleApply != null){
            	wholesaleApply.setStatus(EnumWholesaleStatus.WHOLESALE_STATUS_FINISH.getKey());
            	wholesaleApplyManager.editWholesaleApply(wholesaleApply);
            }
        }
        message = "success";
        return message;
    }

	/**
	 * ���ն��������Ϣ��ѯ
	 *
	 * @return String �ɹ����
	 * @throws Exception
	 * @author yangak 2009/12/08
	 */
	@RequestMapping(value = "/stock/day_stock_search")
	public String dayStockSearch(
			@ModelAttribute("stockDetailSearchQuery") StockDetailSearchQuery stockDetailSearchQuery,
			@RequestParam(value = "isFirst", required = false, defaultValue = "false") String isFirst,
			@RequestParam(value = "page", required = false, defaultValue = "1") int currPage,
			Model model) {

		if ("true".equals(isFirst)) {
			if (StringUtil.isBlank(stockDetailSearchQuery.getStartTime())
					&& StringUtil.isBlank(stockDetailSearchQuery.getEndTime())) {
				stockDetailSearchQuery.setStartTime(DateUtil.getDiffDate(
						new Date(), -7));
				stockDetailSearchQuery.setEndTime(DateUtil
						.getDateToString(new Date()));
			}
		}

		QueryPage query = orderManager.getOrdersByDate(stockDetailSearchQuery,
				currPage, pageSize);

		if(query.getItems() != null){
			for (Order temp : (List<Order>) query.getItems()) {

				GoodsInstance g = goodsInstanceManager.getInstance(temp
						.getGoodsInstanceId());
				if (g != null) {
					temp.setGoodsInstanceId(g.getId());
					temp.setCode(g.getCode());
					temp.setGoodsInstanceName(g.getInstanceName());
					temp.setShowAllStorageNum(1);
				}

				List<Storage> storageList = storageManager.getStorageWithTrade(temp
						.getGoodsInstanceId(), Boolean.parseBoolean(temp
						.getShowAllStorageNum().toString()));

				if (storageList != null) {
					temp.setStoragelist(storageList);
				}
			}
		}

		model.addAttribute("query", query);
		model.addAttribute("queryObject", stockDetailSearchQuery);
		model.addAttribute("attributeManager", attributeManager);

		return "/stock/day_stock_search";
	}

	/**
	 * ���ն��������Ϣ����
	 *
	 * @return String �ɹ����
	 * @throws Exception
	 * @author yangak 2009/12/09
	 */
	@RequestMapping(value = "/stock/export_day_stock_search")
	public String exportDayStockSearch(
			@ModelAttribute("stockDetailSearchQuery") StockDetailSearchQuery stockDetailSearchQuery,
			@RequestParam(value = "isFirst", required = false, defaultValue = "false") String isFirst,
			HttpServletRequest request, HttpServletResponse response
	) throws Exception {
		OutputStream outwt = null;
		try {

			Date da = new Date();
			outwt = response.getOutputStream();
			SimpleDateFormat df = new SimpleDateFormat("yyyy_MM_dd");
			String date = df.format(da);

			String startTime = stockDetailSearchQuery.getStartTime();
			String endTime = stockDetailSearchQuery.getEndTime();

			// String code = request.getParameter("code");
			response.setContentType("application/octet-stream;charset=utf-8");
			List<String[]> dayStockExportList = new ArrayList<String[]>();
			response.setHeader("Content-disposition",
					"attachment; filename=DayStockSearch_" + date + ".xls");

			if ("true".equals(isFirst)) {

				if (StringUtil.isBlank(startTime)
						&& StringUtil.isBlank(endTime)) {
					startTime = DateUtil.getDiffDate(new Date(), -7);
					endTime = DateUtil.getDateToString(new Date());

					stockDetailSearchQuery.setStartTime(startTime);
					stockDetailSearchQuery.setEndTime(endTime);
				}

			}

			QueryPage query = orderManager.getOrdersByDate(
					stockDetailSearchQuery, 1, Integer.MAX_VALUE);

			if (query.getItems() != null)
				for (Order temp : (List<Order>) query.getItems()) {

					GoodsInstance g = goodsInstanceManager.getInstance(temp
							.getGoodsInstanceId());
					if (g != null) {
						temp.setGoodsInstanceId(g.getId());
						temp.setCode(g.getCode());
						temp.setGoodsInstanceName(g.getInstanceName());
						temp.setShowAllStorageNum(1);
					}

					List<Storage> storageList = storageManager
							.getStorageWithTrade(temp.getGoodsInstanceId(),
									Boolean.parseBoolean(temp.getShowAllStorageNum().toString()));

					if (storageList != null) {
						temp.setStoragelist(storageList);
					}
				}
			String[] preTitle = { "��ʼ����",
					stockDetailSearchQuery.getStartTime(), "��������",
					stockDetailSearchQuery.getEndTime() };
			dayStockExportList.add(preTitle);
			String[] title = { "��Ʒ���", "��Ʒ����", "��Ʒ����", "����������", "������" };
			dayStockExportList.add(title);
			if (query.getItems() != null) {
				for (Order obj : (List<Order>)query.getItems()) {
					StringBuffer s = new StringBuffer();
					for (Storage sto : obj.getStoragelist()) {
						s.append(sto.getDepositoryName()
								+ sto.getStorageNumSum() + "");
					}
					String[] data = {
							obj.getCode() + "",
							obj.getGoodsInstanceName() + "",
							attributeManager.getFullAttributeStringByAttrs(obj
									.getGoodsAttr()) + "",
							obj.getGoodsNumber() + "", s.toString() + "" };

					dayStockExportList.add(data);
				}
			}

			goodsBatch.exportExcel(outwt, dayStockExportList);
			outwt.flush();
		} catch (Exception e) {
			request.setAttribute("errorMessage", "����ʧ�ܣ�");
//			log.error(e);
		} finally {
			outwt.close();
		}
		return "/stock/day_stock_search";
	}

	/*
	 * ��ѯ��������
	 *
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Map getNewMap(Map parMap) {
		Map map = new HashMap();
		map.put("shoppingTimeEnd", parMap.get("shoppingTimeEnd"));
		map.put("shoppingTimeStart", parMap.get("shoppingTimeStart"));
		map.put("supplierId", parMap.get("supplierId"));
		map.put("arriveTimeStart", parMap.get("arriveTimeStart"));
		map.put("supplierName", parMap.get("supplierName"));
		map.put("arriveTimeEnd", parMap.get("arriveTimeEnd"));
		map.put("primitiveNum", parMap.get("primitiveNum"));
		map.put("status", parMap.get("status"));
		map.put("creater", parMap.get("creater"));
		map.put("storType", parMap.get("storType"));
		map.put("shoppingNum", parMap.get("shoppingNum"));
		map.put("financeStatus", parMap.get("financeStatus"));
		map.put("isWholesale", parMap.get("isWholesale"));
		return map;
	}

	/**
     * ��ѯ�ݹ�Ӧ��������б�
     *
     * @return
     * @throws Exception
     */
	@RequestMapping(value = "/stock/due_estimate_search")
    public String dueEstimateSearch(
    		@ModelAttribute("shoppingListQuery") ShoppingListQuery shoppingListQuery,
    		@RequestParam(value = "isFirst", required = false, defaultValue = "false") String isFirst,
    		@RequestParam(value = "page", required = false, defaultValue = "1") int currPage,
    		Model model
    	) throws Exception {
        double gatherSumDueFee = 0;
        double gatherSumFactFee = 0;

        if ("true".equals(isFirst)) {
            Date now = new Date();
            Timestamp nowTs = new Timestamp(now.getTime());
            Date before7 = DateUtil.getDate(now, -7);
            Timestamp beforeTs = new Timestamp(before7.getTime());
            if (StringUtils.isBlank((String) shoppingListQuery.getShoppingTimeStart())) {
            	shoppingListQuery.setShoppingTimeStart(DateUtil.getTimestampToString(beforeTs));
            }
            if (StringUtils.isBlank((String) shoppingListQuery.getShoppingTimeEnd())) {
            	shoppingListQuery.setShoppingTimeEnd(DateUtil.getTimestampToString(nowTs));
            }
        }
        // ȡ�ù���ԱȨ�޵Ļ�Ա�б�
        // TODO �����ȡ�˺�̨�����û�������Ժ���԰��տͻ��������ĵ�
        List<Admin> userList = adminService.getAdminUserList();
        model.addAttribute("userList", userList);

        QueryPage query = shoppingListService.getDueEstimateSearchListsByParameterMap(shoppingListQuery,currPage,pageSize);

        List<ShoppingList> shoppingLists = (List<ShoppingList>)query.getItems();

        if (shoppingLists != null && shoppingLists.size() > 0) {
                for (ShoppingList obj : shoppingLists) {
                    gatherSumDueFee += (obj.getSumDueFee() == null ? 0 : obj.getSumDueFee()
                        .doubleValue());
                    gatherSumFactFee += (obj.getSumFactFee() == null ? 0 : obj.getSumFactFee()
                        .doubleValue());
                }
        }

        model.addAttribute("gatherSumDueFee", gatherSumDueFee);
        model.addAttribute("gatherSumFactFee", gatherSumFactFee);
        model.addAttribute("query", query);
        model.addAttribute("queryObject", shoppingListQuery);
        model.addAttribute("enumStockStatusMap", EnumStockStatus.toMap());

        return "/stock/due_estimate_search";
    }

	/**
     * �ɹ����ݹ��������ȷ��
     * @return
     */
	@RequestMapping(value = "/stock/dueEstimateConfirm")
    public @ResponseBody String dueEstimateConfirm(
    		HttpServletRequest request
    	)throws Exception {
		String message = "";
        String shopId = request.getParameter("param");
        if (shopId == null || shopId.length() == 0) {
            message = "nothing";
            return message;
        }
        ShoppingList shoppingList = shoppingListService.getShoppingList(Long.parseLong(shopId));
        if (shoppingList == null) {
            message = "nothing";
            return message;
        } else if (shoppingList.getFinanceStatus().equals("y")) {
            message = "already true";
            return message;
        } else if (!EnumStockStatus.STOCK_FINISHED.getKey().equals(shoppingList.getStatus())) {
            message = "wrong";
            return message;
        } else if (!shoppingList.getStorType().equals("v")) {
            message = "notype";
            return message;
        } else {
            shoppingList.setFinanceStatus("y");
            shoppingListService.editShoppingList(shoppingList,null,null);
            message = "true";
        }
        return message;
    }

	/**
     * �ݹ��ɹ�����Ӧ�����ѯ�������
     *
     * @return String �ɹ����
     * @throws Exception
     * @author
     */
	@RequestMapping(value="/stock/exportDueEstimateSearchList")
    public void exportDueEstimateSearchList(
    		@ModelAttribute("shoppingListQuery") ShoppingListQuery shoppingListQuery,
    		HttpServletResponse res
    	) throws Exception {
        OutputStream outwt = null;
        try {
            Date da = new Date();
            outwt = res.getOutputStream();
            SimpleDateFormat df = new SimpleDateFormat("yyyy_MM_dd");
            String date = df.format(da);
            res.setContentType("application/octet-stream;charset=utf-8");
            List<String[]> storageExportList = new ArrayList<String[]>();
            res.setHeader("Content-disposition", "attachment; filename=Estimate_Shopping_Search_"
                                                 + date + ".xls");
//            List<Admin> userList = adminService.getAdminUserList();
//            getRequest().setAttribute("userList", userList);

            QueryPage queryPage = shoppingListService.getDueEstimateSearchListsByParameterMap(shoppingListQuery,
                1,Integer.MAX_VALUE);
            List<ShoppingList> shoppingLists = (List<ShoppingList>) queryPage.getItems();
            String[] title = { "�ɹ�������", "��Ӧ������", "�ɹ�ʱ��", "��ͬ���", "Ӧ�����(��)", "ʵ�����(��)", "������",
                    "����״̬", "����״̬" };
            storageExportList.add(title);

            if (shoppingLists != null) {
            	Map enumStockStatusMap = EnumStockStatus.toMap();
                for (ShoppingList shopList : shoppingLists) {
                    String[] data = {
                            shopList.getShoppingNum(),
                            shopList.getSupplierName(),
                            shopList.getShoppingTime_str(),
                            shopList.getPrimitiveNum(),
                            (shopList.getSumDueFee() == null ? "0.0" : shopList.getSumDueFee() + ""),
                            (shopList.getSumFactFee() == null ? "0.0" : shopList.getSumFactFee()
                                                                       + ""),
                            shopList.getCreater(), (String) enumStockStatusMap.get(shopList.getStatus()),
                            ("y".equals(shopList.getFinanceStatus()) ? "��ȷ��" : "δȷ��") };
                    storageExportList.add(data);
                }
            }
            goodsBatch.exportExcel(outwt, storageExportList);
            outwt.flush();
        } catch (Exception e) {
//            request.setAttribute("errorMessage", "����ʧ�ܣ�");
//            log.error(e);
        } finally {
//            close(outwt);
        	outwt.close();
        }
    }

}
