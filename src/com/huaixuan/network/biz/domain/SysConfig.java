package com.huaixuan.network.biz.domain; 
  
 import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
  
 /**  
  * (bibleUtil auto code generation) 
  * @version 3.2.0  
  */  
 public class SysConfig extends BaseObject implements Serializable { 
 		 /* @property: */
	 private long id;
	 /* @property: */
	 private long shopId;
	 /* @property: */
	 private String code;
	 /* @property: */
	 private String value;
	 /* @property: */
	 private Date gmtCreate;
	 /* @property: */
	 private Date gmtModify;
	 /* Default constructor - creates a new instance with no values set. */
	 public SysConfig(){} 
	 /* @model: */
	 public void setId(long obj){
		 this.id = obj;
	 }

	 /* @model: */
	 public long getId(){
		 return this.id;
	 }
	 /* @model: */
	 public void setShopId(long obj){
		 this.shopId = obj;
	 }

	 /* @model: */
	 public long getShopId(){
		 return this.shopId;
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
	 public void setValue(String obj){
		 this.value = obj;
	 }

	 /* @model: */
	 public String getValue(){
		 return this.value;
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
	/*{@inheritDoc}*/
	 public boolean equals(Object o) {
		 if (this == o) {
			 return true;
		 }
		 if (!(o instanceof SysConfig)) {
			 return false;
		 }
		 final SysConfig sysconfig = (SysConfig) o;
		 return this.hashCode() == sysconfig.hashCode();
	 }
	/*{@inheritDoc}*/
	 public int hashCode() {
		 return this.hashCode();
	 }
	/*{@inheritDoc}*/
	 public String toString() {
		 ToStringBuilder sb = new ToStringBuilder(this, ToStringStyle.DEFAULT_STYLE)
			 .append("id", this.id)
			 .append("shopId", this.shopId)
			 .append("code", this.code)
			 .append("value", this.value)
			 .append("gmtCreate", this.gmtCreate)
			 .append("gmtModify", this.gmtModify);
		 return sb.toString();
	 }
 
 } 
 