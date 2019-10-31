/**
 * 
 */
package com.huaixuan.network.biz.dao.shop.ibatis;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.shop.BrandRuleDao;
import com.huaixuan.network.biz.domain.shop.BrandRule;

/**
 * @author shengyong
 * 
 */
@Service("brandRuleDao")
public class BrandRuleDaoiBatis implements BrandRuleDao {
	@Autowired
	private SqlMapClientTemplate sqlMapClient;

	@Override
	public void addBrandRule(BrandRule brandRule) throws Exception {
		this.sqlMapClient.insert("addBrandRule", brandRule);
	}

	@Override
	public BrandRule getBrandRuleById(Long id) {
		return (BrandRule) this.sqlMapClient.queryForObject("getBrandRuleById",
				id);
	}

	@Override
	public BrandRule getBrandRuleByRuleId(Long returnId) {
		return (BrandRule) this.sqlMapClient.queryForObject(
				"getBrandRuleByRuleId", returnId);
	}

	@Override
	public List<BrandRule> getBrandRuleByMap(Map parMap) {
		List<BrandRule> queryForList = this.sqlMapClient.queryForList(
				"getBrandRuleByMap", parMap);
		return queryForList;
	}

	@Override
	public List<BrandRule> getRuleBybrandId(Long brandId) {
		List<BrandRule> queryForList = this.sqlMapClient.queryForList(
				"getRuleBybrandId", brandId);
		return queryForList;
	}

	@Override
	public void updateBrandRuleStatusById(BrandRule brandRule) throws Exception {
		this.sqlMapClient.update("updateBrandRuleStatusById", brandRule);
	}

	@Override
	public List<BrandRule> getRuleBybrandIdWithNames(Long brandId) {
		List<BrandRule> queryForList = this.sqlMapClient.queryForList(
				"getRuleBybrandIdWithNames", brandId);
		return queryForList;
	}

}
