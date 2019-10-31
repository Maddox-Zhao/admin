package com.huaixuan.network.biz.domain.goods;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.huaixuan.network.biz.domain.BaseObject;

public class GoodsInstance extends BaseObject implements Cloneable {

    public static final String sep              = ";";

    public static final String equ              = "=";

    private static final long  serialVersionUID = 6409969797333664689L;

    protected Log              log              = LogFactory.getLog(this.getClass());

    private Long               id;

    private Long               goodsId;

    private String             code;
    
    private String 				color;
	
	private String 				material;
	 
	private String 				type;
	
	private Long  				siteId;

    private String             catCode;

    private String             attrs;
    
    private String 				size;

    //大陆零售价
    private Double             sellPrice;                                            // 
    
    //市场价
    private Double             marketPrice;                                          //

    private Double             salesProPrice;                                        // 
    
    //香港批发价
    private Double             wholesalePrice;     

    private String             instanceName;

    private String             pyCode;

    private String             status;

    private String             attrDesc;

    private String             remark;

    private Integer            maxNum;                                               // 1717

    private Integer            minNum;                                               // С1717

    private Integer            existNum;
    
    private Integer            hkExistNum;
    
    private Integer            sellNum;

    private Integer            wayNum;

    private String             isPresent;

    private String             goodsUnit;

    private String             min;

    private String             max;

    private Integer            storageNum;

    private Double            totalWeight;
    
    private Double            totalEpWeight;                     // 包邮商品总重量

    private Double            totalPrice;

    private Long              buyNum;

    private String            depfirstName;

    private Double             agentPrice;                              //代销价格

    private List<AvailableStock>  availableStockList;               //中间表各一级仓库列表

    private Integer                wholeNum;                            //批发数量

    private String            taobaoSkuProperty;                    //熙浪产品对应的淘宝销售属性   -add by shenzh 2010-10-22
    
    private String            catName;
    
	private Double            goodsPrice;
	
	private String            imgSmall;
	
	private Double            goodsWeight;
	
	private Long              supplierId;
	
	private String            sid;
	
	private String            shoppingOriCount;
	
	private Double				hkhxPrice;
    
    private Double				euPrice;
    
    private Double				hkPrice;
    
    private Long 				brandId;
    private Long 				idSeries;
    
    private String 				imgMiddle;
    private String 				goodsTitle;
    

	public String getGoodsTitle() {
		return goodsTitle;
	}

	public void setGoodsTitle(String goodsTitle) {
		this.goodsTitle = goodsTitle;
	}

	public String getImgMiddle() {
		return imgMiddle;
	}

	public void setImgMiddle(String imgMiddle) {
		this.imgMiddle = imgMiddle;
	}

	public Long getBrandId() {
		return brandId;
	}

	public void setBrandId(Long brandId) {
		this.brandId = brandId;
	}

	public Long getIdSeries() {
		return idSeries;
	}

	public void setIdSeries(Long idSeries) {
		this.idSeries = idSeries;
	}

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public String getShoppingOriCount() {
		return shoppingOriCount;
	}

	public void setShoppingOriCount(String shoppingOriCount) {
		this.shoppingOriCount = shoppingOriCount;
	}

	public Long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}

	public Double getTotalWeight() {
		return totalWeight;
	}

	public void setTotalWeight(Double totalWeight) {
		this.totalWeight = totalWeight;
	}

	public Double getTotalEpWeight() {
		return totalEpWeight;
	}

	public void setTotalEpWeight(Double totalEpWeight) {
		this.totalEpWeight = totalEpWeight;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Double getAgentPrice() {
		return agentPrice;
	}

	public void setAgentPrice(Double agentPrice) {
		this.agentPrice = agentPrice;
	}

	public Double getGoodsPrice() {
		return goodsPrice;
	}

	public void setGoodsPrice(Double goodsPrice) {
		this.goodsPrice = goodsPrice;
	}

	public Double getGoodsWeight() {
		return goodsWeight;
	}

	public void setGoodsWeight(Double goodsWeight) {
		this.goodsWeight = goodsWeight;
	}

	public String getImgSmall() {
		return imgSmall;
	}

	public void setImgSmall(String imgSmall) {
		this.imgSmall = imgSmall;
	}

	public String getCatName() {
		return catName;
	}

	public void setCatName(String catName) {
		this.catName = catName;
	}

	public Integer getWholeNum() {
		return wholeNum;
	}

	public void setWholeNum(Integer wholeNum) {
		this.wholeNum = wholeNum;
	}

	public List<AvailableStock> getAvailableStockList() {
		return availableStockList;
	}

	public void setAvailableStockList(List<AvailableStock> availableStockList) {
		this.availableStockList = availableStockList;
	}

	public String getDepfirstName() {
		return depfirstName;
	}

	public void setDepfirstName(String depfirstName) {
		this.depfirstName = depfirstName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getBuyNum() {
		return buyNum;
	}

	public void setBuyNum(Long buyNum) {
		this.buyNum = buyNum;
	}

	public GoodsInstance() {
        super();
    }

    public GoodsInstance clone() {
        GoodsInstance o = null;
        try {
            o = (GoodsInstance) super.clone();
        } catch (CloneNotSupportedException e) {
            log.error(e.getMessage());
        }
        return o;
    }

    public GoodsInstance(Goods goods) {
        super();
        this.goodsId = goods.getId();
        this.catCode = goods.getCatCode();
        this.instanceName = goods.getTitle();
        this.code = goods.getGoodsSn() + "01";
        this.marketPrice = goods.getMarketPrice();
        this.salesProPrice = goods.getSalesProPrice();
        this.sellPrice = goods.getGoodsPrice();
        this.goodsUnit = goods.getGoodsUnit();
        this.type = goods.getType();
        this.material = goods.getMaterial();
        this.color = goods.getColor();
        this.size = goods.getSize();
        this.existNum = goods.getGoodsNumber();
        this.hkhxPrice = goods.getHkhxPrice();
        this.hkPrice = goods.getHkPrice();
        this.euPrice = goods.getEuPrice();
    }

    public GoodsInstance addProperty(String name, String value) {
        if (this.attrs == null) {
            this.attrs = "";
        }
        this.attrs += (name + equ + value + sep);
        return this;
    }

    public GoodsInstance addDescProperty(String name, String value) {
        if (this.attrDesc == null) {
            this.attrDesc = "";
        }
        this.attrDesc += (name + equ + value + sep);
        return this;
    }

    public GoodsInstance addProperties(Map<String, String> pro, String goodsDesc) {
        if (goodsDesc.equalsIgnoreCase("attrs")) {
            for (Entry<String, String> entry : pro.entrySet()) {
                addProperty(entry.getKey(), entry.getValue());
            }
        } else {
            for (Entry<String, String> entry : pro.entrySet()) {
                addDescProperty(entry.getKey(), entry.getValue());
            }
        }

        return this;
    }

    public boolean haveAttr(String name, String value) {
        StringBuilder sb = new StringBuilder();
        if (StringUtils.isNotBlank(name) && StringUtils.isBlank(value)) {
            return false;
        }
        sb.append(name).append(equ).append(value);
        if (this.attrs != null && (this.attrs.indexOf(sb.toString().trim()) != -1)) {
            return true;
        }
        return false;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public Double getSellPrice() {
		return sellPrice;
	}

	public void setSellPrice(Double sellPrice) {
		this.sellPrice = sellPrice;
	}

	public Double getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(Double marketPrice) {
		this.marketPrice = marketPrice;
	}

	public Double getSalesProPrice() {
		return salesProPrice;
	}

	public void setSalesProPrice(Double salesProPrice) {
		this.salesProPrice = salesProPrice;
	}

	public String getInstanceName() {
        return instanceName;
    }

    public void setInstanceName(String instanceName) {
        this.instanceName = instanceName;
    }

    public String getPyCode() {
        return pyCode;
    }

    public void setPyCode(String pyCode) {
        this.pyCode = pyCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAttrDesc() {
        return attrDesc;
    }

    public void setAttrDesc(String attrDesc) {
        this.attrDesc = attrDesc;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getMaxNum() {
        return maxNum;
    }

    public void setMaxNum(Integer maxNum) {
        this.maxNum = maxNum;
    }

    public Integer getMinNum() {
        return minNum;
    }

    public void setMinNum(Integer minNum) {
        this.minNum = minNum;
    }

    public String getIsPresent() {
        return isPresent;
    }

    public void setIsPresent(String isPresent) {
        this.isPresent = isPresent;
    }

    public String getGoodsUnit() {
        return goodsUnit;
    }

    public void setGoodsUnit(String goodsUnit) {
        this.goodsUnit = goodsUnit;
    }

    public Integer getExistNum() {
        return existNum;
    }

    public void setExistNum(Integer existNum) {
        this.existNum = existNum;
    }

    public Integer getWayNum() {
        return wayNum;
    }

    public void setWayNum(Integer wayNum) {
        this.wayNum = wayNum;
    }

    public String getMax() {
        return max;
    }

    public void setMax(String max) {
        this.max = max;
    }

    public String getMin() {
        return min;
    }

    public void setMin(String min) {
        this.min = min;
    }

    public Integer getStorageNum() {
        return storageNum;
    }

    public void setStorageNum(Integer storageNum) {
        this.storageNum = storageNum;
    }

    public String getTaobaoSkuProperty() {
        return taobaoSkuProperty;
    }

    public void setTaobaoSkuProperty(String taobaoSkuProperty) {
        this.taobaoSkuProperty = taobaoSkuProperty;
    }

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}

	public Long getSiteId() {
		return siteId;
	}

	public void setSiteId(Long siteId) {
		this.siteId = siteId;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public Double getWholesalePrice() {
		return wholesalePrice;
	}

	public void setWholesalePrice(Double wholesalePrice) {
		this.wholesalePrice = wholesalePrice;
	}

	public Integer getSellNum() {
		return sellNum;
	}

	public void setSellNum(Integer sellNum) {
		this.sellNum = sellNum;
	}

	public Double getHkhxPrice() {
		return hkhxPrice;
	}

	public void setHkhxPrice(Double hkhxPrice) {
		this.hkhxPrice = hkhxPrice;
	}

	public Double getEuPrice() {
		return euPrice;
	}

	public void setEuPrice(Double euPrice) {
		this.euPrice = euPrice;
	}

	public Double getHkPrice() {
		return hkPrice;
	}

	public void setHkPrice(Double hkPrice) {
		this.hkPrice = hkPrice;
	}

	public Integer getHkExistNum() {
		return hkExistNum;
	}

	public void setHkExistNum(Integer hkExistNum) {
		this.hkExistNum = hkExistNum;
	}

}
