package com.huaixuan.network.biz.service.shop;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.shop.Nav;
import com.huaixuan.network.biz.query.QueryPage;

public interface NavService {
	public void addNav(Nav nav);

	public void editNav(Nav nav);

	public void removeNav(Long navId);

	public Nav getNav(Long navId);

	public List<Nav> getNavs();

	Map<String, List<Nav>> getNavForShow() throws Exception;

	public QueryPage getNavsPage(long shopId, int currentPage, int pageSize);

	public void isshowNav(Nav newNav);

	public void isOpenNav(Nav newNav);
}
