package com.huaixuan.network.biz.domain.storage;

import java.io.Serializable;
import java.util.Date;

import com.huaixuan.network.biz.domain.BaseObject;

/**
 *
 * ��
 *
 */
public class DepLocation extends BaseObject implements Serializable {

    private static final long serialVersionUID = -5334648067003593320L;

    private Long              id;

    private Long              depId;

    private String            depName;

    private String            locCode;

    private String            locName;

    private Long              locSize;

    private String            locDescription;

    private String            isCheck;

    private Date              gmtCreate;

    private Date              gmtModify;

    private String            status;


	/**
	 * @return status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status  status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return gmtCreate
	 */
	public Date getGmtCreate() {
		return gmtCreate;
	}

	/**
	 * @param gmtCreate gmtCreate
	 */
	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	/**
	 * @return gmtModify
	 */
	public Date getGmtModify() {
		return gmtModify;
	}

	/**
	 * @param gmtModifygmt Modify
	 */
	public void setGmtModify(Date gmtModify) {
		this.gmtModify = gmtModify;
	}

	/**
	 * @return locCode
	 */
	public String getLocCode() {
		return locCode;
	}

	/**
	 * @param locCode locCode
	 */
	public void setLocCode(String locCode) {
		this.locCode = locCode;
	}

	public String getIsCheck() {
        return isCheck;
    }

    public void setIsCheck(String isCheck) {
        this.isCheck = isCheck;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDepId() {
        return depId;
    }

    public void setDepId(Long depId) {
        this.depId = depId;
    }

    public String getLocName() {
        return locName;
    }

    public void setLocName(String locName) {
        this.locName = locName;
    }

    public Long getLocSize() {
        return locSize;
    }

    public void setLocSize(Long locSize) {
        this.locSize = locSize;
    }

    public String getLocDescription() {
        return locDescription;
    }

    public void setLocDescription(String locDescription) {
        this.locDescription = locDescription;
    }

    public String getDepName() {
        return depName;
    }

    public void setDepName(String depName) {
        this.depName = depName;
    }

}
