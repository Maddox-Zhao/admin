package com.huaixuan.network.biz.service.product.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.huaixuan.network.biz.dao.autosyn.AutoSyncDao;
import com.huaixuan.network.biz.dao.base.MiniUiBaseDataDao;
import com.huaixuan.network.biz.dao.customer.CustomerDao;
import com.huaixuan.network.biz.dao.goods.GoodsInstanceDao;
import com.huaixuan.network.biz.dao.order.OrderDao;
import com.huaixuan.network.biz.dao.product.ProductDao;
import com.huaixuan.network.biz.dao.product.ProductStockDao;
import com.huaixuan.network.biz.dao.purchase.PurchaseOrderDao;
import com.huaixuan.network.biz.dao.shop.BrandDao;
import com.huaixuan.network.biz.domain.autosyn.PlatformData;
import com.huaixuan.network.biz.domain.base.MiniUiBase;
import com.huaixuan.network.biz.domain.customer.Customer;
import com.huaixuan.network.biz.domain.goods.GoodsInstance;
import com.huaixuan.network.biz.domain.order.Orderdetails;
import com.huaixuan.network.biz.domain.platformstock.PlatformSku2LocationSku;
import com.huaixuan.network.biz.domain.product.GoodsInformation;
import com.huaixuan.network.biz.domain.product.Product;
import com.huaixuan.network.biz.domain.product.ProductHistory;
import com.huaixuan.network.biz.domain.product.ProductSuoKu;
import com.huaixuan.network.biz.domain.product.ProductSuoKuProduct;
import com.huaixuan.network.biz.domain.product.ProductUpdateLog;
import com.huaixuan.network.biz.domain.product.Transfer;
import com.huaixuan.network.biz.domain.purchase.Purchase;
import com.huaixuan.network.biz.domain.shop.Brand;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.service.product.ProductService;
import com.huaixuan.network.common.util.miniui.MiniUiGrid;
import com.huaixuan.network.common.util.miniui.MiniUiUtil;
import com.hundsun.common.lang.StringUtil;



/**
 * @author Mr_Yang   2015-11-20  01:27:37
 **/
@Service("clientProductService")
public class ProductServiceImpl implements ProductService
{
	
	@Autowired
	private ProductDao productDao;
	
	@Autowired
	private OrderDao orderDao;
	
	@Autowired
	private MiniUiBaseDataDao  miniBaseDataDao;
	
	
	@Autowired
	private ProductStockDao productStockDao;
	
	
	@Autowired
	private GoodsInstanceDao goodsInstanceDao;
	
	
	@Autowired
	private AutoSyncDao autoSyncDao;
	
	@Autowired
	private PurchaseOrderDao purchaseOrderDao;
	
	@Autowired
	private  BrandDao brandDao;
	
	@Autowired
	private CustomerDao customerDao;
	
	@Override
	public MiniUiGrid getProductList(Map<String,String> searchMap)
	{
		//customerName 可以是 客户名字或者ID
		if(StringUtil.isNotBlank(searchMap.get("customerName")))
		{
			try
			{
				Integer idCustomer = Integer.valueOf(searchMap.get("customerName"));
				searchMap.put("idCustomer", idCustomer+"");
				searchMap.remove("customerName");
			}
			catch (Exception e)
			{

			}
			
		}
		QueryPage queryPage = new QueryPage(new Object());
		int count = productDao.getProductListCount(searchMap); // 
		
		MiniUiGrid gird = new MiniUiGrid();
		gird.setTotal(count);
		
		if(count >0)
		{
			String userName = searchMap.get("userName");
			//int currPage,int pageSize
			String pageIndex = searchMap.get("pageIndex");
			String pageSize = searchMap.get("pageSize");
			queryPage.setCurrentPageForMiniUi(pageIndex);
			queryPage.setPageSizeString(pageSize);
			queryPage.setTotalItemForMiniUi(count); //之前的当前页大于最大页返回最后页  现在不做处理
			
			//不要分页 查询所有
			if(!"yes".equalsIgnoreCase(searchMap.get("noStartRowAndEndRow")))
			{
				searchMap.put("startRow", queryPage.getPageFristItem()+"");
				searchMap.put("endRow", queryPage.getPageSize()+"");
			}
			List<Product> list = productDao.getProductList(searchMap);
			if(list != null && list.size() >0)
			{
				if(StringUtil.isNotBlank(userName))
				{
					boolean flag = orderDao.canSearchAllOrder(userName);//client_role是否9,是9進入
					if(flag)
					{
						for(Product p  : list)
						{      //如客戶經理不為空，并且當前登錄者不是這個客戶的客戶經理，則讓當前登陸者，對客戶名和售價不可見
							  //若客戶經理為空，不經過if判斷，會出現，所有客戶經理和售價對登陸者可見
							if(p.getManagerUserName() != null && !p.getManagerUserName().equals(userName))      
							{
								if(StringUtil.isNotBlank(p.getCustomerName())){
									p.setCustomerName("***");
									
									if(StringUtil.isNotBlank(p.getSalePrice()+"")){
										p.setSalePrice(1.1);
									}
								}
								
								
							}
						}
					}
				}
				gird.setData(list);
			}
		}
		return gird;
	}
	/**
	 * 
	 * @Description:商品销售信息
	 * @author 
	 * @date 2018-8-1
	 */
	@Override
	public MiniUiGrid getGoodsSaleInfoList(Map<String,String> searchMap){
		QueryPage queryPage = new QueryPage(new Object());
		int count = productDao.getGoodsSaleInfoListCount(searchMap); // 
		MiniUiGrid gird = new MiniUiGrid();
		gird.setTotal(count);
		if(count >0){
			String userName = searchMap.get("userName");
			//int currPage,int pageSize
			String pageIndex = searchMap.get("pageIndex");
			String pageSize = searchMap.get("pageSize");
			queryPage.setCurrentPageForMiniUi(pageIndex);
			queryPage.setPageSizeString(pageSize);
			queryPage.setTotalItemForMiniUi(count); //之前的当前页大于最大页返回最后页  现在不做处理
			//不要分页 查询所有
			if(!"yes".equalsIgnoreCase(searchMap.get("noStartRowAndEndRow"))){
				searchMap.put("startRow", queryPage.getPageFristItem()+"");
				searchMap.put("endRow", queryPage.getPageSize()+"");
			}
			List<Product> list = productDao.getGoodsSaleInfoList(searchMap);
			if(list != null && list.size() >0){
				gird.setData(list);
			}
		}
		return gird;		
	}
	//根據type查詢
	@Override
	 public List<Product> selectProductByType(Map<String, String> map){
		
		   return productDao.selectProductByType(map);
	   }
	
	
	@Override
	public int getProductListCount(Map<String, String> searchMap)
	{
		 return productDao.getProductListCount(searchMap); 
	}

	@Override
	public Product getproduct(String idProduct) {
		Product p =	 productDao.getproduct(idProduct);
		MiniUiUtil.setProductPicture(p);
		return p;
	}

	@Override
	public List<Product> getProductsByIdList(List<String> idsList)
	{
		return productDao.getProductListByIdList(idsList);
	}
	
	
	public int updateProductsByIdList(Product product)
	{
		
		return productDao.updateProductListByIdList(product);
	}
	public List<Product> showShoppingCar(String userName)
	{
		return productDao.showShoppingCar(userName);
	}
	
	
	/* 
	 *获取购物车傳的searchmap
	 */
	@Override
	public List<Product> showShoppingCarMap(Map<String, String> searchmap) {
		
		return productDao.showShoppingCarMap(searchmap);
	}
	
	
	//用sku添加到购物车，获取购物车
	@Override
	public List<Product> selectCarProdcutStatus(String username) {
		
		return productDao.selectShoppingCarStatus(username);
	}
	//根据sku和Status查询符合条件的所有商品
	
   public List<Product> selectProductAllBySku(Map<String, String> map){
	   return productDao.selectProductAllBySkuStatus(map);
   }
	@Override
	public int insertHistory(Map<String, String> map)
	{
		return productDao.insertHistory(map);
	}

	@Override
	public int updateLifeCyle(Map<String, String> map)
	{
		return productDao.updateLifeCyle(map);
	}

	@Override
	public void updateLifeCyleByNotNull(Product product)
	{
		 productDao.updateProductLifecyle(product);
	}
	/**
	 * 开启事务  要么全部成功 要么全部失败  开单
	 */
	@Override
	@Transactional
	public boolean shoppingCar2SettleAccount(Map<String, String> requestMap)
	{
		
		String idProducts = requestMap.get("idProducts"); 
		if("".equals(idProducts) || null == idProducts)
		{
			return false;
		}
		Map<String,Double> productsMap = new HashMap<String, Double>();//保存idProduct 和售价
		String[] arr = idProducts.split(";");  //idProduct:price;idProduct:price
		for(String s : arr)
		{
			if(!"".equals(s))
			{
				String[] ids = s.split(":");
				String idProduct = ids[0];
				Double price = Double.valueOf(ids[1]);
				productsMap.put(idProduct, price);
			}
			
		}
		Map<String,String> customerOrderMap = new HashMap<String, String>();
		List<MiniUiBase> list = miniBaseDataDao.querySiteByAccount(requestMap.get("operator"));
		String site = "";  //获取当前登录人的站点信息
		if(list != null && list.size() > 0)
		{
			site = list.get(0).getId()+"";
			customerOrderMap.put("site", site);
		}
		else
		{
			return false;
		}
		String orderStatus = requestMap.get("status");//订单状态  0-已付款 1-未付款
		customerOrderMap.put("idCustomer", requestMap.get("idCustomer"));
		customerOrderMap.put("idChannel", requestMap.get("idChannel"));
		customerOrderMap.put("subTotal", requestMap.get("subTotal"));
		customerOrderMap.put("idCurrency", requestMap.get("idCurrency"));
		customerOrderMap.put("operator", requestMap.get("operator"));
		
		if("1002011".equals(requestMap.get("operator"))){
		customerOrderMap.put("operator2", requestMap.get("operator2"));
		}else{
			customerOrderMap.put("operator2", 0+"");
		}
		customerOrderMap.put("idPayment", requestMap.get("idPayment"));
		String subTotal = requestMap.get("subTotal");
		String admountCash = requestMap.get("amountCash");
		
		//处理现金 如果页面有填写现金  总金额减去现金就是刷卡金额
		Double a = Double.valueOf(subTotal);
		Double b = Double.valueOf(admountCash);
		double  amountCard = 0;
		//如果是已付款 先设置刷卡金额为总金额  
		if("0".equals(orderStatus))
		{
			amountCard = a;
		}
		//如果有现金 设置刷卡金额为总金额减去现金
		if(b > 0 )
		{
			amountCard  =  a - b;
			
		}
		customerOrderMap.put("amountCard", amountCard+"");
		customerOrderMap.put("amountCash", requestMap.get("amountCash"));
		customerOrderMap.put("status", requestMap.get("status"));
		customerOrderMap.put("remark", requestMap.get("remark"));
		customerOrderMap.put("gmtPay", "0".equals(orderStatus)?"sysdate()":"null"); //如果为已付款 设置付款时间
		
		Integer custoerOrderId= orderDao.insertIntoCustomerOrder(customerOrderMap);  //保存订单信息;
	
		if(custoerOrderId == null) 
		{
			return false;
		}
	
		//更新产品状态
		Map<String,String> lifeCylrMap = new HashMap<String, String>();
		
		//插入历史表
		Map<String,String> historyrMap = new HashMap<String, String>();
		
		//更新产品状态为已售 和订单信息
		Set<Entry<String, Double>>  keySet = productsMap.entrySet();
		Iterator<Map.Entry<String,Double>>it = keySet.iterator();
		while(it.hasNext())
		{
			Map.Entry<String,Double> entry = it.next();
			
			
			Product p = productDao.getproduct(entry.getKey()); //查询当前产品信息(未更新之前)
			
			
			if("0".equals(orderStatus)) //已付款
			{
				lifeCylrMap.put("idStatus", 4+""); //更新为已售
			}
			else
			{
				lifeCylrMap.put("idStatus", 3+""); //更新为预订
			}
			lifeCylrMap.put("idOrder", custoerOrderId+"");
			lifeCylrMap.put("price", entry.getValue()+"");//售价
			lifeCylrMap.put("idProduct", entry.getKey()); 
			lifeCylrMap.put("idPriceCurrency", requestMap.get("idCurrency")); //销售币种
			lifeCylrMap.put("idLastOperator", requestMap.get("operator")); //最后操作人
			lifeCylrMap.put("idLifeCycle", p.getIdLifeCycle()+""); //
			productDao.updateLifeCyle(lifeCylrMap);//更新状态
			
			
			
			historyrMap.put("idProduct", entry.getKey());//idProduct
			historyrMap.put("idOperator", requestMap.get("operator"));//operator
			historyrMap.put("idCustomer", requestMap.get("idCustomer"));//operator
			
			historyrMap.put("idCurStation",p.getCurSiteId());//当前站点
			historyrMap.put("idSupply", p.getIdSupply()+"");//idSupply
			if("0".equals(orderStatus)) //已付款
			{
				historyrMap.put("idOperation", 4+"");//4为销售出库
				historyrMap.put("idStatus",4+"");//已售
			}
			else
			{
				historyrMap.put("idOperation", 5+"");//5为预留
				historyrMap.put("idStatus",3+"");//预订
			}
			
			
			productDao.insertHistory(historyrMap); //插入历史表
			//预订 锁库的到已售不更新web端库存 之前已经更新过
			if(p.getStatus().equals("可售"))
			{
				//更新web端库存
				reduceEmallGoodsStock(entry.getKey());
			}

		}
		   //给客户加积分
			int idCustomer = Integer.parseInt(requestMap.get("idCustomer"));
			Customer cust = customerDao.getCustomerById((long) idCustomer);
			String jfs = cust.getIntegral();
			
			int jf = 0;
			if(jfs != "" && jfs != null){
				jf = Integer.parseInt(jfs);
			}
			float ss = Float.parseFloat(requestMap.get("subTotal"));
			int subTotaljf= (int)ss;
			int integral = jf+subTotaljf;
			Customer customer = new Customer();
			customer.setIdCustomer((long) idCustomer);
			customer.setIntegral(integral+"");
			customerDao.updateCustomer(customer);
		return true;
	}

	/**
	 * 减少emallgoods 库存
	 */
	@Override
	public void reduceEmallGoodsStock(String idProduct)
	{
		Product p = productDao.getproduct(idProduct);
		//更新emall_goods等库存信息
		Map<String,String> emallGoodsMap = new HashMap<String, String>();
		emallGoodsMap.put("goodsId", p.getGoodsId()+"");
		emallGoodsMap.put("size", p.getSize());
		emallGoodsMap.put("siteId", p.getCurSiteId());
		
		emallGoodsMap.put("type", p.getType());
		emallGoodsMap.put("material", p.getMaterial());
		emallGoodsMap.put("color", p.getColor());
		
		
 
		if(MiniUiUtil.hkSite.contains(p.getCurSiteId()))
		{
			emallGoodsMap.put("hk_goods_number", "true"); //更新香港库存字段
		}
		//更新emall_goods库存
		productStockDao.updateEmallGoodsStock(emallGoodsMap);
		
		//更新ioss_goods_instance库存
		productStockDao.updateIossGoodInstanceStock(emallGoodsMap);
		
		
		GoodsInstance gins = goodsInstanceDao.getClientInstance(emallGoodsMap);
		if(gins != null)
		{
			emallGoodsMap.put("goodsInstanceId", gins.getId()+"");
			productStockDao.updateHxAvaliableStock(emallGoodsMap);
		}
	}
	
	

	/**
	 * 添加emallgoods 库存
	 */
	@Override
	public void addEmallGoodsStock(String idProduct,boolean back)
	{
		Product p = productDao.getproduct(idProduct);
		if(p.getGoodsId() == null || "".equals(p.getGoodsId()) || "null".equalsIgnoreCase(p.getGoodsId()+"")) return;
		//更新emall_goods等库存信息
		Map<String,String> emallGoodsMap = new HashMap<String, String>();
		emallGoodsMap.put("goodsId", p.getGoodsId()+"");
		emallGoodsMap.put("size", p.getSize());
		emallGoodsMap.put("siteId", p.getCurSiteId());
		
		emallGoodsMap.put("type", p.getType());
		emallGoodsMap.put("material", p.getMaterial());
		emallGoodsMap.put("color", p.getColor());
		
		
 
		//当前站点在香港-需要更新香港库存
		if(MiniUiUtil.hkSite.contains(p.getCurSiteId()))
		{
			emallGoodsMap.put("hk_goods_number", "true"); //更新香港库存字段
		}
		
		//是退货
		if(back == true)
		{
			emallGoodsMap.put("sell_num", "true"); //减少已售库存
			emallGoodsMap.put("goods_sale_number", "true"); //减少已售库存
		}
		//添加emall_goods库存
		productStockDao.addEmallGoodsStock(emallGoodsMap);
		
		//添加ioss_goods_instance库存
		productStockDao.addIossGoodInstanceStock(emallGoodsMap);
		
		//添加站点库存
		GoodsInstance gins = goodsInstanceDao.getClientInstance(emallGoodsMap);
		if(gins != null)
		{
			emallGoodsMap.put("goodsInstanceId", gins.getId()+"");
			productStockDao.addHxAvaliableStock(emallGoodsMap);
		}
	}

 
	@Override
	@Transactional
	public boolean updateProductByNotNull(Product product)
	{
		 if(product == null   || StringUtil.isBlank(product.getType())) return false;
		 toUperCase(product);
		 String idProduct = product.getIdProduct();
		 Map<String,String> map = new HashMap<String, String>();
		 map.put("type", product.getType());
		 map.put("material", product.getMaterial());
		 map.put("color", product.getColor());
		 map.put("size", product.getSize());
		 map.put("brandId", product.getBrandID());
		 map.put("idProduct", product.getIdProduct());
		 String sku = autoSyncDao.getSupplkierSkuByInfo(map);
		 
		 if(StringUtil.isBlank(sku) || StringUtil.isEmpty(sku)) //没有获取到SKU  1.判断有用该idProduct做SKU的产品没 2.如果没有就用当前idProduct做sku 如果有就用当前idProduct+1
		 {
			 Map<String,String> searchMap = new HashMap<String, String>();
			 searchMap.put("sku", product.getIdProduct());
			 List<Product> productList = productDao.getProductList(searchMap); //如果修改的产品sku为第一个生成的sku  则修改为当前idProduct+1
			 if(productList.size() > 1)
			 {
				 BigDecimal bigDecimal = new BigDecimal(idProduct);
				 sku = bigDecimal.add(new BigDecimal(1)).toString();//设置为当前idProduct+1
			 }
			 else
				 sku = product.getIdProduct(); //设置为当前idProduct

			 
		 }
		 product.setSku(sku);//有获取到 设置为当前sku
		 
		 Product oldProduct = getproduct(product.getIdProduct());
		 
		 //当型号、材质、颜色、尺寸、品牌之中任意一个有变化，将product中的goods_id,instace_id,gmt_export设置为null
		    String type = product.getType();
			String material = product.getMaterial();
			String color = product.getColor();				
			String size= product.getSize();			
			String brandID = product.getBrandID();
			if(!oldProduct.getType().equals(type) || !oldProduct.getMaterial().equals(material) || !oldProduct.getColor().equals(color) || !oldProduct.getSize().equals(size) || !oldProduct.getBrandID().equals(brandID)){				
				 productDao.updateProductGoddsIdIsNull(product);
			}
			
		 
		 //型号 材质  颜色 大小有过更改  更新goods_id 其他平台sku为空
		 /*已用其他表处理
		 if(!sku.equals(oldProduct.getSku()))
		 {
			 productDao.updateProductSku2Null(idProduct);
		 }
		 */
		 
		//处理图片,有branID 使用新的品牌  没有则使用以前的，主要用于设置图片路径的
		if(StringUtil.isNotBlank(product.getBrandID()))
		{
			product.setBrandID(product.getBrandID());
		}
		else
		{
			product.setBrandID(oldProduct.getBrandID());
		}
		Brand brand = brandDao.getBrand(Long.valueOf(product.getBrandID()));
		product.setBrandName(brand.getBrandName());
		
		 //更新图片URL
		String fileName = MiniUiUtil.setProductPicture(product);
		try
		{
			String url = MiniUiUtil.UPLOAD_IMG_URL+"?filename="+fileName;
			MiniUiUtil.getURLContent(url);//上传图片 
			product.setPicture("/upload/view.php?filename=" + fileName);//设置图片路径
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		

		
 		 //sku有变化
		 if(StringUtil.isNotBlank(product.getSku()) && StringUtil.isNotBlank(oldProduct.getSku())  && !product.getSku().equals(oldProduct.getSku()) )
		 {
			 updateProductLog(oldProduct,product,"2");//2-sku改变
		 }
		 productDao.updateProduct(product);
		 productDao.updateProductLifecyle(product);
		 return true;
	}
	
	private void updateProductLog(Product oldProduct,Product nowProduct,String type)
	{
		 ProductUpdateLog pu = new ProductUpdateLog();
		 pu.setIdProduct(nowProduct.getIdProduct());
		 pu.setBeforSku(oldProduct.getSku());
		 pu.setNowSku(nowProduct.getSku());
		 pu.setBeforType(oldProduct.getType());
		 pu.setNowType(nowProduct.getType());
		 pu.setBeforMaterial(oldProduct.getMaterial());
		 pu.setNowMaterial(nowProduct.getMaterial());
		 pu.setBeforColor(oldProduct.getColor());
		 pu.setNowColor(nowProduct.getColor());
		 pu.setBeforSize(oldProduct.getSize());
		 pu.setNowSize(nowProduct.getSize());
		 pu.setBeforSmPrice(oldProduct.getSmPrice());
		 pu.setNowSmprice(nowProduct.getSmPrice());
		 pu.setUpdateUserId(nowProduct.getIdLastOperator());
		 pu.setType(type);	
		 pu.setBeforActivePrice(oldProduct.getActivePrice());
		 pu.setNowActivePrice(nowProduct.getActivePrice());
		 productDao.addProductUpdateLog(pu);
	}
	@Override
	public boolean updateProductActivePrice(Product product)
	{
		 //前端只传递了 idProduct和活动价过来 更新需要记录 sku信息 以及活动价信息 所以要查询2次
		 if(product == null   || StringUtil.isBlank(product.getIdProduct())) return false;
		 Product oldProduct = productDao.getproduct(product.getIdProduct());
		 Product productClone = productDao.getproduct(product.getIdProduct());
		 //活动价有变化 从无到有 
		 if(oldProduct.getActivePrice() == null && product.getActivePrice() > 0)
		 {
			 productClone.setActivePrice(product.getActivePrice());
			 productClone.setIdLastOperator(product.getIdLastOperator());
			 updateProductLog(oldProduct,productClone,"1"); //活动价改变
		 } //有活动价  更新了活动价
		 else if(oldProduct.getActivePrice() > 0 && product.getActivePrice() > 0 && product.getActivePrice().doubleValue() != oldProduct.getActivePrice().doubleValue())
		 {
			 productClone.setIdLastOperator(product.getIdLastOperator());
			 productClone.setActivePrice(product.getActivePrice());
			 updateProductLog(oldProduct,productClone,"1"); //活动价改变
		 }
		 else if(oldProduct.getActivePrice() > 0 && (product.getActivePrice() == null || product.getActivePrice()<=0))
		 {
			 productClone.setIdLastOperator(product.getIdLastOperator());
			 productClone.setActivePrice(product.getActivePrice());
			 updateProductLog(oldProduct,productClone,"1"); //活动价改变
		 }
		 
		 productDao.updateProduct(product);
		 return true;
	}
	

	/**
	 * 转换 型号 材质 颜色 尺寸为大写
	 * @param product
	 */
	private void toUperCase(Product product)
	{
		if(product == null) return;
		if(StringUtil.isNotBlank(product.getType()))
		{
			product.setType(product.getType().toUpperCase());
		}
		if(StringUtil.isNotBlank(product.getMaterial()))
		{
			product.setMaterial(product.getMaterial().toUpperCase());
		}
		if(StringUtil.isNotBlank(product.getColor()))
		{
			product.setColor(product.getColor().toUpperCase());
		}
		if(StringUtil.isNotBlank(product.getSize()))
		{
			product.setSize(product.getSize().toUpperCase());
		}
	}

	/**
	 * 准入库转可售
	 */
	@Override
	@Transactional
	public String productInStock(Product product)
	{
		Product oldProduct = getproduct(product.getIdProduct());
		if(!"准入库".equals(oldProduct.getStatus()))
		{
			return "status error";
		}	
		 Map<String,String> map = new HashMap<String, String>();
		 map.put("type", product.getType());
		 map.put("material", product.getMaterial());
		 map.put("color", product.getColor());
		 map.put("size", product.getSize());
		 map.put("brandId", oldProduct.getBrandID());
		 map.put("idProduct", product.getIdProduct());
		 String sku = autoSyncDao.getSupplkierSkuByInfo(map);
		
		 if(StringUtil.isBlank(sku) || StringUtil.isEmpty(sku)) //没有获取到SKU  如果没有就用当前idProduct做sku 
		 {
			 sku = product.getIdProduct(); //设置为当前idProduct
		 }
		 product.setSku(sku);//有获取到 设置为当前sku
		
		//设置状态
		product.setStatus("1");//可售
		product.setSalePrice(0D);//售价为0
		
		
		
		//如果前端选择的是 新品 或者2手, 需要新建一个采购订单
		if(product.getSecondHand() == 1 || product.getSecondHand() == 2)
		{
			Purchase purchase = new Purchase();
			purchase.setIdcurrency(product.getIdCostCurrency());
			purchase.setIdcustomer(null);
			purchase.setIdsupply(product.getIdSupply());
			purchase.setInsubtotal(0D);
			purchase.setSecondhand(product.getSecondHand() );
			purchase.setSubtotal(product.getCost());
			purchase.setOperator(product.getIdLastOperator());
			Long idPurchase = purchaseOrderDao.insertPurchase(purchase);
			
			//设置当前产品的采购单号
			product.setIdPurchase(idPurchase);
			
		}
		
		 //如果是采购产品   返回新品  页面设置需要
		if(-1 == product.getSecondHand())
		{
			product.setSecondHand(1);
		}
		
		boolean flag  = updateProductByNotNull(product); //更新产品信息
		
		MiniUiUtil.setProductPicture(product);  //更新完设置图片路径,页面显示的路径和数据库不一样 需要重新处理
		
		if(flag) //更新成功插入历史记录
		{
			Map<String,String> historyrMap = new HashMap<String, String>();
			historyrMap.put("idProduct", product.getIdProduct());//idProduct
			historyrMap.put("idOperator", product.getIdLastOperator());//operator
			historyrMap.put("idCurStation",oldProduct.getCurSiteId());//idProduct
			
			Long idPurchase = product.getIdPurchase();
			if(null != idPurchase)
			{
				Purchase purchase = purchaseOrderDao.getPurchaseOrderById(idPurchase);
				historyrMap.put("idSupply", purchase.getIdsupply()+"");//idSupply
				product.setIdSupply(purchase.getIdsupply());
			}
			else
			{
				historyrMap.put("idSupply", oldProduct.getIdSupply()+"");//idSupply
			}
			historyrMap.put("idOperation", 2+"");//确认入库
			historyrMap.put("idCustomer", "-1");//不关联客户
			historyrMap.put("idStatus",1+"");//可售
			productDao.insertHistory(historyrMap); //插入历史表
			
			//addEmallGoodsStock(product.getIdProduct(), false); //添加emall_goods库存  暂时不添加
		}
		return "ok";
	}

	@Override
	public List<ProductHistory> selectProductHistoryByIdProduct(String idProduct)
	{
		return  productDao.selectProductHistory(idProduct);
	}

	@Override
	public boolean updatesalePrice(Product product) {
		
		Product  p = productDao.getproduct(product.getIdProduct());
		Orderdetails order=	orderDao.getorderlistByid(p.getIdOrder());
		//if(order.getStatus().intValue() != 1) return false;//只能修改未付款订单
		
		double a = product.getSalePrice().doubleValue()-p.getSalePrice().doubleValue();
		Map<String,String> searchMap = new HashMap<String, String>();
		searchMap.put("idOrder", p.getIdOrder());
		double totalPrice = 0;
		for(Product pt : productDao.getProductList(searchMap))
		{
			totalPrice += pt.getSalePrice();
		}
		order.setSubTotal(totalPrice);
		Orderdetails orderUpdate = new Orderdetails();
		orderUpdate.setIdorder(order.getIdorder());
		orderUpdate.setSubTotal(order.getSubTotal().doubleValue()+a);
		
		if(order.getIdPayment()!=2){
		double d =0;	
		orderUpdate.setAmountCash(d);
		orderUpdate.setAmountCard(order.getSubTotal().doubleValue()+a);
		orderDao.updateCustomerOrder(orderUpdate);
		
		}else{
		double d =0;
		orderUpdate.setAmountCard(d);
		orderUpdate.setAmountCash(order.getSubTotal().doubleValue()+a);
		orderDao.updateCustomerOrder(orderUpdate);
		}
		
		orderDao.updateCustomerOrder(orderUpdate);
		productDao.updateProductLifecyle(product);
		return true;
	}

	/**
	 * 调货出库
	 * prdduct 需要idProduct和idLocation
	 */
	@Override
	public void transferChuku(Product product)
	{
		product.setStatus("2"); //运输在途
		String idCustomer = product.getIdCustomer();
		Product p = productDao.getproduct(product.getIdProduct());
		//只有可能的才能调货出库
		if("可售".equals(p.getStatus()))
		{
			product.setIdLastLocation(p.getCurSiteId());//前站点
			
			//更新站点和状态
			productDao.updateProductLifecyle(product);
			//插入历史表
			Map<String,String> historyrMap = new HashMap<String, String>();
			historyrMap.put("idProduct", product.getIdProduct());//idProduct
			historyrMap.put("idOperator", product.getIdLastOperator());//operator
			historyrMap.put("idCurStation",product.getIdLocation());//调入的站点
			historyrMap.put("idSupply", p.getIdSupply()+"");//idSupply
			historyrMap.put("idOperation", 8+"");//调货出库
			historyrMap.put("idCustomer", idCustomer);  //之前不关联客户参数为 "-1"       ————2019-08-30调货明细关联客户
			historyrMap.put("idStatus",2+"");//运输在途			
			productDao.insertHistory(historyrMap); //插入历史表
			
			
			PlatformData platformData = new PlatformData();
			platformData.setIdProduct(product.getIdProduct());
		}

 
	} 
	
	
	/**
	 * 调货入库
	 * prdduct 需要idProduct
	 */
	@Override
	public void transferRuku(Product product)
	{
		product.setStatus("1"); //可售
		
		Product p = productDao.getproduct(product.getIdProduct());
		if("运输在途".equals(p.getStatus()))
		{
			//更新站点和状态
			productDao.updateProductLifecyle(product);
			
			
			//插入历史表
			Map<String,String> historyrMap = new HashMap<String, String>();
			historyrMap.put("idProduct", product.getIdProduct());//idProduct
			historyrMap.put("idOperator", product.getIdLastOperator());//operator
			historyrMap.put("idCurStation",p.getCurSiteId());//当前站点
			historyrMap.put("idSupply", p.getIdSupply()+"");//idSupply
			historyrMap.put("idOperation", 9+"");//调货入库
			historyrMap.put("idCustomer", "-1");//不关联客户
			historyrMap.put("idStatus",1+"");//运输在途
			productDao.insertHistory(historyrMap); //插入历史表

		}
	}

	/**
	 * 批量锁库
	 */
	@Override
	@Transactional
	public boolean batchSuKu(Map<String, String> requestMap,List<String> idProductList)
	{
		
		//组装锁库信息
		ProductSuoKu psk = new ProductSuoKu();
		psk.setCreateUser(requestMap.get("operator"));//操作人
		psk.setCustomerId(Long.valueOf(requestMap.get("idCustomer")));
		psk.setEndDate(requestMap.get("overtime"));
		
		int productLockupId = productDao.addProductLockup(psk);
		if(-1 == productLockupId) return false;
		
 
	
		//更新产品状态
		Map<String,String> lifeCylrMap = new HashMap<String, String>();
		
		//插入历史表
		Map<String,String> historyrMap = new HashMap<String, String>();
		
		//锁库产品信息
		Map<String,String> suokuProductMap = new HashMap<String, String>();
		
		for(String idProduct : idProductList)
		{

			//先查询后更新 判断是否需要更新web端库存
			Product p = productDao.getproduct(idProduct); //查询当前产品信息
			
			if("可售".equals(p.getStatus()))
			{
				suokuProductMap.put("lockupId", productLockupId+"");
				suokuProductMap.put("idProduct", idProduct);
				productDao.addProductLockupProduct(suokuProductMap);//添加锁库关联的产品信息
				
				lifeCylrMap.put("idStatus", 8+""); //更新为锁库
				lifeCylrMap.put("idLifeCycle", p.getIdLifeCycle()+""); 
				lifeCylrMap.put("idLastOperator", requestMap.get("operator")); //最后操作人
				productDao.updateLifeCyle(lifeCylrMap);//更新状态
				
				
				
				historyrMap.put("idProduct",idProduct);//idProduct
				historyrMap.put("idOperator", requestMap.get("operator"));//operator
				
				historyrMap.put("idCurStation",p.getCurSiteId());//idProduct
				historyrMap.put("idSupply", p.getIdSupply()+"");//idSupply
				historyrMap.put("idOperation", 13+"");//13为锁库
				historyrMap.put("idCustomer", "-1");//idCustomer
				historyrMap.put("idStatus",8+"");//锁库
				productDao.insertHistory(historyrMap); //插入历史表
				
				
				//更新web端库存
				reduceEmallGoodsStock(idProduct);
				

			}
			
			
			

		}
		
		return true;
	}

	@Override
	public MiniUiGrid getSuoKuProductList(Map<String, String> requestMap)
	{
		QueryPage queryPage = new QueryPage(new Object());
		int count = productDao.getSuoKuProductListCount(requestMap); // 
		
		MiniUiGrid gird = new MiniUiGrid();
		gird.setTotal(count);
		
		if(count >0)
		{
			//int currPage,int pageSize
			String pageIndex = requestMap.get("pageIndex");
			String pageSize = requestMap.get("pageSize");
			queryPage.setCurrentPageForMiniUi(pageIndex);
			queryPage.setPageSizeString(pageSize);
			queryPage.setTotalItemForMiniUi(count); //之前的当前页大于最大页返回最后页  现在不做处理
			if(!"yes".equalsIgnoreCase(requestMap.get("noStartRowAndEndRow")))
			{
				requestMap.put("startRow", queryPage.getPageFristItem()+"");
				requestMap.put("endRow", queryPage.getPageSize()+"");
			}
			List<ProductSuoKuProduct> list = productDao.getSuoKuProductList(requestMap);
			if(list != null && list.size() >0)
			{
				gird.setData(list);
			}
		}
		return gird;
	}

	@Override
	public void batchJieSuo(Map<String, String> requestMap, List<String> idProductList)
	{
 	
		//更新产品状态
		Map<String,String> lifeCylrMap = new HashMap<String, String>();
		
		//插入历史表
		Map<String,String> historyrMap = new HashMap<String, String>();
		
		//锁库产品信息
		Map<String,String> jieSuoProductMap = new HashMap<String, String>();
		
		for(String idProduct : idProductList)
		{
 
			jieSuoProductMap.put("idProduct", idProduct);
			 
			//先查询后更新 判断是否需要更新web端库存
			Product p = productDao.getproduct(idProduct); //查询当前产品信息
			if("锁库".equals(p.getStatus()))
			{
			
				lifeCylrMap.put("idStatus", 1+""); //更新为可售
				lifeCylrMap.put("idLifeCycle", p.getIdLifeCycle()+""); 
				lifeCylrMap.put("idLastOperator", requestMap.get("operator")); //最后操作人
				productDao.updateLifeCyle(lifeCylrMap);//更新状态
				
				
				
				historyrMap.put("idProduct",idProduct);//idProduct
				historyrMap.put("idOperator", requestMap.get("operator"));//operator
				
				historyrMap.put("idCurStation",p.getCurSiteId());//idProduct
				historyrMap.put("idSupply", p.getIdSupply()+"");//idSupply
				historyrMap.put("idOperation", 14+"");//14为解锁产品
				historyrMap.put("idCustomer", "-1");//idCustomer
				historyrMap.put("idStatus",1+"");//锁库
				productDao.insertHistory(historyrMap); //插入历史表
				
				
				//更新web端库存
				addEmallGoodsStock(idProduct, false);
				
				PlatformData platformData = new PlatformData();
				platformData.setIdProduct(idProduct);
				

			}
		}
		
	}

	@Override
	public MiniUiGrid getSuoKuList(Map<String, String> requestMap)
	{
		QueryPage queryPage = new QueryPage(new Object());
		int count = productDao.getProductSuoKuListCount(requestMap);
		
		MiniUiGrid gird = new MiniUiGrid();
		gird.setTotal(count);
		
		if(count >0)
		{
			//int currPage,int pageSize
			String pageIndex = requestMap.get("pageIndex");
			String pageSize = requestMap.get("pageSize");
			queryPage.setCurrentPageForMiniUi(pageIndex);
			queryPage.setPageSizeString(pageSize);
			queryPage.setTotalItemForMiniUi(count); //之前的当前页大于最大页返回最后页  现在不做处理
		 
			List<ProductSuoKu> list = productDao.getProductSuoKuList(requestMap);
			if(list != null && list.size() >0)
			{
				gird.setData(list);
			}
		}
		return gird;
	}

	@Override
	public MiniUiGrid searchProductUpdateLog(Map<String, String> searMap)
	{
		MiniUiGrid gird = new MiniUiGrid();
		List<ProductUpdateLog> list = productDao.searchProductUpdateLog(searMap);
		gird.setTotal(list.size());
		gird.setData(list);
		return gird; 
	}


	@Override
	public void updateProductById(Product prodcut) {
		
		productDao.updateProductId(prodcut);
	}


	@Override
	public void transferDiaoHuo(Transfer transfer) {
		productDao.addTrsnsferInformation(transfer);		
	}

	@Override
	public void GoodSInformationDiaoHuo(GoodsInformation goods) {
		// TODO Auto-generated method stub
		productDao.addGoodsInformation(goods);
	}

	@Override
	public int selectTransferMaxId() {
		// TODO Auto-generated method stub		
		return productDao.selectMaxId();
	}

	@Override
	public MiniUiGrid getTransferList(Map<String, String> requestMap) {
		QueryPage queryPage = new QueryPage(new Object());
		int count = productDao.getShowTranListCount(requestMap); // 
		
		MiniUiGrid gird = new MiniUiGrid();
		gird.setTotal(count);
		
		if(count >0)
		{
			//int currPage,int pageSize
			String pageIndex = requestMap.get("pageIndex");
			String pageSize = requestMap.get("pageSize");
			queryPage.setCurrentPageForMiniUi(pageIndex);
			queryPage.setPageSizeString(pageSize);
			queryPage.setTotalItemForMiniUi(count); //之前的当前页大于最大页返回最后页  现在不做处理
			if(!"yes".equalsIgnoreCase(requestMap.get("noStartRowAndEndRow")))
			{
				requestMap.put("startRow", queryPage.getPageFristItem()+"");
				requestMap.put("endRow", queryPage.getPageSize()+"");
			}
			List<Transfer> list = productDao.showTran(requestMap);
			if(list != null && list.size() >0)
			{
				gird.setData(list);
			}
		}
		return gird;
	}

	@Override
	public MiniUiGrid getDiaoHuoProductList(Map<String, String> requestMap) {
		QueryPage queryPage = new QueryPage(new Object());
		int count = productDao.getShowGoodsListCount(requestMap); // 
		
		MiniUiGrid gird = new MiniUiGrid();
		gird.setTotal(count);
		
		if(count >0)
		{
			//int currPage,int pageSize
			String pageIndex = requestMap.get("pageIndex");
			String pageSize = requestMap.get("pageSize");
			queryPage.setCurrentPageForMiniUi(pageIndex);
			queryPage.setPageSizeString(pageSize);
			queryPage.setTotalItemForMiniUi(count); //之前的当前页大于最大页返回最后页  现在不做处理
			if(!"yes".equalsIgnoreCase(requestMap.get("noStartRowAndEndRow")))
			{
				requestMap.put("startRow", queryPage.getPageFristItem()+"");
				requestMap.put("endRow", queryPage.getPageSize()+"");
			}
			List<GoodsInformation> list = productDao.showGoods(requestMap);
			if(list != null && list.size() >0)
			{
				gird.setData(list);
			}
		}
		return gird;
	}


	@Override
	public void updatebyid(Transfer transfer) {
		productDao.updatebyid(transfer);
		
	}

	/*
	 * 	//安全类别、执行标准、商品名称、材质描述、颜色描述、产地的导入
	 * 
	 */
	@Override
	public void updateSecurityTCByFile(Map<String, List<String>> keyMap) {
		  //从map集合中得到一个整体，就是其中的一个键和一个list集合
		 Set<Entry<String,List<String>>> keySet= keyMap.entrySet();
		 Iterator<Entry<String,List<String>>> it = keySet.iterator();
		 //将map中的每个键值对一个一个拿出来
		 while(it.hasNext())
		 { 
			 Entry<String, List<String>> entry = it.next();
			 //拿出一对中的键
			 String sikuSku = entry.getKey();
			 //拿出一对中的值
			 List<String> ourList = entry.getValue();
			  Product p = new Product();
			  p.setSku(ourList.get(0));      //sku
			  p.setSecurityTC(ourList.get(1));   //安全技术类别
			  p.setImplementationS(ourList.get(2));  //执行标准
			  p.setName(ourList.get(3));      //商品名称
			  p.setMaterialdes(ourList.get(4));  //材质描述
			  p.setColordes(ourList.get(5));    //颜色描述
			  p.setOrigin(ourList.get(6));		//产地	 
			  productDao.updateProductsetSecurityTC(p);

		 }
		
	}

}
 

