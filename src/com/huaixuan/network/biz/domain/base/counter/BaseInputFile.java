/**
 * created since 2010-6-7
 */
package com.huaixuan.network.biz.domain.base.counter;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.huaixuan.network.biz.domain.BaseObject;

/**
 * @author guoyj
 * @version $Id: BaseInputFile.java,v 0.1 2010-6-7 ÉÏÎç10:58:48 guoyj Exp $
 */
public class BaseInputFile extends BaseObject implements Serializable {

    private static final long serialVersionUID = -6118991570131967053L;
    
    
     /* @property:¦Ê,pk,seq */
     private Long batchId;
     /* @property:C:T:/ */
     private String operateType;
     /* @property: */
     private String bankType;
     /* @property: */
     private Long waitDealCount;
     /* @property:1?2§µ3 */
     private Long status;
     /* @property: */
     private Date gmtCreate;
     /* @property: */
     private Date  gmtDealDate;
     /* @property: */
     private String dealOperator;
     /* @property: */
     private Date gmtModified;
     
     private String fileName;
     /* Default constructor - creates a new instance with no values set. */
     public BaseInputFile(){} 
     /* @model:¦Ê,pk,seq */
     public void setBatchId(Long obj){
         this.batchId = obj;
     }

     /* @model:¦Ê,pk,seq */
     public Long getBatchId(){
         return this.batchId;
     }
     /* @model:¨°C:T:/ */
     public void setOperateType(String obj){
         this.operateType = obj;
     }

     /* @model:C:T:/ */
     public String getOperateType(){
         return this.operateType;
     }
     /* @model: */
     public void setBankType(String obj){
         this.bankType = obj;
     }

     /* @model: */
     public String getBankType(){
         return this.bankType;
     }
     /* @model: */
     public void setWaitDealCount(Long obj){
         this.waitDealCount = obj;
     }

     /* @model: */
     public Long getWaitDealCount(){
         return this.waitDealCount;
     }
     /* @model:1?2§µ3 */
     public void setStatus(Long obj){
         this.status = obj;
     }

     /* @model:1?2§µ3 */
     public Long getStatus(){
         return this.status;
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
     public void setGmtDealDate(Date obj){
         this.gmtDealDate = obj;
     }

     /* @model: */
     public Date getGmtDealDate(){
         return this.gmtDealDate;
     }
     /* @model: */
     public void setDealOperator(String obj){
         this.dealOperator = obj;
     }

     /* @model: */
     public String getDealOperator(){
         return this.dealOperator;
     }
     /* @model: */
     public void setGmtModified(Date obj){
         this.gmtModified = obj;
     }

     /* @model: */
     public Date  getGmtModified(){
         return this.gmtModified;
     }
    /*{@inheritDoc}*/
     public boolean equals(Object o) {
         if (this == o) {
             return true;
         }
         if (!(o instanceof BaseInputFile)) {
             return false;
         }
         final BaseInputFile epinputfile = (BaseInputFile) o;
         return this.hashCode() == epinputfile.hashCode();
     }
    /*{@inheritDoc}*/
     public String toString() {
         ToStringBuilder sb = new ToStringBuilder(this, ToStringStyle.DEFAULT_STYLE)
             .append("batchId", this.batchId)
             .append("operateType", this.operateType)
             .append("bankType", this.bankType)
             .append("waitDealCount", this.waitDealCount)
             .append("status", this.status)
             .append("gmtCreate", this.gmtCreate)
             .append("gmtDealDate", this.gmtDealDate)
             .append("deal Operator", this.dealOperator)
             .append("gmtModified", this.gmtModified)
             .append("fileName", this.fileName);
         return sb.toString();
     }
    public String getFileName() {
        return fileName;
    }
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    /**
     * @see java.lang.Object#hashCode()
     */
    public int hashCode() {
        return new HashCodeBuilder(-209334269, 132032747).appendSuper(super.hashCode()).append(
            this.gmtModified).append(this.gmtCreate).append(this.operateType).append(this.status)
            .append(this.bankType).append(this.waitDealCount).append(this.fileName).append(
                this.gmtDealDate).append(this.batchId).append(this.dealOperator).toHashCode();
    }

}
