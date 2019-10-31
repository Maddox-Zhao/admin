package com.huaixuan.network.biz.dao.product.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.huaixuan.network.biz.dao.product.ProductDao;
import com.huaixuan.network.biz.domain.autosyn.PlatformData;
import com.huaixuan.network.biz.domain.product.GoodsInformation;
import com.huaixuan.network.biz.domain.product.Product;
import com.huaixuan.network.biz.domain.product.ProductHistory;
import com.huaixuan.network.biz.domain.product.ProductSuoKu;
import com.huaixuan.network.biz.domain.product.ProductSuoKuProduct;
import com.huaixuan.network.biz.domain.product.ProductUpdateLog;
import com.huaixuan.network.biz.domain.product.Transfer;





/**
 * @author Mr_Yang   2015-11-20 ����12:29:25
 **/

@Repository("clientProductDao")
public class ProductDaoImpl implements ProductDao
{

	@Autowired
	private SqlMapClientTemplate sqlMapClient;

	@Override
	public List<Product> getProductList(Map<String,String> searchMap)
	{
		return sqlMapClient.queryForList("getProductList",searchMap);
	}

	@Override
	public int getProductListCount(Map<String, String> searchMap)
	{
		Object obj = sqlMapClient.queryForObject("getProductListCount",searchMap);
		if(obj == null) return 0;
		return (Integer)obj;
	}
	/**
	 * @Description: 商品销售信息
	 * @date 2018-8-1
	 */
	@Override
	public int getGoodsSaleInfoListCount(Map<String, String> searchMap){
		Object obj = sqlMapClient.queryForObject("getGoodsSaleInfoListCount",searchMap);
		if(obj == null) return 0;
		return (Integer)obj;
	}
	@Override
	public List<Product> getGoodsSaleInfoList(Map<String,String> searchMap){
		List<Product> queryForList =null;
		try {
			queryForList = sqlMapClient.queryForList("getGoodsSaleInfoList",searchMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return queryForList ;
	}
	
	@Override
	public Product getproduct(String idProduct) {
		return (Product)this.sqlMapClient.queryForObject("getProductbyidProduct",idProduct);
	}

	@Override
	public List<Product> getProductListByIdList(List<String> idList)
	{
		if(idList == null || idList.size() == 0)
			return new ArrayList<Product>();
		return this.sqlMapClient.queryForList("selectProductsByIdList",idList);
	}
	
	public int updateProductListByIdList(Product product)
	{
		
		  return sqlMapClient.update("updateProductsByIdList",product);
	}
	public List<Product> showShoppingCar(String userName)
	{
		return sqlMapClient.queryForList("selectShoppingCar",userName);
	}
	
	/* 
	 * 获取购物车傳的searchmap
	 */
	@Override
	public List<Product> showShoppingCarMap(Map<String, String> searchmap) {
		
		return sqlMapClient.queryForList("selectShoppingCarMap",searchmap);
	}
	
	
	//用sku添加到购物车，获取购物车
	public List<Product> selectShoppingCarStatus(String userName){
		
		return sqlMapClient.queryForList("selectShoppingCarStatus",userName);
	}
	
	//根据sku和Status查询符合条件的所有商品
	@Override
	public List<Product> selectProductAllBySkuStatus(Map<String, String> searchMap) {
		return sqlMapClient.queryForList("selectProductBySkuAndStatus", searchMap);
	}
	
	@Override
	public int insertHistory(Map<String, String> map)
	{
		return sqlMapClient.update("insertIntoHistory",map);
		
	}

	@Override
	public int updateLifeCyle(Map<String, String> map)
	{
		return sqlMapClient.update("updateLifeCyle",map);
	}

	@Override
	public void updateProduct(Product product)
	{
		sqlMapClient.update("updateProductByNotNull",product);
	}

	@Override
	public void updateProductLifecyle(Product product)
	{
		sqlMapClient.update("updateLifecycleByNotNull",product);
	}

	@Override
	public void updateProductSku2Null(String idProduct)
	{
		sqlMapClient.update("updateProductSku2Null",idProduct);
	}

	@Override
	public List<ProductHistory> selectProductHistory(String idProduct)
	{
		return sqlMapClient.queryForList("selectProductHistoryByIdProduct", idProduct);
	}

	@Override
	public int addProductLockup(ProductSuoKu productSuoKu)
	{
		Object o = sqlMapClient.insert("addProductSuoKu",productSuoKu);
		if(o != null) 
		{
			return (Integer)o;
		}
		return -1;
	}

	@Override
	public void addProductLockupProduct(Map<String, String> map)
	{
		 sqlMapClient.insert("addProductSuoKuProduct",map);
	}

	@Override
	public List<ProductSuoKuProduct> getSuoKuProductList(Map<String, String> searchMap)
	{
		return sqlMapClient.queryForList("selectSuoKuProducts",searchMap);
	}

	@Override
	public int getSuoKuProductListCount(Map<String, String> searchMap)
	{
		Object obj = sqlMapClient.queryForObject("selectSuoKuProductsCount",searchMap);
		if(obj == null) return 0;
		return (Integer)obj;
	}

	@Override
	public void insertPlatformNeedUpdateStock(PlatformData platformData)
	{
			sqlMapClient.insert("insertAutosyncPlatformStock",platformData);
		
	}

	@Override
	public void copyLifeCyleByIdLiceCyle(Long idLifeCyle)
	{
		sqlMapClient.insert("copyLifeCyleByIdLiceCyle",idLifeCyle);
	}

	@Override
	public List<ProductSuoKu> getProductSuoKuList(Map<String, String> map)
	{
		 return (List<ProductSuoKu>)sqlMapClient.queryForList("selectSuoKuList",map);
	}

	@Override
	public int getProductSuoKuListCount(Map<String, String> map)
	{
		Object o =  sqlMapClient.queryForObject("selectSuoKuListCount",map);
		if(o == null) return 0;
		return (Integer)o;
		
	}

	@Override
	public void addProduct(Product product)
	{ 	 
		sqlMapClient.insert("addProduct",product);
	}

	@Override
	public Long addProductLifecycle(Product product)
	{
		Object o =  sqlMapClient.insert("addProductLifeCyle",product);
		if(o != null) return (Long)o;
		return -1L;
	}

	@Override
	public void addProductUpdateLog(ProductUpdateLog productUpdateLog)
	{
		sqlMapClient.insert("addProductUpdateLog",productUpdateLog);
	}

	@Override
	public List<ProductUpdateLog> searchProductUpdateLog(Map<String, String> map)
	{
		return (List<ProductUpdateLog>)sqlMapClient.queryForList("selectProductUpdateLogList",map);
	}

	@Override
	public void updateProductId(Product product)
	{
		sqlMapClient.update("updateProductId",product);
	}

	
	
	
	
	
	
	
	
	
	
	//添加调货信息
	@Override
	public int addTrsnsferInformation(Transfer transfer) {
		Object o = sqlMapClient.insert("addTrsnsferInformation",transfer);
		if(o != null) 
		{
			return (Integer)o;
		}
		return -1;
	}
	//添加调货产品
	@Override
	public void addGoodsInformation(GoodsInformation goods) {
		 sqlMapClient.insert("addGoodsInformation",goods);
		
	}
	@Override
	public List<Transfer> showTran(Map<String, String> searchMap) {
		// TODO Auto-generated method stub
		return sqlMapClient.queryForList("selectShowTranList",searchMap);
	}
	@Override
	public int getShowTranListCount(Map<String, String> searchMap) {
		Object o =  sqlMapClient.queryForObject("selectTransferListCount",searchMap);
		if(o == null) return 0;
		return (Integer)o;
	}
	
	@Override
	public List<GoodsInformation> showGoods(Map<String, String> searchMap) {
		// TODO Auto-generated method stub
		return sqlMapClient.queryForList("selectSDiaoHuoProductsList",searchMap);
	}

	@Override
	public int getShowGoodsListCount(Map<String, String> searchMap) {
		Object o =  sqlMapClient.queryForObject("selectGoodsListCountCount",searchMap);
		if(o == null) return 0;
		return (Integer)o;
	}

	@Override
	public int selectMaxId() {
		// TODO Auto-generated method stub
		int maxId=(Integer) sqlMapClient.queryForObject("selectMaxId");
		return maxId;
	}
      //根據type查詢全部
	@Override
	public List<Product> selectProductByType(Map<String, String> searchMap) {
		
		return sqlMapClient.queryForList("getProductList",searchMap);
	}
	//通过sku集合查询product表中的 型号 材质  颜色   （去重复）
	@Override
	public List<Product> showSkuinfo(List<String> sku) {
		// TODO Auto-generated method stub
		return sqlMapClient.queryForList("selectSkuinformation",sku);
	}

	@Override
	public void updatebyid(Transfer transfer) {
		sqlMapClient.update("updatehxtransferbyid", transfer);
		
	}

	@Override
	public void updateGoodSInformation(GoodsInformation goods) {
		sqlMapClient.update("updateGoodSInformation", goods);
		
	}

	/* 
	 * 安全类别、执行标准、商品名称、材质描述、颜色描述、产地的导入
	 */
	@Override
	public void updateProductsetSecurityTC(Product product) {
		sqlMapClient.update("updateProductsetSecurityTC", product);
		
	}

	/* 
     * 当型号、材质、颜色、尺寸、品牌之中任意一个有变化，将product中的goods_id,instace_id,gmt_export置为null
	 */
	@Override
	public void updateProductGoddsIdIsNull(Product product) {
		sqlMapClient.update("updateProductPutNull", product);
		
	}

	/* (non-Javadoc)
	 * @see com.huaixuan.network.biz.dao.product.ProductDao#selectProductBySku(java.lang.String)
	 */
	@Override
	public Product selectProductBySku(String sku) {
		// TODO Auto-generated method stub
		return (Product)this.sqlMapClient.queryForObject("getProductbysku",sku);
	}

	


}
 
