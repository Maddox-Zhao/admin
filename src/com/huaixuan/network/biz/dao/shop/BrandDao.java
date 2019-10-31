package com.huaixuan.network.biz.dao.shop;

import java.util.List;
import java.util.Map;

 

import com.huaixuan.network.biz.domain.hy.ProductViewAll;
import com.huaixuan.network.biz.domain.shop.Brand;
import com.huaixuan.network.biz.domain.shop.BrandStoreDay;
import com.huaixuan.network.biz.domain.shop.Series;
import com.huaixuan.network.biz.domain.shop.Size;
 

public interface BrandDao {
	void addBrand(Brand brand) throws Exception;
	
	void addBrandStoreDay(BrandStoreDay brandStoreDay) throws Exception;

	void editBrand(Brand brand) throws Exception;

	void removeBrand(Long brandId) throws Exception;

	Brand getBrand(Long brandId);

	List<Brand> getBrands() throws Exception;

	Integer getBrandListCount(Long shopId) throws Exception;

	List<Brand> getBrandListPage(Map<String, String> conditions)
			throws Exception;

	Integer getMaxBrandSort(Long shopId) throws Exception;

	void updateBrand(Brand brand) throws Exception;

	void updateBrandSort(int sort, long shopId) throws Exception;

	public List<Brand> getBrandsByName(String brandName) throws Exception;

	public Integer getBrandisHaveGoods(Long brandId) throws Exception;

	/**
	 * �����Ŀ�ҵ���ƷƷ��
	 *
	 * @param catCode
	 * @return
	 * @throws Exception
	 */
	List<Brand> getBrandByCatCode(String catCode) throws Exception;
	
	
	List<BrandStoreDay> getBrandStoreDaylist(String date) throws Exception;
	
	
	public Integer getBrandStoreDayConditionWithPageCount(Map parMap) throws Exception;
	
	/**
	 * @return
	 */
	public List<ProductViewAll> getBrandStoreDayConditionWithPage(Map parMap) throws Exception;
	
	
	
	public List<Series> getAllSeries();
	
	public List<Size>  getAllSize(); 
	//查询 商品
	public Brand getBrandByName(Brand brand);
//查询品类
	public Series getSeriesByName(Series series);

	/**
	 * @param ser
	 * @return
	 * 品类三级联动使用
	 */
	List<Series> getSeriesBySeries(Series ser);
	
}
