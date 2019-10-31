package com.huaixuan.network.biz.domain.goods;

 import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.huaixuan.network.biz.domain.BaseObject;


 public class GoodsGallery extends BaseObject {
	 private long id;
	 /* @property: */
	 private String imgLarge;
	 /* @property: */
	 private String imgMiddle;
	 /* @property: */
	 private String imgSmall;
	 /* @property: */
	 private String imgOriginal;
	 /* @property: */
	 private String imgDesc;
	 /* @property: */
	 private Date gmtCreate;
	 /* @property: */
	 private Date gmtModify;
	 /* @property: */
	 private long goodsId;
	 /* @property: */
	 private int sort;
	 /* Default constructor - creates a new instance with no values set. */
	 public GoodsGallery(){}
	 /* @model: */
	 public void setId(long obj){
		 this.id = obj;
	 }

	 /* @model: */
	 public long getId(){
		 return this.id;
	 }
	 /* @model: */
	 public void setImgLarge(String obj){
		 this.imgLarge = obj;
	 }

	 /* @model: */
	 public String getImgLarge(){
		 return this.imgLarge;
	 }
	 /* @model: */
	 public void setImgMiddle(String obj){
		 this.imgMiddle = obj;
	 }

	 /* @model: */
	 public String getImgMiddle(){
		 return this.imgMiddle;
	 }
	 /* @model: */
	 public void setImgSmall(String obj){
		 this.imgSmall = obj;
	 }

	 /* @model: */
	 public String getImgSmall(){
		 return this.imgSmall;
	 }
	 /* @model: */
	 public void setImgOriginal(String obj){
		 this.imgOriginal = obj;
	 }

	 /* @model: */
	 public String getImgOriginal(){
		 return this.imgOriginal;
	 }
	 /* @model: */
	 public void setImgDesc(String obj){
		 this.imgDesc = obj;
	 }

	 /* @model: */
	 public String getImgDesc(){
		 return this.imgDesc;
	 }
	 /* @model: */
	 public void setGmtCreate(Date obj){
		 this.gmtCreate = obj;
	 }

	 /* @model: */
	 public Date getGmtCreate(){
		 return this.gmtCreate;
	 }
	 /* @model: */
	 public void setGmtModify(Date obj){
		 this.gmtModify = obj;
	 }

	 /* @model: */
	 public Date getGmtModify(){
		 return this.gmtModify;
	 }
	 /* @model: */
	 public void setGoodsId(long obj){
		 this.goodsId = obj;
	 }

	 /* @model: */
	 public long getGoodsId(){
		 return this.goodsId;
	 }
	public int getSort() {
		return sort;
	}
	public void setSort(int sort) {
		this.sort = sort;
	}
	/*{@inheritDoc}*/
	 public boolean equals(Object o) {
		 if (this == o) {
			 return true;
		 }
		 if (!(o instanceof GoodsGallery)) {
			 return false;
		 }
		 final GoodsGallery goodsgallery = (GoodsGallery) o;
		 return this.hashCode() == goodsgallery.hashCode();
	 }
	/*{@inheritDoc}*/
	 public int hashCode() {
	     final int prime = 31;
         int result = 1;
         result = result + (int) this.id * prime;
         result = result + (int) this.gmtCreate.getTime() * prime;
         result = result + (int) this.gmtModify.getTime() * prime;
         return result;
	 }
	/*{@inheritDoc}*/
	 public String toString() {
		 ToStringBuilder sb = new ToStringBuilder(this, ToStringStyle.DEFAULT_STYLE)
			 .append("id", this.id)
			 .append("imgLarge", this.imgLarge)
			 .append("imgMiddle", this.imgMiddle)
			 .append("imgSmall", this.imgSmall)
			 .append("imgOriginal", this.imgOriginal)
			 .append("imgDesc", this.imgDesc)
			 .append("gmtCreate", this.gmtCreate)
			 .append("gmtModify", this.gmtModify)
			 .append("goodsId", this.goodsId)
			 .append("sort", this.sort);
		 return sb.toString();
	 }

 }
