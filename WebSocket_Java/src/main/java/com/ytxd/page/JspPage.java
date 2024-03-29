/**
 * Page.java
 * 
 * Copyright(C)2008 Founder Corporation.
 * written by Founder Corp.
 */
package com.ytxd.page;

import java.util.ArrayList;

/**
 * [类名]<br>
 * Page<br>
 * [功能概要]<br>
 * <br>
 * <br>
 * [変更履歴]<br>
 * 2009-3-16 ver1.00 新建 zhaoxinsheng<br>
 * 
 * @author FOUNDER CORPORATION
 * @version 1.00
 */
public class JspPage implements Page {
    // 每页的记录数
    private int pageSize = DEFAULT_PAGE_SIZE;

    // 当前页第�?条数据在List中的位置,�?0�?�?
    private long start;

    // 当前页中存放的记�?,类型�?般为List
    private Object data;

    // 总记录数
    private long totalCount;

    /**
     * 构�?�方法，只构造空�?.
     */
    public JspPage() {
        this(0, 0, DEFAULT_PAGE_SIZE, new ArrayList());
    }

    /**
     * 默认构�?�方�?.
     * 
     * @param start
     *            本页数据在数据库中的起始位置
     * @param totalSize
     *            数据库中总记录条�?
     * @param pageSize
     *            本页容量
     * @param data
     *            本页包含的数�?
     */
    public JspPage(long start, long totalSize, int pageSize, Object data) {
        this.pageSize = pageSize;
        this.start = start;
        this.totalCount = totalSize;
        this.data = data;
    }

    /**
     * @return the data
     */
    public Object getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(Object data) {
        this.data = data;
    }

    /**
     * 取�?�记录数.
     */
    public long getTotalCount() {
        return this.totalCount;
    }

    /**
     * 取�?�页�?.
     */
    public long getTotalPageCount() {
        return (this.totalCount + this.pageSize - 1) / this.pageSize;
    }

    /**
     * 取每页数据容�?.
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * 取当前页中的记录.
     */
    public Object getResult() {
        return data;
    }

    /**
     * 取该页当前页�?,页码�?1�?�?.
     */
    public long getCurrentPageNo() {
        return start / pageSize + 1 < 1 ? 1 : start / pageSize + 1; // modify by meixingjian
    }

    /**
     * 取该页当前开始数.
     */
    public long getStart() {
        return start;
    }

    /**
     * 该页是否有下�?�?.
     */
    public boolean hasNextPage() {
        return this.getCurrentPageNo() < this.getTotalPageCount() - 1;
    }

    /**
     * 该页是否有上�?�?.
     */
    public boolean hasPreviousPage() {
        return this.getCurrentPageNo() > 1;
    }

    /** update */
    /** get page no **/
    public long getFirstPage() {
        if ((getCurrentPageNo() - 1) > 0)
            return 1;
        else
            return 0;
    }

    public long getPreviousPage() {
        if ((getCurrentPageNo() - 1) > 0)
            return (this.getCurrentPageNo() - 1);
        else
            return 0;
    }

    public long getNextPage() {
        if ((getCurrentPageNo() + 1) <= getTotalPageCount())
            return (this.getCurrentPageNo() + 1);
        else
            return 0;
    }

    public long getLastPage() {
        if ((getCurrentPageNo() + 1) <= getTotalPageCount())
            return (getTotalPageCount());
        else
            return 0;
    }

    /**
     * 获取任一页第�?条数据在数据集的位置，每页条数使用默认�??.
     * 
     * @see #getStartOfPage(int,int)
     */
    protected static int getStartOfPage(int pageNo) {
        return getStartOfPage(pageNo, DEFAULT_PAGE_SIZE);
    }

    /**
     * 获取任一页第�?条数据在数据集的位置.
     * 
     * @param pageNo
     *            �?1�?始的页号
     * @param pageSize
     *            每页记录条数
     * @return 该页第一条数�?
     */
    public static int getStartOfPage(int pageNo, int pageSize) {
        return (pageNo - 1) * pageSize;
    }

	public JspPage(Object data, long totalCount) {
		super();
		this.data = data;
		this.totalCount = totalCount;
	}
    
    
}