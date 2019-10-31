package com.huaixuan.network.biz.domain.shop;

 import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.huaixuan.network.biz.domain.BaseObject;

 /**
  * (bibleUtil auto code generation)
  * @version 3.2.0
  */
 public class Article extends BaseObject implements Serializable {
 		 /* @property: */
	 private long id;
	 /* @property: */
	 private String catCode;
	 /* @property: */
	 private String title;
	 /* @property: */
	 private String digest;
	 /* @property: */
	 private String content;
	 /* @property: */
	 private String author;
	 /* @property: */
	 private String keywords;
	 /* @property: */
	 private int isTop;
	 /* @property: */
	 private int isShow;
	 /* @property: */
	 private String source;
	 /* @property: */
	 private String sourceUrl;
	 /* @property: */
	 private int clickCount;
	 /* @property: */
	 private Date gmtCreate;
	 /* @property: */
	 private Date gmtModify;
	 /* @property: */
	 private String catName;
	 public String getCatName() {
		return catName;
	}
	public void setCatName(String catName) {
		this.catName = catName;
	}
	/* Default constructor - creates a new instance with no values set. */
	 public Article(){}
	 /* @model: */
	 public void setId(long obj){
		 this.id = obj;
	 }

	 /* @model: */
	 public long getId(){
		 return this.id;
	 }
	 /* @model: */
	 public void setCatCode(String obj){
		 this.catCode = obj;
	 }

	 /* @model: */
	 public String getCatCode(){
		 return this.catCode;
	 }
	 /* @model: */
	 public void setTitle(String obj){
		 this.title = obj;
	 }

	 /* @model: */
	 public String getTitle(){
		 return this.title;
	 }
	 /* @model: */
	 public void setDigest(String obj){
		 this.digest = obj;
	 }

	 /* @model: */
	 public String getDigest(){
		 return this.digest;
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
	 public void setAuthor(String obj){
		 this.author = obj;
	 }

	 /* @model: */
	 public String getAuthor(){
		 return this.author;
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
	 public void setIsTop(int obj){
		 this.isTop = obj;
	 }

	 /* @model: */
	 public int getIsTop(){
		 return this.isTop;
	 }
	 /* @model: */
	 public void setIsShow(int obj){
		 this.isShow = obj;
	 }

	 /* @model: */
	 public int getIsShow(){
		 return this.isShow;
	 }
	 /* @model: */
	 public void setSource(String obj){
		 this.source = obj;
	 }

	 /* @model: */
	 public String getSource(){
		 return this.source;
	 }
	 /* @model: */
	 public void setSourceUrl(String obj){
		 this.sourceUrl = obj;
	 }

	 /* @model: */
	 public String getSourceUrl(){
		 return this.sourceUrl;
	 }
	 /* @model: */
	 public void setClickCount(int obj){
		 this.clickCount = obj;
	 }

	 /* @model: */
	 public int getClickCount(){
		 return this.clickCount;
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
	 public boolean equals(Object obj) {
		 if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			final Article other = (Article) obj;
			if (catCode == null) {
				if (other.catCode != null)
					return false;
			} else if (!catCode.equals(other.catCode))
				return false;
			if (title == null) {
				if (other.title != null)
					return false;
			} else if (!title.equals(other.title))
				return false;
			if (digest == null) {
				if (other.digest != null)
					return false;
			} else if (!digest.equals(other.digest))
				return false;
			if (gmtCreate == null) {
				if (other.gmtCreate != null)
					return false;
			} else if (!gmtCreate.equals(other.gmtCreate))
				return false;
			if (gmtModify == null) {
				if (other.gmtModify != null)
					return false;
			} else if (!gmtModify.equals(other.gmtModify))
				return false;
			if (content == null) {
				if (other.content != null)
					return false;
			} else if (!content.equals(other.content))
				return false;
			if (author == null) {
				if (other.author != null)
					return false;
			} else if (!author.equals(other.author))
				return false;
			if (keywords == null) {
				if (other.keywords != null)
					return false;
			} else if (!keywords.equals(other.keywords))
				return false;
			if (source == null) {
				if (other.source != null)
					return false;
			} else if (!source.equals(other.source))
				return false;
			if (sourceUrl == null) {
				if (other.sourceUrl != null)
					return false;
			} else if (!sourceUrl.equals(other.sourceUrl))
				return false;
			if (catName == null) {
				if (other.catName != null)
					return false;
			} else if (!catName.equals(other.catName))
				return false;

			return true;
	 }
	/*{@inheritDoc}*/
	 public int hashCode() {
		 final int prime = 31;
			int result = 1;
			result = prime * result
					+ ((catCode == null) ? 0 : catCode.hashCode());
			result = prime * result + ((title == null) ? 0 : title.hashCode());
			result = prime * result + ((digest == null) ? 0 : digest.hashCode());
			result = prime * result + ((gmtCreate == null) ? 0 : gmtCreate.hashCode());
			result = prime * result + ((gmtModify == null) ? 0 : gmtModify.hashCode());
			result = prime * result + ((content == null) ? 0 : content.hashCode());
			result = prime * result + ((author == null) ? 0 : author.hashCode());
			result = prime * result + ((keywords == null) ? 0 : keywords.hashCode());
			result = prime * result + ((source == null) ? 0 : source.hashCode());
			result = prime * result + ((sourceUrl == null) ? 0 : sourceUrl.hashCode());
			result = prime * result + ((catName == null) ? 0 : catName.hashCode());
			return result;
	 }
	/*{@inheritDoc}*/
	 public String toString() {
		 ToStringBuilder sb = new ToStringBuilder(this, ToStringStyle.DEFAULT_STYLE)
			 .append("id", this.id)
			 .append("catCode", this.catCode)
			 .append("title", this.title)
			 .append("digest", this.digest)
			 .append("content", this.content)
			 .append("author", this.author)
			 .append("keywords", this.keywords)
			 .append("isTop", this.isTop)
			 .append("isShow", this.isShow)
			 .append("source", this.source)
			 .append("sourceUrl", this.sourceUrl)
			 .append("clickCount", this.clickCount)
			 .append("gmtCreate", this.gmtCreate)
			 .append("gmtModify", this.gmtModify);
		 return sb.toString();
	 }

 }
