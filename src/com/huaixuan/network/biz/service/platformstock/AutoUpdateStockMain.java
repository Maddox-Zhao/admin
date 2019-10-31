
package com.huaixuan.network.biz.service.platformstock;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.PostConstruct;


import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.huaixuan.network.biz.dao.goods.CatAttrRelDao;

import com.huaixuan.network.biz.domain.goods.Attribute;
import com.huaixuan.network.biz.domain.goods.AttributeDTO;
import com.huaixuan.network.biz.domain.goods.AvailableStock;
import com.huaixuan.network.biz.domain.goods.Goods;
import com.huaixuan.network.biz.domain.goods.GoodsGallery;
import com.huaixuan.network.biz.domain.goods.GoodsInstance;
import com.huaixuan.network.biz.domain.hy.Product;
import com.huaixuan.network.biz.domain.shop.Brand;
import com.huaixuan.network.biz.enums.EnumGoodsStatus;
import com.huaixuan.network.biz.enums.hy.EnumBrandType;
import com.huaixuan.network.biz.exception.ManagerException;

import com.huaixuan.network.biz.service.goods.AttributeManager;

import com.huaixuan.network.biz.service.goods.GoodsGalleryManager;
import com.huaixuan.network.biz.service.goods.GoodsInstanceManager;
import com.huaixuan.network.biz.service.goods.GoodsManager;

import com.huaixuan.network.biz.service.hy.ProductService;

import com.huaixuan.network.biz.service.provider.XiYouPlatFormStockUpdate;
import com.huaixuan.network.biz.service.shop.BrandService;
import com.huaixuan.network.biz.service.stock.AvailableStockService;

import com.hundsun.network.melody.common.util.StringUtil;

/**
 * @author Mr_Yang 2016-5-13 下午04:56:10 自动同步库存入口 有2个 一个同步历史表库存到库存表<br/>
 *         在同步库存信息到各个平台<br/>
 *         2 同步订单信息
 **/

@Service
public class AutoUpdateStockMain {
	@Autowired
	private StockUpdateService stockUpdateService;

    @Autowired
    private BrandService  brandService;
    
    @Autowired
    private CatAttrRelDao catAttrRelDao;
    
    @Autowired
    private GoodsManager goodsManager;
    

    @Autowired
    private GoodsGalleryManager goodsGalleryManager;

	@Autowired
	private ProductService productService;
	
	@Autowired
	private AttributeManager attributeManager;
	
	@Autowired
    private GoodsInstanceManager goodsInstanceManager;
	
	@Autowired
    private AvailableStockService availableStockService;

	@Autowired
	private XiYouPlatFormStockUpdate xiYouPlatFormStockUpdate;
	
	private @Value("${system.devMode}")
	boolean devModel;
	
	private @Value("${file.upload.dir}")
	String upload;
	
	
	
	protected Log log = LogFactory.getLog(this.getClass());

	

	@PostConstruct
	public void startDealStock() {

		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					// 没有在全量更新当前库存以及不是开发者模式
					if ("true".equals(stockUpdateService.getsyncNowStockStatus()) && !devModel) {
						//同步西有库存(银泰的已禁用掉)
						//xiYouPlatFormStockUpdate.getChangeStock();
						
						
						// 处理历史表数据
//						 stockUpdateService.autoSyncLocationStock();

						// 同步平台库存
//						 stockUpdateService.autoSyncPlatformStock();
					   
						
					}
					try {
						Thread.sleep(1000 * 60 * 2); // 每隔2分钟同步一次

					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

			}

		});
		thread.start();
	}

	@PostConstruct
	public void startDealOrder() {
		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					if ("true".equals(stockUpdateService.getsyncNowStockStatus()) && !devModel) {
						// 同步订单信息到本地(更新库存表 订单库存数)
//						stockUpdateService.autoSyncOrderStock(); 
						
						//同步西有商品的订单详情(银泰的已禁用)
						//xiYouPlatFormStockUpdate.selectProvideOrderDetail();
					}
					try {
						Thread.sleep(1000 * 60 * 5); // 每隔5分钟扫描一次

					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

			}
		});
		thread.start();
	}
	
	//product产品没有goods_id的在emall_goods中生成新的数据，已有我填充product的goods_id 字段
//	@PostConstruct
	public void doClientPublish() throws Exception {

		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					Date date=new Date();
						SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						String time=format.format(date);  //系统当前时间
						String startTime = time.substring(0, 10)+" 20:00:00";            //系统可查询开始时间
						String endTime = time.substring(0, 10)+" 21:00:00";               //系统可查询借宿时间   		
						Long longStartTime = 0L;
						Long longTime = 0L;
						Long longEndTime = 0L;
						try {
							longStartTime = format.parse(startTime).getTime();
							longTime = format.parse(time).getTime();            //系统可执行开始时间戳
							longEndTime = format.parse(endTime).getTime();      //系统可执行结束时间戳
						} catch (ParseException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}  //系统当前时间的时间戳
						
						
				             
					    if(longTime>longStartTime && longTime<longEndTime){
//					    	System.out.println("进入方法+++++++++++");
						
						Map parMap = new HashMap();
//				    	parMap.put("startRow", 0);
//				    	parMap.put("endRow", 200);
//				    	System.out.println("111111111111111111");
				    	Map<String,String> enumBrandTypeMap = EnumBrandType.toMap();
				    	List<Brand> list = brandService.getBrands();
				    	for(Brand b : list)
				    	{
				    		enumBrandTypeMap.put(b.getId()+"", b.getBrandName());
				    	}
//				    	parMap.put("type", "4595405");
//				    	parMap.put("material", "53084");
//				    	parMap.put("color", "999");
				    	
//				    	parMap.put("idBrand", "4");
//				    	parMap.put("idLocation", "102");
//				    	parMap.put("idSeries", "3");
				    	
				    	List<Product> productList = productService.getProductClientToBrowser(parMap);
				    	
				    	for(Product product:productList){
				    		
//				    		if(product.getIdStatus().longValue()!=1 &&
//				    				product.getIdStatus().longValue()!=4){
//				    			continue;
//				    		}
				    		if(product.getIdStatus().longValue()==6){
				    			continue;
				    		}
				    		
				    		Map pramas = new HashMap();
				    		pramas.put("type", product.getType());
				    		pramas.put("material", product.getMaterial());
				    		pramas.put("color", product.getColor());
//				    		pramas.put("idBrand", product.getIdBrand());
//				    		pramas.put("idSeries", product.getIdSeries());
//				    		
				    		Goods exsitGoods = goodsManager.getClientGoodsExist(pramas);
				    		
				    		//如果已经存在就查找instance
				    		if(exsitGoods != null){
				    			List<GoodsGallery>  listGoodsGallery = goodsGalleryManager.getGoodsGallerysByGoodsId(exsitGoods.getId());
				    			boolean updateLastTime = false;//是否排序到前面
				    			if(listGoodsGallery != null && listGoodsGallery.size() >=2) updateLastTime=true; //有多张照片的排到前面
				    			
				    			//存在，商品库存先增加1
				    			if(product.getIdStatus().longValue()==1){
				    				goodsManager.updateGoodsGoodsNumberById(exsitGoods.getId(), 1L,updateLastTime);
				    			}
				    			//存在，如果是香港库存，香港库存增加1
				    			//20150115增加帝国中心
				    			if(product.getIdStatus().longValue()==1 && 
				    					(product.getIdLocation().longValue()==102 || product.getIdLocation().longValue()==103 || product.getIdLocation().longValue()==104)){
				    				goodsManager.updateGoodsHKGoodsNumberById(exsitGoods.getId(), 1L,updateLastTime);
				    			}
				    			//存在状态为已售，商品已售库存增加1
				    			if(product.getIdStatus().longValue()==4){
				    				goodsManager.updateSaleNumberById(exsitGoods.getId(), 1L);
				    			}
				    			try {
									checkInstanceExsit(null,product,pramas,exsitGoods);
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
				    			//更新最新欧洲价和尚上价和shangshang价和大陆价
				    			if(product.getHkhxPrice().doubleValue()>0 && product.getHkhxPrice().doubleValue()>exsitGoods.getHkhxPrice()){
				    				exsitGoods.setHkhxPrice(product.getHkhxPrice());
				    			}
				    			if(product.getHxPrice().doubleValue()>0 && product.getHxPrice().doubleValue()>exsitGoods.getGoodsPrice()){
				    				exsitGoods.setGoodsPrice(product.getHxPrice());
				    			}
				    			if(product.getEuPrice().doubleValue()>0){
				    				exsitGoods.setEuPrice(product.getEuPrice());
				    			}
				    			if(product.getCnPrice().doubleValue()>0){
				    				exsitGoods.setMarketPrice(product.getCnPrice());
				    			}
				    			if(product.getHkPrice().doubleValue()>0){
				    				exsitGoods.setHkPrice(product.getHkPrice());
				    			}
				    			goodsManager.editGoods(exsitGoods);
				    			continue;
				    		}
				    		
				    		Goods newgoods = new Goods();
				    		
				    		newgoods.setBrandId(product.getIdBrand());
				    		
				    		//倒过来数据批发平台专用类目
				    		if(StringUtil.isBlank(product.getSize())){
				    			newgoods.setCatCode("287");
				    		}else{
				    			newgoods.setCatCode("286");
				    		}
				    		if(product.getIdStatus().longValue()==1){
				    			newgoods.setGoodsNumber(1);
				    		}
				    		if(product.getIdStatus().longValue()==1 && product.getIdLocation().longValue()==102){
				    			newgoods.setHkGoodsNumber(1);
							}
				    		if(product.getIdStatus().longValue()==4){
				    			newgoods.setSaleNumber(1);
				    		}
				    		newgoods.setProductId(product.getIdProduct());
				    		newgoods.setColor(product.getColor());
				    		newgoods.setSiteId(product.getIdLocation());
				    		newgoods.setType(product.getType());
				    		newgoods.setTitle(enumBrandTypeMap.get(product.getIdBrand().toString())
				    				+" 型号："+product.getType()
				    				+" "+product.getMaterial()
				    				+" "+product.getColor());
				    		newgoods.setMaterial(product.getMaterial());
				    		newgoods.setTargetCustomers(product.getTargetCustomers());
				    		newgoods.setIsAgent("n");
				    		//goodsSn 商品type加上2位随机数
				    		Random rd1 = new Random();
				    		int ronnum = 10 + rd1.nextInt(90);
				    		newgoods.setGoodsSn(product.getType()+ronnum);
				    		newgoods.setGoodsUnit("件");
				    		newgoods.setIsWholesale("n");
				    		newgoods.setGoodsWeight(newgoods.getGoodsWeight()!=null?newgoods.getGoodsWeight():0.000);
				            //加入商品货号 zhangwy
				            if(newgoods.getGoodsItem()!=null){
				            	newgoods.setGoodsItem(newgoods.getGoodsItem().trim());
				            }
				            
				            //设置一些产品需要的属性
				            newgoods.setSize(product.getSize());
				            newgoods.setCost(product.getCost());
				            newgoods.setIdCostCurrency(product.getIdCostCurrency());
				            newgoods.setHkhxPrice(product.getHkhxPrice());
				    		newgoods.setMarketPrice(product.getCnPrice());
				    		newgoods.setGoodsPrice(product.getHxPrice());
				    		newgoods.setHkPrice(product.getHkPrice());
				    		newgoods.setEuPrice(product.getEuPrice());
				    		newgoods.setIdSeries(product.getIdSeries());
				            
				            
				    		
				    		//图片，先从网络上取图片资源到本地，再上传，压缩，
				    		//如果本地资源路径可以找到图片则不用这么麻烦
//				    		String fileUrl = "http://www.shangshangsp.com:88/upload/GUCCI_211137_FCIER_9643.jpg";   
				    		String fileUrl = "http://116.231.158.188:9090"+StringUtils.replace(
				    				product.getPicture(),"view.php?filename=","");
							  /*photoUrl.substring(photoUrl.lastIndexOf("/")的方法将返回最后一个符号为  
							  * ‘/’后photoUrl变量中的所有字符，包裹此自身符号*/  
				    		String fileName = fileUrl.substring(fileUrl.lastIndexOf("/"));   
				    		String filePath = upload+"/goodsOri"; 
				    		String savePath = filePath+fileName;
				    		
				    		File dirPath = new File(filePath);

				    		// 如果没有，建立目录
				    		if (!dirPath.exists()) {
				    			dirPath.mkdirs();
				    		}
				    		
				    		//如果文件已经存在就不上传了
				    		File exsitFile = new File(savePath);
				    		if (!exsitFile.exists()) {
					    		try {
						  		  	URL url = new URL(fileUrl);/*将网络资源地址传给,即赋值给url*/  
						  		  /*此为联系获得网络资源的固定格式用法，以便后面的in变量获得url截取网络资源的输入流*/  
						  		  	HttpURLConnection connection = (HttpURLConnection)url.openConnection();   
						  		  	DataInputStream in = new DataInputStream(connection.getInputStream());   
						  		  /*此处也可用BufferedInputStream与BufferedOutputStream*/  
						  		  	DataOutputStream out = new DataOutputStream(new FileOutputStream(savePath));   
						  		  /*将参数savePath，即将截取的图片的存储在本地地址赋值给out输出流所指定的地址*/  
						  		  	byte[] buffer = new byte[4096];   
						  		  	int count = 0;   
						  		  	while ((count = in.read(buffer)) > 0)/*将输入流以字节的形式读取并写入buffer中*/  
						  		  	{   
						  			  out.write(buffer, 0, count);   
						  		  	}   
						  		  	out.close();/*后面三行为关闭输入输出流以及网络资源的固定格式*/  
						  		  	in.close();   
						  		  	connection.disconnect();   /*网络资源截取并存储本地成功返回true*/  
							  	}   
							  	catch (Exception e)   
							  	{
							  		//System.out.println(e + fileUrl + savePath);   
							  	}
				    		}
				    		
						  	File file = new File(savePath);
				        	
						  	try {
								checkAttr(product,null,newgoods);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
				    		//TODO
				    		newgoods.setGoodsDesc(newgoods.getTitle());
				    		//TODO
				    		newgoods.setGoodsStatus(EnumGoodsStatus.ON_DEPOT.getKey());
				    		
				    		
				    		newgoods.setSalesProPrice(0.00);
				    		newgoods.setAgentPrice(0.00);
				    		
				           
				            try {
				                boolean flag = goodsManager.newClientToBrowser(newgoods, file,savePath);
				                if (flag) {
//				                    model.addAttribute("message", "publishsuccess");
				                } else {
//				                    model.addAttribute("errorMessage", "publishfail");
				                    continue;
				                }
				            } catch (ManagerException ex) {
//				                model.addAttribute("errorMessage", "publishfail");
				                continue;
				            }
				            
				    	}

					}
					try {
						Thread.sleep(1000 * 60 * 30); // 每隔30分钟扫描一次
					} catch (InterruptedException e) {
						e.printStackTrace();
						log.error(e.getMessage());
					}
				}

			}
		});
		thread.start();
    	
    	
    }
	
	@SuppressWarnings("unchecked")
	private void checkInstanceExsit(Model model,Product product,Map pramas,Goods exsitGoods) throws Exception{
    	//TODO
		pramas.put("size", product.getSize());
		GoodsInstance goodsInstance = goodsInstanceManager.getClientInstance(pramas);
		//相应属性值的产品已经存在，在原来的基础上增加库存
		if(goodsInstance!=null){
			//状态为可售增加库存
			if(product.getIdStatus().longValue()==1){
				goodsInstanceManager.updateGoodsInstanceExistNumById(
						goodsInstance.getId(),1L);
			}
			
			//存在，如果是香港库存，香港库存增加1
			if(product.getIdStatus().longValue()==1 
					&& (product.getIdLocation().longValue()==102 || product.getIdLocation().longValue()==103)){
				goodsInstanceManager.updateGoodsInstanceHKExistNumById(goodsInstance.getId(),1L);
			}
			
			//判断仓库位置在哪里
			Map<String,Object> aspra = new HashMap<String,Object>();
			aspra.put("siteId", product.getIdLocation());
			aspra.put("goodsInstanceId", goodsInstance.getId());
			AvailableStock availableStock = availableStockService.getAvailableStockByPramas(aspra);
			//如果仓库已经存在，在这个仓库上增加库存
			if(availableStock!=null){
				if(product.getIdStatus().longValue()==1){
					aspra.put("goodsNumber", 1);
					availableStockService.updateAvaiStoEsNumByPramas(aspra);
				}
			}else{
				//如果仓库不存在，在这个仓库上增加产品仓库
				AvailableStock availableStockNew = new AvailableStock();
				availableStockNew.setGoodsId(goodsInstance.getGoodsId());
				availableStockNew.setGoodsInstanceId(goodsInstance.getId());
				if(product.getIdStatus().longValue()==1){
					availableStockNew.setGoodsNumber(1L);
				}else{
					availableStockNew.setGoodsNumber(0L);
				}
				availableStockNew.setSiteId(product.getIdLocation());
				availableStockService.addhxAvaiStoByPramas(availableStockNew);
			}
			
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("idProduct", product.getIdProduct());
			map.put("goodsId", exsitGoods.getId());
			map.put("instanceId", goodsInstance.getId());
			productService.updateProductToBrowser(map);
			//TODO  状态已售增加销售量
		}else{
			
			exsitGoods.setSiteId(product.getIdLocation());
			exsitGoods.setSize(product.getSize());
			exsitGoods.setCost(product.getCost());
			exsitGoods.setIdCostCurrency(product.getIdCostCurrency());
			exsitGoods.setHkhxPrice(product.getHkhxPrice());
			exsitGoods.setMarketPrice(product.getCnPrice());
			exsitGoods.setGoodsPrice(product.getHxPrice());
			exsitGoods.setHkPrice(product.getHkPrice());
			exsitGoods.setEuPrice(product.getEuPrice());
			exsitGoods.setIdSeries(product.getIdSeries());
			exsitGoods.setProductId(product.getIdProduct());
			
			//设置库存
			//状态为可售增加库存
			if(product.getIdStatus().longValue()==1){
				exsitGoods.setGoodsNumber(1);
			}else{
				exsitGoods.setGoodsNumber(0);
			}
			
			//TODO  状态已售增加销售量
			
			//相应属性值的产品不存在，则要增加新的产品
			//先增加新的属性值,更新商品、增加产品
			this.checkAttr( product, model, exsitGoods);
			goodsManager.editGoodsAddGoodsInstance(exsitGoods);
		}
    }
	
	private void checkAttr(Product product,Model model,Goods newgoods) throws Exception{
    	//有size属性的商品
	  	if(StringUtil.isNotBlank(product.getSize())){
	  		List<AttributeDTO> attributeList = catAttrRelDao.getAttributeDTOByCatCode(newgoods.getCatCode());
	  		
            //属性值集合 属性集描述
            StringBuffer attrValue = new StringBuffer();
            StringBuffer attrDesc = new StringBuffer();
            StringBuffer attrChoose = new StringBuffer();
            for (AttributeDTO attributeDTO : attributeList) {
            	if("20111108000001".equalsIgnoreCase(attributeDTO.getAttrCode())){
	                if ("checkbox".equalsIgnoreCase(attributeDTO.getInputType().toLowerCase())) {
	                    String paramValue = product.getSize();
	                    String[] attrAll = attributeDTO.getValues();
	                    //判断导入的尺码是否已经存在
	                    boolean exsitTag = false;
	                    for(int i = 0; i < attrAll.length; i++){
	                    	if(attrAll[i].equalsIgnoreCase(paramValue)){
	                    		exsitTag = true;
	                    		break;
	                    	}
	                    }
	                    //如果不存在则要更新属性，增加新的尺码
	                    if(!exsitTag){
	                    	Attribute attribute = attributeManager.getAttribute(attributeDTO.getId());
	                    	if(StringUtil.isBlank(attribute.getAttrValues())){
	                    		attribute.setAttrValues(paramValue);
	                    	}else{
	                    		attribute.setAttrValues(StringUtil.trim
	                    				(attribute.getAttrValues())+"\r\n"+paramValue);
	                    	}
	                        if (attributeManager.editAttribute(attribute)) {
//	                            model.addAttribute("message", "editsuccess");
	                        }else{
//	                            model.addAttribute("message", "editfail");
	                        }
	                    }
	                    attrChoose.append(attributeDTO.getAttrCode());
	                    attrChoose.append("*");
	                    attrChoose.append(attributeDTO.getAttrName());
	                    attrChoose.append("*");
	                    attrChoose.append(paramValue);
	                    attrChoose.append("+");
	                } 
            	}
            }
            newgoods.setAttrValue(attrValue.toString());
            newgoods.setAttrDesc(attrDesc.toString());
            newgoods.setChoose(attrChoose.toString());
	  	}
    }
}
