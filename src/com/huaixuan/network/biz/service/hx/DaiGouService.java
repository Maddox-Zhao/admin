package com.huaixuan.network.biz.service.hx;

import com.huaixuan.network.biz.domain.admin.AdminAgent;
import com.huaixuan.network.biz.domain.hx.Customer;
import com.huaixuan.network.biz.domain.hx.DaiGou;
import com.huaixuan.network.biz.query.QueryPage;

public interface DaiGouService
{
	public void addDaiGou(DaiGou daigou, Customer customer,AdminAgent adminAgent);

	public boolean daiGouCodeIsExist(String daigouCode);

	public void deleteDaiGouById(Long id);

	public QueryPage getDaiGouByConditionWithPage(DaiGou daigou, int currPage,
			int pageSize);

	public DaiGou getDaiGouById(Long id);

	public DaiGou selectDaigouByDaigouCode(String daigouCode);

	public void updateDaiGouByNotNull(DaiGou daigou);

}
