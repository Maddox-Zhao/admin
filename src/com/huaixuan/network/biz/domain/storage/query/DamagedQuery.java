package com.huaixuan.network.biz.domain.storage.query;

import java.util.List;

/**
 * (bibleUtil auto code generation)
 *
 * @version 3.2.0
 */
public class DamagedQuery {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	/* @property: */
	private String damagedCode;
	/* @property: */
	private String status;
	/* @property: */
	private String creater;
	/* @property: */
	private String agent;
	private String depFirstId;
	/* @property: */
	private String damagedTimeStart;
	/* @property: */
	private String damagedTimeEnd;

	private List<Long> depFirstIds;

	public List<Long> getDepFirstIds() {
		return depFirstIds;
	}

	public void setDepFirstIds(List<Long> depFirstIds) {
		this.depFirstIds = depFirstIds;
	}

	public String getDepFirstId() {
		return depFirstId;
	}

	public void setDepFirstId(String depFirstId) {
		this.depFirstId = depFirstId;
	}

	public String getDamagedCode() {
		return damagedCode;
	}

	public void setDamagedCode(String damagedCode) {
		this.damagedCode = damagedCode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCreater() {
		return creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	public String getAgent() {
		return agent;
	}

	public void setAgent(String agent) {
		this.agent = agent;
	}

	public String getDamagedTimeStart() {
		return damagedTimeStart;
	}

	public void setDamagedTimeStart(String damagedTimeStart) {
		this.damagedTimeStart = damagedTimeStart;
	}

	public String getDamagedTimeEnd() {
		return damagedTimeEnd;
	}

	public void setDamagedTimeEnd(String damagedTimeEnd) {
		this.damagedTimeEnd = damagedTimeEnd;
	}

}
