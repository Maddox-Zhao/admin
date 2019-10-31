package com.huaixuan.network.biz.service.platformstock.impl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.autosyn.AutoSyncDao;
import com.huaixuan.network.biz.dao.platformstock.PlatformStockUpdateDao;
import com.huaixuan.network.biz.dao.product.ProductDao;
import com.huaixuan.network.biz.domain.autosyn.StockData;
import com.huaixuan.network.biz.domain.platformstock.StockReserve;
import com.huaixuan.network.biz.domain.platformstock.StockUpdate;
import com.huaixuan.network.biz.domain.platformstock.StockUpdateHistory;
import com.huaixuan.network.biz.domain.platformstock.StockUpdateLog;
import com.huaixuan.network.biz.domain.platformstock.WeimobEntity;
import com.huaixuan.network.biz.domain.product.Product;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.service.autosyn.HttpRequest;
import com.huaixuan.network.biz.service.platformstock.FenQiLePlatFormStocuUpdate;
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
import com.huaixuan.network.biz.service.platformstock.StockUpdateService;
import com.huaixuan.network.biz.service.platformstock.SuNingPlatFormStockUpdate;
import com.huaixuan.network.biz.service.platformstock.TmallPlatFormStockUpdate;
import com.huaixuan.network.biz.service.platformstock.WeiMobPlatFormStockUpdate;
import com.huaixuan.network.biz.service.platformstock.XiaohongshuPlatFormStockupdate;
import com.huaixuan.network.biz.service.platformstock.YhdPlatformStockUpdate;
import com.huaixuan.network.biz.service.platformstock.YinTaiPlatFormStockUpdate;
import com.huaixuan.network.biz.service.platformstock.YunShangPlatFormStockUpdate;
import com.huaixuan.network.biz.service.platformstock.ZhenPinPlatformStockUpdate;
import com.huaixuan.network.common.util.json.JSONException;
import com.huaixuan.network.common.util.json.JSONObject;
import com.huaixuan.network.common.util.miniui.MiniUiGrid;
import com.huaixuan.network.common.util.miniui.MiniUiUtil;
import com.hundsun.common.lang.StringUtil;

/**
 * @author Mr_Yang 2016-5-13 下午12:18:40
 **/

@Service("stockUpdateService")
public class StockUpdateServiceImpl implements StockUpdateService {

	@Autowired
	private AutoSyncDao autoSyncDao;

	@Autowired
	private PlatformStockUpdateDao platformStockUpdateDao;

	@Autowired
	private KaolaPlatformStockUpdate kaolaPlatformStockUpdate;
	
	@Autowired
	private KaolaPlatHaiTaoformStockUpdate kaolaPlatHaiTaoformStockUpdate;

	@Autowired
	private ZhenPinPlatformStockUpdate zhenPinPlatformStockUpdate;

	@Autowired
	private SiKuPlatFormStockUpdate siKuPlatFormStockUpdate;
	
	@Autowired
	private SiKuNewPlatFormStockUpdate siKuNewPlatFormStockUpdate;

	@Autowired
	private ShangPinPlatformStocuUpdate shangPinPlatformStocuUpdate;

	@Autowired
	private YhdPlatformStockUpdate yhdPlatformStockUpdate;

	@Autowired
	private HigoPlatFormStockUpdate higoFormStockUpdate;

	@Autowired
	private PinDuoDuoNewPlatFormStockUpdate pinduoduonewPlatFormStockUpdate;
	
	@Autowired
	private PinDuoDuoPlatFormStockUpdate pinduoduoPlatFormStockUpdate;
	
	@Autowired
	private YunShangPlatFormStockUpdate yunShangPlatFormStockUpdate;
	
	
	@Autowired
	private TmallPlatFormStockUpdate tmallFormStockUpdate;

	@Autowired
	private FenQiLePlatFormStocuUpdate fenQiLePlatFormStocuUpdate;

	@Autowired
	private GouDongPlatFormStocuUpdateImpl gouDongPlatFormStocuUpdateImpl;
	
	
	
	@Autowired
	private MeiLiHuiPlatFormStockUpdate MeiLiHuiPlatFormStockUpdate;
	
	@Autowired
	private MeiLiHuiNewPlatFormStockUpdate MeiLiHuinewPlatFormStockUpdate;

	@Autowired
	private XiaohongshuPlatFormStockupdate xiaohongshuPlatFormStockupdate;

	@Autowired
	private OFashionPlatFormStockUpdate ofashionPlatFormStockUpdate;

	@Autowired
	private WeiMobPlatFormStockUpdate weiMobPlatFormStockUpdate;

	@Autowired
	private YinTaiPlatFormStockUpdate yinTaiPlatFormStockUpdate;
	
	@Autowired
	private OfMiChengPaltFormStockupdate ofMiChengPaltFormStockupdate;

	
	@Autowired
	private ShePinPlatFormStockUpdate shePinFormStockUpdate;
	
	@Autowired
	private SuNingPlatFormStockUpdate suNingPlatFormStockUpdate;
	

	@Autowired
	private ProductDao productDao;

	private static final String path = "d:/stock_log/";// 记录库存的日志文件夹

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//統計可售庫存到hx_stock_update表
	@Override
	public boolean syncNowStock() {
		boolean result = false;
		String status = getsyncNowStockStatus();
		if ("true".equals(status)) {
			setCanUpdateStockStatus("false"); // 暂时不自动同步库存，等更新完了库存在同步库存
			syncNowStockByType("hk");
			syncNowStockByType("sh");
			setCanUpdateStockStatus("true"); // 更新完了 可以同步库存
			result = true;
		}
		return result;

	}

	private void syncNowStockByType(String type) {
		List<Long> siteList = new ArrayList<Long>();
		if ("hk".equals(type)) {
			// 获取可售库存所需站点信息
			for (String idSite : MiniUiUtil.hkSite) {
				siteList.add(Long.valueOf(idSite));
			}
		} else {
			// 获取可售库存所需站点信息
			for (String idSite : MiniUiUtil.shSite) {
				siteList.add(Long.valueOf(idSite));
			}
		}

//		platformStockUpdateDao.updateStock2ZeroByType(type);// 更新库存为0，在更新>0的到库存表
															// (同步库存)
		List<StockData> stockDataList = autoSyncDao.searchStockBySiteList(siteList);
		Map updateMap = new HashMap();
		updateMap.put("type", type);
		List<StockUpdate> list = new ArrayList<StockUpdate>();
		for (StockData s : stockDataList) {

			StockUpdate su = new StockUpdate();
			su.setSku(s.getSku());
			su.setNowStockNum(s.getNum());
			su.setType(type);
			list.add(su);
		}
		updateMap.put("list", list);
		platformStockUpdateDao.updateBatchNowStock(updateMap);

	}

	public void setCanUpdateStockStatus(String flag) {

		FileWriter fw = null;
		try {
			File file = new File(path + "canupdatestock_not_delete.txt");
			fw = new FileWriter(file);
			fw.write(flag);
		} catch (Exception e) {
			// e.printStackTrace();
		} finally {
			if (fw != null) {
				try {
					fw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public String getsyncNowStockStatus() {
		RandomAccessFile accessFile = null;
		try {
			accessFile = new RandomAccessFile(path
					+ "canupdatestock_not_delete.txt", "r");
			int length = (int) accessFile.length();
			byte[] c = new byte[length];
			accessFile.seek(0);
			accessFile.read(c, 0, length);
			return new String(c);
		} catch (Exception e) {
			try {
				accessFile = new RandomAccessFile(path
						+ "canupdatestock_not_delete.txt", "rw");
				accessFile.seek(0);
				accessFile.write("true".getBytes());
				return "true";
			} catch (Exception e1) {

			}

		} finally {
			if (accessFile != null) {
				try {
					accessFile.close();
				} catch (IOException e) {
					// e.printStackTrace();
				}
			}
		}
		return "false";
	}

	@Override
	public void updatePlatformOrderStock2Zero(String field) {
		// 先更新寺库订单数 字段为0 在同步 总的订单库存数
		Map<String, String> updateMap = new HashMap<String, String>();
		updateMap.put("field", field);
		platformStockUpdateDao.updatePlatformOrderStock2ZeroByType(updateMap);
		// 同步总的订单库存总数 (总数量=寺库+尚品+珍品+考拉+1号店)
		platformStockUpdateDao.syncOrderStock();

	}

	// 从历史表更新库存
	@Override
	public void autoSyncLocationStock() {
		// 处理历史表数据
		Long manxHistoryId = platformStockUpdateDao.getHistoryMaxId();
		if (manxHistoryId <= 0)
			return;
		List<StockUpdateHistory> list = platformStockUpdateDao.selectHistoryByHistoryMaxid(manxHistoryId);
		if (list.size() == 0)
			return;
		Map<String, String> searchMap1 = new HashMap<String, String>(); // 查询历史表
																		// 上一步操作

		for (StockUpdateHistory suh : list) {
			Long idOperation = suh.getIdOperation();
			String custonerNmae = suh.getCustomerName();
			if (custonerNmae == null)
				custonerNmae = "";
			String idLocation = suh.getIdLocation();
			String sku = suh.getSku();
			if (suh.getIdHistory() > manxHistoryId)
				manxHistoryId = suh.getIdHistory();
			if (null == sku || "".equals(sku) || StringUtil.isEmpty(sku) || StringUtil.isBlank(sku)) continue; // 没有sku 不做处理
			boolean updatePlatformOrderNum = false;
			if (1 == idOperation)
				continue; // 准备入库 不做处理
			if (8 == idOperation) // 调货出库 需要更新库存的站点为 上一个站点
			{
				idLocation = suh.getIdLastLocation();
			}

			String type = "";
			if (MiniUiUtil.hkSite.contains(idLocation)) {
				type = "hk";
			} else if (MiniUiUtil.shSite.contains(idLocation)) {
				type = "sh";
			}
			if (StringUtil.isBlank(type))
				continue; // 站点不在香港或上海 不做更新

			// 判断是否需要更新各个平台订单数
			if (4 == idOperation || 5 == idOperation) // 销售出库或者预留 (开单 判断是否开给平台
														// 如果是开给平台 更新库存表订单数量)
														// 5-预留 4-销售出库
			{
				// 如果是客户端预订 历史表没有客户信息 通过lifecyle来获取客户信息
				if (5 == idOperation && StringUtil.isBlank(custonerNmae)) {
					custonerNmae = suh.getCustomerName2();// 通过lifecyle 关联订单表获取
					if (custonerNmae == null)
						custonerNmae = "";
				}
				// 有订单接口的平台
				if (custonerNmae.contains("寺库") || custonerNmae.contains("网易")
						|| custonerNmae.contains("珍品")
						|| custonerNmae.contains("尚品")
						|| custonerNmae.contains("天猫")
						|| custonerNmae.contains("HIGO")
						|| custonerNmae.contains("分期乐")
						|| custonerNmae.contains("京东")
						|| custonerNmae.contains("拼多多")
						|| custonerNmae.contains("魅力惠")
						|| custonerNmae.contains("小红书")
						|| custonerNmae.contains("ofashion")
						|| custonerNmae.contains("微盟")
						|| custonerNmae.contains("银泰")
						|| custonerNmae.contains("奢品")
						|| custonerNmae.contains("ofashion迷橙")
						|| custonerNmae.contains("苏宁")
						|| custonerNmae.contains("新寺库")
						|| custonerNmae.contains("考拉海淘")
						|| custonerNmae.contains("新拼多多")) {
					searchMap1.put("idHistory", suh.getIdHistory() + "");
					searchMap1.put("idProduct", suh.getIdProduct());
					// 若果上一步操作 产品状态是可售的才需要更新订单库存数
					StockUpdateHistory lastUpdateProduct = platformStockUpdateDao.selectProductLastSteepStatus(searchMap1);
					if (lastUpdateProduct.getIdStatus() == 1) // 如果上一步操作 产品状态是可售
																// 才需要更新订单数 减1
					{
						updatePlatformOrderNum = true;// 需要更新订单库存数
					}

				}
			}

			List<Long> siteList = new ArrayList<Long>();
			if ("hk".equals(type)) {
				// 获取可售库存所需站点信息
				for (String idSite : MiniUiUtil.hkSite) {
					siteList.add(Long.valueOf(idSite));
				}
			} else {
				// 获取可售库存所需站点信息
				for (String idSite : MiniUiUtil.shSite) {
					siteList.add(Long.valueOf(idSite));
				}
			}
			int num = autoSyncDao.searchStockBySiteAndSku(siteList, sku); // 查出国内所有站点(或国外)，是这个sku的所有可售商品
          //通过sku获取型号材质颜色尺寸
		    Product prot = productDao.selectProductBySku(sku);
			String typename= prot.getType(); //型号
			String materialname = prot.getMaterial(); //材质
			String colorname = prot.getColor();   //颜色
			String sizename = prot.getSize();    //尺寸
			Long idBrand = prot.getIdBrand(); //品牌Id
			Long idSeries = prot.getIdSeries(); //品类ID
			
			
			Map<String, String> searchMap = new HashMap<String, String>();
			searchMap.put("sku", sku);
			searchMap.put("type", type);
			List<StockUpdate> needUpdatePlatformList = platformStockUpdateDao.selectStockUpdateByMap(searchMap);
			// 当前产品为新入库
			if (needUpdatePlatformList.size() == 0) {
				StockUpdate su = new StockUpdate();
				su.setSku(sku);
				su.setNowStockNum(num);
				su.setLastUpdateStockNum(num);// 刚插入的数据没有 各个平台sku 不做更新
				su.setType(type);
				
				su.setTypename(typename);
				su.setMaterialname(materialname);
				su.setColorname(colorname);
				su.setSizename(sizename);
				su.setIdBrand(idBrand);
				su.setIdSeries(idSeries);
				
				platformStockUpdateDao.insertStockUpdate(su);

				// 如果是海外的货 国内没有上过 也插入国内sku到hx_stock_update表
				// 京东 天猫 分期乐 加了海外库存 所以国内没上海外有库存的话就更新不到(没有平台sku更新下来)
				if ("hk".equals(type)) {
					Map<String, String> searchMapSh = new HashMap<String, String>();
					searchMapSh.put("sku", sku);
					searchMapSh.put("type", "sh");
					List<StockUpdate> needUpdatePlatformListSh = platformStockUpdateDao.selectStockUpdateByMap(searchMapSh);
					if (needUpdatePlatformListSh.size() == 0) {
						List<Long> shSiteList = new ArrayList<Long>();
						// 获取可售库存所需站点信息
						for (String idSite : MiniUiUtil.shSite) {
							shSiteList.add(Long.valueOf(idSite));
						} 
						int shNum = autoSyncDao.searchStockBySiteAndSku(shSiteList, sku);
						su = new StockUpdate();
						su.setSku(sku);
						su.setNowStockNum(shNum);
						su.setLastUpdateStockNum(shNum);// 刚插入的数据没有 各个平台sku 不做更新
						su.setType("sh");
						
						su.setTypename(typename);
						su.setMaterialname(materialname);
						su.setColorname(colorname);
						su.setSizename(sizename);
						su.setIdBrand(idBrand);
						su.setIdSeries(idSeries);
						platformStockUpdateDao.insertStockUpdate(su);
					}
				}
				continue;
			}
			for (StockUpdate nufl : needUpdatePlatformList) {
				if (updatePlatformOrderNum) // 是开给各个平台的 需要更新订单数
				{
					int orderNum = nufl.getOrderStockNum();
					orderNum--;
					if (orderNum < 0)
						orderNum = 0;
					nufl.setOrderStockNum(orderNum);

					if (custonerNmae.contains("寺库")) {
						int sikuOrderStock = nufl.getSikuOrderStock();
						sikuOrderStock--;
						if (sikuOrderStock < 0)
							sikuOrderStock = 0;
						nufl.setSikuOrderStock(sikuOrderStock);
					} else if (custonerNmae.contains("网易")) {
						int kaolaOrderStock = nufl.getKaolaOrderStock();
						kaolaOrderStock--;
						if (kaolaOrderStock < 0)
							kaolaOrderStock = 0;
						nufl.setKaolaOrderStock(kaolaOrderStock);
					} else if (custonerNmae.contains("考拉海淘")) {
						int kaolahtOrderStock = nufl.getKaolahtOrderStock();
						kaolahtOrderStock--;
						if (kaolahtOrderStock < 0)
							kaolahtOrderStock = 0;
						nufl.setKaolahtOrderStock(kaolahtOrderStock);
					}else if (custonerNmae.contains("珍品")) {
						int zhenpinOrderStock = nufl.getZhenpinOrderStock();
						zhenpinOrderStock--;
						if (zhenpinOrderStock < 0)
							zhenpinOrderStock = 0;
						nufl.setZhenpinOrderStock(zhenpinOrderStock);
					}
					// else if(custonerNmae.contains("尚品"))
					// {
					// int shangpinOrderStock = nufl.getShangpinOrderStock();
					// shangpinOrderStock--;
					// if(shangpinOrderStock < 0) shangpinOrderStock = 0;
					// nufl.setShangpinOrderStock(shangpinOrderStock);
					// }
					/*else if (custonerNmae.contains("HIGO")) {
						int higoOrderStock = nufl.getHigoOrderStock();
						higoOrderStock--;
						if (higoOrderStock < 0)
							higoOrderStock = 0;
						nufl.setHigoOrderStock(higoOrderStock);
					}*/
					else if (custonerNmae.contains("天猫")) {
						int tmallOrderStock = nufl.getTmallOrderStock();
						tmallOrderStock--;
						if (tmallOrderStock < 0)
							tmallOrderStock = 0;
						nufl.setTmallOrderStock(tmallOrderStock);
					} /*else if (custonerNmae.contains("分期乐")) {
						int fqlOrderStock = nufl.getFqlOrderStock();
						fqlOrderStock--;
						if (fqlOrderStock < 0)
							fqlOrderStock = 0;
						nufl.setFqlOrderStock(fqlOrderStock);
					}
                 */					
					else if (custonerNmae.contains("京东")) {
						int jdOrderStock = nufl.getJdOrderStock();
						jdOrderStock--;
						if (jdOrderStock < 0)
							jdOrderStock = 0;
						nufl.setJdOrderStock(jdOrderStock);
					} else if (custonerNmae.contains("拼多多")) {
						int pddOrderStock = nufl.getPddOrderStock();
						pddOrderStock--;
						if (pddOrderStock < 0)
							pddOrderStock = 0;
						nufl.setPddOrderStock(pddOrderStock);
					} else if (custonerNmae.contains("魅力惠")) {
						int MlhOrderStock = nufl.getMlhOrderStock();
						MlhOrderStock--;
						if (MlhOrderStock < 0)
							MlhOrderStock = 0;
						nufl.setMlhOrderStock(MlhOrderStock);
					} else if (custonerNmae.contains("小红书")) {
						int xhsOrderStock = nufl.getXhsOrderStock();
						xhsOrderStock--;
						if (xhsOrderStock < 0)
							xhsOrderStock = 0;
						nufl.setXhsOrderStock(xhsOrderStock);
					} else if (custonerNmae.contains("ofashion")) {
						int ofashionOrderStock = nufl.getOfashionOrderStock();
						ofashionOrderStock--;
						if (ofashionOrderStock < 0)
							ofashionOrderStock = 0;
						nufl.setOfashionOrderStock(ofashionOrderStock);
					}
//					else if (custonerNmae.contains("微盟")) {
//						int weiMobOrderStock = nufl.getWeiMobOrderStock();
//						weiMobOrderStock--;
//						if (weiMobOrderStock < 0)
//							weiMobOrderStock = 0;
//
//						nufl.setWeiMobOrderStock(weiMobOrderStock);
//
//					} 
					else if (custonerNmae.contains("银泰")) {
						int yinTaiOrderStock = nufl.getYinTaiOrderStock();
						yinTaiOrderStock--;
						if (yinTaiOrderStock < 0)
							yinTaiOrderStock = 0;
						nufl.setYinTaiOrderStock(yinTaiOrderStock);
//						nufl.setYhdOrderStock(yinTaiOrderStock);

					}else if (custonerNmae.contains("奢品")) {
						int shepinOrderStock = nufl.getShepinOrderStock();
						shepinOrderStock--;
						if (shepinOrderStock < 0)
							shepinOrderStock = 0;
						nufl.setShepinOrderStock(shepinOrderStock);
					}else if (custonerNmae.contains("ofashion迷橙")) {
						int ofashionMcOrderStock = nufl.getOfashionMcOrderStock();
						ofashionMcOrderStock--;
						if (ofashionMcOrderStock < 0)
							ofashionMcOrderStock = 0;
						nufl.setOfashionMcOrderStock(ofashionMcOrderStock);
					}else if (custonerNmae.contains("云尚")) {
						int yunShangOrderStock = nufl.getYunshangOrderStock();
						yunShangOrderStock--;
						if (yunShangOrderStock < 0)
							yunShangOrderStock = 0;
						nufl.setYunshangOrderStock(yunShangOrderStock);
					}
					/*else if (custonerNmae.contains("苏宁")) {
						int suningOrderStock = nufl.getSuningOrderStock();
						suningOrderStock--;
						if (suningOrderStock < 0)
							suningOrderStock = 0;
						nufl.setSuningOrderStock(suningOrderStock);
					}*/
					/*else if(custonerNmae.contains("新寺库")){
						int sikunewOrderStock = nufl.getSikunewOrderStock();
						sikunewOrderStock--;
						if(sikunewOrderStock < 0)
							sikunewOrderStock = 0;
						nufl.setSikunewOrderStock(sikunewOrderStock);
					}*/
					/*else if(custonerNmae.contains("新拼多多")){
						int pddnewOrderStock = nufl.getPddnewOrderStock();
						pddnewOrderStock--;
						if(pddnewOrderStock < 0)
							pddnewOrderStock = 0;
						nufl.setPddnewOrderStock(pddnewOrderStock);
					}*/
				}
				nufl.setNowStockNum(num);
				nufl.setSku(sku);
				nufl.setType(type);
				platformStockUpdateDao.updateStockByNotNull(nufl); // 更新库存数
			}
		}
		// 处理完 更新最大记录到表
		platformStockUpdateDao.updateHistoryMaxId(manxHistoryId);

	}
	// 同步平台库存
	public  void autoSyncPlatformStock() {
		// 处理完历史表 查询当前库存数不一致的数据 做更新
		Map<String, String> searchNeedSync = new HashMap<String, String>();
		searchNeedSync.put("searchNeedSync", "yes");
		List<StockUpdate> needUpdateList = platformStockUpdateDao.selectStockUpdateByMap(searchNeedSync);
		for (StockUpdate s : needUpdateList) {
			int canSaleNum = s.getNowStockNum() - s.getOrderStockNum();

			int lastUpdateNum = s.getLastUpdateStockNum();

			// 现在库存数和上次更新的库存数不一致 更新库存到各个平台
			if (canSaleNum != lastUpdateNum) {
				if (canSaleNum < 0)
					canSaleNum = 0;// 更新的库存不能小于0
				
				kaolaPlatformStockUpdate.updateKaoLaStocku(s.getKaolaSku(),s.getSku(), canSaleNum, s.getType());
				kaolaPlatHaiTaoformStockUpdate.updateKaoLahtStocku(s.getKaolahtSku(), s.getSku(), canSaleNum, s.getType());
				siKuPlatFormStockUpdate.updateSikuStock(s.getSikuSku(),s.getSku(), canSaleNum, s.getType());
				zhenPinPlatformStockUpdate.updateZhenpinStock(s.getZhenpinSku(), s.getSku(), canSaleNum, s.getType());
				MeiLiHuiPlatFormStockUpdate.updateCanSaleProduct(s.getMlhSku(),s.getSku(), canSaleNum, s.getType());
				MeiLiHuinewPlatFormStockUpdate.updateCanSaleProduct(s.getMlhnewSku(),s.getSku(), canSaleNum, s.getType());
				MeiLiHuinewPlatFormStockUpdate.updateCanSaleProducts(s.getMlhnewsecSku(),s.getSku(), canSaleNum, s.getType());
				gouDongPlatFormStocuUpdateImpl.updateGouDongStock(s.getJdSku(),s.getSku(), canSaleNum, s.getType());
				xiaohongshuPlatFormStockupdate.updateCanSaleProduct(s.getXhsSku(), s.getSku(), canSaleNum, s.getType());
				yinTaiPlatFormStockUpdate.updateYinTaiStock(s.getSku(),s.getYinTaiSku(), canSaleNum, s.getType());
				ofMiChengPaltFormStockupdate.updateOFashionStock(s.getSku(),canSaleNum, s.getType());
				shePinFormStockUpdate.updateTmallStock(s.getShepinSku(),s.getSku(), canSaleNum, s.getType());
				pinduoduoPlatFormStockUpdate.updatePinDuoduoStock(s.getPddSku(), s.getSku(), canSaleNum, s.getType());
				pinduoduonewPlatFormStockUpdate.updatePinDuoduoStock(s.getPddnewSku(), s.getSku(), canSaleNum, s.getType());
				yunShangPlatFormStockUpdate.updateYunShangStock(s.getYunshangSku(),s.getSku(), canSaleNum, s.getType());
				tmallFormStockUpdate.updateTmallStock(s.getTmallSku(),s.getSku(), canSaleNum, s.getType());
				
//				//siKuNewPlatFormStockUpdate.updateSikuStock(s.getSikunewSku(),s.getSku(), canSaleNum, s.getType());
//				//shangPinPlatformStocuUpdate.updateShangpinStocku(s.getShangpinSku(),s.getSku(), canSaleNum, s.getType());
//				//suNingPlatFormStockUpdate.updateSuNingStock(s.getSku(),s.getSuningSku(), canSaleNum, s.getType());
//				//yhdPlatformStockUpdate.updateYhdStocku(s.getYhdSku(), s.getSku(), canSaleNum, s.getType());
				// 天猫 京东 分期 需要加海外库存
				Map<String, String> searchMap = new HashMap<String, String>();
				searchMap.put("sku", s.getSku());

				List<StockUpdate> list = platformStockUpdateDao.selectStockUpdateByMap(searchMap);
				int shAndHkNum = 0;// 国内和海外库存
				for (StockUpdate st : list) {
					int saleStock = st.getNowStockNum() - st.getOrderStockNum();
					if (saleStock < 0)
						saleStock = 0;
					shAndHkNum += saleStock;
				}
				
				
				ofashionPlatFormStockUpdate.updateOFashionStock(s.getOfashionsku(), s.getSku(), shAndHkNum, "sh");
//                tmallFormStockUpdate.updateTmallStock(s.getTmallSku(),s.getSku(), shAndHkNum, "sh");
//				//fenQiLePlatFormStocuUpdate.updateFenQiLeStock(s.getFqlSku(),s.getSku(), shAndHkNum, "sh");
//			    //higoFormStockUpdate.updateHigOStock(s.getHigoSku(), s.getSku(),shAndHkNum, "sh");
//			    //weiMobPlatFormStockUpdate.updateWeimobStock(s.getSku(),s.getWeimobsku(), shAndHkNum, "sh");		
//				//shePinFormStockUpdate.updateTmallStock(s.getShepinSku(),s.getSku(), shAndHkNum, "sh");
				s.setLastUpdateStockNum(s.getNowStockNum()- s.getOrderStockNum());
				platformStockUpdateDao.updateStockByNotNull(s); // 更新成功设置上次更新库存为当前可售数

			}

		}
	}   

	public String getOrSetLastUpdateTime(String lastTime) {
		String result = "";
		// 设置
		if (StringUtil.isNotBlank(lastTime)) {
			FileWriter fw = null;
			try {
				File file = new File(path + "last_update_time_not_delete.txt");
				fw = new FileWriter(file);
				fw.write(lastTime);
			} catch (Exception e) {
			} finally {
				if (fw != null) {
					try {
						fw.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		} else // 获取
		{
			RandomAccessFile accessFile = null;
			try {
				accessFile = new RandomAccessFile(path + "last_update_time_not_delete.txt", "rw");
				int length = (int) accessFile.length();
				byte[] c = new byte[length];
				accessFile.seek(0);
				accessFile.read(c, 0, length);
				result = new String(c);
			} catch (Exception e) {

			} finally {
				if (accessFile != null) {
					try {
						accessFile.close();
					} catch (IOException e) {
						// e.printStackTrace();
					}
				}
			}
		}
		return result;
	}

	@Override
	public void autoSyncOrderStock() {

		String startTime = getOrSetLastUpdateTime("");
		Date nowDate = new Date();
		String endTime = sdf.format(nowDate);
		if (StringUtil.isBlank(startTime)) {

			Date now_10 = new Date(nowDate.getTime() - 10 * 60 * 1000); // 10分钟前时间
			startTime = sdf.format(now_10);
		}
		int updateTotalNum = 0;

		

		updateTotalNum += xiaohongshuPlatFormStockupdate.atuoSyncOrder("sh");
		updateTotalNum += xiaohongshuPlatFormStockupdate.atuoSyncOrder("hk");

		updateTotalNum += siKuPlatFormStockUpdate.atuoSyncOrder("hk");
		updateTotalNum += siKuPlatFormStockUpdate.atuoSyncOrder("sh");	
		

		updateTotalNum += kaolaPlatformStockUpdate.atuoSyncOrder("hk");
		updateTotalNum += kaolaPlatformStockUpdate.atuoSyncOrder("sh");

		updateTotalNum += kaolaPlatHaiTaoformStockUpdate.atuoSyncOrder("hk");

		updateTotalNum += tmallFormStockUpdate.atuoSyncOrder();
		
		updateTotalNum += shePinFormStockUpdate.atuoSyncOrder();
		
		updateTotalNum += pinduoduoPlatFormStockUpdate.atuoSyncOrder();

		updateTotalNum += ofashionPlatFormStockUpdate.atuoSyncOrder();

		updateTotalNum += yinTaiPlatFormStockUpdate.atuoSyncOrder();

        updateTotalNum += zhenPinPlatformStockUpdate.atuoSyncOrder(startTime,endTime, "hk");
        updateTotalNum += zhenPinPlatformStockUpdate.atuoSyncOrder(startTime,endTime, "sh");

		updateTotalNum += ofMiChengPaltFormStockupdate.atuoSyncOrder();
		
		updateTotalNum += pinduoduonewPlatFormStockUpdate.atuoSyncOrder();
		
		updateTotalNum += yunShangPlatFormStockUpdate.atuoSyncOrder("sh");	
//		//updateTotalNum += fenQiLePlatFormStocuUpdate.atuoSyncOrder();
//		//updateTotalNum += siKuNewPlatFormStockUpdate.atuoSyncOrder("hk");
//		//updateTotalNum += pinduoduonewPlatFormStockUpdate.atuoSyncOrder();  //還沒有過訂單，沒測試
//	    // updateTotalNum += weiMobPlatFormStockUpdate.atuoSyncOrder();
//		// updateTotalNum += shangPinPlatformStocuUpdate.atuoSyncOrder(startTime, endTime, "hk");
//		// updateTotalNum += shangPinPlatformStocuUpdate.atuoSyncOrder(startTime, endTime, "sh");
//	    // updateTotalNum += higoFormStockUpdate.atuoSyncOrder();  
//		// updateTotalNum += suNingPlatFormStockUpdate.atuoSyncOrder();
		
		if (updateTotalNum > 0)// 有订单下来 同步订单总库存
		{
			platformStockUpdateDao.syncOrderStock();
		}
		getOrSetLastUpdateTime(endTime);

	}

	@Override
	public void updateStockUpdateProductNum(String idProduct, String oldSku) {
		Product p = productDao.getproduct(idProduct);

		List<Long> siteList = new ArrayList<Long>();
		String type = "sh";
		if (MiniUiUtil.hkSite.contains(p.getCurSiteId())) {
			type = "hk";
			// 获取可售库存所需站点信息
			for (String idSite : MiniUiUtil.hkSite) {
				siteList.add(Long.valueOf(idSite));
			}
		} else if (MiniUiUtil.shSite.contains(p.getCurSiteId())) {
			// 获取可售库存所需站点信息
			for (String idSite : MiniUiUtil.shSite) {
				siteList.add(Long.valueOf(idSite));
			}
		} else {
			return;// 只更新 海外和国内 101 102 103 104
		}
		String sku = p.getSku();
		if (null == sku || "".equals(sku) || StringUtil.isBlank(sku))
			return;
		int num = autoSyncDao.searchStockBySiteAndSku(siteList, sku);
		Map<String, String> searchMap = new HashMap<String, String>();
		searchMap.put("sku", sku);
		searchMap.put("type", type);
		List<StockUpdate> needUpdatePlatformList = platformStockUpdateDao
				.selectStockUpdateByMap(searchMap);
		for (StockUpdate nufl : needUpdatePlatformList) {
			nufl.setNowStockNum(num);
			nufl.setSku(sku);
			nufl.setType(type);
			platformStockUpdateDao.updateStockByNotNull(nufl); // 更新库存数
		}

		// 如果是sku变了，更新以前sku的库存
		if (StringUtil.isNotBlank(oldSku)) {
			num = autoSyncDao.searchStockBySiteAndSku(siteList, oldSku);
			searchMap.put("sku", oldSku);
			searchMap.put("type", type);
			needUpdatePlatformList = platformStockUpdateDao.selectStockUpdateByMap(searchMap);
			for (StockUpdate nufl : needUpdatePlatformList) {
				nufl.setNowStockNum(num);
				nufl.setSku(sku);
				nufl.setType(type);
				platformStockUpdateDao.updateStockByNotNull(nufl); // 更新库存数
			}
		}
	}

	@Override
	public MiniUiGrid searchStockUpdate(Map<String, String> searchMap) {
		QueryPage queryPage = new QueryPage(new Object());
		int count = platformStockUpdateDao.searchStockUpdateCnt(searchMap);

		MiniUiGrid gird = new MiniUiGrid();
		gird.setTotal(count);

		if (count > 0) {
			// int currPage,int pageSize
			String pageIndex = searchMap.get("pageIndex");
			String pageSize = searchMap.get("pageSize");
			queryPage.setCurrentPageForMiniUi(pageIndex);
			queryPage.setPageSizeString(pageSize);
			queryPage.setTotalItemForMiniUi(count); // 之前的当前页大于最大页返回最后页 现在不做处理.

			// 不要分页 查询所有
			if (!"yes".equalsIgnoreCase(searchMap.get("noStartRowAndEndRow"))) {
				searchMap.put("startRow", queryPage.getPageFristItem() + "");
				searchMap.put("endRow", queryPage.getPageSize() + "");
			}

			List<StockUpdate> list = platformStockUpdateDao
					.searchStockUpdate(searchMap);

			// 获取list集合中的用一个list集合接收
			List<String> sku = new ArrayList<String>();
			for (int i = 0; i < list.size(); i++) {
				String Sku = list.get(i).getSku();
				sku.add(Sku);

			}
			// 根据sku集合获取prodouct表中的型号材质颜色的集合(去重复)
			List<Product> products = productDao.showSkuinfo(sku);
			// 循环遍历list集合取出每一位的sku
			for (int i = 0; i < list.size(); i++) {
				String hsku = list.get(i).getSku();
				String color = "";// 定义一个接收颜色的变量
				String material = "";// 定义一个接收材质的变量
				String pType = "";// 定义一个接收型号的变量
				// 循环遍历products集合
				for (int j = 0; j < products.size(); j++) {
					// 取得里面的每一位的sku(嵌套的for循环，list循环一次下面执行全部)
					String psku = products.get(j).getSku();
					// 当produncts第j位的psku与list中的hsku相等时
					if (psku.equals(hsku)) {
						color = products.get(j).getColor();// 查询出produncts第j位的颜色
						material = products.get(j).getMaterial();// 查询出produncts第j位的材质
						pType = products.get(j).getType();// 查询出produncts第j位的型号
						break;// 结束此次循环（内循环）
					}
				}
				// 把查到的型号、材质、颜色放到list的第i位里面（为前台显示用）
				list.get(i).setColor(color);
				list.get(i).setMaterial(material);
				list.get(i).setpType(pType);
			}
			if (list != null && list.size() > 0) {
				gird.setData(list);
			}
		}
		return gird;
	}

	@Override
	public void updateStockUpdate(StockUpdate stockUpdate) {
		platformStockUpdateDao.updateStockByNotNull(stockUpdate); // 更新库存数
	}

	@Override
	public MiniUiGrid searchStockUpdateLog(Map<String, String> searchMap) {

		MiniUiGrid grid = new MiniUiGrid();

		List<StockUpdateLog> list = platformStockUpdateDao
				.selectStockUpdateLogByMap(searchMap);
		if (list != null && list.size() > 0) {
			grid.setData(list);
			grid.setTotal(list.size());
		} else {
			grid.setTotal(0);
		}
		return grid;
	}

	@Override
	public StockUpdate selectstock(StockUpdate stockupdate) {
		return platformStockUpdateDao.selectstock(stockupdate);
	}

	@Override
	public void updateStockUpdateorder(Map<String, String> searchMap) {
		platformStockUpdateDao.updateStockUpdate(searchMap);

	}

	@Override
	public void insertStockReserve(StockReserve stockreserve) {
		platformStockUpdateDao.insertStockReserve(stockreserve);

	}
}
