package com.huaixuan.network.biz.domain.goods;

 import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.huaixuan.network.biz.domain.BaseObject;

 /**
  * ��������������������(bibleUtil auto code generation)
  * @version 3.2.0
  */
 public class GoodsBillboard extends BaseObject {
 		 /**
	 *
	 */
	private static final long serialVersionUID = 1L;
	/* @property: */
	 private long id;
	 /* @property: */
	 private int type;
	 /* @property: */
	 private String cat;
	 /* @property: */
	 private long goodsId;
	 /* @property: */
	 private long point;
	 /* @property: */
	 private Date gmtCreate;
	 /* @property: */
	 private Date gmtModify;
	 /* Default constructor - creates a new instance with no values set. */
	 public GoodsBillboard(){}
	 /* @model:�������� */
	 public void setId(long obj){
		 this.id = obj;
	 }

	 /* @model:������ */
	 public long getId(){
		 return this.id;
	 }
	 /* @model:�������� */
	 public void setType(int obj){
		 this.type = obj;
	 }

	 /* @model:������ */
	 public int getType(){
		 return this.type;
	 }
	 /* @model:�������� */
	 public void setCat(String obj){
		 this.cat = obj;
	 }

	 /* @model:������ */
	 public String getCat(){
		 return this.cat;
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
	 public void setPoint(long obj){
		 this.point = obj;
	 }

	 /* @model:������ */
	 public long getPoint(){
		 return this.point;
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
		 if (!(o instanceof GoodsBillboard)) {
			 return false;
		 }
		 final GoodsBillboard goodsbillboard = (GoodsBillboard) o;
		 return this.hashCode() == goodsbillboard.hashCode();
	 }
	/*{@inheritDoc}*/
	 public int hashCode() {
	     final int prime = 31;
         int result = 1;
         result = prime * result + type;
         result = prime * result + ((cat == null) ? 0 : cat.hashCode());
         result = prime * result + ((gmtCreate == null) ? 0 : gmtCreate.hashCode());
         result = prime * result + ((gmtModify == null) ? 0 : gmtModify.hashCode());
         return result;
	 }
	/*{@inheritDoc}*/
	 public String toString() {
		 ToStringBuilder sb = new ToStringBuilder(this, ToStringStyle.DEFAULT_STYLE)
			 .append("id", this.id)
			 .append("type", this.type)
			 .append("cat", this.cat)
			 .append("goodsId", this.goodsId)
			 .append("point", this.point)
			 .append("gmtCreate", this.gmtCreate)
			 .append("gmtModify", this.gmtModify);
		 return sb.toString();
	 }

 }
