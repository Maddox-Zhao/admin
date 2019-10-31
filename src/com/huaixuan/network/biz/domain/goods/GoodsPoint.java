package com.huaixuan.network.biz.domain.goods;

 import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.huaixuan.network.biz.domain.BaseObject;

 /**
  * ��������������������(bibleUtil auto code generation)
  * @version 3.2.0
  */
 public class GoodsPoint extends BaseObject {
 		 /**
	 *
	 */
	private static final long serialVersionUID = 1L;
	/* @property: */
	 private long id;
	 /* @property: */
	 private Date pointDate;
	 /* @property: */
	 private long goodsId;
	 /* @property: */
	 private String goodsCat;
	 /* @property: */
	 private int clickCount;
	 /* @property: */
	 private int saleCount;
	 /* @property: */
	 private int tradeCount;
	 /* @property: */
	 private Integer hotSalePoint;
	 /* @property: */
	 private Integer highPopularPoint;
	 /* @property: */
	 private Date gmtCreate;
	 /* @property: */
	 private Date gmtModify;
	 /* Default constructor - creates a new instance with no values set. */
	 public GoodsPoint(){}
	 /* @model:�������� */
	 public void setId(long obj){
		 this.id = obj;
	 }

	 /* @model:������ */
	 public long getId(){
		 return this.id;
	 }
	 /* @model:�������� */
	 public void setPointDate(Date obj){
		 this.pointDate = obj;
	 }

	 /* @model:������ */
	 public Date getPointDate(){
		 return this.pointDate;
	 }
	 /* @model:�������� */
	 public void setGoodsId(long obj){
		 this.goodsId = obj;
	 }

	 /* @model:������ */
	 public long getGoodsId(){
		 return this.goodsId;
	 }
	 /* @model:�������� */
	 public void setGoodsCat(String obj){
		 this.goodsCat = obj;
	 }

	 /* @model:������ */
	 public String getGoodsCat(){
		 return this.goodsCat;
	 }
	 /* @model:�������� */
	 public void setClickCount(int obj){
		 this.clickCount = obj;
	 }

	 /* @model:������ */
	 public int getClickCount(){
		 return this.clickCount;
	 }
	 /* @model:�������� */
	 public void setSaleCount(int obj){
		 this.saleCount = obj;
	 }

	 /* @model:������ */
	 public int getSaleCount(){
		 return this.saleCount;
	 }
	 /* @model:�������� */
	 public void setTradeCount(int obj){
		 this.tradeCount = obj;
	 }

	 /* @model:������ */
	 public int getTradeCount(){
		 return this.tradeCount;
	 }

	 public Integer getHotSalePoint() {
        return hotSalePoint;
    }
    public void setHotSalePoint(Integer hotSalePoint) {
        this.hotSalePoint = hotSalePoint;
    }
    public Integer getHighPopularPoint() {
        return highPopularPoint;
    }
    public void setHighPopularPoint(Integer highPopularPoint) {
        this.highPopularPoint = highPopularPoint;
    }
    /* @model:�������� */
	 public void setGmtCreate(Date obj){
		 this.gmtCreate = obj;
	 }

	 /* @model:������ */
	 public Date getGmtCreate(){
		 return this.gmtCreate;
	 }
	 /* @model:�������� */
	 public void setGmtModify(Date obj){
		 this.gmtModify = obj;
	 }

	 /* @model:������ */
	 public Date getGmtModify(){
		 return this.gmtModify;
	 }
	/*{@inheritDoc}*/
	 public boolean equals(Object o) {
		 if (this == o) {
			 return true;
		 }
		 if (!(o instanceof GoodsPoint)) {
			 return false;
		 }
		 final GoodsPoint goodspoint = (GoodsPoint) o;
		 return this.hashCode() == goodspoint.hashCode();
	 }
	/*{@inheritDoc}*/
	 public int hashCode() {
	     final int prime = 31;
         int result = 1;
         result = prime * result + ((pointDate == null) ? 0 : pointDate.hashCode());
         result = prime * result + ((goodsCat == null) ? 0 : goodsCat.hashCode());
         result = prime * result + clickCount;
         result = prime * result + saleCount;
         result = prime * result + ((gmtCreate == null) ? 0 : gmtCreate.hashCode());
         result = prime * result + ((gmtModify == null) ? 0 : gmtModify.hashCode());
         result = prime * result + tradeCount;

         return result;
	 }
	/*{@inheritDoc}*/
	 public String toString() {
		 ToStringBuilder sb = new ToStringBuilder(this, ToStringStyle.DEFAULT_STYLE)
			 .append("id", this.id)
			 .append("pointDate", this.pointDate)
			 .append("goodsId", this.goodsId)
			 .append("goodsCat", this.goodsCat)
			 .append("clickCount", this.clickCount)
			 .append("saleCount", this.saleCount)
			 .append("tradeCount", this.tradeCount)
			 .append("hotSalePoint", this.hotSalePoint)
			 .append("highPopularPoint", this.highPopularPoint)
			 .append("gmtCreate", this.gmtCreate)
			 .append("gmtModify", this.gmtModify);
		 return sb.toString();
	 }

 }
