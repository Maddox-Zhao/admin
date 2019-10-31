package com.huaixuan.network.biz.domain.goods;

 import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.huaixuan.network.biz.domain.BaseObject;

 /**
  * ����������������������(bibleUtil auto code generation)
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
	 /* @model:�������� */
	 public void setId(long obj){
		 this.id = obj;
	 }

	 /* @model:������ */
	 public long getId(){
		 return this.id;
	 }
	 /* @model:�������� */
	 public void setGroupName(String obj){
		 this.groupName = obj;
	 }

	 /* @model:������ */
	 public String getGroupName(){
		 return this.groupName;
	 }
	 /* @model:�������� */
	 public void setGroupDesc(String obj){
		 this.groupDesc = obj;
	 }

	 /* @model:������ */
	 public String getGroupDesc(){
		 return this.groupDesc;
	 }
	 /* @model:�������� */
	 public void setEnable(int obj){
		 this.enable = obj;
	 }

	 /* @model:������ */
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
