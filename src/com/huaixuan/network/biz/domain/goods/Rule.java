package com.huaixuan.network.biz.domain.goods;

 import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.huaixuan.network.biz.domain.BaseObject;


 public class Rule extends BaseObject {
		 /* @property: */
	 private long id;
	 /* @property: */
	 private long promationId;
	 /* @property: */
	 private String ruleCode;
	 /* @property: */
	 private String conditionValue;
	 /* @property: */
	 private String resultValue;
	 /* @property: */
	 private String exrtaInfo;
	 /* @property: */
	 private Date gmtCreate;
	 /* @property: */
	 private Date gmtModify;
	 /* @property: */
	 private int ruleType;
	 /* Default constructor - creates a new instance with no values set. */
	 public Rule(){}
	 /* @model: */
	 public void setId(long obj){
		 this.id = obj;
	 }

	 /* @model: */
	 public long getId(){
		 return this.id;
	 }
	 /* @model: */
	 public void setPromationId(long obj){
		 this.promationId = obj;
	 }

	 /* @model: */
	 public long getPromationId(){
		 return this.promationId;
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
	 public void setConditionValue(String obj){
		 this.conditionValue = obj;
	 }

	 /* @model: */
	 public String getConditionValue(){
		 return this.conditionValue;
	 }
	 /* @model: */
	 public void setResultValue(String obj){
		 this.resultValue = obj;
	 }

	 /* @model: */
	 public String getResultValue(){
		 return this.resultValue;
	 }
	 /* @model: */
	 public void setExrtaInfo(String obj){
		 this.exrtaInfo = obj;
	 }

	 /* @model: */
	 public String getExrtaInfo(){
		 return this.exrtaInfo;
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
		 if (!(o instanceof Rule)) {
			 return false;
		 }
		 final Rule rule = (Rule) o;
		 return this.hashCode() == rule.hashCode();
	 }
	/*{@inheritDoc}*/
	 public int hashCode() {
	     final int prime = 31;
         int result = 1;
         result = prime * result + ((ruleCode == null) ? 0 : ruleCode.hashCode());
         result = prime * result + ((conditionValue == null) ? 0 : conditionValue.hashCode());
         result = prime * result + ((resultValue == null) ? 0 : resultValue.hashCode());
         result = prime * result + ((exrtaInfo == null) ? 0 : exrtaInfo.hashCode());
         //result = prime * result + ((ruleType == null) ? 0 : ruleType.hashCode());
         result = prime * result + ((gmtCreate == null) ? 0 : gmtCreate.hashCode());
         result = prime * result + ((gmtModify == null) ? 0 : gmtModify.hashCode());
         return result;
	 }
	/*{@inheritDoc}*/
	 public String toString() {
		 ToStringBuilder sb = new ToStringBuilder(this, ToStringStyle.DEFAULT_STYLE)
			 .append("id", this.id)
			 .append("promationId", this.promationId)
			 .append("ruleCode", this.ruleCode)
			 .append("conditionValue", this.conditionValue)
			 .append("resultValue", this.resultValue)
			 .append("exrtaInfo", this.exrtaInfo)
			 .append("gmtCreate", this.gmtCreate)
			 .append("gmtModify", this.gmtModify)
			 .append("ruleType", this.ruleType);
		 return sb.toString();
	 }
    public int getRuleType() {
        return ruleType;
    }
    public void setRuleType(int ruleType) {
        this.ruleType = ruleType;
    }

 }
