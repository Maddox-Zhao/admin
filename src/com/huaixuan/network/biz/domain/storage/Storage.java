package com.huaixuan.network.biz.domain.storage;

import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.huaixuan.network.biz.domain.BaseObject;

/**
 * (bibleUtil auto code generation)
 * @version 3.2.0
 */
public class Storage extends BaseObject {
    /**
	 *
	 */
	private static final long serialVersionUID = -1546602608530274027L;
	public static final String sep              = ";";
    public static final String equ              = "=";
    /* @property: */
    private Long               id;
    /* @property: */
    private Long               goodsId;
    /* @property: */
    private Long               locId;
    /* @property: */
    private Long               goodsInstanceId;
    /* @property: */
    private Long               existNum;
    /* @property: */
    private Long               storageNum;
    /* @property: */
    private Double             price;
    /* @property: */
    private Long               supplierId;
    /* @property: */
    private Date               gmtCreate;
    /* @property: */
    private Date               gmtModify;

    private Long               checkNum;
    //供应商产品编码
    private String             supplier_code;

    //产品销售记录
    private long             salesSum;

    /**redundant fileds**/
    private String             batchNum;
    // 供应商名称
    private String             supplierName;
    //商品编码
    private String             code;
    //商品名称
    private String             instanceName;
    //类目
    private String             catCode;
    //  类目名称
    private String             catName;
    //商品属性
    private String             attrs;
    //单位
    private String             goodsUnit;
    //仓库ID
    private Long               depId;
    //仓库名称
    private String             depositoryName;
    //仓库类型
    private String             depType;
    //在途库存
    private Long               wayNum;
    //可用库存
    private Long               instanceExistNum;
    //成本均价
    private Double             averagePrice;
    //库存成本
    private Double             storageCost;

    private Long               existNumSum;
    //总库存
    private Long               storageNumSum;
    //库位
    private String             depLocationName;
    //一级仓库ID
    private Long               depFirstId;
    //一级仓库名称
    private String             depFirstName;
    //类型
    private String             storType;
    //商城价
    private double             goodsPrice;
    //代销价
    private double             agentPrice;
    //一级仓库类型
    private String             depfirstType;
    //是否批发记录
    private String             isWholesale;
    //批发订单号
    private String             tid;

    private String[]			storageids;

    public String[] getStorageids() {
		return storageids;
	}

	public void setStorageids(String[] storageids) {
		this.storageids = storageids;
	}

	public String getIsWholesale() {
		return isWholesale;
	}

	public void setIsWholesale(String isWholesale) {
		this.isWholesale = isWholesale;
	}

	public String getTid() {
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

	public String getDepfirstType() {
		return depfirstType;
	}

	public void setDepfirstType(String depfirstType) {
		this.depfirstType = depfirstType;
	}

	public double getGoodsPrice() {
		return goodsPrice;
	}

	public void setGoodsPrice(double goodsPrice) {
		this.goodsPrice = goodsPrice;
	}

	public double getAgentPrice() {
		return agentPrice;
	}

	public void setAgentPrice(double agentPrice) {
		this.agentPrice = agentPrice;
	}

	public String getStorType() {
		return storType;
	}

	public void setStorType(String storType) {
		this.storType = storType;
	}

	public String getDepFirstName() {
		return depFirstName;
	}

	public void setDepFirstName(String depFirstName) {
		this.depFirstName = depFirstName;
	}

	public Long getDepFirstId() {
		return depFirstId;
	}

	public void setDepFirstId(Long depFirstId) {
		this.depFirstId = depFirstId;
	}

	/**redundant fileds**/

    /* Default constructor - creates a new instance with no values set. */
    public Storage() {
    }

    /**
     * @return the depType
     */
    public String getDepType() {
        return depType;
    }

    /**
     * @param depType the depType to set
     */
    public void setDepType(String depType) {
        this.depType = depType;
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
    public void setGoodsId(Long obj) {
        this.goodsId = obj;
    }

    public Long getCheckNum() {
        return checkNum;
    }

    public void setCheckNum(Long checkNum) {
        this.checkNum = checkNum;
    }

    /* @model: */
    public Long getGoodsId() {
        return this.goodsId;
    }

    public Long getLocId() {
        return locId;
    }

    public void setLocId(Long locId) {
        this.locId = locId;
    }

    /* @model: */
    public void setGoodsInstanceId(Long obj) {
        this.goodsInstanceId = obj;
    }

    /* @model: */
    public Long getGoodsInstanceId() {
        return this.goodsInstanceId;
    }

    /* @model: */
    public void setExistNum(Long obj) {
        this.existNum = obj;
    }

    /* @model: */
    public Long getExistNum() {
        return this.existNum;
    }

    /* @model: */
    public void setStorageNum(Long obj) {
        this.storageNum = obj;
    }

    /* @model: */
    public Long getStorageNum() {
        return this.storageNum;
    }

    /* @model: */
    public void setPrice(Double obj) {
        this.price = obj;
    }

    /* @model: */
    public Double getPrice() {
        return this.price;
    }

    /* @model: */
    public void setSupplierId(Long obj) {
        this.supplierId = obj;
    }

    /* @model: */
    public Long getSupplierId() {
        return this.supplierId;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getInstanceName() {
        return instanceName;
    }

    public void setInstanceName(String instanceName) {
        this.instanceName = instanceName;
    }

    public String getCatCode() {
        return catCode;
    }

    public void setCatCode(String catCode) {
        this.catCode = catCode;
    }

    public String getAttrs() {
        return attrs;
    }

    public void setAttrs(String attrs) {
        this.attrs = attrs;
    }

    public String getGoodsUnit() {
        return goodsUnit;
    }

    public void setGoodsUnit(String goodsUnit) {
        this.goodsUnit = goodsUnit;
    }

    public String getDepositoryName() {
        return depositoryName;
    }

    public void setDepositoryName(String depositoryName) {
        this.depositoryName = depositoryName;
    }

    public Long getWayNum() {
        return wayNum;
    }

    public void setWayNum(Long wayNum) {
        this.wayNum = wayNum;
    }

    public Long getInstanceExistNum() {
        return instanceExistNum;
    }

    public void setInstanceExistNum(Long instanceExistNum) {
        this.instanceExistNum = instanceExistNum;
    }

    public Double getAveragePrice() {
        return averagePrice;
    }

    public void setAveragePrice(Double averagePrice) {
        this.averagePrice = averagePrice;
    }

    public Double getStorageCost() {
        return storageCost;
    }

    public void setStorageCost(Double storageCost) {
        this.storageCost = storageCost;
    }

    public Long getDepId() {
        return depId;
    }

    public void setDepId(Long depId) {
        this.depId = depId;
    }

    public Long getExistNumSum() {
        return existNumSum;
    }

    public void setExistNumSum(Long existNumSum) {
        this.existNumSum = existNumSum;
    }

    public String getDirectCategoryCode() {
        if (this.catCode == null) {
            return null;
        }
        int last = this.catCode.lastIndexOf('.');
        if (last == -1) {
            return this.catCode;
        }
        return this.catCode.substring(last + 1);
    }

    public Map<String, String> getProperties() {
        if (StringUtils.isBlank(attrs)) {
            return Collections.EMPTY_MAP;
        }
        String[] pros = attrs.split(sep);
        if (pros.length == 0) {
            return Collections.EMPTY_MAP;
        }
        Map<String, String> back = new LinkedHashMap<String, String>();
        for (String s : pros) {
            if (StringUtils.isBlank(s)) {
                continue;
            }
            String[] kvpair = s.split(equ);
            if (kvpair == null) {
                continue;
            }
            if (kvpair.length == 1) {
                back.put(kvpair[0], null);
            }
            if (kvpair.length == 2) {
                back.put(kvpair[0], kvpair[1]);
            }
        }
        return back;
    }

    public Long getStorageNumSum() {
        return storageNumSum;
    }

    public void setStorageNumSum(Long storageNumSum) {
        this.storageNumSum = storageNumSum;
    }

    public String getDepLocationName() {
        return depLocationName;
    }

    public void setDepLocationName(String depLocationName) {
        this.depLocationName = depLocationName;
    }

    public String getBatchNum() {
        return batchNum;
    }

    public void setBatchNum(String batchNum) {
        this.batchNum = batchNum;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    /**
     * @return supplierName
     */
    public String getSupplierName() {
        return supplierName;
    }

    /**
     * @param supplierName 要设置的 supplierName
     */
    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

	public String getSupplier_code() {
		return supplier_code;
	}

	public void setSupplier_code(String supplier_code) {
		this.supplier_code = supplier_code;
	}

	public long getSalesSum() {
		return salesSum;
	}

	public void setSalesSum(long salesSum) {
		this.salesSum = salesSum;
	}

}
