package com.huaixuan.network.biz.service.storage;

import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.huaixuan.network.biz.domain.admin.AdminAgent;
import com.huaixuan.network.biz.domain.trade.Refund;

/**
 * @author liuchao
 * @version $Id: InAndOutDepositoryManager.java,v 0.1 2009-7-17 ����10:38:10 liuchao Exp $
 */
public interface InAndOutDepositoryManager {

	/* @interface model: ���һ��InDepository��¼ */
	public void addInDepository(Long tradeId);

	/* @interface model: ���һ��OutDepository��¼ */
	@Transactional
	public Long addOutDepository(String[] tIds, AdminAgent agent);

	public String goodsOption(String tid, String[] orderId, String[] goodsInsId, String[] goodsNum,
			String[] goodsChangePrice, String remark, String[] duFees, String[] factFees, Double shipFee,
			boolean isRefund, AdminAgent agent);

	/**
	 * �ύ�˻�����
	 * 
	 * @param param
	 * @return @
	 */
	@SuppressWarnings("unchecked")
	public String refundGoods(Map param, AdminAgent agent);

	/**
	 * ͬ���˻�����
	 * 
	 * @param param
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String checkRefundGoods(Map param, AdminAgent agent);

	/**
	 * �����˻���¼
	 * 
	 * @param param
	 * @return
	 */
	public void editRefundGoods(Refund refund);
	
	/**
	 * �������ж�
	 * @return
	 */
	public boolean judgeFactStorage(String tid);
	
	/**
	 * �ϲ��������ж�
	 * @return
	 */
	public boolean judgeFactStorageMerge(String[] tids);
}
