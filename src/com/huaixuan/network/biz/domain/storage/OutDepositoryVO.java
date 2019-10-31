/**
 * @Title: OutDepositoryVO.java
 * @Package com.hundsun.bible.domain.model.ios
 * @Description: TODO
 * @author liuwd weida-liu@foxmail.com
 * @date 2010-11-11 上午10:59:05
 * @version V1.0
 */
package com.huaixuan.network.biz.domain.storage;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.BaseObject;

/**
 * @ClassName: OutDepositoryVO
 * @Description: 出库单对象
 * @author liuwd weida-liu@foxmail.com
 * @date 2010-11-11 上午10:59:05
 *
 */
public class OutDepositoryVO extends BaseObject {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/*
	 * 出库单主表信息
	 */
	private OutDepository outDepository;

	/*
	 * 出库单中的产品信息
	 */
	private List<ProdRelationOut> prodRelationOutList;

	/*
	 * 其他统计信息
	 */
	private Map<String,String> otherInfoMap;

	public OutDepository getOutDepository() {
		return outDepository;
	}

	public void setOutDepository(OutDepository outDepository) {
		this.outDepository = outDepository;
	}

	public List<ProdRelationOut> getProdRelationOutList() {
		return prodRelationOutList;
	}

	public void setProdRelationOutList(List<ProdRelationOut> prodRelationOutList) {
		this.prodRelationOutList = prodRelationOutList;
	}

	public Map<String, String> getOtherInfoMap() {
		return otherInfoMap;
	}

	public void setOtherInfoMap(Map<String, String> otherInfoMap) {
		this.otherInfoMap = otherInfoMap;
	}

}
