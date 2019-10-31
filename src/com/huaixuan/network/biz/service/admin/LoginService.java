package com.huaixuan.network.biz.service.admin;

import com.huaixuan.network.biz.domain.admin.Admin;
import com.huaixuan.network.biz.domain.hx.Dxrecord;
import com.hundsun.network.melody.common.web.cookyjar.Cookyjar;

public interface LoginService {

	/**
	 * ��̨ϵͳ��¼
	 * @param admin
	 * @return
	 */
	public Admin login(Admin admin, Cookyjar cookyjar, boolean remember);
	
	public void addDxrecord(Dxrecord dx);
}
