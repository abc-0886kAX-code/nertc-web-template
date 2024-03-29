package com.ytxd.model.SystemManage;
import com.ytxd.model.BaseBO;
import com.ytxd.util.StringUtil;
public class SM_Role extends BaseBO {
	private static final long serialVersionUID = 1L;
	private String creatby;  //由谁创建 关联表SM_Users的UserId字段
	private String disabled;  //是可用00不可用01可用
	private String systemtype;  //
	private String systemtypename;  //
	private String userid;  //自增长ID
	private String usertype;  //用户类型00为普通用户01为外部资源02为角色用户03为内置用户
	private String loginname;  //系统登录名
	private String truename;  //姓名
	private String remark;  //备注
	private String endorderid;  //排序
	private String orderid;  //排序
	private String startorderid;  //排序
	private String allrole;  //角色
	private String allrolename;  //角色
	private String ischecked;  //是否选择
	private String rdmid;
	private String rdmallrole;
	private String roletype;  //角色分类
	private String roletypename;  //角色分类
	
	private String[] systemtypearray;
	public void setsystemtypearray(String[] systemtypearray) {
		this.systemtypearray = systemtypearray;
	}
	public String[] getsystemtypearray() {
		return systemtypearray;
	}
	private String[] usertypearray;
	public void setusertypearray(String[] usertypearray) {
		this.usertypearray = usertypearray;
	}
	public String[] getusertypearray() {
		return usertypearray;
	}
	private String[] allrolearray;
	public void setallrolearray(String[] allrolearray) {
		this.allrolearray = allrolearray;
	}
	public String[] getallrolearray() {
		return allrolearray;
	}
	private String[] rdmallrolearray;
	public void setrdmallrolearray(String[] rdmallrolearray) {
		this.rdmallrolearray = rdmallrolearray;
	}
	public String[] getrdmallrolearray() {
		return rdmallrolearray;
	}
	private String[] roletypearray;
	public void setroletypearray(String[] roletypearray) {
		this.roletypearray = roletypearray;
	}
	public String[] getroletypearray() {
		return roletypearray;
	}

	/**
	 * 由谁创建 关联表SM_Users的UserId字段
	 * @param creatby
	 */
	public void setcreatby(String creatby) {	this.creatby = creatby;	}
	/**
	 * 由谁创建 关联表SM_Users的UserId字段
	 * @return String
	 */
	public String getcreatby() {	return creatby;	}
	/**
	 * 是可用00不可用01可用
	 * @param disabled
	 */
	public void setdisabled(String disabled) {	this.disabled = disabled;	}
	/**
	 * 是可用00不可用01可用
	 * @return String
	 */
	public String getdisabled() {	return disabled;	}
	/**
	 * 
	 * @param systemtype
	 */
	public void setsystemtype(String systemtype) {	this.systemtype = systemtype; if(StringUtil.isNotEmpty(systemtype)){ this.setsystemtypearray(systemtype.replace("'", "").split(","));}	}
	/**
	 * 
	 * @return String
	 */
	public String getsystemtype() {	return systemtype;	}
	/**
	 * 
	 * @param systemtypename
	 */
	public void setsystemtypename(String systemtypename) {	this.systemtypename = systemtypename;	}
	/**
	 * 
	 * @return String
	 */
	public String getsystemtypename() {	return systemtypename;	}
	/**
	 * 自增长ID
	 * @param userid
	 */
	public void setuserid(String userid) {	this.userid = userid; if(StringUtil.isNotEmpty(userid)){ this.setidarray(userid.replace("'", "").split(","));}	}
	/**
	 * 自增长ID
	 * @return String
	 */
	public String getuserid() {	return userid;	}
	/**
	 * 用户类型00为普通用户01为外部资源02为角色用户03为内置用户
	 * @param usertype
	 */
	public void setusertype(String usertype) {	this.usertype = usertype; if(StringUtil.isNotEmpty(usertype)){ this.setusertypearray(usertype.replace("'", "").split(","));}	}
	/**
	 * 用户类型00为普通用户01为外部资源02为角色用户03为内置用户
	 * @return String
	 */
	public String getusertype() {	return usertype;	}
	/**
	 * 系统登录名
	 * @param loginname
	 */
	public void setloginname(String loginname) {	this.loginname = loginname;	}
	/**
	 * 系统登录名
	 * @return String
	 */
	public String getloginname() {	return loginname;	}
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
	 * 备注
	 * @param remark
	 */
	public void setremark(String remark) {	this.remark = remark;	}
	/**
	 * 备注
	 * @return String
	 */
	public String getremark() {	return remark;	}
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
	 * 角色
	 * @param allrole
	 */
	public void setallrole(String allrole) {	this.allrole = allrole; if(StringUtil.isNotEmpty(allrole)){ this.setallrolearray(allrole.replace("'", "").split(","));}	}
	/**
	 * 角色
	 * @return String
	 */
	public String getallrole() {	return allrole;	}
	/**
	 * 角色
	 * @param allrolename
	 */
	public void setallrolename(String allrolename) {	this.allrolename = allrolename;	}
	/**
	 * 角色
	 * @return String
	 */
	public String getallrolename() {	return allrolename;	}
	/**
	 * 是否选择
	 * @param ischecked
	 */
	public void setischecked(String ischecked) {	this.ischecked = ischecked;	}
	/**
	 * 是否选择
	 * @return String
	 */
	public String getischecked() {	return ischecked;	}
	/**
	 * rdmid
	 * @param rdmid
	 */
	public void setrdmid(String rdmid) {	this.rdmid = rdmid;	}
	/**
	 * rdmid
	 * @return String
	 */
	public String getrdmid() {	return rdmid;	}
	/**
	 * rdmallrole
	 * @param rdmallrole
	 */
	public void setrdmallrole(String rdmallrole) {	this.rdmallrole = rdmallrole; if(StringUtil.isNotEmpty(rdmallrole)){ this.setrdmallrolearray(rdmallrole.replace("'", "").split(","));}	}
	/**
	 * rdmallrole
	 * @return String
	 */
	public String getrdmallrole() {	return rdmallrole;	}
	/**
	 * roletype
	 * @param roletype
	 */
	public void setroletype(String roletype) {	this.roletype = roletype; if(StringUtil.isNotEmpty(roletype)){ this.setroletypearray(roletype.replace("'", "").split(","));}	}
	/**
	 * roletype
	 * @return String
	 */
	public String getroletype() {	return roletype;	}
	/**
	 * roletypename
	 * @param roletypename
	 */
	public void setroletypename(String roletypename) {	this.roletypename = roletypename;	}
	/**
	 * roletypename
	 * @return String
	 */
	public String getroletypename() {	return roletypename;	}

	public SM_Role GetdecodeUtf(){
		super.GetdecodeUtf();
		try {
			String creatby = this.getcreatby();
			if(StringUtil.isNotEmpty(creatby)){
				creatby = java.net.URLDecoder.decode(creatby,"UTF-8");
				this.setcreatby(creatby);
			}
			String disabled = this.getdisabled();
			if(StringUtil.isNotEmpty(disabled)){
				disabled = java.net.URLDecoder.decode(disabled,"UTF-8");
				this.setdisabled(disabled);
			}
			String systemtype = this.getsystemtype();
			if(StringUtil.isNotEmpty(systemtype)){
				systemtype = java.net.URLDecoder.decode(systemtype,"UTF-8");
				this.setsystemtype(systemtype);
			}
			String userid = this.getuserid();
			if(StringUtil.isNotEmpty(userid)){
				userid = java.net.URLDecoder.decode(userid,"UTF-8");
				this.setuserid(userid);
			}
			String usertype = this.getusertype();
			if(StringUtil.isNotEmpty(usertype)){
				usertype = java.net.URLDecoder.decode(usertype,"UTF-8");
				this.setusertype(usertype);
			}
			String loginname = this.getloginname();
			if(StringUtil.isNotEmpty(loginname)){
				loginname = java.net.URLDecoder.decode(loginname,"UTF-8");
				this.setloginname(loginname);
			}
			String truename = this.gettruename();
			if(StringUtil.isNotEmpty(truename)){
				truename = java.net.URLDecoder.decode(truename,"UTF-8");
				this.settruename(truename);
			}
			String remark = this.getremark();
			if(StringUtil.isNotEmpty(remark)){
				remark = java.net.URLDecoder.decode(remark,"UTF-8");
				this.setremark(remark);
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
			String allrole = this.getallrole();
			if(StringUtil.isNotEmpty(allrole)){
				allrole = java.net.URLDecoder.decode(allrole,"UTF-8");
				this.setallrole(allrole);
			}
			String allrolename = this.getallrolename();
			if(StringUtil.isNotEmpty(allrolename)){
				allrolename = java.net.URLDecoder.decode(allrolename,"UTF-8");
				this.setallrolename(allrolename);
			}
			String ischecked = this.getischecked();
			if(StringUtil.isNotEmpty(ischecked)){
				ischecked = java.net.URLDecoder.decode(ischecked,"UTF-8");
				this.setischecked(ischecked);
			}
			String rdmid = this.getrdmid();
			if(StringUtil.isNotEmpty(rdmid)){
				rdmid = java.net.URLDecoder.decode(rdmid,"UTF-8");
				this.setrdmid(rdmid);
			}
			String rdmallrole = this.getrdmallrole();
			if(StringUtil.isNotEmpty(rdmallrole)){
				rdmallrole = java.net.URLDecoder.decode(rdmallrole,"UTF-8");
				this.setrdmallrole(rdmallrole);
			}
			String roletype = this.getroletype();
			if(StringUtil.isNotEmpty(roletype)){
				roletype = java.net.URLDecoder.decode(roletype,"UTF-8");
				this.setroletype(roletype);
			}
			String roletypename = this.getroletypename();
			if(StringUtil.isNotEmpty(roletypename)){
				roletypename = java.net.URLDecoder.decode(roletypename,"UTF-8");
				this.setroletypename(roletypename);
			}
		} catch (Exception e) {
			System.out.println("中文转码出现问题");
		}
		return this;
	}
}
