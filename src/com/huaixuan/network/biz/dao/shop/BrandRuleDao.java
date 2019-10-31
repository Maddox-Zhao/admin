/**
 * 
 */
package com.huaixuan.network.biz.dao.shop;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.shop.BrandRule;

/**
 * @author shengyong
 * 
 */
public interface BrandRuleDao {

	List<BrandRule> getRuleBybrandId(Long brandId);

	BrandRule getBrandRuleById(Long id);

	BrandRule getBrandRuleByRuleId(Long returnId);

	void updateBrandRuleStatusById(BrandRule brandRule) throws Exception;

	void addBrandRule(BrandRule brandRule) throws Exception;

	List<BrandRule> getBrandRuleByMap(Map parMap);

	List<BrandRule> getRuleBybrandIdWithNames(Long brandId);

}
