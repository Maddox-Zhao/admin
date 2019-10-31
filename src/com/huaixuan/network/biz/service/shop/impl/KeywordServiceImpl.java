package com.huaixuan.network.biz.service.shop.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.shop.KeywordDao;
import com.huaixuan.network.biz.domain.shop.Keyword;
import com.huaixuan.network.biz.exception.ManagerException;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.service.shop.KeywordService;

@Service("keywordService")
public class KeywordServiceImpl implements KeywordService {
	Log log = LogFactory.getLog(this.getClass());

	@Autowired
	public KeywordDao keywordDao;

	@Override
	public Long addKeyword(Keyword keyword) {
		log.info("KeywordManagerImpl.addKeyword method");
		try {
			/* todo url */
			Long id = null;
			Long sort = this.keywordDao.getKeywordMaxSort(keyword.getShopId());
			int max = 0;
			if (sort != null) {
				max = sort.intValue();
			}
			if (keyword.getSort() > max) {
				keyword.setSort(max + 1);
			} else {
				this.keywordDao.updateKeywordSort(keyword.getSort(),
						keyword.getShopId());
			}
			id = this.keywordDao.addKeyword(keyword);

			return id;
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new ManagerException(e);
		}
	}

	@Override
	public void editKeyword(Keyword keyword) {
		log.info("KeywordManagerImpl.editKeyword method");
		try {
			/* todo url */
			Keyword keywordnew = this.keywordDao.getKeyword(keyword.getId());
			Long sort = this.keywordDao.getKeywordMaxSort(keywordnew
					.getShopId());
			int max = 0;
			if (sort != null) {
				max = sort.intValue();
			}
			Keyword keywordold = this.keywordDao.getKeyword(keyword.getId());
			if (keyword.getSort() != keywordold.getSort()) {
				if (keyword.getSort() > max) {
					keyword.setSort(max);
					this.keywordDao.updateKeywordSortlow(keyword.getSort(),
							keywordnew.getShopId(), keywordold.getSort());
				} else {
					if (keyword.getSort() < keywordold.getSort()) {
						this.keywordDao.updateKeywordSortUpdtae(
								keyword.getSort(), keywordnew.getShopId(),
								keywordold.getSort());
					}
					if (keyword.getSort() > keywordold.getSort()) {
						this.keywordDao.updateKeywordSortlow(keyword.getSort(),
								keywordnew.getShopId(), keywordold.getSort());
					}
				}
			}
			this.keywordDao.editKeyword(keyword);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new ManagerException(e);
		}

	}

	@Override
	public void removeKeyword(Long keywordId) {
		log.info("KeywordManagerImpl.removeKeyword method");
		try {
			Keyword keywordnew = this.keywordDao.getKeyword(keywordId);
			int max = (this.keywordDao
					.getKeywordMaxSort(keywordnew.getShopId())).intValue();

			if (keywordnew.getSort() < max) {
				this.keywordDao.updateKeywordSortsamll(keywordnew.getSort(),
						keywordnew.getShopId());
			}
			this.keywordDao.removeKeyword(keywordId);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new ManagerException(e);
		}

	}

	@Override
	public Keyword getKeyword(Long keywordId) {
		log.info("KeywordManagerImpl.getKeyword method");
		try {
			return this.keywordDao.getKeyword(keywordId);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new ManagerException(e);
		}
	}

	@Override
	public List<Keyword> getKeywords() {
		log.info("KeywordManagerImpl.getKeywords method");
		try {
			return this.keywordDao.getKeywords();
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new ManagerException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public QueryPage getKeywordsPage(long shopId, int currentPage, int pageSize) {
		try {
			QueryPage queryPage = new QueryPage(shopId);
			Map pramas = queryPage.getParameters();

			Integer count = this.keywordDao.getKeywordsCount(shopId);

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
				List<Keyword> keywordList = this.keywordDao
						.getKeywordsPage(pramas);
				if (keywordList != null && keywordList.size() > 0) {
					queryPage.setItems(keywordList);
				}
			}
			return queryPage;
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new ManagerException(e);
		}
	}
}
