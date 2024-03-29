package com.ytxd.model.SystemManage;
import com.ytxd.model.BaseBO;
import com.ytxd.util.StringUtil;
public class SM_ExcelCollect extends BaseBO {
	private static final long serialVersionUID = 1L;
	private String copyformat;  //
	private String id;  //
	private String exportname;  //报表标识
	private String description;  //报表名称
	private String sheetname;  //ExcelSheetName
	private String endrownumber;  //开始行号
	private String rownumber;  //开始行号
	private String startrownumber;  //开始行号
	private String columncount;  //列数
	private String endcolumncount;  //列数
	private String startcolumncount;  //列数
	private String templatefile;  //模板文件
	private String auto;  //是否自动
	private String autoname;  //是否自动

	/**
	 * 
	 * @param copyformat
	 */
	public void setcopyformat(String copyformat) {	this.copyformat = copyformat;	}
	/**
	 * 
	 * @return String
	 */
	public String getcopyformat() {	return copyformat;	}
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
	 * 报表标识
	 * @param exportname
	 */
	public void setexportname(String exportname) {	this.exportname = exportname;	}
	/**
	 * 报表标识
	 * @return String
	 */
	public String getexportname() {	return exportname;	}
	/**
	 * 报表名称
	 * @param description
	 */
	public void setdescription(String description) {	this.description = description;	}
	/**
	 * 报表名称
	 * @return String
	 */
	public String getdescription() {	return description;	}
	/**
	 * ExcelSheetName
	 * @param sheetname
	 */
	public void setsheetname(String sheetname) {	this.sheetname = sheetname;	}
	/**
	 * ExcelSheetName
	 * @return String
	 */
	public String getsheetname() {	return sheetname;	}
	/**
	 * 开始行号
	 * @param endrownumber
	 */
	public void setendrownumber(String endrownumber) {	this.endrownumber = endrownumber;	}
	/**
	 * 开始行号
	 * @return String
	 */
	public String getendrownumber() {	return endrownumber;	}
	/**
	 * 开始行号
	 * @param rownumber
	 */
	public void setrownumber(String rownumber) {	this.rownumber = rownumber;	}
	/**
	 * 开始行号
	 * @return String
	 */
	public String getrownumber() {	return rownumber;	}
	/**
	 * 开始行号
	 * @param startrownumber
	 */
	public void setstartrownumber(String startrownumber) {	this.startrownumber = startrownumber;	}
	/**
	 * 开始行号
	 * @return String
	 */
	public String getstartrownumber() {	return startrownumber;	}
	/**
	 * 列数
	 * @param columncount
	 */
	public void setcolumncount(String columncount) {	this.columncount = columncount;	}
	/**
	 * 列数
	 * @return String
	 */
	public String getcolumncount() {	return columncount;	}
	/**
	 * 列数
	 * @param endcolumncount
	 */
	public void setendcolumncount(String endcolumncount) {	this.endcolumncount = endcolumncount;	}
	/**
	 * 列数
	 * @return String
	 */
	public String getendcolumncount() {	return endcolumncount;	}
	/**
	 * 列数
	 * @param startcolumncount
	 */
	public void setstartcolumncount(String startcolumncount) {	this.startcolumncount = startcolumncount;	}
	/**
	 * 列数
	 * @return String
	 */
	public String getstartcolumncount() {	return startcolumncount;	}
	/**
	 * 模板文件
	 * @param templatefile
	 */
	public void settemplatefile(String templatefile) {	this.templatefile = templatefile;	}
	/**
	 * 模板文件
	 * @return String
	 */
	public String gettemplatefile() {	return templatefile;	}
	/**
	 * 是否自动
	 * @param auto
	 */
	public void setauto(String auto) {	this.auto = auto;	}
	/**
	 * 是否自动
	 * @return String
	 */
	public String getauto() {	return auto;	}
	/**
	 * 是否自动
	 * @param autoname
	 */
	public void setautoname(String autoname) {	this.autoname = autoname;	}
	/**
	 * 是否自动
	 * @return String
	 */
	public String getautoname() {	return autoname;	}

	public SM_ExcelCollect GetdecodeUtf(){
		super.GetdecodeUtf();
		try {
			String copyformat = this.getcopyformat();
			if(StringUtil.isNotEmpty(copyformat)){
				copyformat = java.net.URLDecoder.decode(copyformat,"UTF-8");
				this.setcopyformat(copyformat);
			}
			String id = this.getid();
			if(StringUtil.isNotEmpty(id)){
				id = java.net.URLDecoder.decode(id,"UTF-8");
				this.setid(id);
			}
			String exportname = this.getexportname();
			if(StringUtil.isNotEmpty(exportname)){
				exportname = java.net.URLDecoder.decode(exportname,"UTF-8");
				this.setexportname(exportname);
			}
			String description = this.getdescription();
			if(StringUtil.isNotEmpty(description)){
				description = java.net.URLDecoder.decode(description,"UTF-8");
				this.setdescription(description);
			}
			String sheetname = this.getsheetname();
			if(StringUtil.isNotEmpty(sheetname)){
				sheetname = java.net.URLDecoder.decode(sheetname,"UTF-8");
				this.setsheetname(sheetname);
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
			String columncount = this.getcolumncount();
			if(StringUtil.isNotEmpty(columncount)){
				columncount = java.net.URLDecoder.decode(columncount,"UTF-8");
				this.setcolumncount(columncount);
			}
			String endcolumncount = this.getendcolumncount();
			if(StringUtil.isNotEmpty(endcolumncount)){
				endcolumncount = java.net.URLDecoder.decode(endcolumncount,"UTF-8");
				this.setendcolumncount(endcolumncount);
			}
			String startcolumncount = this.getstartcolumncount();
			if(StringUtil.isNotEmpty(startcolumncount)){
				startcolumncount = java.net.URLDecoder.decode(startcolumncount,"UTF-8");
				this.setstartcolumncount(startcolumncount);
			}
			String templatefile = this.gettemplatefile();
			if(StringUtil.isNotEmpty(templatefile)){
				templatefile = java.net.URLDecoder.decode(templatefile,"UTF-8");
				this.settemplatefile(templatefile);
			}
			String auto = this.getauto();
			if(StringUtil.isNotEmpty(auto)){
				auto = java.net.URLDecoder.decode(auto,"UTF-8");
				this.setauto(auto);
			}
			String autoname = this.getautoname();
			if(StringUtil.isNotEmpty(autoname)){
				autoname = java.net.URLDecoder.decode(autoname,"UTF-8");
				this.setautoname(autoname);
			}
		} catch (Exception e) {
			System.out.println("中文转码出现问题");
		}
		return this;
	}
}
