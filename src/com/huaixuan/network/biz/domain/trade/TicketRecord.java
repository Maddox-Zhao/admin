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
 public class TicketRecord extends BaseObject implements Serializable {
 		 /* @property: */
	 private long id;
	 /* @property: */
	 private Date gmtCreate;
	 /* @property: */
	 private Date gmtModify;
	 /* @property: */
	 private long userId;
	 /* @property: */
	 private int ticketAmount;
	 /* @property: */
	 private String type;
	 /* @property: */
	 private String memo;
	 /* Default constructor - creates a new instance with no values set. */
	 public TicketRecord(){}
	 /* @model:设置 */
	 public void setId(long obj){
		 this.id = obj;
	 }

	 /* @model:获取 */
	 public long getId(){
		 return this.id;
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
	 /* @model:设置 */
	 public void setUserId(long obj){
		 this.userId = obj;
	 }

	 /* @model:获取 */
	 public long getUserId(){
		 return this.userId;
	 }

	 /* @model:设置 */
	 public void setType(String obj){
		 this.type = obj;
	 }

	 /* @model:获取 */
	 public String getType(){
		 return this.type;
	 }
	 /* @model:设置 */
	 public void setMemo(String obj){
		 this.memo = obj;
	 }

	 /* @model:获取 */
	 public String getMemo(){
		 return this.memo;
	 }
	/*{@inheritDoc}*/
	 public boolean equals(Object o) {
		 if (this == o) {
			 return true;
		 }
		 if (!(o instanceof TicketRecord)) {
			 return false;
		 }
		 final TicketRecord ticketrecord = (TicketRecord) o;
		 return this.hashCode() == ticketrecord.hashCode();
	 }
	/*{@inheritDoc}*/
	 public int hashCode() {
		 return this.hashCode();
	 }
	/*{@inheritDoc}*/
	 public String toString() {
		 ToStringBuilder sb = new ToStringBuilder(this, ToStringStyle.DEFAULT_STYLE)
			 .append("id", this.id)
			 .append("gmtCreate", this.gmtCreate)
			 .append("gmtModify", this.gmtModify)
			 .append("userId", this.userId)
			 .append("ticketAmount", this.ticketAmount)
			 .append("type", this.type)
			 .append("memo", this.memo);
		 return sb.toString();
	 }
	public int getTicketAmount() {
		return ticketAmount;
	}
	public void setTicketAmount(int ticketAmount) {
		this.ticketAmount = ticketAmount;
	}


 }
