package com.huaixuan.network.biz.service.autosyn.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.huaixuan.network.biz.dao.autosyn.AutoSyncDao;
import com.huaixuan.network.biz.dao.product.ProductDao;
import com.huaixuan.network.biz.domain.autosyn.PlatformData;
import com.huaixuan.network.biz.domain.product.Product;
import com.huaixuan.network.biz.service.autosyn.AutoSyncPlatformStockManger;
import com.huaixuan.network.biz.service.autosyn.AutoSyncShangPingManager;
import com.huaixuan.network.biz.service.autosyn.KaoLaSyncManager;
import com.huaixuan.network.biz.service.autosyn.SiKuSyncManager;
import com.huaixuan.network.biz.service.autosyn.ZhenPinSyncManager;
import com.huaixuan.network.common.util.miniui.MiniUiUtil;
import com.hundsun.common.lang.StringUtil;



/**
 * @author Mr_Yang   2016-3-28 上午11:47:55
 * 自动同步平台库存 弃用
 **/
 

public class AutoSyncPlatformStockMangerImpl implements AutoSyncPlatformStockManger
{
	@Autowired
	private AutoSyncDao autoSyncDao;
	
	@Autowired
	private KaoLaSyncManager kaoLaSyncManager;
	
	@Autowired
	private ZhenPinSyncManager zhenPinSyncManager;
	
	@Autowired
	private AutoSyncShangPingManager autoSyncShangPingManager;
	
	@Autowired
	private SiKuSyncManager siKuSyncManager;
	
	@Autowired
	private ProductDao productDao;
	
	private @Value("${system.devMode}") boolean devModel;
	
	private static Map<String,String> updateErrorMap = new HashMap<String, String>(); //更新失败 记录在Map
	

	/**
	 * spring 自动启动线程 同步各个平台库存
	 * 暂时弃用  用最新的 通过历史表同步库存
	 */
	@Override
	public void autoSyncPlatformStock()
	{
		Thread thread = new Thread(new Runnable(){

			@Override
			public void run()
			{
				while(true)
				{
					if(devModel) break;
					
					//先设置寺库sku的对应关系
					siKuSyncManager.setSiku2OurSkuMatch("sh");
					siKuSyncManager.setSiku2OurSkuMatch("hk");
					
					Map<String,String> searchMap = new HashMap<String, String>();

					//查询需要处理的数据
					List<PlatformData> list =  autoSyncDao.selectNeedUpdateStockProduct(searchMap);
					String hkSite =  "";
					for(String s : MiniUiUtil.hkSite)
					{
						hkSite += s + ",";
					}
					hkSite += "-1";
					String shSite =  "";
					for(String s : MiniUiUtil.shSite)
					{
						shSite += s + ",";
					}
					shSite += "-1";
					
					for(PlatformData platformData : list)
					{
						String type = platformData.getType();
						
						Product product = productDao.getproduct(platformData.getIdProduct());
						String idLocation = product.getCurSiteId();
						if("运输在途".equals(product.getStatus()))
						{
							idLocation = product.getIdLastLocation();
						}
						if(MiniUiUtil.hkSite.contains(idLocation))
						{
							type = "hk";
						}
						if(MiniUiUtil.shSite.contains(idLocation))
						{
							type = "sh";
						}
						
						String ourSku  = platformData.getSku();
						String idProduct = platformData.getIdProduct();
						Long id = platformData.getId();
						
						//更新为已处理
						searchMap.put("id", id+"");
						searchMap.put("type", type);
					 
						boolean falg1 = false;
						boolean falg2 = false;
						boolean falg3 = false;
						boolean falg4 = true;
						//在香港或者上海更新的库存之列
						if(StringUtil.isNotBlank(type))
						{
							searchMap.put("sku", ourSku);
							int nowNum = 0;
							 
							if("sh".equals(type))
							{
								searchMap.put("location", shSite);
								nowNum = autoSyncDao.getStockByOurSku(searchMap);
								//考拉
								//falg1 = kaoLaSyncManager.updateStockByOurSkuAndPlatFormSku(type, platformData.getSkuKaolaSh(), ourSku, nowNum,idProduct);
								
								//珍品 通过我们的sku来更新
								//falg2 = zhenPinSyncManager.updateStockByOurSkuAndPlatFormSku(type, ourSku, ourSku, nowNum);
								
								//尚品
								//falg3 = autoSyncShangPingManager.updateStockByOurSkuAndPlatFormSku(type, platformData.getSkuShangpinSh(), ourSku, nowNum);
								
								
							}
							else if("hk".equals(type))
							{
								searchMap.put("location", hkSite);
								nowNum = autoSyncDao.getStockByOurSku(searchMap);
								
								//考拉
								//falg1 = kaoLaSyncManager.updateStockByOurSkuAndPlatFormSku(type, platformData.getSkuKaolaHk(), ourSku, nowNum,idProduct);
								
								//珍品 通过我们的sku来更新
								//falg2 = zhenPinSyncManager.updateStockByOurSkuAndPlatFormSku(type, ourSku, ourSku, nowNum);
								
								//尚品
								//falg3 = autoSyncShangPingManager.updateStockByOurSkuAndPlatFormSku(type, platformData.getSkuShangpinHk(), ourSku, nowNum);
								
								//寺库
								//falg4 = siKuSyncManager.updateStockByOurSkuAndPlatFormSku(type, "", ourSku, nowNum, idProduct);
								
							}
							
							//更新一次后休息0.5秒  避免平台更新过快
							try
							{
								Thread.sleep(500);
							} 
							catch (InterruptedException e1)
							{
								e1.printStackTrace();
							}
						
						}
						else
						{
							//不属于香港或者上海
							falg1 = true;
							falg2 = true;
							falg3 = true;
							falg4 = true;
						}
						falg1 = true;
						falg2 = true;
						falg3 = true;
 
						
						//如果都更新成功，更新数据 为已处理,防止服务端做更新 不能发送数据 这次更新就没更上去
						if(falg1 && falg2 && falg3 && falg4)
						{
							autoSyncDao.updateNeedUpdateStockProduct(searchMap);
						}

					}
					
					
					try
					{
						Thread.sleep(1000*60);  //每隔60秒扫描一次
					}
					catch (InterruptedException e)
					{
						e.printStackTrace();
					}
				}
				
				
			}
			
			
		});
		thread.start();
	}

}
 
