package com.huaixuan.network.biz.domain.shop;

 import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.huaixuan.network.biz.domain.BaseObject;
import com.huaixuan.network.biz.domain.goods.Goods;

 /**
  * ��������������������(bibleUtil auto code generation)
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
	 /* @model:�������� */
	 public void setId(long obj){
		 this.id = obj;
	 }

	 /* @model:������ */
	 public long getId(){
		 return this.id;
	 }
	 /* @model:�������� */
	 public void setActivityId(long obj){
		 this.activityId = obj;
	 }

	 /* @model:������ */
	 public long getActivityId(){
		 return this.activityId;
	 }
	 /* @model:�������� */
	 public void setCategory(String obj){
		 this.category = obj;
	 }

	 /* @model:������ */
	 public String getCategory(){
		 return this.category;
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
