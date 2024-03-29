package com.ytxd.model.SystemManage;
import com.ytxd.model.BaseBO;
import com.ytxd.util.StringUtil;
public class SM_ExcelImportCollect extends BaseBO {
	private static final long serialVersionUID = 1L;
	private String id;  //
	private String importname;  //导入名称
	private String description;  //导入描述
	private String tablename;  //导入表名
	private String importcheckresult;  //导入状态
	private String templatefile;  //模板文件
	private String remark;  //导入说明
	private String idfieldname;  //导入表Id名称

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
	 * 导入名称
	 * @param importname
	 */
	public void setimportname(String importname) {	this.importname = importname;	}
	/**
	 * 导入名称
	 * @return String
	 */
	public String getimportname() {	return importname;	}
	/**
	 * 导入描述
	 * @param description
	 */
	public void setdescription(String description) {	this.description = description;	}
	/**
	 * 导入描述
	 * @return String
	 */
	public String getdescription() {	return description;	}
	/**
	 * 导入表名
	 * @param tablename
	 */
	public void settablename(String tablename) {	this.tablename = tablename;	}
	/**
	 * 导入表名
	 * @return String
	 */
	public String gettablename() {	return tablename;	}
	/**
	 * 导入状态
	 * @param importcheckresult
	 */
	public void setimportcheckresult(String importcheckresult) {	this.importcheckresult = importcheckresult;	}
	/**
	 * 导入状态
	 * @return String
	 */
	public String getimportcheckresult() {	return importcheckresult;	}
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
	 * 导入说明
	 * @param remark
	 */
	public void setremark(String remark) {	this.remark = remark;	}
	/**
	 * 导入说明
	 * @return String
	 */
	public String getremark() {	return remark;	}
	/**
	 * 导入表Id名称
	 * @param idfieldname
	 */
	public void setidfieldname(String idfieldname) {	this.idfieldname = idfieldname;	}
	/**
	 * 导入表Id名称
	 * @return String
	 */
	public String getidfieldname() {	return idfieldname;	}

	public SM_ExcelImportCollect GetdecodeUtf(){
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
			String description = this.getdescription();
			if(StringUtil.isNotEmpty(description)){
				description = java.net.URLDecoder.decode(description,"UTF-8");
				this.setdescription(description);
			}
			String tablename = this.gettablename();
			if(StringUtil.isNotEmpty(tablename)){
				tablename = java.net.URLDecoder.decode(tablename,"UTF-8");
				this.settablename(tablename);
			}
			String importcheckresult = this.getimportcheckresult();
			if(StringUtil.isNotEmpty(importcheckresult)){
				importcheckresult = java.net.URLDecoder.decode(importcheckresult,"UTF-8");
				this.setimportcheckresult(importcheckresult);
			}
			String templatefile = this.gettemplatefile();
			if(StringUtil.isNotEmpty(templatefile)){
				templatefile = java.net.URLDecoder.decode(templatefile,"UTF-8");
				this.settemplatefile(templatefile);
			}
			String remark = this.getremark();
			if(StringUtil.isNotEmpty(remark)){
				remark = java.net.URLDecoder.decode(remark,"UTF-8");
				this.setremark(remark);
			}
			String idfieldname = this.getidfieldname();
			if(StringUtil.isNotEmpty(idfieldname)){
				idfieldname = java.net.URLDecoder.decode(idfieldname,"UTF-8");
				this.setidfieldname(idfieldname);
			}
		} catch (Exception e) {
			System.out.println("中文转码出现问题");
		}
		return this;
	}
}
