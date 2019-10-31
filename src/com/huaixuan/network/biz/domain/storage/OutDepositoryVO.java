/**
 * @Title: OutDepositoryVO.java
 * @Package com.hundsun.bible.domain.model.ios
 * @Description: TODO
 * @author liuwd weida-liu@foxmail.com
 * @date 2010-11-11 ����10:59:05
 * @version V1.0
 */
package com.huaixuan.network.biz.domain.storage;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.BaseObject;

/**
 * @ClassName: OutDepositoryVO
 * @Description: ���ⵥ����
 * @author liuwd weida-liu@foxmail.com
 * @date 2010-11-11 ����10:59:05
 *
 */
public class OutDepositoryVO extends BaseObject {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/*
	 * ���ⵥ������Ϣ
	 */
	private OutDepository outDepository;

	/*
	 * ���ⵥ�еĲ�Ʒ��Ϣ
	 */
	private List<ProdRelationOut> prodRelationOutList;

	/*
	 * ����ͳ����Ϣ
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
