package com.huaixuan.network.biz.query;


import com.huaixuan.network.biz.domain.BaseObject;

public class PromationQuery extends BaseObject{

	 private String modeCode;
	 
	 private String name;
	 
	 private String startDate;
	 
	 private String endDate;
	 
	 private String startDateEnd;
	 
	 private String endDateEnd;

	public String getModeCode() {
		return modeCode;
	}

	public void setModeCode(String modeCode) {
		this.modeCode = modeCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getStartDateEnd() {
		return startDateEnd;
	}

	public void setStartDateEnd(String startDateEnd) {
		this.startDateEnd = startDateEnd;
	}

	public String getEndDateEnd() {
		return endDateEnd;
	}

	public void setEndDateEnd(String endDateEnd) {
		this.endDateEnd = endDateEnd;
	}

}
