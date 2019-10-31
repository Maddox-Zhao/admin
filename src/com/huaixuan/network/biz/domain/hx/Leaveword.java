package com.huaixuan.network.biz.domain.hx;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.huaixuan.network.biz.domain.BaseObject;

/**
 * @version 3.2.0
 */

public class Leaveword extends BaseObject {

	private static final long serialVersionUID = 7035075581533031700L;
	/* @property: */
	 private Long id;
	 /* @property: */
	 private Long idCustomer;
	 
	 private String name;
	 
	 private String phone;
	 /* @property: */
	 private String content;
	 /* @property: */
	 private Date leaveTime;
	 
	 private int isBroad;
	 
	 private String title;
	 
	 private String startTime;

	 private String endTime;
	 
	 private List<Insertcontent> insertcontentList;

	public Long getIdCustomer() {
		return idCustomer;
	}

	public void setIdCustomer(Long idCustomer) {
		this.idCustomer = idCustomer;
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

	public int getIsBroad() {
		return isBroad;
	}

	public void setIsBroad(int isBroad) {
		this.isBroad = isBroad;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<Insertcontent> getInsertcontentList() {
		return insertcontentList;
	}

	public void setInsertcontentList(List<Insertcontent> insertcontentList) {
		this.insertcontentList = insertcontentList;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((content == null) ? 0 : content.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((idCustomer == null) ? 0 : idCustomer.hashCode());
		result = prime
				* result
				+ ((insertcontentList == null) ? 0 : insertcontentList
						.hashCode());
		result = prime * result + isBroad;
		result = prime * result
				+ ((leaveTime == null) ? 0 : leaveTime.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
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
		Leaveword other = (Leaveword) obj;
		if (content == null) {
			if (other.content != null)
				return false;
		} else if (!content.equals(other.content))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (idCustomer == null) {
			if (other.idCustomer != null)
				return false;
		} else if (!idCustomer.equals(other.idCustomer))
			return false;
		if (insertcontentList == null) {
			if (other.insertcontentList != null)
				return false;
		} else if (!insertcontentList.equals(other.insertcontentList))
			return false;
		if (isBroad != other.isBroad)
			return false;
		if (leaveTime == null) {
			if (other.leaveTime != null)
				return false;
		} else if (!leaveTime.equals(other.leaveTime))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

}


