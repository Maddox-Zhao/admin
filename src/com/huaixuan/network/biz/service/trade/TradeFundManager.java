package com.huaixuan.network.biz.service.trade;

import java.util.List;

import com.huaixuan.network.biz.domain.trade.TradeFund;

/**
 * �����Զ�����(bibleUtil auto code generation)
 * 
 * @version 3.2.0
 */
public interface TradeFundManager {
	/* @interface model: ���һ��TradeFund��¼ */
	public void addTradeFund(TradeFund tradeFund);

	/* @interface model: ����һ��TradeFund��¼ */
	public void editTradeFund(TradeFund tradeFund);

	/* @interface model: ɾ��һ��TradeFund��¼ */
	public void removeTradeFund(Long tradeFundId);

	/* @interface model: ��ѯһ��TradeFund�����,����TradeFund���� */
	public TradeFund getTradeFund(Long tradeFundId);

	/* @interface model: ��ѯ����TradeFund�����,����TradeFund����ļ��� */
	public List<TradeFund> getTradeFunds();
}
