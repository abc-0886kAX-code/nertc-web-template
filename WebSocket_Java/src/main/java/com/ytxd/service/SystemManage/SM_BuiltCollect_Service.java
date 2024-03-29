package com.ytxd.service.SystemManage;

import javax.servlet.http.HttpServletRequest;

public interface SM_BuiltCollect_Service {
	/**
	 * 初始化表格视图
	 * @param TableName 表名
	 * @return 1
	 * @throws Exception
	 */
	Integer InitBuiltItem(String tablename) throws Exception;
	/**
	 * 导出表格视图配置SQL
	 * @param id 
	 * @return
	 * @throws Exception
	 */
	String ExportSql(HttpServletRequest request, String id) throws Exception;
}
