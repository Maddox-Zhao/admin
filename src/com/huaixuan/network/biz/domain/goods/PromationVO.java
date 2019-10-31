/**
 * created since Jun 16, 2009
 */
package com.huaixuan.network.biz.domain.goods;

import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class PromationVO extends Promation {

	private String ruleExrtaInfo;  // �����еĶ�����Ϣ2
	 
	private String ruleResultValue; // �����еĶ�����Ϣ1
	/**
	 * �Żݼۣ��������ʱ����Ϊ�ײͼ۸�
	 *        �����ʱ������Ʒ�ļ۸�
	 */
    private double promationPrice;
    /**\
     * �ϼƼۣ��������ʱ��������Ʒ���ۼ۸�ϼ�
     *        �����ʱ�������Ʒ+������Ʒ�����ۼ۸�ϼ�
     */
    private double totalPrice;

    /**
     * ��Ʒ�б� �������ʱ��������ϵ���Ʒ
     *         �����ʱ�������Ʒ��������Ʒ
     */
    private List<Goods> goodsList;

    private List<GoodsInstance> goodsInstanceList;//��Ʒ�б�

    
    public String getRuleExrtaInfo() {
		return ruleExrtaInfo;
	}

	public void setRuleExrtaInfo(String ruleExrtaInfo) {
		this.ruleExrtaInfo = ruleExrtaInfo;
	}

	public String getRuleResultValue() {
		return ruleResultValue;
	}

	public void setRuleResultValue(String ruleResultValue) {
		this.ruleResultValue = ruleResultValue;
	}

	public double getPromationPrice() {
        return promationPrice;
    }

    public void setPromationPrice(double promationPrice) {
        this.promationPrice = promationPrice;
    }


    public List<Goods> getGoodsList() {
        return goodsList;
    }


    public void setGoodsList(List<Goods> goodsList) {
        this.goodsList = goodsList;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String toString() {
        ToStringBuilder sb =new ToStringBuilder(this, ToStringStyle.DEFAULT_STYLE)
        .append(super.toString())
        .append("promationPrice",promationPrice)
        .append("totalPrice",totalPrice)
        .append("goodsList",goodsList);
        return sb.toString();
    }

	public List<GoodsInstance> getGoodsInstanceList() {
		return goodsInstanceList;
	}

	public void setGoodsInstanceList(List<GoodsInstance> goodsInstanceList) {
		this.goodsInstanceList = goodsInstanceList;
	}

}
