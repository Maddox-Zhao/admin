/**
 *
 */
package com.huaixuan.network.biz.domain;

import java.io.Serializable;
import java.util.Date;

import com.huaixuan.network.biz.enums.EnumTransAppStatus;

/**
 * @author shlin@hundsun.com
 *
 */
public class TransLogApp extends BaseObject implements Serializable {

	private static final long serialVersionUID = 8381134786070524013L;

	private long id;
	private long transLogId;
	private String transMemo;
	private String status;
	private Date gmtCreate;
	private Date gmtModify;

    public String getStatusName(){
    	return (String)(EnumTransAppStatus.toMap().get(this.status));
    }

	public String getTransMemo() {
		return transMemo;
	}

	public void setTransMemo(String transMemo) {
		this.transMemo = transMemo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getTransLogId() {
		return transLogId;
	}

	public void setTransLogId(long transLogId) {
		this.transLogId = transLogId;
	}

}
