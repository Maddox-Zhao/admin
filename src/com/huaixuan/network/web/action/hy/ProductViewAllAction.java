package com.huaixuan.network.web.action.hy;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import com.huaixuan.network.biz.domain.hy.ProductViewAll;
import com.huaixuan.network.biz.domain.shop.Brand;
import com.huaixuan.network.biz.domain.shop.BrandStoreDay;
import com.huaixuan.network.biz.enums.EnumAdminPermission;
import com.huaixuan.network.biz.enums.hy.EnumBrandType;
import com.huaixuan.network.biz.enums.hy.EnumSellChannel;
import com.huaixuan.network.biz.enums.hy.EnumSeriesType;
import com.huaixuan.network.biz.enums.hy.EnumStepLocation;
import com.huaixuan.network.biz.enums.hy.EnumTargetCustomer;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.service.admin.RoleAuthorityService;
import com.huaixuan.network.biz.service.admin.RoleService;
import com.huaixuan.network.biz.service.hy.HistoryViewService;
import com.huaixuan.network.biz.service.hy.ProductViewAllService;
import com.huaixuan.network.biz.service.hy.ProductViewService;
import com.huaixuan.network.biz.service.shop.BrandService;
import com.huaixuan.network.common.util.DateUtil;
import com.huaixuan.network.common.util.json.JSONArray;
import com.huaixuan.network.web.action.BaseAction;
import com.huaixuan.network.web.action.interceptor.AdminAccess;
import com.hundsun.network.melody.common.util.StringUtil;
@Controller
@RequestMapping(value ="/productview")
public class ProductViewAllAction extends BaseAction {

	@Autowired
	private ProductViewAllService productViewAllService;

	@Autowired
	private BrandService brandService;

	@AdminAccess({EnumAdminPermission.A_ROLE_VIEW_USER})
    @RequestMapping("/productviewAllList")
    public String historyViewList(@ModelAttribute("productViewAll") ProductViewAll productViewAll, Model model,
    		@RequestParam(value = "isFirst", required = false, defaultValue = "false") String isFirst,
    		@RequestParam(value = "page", required = false, defaultValue = "1") int currPage) {
    	
		if ("true".equals(isFirst)) {
			Date now = new Date();
			Timestamp nowTs = new Timestamp(now.getTime());
//			Date before30 = DateUtil.getDate(now, -30);
//			Timestamp beforeTs = new Timestamp(before30.getTime());
			if (StringUtils.isBlank(productViewAll
					.getDateStart())) {
				productViewAll.setDateStart(DateUtil
						.getTimestampToString(nowTs));
			}
			if (StringUtils.isBlank(productViewAll
					.getDateEnd())) {
				productViewAll.setDateEnd(DateUtil
						.getTimestampToString(nowTs));
			}
		}
		
		QueryPage page = productViewAllService.getProductViewAllByConditionWithPage(productViewAll, currPage, this.pageSize);
		
		List<ProductViewAll> productViewAllSumList = new ArrayList<ProductViewAll>();
		List<ProductViewAll> productViewCostSumList = new ArrayList<ProductViewAll>();
		if(page.getItems()!=null && page.getItems().size()>0){
			productViewAllSumList = productViewAllService.getProductViewAllSumByConditionWithPage(productViewAll);
			
			productViewCostSumList = productViewAllService.productViewCostSumValue(productViewAll);

		}
		
//		model.addAttribute("brandMap", EnumBrandType.toMap());
		List<Brand> brandList = brandService.getBrands();
        model.addAttribute("brandList", brandList);
//		model.addAttribute("locationMap", EnumStepLocation.toMap());
		model.addAttribute("seriesMap", EnumSeriesType.toMap());
//		model.addAttribute("tarcustomerMap", EnumTargetCustomer.toMap());
		model.addAttribute("sellChannelMap", EnumSellChannel.toMap());
		
		
    	if(page != null){
    		model.addAttribute("query", page);
    		model.addAttribute("productViewAllSumList", productViewAllSumList);
    		model.addAttribute("productViewCostSumList", productViewCostSumList);
    	}
    	return "/hy/productViewAllList";
    }
	
	
	@AdminAccess({EnumAdminPermission.A_ROLE_VIEW_USER})
    @RequestMapping("/brandStoreViewInit")
    public String brandStoreViewInit( Model model,
    		HttpServletRequest request) {
    	
		List<Brand> brandList = brandService.getBrands();
        model.addAttribute("brandList", brandList);
        
    	return "/hy/brandStoreDay";
    }
	
	@AdminAccess({EnumAdminPermission.A_ROLE_VIEW_USER})
    @RequestMapping("/brandStoreView")
    public String brandStoreView( Model model,
    		HttpServletRequest request,@RequestParam(value = "yearMonth1",required = false) String yearMonth1,
    		@RequestParam(value = "dateStart",required = false) String dateStart,
            @RequestParam(value = "dateEnd",required = false) String dateEnd,
            @RequestParam(value = "idbrand",required = false) String idbrand,
    		@RequestParam(value = "page", required = false, defaultValue = "1") int currPage) {
		
		if(StringUtils.isNotBlank(idbrand) 
				&& StringUtils.isNotBlank(dateStart) && StringUtils.isNotBlank(dateEnd)){
			BrandStoreDay brandStoreDay = new BrandStoreDay();
			brandStoreDay.setIdBrand(Long.parseLong(idbrand));
			brandStoreDay.setDateEnd(dateEnd);
			brandStoreDay.setDateStart(dateStart);
			
			QueryPage page = productViewAllService.getBrandStoreDayConditionWithPage(brandStoreDay, currPage, this.pageSize);
			model.addAttribute("query", page);
			model.addAttribute("dateStart", dateStart);
			model.addAttribute("dateEnd", dateEnd);
			model.addAttribute("idbrand", idbrand);
		}
		
		if(StringUtils.isNotBlank(yearMonth1)){
			BrandStoreDay brandStoreDay = new BrandStoreDay();
			brandStoreDay.setDateEnd(yearMonth1);
			brandStoreDay.setDateStart(yearMonth1);
			
			QueryPage page = productViewAllService.getBrandStoreDayConditionWithPage(brandStoreDay, 1, 100);
			model.addAttribute("brandStoreDayAllList", page.getItems());
			model.addAttribute("yearMonth1", yearMonth1);
		}
		
		List<Brand> brandList = brandService.getBrands();
        model.addAttribute("brandList", brandList);
        model.addAttribute("brandMap", EnumBrandType.toMap());
        
    	return "/hy/brandStoreDay";
    }
	
    @RequestMapping("/addProductHistory")
    public String addProductHistory( Model model,
    		HttpServletRequest request) {
    	
    	productViewAllService.addProductHistory();
		return "/goods/timetask/checkGoodsNum"; 
    }
	
    
    @RequestMapping("/productHistoryList")
    public String productHistoryList(@ModelAttribute("productViewAll") ProductViewAll productViewAll, Model model,
    		@RequestParam(value = "datestr", required = false) String datestr,
    		@RequestParam(value = "idBrand", required = false) long idBrand,
    		@RequestParam(value = "idStatus", required = false, defaultValue = "1") int idStatus,
    		@RequestParam(value = "page", required = false, defaultValue = "1") int currPage) {
    	
    	Date da = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		if (StringUtils.isBlank(datestr)) {
			
            datestr = df.format(da);
//          String date = "2013-10-08";
           
		}
		try {
			productViewAll.setDate(df.parse(datestr));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if(idStatus==0){
			idStatus= 1;
		}
		productViewAll.setIdBrand(idBrand);
		QueryPage page = productViewAllService.getProductHistoryByConditionWithPage(productViewAll, currPage, 100);
		
		
		List<Brand> brandList = brandService.getBrands();
        model.addAttribute("brandList", brandList);
		model.addAttribute("seriesMap", EnumSeriesType.toMap());
		model.addAttribute("sellChannelMap", EnumSellChannel.toMap());
		
		
		model.addAttribute("query", page);
		model.addAttribute("datestr", datestr);
		model.addAttribute("idStatus", idStatus);
		
    	return "/hy/productHistoryList";
    }
	
}
