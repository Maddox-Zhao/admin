package com.huaixuan.network.biz.enums;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

/**
 * 退款付款状态
 * 
 * @author
 * @version $Id: EnumTradePayStatus.java,v 0.1 2009-3-23 下午05:09:19
 *          Administrator Exp $
 */
public enum EnumRefundStatus {
	Wait_Seller_Agree("wait_seller_agree", "等待商家同意"), 
	Seller_Refuse_Refund("seller_refuse_refund", "商家拒绝"), 
	Wait_Buyer_Return_Goods("wait_buyer_return_goods", "等待买家发退货"), 
	Wait_Seller_Check_Goods("wait_seller_check_goods", "等待商家验收货物"),
	Wait_Seller_Confirm_Goods("wait_seller_confirm_goods", "等待商家确认货物"), 
	Goods_In_Depository("goods_in_depository", "货物已入库"), 
	Financer_Refund("financer_refund","财务退款"),
	Goods_Confirm_Success("success","完成"), 
	Refund_Close("close", "关闭");
	
	private String code;

	private String name;

	EnumRefundStatus(String code, String name) {
		this.code = code;
		this.name = name;
	}

	public String getKey() {
		return this.code;
	}

	public String getValue() {
		return this.name;
	}

	public static Map<String, String> toMap() {
		Map<String, String> enumDataMap = new TreeMap<String, String>(
				new Comparator() {
					public int compare(Object element1, Object element2) {
						String eOp1 = (String) element1;
						String eOp2 = (String) element2;
						if (eOp1 == null) {
							return -1;
						}
						if (eOp2 == null) {
							return 1;
						}

						int ob1Index = 0;
						int ob2Index = 0;
						if ("wait_seller_agree".equalsIgnoreCase(eOp1)) {
							ob1Index = 1;
						}
						if ("wait_seller_agree".equalsIgnoreCase(eOp2)) {
							ob2Index = 1;
						}
						if ("seller_refuse_refund".equalsIgnoreCase(eOp1)) {
							ob1Index = 2;
						}
						if ("seller_refuse_refund".equalsIgnoreCase(eOp2)) {
							ob2Index = 2;
						}
						if ("wait_buyer_return_goods".equalsIgnoreCase(eOp1)) {
							ob1Index = 3;
						}
						if ("wait_buyer_return_goods".equalsIgnoreCase(eOp2)) {
							ob2Index = 3;
						}
						if ("wait_seller_check_goods".equalsIgnoreCase(eOp1)) {
                            ob1Index = 4;
                        }
                        if ("wait_seller_check_goods".equalsIgnoreCase(eOp2)) {
                            ob2Index = 4;
                        }
						if ("wait_seller_confirm_goods".equalsIgnoreCase(eOp1)) {
							ob1Index = 5;
						}
						if ("wait_seller_confirm_goods".equalsIgnoreCase(eOp2)) {
							ob2Index = 5;
						}
						if ("success".equalsIgnoreCase(eOp1)) {
							ob1Index = 6;
						}
						if ("success".equalsIgnoreCase(eOp2)) {
							ob2Index = 6;
						}
						if ("close".equalsIgnoreCase(eOp1)) {
							ob1Index = 7;
						}
						if ("close".equalsIgnoreCase(eOp2)) {
							ob2Index = 7;
						}
						if ("change_goods".equalsIgnoreCase(eOp1)) {
							ob1Index = 8;
						}
						if ("change_goods".equalsIgnoreCase(eOp2)) {
							ob2Index = 8;
						}
						if ("refund_goods".equalsIgnoreCase(eOp1)) {
							ob1Index = 9;
						}
						if ("refund_goods".equalsIgnoreCase(eOp2)) {
							ob2Index = 9;
						}
						if ("goods_in_depository".equalsIgnoreCase(eOp1)) {
							ob1Index = 10;
						}
						if ("goods_in_depository".equalsIgnoreCase(eOp2)) {
							ob2Index = 10;
						}
						if ("financer_refund".equalsIgnoreCase(eOp1)) {
							ob1Index = 11;
						}
						if ("financer_refund".equalsIgnoreCase(eOp2)) {
							ob2Index = 11;
						}
						Integer ob1 = new Integer(ob1Index);
						Integer ob2 = new Integer(ob2Index);

						return ob1.compareTo(ob2);
					}
				});

		enumDataMap.put(Wait_Seller_Agree.getKey(), Wait_Seller_Agree
				.getValue());
		enumDataMap.put(Seller_Refuse_Refund.getKey(), Seller_Refuse_Refund
				.getValue());
		enumDataMap.put(Wait_Buyer_Return_Goods.getKey(),
				Wait_Buyer_Return_Goods.getValue());
		enumDataMap.put(Wait_Seller_Check_Goods.getKey(),
		    Wait_Seller_Check_Goods.getValue());
		enumDataMap.put(Wait_Seller_Confirm_Goods.getKey(),
				Wait_Seller_Confirm_Goods.getValue());
		enumDataMap.put(Goods_In_Depository.getKey(), Goods_In_Depository
				.getValue());
		enumDataMap.put(Financer_Refund.getKey(), Financer_Refund.getValue());
		enumDataMap.put(Goods_Confirm_Success.getKey(), Goods_Confirm_Success
				.getValue());
		enumDataMap.put(Refund_Close.getKey(), Refund_Close.getValue());
		return enumDataMap;
	}
}
