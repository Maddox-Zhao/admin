package com.huaixuan.network.biz.service.storage;

import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.huaixuan.network.biz.domain.admin.AdminAgent;
import com.huaixuan.network.biz.domain.trade.Refund;

/**
 * @author liuchao
 * @version $Id: InAndOutDepositoryManager.java,v 0.1 2009-7-17 上午10:38:10 liuchao Exp $
 */
public interface InAndOutDepositoryManager {

	/* @interface model: 添加一条InDepository记录 */
	public void addInDepository(Long tradeId);

	/* @interface model: 添加一条OutDepository记录 */
	@Transactional
	public Long addOutDepository(String[] tIds, AdminAgent agent);

	public String goodsOption(String tid, String[] orderId, String[] goodsInsId, String[] goodsNum,
			String[] goodsChangePrice, String remark, String[] duFees, String[] factFees, Double shipFee,
			boolean isRefund, AdminAgent agent);

	/**
	 * 提交退货操作
	 * 
	 * @param param
	 * @return @
	 */
	@SuppressWarnings("unchecked")
	public String refundGoods(Map param, AdminAgent agent);

	/**
	 * 同意退货操作
	 * 
	 * @param param
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String checkRefundGoods(Map param, AdminAgent agent);

	/**
	 * 更新退货记录
	 * 
	 * @param param
	 * @return
	 */
	public void editRefundGoods(Refund refund);
	
	/**
	 * 配货库存判定
	 * @return
	 */
	public boolean judgeFactStorage(String tid);
	
	/**
	 * 合并配货库存判定
	 * @return
	 */
	public boolean judgeFactStorageMerge(String[] tids);
}
