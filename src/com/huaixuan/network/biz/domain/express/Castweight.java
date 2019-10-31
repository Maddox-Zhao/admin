package com.huaixuan.network.biz.domain.express;

 import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.huaixuan.network.biz.domain.BaseObject;

 /**
  * �bibleUtil auto code generation)
  * @version 3.2.0
  */
 public class Castweight extends BaseObject {
 		 /**
	 *
	 */
	private static final long serialVersionUID = 1L;
	/* @property:�D */
	 private Long id;
	 /* @property:�*/
	 private Date gmtCreate;
	 /* @property:�*/
	 private Date gmtModify;
	 /* @property:�D */
	 private Long goodsId;
	 /* @property:� */
	 private Double goodsWeight;
	 /* @property:�*/
	 private String goodsSn;
	 /* @property:�*/
	 private String goodsName;
	 /* @property:�D */
	 private Long expressId;
	 /* @property:�*/
	 private String expressName;
	 /* @property:� */
	 private Double castWeight;

	 private String status;
	 /* Default constructor - creates a new instance with no values set. */
	 public Castweight(){}
	 /* @model:�D */
	 public void setId(Long obj){
		 this.id = obj;
	 }

	 /* @model:�D */
	 public Long getId(){
		 return this.id;
	 }
	 /* @model:�*/
	 public void setGmtCreate(Date obj){
		 this.gmtCreate = obj;
	 }

	 /* @model:�*/
	 public Date getGmtCreate(){
		 return this.gmtCreate;
	 }
	 /* @model:�*/
	 public void setGmtModify(Date obj){
		 this.gmtModify = obj;
	 }

	 /* @model:�*/
	 public Date getGmtModify(){
		 return this.gmtModify;
	 }
	 /* @model:�D */
	 public void setGoodsId(Long obj){
		 this.goodsId = obj;
	 }

	 /* @model:�D */
	 public Long getGoodsId(){
		 return this.goodsId;
	 }
	 /* @model:� */
	 public void setGoodsWeight(Double obj){
		 this.goodsWeight = obj;
	 }

	 /* @model:� */
	 public Double getGoodsWeight(){
		 return this.goodsWeight;
	 }
	 /* @model:�*/
	 public void setGoodsSn(String obj){
		 this.goodsSn = obj;
	 }

	 /* @model:�*/
	 public String getGoodsSn(){
		 return this.goodsSn;
	 }
	 /* @model:�*/
	 public void setGoodsName(String obj){
		 this.goodsName = obj;
	 }

	 /* @model:�*/
	 public String getGoodsName(){
		 return this.goodsName;
	 }
	 /* @model:�D */
	 public void setExpressId(Long obj){
		 this.expressId = obj;
	 }

	 /* @model:�D */
	 public Long getExpressId(){
		 return this.expressId;
	 }
	 /* @model:�*/
	 public void setExpressName(String obj){
		 this.expressName = obj;
	 }

	 /* @model:�*/
	 public String getExpressName(){
		 return this.expressName;
	 }

	 public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	/* @model:��� */
	 public void setCastWeight(Double obj){
		 this.castWeight = obj;
	 }

	 /* @model:� */
	 public Double getCastWeight(){
		 return this.castWeight;
	 }
	/*{@inheritDoc}*/
	 public boolean equals(Object o) {
		 if (this == o) {
			 return true;
		 }
		 if (!(o instanceof Castweight)) {
			 return false;
		 }
		 final Castweight castweight = (Castweight) o;
		 return this.hashCode() == castweight.hashCode();
	 }
	/*{@inheritDoc}*/
	 public int hashCode() {
		 return this.hashCode();
	 }
	/*{@inheritDoc}*/
	 public String toString() {
		 ToStringBuilder sb = new ToStringBuilder(this, ToStringStyle.DEFAULT_STYLE)
			 .append("id", this.id)
			 .append("gmtCreate", this.gmtCreate)
			 .append("gmtModify", this.gmtModify)
			 .append("goodsId", this.goodsId)
			 .append("goodsWeight", this.goodsWeight)
			 .append("goodsSn", this.goodsSn)
			 .append("goodsName", this.goodsName)
			 .append("expressId", this.expressId)
			 .append("expressName", this.expressName)
			 .append("castWeight", this.castWeight);
		 return sb.toString();
	 }

 }
