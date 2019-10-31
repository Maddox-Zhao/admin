package com.huaixuan.network.biz.domain.goods;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

public class GoodsQuery extends Goods {

	private static final long serialVersionUID = -4247597297168200291L;

	private Set<String> attributes;
	
	private String               firstPriceMin;

    private String               firstPriceMax;

	private String getPair(String attrName, String attrValue) {
		StringBuilder sb = new StringBuilder(StringUtils.trim(attrName));
		sb.append(':').append(StringUtils.trim(attrValue));
		return sb.toString();
	}

	public void addAttributePair(String attrName, String attrValue) {
		if (StringUtils.isBlank(attrName) || StringUtils.isBlank(attrValue)) {
			return;
		}
		if (attributes == null) {
			attributes = new HashSet<String>();
		}
		attributes.add(getPair(attrName, attrValue));
	}

	public Set<String> getAttributes() {
		return attributes;
	}

	public void setAttributes(Set<String> attributes) {
		this.attributes = attributes;
	}

	public String getFirstPriceMin() {
		return firstPriceMin;
	}

	public void setFirstPriceMin(String firstPriceMin) {
		this.firstPriceMin = firstPriceMin;
	}

	public String getFirstPriceMax() {
		return firstPriceMax;
	}

	public void setFirstPriceMax(String firstPriceMax) {
		this.firstPriceMax = firstPriceMax;
	}

	public boolean hasValue(String attrName, String attrValue) {
		if(this.attributes == null){
			return false;
		}
		if (StringUtils.isBlank(attrName) || StringUtils.isBlank(attrValue)) {
			return false;
		}
		String s = getPair(attrName,attrValue);
		return attributes.contains(s);
	}
}
