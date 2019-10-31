package com.huaixuan.network.web.action.hy;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.huaixuan.network.biz.domain.admin.Role;
import com.huaixuan.network.biz.domain.hy.HistoryView;
import com.huaixuan.network.biz.domain.hy.ProductView;
import com.huaixuan.network.biz.domain.shop.Brand;
import com.huaixuan.network.biz.enums.EnumAdminPermission;
import com.huaixuan.network.biz.enums.hy.EnumBrandType;
import com.huaixuan.network.biz.enums.hy.EnumSeriesType;
import com.huaixuan.network.biz.enums.hy.EnumStepLocation;
import com.huaixuan.network.biz.enums.hy.EnumTargetCustomer;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.service.admin.RoleAuthorityService;
import com.huaixuan.network.biz.service.admin.RoleService;
import com.huaixuan.network.biz.service.hy.HistoryViewService;
import com.huaixuan.network.biz.service.hy.ProductViewService;
import com.huaixuan.network.biz.service.shop.BrandService;
import com.huaixuan.network.common.util.DateUtil;
import com.huaixuan.network.common.util.json.JSONArray;
import com.huaixuan.network.web.action.BaseAction;
import com.huaixuan.network.web.action.interceptor.AdminAccess;
import com.hundsun.network.melody.common.util.StringUtil;
@Controller
@RequestMapping(value ="/productview")
public class ProductViewAction extends BaseAction {

	@Autowired
	private ProductViewService productViewService;
	
	@Autowired
	private BrandService brandService;


	@AdminAccess({EnumAdminPermission.A_ROLE_VIEW_USER})
    @RequestMapping("/productviewList")
    public String historyViewList(@ModelAttribute("productView") ProductView productView, Model model,
    		@RequestParam(value = "page", required = false, defaultValue = "1") int currPage) {
    	
		
		
		QueryPage page = productViewService.getProductViewByConditionWithPage(productView, currPage, this.pageSize);
		
//		model.addAttribute("brandMap", EnumBrandType.toMap());
		List<Brand> brandList = brandService.getBrands();
        model.addAttribute("brandList", brandList);
		model.addAttribute("locationMap", EnumStepLocation.toMap());
		model.addAttribute("seriesMap", EnumSeriesType.toMap());
		model.addAttribute("tarcustomerMap", EnumTargetCustomer.toMap());
		
    	if(page != null){
    		model.addAttribute("query", page);
    	}
    	return "/hy/productViewList";
    }
}
