package com.huaixuan.network.web.action.product;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.huaixuan.network.biz.domain.goods.Goods;
import com.huaixuan.network.biz.service.goods.GoodsTesService;
import com.huaixuan.network.common.util.miniui.MiniUiUtil;

@Controller
@RequestMapping("/product")
public class ProducctMiniAction {
	@Autowired
	private GoodsTesService goodsTesService;
	//  /product/aGoodsMiniTest
	
	@RequestMapping("/aGoodsMiniTest")
	public String miniGoodsTes(Goods goods,HttpServletRequest request,Model model){
		model.addAttribute("goods",goods.getId());
		return "/product/aGoodsMiniTest";
	}
	
	@RequestMapping("/aGoodsSearchMiniTest")
	@ResponseBody
	public Object miniSelectGoods(HttpServletRequest request,Goods goods){
		Map<String, String> searchMap = MiniUiUtil.getParameterMap(request);
		
		MiniUiUtil.trimAndConvSpeSqlStr(searchMap, false, true, true);//去除空格
		
		return goodsTesService.searchAllOrderId(searchMap);
		
	}
	
}
