package com.huaixuan.network.biz.domain.shop;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.huaixuan.network.biz.domain.BaseObject;

/**
 *
 * @version 3.2.0
 */
public class Cabinet extends BaseObject implements Serializable {
    /* @property: */
    private long            id;
    /* @property: */
    private String          cabinetName;
    /* @property: */
    private String          cabinetUrl;
    /* @property: */
    private String          cabinetPic;
    /* @property: */
    private String          cabinetKeyword;
    /* @property: */
    private int             isShow;
    /* @property: */
    private String          isShowstr;
    /* @property: */
    private String          isadd;
    /* @property: */
    private int             sort;
    /* @property: */
    private String          sortstr;
    /* @property: */
    private int             goodsNum;
    /* @property: */
    private Date            gmtCreate;
    /* @property: */
    private Date            gmtModify;
    /* @property: */
    private List<Showcase>  showcases;

    public static final int isShow_show   = 1;

    public static final int isShow_noshow = 0;

    /* @property: */
    private long            shopId;

    private int             goodsAmount;

    /** ³÷´°ÀàÐÍ */
    private int             cabinetType;

    public int getCabinetType() {
        return cabinetType;
    }

    public void setCabinetType(int cabinetType) {
        this.cabinetType = cabinetType;
    }

    public int getGoodsAmount() {
        return goodsAmount;
    }

    public void setGoodsAmount(int goodsAmount) {
        this.goodsAmount = goodsAmount;
    }

    public long getShopId() {
        return shopId;
    }

    public void setShopId(long shopId) {
        this.shopId = shopId;
    }

    /* Default constructor - creates a new instance with no values set. */
    public Cabinet() {
    }

    public void setId(long obj) {
        this.id = obj;
    }

    public long getId() {
        return this.id;
    }

    public void setCabinetName(String obj) {
        this.cabinetName = obj;
    }

    public String getCabinetName() {
        return this.cabinetName;
    }

    public void setCabinetUrl(String obj) {
        this.cabinetUrl = obj;
    }

    public String getCabinetUrl() {
        return this.cabinetUrl;
    }

    public void setCabinetPic(String obj) {
        this.cabinetPic = obj;
    }

    public String getCabinetPic() {
        return this.cabinetPic;
    }

    public void setIsShow(int obj) {
        this.isShow = obj;
    }

    public int getIsShow() {
        return this.isShow;
    }

    public void setSort(int obj) {
        this.sort = obj;
    }

    public int getSort() {
        return this.sort;
    }

    public void setGoodsNum(int obj) {
        this.goodsNum = obj;
    }

    public int getGoodsNum() {
        return this.goodsNum;
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

    /*{@inheritDoc}*/
    public boolean equals(Object obj) {
    	if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Cabinet other = (Cabinet) obj;
		if (cabinetName == null) {
			if (other.cabinetName != null)
				return false;
		} else if (!cabinetName.equals(other.cabinetName))
			return false;
		if (cabinetUrl == null) {
			if (other.cabinetUrl != null)
				return false;
		} else if (!cabinetUrl.equals(other.cabinetUrl))
			return false;
		if (cabinetUrl == null) {
			if (other.cabinetUrl != null)
				return false;
		} else if (!cabinetUrl.equals(other.cabinetUrl))
			return false;
		if (gmtCreate == null) {
			if (other.gmtCreate != null)
				return false;
		} else if (!gmtCreate.equals(other.gmtCreate))
			return false;
		if (gmtModify == null) {
			if (other.gmtModify != null)
				return false;
		} else if (!gmtModify.equals(other.gmtModify))
			return false;
		if (cabinetPic == null) {
			if (other.cabinetPic != null)
				return false;
		} else if (!cabinetPic.equals(other.cabinetPic))
			return false;
		if (isShowstr == null) {
			if (other.cabinetPic != null)
				return false;
		} else if (!isShowstr.equals(other.isShowstr))
			return false;
		if (isadd == null) {
			if (other.cabinetPic != null)
				return false;
		} else if (!isadd.equals(other.isadd))
			return false;
		if (sortstr == null) {
			if (other.sortstr != null)
				return false;
		} else if (!sortstr.equals(other.sortstr))
			return false;
		if (showcases == null) {
			if (other.showcases != null)
				return false;
		} else if (!showcases.equals(other.showcases))
			return false;

		return true;
    }

    /*{@inheritDoc}*/
    public int hashCode() {
    	final int prime = 29;
		int result = 1;
		result = prime * result
				+ ((cabinetName == null) ? 0 : cabinetName.hashCode());
		result = prime * result + ((cabinetUrl == null) ? 0 : cabinetUrl.hashCode());
		result = prime * result + ((cabinetPic == null) ? 0 : cabinetPic.hashCode());
		result = prime * result + ((gmtCreate == null) ? 0 : gmtCreate.hashCode());
		result = prime * result + ((gmtModify == null) ? 0 : gmtModify.hashCode());
		result = prime * result + ((isShowstr == null) ? 0 : isShowstr.hashCode());
		result = prime * result + ((isadd == null) ? 0 : isadd.hashCode());
		result = prime * result + ((sortstr == null) ? 0 : sortstr.hashCode());
		result = prime * result + ((showcases == null) ? 0 : showcases.hashCode());

		return result;
    }

    /*{@inheritDoc}*/
    public String toString() {
        ToStringBuilder sb = new ToStringBuilder(this, ToStringStyle.DEFAULT_STYLE).append("id",
            this.id).append("cabinetName", this.cabinetName).append("cabinetUrl", this.cabinetUrl)
            .append("cabinetPic", this.cabinetPic).append("isShow", this.isShow).append("sort",
                this.sort).append("goodsNum", this.goodsNum).append("gmtCreate", this.gmtCreate)
            .append("gmtModify", this.gmtModify);
        return sb.toString();
    }

    public String getIsShowstr() {
        return isShowstr;
    }

    public void setIsShowstr(String isShowstr) {
        this.isShowstr = isShowstr;
    }

    public String getIsadd() {
        return isadd;
    }

    public void setIsadd(String isadd) {
        this.isadd = isadd;
    }

    public String getSortstr() {
        return sortstr;
    }

    public void setSortstr(String sortstr) {
        this.sortstr = sortstr;
    }

    public List<Showcase> getShowcases() {
        return showcases;
    }

    public void setShowcases(List<Showcase> showcases) {
        this.showcases = showcases;
    }

    public String getCabinetKeyword() {
        return cabinetKeyword;
    }

    public void setCabinetKeyword(String cabinetKeyword) {
        this.cabinetKeyword = cabinetKeyword;
    }

}
