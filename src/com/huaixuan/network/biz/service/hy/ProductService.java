package com.huaixuan.network.biz.service.hy;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.hy.Product;

public interface ProductService {

	/**
	 * @param 
	 * @return
	 */
	public List<Product> getProductClientToBrowser(Map parMap);
	
	public List<Product> getProducttoCheckNum(Map<String,Object> parMap);
	
	/**
	 * @param 
	 * @return
	 */
	public void updateProductToBrowser(Map<String,Object> parMap);
	
	/**
	 * 查找product 信息
	 * @param 
	 * @return
	 */
	public Product getProductByMap(Map<String,Object> parMap);
	
	
	public Product getProductById(Long id);
	
	public List<Product> getProductByCustomerOrderId(Long id);
	
	public Long getProductNumByPramas(Map<String,Object> pramas);
	
	
	
	/**
	 * 向表emall_order_product插入一条和OrderID想关联的记录
	 * @param parMap
	 */
	public void insertProductVSOrderId(Long oid,Long pid);
	
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
	 *查询product和lifecle和customerorder
	 */
	public List<Product> selectProAndLifcle(Map<String, String> map);

	/**
	 * @return
	 * //查询出三个月内的销售的所有产品
	 */
	public List<Product> selectBySelDate();
}
