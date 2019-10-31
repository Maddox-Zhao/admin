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
 public class Keyword extends BaseObject implements Serializable {
 		 /* @property: */
	 private long id;
	 /* @property: */
	 private String name;
	 /* @property: */
	 private String link;
	 /* @property: */
	 private String desc;
	 /* @property: */
	 private int sort;
	 /* @property: */
	 private String sortstr;
	 /* @property: */
	 private Date gmtCreate;
	 /* @property: */
	 private Date gmtModify;
	 /* @property: */
	 private Long shopId;
	 /* Default constructor - creates a new instance with no values set. */
	 public Keyword(){}
	 /* @model: */
	 public void setId(long obj){
		 this.id = obj;
	 }

	 /* @model: */
	 public long getId(){
		 return this.id;
	 }
	 /* @model: */
	 public void setName(String obj){
		 this.name = obj;
	 }

	 /* @model: */
	 public String getName(){
		 return this.name;
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
	 public void setDesc(String obj){
		 this.desc = obj;
	 }

	 /* @model: */
	 public String getDesc(){
		 return this.desc;
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
	 public void setShopId(Long obj){
		 this.shopId = obj;
	 }

	 /* @model: */
	 public Long getShopId(){
		 return this.shopId;
	 }
	/*{@inheritDoc}*/
	 public boolean equals(Object obj) {
		 if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			final Keyword other = (Keyword) obj;
			if (name == null) {
				if (other.name != null)
					return false;
			} else if (!name.equals(other.name))
				return false;
			if (link == null) {
				if (other.link != null)
					return false;
			} else if (!link.equals(other.link))
				return false;
			if (desc == null) {
				if (other.desc != null)
					return false;
			} else if (!desc.equals(other.desc))
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
			if (shopId == null) {
				if (other.shopId != null)
					return false;
			} else if (!shopId.equals(other.shopId))
				return false;

			return true;
	 }
	/*{@inheritDoc}*/
	 public int hashCode() {
		 final int prime = 31;
			int result = 1;
			result = prime * result
					+ ((name == null) ? 0 : name.hashCode());
			result = prime * result + ((link == null) ? 0 : link.hashCode());
			result = prime * result + ((desc == null) ? 0 : desc.hashCode());
			result = prime * result + ((gmtCreate == null) ? 0 : gmtCreate.hashCode());
			result = prime * result + ((gmtModify == null) ? 0 : gmtModify.hashCode());
			result = prime * result + ((shopId == null) ? 0 : shopId.hashCode());
			return result;
	 }
	/*{@inheritDoc}*/
	 public String toString() {
		 ToStringBuilder sb = new ToStringBuilder(this, ToStringStyle.DEFAULT_STYLE)
			 .append("id", this.id)
			 .append("name", this.name)
			 .append("link", this.link)
			 .append("desc", this.desc)
			 .append("sort", this.sort)
			 .append("gmtCreate", this.gmtCreate)
			 .append("gmtModify", this.gmtModify)
			 .append("shopId", this.shopId);
		 return sb.toString();
	 }
	public String getSortstr() {
		return sortstr;
	}
	public void setSortstr(String sortstr) {
		this.sortstr = sortstr;
	}

 }
