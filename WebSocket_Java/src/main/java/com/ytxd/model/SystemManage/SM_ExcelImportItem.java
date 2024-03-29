package com.ytxd.model.SystemManage;
import com.ytxd.model.BaseBO;
import com.ytxd.util.StringUtil;
public class SM_ExcelImportItem extends BaseBO {
	private static final long serialVersionUID = 1L;
	private String id;  //
	private String importname;  //
	private String fieldname;  //字段名称
	private String excelcolumnname;  //Excel列名
	private String visible;  //是否可见
	private String visiblename;  //是否可见
	private String columnnumber;  //排序
	private String endcolumnnumber;  //排序
	private String startcolumnnumber;  //排序
	private String notnull;  //是否必填
	private String notnullname;  //是否必填
	private String fieldtype;  //字段类型
	private String fieldtypename;  //字段类型
	private String endfieldshortestlen;  //最短长度
	private String fieldshortestlen;  //最短长度
	private String startfieldshortestlen;  //最短长度
	private String endfieldlen;  //最长长度
	private String fieldlen;  //最长长度
	private String startfieldlen;  //最长长度
	private String codeid;  //CodeId
	private String codename;  //Code保存描述
	private String codenamename;  //Code保存描述
	private String onlyflag;  //唯一标识
	private String onlyflagname;  //唯一标识
	private String amuserimporttype;  //成果人员子表导入类型
	private String amuserimporttypename;  //成果人员子表导入类型

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
	 * 
	 * @param importname
	 */
	public void setimportname(String importname) {	this.importname = importname;	}
	/**
	 * 
	 * @return String
	 */
	public String getimportname() {	return importname;	}
	/**
	 * 字段名称
	 * @param fieldname
	 */
	public void setfieldname(String fieldname) {	this.fieldname = fieldname;	}
	/**
	 * 字段名称
	 * @return String
	 */
	public String getfieldname() {	return fieldname;	}
	/**
	 * Excel列名
	 * @param excelcolumnname
	 */
	public void setexcelcolumnname(String excelcolumnname) {	this.excelcolumnname = excelcolumnname;	}
	/**
	 * Excel列名
	 * @return String
	 */
	public String getexcelcolumnname() {	return excelcolumnname;	}
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
	 * 排序
	 * @param columnnumber
	 */
	public void setcolumnnumber(String columnnumber) {	this.columnnumber = columnnumber;	}
	/**
	 * 排序
	 * @return String
	 */
	public String getcolumnnumber() {	return columnnumber;	}
	/**
	 * 排序
	 * @param endcolumnnumber
	 */
	public void setendcolumnnumber(String endcolumnnumber) {	this.endcolumnnumber = endcolumnnumber;	}
	/**
	 * 排序
	 * @return String
	 */
	public String getendcolumnnumber() {	return endcolumnnumber;	}
	/**
	 * 排序
	 * @param startcolumnnumber
	 */
	public void setstartcolumnnumber(String startcolumnnumber) {	this.startcolumnnumber = startcolumnnumber;	}
	/**
	 * 排序
	 * @return String
	 */
	public String getstartcolumnnumber() {	return startcolumnnumber;	}
	/**
	 * 是否必填
	 * @param notnull
	 */
	public void setnotnull(String notnull) {	this.notnull = notnull;	}
	/**
	 * 是否必填
	 * @return String
	 */
	public String getnotnull() {	return notnull;	}
	/**
	 * 是否必填
	 * @param notnullname
	 */
	public void setnotnullname(String notnullname) {	this.notnullname = notnullname;	}
	/**
	 * 是否必填
	 * @return String
	 */
	public String getnotnullname() {	return notnullname;	}
	/**
	 * 字段类型
	 * @param fieldtype
	 */
	public void setfieldtype(String fieldtype) {	this.fieldtype = fieldtype;	}
	/**
	 * 字段类型
	 * @return String
	 */
	public String getfieldtype() {	return fieldtype;	}
	/**
	 * 字段类型
	 * @param fieldtypename
	 */
	public void setfieldtypename(String fieldtypename) {	this.fieldtypename = fieldtypename;	}
	/**
	 * 字段类型
	 * @return String
	 */
	public String getfieldtypename() {	return fieldtypename;	}
	/**
	 * 最短长度
	 * @param endfieldshortestlen
	 */
	public void setendfieldshortestlen(String endfieldshortestlen) {	this.endfieldshortestlen = endfieldshortestlen;	}
	/**
	 * 最短长度
	 * @return String
	 */
	public String getendfieldshortestlen() {	return endfieldshortestlen;	}
	/**
	 * 最短长度
	 * @param fieldshortestlen
	 */
	public void setfieldshortestlen(String fieldshortestlen) {	this.fieldshortestlen = fieldshortestlen;	}
	/**
	 * 最短长度
	 * @return String
	 */
	public String getfieldshortestlen() {	return fieldshortestlen;	}
	/**
	 * 最短长度
	 * @param startfieldshortestlen
	 */
	public void setstartfieldshortestlen(String startfieldshortestlen) {	this.startfieldshortestlen = startfieldshortestlen;	}
	/**
	 * 最短长度
	 * @return String
	 */
	public String getstartfieldshortestlen() {	return startfieldshortestlen;	}
	/**
	 * 最长长度
	 * @param endfieldlen
	 */
	public void setendfieldlen(String endfieldlen) {	this.endfieldlen = endfieldlen;	}
	/**
	 * 最长长度
	 * @return String
	 */
	public String getendfieldlen() {	return endfieldlen;	}
	/**
	 * 最长长度
	 * @param fieldlen
	 */
	public void setfieldlen(String fieldlen) {	this.fieldlen = fieldlen;	}
	/**
	 * 最长长度
	 * @return String
	 */
	public String getfieldlen() {	return fieldlen;	}
	/**
	 * 最长长度
	 * @param startfieldlen
	 */
	public void setstartfieldlen(String startfieldlen) {	this.startfieldlen = startfieldlen;	}
	/**
	 * 最长长度
	 * @return String
	 */
	public String getstartfieldlen() {	return startfieldlen;	}
	/**
	 * CodeId
	 * @param codeid
	 */
	public void setcodeid(String codeid) {	this.codeid = codeid;	}
	/**
	 * CodeId
	 * @return String
	 */
	public String getcodeid() {	return codeid;	}
	/**
	 * Code保存描述
	 * @param codename
	 */
	public void setcodename(String codename) {	this.codename = codename;	}
	/**
	 * Code保存描述
	 * @return String
	 */
	public String getcodename() {	return codename;	}
	/**
	 * Code保存描述
	 * @param codenamename
	 */
	public void setcodenamename(String codenamename) {	this.codenamename = codenamename;	}
	/**
	 * Code保存描述
	 * @return String
	 */
	public String getcodenamename() {	return codenamename;	}
	/**
	 * 唯一标识
	 * @param onlyflag
	 */
	public void setonlyflag(String onlyflag) {	this.onlyflag = onlyflag;	}
	/**
	 * 唯一标识
	 * @return String
	 */
	public String getonlyflag() {	return onlyflag;	}
	/**
	 * 唯一标识
	 * @param onlyflagname
	 */
	public void setonlyflagname(String onlyflagname) {	this.onlyflagname = onlyflagname;	}
	/**
	 * 唯一标识
	 * @return String
	 */
	public String getonlyflagname() {	return onlyflagname;	}
	/**
	 * 成果人员子表导入类型
	 * @param amuserimporttype
	 */
	public void setamuserimporttype(String amuserimporttype) {	this.amuserimporttype = amuserimporttype;	}
	/**
	 * 成果人员子表导入类型
	 * @return String
	 */
	public String getamuserimporttype() {	return amuserimporttype;	}
	/**
	 * 成果人员子表导入类型
	 * @param amuserimporttypename
	 */
	public void setamuserimporttypename(String amuserimporttypename) {	this.amuserimporttypename = amuserimporttypename;	}
	/**
	 * 成果人员子表导入类型
	 * @return String
	 */
	public String getamuserimporttypename() {	return amuserimporttypename;	}

	public SM_ExcelImportItem GetdecodeUtf(){
		super.GetdecodeUtf();
		try {
			String id = this.getid();
			if(StringUtil.isNotEmpty(id)){
				id = java.net.URLDecoder.decode(id,"UTF-8");
				this.setid(id);
			}
			String importname = this.getimportname();
			if(StringUtil.isNotEmpty(importname)){
				importname = java.net.URLDecoder.decode(importname,"UTF-8");
				this.setimportname(importname);
			}
			String fieldname = this.getfieldname();
			if(StringUtil.isNotEmpty(fieldname)){
				fieldname = java.net.URLDecoder.decode(fieldname,"UTF-8");
				this.setfieldname(fieldname);
			}
			String excelcolumnname = this.getexcelcolumnname();
			if(StringUtil.isNotEmpty(excelcolumnname)){
				excelcolumnname = java.net.URLDecoder.decode(excelcolumnname,"UTF-8");
				this.setexcelcolumnname(excelcolumnname);
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
			String notnull = this.getnotnull();
			if(StringUtil.isNotEmpty(notnull)){
				notnull = java.net.URLDecoder.decode(notnull,"UTF-8");
				this.setnotnull(notnull);
			}
			String notnullname = this.getnotnullname();
			if(StringUtil.isNotEmpty(notnullname)){
				notnullname = java.net.URLDecoder.decode(notnullname,"UTF-8");
				this.setnotnullname(notnullname);
			}
			String fieldtype = this.getfieldtype();
			if(StringUtil.isNotEmpty(fieldtype)){
				fieldtype = java.net.URLDecoder.decode(fieldtype,"UTF-8");
				this.setfieldtype(fieldtype);
			}
			String fieldtypename = this.getfieldtypename();
			if(StringUtil.isNotEmpty(fieldtypename)){
				fieldtypename = java.net.URLDecoder.decode(fieldtypename,"UTF-8");
				this.setfieldtypename(fieldtypename);
			}
			String endfieldshortestlen = this.getendfieldshortestlen();
			if(StringUtil.isNotEmpty(endfieldshortestlen)){
				endfieldshortestlen = java.net.URLDecoder.decode(endfieldshortestlen,"UTF-8");
				this.setendfieldshortestlen(endfieldshortestlen);
			}
			String fieldshortestlen = this.getfieldshortestlen();
			if(StringUtil.isNotEmpty(fieldshortestlen)){
				fieldshortestlen = java.net.URLDecoder.decode(fieldshortestlen,"UTF-8");
				this.setfieldshortestlen(fieldshortestlen);
			}
			String startfieldshortestlen = this.getstartfieldshortestlen();
			if(StringUtil.isNotEmpty(startfieldshortestlen)){
				startfieldshortestlen = java.net.URLDecoder.decode(startfieldshortestlen,"UTF-8");
				this.setstartfieldshortestlen(startfieldshortestlen);
			}
			String endfieldlen = this.getendfieldlen();
			if(StringUtil.isNotEmpty(endfieldlen)){
				endfieldlen = java.net.URLDecoder.decode(endfieldlen,"UTF-8");
				this.setendfieldlen(endfieldlen);
			}
			String fieldlen = this.getfieldlen();
			if(StringUtil.isNotEmpty(fieldlen)){
				fieldlen = java.net.URLDecoder.decode(fieldlen,"UTF-8");
				this.setfieldlen(fieldlen);
			}
			String startfieldlen = this.getstartfieldlen();
			if(StringUtil.isNotEmpty(startfieldlen)){
				startfieldlen = java.net.URLDecoder.decode(startfieldlen,"UTF-8");
				this.setstartfieldlen(startfieldlen);
			}
			String codeid = this.getcodeid();
			if(StringUtil.isNotEmpty(codeid)){
				codeid = java.net.URLDecoder.decode(codeid,"UTF-8");
				this.setcodeid(codeid);
			}
			String codename = this.getcodename();
			if(StringUtil.isNotEmpty(codename)){
				codename = java.net.URLDecoder.decode(codename,"UTF-8");
				this.setcodename(codename);
			}
			String codenamename = this.getcodenamename();
			if(StringUtil.isNotEmpty(codenamename)){
				codenamename = java.net.URLDecoder.decode(codenamename,"UTF-8");
				this.setcodenamename(codenamename);
			}
			String onlyflag = this.getonlyflag();
			if(StringUtil.isNotEmpty(onlyflag)){
				onlyflag = java.net.URLDecoder.decode(onlyflag,"UTF-8");
				this.setonlyflag(onlyflag);
			}
			String onlyflagname = this.getonlyflagname();
			if(StringUtil.isNotEmpty(onlyflagname)){
				onlyflagname = java.net.URLDecoder.decode(onlyflagname,"UTF-8");
				this.setonlyflagname(onlyflagname);
			}
			String amuserimporttype = this.getamuserimporttype();
			if(StringUtil.isNotEmpty(amuserimporttype)){
				amuserimporttype = java.net.URLDecoder.decode(amuserimporttype,"UTF-8");
				this.setamuserimporttype(amuserimporttype);
			}
			String amuserimporttypename = this.getamuserimporttypename();
			if(StringUtil.isNotEmpty(amuserimporttypename)){
				amuserimporttypename = java.net.URLDecoder.decode(amuserimporttypename,"UTF-8");
				this.setamuserimporttypename(amuserimporttypename);
			}
		} catch (Exception e) {
			System.out.println("中文转码出现问题");
		}
		return this;
	}
}
