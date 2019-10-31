/*
 * Hundsun Inc.
 * Copyright (c) 2006-2009 All Rights Reserved.
 *
 * Author     :shengyong
 * Version    :1.0
 * Create Date:2011-3-3
 */
package com.huaixuan.network.web.action.supplier;

import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
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
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.huaixuan.network.biz.domain.goods.GoodsInstanceSupplier;
import com.huaixuan.network.biz.domain.supplier.Supplier;
import com.huaixuan.network.biz.enums.EnumAdminPermission;
import com.huaixuan.network.biz.enums.EnumSupplierStatus;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.service.goods.GoodsInstanceSupplierManager;
import com.huaixuan.network.biz.service.supplier.SupplierService;
import com.huaixuan.network.common.util.CsvWriter;
import com.huaixuan.network.common.util.MoneyUtil;
import com.huaixuan.network.web.action.BaseAction;
import com.huaixuan.network.web.action.interceptor.AdminAccess;
import com.hundsun.network.melody.common.util.StringUtil;

/**
 * @author shengyong
 * @version $Id: SupplierAction.java,v 0.1 2011-3-3 上午09:50:39 shengyong Exp $
 */
@Controller
public class SupplierAction extends BaseAction {

	protected final Log log = LogFactory.getLog(this.getClass());

	@Autowired
	SupplierService supplierService;

	@Autowired
	GoodsInstanceSupplierManager goodsInstanceSupplierManager;

	@Autowired
	private Validator supplierAddValidator;

	@AdminAccess({ EnumAdminPermission.A_SUPPLIER_VIEW_USER })
	@RequestMapping(value = "/supplier/searchSupplier", method = RequestMethod.GET)
	public String searchSupplier(@ModelAttribute("supplier") Supplier supplier, Model model) {
		return "/supplier/searchSupplier";
	}

	@AdminAccess({ EnumAdminPermission.A_SUPPLIER_VIEW_USER })
	@RequestMapping(value = "/supplier/searchSupplier", method = RequestMethod.POST)
	public String searchSupplierList(@ModelAttribute("supplier") Supplier supplier,
			@RequestParam(value = "page", required = false, defaultValue = "1") int currPage, Model model) {
		if (supplier != null && StringUtils.isNotBlank(supplier.getName())) {
			supplier.setName(supplier.getName().trim());
		}

		QueryPage page = supplierService.searchSupplierListWithPage(supplier, currPage, this.pageSize);
		model.addAttribute("query", page);
		return "/supplier/searchSupplier";
	}

	/**
	 * @Title: searchSupplierList
	 * @Description: 弹出窗口（选择供应商）
	 * @param supplier
	 * @param currPage
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/supplier/supplier_select")
	public String searchSupplierSelect(@ModelAttribute("supplier") Supplier supplier,
			@RequestParam(value = "page", required = false, defaultValue = "1") int currPage, Model model) {
		if (supplier != null && StringUtils.isNotBlank(supplier.getName())) {
			supplier.setName(supplier.getName().trim());
		}

		QueryPage query = supplierService.searchSupplierListWithPage(supplier, currPage, this.pageSize);

		model.addAttribute("query", query);
		model.addAttribute("queryObject", supplier);
		return "/supplier/supplierSelect";
	}

	@AdminAccess({ EnumAdminPermission.A_SUPPLIER_ADD_USER })
	@RequestMapping(value = "/supplier/addSupplier", method = RequestMethod.GET)
	public String addSupplier(@ModelAttribute("supplier") Supplier supplier, BindingResult result, Model model) {
		return "/supplier/addSupplier";
	}

	@AdminAccess({ EnumAdminPermission.A_SUPPLIER_ADD_USER })
	@RequestMapping(value = "/supplier/addSupplier", method = RequestMethod.POST)
	public String addSupplierSave(@ModelAttribute("supplier") Supplier supplier, BindingResult result, Model model) {
		supplierAddValidator.validate(supplier, result);
		if (result.hasErrors()) {
			model.addAttribute("supplier", supplier);
			return "/supplier/addSupplier";
		}
		try {
			supplier.setStatus("v");
			Supplier s = supplierService.selectSupplierByName(supplier.getName());
			if (s == null) {
				// 默认为正常状态
				supplier.setStatus(EnumSupplierStatus.VALID.getKey());
				supplierService.addSupplier(supplier);
				model.addAttribute("message", "添加供应商信息成功！");

			} else {
				model.addAttribute("message", "供应商名称重复，请重新输入！");
				return "/supplier/addSupplier";
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			model.addAttribute("message", "添加供应商信息失败！");
			return "/supplier/addSupplier";
		}

		return "/supplier/addSupplier";
	}

	@AdminAccess({ EnumAdminPermission.A_SUPPLIER_DELETE_USER })
	@RequestMapping(value = "/supplier/enable")
	public String supplierEnable(@ModelAttribute("supplier") Supplier supplier,
			@RequestParam(value = "sid", required = false) String sid, Model model) {
		try {
			Supplier supplierForUpdate = supplierService.selectSupplierById(new Long(sid));
			supplierForUpdate.setStatus(EnumSupplierStatus.VALID.getKey());
			supplierService.updateSupplierStatus(supplierForUpdate);
			model.addAttribute("message", "供应商激活成功！");
		} catch (Exception e) {
			log.error(e.getMessage());
			model.addAttribute("message", "供应商激活失败！");
		}
		return searchSupplierList(supplier, 1, model);
	}

	@AdminAccess({ EnumAdminPermission.A_SUPPLIER_DELETE_USER })
	@RequestMapping(value = "/supplier/disable")
	public String supplierDisable(@ModelAttribute("supplier") Supplier supplier,
			@RequestParam(value = "sid", required = false) String sid, Model model) {
		try {
			Supplier supplierForUpdate = supplierService.selectSupplierById(new Long(sid));
			supplierForUpdate.setStatus(EnumSupplierStatus.INVALID.getKey());
			supplierService.updateSupplierStatus(supplierForUpdate);
			model.addAttribute("message", "供应商失效操作成功！");
		} catch (Exception e) {
			log.error(e.getMessage());
			model.addAttribute("message", "供应商失效操作失败！");
		}
		return searchSupplierList(supplier, 1, model);
	}

	@AdminAccess({ EnumAdminPermission.A_SUPPLIER_MODIFY_USER })
	@RequestMapping(value = "/supplier/editSupplier", method = RequestMethod.GET)
	public String editSupplierInis(@ModelAttribute("supplier") Supplier supplier,
			@RequestParam(value = "sid", required = false) String sid, Model model) {
		supplier = supplierService.selectSupplierById(new Long(sid));
		model.addAttribute("supplier", supplier);
		return "/supplier/editSupplier";
	}

	@AdminAccess({ EnumAdminPermission.A_SUPPLIER_MODIFY_USER })
	@RequestMapping(value = "/supplier/editSupplier", method = RequestMethod.POST)
	public String editSupplier(@ModelAttribute("supplier") Supplier supplier, BindingResult result,
			@RequestParam(value = "sid", required = false) String sid, Model model) {
		supplierAddValidator.validate(supplier, result);
		if (result.hasErrors()) {
			return "/supplier/editSupplier";
		}

		try {
			supplierService.updateSupplier(supplier);
			sid = supplier.getId().toString();
			model.addAttribute("message", "更新供应商信息成功！");
		} catch (Exception e) {
			log.error(e.getMessage());
			model.addAttribute("message", "更新供应商信息失败！");
		}
		model.addAttribute("supplier", supplier);
		return "/supplier/editSupplier";
	}

	@AdminAccess({ EnumAdminPermission.A_SUPPLIER_VIEW_USER })
	@RequestMapping(value = "/supplier/viewSupplier")
	public String viewSupplierGoods(@ModelAttribute("supplier") Supplier supplier,
			@RequestParam(value = "sid", required = false) String sid,
			@RequestParam(value = "title", required = false) String title,
			@RequestParam(value = "code", required = false) String code,
			@RequestParam(value = "supplierCode", required = false) String supplierCode,
			@RequestParam(value = "page", required = false, defaultValue = "1") int currPage, Model model) {
		Map parMap = new HashMap();
		parMap.put("supplierId", String.valueOf(sid));
		parMap.put("title", title);
		parMap.put("code", code);
		parMap.put("supplierCode", supplierCode);
		supplier = supplierService.selectSupplierById(new Long(sid));

		try {
			supplier = supplierService.selectSupplierById(new Long(sid));

			QueryPage page = goodsInstanceSupplierManager.getGoodsInstanceSuppliersByQuery(parMap, currPage,
					this.pageSize);
			model.addAttribute("query", page);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		model.addAttribute("parMap", parMap);
		model.addAttribute("supplier", supplier);
		return "/supplier/viewSupplier";
	}

	private String exportContentType = "application/oct-stream";

	private String defaultCsvName = "supplierGoods.csv";

	@RequestMapping(value = "/supplier/doExportSupplier")
	public String doExportSupplier(@ModelAttribute("supplier") Supplier supplier,
			@RequestParam(value = "sid", required = false) String sid,
			@RequestParam(value = "title", required = false) String title,
			@RequestParam(value = "code", required = false) String code,
			@RequestParam(value = "supplierCode", required = false) String supplierCode,
			@RequestParam(value = "page", required = false, defaultValue = "1") int currPage, Model model,
			HttpServletResponse response) {

		Map parMap = new HashMap();
		parMap.put("supplierId", String.valueOf(sid));
		parMap.put("title", title);
		parMap.put("code", code);
		parMap.put("supplierCode", supplierCode);

		try {
			response.resetBuffer();

			response.getOutputStream().flush();
			response.setCharacterEncoding("GBK");
			response.setContentType(exportContentType);
			Date da = new Date();
			SimpleDateFormat df = new SimpleDateFormat("yyyy_MM_dd");
			String date = df.format(da);
			response.setHeader("Content-Disposition", "attachment;Filename=" + date + defaultCsvName);

			CsvWriter writer = new CsvWriter(response.getOutputStream(), ',', Charset.forName("GBK"));

			List<GoodsInstanceSupplier> goodsInstanceSupplier = null;
			supplier = supplierService.selectSupplierById(new Long(sid));
			int count = goodsInstanceSupplierManager.getGoodsInstanceSuppliersCountByParameterMap(parMap);
			goodsInstanceSupplier = goodsInstanceSupplierManager.getGoodsInstanceSuppliersByParameterMap(parMap, 1,
					count);

			String[] titleName = { "产品名称", "产品编码", "供应商产品编码", "单位", "类目", "进货价格" };
			writer.writeRecord(titleName);
			if (goodsInstanceSupplier != null) {
				for (GoodsInstanceSupplier tmp : goodsInstanceSupplier) {
					if (StringUtil.isBlank(tmp.getSupplierCode())) {
						tmp.setSupplierCode("");
					}
					String[] data = { tmp.getGoodsInstanceName() + "", tmp.getCode() + "", tmp.getSupplierCode() + "",
							tmp.getGoodsUnit() + "", tmp.getCatCode() + "",
							MoneyUtil.getFormatMoney(tmp.getConsultPrice(), "0.00") };
					writer.writeRecord(data);
				}
			}
			writer.flush();
			writer.close();
			response.getOutputStream().flush();
		} catch (Exception e) {
			model.addAttribute("errorMessage", "导出失败！");
			log.error(e);
		}
		return "";
	}

	@RequestMapping(value = "/supplier/editDwrGoodsPrice")
	public @ResponseBody
	String editDwrGoodsPrice(@RequestParam("param1") String goodsId_str, @RequestParam("param2") String amount_str,
			HttpServletResponse response) {
		String message = "";
		if (goodsId_str == null) {
			message = "['false','goodsId and amount must be not null!']";
			return message;
		}
		Long goodsId = Long.parseLong(goodsId_str);
		GoodsInstanceSupplier goods = goodsInstanceSupplierManager.getGoodsInstanceSupplier(goodsId);
		if (goods == null) {
			message = "['false','the goods is not exist!']";
			return message;
		}
		if (amount_str != null) {
			BigDecimal b1 = new BigDecimal(amount_str);
			goods.setConsultPrice(b1);
			goodsInstanceSupplierManager.editGoodsInstanceSupplier(goods);
			message = "['true','edit success!']";
		} else {
			message = "['false','amount is null!']";
		}
		return message;

	}

	@RequestMapping(value = "/supplier/editDwrGoodsCode")
	public @ResponseBody
	String editDwrGoodsCode(@RequestParam("param1") String goodsId_str, @RequestParam("param2") String code,
			HttpServletResponse response) {
		String message = "";
		if (goodsId_str == null) {
			message = "['false','goodsId and code must be not null!']";
			return message;
		}
		Long goodsId = Long.parseLong(goodsId_str);
		GoodsInstanceSupplier goods = goodsInstanceSupplierManager.getGoodsInstanceSupplier(goodsId);
		if (goods == null) {
			message = "['false','the goods is not exist!']";

		} else {
			goods.setSupplierCode(code);
			goodsInstanceSupplierManager.editGoodsInstanceSupplier(goods);
			message = "['true','edit success!']";
		}
		return message;

	}

	@RequestMapping(value = "/supplier/addSupplierGoods", method = RequestMethod.GET)
	public String addSupplierGoodsInit(@ModelAttribute("supplier") Supplier supplier,
			@RequestParam(value = "sid", required = false) String sid, Model model) {
		supplier = supplierService.selectSupplierById(new Long(sid));
		model.addAttribute("supplier", supplier);
		return "/supplier/addSupplierGoods";
	}

	@RequestMapping(value = "/supplier/addSupplierGoods", method = RequestMethod.POST)
	public String addSupplierGoods(@ModelAttribute("supplier") Supplier supplier,
			@RequestParam(value = "sid", required = false) String sid, HttpServletRequest request, Model model) {

		String message = "";
		String supplierId = request.getParameter("supplierId");
		if (StringUtil.isBlank(supplierId)) {
			message = "添加供应商产品失败！";
			model.addAttribute("message", message);
			return "/supplier/addSupplierGoods";
		}
		supplier = supplierService.selectSupplierById(new Long(supplierId));
		model.addAttribute("supplier", supplier);

		String[] goodsInstanceId = request.getParameterValues("goodsInstanceId");
		String[] goodsId = request.getParameterValues("goodsId");
		String[] goodsInstanceName = request.getParameterValues("goodsInstanceName");
		String[] supplierCode = request.getParameterValues("supplierCode");
		String[] consultPrice = request.getParameterValues("consultPrice");
		try {
			if (goodsInstanceId == null || goodsInstanceId.length == 0) {
				message = "请选择产品！";
				model.addAttribute("message", message);
				return "/supplier/addSupplierGoods";
			}

			for (int i = 0; i < goodsInstanceId.length; i++) {
				GoodsInstanceSupplier gs = new GoodsInstanceSupplier();
				gs.setGoodsInstanceId(new Long(goodsInstanceId[i]));
				gs.setGoodsInstanceName(goodsInstanceName[i]);
				gs.setGoodsId(new Long(goodsId[i]));
				if (StringUtil.isNotBlank(consultPrice[i])) {
					Double amount = new Double(consultPrice[i]);
					gs.setConsultPrice(new BigDecimal(amount.doubleValue()));
				}
				if (StringUtil.isNotBlank(supplierCode[i])) {
					gs.setSupplierCode(supplierCode[i].trim());
				} else {
					gs.setSupplierCode(supplierCode[i]);
				}
				gs.setSupplierId(new Long(supplierId));
				Map parMap = new HashMap();
				parMap.put("supplierId", supplierId);
				parMap.put("goodsInstanceId", String.valueOf(gs.getGoodsInstanceId().longValue()));
				int count = goodsInstanceSupplierManager.getGoodsInstanceSuppliersCountByParameterMap(parMap);
				if (count == 0) {
					goodsInstanceSupplierManager.addGoodsInstanceSupplier(gs);
				}
			}
			message = "添加供应商产品成功！";
			model.addAttribute("message", message);
		} catch (Exception e) {
			log.error(e.getMessage());
			message = "添加供应商产品失败！";
			model.addAttribute("message", message);
			return "/supplier/addSupplierGoods";
		}
		return "/supplier/addSupplierGoods";
	}

	@RequestMapping("/supplier/delSupplierGoods")
	public String delSupplierGoods(@ModelAttribute("supplier") Supplier supplier,
			@RequestParam(value = "sid", required = false) String sid,
			@RequestParam(value = "title", required = false) String title,
			@RequestParam(value = "code", required = false) String code,
			@RequestParam(value = "supplierCode", required = false) String supplierCode,
			@RequestParam(value = "page", required = false, defaultValue = "1") int currPage,
			@RequestParam("supplierGoodsId") Long supplierGoodsId, Model model) {
		try {
			goodsInstanceSupplierManager.removeGoodsInstanceSupplier(supplierGoodsId);
			model.addAttribute("success", "删除供应商商品成功！");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			model.addAttribute("error", "删除供应商商品失败！");
		}
		return viewSupplierGoods(supplier, sid, title, code, supplierCode, currPage, model);
	}

}
