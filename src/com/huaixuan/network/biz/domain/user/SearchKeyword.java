package com.huaixuan.network.biz.domain.user; 
  
 import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.huaixuan.network.biz.domain.BaseObject;
  
 /**  
  * (bibleUtil auto code generation) 
  * @version 3.2.0  
  */  
 public class SearchKeyword extends BaseObject implements Serializable { 
 		 /* @property: */
	 private long id;
	 /* @property: */
	 private String keyword;
	 /* @property: */
	 private String keywordPinying;
	 /* @property: */
	 private int resultCount;
	 /* @property: */
	 private int searchCount;
	 /* @property: */
	 private Date gmtCreate;
	 /* @property: */
	 private Date gmtModify;
	 /* Default constructor - creates a new instance with no values set. */
	 public SearchKeyword(){} 
	 /* @model: */
	 public void setId(long obj){
		 this.id = obj;
	 }

	 /* @model: */
	 public long getId(){
		 return this.id;
	 }
	 /* @model: */
	 public void setKeyword(String obj){
		 this.keyword = obj;
	 }

	 /* @model: */
	 public String getKeyword(){
		 return this.keyword;
	 }
	 /* @model: */
	 public void setKeywordPinying(String obj){
		 this.keywordPinying = obj;
	 }

	 /* @model: */
	 public String getKeywordPinying(){
		 return this.keywordPinying;
	 }
	 /* @model: */
	 public void setResultCount(int obj){
		 this.resultCount = obj;
	 }

	 /* @model: */
	 public int getResultCount(){
		 return this.resultCount;
	 }
	 /* @model: */
	 public void setSearchCount(int obj){
		 this.searchCount = obj;
	 }

	 /* @model: */
	 public int getSearchCount(){
		 return this.searchCount;
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
		 if (!(o instanceof SearchKeyword)) {
			 return false;
		 }
		 final SearchKeyword searchkeyword = (SearchKeyword) o;
		 return this.hashCode() == searchkeyword.hashCode();
	 }

	/*{@inheritDoc}*/
	 public String toString() {
		 ToStringBuilder sb = new ToStringBuilder(this, ToStringStyle.DEFAULT_STYLE)
			 .append("id", this.id)
			 .append("keyword", this.keyword)
			 .append("keywordPinying", this.keywordPinying)
			 .append("resultCount", this.resultCount)
			 .append("searchCount", this.searchCount)
			 .append("gmtCreate", this.gmtCreate)
			 .append("gmtModify", this.gmtModify);
		 return sb.toString();
	 }
 
 } 
 