package com.ytxd.model.SystemManage;

import com.ytxd.model.BaseBO;
import com.ytxd.util.StringUtil;

public class SM_Users_Combobox extends BaseBO {
	private static final long serialVersionUID = 1L;
	private String userid;  //自增长ID
	private String truename;  //姓名
	private String jobno;  //工号
	private String departmentidname;  //所在单位

	/**
	 * 自增长ID
	 * @param userid
	 */
	public void setuserid(String userid) {	this.userid = userid; this.setidarray(userid.replace("'", "").split(",")); }
	/**
	 * 自增长ID
	 * @return String
	 */
	public String getuserid() {	return userid;	}
	/**
	 * 姓名
	 * @param truename
	 */
	public void settruename(String truename) {	this.truename = truename;	}
	/**
	 * 姓名
	 * @return String
	 */
	public String gettruename() {	return truename;	}
	/**
	 * 工号
	 * @param jobno
	 */
	public void setjobno(String jobno) {	this.jobno = jobno;	}
	/**
	 * 工号
	 * @return String
	 */
	public String getjobno() {	return jobno;	}
	/**
	 * 所在单位
	 * @param departmentidname
	 */
	public void setdepartmentidname(String departmentidname) {	this.departmentidname = departmentidname;	}
	/**
	 * 所在单位
	 * @return String
	 */
	public String getdepartmentidname() {	return departmentidname;	}

	public SM_Users_Combobox GetdecodeUtf(){
		try {
			String truename = this.gettruename();
			if(StringUtil.isNotEmpty(truename)){
				truename = java.net.URLDecoder.decode(truename,"UTF-8");
				this.settruename(truename);
			}
			String jobno = this.getjobno();
			if(StringUtil.isNotEmpty(jobno)){
				jobno = java.net.URLDecoder.decode(jobno,"UTF-8");
				this.setjobno(jobno);
			}
		} catch (Exception e) {
			System.out.println("中文转码出现问题");
		}
		return this;
	}
}
