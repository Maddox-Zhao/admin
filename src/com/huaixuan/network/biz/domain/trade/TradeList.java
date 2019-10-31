package com.huaixuan.network.biz.domain.trade;

import java.util.ArrayList;
import java.util.List;

import com.huaixuan.network.biz.domain.express.ExpressDist;

/**
 * 
 * @author lincf
 * 
 * Sep 25, 2009
 */
public class TradeList {

	private ExpressDist[] array;

	private String paymentTempSel;

	public ExpressDist[] getArray() {
		return array;
	}

	public void setArray(ExpressDist[] array) {
		this.array = array;
	}

	public TradeList(List arraylist) {
		int length = arraylist.size();
		array = new ExpressDist[length];
		for (int i = 0; i < length; i++) {
			array[i] = (ExpressDist) arraylist.get(i);
		}
	}

	public TradeList() {

	}

	public String getPaymentTempSel() {
		return paymentTempSel;
	}

	public void setPaymentTempSel(String paymentTempSel) {
		this.paymentTempSel = paymentTempSel;
	}

}
