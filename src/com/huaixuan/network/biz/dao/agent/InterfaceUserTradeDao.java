/*
 * Hundsun Inc.
 * Copyright (c) 2006-2009 All Rights Reserved.
 *
 * Author     :shengyong
 * Version    :1.0
 * Create Date:2011-3-7
 */
package com.huaixuan.network.biz.dao.agent;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.agent.InterfaceUserTrade;

/**
 * @author shengyong
 * @version $Id: InterfaceUserTradeDao.java,v 0.1 2011-3-7 下午05:05:55 shengyong Exp $
 */
public interface InterfaceUserTradeDao {

    void addInterfaceUserTrade(InterfaceUserTrade interfaceUserTrade) throws Exception;

    void editInterfaceUserTrade(InterfaceUserTrade interfaceUserTrade) throws Exception;

    void editInterfaceUserTradeByDealCode(String dealCode, String tradeId) throws Exception;

    void removeInterfaceUserTrade(Long interfaceUserTradeId) throws Exception;

    InterfaceUserTrade getInterfaceUserTrade(Long interfaceUserTradeId) throws Exception;

    /*
     * 根据外部订单号获得同步表数据
     * added by  2011-05-17
     */
    InterfaceUserTrade getInterfaceUserTradeByPaipaiTradeId(String paipaiTradeId) throws Exception;

    InterfaceUserTrade getInterfaceUserTradeByTid(String tid) throws Exception;

    InterfaceUserTrade getInterfaceUserTradeByDealId(String dealId) throws Exception;

    List<InterfaceUserTrade> getInterfaceUserTrades() throws Exception;

    public int searchInterfaceCountByParameterMap(Map<String, String> parMap);

    //    public List<InterfaceUserTrade> searchInterfaceByParameterMap(Map<String, String> parMap,
    //                                                                  Page page);

    public List<InterfaceUserTrade> searchInterfaceByParameterMap(Map parMap);

}
