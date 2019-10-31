package com.huaixuan.network.biz.domain.goods;

 import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.huaixuan.network.biz.domain.BaseObject;

 public class RuleDef extends BaseObject {
	 

	 private long id;
	 /* @property: */
	 private String ruleCode;
	 /* @property: */
	 private int ruleType;
	 /* @property: */
	 private String conditionExpression;
	 /* @property: */
	 private String resultExpression;
	 /* @property: */
	 private String remark;
	 /* @property: */
	 private Date gmtCreate;
	 /* @property: */
	 private Date gmtModify;
	 /* Default constructor - creates a new instance with no values set. */
	 public RuleDef(){}
	 /* @model: */
	 public void setId(long obj){
		 this.id = obj;
	 }

	 /* @model: */
	 public long getId(){
		 return this.id;
	 }
	 /* @model: */
	 public void setRuleCode(String obj){
		 this.ruleCode = obj;
	 }

	 /* @model: */
	 public String getRuleCode(){
		 return this.ruleCode;
	 }
	 /* @model: */
	 public void setRuleType(int obj){
		 this.ruleType = obj;
	 }

	 /* @model: */
	 public int getRuleType(){
		 return this.ruleType;
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
	 public void setResultExpression(String obj){
		 this.resultExpression = obj;
	 }

	 /* @model: */
	 public String getResultExpression(){
		 return this.resultExpression;
	 }
	 /* @model: */
	 public void setRemark(String obj){
		 this.remark = obj;
	 }

	 /* @model: */
	 public String getRemark(){
		 return this.remark;
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
	        final RuleDef other = (RuleDef) obj;
	        if (ruleCode == null) {
	            if (other.ruleCode != null)
	                return false;
	        } else if (!ruleCode.equals(other.ruleCode))
	            return false;
	        if (conditionExpression == null) {
	            if (other.conditionExpression != null)
	                return false;
	        } else if (!conditionExpression.equals(other.conditionExpression))
	            return false;
	        if (resultExpression == null) {
	            if (other.resultExpression != null)
	                return false;
	        } else if (!resultExpression.equals(other.resultExpression))
	            return false;
	        if (remark == null) {
	            if (other.remark != null)
	                return false;
	        } else if (!remark.equals(other.remark))
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
	        result = prime * result + ((ruleCode == null) ? 0 : ruleCode.hashCode());
	        result = prime * result + ((conditionExpression == null) ? 0 : conditionExpression.hashCode());
	        result = prime * result + ((resultExpression == null) ? 0 : resultExpression.hashCode());
	        result = prime * result + ((remark == null) ? 0 : remark.hashCode());
	        result = prime * result + ((gmtCreate == null) ? 0 : gmtCreate.hashCode());
	        result = prime * result + ((gmtModify == null) ? 0 : gmtModify.hashCode());
	        return result;
	    }
	/*{@inheritDoc}*/
	 public String toString() {
		 ToStringBuilder sb = new ToStringBuilder(this, ToStringStyle.DEFAULT_STYLE)
			 .append("id", this.id)
			 .append("ruleCode", this.ruleCode)
			 .append("ruleType", this.ruleType)
			 .append("conditionExpression", this.conditionExpression)
			 .append("resultExpression", this.resultExpression)
			 .append("remark", this.remark)
			 .append("gmtCreate", this.gmtCreate)
			 .append("gmtModify", this.gmtModify);
		 return sb.toString();
	 }

 }
