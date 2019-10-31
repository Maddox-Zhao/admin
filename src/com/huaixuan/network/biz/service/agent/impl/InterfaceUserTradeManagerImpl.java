/*
 * Hundsun Inc.
 * Copyright (c) 2006-2009 All Rights Reserved.
 *
 * Author     :shengyong
 * Version    :1.0
 * Create Date:2011-3-7
 */
package com.huaixuan.network.biz.service.agent.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.agent.InterfaceUserTradeDao;
import com.huaixuan.network.biz.domain.agent.InterfaceUserTrade;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.query.SearchInterfaceTradeQuery;
import com.huaixuan.network.biz.service.agent.InterfaceUserTradeManager;

/**
 * @author shengyong
 * @version $Id: InterfaceUserTradeManagerImpl.java,v 0.1 2011-3-7 下午05:01:37 shengyong Exp $
 */
@Service("interfaceUserTradeManager")
public class InterfaceUserTradeManagerImpl implements InterfaceUserTradeManager {

    protected final Log   log = LogFactory.getLog(this.getClass());

    @Autowired
    InterfaceUserTradeDao interfaceUserTradeDao;

    /* @model: */
    public void addInterfaceUserTrade(InterfaceUserTrade interfaceUserTradeDao) {
        log.info("InterfaceUserTradeManagerImpl.addInterfaceUserTrade method");
        try {
            InterfaceUserTrade object = this.interfaceUserTradeDao
                .getInterfaceUserTradeByDealId(interfaceUserTradeDao.getPaipaiTradeId());
            if (object == null) {
                this.interfaceUserTradeDao.addInterfaceUserTrade(interfaceUserTradeDao);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    /* @model: */
    public void editInterfaceUserTrade(InterfaceUserTrade interfaceUserTrade) {
        log.info("InterfaceUserTradeManagerImpl.editInterfaceUserTrade method");
        try {
            this.interfaceUserTradeDao.editInterfaceUserTrade(interfaceUserTrade);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    /* @model: */
    public void removeInterfaceUserTrade(Long interfaceUserTradeId) {
        log.info("InterfaceUserTradeManagerImpl.removeInterfaceUserTrade method");
        try {
            this.interfaceUserTradeDao.removeInterfaceUserTrade(interfaceUserTradeId);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    /* @model: */
    public InterfaceUserTrade getInterfaceUserTrade(Long interfaceUserTradeId) {
        log.info("InterfaceUserTradeManagerImpl.getInterfaceUserTrade method");
        try {
            return this.interfaceUserTradeDao.getInterfaceUserTrade(interfaceUserTradeId);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    public InterfaceUserTrade getInterfaceUserTradeByTid(String tid) {
        log.info("InterfaceUserTradeManagerImpl.getInterfaceUserTradeByTid method");
        try {
            return this.interfaceUserTradeDao.getInterfaceUserTradeByTid(tid);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    public InterfaceUserTrade getInterfaceUserTradeByDealId(String dealId) throws Exception {
        log.info("InterfaceUserTradeManagerImpl.getInterfaceUserTradeByDealId method");
        try {
            return this.interfaceUserTradeDao.getInterfaceUserTradeByDealId(dealId);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    /* @model: */
    public List<InterfaceUserTrade> getInterfaceUserTrades() {
        log.info("InterfaceUserTradeManagerImpl.getInterfaceUserTrades method");
        try {
            return this.interfaceUserTradeDao.getInterfaceUserTrades();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    /**
     * @author fangqing
     * @return int
     * 接口同步数据条数
     *
     */
    public int searchInterfaceCountByParameterMap(Map<String, String> parMap) {
        return interfaceUserTradeDao.searchInterfaceCountByParameterMap(parMap);
    }

    /**
     * @author fangqing
     * @return List
     * 接口同步数据list
     */
    @SuppressWarnings("unchecked")
	@Override
    public QueryPage searchInterfaceByParameterMap(SearchInterfaceTradeQuery searchInterfaceTradeQuery,
                                                   int currPage, int pageSize) {

        QueryPage queryPage = new QueryPage(searchInterfaceTradeQuery);
        Map pramas = queryPage.getParameters();

        int count = interfaceUserTradeDao.searchInterfaceCountByParameterMap(pramas);

        if (count > 0) {

            /* 当前页 */
            queryPage.setCurrentPage(currPage);
            /* 每页总数 */
            queryPage.setPageSize(pageSize);
            queryPage.setTotalItem(count);

            pramas.put("startRow", queryPage.getPageFristItem());
            pramas.put("endRow", queryPage.getPageLastItem());

            List<InterfaceUserTrade> interfaceUserTradeList = interfaceUserTradeDao
                .searchInterfaceByParameterMap(pramas);
            if (interfaceUserTradeList != null && interfaceUserTradeList.size() > 0) {
                queryPage.setItems(interfaceUserTradeList);
            }
        }
        return queryPage;
    }
}
