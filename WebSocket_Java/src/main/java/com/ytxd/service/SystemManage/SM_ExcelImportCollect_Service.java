package com.ytxd.service.SystemManage;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

public interface SM_ExcelImportCollect_Service {
	/**
	 * 导出配置SQL
	 * @param id 
	 * @return
	 * @throws Exception
	 */
	String ExportSql(HttpServletRequest request, String id) throws Exception;

	String ExportFileAuto(HashMap<String, Object> map) throws Exception;
}
