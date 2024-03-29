package com.ytxd.model.SystemManage;
import com.ytxd.model.BaseBO;
import com.ytxd.util.StringUtil;
public class SM_Department extends BaseBO {
	private String keysysid;  //
	private String parentid;  //所属学校
	private String parentidname;  //所属学校
	private String depttypename;  //机构类型
	private String depttype;  //机构类型
	private String nodename;  //院系名称
	private String departmentnumber;  //机构编号
	private String disciplines;  //一级学科
	private String responsible;  //负责人
	private String approval;  //批准机构
	private String approvalname;  //批准机构
	private String setuptime;  //成立时间
	private String zipcode;  //邮编
	private String address;  //地址
	private String contacts;  //联系人
	private String fax;  //传真
	private String phone;  //电话
	private String telephone;  //手机
	private String email;  //电子邮件
	private String website;  //网址
	private String doctorcount;  //博士生人数
	private String mastercount;  //硕士生人数
	private String schoolcount;  //研究生班人数
	private String bookcncount;  //中文图书数
	private String bookencount;  //外文图书数
	private String journalcncount;  //中文期刊
	private String journalencount;  //外文期刊
	private String mechanism;  //机构简介
	private String departmentid;  //ID
	private String remark;  //备注
	private String bmgzfw;  //
	private String orderid;  //排序ID
	private String status;  //状态 00为可用01为删除
	private String number;  //机构编号
	private String systemtype;  //
	private String sinoss_code;  //
	private String disciplinesname;  //
	private String synchroremark;  //
	
	private String haschildren;	//是否有子节点
	private String selectvalue;


	/**
	 * 
	 * @param keysysid
	 */
	public void setkeysysid(String keysysid) {	this.keysysid = keysysid;	}
	/**
	 * 
	 * @return String
	 */
	public String getkeysysid() {	return keysysid;	}
	/**
	 * 所属学校
	 * @param parentid
	 */
	public void setparentid(String parentid) {	this.parentid = parentid;	}
	/**
	 * 所属学校
	 * @return String
	 */
	public String getparentid() {	return parentid;	}
	/**
	 * 所属学校
	 * @param parentidname
	 */
	public void setparentidname(String parentidname) {	this.parentidname = parentidname;	}
	/**
	 * 所属学校
	 * @return String
	 */
	public String getparentidname() {	return parentidname;	}
	/**
	 * 机构类型
	 * @param depttypename
	 */
	public void setdepttypename(String depttypename) {	this.depttypename = depttypename;	}
	/**
	 * 机构类型
	 * @return String
	 */
	public String getdepttypename() {	return depttypename;	}
	/**
	 * 机构类型
	 * @param depttype
	 */
	public void setdepttype(String depttype) {	this.depttype = depttype;	}
	/**
	 * 机构类型
	 * @return String
	 */
	public String getdepttype() {	return depttype;	}
	/**
	 * 院系名称
	 * @param nodename
	 */
	public void setnodename(String nodename) {	this.nodename = nodename;	}
	/**
	 * 院系名称
	 * @return String
	 */
	public String getnodename() {	return nodename;	}
	/**
	 * 机构编号
	 * @param departmentnumber
	 */
	public void setdepartmentnumber(String departmentnumber) {	this.departmentnumber = departmentnumber;	}
	/**
	 * 机构编号
	 * @return String
	 */
	public String getdepartmentnumber() {	return departmentnumber;	}
	/**
	 * 一级学科
	 * @param disciplines
	 */
	public void setdisciplines(String disciplines) {	this.disciplines = disciplines;	}
	/**
	 * 一级学科
	 * @return String
	 */
	public String getdisciplines() {	return disciplines;	}
	/**
	 * 负责人
	 * @param responsible
	 */
	public void setresponsible(String responsible) {	this.responsible = responsible;	}
	/**
	 * 负责人
	 * @return String
	 */
	public String getresponsible() {	return responsible;	}
	/**
	 * 批准机构
	 * @param approval
	 */
	public void setapproval(String approval) {	this.approval = approval;	}
	/**
	 * 批准机构
	 * @return String
	 */
	public String getapproval() {	return approval;	}
	/**
	 * 批准机构
	 * @param approvalname
	 */
	public void setapprovalname(String approvalname) {	this.approvalname = approvalname;	}
	/**
	 * 批准机构
	 * @return String
	 */
	public String getapprovalname() {	return approvalname;	}
	/**
	 * 成立时间
	 * @param setuptime
	 */
	public void setsetuptime(String setuptime) {	this.setuptime = setuptime;	}
	/**
	 * 成立时间
	 * @return String
	 */
	public String getsetuptime() {	return setuptime;	}
	/**
	 * 邮编
	 * @param zipcode
	 */
	public void setzipcode(String zipcode) {	this.zipcode = zipcode;	}
	/**
	 * 邮编
	 * @return String
	 */
	public String getzipcode() {	return zipcode;	}
	/**
	 * 地址
	 * @param address
	 */
	public void setaddress(String address) {	this.address = address;	}
	/**
	 * 地址
	 * @return String
	 */
	public String getaddress() {	return address;	}
	/**
	 * 联系人
	 * @param contacts
	 */
	public void setcontacts(String contacts) {	this.contacts = contacts;	}
	/**
	 * 联系人
	 * @return String
	 */
	public String getcontacts() {	return contacts;	}
	/**
	 * 传真
	 * @param fax
	 */
	public void setfax(String fax) {	this.fax = fax;	}
	/**
	 * 传真
	 * @return String
	 */
	public String getfax() {	return fax;	}
	/**
	 * 电话
	 * @param phone
	 */
	public void setphone(String phone) {	this.phone = phone;	}
	/**
	 * 电话
	 * @return String
	 */
	public String getphone() {	return phone;	}
	/**
	 * 手机
	 * @param telephone
	 */
	public void settelephone(String telephone) {	this.telephone = telephone;	}
	/**
	 * 手机
	 * @return String
	 */
	public String gettelephone() {	return telephone;	}
	/**
	 * 电子邮件
	 * @param email
	 */
	public void setemail(String email) {	this.email = email;	}
	/**
	 * 电子邮件
	 * @return String
	 */
	public String getemail() {	return email;	}
	/**
	 * 网址
	 * @param website
	 */
	public void setwebsite(String website) {	this.website = website;	}
	/**
	 * 网址
	 * @return String
	 */
	public String getwebsite() {	return website;	}
	/**
	 * 博士生人数
	 * @param doctorcount
	 */
	public void setdoctorcount(String doctorcount) {	this.doctorcount = doctorcount;	}
	/**
	 * 博士生人数
	 * @return String
	 */
	public String getdoctorcount() {	return doctorcount;	}
	/**
	 * 硕士生人数
	 * @param mastercount
	 */
	public void setmastercount(String mastercount) {	this.mastercount = mastercount;	}
	/**
	 * 硕士生人数
	 * @return String
	 */
	public String getmastercount() {	return mastercount;	}
	/**
	 * 研究生班人数
	 * @param schoolcount
	 */
	public void setschoolcount(String schoolcount) {	this.schoolcount = schoolcount;	}
	/**
	 * 研究生班人数
	 * @return String
	 */
	public String getschoolcount() {	return schoolcount;	}
	/**
	 * 中文图书数
	 * @param bookcncount
	 */
	public void setbookcncount(String bookcncount) {	this.bookcncount = bookcncount;	}
	/**
	 * 中文图书数
	 * @return String
	 */
	public String getbookcncount() {	return bookcncount;	}
	/**
	 * 外文图书数
	 * @param bookencount
	 */
	public void setbookencount(String bookencount) {	this.bookencount = bookencount;	}
	/**
	 * 外文图书数
	 * @return String
	 */
	public String getbookencount() {	return bookencount;	}
	/**
	 * 中文期刊
	 * @param journalcncount
	 */
	public void setjournalcncount(String journalcncount) {	this.journalcncount = journalcncount;	}
	/**
	 * 中文期刊
	 * @return String
	 */
	public String getjournalcncount() {	return journalcncount;	}
	/**
	 * 外文期刊
	 * @param journalencount
	 */
	public void setjournalencount(String journalencount) {	this.journalencount = journalencount;	}
	/**
	 * 外文期刊
	 * @return String
	 */
	public String getjournalencount() {	return journalencount;	}
	/**
	 * 机构简介
	 * @param mechanism
	 */
	public void setmechanism(String mechanism) {	this.mechanism = mechanism;	}
	/**
	 * 机构简介
	 * @return String
	 */
	public String getmechanism() {	return mechanism;	}
	/**
	 * ID
	 * @param departmentid
	 */
	public void setdepartmentid(String departmentid) {	this.departmentid = departmentid;if(StringUtil.isNotEmpty(departmentid)){this.setidarray(departmentid.replace("'", "").split(","));}	}
	/**
	 * ID
	 * @return String
	 */
	public String getdepartmentid() {	return departmentid;	}
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
	 * 
	 * @param bmgzfw
	 */
	public void setbmgzfw(String bmgzfw) {	this.bmgzfw = bmgzfw;	}
	/**
	 * 
	 * @return String
	 */
	public String getbmgzfw() {	return bmgzfw;	}
	/**
	 * 排序ID
	 * @param orderid
	 */
	public void setorderid(String orderid) {	this.orderid = orderid;	}
	/**
	 * 排序ID
	 * @return String
	 */
	public String getorderid() {	return orderid;	}
	/**
	 * 状态 00为可用01为删除
	 * @param status
	 */
	public void setstatus(String status) {	this.status = status;	}
	/**
	 * 状态 00为可用01为删除
	 * @return String
	 */
	public String getstatus() {	return status;	}
	/**
	 * 机构编号
	 * @param number
	 */
	public void setnumber(String number) {	this.number = number;	}
	/**
	 * 机构编号
	 * @return String
	 */
	public String getnumber() {	return number;	}
	/**
	 * 
	 * @param systemtype
	 */
	public void setsystemtype(String systemtype) {	this.systemtype = systemtype;	}
	/**
	 * 
	 * @return String
	 */
	public String getsystemtype() {	return systemtype;	}
	/**
	 * 
	 * @param sinoss_code
	 */
	public void setsinoss_code(String sinoss_code) {	this.sinoss_code = sinoss_code;	}
	/**
	 * 
	 * @return String
	 */
	public String getsinoss_code() {	return sinoss_code;	}
	/**
	 * 
	 * @param disciplinesname
	 */
	public void setdisciplinesname(String disciplinesname) {	this.disciplinesname = disciplinesname;	}
	/**
	 * 
	 * @return String
	 */
	public String getdisciplinesname() {	return disciplinesname;	}
	/**
	 * 
	 * @param synchroremark
	 */
	public void setsynchroremark(String synchroremark) {	this.synchroremark = synchroremark;	}
	/**
	 * 
	 * @return String
	 */
	public String getsynchroremark() {	return synchroremark;	}
	
	public void sethaschildren(String haschildren) {	this.haschildren = haschildren;	}
	public String gethaschildren() {	return haschildren;	}
	public void setselectvalue(String selectvalue) {	this.selectvalue = selectvalue;	}
	public String getselectvalue() {	return selectvalue;	}
	

	public SM_Department GetdecodeUtf(){
		super.GetdecodeUtf();
		try {
			String keysysid = this.getkeysysid();
			if(StringUtil.isNotEmpty(keysysid)){
				keysysid = java.net.URLDecoder.decode(keysysid,"UTF-8");
				this.setkeysysid(keysysid);
			}
			String parentid = this.getparentid();
			if(StringUtil.isNotEmpty(parentid)){
				parentid = java.net.URLDecoder.decode(parentid,"UTF-8");
				this.setparentid(parentid);
			}
			String parentidname = this.getparentidname();
			if(StringUtil.isNotEmpty(parentidname)){
				parentidname = java.net.URLDecoder.decode(parentidname,"UTF-8");
				this.setparentidname(parentidname);
			}
			String depttypename = this.getdepttypename();
			if(StringUtil.isNotEmpty(depttypename)){
				depttypename = java.net.URLDecoder.decode(depttypename,"UTF-8");
				this.setdepttypename(depttypename);
			}
			String depttype = this.getdepttype();
			if(StringUtil.isNotEmpty(depttype)){
				depttype = java.net.URLDecoder.decode(depttype,"UTF-8");
				this.setdepttype(depttype);
			}
			String nodename = this.getnodename();
			if(StringUtil.isNotEmpty(nodename)){
				nodename = java.net.URLDecoder.decode(nodename,"UTF-8");
				this.setnodename(nodename);
			}
			String departmentnumber = this.getdepartmentnumber();
			if(StringUtil.isNotEmpty(departmentnumber)){
				departmentnumber = java.net.URLDecoder.decode(departmentnumber,"UTF-8");
				this.setdepartmentnumber(departmentnumber);
			}
			String disciplines = this.getdisciplines();
			if(StringUtil.isNotEmpty(disciplines)){
				disciplines = java.net.URLDecoder.decode(disciplines,"UTF-8");
				this.setdisciplines(disciplines);
			}
			String responsible = this.getresponsible();
			if(StringUtil.isNotEmpty(responsible)){
				responsible = java.net.URLDecoder.decode(responsible,"UTF-8");
				this.setresponsible(responsible);
			}
			String approval = this.getapproval();
			if(StringUtil.isNotEmpty(approval)){
				approval = java.net.URLDecoder.decode(approval,"UTF-8");
				this.setapproval(approval);
			}
			String approvalname = this.getapprovalname();
			if(StringUtil.isNotEmpty(approvalname)){
				approvalname = java.net.URLDecoder.decode(approvalname,"UTF-8");
				this.setapprovalname(approvalname);
			}
			String setuptime = this.getsetuptime();
			if(StringUtil.isNotEmpty(setuptime)){
				setuptime = java.net.URLDecoder.decode(setuptime,"UTF-8");
				this.setsetuptime(setuptime);
			}
			String zipcode = this.getzipcode();
			if(StringUtil.isNotEmpty(zipcode)){
				zipcode = java.net.URLDecoder.decode(zipcode,"UTF-8");
				this.setzipcode(zipcode);
			}
			String address = this.getaddress();
			if(StringUtil.isNotEmpty(address)){
				address = java.net.URLDecoder.decode(address,"UTF-8");
				this.setaddress(address);
			}
			String contacts = this.getcontacts();
			if(StringUtil.isNotEmpty(contacts)){
				contacts = java.net.URLDecoder.decode(contacts,"UTF-8");
				this.setcontacts(contacts);
			}
			String fax = this.getfax();
			if(StringUtil.isNotEmpty(fax)){
				fax = java.net.URLDecoder.decode(fax,"UTF-8");
				this.setfax(fax);
			}
			String phone = this.getphone();
			if(StringUtil.isNotEmpty(phone)){
				phone = java.net.URLDecoder.decode(phone,"UTF-8");
				this.setphone(phone);
			}
			String telephone = this.gettelephone();
			if(StringUtil.isNotEmpty(telephone)){
				telephone = java.net.URLDecoder.decode(telephone,"UTF-8");
				this.settelephone(telephone);
			}
			String email = this.getemail();
			if(StringUtil.isNotEmpty(email)){
				email = java.net.URLDecoder.decode(email,"UTF-8");
				this.setemail(email);
			}
			String website = this.getwebsite();
			if(StringUtil.isNotEmpty(website)){
				website = java.net.URLDecoder.decode(website,"UTF-8");
				this.setwebsite(website);
			}
			String doctorcount = this.getdoctorcount();
			if(StringUtil.isNotEmpty(doctorcount)){
				doctorcount = java.net.URLDecoder.decode(doctorcount,"UTF-8");
				this.setdoctorcount(doctorcount);
			}
			String mastercount = this.getmastercount();
			if(StringUtil.isNotEmpty(mastercount)){
				mastercount = java.net.URLDecoder.decode(mastercount,"UTF-8");
				this.setmastercount(mastercount);
			}
			String schoolcount = this.getschoolcount();
			if(StringUtil.isNotEmpty(schoolcount)){
				schoolcount = java.net.URLDecoder.decode(schoolcount,"UTF-8");
				this.setschoolcount(schoolcount);
			}
			String bookcncount = this.getbookcncount();
			if(StringUtil.isNotEmpty(bookcncount)){
				bookcncount = java.net.URLDecoder.decode(bookcncount,"UTF-8");
				this.setbookcncount(bookcncount);
			}
			String bookencount = this.getbookencount();
			if(StringUtil.isNotEmpty(bookencount)){
				bookencount = java.net.URLDecoder.decode(bookencount,"UTF-8");
				this.setbookencount(bookencount);
			}
			String journalcncount = this.getjournalcncount();
			if(StringUtil.isNotEmpty(journalcncount)){
				journalcncount = java.net.URLDecoder.decode(journalcncount,"UTF-8");
				this.setjournalcncount(journalcncount);
			}
			String journalencount = this.getjournalencount();
			if(StringUtil.isNotEmpty(journalencount)){
				journalencount = java.net.URLDecoder.decode(journalencount,"UTF-8");
				this.setjournalencount(journalencount);
			}
			String mechanism = this.getmechanism();
			if(StringUtil.isNotEmpty(mechanism)){
				mechanism = java.net.URLDecoder.decode(mechanism,"UTF-8");
				this.setmechanism(mechanism);
			}
			String departmentid = this.getdepartmentid();
			if(StringUtil.isNotEmpty(departmentid)){
				departmentid = java.net.URLDecoder.decode(departmentid,"UTF-8");
				this.setdepartmentid(departmentid);
			}
			String remark = this.getremark();
			if(StringUtil.isNotEmpty(remark)){
				remark = java.net.URLDecoder.decode(remark,"UTF-8");
				this.setremark(remark);
			}
			String bmgzfw = this.getbmgzfw();
			if(StringUtil.isNotEmpty(bmgzfw)){
				bmgzfw = java.net.URLDecoder.decode(bmgzfw,"UTF-8");
				this.setbmgzfw(bmgzfw);
			}
			String orderid = this.getorderid();
			if(StringUtil.isNotEmpty(orderid)){
				orderid = java.net.URLDecoder.decode(orderid,"UTF-8");
				this.setorderid(orderid);
			}
			String status = this.getstatus();
			if(StringUtil.isNotEmpty(status)){
				status = java.net.URLDecoder.decode(status,"UTF-8");
				this.setstatus(status);
			}
			String number = this.getnumber();
			if(StringUtil.isNotEmpty(number)){
				number = java.net.URLDecoder.decode(number,"UTF-8");
				this.setnumber(number);
			}
			String systemtype = this.getsystemtype();
			if(StringUtil.isNotEmpty(systemtype)){
				systemtype = java.net.URLDecoder.decode(systemtype,"UTF-8");
				this.setsystemtype(systemtype);
			}
			String sinoss_code = this.getsinoss_code();
			if(StringUtil.isNotEmpty(sinoss_code)){
				sinoss_code = java.net.URLDecoder.decode(sinoss_code,"UTF-8");
				this.setsinoss_code(sinoss_code);
			}
			String disciplinesname = this.getdisciplinesname();
			if(StringUtil.isNotEmpty(disciplinesname)){
				disciplinesname = java.net.URLDecoder.decode(disciplinesname,"UTF-8");
				this.setdisciplinesname(disciplinesname);
			}
			String synchroremark = this.getsynchroremark();
			if(StringUtil.isNotEmpty(synchroremark)){
				synchroremark = java.net.URLDecoder.decode(synchroremark,"UTF-8");
				this.setsynchroremark(synchroremark);
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
