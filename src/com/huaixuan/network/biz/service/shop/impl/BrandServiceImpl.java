package com.huaixuan.network.biz.service.shop.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.huaixuan.network.biz.dao.hy.ProductDao;
import com.huaixuan.network.biz.dao.shop.BrandDao;
import com.huaixuan.network.biz.domain.base.Constants;
import com.huaixuan.network.biz.domain.shop.Brand;
import com.huaixuan.network.biz.domain.shop.BrandStoreDay;
import com.huaixuan.network.biz.domain.shop.Series;
import com.huaixuan.network.biz.domain.shop.Size;
import com.huaixuan.network.biz.exception.ManagerException;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.service.goods.UploadUtil;
import com.huaixuan.network.biz.service.shop.BrandService;
import com.huaixuan.network.common.util.DateUtil;


@Service("brandService")
public class BrandServiceImpl implements BrandService {
	Log log = LogFactory.getLog(this.getClass());
	@Autowired
	public BrandDao brandDao;
	@Autowired
	private UploadUtil uploadUtil;
	@Autowired
	public ProductDao productDao;

	public void editBrand(Brand brand) {
		log.info("BrandManagerImpl.editBrand method");
		try {
			this.brandDao.editBrand(brand);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new ManagerException(e);
		}
	}

	/* @model: */
	public void removeBrand(Long brandId) {
		log.info("BrandManagerImpl.removeBrand method");
		try {
			Brand brandnew = this.brandDao.getBrand(brandId);
			int max = this.brandDao.getMaxBrandSort(brandnew.getShopId());

			if (brandnew.getSort() < max) {
				this.brandDao.updateBrandSort(brandnew.getSort(),
						brandnew.getShopId());
			}
			this.brandDao.removeBrand(brandId);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new ManagerException(e);
		}
	}

	/* @model: */
	public Brand getBrand(Long brandId) {
		log.info("BrandManagerImpl.getBrand method");
		try {
			return this.brandDao.getBrand(brandId);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new ManagerException(e);
		}
	}

	/* @model: */
	public List<Brand> getBrands() {
		log.info("BrandManagerImpl.getBrands method");
		try {
			return this.brandDao.getBrands();
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new ManagerException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public QueryPage getBrandListPage(long shopId, int currentPage, int pageSize) {
		try {
			QueryPage queryPage = new QueryPage(shopId);
			Map pramas = queryPage.getParameters();

			Integer count = this.brandDao.getBrandListCount(shopId);
			if (count > 0) {

				/* ��ǰҳ */
				queryPage.setCurrentPage(currentPage);
				/* ÿҳ���� */
				queryPage.setPageSize(pageSize);
				queryPage.setTotalItem(count);

				pramas.put("startRow",
						queryPage.getPageFristItem());
				pramas.put("endRow",
						queryPage.getPageLastItem());

				/* ��ҳ��ѯ����Ա��¼ */
				List<Brand> brandList = brandDao.getBrandListPage(pramas);
				if (brandList != null && brandList.size() > 0) {
					queryPage.setItems(brandList);
				}
			}
			return queryPage;
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new ManagerException(e);
		}
	}

	public void addBrand(Brand brand, List<MultipartFile> files) {
		try {
			String goodsPicPath = "brand" + Constants.FILE_SEP
					+ DateUtil.getDateTime("yyyyMM", new Date());
			// String realPathPre = uploadUtil.getRealUpload() +
			// Constants.FILE_SEP
			// + uploadUtil.getUploadRootPath();
			int i = 0;
			if (files != null && files.size() > 0) {
				for (MultipartFile file : files) {
					if (file.getSize() > 0) {
						String fileName = uploadUtil.newUpload(file,
								goodsPicPath);
						if (i == 0) {
							brand.setBrandLogo(goodsPicPath
									+ Constants.FILE_SEP + fileName);

						}
						i++;
					}
				}
			}
			int sort = 0;
			Integer max = this.brandDao.getMaxBrandSort(brand.getShopId());
			if (max != null) {
				sort = max.intValue();
			}
			brand.setSort(sort + 1);

			this.brandDao.addBrand(brand);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new ManagerException(e);
		}

	}

	public void updateBrand(Brand brand, List<MultipartFile> files) {
		try {
			String goodsPicPath = "brand" + Constants.FILE_SEP
					+ DateUtil.getDateTime("yyyyMM", new Date());
			// String realPathPre = uploadUtil.getRealUpload() +
			// Constants.FILE_SEP
			// + uploadUtil.getUploadRootPath();
			int i = 0;
			if (files != null && files.size() > 0) {
				for (MultipartFile file : files) {
					if (file.getSize() > 0) {
						String fileName = uploadUtil.newUpload(file,
								goodsPicPath);
						if (i == 0) {
							brand.setBrandLogo(goodsPicPath
									+ Constants.FILE_SEP + fileName);
							;
						}
						i++;
					}
				}
			} else {
				brand.setBrandLogo("");
			}
			// int sort = this.brandDao.getMaxBrandSort(brand.getShopId());
			// brand.setSort(sort+1);

			this.brandDao.updateBrand(brand);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new ManagerException(e);
		}
	}

	public List<Brand> getBrandsByName(String brandName) {
		log.info("BrandManagerImpl.getBrandsByName method");
		try {
			return this.brandDao.getBrandsByName(brandName);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new ManagerException(e);
		}
	}

	public List<Brand> getBrandByCatCode(String catCode) {
		log.info("BrandManagerImpl.getBrandByCatCode method");
		try {
			return this.brandDao.getBrandByCatCode(catCode);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new ManagerException(e);
		}
	}

	public Boolean getBrandisHaveGoods(Long brandId) {
		log.info("BrandManagerImpl.getBrandisHaveGoods method");
		try {
			Integer num = null;
			num = this.brandDao.getBrandisHaveGoods(brandId);
			if (num != null && num.intValue() > 0) {
				return true;
			}
			return false;
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new ManagerException(e);
		}

	}
	
	public void addBrandStoreDay() {
		log.info("BrandManagerImpl.addBrandStoreDay method");
		try {
			Date da = new Date();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//            String date = df.format(da);
            String date = "2013-10-28";
			List<BrandStoreDay>  brandStoreDaylist = brandDao.getBrandStoreDaylist(date);
			for(BrandStoreDay brandStoreDay :brandStoreDaylist ){
				brandStoreDay.setBrandStoreRate(dedoub((brandStoreDay.getBrandStore()/brandStoreDay.getAllStore())*100));
				brandStoreDay.setDate(df.parse(date));
				brandStoreDay.setDayMaori(dedoub(brandStoreDay.getDaySaleAmount()-brandStoreDay.getDaySaleCostHkd()));
				if(brandStoreDay.getBrandStore()==0){
					brandStoreDay.setDayRate(0);
				}else{
					brandStoreDay.setDayRate(dedoub((brandStoreDay.getDaySaleCost()/brandStoreDay.getBrandStore())*100));
				}
				brandDao.addBrandStoreDay(brandStoreDay);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new ManagerException(e);
		}
	}
	
	public double dedoub(double num){
		if(num  == 0 ){
			return 0;
		}
		BigDecimal bg = new BigDecimal(num);
		double f1 = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		
		return f1;
	}

	@Override
	public List<Series> getAllSeries()
	{
		return brandDao.getAllSeries();
	}

	@Override
	public List<Size> getAllSize() {
		return brandDao.getAllSize();
	}
	
	
	public Brand selectBrandByName(Brand brand) {
		
		
		return brandDao.getBrandByName(brand);
		
	}
	
	
	
public Series selectSeriesByName(Series series) {
		
		
		return brandDao.getSeriesByName(series);
		
	}

/* (non-Javadoc)
 * 品类三级联动使用
 */
@Override
public List<Series> getSeriesBySeries(Series ser) {
	
	return brandDao.getSeriesBySeries(ser);
}
	
}
