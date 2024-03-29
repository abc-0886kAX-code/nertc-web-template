package com.ytxd.model.SystemManage;
import com.ytxd.model.BaseBO;
import com.ytxd.util.StringUtil;
public class SM_Department_Dept extends BaseBO {
	private static final long serialVersionUID = 1L;
	private String id;  //
	private String departmentid;  //管理机构
	private String departmentidname;  //管理机构
	private String deptid;  //代管机构
	private String deptidname;  //代管机构

	/**
	 * 
	 * @param id
	 */
	public void setid(String id) {	this.id = id;	}
	/**
	 * 
	 * @return String
	 */
	public String getid() {	return id;	}
	/**
	 * 管理机构
	 * @param departmentid
	 */
	public void setdepartmentid(String departmentid) {	this.departmentid = departmentid;	}
	/**
	 * 管理机构
	 * @return String
	 */
	public String getdepartmentid() {	return departmentid;	}
	/**
	 * 管理机构
	 * @param departmentidname
	 */
	public void setdepartmentidname(String departmentidname) {	this.departmentidname = departmentidname;	}
	/**
	 * 管理机构
	 * @return String
	 */
	public String getdepartmentidname() {	return departmentidname;	}
	/**
	 * 代管机构
	 * @param deptid
	 */
	public void setdeptid(String deptid) {	this.deptid = deptid;	}
	/**
	 * 代管机构
	 * @return String
	 */
	public String getdeptid() {	return deptid;	}
	/**
	 * 代管机构
	 * @param deptidname
	 */
	public void setdeptidname(String deptidname) {	this.deptidname = deptidname;	}
	/**
	 * 代管机构
	 * @return String
	 */
	public String getdeptidname() {	return deptidname;	}

	public SM_Department_Dept GetdecodeUtf(){
		super.GetdecodeUtf();
		try {
			String id = this.getid();
			if(StringUtil.isNotEmpty(id)){
				id = java.net.URLDecoder.decode(id,"UTF-8");
				this.setid(id);
			}
			String departmentid = this.getdepartmentid();
			if(StringUtil.isNotEmpty(departmentid)){
				departmentid = java.net.URLDecoder.decode(departmentid,"UTF-8");
				this.setdepartmentid(departmentid);
			}
			String departmentidname = this.getdepartmentidname();
			if(StringUtil.isNotEmpty(departmentidname)){
				departmentidname = java.net.URLDecoder.decode(departmentidname,"UTF-8");
				this.setdepartmentidname(departmentidname);
			}
			String deptid = this.getdeptid();
			if(StringUtil.isNotEmpty(deptid)){
				deptid = java.net.URLDecoder.decode(deptid,"UTF-8");
				this.setdeptid(deptid);
			}
			String deptidname = this.getdeptidname();
			if(StringUtil.isNotEmpty(deptidname)){
				deptidname = java.net.URLDecoder.decode(deptidname,"UTF-8");
				this.setdeptidname(deptidname);
			}
		} catch (Exception e) {
			System.out.println("中文转码出现问题");
		}
		return this;
	}
}
