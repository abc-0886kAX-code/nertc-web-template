package com.ytxd.model.SystemManage;
import java.util.List;

import com.ytxd.model.BaseBO;
import com.ytxd.util.StringUtil;
public class SM_Route extends BaseBO {
	private static final long serialVersionUID = 1L;
	private String id;  //ID
	private String parentid;  //父级ID
	private String path;  //
	private String name;  //菜单名称
	private String redirect;  //权限标识
	private String compenent;  //链接网页
	private String title;  //子系统
	private String icon;  //图片地址
	private String hidden;  //是否可见
	private String hiddenname;  //是否可见
	private String endorderid;  //排序
	private String orderid;  //排序
	private String startorderid;  //排序
	private String haschildren;	//是否有子节点
	private String selectvalue;	//选择的值
	private List<SM_Route> children;
	private String userid;
	private String query;
	private String level;
	private String open;
	
	public String getOpen() {
		return open;
	}
	public void setOpen(String open) {
		this.open = open;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	private String checked;	//是否选中
	
	public String getChecked() {
		return checked;
	}
	public void setChecked(String checked) {
		this.checked = checked;
	}
	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public List<SM_Route> getChildren() {
		return children;
	}
	public void setChildren(List<SM_Route> children) {
		this.children = children;
	}
	/**
	 * ID
	 * @param id
	 */
	public void setid(String id) {	
		this.id = id;
		if(StringUtil.isNotEmpty(id)){
			this.setidarray(id.replace("'", "").split(","));
		}
	}
	/**
	 * ID
	 * @return String
	 */
	public String getid() {	return id;	}
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
	 * 
	 * @param path
	 */
	public void setpath(String path) {	this.path = path;	}
	/**
	 * 
	 * @return String
	 */
	public String getpath() {	return path;	}
	/**
	 * 菜单名称
	 * @param name
	 */
	public void setname(String name) {	this.name = name;	}
	/**
	 * 菜单名称
	 * @return String
	 */
	public String getname() {	return name;	}
	/**
	 * 权限标识
	 * @param redirect
	 */
	public void setredirect(String redirect) {	this.redirect = redirect;	}
	/**
	 * 权限标识
	 * @return String
	 */
	public String getredirect() {	return redirect;	}
	/**
	 * 链接网页
	 * @param compenent
	 */
	public void setcompenent(String compenent) {	this.compenent = compenent;	}
	/**
	 * 链接网页
	 * @return String
	 */
	public String getcompenent() {	return compenent;	}
	/**
	 * 子系统
	 * @param title
	 */
	public void settitle(String title) {	this.title = title;	}
	/**
	 * 子系统
	 * @return String
	 */
	public String gettitle() {	return title;	}
	
	/**
	 * 图片地址
	 * @param icon
	 */
	public void seticon(String icon) {	this.icon = icon;	}
	/**
	 * 图片地址
	 * @return String
	 */
	public String geticon() {	return icon;	}
	/**
	 * 是否可见
	 * @param hidden
	 */
	public void sethidden(String hidden) {	this.hidden = hidden;	}
	/**
	 * 是否可见
	 * @return String
	 */
	public String gethidden() {	return hidden;	}
	/**
	 * 是否可见
	 * @param hiddenname
	 */
	public void sethiddenname(String hiddenname) {	this.hiddenname = hiddenname;	}
	/**
	 * 是否可见
	 * @return String
	 */
	public String gethiddenname() {	return hiddenname;	}
	/**
	 * 排序
	 * @param endorderid
	 */
	public void setendorderid(String endorderid) {	this.endorderid = endorderid;	}
	/**
	 * 排序
	 * @return String
	 */
	public String getendorderid() {	return endorderid;	}
	/**
	 * 排序
	 * @param orderid
	 */
	public void setorderid(String orderid) {	this.orderid = orderid;	}
	/**
	 * 排序
	 * @return String
	 */
	public String getorderid() {	return orderid;	}
	/**
	 * 排序
	 * @param startorderid
	 */
	public void setstartorderid(String startorderid) {	this.startorderid = startorderid;	}
	/**
	 * 排序
	 * @return String
	 */
	public String getstartorderid() {	return startorderid;	}
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
	/**
	 * 选择的值

	 * @param selectvalue
	 */
	public void setselectvalue(String selectvalue) {	this.selectvalue = selectvalue;	}
	/**
	 * 选择的值

	 * @return String
	 */
	public String getselectvalue() {	return selectvalue;	}
	public SM_Route GetdecodeUtf(){
		super.GetdecodeUtf();
		try {
			String id = this.getid();
			if(StringUtil.isNotEmpty(id)){
				id = java.net.URLDecoder.decode(id,"UTF-8");
				this.setid(id);
			}
			String parentid = this.getparentid();
			if(StringUtil.isNotEmpty(parentid)){
				parentid = java.net.URLDecoder.decode(parentid,"UTF-8");
				this.setparentid(parentid);
			}
			String path = this.getpath();
			if(StringUtil.isNotEmpty(path)){
				path = java.net.URLDecoder.decode(path,"UTF-8");
				this.setpath(path);
			}
			String name = this.getname();
			if(StringUtil.isNotEmpty(name)){
				name = java.net.URLDecoder.decode(name,"UTF-8");
				this.setname(name);
			}
			String redirect = this.getredirect();
			if(StringUtil.isNotEmpty(redirect)){
				redirect = java.net.URLDecoder.decode(redirect,"UTF-8");
				this.setredirect(redirect);
			}
			String compenent = this.getcompenent();
			if(StringUtil.isNotEmpty(compenent)){
				compenent = java.net.URLDecoder.decode(compenent,"UTF-8");
				this.setcompenent(compenent);
			}
			String title = this.gettitle();
			if(StringUtil.isNotEmpty(title)){
				title = java.net.URLDecoder.decode(title,"UTF-8");
				this.settitle(title);
			}
			String icon = this.geticon();
			if(StringUtil.isNotEmpty(icon)){
				icon = java.net.URLDecoder.decode(icon,"UTF-8");
				this.seticon(icon);
			}
			String hidden = this.gethidden();
			if(StringUtil.isNotEmpty(hidden)){
				hidden = java.net.URLDecoder.decode(hidden,"UTF-8");
				this.sethidden(hidden);
			}
			String hiddenname = this.gethiddenname();
			if(StringUtil.isNotEmpty(hiddenname)){
				hiddenname = java.net.URLDecoder.decode(hiddenname,"UTF-8");
				this.sethiddenname(hiddenname);
			}
			String endorderid = this.getendorderid();
			if(StringUtil.isNotEmpty(endorderid)){
				endorderid = java.net.URLDecoder.decode(endorderid,"UTF-8");
				this.setendorderid(endorderid);
			}
			String orderid = this.getorderid();
			if(StringUtil.isNotEmpty(orderid)){
				orderid = java.net.URLDecoder.decode(orderid,"UTF-8");
				this.setorderid(orderid);
			}
			String startorderid = this.getstartorderid();
			if(StringUtil.isNotEmpty(startorderid)){
				startorderid = java.net.URLDecoder.decode(startorderid,"UTF-8");
				this.setstartorderid(startorderid);
			}
		} catch (Exception e) {
			System.out.println("中文转码出现问题");
		}
		return this;
	}
}
