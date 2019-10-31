package com.huaixuan.network.biz.domain.shop;

 import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.huaixuan.network.biz.domain.BaseObject;

 /**
  * (bibleUtil auto code generation)
  * @version 3.2.0
  */
 public class Showcase extends BaseObject implements Serializable {
 		 /* @property: */
	 private long id;
	 /* @property: */
	 private long goodsId;
	 /* @property: */
	 private int sort;
	 /* @property: */
	 private Date gmtCreate;
	 /* @property: */
	 private Date gmtModify;
	 /* @property: */
	 private long cabinetId;
	/* Default constructor - creates a new instance with no values set. */
	 private String imgMiddle;

	 private String goodsTitle;
	 
	 private String viceName;
	 
	 
	 public String getViceName() {
		return viceName;
	}
	public void setViceName(String viceName) {
		this.viceName = viceName;
	}
	public String getGoodsTitle() {
		return goodsTitle;
	}
	public void setGoodsTitle(String goodsTitle) {
		this.goodsTitle = goodsTitle;
	}
	public String getImgMiddle() {
		return imgMiddle;
	}
	public void setImgMiddle(String imgMiddle) {
		this.imgMiddle = imgMiddle;
	}
	public Showcase(){}
	 /* @model: */
	 public void setId(long obj){
		 this.id = obj;
	 }

	 /* @model: */
	 public long getId(){
		 return this.id;
	 }
	 /* @model: */
	 public void setGoodsId(long obj){
		 this.goodsId = obj;
	 }

	 /* @model: */
	 public long getGoodsId(){
		 return this.goodsId;
	 }
	 /* @model: */
	 public void setSort(int obj){
		 this.sort = obj;
	 }

	 /* @model: */
	 public int getSort(){
		 return this.sort;
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

	 public long getCabinetId() {
			return cabinetId;
		}

	 public void setCabinetId(long cabinetId) {
		this.cabinetId = cabinetId;

	 }

	/*{@inheritDoc}*/
	 public boolean equals(Object obj) {
		 if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			final Showcase other = (Showcase) obj;
			if (imgMiddle == null) {
				if (other.imgMiddle != null)
					return false;
			} else if (!imgMiddle.equals(other.imgMiddle))
				return false;
			if (goodsTitle == null) {
				if (other.goodsTitle != null)
					return false;
			} else if (!goodsTitle.equals(other.goodsTitle))
				return false;
			if (gmtCreate == null) {
				if (other.gmtCreate != null)
					return false;
			} else if (!gmtCreate.equals(other.gmtCreate))
				return false;
			if (gmtModify == null) {
				if (other.gmtModify != null)
					return false;
			} else if (!gmtModify.equals(other.gmtModify))
				return false;

			return true;
	 }
	/*{@inheritDoc}*/
	 public int hashCode() {
		 final int prime = 30;
			int result = 1;
			result = prime * result
					+ ((imgMiddle == null) ? 0 : imgMiddle.hashCode());
			result = prime * result + ((goodsTitle == null) ? 0 : goodsTitle.hashCode());
			result = prime * result + ((gmtCreate == null) ? 0 : gmtCreate.hashCode());
			result = prime * result + ((gmtModify == null) ? 0 : gmtModify.hashCode());
			return result;
	 }
	/*{@inheritDoc}*/
	 public String toString() {
		 ToStringBuilder sb = new ToStringBuilder(this, ToStringStyle.DEFAULT_STYLE)
			 .append("id", this.id)
			 .append("goodsId", this.goodsId)
			 .append("sort", this.sort)
			 .append("gmtCreate", this.gmtCreate)
			 .append("gmtModify", this.gmtModify);
		 return sb.toString();
	 }

 }
