package com.huaixuan.network.biz.domain.storage;

import java.util.Date;

import com.huaixuan.network.biz.domain.BaseObject;

/**
 * ï¿bibleUtil auto code generation)
 * @version 3.2.0
 */
public class InOutStatReport extends BaseObject {

    private static final long serialVersionUID = 8967824255628949460L;
    /* @property: */
    private Long              id;
    /* @property:ï¿*/
    private Date              gmtCreate;
    /* @property:ï¿*/
    private String            creator;
    /* @property:ï¿*/
    private String            gmtReport;
    private Long              goodsId;
    /* @property: */
    private Long              goodsInstanceId;
    /* @property:ï¿*/
    private String            goodsName;
    /* @property:ï¿*/
    private String            goodsCode;
    /* @property:ï¿ */
    private Long              lastRemainAmount;
    /* @property:ï¿*/
    private Double            lastRemainMoney;
    /* @property:ï¿ */
    private Long              thisInAmount;
    /* @property:ï¿*/
    private Double            thisInMoney;
    /* @property:ï¿ */
    private Long              thisOutAmount;
    /* @property:ï¿*/
    private Double            thisOutMoney;
    /* @property:ï¿ */
    private Long              thisRemainAmount;
    /* @property:ï¿*/
    private Double            thisRemainMoney;
    /* @property:ï¿D */
    private Long              depFirstId;
    /* @property:ï¿D */
    private Long              depId;
    private Long              locId;

    /* Default constructor - creates a new instance with no values set. */
    public InOutStatReport() {
    }

    /* @model:ï¿*/
    public void setId(Long obj) {
        this.id = obj;
    }

    /* @model:ï¿*/
    public Long getId() {
        return this.id;
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
    public void setCreator(String obj) {
        this.creator = obj;
    }

    /* @model:ï¿*/
    public String getCreator() {
        return this.creator;
    }

    /* @model:ï¿*/
    public void setGmtReport(String obj) {
        this.gmtReport = obj;
    }

    /* @model:ï¿*/
    public String getGmtReport() {
        return this.gmtReport;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public Long getGoodsInstanceId() {
        return goodsInstanceId;
    }

    public void setGoodsInstanceId(Long goodsInstanceId) {
        this.goodsInstanceId = goodsInstanceId;
    }

    /* @model:ï¿*/
    public void setGoodsName(String obj) {
        this.goodsName = obj;
    }

    /* @model:ï¿*/
    public String getGoodsName() {
        return this.goodsName;
    }

    /* @model:ï¿*/
    public void setGoodsCode(String obj) {
        this.goodsCode = obj;
    }

    /* @model:ï¿*/
    public String getGoodsCode() {
        return this.goodsCode;
    }

    /* @model:ï¿ */
    public void setLastRemainAmount(Long obj) {
        this.lastRemainAmount = obj;
    }

    /* @model:ï¿ */
    public Long getLastRemainAmount() {
        return this.lastRemainAmount;
    }

    /* @model:ï¿*/
    public void setLastRemainMoney(Double obj) {
        this.lastRemainMoney = obj;
    }

    /* @model:ï¿*/
    public Double getLastRemainMoney() {
        return this.lastRemainMoney;
    }

    /* @model:ï¿ */
    public void setThisInAmount(Long obj) {
        this.thisInAmount = obj;
    }

    /* @model:ï¿ */
    public Long getThisInAmount() {
        return this.thisInAmount;
    }

    /* @model:ï¿*/
    public void setThisInMoney(Double obj) {
        this.thisInMoney = obj;
    }

    /* @model:ï¿*/
    public Double getThisInMoney() {
        return this.thisInMoney;
    }

    /* @model:ï¿ */
    public void setThisOutAmount(Long obj) {
        this.thisOutAmount = obj;
    }

    /* @model:ï¿ */
    public Long getThisOutAmount() {
        return this.thisOutAmount;
    }

    /* @model:ï¿*/
    public void setThisOutMoney(Double obj) {
        this.thisOutMoney = obj;
    }

    /* @model:ï¿*/
    public Double getThisOutMoney() {
        return this.thisOutMoney;
    }

    /* @model:ï¿ */
    public void setThisRemainAmount(Long obj) {
        this.thisRemainAmount = obj;
    }

    /* @model:ï¿ */
    public Long getThisRemainAmount() {
        return this.thisRemainAmount;
    }

    /* @model:ï¿*/
    public void setThisRemainMoney(Double obj) {
        this.thisRemainMoney = obj;
    }

    /* @model:ï¿*/
    public Double getThisRemainMoney() {
        return this.thisRemainMoney;
    }

    /* @model:ï¿D */
    public void setDepFirstId(Long obj) {
        this.depFirstId = obj;
    }

    /* @model:ï¿D */
    public Long getDepFirstId() {
        return this.depFirstId;
    }

    /* @model:ï¿½ï¿½ï¿D */
    public void setDepId(Long obj) {
        this.depId = obj;
    }

    /* @model:ï¿D */
    public Long getDepId() {
        return this.depId;
    }

    public Long getLocId() {
        return locId;
    }

    public void setLocId(Long locId) {
        this.locId = locId;
    }

}
