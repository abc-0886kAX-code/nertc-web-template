package com.ytxd.model.SystemManage;
import com.ytxd.model.BaseBO;
import com.ytxd.util.StringUtil;
public class SM_CodeItem extends BaseBO {
	private static final long serialVersionUID = 1L;
	private String codeid;  //参数ID
	private String code;  //代码
	private String description;  //代码描述
	private String shortname;  //备注
	private String flag;  //是否可见
	private String pptr;  //父级CodeId
	private String cptr;  //是否含有子集
	private String score;  //分数
	private String orderid;  //排序
	private String haschildren;	//是否有子节点
	private String selectvalue;	//选择的值
	private String checked;	//是否选中
	
	private String sinoss_code;  //社科系统对照
	private String kjtj_code;  //科技系统对照
	private String kjtj_code_value;  //科技系统对照字段Code
	private String systemtype;  //统计分类
	private String systemtypename;  //统计分类
	private String orderauto;  //CodeId整体排序
	private String sinoss_code_value;  //
	
	
	// 拆分in()查询字段到数组
	private String[] codeidarray;
	public void setcodeidarray(String[] codeidarray) {
		this.codeidarray = codeidarray;
	}
	public String[] getcodeidarray() {
		return codeidarray;
	}
	private String[] codearray;
	public void setcodearray(String[] codearray) {
		this.codearray = codearray;
	}
	public String[] getcodearray() {
		return codearray;
	}

	
	
	//用于细化过滤条件
	private String codenotin; //过滤掉哪些code
	private String pptrnotin; //过滤掉哪些pptr
	private String codelength;//获取指定长度的code
	private String codelikepre;//code以xx开头
	private String codelikesuf;//code以xx结尾
	private String nothing;//如果为'01'，就表示什么都不获取，返回空
	
	
	
	public String getCodenotin() {
		return codenotin;
	}
	public void setCodenotin(String codenotin) {
		this.codenotin = codenotin;
	}
	public String getPptrnotin() {
		return pptrnotin;
	}
	public void setPptrnotin(String pptrnotin) {
		this.pptrnotin = pptrnotin;
	}
	public String getCodelength() {
		return codelength;
	}
	public void setCodelength(String codelength) {
		this.codelength = codelength;
	}
	public String getCodelikepre() {
		return codelikepre;
	}
	public void setCodelikepre(String codelikepre) {
		this.codelikepre = codelikepre;
	}
	public String getCodelikesuf() {
		return codelikesuf;
	}
	public void setCodelikesuf(String codelikesuf) {
		this.codelikesuf = codelikesuf;
	}
	public String getNothing() {
		return nothing;
	}
	public void setNothing(String nothing) {
		this.nothing = nothing;
	}
	/**
	 * 参数ID
	 * @param codeid
	 */
	public void setcodeid(String codeid) {	this.codeid = codeid;if(StringUtil.isNotEmpty(codeid)){ this.setcodeidarray(codeid.replace("'", "").split(","));this.setidarray(codeid.replace("'", "").split(","));}	}
	/**
	 * 参数ID
	 * @return String
	 */
	public String getcodeid() {	return codeid;	}
	/**
	 * 代码
	 * @param code
	 */
	public void setcode(String code) {	this.code = code; if(StringUtil.isNotEmpty(code)){this.setcodearray(code.replace("'", "").split(","));}	}
	/**
	 * 代码
	 * @return String
	 */
	public String getcode() {	return code;	}
	/**
	 * 代码描述
	 * @param description
	 */
	public void setdescription(String description) {	this.description = description;	}
	/**
	 * 代码描述
	 * @return String
	 */
	public String getdescription() {	return description;	}
	/**
	 * 备注
	 * @param shortname
	 */
	public void setshortname(String shortname) {	this.shortname = shortname;	}
	/**
	 * 备注
	 * @return String
	 */
	public String getshortname() {	return shortname;	}
	/**
	 * 是否可见
	 * @param flag
	 */
	public void setflag(String flag) {	this.flag = flag;	}
	/**
	 * 是否可见
	 * @return String
	 */
	public String getflag() {	return flag;	}
	/**
	 * 父级CodeId
	 * @param pptr
	 */
	public void setpptr(String pptr) {	this.pptr = pptr;	}
	/**
	 * 父级CodeId
	 * @return String
	 */
	public String getpptr() {	return pptr;	}
	/**
	 * 是否含有子集
	 * @param cptr
	 */
	public void setcptr(String cptr) {	this.cptr = cptr;	}
	/**
	 * 是否含有子集
	 * @return String
	 */
	public String getcptr() {	return cptr;	}
	/**
	 * 分数
	 * @param score
	 */
	public void setscore(String score) {	this.score = score;	}
	/**
	 * 分数
	 * @return String
	 */
	public String getscore() {	return score;	}
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
	 * 社科系统对照
	 * @param sinoss_code
	 */
	public void setsinoss_code(String sinoss_code) {	this.sinoss_code = sinoss_code;	}
	/**
	 * 社科系统对照
	 * @return String
	 */
	public String getsinoss_code() {	return sinoss_code;	}
	/**
	 * 科技系统对照
	 * @param kjtj_code
	 */
	public void setkjtj_code(String kjtj_code) {	this.kjtj_code = kjtj_code;	}
	/**
	 * 科技系统对照
	 * @return String
	 */
	public String getkjtj_code() {	return kjtj_code;	}
	/**
	 * 科技系统对照字段Code
	 * @param kjtj_code_value
	 */
	public void setkjtj_code_value(String kjtj_code_value) {	this.kjtj_code_value = kjtj_code_value;	}
	/**
	 * 科技系统对照字段Code
	 * @return String
	 */
	public String getkjtj_code_value() {	return kjtj_code_value;	}
	/**
	 * 统计分类
	 * @param systemtype
	 */
	public void setsystemtype(String systemtype) {	this.systemtype = systemtype;	}
	/**
	 * 统计分类
	 * @return String
	 */
	public String getsystemtype() {	return systemtype;	}
	/**
	 * 统计分类
	 * @param systemtypename
	 */
	public void setsystemtypename(String systemtypename) {	this.systemtypename = systemtypename;	}
	/**
	 * 统计分类
	 * @return String
	 */
	public String getsystemtypename() {	return systemtypename;	}
	/**
	 * CodeId整体排序
	 * @param orderauto
	 */
	public void setorderauto(String orderauto) {	this.orderauto = orderauto;	}
	/**
	 * CodeId整体排序
	 * @return String
	 */
	public String getorderauto() {	return orderauto;	}
	/**
	 * 
	 * @param sinoss_code_value
	 */
	public void setsinoss_code_value(String sinoss_code_value) {	this.sinoss_code_value = sinoss_code_value;	}
	/**
	 * 
	 * @return String
	 */
	public String getsinoss_code_value() {	return sinoss_code_value;	}

	public SM_CodeItem GetdecodeUtf(){
		super.GetdecodeUtf();
		try {
			String codeid = this.getcodeid();
			if(StringUtil.isNotEmpty(codeid)){
				codeid = java.net.URLDecoder.decode(codeid,"UTF-8");
				this.setcodeid(codeid);
			}
			String code = this.getcode();
			if(StringUtil.isNotEmpty(code)){
				code = java.net.URLDecoder.decode(code,"UTF-8");
				this.setcode(code);
			}
			String description = this.getdescription();
			if(StringUtil.isNotEmpty(description)){
				description = java.net.URLDecoder.decode(description,"UTF-8");
				this.setdescription(description);
			}
			String shortname = this.getshortname();
			if(StringUtil.isNotEmpty(shortname)){
				shortname = java.net.URLDecoder.decode(shortname,"UTF-8");
				this.setshortname(shortname);
			}
			String flag = this.getflag();
			if(StringUtil.isNotEmpty(flag)){
				flag = java.net.URLDecoder.decode(flag,"UTF-8");
				this.setflag(flag);
			}
			String pptr = this.getpptr();
			if(StringUtil.isNotEmpty(pptr)){
				pptr = java.net.URLDecoder.decode(pptr,"UTF-8");
				this.setpptr(pptr);
			}
			String cptr = this.getcptr();
			if(StringUtil.isNotEmpty(cptr)){
				cptr = java.net.URLDecoder.decode(cptr,"UTF-8");
				this.setcptr(cptr);
			}
			String score = this.getscore();
			if(StringUtil.isNotEmpty(score)){
				score = java.net.URLDecoder.decode(score,"UTF-8");
				this.setscore(score);
			}
			String orderid = this.getorderid();
			if(StringUtil.isNotEmpty(orderid)){
				orderid = java.net.URLDecoder.decode(orderid,"UTF-8");
				this.setorderid(orderid);
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
			
			
			String sinoss_code = this.getsinoss_code();
			if(StringUtil.isNotEmpty(sinoss_code)){
				sinoss_code = java.net.URLDecoder.decode(sinoss_code,"UTF-8");
				this.setsinoss_code(sinoss_code);
			}
			String kjtj_code = this.getkjtj_code();
			if(StringUtil.isNotEmpty(kjtj_code)){
				kjtj_code = java.net.URLDecoder.decode(kjtj_code,"UTF-8");
				this.setkjtj_code(kjtj_code);
			}
			String kjtj_code_value = this.getkjtj_code_value();
			if(StringUtil.isNotEmpty(kjtj_code_value)){
				kjtj_code_value = java.net.URLDecoder.decode(kjtj_code_value,"UTF-8");
				this.setkjtj_code_value(kjtj_code_value);
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
			String orderauto = this.getorderauto();
			if(StringUtil.isNotEmpty(orderauto)){
				orderauto = java.net.URLDecoder.decode(orderauto,"UTF-8");
				this.setorderauto(orderauto);
			}
			String sinoss_code_value = this.getsinoss_code_value();
			if(StringUtil.isNotEmpty(sinoss_code_value)){
				sinoss_code_value = java.net.URLDecoder.decode(sinoss_code_value,"UTF-8");
				this.setsinoss_code_value(sinoss_code_value);
			}
		} catch (Exception e) {
			System.out.println("中文转码出现问题");
		}
		return this;
	}
}
