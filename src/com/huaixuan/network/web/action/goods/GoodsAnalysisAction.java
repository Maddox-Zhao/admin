package com.huaixuan.network.web.action.goods;

import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.huaixuan.network.biz.domain.admin.AdminAgent;
import com.huaixuan.network.biz.domain.goods.Category;
import com.huaixuan.network.biz.domain.statistics.GoodsAnalysis;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.service.goods.AttributeManager;
import com.huaixuan.network.biz.service.goods.CategoryManager;
import com.huaixuan.network.biz.service.goods.GoodsBatchManager;
import com.huaixuan.network.biz.service.statistics.AnalysisManager;
import com.huaixuan.network.common.util.DoubleUtil;
import com.huaixuan.network.common.util.Page;
import com.huaixuan.network.web.action.BaseAction;
import com.hundsun.network.melody.common.util.DateUtil;
import com.hundsun.network.melody.common.util.StringUtil;

/**
 * @author shengyong
 * @version $Id: GoodsAnalysisAction.java,v 0.1 2009-8-5 下午02:22:41 shengyong
 *          Exp $
 */
@Controller
@RequestMapping("/goods")
public class GoodsAnalysisAction extends BaseAction {

	// private CategoryManager categoryManager;
	@Autowired
	private AttributeManager attributeManager;
	@Autowired
	private AnalysisManager analysisManager;
	@Autowired
	GoodsBatchManager goodsBatch;
	@Autowired
	CategoryManager categoryManager;

	//
	// private StorageManager storageManager;
	//
	// private DepositoryManager depositoryManager;
	//
	// private InOutStatReportManager inOutStatReportManager;
	//
	// private Page page;
	//
	// private List<GoodsAnalysis> goodsAnalysisList = new
	// ArrayList<GoodsAnalysis>();
	//
	// private List<InOutStatReport> inOutStatReportList = new
	// ArrayList<InOutStatReport>();
	//
	// private List<SaleAnalysis> saleAnalysisList = new
	// ArrayList<SaleAnalysis>();
	//
	// private GoodsBatchManager goodsBatch;
	//
	// private List<DepositoryFirst> depositoryFirstList; //一级仓库列表
	//
	//
	// private List<Depository> depositoryList; //仓库列表
	//
	// private List<DepLocation> depLocationLists; //库位列表
	//
	// private BrandManager brandManager;
	// /**
	// * 热销商品
	// *
	// * @return
	// * @throws Exception
	// */
	// public String searchHotGoods() throws Exception {
	// ActionContext context = ActionContext.getContext();
	// //增加品牌检索条件 zhangwy
	// List<Brand> brandList = brandManager.getBrands();
	// context.put("brandList", brandList);
	//
	// int count = 0;
	// HttpServletRequest request = getRequest();
	//
	// String dateStart = request.getParameter("dateStart");
	// String dateEnd = request.getParameter("dateEnd");
	// String goodsNum = request.getParameter("goodsNum");
	// String dosearch = request.getParameter("dosearch");
	//
	// if (StringUtil.isBlank(dateStart) && StringUtil.isBlank(dateEnd)) {
	// dateStart = DateUtil.getDiffMon(new Date(), -1);
	// dateEnd = DateUtil.getDateToString(new Date());
	//
	// }
	//
	// if (StringUtil.isNotBlank(goodsNum) && StringUtil.isNumeric(goodsNum)) {
	// count = (new Integer(goodsNum)).intValue();
	// } else {
	// goodsNum = "20";
	// count = 20;
	// }
	// String catCode = request.getParameter("catCode");
	// String brandId = request.getParameter("brandId");
	// parMap.put("dateStart", dateStart);
	// parMap.put("dateEnd", dateEnd);
	// parMap.put("catCode", catCode);
	// parMap.put("goodsNum", goodsNum);
	// parMap.put("brandId", brandId);
	//
	// if (count > 0) {
	// int total = analysisManager.getHotSalesGoodsCount(parMap);
	//
	// // if (count < total) {
	// //
	// // if (count - ((page.getCurrentPage() - 1) * pageSize) < pageSize)
	// // {
	// // page.setPageSize(count
	// // - ((page.getCurrentPage() - 1) * pageSize));
	// // } else {
	// // page.setPageSize(pageSize);
	// // }
	// // page.setTotalRowsAmount(count);
	// // page.setCurrentPage(currentPage);
	// //
	// // } else {
	// //
	// page = new Page();
	// page.setPageSize(pageSize);
	// page.setTotalRowsAmount(total);
	// if (StringUtil.isBlank(dosearch)) {
	// page.setCurrentPage(1);
	// } else {
	// page.setCurrentPage(currentPage);
	// }
	// parMap.put("dosearch", String.valueOf(currentPage));
	// if (page.getCurrentPage() == 1) {
	// getRequest().setAttribute("sumMap",
	// analysisManager.getHotSalesGoodsSum(parMap));
	// }
	//
	// request.setAttribute("hotSalesGoods",
	// analysisManager.getHotSalesGoods(parMap, page));
	// page.setPageSize(pageSize);
	// }
	// return SUCCESS;
	// }
	//
	// /**
	// * 滞销商品
	// *
	// * @return
	// * @throws Exception
	// */
	// public String searchSlowGoods() throws Exception {
	// ActionContext context = ActionContext.getContext();
	// List<Category> catList = categoryManager.getCatInfoByDepth(1);
	// context.put("catList", catList);
	// int count = 0;
	// HttpServletRequest request = getRequest();
	//
	// String dateStart = request.getParameter("dateStart");
	// String dateEnd = request.getParameter("dateEnd");
	// String goodsNum = request.getParameter("goodsNum");
	// String dosearch = request.getParameter("dosearch");
	// //zhangwy
	// String goodsCode = request.getParameter("goodsCode");
	//
	// if (StringUtil.isBlank(dateStart) && StringUtil.isBlank(dateEnd)) {
	// dateStart = DateUtil.getDiffMon(new Date(), -1);
	// dateEnd = DateUtil.getDateToString(new Date());
	//
	// }
	//
	// if (StringUtil.isNotBlank(goodsNum) && StringUtil.isNumeric(goodsNum)) {
	// count = (new Integer(goodsNum)).intValue();
	// } else {
	// goodsNum = "20";
	// count = 20;
	// }
	// String catCode = request.getParameter("catCode");
	// parMap.put("dateStart", dateStart);
	// parMap.put("dateEnd", dateEnd);
	// parMap.put("catCode", catCode);
	// parMap.put("goodsNum", goodsNum);
	// //zhangwy
	// parMap.put("goodsCode", goodsCode);
	//
	// if (count >= 0) {
	// int total = analysisManager.getSlowSalesGoodsCount(parMap);
	// page = new Page();
	// page.setPageSize(pageSize);
	// page.setTotalRowsAmount(total);
	// if (StringUtil.isBlank(dosearch)) {
	// page.setCurrentPage(1);
	// } else {
	// page.setCurrentPage(currentPage);
	// }
	// parMap.put("dosearch", String.valueOf(currentPage));
	// if (page.getCurrentPage() == 1) {
	// getRequest().setAttribute("sumMap",
	// analysisManager.getSlowSalesGoodsSum(parMap));
	// }
	//
	// request.setAttribute("slowSalesGoods",
	// analysisManager.getSlowSalesGoods(parMap, page));
	// page.setPageSize(pageSize);
	// }
	// return SUCCESS;
	// }

	/**
	 * 滞销商品EXCEL导出
	 *
	 * @return
	 */
	@RequestMapping(value = "/doExportSlowGoods")
	public void doExportSlowGoods(AdminAgent adminAgent,
			HttpServletResponse response, HttpServletRequest request,
			Model model) throws Exception {
		List<Category> catList = categoryManager.getCatInfoByDepth(1);
		model.addAttribute("catList", catList);
		OutputStream outwt = null;
		try {
			Date da = new Date();
			outwt = response.getOutputStream();
			SimpleDateFormat df = new SimpleDateFormat("yyyy_MM_dd");
			String date = df.format(da);
			response.setHeader("Content-disposition",
					"attachment; filename=slowGoods" + date + ".xls");
			response.setContentType("application/octet-stream;charset=utf-8");

			List<Object[]> slowGoodsExportList = new ArrayList<Object[]>();
			String[] title = { "商品编码", "商品名称", "类目", "单位", "销售", "销售成本", "销售额",
					"毛利", "毛利率", "可用库存" };
			slowGoodsExportList.add(title);

			int count = 0;
			String dateStart = request.getParameter("dateStart");
			String dateEnd = request.getParameter("dateEnd");
			String goodsNum = request.getParameter("goodsNum");
			// zhangwy
			String goodsCode = request.getParameter("goodsCode");

			if (StringUtil.isBlank(dateStart) && StringUtil.isBlank(dateEnd)) {
				dateStart = com.huaixuan.network.common.util.DateUtil
						.getDiffMon(new Date(), -1);
				dateEnd = com.huaixuan.network.common.util.DateUtil
						.getDateToString(new Date());

			}

			if (StringUtil.isNotBlank(goodsNum)
					&& StringUtil.isNumeric(goodsNum)) {
				count = (new Integer(goodsNum)).intValue();
			} else {
				goodsNum = "20";
				count = 20;
			}
			String catCode = request.getParameter("catCode");
			Map parMap = new HashMap();
			parMap.put("dateStart", dateStart);
			parMap.put("dateEnd", dateEnd);
			parMap.put("catCode", catCode);
			parMap.put("goodsNum", goodsNum);
			// zhangwy
			parMap.put("goodsCode", goodsCode);

			QueryPage goodsAnalysisList = analysisManager.getSlowSalesGoods(
					parMap, 1, Integer.MAX_VALUE);
			if (goodsAnalysisList != null) {
				for (GoodsAnalysis tmp : (List<GoodsAnalysis>) goodsAnalysisList
						.getItems()) {
					String[] data = {
							tmp.getGoodsSn(),
							tmp.getGoodsName(),
							categoryManager.getCatFullNameByCatcode(tmp
									.getCatCode()), tmp.getUnit(),
							tmp.getSaleSum()+"",
							DoubleUtil.round(tmp.getInPrice(), 2)+"",
							DoubleUtil.round(tmp.getOutPrice(), 2)+"",
							tmp.getProfit(), tmp.getProfitPer(),
							tmp.getAvailableNum()+"" };
					slowGoodsExportList.add(data);
				}
			}
			goodsBatch.exportExcelByObject(outwt, slowGoodsExportList);
			outwt.flush();
		} catch (Exception e) {
			model.addAttribute("errorMessage", "导出失败！");
		} finally {
			outwt.close();
		}
	}

	// /**
	// * 类目销售汇总
	// *
	// * @return
	// * @throws Exception
	// */
	// public String searchCatSum() throws Exception {
	// ActionContext context = ActionContext.getContext();
	// List<Category> catList = categoryManager.getCatInfoByDepth(1);
	// context.put("catList", catList);
	//
	// HttpServletRequest request = getRequest();
	//
	// String dateStart = request.getParameter("dateStart");
	// String dateEnd = request.getParameter("dateEnd");
	//
	// String catCode = request.getParameter("catCode");
	// String dosearch = request.getParameter("dosearch");
	//
	// if (StringUtil.isBlank(dateStart) && StringUtil.isBlank(dateEnd)) {
	// dateStart = DateUtil.getDiffMon(new Date(), -1);
	// dateEnd = DateUtil.getDateToString(new Date());
	//
	// }
	// parMap.put("dateStart", dateStart);
	// parMap.put("dateEnd", dateEnd);
	// parMap.put("catCode", catCode);
	//
	// int total = analysisManager.getCatSalesGoodsCount(parMap);
	// page = new Page();
	// page.setPageSize(pageSize);
	// page.setTotalRowsAmount(total);
	// if (StringUtil.isBlank(dosearch)) {
	// page.setCurrentPage(1);
	// } else {
	// page.setCurrentPage(currentPage);
	// }
	// parMap.put("dosearch", String.valueOf(currentPage));
	// if (page.getCurrentPage() == 1) {
	// getRequest().setAttribute("sumMap",
	// analysisManager.getCatSalesGoodsSum(parMap));
	// }
	//
	// List<GoodsAnalysis> gas = analysisManager.getCatSalesGoods(parMap, page);
	// // 这边是用程序处理排序 shlin
	// // if(gas.size() > 0 && StringUtil.isBlank(catCode)){
	// // List<GoodsAnalysis> analysisList = new
	// ArrayList<GoodsAnalysis>(gas.size());
	// // for(Category category:catList){
	// // List<GoodsAnalysis> tmp = new ArrayList<GoodsAnalysis>();
	// // for(GoodsAnalysis goodsAnalysis :gas){
	// // if(goodsAnalysis.getCatCode().startsWith(category.getCatCode())){
	// // tmp.add(goodsAnalysis);
	// // }
	// // }
	// // Collections.sort(tmp ,Collections.reverseOrder());
	// // analysisList.addAll(tmp);
	// // }
	// // request.setAttribute("catSalesGoods", analysisList);
	// // }else{
	// // request.setAttribute("catSalesGoods", gas);
	// // }
	// request.setAttribute("catSalesGoods", gas);
	// page.setPageSize(pageSize);
	//
	// return SUCCESS;
	// }

	/**
	 * 退货商品查询
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("searchRefundGoods")
	public String searchRefundGoods(
			@RequestParam(value = "dateStart", required = false) String dateStart,
			@RequestParam(value = "dateEnd", required = false) String dateEnd,
			@RequestParam(value = "goodsInstanceId", required = false) String goodsInstanceId,
			@RequestParam(value = "page", required = false, defaultValue = "1") int currPage,
			@RequestParam(value = "goodsInstanceName", required = false) String goodsInstanceName,
			Model model) throws Exception {
		if (StringUtil.isBlank(dateStart) && StringUtil.isBlank(dateEnd)) {
			dateStart = DateUtil.convertDateToString(DateUtil.getRelativeDate(
					new Date(), -30));
			dateEnd = DateUtil.convertDateToString(new Date());
		}

		Map<String, Object> parMap = new HashMap<String, Object>();
		parMap.put("dateStart", dateStart);
		parMap.put("dateEnd", dateEnd);
		parMap.put("goodsInstanceName", goodsInstanceName);
		parMap.put("goodsInstanceId", goodsInstanceId);

		Page paginator = new Page();
		paginator.setPageSize(pageSize);
		paginator.setCurrentPage(currPage);

		QueryPage page = analysisManager.getAnalysisRefundGoods(parMap,
				paginator);

		model.addAttribute("page", page);

		model.addAttribute("dateStart", dateStart);
		model.addAttribute("dateEnd", dateEnd);
		model.addAttribute("goodsInstanceName", goodsInstanceName);
		model.addAttribute("goodsInstanceId", goodsInstanceId);

		model.addAttribute("attributeManager", attributeManager);

		return "/goods/searchRefundGoods";
	}

	// /**
	// * 类目库存成本汇总
	// *
	// * @return
	// * @throws Exception
	// */
	// public String searchStorageCost() throws Exception {
	// HttpServletRequest request = getRequest();
	// parMap.put("status", "v");
	// List<Depository> depositorys =
	// depositoryManager.getDepositorysByParMap(parMap, null);
	// request.setAttribute("depositorys", depositorys);
	//
	// String depId = request.getParameter("depId");
	// parMap.put("depId", depId);
	// if (StringUtil.isBlank(depId)) {
	// return SUCCESS;
	// }
	//
	// request.setAttribute("storages", storageManager.sumStorageCostByDepid(new
	// Long(depId)));
	//
	// return SUCCESS;
	// }
	//
	// /**
	// * 在库产品统计
	// * @return
	// */
	// public String searchInStorage() throws Exception {
	// depositoryFirstList = getDepositoryFirstInit(parMap);
	// if (depositoryFirstList == null||depositoryFirstList.size() == 0) {
	// return SUCCESS;
	// }
	// if (StringUtil.isNotBlank(parMap.get("depfirstId"))) {
	// depositoryList = getDeplistInit(parMap);
	// }
	// if (StringUtil.isNotBlank(parMap.get("depId"))) {
	// depLocationLists = getLocationsInit(parMap);
	// }
	//
	// ActionContext context = ActionContext.getContext();
	// List<Category> catList = categoryManager.getCatInfoByDepth(1);
	//
	// context.put("catList", catList);
	// context.put("depositoryList", depositoryList);
	// int total = analysisManager.getGoodsInStorageCount(parMap);
	// if(total > 0){
	// page = new Page();
	// page.setCurrentPage(currentPage);
	// page.setPageSize(pageSize);
	// page.setTotalRowsAmount(total);
	// page.setCurrentPage(currentPage);
	// saleAnalysisList = analysisManager.getGoodsInStorage(parMap, page);
	// }
	// return SUCCESS;
	// }
	//
	// /**
	// * 月进出货统计报表
	// *
	// * @return
	// * @throws Exception
	// */
	// public String searchInOutStatReport() throws Exception {
	// depositoryFirstList = getDepositoryFirstInit(parMap);
	// if (depositoryFirstList == null||depositoryFirstList.size() == 0) {
	// return SUCCESS;
	// }
	// if (StringUtil.isNotBlank(parMap.get("depfirstId"))) {
	// depositoryList = getDeplistInit(parMap);
	// }
	// if (StringUtil.isNotBlank(parMap.get("depId"))) {
	// depLocationLists = getLocationsInit(parMap);
	// }
	//
	// String depIdStr = this.getRequest().getParameter("parMap.depId");
	// if(StringUtil.isBlank(depIdStr)){
	// return SUCCESS;
	// }
	//
	// int total = inOutStatReportManager.getInOutStatReportCountByMap(parMap);
	// if(total > 0){
	// page = new Page();
	// page.setCurrentPage(currentPage);
	// page.setPageSize(pageSize);
	// page.setTotalRowsAmount(total);
	// page.setCurrentPage(currentPage);
	// List<InOutStatReport> inOutStatReportList =
	// inOutStatReportManager.getInOutStatReportListByMap(parMap, page);
	// if(inOutStatReportList!=null && inOutStatReportList.size() > 0){
	// for(InOutStatReport inOutStatReport : inOutStatReportList){
	// if(inOutStatReport.getLastRemainAmount() == 0 &&
	// inOutStatReport.getLastRemainMoney() == 0 &&
	// inOutStatReport.getThisInAmount() == 0 &&
	// inOutStatReport.getThisInMoney() == 0 &&
	// inOutStatReport.getThisOutAmount() == 0 &&
	// inOutStatReport.getThisOutMoney() == 0 &&
	// inOutStatReport.getThisRemainAmount() == 0 &&
	// inOutStatReport.getThisRemainMoney() == 0){
	// continue;
	// }else{
	// this.inOutStatReportList.add(inOutStatReport);
	// }
	// }
	// }
	// }
	// return SUCCESS;
	// }
	// /**
	// * 导出月进出货统计报表
	// * @return
	// * @throws Exception
	// */
	// public String exportInOutStatReport() throws Exception {
	// OutputStream outwt = null;
	// HttpServletRequest request = getRequest();
	// try {
	// HttpServletResponse res = getResponse();
	// Date da = new Date();
	// outwt = res.getOutputStream();
	// SimpleDateFormat df = new SimpleDateFormat("yyyy_MM_dd");
	// String date = df.format(da);
	// res.setContentType("application/octet-stream;charset=utf-8");
	// List<String[]> storageExportList = new ArrayList<String[]>();
	// res.setHeader("Content-disposition",
	// "attachment; filename=InOutStatReport_" + date
	// + ".xls");
	//
	// searchInOutStatReport();
	//
	// long sum_lastRemainAmount =0;
	// double sum_lastRemainMoney =0;
	// long sum_thisInAmount =0;
	// double sum_thisInMoney =0;
	// long sum_thisOutAmount =0;
	// double sum_thisOutMoney =0;
	// long sum_thisRemainAmount =0;
	// double sum_thisRemainMoney =0;
	//
	// String[] title = { "商品名称", "商品编码", "上月结余数量", "上月结余金额(￥)", "本月进货数量",
	// "本月进货金额(￥)", "本月出货数量",
	// "本月出货金额(￥)", "本月结余数量", "本月结余金额(￥)" };
	// storageExportList.add(title);
	// if (inOutStatReportList != null) {
	// for (InOutStatReport report : inOutStatReportList) {
	// String[] data = { report.getGoodsName(), report.getGoodsCode(),
	// report.getLastRemainAmount()+"", report.getLastRemainMoney()+"",
	// report.getThisInAmount()+"", report.getThisInMoney()+"",
	// report.getThisOutAmount()+"", report.getThisOutMoney()+"",
	// report.getThisRemainAmount()+"",
	// report.getThisRemainMoney()+"" };
	// storageExportList.add(data);
	//
	// sum_lastRemainAmount = MoneyUtil.addNumber(
	// sum_lastRemainAmount,report.getLastRemainAmount());
	// sum_lastRemainMoney = MoneyUtil.add(
	// sum_lastRemainMoney,report.getLastRemainMoney());
	// sum_thisInAmount = MoneyUtil.addNumber(
	// sum_thisInAmount,report.getThisInAmount());
	// sum_thisInMoney = MoneyUtil.add(
	// sum_thisInMoney,report.getThisInMoney());
	// sum_thisOutAmount = MoneyUtil.addNumber(
	// sum_thisOutAmount,report.getThisOutAmount());
	// sum_thisOutMoney = MoneyUtil.add(
	// sum_thisOutMoney,report.getThisOutMoney());
	// sum_thisRemainAmount = MoneyUtil.addNumber(
	// sum_thisRemainAmount,report.getThisRemainAmount());
	// sum_thisRemainMoney = MoneyUtil.add(
	// sum_thisRemainMoney,report.getThisRemainMoney());
	// }
	// }
	//
	// String[] bottom = { "合计", "" , sum_lastRemainAmount+"",
	// "￥"+MoneyUtil.getFormatMoney(sum_lastRemainMoney,"0.00"),
	// sum_thisInAmount+"",
	// "￥"+MoneyUtil.getFormatMoney(sum_thisInMoney,"0.00"),
	// sum_thisOutAmount+"",
	// "￥"+MoneyUtil.getFormatMoney(sum_thisOutMoney,"0.00"),
	// sum_thisRemainAmount+"",
	// "￥"+MoneyUtil.getFormatMoney(sum_thisRemainMoney,"0.00")};
	// storageExportList.add(bottom);
	//
	// goodsBatch.exportExcel(outwt, storageExportList);
	// outwt.flush();
	// } catch (Exception e) {
	// request.setAttribute("errorMessage", "导出失败！");
	// log.error(e);
	// } finally {
	// close(outwt);
	// }
	// return SUCCESS;
	// }

	/**
	 * 热销商品excel统计
	 *
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/exportHotGoods")
	public void exportHotGoods(HttpServletResponse response,
			HttpServletRequest request, Model model) throws Exception {
		OutputStream outwt = null;
		try {
			Date da = new Date();
			outwt = response.getOutputStream();
			SimpleDateFormat df = new SimpleDateFormat("yyyy_MM_dd");
			String date = df.format(da);
			response.setContentType("application/octet-stream;charset=utf-8");
			List<String[]> storageExportList = new ArrayList<String[]>();
			response.setHeader("Content-disposition",
					"attachment; filename=HotGoodsList_" + date + ".xls");
			String dateStart = request.getParameter("dateStart");
			String dateEnd = request.getParameter("dateEnd");
			String goodsNum = request.getParameter("goodsNum");
			String dosearch = request.getParameter("dosearch");

			if (StringUtil.isBlank(dateStart) && StringUtil.isBlank(dateEnd)) {
				dateStart = com.huaixuan.network.common.util.DateUtil
						.getDiffMon(new Date(), -1);
				dateEnd = com.huaixuan.network.common.util.DateUtil
						.getDateToString(new Date());

			}
			int count = 0;
			if (StringUtil.isNotBlank(goodsNum)
					&& StringUtil.isNumeric(goodsNum)) {
				count = (new Integer(goodsNum)).intValue();
			} else {
				goodsNum = "20";
				count = 20;
			}
			String catCode = request.getParameter("catCode");
			String brandId = request.getParameter("brandId");
			Map parMap = new HashMap();
			parMap.put("dateStart", dateStart);
			parMap.put("dateEnd", dateEnd);
			parMap.put("catCode", catCode);
			parMap.put("goodsNum", Integer.valueOf(goodsNum));
			parMap.put("brandId", brandId);

			QueryPage goodsAnalysisList = null;
			Map sumMap = new HashMap();
			if (count > 0) {
				goodsAnalysisList = analysisManager.getHotSalesGoods(parMap, 1,
						Integer.MAX_VALUE);
				sumMap = analysisManager.getHotSalesGoodsSum(parMap);
			}
			String[] title = { "编码", "商品名称", "商品名牌", "类目", "单位", "销量", "销售成本",
					"销售额", "毛利", "毛利率", "库存", "可用库存" };
			storageExportList.add(title);
			if (goodsAnalysisList != null) {
				for (GoodsAnalysis hotSalesGood : (List<GoodsAnalysis>) goodsAnalysisList
						.getItems()) {
					String[] data = { hotSalesGood.getGoodsSn(),
							hotSalesGood.getGoodsName(),
							hotSalesGood.getBrandName(),
							hotSalesGood.getCatName(), hotSalesGood.getUnit(),
							hotSalesGood.getSaleSum() + "",
							hotSalesGood.getInPrice() + "",
							hotSalesGood.getOutPrice() + "",
							hotSalesGood.getProfit(),
							hotSalesGood.getProfitPer() + "%",
							hotSalesGood.getStorageNum() + "",
							hotSalesGood.getAvailableNum() + "" };
					storageExportList.add(data);
				}
			}
			for (Object o : sumMap.values()) {
				Map sumResult = (HashMap) o;
				String[] bottom = { "总计", "", "", "",
						sumResult.get("saleNum") + "",
						sumResult.get("goodsInPrice") + "元",
						sumResult.get("salePrice") + "元", "", "", "" };
				storageExportList.add(bottom);
				break;
			}
			goodsBatch.exportExcel(outwt, storageExportList);
			outwt.flush();
		} catch (Exception e) {
			model.addAttribute("errorMessage", "导出失败！");
		} finally {
			outwt.close();
		}
	}
	// public static void close(OutputStream out) {
	// try {
	// if (out != null) {
	// out.close();
	// }
	// } catch (IOException ioe) {
	// // ignore
	// }
	// }
}
