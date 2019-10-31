package com.huaixuan.network.biz.dao.trade;

import java.util.List;

import com.huaixuan.network.biz.domain.trade.TradePackage;

public interface TradePackageDao {
	void addTradePackage(TradePackage tradePackage);

	void editTradePackage(TradePackage tradePackage);

	void removeTradePackage(Long tradePackageId);

	TradePackage getTradePackage(Long tradePackageId);

	List<TradePackage> getTradePackages();

	/**
	 * 根据合并TID得到合并订单结果集合
	 * 
	 * @return @
	 */
	List<TradePackage> getTradePackagesByMergeTid(String mergeTid);

	/**
	 * 根据合并TID得到合并订单号结果集合
	 * 
	 * @return @
	 */
	List<String> getTidByMergeTid(String mergeTid);
}
