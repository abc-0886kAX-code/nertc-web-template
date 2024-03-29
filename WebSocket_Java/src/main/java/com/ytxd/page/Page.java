/**
 * Page.java
 * 
 * Copyright(C)2008 Founder Corporation.
 * written by Founder Corp.
 */
package com.ytxd.page;

/**
 * [类名]<br>
 * Page<br>
 * [功能概要]<br>
 * <br>
 * <br>
 * [変更履歴]<br>
 * 2009-3-24 ver1.00 新建 zhaoxinsheng<br>
 * 
 * @author FOUNDER CORPORATION
 * @version 1.00
 */
public interface Page {
    int DEFAULT_PAGE_SIZE = 12;
    /**
     * 取�?�记录数.
     */
    long getTotalCount();
    /**
     * 取�?�页�?.
     */
    long getTotalPageCount();
    /**
     * 取每页数据容�?.
     */
    int getPageSize();
    /**
     * 取当前页中的记录.
     */
    Object getResult();
    
    /**
     * 赋�?�当前页中的记录.
     */
    void setData(Object data);
    /**
     * 取该页当前页�?,页码�?1�?�?.
     */
    long getCurrentPageNo();
    /**
     * 取该页当前开始数.
     */
    long getStart();
    /**
     * 该页是否有下�?�?.
     */
    boolean hasNextPage();
    /**
     * 该页是否有上�?�?.
     */
    boolean hasPreviousPage();
    /**
     * 第一�?.
     */
    long getFirstPage();
    /**
     * 上一�?
     */
    long getPreviousPage();
    /**
     * 下一�?
     */
    long getNextPage();
    /**
     * �?后一�?
     */
    long getLastPage();
    
}
