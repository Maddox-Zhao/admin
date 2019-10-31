package com.huaixuan.network.biz.service.shop.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.shop.ShowcaseDao;
import com.huaixuan.network.biz.domain.shop.Showcase;
import com.huaixuan.network.biz.exception.ManagerException;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.service.shop.ShowcaseService;

@Service("showcaseService")
public class ShowcaseServiceImpl implements ShowcaseService {
	Log log = LogFactory.getLog(this.getClass());

	@Autowired
	public ShowcaseDao showcaseDao;

	@Override
	public Long addShowcase(Showcase showcaseDao) throws Exception {
		log.info("ShowcaseManagerImpl.addShowcase method");
		Long id = null;

		id = this.showcaseDao.addShowcase(showcaseDao);

		return id;
	}

	@Override
	public void editShowcase(Showcase showcase) throws Exception {
		log.info("ShowcaseManagerImpl.editShowcase method");

		this.showcaseDao.editShowcase(showcase);

	}

	@Override
	public void removeShowcase(Long showcaseId) throws Exception {
		log.info("ShowcaseManagerImpl.removeShowcase method");

		Showcase showcasenew = this.showcaseDao.getShowcase(showcaseId);
		if (showcasenew != null) {
			int max = 0;
			Integer sort = this.showcaseDao
					.getShowcaseMaxSortByCabinetId(showcasenew.getCabinetId());
			if (sort != null) {
				max = sort.intValue();
			}
			if (showcasenew.getSort() < max) {
				this.showcaseDao.updateShowcaseSortByCabinetId(showcasenew);
			}
		}
		this.showcaseDao.removeShowcase(showcaseId);

	}

	@Override
	public Showcase getShowcase(Long showcaseId) throws Exception {
		log.info("ShowcaseManagerImpl.getShowcase method");

		return this.showcaseDao.getShowcase(showcaseId);

	}

	@Override
	public List<Showcase> getShowcases() throws Exception {
		log.info("ShowcaseManagerImpl.getShowcases method");

		return this.showcaseDao.getShowcases();

	}

	@Override
	public void showcaseUp(Long showcaseId) throws Exception {
		log.info("ShowcaseManagerImpl.showcaseUp method");

		Showcase showcasenew = this.showcaseDao.getShowcase(showcaseId);
		int sort = showcasenew.getSort();
		this.showcaseDao.showcaseUpSamll(showcasenew.getCabinetId(), sort - 1);
		this.showcaseDao.showcaseUpBig(showcaseId);

	}

	@Override
	public void showcaseDown(Long showcaseId) throws Exception {
		log.info("ShowcaseManagerImpl.showcaseUp method");

		Showcase showcasenew = this.showcaseDao.getShowcase(showcaseId);
		int sort = showcasenew.getSort();
		this.showcaseDao
				.showcaseDownSamll(showcasenew.getCabinetId(), sort + 1);
		this.showcaseDao.showcaseDownBig(showcaseId);

	}

	@Override
	public void addGoodsToCabinet(List<Long> goodsIds, Long cabId)
			throws Exception {
		log.info("ShowcaseManagerImpl.addGoodsToCabinet method");
		if (goodsIds != null && goodsIds.size() > 0) {
			for (int i = 0; i < goodsIds.size(); i++) {
				Showcase showcaseold = this.showcaseDao
						.getShowcaseByGoodsIdAndCabId(goodsIds.get(i), cabId);
				if (showcaseold == null) {
					int max = 0;
					Integer sort = this.showcaseDao
							.getShowcaseMaxSortByCabinetId(cabId);
					if (sort != null) {
						max = sort.intValue();
					}
					Showcase showcase = new Showcase();
					showcase.setCabinetId(cabId);
					showcase.setGoodsId(goodsIds.get(i));
					showcase.setSort(max + 1);
					this.showcaseDao.addShowcase(showcase);
				}
			}
		}

	}

	@Override
	public QueryPage getShowcasesPage(long cabinetId, int currentPage,
			int pageSize) throws Exception {
		try {
			QueryPage queryPage = new QueryPage(cabinetId);
			Map pramas = new HashMap();
			pramas.put("cabinetId", cabinetId);
			Integer count = showcaseDao.getShowcasesCount(cabinetId);

			if (count > 0) {

				/* 当前页 */
				queryPage.setCurrentPage(currentPage);
				/* 每页总数 */
				queryPage.setPageSize(pageSize);
				queryPage.setTotalItem(count);

				pramas.put("startRow",
						queryPage.getPageFristItem());
				pramas.put("endRow",
						queryPage.getPageLastItem());

				/* 分页查询操作员记录 */
				List<Showcase> showcaseList = this.showcaseDao
						.getShowcasesPage(pramas);
				if (showcaseList != null && showcaseList.size() > 0) {
					queryPage.setItems(showcaseList);
				}
			}
			return queryPage;
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new ManagerException(e);
		}

	}

	@Override
	public int getGoodsNumByCabId(long cabId) throws Exception {
		Integer num = this.showcaseDao.getShowcasesCount(cabId);
		int goodsnum = 0;
		if (num != null) {
			goodsnum = num.intValue();
		}
		return goodsnum;
	}

	@Override
	public Showcase getShowcaseByGoodsIdAndCabId(Long goodsId, Long cabId)
			throws Exception {

		return this.showcaseDao.getShowcaseByGoodsIdAndCabId(goodsId, cabId);
	}

}
