
package com.huaixuan.network.web.action.autosyn;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.huaixuan.network.biz.service.autosyn.AutoSyncShangPingManager;
import com.huaixuan.network.biz.service.autosyn.AutoSyncWangDianTongManager;
import com.huaixuan.network.biz.service.autosyn.AutoSyncZouXiuManager;
import com.huaixuan.network.biz.service.autosyn.KaoLaSyncManager;
import com.huaixuan.network.biz.service.autosyn.SiKuSyncManager;
import com.huaixuan.network.biz.service.autosyn.ZhenPinSyncManager;
import com.huaixuan.network.web.action.BaseAction;

 //暂时弃用
public class AutoSycAction extends BaseAction 
{
	@Autowired
	private AutoSyncShangPingManager autoSyncShangPingManager;
	
	
	@Autowired
	private AutoSyncZouXiuManager autoSyncZouXiuManager;
	
	
	@Autowired
	private AutoSyncWangDianTongManager autoSyncWangDianTongManager;
	
	
	@Autowired
	private KaoLaSyncManager kaoLaSyncManager;
	
	
	@Autowired
	private ZhenPinSyncManager zhenPinSyncManager;
	
	@Autowired
	private SiKuSyncManager siKuSyncManager;
	
	
	
	/**
	 * 同步走秀sku到本地  isAll=true  更新所有  默认只更新一个小时内的数据
	 * @param model  
	 * @return  
	 */
	@RequestMapping(value = "/timetask/updateZouXiuSku")
    public String autoSyncZouXiuSku2Local(Model model,HttpServletRequest request)
	{
		String isAll = request.getParameter("isAll");
		List<String> resultListSh = autoSyncZouXiuManager.updateZouXiuSku("hk",isAll);
		model.addAttribute("resultList",resultListSh);
		 return "/autosync/success";
	}
	
	/**
	 * 同步走秀库存
	 * @param model  
	 * @return  
	 */
	@RequestMapping(value = "/timetask/updateZouXiuStock")
    public String autoSyncZouXiuStock(Model model)
	{
		List<String> resultListSh = autoSyncZouXiuManager.getZouXiuStock("hk");
		model.addAttribute("resultList",resultListSh);
		 return "/autosync/success";
	}
	
	
	
	
	//旺店通
	@RequestMapping(value = "/timetask/updateWangDianTongSku2Location")
	public String updateWangDianTongSku2Location()
	{
		autoSyncWangDianTongManager.updateWDTSku2Loacation();
		return "/autosync/success";
	}
	
	@RequestMapping(value = "/timetask/updateWangDianStock")
	public String updateWangDianStock()
	{
		autoSyncWangDianTongManager.updateWangDianTongStock();
		return "/autosync/success";
	}
	

	
	//全量更新尚品 考拉sku到本地
	@RequestMapping("/timetask/updateAllSku2Location")
	public String updateSku2Location(HttpServletRequest request)
	{
		String  isAll = request.getParameter("isAll");
		isAll = "day"; //默认更新两天内的数据
		//尚品
		autoSyncShangPingManager.updateShangpSku("hk",isAll);
		autoSyncShangPingManager.updateShangpSku("sh",isAll);
		
		
		//考拉
		kaoLaSyncManager.updateSku2Location();
		
		//旺店通
		//autoSyncWangDianTongManager.updateWDTSku2Loacation(); //更新旺店通
		//autoSyncWangDianTongManager.updateWangDianTongStock();//旺店通库存一天同步一次
		
		
		//走秀
		//autoSyncZouXiuManager.updateZouXiuSku("hk","true");

		
		return "/autosync/success";
	}
	
	//更新平台可售和下架的产品sku 和库存   -全量更新-尚品
	@RequestMapping(value = "/timetask/updateShangPingStock")
    public String autoSyncShangPing(Model model)
	{
		autoSyncShangPingManager.updateShangpSku("hk","day");
		autoSyncShangPingManager.updateShangpSku("sh","day");//先更新尚品sku到本地  查询本地库存需要  
		
		
		List<String> resultListSh = autoSyncShangPingManager.syncShangPingStockForSh();
		List<String> resultListHk = autoSyncShangPingManager.syncShangPingStockForHk(); //同步香港库存
		resultListSh.addAll(resultListHk);
		model.addAttribute("resultList",resultListSh);
		 return "/autosync/success";
	}
	
	//更新平台可售和下架的产品sku 和库存   -全量更新-考拉
	@RequestMapping("/timetask/updateFullProductForKaoLa")
	public String updateAllCanSaleProductForKaoLa(Model model,HttpServletRequest request)
	{
		//网易考拉
		kaoLaSyncManager.updateCanSaleProduct();

		model.addAttribute("resultList", new ArrayList<String>());
		return "/autosync/success";
	}
	
	//更新平台可售和下架的产品sku 和库存   -全量更新-珍品
	@RequestMapping("/timetask/updateFullProductForZhenPin")
	public String updateAllCanSaleProduct(Model model,HttpServletRequest request)
	{
		//珍品
		zhenPinSyncManager.updateCanSaleProduct();
		model.addAttribute("resultList", new ArrayList<String>());
		return "/autosync/success";
	}
	
	
	//更新平台可售和下架的产品sku 和库存   -全量更新-珍品
	@RequestMapping("/timetask/updateFullProductForSiKu")
	public String updateAllStockForSiku(Model model,HttpServletRequest request)
	{
		//寺库
		siKuSyncManager.updateCanSaleProduct();
		model.addAttribute("resultList", new ArrayList<String>());
		return "/autosync/success";
	}
	
	
	
	//更新平台某个时间段内的库存改变  早上一次 晚上一次  某天有过库存变化的  某段时间内的改为定时读表执行  （暂未执行）
	@RequestMapping("/timetask/updateChangedStockProduct")
	public String updateChangedStockProduct(Model model,HttpServletRequest request)
	{
		
		String typeSh = "sh";
		String typeHk = "hk";
		//获取到有库存改变的数据
		Map<String,Integer> needUpdateMapSh = autoSyncShangPingManager.getChangedStock(typeSh);
		Map<String,Integer>  needUpdateMapHk = autoSyncShangPingManager.getChangedStock(typeHk);
		

		//更新珍品库存
		zhenPinSyncManager.updateChangedStock(needUpdateMapSh, typeSh);
		zhenPinSyncManager.updateChangedStock(needUpdateMapHk, typeHk);
		
		
		//更新尚品库存
		autoSyncShangPingManager.updateChangedStock(needUpdateMapSh, typeSh);
		autoSyncShangPingManager.updateChangedStock(needUpdateMapHk, typeHk);

		
		//更新考拉库存
		kaoLaSyncManager.updateChangedStock(needUpdateMapSh, typeSh);
		kaoLaSyncManager.updateChangedStock(needUpdateMapHk, typeHk);

		model.addAttribute("resultList", new ArrayList<String>());
		
		return "/autosync/success";
	}
	
	@RequestMapping("/timetask/test")
	public String test()
	{
		int a = kaoLaSyncManager.getKaoLaStockByKaoLaSku("sh", "5342015283050", "11623-2028");
		System.out.println(a);
		return "/autosync/success";
	}
	
	 
	
	
}
