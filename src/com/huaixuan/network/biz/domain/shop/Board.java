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
 public class Board extends BaseObject implements Serializable { 
 		 /* @property: */
	 private Long id;
	 /* @property: */
	 private Long shopId;
	 /* @property: */
	 private String name;
	 /* @property: */
	 private String title;
	 /* @property: */
	 private String position;
	 /* @property: */
	 private Integer priority;
	 /* @property: */
	 private String display;
	 /* @property: */
	 private Integer period;
	 /* @property: */
	 private Integer amount;
	 /* @property: */
	 private Date gmtCreate;
	 /* @property: */
	 private Date gmtModify;
	 /* Default constructor - creates a new instance with no values set. */
	 public Board(){} 
	 /* @model: */
	
	 
	 public Long getShopId() {
        return shopId;
    }
    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }


    public void setShopId(Long shopId) {
        this.shopId = shopId;
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
	 public void setTitle(String obj){
		 this.title = obj;
	 }

	 /* @model: */
	 public String getTitle(){
		 return this.title;
	 }
	 /* @model: */
	 public void setPosition(String obj){
		 this.position = obj;
	 }

	 /* @model: */
	 public String getPosition(){
		 return this.position;
	 }
	 
	 public Integer getPriority() {
        return priority;
    }
    public void setPriority(Integer priority) {
        this.priority = priority;
    }
    /* @model: */
	 public void setDisplay(String obj){
		 this.display = obj;
	 }

	 /* @model: */
	 public String getDisplay(){
		 return this.display;
	 }
	
	 public Integer getPeriod() {
        return period;
    }
    public void setPeriod(Integer period) {
        this.period = period;
    }
    public Integer getAmount() {
        return amount;
    }
    public void setAmount(Integer amount) {
        this.amount = amount;
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
		 if (!(o instanceof Board)) {
			 return false;
		 }
		 final Board board = (Board) o;
		 return this.hashCode() == board.hashCode();
	 }
	/*{@inheritDoc}*/
	 public int hashCode() {
	     final int prime = 31;
	        int result = 1;
	        result = prime * result + ((name == null) ? 0 : name.hashCode());
	        result = prime * result + ((title == null) ? 0 : title.hashCode());
	        result = prime * result + ((position == null) ? 0 : position.hashCode());
	        result = prime * result + ((priority==null)?0 : priority.intValue());
	        result = prime * result + ((gmtCreate == null) ? 0 : gmtCreate.hashCode());
	        result = prime * result + ((gmtModify == null) ? 0 : gmtModify.hashCode());
	        result = prime * result + ((display == null) ? 0 : display.hashCode());
	        result = prime * result + ((period==null)?0 : period.intValue());
	        result = prime * result + ((amount==null)?0 : amount.intValue());

	        return result;
	 }
	/*{@inheritDoc}*/
	 public String toString() {
		 ToStringBuilder sb = new ToStringBuilder(this, ToStringStyle.DEFAULT_STYLE)
			 .append("id", this.id)
			 .append("shopId", this.shopId)
			 .append("name", this.name)
			 .append("title", this.title)
			 .append("position", this.position)
			 .append("priority", this.priority)
			 .append("display", this.display)
			 .append("period", this.period)
			 .append("amount", this.amount)
			 .append("gmtCreate", this.gmtCreate)
			 .append("gmtModify", this.gmtModify);
		 return sb.toString();
	 }
 
 } 
 