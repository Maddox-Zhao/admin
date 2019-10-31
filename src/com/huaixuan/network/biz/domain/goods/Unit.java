package com.huaixuan.network.biz.domain.goods;

import com.huaixuan.network.biz.domain.BaseObject;

public class Unit extends BaseObject {

	private static final long serialVersionUID = 3541624871076774508L;
	private Long id;
	private String name;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
