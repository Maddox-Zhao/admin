package com.huaixuan.network.biz.dao.purchase.ibatis;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.huaixuan.network.biz.dao.purchase.PurchaseProudctDao;
import com.huaixuan.network.biz.domain.purchase.PurchaseProduct;

@Repository("PurchaseProudctDao")
public class PurchaseProductDaoImpl implements PurchaseProudctDao{
	@Autowired
	private SqlMapClientTemplate sqlMap;
	
	@Override
	public List<PurchaseProduct> getPurchaseproductList(Map<String, String> searchMap) {
		return sqlMap.queryForList("getPurchaseproduct", searchMap);
	}

	@Override
	public int getPurchaseProduct(Map<String, String> searchMap) {
		Object obj = sqlMap.queryForObject("getPurchaseproductCount", searchMap);
		if(obj == null) 
			return 0;
			return (Integer)obj;
	}

	@Override
	public void updatePurchaseProduct(PurchaseProduct purchaseProduct) {
		this.sqlMap.update("updatePurchaseProductf",purchaseProduct);
		
	}

	@Override
	public Long addPurchaseProduct(PurchaseProduct purchaseProduct) {
		return (Long) this.sqlMap.insert("addPurchaseProduct", purchaseProduct);
		 
		
	}

	@Override
	public String getPurchaseProductTotlPrice(Map<String, String> searchMap) {
		Object obj = sqlMap.queryForObject("getPurchaseProductTotalPrice",searchMap);
		if(obj == null) 
		return "0";
		return obj.toString();
	}

	@Override
	public String getPurchaseProducttotalNumber(Map<String, String> searchMap) {
		Object obj = sqlMap.queryForObject("getPurchaseProducttotalNumber", searchMap);
		if(obj == null)
		return "0";
		return obj.toString();
	}

	@Override
	public Long deletePurchaseProduct(PurchaseProduct purchaseProduct) {
		return (Long) this.sqlMap.insert("deletePurchaseProduct", purchaseProduct);
	}

}
