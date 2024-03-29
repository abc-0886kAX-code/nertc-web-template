package com.ytxd.model.SystemManage;
import com.ytxd.model.BaseBO;
import com.ytxd.util.StringUtil;

public class SM_Report extends BaseBO {

	private String id;  //
	private String type;  //
	private String tablename; // 
	private String descirption;  //
	private String firstfieldname;  //
	private String firstcodeid;  //
	private String secondfieldname;  //
	private String secondcodeid;  //
	private String totalfieldname;  //
	private String totalway;  //
	private String searchname;  //
	private String listname;  //
	private String excelname;  //
	private String ismake;  //
	private String orderid;  //
	private String submitman;  //
	private String submittime;  //
	private String visible;  //
	private String selectvalue;  //
	private String wherevalue;  //
	private String leftjoinvalue;  //
	private String groupbyvalue;  //
	private String orderbyvalue;  //
	private String viewurl;  //
	private String company;  //
	private String yaxis;  //
	private String reportsearchname;  //
	private String width;  //
	private String pointwidth;  //
	private String xmaxcount;  //
	private String chartstype;  //
	private String firstcodewhere;  //
	private String secondcodewhere;  //
	private String reporttype;  //
	private String typecolumnname;  //
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTablename() {
		return tablename;
	}
	public void setTablename(String tablename) {
		this.tablename = tablename;
	}
	public String getDescirption() {
		return descirption;
	}
	public void setDescirption(String descirption) {
		this.descirption = descirption;
	}
	public String getFirstfieldname() {
		return firstfieldname;
	}
	public void setFirstfieldname(String firstfieldname) {
		this.firstfieldname = firstfieldname;
	}
	public String getFirstcodeid() {
		return firstcodeid;
	}
	public void setFirstcodeid(String firstcodeId) {
		this.firstcodeid = firstcodeId;
	}
	public String getSecondfieldname() {
		return secondfieldname;
	}
	public void setSecondfieldname(String secondfieldname) {
		this.secondfieldname = secondfieldname;
	}
	public String getSecondcodeid() {
		return secondcodeid;
	}
	public void setSecondcodeid(String secondcodeId) {
		this.secondcodeid = secondcodeId;
	}
	public String getTotalfieldname() {
		return totalfieldname;
	}
	public void setTotalfieldname(String totalfieldname) {
		this.totalfieldname = totalfieldname;
	}
	public String getTotalway() {
		return totalway;
	}
	public void setTotalway(String totalway) {
		this.totalway = totalway;
	}
	public String getSearchname() {
		return searchname;
	}
	public void setSearchname(String searchname) {
		this.searchname = searchname;
	}
	public String getListname() {
		return listname;
	}
	public void setListname(String listname) {
		this.listname = listname;
	}
	public String getExcelname() {
		return excelname;
	}
	public void setExcelname(String excelname) {
		this.excelname = excelname;
	}
	public String getIsmake() {
		return ismake;
	}
	public void setIsmake(String ismake) {
		this.ismake = ismake;
	}
	public String getOrderid() {
		return orderid;
	}
	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}
	public String getSubmitman() {
		return submitman;
	}
	public void setSubmitman(String submitman) {
		this.submitman = submitman;
	}
	public String getSubmittime() {
		return submittime;
	}
	public void setSubmittime(String submittime) {
		this.submittime = submittime;
	}
	public String getVisible() {
		return visible;
	}
	public void setVisible(String visible) {
		this.visible = visible;
	}
	public String getSelectvalue() {
		return selectvalue;
	}
	public void setSelectvalue(String selectvalue) {
		this.selectvalue = selectvalue;
	}
	public String getWherevalue() {
		return wherevalue;
	}
	public void setWherevalue(String wherevalue) {
		this.wherevalue = wherevalue;
	}
	public String getLeftjoinvalue() {
		return leftjoinvalue;
	}
	public void setLeftjoinvalue(String leftjoinvalue) {
		this.leftjoinvalue = leftjoinvalue;
	}
	public String getGroupbyvalue() {
		return groupbyvalue;
	}
	public void setGroupbyvalue(String groupbyvalue) {
		this.groupbyvalue = groupbyvalue;
	}
	public String getOrderbyvalue() {
		return orderbyvalue;
	}
	public void setOrderbyvalue(String orderbyvalue) {
		this.orderbyvalue = orderbyvalue;
	}
	public String getViewurl() {
		return viewurl;
	}
	public void setViewurl(String viewurl) {
		this.viewurl = viewurl;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getYaxis() {
		return yaxis;
	}
	public void setYaxis(String yaxis) {
		this.yaxis = yaxis;
	}
	public String getReportsearchname() {
		return reportsearchname;
	}
	public void setReportsearchname(String reportsearchname) {
		this.reportsearchname = reportsearchname;
	}
	public String getWidth() {
		return width;
	}
	public void setWidth(String width) {
		this.width = width;
	}
	public String getPointwidth() {
		return pointwidth;
	}
	public void setPointwidth(String pointwidth) {
		this.pointwidth = pointwidth;
	}
	public String getXmaxcount() {
		return xmaxcount;
	}
	public void setXmaxcount(String xmaxcount) {
		this.xmaxcount = xmaxcount;
	}
	public String getChartstype() {
		return chartstype;
	}
	public void setChartstype(String chartstype) {
		this.chartstype = chartstype;
	}
	public String getFirstcodewhere() {
		return firstcodewhere;
	}
	public void setFirstcodewhere(String firstcodewhere) {
		this.firstcodewhere = firstcodewhere;
	}
	public String getSecondcodewhere() {
		return secondcodewhere;
	}
	public void setSecondcodewhere(String secondcodewhere) {
		this.secondcodewhere = secondcodewhere;
	}
	public String getReporttype() {
		return reporttype;
	}
	public void setReporttype(String reporttype) {
		this.reporttype = reporttype;
	}
	public String getTypecolumnname() {
		return typecolumnname;
	}
	public void setTypecolumnname(String typecolumnname) {
		this.typecolumnname = typecolumnname;
	}
	 
	 
}