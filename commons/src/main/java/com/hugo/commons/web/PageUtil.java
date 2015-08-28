package com.hugo.commons.web;

/**
 * @author wangweiguang
 */
public class PageUtil {
    private int currentPage = 1;// 当前页数

    public int totalPages = 0;// 总页数

    private int pageSize = 0;// 每页显示数

    private int totalRows = 0;// 总数据数

    private int startNum = 0;// 开始记录

    private int nextPage = 0;// 下一页

    private int previousPage = 0;// 上一页

    private boolean hasNextPage = false;// 是否有下一页

    private boolean hasPreviousPage = false;// 是否有前一页

    public PageUtil(int pageSize, int currentPage, int totalRows) {

        this.pageSize = pageSize;
        this.currentPage = currentPage;
        this.totalRows = totalRows;

        if ((totalRows % pageSize) == 0) {
            totalPages = totalRows / pageSize;
        } else {
            totalPages = totalRows / pageSize + 1;
        }

        if (currentPage >= totalPages) {
            hasNextPage = false;
            currentPage = totalPages;
        } else {
            hasNextPage = true;
        }

        if (currentPage <= 1) {
            hasPreviousPage = false;
            currentPage = 1;
        } else {
            hasPreviousPage = true;
        }

        startNum = (currentPage - 1) * pageSize;

        nextPage = currentPage + 1;

        if (nextPage >= totalPages) {
            nextPage = totalPages;
        }

        previousPage = currentPage - 1;

        if (previousPage <= 1) {
            previousPage = 1;
        }

    }

    public boolean isHasNextPage() {

        return hasNextPage;

    }

    public boolean isHasPreviousPage() {

        return hasPreviousPage;

    }

    public int getNextPage() {
        return nextPage;
    }

    public void setNextPage(int nextPage) {
        this.nextPage = nextPage;
    }

    public int getPreviousPage() {
        return previousPage;
    }

    public void setPreviousPage(int previousPage) {
        this.previousPage = previousPage;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getTotalRows() {
        return totalRows;
    }

    public void setTotalRows(int totalRows) {
        this.totalRows = totalRows;
    }

    public void setHasNextPage(boolean hasNextPage) {
        this.hasNextPage = hasNextPage;
    }

    public void setHasPreviousPage(boolean hasPreviousPage) {
        this.hasPreviousPage = hasPreviousPage;
    }

    public int getStartNum() {
        return startNum;
    }

    public void setStartNum(int startNum) {
        this.startNum = startNum;
    }
}
