package com.huaixuan.network.biz.domain.trade; 
  
 import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.huaixuan.network.biz.domain.BaseObject;
  
 /**  
  * (bibleUtil auto code generation) 
  * @version 3.2.0  
  */  
 public class PackageTrade extends BaseObject implements Serializable { 
 		 /* @property: */
	 private Long id;
	 /* @property: */
	 private String tid;
	 /* @property: */
	 private String name;
	 /* @property: */
	 private Double goodsAmount;
	 /* @property: */
	 private Double packageAmount;
	 /* @property: */
	 private Integer number;
	 /* @property: */
	 private Date gmtCreate;
	 /* @property: */
	 private Date gmtModify;
	 
	 private boolean ischeck;
	 /* Default constructor - creates a new instance with no values set. */
	 public PackageTrade(){} 
	 /* @model: */
	 
	
	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getGoodsAmount() {
        return goodsAmount;
    }

    public void setGoodsAmount(Double goodsAmount) {
        this.goodsAmount = goodsAmount;
    }

    public Double getPackageAmount() {
        return packageAmount;
    }

    public void setPackageAmount(Double packageAmount) {
        this.packageAmount = packageAmount;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModify() {
        return gmtModify;
    }

    public void setGmtModify(Date gmtModify) {
        this.gmtModify = gmtModify;
    }

    /*{@inheritDoc}*/
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PackageTrade)) {
            return false;
        }
        final PackageTrade packageInstanse = (PackageTrade) o;
        return this.hashCode() == packageInstanse.hashCode();
    }
    
    /*{@inheritDoc}*/
	 public int hashCode() {
	     final int prime = 31;
         int result = 1;
         result = prime * result + ((id == null) ? 0 : id.hashCode());
         result = prime * result + ((tid == null) ? 0 : tid.hashCode());
         result = prime * result + ((name == null) ? 0 : name.hashCode());
         result = prime * result + ((goodsAmount==null)?0 : goodsAmount.intValue());
         result = prime * result + ((gmtCreate == null) ? 0 : gmtCreate.hashCode());
         result = prime * result + ((gmtModify == null) ? 0 : gmtModify.hashCode());
         result = prime * result + ((packageAmount == null) ? 0 : packageAmount.hashCode());
         result = prime * result + ((number==null)?0 : number.intValue());

         return result;
	 }
	/*{@inheritDoc}*/
	 public String toString() {
		 ToStringBuilder sb = new ToStringBuilder(this, ToStringStyle.DEFAULT_STYLE)
			 .append("id", this.id)
			 .append("tid", this.tid)
			 .append("name", this.name)
			 .append("goodsAmount", this.goodsAmount)
			 .append("packageAmount", this.packageAmount)
			 .append("number", this.number)
			 .append("gmtCreate", this.gmtCreate)
			 .append("gmtModify", this.gmtModify);
		 return sb.toString();
	 }


    public boolean isIscheck() {
        return ischeck;
    }


    public void setIscheck(boolean ischeck) {
        this.ischeck = ischeck;
    }
 
 } 
 