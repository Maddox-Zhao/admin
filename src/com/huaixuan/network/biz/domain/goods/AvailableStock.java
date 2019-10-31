package com.huaixuan.network.biz.domain.goods;

import java.util.Date;

public class AvailableStock {
    private Long   id;

    private Long   goodsId;        //��ƷID

    private Long   goodsInstanceId; //��ƷID

    private Long   depFirstId;     //һ���ֿ�ID

    private String depFirstName;   //һ���ֿ�����

    private Long   goodsNumber;    //��Ʒ����

    private Date   gmtCreate;

    private Date   gmtModify;
    
    private String regionCode;
    
    private Long   siteId;
    
    private Long   goodsVirtualNumber;
    
    private Long   goodsSaleNumber;
    
    private String siteName;

    public String getRegionCode() {
		return regionCode;
	}

	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getDepFirstId() {
        return depFirstId;
    }

    public void setDepFirstId(Long depFirstId) {
        this.depFirstId = depFirstId;
    }

    public Long getGoodsNumber() {
        return goodsNumber;
    }

    public void setGoodsNumber(Long goodsNumber) {
        this.goodsNumber = goodsNumber;
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

    public String getDepFirstName() {
        return depFirstName;
    }

    public void setDepFirstName(String depFirstName) {
        this.depFirstName = depFirstName;
    }

	public Long getSiteId() {
		return siteId;
	}

	public void setSiteId(Long siteId) {
		this.siteId = siteId;
	}

	public Long getGoodsVirtualNumber() {
		return goodsVirtualNumber;
	}

	public void setGoodsVirtualNumber(Long goodsVirtualNumber) {
		this.goodsVirtualNumber = goodsVirtualNumber;
	}

	public Long getGoodsSaleNumber() {
		return goodsSaleNumber;
	}

	public void setGoodsSaleNumber(Long goodsSaleNumber) {
		this.goodsSaleNumber = goodsSaleNumber;
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}
    
    
}
