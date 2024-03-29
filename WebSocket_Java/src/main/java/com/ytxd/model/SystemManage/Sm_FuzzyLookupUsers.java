package com.ytxd.model.SystemManage;

public class Sm_FuzzyLookupUsers {
	private static final long serialVersionUID = 15454646L;
	private String userid;  //自增长ID
	private String loginname;  //系统登录名
	private String truename;  //姓名
	//有参构造
	public Sm_FuzzyLookupUsers(String userid, String loginname, String truename) {
		super();
		this.userid = userid;
		this.loginname = loginname;
		this.truename = truename;
	}
	//无参构造
	public Sm_FuzzyLookupUsers() {
		super();
	}

	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getLoginname() {
		return loginname;
	}
	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}
	public String getTruename() {
		return truename;
	}
	public void setTruename(String truename) {
		this.truename = truename;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
