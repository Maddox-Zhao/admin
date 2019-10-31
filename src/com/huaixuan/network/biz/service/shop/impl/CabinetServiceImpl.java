package com.huaixuan.network.biz.service.shop.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.huaixuan.network.biz.dao.goods.CategoryDao;
import com.huaixuan.network.biz.dao.goods.GoodsDao;
import com.huaixuan.network.biz.dao.goods.GoodsWholsaleDao;
import com.huaixuan.network.biz.dao.shop.CabinetDao;
import com.huaixuan.network.biz.dao.shop.ShowcaseDao;
import com.huaixuan.network.biz.domain.base.Constants;
import com.huaixuan.network.biz.domain.goods.Category;
import com.huaixuan.network.biz.domain.goods.Goods;
import com.huaixuan.network.biz.domain.goods.GoodsWholsale;
import com.huaixuan.network.biz.domain.shop.Cabinet;
import com.huaixuan.network.biz.domain.shop.Showcase;
import com.huaixuan.network.biz.exception.ManagerException;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.service.goods.UploadUtil;
import com.huaixuan.network.biz.service.shop.CabinetService;
import com.huaixuan.network.common.util.DateUtil;
import com.hundsun.common.lang.StringUtil;

@Service("cabinetService")
public class CabinetServiceImpl implements CabinetService {

	Log log = LogFactory.getLog(this.getClass());
	@Autowired
	private CabinetDao cabinetDao;
	@Autowired
	private ShowcaseDao showcaseDao;
	@Autowired
	private CategoryDao categoryDao;
	@Autowired
	private GoodsDao goodsDao;
	@Autowired
	private GoodsWholsaleDao goodsWholsaleDao;
	@Autowired
    private UploadUtil uploadUtil;

	@Override
	public Long addCabinet(Cabinet cabinet) throws Exception {
		log.info("CabinetManagerImpl.addCabinet method");
		Long id = null;
		Integer max = this.cabinetDao.getCabinetMaxSort();
		int sort = 0;
		if (max == null) {
			sort = 0;
		} else {
			sort = max.intValue();
		}
		cabinet.setSort(sort + 1);
		id = this.cabinetDao.addCabinet(cabinet);

		return id;
	}

	@Override
	public Long addCabinet(Cabinet cabinet, List<MultipartFile> files) throws Exception {
        log.info("CabinetManagerImpl.addCabinet method");
        String picPath = "cabinet" + Constants.FILE_SEP
                              + DateUtil.getDateTime("yyyyMM", new Date());
        int i = 0;
        if (files != null && files.size() > 0) {
            for (MultipartFile file : files) {
                if (file.getSize() > 0) {
                    String fileName = uploadUtil.newUpload(file, picPath);
                    if (i == 0) {
                        cabinet.setCabinetPic(picPath + Constants.FILE_SEP + fileName);

                    }
                    i++;
                }
            }
        }
	    Long id = null;
	    Integer max = this.cabinetDao.getCabinetMaxSort();
	    int sort = 0;
	    if (max == null) {
	        sort = 0;
	    } else {
	        sort = max.intValue();
	    }
	    cabinet.setSort(sort + 1);
	    id = this.cabinetDao.addCabinet(cabinet);

	    return id;
	}

	@Override
	public void editCabinet(Cabinet cabinet) throws Exception {
		log.info("CabinetManagerImpl.editCabinet method");

		int sort = (this.cabinetDao.getCabinetMaxSort()).intValue();
		Cabinet cabinetold = this.cabinetDao.getCabinet(cabinet.getId());
		if (cabinet.getSort() != cabinetold.getSort()) {
			if (cabinet.getSort() > sort) {
				cabinet.setSort(sort);
				this.cabinetDao.updateCabinetSortlow(cabinet.getSort(),
						cabinetold.getSort());
			} else {
				if (cabinet.getSort() < cabinetold.getSort()) {
					this.cabinetDao.updateCabinetSortUpdtae(cabinet.getSort(),
							cabinetold.getSort());
				}
				if (cabinet.getSort() > cabinetold.getSort()) {
					this.cabinetDao.updateCabinetSortlow(cabinet.getSort(),
							cabinetold.getSort());
				}
			}
		}
		this.cabinetDao.editCabinet(cabinet);

	}

	@Override
	public void editCabinet(Cabinet cabinet, List<MultipartFile> files) throws Exception {
	    log.info("CabinetManagerImpl.editCabinet method");

        String picPath = "cabinet" + Constants.FILE_SEP
                         + DateUtil.getDateTime("yyyyMM", new Date());
        int i = 0;
        if (files != null && files.size() > 0) {
            for (MultipartFile file : files) {
                if (file.getSize() > 0) {
                    String fileName = uploadUtil.newUpload(file, picPath);
                    if (i == 0) {
                        cabinet.setCabinetPic(picPath + Constants.FILE_SEP + fileName);
                        ;
                    }
                    i++;
                }
            }
        } else {
            cabinet.setCabinetPic("");
        }

	    int sort = (this.cabinetDao.getCabinetMaxSort()).intValue();
	    Cabinet cabinetold = this.cabinetDao.getCabinet(cabinet.getId());
	    if (cabinet.getSort() != cabinetold.getSort()) {
	        if (cabinet.getSort() > sort) {
	            cabinet.setSort(sort);
	            this.cabinetDao.updateCabinetSortlow(cabinet.getSort(),
	                cabinetold.getSort());
	        } else {
	            if (cabinet.getSort() < cabinetold.getSort()) {
	                this.cabinetDao.updateCabinetSortUpdtae(cabinet.getSort(),
	                    cabinetold.getSort());
	            }
	            if (cabinet.getSort() > cabinetold.getSort()) {
	                this.cabinetDao.updateCabinetSortlow(cabinet.getSort(),
	                    cabinetold.getSort());
	            }
	        }
	    }
	    this.cabinetDao.editCabinet(cabinet);

	}

	@Override
	public void removeCabinet(Long cabinetId) throws Exception {
		log.info("CabinetManagerImpl.removeCabinet method");

		this.cabinetDao.removeCabinet(cabinetId);

	}

	@Override
	public Cabinet getCabinet(Long cabinetId) throws Exception {
		log.info("CabinetManagerImpl.getCabinet method");

		Cabinet cabinetnew = this.cabinetDao.getCabinet(cabinetId);
		List<Showcase> showcaseList = this.showcaseDao
				.getShowcasesByCabinetId(cabinetnew.getId());
		if (showcaseList != null && showcaseList.size() > 0) {
			cabinetnew.setShowcases(showcaseList);
		}
		return cabinetnew;

	}

	@Override
	public List<Cabinet> getCabinets() throws Exception {
		log.info("CabinetManagerImpl.getCabinets method");

		List<Cabinet> cabinetList = new ArrayList<Cabinet>();
		cabinetList = this.cabinetDao.getCabinets();
		if (cabinetList != null && cabinetList.size() > 0) {
			for (int i = 0; i < cabinetList.size(); i++) {
				Cabinet cabinet = cabinetList.get(i);
				cabinet.setCabinetKeyword(StringUtils.substringAfterLast(
						cabinet.getCabinetUrl(), "="));
				if (cabinet != null) {
					List<Showcase> showcaseList = this.showcaseDao
							.getShowcasesByCabinetId(cabinet.getId());
					if (showcaseList != null && showcaseList.size() > 0) {
						cabinet.setShowcases(showcaseList);
					}
				}
			}
		}
		return cabinetList;

	}

	@Override
	public List<Category> getCategorysList(int i) throws Exception {
		log.info("CabinetManagerImpl.getCategorysList method");

		return this.categoryDao.getCategoryInfoByDepth(i);

	}

	@SuppressWarnings("unchecked")
	@Override
	public QueryPage showcaseGoods(String catCode, String goodsName,
			String goodsSn, double priceLow, double priceHigh, int currentPage,
			int pageSize, String selectSort, String isCutprice, Long goodsNumber)
			throws Exception {
		log.info("ShowcaseManagerImpl.showcaseGoods method");

		try {
			QueryPage queryPage = new QueryPage("par");
			Map prama = queryPage.getParameters();
			if (StringUtil.isNotBlank(catCode)) {
				prama.put("catCode", catCode + "%");
			}
			if (StringUtil.isNotBlank(goodsName)) {
				prama.put("goodsName", goodsName);
			}
			if (StringUtil.isNotBlank(goodsName)) {
				prama.put("goodsNamelike", "%" + goodsName + "%");
			}
				prama.put("priceLow", priceLow);
				prama.put("priceHigh", priceHigh);
			if (StringUtil.isNotBlank(goodsSn)) {
				prama.put("goodsSn", goodsSn);
			}
			if (StringUtil.isNotBlank(goodsSn)) {
				prama.put("goodsSnlike", "%" + goodsSn + "%");
			}
			if (StringUtil.isNotBlank(selectSort)) {
				prama.put("selectSort", selectSort);
			}
			if (StringUtil.isNotBlank(isCutprice)) {
				prama.put("isCutprice", isCutprice);
			}
				prama.put("goodsNumber", goodsNumber);
			if (priceLow == 0.0) {
				prama.put("priceLowstr", "");
			} else {
				prama.put("priceLowstr", "yes");
			}

			if (priceHigh == 0.0) {
				prama.put("priceHighstr", "");
			} else {
				prama.put("priceHighstr", "yes");
			}
			Integer count = this.goodsDao.getshowcaseGoodsCount(prama);

			if (count > 0) {

				/* 当前页 */
				queryPage.setCurrentPage(currentPage);
				/* 每页总数 */
				queryPage.setPageSize(pageSize);
				queryPage.setTotalItem(count);

				prama.put("startRow",
						queryPage.getPageFristItem());
				prama.put("endRow", queryPage.getPageLastItem());

				/* 分页查询操作员记录 */
				List<Goods> goodsList = this.goodsDao.showcaseGoods(prama);
				if (goodsList != null && goodsList.size() > 0) {
					queryPage.setItems(goodsList);
				}
			}
			return queryPage;
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new ManagerException(e);
		}

	}

	@Override
	@SuppressWarnings("unchecked")
	public Map<String, Object> getCabAndGoodsInfo() throws Exception {
		List<Cabinet> listOfCab = new ArrayList<Cabinet>();
		List<Goods> listGoods = new ArrayList<Goods>();
		Map map = new HashMap<String, Object>();
		listOfCab = cabinetDao.getCabinetForSort();
		for (Cabinet c : listOfCab) {
			int count = this.cabinetDao.getGoodsAmountOfTheCabinet(c.getId());
			c.setGoodsAmount(count);
		}
		listGoods = goodsDao.getGoodsAllForCabinet();
		// for (Goods goods : listGoods) {
		// String title = StringEscapeUtil.escapeHtml(goods.getTitle());
		// /*title = StringUtil.abbreviate(title, 20);
		// goods.setTitle(title);*/
		// }
		map.put("listOfCab", listOfCab);
		map.put("listGoods", listGoods);
		return map;
	}

	@Override
	public List<Goods> getCabGoodsByName(String name) throws Exception {
		List<Goods> listGoods = new ArrayList<Goods>();
		listGoods = cabinetDao.getCabGoodsByName(name);
		return listGoods;
	}

	@Override
	public Map<String, Object> getCabAndGoodInfoByName(String name)
			throws Exception {
		Cabinet cabinet = new Cabinet();
		List<Goods> listGoods = new ArrayList<Goods>();
		Map map = new HashMap<String, Object>();
		cabinet = cabinetDao.getCabinetByName(name);
		if (cabinet != null) {
			int count = this.cabinetDao.getGoodsAmountOfTheCabinet(cabinet
					.getId());
			cabinet.setGoodsAmount(count);
		}
		listGoods = goodsDao.getGoodsAllForCabinet();
		for (Goods goods : listGoods) {
			// ****************** String title =
			// StringEscapeUtil.escapeHtml(goods.getTitle());
			/*
			 * title = StringUtil.abbreviate(title, 20); goods.setTitle(title);
			 */
		}
		map.put("Cab", cabinet);
		map.put("listGoods", listGoods);
		return map;
	}

	@Override
	public Map<String, Object> getCabAndGoodsInfoByWholeSale() throws Exception {
		List<Cabinet> listOfCab = new ArrayList<Cabinet>();
		List<Goods> listGoods = new ArrayList<Goods>();
		Map map = new HashMap<String, Object>();
		listOfCab = cabinetDao.getCabinetForSort();
		for (Cabinet c : listOfCab) {
			int count = this.cabinetDao.getGoodsAmountOfTheCabinet(c.getId());
			c.setGoodsAmount(count);
		}
		listGoods = goodsDao.getGoodsAllForCabinetByWholeSale();
		if (listGoods != null && listGoods.size() > 0) {
			for (Goods g : listGoods) {
				List<GoodsWholsale> gwl = goodsWholsaleDao
						.getGoodsWholsalelistByGoodsId(g.getId());
				if (gwl != null && gwl.size() > 0) {
					for (GoodsWholsale qw : gwl) {
						if (qw.getWholesaleLevel() == 1) {
							g.setCabinetWholePrice(qw.getWholesalePrice());
						}
					}
				}
			}
		}
		// for (Goods goods : listGoods) {
		// String title = StringEscapeUtil.escapeHtml(goods.getTitle());
		// /*title = StringUtil.abbreviate(title, 20);
		// goods.setTitle(title);*/
		// }
		map.put("listOfCab", listOfCab);
		map.put("listGoods", listGoods);
		return map;
	}

}
