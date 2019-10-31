package com.huaixuan.network.common.util;

import java.util.List;
import java.util.ArrayList;

public class PageUtils {

	public List doPage(List list, Page page) {
		if (list == null) {
			throw new NullPointerException("未传入必须的List对象.");
		}
		int pagesize = page.getPageSize();
		int totalPages = page.getTotalPages();
		int currentPage = page.getCurrentPage();
		int start = page.getPageStartRow();
		int end = page.getPageEndRow();
		int previous = page.getPreviousPage();
		int next = page.getNextPage();
		List temp = new ArrayList();
		int size = list.size();
		int pages = this.getPages(size, pagesize);
		start = 0;
		end = 0;
		page.setTotalPages(pages);
		page.setTotalRowsAmount(size);
		page.setCurrentPage(currentPage);
		page.setPageSize(pagesize);
		if (currentPage >= pages) {
			currentPage = pages;
			start = (pages - 1) * pagesize;
			end = size;
			previous = pages - 1;
			next = pages;
		} else if (currentPage < pages) {
			start = (currentPage - 1) * pagesize;
			end = (currentPage) * pagesize;
			next = currentPage + 1;
			previous = currentPage - 1;
		}
		for (int i = start; i < end; i++) {
			temp.add(list.get(i));
		}
		return temp;
	}

	private int getPages(int size, int pagesize) {
		int pages = 0;
		if (size % pagesize == 0 && size != 0) {
			pages = size / pagesize;
		} else {
			pages = size / pagesize + 1;
		}
		return pages;
	}
}