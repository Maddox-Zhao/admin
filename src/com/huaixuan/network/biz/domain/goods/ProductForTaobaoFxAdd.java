package com.huaixuan.network.biz.domain.goods;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

public class ProductForTaobaoFxAdd {
	private static final long serialVersionUID = -7399041883722516899L;
	public static final String sep              = ";";
    public static final String equ              = "=";
	private Long goodsId;
	private String attrs;
	private Integer existNum;
	private String title;
	private double goodsPrice;
	private double agentPrice;
	private String goodsDesc;
	private String attrDesc;
	private String instanceName;
	private String code;
	private String pyCode;
	private String catCode;
	private Long productId;
	private String depFirstId;
	private Long goodsNumber;

	public Long getGoodsNumber() {
		return goodsNumber;
	}

	public void setGoodsNumber(Long goodsNumber) {
		this.goodsNumber = goodsNumber;
	}

	public String getDepFirstId() {
		return depFirstId;
	}

	public void setDepFirstId(String depFirstId) {
		this.depFirstId = depFirstId;
	}

	public Map<String, String> getProperties() {
        if (StringUtils.isBlank(attrs)) {
            return Collections.EMPTY_MAP;
        }
        String[] pros = attrs.split(sep);
        if (pros.length == 0) {
            return Collections.EMPTY_MAP;
        }
        Map<String, String> back = new LinkedHashMap<String, String>();
        for (String s : pros) {
            if (StringUtils.isBlank(s)) {
                continue;
            }
            String[] kvpair = s.split(equ);
            if (kvpair == null) {
                continue;
            }
            if (kvpair.length == 1) {
                back.put(kvpair[0], null);
            }
            if (kvpair.length == 2) {
                back.put(kvpair[0], kvpair[1]);
            }
        }
        return back;
    }
	
	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getCatCode() {
		return catCode;
	}

	public void setCatCode(String catCode) {
		this.catCode = catCode;
	}

	public String getPyCode() {
		return pyCode;
	}

	public void setPyCode(String pyCode) {
		this.pyCode = pyCode;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getInstanceName() {
		return instanceName;
	}

	public void setInstanceName(String instanceName) {
		this.instanceName = instanceName;
	}

	public String getAttrDesc() {
		return attrDesc;
	}

	public void setAttrDesc(String attrDesc) {
		this.attrDesc = attrDesc;
	}

	public String getGoodsDesc() {
		return goodsDesc;
	}

	public void setGoodsDesc(String goodsDesc) {
		this.goodsDesc = goodsDesc;
	}

	public double getAgentPrice() {
		return agentPrice;
	}

	public void setAgentPrice(double agentPrice) {
		this.agentPrice = agentPrice;
	}

	public double getGoodsPrice() {
		return goodsPrice;
	}

	public void setGoodsPrice(double goodsPrice) {
		this.goodsPrice = goodsPrice;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getExistNum() {
		return existNum;
	}

	public void setExistNum(Integer existNum) {
		this.existNum = existNum;
	}

	public String getAttrs() {
		return attrs;
	}

	public void setAttrs(String attrs) {
		this.attrs = attrs;
	}

	public Long getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(Long goodsId) {
		this.goodsId = goodsId;
	}
}
