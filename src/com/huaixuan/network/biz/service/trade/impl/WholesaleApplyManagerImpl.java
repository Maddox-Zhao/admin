package com.huaixuan.network.biz.service.trade.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.huaixuan.network.biz.dao.trade.OrderDao;
import com.huaixuan.network.biz.dao.trade.TradeDao;
import com.huaixuan.network.biz.dao.trade.WholesaleApplyDao;
import com.huaixuan.network.biz.dao.trade.WholesaleApplyDetailDao;
import com.huaixuan.network.biz.domain.trade.Order;
import com.huaixuan.network.biz.domain.trade.Trade;
import com.huaixuan.network.biz.domain.trade.WholesaleApply;
import com.huaixuan.network.biz.domain.trade.WholesaleApplyDetail;
import com.huaixuan.network.biz.enums.EnumTradeStatus;
import com.huaixuan.network.biz.enums.EnumWholesaleStatus;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.service.trade.WholesaleApplyManager;

@Service("wholesaleApplyManager")
public class WholesaleApplyManagerImpl implements WholesaleApplyManager {
    protected Log                   log = LogFactory.getLog(this.getClass());

    @Autowired
    private WholesaleApplyDao       wholesaleApplyDao;
    @Autowired
    private WholesaleApplyDetailDao wholesaleApplyDetailDao;
    @Autowired
    private OrderDao                orderDao;
    @Autowired
    private TradeDao                tradeDao;

    //    @Autowired
    //    private InAndOutDepositoryManager inAndOutDepositoryManager;

    @Transactional
//    @Override
//    public long addWholesaleApply(WholesaleApply wholesaleApplyDao) {
//        log.info("WholesaleApplyManagerImpl.addWholesaleApply method");
//        long result = 0;
//        try {
//            wholesaleApplyDao.setStatus(EnumWholesaleStatus.WHOLESALE_STATUS_NEW.getKey());
//            result = this.wholesaleApplyDao.addWholesaleApply(wholesaleApplyDao);
//            if (result > 0) {
//                // 增加批发申请商品信息
//                List<Order> orderList = orderDao.getOrdersByTid(wholesaleApplyDao.getTid());
//                if (orderList != null && orderList.size() > 0) {
//                    WholesaleApplyDetail wDetail = null;
//                    for (Order obj : orderList) {
//                        wDetail = new WholesaleApplyDetail();
//                        wDetail.setApplyNumber(obj.getGoodsNumber());
//                        wDetail.setGoodsInstanceId(obj.getGoodsInstanceId());
//                        wDetail.setWholesaleApplyId(result);
//                        wholesaleApplyDetailDao.addWholesaleApplyDetail(wDetail);
//                    }
//                }
//            } else {
//                result = 0;
//                throw new Exception();
//            }
//
//            Trade trade = tradeDao.getTradeByTid(wholesaleApplyDao.getTid());
//            String[] tIds = new String[1];
//            if (trade == null) {
//                tIds[0] = trade.getTid();
//                result = 0;
//                throw new Exception();
//            }
//            // 添加出库单
//            inAndOutDepositoryManager.addOutDepository(tIds);
//            // 修改订单状态
//            trade.setStatus(EnumTradeStatus.WAIT_DISTRIBUTION.getKey());
//            trade.setGmtModify(new Date());
//            tradeDao.editTrade(trade);
//        } catch (Exception e) {
//            log.error(e.getMessage());
//        }
//        return result;
//    }

    @Override
    public void editWholesaleApply(WholesaleApply wholesaleApply) {
        log.info("WholesaleApplyManagerImpl.editWholesaleApply method");
        try {
            this.wholesaleApplyDao.editWholesaleApply(wholesaleApply);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public void removeWholesaleApply(Long wholesaleApplyId) {
        log.info("WholesaleApplyManagerImpl.removeWholesaleApply method");
        try {
            this.wholesaleApplyDao.removeWholesaleApply(wholesaleApplyId);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public WholesaleApply getWholesaleApply(Long wholesaleApplyId) {
        log.info("WholesaleApplyManagerImpl.getWholesaleApply method");
        try {
            return this.wholesaleApplyDao.getWholesaleApply(wholesaleApplyId);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    @Override
    public WholesaleApply getWholesaleApplyByTid(String tid) {
        log.info("WholesaleApplyManagerImpl.getWholesaleApplyByTid method");
        try {
            return this.wholesaleApplyDao.getWholesaleApplyByTid(tid);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    @Override
    public List<WholesaleApply> getWholesaleApplys() {
        log.info("WholesaleApplyManagerImpl.getWholesaleApplys method");
        try {
            return this.wholesaleApplyDao.getWholesaleApplys();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    @Override
    public QueryPage getWholesaleApplysByParMap(Map parMap, int currentPage, int pageSize) {
        log.info("WholesaleApplyManagerImpl.getWholesaleApplysByParMap method");
        try {
            return this.wholesaleApplyDao.getWholesaleApplysByParMap(parMap, currentPage, pageSize);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    @Override
    public int getCountWholesaleApplysByParMap(Map parMap) {
        log.info("WholesaleApplyManagerImpl.getCountWholesaleApplysByParMap method");
        try {
            return this.wholesaleApplyDao.getCountWholesaleApplysByParMap(parMap);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return 0;
    }
}
