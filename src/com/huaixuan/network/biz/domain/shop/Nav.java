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
 public class Nav extends BaseObject implements Serializable {
 		 /* @property: */
	 private long id;
	 /* @property: */
	 private String title;
	 /* @property: */
	 private int isShow;
	 /* @property: */
	 private int sort;
	 /* @property: */
	 private int opennew;
	 /* @property: */
	 private String isShowstr;
	 /* @property: */
	 private String isOpenstr;
	 /* @property: */
	 private String sortstr;
	 /* @property: */
	 /* @property: */
	 private Date gmtCreate;
	 /* @property: */
	 private Date gmtModify;
	 /* @property: */
	 private String link;
	 /* @property: */
	 private String type;

	 /* @property: is_showœ‘ æ◊¥Ã¨delete*/
	 public static final int Isshow_yes = 1;
	 /* @property: is_show≤ªœ‘ æ◊¥Ã¨delete*/
	 public static final int Isshow_no = -1;

	 /* @property: is_showœ‘ æ◊¥Ã¨delete*/
	 public static final int Isopen_yes = 1;
	 /* @property: is_show≤ªœ‘ æ◊¥Ã¨delete*/
	 public static final int Isopen_no = 2;

	 /* Default constructor - creates a new instance with no values set. */
	 public Nav(){}
	 /* @model: */
	 public void setId(long obj){
		 this.id = obj;
	 }

	 /* @model: */
	 public long getId(){
		 return this.id;
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
	 public void setIsShow(int obj){
		 this.isShow = obj;
	 }

	 /* @model: */
	 public int getIsShow(){
		 return this.isShow;
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
	 public void setOpennew(int obj){
		 this.opennew = obj;
	 }

	 /* @model: */
	 public int getOpennew(){
		 return this.opennew;
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
	 public void setLink(String obj){
		 this.link = obj;
	 }

	 /* @model: */
	 public String getLink(){
		 return this.link;
	 }
	 /* @model: */
	 public void setType(String obj){
		 this.type = obj;
	 }

	 /* @model: */
	 public String getType(){
		 return this.type;
	 }
	/*{@inheritDoc}*/
	 public boolean equals(Object o) {
		 if (this == o) {
			 return true;
		 }
		 if (!(o instanceof Nav)) {
			 return false;
		 }
		 final Nav nav = (Nav) o;
		 return this.hashCode() == nav.hashCode();
	 }
	/*{@inheritDoc}*/
	 public int hashCode() {
		 return this.hashCode();
	 }
	/*{@inheritDoc}*/
	 public String toString() {
		 ToStringBuilder sb = new ToStringBuilder(this, ToStringStyle.DEFAULT_STYLE)
			 .append("id", this.id)
			 .append("title", this.title)
			 .append("isShow", this.isShow)
			 .append("sort", this.sort)
			 .append("opennew", this.opennew)
			 .append("gmtCreate", this.gmtCreate)
			 .append("gmtModify", this.gmtModify)
			 .append("link", this.link)
			 .append("type", this.type);
		 return sb.toString();
	 }
	public String getIsShowstr() {
		return isShowstr;
	}
	public void setIsShowstr(String isShowstr) {
		this.isShowstr = isShowstr;
	}
	public String getIsOpenstr() {
		return isOpenstr;
	}
	public void setIsOpenstr(String isOpenstr) {
		this.isOpenstr = isOpenstr;
	}
	public String getSortstr() {
		return sortstr;
	}
	public void setSortstr(String sortstr) {
		this.sortstr = sortstr;
	}

 }
