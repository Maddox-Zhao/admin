package com.huaixuan.network.web.action.platformstock;

import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.huaixuan.network.biz.domain.admin.AdminAgent;
import com.huaixuan.network.biz.domain.platformstock.StockReserve;
import com.huaixuan.network.biz.domain.platformstock.StockUpdate;
import com.huaixuan.network.biz.domain.platformstock.StockUpdateLog;
import com.huaixuan.network.biz.domain.product.Product;
import com.huaixuan.network.biz.service.platformstock.HigoPlatFormStockUpdate;
import com.huaixuan.network.biz.service.platformstock.KaolaPlatformStockUpdate;
import com.huaixuan.network.biz.service.platformstock.ShangPinPlatformStocuUpdate;
import com.huaixuan.network.biz.service.platformstock.SiKuPlatFormStockUpdate;
import com.huaixuan.network.biz.service.platformstock.StockUpdateLogService;
import com.huaixuan.network.biz.service.platformstock.StockUpdateService;
import com.huaixuan.network.biz.service.platformstock.TmallPlatFormStockUpdate;
import com.huaixuan.network.biz.service.platformstock.YhdPlatformStockUpdate;
import com.huaixuan.network.biz.service.platformstock.ZhenPinPlatformStockUpdate;
import com.huaixuan.network.common.util.miniui.MiniUiUtil;
import com.huaixuan.network.web.action.interceptor.AdminAccess;
import com.hundsun.common.lang.StringUtil;



/**
 * @author Mr_Yang   2016-10-26 16:12:40 同步库存日志查询
 **/

@Controller
@RequestMapping("stockupdate")
public class StockUpdateLogAction
{
	@Autowired
	private StockUpdateService stockUpdateService;
	@Autowired
	private StockUpdateLogService stockUpdateLogService; 
	
	  
 
	@RequestMapping("/searchStockUpdate")
	@ResponseBody
	public Object searchStockUpdate(Model model,HttpServletRequest request)
	{
		Map<String,String> searchMap = MiniUiUtil.getParameterMap(request);
		return stockUpdateService.searchStockUpdate(searchMap);
	}
	
 
	@RequestMapping("/toSearchStockUpdate")
	public String toSearchStockUpdate(Model model,HttpServletRequest request)
	{
		return "/autosync/stockUpdateList";
	}
	
	
	
	

	@RequestMapping("/forYuliu")
	public String yuliu(Model model,HttpServletRequest request,AdminAgent adminAgent)
	{
		model.addAttribute("curSite", adminAgent.getSiteId());
		return "/autosync/yuliu";
	}
	
	
	@RequestMapping("/yuliuchanpin")
	public @ResponseBody
	StockUpdate getProduct(HttpServletRequest request) {
		String sku = request.getParameter("sku");
		String type = request.getParameter("type");
		StockUpdate stu = new StockUpdate();
		stu.setSku(sku);
		stu.setType(type);
		StockUpdate stus = stockUpdateService.selectstock(stu);
		return stus;
	}
	
	@RequestMapping("/updateStock")
	@AdminAccess
	public @ResponseBody Object updateStock(AdminAgent adminAgent,HttpServletRequest request)
	{
		Map<String,String> requestMap = MiniUiUtil.getParameterMap(request);
		
		String sku = request.getParameter("sku");
		String type =  request.getParameter("type");
		StockUpdate stoctUpdate = new StockUpdate();
		stoctUpdate.setSku(sku);
		stoctUpdate.setType(type);
		StockUpdate stoct = stockUpdateService.selectstock(stoctUpdate);
		int orderStockNum = stoct.getOrderStockNum();//查询出的订单总数量
		int nowStockNum = stoct.getNowStockNum();//查询出的当前库存数
		
		if(StringUtil.isBlank(request.getParameter("num"))){
			return "numNo";
		}
		
		int num = Integer.parseInt(request.getParameter("num"));//前台预留的订单数量
		int total = orderStockNum + num;
		if(total > nowStockNum){
			return "no";
		}
		stockUpdateService.updateStockUpdateorder(requestMap);
		
		StockReserve stockreserve = new StockReserve();
		stockreserve.setUsername(adminAgent.getUsername());
		stockreserve.setSku(request.getParameter("sku"));
		stockreserve.setType(request.getParameter("type"));
		stockreserve.setPtype(request.getParameter("ptype"));
		stockreserve.setNum(num);
		stockreserve.setStatus(1);
		stockUpdateService.insertStockReserve(stockreserve);
		return "ok";
		
	}
	
	//更新平台可售和下架的产品sku 和库存   -全量更新-寺库
	@RequestMapping("/updateStockUpdate")
	@ResponseBody
	public Object updateStockUpdate(Model model,HttpServletRequest request)
	{
		Map<String,String> requestMap = MiniUiUtil.getParameterMap(request);
		StockUpdate stockUpdate = (StockUpdate)MiniUiUtil.Map2Object(requestMap, StockUpdate.class);
		stockUpdateService.updateStockUpdate(stockUpdate);
		return "ok";
	}
	  
	
	
	
	
	@RequestMapping("/searchStockUpdateLog")
	@ResponseBody
	public Object searchStockUpdateLog(Model model,HttpServletRequest request)
	{
		Map<String,String> searchMap = MiniUiUtil.getParameterMap(request);
		return stockUpdateService.searchStockUpdateLog(searchMap);
	}
	
 
	@RequestMapping("/toSearchStockUpdateLog")
	public String toSearchStockUpdateLog(Model model,HttpServletRequest request)
	{
		return "/autosync/stockUpdateLogList";
	}
	
	
	
	
	
	
	
	@RequestMapping("/searchStockLog")
	@ResponseBody
	public Object searchStock(Model model,HttpServletRequest request){
		Map<String,String> searchMap = MiniUiUtil.getParameterMap(request);
		
		if(request.getParameter("location")!=null){
			  String locations = request.getParameter("location");
			
			if(locations.length()>0){
			int location = Integer.parseInt(locations);
			if(location==1){
				searchMap.put("location", "sh");
			}else if(location==2){
				searchMap.put("location", "hk");
			}
		}
	}
		
		return stockUpdateLogService.searchAllStock(searchMap);
	}
	
	@RequestMapping("/toSearchStockLog")
	public String toSearchStock(Model model,HttpServletRequest request)
	{
		return "/autosync/stockUpdateLog";
	}
	
	
	
	/*
	
	//查找所有的渠道
	@RequestMapping("/getAllLocation")
	public @ResponseBody Object getAllSellChannel(HttpServletRequest request)
	{
		return stockUpdateLogService.getAllLocation();		
	}
	
*/	
	
//	stockUpdateLogService
	
}
 
