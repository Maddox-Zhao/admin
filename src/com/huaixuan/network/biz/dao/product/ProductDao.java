package com.huaixuan.network.biz.dao.product;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.autosyn.PlatformData;
import com.huaixuan.network.biz.domain.product.GoodsInformation;
import com.huaixuan.network.biz.domain.product.Product;
import com.huaixuan.network.biz.domain.product.ProductHistory;
import com.huaixuan.network.biz.domain.product.ProductSuoKu;
import com.huaixuan.network.biz.domain.product.ProductSuoKuProduct;
import com.huaixuan.network.biz.domain.product.ProductUpdateLog;

import com.huaixuan.network.biz.domain.shop.Brand;

import com.huaixuan.network.biz.domain.product.Transfer;

import com.huaixuan.network.common.util.miniui.MiniUiGrid;





/**
 * @author Mr_Yang   2015-11-20  12:13:17
 * 产品管理
 **/

public interface ProductDao
{
	public List<Product> getProductList(Map<String,String> searchMap);
	public int getProductListCount(Map<String,String> searchMap);
	public Product getproduct(String idProduct);

	public List<Product> getGoodsSaleInfoList(Map<String,String> searchMap);
	public int getGoodsSaleInfoListCount(Map<String, String> searchMap);
	//通过idProducts获取产品信息
	public List<Product> getProductListByIdList(List<String> idList);
	//通过idProducts修改产品信息
	public int updateProductListByIdList(Product product);
	
	
	//通过sku集合查询product表中的 型号 材质  颜色   （去重复）
	public List<Product> showSkuinfo(List<String> sku);
	
	
	
	public List<Product> showShoppingCar(String userName);
	
	/**
	 * 获取购物车傳的searchmap
	 * @param searchmap
	 * @return
	 */
	public List<Product> showShoppingCarMap(Map<String, String> searchmap);
	
	
	//用sku添加到购物车，获取购物车
	public List<Product> selectShoppingCarStatus(String username);
	//根据sku和Status查询符合条件的所有商品
	public List<Product> selectProductAllBySkuStatus(Map<String,String> searchMap);
	
	//根据type查询符合条件的所有商品
		public List<Product> selectProductByType(Map<String,String> searchMap);
	/**
	 * 更新lifecyle的状态 价格 等数据
	 * @param map
	 * @return
	 */
	public int updateLifeCyle(Map<String,String> map);
	
	/**
	 * 插入历史记录
	 * @param map
	 * @return
	 */
	public int insertHistory(Map<String,String> map);
	
	/**
	 * 修改调货产品历史信息商品状态
	 */
	public void updateGoodSInformation(GoodsInformation goods);
	
	
	/**
	 * 更新product表信息
	 * @param product
	 */
	public void updateProduct(Product product);
	
	/**
	 * 更新lifeCyle表信息
	 * @param product
	 */
	public void updateProductLifecyle(Product product);
	
	/**
	 * 退单后复制一条数据插入 lifecyle表
	 * @param idLifeCyle
	 */
	public void copyLifeCyleByIdLiceCyle(Long idLifeCyle);
	/**
	 * 型号修改后更新平台sku 和goodsid为空
	 */
	public void updateProductSku2Null(String idProduct);
	
	
	/**
	 * 根据idProduct查询历史记录
	 * @param idProduct
	 * @return
	 */
	public List<ProductHistory> selectProductHistory(String idProduct);
	
	
	/**
	 * 添加锁库信息
	 * @param productSuoKu
	 * @return
	 */
	public int addProductLockup(ProductSuoKu productSuoKu);
	
	
	/**
	 * 添加锁库产品 
	 * @param map  lockupId idProduct  必须
	 */
	public void addProductLockupProduct(Map<String,String> map);
	/*
	 * 添加调货信息
	 */
	public int addTrsnsferInformation(Transfer transfer);
	/*
	 * 查询Transfer最大的id
	 */
	public int selectMaxId();
	
	/*
	 * 根据ID修改状态
	 */
	public void updatebyid(Transfer transfer);
	
	/*
	 * 添加调货产品
	 */
	public void addGoodsInformation(GoodsInformation goods);
	/*
	 * 显示调货信息
	 */
	public List<Transfer> showTran(Map<String,String> map);
	public int getShowTranListCount(Map<String,String> searchMap);
	/*
	 * 显示调货产品
	 */
	public List<GoodsInformation> showGoods(Map<String,String> map);
	public int getShowGoodsListCount(Map<String,String> searchMap);
	/**
	 * 查询锁库产品信息
	 * @param map
	 * @return
	 */
	public List<ProductSuoKuProduct> getSuoKuProductList(Map<String,String> map);
	
	
	public int getSuoKuProductListCount(Map<String,String> searchMap);
	
	
	/**
	 * 开单 调货 锁库 等库存有减少的插入该表  会自动更新各个平台库存
	 * @param idProduct
	 */
	public void insertPlatformNeedUpdateStock(PlatformData platformData);
	
	
	
	/**
	 * 获取锁库列表
	 * @param map
	 */
	public List<ProductSuoKu> getProductSuoKuList(Map<String,String> map);
	public int getProductSuoKuListCount(Map<String,String> map);
	
	
	public void addProduct(Product product);
	public Long addProductLifecycle(Product product);
	
	
	/**
	 * 产品更新日志 记录 sku以及活动价的变动
	 * @param productUpdateLog
	 */
	public void addProductUpdateLog(ProductUpdateLog productUpdateLog);
	
	
	/**
	 * 查询产品历史更新记录  活动价  sku等修改过的信息
	 * @param map
	 * @return
	 */
	public List<ProductUpdateLog> searchProductUpdateLog(Map<String,String> map);
	
	public void updateProductId(Product product);
	/**
	 * @param p
	 * //安全类别、执行标准、商品名称、材质描述、颜色描述、产地的导入updateProductsetSecurityTC
	 */
	public void updateProductsetSecurityTC(Product p);
	/**
	 * @param product
	 * 
	 * 当型号、材质、颜色、尺寸、品牌之中任意一个有变化，将product中的goods_id,instace_id,gmt_export置为null
	 */
	public void updateProductGoddsIdIsNull(Product product);
	/**
	 * @param sku
	 * @return
	 * 通过sku获取，一条数据
	 */
	public Product selectProductBySku(String sku);
	
}
 

