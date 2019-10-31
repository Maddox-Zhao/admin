package com.huaixuan.network.web.action.wap;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.huaixuan.network.biz.domain.admin.AdminAgent;
import com.huaixuan.network.biz.domain.base.MiniUiBase;
import com.huaixuan.network.biz.domain.product.Product;
import com.huaixuan.network.biz.domain.purchase.Purchaselifecycle;
import com.huaixuan.network.biz.domain.shop.Brand;
import com.huaixuan.network.biz.domain.shop.Series;
import com.huaixuan.network.biz.service.base.MiniuiBaseService;
import com.huaixuan.network.biz.service.product.ProductService;
import com.huaixuan.network.biz.service.purchase.PurchaseOrderService;
import com.huaixuan.network.biz.service.shop.BrandService;
import com.huaixuan.network.web.action.interceptor.AdminAccess;



/**
 * @author Mr_Yang   2015-12-29 下午04:02:54
 **/

@Controller
@RequestMapping("/m/product")
public class WapProductAction
{
	@Autowired
	private ProductService productService;

	@Autowired
	private MiniuiBaseService miniuiBaseService;
	
	@Autowired
	private PurchaseOrderService purchaseOrderService;
	
	@Autowired
	private BrandService brandService;
	
	
	/**
	 * 准备入库
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/product2sale")
	@AdminAccess
	public String product2sale(HttpServletRequest request,Model model) {
		String idProduct = request.getParameter("idproduct");
		Product product = productService.getproduct(idProduct);
		List<Series> sereisList =  brandService.getAllSeries();
		List<Brand> brandList = brandService.getBrands();
		List<MiniUiBase> supplierList = miniuiBaseService.querySupplier(new HashMap<String, String>());
		
		Purchaselifecycle   purchaselifecycle = new Purchaselifecycle();
		purchaselifecycle.setIdStatus(14L);//已发货 待确认
		List<Purchaselifecycle> purchaselifecycleList = purchaseOrderService.queryPurchaselifecycle(purchaselifecycle);
		
		model.addAttribute("product", product);
		model.addAttribute("sereisList", sereisList);
		model.addAttribute("brandList", brandList);
		model.addAttribute("supplierList", supplierList);
		model.addAttribute("purchaselifecycleList", purchaselifecycleList);
		return "/web/product2sale";
	}
	
	@RequestMapping("/productList")
	@AdminAccess
	public String productList(HttpServletRequest request,Model model,AdminAgent adminAgent) {
		List<Series> sereisList =  brandService.getAllSeries();
		List<Brand> brandList = brandService.getBrands();
		List<MiniUiBase> siteList = null;
		String type = request.getParameter("type");
		String name = adminAgent.getUsername();
		if(name.equals("30001")){
			if(null == type || "".equals(type)) type = "1";
			 siteList =  miniuiBaseService.getSiteWhereMenDian(type);
		}else{
			if(null == type || "".equals(type)) type = "1";
			 siteList = miniuiBaseService.getSiteByType(type);
		}
		model.addAttribute("sereisList", sereisList);
		model.addAttribute("brandList", brandList);
		model.addAttribute("siteList", siteList);
		return "/web/productList";
	}
	
	
	
	@RequestMapping("/detail")
	@AdminAccess
	public String detail(HttpServletRequest request,Model model) {
		String idProduct = request.getParameter("idproduct");
		Product product = productService.getproduct(idProduct);
		List<Series> sereisList =  brandService.getAllSeries();
		List<Brand> brandList = brandService.getBrands();
		List<MiniUiBase> siteList = miniuiBaseService.getSiteByType("1");
		List<MiniUiBase> supplierList = miniuiBaseService.querySupplier(new HashMap<String, String>());
		
		if("准入库".equals(product.getStatus()))
		{
			Purchaselifecycle   purchaselifecycle = new Purchaselifecycle();
			purchaselifecycle.setIdStatus(14L);//已发货 待确认
			List<Purchaselifecycle> purchaselifecycleList = purchaseOrderService.queryPurchaselifecycle(purchaselifecycle);
			model.addAttribute("purchaselifecycleList", purchaselifecycleList);
		}

		model.addAttribute("product", product);
		model.addAttribute("sereisList", sereisList);
		model.addAttribute("brandList", brandList);
		model.addAttribute("siteList", siteList);
		model.addAttribute("supplierList", supplierList);

		return "/web/productDetail";
	}
}
 
