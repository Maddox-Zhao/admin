package com.huaixuan.network.biz.service.product;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.product.GoodsInformation;
import com.huaixuan.network.biz.domain.product.Product;
import com.huaixuan.network.biz.domain.product.ProductHistory;

import com.huaixuan.network.biz.domain.shop.Brand;


import com.huaixuan.network.biz.domain.product.Transfer;

import com.huaixuan.network.common.util.miniui.MiniUiGrid;



/**
 * @author Mr_Yang   2015-11-20  01:23:55
 **/

public interface ProductService
{
	public  MiniUiGrid getProductList(Map<String,String> searchMap);
	
	public MiniUiGrid getGoodsSaleInfoList(Map<String,String> searchMap);
	
	public int getProductListCount(Map<String,String> searchMap);
	
	public  Product getproduct(String idProduct);
	
	public  List<Product> getProductsByIdList(List<String> idsList);
	
	public  int updateProductsByIdList(Product product);
	/**
	 * 获取购物车
	 * @param userName
	 * @return
	 */
	public List<Product> showShoppingCar(String userName);
	
	
	//用sku添加到购物车，获取购物车
	public List<Product> selectCarProdcutStatus(String username);
	
	//根据sku和Status查询符合条件的所有商品
	
    public List<Product> selectProductAllBySku(Map<String, String> map);
  //根据type查询符合条件的所有商品	      
    public List<Product> selectProductByType(Map<String, String> map);   
	
	/**
	 * 更新lifecyle表
	 * @param map
	 * @return
	 */
	public int updateLifeCyle(Map<String,String> map);
	
	
	public void updateLifeCyleByNotNull(Product product);
	
	
	/**
	 * 插入历史表
	 * @param map
	 * @return
	 */
	public int insertHistory(Map<String,String> map);
	
	
	
	/**
	 * 购物车结算
	 * @param requestMap
	 * @return
	 */
	
	public boolean shoppingCar2SettleAccount(Map<String,String> requestMap);
	
	
	/**
	 * 减少emall_goods库存
	 */
	public void reduceEmallGoodsStock(String idProduct);
	
	
	 /**
	  * 
	  * @param idProduct
	  * @param back 是否退货 或者取消预留
	  */
	public void addEmallGoodsStock(String idProduct,boolean back);
	
	
	/**
	 * 更新产品信息 包括 型号 材质 SKU  状态等信息
	 * 更新的时候型号 材质 颜色 大小 品牌 必须设置
	 * @param product
	 */
	public boolean updateProductByNotNull(Product product);
	
	/**
	 * 更新活动价
	 * @param product
	 * @return
	 */
	public boolean updateProductActivePrice(Product product);
	
	public boolean updatesalePrice(Product product);
	
	/**
	 * 产品入库
	 * @param product
	 */
	public String productInStock(Product product);
	
	
	 /**
	  * 根据idproduct查询历史记录
	  * @param idProduct
	  * @return
	  */
	public List<ProductHistory> selectProductHistoryByIdProduct(String idProduct);
	
	
	
	/**
	 * 批量调货出库
	 * @param product
	 * @return
	 */
	public void transferChuku(Product product);
	/*
	 * 调货信息
	 */
	public void transferDiaoHuo(Transfer transfer);	
	//查找Transfer 里面的信息
	public MiniUiGrid getTransferList(Map<String,String> requestMap);
	
	public void updatebyid(Transfer transfer);//根据ID修改状态
	/*
	 * 调货产品
	 */
	public void GoodSInformationDiaoHuo(GoodsInformation goods);
	
	/*
	 * 查找transfer最大的id
	 */
	public int selectTransferMaxId();
	
	/**
	 * 批量调货入库
	 * @param product
	 * @return
	 */
	public void transferRuku(Product product);
	
	
	
	
	/**
	 * 批量锁库
	 * @param requestMap
	 * @return
	 */
	public boolean batchSuKu(Map<String, String> requestMap,List<String> idProductList);
	
	
	
	/**
	 * 查询锁库产品
	 * @param requestMap
	 * @return
	 */
	public MiniUiGrid getSuoKuProductList(Map<String,String> requestMap);
	/*
	 * 查找调货产品你
	 */
	public MiniUiGrid getDiaoHuoProductList(Map<String,String> requestMap);
	
	/**
	 * 锁库产品解锁
	 * @param requestMap
	 * @param idProductList
	 */
	public void batchJieSuo(Map<String, String> requestMap,List<String> idProductList);
	
	
	
	/**
	 * 查询锁库列表 一个列对应多个产品
	 * @param requestMap
	 * @return
	 */
	public MiniUiGrid getSuoKuList(Map<String,String> requestMap);
	
	
	/**
	 * 查询产品的更新记录
	 * @param searMap
	 * @return
	 */
	public MiniUiGrid searchProductUpdateLog(Map<String,String> searMap);
	
	public void updateProductById(Product product);

	/**
	 * 获取购物车傳的searchmap
	 * @param searMap
	 * @return
	 */
	public List<Product> showShoppingCarMap(Map<String, String> searchmap);

	/**
	 * @param locationMap
	 * @param type
	 * 
	 * 	安全类别、执行标准、商品名称、材质描述、颜色描述、产地导入
	 */
	public void updateSecurityTCByFile(Map<String, List<String>> locationMap);
}
 
