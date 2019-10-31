package com.huaixuan.network.biz.domain.trade;

import java.io.Serializable;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import java.util.Date;

public class WholesaleApply implements Serializable {

    private Long   id;

    private Date   gmtCreate;

    private Date   gmtModify;

    private String tid;

    private Long   depFirstId;

    private String depFirstName;

    private String status;

    /* Default constructor - creates a new instance with no values set. */
    public WholesaleApply() {
    }

    public void setId(Long obj) {
        this.id = obj;
    }

    public Long getId() {
        return this.id;
    }

    public void setGmtCreate(Date obj) {
        this.gmtCreate = obj;
    }

    public Date getGmtCreate() {
        return this.gmtCreate;
    }

    public void setGmtModify(Date obj) {
        this.gmtModify = obj;
    }

    public Date getGmtModify() {
        return this.gmtModify;
    }

    public void setTid(String obj) {
        this.tid = obj;
    }

    public String getTid() {
        return this.tid;
    }

    public void setDepFirstId(Long obj) {
        this.depFirstId = obj;
    }

    public Long getDepFirstId() {
        return this.depFirstId;
    }

    public String getDepFirstName() {
        return depFirstName;
    }

    public void setDepFirstName(String depFirstName) {
        this.depFirstName = depFirstName;
    }

    public void setStatus(String obj) {
        this.status = obj;
    }

    public String getStatus() {
        return this.status;
    }

    /*{@inheritDoc}*/
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof WholesaleApply)) {
            return false;
        }
        final WholesaleApply wholesaleapply = (WholesaleApply) o;
        return this.hashCode() == wholesaleapply.hashCode();
    }

    /*{@inheritDoc}*/
    public int hashCode() {
        return this.hashCode();
    }

    /*{@inheritDoc}*/
    public String toString() {
        ToStringBuilder sb = new ToStringBuilder(this, ToStringStyle.DEFAULT_STYLE).append("id",
            this.id).append("gmtCreate", this.gmtCreate).append("gmtModify", this.gmtModify)
            .append("tid", this.tid).append("depFirstId", this.depFirstId).append("status",
                this.status);
        return sb.toString();
    }

}
