package com.huaixuan.network.biz.domain.stock;

 import java.io.Serializable;

 /**
  * 逴�明细查询对象(bibleUtil auto code generation)
  * @version 3.2.0
  */
 public class ShoppingRefundGatherSearch implements Serializable {

	 /**
	 *
	 */
	private static final long serialVersionUID = -5058723490361404002L;

	 /* @property: 产品编码*/
	 private String goodsCode;
	 /* @property: 产品名称*/
	 private String instanceName;
	 /* @property: 类目*/
	 private String catCode;
	 /* @property: 属�*/
	 private String attrs;
	 /* @property: 单位*/
	 private String units;
	 /* @property: 逴�数量*/
	 private Long refNum;
	 /* @property: 应收金额*/
	 private Double dueFee;
	 /* @property: 实收金额*/
	 private Double factFee;

	 private String storType;
	 private Long   depFirstId;

	 private Long damagedNum;//逴�残品数量

	public String getGoodsCode() {
		return goodsCode;
	}

	public void setGoodsCode(String goodsCode) {
		this.goodsCode = goodsCode;
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

	public String getUnits() {
		return units;
	}

	public void setUnits(String units) {
		this.units = units;
	}

	public Long getRefNum() {
		return refNum;
	}

	public void setRefNum(Long refNum) {
		this.refNum = refNum;
	}

	public Double getDueFee() {
		return dueFee;
	}

	public void setDueFee(Double dueFee) {
		this.dueFee = dueFee;
	}

	public Double getFactFee() {
		return factFee;
	}

	public void setFactFee(Double factFee) {
		this.factFee = factFee;
	}

	public Long getDamagedNum() {
		return damagedNum;
	}

	public void setDamagedNum(Long damagedNum) {
		this.damagedNum = damagedNum;
	}

    public String getStorType() {
        return storType;
    }

    public void setStorType(String storType) {
        this.storType = storType;
    }

    public Long getDepFirstId() {
        return depFirstId;
    }

    public void setDepFirstId(Long depFirstId) {
        this.depFirstId = depFirstId;
    }
 }
