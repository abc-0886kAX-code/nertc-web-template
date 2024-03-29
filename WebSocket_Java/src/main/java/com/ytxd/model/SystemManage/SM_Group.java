package com.ytxd.model.SystemManage;

import com.ytxd.model.BaseBO;
import com.ytxd.util.StringUtil;

public class SM_Group extends BaseBO  {
	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = -8113075447772822836L;
	private String id;	//分组自增id
	private String name;	//小组名称
	private String status;	//小组使用状态
	private String statusname;	//小组使用状态
	private String remark;  //小组简介
	private String orderid;//小组排序
	
	private String submitman;  //录入人
	private String submitmanname;  //录入人
	private String endsubmittime;  //录入日期
	private String startsubmittime;  //录入日期
	private String submittime;  //录入日期
	private String yearsubmittime;  //录入日期
	/*
	 * SM_GROUP与SM_USERS 关系表
	 */
	private String groupid;//小组id
	private String userid;//小组成员id
	
	//封装树型数据
	private String cptr;  //是否含有子集00含有子集01为无
	private String parentid;  //父级ID
	private String checked;	//是否选中
	private String haschildren;	//是否有子节点
	
	public String getid() {
		return id;
	}
	public void setid(String id) {
		this.id = id;
		if(StringUtil.isNotEmpty(id)){this.setidarray(id.replace("'", "").split(","));}
	}
	public String getname() {
		return name;
	}
	public void setname(String name) {
		this.name = name;
	}
	public String getstatus() {
		return status;
	}
	public void setstatus(String status) {
		this.status = status;
	}
	public String getstatusname() {
		return statusname;
	}
	public void setstatusname(String statusname) {
		this.statusname = statusname;
	}
	
	/**
	 * 小组简介

	 * @param remark
	 */
	public void setremark(String remark) {	this.remark = remark;	}
	/**
	 * 小组简介

	 * @return String
	 */
	public String getremark() {	return remark;	}
	/**
	 * 小组排序

	 * @param orderid
	 */
	public void setorderid(String orderid) {	this.orderid = orderid;	}
	/**
	 * 小组排序

	 * @return String
	 */
	public String getorderid() {	return orderid;	}
	/**
	 * 录入人

	 * @param submitman
	 */
	public void setsubmitman(String submitman) {	this.submitman = submitman;	}
	/**
	 * 录入人

	 * @return String
	 */
	public String getsubmitman() {	return submitman;	}
	/**
	 * 录入人

	 * @param submitmanname
	 */
	public void setsubmitmanname(String submitmanname) {	this.submitmanname = submitmanname;	}
	/**
	 * 录入人

	 * @return String
	 */
	public String getsubmitmanname() {	return submitmanname;	}
	/**
	 * 录入日期
	 * @param endsubmittime
	 */
	public void setendsubmittime(String endsubmittime) {	this.endsubmittime = endsubmittime;	}
	/**
	 * 录入日期
	 * @return String
	 */
	public String getendsubmittime() {	return endsubmittime;	}
	/**
	 * 录入日期
	 * @param startsubmittime
	 */
	public void setstartsubmittime(String startsubmittime) {	this.startsubmittime = startsubmittime;	}
	/**
	 * 录入日期
	 * @return String
	 */
	public String getstartsubmittime() {	return startsubmittime;	}
	/**
	 * 录入日期
	 * @param submittime
	 */
	public void setsubmittime(String submittime) {	this.submittime = submittime;	}
	/**
	 * 录入日期
	 * @return String
	 */
	public String getsubmittime() {	return submittime;	}
	/**
	 * 录入日期
	 * @param yearsubmittime
	 */
	public void setyearsubmittime(String yearsubmittime) {	this.yearsubmittime = yearsubmittime;	}
	/**
	 * 录入日期
	 * @return String
	 */
	public String getyearsubmittime() {	return yearsubmittime;	}
	
	public String getgroupid() {
		return groupid;
	}
	public void setgroupid(String groupid) {
		this.groupid = groupid;
	}
	public String getuserid() {
		return userid;
	}
	public void setuserid(String userid) {
		this.userid = userid;
	}
	
	/**
	 * 是否含有子集00含有子集01为无
	 * @param cptr
	 */
	public void setcptr(String cptr) {	this.cptr = cptr;	}
	/**
	 * 是否含有子集00含有子集01为无
	 * @return String
	 */
	public String getcptr() {	return cptr;	}
	/**
	 * 父级ID
	 * @param parentid
	 */
	public void setparentid(String parentid) {	this.parentid = parentid;	}
	/**
	 * 父级ID
	 * @return String
	 */
	public String getparentid() {	return parentid;	}
	/**
	 * 是否选中
	 * @param checked
	 */
	public void setchecked(String checked) {
		this.checked = checked;
	}
	/**
	 * 是否选中
	 * @return String
	 */
	public String getchecked() {
		return checked;
	}
	/**
	 * 是否有子节点
	 * @param haschildren
	 */
	public void sethaschildren(String haschildren) {	this.haschildren = haschildren;	}
	/**
	 * 是否有子节点
	 * @return String
	 */
	public String gethaschildren() {	return haschildren;	}
	
	public SM_Group GetdecodeUtf(){
		super.GetdecodeUtf();
		try {
			String id = this.getid();
			if(StringUtil.isNotEmpty(id)){
				id = java.net.URLDecoder.decode(id,"UTF-8");
				this.setid(id);
			}
			String name = this.getname();
			if(StringUtil.isNotEmpty(name)){
				name = java.net.URLDecoder.decode(name,"UTF-8");
				this.setname(name);
			}
			String status = this.getstatus();
			if(StringUtil.isNotEmpty(status)){
				status = java.net.URLDecoder.decode(status,"UTF-8");
				this.setstatus(status);
			}
			String remark = this.getremark();
			if(StringUtil.isNotEmpty(remark)){
				remark = java.net.URLDecoder.decode(remark,"UTF-8");
				this.setremark(remark);
			}
			String submitman = this.getsubmitman();
			if(StringUtil.isNotEmpty(submitman)){
				submitman = java.net.URLDecoder.decode(submitman,"UTF-8");
				this.setsubmitman(submitman);
			}
			String submitmanname = this.getsubmitmanname();
			if(StringUtil.isNotEmpty(submitmanname)){
				submitmanname = java.net.URLDecoder.decode(submitmanname,"UTF-8");
				this.setsubmitmanname(submitmanname);
			}
			String endsubmittime = this.getendsubmittime();
			if(StringUtil.isNotEmpty(endsubmittime)){
				endsubmittime = java.net.URLDecoder.decode(endsubmittime,"UTF-8");
				this.setendsubmittime(endsubmittime);
			}
			String startsubmittime = this.getstartsubmittime();
			if(StringUtil.isNotEmpty(startsubmittime)){
				startsubmittime = java.net.URLDecoder.decode(startsubmittime,"UTF-8");
				this.setstartsubmittime(startsubmittime);
			}
			String submittime = this.getsubmittime();
			if(StringUtil.isNotEmpty(submittime)){
				submittime = java.net.URLDecoder.decode(submittime,"UTF-8");
				this.setsubmittime(submittime);
			}
			String yearsubmittime = this.getyearsubmittime();
			if(StringUtil.isNotEmpty(yearsubmittime)){
				yearsubmittime = java.net.URLDecoder.decode(yearsubmittime,"UTF-8");
				this.setyearsubmittime(yearsubmittime);
			}
			String groupid = this.getgroupid();
			if(StringUtil.isNotEmpty(groupid)){
				groupid = java.net.URLDecoder.decode(groupid,"UTF-8");
				this.setgroupid(groupid);
			}
			String userid = this.getuserid();
			if(StringUtil.isNotEmpty(userid)){
				userid = java.net.URLDecoder.decode(userid,"UTF-8");
				this.setuserid(userid);
			}
			String haschildren = this.gethaschildren();
			if(StringUtil.isNotEmpty(haschildren)){
				haschildren = java.net.URLDecoder.decode(haschildren,"UTF-8");
				this.sethaschildren(haschildren);
			}
		} catch (Exception e) {
			System.out.println("中文转码出现问题");
		}
		return this;
	}
	
}
