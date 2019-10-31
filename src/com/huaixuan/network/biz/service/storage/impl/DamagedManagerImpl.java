package com.huaixuan.network.biz.service.storage.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.storage.DamagedDao;
import com.huaixuan.network.biz.domain.storage.Damaged;
import com.huaixuan.network.biz.domain.storage.query.DamagedQuery;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.service.storage.DamagedManager;

/**
 * bibleUtil auto code generation)
 *
 * @version 3.2.0
 */
@Service("damagedManager")
public class DamagedManagerImpl implements DamagedManager {

	@Autowired
	DamagedDao damagedDao;

	protected final Log log = LogFactory.getLog(getClass());

	@Override
	public long addDamaged(Damaged damaged) {
		log.info("DamagedManagerImpl.addDamaged method");
		try {
			return this.damagedDao.addDamaged(damaged);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return 0;
	}

	@Override
	public void editDamaged(Damaged damaged) {
		log.info("DamagedManagerImpl.editDamaged method");
		try {
			this.damagedDao.editDamaged(damaged);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}

	@Override
	public void removeDamaged(Long damagedId) {
		log.info("DamagedManagerImpl.removeDamaged method");
		try {
			this.damagedDao.removeDamaged(damagedId);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}

	@Override
	public Damaged getDamaged(Map parMap) {
		log.info("DamagedManagerImpl.getDamaged method");
		try {
			return this.damagedDao.getDamaged(parMap);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}

	@Override
	public List<Damaged> getDamageds() {
		log.info("DamagedManagerImpl.getDamageds method");
		try {
			return this.damagedDao.getDamageds();
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}

	@Override
	public int getDamagedCountByParameterMap(Map<String, String> parMap) {
		try {
			return damagedDao.getDamagedCountByParameterMap(parMap);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return 0;
	}

	@SuppressWarnings("unchecked")
	@Override
	public QueryPage getDamagedListsByParameterMap(DamagedQuery damagedQuery,
			int currPage, int pageSize) {
		log.info("DamagedManagerImpl.getDamagedListsByParameterMap method");
		try {
			QueryPage queryPage = new QueryPage(damagedQuery);
			Map pramas = queryPage.getParameters();
			pramas.put("depfirstIds", damagedQuery.getDepFirstIds());
			int count = damagedDao.getDamagedCountByParameterMap(pramas);

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
				List<Damaged> damagedList = damagedDao
						.getDamagedListsByParameterMap(pramas);
				if (damagedList != null && damagedList.size() > 0) {
					queryPage.setItems(damagedList);
				}
			}
			return queryPage;
		} catch (Exception e) {
			// log.error(e.getMessage());
			// throw new ManagerException(e);
			return null;
		}

	}
}
