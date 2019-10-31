package com.huaixuan.network.biz.domain.trade; 
  
 import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.huaixuan.network.biz.domain.BaseObject;
  
 /**  
  * 代码自动生成(bibleUtil auto code generation) 
  * @version 3.2.0  
  */  
 public class PayPackage extends BaseObject implements Serializable { 
 		 /* @property: */
	 private Long id;
	 /* @property:支付包ID */
	 private Long packageId;
	 /* @property:交易ID */
	 private String tid;
	 /* @property:交易金额 */
	 private double amount;
	 /* @property: */
	 private Date gmtCreate;
	 /* @property: */
	 private Date gmtModify;
	 /* Default constructor - creates a new instance with no values set. */
	 public PayPackage(){} 
	 /* @model:设置 */
	 public void setId(Long obj){
		 this.id = obj;
	 }

	 /* @model:获取 */
	 public Long getId(){
		 return this.id;
	 }
	 /* @model:设置支付包ID */
	 public void setPackageId(Long obj){
		 this.packageId = obj;
	 }

	 /* @model:获取支付包ID */
	 public Long getPackageId(){
		 return this.packageId;
	 }
	 /* @model:设置交易ID */
	 public void setTid(String obj){
		 this.tid = obj;
	 }

	 /* @model:获取交易ID */
	 public String getTid(){
		 return this.tid;
	 }
	 /* @model:设置交易金额 */
	 public void setAmount(double obj){
		 this.amount = obj;
	 }

	 /* @model:获取交易金额 */
	 public double getAmount(){
		 return this.amount;
	 }
	 /* @model:设置 */
	 public void setGmtCreate(Date obj){
		 this.gmtCreate = obj;
	 }

	 /* @model:获取 */
	 public Date getGmtCreate(){
		 return this.gmtCreate;
	 }
	 /* @model:设置 */
	 public void setGmtModify(Date obj){
		 this.gmtModify = obj;
	 }

	 /* @model:获取 */
	 public Date getGmtModify(){
		 return this.gmtModify;
	 }
	/*{@inheritDoc}*/
	 public boolean equals(Object o) {
		 if (this == o) {
			 return true;
		 }
		 if (!(o instanceof PayPackage)) {
			 return false;
		 }
		 final PayPackage paypackage = (PayPackage) o;
		 return this.hashCode() == paypackage.hashCode();
	 }
	/*{@inheritDoc}*/
	 public int hashCode() {
		 return this.hashCode();
	 }
	/*{@inheritDoc}*/
	 public String toString() {
		 ToStringBuilder sb = new ToStringBuilder(this, ToStringStyle.DEFAULT_STYLE)
			 .append("id", this.id)
			 .append("packageId", this.packageId)
			 .append("tid", this.tid)
			 .append("amount", this.amount)
			 .append("gmtCreate", this.gmtCreate)
			 .append("gmtModify", this.gmtModify);
		 return sb.toString();
	 }
 
 } 
 