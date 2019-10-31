package com.huaixuan.network.biz.service.trade.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.trade.PayRecordDao;
import com.huaixuan.network.biz.domain.stock.ShoppingList;
import com.huaixuan.network.biz.domain.trade.PayRecord;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.query.TradeListQuery;
import com.huaixuan.network.biz.service.trade.PayRecordManager;

@Service("payRecordManager")
public class PayRecordManagerImpl implements PayRecordManager {

    protected Log        log = LogFactory.getLog(this.getClass());

    @Autowired
    private PayRecordDao payRecordDao;

    @Override
    public void addPayRecord(PayRecord payRecordDao) {
        log.info("PayRecordManagerImpl.addPayRecord method");
        try {
            this.payRecordDao.addPayRecord(payRecordDao);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public void editPayRecord(PayRecord payRecord) {
        log.info("PayRecordManagerImpl.editPayRecord method");
        try {
            this.payRecordDao.editPayRecord(payRecord);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public void removePayRecord(Long payRecordId) {
        log.info("PayRecordManagerImpl.removePayRecord method");
        try {
            this.payRecordDao.removePayRecord(payRecordId);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public PayRecord getPayRecord(Long payRecordId) {
        log.info("PayRecordManagerImpl.getPayRecord method");
        try {
            return this.payRecordDao.getPayRecord(payRecordId);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    @Override
    public List<PayRecord> getPayRecords() {
        log.info("PayRecordManagerImpl.getPayRecords method");
        try {
            return this.payRecordDao.getPayRecords();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    @Override
    public void updatePayRecordByNotify(PayRecord payRecord) {
        this.payRecordDao.updatePayRecordByNotify(payRecord);
    }

    @Override
    public PayRecord getPayRecordByTid(String tid) {
        return this.payRecordDao.getPayRecordByTid(tid);
    }

    @SuppressWarnings("unchecked")
	@Override
    public QueryPage getPayRecordLists(TradeListQuery tradeListQuery, int currentPage, int pageSize)throws Exception {

        QueryPage queryPage = new QueryPage(tradeListQuery);
		Map pramas = queryPage.getParameters();

		int count = payRecordDao.getPayRecordListsCount(pramas);

		if (count > 0) {

			/* 当前页 */
			queryPage.setCurrentPage(currentPage);
			/* 每页总数 */
			queryPage.setPageSize(pageSize);
			queryPage.setTotalItem(count);

			pramas.put("startRow", queryPage.getPageFristItem());
			pramas.put("endRow", queryPage.getPageLastItem());

			/* 分页查询操作员记录 */
			List<PayRecord> payRecords = payRecordDao.getPayRecordLists(pramas);

			if (payRecords != null
					&& payRecords.size() > 0) {
				queryPage.setItems(payRecords);
			}
		}
		return queryPage;
    }

//    @Override
//    public int getPayRecordListsCount(Map parMap) {
//        log.info("PayRecordManagerImpl.getPayRecordListsCount method");
//        try {
//            return this.payRecordDao.getPayRecordListsCount(parMap);
//        } catch (Exception e) {
//            log.error(e.getMessage());
//        }
//        return 0;
//    }

    @Override
    public PayRecord getPayAmountByPlatform(TradeListQuery tradeListQuery) {
        log.info("PayRecordManagerImpl.getPayAmountByPlatform method");
        try {
            return this.payRecordDao.getPayAmountByPlatform(tradeListQuery);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    @Override
    public int getCodPayRecordListsCount(Map parMap) {
        log.info("PayRecordManagerImpl.getCodPayRecordListsCount method");
        try {
            return this.payRecordDao.getCodPayRecordListsCount(parMap);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return 0;
    }

    @Override
    public PayRecord getPayAmountByCod(Map parMap) {
        log.info("PayRecordManagerImpl.getPayAmountByCod method");
        try {
            return this.payRecordDao.getPayAmountByCod(parMap);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    //    public List<PayRecord> getPayRecordListsByCod(Map parMap, Page page) {
    //        log.info("PayRecordManagerImpl.getPayRecordListsByCod method");
    //        try {
    //            return this.payRecordDao.getPayRecordListsByCod(parMap, page);
    //        } catch (Exception e) {
    //            log.error(e.getMessage());
    //        }
    //        return null;
    //    }
}
