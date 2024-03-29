package com.ytxd.model.SystemManage;
import com.ytxd.model.BaseBO;
import com.ytxd.util.StringUtil;

/**
 * 用户表
 */
public class SM_Users extends BaseBO {
	private static final long serialVersionUID = 1L;
	private String keysysid;  //
	private String loginname;  //账号
	private String truename;  //姓名
	private String jobno;  //教工号
	private String affiliation;  //科研单位
	private String affiliationname;  //科研单位
	private String departmentid;  //部门、院、系
	private String departmentidname;  //部门、院、系
	private String homeaddress;  //籍贯
	private String sex;  //性别
	private String sexname;  //性别
	private String idcard;  //证件号码
	private String birthday;  //出生日期
	private String endbirthday;  //出生日期
	private String startbirthday;  //出生日期
	private String yearbirthday;  //出生日期
	private String degreetag;  //最后学历
	private String degreetagname;  //最后学历
	private String degree;  //最后学位
	private String degreename;  //最后学位
	private String titlegrade;  //职称级别
	private String titlegradename;  //职称级别
	private String title;  //职称
	private String titlename;  //职称
	private String titletype;  //职务类别
	private String titletypename;  //职务类别
	private String joborientationdate;  //定职日期
	private String titletag;  //职务
	private String politicalappearancetag;  //政治面貌
	private String politicalappearancetagname;  //政治面貌
	private String iscount;  //是否统计
	private String iscountname;  //是否统计
	private String systemtype;  //学科门类
	private String systemtypename;  //学科门类
	private String subjecttype;  //一级学科
	private String subjecttype02;  //二级学科
	private String subjecttype03;  //三级学科
	private String subject;  //研究方向
	private String email;  //电子邮件
	private String officephone;  //办公电话
	private String fax;  //传真
	private String mobilephone;  //手机
	private String qqaccount;  //QQ帐号
	private String address;  //联系地址
	private String zipcode;  //邮编
	private String remark;  //简介
	private String remark2;  //获得荣誉
	private String paperworktypename;  //证件类型
	private String countrytagname;  //是否中国国籍
	private String nationtagname;  //民族
	private String isbodaoname;  //是否为博士后
	private String language1name;  //第一外语
	private String language1levername;  //第一外语程度
	private String language2name;  //第二外语
	private String language2levername;  //第二外语程度
	private String isretirementname;  //是否退休
	private String userid;  //自增长ID
	private String useridnot;  //自增长ID
	private String password;  //登录密码
	private String oldpassword;  //旧密码  只是用于自己修改密码
	private String secondpassword;  //第二次密码  只是用于自己修改密码
	private String orderid;  //排序ID
	private String usertype;  //用户类型00为普通用户01为外部资源02为角色用户03为内置用户
	private String disabled;  //是可用00不可用01可用
	private String style;  //个人样式
	private String creatby;  //由谁创建 关联表SM_Users的UserId字段
	private String disabledname;  //是否启用
	private String code;  //用户编号
	private String marrid;  //婚否(00为已婚，01为未婚)
	private String countrytag;  //国籍
	private String nationtag;  //民族
	private String handletag;  //头衔
	private String postiontag;  //职位
	private String postionname;  //职位名称
	private String professionaltag;  //
	private String workday;  //参加工作日期
	private String entersday;  //入职日期
	private String leavesday;  //离职日期
	private String familyphone;  //家庭电话
	private String stafftype;  //员工类型
	private String checkdirection;  //
	private String fame;  //
	private String businessname;  //
	private String unitcode;  //
	private String permitcode;  //
	private String taxcode;  //
	private String registeradress;  //
	private String registermoney;  //
	private String unionphone;  //
	private String isoutside;  //
	private String createdate;  //
	private String iscenter;  //
	private String paperworktype;  //证件类型
	private String paperworknumber;  //证件号码
	private String paperworknumberatt;  //证件扫描件
	private String workdepartment;  //工作部门
	private String researchfield;  //研究领域
	private String other;  //其他信息
	private String region;  //地区
	private String resume;  // 工作简历
	private String photo;  //照片
	private String specialty;  //所学专长
	private String age;  //年龄
	private String participate;  //曾参与评估课题
	private String evaluate;  //专家评价
	private String isretirement;  //是否退休
	private String bankofdeposit;  //开户行
	private String accountname;  //开户名
	private String accountnumber;  //银行卡号
	private String isexpert;  //
	private String number;  //人员编号
	private String englishname;  //
	private String oldname;  //
	private String jszwrmsj;  //
	private String nationality;  //国籍,YM
	private String addressphone;  //
	private String addressportraiture;  //
	private String bleeper;  //传呼机
	private String personweb;  //个人网址
	private String isbodao;  //是否为博导
	private String language1;  //第一外语，FS
	private String language1lever;  //第一外语程度，QD
	private String language2;  //第二外语，FS
	private String language2lever;  //第二外语程度，QD
	private String learningstate;  //进修情况
	private String academicactivities;  //学术兼职
	private String speciality;  //特长
	private String jszw;  //
	private String mqzt;  //
	private String score;  //
	private String head;  //负责人
	private String approval;  //批准机构
	private String establishdate;  //成立时间
	private String contacts;  //联系人
	private String telephone;  //电话
	private String mechanism;  //机构简介
	private String affiliationroom;  //所属教研室
	private String personnelunit;  //人事所在单位
	private String politicslook;  //政治面貌
	private String isstatistics;  //是否统计
	private String personalbrief;  //个人简介
	private String disciplines;  //学科门类
	private String termstarttime;  //账户启用时间
	private String termendtime;  //账户停用时间
	private String qq;  //
	private String msn;  //
	private String sinoss_code;  //
	private String subjecttype02name;  //
	private String subjecttype03name;  //
	private String subjecttypename;  //
	private String synchroremark;  //
	private String fullpinyin;  //
	private String simplepinyin;  //


	private String allrole;  //角色
	private String allrolename;  //角色
	private String rdmid;
	private String isrdm;//是否同步到rdm
	private String rdmallrole;	
	private String mark;//标记符，没有明确定义，用于封装一些需要在程序中传递的标记符
	private String validatacode;//找回密码唯一标识
	private String outdate;//找回秘密过期时间
	private String lastlogintime;//修改信息时间	
	private String avatar;//IM头像	
	private Integer wrongtimes; // 密码错误次数
	private String islock;//是否锁死
	private String locktime;// 锁死时间
	/**
	 * 盐
	 */
	private String salt;
	
	public String getSalt() {
		return salt;
	}
	public void setSalt(String salt) {
		this.salt = salt;
	}
	private String [] useridnotarray;
	
	public String[] getUseridnotarray() {
		return useridnotarray;
	}
	public void setUseridnotarray(String[] useridnotarray) {
		this.useridnotarray = useridnotarray;
	}
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
	
	public Integer getWrongtimes() {
		return wrongtimes;
	}
	public void setWrongtimes(Integer wrongtimes) {
		this.wrongtimes = wrongtimes;
	}
	public String getIslock() {
		return islock;
	}
	public void setIslock(String islock) {
		this.islock = islock;
	}
	public String getLocktime() {
		return locktime;
	}
	public void setLocktime(String locktime) {
		this.locktime = locktime;
	}
	/**
	 * IM头像
	 */
	public String getavatar() {
		return avatar;
	}
	public void setavatar(String avatar) {
		this.avatar = avatar;
	}
	public String getLastlogintime() {
		return lastlogintime;
	}
	public void setLastlogintime(String lastlogintime) {
		this.lastlogintime = lastlogintime;
	}
	/**
	 * 找回密码唯一标识
	 * @return
	 */
	public String getvalidatacode() {
		return validatacode;
	}
	public void setvalidatacode(String validatacode) {
		this.validatacode = validatacode;
	}
	/*
	 * 找回秘密过期时间
	 */
	public String getoutdate() {
		return outdate;
	}
	public void setoutdate(String outdate) {
		this.outdate = outdate;
	}
	/**
	 * 标记符

	 * @return
	 */
	public String getMark() {
		return mark;
	}
	public void setMark(String mark) {
		this.mark = mark;
	}
	/**
	 * 
	 * @param systemtype
	 */
	public void setsystemtype(String systemtype) {	this.systemtype = systemtype;
		if(StringUtil.isNotEmpty(systemtype)){
			this.systemtypearray=systemtype.split(",");
		}
			}
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
	 * 自增长ID
	 * @param useridnot
	 */
	public void setuseridnot(String useridnot) {	this.useridnot = useridnot;this.useridnotarray=useridnot.split(",");	}
	/**
	 * 自增长ID
	 * @return String
	 */
	public String getuseridnot() {	return useridnot;	}
	/**
	 * 用户类型00为普通用户01为外部资源02为角色用户03为内置用户

	 * @param usertype
	 */
	public void setusertype(String usertype) {	this.usertype = usertype; this.usertypearray=usertype.split(",");	}
	/**
	 * 用户类型00为普通用户01为外部资源02为角色用户03为内置用户

	 * @return String
	 */
	public String getusertype() {	return usertype;	}
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
	 * isrdm
	 * @param isrdm
	 */
	public void setisrdm(String isrdm) {	this.isrdm = isrdm;	}
	/**
	 * isrdm
	 * @return String
	 */
	public String getisrdm() {	return isrdm;	}
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
	 * 账号
	 * @param loginname
	 */
	public void setloginname(String loginname) {	this.loginname = loginname;	}
	/**
	 * 账号
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
	 * 教工号
	 * @param jobno
	 */
	public void setjobno(String jobno) {	this.jobno = jobno;	}
	/**
	 * 教工号
	 * @return String
	 */
	public String getjobno() {	return jobno;	}
	/**
	 * 科研单位
	 * @param affiliation
	 */
	public void setaffiliation(String affiliation) {	this.affiliation = affiliation;	}
	/**
	 * 科研单位
	 * @return String
	 */
	public String getaffiliation() {	return affiliation;	}
	/**
	 * 科研单位
	 * @param affiliationname
	 */
	public void setaffiliationname(String affiliationname) {	this.affiliationname = affiliationname;	}
	/**
	 * 科研单位
	 * @return String
	 */
	public String getaffiliationname() {	return affiliationname;	}
	/**
	 * 部门、院、系
	 * @param departmentid
	 */
	public void setdepartmentid(String departmentid) {	this.departmentid = departmentid;	}
	/**
	 * 部门、院、系
	 * @return String
	 */
	public String getdepartmentid() {	return departmentid;	}
	/**
	 * 部门、院、系
	 * @param departmentidname
	 */
	public void setdepartmentidname(String departmentidname) {	this.departmentidname = departmentidname;	}
	/**
	 * 部门、院、系
	 * @return String
	 */
	public String getdepartmentidname() {	return departmentidname;	}
	/**
	 * 籍贯
	 * @param homeaddress
	 */
	public void sethomeaddress(String homeaddress) {	this.homeaddress = homeaddress;	}
	/**
	 * 籍贯
	 * @return String
	 */
	public String gethomeaddress() {	return homeaddress;	}
	/**
	 * 性别
	 * @param sex
	 */
	public void setsex(String sex) {	this.sex = sex;	}
	/**
	 * 性别
	 * @return String
	 */
	public String getsex() {	return sex;	}
	/**
	 * 性别
	 * @param sexname
	 */
	public void setsexname(String sexname) {	this.sexname = sexname;	}
	/**
	 * 性别
	 * @return String
	 */
	public String getsexname() {	return sexname;	}
	/**
	 * 证件号码
	 * @param idcard
	 */
	public void setidcard(String idcard) {	this.idcard = idcard;	}
	/**
	 * 证件号码
	 * @return String
	 */
	public String getidcard() {	return idcard;	}
	/**
	 * 出生日期
	 * @param birthday
	 */
	public void setbirthday(String birthday) {	this.birthday = birthday;	}
	/**
	 * 出生日期
	 * @return String
	 */
	public String getbirthday() {	return birthday;	}
	/**
	 * 出生日期
	 * @param endbirthday
	 */
	public void setendbirthday(String endbirthday) {	this.endbirthday = endbirthday;	}
	/**
	 * 出生日期
	 * @return String
	 */
	public String getendbirthday() {	return endbirthday;	}
	/**
	 * 出生日期
	 * @param startbirthday
	 */
	public void setstartbirthday(String startbirthday) {	this.startbirthday = startbirthday;	}
	/**
	 * 出生日期
	 * @return String
	 */
	public String getstartbirthday() {	return startbirthday;	}
	/**
	 * 出生日期
	 * @param yearbirthday
	 */
	public void setyearbirthday(String yearbirthday) {	this.yearbirthday = yearbirthday;	}
	/**
	 * 出生日期
	 * @return String
	 */
	public String getyearbirthday() {	return yearbirthday;	}
	/**
	 * 最后学历
	 * @param degreetag
	 */
	public void setdegreetag(String degreetag) {	this.degreetag = degreetag;	}
	/**
	 * 最后学历
	 * @return String
	 */
	public String getdegreetag() {	return degreetag;	}
	/**
	 * 最后学历
	 * @param degreetagname
	 */
	public void setdegreetagname(String degreetagname) {	this.degreetagname = degreetagname;	}
	/**
	 * 最后学历
	 * @return String
	 */
	public String getdegreetagname() {	return degreetagname;	}
	/**
	 * 最后学位
	 * @param degree
	 */
	public void setdegree(String degree) {	this.degree = degree;	}
	/**
	 * 最后学位
	 * @return String
	 */
	public String getdegree() {	return degree;	}
	/**
	 * 最后学位
	 * @param degreename
	 */
	public void setdegreename(String degreename) {	this.degreename = degreename;	}
	/**
	 * 最后学位
	 * @return String
	 */
	public String getdegreename() {	return degreename;	}
	/**
	 * 职称级别
	 * @param titlegrade
	 */
	public void settitlegrade(String titlegrade) {	this.titlegrade = titlegrade;	}
	/**
	 * 职称级别
	 * @return String
	 */
	public String gettitlegrade() {	return titlegrade;	}
	/**
	 * 职称级别
	 * @param titlegradename
	 */
	public void settitlegradename(String titlegradename) {	this.titlegradename = titlegradename;	}
	/**
	 * 职称级别
	 * @return String
	 */
	public String gettitlegradename() {	return titlegradename;	}
	/**
	 * 职称
	 * @param title
	 */
	public void settitle(String title) {	this.title = title;	}
	/**
	 * 职称
	 * @return String
	 */
	public String gettitle() {	return title;	}
	/**
	 * 职称
	 * @param titlename
	 */
	public void settitlename(String titlename) {	this.titlename = titlename;	}
	/**
	 * 职称
	 * @return String
	 */
	public String gettitlename() {	return titlename;	}
	/**
	 * 职务类别
	 * @param titletype
	 */
	public void settitletype(String titletype) {	this.titletype = titletype;	}
	/**
	 * 职务类别
	 * @return String
	 */
	public String gettitletype() {	return titletype;	}
	/**
	 * 职务类别
	 * @param titletypename
	 */
	public void settitletypename(String titletypename) {	this.titletypename = titletypename;	}
	/**
	 * 职务类别
	 * @return String
	 */
	public String gettitletypename() {	return titletypename;	}
	/**
	 * 定职日期
	 * @param joborientationdate
	 */
	public void setjoborientationdate(String joborientationdate) {	this.joborientationdate = joborientationdate;	}
	/**
	 * 定职日期
	 * @return String
	 */
	public String getjoborientationdate() {	return joborientationdate;	}
	/**
	 * 职务
	 * @param titletag
	 */
	public void settitletag(String titletag) {	this.titletag = titletag;	}
	/**
	 * 职务
	 * @return String
	 */
	public String gettitletag() {	return titletag;	}
	/**
	 * 政治面貌
	 * @param politicalappearancetag
	 */
	public void setpoliticalappearancetag(String politicalappearancetag) {	this.politicalappearancetag = politicalappearancetag;	}
	/**
	 * 政治面貌
	 * @return String
	 */
	public String getpoliticalappearancetag() {	return politicalappearancetag;	}
	/**
	 * 政治面貌
	 * @param politicalappearancetagname
	 */
	public void setpoliticalappearancetagname(String politicalappearancetagname) {	this.politicalappearancetagname = politicalappearancetagname;	}
	/**
	 * 政治面貌
	 * @return String
	 */
	public String getpoliticalappearancetagname() {	return politicalappearancetagname;	}
	/**
	 * 是否统计
	 * @param iscount
	 */
	public void setiscount(String iscount) {	this.iscount = iscount;	}
	/**
	 * 是否统计
	 * @return String
	 */
	public String getiscount() {	return iscount;	}
	/**
	 * 是否统计
	 * @param iscountname
	 */
	public void setiscountname(String iscountname) {	this.iscountname = iscountname;	}
	/**
	 * 是否统计
	 * @return String
	 */
	public String getiscountname() {	return iscountname;	}
	/**
	 * 一级学科
	 * @param subjecttype
	 */
	public void setsubjecttype(String subjecttype) {	this.subjecttype = subjecttype;	}
	/**
	 * 一级学科
	 * @return String
	 */
	public String getsubjecttype() {	return subjecttype;	}
	/**
	 * 二级学科
	 * @param subjecttype02
	 */
	public void setsubjecttype02(String subjecttype02) {	this.subjecttype02 = subjecttype02;	}
	/**
	 * 二级学科
	 * @return String
	 */
	public String getsubjecttype02() {	return subjecttype02;	}
	/**
	 * 三级学科
	 * @param subjecttype03
	 */
	public void setsubjecttype03(String subjecttype03) {	this.subjecttype03 = subjecttype03;	}
	/**
	 * 三级学科
	 * @return String
	 */
	public String getsubjecttype03() {	return subjecttype03;	}
	/**
	 * 研究方向
	 * @param subject
	 */
	public void setsubject(String subject) {	this.subject = subject;	}
	/**
	 * 研究方向
	 * @return String
	 */
	public String getsubject() {	return subject;	}
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
	 * 办公电话
	 * @param officephone
	 */
	public void setofficephone(String officephone) {	this.officephone = officephone;	}
	/**
	 * 办公电话
	 * @return String
	 */
	public String getofficephone() {	return officephone;	}
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
	 * 手机
	 * @param mobilephone
	 */
	public void setmobilephone(String mobilephone) {	this.mobilephone = mobilephone;	}
	/**
	 * 手机
	 * @return String
	 */
	public String getmobilephone() {	return mobilephone;	}
	/**
	 * QQ帐号
	 * @param qqaccount
	 */
	public void setqqaccount(String qqaccount) {	this.qqaccount = qqaccount;	}
	/**
	 * QQ帐号
	 * @return String
	 */
	public String getqqaccount() {	return qqaccount;	}
	/**
	 * 联系地址
	 * @param address
	 */
	public void setaddress(String address) {	this.address = address;	}
	/**
	 * 联系地址
	 * @return String
	 */
	public String getaddress() {	return address;	}
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
	 * 简介
	 * @param remark
	 */
	public void setremark(String remark) {	this.remark = remark;	}
	/**
	 * 简介
	 * @return String
	 */
	public String getremark() {	return remark;	}
	/**
	 * 获得荣誉
	 * @param remark2
	 */
	public void setremark2(String remark2) {	this.remark2 = remark2;	}
	/**
	 * 获得荣誉
	 * @return String
	 */
	public String getremark2() {	return remark2;	}
	/**
	 * 证件类型
	 * @param paperworktypename
	 */
	public void setpaperworktypename(String paperworktypename) {	this.paperworktypename = paperworktypename;	}
	/**
	 * 证件类型
	 * @return String
	 */
	public String getpaperworktypename() {	return paperworktypename;	}
	/**
	 * 是否中国国籍
	 * @param countrytagname
	 */
	public void setcountrytagname(String countrytagname) {	this.countrytagname = countrytagname;	}
	/**
	 * 是否中国国籍
	 * @return String
	 */
	public String getcountrytagname() {	return countrytagname;	}
	/**
	 * 民族
	 * @param nationtagname
	 */
	public void setnationtagname(String nationtagname) {	this.nationtagname = nationtagname;	}
	/**
	 * 民族
	 * @return String
	 */
	public String getnationtagname() {	return nationtagname;	}
	/**
	 * 是否为博士后
	 * @param isbodaoname
	 */
	public void setisbodaoname(String isbodaoname) {	this.isbodaoname = isbodaoname;	}
	/**
	 * 是否为博士后
	 * @return String
	 */
	public String getisbodaoname() {	return isbodaoname;	}
	/**
	 * 第一外语
	 * @param language1name
	 */
	public void setlanguage1name(String language1name) {	this.language1name = language1name;	}
	/**
	 * 第一外语
	 * @return String
	 */
	public String getlanguage1name() {	return language1name;	}
	/**
	 * 第一外语程度
	 * @param language1levername
	 */
	public void setlanguage1levername(String language1levername) {	this.language1levername = language1levername;	}
	/**
	 * 第一外语程度
	 * @return String
	 */
	public String getlanguage1levername() {	return language1levername;	}
	/**
	 * 第二外语
	 * @param language2name
	 */
	public void setlanguage2name(String language2name) {	this.language2name = language2name;	}
	/**
	 * 第二外语
	 * @return String
	 */
	public String getlanguage2name() {	return language2name;	}
	/**
	 * 第二外语程度
	 * @param language2levername
	 */
	public void setlanguage2levername(String language2levername) {	this.language2levername = language2levername;	}
	/**
	 * 第二外语程度
	 * @return String
	 */
	public String getlanguage2levername() {	return language2levername;	}
	/**
	 * 是否退休
	 * @param isretirementname
	 */
	public void setisretirementname(String isretirementname) {	this.isretirementname = isretirementname;	}
	/**
	 * 是否退休
	 * @return String
	 */
	public String getisretirementname() {	return isretirementname;	}
	/**
	 * 登录密码
	 * @param password
	 */
	public void setpassword(String password) {	this.password = password;	}
	/**
	 * 登录密码
	 * @return String
	 */
	public String getpassword() {	return password;	}
	/**
	 * 旧密码  只是用于自己修改密码
	 * @param oldpassword
	 */
	public void setoldpassword(String oldpassword) {	this.oldpassword = oldpassword;	}
	/**
	 * 旧密码  只是用于自己修改密码
	 * @return String
	 */
	public String getoldpassword() {	return oldpassword;	}
	/**
	 * 第二次密码  只是用于自己修改密码
	 * @param secondpassword
	 */
	public void setsecondpassword(String secondpassword) {	this.secondpassword = secondpassword;	}
	/**
	 * 第二次密码  只是用于自己修改密码
	 * @return String
	 */
	public String getsecondpassword() {	return secondpassword;	}
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
	 * 个人样式
	 * @param style
	 */
	public void setstyle(String style) {	this.style = style;	}
	/**
	 * 个人样式
	 * @return String
	 */
	public String getstyle() {	return style;	}
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
	 * 是否启用
	 * @param disabledname
	 */
	public void setdisabledname(String disabledname) {	this.disabledname = disabledname;	}
	/**
	 * 是否启用
	 * @return String
	 */
	public String getdisabledname() {	return disabledname;	}
	/**
	 * 用户编号
	 * @param code
	 */
	public void setcode(String code) {	this.code = code;	}
	/**
	 * 用户编号
	 * @return String
	 */
	public String getcode() {	return code;	}
	/**
	 * 婚否(00为已婚，01为未婚)
	 * @param marrid
	 */
	public void setmarrid(String marrid) {	this.marrid = marrid;	}
	/**
	 * 婚否(00为已婚，01为未婚)
	 * @return String
	 */
	public String getmarrid() {	return marrid;	}
	/**
	 * 国籍
	 * @param countrytag
	 */
	public void setcountrytag(String countrytag) {	this.countrytag = countrytag;	}
	/**
	 * 国籍
	 * @return String
	 */
	public String getcountrytag() {	return countrytag;	}
	/**
	 * 民族
	 * @param nationtag
	 */
	public void setnationtag(String nationtag) {	this.nationtag = nationtag;	}
	/**
	 * 民族
	 * @return String
	 */
	public String getnationtag() {	return nationtag;	}
	/**
	 * 头衔
	 * @param handletag
	 */
	public void sethandletag(String handletag) {	this.handletag = handletag;	}
	/**
	 * 头衔
	 * @return String
	 */
	public String gethandletag() {	return handletag;	}
	/**
	 * 职位
	 * @param postiontag
	 */
	public void setpostiontag(String postiontag) {	this.postiontag = postiontag;	}
	/**
	 * 职位
	 * @return String
	 */
	public String getpostiontag() {	return postiontag;	}
	/**
	 * 职位名称
	 * @param postionname
	 */
	public void setpostionname(String postionname) {	this.postionname = postionname;	}
	/**
	 * 职位名称
	 * @return String
	 */
	public String getpostionname() {	return postionname;	}
	/**
	 * 
	 * @param professionaltag
	 */
	public void setprofessionaltag(String professionaltag) {	this.professionaltag = professionaltag;	}
	/**
	 * 
	 * @return String
	 */
	public String getprofessionaltag() {	return professionaltag;	}
	/**
	 * 参加工作日期
	 * @param workday
	 */
	public void setworkday(String workday) {	this.workday = workday;	}
	/**
	 * 参加工作日期
	 * @return String
	 */
	public String getworkday() {	return workday;	}
	/**
	 * 入职日期
	 * @param entersday
	 */
	public void setentersday(String entersday) {	this.entersday = entersday;	}
	/**
	 * 入职日期
	 * @return String
	 */
	public String getentersday() {	return entersday;	}
	/**
	 * 离职日期
	 * @param leavesday
	 */
	public void setleavesday(String leavesday) {	this.leavesday = leavesday;	}
	/**
	 * 离职日期
	 * @return String
	 */
	public String getleavesday() {	return leavesday;	}
	/**
	 * 家庭电话
	 * @param familyphone
	 */
	public void setfamilyphone(String familyphone) {	this.familyphone = familyphone;	}
	/**
	 * 家庭电话
	 * @return String
	 */
	public String getfamilyphone() {	return familyphone;	}
	/**
	 * 员工类型
	 * @param stafftype
	 */
	public void setstafftype(String stafftype) {	this.stafftype = stafftype;	}
	/**
	 * 员工类型
	 * @return String
	 */
	public String getstafftype() {	return stafftype;	}
	/**
	 * 
	 * @param checkdirection
	 */
	public void setcheckdirection(String checkdirection) {	this.checkdirection = checkdirection;	}
	/**
	 * 
	 * @return String
	 */
	public String getcheckdirection() {	return checkdirection;	}
	/**
	 * 
	 * @param fame
	 */
	public void setfame(String fame) {	this.fame = fame;	}
	/**
	 * 
	 * @return String
	 */
	public String getfame() {	return fame;	}
	/**
	 * 
	 * @param businessname
	 */
	public void setbusinessname(String businessname) {	this.businessname = businessname;	}
	/**
	 * 
	 * @return String
	 */
	public String getbusinessname() {	return businessname;	}
	/**
	 * 
	 * @param unitcode
	 */
	public void setunitcode(String unitcode) {	this.unitcode = unitcode;	}
	/**
	 * 
	 * @return String
	 */
	public String getunitcode() {	return unitcode;	}
	/**
	 * 
	 * @param permitcode
	 */
	public void setpermitcode(String permitcode) {	this.permitcode = permitcode;	}
	/**
	 * 
	 * @return String
	 */
	public String getpermitcode() {	return permitcode;	}
	/**
	 * 
	 * @param taxcode
	 */
	public void settaxcode(String taxcode) {	this.taxcode = taxcode;	}
	/**
	 * 
	 * @return String
	 */
	public String gettaxcode() {	return taxcode;	}
	/**
	 * 
	 * @param registeradress
	 */
	public void setregisteradress(String registeradress) {	this.registeradress = registeradress;	}
	/**
	 * 
	 * @return String
	 */
	public String getregisteradress() {	return registeradress;	}
	/**
	 * 
	 * @param registermoney
	 */
	public void setregistermoney(String registermoney) {	this.registermoney = registermoney;	}
	/**
	 * 
	 * @return String
	 */
	public String getregistermoney() {	return registermoney;	}
	/**
	 * 
	 * @param unionphone
	 */
	public void setunionphone(String unionphone) {	this.unionphone = unionphone;	}
	/**
	 * 
	 * @return String
	 */
	public String getunionphone() {	return unionphone;	}
	/**
	 * 
	 * @param isoutside
	 */
	public void setisoutside(String isoutside) {	this.isoutside = isoutside;	}
	/**
	 * 
	 * @return String
	 */
	public String getisoutside() {	return isoutside;	}
	/**
	 * 
	 * @param createdate
	 */
	public void setcreatedate(String createdate) {	this.createdate = createdate;	}
	/**
	 * 
	 * @return String
	 */
	public String getcreatedate() {	return createdate;	}
	/**
	 * 
	 * @param iscenter
	 */
	public void setiscenter(String iscenter) {	this.iscenter = iscenter;	}
	/**
	 * 
	 * @return String
	 */
	public String getiscenter() {	return iscenter;	}
	/**
	 * 证件类型
	 * @param paperworktype
	 */
	public void setpaperworktype(String paperworktype) {	this.paperworktype = paperworktype;	}
	/**
	 * 证件类型
	 * @return String
	 */
	public String getpaperworktype() {	return paperworktype;	}
	/**
	 * 证件号码
	 * @param paperworknumber
	 */
	public void setpaperworknumber(String paperworknumber) {	this.paperworknumber = paperworknumber;	}
	/**
	 * 证件号码
	 * @return String
	 */
	public String getpaperworknumber() {	return paperworknumber;	}
	/**
	 * 证件扫描件
	 * @param paperworknumberatt
	 */
	public void setpaperworknumberatt(String paperworknumberatt) {	this.paperworknumberatt = paperworknumberatt;	}
	/**
	 * 证件扫描件
	 * @return String
	 */
	public String getpaperworknumberatt() {	return paperworknumberatt;	}
	/**
	 * 工作部门
	 * @param workdepartment
	 */
	public void setworkdepartment(String workdepartment) {	this.workdepartment = workdepartment;	}
	/**
	 * 工作部门
	 * @return String
	 */
	public String getworkdepartment() {	return workdepartment;	}
	/**
	 * 研究领域
	 * @param researchfield
	 */
	public void setresearchfield(String researchfield) {	this.researchfield = researchfield;	}
	/**
	 * 研究领域
	 * @return String
	 */
	public String getresearchfield() {	return researchfield;	}
	/**
	 * 其他信息
	 * @param other
	 */
	public void setother(String other) {	this.other = other;	}
	/**
	 * 其他信息
	 * @return String
	 */
	public String getother() {	return other;	}
	/**
	 * 地区
	 * @param region
	 */
	public void setregion(String region) {	this.region = region;	}
	/**
	 * 地区
	 * @return String
	 */
	public String getregion() {	return region;	}
	/**
	 *  工作简历
	 * @param resume
	 */
	public void setresume(String resume) {	this.resume = resume;	}
	/**
	 *  工作简历
	 * @return String
	 */
	public String getresume() {	return resume;	}
	/**
	 * 照片
	 * @param photo
	 */
	public void setphoto(String photo) {	this.photo = photo;	}
	/**
	 * 照片
	 * @return String
	 */
	public String getphoto() {	return photo;	}
	/**
	 * 所学专长
	 * @param specialty
	 */
	public void setspecialty(String specialty) {	this.specialty = specialty;	}
	/**
	 * 所学专长
	 * @return String
	 */
	public String getspecialty() {	return specialty;	}
	/**
	 * 年龄
	 * @param age
	 */
	public void setage(String age) {	this.age = age;	}
	/**
	 * 年龄
	 * @return String
	 */
	public String getage() {	return age;	}
	/**
	 * 曾参与评估课题
	 * @param participate
	 */
	public void setparticipate(String participate) {	this.participate = participate;	}
	/**
	 * 曾参与评估课题
	 * @return String
	 */
	public String getparticipate() {	return participate;	}
	/**
	 * 专家评价
	 * @param evaluate
	 */
	public void setevaluate(String evaluate) {	this.evaluate = evaluate;	}
	/**
	 * 专家评价
	 * @return String
	 */
	public String getevaluate() {	return evaluate;	}
	/**
	 * 是否退休
	 * @param isretirement
	 */
	public void setisretirement(String isretirement) {	this.isretirement = isretirement;	}
	/**
	 * 是否退休
	 * @return String
	 */
	public String getisretirement() {	return isretirement;	}
	/**
	 * 开户行
	 * @param bankofdeposit
	 */
	public void setbankofdeposit(String bankofdeposit) {	this.bankofdeposit = bankofdeposit;	}
	/**
	 * 开户行
	 * @return String
	 */
	public String getbankofdeposit() {	return bankofdeposit;	}
	/**
	 * 开户名
	 * @param accountname
	 */
	public void setaccountname(String accountname) {	this.accountname = accountname;	}
	/**
	 * 开户名
	 * @return String
	 */
	public String getaccountname() {	return accountname;	}
	/**
	 * 银行卡号
	 * @param accountnumber
	 */
	public void setaccountnumber(String accountnumber) {	this.accountnumber = accountnumber;	}
	/**
	 * 银行卡号
	 * @return String
	 */
	public String getaccountnumber() {	return accountnumber;	}
	/**
	 * 
	 * @param isexpert
	 */
	public void setisexpert(String isexpert) {	this.isexpert = isexpert;	}
	/**
	 * 
	 * @return String
	 */
	public String getisexpert() {	return isexpert;	}
	/**
	 * 人员编号
	 * @param number
	 */
	public void setnumber(String number) {	this.number = number;	}
	/**
	 * 人员编号
	 * @return String
	 */
	public String getnumber() {	return number;	}
	/**
	 * 
	 * @param englishname
	 */
	public void setenglishname(String englishname) {	this.englishname = englishname;	}
	/**
	 * 
	 * @return String
	 */
	public String getenglishname() {	return englishname;	}
	/**
	 * 
	 * @param oldname
	 */
	public void setoldname(String oldname) {	this.oldname = oldname;	}
	/**
	 * 
	 * @return String
	 */
	public String getoldname() {	return oldname;	}
	/**
	 * 
	 * @param jszwrmsj
	 */
	public void setjszwrmsj(String jszwrmsj) {	this.jszwrmsj = jszwrmsj;	}
	/**
	 * 
	 * @return String
	 */
	public String getjszwrmsj() {	return jszwrmsj;	}
	/**
	 * 国籍,YM
	 * @param nationality
	 */
	public void setnationality(String nationality) {	this.nationality = nationality;	}
	/**
	 * 国籍,YM
	 * @return String
	 */
	public String getnationality() {	return nationality;	}
	/**
	 * 
	 * @param addressphone
	 */
	public void setaddressphone(String addressphone) {	this.addressphone = addressphone;	}
	/**
	 * 
	 * @return String
	 */
	public String getaddressphone() {	return addressphone;	}
	/**
	 * 
	 * @param addressportraiture
	 */
	public void setaddressportraiture(String addressportraiture) {	this.addressportraiture = addressportraiture;	}
	/**
	 * 
	 * @return String
	 */
	public String getaddressportraiture() {	return addressportraiture;	}
	/**
	 * 传呼机
	 * @param bleeper
	 */
	public void setbleeper(String bleeper) {	this.bleeper = bleeper;	}
	/**
	 * 传呼机
	 * @return String
	 */
	public String getbleeper() {	return bleeper;	}
	/**
	 * 个人网址
	 * @param personweb
	 */
	public void setpersonweb(String personweb) {	this.personweb = personweb;	}
	/**
	 * 个人网址
	 * @return String
	 */
	public String getpersonweb() {	return personweb;	}
	/**
	 * 是否为博导
	 * @param isbodao
	 */
	public void setisbodao(String isbodao) {	this.isbodao = isbodao;	}
	/**
	 * 是否为博导
	 * @return String
	 */
	public String getisbodao() {	return isbodao;	}
	/**
	 * 第一外语，FS
	 * @param language1
	 */
	public void setlanguage1(String language1) {	this.language1 = language1;	}
	/**
	 * 第一外语，FS
	 * @return String
	 */
	public String getlanguage1() {	return language1;	}
	/**
	 * 第一外语程度，QD
	 * @param language1lever
	 */
	public void setlanguage1lever(String language1lever) {	this.language1lever = language1lever;	}
	/**
	 * 第一外语程度，QD
	 * @return String
	 */
	public String getlanguage1lever() {	return language1lever;	}
	/**
	 * 第二外语，FS
	 * @param language2
	 */
	public void setlanguage2(String language2) {	this.language2 = language2;	}
	/**
	 * 第二外语，FS
	 * @return String
	 */
	public String getlanguage2() {	return language2;	}
	/**
	 * 第二外语程度，QD
	 * @param language2lever
	 */
	public void setlanguage2lever(String language2lever) {	this.language2lever = language2lever;	}
	/**
	 * 第二外语程度，QD
	 * @return String
	 */
	public String getlanguage2lever() {	return language2lever;	}
	/**
	 * 进修情况
	 * @param learningstate
	 */
	public void setlearningstate(String learningstate) {	this.learningstate = learningstate;	}
	/**
	 * 进修情况
	 * @return String
	 */
	public String getlearningstate() {	return learningstate;	}
	/**
	 * 学术兼职
	 * @param academicactivities
	 */
	public void setacademicactivities(String academicactivities) {	this.academicactivities = academicactivities;	}
	/**
	 * 学术兼职
	 * @return String
	 */
	public String getacademicactivities() {	return academicactivities;	}
	/**
	 * 特长
	 * @param speciality
	 */
	public void setspeciality(String speciality) {	this.speciality = speciality;	}
	/**
	 * 特长
	 * @return String
	 */
	public String getspeciality() {	return speciality;	}
	/**
	 * 
	 * @param jszw
	 */
	public void setjszw(String jszw) {	this.jszw = jszw;	}
	/**
	 * 
	 * @return String
	 */
	public String getjszw() {	return jszw;	}
	/**
	 * 
	 * @param mqzt
	 */
	public void setmqzt(String mqzt) {	this.mqzt = mqzt;	}
	/**
	 * 
	 * @return String
	 */
	public String getmqzt() {	return mqzt;	}
	/**
	 * 
	 * @param score
	 */
	public void setscore(String score) {	this.score = score;	}
	/**
	 * 
	 * @return String
	 */
	public String getscore() {	return score;	}
	/**
	 * 负责人
	 * @param head
	 */
	public void sethead(String head) {	this.head = head;	}
	/**
	 * 负责人
	 * @return String
	 */
	public String gethead() {	return head;	}
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
	 * 成立时间
	 * @param establishdate
	 */
	public void setestablishdate(String establishdate) {	this.establishdate = establishdate;	}
	/**
	 * 成立时间
	 * @return String
	 */
	public String getestablishdate() {	return establishdate;	}
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
	 * 电话
	 * @param telephone
	 */
	public void settelephone(String telephone) {	this.telephone = telephone;	}
	/**
	 * 电话
	 * @return String
	 */
	public String gettelephone() {	return telephone;	}
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
	 * 所属教研室
	 * @param affiliationroom
	 */
	public void setaffiliationroom(String affiliationroom) {	this.affiliationroom = affiliationroom;	}
	/**
	 * 所属教研室
	 * @return String
	 */
	public String getaffiliationroom() {	return affiliationroom;	}
	/**
	 * 人事所在单位
	 * @param personnelunit
	 */
	public void setpersonnelunit(String personnelunit) {	this.personnelunit = personnelunit;	}
	/**
	 * 人事所在单位
	 * @return String
	 */
	public String getpersonnelunit() {	return personnelunit;	}
	/**
	 * 政治面貌
	 * @param politicslook
	 */
	public void setpoliticslook(String politicslook) {	this.politicslook = politicslook;	}
	/**
	 * 政治面貌
	 * @return String
	 */
	public String getpoliticslook() {	return politicslook;	}
	/**
	 * 是否统计
	 * @param isstatistics
	 */
	public void setisstatistics(String isstatistics) {	this.isstatistics = isstatistics;	}
	/**
	 * 是否统计
	 * @return String
	 */
	public String getisstatistics() {	return isstatistics;	}
	/**
	 * 个人简介
	 * @param personalbrief
	 */
	public void setpersonalbrief(String personalbrief) {	this.personalbrief = personalbrief;	}
	/**
	 * 个人简介
	 * @return String
	 */
	public String getpersonalbrief() {	return personalbrief;	}
	/**
	 * 学科门类
	 * @param disciplines
	 */
	public void setdisciplines(String disciplines) {	this.disciplines = disciplines;	}
	/**
	 * 学科门类
	 * @return String
	 */
	public String getdisciplines() {	return disciplines;	}
	/**
	 * 账户启用时间
	 * @param termstarttime
	 */
	public void settermstarttime(String termstarttime) {	this.termstarttime = termstarttime;	}
	/**
	 * 账户启用时间
	 * @return String
	 */
	public String gettermstarttime() {	return termstarttime;	}
	/**
	 * 账户停用时间
	 * @param termendtime
	 */
	public void settermendtime(String termendtime) {	this.termendtime = termendtime;	}
	/**
	 * 账户停用时间
	 * @return String
	 */
	public String gettermendtime() {	return termendtime;	}
	/**
	 * 
	 * @param qq
	 */
	public void setqq(String qq) {	this.qq = qq;	}
	/**
	 * 
	 * @return String
	 */
	public String getqq() {	return qq;	}
	/**
	 * 
	 * @param msn
	 */
	public void setmsn(String msn) {	this.msn = msn;	}
	/**
	 * 
	 * @return String
	 */
	public String getmsn() {	return msn;	}
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
	 * @param subjecttype02name
	 */
	public void setsubjecttype02name(String subjecttype02name) {	this.subjecttype02name = subjecttype02name;	}
	/**
	 * 
	 * @return String
	 */
	public String getsubjecttype02name() {	return subjecttype02name;	}
	/**
	 * 
	 * @param subjecttype03name
	 */
	public void setsubjecttype03name(String subjecttype03name) {	this.subjecttype03name = subjecttype03name;	}
	/**
	 * 
	 * @return String
	 */
	public String getsubjecttype03name() {	return subjecttype03name;	}
	/**
	 * 
	 * @param subjecttypename
	 */
	public void setsubjecttypename(String subjecttypename) {	this.subjecttypename = subjecttypename;	}
	/**
	 * 
	 * @return String
	 */
	public String getsubjecttypename() {	return subjecttypename;	}
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
	/**
	 * 
	 * @param fullpinyin
	 */
	public void setfullpinyin(String fullpinyin) {	this.fullpinyin = fullpinyin;	}
	/**
	 * 
	 * @return String
	 */
	public String getfullpinyin() {	return fullpinyin;	}
	/**
	 * 
	 * @param simplepinyin
	 */
	public void setsimplepinyin(String simplepinyin) {	this.simplepinyin = simplepinyin;	}
	/**
	 * 
	 * @return String
	 */
	public String getsimplepinyin() {	return simplepinyin;	}
	

	public SM_Users GetdecodeUtf(){
		super.GetdecodeUtf();
		try {
			String useridnot = this.getuseridnot();
			if(StringUtil.isNotEmpty(useridnot)){
				useridnot = java.net.URLDecoder.decode(useridnot,"UTF-8");
				this.setuseridnot(useridnot);
			}
			String usertype = this.getusertype();
			if(StringUtil.isNotEmpty(usertype)){
				usertype = java.net.URLDecoder.decode(usertype,"UTF-8");
				this.setusertype(usertype);
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
			String rdmid = this.getrdmid();
			if(StringUtil.isNotEmpty(rdmid)){
				rdmid = java.net.URLDecoder.decode(rdmid,"UTF-8");
				this.setrdmid(rdmid);
			}
			String isrdm = this.getisrdm();
			if(StringUtil.isNotEmpty(isrdm)){
				isrdm = java.net.URLDecoder.decode(isrdm,"UTF-8");
				this.setisrdm(isrdm);
			}
			String rdmallrole = this.getrdmallrole();
			if(StringUtil.isNotEmpty(rdmallrole)){
				rdmallrole = java.net.URLDecoder.decode(rdmallrole,"UTF-8");
				this.setrdmallrole(rdmallrole);
			}
			String mark = this.getMark();
			if(StringUtil.isNotEmpty(mark)){
				mark = java.net.URLDecoder.decode(mark,"UTF-8");
				this.setMark(mark);
			}
			String outdate = this.getoutdate();
			if(StringUtil.isNotEmpty(outdate)){
				outdate = java.net.URLDecoder.decode(outdate,"UTF-8");
				this.setoutdate(outdate);
			}
			String validatacode = this.getvalidatacode();
			if(StringUtil.isNotEmpty(validatacode)){
				validatacode = java.net.URLDecoder.decode(validatacode,"UTF-8");
				this.setvalidatacode(validatacode);
			}
			String avatar = this.getavatar();
			if(StringUtil.isNotEmpty(avatar)){
				avatar = java.net.URLDecoder.decode(avatar,"UTF-8");
				this.setavatar(avatar);
			}
			
			
			
			
			
			String keysysid = this.getkeysysid();
			if(StringUtil.isNotEmpty(keysysid)){
				keysysid = java.net.URLDecoder.decode(keysysid,"UTF-8");
				this.setkeysysid(keysysid);
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
			String jobno = this.getjobno();
			if(StringUtil.isNotEmpty(jobno)){
				jobno = java.net.URLDecoder.decode(jobno,"UTF-8");
				this.setjobno(jobno);
			}
			String affiliation = this.getaffiliation();
			if(StringUtil.isNotEmpty(affiliation)){
				affiliation = java.net.URLDecoder.decode(affiliation,"UTF-8");
				this.setaffiliation(affiliation);
			}
			String affiliationname = this.getaffiliationname();
			if(StringUtil.isNotEmpty(affiliationname)){
				affiliationname = java.net.URLDecoder.decode(affiliationname,"UTF-8");
				this.setaffiliationname(affiliationname);
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
			String homeaddress = this.gethomeaddress();
			if(StringUtil.isNotEmpty(homeaddress)){
				homeaddress = java.net.URLDecoder.decode(homeaddress,"UTF-8");
				this.sethomeaddress(homeaddress);
			}
			String sex = this.getsex();
			if(StringUtil.isNotEmpty(sex)){
				sex = java.net.URLDecoder.decode(sex,"UTF-8");
				this.setsex(sex);
			}
			String sexname = this.getsexname();
			if(StringUtil.isNotEmpty(sexname)){
				sexname = java.net.URLDecoder.decode(sexname,"UTF-8");
				this.setsexname(sexname);
			}
			String idcard = this.getidcard();
			if(StringUtil.isNotEmpty(idcard)){
				idcard = java.net.URLDecoder.decode(idcard,"UTF-8");
				this.setidcard(idcard);
			}
			String birthday = this.getbirthday();
			if(StringUtil.isNotEmpty(birthday)){
				birthday = java.net.URLDecoder.decode(birthday,"UTF-8");
				this.setbirthday(birthday);
			}
			String endbirthday = this.getendbirthday();
			if(StringUtil.isNotEmpty(endbirthday)){
				endbirthday = java.net.URLDecoder.decode(endbirthday,"UTF-8");
				this.setendbirthday(endbirthday);
			}
			String startbirthday = this.getstartbirthday();
			if(StringUtil.isNotEmpty(startbirthday)){
				startbirthday = java.net.URLDecoder.decode(startbirthday,"UTF-8");
				this.setstartbirthday(startbirthday);
			}
			String yearbirthday = this.getyearbirthday();
			if(StringUtil.isNotEmpty(yearbirthday)){
				yearbirthday = java.net.URLDecoder.decode(yearbirthday,"UTF-8");
				this.setyearbirthday(yearbirthday);
			}
			String degreetag = this.getdegreetag();
			if(StringUtil.isNotEmpty(degreetag)){
				degreetag = java.net.URLDecoder.decode(degreetag,"UTF-8");
				this.setdegreetag(degreetag);
			}
			String degreetagname = this.getdegreetagname();
			if(StringUtil.isNotEmpty(degreetagname)){
				degreetagname = java.net.URLDecoder.decode(degreetagname,"UTF-8");
				this.setdegreetagname(degreetagname);
			}
			String degree = this.getdegree();
			if(StringUtil.isNotEmpty(degree)){
				degree = java.net.URLDecoder.decode(degree,"UTF-8");
				this.setdegree(degree);
			}
			String degreename = this.getdegreename();
			if(StringUtil.isNotEmpty(degreename)){
				degreename = java.net.URLDecoder.decode(degreename,"UTF-8");
				this.setdegreename(degreename);
			}
			String titlegrade = this.gettitlegrade();
			if(StringUtil.isNotEmpty(titlegrade)){
				titlegrade = java.net.URLDecoder.decode(titlegrade,"UTF-8");
				this.settitlegrade(titlegrade);
			}
			String titlegradename = this.gettitlegradename();
			if(StringUtil.isNotEmpty(titlegradename)){
				titlegradename = java.net.URLDecoder.decode(titlegradename,"UTF-8");
				this.settitlegradename(titlegradename);
			}
			String title = this.gettitle();
			if(StringUtil.isNotEmpty(title)){
				title = java.net.URLDecoder.decode(title,"UTF-8");
				this.settitle(title);
			}
			String titlename = this.gettitlename();
			if(StringUtil.isNotEmpty(titlename)){
				titlename = java.net.URLDecoder.decode(titlename,"UTF-8");
				this.settitlename(titlename);
			}
			String titletype = this.gettitletype();
			if(StringUtil.isNotEmpty(titletype)){
				titletype = java.net.URLDecoder.decode(titletype,"UTF-8");
				this.settitletype(titletype);
			}
			String titletypename = this.gettitletypename();
			if(StringUtil.isNotEmpty(titletypename)){
				titletypename = java.net.URLDecoder.decode(titletypename,"UTF-8");
				this.settitletypename(titletypename);
			}
			String joborientationdate = this.getjoborientationdate();
			if(StringUtil.isNotEmpty(joborientationdate)){
				joborientationdate = java.net.URLDecoder.decode(joborientationdate,"UTF-8");
				this.setjoborientationdate(joborientationdate);
			}
			String titletag = this.gettitletag();
			if(StringUtil.isNotEmpty(titletag)){
				titletag = java.net.URLDecoder.decode(titletag,"UTF-8");
				this.settitletag(titletag);
			}
			String politicalappearancetag = this.getpoliticalappearancetag();
			if(StringUtil.isNotEmpty(politicalappearancetag)){
				politicalappearancetag = java.net.URLDecoder.decode(politicalappearancetag,"UTF-8");
				this.setpoliticalappearancetag(politicalappearancetag);
			}
			String politicalappearancetagname = this.getpoliticalappearancetagname();
			if(StringUtil.isNotEmpty(politicalappearancetagname)){
				politicalappearancetagname = java.net.URLDecoder.decode(politicalappearancetagname,"UTF-8");
				this.setpoliticalappearancetagname(politicalappearancetagname);
			}
			String iscount = this.getiscount();
			if(StringUtil.isNotEmpty(iscount)){
				iscount = java.net.URLDecoder.decode(iscount,"UTF-8");
				this.setiscount(iscount);
			}
			String iscountname = this.getiscountname();
			if(StringUtil.isNotEmpty(iscountname)){
				iscountname = java.net.URLDecoder.decode(iscountname,"UTF-8");
				this.setiscountname(iscountname);
			}
			String systemtype = this.getsystemtype();
			if(StringUtil.isNotEmpty(systemtype)){
				systemtype = java.net.URLDecoder.decode(systemtype,"UTF-8");
				this.setsystemtype(systemtype);
			}
			String systemtypename = this.getsystemtypename();
			if(StringUtil.isNotEmpty(systemtypename)){
				systemtypename = java.net.URLDecoder.decode(systemtypename,"UTF-8");
				this.setsystemtypename(systemtypename);
			}
			String subjecttype = this.getsubjecttype();
			if(StringUtil.isNotEmpty(subjecttype)){
				subjecttype = java.net.URLDecoder.decode(subjecttype,"UTF-8");
				this.setsubjecttype(subjecttype);
			}
			String subjecttype02 = this.getsubjecttype02();
			if(StringUtil.isNotEmpty(subjecttype02)){
				subjecttype02 = java.net.URLDecoder.decode(subjecttype02,"UTF-8");
				this.setsubjecttype02(subjecttype02);
			}
			String subjecttype03 = this.getsubjecttype03();
			if(StringUtil.isNotEmpty(subjecttype03)){
				subjecttype03 = java.net.URLDecoder.decode(subjecttype03,"UTF-8");
				this.setsubjecttype03(subjecttype03);
			}
			String subject = this.getsubject();
			if(StringUtil.isNotEmpty(subject)){
				subject = java.net.URLDecoder.decode(subject,"UTF-8");
				this.setsubject(subject);
			}
			String email = this.getemail();
			if(StringUtil.isNotEmpty(email)){
				email = java.net.URLDecoder.decode(email,"UTF-8");
				this.setemail(email);
			}
			String officephone = this.getofficephone();
			if(StringUtil.isNotEmpty(officephone)){
				officephone = java.net.URLDecoder.decode(officephone,"UTF-8");
				this.setofficephone(officephone);
			}
			String fax = this.getfax();
			if(StringUtil.isNotEmpty(fax)){
				fax = java.net.URLDecoder.decode(fax,"UTF-8");
				this.setfax(fax);
			}
			String mobilephone = this.getmobilephone();
			if(StringUtil.isNotEmpty(mobilephone)){
				mobilephone = java.net.URLDecoder.decode(mobilephone,"UTF-8");
				this.setmobilephone(mobilephone);
			}
			String qqaccount = this.getqqaccount();
			if(StringUtil.isNotEmpty(qqaccount)){
				qqaccount = java.net.URLDecoder.decode(qqaccount,"UTF-8");
				this.setqqaccount(qqaccount);
			}
			String address = this.getaddress();
			if(StringUtil.isNotEmpty(address)){
				address = java.net.URLDecoder.decode(address,"UTF-8");
				this.setaddress(address);
			}
			String zipcode = this.getzipcode();
			if(StringUtil.isNotEmpty(zipcode)){
				zipcode = java.net.URLDecoder.decode(zipcode,"UTF-8");
				this.setzipcode(zipcode);
			}
			String remark = this.getremark();
			if(StringUtil.isNotEmpty(remark)){
				remark = java.net.URLDecoder.decode(remark,"UTF-8");
				this.setremark(remark);
			}
			String remark2 = this.getremark2();
			if(StringUtil.isNotEmpty(remark2)){
				remark2 = java.net.URLDecoder.decode(remark2,"UTF-8");
				this.setremark2(remark2);
			}
			String paperworktypename = this.getpaperworktypename();
			if(StringUtil.isNotEmpty(paperworktypename)){
				paperworktypename = java.net.URLDecoder.decode(paperworktypename,"UTF-8");
				this.setpaperworktypename(paperworktypename);
			}
			String countrytagname = this.getcountrytagname();
			if(StringUtil.isNotEmpty(countrytagname)){
				countrytagname = java.net.URLDecoder.decode(countrytagname,"UTF-8");
				this.setcountrytagname(countrytagname);
			}
			String nationtagname = this.getnationtagname();
			if(StringUtil.isNotEmpty(nationtagname)){
				nationtagname = java.net.URLDecoder.decode(nationtagname,"UTF-8");
				this.setnationtagname(nationtagname);
			}
			String isbodaoname = this.getisbodaoname();
			if(StringUtil.isNotEmpty(isbodaoname)){
				isbodaoname = java.net.URLDecoder.decode(isbodaoname,"UTF-8");
				this.setisbodaoname(isbodaoname);
			}
			String language1name = this.getlanguage1name();
			if(StringUtil.isNotEmpty(language1name)){
				language1name = java.net.URLDecoder.decode(language1name,"UTF-8");
				this.setlanguage1name(language1name);
			}
			String language1levername = this.getlanguage1levername();
			if(StringUtil.isNotEmpty(language1levername)){
				language1levername = java.net.URLDecoder.decode(language1levername,"UTF-8");
				this.setlanguage1levername(language1levername);
			}
			String language2name = this.getlanguage2name();
			if(StringUtil.isNotEmpty(language2name)){
				language2name = java.net.URLDecoder.decode(language2name,"UTF-8");
				this.setlanguage2name(language2name);
			}
			String language2levername = this.getlanguage2levername();
			if(StringUtil.isNotEmpty(language2levername)){
				language2levername = java.net.URLDecoder.decode(language2levername,"UTF-8");
				this.setlanguage2levername(language2levername);
			}
			String isretirementname = this.getisretirementname();
			if(StringUtil.isNotEmpty(isretirementname)){
				isretirementname = java.net.URLDecoder.decode(isretirementname,"UTF-8");
				this.setisretirementname(isretirementname);
			}
			String userid = this.getuserid();
			if(StringUtil.isNotEmpty(userid)){
				userid = java.net.URLDecoder.decode(userid,"UTF-8");
				this.setuserid(userid);
			}
			String password = this.getpassword();
			if(StringUtil.isNotEmpty(password)){
				password = java.net.URLDecoder.decode(password,"UTF-8");
				this.setpassword(password);
			}
			String oldpassword = this.getoldpassword();
			if(StringUtil.isNotEmpty(oldpassword)){
				oldpassword = java.net.URLDecoder.decode(oldpassword,"UTF-8");
				this.setoldpassword(oldpassword);
			}
			String secondpassword = this.getsecondpassword();
			if(StringUtil.isNotEmpty(secondpassword)){
				secondpassword = java.net.URLDecoder.decode(secondpassword,"UTF-8");
				this.setsecondpassword(secondpassword);
			}
			String orderid = this.getorderid();
			if(StringUtil.isNotEmpty(orderid)){
				orderid = java.net.URLDecoder.decode(orderid,"UTF-8");
				this.setorderid(orderid);
			}
			String disabled = this.getdisabled();
			if(StringUtil.isNotEmpty(disabled)){
				disabled = java.net.URLDecoder.decode(disabled,"UTF-8");
				this.setdisabled(disabled);
			}
			String style = this.getstyle();
			if(StringUtil.isNotEmpty(style)){
				style = java.net.URLDecoder.decode(style,"UTF-8");
				this.setstyle(style);
			}
			String creatby = this.getcreatby();
			if(StringUtil.isNotEmpty(creatby)){
				creatby = java.net.URLDecoder.decode(creatby,"UTF-8");
				this.setcreatby(creatby);
			}
			String disabledname = this.getdisabledname();
			if(StringUtil.isNotEmpty(disabledname)){
				disabledname = java.net.URLDecoder.decode(disabledname,"UTF-8");
				this.setdisabledname(disabledname);
			}
			String code = this.getcode();
			if(StringUtil.isNotEmpty(code)){
				code = java.net.URLDecoder.decode(code,"UTF-8");
				this.setcode(code);
			}
			String marrid = this.getmarrid();
			if(StringUtil.isNotEmpty(marrid)){
				marrid = java.net.URLDecoder.decode(marrid,"UTF-8");
				this.setmarrid(marrid);
			}
			String countrytag = this.getcountrytag();
			if(StringUtil.isNotEmpty(countrytag)){
				countrytag = java.net.URLDecoder.decode(countrytag,"UTF-8");
				this.setcountrytag(countrytag);
			}
			String nationtag = this.getnationtag();
			if(StringUtil.isNotEmpty(nationtag)){
				nationtag = java.net.URLDecoder.decode(nationtag,"UTF-8");
				this.setnationtag(nationtag);
			}
			String handletag = this.gethandletag();
			if(StringUtil.isNotEmpty(handletag)){
				handletag = java.net.URLDecoder.decode(handletag,"UTF-8");
				this.sethandletag(handletag);
			}
			String postiontag = this.getpostiontag();
			if(StringUtil.isNotEmpty(postiontag)){
				postiontag = java.net.URLDecoder.decode(postiontag,"UTF-8");
				this.setpostiontag(postiontag);
			}
			String postionname = this.getpostionname();
			if(StringUtil.isNotEmpty(postionname)){
				postionname = java.net.URLDecoder.decode(postionname,"UTF-8");
				this.setpostionname(postionname);
			}
			String professionaltag = this.getprofessionaltag();
			if(StringUtil.isNotEmpty(professionaltag)){
				professionaltag = java.net.URLDecoder.decode(professionaltag,"UTF-8");
				this.setprofessionaltag(professionaltag);
			}
			String workday = this.getworkday();
			if(StringUtil.isNotEmpty(workday)){
				workday = java.net.URLDecoder.decode(workday,"UTF-8");
				this.setworkday(workday);
			}
			String entersday = this.getentersday();
			if(StringUtil.isNotEmpty(entersday)){
				entersday = java.net.URLDecoder.decode(entersday,"UTF-8");
				this.setentersday(entersday);
			}
			String leavesday = this.getleavesday();
			if(StringUtil.isNotEmpty(leavesday)){
				leavesday = java.net.URLDecoder.decode(leavesday,"UTF-8");
				this.setleavesday(leavesday);
			}
			String familyphone = this.getfamilyphone();
			if(StringUtil.isNotEmpty(familyphone)){
				familyphone = java.net.URLDecoder.decode(familyphone,"UTF-8");
				this.setfamilyphone(familyphone);
			}
			String stafftype = this.getstafftype();
			if(StringUtil.isNotEmpty(stafftype)){
				stafftype = java.net.URLDecoder.decode(stafftype,"UTF-8");
				this.setstafftype(stafftype);
			}
			String checkdirection = this.getcheckdirection();
			if(StringUtil.isNotEmpty(checkdirection)){
				checkdirection = java.net.URLDecoder.decode(checkdirection,"UTF-8");
				this.setcheckdirection(checkdirection);
			}
			String fame = this.getfame();
			if(StringUtil.isNotEmpty(fame)){
				fame = java.net.URLDecoder.decode(fame,"UTF-8");
				this.setfame(fame);
			}
			String businessname = this.getbusinessname();
			if(StringUtil.isNotEmpty(businessname)){
				businessname = java.net.URLDecoder.decode(businessname,"UTF-8");
				this.setbusinessname(businessname);
			}
			String unitcode = this.getunitcode();
			if(StringUtil.isNotEmpty(unitcode)){
				unitcode = java.net.URLDecoder.decode(unitcode,"UTF-8");
				this.setunitcode(unitcode);
			}
			String permitcode = this.getpermitcode();
			if(StringUtil.isNotEmpty(permitcode)){
				permitcode = java.net.URLDecoder.decode(permitcode,"UTF-8");
				this.setpermitcode(permitcode);
			}
			String taxcode = this.gettaxcode();
			if(StringUtil.isNotEmpty(taxcode)){
				taxcode = java.net.URLDecoder.decode(taxcode,"UTF-8");
				this.settaxcode(taxcode);
			}
			String registeradress = this.getregisteradress();
			if(StringUtil.isNotEmpty(registeradress)){
				registeradress = java.net.URLDecoder.decode(registeradress,"UTF-8");
				this.setregisteradress(registeradress);
			}
			String registermoney = this.getregistermoney();
			if(StringUtil.isNotEmpty(registermoney)){
				registermoney = java.net.URLDecoder.decode(registermoney,"UTF-8");
				this.setregistermoney(registermoney);
			}
			String unionphone = this.getunionphone();
			if(StringUtil.isNotEmpty(unionphone)){
				unionphone = java.net.URLDecoder.decode(unionphone,"UTF-8");
				this.setunionphone(unionphone);
			}
			String isoutside = this.getisoutside();
			if(StringUtil.isNotEmpty(isoutside)){
				isoutside = java.net.URLDecoder.decode(isoutside,"UTF-8");
				this.setisoutside(isoutside);
			}
			String createdate = this.getcreatedate();
			if(StringUtil.isNotEmpty(createdate)){
				createdate = java.net.URLDecoder.decode(createdate,"UTF-8");
				this.setcreatedate(createdate);
			}
			String iscenter = this.getiscenter();
			if(StringUtil.isNotEmpty(iscenter)){
				iscenter = java.net.URLDecoder.decode(iscenter,"UTF-8");
				this.setiscenter(iscenter);
			}
			String paperworktype = this.getpaperworktype();
			if(StringUtil.isNotEmpty(paperworktype)){
				paperworktype = java.net.URLDecoder.decode(paperworktype,"UTF-8");
				this.setpaperworktype(paperworktype);
			}
			String paperworknumber = this.getpaperworknumber();
			if(StringUtil.isNotEmpty(paperworknumber)){
				paperworknumber = java.net.URLDecoder.decode(paperworknumber,"UTF-8");
				this.setpaperworknumber(paperworknumber);
			}
			String paperworknumberatt = this.getpaperworknumberatt();
			if(StringUtil.isNotEmpty(paperworknumberatt)){
				paperworknumberatt = java.net.URLDecoder.decode(paperworknumberatt,"UTF-8");
				this.setpaperworknumberatt(paperworknumberatt);
			}
			String workdepartment = this.getworkdepartment();
			if(StringUtil.isNotEmpty(workdepartment)){
				workdepartment = java.net.URLDecoder.decode(workdepartment,"UTF-8");
				this.setworkdepartment(workdepartment);
			}
			String researchfield = this.getresearchfield();
			if(StringUtil.isNotEmpty(researchfield)){
				researchfield = java.net.URLDecoder.decode(researchfield,"UTF-8");
				this.setresearchfield(researchfield);
			}
			String other = this.getother();
			if(StringUtil.isNotEmpty(other)){
				other = java.net.URLDecoder.decode(other,"UTF-8");
				this.setother(other);
			}
			String region = this.getregion();
			if(StringUtil.isNotEmpty(region)){
				region = java.net.URLDecoder.decode(region,"UTF-8");
				this.setregion(region);
			}
			String resume = this.getresume();
			if(StringUtil.isNotEmpty(resume)){
				resume = java.net.URLDecoder.decode(resume,"UTF-8");
				this.setresume(resume);
			}
			String photo = this.getphoto();
			if(StringUtil.isNotEmpty(photo)){
				photo = java.net.URLDecoder.decode(photo,"UTF-8");
				this.setphoto(photo);
			}
			String specialty = this.getspecialty();
			if(StringUtil.isNotEmpty(specialty)){
				specialty = java.net.URLDecoder.decode(specialty,"UTF-8");
				this.setspecialty(specialty);
			}
			String age = this.getage();
			if(StringUtil.isNotEmpty(age)){
				age = java.net.URLDecoder.decode(age,"UTF-8");
				this.setage(age);
			}
			String participate = this.getparticipate();
			if(StringUtil.isNotEmpty(participate)){
				participate = java.net.URLDecoder.decode(participate,"UTF-8");
				this.setparticipate(participate);
			}
			String evaluate = this.getevaluate();
			if(StringUtil.isNotEmpty(evaluate)){
				evaluate = java.net.URLDecoder.decode(evaluate,"UTF-8");
				this.setevaluate(evaluate);
			}
			String isretirement = this.getisretirement();
			if(StringUtil.isNotEmpty(isretirement)){
				isretirement = java.net.URLDecoder.decode(isretirement,"UTF-8");
				this.setisretirement(isretirement);
			}
			String bankofdeposit = this.getbankofdeposit();
			if(StringUtil.isNotEmpty(bankofdeposit)){
				bankofdeposit = java.net.URLDecoder.decode(bankofdeposit,"UTF-8");
				this.setbankofdeposit(bankofdeposit);
			}
			String accountname = this.getaccountname();
			if(StringUtil.isNotEmpty(accountname)){
				accountname = java.net.URLDecoder.decode(accountname,"UTF-8");
				this.setaccountname(accountname);
			}
			String accountnumber = this.getaccountnumber();
			if(StringUtil.isNotEmpty(accountnumber)){
				accountnumber = java.net.URLDecoder.decode(accountnumber,"UTF-8");
				this.setaccountnumber(accountnumber);
			}
			String isexpert = this.getisexpert();
			if(StringUtil.isNotEmpty(isexpert)){
				isexpert = java.net.URLDecoder.decode(isexpert,"UTF-8");
				this.setisexpert(isexpert);
			}
			String number = this.getnumber();
			if(StringUtil.isNotEmpty(number)){
				number = java.net.URLDecoder.decode(number,"UTF-8");
				this.setnumber(number);
			}
			String englishname = this.getenglishname();
			if(StringUtil.isNotEmpty(englishname)){
				englishname = java.net.URLDecoder.decode(englishname,"UTF-8");
				this.setenglishname(englishname);
			}
			String oldname = this.getoldname();
			if(StringUtil.isNotEmpty(oldname)){
				oldname = java.net.URLDecoder.decode(oldname,"UTF-8");
				this.setoldname(oldname);
			}
			String jszwrmsj = this.getjszwrmsj();
			if(StringUtil.isNotEmpty(jszwrmsj)){
				jszwrmsj = java.net.URLDecoder.decode(jszwrmsj,"UTF-8");
				this.setjszwrmsj(jszwrmsj);
			}
			String nationality = this.getnationality();
			if(StringUtil.isNotEmpty(nationality)){
				nationality = java.net.URLDecoder.decode(nationality,"UTF-8");
				this.setnationality(nationality);
			}
			String addressphone = this.getaddressphone();
			if(StringUtil.isNotEmpty(addressphone)){
				addressphone = java.net.URLDecoder.decode(addressphone,"UTF-8");
				this.setaddressphone(addressphone);
			}
			String addressportraiture = this.getaddressportraiture();
			if(StringUtil.isNotEmpty(addressportraiture)){
				addressportraiture = java.net.URLDecoder.decode(addressportraiture,"UTF-8");
				this.setaddressportraiture(addressportraiture);
			}
			String bleeper = this.getbleeper();
			if(StringUtil.isNotEmpty(bleeper)){
				bleeper = java.net.URLDecoder.decode(bleeper,"UTF-8");
				this.setbleeper(bleeper);
			}
			String personweb = this.getpersonweb();
			if(StringUtil.isNotEmpty(personweb)){
				personweb = java.net.URLDecoder.decode(personweb,"UTF-8");
				this.setpersonweb(personweb);
			}
			String isbodao = this.getisbodao();
			if(StringUtil.isNotEmpty(isbodao)){
				isbodao = java.net.URLDecoder.decode(isbodao,"UTF-8");
				this.setisbodao(isbodao);
			}
			String language1 = this.getlanguage1();
			if(StringUtil.isNotEmpty(language1)){
				language1 = java.net.URLDecoder.decode(language1,"UTF-8");
				this.setlanguage1(language1);
			}
			String language1lever = this.getlanguage1lever();
			if(StringUtil.isNotEmpty(language1lever)){
				language1lever = java.net.URLDecoder.decode(language1lever,"UTF-8");
				this.setlanguage1lever(language1lever);
			}
			String language2 = this.getlanguage2();
			if(StringUtil.isNotEmpty(language2)){
				language2 = java.net.URLDecoder.decode(language2,"UTF-8");
				this.setlanguage2(language2);
			}
			String language2lever = this.getlanguage2lever();
			if(StringUtil.isNotEmpty(language2lever)){
				language2lever = java.net.URLDecoder.decode(language2lever,"UTF-8");
				this.setlanguage2lever(language2lever);
			}
			String learningstate = this.getlearningstate();
			if(StringUtil.isNotEmpty(learningstate)){
				learningstate = java.net.URLDecoder.decode(learningstate,"UTF-8");
				this.setlearningstate(learningstate);
			}
			String academicactivities = this.getacademicactivities();
			if(StringUtil.isNotEmpty(academicactivities)){
				academicactivities = java.net.URLDecoder.decode(academicactivities,"UTF-8");
				this.setacademicactivities(academicactivities);
			}
			String speciality = this.getspeciality();
			if(StringUtil.isNotEmpty(speciality)){
				speciality = java.net.URLDecoder.decode(speciality,"UTF-8");
				this.setspeciality(speciality);
			}
			String jszw = this.getjszw();
			if(StringUtil.isNotEmpty(jszw)){
				jszw = java.net.URLDecoder.decode(jszw,"UTF-8");
				this.setjszw(jszw);
			}
			String mqzt = this.getmqzt();
			if(StringUtil.isNotEmpty(mqzt)){
				mqzt = java.net.URLDecoder.decode(mqzt,"UTF-8");
				this.setmqzt(mqzt);
			}
			String score = this.getscore();
			if(StringUtil.isNotEmpty(score)){
				score = java.net.URLDecoder.decode(score,"UTF-8");
				this.setscore(score);
			}
			String head = this.gethead();
			if(StringUtil.isNotEmpty(head)){
				head = java.net.URLDecoder.decode(head,"UTF-8");
				this.sethead(head);
			}
			String approval = this.getapproval();
			if(StringUtil.isNotEmpty(approval)){
				approval = java.net.URLDecoder.decode(approval,"UTF-8");
				this.setapproval(approval);
			}
			String establishdate = this.getestablishdate();
			if(StringUtil.isNotEmpty(establishdate)){
				establishdate = java.net.URLDecoder.decode(establishdate,"UTF-8");
				this.setestablishdate(establishdate);
			}
			String contacts = this.getcontacts();
			if(StringUtil.isNotEmpty(contacts)){
				contacts = java.net.URLDecoder.decode(contacts,"UTF-8");
				this.setcontacts(contacts);
			}
			String telephone = this.gettelephone();
			if(StringUtil.isNotEmpty(telephone)){
				telephone = java.net.URLDecoder.decode(telephone,"UTF-8");
				this.settelephone(telephone);
			}
			String mechanism = this.getmechanism();
			if(StringUtil.isNotEmpty(mechanism)){
				mechanism = java.net.URLDecoder.decode(mechanism,"UTF-8");
				this.setmechanism(mechanism);
			}
			String affiliationroom = this.getaffiliationroom();
			if(StringUtil.isNotEmpty(affiliationroom)){
				affiliationroom = java.net.URLDecoder.decode(affiliationroom,"UTF-8");
				this.setaffiliationroom(affiliationroom);
			}
			String personnelunit = this.getpersonnelunit();
			if(StringUtil.isNotEmpty(personnelunit)){
				personnelunit = java.net.URLDecoder.decode(personnelunit,"UTF-8");
				this.setpersonnelunit(personnelunit);
			}
			String politicslook = this.getpoliticslook();
			if(StringUtil.isNotEmpty(politicslook)){
				politicslook = java.net.URLDecoder.decode(politicslook,"UTF-8");
				this.setpoliticslook(politicslook);
			}
			String isstatistics = this.getisstatistics();
			if(StringUtil.isNotEmpty(isstatistics)){
				isstatistics = java.net.URLDecoder.decode(isstatistics,"UTF-8");
				this.setisstatistics(isstatistics);
			}
			String personalbrief = this.getpersonalbrief();
			if(StringUtil.isNotEmpty(personalbrief)){
				personalbrief = java.net.URLDecoder.decode(personalbrief,"UTF-8");
				this.setpersonalbrief(personalbrief);
			}
			String disciplines = this.getdisciplines();
			if(StringUtil.isNotEmpty(disciplines)){
				disciplines = java.net.URLDecoder.decode(disciplines,"UTF-8");
				this.setdisciplines(disciplines);
			}
			String termstarttime = this.gettermstarttime();
			if(StringUtil.isNotEmpty(termstarttime)){
				termstarttime = java.net.URLDecoder.decode(termstarttime,"UTF-8");
				this.settermstarttime(termstarttime);
			}
			String termendtime = this.gettermendtime();
			if(StringUtil.isNotEmpty(termendtime)){
				termendtime = java.net.URLDecoder.decode(termendtime,"UTF-8");
				this.settermendtime(termendtime);
			}
			String qq = this.getqq();
			if(StringUtil.isNotEmpty(qq)){
				qq = java.net.URLDecoder.decode(qq,"UTF-8");
				this.setqq(qq);
			}
			String msn = this.getmsn();
			if(StringUtil.isNotEmpty(msn)){
				msn = java.net.URLDecoder.decode(msn,"UTF-8");
				this.setmsn(msn);
			}
			String sinoss_code = this.getsinoss_code();
			if(StringUtil.isNotEmpty(sinoss_code)){
				sinoss_code = java.net.URLDecoder.decode(sinoss_code,"UTF-8");
				this.setsinoss_code(sinoss_code);
			}
			String subjecttype02name = this.getsubjecttype02name();
			if(StringUtil.isNotEmpty(subjecttype02name)){
				subjecttype02name = java.net.URLDecoder.decode(subjecttype02name,"UTF-8");
				this.setsubjecttype02name(subjecttype02name);
			}
			String subjecttype03name = this.getsubjecttype03name();
			if(StringUtil.isNotEmpty(subjecttype03name)){
				subjecttype03name = java.net.URLDecoder.decode(subjecttype03name,"UTF-8");
				this.setsubjecttype03name(subjecttype03name);
			}
			String subjecttypename = this.getsubjecttypename();
			if(StringUtil.isNotEmpty(subjecttypename)){
				subjecttypename = java.net.URLDecoder.decode(subjecttypename,"UTF-8");
				this.setsubjecttypename(subjecttypename);
			}
			String synchroremark = this.getsynchroremark();
			if(StringUtil.isNotEmpty(synchroremark)){
				synchroremark = java.net.URLDecoder.decode(synchroremark,"UTF-8");
				this.setsynchroremark(synchroremark);
			}
			String fullpinyin = this.getfullpinyin();
			if(StringUtil.isNotEmpty(fullpinyin)){
				fullpinyin = java.net.URLDecoder.decode(fullpinyin,"UTF-8");
				this.setfullpinyin(fullpinyin);
			}
			String simplepinyin = this.getsimplepinyin();
			if(StringUtil.isNotEmpty(simplepinyin)){
				simplepinyin = java.net.URLDecoder.decode(simplepinyin,"UTF-8");
				this.setsimplepinyin(simplepinyin);
			}
		} catch (Exception e) {
			System.out.println("中文转码出现问题");
		}
		return this;
	}
}
