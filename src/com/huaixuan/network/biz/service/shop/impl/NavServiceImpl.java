package com.huaixuan.network.biz.service.shop.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.shop.NavDao;
import com.huaixuan.network.biz.domain.shop.Ad;
import com.huaixuan.network.biz.domain.shop.Nav;
import com.huaixuan.network.biz.exception.ManagerException;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.service.shop.NavService;

@Service("navService")
public class NavServiceImpl implements NavService {
	Log log = LogFactory.getLog(this.getClass());

	@Autowired
	public NavDao navDao;

	@Override
	public void addNav(Nav nav) {
		log.info("NavManagerImpl.addNav method");
		try {
			Integer max = this.navDao.getNavsMax(nav.getType());
			int sort = 1;
			if (max != null) {
				sort = max.intValue() + 1;
			}
			nav.setSort(sort);
			this.navDao.addNav(nav);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}

	@Override
	public void editNav(Nav nav) {
		log.info("NavManagerImpl.editNav method");
		try {
			Nav navold = this.navDao.getNav(nav.getId());

			Integer maxold = this.navDao.getNavsMax(navold.getType());
			Integer max = this.navDao.getNavsMax(nav.getType());
			if (maxold == null) {
				maxold = 0;
			}
			if (max == null) {
				max = 0;
			}
			if (navold.getType().equals(nav.getType())) {
				if (navold.getSort() != nav.getSort()) {
					if (nav.getSort() >= maxold.intValue()) {
						this.navDao.getNavsSortsamll(navold.getSort(),
								nav.getType());
						nav.setSort(maxold.intValue());
					} else if (nav.getSort() < navold.getSort()) {
						this.navDao.getNavsSortBig(nav.getSort(),
								navold.getSort(), nav.getType());
					} else if (nav.getSort() > navold.getSort()) {
						this.navDao.getNavsSortBwteenSmall(nav.getSort(),
								navold.getSort(), nav.getType());
					}
				}
			} else {
				this.navDao
						.getNavsSortsamll(navold.getSort(), navold.getType());
				if (nav.getSort() > max.intValue()) {
					nav.setSort(max.intValue() + 1);
				} else {
					this.navDao.getNavsSortBig(nav.getSort(),
							max.intValue() + 1, nav.getType());
				}
			}

			this.navDao.editNav(nav);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}

	@Override
	public void removeNav(Long navId) {
		log.info("NavManagerImpl.removeNav method");
		try {
			Nav navold = this.navDao.getNav(navId);
			this.navDao.getNavsSortsamll(navold.getSort(), navold.getType());
			this.navDao.removeNav(navId);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}

	@Override
	public Nav getNav(Long navId) {
		log.info("NavManagerImpl.getNav method");
		try {
			return this.navDao.getNav(navId);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}

	@Override
	public List<Nav> getNavs() {
		log.info("NavManagerImpl.getNavs method");
		try {
			return this.navDao.getNavs();
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}

	@Override
	public Map<String, List<Nav>> getNavForShow() throws Exception {
		List<Nav> top = this.navDao.getNavByType("top");
		List<Nav> middle = this.navDao.getNavByType("middle");
		List<Nav> bottom = this.navDao.getNavByType("bottom");
		Map<String, List<Nav>> map = new HashMap<String, List<Nav>>();
		map.put("top", top);
		map.put("middle", middle);
		map.put("bottom", bottom);
		return map;
	}

	@SuppressWarnings("unchecked")
	@Override
	public QueryPage getNavsPage(long shopId, int currentPage, int pageSize) {
		try {
			QueryPage queryPage = new QueryPage(shopId);
			Map pramas = queryPage.getParameters();

			Integer count = this.navDao.getNavsCount(shopId);

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
				List<Nav> navList = this.navDao.getNavsPage(pramas);
				if (navList != null && navList.size() > 0) {
					queryPage.setItems(navList);
				}
			}
			return queryPage;
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new ManagerException(e);
		}
	}

	@Override
	public void isshowNav(Nav newNav) {
		log.info("NoticeManagerImpl.isshowNav method");
		try {
			this.navDao.isshowNav(newNav);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new ManagerException(e);
		}

	}

	@Override
	public void isOpenNav(Nav newNav) {
		log.info("NoticeManagerImpl.isOpenNav method");
		try {
			this.navDao.isOpenNav(newNav);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new ManagerException(e);
		}

	}

}
