package com.ytxd.model;

import java.io.Serializable;

/**
 * 错误提示类
 * 
 * @author wsr 用于json传递数据时的错误提示
 * 
 */
public class JsResult implements Serializable {

	private static final long serialVersionUID = -6438702945262658480L;

	// 提示弹出框标题
	private String title = "操作提示";
	// 提示弹出框的状态图片 info/warning/error
	private String icon;
	// 返回状态 info(操作正确结束的提示信息时的状态)/warning(验证没有通过时的警告信息时的状态)/error(遇到错误时的状态)
	private String resultStatus;
	// 提示信息
	private String message;
	// 下一步操作需要的请求链接
	private String url;
	// 参数
	private Object attr;

	private String originalFilename;

	
	/**
	 * 空参构造器
	 */
	public JsResult() {
		super();
	}

	
	/**
	 * 向前台返回一段字符串
	 * @param message
	 */
	public JsResult(String message) {
		super();
		this.message = message;
	}

	
	/**
	 * 构造一个使用默认标题的提示框
	 * @param icon
	 * @param message
	 */
	public JsResult(String icon, String message) {
		super();
		this.icon = icon;
		this.message = message;
	}


	/**
	 * 一个提示框的基本构造
	 * @param title
	 * @param icon
	 * @param message
	 */
	public JsResult(String title, String icon, String message) {
		super();
		this.title = title;
		this.icon = icon;
		this.message = message;
	}

	
	/**
	 * 全参构造器
	 * @param title
	 * @param icon
	 * @param resultStatus
	 * @param message
	 * @param url
	 * @param attr
	 * @param originalFilename
	 */
	public JsResult(String title, String icon, String resultStatus, String message, String url, Object attr,
			String originalFilename) {
		super();
		this.title = title;
		this.icon = icon;
		this.resultStatus = resultStatus;
		this.message = message;
		this.url = url;
		this.attr = attr;
		this.originalFilename = originalFilename;
	}


	//get/set方法
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getResultStatus() {
		return resultStatus;
	}

	public void setResultStatus(String resultStatus) {
		this.resultStatus = resultStatus;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Object getAttr() {
		return attr;
	}

	public void setAttr(Object attr) {
		this.attr = attr;
	}

	public String getOriginalFilename() {
		return originalFilename;
	}

	public void setOriginalFilename(String originalFilename) {
		this.originalFilename = originalFilename;
	}
	

	// /* */ public String getOriginalFilename()
	// /* */ {
	// /* 59 */ return this.originalFilename;
	// /* */ }
	// /* */ public void setOriginalFilename(String originalFilename) {
	// /* 62 */ this.originalFilename = originalFilename;
	// /* */ }
}
