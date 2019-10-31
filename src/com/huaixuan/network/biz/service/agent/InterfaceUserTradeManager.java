/*
 * Hundsun Inc.
 * Copyright (c) 2006-2009 All Rights Reserved.
 *
 * Author     :shengyong
 * Version    :1.0
 * Create Date:2011-3-7
 */
package com.huaixuan.network.biz.service.agent;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.agent.InterfaceUserTrade;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.query.SearchInterfaceTradeQuery;

/**
 * @author shengyong
 * @version $Id: InterfaceUserTradeManager.java,v 0.1 2011-3-7 下午04:53:29 shengyong Exp $
 */
public interface InterfaceUserTradeManager {
    /* @interface model: InterfaceUserTrade */
    public void addInterfaceUserTrade(InterfaceUserTrade interfaceUserTrade);

    /* @interface model: InterfaceUserTrade */
    public void editInterfaceUserTrade(InterfaceUserTrade interfaceUserTrade);

    /* @interface model: InterfaceUserTrade */
    public void removeInterfaceUserTrade(Long interfaceUserTradeId);

    /* @interface model: InterfaceUserTrade,InterfaceUserTrade */
    public InterfaceUserTrade getInterfaceUserTrade(Long interfaceUserTradeId);

    public InterfaceUserTrade getInterfaceUserTradeByTid(String tid);

    public InterfaceUserTrade getInterfaceUserTradeByDealId(String dealId) throws Exception;

    /* @interface model: InterfaceUserTrade,InterfaceUserTrade */
    public List<InterfaceUserTrade> getInterfaceUserTrades();

    /**
     * @author fangqing
     * @param parMap
     * @return
     * 查找同步数据条数
     */
    public int searchInterfaceCountByParameterMap(Map<String, String> parMap);

    /**
     * @author fangqing
     * @param parMap
     * @return
     * 查找同步数据List
     */
    //    public List<InterfaceUserTrade> searchInterfaceByParameterMap(Map<String, String> parMap,
    //                                                                  Page page);

    public QueryPage searchInterfaceByParameterMap(SearchInterfaceTradeQuery searchInterfaceTradeQuery,
                                                   int currPage, int pageSize);
}
