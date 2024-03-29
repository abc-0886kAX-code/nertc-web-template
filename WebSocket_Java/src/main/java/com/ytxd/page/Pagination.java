package com.ytxd.page;

import java.util.ArrayList;
import java.util.List;

public class Pagination {
	
	private int pageSize = 12; 				//每页显示的记录数,默认是12条
	private int totalCount; 				//所有数据总的记录数
	private int page=1;						//当前页
	private List data = new ArrayList(); 	//分页数据
	private String requestURL;				//请求URL
	private String params;					//chen_zhenyu添加的，用于传递查询的参数
	private String extraInfo;				//请求地址的扩展信息，例如?keyword=ABC]
	
	/**
	 * 无参构造
	 */
	public Pagination(){}
	
	/**
	 * 
	 * @param page			当前页
	 * @param totalCount	总记录数
	 */
	public Pagination(int page,int totalCount){
		super();
		this.totalCount = totalCount;
		this.page = page;
	}
	
	/**
	 * 
	 * @param page			当前页
	 * @param pageSize		每页记录数
	 * @param totalCount	总记录数
	 */
	public Pagination(int page,int pageSize,int totalCount) {
		super();
		this.pageSize = pageSize;
		this.totalCount = totalCount;
		this.page = page;
	}

	/**
	 * 返回总页数
	 * @return
	 */
	public int getTotalPage() {
		return (this.totalCount + this.pageSize - 1) / this.pageSize;
	}
	
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public List getData() {
		return data;
	}
	public void setData(List data) {
		this.data = data;
	}
	public String getRequestURL() {
		return requestURL;
	}
	public void setRequestURL(String requestURL) {
		this.requestURL = requestURL;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public String getExtraInfo() {
		return extraInfo;
	}

	public void setExtraInfo(String extraInfo) {
		this.extraInfo = extraInfo;
	}
}
