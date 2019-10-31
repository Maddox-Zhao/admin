package com.huaixuan.network.biz.domain.goods;

 import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.huaixuan.network.biz.domain.BaseObject;


 public class PromationModel extends BaseObject {
		 /* @property: */
	 private long id;
	 /* @property: */
	 private String name;
	 /* @property: */
	 private String code;
	 /* @property: */
	 private String conditionExpression;
	 /* @property: */
	 private String promationExpression;
	 /* @property: */
	 private Date gmtCreate;
	 /* @property: */
	 private Date gmtModify;
	 /* Default constructor - creates a new instance with no values set. */
	 public PromationModel(){}
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
	 public void setCode(String obj){
		 this.code = obj;
	 }

	 /* @model: */
	 public String getCode(){
		 return this.code;
	 }
	 /* @model: */
	 public void setConditionExpression(String obj){
		 this.conditionExpression = obj;
	 }

	 /* @model: */
	 public String getConditionExpression(){
		 return this.conditionExpression;
	 }
	 /* @model: */
	 public void setPromationExpression(String obj){
		 this.promationExpression = obj;
	 }

	 /* @model: */
	 public String getPromationExpression(){
		 return this.promationExpression;
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
	        final PromationModel other = (PromationModel) obj;
	        if (name == null) {
	            if (other.name != null)
	                return false;
	        } else if (!name.equals(other.name))
	            return false;
	        if (code == null) {
	            if (other.code != null)
	                return false;
	        } else if (!code.equals(other.code))
	            return false;
	        if (conditionExpression == null) {
	            if (other.conditionExpression != null)
	                return false;
	        } else if (!conditionExpression.equals(other.conditionExpression))
	            return false;
	        if (promationExpression == null) {
	            if (other.promationExpression != null)
	                return false;
	        } else if (!promationExpression.equals(other.promationExpression))
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
	        final int prime = 31;
	        int result = 1;
	        result = prime * result + ((name == null) ? 0 : name.hashCode());
	        result = prime * result + ((code == null) ? 0 : code.hashCode());
	        result = prime * result + ((conditionExpression == null) ? 0 : conditionExpression.hashCode());
	        result = prime * result + ((promationExpression == null) ? 0 : promationExpression.hashCode());
	        result = prime * result + ((gmtCreate == null) ? 0 : gmtCreate.hashCode());
	        result = prime * result + ((gmtModify == null) ? 0 : gmtModify.hashCode());
	        return result;
	    }

	/*{@inheritDoc}*/
	 public String toString() {
		 ToStringBuilder sb = new ToStringBuilder(this, ToStringStyle.DEFAULT_STYLE)
			 .append("id", this.id)
			 .append("name", this.name)
			 .append("code", this.code)
			 .append("conditionExpression", this.conditionExpression)
			 .append("promationExpression", this.promationExpression)
			 .append("gmtCreate", this.gmtCreate)
			 .append("gmtModify", this.gmtModify);
		 return sb.toString();
	 }

 }
