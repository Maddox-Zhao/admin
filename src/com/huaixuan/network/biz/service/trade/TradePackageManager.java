package com.huaixuan.network.biz.service.trade;

import java.util.List;

import com.huaixuan.network.biz.domain.trade.TradePackage;

/**
 * (bibleUtil auto code generation)
 * 
 * @version 3.2.0
 */
public interface TradePackageManager {
	/* @interface model: TradePackage */
	public void addTradePackage(TradePackage tradePackage);

	/* @interface model: TradePackage */
	public void editTradePackage(TradePackage tradePackage);

	/* @interface model: TradePackage */
	public void removeTradePackage(Long tradePackageId);

	/* @interface model: TradePackage,TradePackage */
	public TradePackage getTradePackage(Long tradePackageId);

	/* @interface model: TradePackage,TradePackage */
	public List<TradePackage> getTradePackages();

	/**
	 * ���ݺϲ�TID�õ��ϲ������Ž������
	 * 
	 * @return
	 * @throws Exception
	 */
	List<String> getTidByMergeTid(String mergeTid);
}
