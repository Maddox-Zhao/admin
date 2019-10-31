package com.huaixuan.network.biz.domain.taobao;

 import java.io.Serializable;
import java.util.Date;

import com.huaixuan.network.biz.domain.BaseObject;

 /**
  * (bibleUtil auto code generation)
  * @version 3.2.0
  */
 public class TaobaoApply extends BaseObject implements Serializable {
 		 /**
	 *
	 */
	private static final long serialVersionUID = -5384735440213569619L;
		/* @property:ID */
	 private Long id;
	 /* @property: */
	 private Date gmtCreate;
	 /* @property: */
	 private Date gmtModify;
	 /* @property:ID */
	 private Long userId;
	 private String account;
	 /* @property:QQ */
	 private String paramOne;
	 /* @property:UID */
	 private String paramTwo;
     /* @property:淘宝卖家昵称 */
     private String paramThree;
	 /* @property:URL */
	 private String shopUrl;
	 /* @property:ID */
	 private Long postId;
	 /* @property: */
	 private double postNormal;
	 /* @property:EMS */
	 private double postEms;
	 /* @property: */
	 private double postExpress;
	 /* @property:newpassfail */
	 private String status;
	 /* @property:paipai:taobao: */
	 private String type;
	 /* @property: */
	 private String memo;
	 /* @property: */
	 private Date gmtSync;
	 //同步生成订单时候的物流公司ID
     private Long ownExpressId;
     //同步订单发货状态时候的物流公司CODE
     private String interfaceExpressCode;
     
     public Long getOwnExpressId() {
         return ownExpressId;
     }
     public void setOwnExpressId(Long ownExpressId) {
         this.ownExpressId = ownExpressId;
     }
     public String getInterfaceExpressCode() {
         return interfaceExpressCode;
     }
     public void setInterfaceExpressCode(String interfaceExpressCode) {
         this.interfaceExpressCode = interfaceExpressCode;
     }
     
	 /* Default constructor - creates a new instance with no values set. */
	 public TaobaoApply(){}
	 /* @model:ID */
	 public void setId(Long obj){
		 this.id = obj;
	 }

	 /* @model:ID */
	 public Long getId(){
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
	 /* @model:ID */
	 public void setUserId(Long obj){
		 this.userId = obj;
	 }

	 /* @model:ID */
	 public Long getUserId(){
		 return this.userId;
	 }
	 /* @model:QQ */
	 public void setParamOne(String obj){
		 this.paramOne = obj;
	 }

	 /* @model:QQ */
	 public String getParamOne(){
		 return this.paramOne;
	 }
	 /* @model:UID */
	 public void setParamTwo(String obj){
		 this.paramTwo = obj;
	 }

	 /* @model:UID */
	 public String getParamTwo(){
		 return this.paramTwo;
	 }
	 /* @model:URL */
	 public void setShopUrl(String obj){
		 this.shopUrl = obj;
	 }

	 /* @model:URL */
	 public String getShopUrl(){
		 return this.shopUrl;
	 }
	 /* @model:ID */
	 public void setPostId(Long obj){
		 this.postId = obj;
	 }

	 /* @model:ID */
	 public Long getPostId(){
		 return this.postId;
	 }
	 /* @model: */
	 public void setPostNormal(double obj){
		 this.postNormal = obj;
	 }

	 /* @model: */
	 public double getPostNormal(){
		 return this.postNormal;
	 }
	 /* @model:EMS */
	 public void setPostEms(double obj){
		 this.postEms = obj;
	 }

	 /* @model:EMS */
	 public double getPostEms(){
		 return this.postEms;
	 }
	 /* @model: */
	 public void setPostExpress(double obj){
		 this.postExpress = obj;
	 }

	 /* @model: */
	 public double getPostExpress(){
		 return this.postExpress;
	 }
	 /* @model:newpassfail */
	 public void setStatus(String obj){
		 this.status = obj;
	 }

	 /* @model:newpassfail */
	 public String getStatus(){
		 return this.status;
	 }
	 /* @model:paipai:taobao: */
	 public void setType(String obj){
		 this.type = obj;
	 }

	 /* @model:paipai:taobao: */
	 public String getType(){
		 return this.type;
	 }
	 /* @model: */
	 public void setMemo(String obj){
		 this.memo = obj;
	 }

	 /* @model: */
	 public String getMemo(){
		 return this.memo;
	 }
	 /* @model: */
	 public void setGmtSync(Date obj){
		 this.gmtSync = obj;
	 }

	 /* @model: */
	 public Date getGmtSync(){
		 return this.gmtSync;
	 }
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
    public String getParamThree() {
        return paramThree;
    }
    public void setParamThree(String paramThree) {
        this.paramThree = paramThree;
    }

 }
