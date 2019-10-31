package com.huaixuan.network.biz.service.shop;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.huaixuan.network.biz.domain.shop.Brand;
import com.huaixuan.network.biz.domain.shop.Series;
import com.huaixuan.network.biz.domain.shop.Size;
import com.huaixuan.network.biz.query.QueryPage;

public interface BrandService {
	/* @interface model: */
	public void addBrand(Brand brand, List<MultipartFile> images);

	/* @interface model: */
	public void editBrand(Brand brand);

	public void removeBrand(Long brandId);

	/* @interface model: */
	public Brand getBrand(Long brandId);

	/* @interface model: */
	public List<Brand> getBrands();

	public QueryPage getBrandListPage(long shopId, int currentPage, int pageSize);

	public void updateBrand(Brand brand, List<MultipartFile> images);

	public List<Brand> getBrandsByName(String brandName);

	public Boolean getBrandisHaveGoods(Long brandId);

	List<Brand> getBrandByCatCode(String catCode);
	
	public void addBrandStoreDay();
	
	
	public List<Series> getAllSeries();
	
	public List<Size> getAllSize();
  //根据商品名查询商品
    public Brand selectBrandByName(Brand brand);
    //查询品类
    public Series selectSeriesByName(Series seies);

	/**
	 * @param ser
	 * @return
	 * 品类三级联动使用
	 */
	public List<Series> getSeriesBySeries(Series ser);
    
}
