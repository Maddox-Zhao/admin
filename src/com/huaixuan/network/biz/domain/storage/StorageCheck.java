package com.huaixuan.network.biz.domain.storage;

import java.util.Date;

import com.huaixuan.network.biz.domain.BaseObject;

/**
 * (bibleUtil auto code generation)
 * @version 3.2.0
 */
public class StorageCheck extends BaseObject {
    private static final long serialVersionUID = 1914818794005252655L;
    /* @property: */
    private long              id;
    /* @property: */
    private long              locId;

    private String            locName;
    /* @property: */
    private long              depId;

    private String            depName;
    /* @property: */
    private String            status;
    /* @property: */
    private String            creater;
    /* @property: */
    private String            checkType;
    /* @property: */
    private Date              gmtCreate;
    /* @property: */
    private Date              gmtModify;

    //  一级仓库ID
    private Long              depFirstId;
    //  一级仓库名称
    private String            depFirstName;

    /* Default constructor - creates a new instance with no values set. */
    public StorageCheck() {
    }

    /* @model: */
    public void setId(long obj) {
        this.id = obj;
    }

    /* @model: */
    public long getId() {
        return this.id;
    }

    /* @model: */
    public void setLocId(long obj) {
        this.locId = obj;
    }

    /* @model: */
    public long getLocId() {
        return this.locId;
    }

    /* @model: */
    public void setDepId(long obj) {
        this.depId = obj;
    }

    /* @model: */
    public long getDepId() {
        return this.depId;
    }

    /* @model: */
    public void setStatus(String obj) {
        this.status = obj;
    }

    /* @model: */
    public String getStatus() {
        return this.status;
    }

    /* @model: */
    public void setCreater(String obj) {
        this.creater = obj;
    }

    /* @model: */
    public String getCreater() {
        return this.creater;
    }

    /* @model: */
    public void setCheckType(String obj) {
        this.checkType = obj;
    }

    /* @model: */
    public String getCheckType() {
        return this.checkType;
    }

    /* @model: */
    public void setGmtCreate(Date obj) {
        this.gmtCreate = obj;
    }

    /* @model: */
    public Date getGmtCreate() {
        return this.gmtCreate;
    }

    /* @model: */
    public void setGmtModify(Date obj) {
        this.gmtModify = obj;
    }

    /* @model: */
    public Date getGmtModify() {
        return this.gmtModify;
    }

    public String getDepName() {
        return depName;
    }

    public void setDepName(String depName) {
        this.depName = depName;
    }

    public String getLocName() {
        return locName;
    }

    public void setLocName(String locName) {
        this.locName = locName;
    }

    public Long getDepFirstId() {
        return depFirstId;
    }

    public void setDepFirstId(Long depFirstId) {
        this.depFirstId = depFirstId;
    }

    public String getDepFirstName() {
        return depFirstName;
    }

    public void setDepFirstName(String depFirstName) {
        this.depFirstName = depFirstName;
    }

}
