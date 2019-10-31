package com.huaixuan.network.biz.domain.shop; 
  
 import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.huaixuan.network.biz.domain.BaseObject;
  
 /**  
  * (bibleUtil auto code generation) 
  * @version 3.2.0  
  */  
 public class ArticleCat extends BaseObject implements Serializable { 
 		 /* @property: */
	 private long id;
	 /* @property: */
	 private String code;
	 /* @property: */
	 private String parentCode;
	 /* @property: */
	 private String catName;
	 /* @property: */
	 private int catType;
	 /* @property: */
	 private String keywords;
	 /* @property: */
	 private String catDesc;
	 /* @property: */
	 private int sortOrder;
	 /* Default constructor - creates a new instance with no values set. */
	 public ArticleCat(){} 
	 /* @model: */
	 public void setId(long obj){
		 this.id = obj;
	 }

	 /* @model: */
	 public long getId(){
		 return this.id;
	 }
	 /* @model: */
	 public void setCode(String obj){
		 this.code = obj;
	 }

	 /* @model: */
	 public String getCode(){
		 return this.code;
	 }
	 /* @model: */
	 public void setParentCode(String obj){
		 this.parentCode = obj;
	 }

	 /* @model: */
	 public String getParentCode(){
		 return this.parentCode;
	 }
	 /* @model: */
	 public void setCatName(String obj){
		 this.catName = obj;
	 }

	 /* @model: */
	 public String getCatName(){
		 return this.catName;
	 }
	 /* @model: */
	 public void setCatType(int obj){
		 this.catType = obj;
	 }

	 /* @model: */
	 public int getCatType(){
		 return this.catType;
	 }
	 /* @model: */
	 public void setKeywords(String obj){
		 this.keywords = obj;
	 }

	 /* @model: */
	 public String getKeywords(){
		 return this.keywords;
	 }
	 /* @model: */
	 public void setCatDesc(String obj){
		 this.catDesc = obj;
	 }

	 /* @model: */
	 public String getCatDesc(){
		 return this.catDesc;
	 }
	 /* @model: */
	 public void setSortOrder(int obj){
		 this.sortOrder = obj;
	 }

	 /* @model: */
	 public int getSortOrder(){
		 return this.sortOrder;
	 }
	/*{@inheritDoc}*/
	 public boolean equals(Object o) {
		 if (this == o) {
			 return true;
		 }
		 if (!(o instanceof ArticleCat)) {
			 return false;
		 }
		 final ArticleCat articlecat = (ArticleCat) o;
		 return this.hashCode() == articlecat.hashCode();
	 }
	/*{@inheritDoc}*/
	 public int hashCode() {
		 return this.hashCode();
	 }
	/*{@inheritDoc}*/
	 public String toString() {
		 ToStringBuilder sb = new ToStringBuilder(this, ToStringStyle.DEFAULT_STYLE)
			 .append("id", this.id)
			 .append("code", this.code)
			 .append("parentCode", this.parentCode)
			 .append("catName", this.catName)
			 .append("catType", this.catType)
			 .append("keywords", this.keywords)
			 .append("catDesc", this.catDesc)
			 .append("sortOrder", this.sortOrder);
		 return sb.toString();
	 }
 
 } 
 