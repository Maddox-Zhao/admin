package com.huaixuan.network.biz.dao.goods.ibatis;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.huaixuan.network.biz.dao.goods.GoodsRuleDao;
import com.huaixuan.network.biz.domain.goods.GoodsRule;

@Repository("goodsRuleDao")
public class GoodsRuleDaoiBatis implements GoodsRuleDao {
	@Autowired
    private SqlMapClientTemplate sqlMapClient;

	public void addGoodsRule(GoodsRule goodsRule) throws Exception {
		this.sqlMapClient.insert("addGoodsRule", goodsRule);
	}

	public List<GoodsRule> getGoodsRuleByGoodsId(Long goodsId) {
		return  this.sqlMapClient.queryForList("getGoodsRuleByGoodsId", goodsId);
	}

	public GoodsRule getGoodsRuleByMap(Map parMap) {
		return (GoodsRule)this.sqlMapClient.queryForObject("getGoodsRuleByMap",parMap);
	}

	public int countGoodsRuleByReturnId(Long returnId) throws Exception {
		return (Integer) this.sqlMapClient.queryForObject("countGoodsRuleByReturnId",returnId);
	}

	public void editGoodsRule(GoodsRule goodsRule) {
		this.sqlMapClient.update("editGoodsRule",goodsRule);
	}
}
