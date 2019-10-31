/**
 *
 */
package com.huaixuan.network.web.action.stock;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.huaixuan.network.biz.domain.stock.V_SupplierShoppingList;
import com.huaixuan.network.biz.domain.stock.query.StockDetailSearchQuery;
import com.huaixuan.network.biz.enums.EnumStockStatus;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.service.goods.GoodsBatchManager;
import com.huaixuan.network.biz.service.stock.ShoppingListService;
import com.huaixuan.network.biz.service.supplier.SupplierService;
import com.huaixuan.network.common.util.DateUtil;
import com.huaixuan.network.common.util.DoubleUtil;
import com.huaixuan.network.web.action.BaseAction;
import com.hundsun.network.melody.common.util.StringUtil;

/**
 * @author zhangxiang
 */
@Controller
public class SupplierShoppingListAction extends BaseAction {

	private static final long serialVersionUID = 1317900109477183462L;

	@Autowired
	SupplierService supplierService; // 对应的供应商service

	@Autowired
	ShoppingListService shoppingListService; // 对应的供应商采购单

	Map<String, String> enumStockStatusMap = EnumStockStatus.toMap();

//	private Page page;

	@Autowired
	GoodsBatchManager goodsBatch;

	/**
	 * 供应商供货查询
	 *
	 * @return
	 */
	@RequestMapping(value = "/stock/search_supplier_shopping_list")
	public String searchSupplierShoppingList(
			@ModelAttribute("stockDetailSearchQuery")StockDetailSearchQuery stockDetailSearchQuery,
    		@RequestParam(value = "page", required = false, defaultValue = "1") int currPage,
    		Model model) throws Exception {
		if (StringUtil.isBlank(stockDetailSearchQuery.getStartTime())
				&& StringUtil.isBlank(stockDetailSearchQuery.getEndTime())) {
			stockDetailSearchQuery.setStartTime(DateUtil.getDiffDate(
					new Date(), -30));
			stockDetailSearchQuery.setEndTime(DateUtil
					.getDateToString(new Date()));
		}
//		List supplierLists = supplierService.getSupplier();// 供应商列表

		QueryPage query = shoppingListService.getSearchShoppingLists(
				stockDetailSearchQuery, currPage, pageSize); // 返回分页的List

//		model.addAttribute("supplierLists", supplierLists);
		model.addAttribute("queryObject", stockDetailSearchQuery);
		model.addAttribute("query", query);
		model.addAttribute("enumStockStatusMap", EnumStockStatus.toMap());
		return "/stock/show_supplier_shopping_list";

	}

	/**
	 * 供应商供应商品导出
	 *
	 * @return
	 */
	@RequestMapping(value = "/stock/export_supplier_shopping_list")
	public String exportSupplierShoppingList(
			@ModelAttribute("stockDetailSearchQuery")StockDetailSearchQuery stockDetailSearchQuery,
			HttpServletRequest request,
			HttpServletResponse response
		) throws Exception {

		OutputStream outwt = null;
		try {
			Date da = new Date();
			outwt = response.getOutputStream();
			SimpleDateFormat df = new SimpleDateFormat("yyyy_MM_dd");
			String date = df.format(da);
			response.setHeader("Content-disposition",
					"attachment; filename=supplierShopping" + date + ".xls");
			response.setContentType("application/octet-stream;charset=utf-8");

			List<Object[]> supplierShoppingExportList = new ArrayList<Object[]>();
			String[] title = { "供货商", "采购订单号", "采购单状态", "商品编码", "产品名称", "产品属性",
					"数量", "单价", "应付金额", "实付金额", "缺货数量", "拒收数量", "采购时间",
					"预期到货时间" };
			supplierShoppingExportList.add(title);

			List<V_SupplierShoppingList> supplierShoppingList = shoppingListService
					.getSupplierShoppingExportList(stockDetailSearchQuery);

			SimpleDateFormat usedf = new SimpleDateFormat("yyyy-MM-dd");
			if (supplierShoppingList != null) {
				for (V_SupplierShoppingList tmp : supplierShoppingList) {
					Object[] data = { tmp.getSupplierName(),
							tmp.getShoppingNum(),
							enumStockStatusMap.get(tmp.getStatus()),
							tmp.getGoodsSn(), tmp.getInstanceName(),
							tmp.getAttrDesc(), tmp.getAmount(),
							DoubleUtil.round(tmp.getUnitPrice(), 2),
							DoubleUtil.round(tmp.getDueFee(), 2),
							DoubleUtil.round(tmp.getFactFee(), 2),
							(Long) tmp.getMissingNum(),
							(Long) tmp.getRejectNum(),
							usedf.format(tmp.getShoppingTime()),
							usedf.format(tmp.getArriveTime()) };
					supplierShoppingExportList.add(data);
				}
			}
			goodsBatch.exportExcelByObject(outwt, supplierShoppingExportList);
			outwt.flush();
		} catch (Exception e) {
			request.setAttribute("errorMessage", "导出失败！");
//			log.error(e);
		} finally {
			close(outwt);
		}
		return "/stock/show_supplier_shopping_list";
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

}