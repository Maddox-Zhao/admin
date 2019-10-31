package com.huaixuan.network.biz.domain; 
  
 import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
  
 /**  
  * (bibleUtil auto code generation) 
  * @version 3.2.0  
  */  
 public class Csvdate extends BaseObject implements Serializable { 
 		 /* @property: */
	 private long id;
	//外部订单号
	 private String outOrderNumber;
	//账户余额(元)
	 private double accountBalance;
	//时间
	 private Date time;
	//流水号
	 private String serialNumber;
	//支付宝交易号,
	 private String sellNumber;
	//交易对方Email
	 private String otherEmail;
	//交易对方
	 private String otherName;
	//用户编号,
	 private String otherNo;
	//收入（元）
	 private double income;
	//支出（元）
	 private double outlay;
	 //交易场所,
	 private String place;
	//商品名称
	 private String tradeName;
	//类型
	 private String type;
	//说明
	 private String remark;
	//是否匹配0匹配1是不匹配
	 private int ismatch;
	 //不匹配信息
	 private String match;
	 //'使用的支付方式：支付宝：zfb',  
	 private String payType;
	 
	 private Date               gmtCreate;
	    /* @property: */
	 private Date               gmtModify;
	    
	 /* Default constructor - creates a new instance with no values set. */
	 public Csvdate(){} 
	 /* @model: */
	 public Csvdate(java.util.List<String> param) {
	        super();
	        this.outOrderNumber = param.get(0).trim();
	        this.accountBalance = param.get(1)==null||param.get(1)==""?0.00:Double.parseDouble(param.get(1));
	        this.time = strToDate(param.get(2),"yyyy年MM月dd日 HH:mm:ss");
	        this.serialNumber = param.get(3).trim();
	        this.sellNumber = param.get(4).trim();
	        this.otherEmail = param.get(5).trim();
	        this.otherName = param.get(6).trim();
	        this.otherNo = param.get(7).trim();
	        this.income = param.get(8)!=null&&param.get(8).length()!=0?Double.parseDouble(param.get(8)):0.00;
	        this.outlay =param.get(9)!=null&&param.get(9).length()!=0?Double.parseDouble(param.get(9)):0.00;
	        this.place = param.get(10).trim();
	        this.tradeName = param.get(11).trim();
	        this.type = param.get(12).trim();
	        this.remark = param.get(13).trim();
	    }
	 public  Date strToDate(String str,String pattern){
	     Date dateTemp = null;
	     SimpleDateFormat formater2 = new SimpleDateFormat(pattern);
	        try {
	            dateTemp = formater2.parse(str);
	        } catch (Exception e) {
	        }
	        return dateTemp;
	 }

	 public void setId(long obj){
		 this.id = obj;
	 }

	 /* @model: */
	 public long getId(){
		 return this.id;
	 }
	 /* @model: */
	 public void setOutOrderNumber(String obj){
		 this.outOrderNumber = obj;
	 }

	 /* @model: */
	 public String getOutOrderNumber(){
		 return this.outOrderNumber;
	 }
	 /* @model: */
	 public void setAccountBalance(double obj){
		 this.accountBalance = obj;
	 }

	 /* @model: */
	 public double getAccountBalance(){
		 return this.accountBalance;
	 }
	 /* @model: */
	 public void setTime(Date obj){
		 this.time = obj;
	 }

	 /* @model: */
	 public Date getTime(){
		 return this.time;
	 }
	 /* @model: */
	 public void setSerialNumber(String obj){
		 this.serialNumber = obj;
	 }

	 /* @model: */
	 public String getSerialNumber(){
		 return this.serialNumber;
	 }
	 /* @model: */
	 public void setSellNumber(String obj){
		 this.sellNumber = obj;
	 }

	 /* @model: */
	 public String getSellNumber(){
		 return this.sellNumber;
	 }
	 /* @model: */
	 public void setOtherEmail(String obj){
		 this.otherEmail = obj;
	 }

	 /* @model: */
	 public String getOtherEmail(){
		 return this.otherEmail;
	 }
	 /* @model: */
	 public void setOtherName(String obj){
		 this.otherName = obj;
	 }

	 /* @model: */
	 public String getOtherName(){
		 return this.otherName;
	 }
	 /* @model: */
	 public void setOtherNo(String obj){
		 this.otherNo = obj;
	 }

	 /* @model: */
	 public String getOtherNo(){
		 return this.otherNo;
	 }
	 /* @model: */
	 public void setIncome(double obj){
		 this.income = obj;
	 }

	 /* @model: */
	 public double getIncome(){
		 return this.income;
	 }
	 /* @model: */
	 public void setOutlay(double obj){
		 this.outlay = obj;
	 }

	 /* @model: */
	 public double getOutlay(){
		 return this.outlay;
	 }
	 /* @model: */
	 public void setPlace(String obj){
		 this.place = obj;
	 }

	 /* @model: */
	 public String getPlace(){
		 return this.place;
	 }
	 /* @model: */
	 public void setTradeName(String obj){
		 this.tradeName = obj;
	 }

	 /* @model: */
	 public String getTradeName(){
		 return this.tradeName;
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
	 public void setRemark(String obj){
		 this.remark = obj;
	 }

	 /* @model: */
	 public String getRemark(){
		 return this.remark;
	 }
	 /* @model: */
	 public void setIsmatch(int obj){
		 this.ismatch = obj;
	 }

	 /* @model: */
	 public int getIsmatch(){
		 return this.ismatch;
	 }
	 /* @model: */
	 public void setMatch(String obj){
		 this.match = obj;
	 }

	 /* @model: */
	 public String getMatch(){
		 return this.match;
	 }
	/*{@inheritDoc}*/
	 public boolean equals(Object o) {
		 if (this == o) {
			 return true;
		 }
		 if (!(o instanceof Csvdate)) {
			 return false;
		 }
		 final Csvdate csvdate = (Csvdate) o;
		 return this.hashCode() == csvdate.hashCode();
	 }
	/*{@inheritDoc}*/
	 public int hashCode() {
		 return this.hashCode();
	 }
	/*{@inheritDoc}*/
	 public String toString() {
		 ToStringBuilder sb = new ToStringBuilder(this, ToStringStyle.DEFAULT_STYLE)
			 .append("id", this.id)
			 .append("outOrderNumber", this.outOrderNumber)
			 .append("accountBalance", this.accountBalance)
			 .append("time", this.time)
			 .append("serialNumber", this.serialNumber)
			 .append("sellNumber", this.sellNumber)
			 .append("otherEmail", this.otherEmail)
			 .append("otherName", this.otherName)
			 .append("otherNo", this.otherNo)
			 .append("income", this.income)
			 .append("outlay", this.outlay)
			 .append("place", this.place)
			 .append("tradeName", this.tradeName)
			 .append("type", this.type)
			 .append("remark", this.remark)
			 .append("ismatch", this.ismatch)
			 .append("match", this.match);
		 return sb.toString();
	 }
    public String getPayType() {
        return payType;
    }
    public void setPayType(String payType) {
        this.payType = payType;
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
 
 } 
 