/*
 * Hundsun Inc.
 * Copyright (c) 2006-2009 All Rights Reserved.
 *
 * Author     :shengyong
 * Version    :1.0
 * Create Date:2011-3-7
 */
package com.huaixuan.network.biz.dao.agent.ibatis;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.huaixuan.network.biz.dao.agent.InterfaceUserTradeDao;
import com.huaixuan.network.biz.domain.agent.InterfaceUserTrade;

/**
 * @version $Id: InterfaceUserTradeDaoiBatis.java,v 0.1 2011-3-7 ÏÂÎç05:07:50
 *           Exp $
 */
@Repository("interfaceUserTradeDao")
public class InterfaceUserTradeDaoiBatis implements InterfaceUserTradeDao {

	protected final Log log = LogFactory.getLog(this.getClass());

	@Autowired
	private SqlMapClientTemplate sqlMapClient;

	public void addInterfaceUserTrade(InterfaceUserTrade interfaceUserTrade)
			throws Exception {
		this.sqlMapClient.insert("addInterfaceUserTrade", interfaceUserTrade);
	}

	public void editInterfaceUserTrade(InterfaceUserTrade interfaceUserTrade)
			throws Exception {
		this.sqlMapClient.update("editInterfaceUserTrade", interfaceUserTrade);
	}

	public void editInterfaceUserTradeByDealCode(String dealCode, String tradeId)
			throws Exception {
		InterfaceUserTrade interfaceUserTrade = new InterfaceUserTrade();
		interfaceUserTrade.setTradeId(tradeId);
		interfaceUserTrade.setPaipaiTradeId(dealCode);
		this.sqlMapClient.update("editInterfaceUserTradeByDealCode",
				interfaceUserTrade);
	}

	public void removeInterfaceUserTrade(Long interfaceUserTradeId)
			throws Exception {
		this.sqlMapClient.delete("removeInterfaceUserTrade",
				interfaceUserTradeId);
	}

	public InterfaceUserTrade getInterfaceUserTrade(Long interfaceUserTradeId)
			throws Exception {
		return (InterfaceUserTrade) this.sqlMapClient.queryForObject(
				"getInterfaceUserTrade", interfaceUserTradeId);
	}

	public InterfaceUserTrade getInterfaceUserTradeByTid(String tid)
			throws Exception {
		return (InterfaceUserTrade) this.sqlMapClient.queryForObject(
				"getInterfaceUserTradeByTid", tid);
	}

	public InterfaceUserTrade getInterfaceUserTradeByPaipaiTradeId(String paipaiTradeId)throws Exception{
    	return (InterfaceUserTrade) this.sqlMapClient.queryForObject("getInterfaceUserTradeByPaipaiTradeId",
    			paipaiTradeId);
    }

	public InterfaceUserTrade getInterfaceUserTradeByDealId(String dealId)
			throws Exception {
		return (InterfaceUserTrade) this.sqlMapClient.queryForObject(
				"getInterfaceUserTradeByDealId", dealId);
	}

	public List<InterfaceUserTrade> getInterfaceUserTrades() throws Exception {
		return this.sqlMapClient.queryForList("getInterfaceUserTrades", null);
	}

	public int searchInterfaceCountByParameterMap(Map<String, String> parMap) {
		try {
			return (Integer) this.sqlMapClient.queryForObject(
					"searchInterfaceCountByParameterMap", parMap);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return 0;
	}

	public List<InterfaceUserTrade> searchInterfaceByParameterMap(Map parMap) {

		return this.sqlMapClient.queryForList("searchInterfaceByParameterMap",
				parMap);

	}

}
