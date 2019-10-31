package com.huaixuan.network.biz.query;

import java.io.Serializable;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

/**
 * @author fish
 */
public abstract class QueryBase implements Serializable {

	private static final long serialVersionUID = 7603300820027561064L;

	private static final Integer defaultPageSize = new Integer(20);

	private static final Integer defaultFriatPage = new Integer(1);

	private static final Integer defaultTotleItem = new Integer(0);

	private Integer totalItem;

	private Integer pageSize;

	private Integer currentPage;

	/**
	 * @return Returns the defaultPageSize.
	 */
	protected final Integer getDefaultPageSize() {
		return defaultPageSize;
	}

	public boolean isFirstPage() {
		return this.getCurrentPage().intValue() == 1;
	}

	public int getPreviousPage() {
		int back = this.getCurrentPage().intValue() - 1;

		if (back <= 0) {
			back = 1;
		}

		return back;
	}

	public boolean isLastPage() {
		return this.getTotalPage() == this.getCurrentPage().intValue();
	}

	public int getNextPage() {
		int back = this.getCurrentPage().intValue() + 1;

		if (back > this.getTotalPage()) {
			back = this.getTotalPage();
		}

		return back;
	}

	/**
	 * @return Returns the currentPage.
	 */
	public Integer getCurrentPage() {
		if (currentPage == null) {
			return defaultFriatPage;
		}

		return currentPage;
	}

	/**
	 * @param current
	 *            The currentPage to set.
	 */
	public void setCurrentPage(Integer cPage) {
		if ((cPage == null) || (cPage.intValue() <= 0)) {
			this.currentPage = defaultFriatPage;
		} else {
			this.currentPage = cPage;
		}
	}
	
	/**
	 * 把currPage +1  miniui pageIndex 默认从0开始
	 * @param currPage
	 */
	public void setCurrentPageForMiniUi(String currPage) {
		Integer cPage = 0;
		try
		{
			cPage = Integer.valueOf(currPage);
			cPage++;//miniui  pageIndex 是从0开始
		}
		catch (Exception e) {
			cPage = -1;
		}
		if ((cPage == null) || (cPage.intValue() <= 0)) {
			this.currentPage = defaultFriatPage;
		} else {
			this.currentPage = cPage;
		}
	}

	public void setCurrentPageString(String s) {
		if (StringUtils.isBlank(s)) {
			return;
		}
		try {
			setCurrentPage(Integer.parseInt(s));
		} catch (NumberFormatException ignore) {
			this.setCurrentPage(defaultFriatPage);
		}
	}

	/**
	 * @return Returns the pageSize.
	 */
	public Integer getPageSize() {
		if (pageSize == null) {
			return getDefaultPageSize();
		}

		return pageSize;
	}

	public boolean hasSetPageSize() {
		return pageSize != null;
	}

	/**
	 * @param size
	 *            The pageSize to set.
	 */
	public void setPageSize(Integer pSize) {
		if (pSize == null) {
			throw new IllegalArgumentException("PageSize can't be null.");
		}

		if (pSize.intValue() <= 0) {
			throw new IllegalArgumentException("PageSize must great than zero.");
		}

		this.pageSize = pSize;
	}
	
 
	public void setPageSizeString(String pageSizeString) {
		if (StringUtils.isBlank(pageSizeString)) {
			return;
		}

		try {
			Integer integer = new Integer(pageSizeString);
			this.setPageSize(integer);
		} catch (NumberFormatException ignore) {
		}
	}

	/**
	 * @return Returns the totalItem.
	 */
	public Integer getTotalItem() {
		if (totalItem == null) {
			// throw new IllegalStateException("Please set the TotalItem
			// frist.");
			return defaultTotleItem;
		}
		return totalItem;
	}

	/**
	 * @param totalItem
	 *            The totalItem to set.
	 */
	public void setTotalItem(Integer tItem) {
		if (tItem == null) {
			// throw new IllegalArgumentException("TotalItem can't be null.");
			tItem = new Integer(0);
		}
		this.totalItem = tItem;
		int current = this.getCurrentPage().intValue();
		int lastPage = this.getTotalPage();
		if (current > lastPage) {
			this.setCurrentPage(new Integer(lastPage));
		}
	}
	
	public void setTotalItemForMiniUi(Integer tItem) {
		if (tItem == null) {
			// throw new IllegalArgumentException("TotalItem can't be null.");
			tItem = new Integer(0);
		}
		this.totalItem = tItem;
	}

	public int getTotalPage() {
		int pgSize = this.getPageSize().intValue();
		int total = this.getTotalItem().intValue();
		int result = total / pgSize;
		if ((total % pgSize) != 0) {
			result++;
		}
		return result;
	}

	public int getPageFristItem() {
		int cPage = this.getCurrentPage().intValue();
		if (cPage == 1) {
			//return 1; 
			return 0;
		}
		cPage--;
		int pgSize = this.getPageSize().intValue();

		//return (pgSize * cPage) + 1;
		return (pgSize * cPage);
	}

	/**
	 *
	 */
	public int getMysqlPageFristItem() {
		return getPageFristItem() - 1;
	}

	public int getPageLastItem() {
		int assumeLast = getExpectPageLastItem();
		int totalItem = getTotalItem().intValue();

		if (assumeLast > totalItem) {
			return totalItem-(this.getPageSize().intValue()*(this.getCurrentPage().intValue()-1));
		} else {
			return this.getPageSize().intValue();
		}
	}
	

	public int getPageLastItem1() {
		return this.getPageSize().intValue();
	}

	public int getExpectPageLastItem() {
		int cPage = this.getCurrentPage().intValue();
		int pgSize = this.getPageSize().intValue();
		return pgSize * cPage;
	}

	protected String getSQLBlurValue(String value) {
		if (value == null) {
			return null;
		}

		return value + '%';
	}

	/**
	 * 
	 */
	public abstract Map<String, String> getParameters();
}

