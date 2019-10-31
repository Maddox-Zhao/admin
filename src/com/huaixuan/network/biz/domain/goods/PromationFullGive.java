/**
 * created since Jun 16, 2009
 */
package com.huaixuan.network.biz.domain.goods;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author taobao
 * @version $Id: PromationVO.java,v 0.1 Jun 16, 2009 9:00:23 AM taobao Exp $
 */
public class PromationFullGive extends Promation {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String scope;//������ײͷ�Χ��ȫ��all ��Ŀ:cat Ʒ��:brand ��
	private String scopeValue;//������ײͷ�Χֵ����Ŀ��ţ�Ʒ��ID��
	private String fullGiveType;//������ײ����ͣ��������ͣ���������ͣ�
    private List<GoodsInstance> goodsInstanceList;//��Ʒ�б�
    private List<String> lowerPrice;
    private List<String> topPrice;
    private Double sumMoney;
    private Integer sumNumber;
    private Map<String,GoodsInstance> resultMap = new HashMap<String,GoodsInstance>();


	public Double getSumMoney() {
		return sumMoney;
	}
	public void setSumMoney(Double sumMoney) {
		this.sumMoney = sumMoney;
	}
	public Integer getSumNumber() {
		return sumNumber;
	}
	public void setSumNumber(Integer sumNumber) {
		this.sumNumber = sumNumber;
	}
	public String getScope() {
		return scope;
	}
	public void setScope(String scope) {
		this.scope = scope;
	}
	public String getScopeValue() {
		return scopeValue;
	}
	public void setScopeValue(String scopeValue) {
		this.scopeValue = scopeValue;
	}
	public String getFullGiveType() {
		return fullGiveType;
	}
	public void setFullGiveType(String fullGiveType) {
		this.fullGiveType = fullGiveType;
	}
	public List<GoodsInstance> getGoodsInstanceList() {
		return goodsInstanceList;
	}
	public void setGoodsInstanceList(List<GoodsInstance> goodsInstanceList) {
		this.goodsInstanceList = goodsInstanceList;
	}
	public List<String> getLowerPrice() {
		return lowerPrice;
	}
	public void setLowerPrice(List<String> lowerPrice) {
		this.lowerPrice = lowerPrice;
	}
	public List<String> getTopPrice() {
		return topPrice;
	}
	public void setTopPrice(List<String> topPrice) {
		this.topPrice = topPrice;
	}

    public Map<String, GoodsInstance> getResultMap() {
		return resultMap;
	}
	public void setResultMap(List<GoodsInstance> goodsInstanceList,List<String> topPrice){
		if(topPrice != null && goodsInstanceList != null){
	    	for(int i=0;i<topPrice.size();i++){
	    		this.resultMap.put((String)topPrice.get(i), (GoodsInstance)goodsInstanceList.get(i));
	    	}
		}
    }
}
