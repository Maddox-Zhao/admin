/**
 * 
 */
package com.huaixuan.network.biz.domain.provider;

/**
 * @author TT
 * 
 */
public class ProviderYShangPage {

	private int id;
	private Integer page;
	private Integer needPage;
	public String updateTime;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Integer getPage() {
		return page;
	}
	public void setPage(Integer page) {
		this.page = page;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public Integer getNeedPage() {
		return needPage;
	}
	public void setNeedPage(Integer needPage) {
		this.needPage = needPage;
	}
	
	
}
