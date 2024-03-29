package com.ytxd.service.SystemManage;

import javax.servlet.http.HttpServletRequest;

public interface SM_ListCollect_Service {
	/**
	 * 导出配置SQL
	 * @param id 
	 * @return
	 * @throws Exception
	 */
	String ExportSql(HttpServletRequest request, String id) throws Exception;
	/**
	 * 通过列表生成对应的Excel导出
	 * @param listname 
	 * @return
	 * @throws Exception
	 */
	Integer InitExcelByList(HttpServletRequest request, String listname) throws Exception;
	
	/**
	 * 更新ListHtml
	 * @param request
	 * @param listname
	 * @throws Exception
	 */
	void UpdateListHtml(HttpServletRequest request, String listname) throws Exception;
}
