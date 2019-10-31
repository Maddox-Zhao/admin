package com.huaixuan.network.biz.domain.goods;

 import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.huaixuan.network.biz.domain.BaseObject;

 public class Promation extends BaseObject {
 		 /**
	 *
	 */
		 /* @property: */
	 private long id;
	 /* @property: */

	 /* @property: */
	 private String modeCode;
	 /* @property: */
	 private String name;
	 /* @property: */
	 private String isFreeze;
	 /* @property: */
	 private Date startDate;
	 /* @property: */
	 private Date endDate;
	 /* @property: */
	 private Date gmtCreate;
	 /* @property: */
	 private Date gmtModify;

	 private double price;

	 private String startDate_hour;

	 private String endDate_hour;

	 private String endDate_str;

	 private String startDate_str;

	 //套餐类型:组合销售code
	 public static final String Promation_combined_sale   = "combined_sale";
	 //套餐类型:买就赠code
	 public static final String Promation_sale_give   = "sale_give";
	 //套餐类型:全场金额满就减金额code
	 public static final String Promation_full_reduce  = "full_reduce";
	 //套餐类型:全场金额满就送物品code
	 public static final String Promation_full_give  = "full_give";
	 
	/* Default constructor - creates a new instance with no values set. */
	 public Promation(){}
	 /* @model: */
	 public void setId(long obj){
		 this.id = obj;
	 }

	 /* @model: */
	 public long getId(){
		 return this.id;
	 }

	 /* @model: */
	 public void setModeCode(String obj){
		 this.modeCode = obj;
	 }

	 /* @model: */
	 public String getModeCode(){
		 return this.modeCode;
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
	 public void setIsFreeze(String obj){
		 this.isFreeze = obj;
	 }

	 /* @model: */
	 public String getIsFreeze(){
		 return this.isFreeze;
	 }
	 /* @model: */
	 public void setStartDate(Date obj){
		 this.startDate = obj;
	 }

	 /* @model: */
	 public Date getStartDate(){
		 return this.startDate;
	 }
	 /* @model: */
	 public void setEndDate(Date obj){
		 this.endDate = obj;
	 }

	 /* @model: */
	 public Date getEndDate(){
		 return this.endDate;
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
		 if (!(o instanceof Promation)) {
			 return false;
		 }
		 final Promation promation = (Promation) o;
		 return this.hashCode() == promation.hashCode();
	 }
	/*{@inheritDoc}*/
	 public int hashCode() {

	     final int prime = 31;
	        int result = 1;
	        result = prime * result + ((modeCode == null) ? 0 : modeCode.hashCode());
	        result = prime * result + ((name == null) ? 0 : name.hashCode());
	        result = prime * result + ((isFreeze == null) ? 0 : isFreeze.hashCode());
	        result = prime * result + ((startDate == null) ? 0 : startDate.hashCode());
	        result = prime * result + ((endDate == null) ? 0 : endDate.hashCode());
	        result = prime * result + ((gmtCreate == null) ? 0 : gmtCreate.hashCode());
	        result = prime * result + ((gmtModify == null) ? 0 : gmtModify.hashCode());
        return result;

	 }
	/*{@inheritDoc}*/
	 public String toString() {
		 ToStringBuilder sb = new ToStringBuilder(this, ToStringStyle.DEFAULT_STYLE)
			 .append("id", this.id)
			 .append("modeCode", this.modeCode)
			 .append("name", this.name)
			 .append("isFreeze", this.isFreeze)
			 .append("startDate", this.startDate)
			 .append("endDate", this.endDate)
			 .append("gmtCreate", this.gmtCreate)
			 .append("gmtModify", this.gmtModify);
		 return sb.toString();
	 }
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}


    public String getStartDate_hour() {
        return startDate_hour;
    }
    public void setStartDate_hour(String startDate_hour) {
        this.startDate_hour = startDate_hour;
    }
    public String getEndDate_hour() {
        return endDate_hour;
    }
    public void setEndDate_hour(String endDate_hour) {
        this.endDate_hour = endDate_hour;
    }
    public String getEndDate_str() {
        return endDate_str;
    }
    public void setEndDate_str(String endDate_str) {
        this.endDate_str = endDate_str;
    }
    public String getStartDate_str() {
        return startDate_str;
    }
    public void setStartDate_str(String startDate_str) {
        this.startDate_str = startDate_str;
    }
   
 }
