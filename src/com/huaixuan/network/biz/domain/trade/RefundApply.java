package com.huaixuan.network.biz.domain.trade;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.huaixuan.network.biz.domain.BaseObject;
import com.hundsun.network.melody.common.util.StringUtil;

/**  
 * (bibleUtil auto code generation) 
 * @version 3.2.0  
 */
public class RefundApply extends BaseObject implements Serializable {
    /**
     * 
     */
    private static final long      serialVersionUID = 6397969953156951644L;
    /* @property: */
    private Long                   id;
    /* @property: */
    private String                 tid;
    /* @property:id */
    private Long                   buyId;
    /* @property: */
    private String                 buyNick;
    /* @property: */
    private Date                   gmtCreate;
    /* @property: */
    private Date                   gmtModify;
    /* @property: */
    private String                 note;
    /* @property: */
    private String                 creater;
    private Long                   createrId;
    /* @property: */
    private String                 serviceNote;
    /* @property:ID */
    private Long                   depFirstId;
    /* @property: */
    private Date                   gmtApply;
    /* @property: */
    private String                 type;
    /* @property: */
    private String                 status;
    /* @property: */
    private String                 applyPics;
    private List<RefundApplyOrder> refundApplyOrder;

    /* Default constructor - creates a new instance with no values set. */
    public RefundApply() {
    }

    /* @model: */
    public void setId(Long obj) {
        this.id = obj;
    }

    /* @model: */
    public Long getId() {
        return this.id;
    }

    /* @model: */
    public void setTid(String obj) {
        this.tid = obj;
    }

    /* @model: */
    public String getTid() {
        return this.tid;
    }

    /* @model:id */
    public void setBuyId(Long obj) {
        this.buyId = obj;
    }

    /* @model:id */
    public Long getBuyId() {
        return this.buyId;
    }

    /* @model: */
    public void setBuyNick(String obj) {
        this.buyNick = obj;
    }

    /* @model: */
    public String getBuyNick() {
        return this.buyNick;
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
    public void setNote(String obj) {
        this.note = obj;
    }

    /* @model: */
    public String getNote() {
        return this.note;
    }

    /* @model:¨° */
    public void setCreater(String obj) {
        this.creater = obj;
    }

    /* @model: */
    public String getCreater() {
        return this.creater;
    }

    /* @model: */
    public void setServiceNote(String obj) {
        this.serviceNote = obj;
    }

    /* @model: */
    public String getServiceNote() {
        return this.serviceNote;
    }

    /* @model:ID */
    public void setDepFirstId(Long obj) {
        this.depFirstId = obj;
    }

    /* @model:ID */
    public Long getDepFirstId() {
        return this.depFirstId;
    }

    /* @model: */
    public void setGmtApply(Date obj) {
        this.gmtApply = obj;
    }

    /* @model: */
    public Date getGmtApply() {
        return this.gmtApply;
    }

    /* @model: */
    public void setType(String obj) {
        this.type = obj;
    }

    /* @model: */
    public String getType() {
        return this.type;
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
    public void setApplyPics(String obj) {
        this.applyPics = obj;
    }

    /* @model: */
    public String getApplyPics() {
        return this.applyPics;
    }

    public List<RefundApplyOrder> getRefundApplyOrder() {
        return refundApplyOrder;
    }

    public void setRefundApplyOrder(List<RefundApplyOrder> refundApplyOrder) {
        this.refundApplyOrder = refundApplyOrder;
    }

    public Long getCreaterId() {
        return createrId;
    }

    public void setCreaterId(Long createrId) {
        this.createrId = createrId;
    }

    public String[] getApplyPicsForPage() {
        if (StringUtil.isNotBlank(this.applyPics)) {
            String[] applypics = this.applyPics.split("\\|");
            return applypics;
        }
        return null;

    }
}
