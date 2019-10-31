package com.huaixuan.network.biz.domain.hx;

import java.util.Date;

import com.huaixuan.network.biz.domain.BaseObject;

/**
 * @version 3.2.0
 */

public class Insertcontent extends BaseObject {

	private static final long serialVersionUID = 3577201145259820481L;

	/* @property: */
	private Long insertId;
	
	 private Long leavewordId;
	 /* @property: */
	 private String content;
	 /* @property: */
	 private Date leaveTime;
	 
	 private Long idCustomer;

	public Long getInsertId() {
		return insertId;
	}

	public void setInsertId(Long insertId) {
		this.insertId = insertId;
	}

	public Long getLeavewordId() {
		return leavewordId;
	}

	public void setLeavewordId(Long leavewordId) {
		this.leavewordId = leavewordId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getLeaveTime() {
		return leaveTime;
	}

	public void setLeaveTime(Date leaveTime) {
		this.leaveTime = leaveTime;
	}

	public Long getIdCustomer() {
		return idCustomer;
	}

	public void setIdCustomer(Long idCustomer) {
		this.idCustomer = idCustomer;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((content == null) ? 0 : content.hashCode());
		result = prime * result
				+ ((idCustomer == null) ? 0 : idCustomer.hashCode());
		result = prime * result
				+ ((insertId == null) ? 0 : insertId.hashCode());
		result = prime * result
				+ ((leaveTime == null) ? 0 : leaveTime.hashCode());
		result = prime * result
				+ ((leavewordId == null) ? 0 : leavewordId.hashCode());
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
		Insertcontent other = (Insertcontent) obj;
		if (content == null) {
			if (other.content != null)
				return false;
		} else if (!content.equals(other.content))
			return false;
		if (idCustomer == null) {
			if (other.idCustomer != null)
				return false;
		} else if (!idCustomer.equals(other.idCustomer))
			return false;
		if (insertId == null) {
			if (other.insertId != null)
				return false;
		} else if (!insertId.equals(other.insertId))
			return false;
		if (leaveTime == null) {
			if (other.leaveTime != null)
				return false;
		} else if (!leaveTime.equals(other.leaveTime))
			return false;
		if (leavewordId == null) {
			if (other.leavewordId != null)
				return false;
		} else if (!leavewordId.equals(other.leavewordId))
			return false;
		return true;
	}
	 
}


