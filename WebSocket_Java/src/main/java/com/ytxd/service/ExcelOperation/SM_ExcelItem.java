package com.ytxd.service.ExcelOperation;

public class SM_ExcelItem {
	private String exportname;  //
	private String id;  //
	private String sourcecolumnname;  //数据源列名
	private String excelcolumnname;  //Excel导出列名
	private String endrownumber;  //行号
	private String rownumber;  //行号
	private String startrownumber;  //行号
	private String columnnumber;  //列号
	private String endcolumnnumber;  //列号
	private String startcolumnnumber;  //列号
	private String visible;  //是否可见
	private String visiblename;  //是否可见
	private String columnwidth;  //列宽
	private String horizontalalignment;  //对齐方式
	private String horizontalalignmentname;  //对齐方式
	private String numberformatlocal;  //格式
	private String iftotal;
	private Integer page;
	private Integer rows;
	private String sort;
	private String order;

	public void setexportname(String exportname) {	this.exportname = exportname;	}
	public String getexportname() {	return exportname;	}
	public void setid(String id) {	this.id = id;	}
	public String getid() {	return id;	}
	public void setsourcecolumnname(String sourcecolumnname) {	this.sourcecolumnname = sourcecolumnname;	}
	public String getsourcecolumnname() {	return sourcecolumnname;	}
	public void setexcelcolumnname(String excelcolumnname) {	this.excelcolumnname = excelcolumnname;	}
	public String getexcelcolumnname() {	return excelcolumnname;	}
	public void setendrownumber(String endrownumber) {	this.endrownumber = endrownumber;	}
	public String getendrownumber() {	return endrownumber;	}
	public void setrownumber(String rownumber) {	this.rownumber = rownumber;	}
	public String getrownumber() {	return rownumber;	}
	public void setstartrownumber(String startrownumber) {	this.startrownumber = startrownumber;	}
	public String getstartrownumber() {	return startrownumber;	}
	public void setcolumnnumber(String columnnumber) {	this.columnnumber = columnnumber;	}
	public String getcolumnnumber() {	return columnnumber;	}
	public void setendcolumnnumber(String endcolumnnumber) {	this.endcolumnnumber = endcolumnnumber;	}
	public String getendcolumnnumber() {	return endcolumnnumber;	}
	public void setstartcolumnnumber(String startcolumnnumber) {	this.startcolumnnumber = startcolumnnumber;	}
	public String getstartcolumnnumber() {	return startcolumnnumber;	}
	public void setvisible(String visible) {	this.visible = visible;	}
	public String getvisible() {	return visible;	}
	public void setvisiblename(String visiblename) {	this.visiblename = visiblename;	}
	public String getvisiblename() {	return visiblename;	}
	public void setcolumnwidth(String columnwidth) {	this.columnwidth = columnwidth;	}
	public String getcolumnwidth() {	return columnwidth;	}
	public void sethorizontalalignment(String horizontalalignment) {	this.horizontalalignment = horizontalalignment;	}
	public String gethorizontalalignment() {	return horizontalalignment;	}
	public void sethorizontalalignmentname(String horizontalalignmentname) {	this.horizontalalignmentname = horizontalalignmentname;	}
	public String gethorizontalalignmentname() {	return horizontalalignmentname;	}
	public void setnumberformatlocal(String numberformatlocal) {	this.numberformatlocal = numberformatlocal;	}
	public String getnumberformatlocal() {	return numberformatlocal;	}
	public void setiftotal(String iftotal) {	this.iftotal = iftotal;	}
	public String getiftotal() {	return iftotal;	}
	public void setpage(Integer page) {	this.page = page;	}
	public Integer getpage() {	return page;	}
	public void setrows(Integer rows) {	this.rows = rows;	}
	public Integer getrows() {	return rows;	}
	public void setSort(String sort) { this.sort = sort; }
	public String getSort() { return sort; }
	public void setOrder(String order) { this.order = order; }
	public String getOrder() { return order; }
}
