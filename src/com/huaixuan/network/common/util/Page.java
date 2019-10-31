package com.huaixuan.network.common.util;

import java.util.ArrayList;

/**
 * ��װ��ҳ���������.
 */
/**
 * ��װ��ҳ���������.
 */
public class Page {

    public static final int PAGESIZE    = 25;
    /**
     * ������
     */
    private int             totalRowsAmount;
    /**
     * �Ƿ����ù�totalRowsAmount
     */
    private boolean         rowsAmountSet;
    /**
     * ÿҳ����
     */
    private int             pageSize    = PAGESIZE;
    /**
     * ��ǰҳ��
     */
    private int             currentPage = 1;
    /**
     * ��һҳ
     */
    private int             nextPage;
    /**
     * ��һҳ
     */
    private int             previousPage;
    /**
     * ��ҳ��
     */
    private int             totalPages;
    /**
     * �Ƿ�����һҳ
     */
    private boolean         hasNext;
    /**
     * �Ƿ���ǰһҳ
     */
    private boolean         hasPrevious;

    /**
     * ��ǰҳ��ʼ��¼
     */
    private int             pageStartRow;
    /**
     * ��ǰҳ��ֹ��¼
     */
    private int             pageEndRow;

    /**
     *
     */

    public Page(int totalRows) {
        setTotalRowsAmount(totalRows);
    }

    public Page() {
    }

    /**
     * @param totalRowsAmount   The totalRowsAmount to set.
     */
    public void setTotalRowsAmount(int i) {
        if (pageSize == 0)
            pageSize = PAGESIZE;

        if (!this.rowsAmountSet) {
            totalRowsAmount = i;
            totalPages = (totalRowsAmount % pageSize == 0) ? totalRowsAmount / pageSize
                : totalRowsAmount / pageSize + 1;
            this.rowsAmountSet = true;
        }

    }

    /**
     * @param currentPage   The currentPage to set.
     */
    public void setCurrentPage(int i) {
        currentPage = i;
        nextPage = currentPage + 1;
        previousPage = currentPage - 1;//���㵱ǰҳ��ʼ�кͽ�����
        if (currentPage * pageSize < totalRowsAmount) {
            pageEndRow = currentPage * pageSize;
            pageStartRow = pageEndRow - pageSize + 1;

        } else {
            pageEndRow = totalRowsAmount;
            pageStartRow = pageSize * (totalPages - 1) + 1;
            if (pageStartRow < 0) {//修改了当totalRowsAmount =0,currentPage=1 的错� 
                pageStartRow = 0;
            }
        }
        if (nextPage > totalPages) {
            hasNext = false;
        } else {
            hasNext = true;
        }
        if (previousPage == 0) {
            hasPrevious = false;
        } else {
            hasPrevious = true;
        }
    }

    /**
     * @return   Returns the currentPage.
     */
    public int getCurrentPage() {
        return currentPage;
    }

    public boolean isHasNext() {
        return hasNext;
    }

    public boolean isHasPrevious() {
        return hasPrevious;
    }

    /**
     * @return   Returns the nextPage.
     */
    public int getNextPage() {
        return nextPage;
    }

    /**
     * @return   Returns the pageSize.
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * @return   Returns the previousPage.
     */
    public int getPreviousPage() {
        return previousPage;
    }

    /**
     * @return   Returns the totalPages.
     */
    public int getTotalPages() {
        return totalPages;
    }

    /**
     * @return   Returns the totalRowsAmount.
     */
    public int getTotalRowsAmount() {
        return totalRowsAmount;
    }

    /**
     * @param hasNext   The hasNext to set.
     */
    public void setHasNext(boolean b) {
        hasNext = b;
    }

    /**
     * @param hasPrevious   The hasPrevious to set.
     */
    public void setHasPrevious(boolean b) {
        hasPrevious = b;
    }

    /**
     * @param nextPage   The nextPage to set.
     */
    public void setNextPage(int i) {
        nextPage = i;
    }

    /**
     * @param pageSize   The pageSize to set.
     */
    public void setPageSize(int i) {
        pageSize = i;
    }

    /**
     * @param previousPage   The previousPage to set.
     */
    public void setPreviousPage(int i) {
        previousPage = i;
    }

    /**
     * @param totalPages   The totalPages to set.
     */
    public void setTotalPages(int i) {
        totalPages = i;
    }

    /**
     * @return   Returns the pageEndRow.
     */
    public int getPageEndRow() {
        int cPage = currentPage;
        int pgSize = pageSize;
        int assumeLast = pgSize * cPage;
        int totalItem = getTotalRowsAmount();
        if (assumeLast > totalItem) {
            return totalItem;
        } else {
            return assumeLast;
        }
    }

    /**
     * @return   Returns the pageStartRow.
     */
    public int getPageStartRow() {
        int cPage = currentPage;
        if (cPage == 1)
            return 1;// 第一页开始当然是� 1 条记�
        cPage--;
        int pgSize = pageSize;
        return pgSize * cPage + 1;
    }

    public int getPageFristItem() {
        int cPage = currentPage;
        if (cPage == 1)
            return 1;// 第一页开始当然是� 1 条记�
        cPage--;
        int pgSize = pageSize;
        return pgSize * cPage + 1;
    }

    public int getPageLastItem() {
        int cPage = currentPage;
        int pgSize = pageSize;
        int assumeLast = pgSize * cPage;
        int totalItem = getTotalRowsAmount();
        if (assumeLast > totalItem) {
            return totalItem;
        } else {
            return assumeLast;
        }
    }

    /**
     * @param pageStartRow   The pageStartRow to set.
     */
    public void setPageStartRow(int pageStartRow) {
        this.pageStartRow = pageStartRow;
    }

    //	public String getDescription() {
    //		String description = "Total:" + this.getTotalRowsAmount() + " items "
    //				+ this.getTotalPages() + " pages";
    //			      this.currentPage+" Previous "+this.hasPrevious +
    //			      " Next:"+this.hasNext+
    //			      " start row:"+this.pageStartRow+
    //			      " end row:"+this.pageEndRow;
    //		return description;
    //	}

    public String getDescription() {
        String description = "Total:" + this.getTotalRowsAmount() + " items "
                             + this.getTotalPages() + " pages,Current page:" + this.currentPage
                             + " Previous " + this.hasPrevious + " Next:" + this.hasNext
                             + " start row:" + this.pageStartRow + " end row:" + this.pageEndRow;
        return description;
    }

    public static void main(String args[]) {
        Page pc = new Page();

        pc.setPageSize(4);
        pc.setTotalRowsAmount(8);
        pc.setCurrentPage(1);
    }
}
