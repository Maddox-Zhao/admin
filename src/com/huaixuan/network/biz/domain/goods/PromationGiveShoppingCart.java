package com.huaixuan.network.biz.domain.goods;

import java.util.List;

import com.huaixuan.network.biz.domain.BaseObject;

public class PromationGiveShoppingCart extends BaseObject {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private List<PromationGive> promationGiveList;

	private List<PromationGive> noCountGiveList;

	public List<PromationGive> getPromationGiveList() {
		return promationGiveList;
	}

	public void setPromationGiveList(List<PromationGive> promationGiveList) {
		this.promationGiveList = promationGiveList;
	}

	public List<PromationGive> getNoCountGiveList() {
		return noCountGiveList;
	}

	public void setNoCountGiveList(List<PromationGive> noCountGiveList) {
		this.noCountGiveList = noCountGiveList;
	}
}
