package com.huaixuan.network.biz.domain.storage;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.huaixuan.network.biz.domain.BaseObject;

/**
 * ï¿bibleUtil auto code generation)
 * @version 3.2.0
 */
public class Depository extends BaseObject implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = -5080778723804804713L;
    /* @property: */
    private long              id;
    /* @property: */
    private String            name;
    /* @property: */
    private String            address;
    /* @property: */
    private String            leader;
    /* @property: */
    private String            tel;
    /* @property: */
    private String            remark;
    /* @property: */
    private Date              gmtCreate;
    /* @property: */
    private Date              gmtModify;

    private String            status;

    private String            type;

    private Long              depFirstId;

    private String            depFirstName;

    private String            locName;

    public String getLocName() {
        return locName;
    }

    public void setLocName(String locName) {
        this.locName = locName;
    }
    /* Default constructor - creates a new instance with no values set. */
    public Depository() {
    }

    /* @model:ï¿*/
    public void setId(long obj) {
        this.id = obj;
    }

    /* @model:ï¿*/
    public long getId() {
        return this.id;
    }

    /* @model:ï¿*/
    public void setName(String obj) {
        this.name = obj;
    }

    /* @model:ï¿*/
    public String getName() {
        return this.name;
    }

    /* @model:ï¿*/
    public void setAddress(String obj) {
        this.address = obj;
    }

    /* @model:ï¿*/
    public String getAddress() {
        return this.address;
    }

    /* @model:ï¿*/
    public void setLeader(String obj) {
        this.leader = obj;
    }

    /* @model:ï¿*/
    public String getLeader() {
        return this.leader;
    }

    /* @model:ï¿*/
    public void setTel(String obj) {
        this.tel = obj;
    }

    /* @model:ï¿*/
    public String getTel() {
        return this.tel;
    }

    /**
     * @return remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * @param remark Òªï¿½ï¿½ï¿½Ãµï¿½ remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /* @model:ï¿*/
    public void setGmtCreate(Date obj) {
        this.gmtCreate = obj;
    }

    /* @model:ï¿*/
    public Date getGmtCreate() {
        return this.gmtCreate;
    }

    /* @model:ï¿*/
    public void setGmtModify(Date obj) {
        this.gmtModify = obj;
    }

    /* @model:ï¿*/
    public Date getGmtModify() {
        return this.gmtModify;
    }

    /**
     * @return status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status Òªï¿½ï¿½ï¿½Ãµï¿½ status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /*{@inheritDoc}*/
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Depository)) {
            return false;
        }
        final Depository depository = (Depository) o;
        return this.hashCode() == depository.hashCode();
    }

    public Long getDepFirstId() {
		return depFirstId;
	}

	public void setDepFirstId(Long depFirstId) {
		this.depFirstId = depFirstId;
	}

	/*{@inheritDoc}*/
    public String toString() {
        ToStringBuilder sb = new ToStringBuilder(this, ToStringStyle.DEFAULT_STYLE).append("id",
            this.id).append("name", this.name).append("address", this.address).append("leader",
            this.leader).append("tel", this.tel).append("remark", this.remark).append("gmtCreate",
            this.gmtCreate).append("gmtModify", this.gmtModify).append("status", this.status)
            .append("type", this.type);
        return sb.toString();
    }

    public String getDepFirstName() {
        return depFirstName;
    }

    public void setDepFirstName(String depFirstName) {
        this.depFirstName = depFirstName;
    }

}
