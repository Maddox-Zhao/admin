package com.huaixuan.network.biz.domain.provider;

public class ProvideUpdateGoodsXiYouLog {

	private Long id;

	private Long prodid;// 银泰商品唯一标识

	private int stock;// 库存数量

	private String brandname;// 品牌名

	private String prodname;// 商品名

	private Double cost;// 成本价

	private Double oursaleprice;// 我们的销售价

	private Double newcost;// 新成本价

	private Double newoursaleprice;// 新销售价

	private String costchangetime;// cost价格变动时间

	private String inserttime;// 数据生成时间

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getProdid() {
		return prodid;
	}

	public void setProdid(Long prodid) {
		this.prodid = prodid;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public String getBrandname() {
		return brandname;
	}

	public void setBrandname(String brandname) {
		this.brandname = brandname;
	}

	public String getProdname() {
		return prodname;
	}

	public void setProdname(String prodname) {
		this.prodname = prodname;
	}

	public Double getCost() {
		return cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}

	public Double getOursaleprice() {
		return oursaleprice;
	}

	public void setOursaleprice(Double oursaleprice) {
		this.oursaleprice = oursaleprice;
	}

	public Double getNewcost() {
		return newcost;
	}

	public void setNewcost(Double newcost) {
		this.newcost = newcost;
	}

	public Double getNewoursaleprice() {
		return newoursaleprice;
	}

	public void setNewoursaleprice(Double newoursaleprice) {
		this.newoursaleprice = newoursaleprice;
	}

	public String getCostchangetime() {
		return costchangetime;
	}

	public void setCostchangetime(String costchangetime) {
		this.costchangetime = costchangetime;
	}

	public String getInserttime() {
		return inserttime;
	}

	public void setInserttime(String inserttime) {
		this.inserttime = inserttime;
	}
}
