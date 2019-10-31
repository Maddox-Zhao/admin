package com.huaixuan.network.biz.dao.shop.ibatis;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.shop.BrandCategoryDao;
import com.huaixuan.network.biz.domain.shop.BrandCategory;

@Service("brandCategoryDao")
public class BrandCategoryDaoiBatis implements BrandCategoryDao {
	@Autowired
	private SqlMapClientTemplate sqlMapClient;

	@Override
	public List<BrandCategory> getCategoryBybrandId(Long brandId) {
		return this.sqlMapClient.queryForList("getCategoryBybrandId", brandId);
	}

	@Override
	public List<BrandCategory> getRuleBybrandIdWithNames(Long brandId) {
		return this.sqlMapClient.queryForList("getRuleBybrandIdWithNames",
				brandId);
	}

	@Override
	public BrandCategory getBrandCategoryById(Long id) {
		return (BrandCategory) this.sqlMapClient.queryForObject(
				"getBrandCategoryById", id);
	}

	@Override
	public void updateBrandCategoryStatusById(BrandCategory brandCategory)
			throws Exception {
		this.sqlMapClient
				.update("updateBrandCategoryStatusById", brandCategory);
	}

	@Override
	public void addBrandCategory(BrandCategory brandCategory) throws Exception {
		this.sqlMapClient.insert("addBrandCategory", brandCategory);
	}

	@Override
	public BrandCategory getBrandCategoryByMap(Map parMap) {
		return (BrandCategory) this.sqlMapClient.queryForObject(
				"getBrandCategoryByMap", parMap);
	}

	@Override
	public List<BrandCategory> getBrandByCateGory(String catCode) {
		return this.sqlMapClient.queryForList("getBrandByCateGory", catCode);
	}
}
