package com.ytxd.model;

public class ActionResult {
	/*
	 * 是否成功，返回true,false
	 */
    private Boolean success;
    /*
     * 返回的提示信息
     */
    private String msg;
    /*
     * 记录总数
     */
    private Integer total;
    
    private Object data;
    
    private Object filehtml;

    /**
     * 初始化
     */
    public ActionResult()
    {
        setSuccess(false);
        setMsg("");
    }

    /**
     * 是否成功，返回true,false
     * @return
     */
	public Boolean getSuccess() {
		return success;
	}

	/**
	 * 是否成功，返回true,false
	 * @param success
	 */
	public void setSuccess(Boolean success) {
		this.success = success;
	}

	/**
	 * 返回的提示信息
	 * @return
	 */
	public String getMsg() {
		return msg;
	}

	/**
	 * 返回的提示信息
	 * @param msg
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}

	/**
	 * 记录总数
	 * @return
	 */
	public Integer getTotal() {
		return total;
	}

	/**
	 * 记录总数
	 * @param total
	 */
	public void setTotal(Integer total) {
		this.total = total;
	}

	/**
	 * 得到Object数据对象
	 * @return Object
	 */
	public Object getData() {
		return data;
	}

	/**
	 * 设置Object数据对象
	 * @param data ObjectData
	 */
	public void setData(Object data) {
		this.data = data;
	}
	
	/**
	 * 得到附件类型保存后回调显示的html内容
	 * @return Object
	 */
	public Object getFileHtml() {
		return filehtml;
	}

	/**
	 * 设置附件类型保存后回调显示的html内容
	 * @param filehtml ObjectData
	 */
	public void setFileHtml(Object filehtml) {
		this.filehtml = filehtml;
	}
}