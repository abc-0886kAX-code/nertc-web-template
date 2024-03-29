package com.ytxd.model.SystemManage;
import com.ytxd.model.BaseBO;
import com.ytxd.util.StringUtil;
public class SM_ExcelItem extends BaseBO {
	private static final long serialVersionUID = 1L;
	private String exportname;  //
	private String id;  //
	private String iftotal;  //合计
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

	/**
	 * 
	 * @param exportname
	 */
	public void setexportname(String exportname) {	this.exportname = exportname;	}
	/**
	 * 
	 * @return String
	 */
	public String getexportname() {	return exportname;	}
	/**
	 * 
	 * @param id
	 */
	public void setid(String id) {	this.id = id;if(StringUtil.isNotEmpty(id)){this.setidarray(id.replace("'", "").split(","));}	}
	/**
	 * 
	 * @return String
	 */
	public String getid() {	return id;	}
	/**
	 * 合计
	 * @param iftotal
	 */
	public void setiftotal(String iftotal) {	this.iftotal = iftotal;	}
	/**
	 * 合计
	 * @return String
	 */
	public String getiftotal() {	return iftotal;	}
	/**
	 * 数据源列名
	 * @param sourcecolumnname
	 */
	public void setsourcecolumnname(String sourcecolumnname) {	this.sourcecolumnname = sourcecolumnname;	}
	/**
	 * 数据源列名
	 * @return String
	 */
	public String getsourcecolumnname() {	return sourcecolumnname;	}
	/**
	 * Excel导出列名
	 * @param excelcolumnname
	 */
	public void setexcelcolumnname(String excelcolumnname) {	this.excelcolumnname = excelcolumnname;	}
	/**
	 * Excel导出列名
	 * @return String
	 */
	public String getexcelcolumnname() {	return excelcolumnname;	}
	/**
	 * 行号
	 * @param endrownumber
	 */
	public void setendrownumber(String endrownumber) {	this.endrownumber = endrownumber;	}
	/**
	 * 行号
	 * @return String
	 */
	public String getendrownumber() {	return endrownumber;	}
	/**
	 * 行号
	 * @param rownumber
	 */
	public void setrownumber(String rownumber) {	this.rownumber = rownumber;	}
	/**
	 * 行号
	 * @return String
	 */
	public String getrownumber() {	return rownumber;	}
	/**
	 * 行号
	 * @param startrownumber
	 */
	public void setstartrownumber(String startrownumber) {	this.startrownumber = startrownumber;	}
	/**
	 * 行号
	 * @return String
	 */
	public String getstartrownumber() {	return startrownumber;	}
	/**
	 * 列号
	 * @param columnnumber
	 */
	public void setcolumnnumber(String columnnumber) {	this.columnnumber = columnnumber;	}
	/**
	 * 列号
	 * @return String
	 */
	public String getcolumnnumber() {	return columnnumber;	}
	/**
	 * 列号
	 * @param endcolumnnumber
	 */
	public void setendcolumnnumber(String endcolumnnumber) {	this.endcolumnnumber = endcolumnnumber;	}
	/**
	 * 列号
	 * @return String
	 */
	public String getendcolumnnumber() {	return endcolumnnumber;	}
	/**
	 * 列号
	 * @param startcolumnnumber
	 */
	public void setstartcolumnnumber(String startcolumnnumber) {	this.startcolumnnumber = startcolumnnumber;	}
	/**
	 * 列号
	 * @return String
	 */
	public String getstartcolumnnumber() {	return startcolumnnumber;	}
	/**
	 * 是否可见
	 * @param visible
	 */
	public void setvisible(String visible) {	this.visible = visible;	}
	/**
	 * 是否可见
	 * @return String
	 */
	public String getvisible() {	return visible;	}
	/**
	 * 是否可见
	 * @param visiblename
	 */
	public void setvisiblename(String visiblename) {	this.visiblename = visiblename;	}
	/**
	 * 是否可见
	 * @return String
	 */
	public String getvisiblename() {	return visiblename;	}
	/**
	 * 列宽
	 * @param columnwidth
	 */
	public void setcolumnwidth(String columnwidth) {	this.columnwidth = columnwidth;	}
	/**
	 * 列宽
	 * @return String
	 */
	public String getcolumnwidth() {	return columnwidth;	}
	/**
	 * 对齐方式
	 * @param horizontalalignment
	 */
	public void sethorizontalalignment(String horizontalalignment) {	this.horizontalalignment = horizontalalignment;	}
	/**
	 * 对齐方式
	 * @return String
	 */
	public String gethorizontalalignment() {	return horizontalalignment;	}
	/**
	 * 对齐方式
	 * @param horizontalalignmentname
	 */
	public void sethorizontalalignmentname(String horizontalalignmentname) {	this.horizontalalignmentname = horizontalalignmentname;	}
	/**
	 * 对齐方式
	 * @return String
	 */
	public String gethorizontalalignmentname() {	return horizontalalignmentname;	}
	/**
	 * 格式
	 * @param numberformatlocal
	 */
	public void setnumberformatlocal(String numberformatlocal) {	this.numberformatlocal = numberformatlocal;	}
	/**
	 * 格式
	 * @return String
	 */
	public String getnumberformatlocal() {	return numberformatlocal;	}

	public SM_ExcelItem GetdecodeUtf(){
		super.GetdecodeUtf();
		try {
			String exportname = this.getexportname();
			if(StringUtil.isNotEmpty(exportname)){
				exportname = java.net.URLDecoder.decode(exportname,"UTF-8");
				this.setexportname(exportname);
			}
			String id = this.getid();
			if(StringUtil.isNotEmpty(id)){
				id = java.net.URLDecoder.decode(id,"UTF-8");
				this.setid(id);
			}
			String iftotal = this.getiftotal();
			if(StringUtil.isNotEmpty(iftotal)){
				iftotal = java.net.URLDecoder.decode(iftotal,"UTF-8");
				this.setiftotal(iftotal);
			}
			String sourcecolumnname = this.getsourcecolumnname();
			if(StringUtil.isNotEmpty(sourcecolumnname)){
				sourcecolumnname = java.net.URLDecoder.decode(sourcecolumnname,"UTF-8");
				this.setsourcecolumnname(sourcecolumnname);
			}
			String excelcolumnname = this.getexcelcolumnname();
			if(StringUtil.isNotEmpty(excelcolumnname)){
				excelcolumnname = java.net.URLDecoder.decode(excelcolumnname,"UTF-8");
				this.setexcelcolumnname(excelcolumnname);
			}
			String endrownumber = this.getendrownumber();
			if(StringUtil.isNotEmpty(endrownumber)){
				endrownumber = java.net.URLDecoder.decode(endrownumber,"UTF-8");
				this.setendrownumber(endrownumber);
			}
			String rownumber = this.getrownumber();
			if(StringUtil.isNotEmpty(rownumber)){
				rownumber = java.net.URLDecoder.decode(rownumber,"UTF-8");
				this.setrownumber(rownumber);
			}
			String startrownumber = this.getstartrownumber();
			if(StringUtil.isNotEmpty(startrownumber)){
				startrownumber = java.net.URLDecoder.decode(startrownumber,"UTF-8");
				this.setstartrownumber(startrownumber);
			}
			String columnnumber = this.getcolumnnumber();
			if(StringUtil.isNotEmpty(columnnumber)){
				columnnumber = java.net.URLDecoder.decode(columnnumber,"UTF-8");
				this.setcolumnnumber(columnnumber);
			}
			String endcolumnnumber = this.getendcolumnnumber();
			if(StringUtil.isNotEmpty(endcolumnnumber)){
				endcolumnnumber = java.net.URLDecoder.decode(endcolumnnumber,"UTF-8");
				this.setendcolumnnumber(endcolumnnumber);
			}
			String startcolumnnumber = this.getstartcolumnnumber();
			if(StringUtil.isNotEmpty(startcolumnnumber)){
				startcolumnnumber = java.net.URLDecoder.decode(startcolumnnumber,"UTF-8");
				this.setstartcolumnnumber(startcolumnnumber);
			}
			String visible = this.getvisible();
			if(StringUtil.isNotEmpty(visible)){
				visible = java.net.URLDecoder.decode(visible,"UTF-8");
				this.setvisible(visible);
			}
			String visiblename = this.getvisiblename();
			if(StringUtil.isNotEmpty(visiblename)){
				visiblename = java.net.URLDecoder.decode(visiblename,"UTF-8");
				this.setvisiblename(visiblename);
			}
			String columnwidth = this.getcolumnwidth();
			if(StringUtil.isNotEmpty(columnwidth)){
				columnwidth = java.net.URLDecoder.decode(columnwidth,"UTF-8");
				this.setcolumnwidth(columnwidth);
			}
			String horizontalalignment = this.gethorizontalalignment();
			if(StringUtil.isNotEmpty(horizontalalignment)){
				horizontalalignment = java.net.URLDecoder.decode(horizontalalignment,"UTF-8");
				this.sethorizontalalignment(horizontalalignment);
			}
			String horizontalalignmentname = this.gethorizontalalignmentname();
			if(StringUtil.isNotEmpty(horizontalalignmentname)){
				horizontalalignmentname = java.net.URLDecoder.decode(horizontalalignmentname,"UTF-8");
				this.sethorizontalalignmentname(horizontalalignmentname);
			}
			String numberformatlocal = this.getnumberformatlocal();
			if(StringUtil.isNotEmpty(numberformatlocal)){
				numberformatlocal = java.net.URLDecoder.decode(numberformatlocal,"UTF-8");
				this.setnumberformatlocal(numberformatlocal);
			}
		} catch (Exception e) {
			System.out.println("中文转码出现问题");
		}
		return this;
	}
}
