package com.huaixuan.network.biz.domain;

 import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

 /**
  * ��������������������(bibleUtil auto code generation)
  * @version 3.2.0
  */
 public class Feedback extends BaseObject implements Serializable {
 		 /* @property: */
	 private long id;
	 /* @property: */
	 private Long userId;
	 /* @property: */
	 private String userNickname;
	 /* @property: */
	 private String userEmail;
	 /* @property: */
	 private String toUserType;
	 /* @property: */
	 private long toUserId;
	 /* @property: */
	 private String toUserNick;
	 /* @property: */
	 private String msgTitle;
	 /* @property:���Ե����ͣ�
		leaveWord�����ԣ�
		complaint��Ͷ�ߣ�
		ask��ѯ�ʣ�
		afterService���ۺ�
		wantBuy����*/
	 private String msgType;
	 /* @property: */
	 private String msgContent;
	 //added property
     private Date gmtCreateStart;
     //added property
     private Date gmtCreateEnd;
	 /* @property: */
	 private Date gmtCreate;
	 /* @property: */
	 private String replyContent;
	 /* @property: */
	 private Date gmtReply;
	 //added property
	 private String replyFlag;

	 private String startTime;

	 private String endTime;

	 private Long countWithReplyFlag;
     
     public Long getCountWithReplyFlag() {
        return countWithReplyFlag;
     }
     public void setCountWithReplyFlag(Long countWithReplyFlag) {
        this.countWithReplyFlag = countWithReplyFlag;
     }

	public String getStartTime() {
		return startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setStartTime(String startTime) {

		this.startTime = startTime;

//		try {
//			if(this.startTime.length()>0)
//			{
//				SimpleDateFormat sf = new SimpleDateFormat("MM-dd-yyyy");
//				this.gmtCreateStart = sf.parse(startTime);
//			}
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}


	}
	public void setEndTime(String endTime) {

		this.endTime = endTime;


//		try {
//			if(this.endTime.length()>0){
//			SimpleDateFormat sf = new SimpleDateFormat("MM-dd-yyyy");
//
//			this.gmtCreateEnd = sf.parse(endTime);
//			}
//
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
	}
	public Date getGmtCreateStart() {
        return gmtCreateStart;
    }
    public void setGmtCreateStart(Date gmtCreateStart) {
        this.gmtCreateStart = gmtCreateStart;
    }
    public Date getGmtCreateEnd() {
        return gmtCreateEnd;
    }
    public void setGmtCreateEnd(Date gmtCreateEnd) {
        this.gmtCreateEnd = gmtCreateEnd;
    }
    public String getReplyFlag() {
		return replyFlag;
	}
	public void setReplyFlag(String replyFlag) {
		this.replyFlag = replyFlag;
	}
	/* Default constructor - creates a new instance with no values set. */
	 public Feedback(){}
	 /* @model:�������� */
	 public void setId(long obj){
		 this.id = obj;
	 }

	 /* @model:������ */
	 public long getId(){
		 return this.id;
	 }
	 /* @model:�������� */
	 public void setUserId(Long obj){
		 this.userId = obj;
	 }

	 /* @model:������ */
	 public Long getUserId(){
		 return this.userId;
	 }
	 /* @model:�������� */
	 public void setUserNickname(String obj){
		 this.userNickname = obj;
	 }

	 /* @model:������ */
	 public String getUserNickname(){
		 return this.userNickname;
	 }
	 /* @model:�������� */
	 public void setUserEmail(String obj){
		 this.userEmail = obj;
	 }

	 /* @model:������ */
	 public String getUserEmail(){
		 return this.userEmail;
	 }
	 /* @model:�������� */
	 public void setToUserType(String obj){
		 this.toUserType = obj;
	 }

	 /* @model:������ */
	 public String getToUserType(){
		 return this.toUserType;
	 }
	 /* @model:�������� */
	 public void setToUserId(long obj){
		 this.toUserId = obj;
	 }

	 /* @model:������ */
	 public long getToUserId(){
		 return this.toUserId;
	 }
	 /* @model:�������� */
	 public void setMsgTitle(String obj){
		 this.msgTitle = obj;
	 }

	 /* @model:������ */
	 public String getMsgTitle(){
		 return this.msgTitle;
	 }
	 /* @model:�������� */
	 public void setMsgType(String obj){
		 this.msgType = obj;
	 }

	 /* @model:������ */
	 public String getMsgType(){
		 return this.msgType;
	 }
	 /* @model:�������� */
	 public void setMsgContent(String obj){
		 this.msgContent = obj;
	 }

	 /* @model:������ */
	 public String getMsgContent(){
		 return this.msgContent;
	 }

	 /* @model:�������� */
	 public void setReplyContent(String obj){
		 this.replyContent = obj;
	 }

	 /* @model:������ */
	 public String getReplyContent(){
		 return this.replyContent;
	 }

	public Date getGmtCreate() {
		return gmtCreate;
	}
	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}
	public Date getGmtReply() {
		return gmtReply;
	}
	public void setGmtReply(Date gmtReply) {
		this.gmtReply = gmtReply;
	}
	/*{@inheritDoc}*/
	 public boolean equals(Object o) {
		 if (this == o) {
			 return true;
		 }
		 if (!(o instanceof Feedback)) {
			 return false;
		 }
		 final Feedback feedback = (Feedback) o;
		 return this.hashCode() == feedback.hashCode();
	 }
	/*{@inheritDoc}*/
	 public int hashCode() {
		 int result=1;
		 result=result+(int)this.id*31;
//		 result=result+(int)this.userId*31;
		 return result;
	 }
	/*{@inheritDoc}*/
	 public String toString() {
		 ToStringBuilder sb = new ToStringBuilder(this, ToStringStyle.DEFAULT_STYLE)
			 .append("id", this.id)
			 .append("userId", this.userId)
			 .append("userNickname", this.userNickname)
			 .append("userEmail", this.userEmail)
			 .append("toUserType", this.toUserType)
			 .append("toUserId", this.toUserId)
			 .append("msgTitle", this.msgTitle)
			 .append("msgType", this.msgType)
			 .append("msgContent", this.msgContent)
			 .append("gmtCreate", this.gmtCreate)
			 .append("replyContent", this.replyContent)
			 .append("gmtReply", this.gmtReply);
		 return sb.toString();
	 }
	public String getToUserNick() {
		return toUserNick;
	}
	public void setToUserNick(String toUserNick) {
		this.toUserNick = toUserNick;
	}

 }
