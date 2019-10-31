package com.huaixuan.network.biz.domain; 
  
 import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
  
 /**  
  * (bibleUtil auto code generation) 
  * @version 3.2.0  
  */  
 public class VerifyInfo extends BaseObject implements Serializable { 
 		 /* @property: */
	 private long id;
	 /* @property: */
	 private long userId;
	 /* @property: */
	 private String verifyType;
	 /* @property: */
	 private String verifyData;
	 /* @property: */
	 private Date gmtCreate;
	 /* @property: */
	 private Date gmtEnd;
	 /* @property: */
	 private int isValid;
	 /* Default constructor - creates a new instance with no values set. */
	 public VerifyInfo(){} 
	 /* @model: */
	 public void setId(long obj){
		 this.id = obj;
	 }

	 /* @model: */
	 public long getId(){
		 return this.id;
	 }
	 /* @model: */
	 public void setUserId(long obj){
		 this.userId = obj;
	 }

	 /* @model: */
	 public long getUserId(){
		 return this.userId;
	 }
	 /* @model: */
	 public void setVerifyType(String obj){
		 this.verifyType = obj;
	 }

	 /* @model: */
	 public String getVerifyType(){
		 return this.verifyType;
	 }
	 /* @model: */
	 public void setVerifyData(String obj){
		 this.verifyData = obj;
	 }

	 /* @model: */
	 public String getVerifyData(){
		 return this.verifyData;
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
	 public void setGmtEnd(Date obj){
		 this.gmtEnd = obj;
	 }

	 /* @model: */
	 public Date getGmtEnd(){
		 return this.gmtEnd;
	 }
	 /* @model: */
	 public void setIsValid(int obj){
		 this.isValid = obj;
	 }

	 /* @model: */
	 public int getIsValid(){
		 return this.isValid;
	 }
	/*{@inheritDoc}*/
	 public boolean equals(Object o) {
		 if (this == o) {
			 return true;
		 }
		 if (!(o instanceof VerifyInfo)) {
			 return false;
		 }
		 final VerifyInfo verifyinfo = (VerifyInfo) o;
		 return this.hashCode() == verifyinfo.hashCode();
	 }
	/*{@inheritDoc}*/
	 public int hashCode() {
		 return this.hashCode();
	 }
	/*{@inheritDoc}*/
	 public String toString() {
		 ToStringBuilder sb = new ToStringBuilder(this, ToStringStyle.DEFAULT_STYLE)
			 .append("id", this.id)
			 .append("userId", this.userId)
			 .append("verifyType", this.verifyType)
			 .append("verifyData", this.verifyData)
			 .append("gmtCreate", this.gmtCreate)
			 .append("gmtEnd", this.gmtEnd)
			 .append("isValid", this.isValid);
		 return sb.toString();
	 }
 
 } 
 