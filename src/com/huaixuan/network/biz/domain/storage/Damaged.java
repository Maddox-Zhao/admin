package com.huaixuan.network.biz.domain.storage;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.huaixuan.network.biz.domain.BaseObject;

/**
 * (bibleUtil auto code generation)
 * @version 3.2.0
 */
public class Damaged extends BaseObject {

    protected static Log      log              = LogFactory.getLog(Damaged.class);
    /**
     *
     */
    private static final long serialVersionUID = -1442047489305268704L;
    /* @property: */
    private long              id;
    /* @property: */
    private String            damagedCode;
    /* @property: */
    private String            status;
    /* @property: */
    private String            creater;
    /* @property: */
    private String            agent;
    /* @property: */
    private Date              gmtCreate;
    /* @property: */
    private Date              gmtModify;
    /* @property: */
    private String            remark;
    /* @property: */
    private long              depId;
    // 仓库名称
    private String            depName;
    /* @property: 报残日期*/
    private Date              gmtDamaged;

    private String            gmtDamaged_str;

    private Long              depfirstId;

    /* Default constructor - creates a new instance with no values set. */
    public Damaged() {
    }

    /**
     * @return depName
     */
    public String getDepName() {
        return depName;
    }

    /**
     * @param depName 要设置的 depName
     */
    public void setDepName(String depName) {
        this.depName = depName;
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
    public void setDamagedCode(String obj) {
        this.damagedCode = obj;
    }

    /* @model: */
    public String getDamagedCode() {
        return this.damagedCode;
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
    public void setAgent(String obj) {
        this.agent = obj;
    }

    /* @model: */
    public String getAgent() {
        return this.agent;
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

    /* @model: */
    public void setRemark(String obj) {
        this.remark = obj;
    }

    /* @model: */
    public String getRemark() {
        return this.remark;
    }

    /**
     * @return depId
     */
    public long getDepId() {
        return depId;
    }

    /**
     * @param depId 要设置的 depId
     */
    public void setDepId(long depId) {
        this.depId = depId;
    }

    /**
     * @return gmtDamaged
     */
    public Date getGmtDamaged() {
        return gmtDamaged;
    }

    /**
     * @param gmtDamaged 要设置的 gmtDamaged
     */
    public void setGmtDamaged(Date gmtDamaged) {
        this.gmtDamaged = gmtDamaged;
    }

    /*{@inheritDoc}*/
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Damaged)) {
            return false;
        }
        final Damaged damaged = (Damaged) o;
        return this.hashCode() == damaged.hashCode();
    }

    /*{@inheritDoc}*/
    public int hashCode() {
        return this.hashCode();
    }

    /*{@inheritDoc}*/
    public String toString() {
        ToStringBuilder sb = new ToStringBuilder(this, ToStringStyle.DEFAULT_STYLE).append("id",
            this.id).append("damagedCode", this.damagedCode).append("status", this.status).append(
            "creater", this.creater).append("agent", this.agent)
            .append("gmtCreate", this.gmtCreate).append("gmtModify", this.gmtModify).append(
                "remark", this.remark).append("depId", this.depId).append("gmtDamaged",
                this.gmtDamaged);
        return sb.toString();
    }

    public String getGmtDamaged_str() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//定义格式，不显示毫秒
        if (this.getGmtDamaged() != null) {
            return df.format(this.getGmtDamaged());
        } else {
            return null;
        }
    }

    public void setGmtDamaged_str(String gmtDamaged_str) {
        this.gmtDamaged_str = gmtDamaged_str;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//定义格式，不显示毫秒
        Date ts = null;
        if (StringUtils.isNotBlank(gmtDamaged_str)) {
            try {
                ts = df.parse(gmtDamaged_str);
            } catch (ParseException e) {
                log.error(e.getMessage());
            }
        }
        this.gmtDamaged = ts;
    }

    public Long getDepfirstId() {
        return depfirstId;
    }

    public void setDepfirstId(Long depfirstId) {
        this.depfirstId = depfirstId;
    }

}
