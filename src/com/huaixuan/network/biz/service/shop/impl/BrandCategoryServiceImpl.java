package com.huaixuan.network.biz.service.shop.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.shop.BrandCategoryDao;
import com.huaixuan.network.biz.dao.shop.BrandDao;
import com.huaixuan.network.biz.domain.shop.Brand;
import com.huaixuan.network.biz.domain.shop.BrandCategory;
import com.huaixuan.network.biz.service.shop.BrandCategoryService;

@Service("brandCategoryService")
public class BrandCategoryServiceImpl implements BrandCategoryService {
	Log log = LogFactory.getLog(this.getClass());
	@Autowired
	public BrandCategoryDao brandCategoryDao;
	@Autowired
	public BrandDao brandDao;

	@Override
	public List<BrandCategory> getCategoryBybrandId(Long brandId) {
		log.info("BrandCategoryManager.getCategoryBybrandId method");
		try {
			List<BrandCategory> list = brandCategoryDao
					.getCategoryBybrandId(brandId);
			if (list != null) {
				for (BrandCategory tmp : list) {
					Brand brand = brandDao.getBrand(tmp.getBrandId());
					tmp.setBrandName(brand.getBrandName());
				}
			}
			return list;
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}

	@Override
	public BrandCategory getBrandCategoryById(Long id) {
		log.info("BrandCategoryManager.getBrandCategoryById method");
		try {
			return brandCategoryDao.getBrandCategoryById(id);
		} catch (Exception e) {
			e.getMessage();
		}
		return null;
	}

	@Override
	public void updateBrandCategoryStatusById(BrandCategory brandCategory) {
		log.info("BrandCategoryManager.updateBrandCategoryStatusById method");
		try {
			brandCategoryDao.updateBrandCategoryStatusById(brandCategory);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}

	@Override
	public void addBrandCategory(BrandCategory brandCategory) {
		log.info("BrandCategoryManager.addBrandCategory method");
		try {
			brandCategoryDao.addBrandCategory(brandCategory);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}

	@Override
	public BrandCategory getBrandCategoryByMap(Map parMap) {
		log.info("BrandCategoryManager.getBrandCategoryByMap method");
		try {
			return brandCategoryDao.getBrandCategoryByMap(parMap);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}

	@Override
	public List<BrandCategory> getBrandByCateGory(String catCode) {
		log.info("BrandCategoryManager.getBrandByCatCode method");
		try {
			return brandCategoryDao.getBrandByCateGory(catCode);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}
}
