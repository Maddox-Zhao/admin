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
 public class AdDetail extends BaseObject implements Serializable {
 		 /* @property: */
	 private long id;
	 /* @property: */
	 private String link;
	 /* @property: */
	 private String mediaCode;
	 /* @property: */
	 private String mediaType;
	 /* @property: */
	 private int sort;
    /* @property: */
    private String            sortstr;
	 /* @property: */
	 private int clickCount;
	 /* @property: */
	 private Date gmtCreate;
	 /* @property: */
	 private Date gmtModify;
	 /* Default constructor - creates a new instance with no values set. */
	 /* MediaType类型为picture*/
	 public static final String MediaType_picture = "picture";
	 /* MediaType类型为text*/
	 public static final String MediaType_text = "text";
	 /* MediaType类型为flash*/
	 public static final String MediaType_flash = "flash";

	 private long adId;

	 public long getAdId() {
		return adId;
	}
	public void setAdId(long adId) {
		this.adId = adId;
	}
	public AdDetail(){}
	 /* @model: */
	 public void setId(long obj){
		 this.id = obj;
	 }

	 /* @model: */
	 public long getId(){
		 return this.id;
	 }
	 /* @model: */
	 public void setLink(String obj){
		 this.link = obj;
	 }

	 /* @model: */
	 public String getLink(){
		 return this.link;
	 }
	 /* @model: */
	 public void setMediaCode(String obj){
		 this.mediaCode = obj;
	 }

	 /* @model: */
	 public String getMediaCode(){
		 return this.mediaCode;
	 }
	 /* @model: */
	 public void setMediaType(String obj){
		 this.mediaType = obj;
	 }

	 /* @model: */
	 public String getMediaType(){
		 return this.mediaType;
	 }
	 /* @model: */
	 public void setSort(int obj){
		 this.sort = obj;
	 }

	 /* @model: */
	 public int getSort(){
		 return this.sort;
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
			final AdDetail other = (AdDetail) obj;
			if (link == null) {
				if (other.link != null)
					return false;
			} else if (!link.equals(other.link))
				return false;
			if (mediaCode == null) {
				if (other.mediaCode != null)
					return false;
			} else if (!mediaCode.equals(other.mediaCode))
				return false;
			if (mediaType == null) {
				if (other.mediaType != null)
					return false;
			} else if (!mediaType.equals(other.mediaType))
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


			return true;
	 }
	/*{@inheritDoc}*/
	 public int hashCode() {
		 final int prime = 27;
			int result = 1;
			result = prime * result
					+ ((link == null) ? 0 : link.hashCode());
			result = prime * result + ((mediaCode == null) ? 0 : mediaCode.hashCode());
			result = prime * result + ((mediaType == null) ? 0 : mediaType.hashCode());
			result = prime * result + ((gmtCreate == null) ? 0 : gmtCreate.hashCode());
			result = prime * result + ((gmtModify == null) ? 0 : gmtModify.hashCode());
			return result;
	 }
	/*{@inheritDoc}*/
	 public String toString() {
		 ToStringBuilder sb = new ToStringBuilder(this, ToStringStyle.DEFAULT_STYLE)
			 .append("id", this.id)
			 .append("link", this.link)
			 .append("mediaCode", this.mediaCode)
			 .append("mediaType", this.mediaType)
			 .append("sort", this.sort)
			 .append("clickCount", this.clickCount)
			 .append("gmtCreate", this.gmtCreate)
			 .append("gmtModify", this.gmtModify);
		 return sb.toString();
	 }
	public String getSortstr() {
		return sortstr;
	}
	public void setSortstr(String sortstr) {
		this.sortstr = sortstr;
	}

 }
