package com.huaixuan.network.biz.dao.supplier.ibatis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.huaixuan.network.biz.dao.supplier.SupplierGoodsDao;
import com.huaixuan.network.biz.domain.supplier.Supplier;
import com.huaixuan.network.biz.domain.supplier.SupplierGoods;
import com.huaixuan.network.biz.domain.supplier.SupplierGoodsSize;



/**
 * @author Mr_Yang   2015-9-15 ÉÏÎç11:07:20
 **/

@Repository("supplierGoodsDao")
public class SupplierGoodsDaoiBatis implements SupplierGoodsDao
{
	@Autowired
	private SqlMapClientTemplate sqlMapClientTemplate;
	
	@Override
	public List<SupplierGoods> searchSupplierGoods(Map map)
	{
		return ( List<SupplierGoods>)sqlMapClientTemplate.queryForList("selectSupplierGoodsByNotNull",map);
	}

	@Override
	public int searchSupplierGoodsCount(Map map)
	{
		Object obj =sqlMapClientTemplate.queryForObject("selectSupplierCountGoodsByNotNull",map);
		if(obj == null) return 0;
		return (Integer)obj;
	}

	@Override
	public String getGoodsSizeByGoodsId(Long goodsId)
	{
		Object obj = sqlMapClientTemplate.queryForObject("selectGoodsSizeByGoodsId",goodsId);
		if(obj ==null) return "";
		else return (String)obj;
	}

	@Override
	public SupplierGoods getGoodsByGoodsId(Long goodsId)
	{
		Map map = new HashMap<String, String>();
		map.put("goodsId", goodsId);
		Object obj =  sqlMapClientTemplate.queryForObject("selectSupplierGoodsByNotNull",map);
		if(obj == null) return null;
		else return (SupplierGoods)obj;
	}

	@Override
	public List<SupplierGoodsSize> getGoodsSizeByGoodsId2List(Long goodsId)
	{
		Map map = new HashMap<String, String>();
		map.put("goodsId", goodsId);
		return  sqlMapClientTemplate.queryForList("selectSupplierGoodsSizeByNotNull",map);
	}

	@Override
	public void updateSupplierGoods(SupplierGoods supplierGoods)
	{
		sqlMapClientTemplate.update("updateSupplierGoodsByNotNull",supplierGoods);
	}

	@Override
	public void updateSupplierGoodsSize(SupplierGoodsSize supplierGoodsSize)
	{
		sqlMapClientTemplate.update("updateSupplierGoodsSizeByNotNull",supplierGoodsSize);
	}

	
}
 
