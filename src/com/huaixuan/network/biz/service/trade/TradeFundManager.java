package com.huaixuan.network.biz.service.trade;

import java.util.List;

import com.huaixuan.network.biz.domain.trade.TradeFund;

/**
 * 代码自动生成(bibleUtil auto code generation)
 * 
 * @version 3.2.0
 */
public interface TradeFundManager {
	/* @interface model: 添加一条TradeFund记录 */
	public void addTradeFund(TradeFund tradeFund);

	/* @interface model: 更新一条TradeFund记录 */
	public void editTradeFund(TradeFund tradeFund);

	/* @interface model: 删除一条TradeFund记录 */
	public void removeTradeFund(Long tradeFundId);

	/* @interface model: 查询一个TradeFund结果集,返回TradeFund对象 */
	public TradeFund getTradeFund(Long tradeFundId);

	/* @interface model: 查询所有TradeFund结果集,返回TradeFund对象的集合 */
	public List<TradeFund> getTradeFunds();
}
