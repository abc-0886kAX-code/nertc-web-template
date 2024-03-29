package com.ytxd.service.SystemManage;

import javax.servlet.http.HttpServletRequest;

public interface SM_SearchCollect_Service {
	/**
	 * 导出配置SQL
	 * @param id 
	 * @return
	 * @throws Exception
	 */
	String ExportSql(HttpServletRequest request, String id) throws Exception;
	
	/**
	 * 更新SearchHtml
	 * @param request
	 * @param searchPageName 搜索配置名称
	 * @throws Exception 
	 */
	void UpdateSearchHtml(HttpServletRequest request, String searchPageName) throws Exception;
}
