package com.huaixuan.network.biz.domain.express;

 import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.huaixuan.network.biz.domain.BaseObject;

 /**
  * ï¿bibleUtil auto code generation)
  * @version 3.2.0
  */
 public class Castweight extends BaseObject {
 		 /**
	 *
	 */
	private static final long serialVersionUID = 1L;
	/* @property:ï¿D */
	 private Long id;
	 /* @property:ï¿*/
	 private Date gmtCreate;
	 /* @property:ï¿*/
	 private Date gmtModify;
	 /* @property:ï¿D */
	 private Long goodsId;
	 /* @property:ï¿ */
	 private Double goodsWeight;
	 /* @property:ï¿*/
	 private String goodsSn;
	 /* @property:ï¿*/
	 private String goodsName;
	 /* @property:ï¿D */
	 private Long expressId;
	 /* @property:ï¿*/
	 private String expressName;
	 /* @property:ï¿ */
	 private Double castWeight;

	 private String status;
	 /* Default constructor - creates a new instance with no values set. */
	 public Castweight(){}
	 /* @model:ï¿D */
	 public void setId(Long obj){
		 this.id = obj;
	 }

	 /* @model:ï¿D */
	 public Long getId(){
		 return this.id;
	 }
	 /* @model:ï¿*/
	 public void setGmtCreate(Date obj){
		 this.gmtCreate = obj;
	 }

	 /* @model:ï¿*/
	 public Date getGmtCreate(){
		 return this.gmtCreate;
	 }
	 /* @model:ï¿*/
	 public void setGmtModify(Date obj){
		 this.gmtModify = obj;
	 }

	 /* @model:ï¿*/
	 public Date getGmtModify(){
		 return this.gmtModify;
	 }
	 /* @model:ï¿D */
	 public void setGoodsId(Long obj){
		 this.goodsId = obj;
	 }

	 /* @model:ï¿D */
	 public Long getGoodsId(){
		 return this.goodsId;
	 }
	 /* @model:ï¿ */
	 public void setGoodsWeight(Double obj){
		 this.goodsWeight = obj;
	 }

	 /* @model:ï¿ */
	 public Double getGoodsWeight(){
		 return this.goodsWeight;
	 }
	 /* @model:ï¿*/
	 public void setGoodsSn(String obj){
		 this.goodsSn = obj;
	 }

	 /* @model:ï¿*/
	 public String getGoodsSn(){
		 return this.goodsSn;
	 }
	 /* @model:ï¿*/
	 public void setGoodsName(String obj){
		 this.goodsName = obj;
	 }

	 /* @model:ï¿*/
	 public String getGoodsName(){
		 return this.goodsName;
	 }
	 /* @model:ï¿D */
	 public void setExpressId(Long obj){
		 this.expressId = obj;
	 }

	 /* @model:ï¿D */
	 public Long getExpressId(){
		 return this.expressId;
	 }
	 /* @model:ï¿*/
	 public void setExpressName(String obj){
		 this.expressName = obj;
	 }

	 /* @model:ï¿*/
	 public String getExpressName(){
		 return this.expressName;
	 }

	 public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	/* @model:ï¿½ï¿½ï¿ */
	 public void setCastWeight(Double obj){
		 this.castWeight = obj;
	 }

	 /* @model:ï¿ */
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
