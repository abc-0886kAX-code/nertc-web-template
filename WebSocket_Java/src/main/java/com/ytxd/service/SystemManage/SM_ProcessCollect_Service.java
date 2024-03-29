package com.ytxd.service.SystemManage;

import javax.servlet.http.HttpServletRequest;

public interface SM_ProcessCollect_Service {
	/**
	 * 导出配置SQL
	 * @param id 
	 * @return
	 * @throws Exception
	 */
	String ExportSql(HttpServletRequest request, String id) throws Exception;
}
