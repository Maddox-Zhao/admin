package com.huaixuan.network.biz.domain;

public class RankGoods {

	private String goodsId;//��ƷId
	private String rankTitle;//���б���
	private String goodsImage;//��ƷͼƬ
	private String marketPrice;//��Ʒ�г��۸�
	private String sellPrice;//���ۼ۸�
	private String goodsTitle;//��Ʒ����
	private String catCode;//��Ŀ����
	private double agentPrice;//�����۸�
	private String goodsLargeImage;//��Ʒ��ͼ
	
	public String getRankTitle() {
		return rankTitle;
	}
	public void setRankTitle(String rankTitle) {
		this.rankTitle = rankTitle;
	}
	public String getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}
	public String getMarketPrice() {
		return marketPrice;
	}
	public void setMarketPrice(String marketPrice) {
		this.marketPrice = marketPrice;
	}
	public String getSellPrice() {
		return sellPrice;
	}
	public void setSellPrice(String sellPrice) {
		this.sellPrice = sellPrice;
	}
	public String getGoodsTitle() {
		return goodsTitle;
	}
	public void setGoodsTitle(String goodsTitle) {
		this.goodsTitle = goodsTitle;
	}
	public String getGoodsImage() {
		return goodsImage;
	}
	public void setGoodsImage(String goodsImage) {
		this.goodsImage = goodsImage;
	}
	public String getCatCode() {
		return catCode;
	}
	public void setCatCode(String catCode) {
		this.catCode = catCode;
	}
    public double getAgentPrice() {
        return agentPrice;
    }
    public void setAgentPrice(double agentPrice) {
        this.agentPrice = agentPrice;
    }
    public String getGoodsLargeImage() {
        return goodsLargeImage;
    }
    public void setGoodsLargeImage(String goodsLargeImage) {
        this.goodsLargeImage = goodsLargeImage;
    }

}
