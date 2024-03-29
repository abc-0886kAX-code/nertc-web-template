package com.ytxd.model.SystemManage;
import com.ytxd.model.BaseBO;
import com.ytxd.util.StringUtil;
public class SM_SystemSearch extends BaseBO {
	private static final long serialVersionUID = 1L;
	private String id;  //ID
	private String tablename;  //
	private String tablesearchname;  //
	private String selectstr;  //
	private String leftjoinstr;  //
	private String wherestr;  //
	private String listname;  //
	private String searchname;  //
	private String listdatakeynames;  //
	private String excelname;  //
	private String listorderfield;  //
	private String listorderway;  //
	private String viewselfwhere;  //
	private String viewdeptwhere;  //
	private String name;  //
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTablename() {
		return tablename;
	}
	public void setTablename(String tablename) {
		this.tablename = tablename;
	}
	public String getTablesearchname() {
		return tablesearchname;
	}
	public void setTablesearchname(String tablesearchname) {
		this.tablesearchname = tablesearchname;
	}
	public String getSelectstr() {
		return selectstr;
	}
	public void setSelectstr(String selectstr) {
		this.selectstr = selectstr;
	}
	public String getLeftjoinstr() {
		return leftjoinstr;
	}
	public void setLeftjoinstr(String leftjoinstr) {
		this.leftjoinstr = leftjoinstr;
	}
	public String getWherestr() {
		return wherestr;
	}
	public void setWherestr(String wherestr) {
		this.wherestr = wherestr;
	}
	public String getListname() {
		return listname;
	}
	public void setListname(String listname) {
		this.listname = listname;
	}
	public String getSearchname() {
		return searchname;
	}
	public void setSearchname(String searchname) {
		this.searchname = searchname;
	}
	public String getListdatakeynames() {
		return listdatakeynames;
	}
	public void setListdatakeynames(String listdatakeynames) {
		this.listdatakeynames = listdatakeynames;
	}
	public String getExcelname() {
		return excelname;
	}
	public void setExcelname(String excelname) {
		this.excelname = excelname;
	}
	public String getListorderfield() {
		return listorderfield;
	}
	public void setListorderfield(String listorderfield) {
		this.listorderfield = listorderfield;
	}
	public String getListorderway() {
		return listorderway;
	}
	public void setListorderway(String listorderway) {
		this.listorderway = listorderway;
	}
	public String getViewselfwhere() {
		return viewselfwhere;
	}
	public void setViewselfwhere(String viewselfwhere) {
		this.viewselfwhere = viewselfwhere;
	}
	public String getViewdeptwhere() {
		return viewdeptwhere;
	}
	public void setViewdeptwhere(String viewdeptwhere) {
		this.viewdeptwhere = viewdeptwhere;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	 
	
}
