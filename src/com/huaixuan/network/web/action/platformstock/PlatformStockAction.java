package com.huaixuan.network.web.action.platformstock;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.write.WritableSheet;


import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.huaixuan.network.biz.dao.autosyn.AutoSyncDao;
import com.huaixuan.network.biz.dao.platformstock.HxPlatformStockUpdateDao;
import com.huaixuan.network.biz.dao.platformstock.PlatformStockUpdateDao;
import com.huaixuan.network.biz.dao.product.ProductDao;
import com.huaixuan.network.biz.dao.provider.ProviderGoodsUpdateDao;
import com.huaixuan.network.biz.domain.platformstock.StockUpdate;
import com.huaixuan.network.biz.domain.platformstock.StockUpdateLog;
import com.huaixuan.network.biz.domain.product.Product;
import com.huaixuan.network.biz.domain.product.SupplyGood;
import com.huaixuan.network.biz.domain.provider.ProvideGoodsXiYou;
import com.huaixuan.network.biz.service.autosyn.HttpRequest;
import com.huaixuan.network.biz.service.goods.impl.GoodsManagerImpl;
import com.huaixuan.network.biz.service.platformstock.FenQiLePlatFormStocuUpdate;
import com.huaixuan.network.biz.service.platformstock.GouDongPlatFormStocuUpdate;
import com.huaixuan.network.biz.service.platformstock.HigoPlatFormStockUpdate;
import com.huaixuan.network.biz.service.platformstock.KaolaPlatHaiTaoformStockUpdate;
import com.huaixuan.network.biz.service.platformstock.KaolaPlatformStockUpdate;
import com.huaixuan.network.biz.service.platformstock.MeiLiHuiNewPlatFormStockUpdate;
import com.huaixuan.network.biz.service.platformstock.MeiLiHuiPlatFormStockUpdate;
import com.huaixuan.network.biz.service.platformstock.OFashionPlatFormStockUpdate;
import com.huaixuan.network.biz.service.platformstock.OfMiChengPaltFormStockupdate;
import com.huaixuan.network.biz.service.platformstock.PinDuoDuoNewPlatFormStockUpdate;
import com.huaixuan.network.biz.service.platformstock.PinDuoDuoPlatFormStockUpdate;
import com.huaixuan.network.biz.service.platformstock.ShangPinPlatformStocuUpdate;
import com.huaixuan.network.biz.service.platformstock.ShePinPlatFormStockUpdate;
import com.huaixuan.network.biz.service.platformstock.SiKuNewPlatFormStockUpdate;
import com.huaixuan.network.biz.service.platformstock.SiKuPlatFormStockUpdate;
import com.huaixuan.network.biz.service.platformstock.StockNotUpdateService;
import com.huaixuan.network.biz.service.platformstock.StockUpdateService;
import com.huaixuan.network.biz.service.platformstock.SuNingPlatFormStockUpdate;
import com.huaixuan.network.biz.service.platformstock.TmallPlatFormStockUpdate;
import com.huaixuan.network.biz.service.platformstock.WeiMobPlatFormStockUpdate;
import com.huaixuan.network.biz.service.platformstock.XiaohongshuPlatFormStockupdate;
import com.huaixuan.network.biz.service.platformstock.YhdPlatformStockUpdate;
import com.huaixuan.network.biz.service.platformstock.YinTaiPlatFormStockUpdate;
import com.huaixuan.network.biz.service.platformstock.YunShangPlatFormStockUpdate;
import com.huaixuan.network.biz.service.platformstock.ZhenPinPlatformStockUpdate;
import com.huaixuan.network.biz.service.platformstock.impl.GouDongGlobalPlatFormStockUpdateImpl;
import com.huaixuan.network.biz.service.product.ProductService;
import com.huaixuan.network.biz.service.provider.XiYouPlatFormStockUpdate;
import com.huaixuan.network.biz.service.provider.YShangPlatFormStockUpdate;
import com.huaixuan.network.common.util.json.JSONArray;
import com.huaixuan.network.common.util.json.JSONException;
import com.huaixuan.network.common.util.json.JSONObject;
import com.huaixuan.network.common.util.miniui.MiniUiUtil;
import com.hundsun.common.lang.StringUtil;
import com.jd.open.api.sdk.DefaultJdClient;
import com.jd.open.api.sdk.JdClient;
import com.jd.open.api.sdk.JdException;
import com.jd.open.api.sdk.request.ware.WareSkuStockUpdateRequest;
import com.jd.open.api.sdk.response.ware.WareSkuStockUpdateResponse;
import com.suning.api.HttpResponse;





/**
 * @author Mr_Yang   2016-5-10 下午05:28:16
 **/

@Controller
public class PlatformStockAction
{
	@Autowired
	private StockUpdateService stockUpdateService;
	
	@Autowired
	private StockNotUpdateService stockNotUpdateService;
	
	@Autowired
	private ProviderGoodsUpdateDao providerGoodsUpdateDao;
	
	@Autowired
	private KaolaPlatformStockUpdate kaolaPlatformStockUpdate;
	
	@Autowired
	private KaolaPlatHaiTaoformStockUpdate kaolaPlatHaiTaoformStockUpdate;
	
	@Autowired
	private ZhenPinPlatformStockUpdate zhenPinPlatformStockUpdate;
	
	@Autowired
	private ShangPinPlatformStocuUpdate shangPinPlatformStocuUpdate;
	
	@Autowired
	private SiKuPlatFormStockUpdate siKuPlatFormStockUpdate;
	
	@Autowired
	private YunShangPlatFormStockUpdate yunShangPlatFormStockUpdate;
	
	@Autowired
	private SiKuNewPlatFormStockUpdate siKunewPlatFormStockUpdate;
	
	@Autowired
	private MeiLiHuiPlatFormStockUpdate meiLiHuiPlatFormStockUpdate;
	
	@Autowired
	private MeiLiHuiNewPlatFormStockUpdate meiLiHuinewPlatFormStockUpdate;
	
	@Autowired
	private XiaohongshuPlatFormStockupdate xiaohongshuPlatFormStockupdate;
	
	@Autowired
	private YhdPlatformStockUpdate yhdPlatformStockUpdate;
	
	
	@Autowired
	private HigoPlatFormStockUpdate higoFormStockUpdate;
	
	@Autowired
	private GouDongPlatFormStocuUpdate gouDongPlatFormStocuUpdate;
	
	@Autowired
	private TmallPlatFormStockUpdate tmallFormStockUpdate;
	
	@Autowired
	private ShePinPlatFormStockUpdate shePinFormStockUpdate;
	
	@Autowired
	private FenQiLePlatFormStocuUpdate fenQiLePlatFormStocuUpdate;
	
	@Autowired
	private PinDuoDuoPlatFormStockUpdate pinDuoDuoPlatFormStockUpdate;
	
	@Autowired
	private PinDuoDuoNewPlatFormStockUpdate pinDuoDuoNewPlatFormStockUpdate;
	
	@Autowired
	private OFashionPlatFormStockUpdate ofashionPlatFormStockUpdate;
	
	@Autowired
	private WeiMobPlatFormStockUpdate weiMobPlatFormStockUpdate;
	
	@Autowired
	private YinTaiPlatFormStockUpdate yinTaiPlatFormStockUpdate;
	
	@Autowired
	private SuNingPlatFormStockUpdate suNingPlatFormStockUpdate;
	
	@Autowired
	private PlatformStockUpdateDao platformStockUpdateDao;
	
	@Autowired
	private HxPlatformStockUpdateDao hxPlatformStockUpdateDao;
	
	@Autowired
	private OfMiChengPaltFormStockupdate offMiChengPaltFormStockupdate;
	
	@Autowired
	private XiYouPlatFormStockUpdate xiYouPlatFormStockUpdate;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private ProductDao productDao;
	
	@Autowired
	private AutoSyncDao autoSyncDao;
	
	@Autowired
	private MeiLiHuiPlatFormStockUpdate MeiLiHuiPlatFormStockUpdate;
	
	@Autowired
	private OfMiChengPaltFormStockupdate ofMiChengPaltFormStockupdate;
	
	  @Autowired
	  private YShangPlatFormStockUpdate yShangPlatFormStockUpdate;
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	//更新所有平台的sku到本地 http://www.shangshangss.com:8078/timetask/updateAllPlatformSku2Location.html?type=
	@RequestMapping("/timetask/updateAllPlatformSku2Location")
	public String updateAllPlatformSku2Locations(HttpServletRequest request)
	{
		String platform = request.getParameter("type"); //更新某个平台
		
		System.out.println(sdf.format(new Date()) + " sync sku start....");
		long startTime = System.currentTimeMillis();

		if("zp".equals(platform))
		{
			//珍品（1小时40分）
			zhenPinPlatformStockUpdate.updateSku2Location();  //有上架下架状态 status，更库存没有status
		}
		else if("kl".equals(platform))
		{
			//考拉（3个小时10分）  20分钟
			kaolaPlatformStockUpdate.updateSku2Location(); //都有 *
		}
		else if("klht".equals(platform))
		{
			//考拉海淘
			kaolaPlatHaiTaoformStockUpdate.updateSku2Location();
		}
//		else if("yhd".equals(platform))
//		{
//			//1号店
//			yhdPlatformStockUpdate.updateSku2Location();
//		}
//		else if("sp".equals(platform))
//		{
//			//尚品
//			shangPinPlatformStocuUpdate.updateSku2Location();
//		}
//		else if("hg".equals(platform))
//		{
//			//higo
//			higoFormStockUpdate.updateSku2Location();
//		}
		else if("tmall".equals(platform))
		{
			//tmall
			tmallFormStockUpdate.updateSku2Location();  //获取sku有状态，更新库存无 *
		}
		else if("fql".equals(platform))
		{
			//分期乐
//			//fenQiLePlatFormStocuUpdate.updateSku2Location();  //获取sku有状态，更库存无
		}
		else if("jd".equals(platform))
		{
			//京东国内、国外(京东全球购包含在里，用type和App_key区分)
			gouDongPlatFormStocuUpdate.updateSku2Location(); //有，更库无 *
			
		}
		else if("pdd".equals(platform))
		{
			//拼多多（5分钟）
			pinDuoDuoPlatFormStockUpdate.updateSku2Location(); //有，更库存无
		}
		else if("xhs".equals(platform))
		{
			//小红书
			xiaohongshuPlatFormStockupdate.updateSku2Location(); //已审核 *
		}
		else if("mlh".equals(platform))
		{
			//魅力惠
			meiLiHuiPlatFormStockUpdate.updateSku2Location(); //启用、禁用，更库存没有状态 *
		}
		else if("mlhnew".equals(platform))
		{
			//魅力惠 5000以上的和魅力惠 5000以下的，都是海外
			meiLiHuinewPlatFormStockUpdate.updateSku2Location(); //启用、禁用，更库存没有状态 *
		}
		else if("ofashion".equals(platform))
		{
			//OFashion
			ofashionPlatFormStockUpdate.updateSku2Location();  //获取sku有状态，更时没有 
		}else if("weimob".equals(platform))
		{
			//微盟(WeiMob)
			// weiMobPlatFormStockUpdate.updateSku2Location();
			
		}else if("yinTai".equals(platform)){
			//银泰(yinTai)
			yinTaiPlatFormStockUpdate.updateSku2Location(); //无
		}else if("ofmc".equals(platform)){
			
			//Ofasion迷橙B2B
			ofMiChengPaltFormStockupdate.updateSku2Location(); //有
		}else if("shepin".equals(platform))
		{
			//天猫奢品
			shePinFormStockUpdate.updateSku2Location();// *
		}
		else if("suning".equals(platform))
		{
			//苏宁
//			suNingPlatFormStockUpdate.updateSku2Location();
		}else if("pddnew".equals(platform))
		{
			//新拼多多
			pinDuoDuoNewPlatFormStockUpdate.updateSku2Location();
		}else if("siku".equals(platform))
		{
			//siku
			siKuPlatFormStockUpdate.updateSku2Location();
		}else if("yunshang".equals(platform))
		{
			//云尚
			yunShangPlatFormStockUpdate.updateSku2Location();
		}
		else if("all".equals(platform))
		{
			//魅力惠一个小时
			meiLiHuiPlatFormStockUpdate.updateSku2Location(); 
			//魅力惠 5000以上的和魅力惠 5000以下的，都是海外
			meiLiHuinewPlatFormStockUpdate.updateSku2Location(); 
			//珍品 2个小时
			zhenPinPlatformStockUpdate.updateSku2Location();
		
			//考拉 4个小时     03:18:01-06:51:15
			kaolaPlatformStockUpdate.updateSku2Location();
			
			kaolaPlatHaiTaoformStockUpdate.updateSku2Location();
			
			//1号店(没了)             
//			yhdPlatformStockUpdate.updateSku2Location();
//			
//			//尚品(没了)
//			shangPinPlatformStocuUpdate.updateSku2Location();
			
			
			//higo(没了) 半小时   06:51:26-07:13:35
//			higoFormStockUpdate.updateSku2Location();
			
			//分期乐           07:14:35-07:15:49
//			//fenQiLePlatFormStocuUpdate.updateSku2Location();
			
			//京东国内、国外
			gouDongPlatFormStocuUpdate.updateSku2Location();
			
			//天猫   20分钟           07:28:51-07:47:25
			tmallFormStockUpdate.updateSku2Location();
		
			//拼多多 5分钟                   07:47:22-07:49:09
			pinDuoDuoPlatFormStockUpdate.updateSku2Location();
			
			//新拼多多 
		    pinDuoDuoNewPlatFormStockUpdate.updateSku2Location();
			
			//银泰(yinTai) 5 13:57:09-14:01:23
			yinTaiPlatFormStockUpdate.updateSku2Location();
			
			//天猫奢品
			shePinFormStockUpdate.updateSku2Location();
			//小红书   1小时                  07:49:12-08:52:27
			xiaohongshuPlatFormStockupdate.updateSku2Location();
			
			siKuPlatFormStockUpdate.updateSku2Location();
			//苏宁(關了)
//			//suNingPlatFormStockUpdate.updateSku2Location();
			
			//Ofashion            08:52:31-13:54:37
			ofashionPlatFormStockUpdate.updateSku2Location();
			
			//Ofasion迷橙B2B
			ofMiChengPaltFormStockupdate.updateSku2Location();
			//微盟(WeiMob)(弃用) 5分钟    13:54:38-13:57:08
			//weiMobPlatFormStockUpdate.updateSku2Location();
			
			yunShangPlatFormStockUpdate.updateSku2Location();
		}

	 
		//处理不需要同步到平台的sku (更新为null) 添加到表  hx_stock_not_update 的sku不需要同步sku
		stockNotUpdateService.dealNotUpdate2PlatformSku();
		
		long endTime = System.currentTimeMillis();
		long a = endTime-startTime;
		long min = a/1000/60;
		System.out.println(sdf.format(new Date()) + " sync sku  total min:" + min + "min");
		return "/autosync/success";
	}
	

	//更新平台可售和下架的产品sku 和库存   -全量更新-考拉
	@RequestMapping("/timetask/updateFullProductForKaoLa")
	public String updateFullProductForKaoLa(Model model,HttpServletRequest request)
	{
		//stockService.syncNowStock();//同步本地所有库存
		
		if("true".equals(stockUpdateService.getsyncNowStockStatus()))
		{
			stockUpdateService.setCanUpdateStockStatus("false"); //暂时不让其他地方做更新
			
			//stockService.updatePlatformOrderStock2Zero("kaola_order_stock");//更新考拉订单数为0
			
			kaolaPlatformStockUpdate.updateAllStock();//同步本地库存到网易考拉
			
			stockUpdateService.setCanUpdateStockStatus("true"); //释放,其他地方可以更新
			
			return "/autosync/success";
		}
		else
		{
			return "/autosync/error";
		}
		
	}
	
	//更新平台可售和下架的产品sku 和库存   -全量更新-考拉海淘
	@RequestMapping("/timetask/updateFullProductForKaoLaHaiTao")
	public String updateFullProductForKaoLaHaiTao(Model model,HttpServletRequest request)
	{
		//stockService.syncNowStock();//同步本地所有库存
		
		if("true".equals(stockUpdateService.getsyncNowStockStatus()))
		{
			stockUpdateService.setCanUpdateStockStatus("false"); //暂时不让其他地方做更新
			
			//stockService.updatePlatformOrderStock2Zero("kaola_order_stock");//更新考拉订单数为0
			
			kaolaPlatHaiTaoformStockUpdate.updateAllStock(); //同步本地库存到海淘
			
			stockUpdateService.setCanUpdateStockStatus("true"); //释放,其他地方可以更新
			
			return "/autosync/success";
		}
		else
		{
			return "/autosync/error";
		}
		
	}
	
	/*
	@RequestMapping("/timetask/jdtest")
	public String test()
	{

		List<String> hkList = getSku("hk");
		List<String> shList = getSku("sh");
		
		Map<String,String> shMap = new HashMap<String, String>();
		for(String sku : shList)
		{
			shMap.put(sku, sku);
		}
		int cnt = 1;
		for(String sku : hkList)
		{
			if(shMap.get(sku) == null)
			{
				Map<String,String> searchMap = new HashMap<String, String>();
				searchMap.put("sku", sku.trim());
				searchMap.put("type", "sh");
				List<StockUpdate> needUpdatePlatformList = platformStockUpdateDao.selectStockUpdateByMap(searchMap);
				//当前产品为新入库且库存大于0 插入库存表
				if(needUpdatePlatformList.size() == 0)
				{
					StockUpdate su = new StockUpdate();
					su.setSku(sku);
					su.setNowStockNum(0);
					su.setLastUpdateStockNum(0);//刚插入的数据没有 各个平台sku  不做更新
					su.setType("sh");
					platformStockUpdateDao.insertStockUpdate(su);
					//if(cnt++ > 100) break;
				}
			}
		}
		return "/autosync/success";
	}
	
	public static List<String> getSku(String type)
	{
		List<String> list = new ArrayList<String>();
		File f = new File("e:/"+type+".txt");
		if(!f.exists()) return list;
		FileReader fr = null;
		BufferedReader br = null;
		try
		{
			fr = new FileReader(f);
			br = new BufferedReader(fr);
			String line = "";
			while((line = br.readLine()) != null)
			{
				if(StringUtil.isNotBlank(line))
				{
					 list.add(line);
				}
			}
		}
		catch (Exception e)
		{
			 
			e.printStackTrace();
		}
		finally
		{
			
			try
			{
					if(fr != null) fr.close();
					if(br != null) br.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		
		return list;
	}
	*/
	
	
	//更新平台可售和下架的产品sku 和库存   -全量更新-珍品
	@RequestMapping("/timetask/updateFullProductForZhenPin")
	public String updateFullProductForZhenPin(Model model,HttpServletRequest request)
	{
		//stockService.syncNowStock();//同步本地所有库存
	
		if("true".equals(stockUpdateService.getsyncNowStockStatus()))
		{
			stockUpdateService.setCanUpdateStockStatus("false"); //暂时不让其他地方做更新
			//stockService.updatePlatformOrderStock2Zero("zhenpin_order_stock");//更新考拉订单数为0

			zhenPinPlatformStockUpdate.updateAllStock();//同步本地库存到珍品
			
			stockUpdateService.setCanUpdateStockStatus("true"); //释放,其他地方可以更新
			
			return "/autosync/success";
		}
		else
		{
			return "/autosync/error";
		}
		
		
	}
	
	
	//更新平台可售和下架的产品sku 和库存   -全量更新-寺库
	@RequestMapping("/timetask/updateFullProductForSiKu")
	public String updateFullProductForSiKu(Model model,HttpServletRequest request)
	{
		//stockService.syncNowStock();//同步本地所有库存
		
		if("true".equals(stockUpdateService.getsyncNowStockStatus()))
		{
			stockUpdateService.setCanUpdateStockStatus("false"); //暂时不让其他地方做更新
			
			//stockService.updatePlatformOrderStock2Zero("siku_order_stock");//更新寺库订单数为0
			
			siKuPlatFormStockUpdate.updateAllStock();//同步本地库存到寺库(上海 香港)
			
			stockUpdateService.setCanUpdateStockStatus("true"); //释放,其他地方可以更新
			
			return "/autosync/success";
		}
		else
		{
			return "/autosync/error";
		}
		
	}
	
	// http://www.shangshangss.com:8078/timetask/updateFullProductForSiKunew
	//更新平台可售和下架的产品sku 和库存   -全量更新-寺库
		@RequestMapping("/timetask/updateFullProductForSiKunew")
		public String updateFullProductForSiKunew(Model model,HttpServletRequest request)
		{
			//stockService.syncNowStock();//同步本地所有库存
			
			if("true".equals(stockUpdateService.getsyncNowStockStatus()))
			{
				stockUpdateService.setCanUpdateStockStatus("false"); //暂时不让其他地方做更新
				
				//stockService.updatePlatformOrderStock2Zero("siku_order_stock");//更新寺库订单数为0
				
				siKunewPlatFormStockUpdate.updateAllStock();//同步本地库存到寺库(香港)
				
				stockUpdateService.setCanUpdateStockStatus("true"); //释放,其他地方可以更新
				
				return "/autosync/success";
			}
			else
			{
				return "/autosync/error";
			}
			
		}
		
		
		
		//更新平台可售和下架的产品sku 和库存   -全量更新-寺库
		@RequestMapping("/timetask/updateFullProductForYunShang")
		public String updateFullProductForYunShang(Model model,HttpServletRequest request)
		{
			//stockService.syncNowStock();//同步本地所有库存
			
			if("true".equals(stockUpdateService.getsyncNowStockStatus()))
			{
				stockUpdateService.setCanUpdateStockStatus("false"); //暂时不让其他地方做更新
								
				yunShangPlatFormStockUpdate.updateAllStock();//同步本地库存到云尚
				
				stockUpdateService.setCanUpdateStockStatus("true"); //释放,其他地方可以更新
				
				return "/autosync/success";
			}
			else
			{
				return "/autosync/error";
			}
			
		}
		
		
		
	
	//更新平台可售和下架的产品sku 和库存   -全量更新-魅力惠
		@RequestMapping("/timetask/updateFullProductForMlh")
		public String updateFullProductForMlh(Model model,HttpServletRequest request)
		{	
			if("true".equals(stockUpdateService.getsyncNowStockStatus()))
			{
				stockUpdateService.setCanUpdateStockStatus("false"); //暂时不让其他地方做更新
				
				meiLiHuiPlatFormStockUpdate.updateAllStock();//同步本地库存到魅力惠(香港)
				
				stockUpdateService.setCanUpdateStockStatus("true"); //释放,其他地方可以更新
				
				return "/autosync/success";
			}
			else
			{
				return "/autosync/error";
			}
			
		}
		
		
		//更新平台可售和下架的产品sku 和库存   -全量更新- 魅力惠 5000以上的和魅力惠 5000以下的，都是海外 hk的
				@RequestMapping("/timetask/updateFullProductForMlhnew")
				public String updateFullProductForMlhnew(Model model,HttpServletRequest request)
				{	
					if("true".equals(stockUpdateService.getsyncNowStockStatus()))
					{
						stockUpdateService.setCanUpdateStockStatus("false"); //暂时不让其他地方做更新
						
						meiLiHuinewPlatFormStockUpdate.updateAllStock();//同步本地库存到魅力惠(香港)
						
						stockUpdateService.setCanUpdateStockStatus("true"); //释放,其他地方可以更新
						
						return "/autosync/success";
					}
					else
					{
						return "/autosync/error";
					}
					
				}
		
		
		
		//更新平台可售和下架的产品sku 和库存   -全量更新-小红书
		@RequestMapping("/timetask/updateFullProductForXhs")
		public String updateFullProductForxhs(Model model,HttpServletRequest request)
		{	
			if("true".equals(stockUpdateService.getsyncNowStockStatus()))
			{
				stockUpdateService.setCanUpdateStockStatus("false"); //暂时不让其他地方做更新
				
				xiaohongshuPlatFormStockupdate.updateAllStock();//同步本地库存到小红书(上海 香港)
				
				stockUpdateService.setCanUpdateStockStatus("true"); //释放,其他地方可以更新
				
				return "/autosync/success";
			}
			else
			{
				return "/autosync/error";
			}
			
		}
		//更新平台可售和下架的产品sku 和库存   -全量更新-OFashion
		@RequestMapping("/timetask/updateFullProductForofashion")
		public String updateFullProductForofashion(Model model,HttpServletRequest request)
		{	
			if("true".equals(stockUpdateService.getsyncNowStockStatus()))
			{
				stockUpdateService.setCanUpdateStockStatus("false"); //暂时不让其他地方做更新
				
				ofashionPlatFormStockUpdate.updateAllStock();//同步本地库存到OFashion
				
				stockUpdateService.setCanUpdateStockStatus("true"); //释放,其他地方可以更新
				
				return "/autosync/success";
			}
			else
			{
				return "/autosync/error";
			}
			
		}
		
		
		//更新平台可售和下架的产品sku和库存  全量更新——微盟(weimob)
	  @RequestMapping("/timetask/updateFullProductForweimob")
	  public String updateFullProductForweimob(Model model,HttpServletRequest request)
	  {
		  if("true".equals(stockUpdateService.getsyncNowStockStatus())){
			  
			 stockUpdateService.setCanUpdateStockStatus("false"); //暂时不让其他地方做更新
			  
			 weiMobPlatFormStockUpdate.updateAllStock();       //同步本地库存到weiMob
			  
			 stockUpdateService.setCanUpdateStockStatus("true"); //释放,其他地方可以更新
			  return "/autosync/success";
		  }else 
		  {
			  return "/autosync/error";
		  }
		
		  
	  }
	  
	  //http://localhost:8079/timetask/updateFullProductForYinTai.html
	//更新平台可售和下架的产品sku 和库存   -全量更新-银泰
			@RequestMapping("/timetask/updateFullProductForYinTai")
			public String updateFullProductForYinTai(Model model,HttpServletRequest request)
			{	
				if("true".equals(stockUpdateService.getsyncNowStockStatus()))
				{
					stockUpdateService.setCanUpdateStockStatus("false"); //暂时不让其他地方做更新
					
					yinTaiPlatFormStockUpdate.updateAllStock();//同步本地库存到银泰
					
					stockUpdateService.setCanUpdateStockStatus("true"); //释放,其他地方可以更新
					
					return "/autosync/success";
				}
				else
				{
					return "/autosync/error";
				}
				
			}
			
			
			//更新平台可售和下架的产品sku 和库存   -全量更新-奢品
			@RequestMapping("/timetask/updateFullProductForShePin")
			public String updateFullProductForShePin(Model model,HttpServletRequest request)
			{	
				if("true".equals(stockUpdateService.getsyncNowStockStatus()))
				{
					stockUpdateService.setCanUpdateStockStatus("false"); //暂时不让其他地方做更新
					
					shePinFormStockUpdate.updateAllStock();//同步本地库存到奢品
					
					stockUpdateService.setCanUpdateStockStatus("true"); //释放,其他地方可以更新
					
					return "/autosync/success";
				}
				else
				{
					return "/autosync/error";
				}
				
			}
			
			//更新平台可售和下架的产品sku 和库存   -全量更新-苏宁
			@RequestMapping("/timetask/updateFullProductForSuNing")
			public String updateFullProductForSuNing(Model model,HttpServletRequest request)
			{	
				if("true".equals(stockUpdateService.getsyncNowStockStatus()))
				{
					stockUpdateService.setCanUpdateStockStatus("false"); //暂时不让其他地方做更新
					
					suNingPlatFormStockUpdate.updateAllStock();//同步本地库存到奢品
					
					stockUpdateService.setCanUpdateStockStatus("true"); //释放,其他地方可以更新
					
					return "/autosync/success";
				}
				else
				{
					return "/autosync/error";
				}
				
			}

			
//	//更新平台可售和下架的产品sku 和库存   -全量更新-尚品
//	@RequestMapping("/timetask/updateFullProductForShangpin2")
//	public String updateFullProductForShangpin(Model model,HttpServletRequest request)
//	{
//		//stockService.syncNowStock();//同步本地所有库存
//		
//		if("true".equals(stockUpdateService.getsyncNowStockStatus()))
//		{
//			stockUpdateService.setCanUpdateStockStatus("false"); //暂时不让其他地方做更新
//			
//			//stockService.updatePlatformOrderStock2Zero("shangpin_order_stock");//更新寺库订单数为0
//
//			shangPinPlatformStocuUpdate.updateAllStock();//同步本地库存到尚品
//			
//			stockUpdateService.setCanUpdateStockStatus("true"); //释放,其他地方可以更新
//			
//			return "/autosync/success";
//		}
//		else
//		{
//			return "/autosync/error";
//		}
//		
//		
//	}
	
//	//更新平台可售和下架的产品sku 和库存   -全量更新-一号店
//	@RequestMapping("/timetask/updateFullProductForYhd")
//	public String updateFullProductForYhd(Model model,HttpServletRequest request)
//	{
//		 
//		if("true".equals(stockUpdateService.getsyncNowStockStatus()))
//		{
//			stockUpdateService.setCanUpdateStockStatus("false"); //暂时不让其他地方做更新
//			yhdPlatformStockUpdate.updateAllStock();//同步本地库存到一号店(上海)
//			stockUpdateService.setCanUpdateStockStatus("true"); //释放,其他地方可以更新
//			return "/autosync/success";
//		}
//		else
//		{
//			return "/autosync/error";
//		}
//		
//	}
	
	
	//更新平台可售和下架的产品sku 和库存   -全量更新-higo
	@RequestMapping("/timetask/updateFullProductForHigo")
	public String updateFullProductForHigo(Model model,HttpServletRequest request)
	{
		
		if("true".equals(stockUpdateService.getsyncNowStockStatus()))
		{
			stockUpdateService.setCanUpdateStockStatus("false"); //暂时不让其他地方做更新
			higoFormStockUpdate.updateAllStock();//同步本地库存到higo
			stockUpdateService.setCanUpdateStockStatus("true"); //释放,其他地方可以更新
			return "/autosync/success";
		}
		else
		{
			return "/autosync/error";
		}
		
	}
	//更新平台可售和下架的产品sku和库存 -全量更新- 拼多多
	@RequestMapping("timetask/updateFullProductForPdd")
	public String updateFullProductForPdd(Model model,HttpServletRequest request)
	{
		if("true".equals(stockUpdateService.getsyncNowStockStatus()))
		{
			stockUpdateService.setCanUpdateStockStatus("false");
			pinDuoDuoPlatFormStockUpdate.updateAllStock();
			stockUpdateService.setCanUpdateStockStatus("true");
			return "/autosync/success";
		}else{
			return "/autosync/error";
		}
		
	}
	
	//更新平台可售和下架的产品sku和库存 -全量更新- 新拼多多
		@RequestMapping("timetask/updateFullProductForPddnew")
		public String updateFullProductForPddnew(Model model,HttpServletRequest request)
		{
			if("true".equals(stockUpdateService.getsyncNowStockStatus()))
			{
				stockUpdateService.setCanUpdateStockStatus("false");
				pinDuoDuoNewPlatFormStockUpdate.updateAllStock();
				stockUpdateService.setCanUpdateStockStatus("true");
				return "/autosync/success";
			}else{
				return "/autosync/error";
			}
			
		}
	
	
	//更新平台可售和下架的产品sku 和库存   -全量更新-tmall
	@RequestMapping("/timetask/updateFullProductForTmall")
	public String updateFullProductForTmall(Model model,HttpServletRequest request)
	{
		
		if("true".equals(stockUpdateService.getsyncNowStockStatus()))
		{
			stockUpdateService.setCanUpdateStockStatus("false"); //暂时不让其他地方做更新
			tmallFormStockUpdate.updateAllStock();//同步本地库存到tmall
			stockUpdateService.setCanUpdateStockStatus("true"); //释放,其他地方可以更新
			return "/autosync/success";
		}
		else
		{
			return "/autosync/error";
		}
		
	}
	
	
	//更新平台可售和下架的产品库存   -全量更新-分期乐
	@RequestMapping("/timetask/updateFullProductForFenQiLe")
	public String updateFullProductForFenQiLe(Model model,HttpServletRequest request)
	{
		if("true".equals(stockUpdateService.getsyncNowStockStatus()))
		{
			stockUpdateService.setCanUpdateStockStatus("false"); //暂时不让其他地方做更新
			fenQiLePlatFormStocuUpdate.updateAllStock();
			stockUpdateService.setCanUpdateStockStatus("true"); //释放,其他地方可以更新
			return "/autosync/success";
		}
		else
		{
			return "/autosync/error";
		}
		
	}
	
	//更新平台可售产品库存   -全量更新-Ofasion迷橙B2B
	@RequestMapping("/timetask/updateFullProductForOfashionMC")
	public String updateFullProductForOfashionMC(Model model,HttpServletRequest request)
	{
		if("true".equals(stockUpdateService.getsyncNowStockStatus()))
		{
			stockUpdateService.setCanUpdateStockStatus("false"); //暂时不让其他地方做更新
			offMiChengPaltFormStockupdate.updateAllStock();
			stockUpdateService.setCanUpdateStockStatus("true"); //释放,其他地方可以更新
			return "/autosync/success";
		}
		else
		{
			return "/autosync/error";
		}
		
	}
	
 
	//更新平台可售和下架的产品库存   -全量更新-京东全球购国内
	@RequestMapping("/timetask/updateFullProductForJd")
	public String updateFullProductForJd(Model model,HttpServletRequest request)
	{
		if("true".equals(stockUpdateService.getsyncNowStockStatus()))
		{
			stockUpdateService.setCanUpdateStockStatus("false"); //暂时不让其他地方做更新
			gouDongPlatFormStocuUpdate.updateAllStock();
			stockUpdateService.setCanUpdateStockStatus("true"); //释放,其他地方可以更新
			return "/autosync/success";
		}
		else
		{
			return "/autosync/error";
		}
		
	}
	
	
	//同步本地库存
	@RequestMapping("/timetask/syncNowStock")
	public String syncNowStock()
	{
		stockUpdateService.syncNowStock();
		System.out.println(sdf.format(new Date()) + " sync stock_update stock over");
		return "/autosync/success";
	}
	
	
	//更新考拉平台待上架的sku到本地
	@RequestMapping("/timetask/updateKaoLaWaitForOnSale")
	public String updateKaoLaWaitForOnSale(Model model)
	{
		int cnt = kaolaPlatformStockUpdate.updateWaitFoOnSaleSku2Location();
		model.addAttribute("cnt", cnt);
		return "/autosync/success";
	}
	//更新考拉海淘平台待上架的sku到本地
	@RequestMapping("/timetask/updateKaoLaHaiTaoWaitForOnSale")
	public String updateKaoLaHaiTaoWaitForOnSale(Model model)
	{
		int cnt = kaolaPlatHaiTaoformStockUpdate.updateWaitFoOnSaleSku2Location();
		model.addAttribute("cnt", cnt);
		return "/autosync/success";
	}
	 

	
	@RequestMapping("/timetask/updateHxSockUpdate")
	public String updateHxSockUpdate()
	{
		Map<String, String> searchMap  = new HashMap<String, String>();
		Map<String, String> searchMaps  = new HashMap<String, String>();
		searchMap.put("status", 1+"");
		List<Product> list = productDao.getProductList(searchMap);
		for(int i = 0;i<list.size();i++){
			String sku = list.get(i).getSku();
			String curSiteId = list.get(i).getCurSiteId();
			searchMaps.put("sku", sku);
			
			List<StockUpdate> sulist = hxPlatformStockUpdateDao.HxSelectStockUpdate(searchMaps);
			if(sulist == null || sulist.size() == 0){
			String type = "";
			if(MiniUiUtil.hkSite.contains(curSiteId))
			{
				type = "hk";
			}
			else if(MiniUiUtil.shSite.contains(curSiteId))
			{
				type = "sh";
			}
			List<Long> siteList = new ArrayList<Long>();
			if("hk".equals(type))
			{
				//获取可售库存所需站点信息
				for(String idSite : MiniUiUtil.hkSite)
				{
					siteList.add(Long.valueOf(idSite));
				}
			}
			else
			{
				//获取可售库存所需站点信息
				for(String idSite : MiniUiUtil.shSite)
				{
					siteList.add(Long.valueOf(idSite));
				}
			}
			int num = autoSyncDao.searchStockBySiteAndSku(siteList,sku);
			StockUpdate su = new StockUpdate();
			su.setSku(sku);
			su.setNowStockNum(num);
			su.setLastUpdateStockNum(num);
			su.setType(type);
			platformStockUpdateDao.insertStockUpdate(su);
			}
			
		}
		return "/autosync/success";
	}
	//通过文件同步寺库sku到本地
	@RequestMapping("/timetask/toUploadSikuFileForHk")
	public String toUploadSikuFileForHk()
	{
		return "/autoSync/sikuSkuHk";
	}
	
	
	
	
	
	//通过文件同步“新”寺库sku到本地
		@RequestMapping("/timetask/toUploadSikuNewFileForHk")
		public String toUploadSikuNewFileForHk()
		{
			return "/autoSync/sikuSkuHkNew";
		}
	
	//通过文件同步寺库sku到本地
	@RequestMapping("/timetask/toUploadSikuFileForSh")
	public String toUploadSikuFileForSh()
	{
		return "/autoSync/sikuSkuSh";
	}
	
	//通过文件同步魅力惠SKU到本地
	@RequestMapping("/timetask/touploadMlhFileForHK")
	public String touploadMlhFileForHK(){
		return "/autoSync/mlhSkuHk";
	}
	//通过文件同步魅力惠SKU到本地
	@RequestMapping("/timetask/touploadMlhFileForSh")
	public String touploadMlhFileForSh(){
		return "/autoSync/mlhSkuSh";
	}
	
	//将旧数据导入provide_goods_xiyou表，补充provide_update_goods_xiyou_log日志数据。（方法只执行一次）
	@RequestMapping("/timetask/toUploadGoodsXiYouFile")
	public String toUploadGoodsXiYouFile(){
		
		return "/autoSync/toUploadGoodsXiYouFile";
		
	}
	
	
	//productDetail页面导入图片的测试页面,
		@RequestMapping("/timetask/securityHttp")
		public String SecurityHttp()
		{
			return "/autoSync/securityHttp";
		}
	
		//通过导入“安全类别和标准”到本地
				@RequestMapping("/timetask/SecurityTC")
				public String SecurityTC()
				{
					return "/autoSync/SecurityTC";
				}
	
	 private String getCellValue(Cell cell) {
	    	if(null == cell) return"";
	        if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
	            return String.valueOf(cell.getBooleanCellValue());
	        } else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
	        	DecimalFormat df = new DecimalFormat("0");  
	            return df.format(cell.getNumericCellValue());
	        } else {
	            return String.valueOf(cell.getStringCellValue());
	        }
	    }

	 

	
	@RequestMapping(value = "/timetask/updateSikuSkuByFile")
    public void updateSikuSkuByFile(Model model,HttpServletRequest req,HttpServletResponse response)  
	{
    	try
    	{
    		String type = req.getParameter("type");
    		if(StringUtil.isBlank(type)) return;
	    	MultipartHttpServletRequest request = (MultipartHttpServletRequest)req;
			MultipartFile locationFile = request.getFile("siku");
			InputStream  in = locationFile.getInputStream();
			String locationFileName = locationFile.getOriginalFilename(); 
			String fileType = locationFileName.substring(locationFileName.lastIndexOf(".")+1);
			
			Workbook wb = null;  
	        if (fileType.equals("xls")) {  
	            wb = new HSSFWorkbook(in);  
	        }  
	        else if (fileType.equals("xlsx")) {
	            wb = new XSSFWorkbook(in);
	        }
	        Sheet sheet1 = wb.getSheetAt(0);
	        
	        
	        
	        //处理本地对应关系excel
	        Map<String,String> locationMap = new HashMap<String, String>();
	        for(int  rowIndex = 1;rowIndex < sheet1.getPhysicalNumberOfRows();rowIndex++)
	        {
	        	Row row  =  sheet1.getRow(rowIndex);
	        	String   sikuKey = "";
	        	String   ourKey = "";
	        	for(int colIndex = 0; colIndex < 2;colIndex++)//只需要前2列
	        	{
	        		Cell cell = row.getCell(colIndex);
	        		String value = getCellValue(cell);
	        		if(StringUtil.isBlank(value)) continue;
	        		value = value.trim();
	            	if(colIndex == 0)
	            	{
	            		sikuKey = value;
	            	}
	            	else if(colIndex == 1)
	            	{
	            		ourKey = value;
	            	}
	        	}
	        	locationMap.put(sikuKey, ourKey);
	        }
	        
	        siKuPlatFormStockUpdate.updateSku2LocationByFile(locationMap, type);
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    
		
	}
	
	
	//通过文件同步寺库sku到本地
			@RequestMapping("/timetask/toUploadYunShangFileFor")
			public String toUploadYunShangFileFor()
			{
				return "/autoSync/yunshangSku";
			}
		
	
	@RequestMapping(value = "/timetask/updateYunshangSkuByFile")
    public void updateYunshangSkuByFile(Model model,HttpServletRequest req,HttpServletResponse response)  
	{
    	try
    	{
    		String type = req.getParameter("type");
    		if(StringUtil.isBlank(type)) return;
	    	MultipartHttpServletRequest request = (MultipartHttpServletRequest)req;
			MultipartFile locationFile = request.getFile("yunshang");
			InputStream  in = locationFile.getInputStream();
			String locationFileName = locationFile.getOriginalFilename(); 
			String fileType = locationFileName.substring(locationFileName.lastIndexOf(".")+1);
			
			Workbook wb = null;  
	        if (fileType.equals("xls")) {  
	            wb = new HSSFWorkbook(in);  
	        }  
	        else if (fileType.equals("xlsx")) {
	            wb = new XSSFWorkbook(in);
	        }
	        Sheet sheet1 = wb.getSheetAt(0);
	        
	        
	        
	        //处理本地对应关系excel
	        Map<String,String> locationMap = new HashMap<String, String>();
	        for(int  rowIndex = 1;rowIndex < sheet1.getPhysicalNumberOfRows();rowIndex++)
	        {
	        	Row row  =  sheet1.getRow(rowIndex);
	        	String   sikuKey = "";
	        	String   ourKey = "";
	        	for(int colIndex = 0; colIndex < 2;colIndex++)//只需要前2列
	        	{
	        		Cell cell = row.getCell(colIndex);
	        		String value = getCellValue(cell);
	        		if(StringUtil.isBlank(value)) continue;
	        		value = value.trim();
	            	if(colIndex == 0)
	            	{
	            		sikuKey = value;
	            	}
	            	else if(colIndex == 1)
	            	{
	            		ourKey = value;
	            	}
	        	}
	        	locationMap.put(sikuKey, ourKey);
	        }
	        
	        yunShangPlatFormStockUpdate.updateSku2LocationByFile(locationMap, type);
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    
		
	}
////魅力惠导入xml表格，只更新锁库的商品的库存
	//http://www.shangshangss.com:8078/timetask/touploadMlhSuoFileForhk.html
		@RequestMapping("/timetask/touploadSuoFileForhk")
		public String touploadSuoFileForhk(){
//			System.out.println("000000000000");
			return "/autoSync/mlhSuoSkuhk";
		}
	
	
	//魅力惠导入xml表格，只更新锁库的商品的库存
	@RequestMapping(value = "/timetask/touploadMlhSuoFileForhk")
    public void touploadMlhSuoFileForhk(Model model,HttpServletRequest req,HttpServletResponse response)  
	{
    	try
    	{
    		String type = req.getParameter("type");
    		if(StringUtil.isBlank(type)) return;
	    	MultipartHttpServletRequest request = (MultipartHttpServletRequest)req;
			MultipartFile locationFile = request.getFile("mlh");
			InputStream  in = locationFile.getInputStream();
			String locationFileName = locationFile.getOriginalFilename(); 
			String fileType = locationFileName.substring(locationFileName.lastIndexOf(".")+1);
			
			Workbook wb = null;  
	        if (fileType.equals("xls")) {  
	            wb = new HSSFWorkbook(in);  
	        }  
	        else if (fileType.equals("xlsx")) {  
	            wb = new XSSFWorkbook(in);  
	        }  
	        Sheet sheet1 = wb.getSheetAt(0);
	        
	        
	        
	        //处理本地对应关系excel
	        Map<String,String> locationMap = new HashMap<String, String>();
	        for(int  rowIndex = 1;rowIndex < sheet1.getPhysicalNumberOfRows();rowIndex++)
	        {
	        	Row row  =  sheet1.getRow(rowIndex);
	        	String   sikuKey = "";
	        	String   ourKey = "";
	        	for(int colIndex = 0; colIndex < 2;colIndex++)//只需要前2列
	        	{
	        		Cell cell = row.getCell(colIndex);
	        		String value = getCellValue(cell);
	        		if(StringUtil.isBlank(value)) continue;
	        		value = value.trim();
	            	if(colIndex == 0)
	            	{
	            		sikuKey = value;
	            	}
	            	else if(colIndex == 1)
	            	{
	            		ourKey = value;
	            	}
	        	}
	        	locationMap.put(sikuKey, ourKey);
	        }
	        for(String sku :locationMap.keySet()){
	        	StockUpdate su = new StockUpdate();
	        	su.setSku(sku);
	        	int num = Integer.parseInt(locationMap.get(sku));//得到每个key多对用value的值
	        	su.setType(type);
	            StockUpdate suc = stockUpdateService.selectstock(su);
//	            System.out.println(sku + "-----" + num+"----"+suc.getMlhSku());  //9560013563250  31396404
	            MeiLiHuiPlatFormStockUpdate.updateCanSaleProduct(suc.getMlhSku(),sku, num, type);
	        }
	        
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    
		
	}
	
	
	//新寺库hk-sku导入
	@RequestMapping(value = "/timetask/updateSikuNewSkuByFile")
    public void updateSikuNewSkuByFile(Model model,HttpServletRequest req,HttpServletResponse response)  
	{
    	try
    	{
    		String type = req.getParameter("type");
    		if(StringUtil.isBlank(type)) return;
	    	MultipartHttpServletRequest request = (MultipartHttpServletRequest)req;
			MultipartFile locationFile = request.getFile("sikunew");
			InputStream  in = locationFile.getInputStream();
			String locationFileName = locationFile.getOriginalFilename(); 
			String fileType = locationFileName.substring(locationFileName.lastIndexOf(".")+1);
			
			Workbook wb = null;  
	        if (fileType.equals("xls")) {  
	            wb = new HSSFWorkbook(in);  
	        }  
	        else if (fileType.equals("xlsx")) {  
	            wb = new XSSFWorkbook(in);  
	        }  
	        Sheet sheet1 = wb.getSheetAt(0);
	        
	        
	        
	        //处理本地对应关系excel
	        Map<String,String> locationMap = new HashMap<String, String>();
	        for(int  rowIndex = 1;rowIndex < sheet1.getPhysicalNumberOfRows();rowIndex++)
	        {
	        	Row row  =  sheet1.getRow(rowIndex);
	        	String   sikuKey = "";
	        	String   ourKey = "";
	        	for(int colIndex = 0; colIndex < 2;colIndex++)//只需要前2列
	        	{
	        		Cell cell = row.getCell(colIndex);
	        		String value = getCellValue(cell);
	        		if(StringUtil.isBlank(value)) continue;
	        		value = value.trim();
	            	if(colIndex == 0)
	            	{
	            		sikuKey = value;
	            	}
	            	else if(colIndex == 1)
	            	{
	            		ourKey = value;
	            	}
	        	}
	        	locationMap.put(sikuKey, ourKey);
	        }
	        
	        siKunewPlatFormStockUpdate.updateSku2LocationByFile(locationMap, type);
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    
		
	}
	
	
	
	
	@RequestMapping(value = "/timetask/updateMlhSkuByFile")
	 public void updateMlhSkuByFile(Model model,HttpServletRequest req,HttpServletResponse response){
		try {
			String type = req.getParameter("type");
			if(StringUtil.isBlank(type)) return;
			MultipartHttpServletRequest request = (MultipartHttpServletRequest)req;
			MultipartFile locationFile = request.getFile("Mlh");
			InputStream  in = locationFile.getInputStream();
			String locationFileName = locationFile.getOriginalFilename();
			String fileType = locationFileName.substring(locationFileName.lastIndexOf(".")+1);
			Workbook wb = null;  
	        if (fileType.equals("xls")) {  
	        	
	            wb = new HSSFWorkbook(in);  
	        }  
	        else if (fileType.equals("xlsx")) {  
	        	
	            wb = new XSSFWorkbook(in);  
	        }  
	        
	        Sheet sheet1 = wb.getSheetAt(0);
	        
	        Map<String,String> locationMap = new HashMap<String, String>();
	        for(int  rowIndex = 1;rowIndex < sheet1.getPhysicalNumberOfRows();rowIndex++){
	        	Row row  =  sheet1.getRow(rowIndex);
	        	String   MlhKey = "";
	        	String   ourKey = "";
	        	for(int colIndex = 0; colIndex < 2;colIndex++){
	        		Cell cell = row.getCell(colIndex);
	        		String value = getCellValue(cell);
	        		if(StringUtil.isBlank(value)) continue;
	        		value = value.trim();
	            	if(colIndex == 0)
	            	{ 
	            		MlhKey = value;
	            	}
	            	else if(colIndex == 1)
	            	{
	            		ourKey = value;
	            	}
	        	}
	        	locationMap.put(MlhKey, ourKey);
	        }
	        meiLiHuiPlatFormStockUpdate.updateSku2LocationByFileMlh(locationMap, type);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}
	
	//安全类别、执行标准、商品名称、材质描述、颜色描述、产地导入
	@RequestMapping(value = "/timetask/SecurityTCSkuByFile")
    public @ResponseBody Object SecurityTCSkuByFile(Model model,HttpServletRequest req,HttpServletResponse response)  
	{	
		int num=0;
    	try
    	{
    		 // request转换
	    	MultipartHttpServletRequest request = (MultipartHttpServletRequest)req;
	    	//取得request中的所有文件名  
			MultipartFile locationFile = request.getFile("secku");
			InputStream  in = locationFile.getInputStream();
			//取得当前上传文件的文件名称
			String locationFileName = locationFile.getOriginalFilename(); 
			//截取上传文件的名称.后面的名称
			String fileType = locationFileName.substring(locationFileName.lastIndexOf(".")+1);
			
			//判断是xls文件，还是xlsx文件
			Workbook wb = null;  
	        if (fileType.equals("xls")) {  
	            wb = new HSSFWorkbook(in);  
	        }  
	        else if (fileType.equals("xlsx")) {  
	            wb = new XSSFWorkbook(in);  
	        }  
	        
	        //得到Excel表格中的第一个sheet1
	        Sheet sheet1 = wb.getSheetAt(0);
	        
	        
	        
	        //处理本地对应关系excel
	        Map<String,List<String>> locationMap = new HashMap<String,List<String>>();
	        for(int  rowIndex = 1;rowIndex < sheet1.getPhysicalNumberOfRows();rowIndex++)
	        { 
	        	 num++;
	        	Row row  =  sheet1.getRow(rowIndex);
	        	String   sku = "";
	        	List<String> list = new ArrayList<String>(); 
	        	for(int colIndex = 0; colIndex < 7;colIndex++)//只需要前7列
	        	{
	        		Cell cell = row.getCell(colIndex);
	        		String value = getCellValue(cell);
	        		/*if(StringUtil.isBlank(value)) continue;
	        		value = value.trim();*/
	            	if(colIndex == 0)    //sku
	            	{  
	            		    sku=value;
	            			list.add(sku);	
	            	}
	            	else if(colIndex == 1)  //安全技术类别
	            	{
	            		if(StringUtil.isBlank(value)){
	            			list.add("");
	            		}else{
	            			list.add(value);
	            		}
	            	}else if(colIndex == 2) //执行标准
	            	{
	            		
	            		if(StringUtil.isBlank(value)){
	            			list.add("");
	            		}else{
	            			list.add(value);
	            		}
	            	}else if(colIndex == 3) //商品名称
	            	{
	            		if(StringUtil.isBlank(value)){
	            			list.add("");
	            		}else{
	            			list.add(value);
	            		}
	            	}else if(colIndex == 4)  //材质描述
	            	{
	            		if(StringUtil.isBlank(value)){
	            			list.add("");
	            		}else{
	            			value=value.replaceAll("\n", " ");  //将传入的内容中有换行的，用空格代替，传向后面，导入数据库
	            			list.add(value);
	            		}
	            	}else if(colIndex == 5) //颜色描述
	            	{
	            		if(StringUtil.isBlank(value)){
	            			list.add("");
	            		}else{
	            			list.add(value);
	            		}
	            	}else if(colIndex == 6) //产地
	            	{
	            		if(StringUtil.isBlank(value)){
	            			list.add("");
	            		}else{
	            			list.add(value);
	            		}
	            	}
	        	}
	        	locationMap.put(sku, list);
	        }
	        
	        productService.updateSecurityTCByFile(locationMap);
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    		return "error";
    	}
    	
		return num;
    
		
	}
	
	
	
	
	
	//导出Excel
		@RequestMapping("/timetask/exportExcel")
		public String exportExcel()
		{
			return "/autoSync/exportSupplyGoodsExcel";
		}
		private static final String url="http://185.58.119.177/velashopapi/";
		private static final String KEY_ACCESSO ="EpFF87Ibl9";
		private static final String DBContext ="Default";
		private static final String CategoryId ="";
		private static final String BrandId ="";
		private static final String SeasonCode ="";
		
		@RequestMapping("/timetask/exportSupplyGood")
		public void exportSupplyGood(HttpServletRequest request,HttpServletResponse response) throws Exception{
//			String getGoodsUrl = "Myapi/Productslist/GetProducts?"; //1963
//			String getGoodsUrl = "Myapi/Productslist/GetWebProducts?"; //1963
//			String getGoodsUrl = "Myapi/Productslist/GetALLProducts?"; //6044
			String getGoodsUrl = "Myapi/Productslist/GetTOTalProducts?";//52732
		       getGoodsUrl = url+getGoodsUrl;
			//EndIndex=1963(总共的)
				Integer startIndex=5001;
				Integer EndIndex =5200;
				Integer num =1;
				 List<SupplyGood> lsg = new ArrayList<SupplyGood>();
				while(EndIndex<10001){
//				 while(num>0){
					  TreeMap<String, String> map = new TreeMap<String, String>();
				   map.put("DBContext", DBContext);
				   map.put("key", KEY_ACCESSO);
				
				   map.put("CategoryId", CategoryId);
				   map.put("BrandId", BrandId);
				   map.put("SeasonCode", SeasonCode);
				   map.put("StartIndex", startIndex+"");
				   map.put("EndIndex", EndIndex+"");
					  String responseGoods = HttpRequest.sendGetMJson(getGoodsUrl, map);
//					  System.out.println(responseGoods);
					 
					  try {
						 
						JSONObject jsons = new JSONObject(responseGoods);
						 num = Integer.parseInt(jsons.getString("number"));
					System.out.println(num);
					
						JSONArray jsonArrays = jsons.getJSONArray("product");
							if(num>0){								
							for(int i = 0; i < jsonArrays.length(); i++){							
								JSONObject dataObject = new JSONObject(jsonArrays.get(i).toString());
								String product_id = dataObject.getString("product_id");
						       String producer_id = dataObject.getString("producer_id");
						       String type = dataObject.getString("type");
						       String season = dataObject.getString("season");
						       String product_name = dataObject.getString("product_name");
						       String description = dataObject.getString("description");
						       String category = dataObject.getString("category");
						       String product_detail = dataObject.getString("product_detail");
						       String product_MadeIn = dataObject.getString("product_MadeIn");
						       String product_Material = dataObject.getString("product_Material");
						       String product_Measure = dataObject.getString("product_Measure");
						       String url = dataObject.getString("url");
						       String supply_price = dataObject.getString("supply_price");
						       String carryOver = dataObject.getString("CarryOver");
						       String product_Note = dataObject.getString("product_Note");
						       String subCategory = dataObject.getString("SubCategory");
								String items = dataObject.getString("items");
								JSONObject jsonsItems = new JSONObject(items);
								JSONArray jsonArrayItem = jsonsItems.getJSONArray("item");
	//					System.out.println(jsonArrayItem.length());
								for(int t=0;t<jsonArrayItem.length();t++){
									
									SupplyGood sg = new SupplyGood();
							//共同点
							sg.setProduct_id(product_id);
							sg.setProducer_id(producer_id);
							sg.setType(type);
							sg.setSeason(season);
							sg.setProduct_name(product_name);
							sg.setDescription(description);
							sg.setCategory(category);
							sg.setProduct_detail(product_detail);
							sg.setProduct_MadeIn(product_MadeIn);
							sg.setProduct_Material(product_Material);
							sg.setProduct_Measure(product_Measure);
							sg.setUrl(url);
							sg.setSupply_price(supply_price);
							sg.setCarryOver(carryOver);
							sg.setProduct_Note(product_Note);
							sg.setSubCategory(subCategory);
							        //子
									JSONObject dataItem = new JSONObject(jsonArrayItem.get(t).toString());		
									String item_id = dataItem.getString("item_id");
									sg.setItem_id(item_id);
									String item_size = dataItem.getString("item_size");	
									sg.setItem_size(item_size);
									String barcode = dataItem.getString("barcode");	
									sg.setBarcode(barcode);
									String color = dataItem.getString("color");
									sg.setColor(color);
									String stock = dataItem.getString("stock");
									sg.setStock(stock);
									String last_modified = dataItem.getString("last_modified");
									sg.setLast_modified(last_modified);
									String base_color = dataItem.getString("base_color");
									sg.setBase_color(base_color);
									String country_size = dataItem.getString("country_size");
									sg.setCountry_size(country_size);
									String web = dataItem.getString("web");
									sg.setWeb(web);
									String madeIn = dataItem.getString("MadeIn");
									sg.setMadeIn(madeIn);
									String picturesOne = dataItem.getString("pictures");
									sg.setPicturesOne(picturesOne);
							
							
							lsg.add(sg);
								}
								
							}
							startIndex=EndIndex+1;
							EndIndex =EndIndex+200;
	//						if(EndIndex>2001) break;

						  }
					} catch (JSONException e) {
					
						e.printStackTrace();
					
	}	
				}
					  System.out.println(lsg.size()+"lsg.size()");
					  int totalCnt = lsg.size();
					  String[] titleArr = new String[]{"product_id","producer_id","type","Season","Product_name","Description","Category","Product_detail","Product_MadeIn","Product_Material",
							  "Product_Measure","Url","Supply_price","CarryOver","Product_Note","SubCategory",
							  "Item_id","Item_size","Barcode","Color","Stock","Last_modified","Base_color","Country_size","Web","MadeIn","PicturesOne"}; 
					  String[][] resultArr = new String[totalCnt+1][titleArr.length]; //保存查询结果  总行数+1(title行)
					  resultArr[0] = titleArr;
					  for(int j=0;j<lsg.size();j++){
						String[] rowArr = resultArr[j+1];   //第一行为title  不需要在设置
						SupplyGood p = lsg.get(j);
						rowArr[0] = p.getProduct_id();
						rowArr[1] = p.getProducer_id();
						rowArr[2] = p.getType();
						rowArr[3] = p.getSeason();
						rowArr[4] = p.getProduct_name();						
						rowArr[5] = p.getDescription();
						rowArr[6] = p.getCategory();
						rowArr[7] = p.getProduct_detail();
						rowArr[8] = p.getProduct_MadeIn();
						rowArr[9] = p.getProduct_Material();
						rowArr[10] = p.getProduct_Measure();
						rowArr[11] = p.getUrl();
						rowArr[12] = p.getSupply_price();
						rowArr[13] = p.getCarryOver();
						rowArr[14] = p.getProduct_Note();
						rowArr[15] = p.getSubCategory();
						
						rowArr[16] = p.getItem_id();
						rowArr[17] = p.getItem_size();
						rowArr[18] = p.getBarcode();
						rowArr[19] = p.getColor();
						rowArr[20] = p.getStock();
						rowArr[21] = p.getLast_modified();
						rowArr[22] = p.getBase_color();
						rowArr[23] = p.getCountry_size();
						rowArr[24] = p.getWeb();
						rowArr[25] = p.getMadeIn();
						rowArr[26] = p.getPicturesOne();
					  }
					HSSFWorkbook exportWb = new HSSFWorkbook();
				 HSSFSheet exportSheet = exportWb.createSheet("sheet0");
				 HSSFCellStyle exportStyle = exportWb.createCellStyle();    
				 exportStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);    
				 for(int  rowIndex = 0;rowIndex < resultArr.length;rowIndex++)
				 {
				 	String[] row =  resultArr[rowIndex];
				
						Row exportRow = exportSheet.createRow(rowIndex);
				
						for(int  colIndex = 0;colIndex < row.length;colIndex++)
						{
				  		 exportRow.createCell(colIndex).setCellValue(row[colIndex]);
						}
							
				 }
				 response.setContentType("application/vnd.ms-excel");    
				 response.setHeader("Content-disposition", "attachment;filename=GetTOTalProducts_0_5000"+System.currentTimeMillis()+".xls");
				 OutputStream ouputStream = response.getOutputStream();    
				 exportWb.write(ouputStream);    
				 ouputStream.flush();    
				 ouputStream.close(); 	
				
		}
		
		//到达分割Excel
		@RequestMapping("/timetask/toCutExcel")
		public String toCutExcel()
		{
			return "/autoSync/cutExcel";
		}
	//分割Excel操作
	@RequestMapping(value = "/timetask/toCutExcelByNum")
    public @ResponseBody Object toCutExcelByNum(Model model,HttpServletRequest req,HttpServletResponse response)  
	{	//安全类别、执行标准、商品名称、材质描述、颜色描述、产地导入
		String[] titleArr = new String[]{"sku","安全类别","执行标准","商品名称","材质描述","颜色描述","产地导入"};
		
	
    	try
    	{
    		
    		 // request转换
	    	MultipartHttpServletRequest request = (MultipartHttpServletRequest)req;
	    	//取得request中的所有文件名  
			MultipartFile locationFile = request.getFile("secku");
			int num = Integer.parseInt(request.getParameter("num")); //每个工作表格最多存储num条数据（注：excel表格一个工作表可以存储65536条）
			
			InputStream  in = locationFile.getInputStream();
			//取得当前上传文件的文件名称
			String locationFileName = locationFile.getOriginalFilename(); 
			//截取上传文件的名称.后面的名称
			String fileType = locationFileName.substring(locationFileName.lastIndexOf(".")+1);
			
			//判断是xls文件，还是xlsx文件
			Workbook wb = null;  
	        if (fileType.equals("xls")) {  
	            wb = new HSSFWorkbook(in);  
	        }  
	        else if (fileType.equals("xlsx")) {  
	            wb = new XSSFWorkbook(in);  
	        }  
	        
	        //得到Excel表格中的第一个sheet1
	        Sheet sheet1 = wb.getSheetAt(0);
	        
	        
	        
	        //处理本地对应关系excel
	        Map<String,List<String>> locationMap = new HashMap<String,List<String>>();
	        
	        List<Product> listProduct = new ArrayList<Product>();
	        
	        int totalRow = sheet1.getPhysicalNumberOfRows();  //总行数
	        int totalColum = sheet1.getRow(0).getPhysicalNumberOfCells(); //总列数
	        
//	        System.out.println(totalRow+"总行数");
//	        System.out.println(totalColum+"总列数");
	        
	        String[][] resultBrr = new String[totalRow+num][totalColum]; //保存查询结果  总行数+1(title行)
	        
	        for(int  rowIndex = 0;rowIndex < sheet1.getPhysicalNumberOfRows();rowIndex++)
	        { 
	        	 
	        	Row row  =  sheet1.getRow(rowIndex);
	        	
	        	for(int colIndex = 0; colIndex < sheet1.getRow(rowIndex).getPhysicalNumberOfCells();colIndex++)//只需要前7列
	        	{
	        		String[] rowBrr = resultBrr[rowIndex]; // 要放入的某行
	        		
	        		Cell cell = row.getCell(colIndex);
	        		String value = getCellValue(cell);
	
	        		if(StringUtil.isBlank(value)){
	        			rowBrr[colIndex]="";
            		}else{
            			rowBrr[colIndex] = value;
            		}

	        	}
	        }
	     
	   		 
	        int k=0;
	        int total = totalRow-1; //行数
	        double ag = total*1.0/num; //总行数除每个sheet放几行，得sheet的个数
	        int avg =(int) Math.ceil(ag);  //sheet的个数
	        System.out.println(avg);
	        HSSFWorkbook exportWb  = new HSSFWorkbook();
	        String[][] resultArr = new String[num+1][totalColum]; //保存查询结果  总行数+1(title行)   把resultBrr的内容放入resultArr这个空数组中
//	        System.out.println(resultArr.length);
//	        System.out.println(resultBrr.length+"resultBrr的长度");
	        for (int i = 0; i < avg; i++) {  
	        	for(int t=0;t<resultArr.length;t++){ //行
			    	   for(int  j = 0; j < totalColum; j++)  //列
					   {
			    		    String[] rowArr = resultArr[t];
							
						    if(t==0){
								rowArr[j]=resultBrr[0][j];
								
							}else{								
								rowArr[j]=resultBrr[k+t][j];
							}
   	 				
   	 			      }
			    	   
               }
	 		
	 			k=k+num;
	 			System.out.println(k+"-----k");  
	 			System.out.println(num+"------num");
	 	        HSSFSheet exportSheet = exportWb.createSheet();
	 		    exportWb.setSheetName(i, "sheet" + String.valueOf(i));
	 	        HSSFCellStyle exportStyle = exportWb.createCellStyle();  
	 	        exportStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);    
	 	        for(int  rowIndex = 0;rowIndex < resultArr.length;rowIndex++)
	 	        {
	 	        	String[] row =  resultArr[rowIndex];
	 	 
	 	    		Row exportRow = exportSheet.createRow(rowIndex);

	 	    		for(int  colIndex = 0;colIndex < row.length;colIndex++)
	 	    		{
	 	         		 exportRow.createCell(colIndex).setCellValue(row[colIndex]);
	 	    		}
	 	    			
	 	        }
	 	        
	        }
	        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd_HH_mm_ss");
	        response.setContentType("application/vnd.ms-excel");    
	        response.setHeader("Content-disposition", "attachment;filename=product_"+sdf1.format(new Date())+".xls");
	        OutputStream ouputStream = response.getOutputStream();    
	        exportWb.write(ouputStream);    
	        ouputStream.flush();    
	        ouputStream.close();
	      
	        
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    		return "error";
    	}
		return response;
    	
		
    
		
	}
	@RequestMapping(value="/timetask/uploadGoodsXiYouFile")
	public @ResponseBody Object uploadGoodsXiYouFile(Model model,HttpServletRequest req,HttpServletResponse response){
		
		int num=0;
		try {
			MultipartHttpServletRequest request = (MultipartHttpServletRequest)req;
			MultipartFile locationFile = request.getFile("xiyou");
			InputStream  in = locationFile.getInputStream();
			String locationFileName = locationFile.getOriginalFilename(); 
			String fileType = locationFileName.substring(locationFileName.lastIndexOf(".")+1);
			
			Workbook wb = null;  
	        if (fileType.equals("xls")) {  
	            wb = new HSSFWorkbook(in);  
	        }  
	        else if (fileType.equals("xlsx")) {  
	            wb = new XSSFWorkbook(in);  
	        } 
	        
	        Sheet sheet1 = wb.getSheetAt(0);
	        Map<String,List<String>> locationMap = new HashMap<String,List<String>>();
	        for (int rowIndex = 1; rowIndex < sheet1.getPhysicalNumberOfRows(); rowIndex++) {
	        	num++;
	        	Row row  =  sheet1.getRow(rowIndex);
	        	String prodid = "";
	        	String cost = "";
	        	String oursaleprice = "";
	        	List<String> list = new ArrayList<String>(); 
	        	for (int colIndex = 0; colIndex < 3; colIndex++) {
	        		Cell cell = row.getCell(colIndex);
	        		String value = getCellValue(cell);
	        		if(colIndex == 0){
	        			prodid = value;
	        			list.add(prodid);
	        		}
	        		else if(colIndex == 1){
	        			if(StringUtil.isBlank(value)){
	            			list.add("");
	            		}else{
	            			cost = value;
	            			list.add(cost);
	            		}
	        		}
	        		else if (colIndex == 2){
	        			if(StringUtil.isBlank(value)){
	            			list.add("");
	            		}else{
	            			oursaleprice = value;
	            			list.add(oursaleprice);
	            		}
	        		}
				}
	        	locationMap.put(prodid, list);
			}
	        
	        Set<Entry<String,List<String>>> keySet= locationMap.entrySet();
			 Iterator<Entry<String,List<String>>> it = keySet.iterator();
			 while(it.hasNext())
			 { 
				 Entry<String, List<String>> entry = it.next();
				 //拿出一对中的键
				 String prodid = entry.getKey();
				 //拿出一对中的值
				 List<String> ourList = entry.getValue();
				 ProvideGoodsXiYou pdgxy = new ProvideGoodsXiYou();
				 pdgxy.setProdId(Long.parseLong(ourList.get(0)));
				 pdgxy.setCost(Double.parseDouble(ourList.get(1)));
				 pdgxy.setOurSalePrice(Double.parseDouble(ourList.get(2)));	 
				 providerGoodsUpdateDao.updateGoodsXiYou(pdgxy);
			 }
		} catch (Exception e) {
			e.printStackTrace();
    		return "error";
		}
		return num;
	}
	
	
	//库存更新，将京东为更新成功的导入在更新
	@RequestMapping("/timetask/jdExcelStock")
	public String jdExcelStock()
	{
		return "/autoSync/jdExcelStock";
	}
	private static final String APP_KEY = "0EDF5C042F17037A1A95660FB2701AA3";
	private static final String APP_SECRET = "13bb88d5e95744a6bbf64b240d729603";
	private static final String BASE_URL = "https://api.jd.com/routerjson";
	private static final String ACCESS_TOKEN = "68b326cd-049a-4935-b2d8-99d1115fd979";
	
	private JdClient client = new DefaultJdClient(BASE_URL,ACCESS_TOKEN,APP_KEY,APP_SECRET);
	
	@RequestMapping(value = "/timetask/jdExcelStockFile")
    public @ResponseBody Object jdExcelStockFile(Model model,HttpServletRequest req,HttpServletResponse response)  
	{	
		int num=0;
    	try
    	{
    		 // request转换
	    	MultipartHttpServletRequest request = (MultipartHttpServletRequest)req;
	    	//取得request中的所有文件名  
			MultipartFile locationFile = request.getFile("secku");
			InputStream  in = locationFile.getInputStream();
			//取得当前上传文件的文件名称
			String locationFileName = locationFile.getOriginalFilename(); 
			//截取上传文件的名称.后面的名称
			String fileType = locationFileName.substring(locationFileName.lastIndexOf(".")+1);
			
			//判断是xls文件，还是xlsx文件
			Workbook wb = null;  
	        if (fileType.equals("xls")) {  
	            wb = new HSSFWorkbook(in);  
	        }  
	        else if (fileType.equals("xlsx")) {  
	            wb = new XSSFWorkbook(in);  
	        }  
	        
	        //得到Excel表格中的第一个sheet1
	        Sheet sheet1 = wb.getSheetAt(0);
	        
	        
//	        System.out.println(sheet1.getRow(0).getPhysicalNumberOfCells()+"有效列数");
//	        System.out.println(sheet1.getRow(1).getPhysicalNumberOfCells()+"有效列数");
//	        System.out.println(sheet1.getPhysicalNumberOfRows()+"总行数");
	        //处理本地对应关系excel
	        Map<String,List<String>> locationMap = new HashMap<String,List<String>>();
	        List<StockUpdateLog> listStockUpdateLog = new ArrayList<StockUpdateLog>();
	        for(int  rowIndex = 1;rowIndex < sheet1.getPhysicalNumberOfRows();rowIndex++)
	        { //循环行
	        	 
	        	Row row  =  sheet1.getRow(rowIndex);
	        	String   sku = "";
	        	List<String> list = new ArrayList<String>(); 
	        	for(int colIndex = 0; colIndex < 5;colIndex++)//只需要前7列
	        	{ //循环某一行的所有列
	        		Cell cell = row.getCell(colIndex);//获得第rowIndex行的第colIndex列单元格
	        		String value = getCellValue(cell);// 给单元格赋值
	        	     
	        		if(colIndex == 0)    //sku
	            	{  
            		    sku=value;
            			list.add(sku);	
	            	}else{
	            		
	            		if(StringUtil.isBlank(value)){
	            			list.add("");
	            		}else{
	            			list.add(value);
	            		}  
	            	}
	        		
	        		      	
	        	}
	        	locationMap.put(sku, list);
	        }
	        //从map集合中得到一个整体，就是其中的一个键和一个list集合
	   		 Set<Entry<String,List<String>>> keySet= locationMap.entrySet();
	   		 Iterator<Entry<String,List<String>>> it = keySet.iterator();
	   		 //将map中的每个键值对一个一个拿出来
	   		 while(it.hasNext())
	   		 { 
	   			 Entry<String, List<String>> entry = it.next();
	   			 //拿出一对中的键
	   			 String pSku = entry.getKey();
	   			 if(StringUtil.isNotBlank(pSku)){
	   				num++;
	   				 //拿出一对中的值
		   			 List<String> ourList = entry.getValue();
		   			  StockUpdateLog sul= new StockUpdateLog();
		   			  sul.setSku(ourList.get(0));      //sku
		   			  sul.setLocation(ourList.get(1));     //sh或hk
		   			  sul.setPsku(ourList.get(2));        //平台sku
		   			  sul.setName(ourList.get(3));        //平台名称	   			  
		   			  sul.setStock(Integer.parseInt(ourList.get(4)));      //要更新的库存
		   			listStockUpdateLog.add(sul);
	   				 
	   			 }
	   			
	   		 }
	        for(int i=0;i<listStockUpdateLog.size();i++){
	        	//记录更新日志
	    		Map<String, String> logMap = new HashMap<String, String>();
	    		StockUpdateLog su = listStockUpdateLog.get(i);
	    		logMap.put("sku", su.getSku());
	    		logMap.put("psku", su.getPsku());
	    		logMap.put("name", "jd");
	    		logMap.put("stock", su.getStock()+"");
	    		logMap.put("location",su.getLocation());
	    		
	    		WareSkuStockUpdateRequest requ=new WareSkuStockUpdateRequest();
	    		requ.setSkuId(su.getPsku());
	    		requ.setQuantity(su.getStock()+"");
	    		try
	    		{
	    			WareSkuStockUpdateResponse respon = client.execute(requ);
	    			if("0".equals(respon.getCode()))
	    			{
	    				logMap.put("type", "success");
	    			}
	    			else
	    			{
	    				logMap.put("type", "error");
	    				logMap.put("error", respon.getMsg());
	    			}
	    		}
	    		catch (JdException e)
	    		{
	    			logMap.put("type", "error");
	    			logMap.put("error", e.getErrMsg());
	    		}
	    		
	    		autoSyncDao.addUpdateLog(logMap);
	        	
	        }	   		 
	         	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    		return "error";
    	}
    	
		return num;
    
		
	}
	
//	FileInputStream inp = new FileInputStream("E:\\WEIAN.xls"); 
//	HSSFWorkbook wb = new HSSFWorkbook(inp);
//	HSSFSheet sheet = wb.getSheetAt(2); // 获得第三个工作薄(2008工作薄)
//	// 填充上面的表格,数据需要从数据库查询
//	HSSFRow row5 = sheet.getRow(4); // 获得工作薄的第五行
//	HSSFCell cell54 = row5.getCell(3);// 获得第五行的第四个单元格
//	cell54.setCellValue("测试纳税人名称");// 给单元格赋值
//	
//	int coloumNum=sheet.getRow(0).getPhysicalNumberOfCells(); //获得总列数
//	int rowNum=sheet.getLastRowNum();//获得总行数
	//http://blog.csdn.net/weixin_38379125/article/details/78773351
	// 获取第一个实际行的下标
	// getFirstRowNum: 8
//	System.out.println("getFirstRowNum: " + sheet.getFirstRowNum());
//	// 获取最后一个实际行的下标
//	// getLastRowNum: 11
//	System.out.println("getLastRowNum: " + sheet.getLastRowNum());
//
//	Row row=sheet.getRow(sheet.getLastRowNum());
//	// 获取在第一行第一个单元格的下标
//	// getFirstCellNum: 4
//	System.out.println("getFirstCellNum: " + sheet.getRow(0).getFirstCellNum());
//	// 获取在第一行的列数
//	// getLastCellNum: 8
//	System.out.println("getLastCellNum: " + sheet.getRow(0).getLastCellNum());
	
	
	//图片导入192.168.1.198:88上的代码示例
//		@RequestMapping(value = "/timetask/SecurityHttpByFile")
//	    public @ResponseBody Object SecurityHttpByFile(Model model,HttpServletRequest req,@RequestParam(value="img",required=false) MultipartFile file,HttpServletResponse response)  
//		{	
//			 String imgUrl="d:\\uploadPicture";
//	    	try
//	    	{ // request转换
//	    	
//		    	MultipartHttpServletRequest request = (MultipartHttpServletRequest)req;
//	    		String locationFileName = request.getFile("img").getOriginalFilename();
//	    		imgUrl=imgUrl+"\\"+locationFileName;
//	    		System.out.println(imgUrl);
//				String result=uploadImg(imgUrl);
//				
//				 System.out.println(result);
//	    	}
//	    	catch(Exception e)
//	    	{
//	    		e.printStackTrace();
//	    		return "error";
//	    	}
//	    	
//	    	return null;
//			
//		}
	
	
	
	
	
//		public static final String API="http://140.207.52.210:88/upload/test.php";
//		 private String uploadImg(String imgUrl) throws Exception {
//		        File imgFile=new File(imgUrl);
//		        URL url=new URL(API);
//		        HttpURLConnection conn=(HttpURLConnection) url.openConnection();
//		        conn.setConnectTimeout(10000);
//		        conn.setRequestMethod("POST");
//		        conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=----123456789");
//		        conn.setDoInput(true);
//		        conn.setDoOutput(true);
//		        
//		        OutputStream os=new DataOutputStream(conn.getOutputStream());
//		        StringBuilder body=new StringBuilder();
//		        body.append("------123456789\r\n");
//		        body.append("Content-Disposition: form-data; name='img'; filename='"+imgFile.getName()+"'\r\n");
//		        body.append("Content-Type: image/jpeg\r\n\r\n");
//		        os.write(body.toString().getBytes());
//		        
//		        InputStream is=new FileInputStream(imgFile);
//		        byte[] b=new byte[1024];
//		        int len=0;
//		        while((len=is.read(b))!=-1){
//		            os.write(b,0,len);
//		        }
//		        String end="\r\n------123456789--";
//		        os.write(end.getBytes());
//		        
//		        //输出返回结果
//		        InputStream input=conn.getInputStream();
//		        byte[] res=new byte[1024];
//		        int resLen=input.read(res);
//		        return new String(res,0,resLen);
//		    }
	
//	public static final  String pic_root = "http://140.207.52.210:88/upload/";
//	public static final  String basePath ="d:\\upic";
//	@RequestMapping(value = "/timetask/SecurityHttpByFile")
//	public @ResponseBody Object SecurityHttpByFile(Model model,HttpServletRequest request,HttpServletResponse response,@RequestParam(value="img",required=false) MultipartFile file)  
//	{	
//		  
//		Map<String,String> map = MiniUiUtil.getParameterMap(request);//request请求转map
//		    Map<String,String> maps = new HashMap<String, String>();
//			maps.put("result", "non");			
//		try
//		{ 
//			String pic = request.getParameter("pic");
//			int i = pic.lastIndexOf("/");
//			int t = pic.lastIndexOf("?");
//			//页面上得到的图片名
//			String pictue = pic.substring(i+1, t);
//
//			String brandName = pictue.split("_")[0];
////			System.out.println(pictue);		
////			System.out.println(brandName);
//			String img =brandName+"_"+file.getOriginalFilename();	
//			String imgPath = basePath+"\\"+img;
//			File destFile = new File(imgPath);
//			//从客户端将文件读入到指定字节数组中
//			InputStream is = file.getInputStream();
//			byte[] data = new byte[(int)file.getSize()];
//			is.read(data);
//			//再将此字节数组写入到指定文件中
//			FileUtils.writeByteArrayToFile(destFile, data);
////			System.out.println("完成");		
//		
//			//上传的图片名要和产品的产品、型号、材质、颜色一致
//			if(!pictue.equals(img)){
//				maps.put("result", "no");
//				return maps;
//			}
//			String result=uploadImg(imgPath);		
//			if(result.equals("upload success")){
//				result=uploadImg(imgPath);
//				if(result.equals("upload success")){
//				maps.put("imgUrl", pic_root+img);
//				maps.put("result", "success");
//				return maps;
//				}
//			}else if(result.equals("upload error")){
//				maps.put("result", "error");
//				return maps;
//			}else if(result.equals("error")){
//				maps.put("result", "error");
//				return maps;
//			}
////			 System.out.println(result);
//		}
//		
//		catch(Exception e)
//		{
//			e.printStackTrace();
//			log.error(e.getMessage(), e);
//		}
//		return maps;
//		
//		
//		
//	}
	//siku错误库存更新日志再次更新
//	http://www.shangshangss.com:8078/timetask/sikuLogError.html
	//http://www.shangshangsp.com:8080/timetask/sikuLogError.html
	///http://www.shangshangss.com:8078/timetask/exportExcel.thml
	@RequestMapping("/timetask/sikuLogError")
	public String sikuLogError()
	{
		return "/autoSync/sikuLogError";
	}

	@RequestMapping("/timetask/updateSkuLogs")
	public String updateSkuLogs(HttpServletRequest request,HttpServletResponse response,Model model) {
		System.out.println(sdf.format(new Date()) + " sync error siku skuLog start....");
		long startTime = System.currentTimeMillis();
		String updateTime = request.getParameter("time");
		String name = request.getParameter("name");
		String location = request.getParameter("location"); 
		if(StringUtil.isBlank(updateTime) || StringUtil.isBlank(name) || StringUtil.isBlank(location)) return null;
		Map<String, String> searchMap = new HashMap<String, String>();
		searchMap.put("name",name);
		searchMap.put("location",location);
		searchMap.put("updateTime",updateTime); //此次更新的开始时间
		List<StockUpdateLog> stockUpdateLog = platformStockUpdateDao.selectStockUpdateLogByMapAndError(searchMap);
		if(stockUpdateLog.size()<=0){
			 return null;
		}
		String endTimeLog = stockUpdateLog.get(0).getUpdateTime(); //此次更新的结束时间
		searchMap.put("type","error");
		//查询出此次更新的错误
		stockUpdateLog = platformStockUpdateDao.selectStockUpdateLogByMapAndError(searchMap);
		if(stockUpdateLog.size()>0){
			for(StockUpdateLog stockLog:stockUpdateLog){
				siKunewPlatFormStockUpdate.updateSikuStock(stockLog.getPsku(), stockLog.getSku(), stockLog.getStock(), location);
			}
		}else{
			model.addAttribute("cnt", stockUpdateLog.size());
			return "/autosync/errorStockSiku";
		}
		long endTime = System.currentTimeMillis();
		long a = endTime-startTime;
		long min = a/1000/60;
		System.out.println(sdf.format(new Date()) + " sync error siku skuLog  total min:" + min + "min");
		model.addAttribute("startLogTime", endTimeLog);
		return "/autosync/errorStockSiku";
	}
	
	
	
	//http://www.shangshangss.com:8078/timetask/setPlatStockZero.html
			@RequestMapping("/timetask/setPlatStockZero")
			public String setPlatStockZero()
			{
				return "/autoSync/setPlatStockZero";
			}
			//天猫 寺库和小红书不能上银泰西有的
			@RequestMapping("/timetask/setPlatStockZeroUPdate")
			public String setPlatStockZeroUPdate(HttpServletRequest request,HttpServletResponse response) throws Exception{
				
				Map<String,String> searchMaps = new  HashMap<String, String>();
				searchMaps = MiniUiUtil.getParameterMap(request);
				String sellPlatName = request.getParameter("sellPlatName");  //售卖平台，如：天猫，魅力惠，京东..... shepin
				String providePlatName = request.getParameter("providePlatName");//供应商，如银泰西有 xiyou
				System.out.println(sdf.format(new Date()) + sellPlatName+ " sync stock start....");
				long startTime = System.currentTimeMillis();
				
				
				System.out.println(sellPlatName);
				System.out.println(providePlatName);
				Map<String,String> searchMap = new HashMap<String, String>();
				if(sellPlatName.equals("Mlh")){
					sellPlatName = "Mlh";
					searchMap.put("skuisnotnull", "Mlh_sku");
				}else if(sellPlatName.equals("jd")){
					sellPlatName = "jd";
					searchMap.put("skuisnotnull", "jd_sku");
				}else if(sellPlatName.equals("shepin")){
					sellPlatName = "shepin";
					searchMap.put("skuisnotnull", "shepin_sku");
				}
				
				if(providePlatName.equals("xiyou")){
					searchMap.put("provideisnotnull", "xiyou_prod_id");
				}
				//同步所有库存
				
				//查询香港库存
				searchMap.put("type", "sh");
				
				
				List<StockUpdate> list =  platformStockUpdateDao.selectStockUpdateByMap(searchMap);
				if(list!= null || list.size() !=0){
					
				if(sellPlatName.equals("Mlh")){
					for(StockUpdate s : list){
						meiLiHuiPlatFormStockUpdate.updateCanSaleProduct(s.getMlhSku(),s.getSku(), 0,s.getType());
					try {
						Thread.sleep(500);
					}catch (InterruptedException e) {
					}
					}

				}else if(sellPlatName.equals("jd")){
					 for(StockUpdate s : list){
					    	gouDongPlatFormStocuUpdate.updateGouDongStock(s.getJdSku(), s.getSku(), 0, s.getType());
							//更新一次休息0.5秒
							try{
								Thread.sleep(500);
							}catch (InterruptedException e){
								
							}
						}
				}else if(sellPlatName.equals("shepin")){
					 for(StockUpdate s : list){
						 shePinFormStockUpdate.updateTmallStock(s.getShepinSku(),s.getSku(),4, s.getType());
							//更新一次休息0.5秒
							try{
								Thread.sleep(500);
							}catch (InterruptedException e){
								
							}
						}
				}
			}
				long endTime = System.currentTimeMillis();
				long a = endTime-startTime;
				long min = a/1000/60;
				System.out.println(sdf.format(new Date()) + sellPlatName+" sync stock  total min:" + min + "min");
				return "/autosync/success";
			}
			
			
			//http://www.shangshangss.com:8078/timetask/getXiYouUpdateStock.html
			//获取一段时间内的西有变化的库存
			@RequestMapping("/timetask/getXiYouUpdateStock")
			public String getXiYouUpdateStock()
			{
				return "/autoSync/getXiYouUpdateStockByTime";
			}
			
			//http://www.shangshangss.com:8078/timetask/getXiYouUpdateStock.html
			@RequestMapping("/timetask/getXiYouUpdateStockByTime")
			public String getXiYouUpdateStockByTime(HttpServletRequest request,HttpServletResponse response) throws Exception{
				System.out.println(sdf.format(new Date()) + " getXiYouUpdateStockByTime stock start....");
				long startTime = System.currentTimeMillis();
				String staTime = request.getParameter("startTime")+" 00:00:00";
				String endTime = request.getParameter("endTime")+" 00:00:00";
				
				Date dateStart = sdf.parse(staTime);
				String startDatetime =sdf.format(dateStart);
				Date dateEnd = sdf.parse(endTime);
				String endDatetime =sdf.format(dateEnd);
//				System.out.println(startDatetime);

				stockUpdateService.setCanUpdateStockStatus("false"); 
				xiYouPlatFormStockUpdate.getChangeStockThree(startDatetime,endDatetime);
				stockUpdateService.setCanUpdateStockStatus("true");
				long endTimes = System.currentTimeMillis();
				long a = endTimes-startTime;
				long min = a/1000/60;
				System.out.println(sdf.format(new Date()) + " getXiYouUpdateStockByTime stock  total min:" + min + "min");
				return "/autosync/success";
			}
			//手动更新云尚的商品
			//http://www.shangshangss.com:8078/timetask/getAllYShangGoodsByHandel.html
			@SuppressWarnings("unused")
			@RequestMapping(value = "/timetask/getAllYShangGoodsByHandel")
			private String getAllYShangGoodsByHandel(Model model,HttpServletRequest request) throws Exception{
				stockUpdateService.setCanUpdateStockStatus("false"); 
				yShangPlatFormStockUpdate.getYShangProducts();
				stockUpdateService.setCanUpdateStockStatus("true");
				return "/autosync/success";
		    }
			
			//手动更新云尚的商品库存
			//http://www.shangshangss.com:8078/provider/updateYShangStockPrice.html
			@SuppressWarnings("unused")
			@RequestMapping(value = "/timetask/updateYShangStockPrice")
			private String updateYShangStockPrice(Model model,HttpServletRequest request) throws Exception{
//				stockUpdateService.setCanUpdateStockStatus("false"); 
				yShangPlatFormStockUpdate.updateYShangStockPrice();
//				stockUpdateService.setCanUpdateStockStatus("true");
				return "/autosync/success";
		    }
}
 
