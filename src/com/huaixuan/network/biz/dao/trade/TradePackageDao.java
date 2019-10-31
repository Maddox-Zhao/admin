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
	 * ���ݺϲ�TID�õ��ϲ������������
	 * 
	 * @return @
	 */
	List<TradePackage> getTradePackagesByMergeTid(String mergeTid);

	/**
	 * ���ݺϲ�TID�õ��ϲ������Ž������
	 * 
	 * @return @
	 */
	List<String> getTidByMergeTid(String mergeTid);
}
