/**
 * 
 */
package com.huaixuan.network.biz.domain.shop;

import java.io.Serializable;

import com.huaixuan.network.biz.domain.BaseObject;

/**
 * @author shengyong
 *
 */
public class BrandRule extends BaseObject implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 4188985989726435012L;

    private long              id;

    private long              brandId;

    private long              returnId;

    private String            status;

    private String            brandName;

    private String            ruleName;
    
    private String            isCheck;

    public String getIsCheck() {
		return isCheck;
	}

	public void setIsCheck(String isCheck) {
		this.isCheck = isCheck;
	}

	public long getBrandId() {
        return brandId;
    }

    public void setBrandId(long brandId) {
        this.brandId = brandId;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

	public long getReturnId() {
		return returnId;
	}

	public void setReturnId(long returnId) {
		this.returnId = returnId;
	}

}
