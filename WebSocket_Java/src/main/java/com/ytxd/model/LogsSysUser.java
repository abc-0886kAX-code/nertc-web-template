package com.ytxd.model;

import java.io.Serializable;

/**
 * 系统用户 登录模型
 * 
 */
public class LogsSysUser implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int id;
	/**
	 * 系统用户编号
	 */
	private Integer sysuserID;
	private String userName;
	/**
	 * 登入时间
	 */
	private String signinDate;

	/**
	 * 登出时间
	 */
	private String signoutDate;

	/**
	 * IP地址
	 */
	private String ip;

	/**
	 * 机器名称
	 */
	private String machineName;

	/**
	 * 用户代理 浏览器的信息
	 */
	private String userAgent;

	
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public LogsSysUser() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSigninDate() {
		return signinDate;
	}

	public void setSigninDate(String signinDate) {
		this.signinDate = signinDate;
	}

	public String getSignoutDate() {
		return signoutDate;
	}

	public void setSignoutDate(String signoutDate) {
		this.signoutDate = signoutDate;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getMachineName() {
		return machineName;
	}

	public void setMachineName(String machineName) {
		this.machineName = machineName;
	}

	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	public Integer getSysuserID() {
		return sysuserID;
	}

	public void setSysuserID(Integer sysuserID) {
		this.sysuserID = sysuserID;
	}

	@Override
	public String toString() {
		return "LogsSysUser [id=" + id + ", ip=" + ip + ", machineName="
				+ machineName + ", signinDate=" + signinDate + ", signoutDate="
				+ signoutDate + ", sysuserID=" + sysuserID + ", userAgent="
				+ userAgent + "]";
	}

}
