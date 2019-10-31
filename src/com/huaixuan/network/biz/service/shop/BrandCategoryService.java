package com.huaixuan.network.biz.service.shop;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.shop.BrandCategory;

public interface BrandCategoryService {

	public List<BrandCategory> getCategoryBybrandId(Long brandId);

	public BrandCategory getBrandCategoryById(Long id);

	public void updateBrandCategoryStatusById(BrandCategory brandCategory);

	public BrandCategory getBrandCategoryByMap(Map parMap);

	public void addBrandCategory(BrandCategory brandCategory);

	public List<BrandCategory> getBrandByCateGory(String catCode);
}
