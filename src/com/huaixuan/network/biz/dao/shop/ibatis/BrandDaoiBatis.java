package com.huaixuan.network.biz.dao.shop.ibatis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.shop.BrandDao;
import com.huaixuan.network.biz.domain.hy.ProductViewAll;
import com.huaixuan.network.biz.domain.shop.Brand;
import com.huaixuan.network.biz.domain.shop.BrandStoreDay;
import com.huaixuan.network.biz.domain.shop.Series;
import com.huaixuan.network.biz.domain.shop.Size;
 

@Service("brandDao")
public class BrandDaoiBatis implements BrandDao {
	@Autowired
	private SqlMapClientTemplate sqlMapClient;

	@Override
	public void addBrand(Brand brand) throws Exception {
		this.sqlMapClient.insert("addBrand", brand);
	}
	
	@Override
	public void addBrandStoreDay(BrandStoreDay brandStoreDay) throws Exception {
		this.sqlMapClient.insert("addBrandStoreDay", brandStoreDay);
	}

	@Override
	public void editBrand(Brand brand) throws Exception {
		this.sqlMapClient.update("editBrand", brand);
	}

	@Override
	public void removeBrand(Long brandId) throws Exception {
		this.sqlMapClient.delete("removeBrand", brandId);
	}

	@Override
	public Brand getBrand(Long brandId) {
		return (Brand) this.sqlMapClient.queryForObject("getBrand", brandId);
	}

	@Override
	public List<Brand> getBrands() throws Exception {
		return this.sqlMapClient.queryForList("getBrands", null);
	}

	@Override
	public Integer getBrandListCount(Long shopId) throws Exception {
		return (Integer) this.sqlMapClient.queryForObject("getBrandListCount",
				shopId);
	}

	@Override
	public List<Brand> getBrandByCatCode(String catCode) throws Exception {
		return this.sqlMapClient.queryForList("getBrandByCatCode", catCode);
	}
	
	@Override
	public List<BrandStoreDay> getBrandStoreDaylist(String date) throws Exception {
		Map prama = new HashMap();
		prama.put("date", date);
		return this.sqlMapClient.queryForList("getBrandStoreDaylist", prama);
	}

	@Override
	public List<Brand> getBrandListPage(Map<String, String> conditions)
			throws Exception {
		return this.sqlMapClient.queryForList("getBrandListPage", conditions);
	}

	@Override
	public Integer getMaxBrandSort(Long shopId) throws Exception {
		return (Integer) this.sqlMapClient.queryForObject("getMaxBrandSort",
				shopId);
	}

	@Override
	public void updateBrand(Brand brand) throws Exception {
		this.sqlMapClient.update("updateBrand", brand);
	}

	@Override
	public void updateBrandSort(int sort, long shopId) throws Exception {
		Map prama = new HashMap();
		prama.put("sort", sort);
		prama.put("shopId", shopId);
		this.sqlMapClient.update("updateBrandSort", prama);
	}

	@Override
	public List<Brand> getBrandsByName(String brandName) throws Exception {
		Map<String, Object> prama = new HashMap<String, Object>();
		prama.put("brandName", brandName);
		return this.sqlMapClient.queryForList("getBrandsByName", prama);
	}

	@Override
	public Integer getBrandisHaveGoods(Long brandId) throws Exception {
		return (Integer) this.sqlMapClient.queryForObject(
				"getBrandisHaveGoods", brandId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Integer getBrandStoreDayConditionWithPageCount(Map parMap)throws Exception  {
		return (Integer)sqlMapClient.queryForObject("getBrandStoreDayConditionWithPageCount", parMap);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ProductViewAll> getBrandStoreDayConditionWithPage(Map parMap) throws Exception {
		return sqlMapClient.queryForList("getBrandStoreDayConditionWithPage", parMap);
	}

	@Override
	public List<Series> getAllSeries()
	{
		return sqlMapClient.queryForList("getAllSeries2Object");
	}

	@Override
	public List<Size> getAllSize() {
		return sqlMapClient.queryForList("getAllSize");
	}
	
	@Override
	public Brand getBrandByName(Brand brand) {
		return (Brand)sqlMapClient.queryForObject("getBrandName",brand);
	}
	
	@Override
	public Series getSeriesByName(Series series) {
		return (Series)sqlMapClient.queryForObject("getSeriesName",series);
	}

	/* (non-Javadoc)
	 * 品类三级联动使用
	 */
	@Override
	public List<Series> getSeriesBySeries(Series series) {
		return sqlMapClient.queryForList("getseriesByParams",series);
	}
}
