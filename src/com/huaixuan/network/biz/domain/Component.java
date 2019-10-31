package com.huaixuan.network.biz.domain; 
  
 import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
  
 /**  
  * (bibleUtil auto code generation) 
  * @version 3.2.0  
  */  
 public class Component extends BaseObject implements Serializable { 
 		 /* @property: */
	 private long id;
	 /* @property: */
	 private int templateType;
	 /* @property: */
	 private int codeType;
	 /* @property: */
	 private String codeContext;
	 /* @property: */
	 private String htmlText;
	 /* @property: */
	 private Date gmtCreate;
	 /* @property: */
	 private Date gmtModify;
	 /* @property: */
	 private long shopId;
	 /* Default constructor - creates a new instance with no values set. */
	 public Component(){} 
	 /* @model: */
	 public void setId(long obj){
		 this.id = obj;
	 }

	 /* @model: */
	 public long getId(){
		 return this.id;
	 }
	 /* @model: */
	 public void setTemplateType(int obj){
		 this.templateType = obj;
	 }

	 /* @model: */
	 public int getTemplateType(){
		 return this.templateType;
	 }
	 /* @model: */
	 public void setCodeType(int obj){
		 this.codeType = obj;
	 }

	 /* @model: */
	 public int getCodeType(){
		 return this.codeType;
	 }
	 /* @model: */
	 public void setCodeContext(String obj){
		 this.codeContext = obj;
	 }

	 /* @model: */
	 public String getCodeContext(){
		 return this.codeContext;
	 }
	 /* @model: */
	 public void setHtmlText(String obj){
		 this.htmlText = obj;
	 }

	 /* @model: */
	 public String getHtmlText(){
		 return this.htmlText;
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
	 public void setShopId(long obj){
		 this.shopId = obj;
	 }

	 /* @model: */
	 public long getShopId(){
		 return this.shopId;
	 }
	/*{@inheritDoc}*/
	 public boolean equals(Object o) {
		 if (this == o) {
			 return true;
		 }
		 if (!(o instanceof Component)) {
			 return false;
		 }
		 final Component component = (Component) o;
		 return this.hashCode() == component.hashCode();
	 }
	/*{@inheritDoc}*/
	 public int hashCode() {
		 return this.hashCode();
	 }
	/*{@inheritDoc}*/
	 public String toString() {
		 ToStringBuilder sb = new ToStringBuilder(this, ToStringStyle.DEFAULT_STYLE)
			 .append("id", this.id)
			 .append("templateType", this.templateType)
			 .append("codeType", this.codeType)
			 .append("codeContext", this.codeContext)
			 .append("htmlText", this.htmlText)
			 .append("gmtCreate", this.gmtCreate)
			 .append("gmtModify", this.gmtModify)
			 .append("shopId", this.shopId);
		 return sb.toString();
	 }
 
 } 
 