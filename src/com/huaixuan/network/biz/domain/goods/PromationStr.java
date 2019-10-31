package com.huaixuan.network.biz.domain.goods;

import com.huaixuan.network.biz.domain.BaseObject;

public class PromationStr extends BaseObject {

    private static final long serialVersionUID = 1L;

    private String name;

    private int amount;

    private double lowerPrice;

    private double topPrice;

    private int lowerNumber;

    private int topNumber;

    private int price;

    private long goodsId;

    private String goodsName;

    private String name0;
    private String name1;
    private String name2;

    private String  attrName;
    private String attrId;
    private String attrDiv;


	public int getLowerNumber() {
		return lowerNumber;
	}

	public void setLowerNumber(int lowerNumber) {
		this.lowerNumber = lowerNumber;
	}

	public int getTopNumber() {
		return topNumber;
	}

	public void setTopNumber(int topNumber) {
		this.topNumber = topNumber;
	}

	public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public double getLowerPrice() {
		return lowerPrice;
	}

	public void setLowerPrice(double lowerPrice) {
		this.lowerPrice = lowerPrice;
	}

	public double getTopPrice() {
		return topPrice;
	}

	public void setTopPrice(double topPrice) {
		this.topPrice = topPrice;
	}

	public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(long goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName0() {
        return name0;
    }

    public void setName0(String name0) {
        this.name0 = name0;
    }

    public String getName1() {
        return name1;
    }

    public void setName1(String name1) {
        this.name1 = name1;
    }

    public String getName2() {
        return name2;
    }

    public void setName2(String name2) {
        this.name2 = name2;
    }

    public String getAttrName() {
        return attrName;
    }

    public void setAttrName(String attrName) {
        this.attrName = attrName;
    }

    public String getAttrId() {
        return attrId;
    }

    public void setAttrId(String attrId) {
        this.attrId = attrId;
    }

    public String getAttrDiv() {
        return attrDiv;
    }

    public void setAttrDiv(String attrDiv) {
        this.attrDiv = attrDiv;
    }




}
