package com.huaixuan.network.biz.service.hy.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.hy.ProductDao;
import com.huaixuan.network.biz.domain.hy.Product;
import com.huaixuan.network.biz.exception.ManagerException;
import com.huaixuan.network.biz.service.hy.ProductService;

@Service("productService")
public class ProductServiceImpl implements ProductService {

	protected Log log = LogFactory.getLog(this.getClass());
	@Autowired
	private ProductDao productDao;

	@SuppressWarnings("unchecked")
	@Override
	public List<Product> getProductClientToBrowser(Map parMap) {

		List<Product> productList = productDao.getProductClientToBrowser(parMap);

		return productList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Product> getProducttoCheckNum(Map parMap) {
		log.info("ProductServiceImpl.getProducttoCheckNum method");
		try {
			List<Product> productList = productDao.getProducttoCheckNum(parMap);
	
			return productList;
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new ManagerException(e);
		}
	}
	

	@Override
	public void updateProductToBrowser(Map<String, Object> parMap) {
		
		log.info("ProductServiceImpl.updateProductToBrowser method");
		try {
			productDao.updateProductToBrowser(parMap);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new ManagerException(e);
		}
	}

	@Override
	public Product getProductByMap(Map<String, Object> parMap) {
		log.info("ProductServiceImpl.getProductByMap method");
		try {
			return productDao.getProductByMap(parMap);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new ManagerException(e);
		}
	}

	@Override
	public Product getProductById(Long id)
	{
		return productDao.getProductById(id);
	}

	@Override
	public List<Product> getProductByCustomerOrderId(Long id) {
		
		List<Product> productList = productDao.getProductByCustomerOrderId(id);
		return productList;
	}

	@Override
	public Long getProductNumByPramas(Map<String,Object> pramas)
	{
		try {
			return productDao.getProductNumByPramas(pramas);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ManagerException(e);
		}
	}

	@Override
	public List<Product> getProductsByOrderId(Long oid)
	{
		return productDao.getProductsByOrderId(oid);
	}


	@Override
	public void insertProductVSOrderId(Long oid, Long pid)
	{
		Map<String,Long> parMap = new HashMap<String,Long>();
		parMap.put("orderId", oid);
		parMap.put("poductId", pid);
		productDao.insertProductVSOrderId(parMap);
	}


	@Override
	public void updateProductStatusByProductId(Map parMap)
	{
		productDao.updateProductStatusByProductId(parMap);
	}


	
	/* (non-Javadoc)
	 * @see com.huaixuan.network.biz.service.hy.ProductService#selectProAndLifcle(java.util.Map)
	 */
	@Override
	public List<Product> selectProAndLifcle(Map<String, String> map) {
		
		return productDao.selectProAndLifcle(map);
	}


	/* 
	 * //查询出三个月内的销售的所有产品
	 */
	@Override
	public List<Product> selectBySelDate() {


		return productDao.selectBySelDate();
	}
}
