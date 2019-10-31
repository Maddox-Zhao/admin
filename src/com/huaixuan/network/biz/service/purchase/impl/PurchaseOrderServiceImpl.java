package com.huaixuan.network.biz.service.purchase.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.product.ProductDao;
import com.huaixuan.network.biz.dao.purchase.PurchaseOrderDao;
import com.huaixuan.network.biz.dao.shop.BrandDao;
import com.huaixuan.network.biz.domain.product.Product;
import com.huaixuan.network.biz.domain.purchase.Purchase;
import com.huaixuan.network.biz.domain.purchase.PurchaseOrder;
import com.huaixuan.network.biz.domain.purchase.PurchaseProductYangJie;
import com.huaixuan.network.biz.domain.purchase.Purchaselifecycle;
import com.huaixuan.network.biz.domain.shop.Brand;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.service.purchase.PurchaseOrderService;
import com.huaixuan.network.common.util.miniui.MiniUiGrid;
import com.huaixuan.network.common.util.miniui.MiniUiUtil;
import com.hundsun.common.lang.StringUtil;



/**
 * @author Mr_Yang   2015-12-30 上午11:38:52
 **/

@Service("purchaseOrderService")
public class PurchaseOrderServiceImpl implements PurchaseOrderService
{
	@Autowired
	private PurchaseOrderDao purchaseOrderDao;
	
	@Autowired
	private BrandDao brandDao;
	
	@Autowired
	private ProductDao productDao;
	
	
	@Override
	public List<Purchaselifecycle> queryPurchaselifecycle(
			Purchaselifecycle purchaselifecycle)
	{
		return purchaseOrderDao.queryPurchaselifecycle(purchaselifecycle);
	}

	@Override
	public Purchase getPurchaseOrderById(Long idPurchase)
	{
		return purchaseOrderDao.getPurchaseOrderById(idPurchase);
	}

	@Override
	public MiniUiGrid queryPurchaseOrder(Map<String, String> searchMap)
	{
		QueryPage queryPage = new QueryPage(new Object());
		int count = purchaseOrderDao.queryPurchaseOrderCntByMap(searchMap);
		
		MiniUiGrid gird = new MiniUiGrid();
		gird.setTotal(count);
		
		if(count >0)
		{
			//int currPage,int pageSize
			String pageIndex = searchMap.get("pageIndex");
			String pageSize = searchMap.get("pageSize");
			queryPage.setCurrentPageForMiniUi(pageIndex);
			queryPage.setPageSizeString(pageSize);
			queryPage.setTotalItemForMiniUi(count); //之前的当前页大于最大页返回最后页  现在不做处理
		 
			searchMap.put("startRow", queryPage.getPageFristItem()+"");
			searchMap.put("endRow", queryPage.getPageSize()+"");
		 
			List<PurchaseOrder> list = purchaseOrderDao.queryPurchaseOrderByMap(searchMap);
			if(list != null && list.size() >0)
			{
				gird.setData(list);
			}
		}
		return gird;
	}

	@Override
	public int purchaseProductInStock(Map<String, String> requestMap)
	{
		String idPurchaseLifecycle = requestMap.get("idPurchaseLifeCycle");
		String idLastOperator = requestMap.get("idLastOperator");
		String idLocation = requestMap.get("idLocation");
		String idPurchase = requestMap.get("idPurchase");
		String years = requestMap.get("years");
		String month = requestMap.get("month");
		List<PurchaseProductYangJie> list = purchaseOrderDao.getPurchasePurchaseProductByIdPurchaseLifecycle(requestMap);
		int number = 1;//生成idProduct需要
		for(PurchaseProductYangJie p : list)
		{
			String sizeName = p.getSizeName();//如果有尺码信息 36,38,40,42,44,46,48,50,52,54,56,58,60
			String sizeNum  = p.getNumber(); //如果有尺码信息1,2,3,4,5,6,0,0,0,0,0,0,0
			int totalNum  = p.getTotalNumber(); //没有尺寸  按总条数循环 插入
			if(sizeName == null) sizeName="";
			if(sizeNum == null) sizeNum = "";
			
			String[] sizeNameArr = sizeName.split(","); 
			String[] sizeNumberArr = sizeNum.split(",");
			//组装需要入库的产品数据
			Product product =   new Product();
			product.setBrandID(p.getIdBrand());
			product.setSeriesId(p.getIdSeries());
			product.setType(p.getType());
			product.setIdPurchase(Long.valueOf(idPurchase));
			product.setMaterial(p.getMaterial());
			product.setColor(p.getColor());
			product.setDlPrice(p.getDlPrice()==null?0:p.getDlPrice());
			product.setOrigin(p.getOrigin());
			product.setEuPrice(p.getEuPrice()==null?0:p.getEuPrice());
			product.setDxPrice(p.getDxPrice()==null?0:p.getDxPrice());
			product.setSsPrice(p.getSsPrice()==null?0:p.getSsPrice());
			product.setSmPrice(p.getSmPrice()==null?0:p.getSmPrice());
			product.setCost(p.getCost()==null?0:p.getCost());
			product.setIdCostCurrency(Integer.valueOf(p.getIdCostCurrency()));
			product.setIdLocation(idLocation);
			product.setIdLastLocation(idLocation);
			product.setStatusID(6+""); //准入库
			product.setIdOrder(-1+"");//不关联订单
			product.setIdLastOperator(idLastOperator);
			product.setYears(years);
			product.setMonth(month);
			
			//图片路径需要品牌信息
			Brand brand = brandDao.getBrand(Long.valueOf(p.getIdBrand()));
			product.setBrandName(brand.getBrandName());
			String picName = MiniUiUtil.setProductPicture(product);
			product.setPicture("/upload/view.php?filename="+picName);//设置图片路径

			if(sizeNameArr.length > 1)  //有尺码信息
			{
				for(int i = 0; i < sizeNameArr.length; i++)
				{
					String size= sizeNameArr[i];
					int num = 0;
					if(i < sizeNumberArr.length)
					{
						if(StringUtil.isNotBlank(sizeNumberArr[i]))
						{
							num = Integer.valueOf(sizeNumberArr[i]);
						}
					}
					for(int index = 0; index < num; index++)
					{
						product.setSize(size);//大小
						String idProduct = MiniUiUtil.getIdProduct(idPurchaseLifecycle, number);
						product.setIdProduct(idProduct);
						productDao.addProduct(product);
						productDao.addProductLifecycle(product);
						//插入历史表
						Map<String,String> historyrMap = new HashMap<String, String>();
						historyrMap.put("idProduct", product.getIdProduct());//idProduct
						historyrMap.put("idOperator", product.getIdLastOperator());//operator
						historyrMap.put("idCurStation",product.getIdLocation());//当前站点
						historyrMap.put("idSupply", p.getIdSupply()+"");//idSupply
						historyrMap.put("idOperation", 1+"");//准备入库
						historyrMap.put("idCustomer", "-1");//不关联客户
						historyrMap.put("idStatus",6+"");//准入库
						historyrMap.put("date","curdate()");//
						productDao.insertHistory(historyrMap); //插入历史表
						number++;
					}
					
				}
			}
			else //没有尺码信息  循环总条数
			{
				for(int index = 0;index < totalNum; index++)
				{
					String idProduct = MiniUiUtil.getIdProduct(idPurchaseLifecycle, number);
					product.setIdProduct(idProduct);
					product.setSize(sizeName);
					productDao.addProduct(product);
					productDao.addProductLifecycle(product);
					//插入历史表
					Map<String,String> historyrMap = new HashMap<String, String>();
					historyrMap.put("idProduct", product.getIdProduct());//idProduct
					historyrMap.put("idOperator", product.getIdLastOperator());//operator
					historyrMap.put("idCurStation",product.getIdLocation());//当前站点
					historyrMap.put("idSupply", p.getIdSupply()+"");//idSupply
					historyrMap.put("idOperation", 1+"");//准备入库
					historyrMap.put("idCustomer", "-1");//不关联客户
					historyrMap.put("idStatus",6+"");//准入库
					historyrMap.put("date","curdate()");//
					productDao.insertHistory(historyrMap); //插入历史表
					number++;
				}
			}
			
		}
		return number;
	}

	@Override
	public List<PurchaseProductYangJie> getPurchaseProduct(Map<String, String> searchMap)
	{
		return purchaseOrderDao.getPurchasePurchaseProductByIdPurchaseLifecycle(searchMap);
	}

 
}
 
