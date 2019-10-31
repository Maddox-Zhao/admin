package com.huaixuan.network.biz.domain.goods;

 import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.huaixuan.network.biz.domain.BaseObject;


 /**
  * �bibleUtil auto code generation)
  * @version 3.2.0
  */
 public class MyCollection extends BaseObject {
 		 /**
	 *
	 */
	private static final long serialVersionUID = 1L;
	/* @property: */
	 private long id;
	 /* @property: */
	 private long userId;
	 /* @property: */
	 private long goodsId;
	 /* @property: */
	 private Date gmtModify;
	 /* @property: */
	 private Date gmtCreate;

	 private String goodsName;

	 private double  marketPrice;

     private double  goodsPrice;

     private String title;//��Ʒ���

     private String account; //�û����

     private String goodSn;//��Ʒ����

     private String goodsUnit;//��Ʒ��λ

     //�����
     private double agentPrice;

     private String imgSmall;

     private Boolean hasStock;

     private String  isWholesale;

     private List<GoodsWholsale> goodsWholesale;
     
     private String userAccount;
     
     private String goodTitle;
     
	public String getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}

	public String getGoodTitle() {
		return goodTitle;
	}

	public void setGoodTitle(String goodTitle) {
		this.goodTitle = goodTitle;
	}

	public List<GoodsWholsale> getGoodsWholesale() {
		return goodsWholesale;
	}

	public void setGoodsWholesale(List<GoodsWholsale> goodsWholesale) {
		this.goodsWholesale = goodsWholesale;
	}

	public String getGoodSn() {
		return goodSn;
	}

	public void setGoodSn(String goodSn) {
		this.goodSn = goodSn;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}
	public double getMarketPrice() {
		return marketPrice;
	}
	public void setMarketPrice(double marketPrice) {
		this.marketPrice = marketPrice;
	}
	public double getGoodsPrice() {
		return goodsPrice;
	}
	public void setGoodsPrice(double goodsPrice) {
		this.goodsPrice = goodsPrice;
	}
	/* Default constructor - creates a new instance with no values set. */
	 public MyCollection(){}
	 /* @model:�*/
	 public void setId(long obj){
		 this.id = obj;
	 }

	 /* @model:�*/
	 public long getId(){
		 return this.id;
	 }
	 /* @model:�*/
	 public void setUserId(long obj){
		 this.userId = obj;
	 }

	 /* @model:�*/
	 public long getUserId(){
		 return this.userId;
	 }
	 /* @model:�*/
	 public void setGoodsId(long obj){
		 this.goodsId = obj;
	 }

	 /* @model:�*/
	 public long getGoodsId(){
		 return this.goodsId;
	 }
	 /* @model:�*/
	 public void setGmtModify(Date obj){
		 this.gmtModify = obj;
	 }

	 /* @model:�*/
	 public Date getGmtModify(){
		 return this.gmtModify;
	 }
	 /* @model:�*/
	 public void setGmtCreate(Date obj){
		 this.gmtCreate = obj;
	 }

	 /* @model:�*/
	 public Date getGmtCreate(){
		 return this.gmtCreate;
	 }
	/*{@inheritDoc}*/
	 public boolean equals(Object o) {
		 if (this == o) {
			 return true;
		 }
		 if (!(o instanceof MyCollection)) {
			 return false;
		 }
		 final MyCollection mycollection = (MyCollection) o;
		 return this.hashCode() == mycollection.hashCode();
	 }
	/*{@inheritDoc}*/
	 public int hashCode() {
		 final int prime = 31;
	        int result = 1;
	        result = prime * result + (int)userId;
	        result = prime * result + (int)goodsId;
	        result = prime * result + ((gmtCreate == null) ? 0 : gmtCreate.hashCode());
	        result = prime * result + ((gmtModify == null) ? 0 : gmtModify.hashCode());

	        return result;
	 }
	/*{@inheritDoc}*/
	 public String toString() {
		 ToStringBuilder sb = new ToStringBuilder(this, ToStringStyle.DEFAULT_STYLE)
			 .append("id", this.id)
			 .append("userId", this.userId)
			 .append("goodsId", this.goodsId)
			 .append("gmtModify", this.gmtModify)
			 .append("gmtCreate", this.gmtCreate);
		 return sb.toString();
	 }
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

    public double getAgentPrice() {
        return agentPrice;
    }

    public void setAgentPrice(double agentPrice) {
        this.agentPrice = agentPrice;
    }

    public String getImgSmall() {
        return imgSmall;
    }

    public void setImgSmall(String imgSmall) {
        this.imgSmall = imgSmall;
    }

    public Boolean getHasStock() {
        return hasStock;
    }

    public void setHasStock(Boolean hasStock) {
        this.hasStock = hasStock;
    }

	public String getGoodsUnit() {
		return goodsUnit;
	}

	public void setGoodsUnit(String goodsUnit) {
		this.goodsUnit = goodsUnit;
	}

	public String getIsWholesale() {
		return isWholesale;
	}

	public void setIsWholesale(String isWholesale) {
		this.isWholesale = isWholesale;
	}

 }
