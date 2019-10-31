package com.huaixuan.network.web.action.shop;

import java.util.List;

import javax.servlet.ServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.huaixuan.network.biz.domain.goods.Category;
import com.huaixuan.network.biz.domain.goods.Goods;
import com.huaixuan.network.biz.domain.shop.Brand;
import com.huaixuan.network.biz.enums.EnumAdminPermission;
import com.huaixuan.network.biz.enums.EnumGoodsStatus;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.service.goods.CategoryManager;
import com.huaixuan.network.biz.service.goods.GoodsManager;
import com.huaixuan.network.biz.service.goods.GoodsTesService;
import com.huaixuan.network.biz.service.shop.BrandService;
import com.huaixuan.network.web.action.BaseAction;
import com.huaixuan.network.web.action.interceptor.AdminAccess;
import com.hundsun.network.melody.common.util.StringUtil;
@Controller
@RequestMapping("/shop")
public class ShoppingCartTesAction extends BaseAction{
	@Autowired
	private  BrandService brandService;
	@Autowired
	private GoodsTesService goodsTesService;
	
	@Autowired
	private CategoryManager categoryManager;
	
	//private @Value("${liangpin99.url}")String liangpin99url;
	/**
     * 购物车商品查询
     * @return
     * @throws Exception 
     */
	@AdminAccess({EnumAdminPermission.A_GOODS_VIEW_USER})
	@RequestMapping("/shoppingCartvm")
	public String search(@ModelAttribute("goods") Goods goods, Model model,
    		@RequestParam(value = "page", required = false, defaultValue = "1") int currPage,
    		@RequestParam(value= "pageFlag",defaultValue = "on_sale") String pageFlag,
    		ServletRequest request) throws Exception{
        //取得品牌列表，用于检索条件
        List<Brand> brandList = brandService.getBrands();
        model.addAttribute("brandList", brandList);
        if("y".equals(goods.getGoodsKind())){
        	goods.setIsAgent("y");
        	goods.setIsWholesale("n");
        }else if("w".equals(goods.getGoodsKind())){
        	goods.setIsAgent("n");
        	goods.setIsWholesale("y");
        }else if("z".equals(goods.getGoodsKind())){
        	goods.setIsAgent("y");
        	goods.setIsWholesale("y");
        }
        
        /*if (EnumGoodsStatus.ON_SALE.getKey().equals(pageFlag)) {
            goods.setGoodsStatus(EnumGoodsStatus.ON_SALE.getKey());
        } else if (EnumGoodsStatus.ON_DEPOT.getKey().equals(pageFlag)) {
            goods.setGoodsStatus(EnumGoodsStatus.ON_DEPOT.getKey());
        } else if (EnumGoodsStatus.CUT_PRICE.getKey().equals(pageFlag)) {
            goods.setIsCutprice(EnumGoodsStatus.CUT_PRICE.getValue());
        } else if (EnumGoodsStatus.ACTIVITY_GOODS.getKey().equals(pageFlag)) {
            goods.setIsCutprice(EnumGoodsStatus.ACTIVITY_GOODS.getValue());
        } else if (EnumGoodsStatus.IS_AGENT.getKey().equals(pageFlag)) {
            goods.setIsAgent(EnumGoodsStatus.IS_AGENT.getValue());
        }*/

        /*if(goods.getIsPaipai()!=null && goods.getIsPaipai().equals("1")){
        	goods.setIsPaipai("y");
        }
        if(goods.getIsTaobao()!=null && goods.getIsTaobao().equals("1")){
        	goods.setIsTaobao("y");
        }*/
    	QueryPage page = goodsTesService.getMyShoppingCartConditionWithPages(goods, currPage, this.pageSize);
    	if(page != null){
    		model.addAttribute("query", page);
    	}

        List<Goods> goodsList = (List<Goods>)page.getItems();
        if(goodsList != null && goodsList.size() > 0){
            for (Goods temp : goodsList) {
                temp.setCatName(categoryManager.getCatFullNameByCatcodeSimple(temp.getCatCode(), ">"));

            }
        }
        List<Category> catList = categoryManager.getCatInfoByDepth(1);
        
        //重定向后request是新的对象，标准的字符集是 "ISO-8859-1"
        if(StringUtil.isNotBlank(request.getParameter("errormessage"))){
	        model.addAttribute("errormessage", request.getParameter("errormessage"));
        }
        model.addAttribute("catList", catList);
       // model.addAttribute("pageFlag", pageFlag);
        model.addAttribute("goods", goods);
       // model.addAttribute("liangpin99url", liangpin99url);
        return "/shop/shoppingCartvm";
    }
}
