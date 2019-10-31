package com.huaixuan.network.biz.domain.storage.query;

import com.huaixuan.network.biz.domain.BaseObject;

/**
 * ø‚¥ÊÕÀªı…Í«Î≤È—Ø¿‡
 * @version 3.2.0
 */
public class StorageRefundApplyQuery extends BaseObject {
    /**
	 *
	 */
	private static final long serialVersionUID = -1546602608530274027L;

    private String             relationNum;
    private String             applyTimeStart;
    private String             applyTimeEnd;
    private String             applyUserName;
    private String             disposeUserName;
    private String             status;
    //“ªº∂≤÷ø‚ID
    private String             depfirstId;
    //≤÷ø‚ID
    private String             depId;
    //ø‚ŒªID
    private String             locId;


	public String getRelationNum() {
		return relationNum;
	}
	public void setRelationNum(String relationNum) {
		this.relationNum = relationNum;
	}
	public String getApplyTimeStart() {
		return applyTimeStart;
	}
	public void setApplyTimeStart(String applyTimeStart) {
		this.applyTimeStart = applyTimeStart;
	}
	public String getApplyTimeEnd() {
		return applyTimeEnd;
	}
	public void setApplyTimeEnd(String applyTimeEnd) {
		this.applyTimeEnd = applyTimeEnd;
	}
	public String getApplyUserName() {
		return applyUserName;
	}
	public void setApplyUserName(String applyUserName) {
		this.applyUserName = applyUserName;
	}
	public String getDisposeUserName() {
		return disposeUserName;
	}
	public void setDisposeUserName(String disposeUserName) {
		this.disposeUserName = disposeUserName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDepfirstId() {
		return depfirstId;
	}
	public void setDepfirstId(String depfirstId) {
		this.depfirstId = depfirstId;
	}
	public String getDepId() {
		return depId;
	}
	public void setDepId(String depId) {
		this.depId = depId;
	}
	public String getLocId() {
		return locId;
	}
	public void setLocId(String locId) {
		this.locId = locId;
	}

}
