package com.huaixuan.network.biz.dao.shop;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.shop.BrandCategory;

public interface BrandCategoryDao {

	List<BrandCategory> getCategoryBybrandId(Long brandId);

	List<BrandCategory> getRuleBybrandIdWithNames(Long brandId);

	BrandCategory getBrandCategoryById(Long id);

	void updateBrandCategoryStatusById(BrandCategory brandCategory)
			throws Exception;

	void addBrandCategory(BrandCategory brandCategory) throws Exception;

	BrandCategory getBrandCategoryByMap(Map parMap);

	List<BrandCategory> getBrandByCateGory(String catCode);
}
