package com.huaixuan.network.biz.dao.goods.ibatis;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.huaixuan.network.biz.dao.goods.GoodsTesDao;
import com.huaixuan.network.biz.domain.goods.Goods;

@Repository("goodsTesDao")
public class GoodsTesDaoiBatis implements GoodsTesDao {
	@Autowired
	private SqlMapClientTemplate sqlMapClientTemplate;
	
	
	@Override
	public List<Goods> selectAllGoods() {
		return this.sqlMapClientTemplate.queryForList("selectSDiaoHuoProductsList");
	}

	/*
	 * (non-Javadoc)总数
	 */
	@Override
	public Integer getGoodsListByConditionWithPageCount(Map param,Goods goods) {
		return (Integer) sqlMapClientTemplate.queryForObject("Goods.searchGoodsDynamicCounts", goods);
		
	}
	
	@Override
	public List<Goods> getGoodsListByConditionWithPage(Map param,Goods goods) {
		return sqlMapClientTemplate.queryForList("Goods.searchGoodsDynamics", param);
	}

	@Override//mini   count
	public int searchMiniuiGoodsCount(Map<String, String> searchMap) {
		Object o = sqlMapClientTemplate.queryForObject("Goods.searchMiniGoodsDynamicCounts",searchMap);
		if(o == null) return 0;
		return (Integer)o;
	}
	
	@Override//miniui 总页数
	public List<Goods> selectMiniuiAllGoodsidOne(Map<String, String> searchMap) {
		return  sqlMapClientTemplate.queryForList("Goods.searchMiniGoodsDynamics",searchMap);
	}
	
	@Override//商品查询测试count
	public Integer getShoppingCartWithPageCount(Map parMap) {
		return (Integer) sqlMapClientTemplate.queryForObject("Goods.getGoodsByConditionCounts", parMap);
	}

	@Override//购物车商品查询测试
	public List<Goods> getShoppingCartWithTestPage(Map parMap) {
		return sqlMapClientTemplate.queryForList("Goods.getGoodsByConditions", parMap);
	}



	


	
	
}
