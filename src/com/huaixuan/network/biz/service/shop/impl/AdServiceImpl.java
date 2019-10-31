package com.huaixuan.network.biz.service.shop.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.huaixuan.network.biz.dao.shop.AdDao;
import com.huaixuan.network.biz.dao.shop.AdDetailDao;
import com.huaixuan.network.biz.dao.shop.AdPositionDao;
import com.huaixuan.network.biz.domain.base.Constants;
import com.huaixuan.network.biz.domain.shop.Ad;
import com.huaixuan.network.biz.domain.shop.AdDetail;
import com.huaixuan.network.biz.domain.shop.AdPosition;
import com.huaixuan.network.biz.exception.ManagerException;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.service.goods.UploadUtil;
import com.huaixuan.network.biz.service.shop.AdService;
import com.huaixuan.network.common.util.DateUtil;

@Service("adService")
public class AdServiceImpl implements AdService {
	Log log = LogFactory.getLog(this.getClass());

	// public AdDao adDao;
	@Autowired
	public AdDetailDao adDetailDao;
	@Autowired
	private UploadUtil uploadUtil;
	@Autowired
	private AdPositionDao adPositionDao;
	@Autowired
	public AdDao adDao;

	@SuppressWarnings("unchecked")
	@Override
	public QueryPage getByMapByConditionWithPage(Ad ad, int currPage,
			int pageSize) throws Exception {

		// log.info("NoticeManagerImpl.addNotice method");
		try {
			QueryPage queryPage = new QueryPage(ad);
			Map pramas = queryPage.getParameters();

			Integer count = this.adDao.getAdsCount(ad);

			if (count > 0) {

				/* 当前页 */
				queryPage.setCurrentPage(currPage);
				/* 每页总数 */
				queryPage.setPageSize(pageSize);
				queryPage.setTotalItem(count);

				pramas.put("startRow",
						queryPage.getPageFristItem());
				pramas.put("endRow",
						queryPage.getPageLastItem());

				/* 分页查询操作员记录 */
				List<Ad> adList = this.adDao.getAdsPage(pramas);
				if (adList != null && adList.size() > 0) {
					queryPage.setItems(adList);
				}
			}
			return queryPage;
		} catch (Exception e) {
			 log.error(e.getMessage());
			 throw new ManagerException(e);
		}
	}

	@Override
	public Long addAd(Ad ad, AdDetail adDetail, List<MultipartFile> files) {
		log.info("AdServiceImpl.addAd method");
		try {
			Long id = null;

			// 上传图片路径
			String goodsPicPath = "ad" + Constants.FILE_SEP
					+ DateUtil.getDateTime("yyyyMM", new Date());
			int i = 0;
			if (files != null && files.size() > 0) {
				for (MultipartFile file : files) {
					if (file.getSize() > 0) {
						String fileName = uploadUtil.newUpload(file,
								goodsPicPath);
						if (i == 0) {
							adDetail.setMediaCode(goodsPicPath
									+ Constants.FILE_SEP + fileName);
						}
						i++;
					}
				}
			}

			// sort
			int sort = 1;
			Integer max = this.adDao.getAdMaxSort();
			if (max != null && max.intValue() > 0) {
				sort = max.intValue() + 1;
			}
			adDetail.setSort(sort);
			// 新增广告，并返回ID
			id = this.adDao.addAd(ad);
			adDetail.setAdId(id);
			// 新增广告详细
			this.adDetailDao.addAdDetail(adDetail);

			return id;
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new ManagerException(e);
		}
	}

	@Override
	public void editAd(Ad ad) {
		log.info("AdManagerImpl.editAd method");
		try {
			this.adDao.editAd(ad);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new ManagerException(e);
		}

	}

	@Override
	public void removeAd(Long adId) {
		log.info("AdManagerImpl.removeAd method");
		try {
			Integer max = this.adDao.getAdMaxSort();
			if (max != null) {
				AdDetail adDetailnew = this.adDetailDao.getAdDetailByAdId(adId);
				if (adDetailnew != null) {
					if (adDetailnew.getSort() < max.intValue()) {
						this.adDao.updateAdSortlow(max.intValue(),
								adDetailnew.getSort());
					}
				}
			}
			Ad ad = new Ad();
			ad.setId(adId);
			ad.setStatus(Ad.Status_delete);
			this.adDao.removeAd(ad);

		} catch (Exception e) {
			log.error(e.getMessage());
			throw new ManagerException(e);
		}
	}

	@Override
	public Ad getAd(Long adId) {
		log.info("AdManagerImpl.getAd method");
		try {
			return this.adDao.getAd(adId);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new ManagerException(e);
		}
	}

	@Override
	public List<Ad> getAds() {
		try {
			return this.adDao.getAds();
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new ManagerException(e);
		}

	}

	@Override
	public void colseAd(Long adId) {
		log.info("AdManagerImpl.closeAd method");
		try {
			Ad ad = new Ad();
			ad.setId(adId);
			ad.setStatus(Ad.Status_close);
			this.adDao.removeAd(ad);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new ManagerException(e);
		}

	}

	@Override
	public void openAd(Long adId) {
		log.info("AdManagerImpl.openAd method");
		try {
			Ad ad = new Ad();
			ad.setId(adId);
			ad.setStatus(Ad.Status_open);
			this.adDao.removeAd(ad);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new ManagerException(e);
		}
	}

	@Override
	public void updateAd(Ad ad, AdDetail adDetail, List<MultipartFile> files) {
		log.info("AdServiceImpl.addAd method");
		try {
			// 文件上传路径
			String goodsPicPath = "ad" + Constants.FILE_SEP
					+ DateUtil.getDateTime("yyyyMM", new Date());

			AdDetail adDetailnew = this.adDetailDao.getAdDetailByAdId(ad
					.getId());
			if (files != null && files.size() > 0) {
				if (adDetailnew != null) {
					uploadUtil.deleteFile("ad", adDetailnew.getMediaCode());
				}
				int i = 0;
				for (MultipartFile file : files) {
					if (file.getSize() > 0) {
						String fileName = uploadUtil.newUpload(file,
								goodsPicPath);
						if (i == 0) {
							adDetail.setMediaCode(goodsPicPath
									+ Constants.FILE_SEP + fileName);
						}
						i++;
					}
				}
			}

			// sort
			adDetail.setSort(ad.getSort());
			Integer sort = this.adDao.getAdMaxSort();
			int max = 0;
			if (sort != null) {
				max = sort.intValue();
			}
			if (adDetailnew != null) {
				if (adDetail.getSort() != adDetailnew.getSort()) {
					if (adDetail.getSort() > max) {
						adDetail.setSort(max);
						this.adDao.updateAdSortlow(adDetail.getSort(),
								adDetailnew.getSort());
					} else {
						if (adDetail.getSort() < adDetailnew.getSort()) {
							this.adDao.updateAdSortUpdtae(adDetail.getSort(),
									adDetailnew.getSort());
						}
						if (adDetail.getSort() > adDetailnew.getSort()) {
							this.adDao.updateAdSortlow(adDetail.getSort(),
									adDetailnew.getSort());
						}
					}
				}
			}
			this.adDao.updateAd(ad);
			Long id = ad.getId();
			if (id != null && id != 0L) {
				adDetail.setAdId(id);
				// AdDetail adDetailnew = adDetailDao.getAdDetailByAdId(id);
				if (adDetailnew == null) {
					this.adDetailDao.addAdDetail(adDetail);
				} else {
					this.adDetailDao.updateAdDetail(adDetail);
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new ManagerException(e);
		}
	}

	// public PageUtil<Ad> getAdsPage(int currentPage, int pageSize,Ad ad){
	// try {
	// Integer count = this.adDao.getAdsCount(ad);
	// return this.adDao.getAdsPage( currentPage, pageSize,
	// count.intValue(),ad);
	// } catch (Exception e) {
	// log.error(e.getMessage());
	// throw new ManagerException(e);
	// }
	// }
	@Override
	public Map<String, Object> getAdInfo(Map parMap) {
		try {
			List<Ad> ad = new ArrayList<Ad>();
			List<AdPosition> adp = new ArrayList<AdPosition>();
			Map<String, Object> map = new HashMap<String, Object>();
			ad = this.adDao.getAddInfo(parMap);
			adp = this.adPositionDao.getAdPositions();
			map.put("ad", ad);
			map.put("adp", adp);
			return map;
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new ManagerException(e);
		}
	}
}
