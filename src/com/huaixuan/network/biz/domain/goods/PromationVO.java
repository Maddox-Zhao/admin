/**
 * created since Jun 16, 2009
 */
package com.huaixuan.network.biz.domain.goods;

import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class PromationVO extends Promation {

	private String ruleExrtaInfo;  // 规则中的额外信息2
	 
	private String ruleResultValue; // 规则中的额外信息1
	/**
	 * 优惠价，组合销售时：则为套餐价格
	 *        买就赠时：买商品的价格
	 */
    private double promationPrice;
    /**\
     * 合计价：组合销售时：所有商品销售价格合计
     *        买就赠时：买的商品+赠送商品的销售价格合计
     */
    private double totalPrice;

    /**
     * 商品列表 组合销售时：所有组合的商品
     *         买就赠时：买的商品和赠送商品
     */
    private List<Goods> goodsList;

    private List<GoodsInstance> goodsInstanceList;//产品列表

    
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
