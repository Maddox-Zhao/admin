package com.huaixuan.network.biz.domain;

 import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

 /**
  * (bibleUtil auto code generation)
  * @version 3.2.0
  */
 public class Code extends BaseObject implements Serializable {
 		 /* @property: */
	 private long id;
	 /* @property: */
	 private int codeType;
	 /* @property: */
	 private Date codeDate;
	 /* @property: */
	 private long curValue;
	 /* @property: */
	 private int codeLength;
	 /* @property: */
	 private Date gmtCreate;
	 /* @property: */
	 private Date gmtModify;
	 /* Default constructor - creates a new instance with no values set. */
	 public Code(){}
	 /* @model: */
	 public void setId(long obj){
		 this.id = obj;
	 }

	 /* @model: */
	 public long getId(){
		 return this.id;
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
	 public void setCodeDate(Date obj){
		 this.codeDate = obj;
	 }

	 /* @model: */
	 public Date getCodeDate(){
		 return this.codeDate;
	 }
	 /* @model: */
	 public void setCurValue(long obj){
		 this.curValue = obj;
	 }

	 /* @model: */
	 public long getCurValue(){
		 return this.curValue;
	 }
	 /* @model: */
	 public void setCodeLength(int obj){
		 this.codeLength = obj;
	 }

	 /* @model: */
	 public int getCodeLength(){
		 return this.codeLength;
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
		 if (!(o instanceof Code)) {
			 return false;
		 }
		 final Code code = (Code) o;
		 return this.hashCode() == code.hashCode();
	 }
	/*{@inheritDoc}*/
	 public int hashCode() {
	     final int prime = 31;
	        int result = 1;
	        result = result + (int) this.id * prime;
	        result = result + (int) this.gmtCreate.getTime() * prime;
	        result = result + (int) this.gmtModify.getTime() * prime;
	        return result;
	 }
	/*{@inheritDoc}*/
	 public String toString() {
		 ToStringBuilder sb = new ToStringBuilder(this, ToStringStyle.DEFAULT_STYLE)
			 .append("id", this.id)
			 .append("codeType", this.codeType)
			 .append("codeDate", this.codeDate)
			 .append("curValue", this.curValue)
			 .append("codeLength", this.codeLength)
			 .append("gmtCreate", this.gmtCreate)
			 .append("gmtModify", this.gmtModify);
		 return sb.toString();
	 }

 }
