package com.huaixuan.network.biz.domain; 
  
 import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
  
 /**  
  * (bibleUtil auto code generation) 
  * @version 3.2.0  
  */  
 public class MailTemplates extends BaseObject implements Serializable { 
 		 /* @property: */
	 private long id;
	 /* @property: */
	 private String templateCode;
	 /* @property: */
	 private int isHtml;
	 /* @property: */
	 private String templateSubject;
	 /* @property: */
	 private String templateContent;
	 /* @property: */
	 private Date gmtCreate;
	 /* @property: */
	 private Date gmtModify;
	 /* Default constructor - creates a new instance with no values set. */
	 public MailTemplates(){} 
	 /* @model: */
	 public void setId(long obj){
		 this.id = obj;
	 }

	 /* @model: */
	 public long getId(){
		 return this.id;
	 }
	 /* @model: */
	 public void setTemplateCode(String obj){
		 this.templateCode = obj;
	 }

	 /* @model: */
	 public String getTemplateCode(){
		 return this.templateCode;
	 }
	 /* @model: */
	 public void setIsHtml(int obj){
		 this.isHtml = obj;
	 }

	 /* @model: */
	 public int getIsHtml(){
		 return this.isHtml;
	 }
	 /* @model: */
	 public void setTemplateSubject(String obj){
		 this.templateSubject = obj;
	 }

	 /* @model: */
	 public String getTemplateSubject(){
		 return this.templateSubject;
	 }
	 /* @model: */
	 public void setTemplateContent(String obj){
		 this.templateContent = obj;
	 }

	 /* @model: */
	 public String getTemplateContent(){
		 return this.templateContent;
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
		 if (!(o instanceof MailTemplates)) {
			 return false;
		 }
		 final MailTemplates mailtemplates = (MailTemplates) o;
		 return this.hashCode() == mailtemplates.hashCode();
	 }
	/*{@inheritDoc}*/
	 public int hashCode() {
		 return this.hashCode();
	 }
	/*{@inheritDoc}*/
	 public String toString() {
		 ToStringBuilder sb = new ToStringBuilder(this, ToStringStyle.DEFAULT_STYLE)
			 .append("id", this.id)
			 .append("templateCode", this.templateCode)
			 .append("isHtml", this.isHtml)
			 .append("templateSubject", this.templateSubject)
			 .append("templateContent", this.templateContent)
			 .append("gmtCreate", this.gmtCreate)
			 .append("gmtModify", this.gmtModify);
		 return sb.toString();
	 }
 
 } 
 