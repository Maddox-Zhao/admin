package com.huaixuan.network.biz.domain.goods;

import java.io.Serializable;
import java.util.Date;

import com.huaixuan.network.biz.domain.BaseObject;

public class SumGoods extends BaseObject implements Serializable{
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private Long id;
    private Long lisingGoodsNumber;
    private Long newLisingGoodsNum;
    private Date gmtCreate;
    private Date gmtModify;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getLisingGoodsNumber() {
        return lisingGoodsNumber;
    }
    public void setLisingGoodsNumber(Long lisingGoodsNumber) {
        this.lisingGoodsNumber = lisingGoodsNumber;
    }
    public Long getNewLisingGoodsNum() {
        return newLisingGoodsNum;
    }
    public void setNewLisingGoodsNum(Long newLisingGoodsNum) {
        this.newLisingGoodsNum = newLisingGoodsNum;
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


}
