package com.huaixuan.network.biz.domain.goods;

 import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.huaixuan.network.biz.domain.BaseObject;

 /**
  * ï¿ï¿ï¿ï¿ï¿ï¿ï¿ï¿ï¿ï¿ï¿ï¿ï¿ï¿ï¿ï¿ï¿ï¿ï¿ï¿ï¿ï¿(bibleUtil auto code generation)
  * @version 3.2.0
  */
 public class AttrGroup extends BaseObject {
 		 /**
	 *
	 */
	private static final long serialVersionUID = 1L;
		/* @property: */
	 private long id;
	 /* @property: */
	 private String groupName;
	 /* @property: */
	 private String groupDesc;
	 /* @property: */
	 private int enable;
	 /* Default constructor - creates a new instance with no values set. */
	 public AttrGroup(){}
	 /* @model:ï¿ï¿ï¿ï¿ï¿ï¿ï¿ï¿ */
	 public void setId(long obj){
		 this.id = obj;
	 }

	 /* @model:ï¿ï¿ï¿ï¿ï¿ï¿ */
	 public long getId(){
		 return this.id;
	 }
	 /* @model:ï¿ï¿ï¿ï¿ï¿ï¿ï¿ï¿ */
	 public void setGroupName(String obj){
		 this.groupName = obj;
	 }

	 /* @model:ï¿ï¿ï¿ï¿ï¿ï¿ */
	 public String getGroupName(){
		 return this.groupName;
	 }
	 /* @model:ï¿ï¿ï¿ï¿ï¿ï¿ï¿ï¿ */
	 public void setGroupDesc(String obj){
		 this.groupDesc = obj;
	 }

	 /* @model:ï¿ï¿ï¿ï¿ï¿ï¿ */
	 public String getGroupDesc(){
		 return this.groupDesc;
	 }
	 /* @model:ï¿ï¿ï¿ï¿ï¿ï¿ï¿ï¿ */
	 public void setEnable(int obj){
		 this.enable = obj;
	 }

	 /* @model:ï¿ï¿ï¿ï¿ï¿ï¿ */
	 public int getEnable(){
		 return this.enable;
	 }
	/*{@inheritDoc}*/
	 public boolean equals(Object o) {
		 if (this == o) {
			 return true;
		 }
		 if (!(o instanceof AttrGroup)) {
			 return false;
		 }
		 final AttrGroup attrgroup = (AttrGroup) o;
		 return this.hashCode() == attrgroup.hashCode();
	 }
	/*{@inheritDoc}*/
	 public int hashCode() {
		 return this.hashCode();
	 }
	/*{@inheritDoc}*/
	 public String toString() {
		 ToStringBuilder sb = new ToStringBuilder(this, ToStringStyle.DEFAULT_STYLE)
			 .append("id", this.id)
			 .append("groupName", this.groupName)
			 .append("groupDesc", this.groupDesc)
			 .append("enable", this.enable);
		 return sb.toString();
	 }

 }
