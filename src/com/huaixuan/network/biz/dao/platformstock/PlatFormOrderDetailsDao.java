package com.huaixuan.network.biz.dao.platformstock;

import java.util.List;

import com.huaixuan.network.biz.domain.platformstock.PlatFormOrderDetails;
import com.huaixuan.network.biz.domain.platformstock.PlatFormOrderRecord;

public interface PlatFormOrderDetailsDao {

	public void insertOrder(PlatFormOrderDetails platformorderdetails);
	
	public PlatFormOrderDetails select(PlatFormOrderDetails platformorderdetails);
	
	//返回多条详情用于weimob
	
	public List<PlatFormOrderDetails> selectList(PlatFormOrderDetails platformorderdetails);
}
