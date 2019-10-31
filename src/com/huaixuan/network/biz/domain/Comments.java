package com.huaixuan.network.biz.domain;

 import java.io.Serializable;
import java.util.Date;

 /**
  * (bibleUtil auto code generation)
  * @version 3.2.0
  */
 public class Comments extends BaseObject implements Serializable {
 		 /* @property: */
	 private long id;
	 /* @property: */
	 private Long userId;
	 /* @property: */
	 private String userNickname;
	 
	 private int userSex;
	 
	 /* @property: */
	 private String email;
	 /* @property: */
	 private int commentType;
	 /* @property: */
	 private long idValue;
	 //added property: if comment on a good ,this is the name of it.
	 private String goodsName;
	 /* @property: */
	 private int commentRank;
	 /* @property: */
	 private String content;
	 /* @property: */
	 private String status;
	 /* @property: */
	 private String ipAddress;
	 /* @property: */
	 private Date gmtCreate;
	 //added property for search
	 private String gmtCreateStart;
	 //added property for search
	 private String gmtCreateEnd;
	 //added property: set it to "1" if it is replied. set it to "0" if not
	 private String replyFlag;
	 /* @property: */
	 private String replyContent;
	 /* @property: */
	 private Date gmtReply;
	 
     private Long countWithReplyFlag;
     
     public Long getCountWithReplyFlag() {
        return countWithReplyFlag;
     }
     public void setCountWithReplyFlag(Long countWithReplyFlag) {
        this.countWithReplyFlag = countWithReplyFlag;
     }
	 /* Default constructor - creates a new instance with no values set. */
	 public Comments(){}
	 /* @model: */
	 public void setId(long obj){
		 this.id = obj;
	 }

	 /* @model: */
	 public long getId(){
		 return this.id;
	 }
	 /* @model: */
	 public void setUserId(Long obj){
		 this.userId = obj;
	 }

	 /* @model: */
	 public Long getUserId(){
		 return this.userId;
	 }
	 /* @model: */
	 public void setUserNickname(String obj){
		 this.userNickname = obj;
	 }

	 /* @model: */
	 public String getUserNickname(){
		 return this.userNickname;
	 }
	 /* @model: */
	 public void setEmail(String obj){
		 this.email = obj;
	 }

	 /* @model: */
	 public String getEmail(){
		 return this.email;
	 }
	 /* @model: */
	 public void setCommentType(int obj){
		 this.commentType = obj;
	 }

	 /* @model: */
	 public int getCommentType(){
		 return this.commentType;
	 }
	 /* @model: */
	 public void setIdValue(long obj){
		 this.idValue = obj;
	 }

	 /* @model: */
	 public long getIdValue(){
		 return this.idValue;
	 }
	 /* @model: */
	 public void setCommentRank(int obj){
		 this.commentRank = obj;
	 }

	 /* @model: */
	 public int getCommentRank(){
		 return this.commentRank;
	 }
	 /* @model: */
	 public void setContent(String obj){
		 this.content = obj;
	 }

	 /* @model: */
	 public String getContent(){
		 return this.content;
	 }
	 /* @model: */
	 public void setIpAddress(String obj){
		 this.ipAddress = obj;
	 }

	 /* @model: */
	 public String getIpAddress(){
		 return this.ipAddress;
	 }

	 /* @model: */
	 public void setReplyContent(String obj){
		 this.replyContent = obj;
	 }

	 /* @model: */
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
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + commentRank;
		result = prime * result + commentType;
		result = prime * result + ((content == null) ? 0 : content.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result
				+ ((gmtCreate == null) ? 0 : gmtCreate.hashCode());
		result = prime * result
				+ ((gmtCreateEnd == null) ? 0 : gmtCreateEnd.hashCode());
		result = prime * result
				+ ((gmtCreateStart == null) ? 0 : gmtCreateStart.hashCode());
		result = prime * result
				+ ((gmtReply == null) ? 0 : gmtReply.hashCode());
		result = prime * result
				+ ((goodsName == null) ? 0 : goodsName.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + (int) (idValue ^ (idValue >>> 32));
		result = prime * result
				+ ((ipAddress == null) ? 0 : ipAddress.hashCode());
		result = prime * result
				+ ((replyContent == null) ? 0 : replyContent.hashCode());
		result = prime * result
				+ ((replyFlag == null) ? 0 : replyFlag.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + (int) (userId ^ (userId >>> 32));
		result = prime * result
				+ ((userNickname == null) ? 0 : userNickname.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Comments other = (Comments) obj;
		if (commentRank != other.commentRank)
			return false;
		if (commentType != other.commentType)
			return false;
		if (content == null) {
			if (other.content != null)
				return false;
		} else if (!content.equals(other.content))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (gmtCreate == null) {
			if (other.gmtCreate != null)
				return false;
		} else if (!gmtCreate.equals(other.gmtCreate))
			return false;
		if (gmtCreateEnd == null) {
			if (other.gmtCreateEnd != null)
				return false;
		} else if (!gmtCreateEnd.equals(other.gmtCreateEnd))
			return false;
		if (gmtCreateStart == null) {
			if (other.gmtCreateStart != null)
				return false;
		} else if (!gmtCreateStart.equals(other.gmtCreateStart))
			return false;
		if (gmtReply == null) {
			if (other.gmtReply != null)
				return false;
		} else if (!gmtReply.equals(other.gmtReply))
			return false;
		if (goodsName == null) {
			if (other.goodsName != null)
				return false;
		} else if (!goodsName.equals(other.goodsName))
			return false;
		if (id != other.id)
			return false;
		if (idValue != other.idValue)
			return false;
		if (ipAddress == null) {
			if (other.ipAddress != null)
				return false;
		} else if (!ipAddress.equals(other.ipAddress))
			return false;
		if (replyContent == null) {
			if (other.replyContent != null)
				return false;
		} else if (!replyContent.equals(other.replyContent))
			return false;
		if (replyFlag == null) {
			if (other.replyFlag != null)
				return false;
		} else if (!replyFlag.equals(other.replyFlag))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (userId != other.userId)
			return false;
		if (userNickname == null) {
			if (other.userNickname != null)
				return false;
		} else if (!userNickname.equals(other.userNickname))
			return false;
		return true;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getReplyFlag() {
		return replyFlag;
	}
	public void setReplyFlag(String replyFlag) {
		this.replyFlag = replyFlag;
	}
	public int getUserSex() {
		return userSex;
	}
	public void setUserSex(int userSex) {
		this.userSex = userSex;
	}
	public String getGmtCreateStart() {
		return gmtCreateStart;
	}
	public void setGmtCreateStart(String gmtCreateStart) {
		this.gmtCreateStart = gmtCreateStart;
	}
	public String getGmtCreateEnd() {
		return gmtCreateEnd;
	}
	public void setGmtCreateEnd(String gmtCreateEnd) {
		this.gmtCreateEnd = gmtCreateEnd;
	}

 }
