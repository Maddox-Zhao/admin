package com.huaixuan.network.biz.domain.storage;

import java.util.Date;

import com.huaixuan.network.biz.domain.BaseObject;

/**
 * �bibleUtil auto code generation)
 * @version 3.2.0
 */
public class InOutStatReport extends BaseObject {

    private static final long serialVersionUID = 8967824255628949460L;
    /* @property: */
    private Long              id;
    /* @property:�*/
    private Date              gmtCreate;
    /* @property:�*/
    private String            creator;
    /* @property:�*/
    private String            gmtReport;
    private Long              goodsId;
    /* @property: */
    private Long              goodsInstanceId;
    /* @property:�*/
    private String            goodsName;
    /* @property:�*/
    private String            goodsCode;
    /* @property:� */
    private Long              lastRemainAmount;
    /* @property:�*/
    private Double            lastRemainMoney;
    /* @property:� */
    private Long              thisInAmount;
    /* @property:�*/
    private Double            thisInMoney;
    /* @property:� */
    private Long              thisOutAmount;
    /* @property:�*/
    private Double            thisOutMoney;
    /* @property:� */
    private Long              thisRemainAmount;
    /* @property:�*/
    private Double            thisRemainMoney;
    /* @property:�D */
    private Long              depFirstId;
    /* @property:�D */
    private Long              depId;
    private Long              locId;

    /* Default constructor - creates a new instance with no values set. */
    public InOutStatReport() {
    }

    /* @model:�*/
    public void setId(Long obj) {
        this.id = obj;
    }

    /* @model:�*/
    public Long getId() {
        return this.id;
    }

    /* @model:�*/
    public void setGmtCreate(Date obj) {
        this.gmtCreate = obj;
    }

    /* @model:�*/
    public Date getGmtCreate() {
        return this.gmtCreate;
    }

    /* @model:�*/
    public void setCreator(String obj) {
        this.creator = obj;
    }

    /* @model:�*/
    public String getCreator() {
        return this.creator;
    }

    /* @model:�*/
    public void setGmtReport(String obj) {
        this.gmtReport = obj;
    }

    /* @model:�*/
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

    /* @model:�*/
    public void setGoodsName(String obj) {
        this.goodsName = obj;
    }

    /* @model:�*/
    public String getGoodsName() {
        return this.goodsName;
    }

    /* @model:�*/
    public void setGoodsCode(String obj) {
        this.goodsCode = obj;
    }

    /* @model:�*/
    public String getGoodsCode() {
        return this.goodsCode;
    }

    /* @model:� */
    public void setLastRemainAmount(Long obj) {
        this.lastRemainAmount = obj;
    }

    /* @model:� */
    public Long getLastRemainAmount() {
        return this.lastRemainAmount;
    }

    /* @model:�*/
    public void setLastRemainMoney(Double obj) {
        this.lastRemainMoney = obj;
    }

    /* @model:�*/
    public Double getLastRemainMoney() {
        return this.lastRemainMoney;
    }

    /* @model:� */
    public void setThisInAmount(Long obj) {
        this.thisInAmount = obj;
    }

    /* @model:� */
    public Long getThisInAmount() {
        return this.thisInAmount;
    }

    /* @model:�*/
    public void setThisInMoney(Double obj) {
        this.thisInMoney = obj;
    }

    /* @model:�*/
    public Double getThisInMoney() {
        return this.thisInMoney;
    }

    /* @model:� */
    public void setThisOutAmount(Long obj) {
        this.thisOutAmount = obj;
    }

    /* @model:� */
    public Long getThisOutAmount() {
        return this.thisOutAmount;
    }

    /* @model:�*/
    public void setThisOutMoney(Double obj) {
        this.thisOutMoney = obj;
    }

    /* @model:�*/
    public Double getThisOutMoney() {
        return this.thisOutMoney;
    }

    /* @model:� */
    public void setThisRemainAmount(Long obj) {
        this.thisRemainAmount = obj;
    }

    /* @model:� */
    public Long getThisRemainAmount() {
        return this.thisRemainAmount;
    }

    /* @model:�*/
    public void setThisRemainMoney(Double obj) {
        this.thisRemainMoney = obj;
    }

    /* @model:�*/
    public Double getThisRemainMoney() {
        return this.thisRemainMoney;
    }

    /* @model:�D */
    public void setDepFirstId(Long obj) {
        this.depFirstId = obj;
    }

    /* @model:�D */
    public Long getDepFirstId() {
        return this.depFirstId;
    }

    /* @model:���D */
    public void setDepId(Long obj) {
        this.depId = obj;
    }

    /* @model:�D */
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
