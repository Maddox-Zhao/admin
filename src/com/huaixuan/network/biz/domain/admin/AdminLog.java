package com.huaixuan.network.biz.domain.admin; 
  
 import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.huaixuan.network.biz.domain.BaseObject;
  
 /**  
  * (bibleUtil auto code generation) 
  * @version 3.2.0  
  */  
 public class AdminLog extends BaseObject implements Serializable { 
 		 /* @property: */
	 private long id;
	 /* @property: */
	 private String operationType;
	 /* @property: */
	 private String content;
	 /* @property: */
	 private String account;
	 /* @property: */
	 private String ip;
	 /* @property: */
	 private Date gmtCreate;
	 /* @property: */
	 private Date gmtModify;
	 
	 private String gmtCreateStart;
	 
	 private String gmtCreateEnd;
	 

	 public String getGmtCreateStart() {
		return gmtCreateStart;
	}
	public void setGmtCreateStart(String gmtCreateStart) {
		this.gmtCreateStart = gmtCreateStart;
	}
	public String getGmtCreateEnd() {
		return gmtCreateEnd;
	}
	public void setGmtCreateEnd(String gmtCreateEnd) {
		this.gmtCreateEnd = gmtCreateEnd;
	}
	/* Default constructor - creates a new instance with no values set. */
	 public AdminLog(){} 
	 /* @model: */
	 public void setId(long obj){
		 this.id = obj;
	 }

	 /* @model: */
	 public long getId(){
		 return this.id;
	 }
	 /* @model: */
	 public void setOperationType(String obj){
		 this.operationType = obj;
	 }

	 /* @model: */
	 public String getOperationType(){
		 return this.operationType;
	 }
	 /* @model: */
	 public void setContent(String obj){
		 this.content = obj;
	 }

	 /* @model: */
	 public String getContent(){
		 return this.content;
	 }
	 /* @model: */
	 public void setAccount(String obj){
		 this.account = obj;
	 }

	 /* @model: */
	 public String getAccount(){
		 return this.account;
	 }
	 /* @model: */
	 public void setIp(String obj){
		 this.ip = obj;
	 }

	 /* @model: */
	 public String getIp(){
		 return this.ip;
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
		 if (!(o instanceof AdminLog)) {
			 return false;
		 }
		 final AdminLog adminlog = (AdminLog) o;
		 return this.hashCode() == adminlog.hashCode();
	 }
	/*{@inheritDoc}*/
	 public int hashCode() {
		 return this.hashCode();
	 }
	/*{@inheritDoc}*/
	 public String toString() {
		 ToStringBuilder sb = new ToStringBuilder(this, ToStringStyle.DEFAULT_STYLE)
			 .append("id", this.id)
			 .append("operationType", this.operationType)
			 .append("content", this.content)
			 .append("account", this.account)
			 .append("ip", this.ip)
			 .append("gmtCreate", this.gmtCreate)
			 .append("gmtModify", this.gmtModify);
		 return sb.toString();
	 }
 
 } 
 