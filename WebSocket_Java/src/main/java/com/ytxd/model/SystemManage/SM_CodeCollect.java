package com.ytxd.model.SystemManage;
import com.ytxd.model.BaseBO;
import com.ytxd.util.StringUtil;
public class SM_CodeCollect extends BaseBO {
	private static final long serialVersionUID = 1L;
	private String codeid;  //参数类型ID
	private String description;  //描述

	/**
	 * 参数类型ID
	 * @param codeid
	 */
	public void setcodeid(String codeid) {	this.codeid = codeid;if(StringUtil.isNotEmpty(codeid)){this.setidarray(codeid.replace("'", "").split(","));}	}
	/**
	 * 参数类型ID
	 * @return String
	 */
	public String getcodeid() {	return codeid;	}
	/**
	 * 描述
	 * @param description
	 */
	public void setdescription(String description) {	this.description = description;	}
	/**
	 * 描述
	 * @return String
	 */
	public String getdescription() {	return description;	}

	public SM_CodeCollect GetdecodeUtf(){
		super.GetdecodeUtf();
		try {
			String codeid = this.getcodeid();
			if(StringUtil.isNotEmpty(codeid)){
				codeid = java.net.URLDecoder.decode(codeid,"UTF-8");
				this.setcodeid(codeid);
			}
			String description = this.getdescription();
			if(StringUtil.isNotEmpty(description)){
				description = java.net.URLDecoder.decode(description,"UTF-8");
				this.setdescription(description);
			}
		} catch (Exception e) {
			System.out.println("中文转码出现问题");
		}
		return this;
	}
}
