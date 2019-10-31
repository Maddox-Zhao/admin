package com.huaixuan.network.biz.domain.admin; 
  
 import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
  
 public class Resources  implements Serializable { 
    
     /**
     * 
     */
    private static final long serialVersionUID = -3356593046569519484L;
    
    //活动模块的类型
     public static final String TYPE_ACTIVITY = "activity";
     /* @property: */
	 private long id;
	 /* @property: */
	 private Date gmtCreate;
	 /* @property: */
	 private Date gmtModify;
	 /* @property: */
	 private String type;
	 /* @property: */
	 private String name;
	 /* @property: */
	 private String nameCn;
	 /* @property: */
	 private String value;
	 /* @property: */
	 private long resourceOrder;
	 /* Default constructor - creates a new instance with no values set. */
	 public Resources(){} 
	 /* @model: */
	 public void setId(long obj){
		 this.id = obj;
	 }

	 /* @model: */
	 public long getId(){
		 return this.id;
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
	 public void setType(String obj){
		 this.type = obj;
	 }

	 /* @model: */
	 public String getType(){
		 return this.type;
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
	 public void setNameCn(String obj){
		 this.nameCn = obj;
	 }

	 /* @model: */
	 public String getNameCn(){
		 return this.nameCn;
	 }
	 /* @model: */
	 public void setValue(String obj){
		 this.value = obj;
	 }

	 /* @model: */
	 public String getValue(){
		 return this.value;
	 }
	 /* @model: */
	 public void setResourceOrder(long obj){
		 this.resourceOrder = obj;
	 }

	 /* @model: */
	 public long getResourceOrder(){
		 return this.resourceOrder;
	 }
	/*{@inheritDoc}*/
	 public boolean equals(Object o) {
		 if (this == o) {
			 return true;
		 }
		 if (!(o instanceof Resources)) {
			 return false;
		 }
		 final Resources resources = (Resources) o;
		 return this.hashCode() == resources.hashCode();
	 }
	/*{@inheritDoc}*/
	 public int hashCode() {
		 return this.hashCode();
	 }
	/*{@inheritDoc}*/
	 public String toString() {
		 ToStringBuilder sb = new ToStringBuilder(this, ToStringStyle.DEFAULT_STYLE)
			 .append("id", this.id)
			 .append("gmtCreate", this.gmtCreate)
			 .append("gmtModify", this.gmtModify)
			 .append("type", this.type)
			 .append("name", this.name)
			 .append("nameCn", this.nameCn)
			 .append("value", this.value)
			 .append("resourceOrder", this.resourceOrder);
		 return sb.toString();
	 }
 
 } 
 