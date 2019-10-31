package com.huaixuan.network.biz.dao.hy.ibatis;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.huaixuan.network.biz.dao.hy.ProductDao;
import com.huaixuan.network.biz.domain.hy.Product;

/**
 *
 * @version 3.2.0
 */
@Repository("productDao")
public class ProductDaoiBatis implements ProductDao {

	protected final Log log = LogFactory.getLog(this.getClass());

	@Autowired
	private SqlMapClientTemplate sqlMapClient;

	
	@SuppressWarnings("unchecked")
	@Override
	public List<Product> getProductClientToBrowser(Map parMap) {
		return sqlMapClient.queryForList("getProductClientToBrowser", parMap);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Product> getProducttoCheckNum(Map parMap)throws Exception {
		return sqlMapClient.queryForList("getProducttoCheckNum", parMap);
	}
	
	
	@Override
	public void updateProductToBrowser(Map<String,Object>  parMap) {
		sqlMapClient.update("updateProductToBrowser", parMap);
	}

	@Override
	public Product getProductByMap(Map<String, Object> parMap) throws Exception {
		return (Product)sqlMapClient.queryForObject("getProductByMap", parMap);
	}

	@Override
	public Product getProductById(Long id)
	{
		
		return (Product)sqlMapClient.queryForObject("selectProductById",id);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Product> getProductByCustomerOrderId(Long id)
	{
		
		return sqlMapClient.queryForList("getProductByCustomerOrderId",id);
	}

	
	@Override
	public Long getProductNumByPramas(Map<String,Object> pramas)throws Exception
	{
		return (Long)sqlMapClient.queryForObject("getProductNumByPramas",pramas);
	}

	@Override
	public List<Product> getProductInfo(List<String> ids)
	{
		
		return sqlMapClient.queryForList("getProducts",ids);
	}

	@Override
	public List<Product> getProductsByOrderId(Long oid)
	{
		return sqlMapClient.queryForList("getProductByOrderId",oid);
	}

	@Override
	public void insertProductVSOrderId(Map<String,Long> parMap)
	{
		sqlMapClient.insert("insertProductVSOrderId",parMap);
	}

	@Override
	public void updateProductStatusByProductId(Map parMap)
	{
		sqlMapClient.update("updateProductStatusByProductId",parMap);
	}

	

	/* (non-Javadoc)
	 * @see com.huaixuan.network.biz.dao.hy.ProductDao#selectProAndLifcle(java.util.Map)
	 */
	@Override
	public List<Product> selectProAndLifcle(Map<String, String> map) {
		
		 return sqlMapClient.queryForList("getProductByDate",map);
	}

	/* (non-Javadoc)
	 * @see com.huaixuan.network.biz.dao.hy.ProductDao#selectBySelDate()
	 */
	@Override
	public List<Product> selectBySelDate() {
		return sqlMapClient.queryForList("getBySellDate");
	}

}
