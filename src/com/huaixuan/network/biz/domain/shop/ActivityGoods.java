package com.huaixuan.network.biz.domain.shop;

 import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.huaixuan.network.biz.domain.BaseObject;
import com.huaixuan.network.biz.domain.goods.Goods;

 /**
  * ï¿ï¿ï¿ï¿ï¿ï¿ï¿ï¿ï¿ï¿ï¿ï¿ï¿ï¿ï¿ï¿ï¿ï¿ï¿ï¿(bibleUtil auto code generation)
  * @version 3.2.0
  */
 public class ActivityGoods extends BaseObject implements Serializable {
 		 /* @property: */
	 private long id;
	 /* @property: */
	 private long activityId;
	 /* @property: */
	 private String category;
	 /* @property: */
	 private long goodsId;
	 /* @property: */
	 private Date gmtCreate;
	 /* @property: */
	 private Date gmtModify;

	 private List<Goods> categoaryGoodsList;

	 /* Default constructor - creates a new instance with no values set. */
	 public ActivityGoods(){}
	 /* @model:ï¿ï¿ï¿ï¿ï¿ï¿ï¿ï¿ */
	 public void setId(long obj){
		 this.id = obj;
	 }

	 /* @model:ï¿ï¿ï¿ï¿ï¿ï¿ */
	 public long getId(){
		 return this.id;
	 }
	 /* @model:ï¿ï¿ï¿ï¿ï¿ï¿ï¿ï¿ */
	 public void setActivityId(long obj){
		 this.activityId = obj;
	 }

	 /* @model:ï¿ï¿ï¿ï¿ï¿ï¿ */
	 public long getActivityId(){
		 return this.activityId;
	 }
	 /* @model:ï¿ï¿ï¿ï¿ï¿ï¿ï¿ï¿ */
	 public void setCategory(String obj){
		 this.category = obj;
	 }

	 /* @model:ï¿ï¿ï¿ï¿ï¿ï¿ */
	 public String getCategory(){
		 return this.category;
	 }
	 /* @model:ï¿ï¿ï¿ï¿ï¿ï¿ï¿ï¿ */
	 public void setGoodsId(long obj){
		 this.goodsId = obj;
	 }

	 /* @model:ï¿ï¿ï¿ï¿ï¿ï¿ */
	 public long getGoodsId(){
		 return this.goodsId;
	 }
	 /* @model:ï¿ï¿ï¿ï¿ï¿ï¿ï¿ï¿ */
	 public void setGmtCreate(Date obj){
		 this.gmtCreate = obj;
	 }

	 /* @model:ï¿ï¿ï¿ï¿ï¿ï¿ */
	 public Date getGmtCreate(){
		 return this.gmtCreate;
	 }
	 /* @model:ï¿ï¿ï¿ï¿ï¿ï¿ï¿ï¿ */
	 public void setGmtModify(Date obj){
		 this.gmtModify = obj;
	 }

	 /* @model:ï¿ï¿ï¿ï¿ï¿ï¿ */
	 public Date getGmtModify(){
		 return this.gmtModify;
	 }
	/*{@inheritDoc}*/
	 public boolean equals(Object o) {
		 if (this == o) {
			 return true;
		 }
		 if (!(o instanceof ActivityGoods)) {
			 return false;
		 }
		 final ActivityGoods activitygoods = (ActivityGoods) o;
		 return this.hashCode() == activitygoods.hashCode();
	 }
	/*{@inheritDoc}*/
	 public int hashCode() {
		 return this.hashCode();
	 }
	/*{@inheritDoc}*/
	 public String toString() {
		 ToStringBuilder sb = new ToStringBuilder(this, ToStringStyle.DEFAULT_STYLE)
			 .append("id", this.id)
			 .append("activityId", this.activityId)
			 .append("category", this.category)
			 .append("goodsId", this.goodsId)
			 .append("gmtCreate", this.gmtCreate)
			 .append("gmtModify", this.gmtModify);
		 return sb.toString();
	 }
	public List<Goods> getCategoaryGoodsList() {
		return categoaryGoodsList;
	}
	public void setCategoaryGoodsList(List<Goods> categoaryGoodsList) {
		this.categoaryGoodsList = categoaryGoodsList;
	}

 }
