package com.huaixuan.network.biz.dao.shop;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.shop.Nav;

public interface NavDao {
	void addNav(Nav nav) throws Exception;

	/* @interface model: Nav */
	void editNav(Nav nav) throws Exception;

	/* @interface model: Nav */
	void removeNav(Long navId) throws Exception;

	/* @interface model: Nav,Nav */
	Nav getNav(Long navId) throws Exception;

	/* @interface model: Nav,Nav */
	List<Nav> getNavs() throws Exception;

	List<Nav> getNavByType(String type) throws Exception;

	public Integer getNavsCount(long shopId) throws Exception;

	List<Nav> getNavsPage(Map<String, String> conditions);

	public void isshowNav(Nav newNav);

	public void isOpenNav(Nav newNav);

	public Integer getNavsMax(String navType);

	public void getNavsSortsamll(int sortold, String type);

	public void getNavsSortBig(int sort, int sortold, String type);

	public void getNavsSortBwteenSmall(int sort, int sortold, String type);
}
