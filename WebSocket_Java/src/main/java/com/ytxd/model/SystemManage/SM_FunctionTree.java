package com.ytxd.model.SystemManage;
import com.ytxd.model.BaseBO;
import com.ytxd.util.StringUtil;
public class SM_FunctionTree extends BaseBO {
	private static final long serialVersionUID = 1L;
	private String cptr;  //是否含有子集00含有子集01为无
	private String id;  //ID
	private String parentid;  //父级ID
	private String processitemid;  //
	private String name;  //菜单名称
	private String logo;  //权限标识
	private String navigateurl;  //链接网页
	private String systype;  //子系统
	private String systypename;  //子系统
	private String imageurl;  //图片地址
	private String disabled;  //是否可见
	private String disabledname;  //是否可见
	private String endorderid;  //排序
	private String orderid;  //排序
	private String startorderid;  //排序
	private String type;  //菜单类型
	private String typename;  //菜单类型
	private String remark;  //菜单使用说明
	private String haschildren;	//是否有子节点
	private String selectvalue;	//选择的值
	private String checked;	//是否选中
	private String userid;	//角色id

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
	 * @param processitemid
	 */
	public void setprocessitemid(String processitemid) {	this.processitemid = processitemid;	}
	/**
	 * 
	 * @return String
	 */
	public String getprocessitemid() {	return processitemid;	}
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
	 * @param logo
	 */
	public void setlogo(String logo) {	this.logo = logo;	}
	/**
	 * 权限标识
	 * @return String
	 */
	public String getlogo() {	return logo;	}
	/**
	 * 链接网页
	 * @param navigateurl
	 */
	public void setnavigateurl(String navigateurl) {	this.navigateurl = navigateurl;	}
	/**
	 * 链接网页
	 * @return String
	 */
	public String getnavigateurl() {	return navigateurl;	}
	/**
	 * 子系统
	 * @param systype
	 */
	public void setsystype(String systype) {	this.systype = systype;	}
	/**
	 * 子系统
	 * @return String
	 */
	public String getsystype() {	return systype;	}
	/**
	 * 子系统
	 * @param systypename
	 */
	public void setsystypename(String systypename) {	this.systypename = systypename;	}
	/**
	 * 子系统
	 * @return String
	 */
	public String getsystypename() {	return systypename;	}
	/**
	 * 图片地址
	 * @param imageurl
	 */
	public void setimageurl(String imageurl) {	this.imageurl = imageurl;	}
	/**
	 * 图片地址
	 * @return String
	 */
	public String getimageurl() {	return imageurl;	}
	/**
	 * 是否可见
	 * @param disabled
	 */
	public void setdisabled(String disabled) {	this.disabled = disabled;	}
	/**
	 * 是否可见
	 * @return String
	 */
	public String getdisabled() {	return disabled;	}
	/**
	 * 是否可见
	 * @param disabledname
	 */
	public void setdisabledname(String disabledname) {	this.disabledname = disabledname;	}
	/**
	 * 是否可见
	 * @return String
	 */
	public String getdisabledname() {	return disabledname;	}
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
	 * 菜单类型
	 * @param type
	 */
	public void settype(String type) {	this.type = type;	}
	/**
	 * 菜单类型
	 * @return String
	 */
	public String gettype() {	return type;	}
	/**
	 * 菜单类型
	 * @param typename
	 */
	public void settypename(String typename) {	this.typename = typename;	}
	/**
	 * 菜单类型
	 * @return String
	 */
	public String gettypename() {	return typename;	}
	/**
	 * 菜单使用说明
	 * @param remark
	 */
	public void setremark(String remark) {	this.remark = remark;	}
	/**
	 * 菜单使用说明
	 * @return String
	 */
	public String getremark() {	return remark;	}
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
	 * 是否选中
	 * @param userid
	 */
	public void setuserid(String userid) {
		this.userid = userid;
	}
	/**
	 * 是否选中
	 * @return String
	 */
	public String getuserid() {
		return userid;
	}

	public SM_FunctionTree GetdecodeUtf(){
		super.GetdecodeUtf();
		try {
			String cptr = this.getcptr();
			if(StringUtil.isNotEmpty(cptr)){
				cptr = java.net.URLDecoder.decode(cptr,"UTF-8");
				this.setcptr(cptr);
			}
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
			String processitemid = this.getprocessitemid();
			if(StringUtil.isNotEmpty(processitemid)){
				processitemid = java.net.URLDecoder.decode(processitemid,"UTF-8");
				this.setprocessitemid(processitemid);
			}
			String name = this.getname();
			if(StringUtil.isNotEmpty(name)){
				name = java.net.URLDecoder.decode(name,"UTF-8");
				this.setname(name);
			}
			String logo = this.getlogo();
			if(StringUtil.isNotEmpty(logo)){
				logo = java.net.URLDecoder.decode(logo,"UTF-8");
				this.setlogo(logo);
			}
			String navigateurl = this.getnavigateurl();
			if(StringUtil.isNotEmpty(navigateurl)){
				navigateurl = java.net.URLDecoder.decode(navigateurl,"UTF-8");
				this.setnavigateurl(navigateurl);
			}
			String systype = this.getsystype();
			if(StringUtil.isNotEmpty(systype)){
				systype = java.net.URLDecoder.decode(systype,"UTF-8");
				this.setsystype(systype);
			}
			String systypename = this.getsystypename();
			if(StringUtil.isNotEmpty(systypename)){
				systypename = java.net.URLDecoder.decode(systypename,"UTF-8");
				this.setsystypename(systypename);
			}
			String imageurl = this.getimageurl();
			if(StringUtil.isNotEmpty(imageurl)){
				imageurl = java.net.URLDecoder.decode(imageurl,"UTF-8");
				this.setimageurl(imageurl);
			}
			String disabled = this.getdisabled();
			if(StringUtil.isNotEmpty(disabled)){
				disabled = java.net.URLDecoder.decode(disabled,"UTF-8");
				this.setdisabled(disabled);
			}
			String disabledname = this.getdisabledname();
			if(StringUtil.isNotEmpty(disabledname)){
				disabledname = java.net.URLDecoder.decode(disabledname,"UTF-8");
				this.setdisabledname(disabledname);
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
			String type = this.gettype();
			if(StringUtil.isNotEmpty(type)){
				type = java.net.URLDecoder.decode(type,"UTF-8");
				this.settype(type);
			}
			String typename = this.gettypename();
			if(StringUtil.isNotEmpty(typename)){
				typename = java.net.URLDecoder.decode(typename,"UTF-8");
				this.settypename(typename);
			}
			String remark = this.getremark();
			if(StringUtil.isNotEmpty(remark)){
				remark = java.net.URLDecoder.decode(remark,"UTF-8");
				this.setremark(remark);
			}
			String haschildren = this.gethaschildren();
			if(StringUtil.isNotEmpty(haschildren)){
				haschildren = java.net.URLDecoder.decode(haschildren,"UTF-8");
				this.sethaschildren(haschildren);
			}
			String selectvalue = this.getselectvalue();
			if(StringUtil.isNotEmpty(selectvalue)){
				selectvalue = java.net.URLDecoder.decode(selectvalue,"UTF-8");
				this.setselectvalue(selectvalue);
			}
		} catch (Exception e) {
			System.out.println("中文转码出现问题");
		}
		return this;
	}
}
