package com.huaixuan.network.biz.dao.hy;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.hy.Product;



public interface ProductDao {
	
	
	/**
	 * @return
	 */
	public List<Product> getProductClientToBrowser(Map parMap);
	
	public List<Product> getProducttoCheckNum(Map<String,Object> parMap)throws Exception;
	
	public void updateProductToBrowser(Map<String,Object> parMap);
	
	public Product getProductByMap(Map<String,Object> parMap) throws Exception;
	
	public Product getProductById(Long id);
	
	public List<Product> getProductByCustomerOrderId(Long id);
	
	public Long getProductNumByPramas(Map<String,Object> pramas)throws Exception;
	
	/**
	 * ͨ通过ids得到这些产品的信息
	 * @param ids
	 * @return
	 */
	public List<Product> getProductInfo(List<String> ids);
	
	/**
	 * 向表emall_order_product插入一条和OrderID想关联的记录
	 * @param parMap
	 */
	public void insertProductVSOrderId(Map<String,Long> parMap);
	
	/**
	 * ͨ通过orderid来得到Product
	 * @param idProduct
	 * @return
	 */
	public List<Product> getProductsByOrderId(Long oid);
	
	
	public void updateProductStatusByProductId(Map parMap);

	/**
	 * @param map
	 * @return
	 */
	public List<Product> selectProAndLifcle(Map<String, String> map);

	/**
	 * @return
	 */
	public List<Product> selectBySelDate();
	
}
