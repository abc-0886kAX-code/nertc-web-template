package com.ytxd.service.SystemManage;

public interface SM_BuiltItem_Service {
	/**
	 * 初始化搜索配置
	 * @param name
	 * @param description
	 * @param tablename
	 * @param idlist
	 * @return
	 * @throws Exception
	 */
	Integer InitSearchById(String searchpagename,String description,String tablename,String idlist) throws Exception;
	/**
	 * 初始化列表配置
	 * @param name
	 * @param description
	 * @param tablename
	 * @param idlist
	 * @param widthtype
	 * @return
	 * @throws Exception
	 */
	Integer InitListById(String listname,String description,String tablename,String idlist,String widthtype) throws Exception;
	/**
	 * Excel导出配置
	 * @param exportname
	 * @param description
	 * @param tablename
	 * @param idlist
	 * @return
	 * @throws Exception
	 */
	Integer InitExcelById(String exportname,String description,String tablename,String idlist) throws Exception;
	/**
	 * Excel导入配置
	 * @param exportname
	 * @param description
	 * @param tablename
	 * @param idlist
	 * @return
	 * @throws Exception
	 */
	Integer InitExcelImportById(String importname,String description,String tablename,String idlist) throws Exception;
}
