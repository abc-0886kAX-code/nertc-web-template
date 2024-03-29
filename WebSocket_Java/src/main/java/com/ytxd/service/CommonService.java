package com.ytxd.service;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ytxd.model.ActionResult;
import com.ytxd.model.MySqlData;
import com.ytxd.model.SysUser;

/**
 * 
 * @ClassName CommonService
 * @Description 实现通用数据操作功能
 * @author 陈兴旺
 * @Date 2019年12月31日 上午10:45:38
 * @version 1.0.0
 */
public interface CommonService {
	/**
	 * 查询列表 返回查询结果列表数据<br>
	 * 与所有配置无关，数据库实体需要有查询的所有信息。<br>
	 * 数据库实体需要有查询字段，查询表名，过滤条件。<br>
	 * 如果需要分页也需要传入分页信息page和rows参数。<br>
	 * 如果需要排序也需要传入排序信息sort和order参数。<br>
	 * @param mySqlData 数据库实体 数据表名和字段名等关键词不能接受用户输入数据。
	 * @return List<HashMap<String, Object>> 查询结果列表数据
	 * @throws Exception 执行SQL报错返回异常
	 */
	public List<HashMap<String, Object>> getDataList(MySqlData mySqlData) throws Exception;
	/**
	 * 查询列表 返回查询结果列表数据<br>
	 * tablename必须有值，会根据tablename数据表的配置自动关系得到关联后的数据列表。<br>
	 * 数据库实体需要有查询字段，查询表名，过滤条件。<br>
	 * 如果需要分页也需要传入分页信息page和rows参数。<br>
	 * 如果需要排序也需要传入排序信息sort和order参数。<br>
	 * @param mySqlData 数据库实体 数据表名和字段名等关键词不能接受用户输入数据。
	 * @return List<HashMap<String, Object>> 查询结果列表数据
	 * @throws Exception 执行SQL报错返回异常
	 */
	public List<HashMap<String, Object>> getJoinDataList(MySqlData mySqlData) throws Exception;
	/**
	 * @Title getList
	 * @Description 查询列表 通过request自动拼接查询条件
	 * @Description 通过表格视图配置和搜索配置生成SQL，然后执行SQL。
	 * @Description 数据验证报错返回错误信息，如果正确返回正确和必要数据。
	 * @Description 执行SQL报错返回异常
	 * @param request 用户界面的查询数据通过request对象传入
	 * @param mySqlData 除列头查询外的SQL查询实体需要赋值searchPageName
	 * @return ActionResult
	 * @throws Exception
	 */
	public ActionResult getList(HttpServletRequest request, MySqlData mySqlData) throws Exception;
	/**
	 * @Title getList
	 * @Description 查询列表 通过request自动拼接查询条件
	 * @param request
	 * @param mySqlData 除列头查询外的SQL查询实体
	 * @param searchPageName 搜索配置名称
	 * @return ActionResult
	 * @throws Exception
	 */
	public ActionResult getList(HttpServletRequest request, MySqlData mySqlData, String searchPageName) throws Exception;
	/**
	 * @Title getList
	 * @Description 查询列表 通过request自动拼接查询条件
	 * @param request
	 * @param mySqlData 除列头查询外的SQL查询实体
	 * @param tableName 数据库表名
	 * @param searchPageName 搜索配置名称
	 * @return ActionResult
	 * @throws Exception
	 */
	public ActionResult getList(HttpServletRequest request, MySqlData mySqlData, String tableName, String searchPageName) throws Exception;
	
	/** 
	 * @Title getListCount
	 * @Description 查询列表总条数
	 * @param mySqlData 查询条件
	 * @return Integer 返回数据记录总条数
	 * @throws Exception
	 */
	public Integer getListCount(MySqlData mySqlData) throws Exception;
	
	/**
	 * @Title getMapByKey
	 * @Description 根据主键获取数据，在记录查看时使用，会关联表格视图显示选择项的描述值列。
	 * @param tableName 数据库表名
	 * @param key id列有的主键值
	 * @return HashMap<String, Object>
	 * @throws Exception
	 */	
	public HashMap<String, Object> getMapByKey(String tableName, String key) throws Exception;
	/**
	 * @Title getMapByKey
	 * @Description 根据主键获取数据，在记录查看时使用，会关联表格视图显示选择项的描述值列。
	 * @param tableName 数据库表名
	 * @param key 主键值
	 * @param keyName 主键列名
	 * @return HashMap<String, Object>
	 * @throws Exception
	 */
	public HashMap<String, Object> getMapByKey(String tableName, String key, String keyName) throws Exception;
	/**
	 * @Title getMapByKey
	 * @Description 查询得到一条数据记录<br>
	 * 在记录查看时使用，会关联表格视图显示选择项的描述值列。<br>
	 * 数据库实体需要有查询字段，查询表名，过滤条件。<br>
	 * 如果需要排序也需要传入排序信息sort和order参数。<br>
	 * 如果查询数据记录有多条只会返回第一行数据。<br>
	 * @param mySqlData 数据库实体 数据表名和字段名等关键词不能接受用户输入数据。
	 * @return HashMap<String, Object> 查询得到一条数据记录
	 * @throws Exception 执行SQL报错返回异常
	 */
	public HashMap<String, Object> getMapByKey(MySqlData mySqlData) throws Exception;
	
	/**
	 * @Title getMap
	 * @Description 根据主键获取数据，在记录修改等场景使用，不会通过表格视图显示选择项的描述值列。
	 * @param tableName 数据库表名
	 * @param key id列有的主键值
	 * @return HashMap<String, Object>
	 * @throws Exception
	 */	
	public HashMap<String, Object> getMap(String tableName, String key) throws Exception;
	/**
	 * @Title getMap
	 * @Description 根据主键获取数据，在记录修改等场景使用，不会通过表格视图显示选择项的描述值列。
	 * @param tableName 数据库表名
	 * @param key 主键值
	 * @param keyName 主键列名
	 * @return HashMap<String, Object>
	 * @throws Exception
	 */
	public HashMap<String, Object> getMap(String tableName, String key, String keyName) throws Exception;
	/**
	 * @Title getMap
	 * @Description 查询得到一条数据记录<br>
	 * 在记录修改等场景使用，不会通过表格视图显示选择项的描述值列。<br>
	 * 数据库实体需要有查询字段，查询表名，过滤条件。<br>
	 * 如果需要排序也需要传入排序信息sort和order参数。<br>
	 * 如果查询数据记录有多条只会返回第一行数据。<br>
	 * @param mySqlData 数据库实体 数据表名和字段名等关键词不能接受用户输入数据。
	 * @return HashMap<String, Object> 查询得到一条数据记录
	 * @throws Exception 执行SQL报错返回异常
	 */
	public HashMap<String, Object> getMap(MySqlData mySqlData) throws Exception;

	/**
	 * 执行insert语句<br/> 
	 * 执行数据库实体SQL，与表格视图配置无关。<br/> 
	 * 数据验证报错返回错误信息，如果正确返回正确和必要数据。<br/> 
	 * 执行SQL报错返回异常<br/> 
	 * @param mySqlData 数据库实体 数据表名和字段名等关键词不能接受用户输入数据。
	 * @return ActionResult 数据验证报错返回错误信息，如果正确返回正确和必要数据。
	 * @throws Exception 执行SQL报错返回异常
	 */
	public ActionResult insert(MySqlData mySqlData) throws Exception;
	/**
	 * 执行insert语句<br/> 
	 * 通过表格视图配置生成SQL，然后执行SQL。<br/> 
	 * 用户输入信息通过request对象传入，然后通过表格视图配置生成对应SQL。<br/>
	 * 数据验证报错返回错误信息，如果正确返回正确和必要数据。<br/> 
	 * 执行SQL报错返回异常<br/> 
	 * @param request 用户输入信息通过request对象传入，然后通过表格视图配置生成对应SQL。
	 * @param mySqlData 数据库实体 数据表名和字段名等关键词不能接受用户输入数据。
	 * @return ActionResult 数据验证报错返回错误信息，如果正确返回正确和必要数据。
	 * @throws Exception 执行SQL报错返回异常
	 */
	public ActionResult insert(MultipartHttpServletRequest request, MySqlData mySqlData) throws Exception;
	public ActionResult insert(HttpServletRequest request, MySqlData mySqlData) throws Exception;
	
	/**
	 * 
	 * 执行update语句<br/> 
	 * 执行数据库实体SQL，与表格视图配置无关。<br/>
	 * 数据验证报错返回错误信息，如果正确返回正确和必要数据。<br/>
	 * 执行SQL报错返回异常<br/>
	 * @param mySqlData 数据库实体 数据表名和字段名等关键词不能接受用户输入数据。
	 * @return ActionResult 数据验证报错返回错误信息，如果正确返回正确和必要数据。
	 * @throws Exception 执行SQL报错返回异常
	 */
	public ActionResult update(MySqlData mySqlData) throws Exception;
	
	/**
	 * 执行update语句<br/> 
	 * 通过表格视图配置生成SQL，然后执行SQL。<br/> 
	 * 用户输入信息通过request对象传入，然后通过表格视图配置生成对应SQL。<br/>
	 * 数据验证报错返回错误信息，如果正确返回正确和必要数据。<br/> 
	 * 执行SQL报错返回异常<br/> 
	 * @param request 用户输入信息通过request对象传入，然后通过表格视图配置生成对应SQL。
	 * @param mySqlData 数据库实体 数据表名等关键词不能接受用户输入数据。
	 * @return ActionResult 数据验证报错返回错误信息，如果正确返回正确和必要数据。
	 * @throws Exception 执行SQL报错返回异常
	 */
	public ActionResult update(MultipartHttpServletRequest request, MySqlData mySqlData) throws Exception;

	/**
	 * 
	 * delete 通过主键删除记录，批量删除值之间用,分隔。<br/>
	 * 删除主键删除记录
	 * @param tableName 数据库表名 数据表名等关键词不能接受用户输入数据。
	 * @param key 主键值，主键列表默认为id
	 * @return Integer 影响记录条数
	 * @throws Exception 执行SQL报错返回异常
	 */
	public Integer delete(String tableName, String key) throws Exception;
	/**
	 * @Title delete 删除主键删除记录，批量删除值之间用,分隔
	 * @Description 删除主键删除记录
	 * @param tableName 数据库表名
	 * @param key 主键值
	 * @param keyName 主键列名
	 * @return Integer
	 * @throws Exception
	 */
	public Integer delete(String tableName, String key, String keyName) throws Exception;
	/**
	 * 
	 * @Title delete
	 * @Description 删除主键删除记录，批量删除值之间用,分隔
	 * @param tableName 数据库表名
	 * @param key 主键值
	 * @param keyName 主键列名
	 * @param key2 主键值2
	 * @param keyName2 主键列名2
	 * @return Integer
	 * @throws Exception
	 */
	public Integer delete(String tableName, String key, String keyName, String key2, String keyName2) throws Exception;
	/**
	 * 通过过滤条件删除记录<br>
	 * 数据库实体需要包括表名和过滤条件<br>
	 * 使用此方法一定要注意，如果没有过滤条数会操作表的所有数据。<br>
	 * 进入必要的数据有效性验证，如果表名不存在或者过滤条件不存在时直接抛出异常<br>
	 * @param mySqlData 数据库实体 包括表名和过滤条件
	 * @return Integer 影响记录条数
	 * @throws Exception 如果表名不存在或者过滤条件不存在时直接抛出异常。<br>
	 * 执行SQL报错返回异常
	 */
	public Integer delete(MySqlData mySqlData) throws Exception;
	
	/**
	 * @Title getExcelCollect
	 * @Description 得到Excel导出主表信息
	 * @param exportName
	 * @return List<HashMap<String, Object>>
	 * @throws Exception
	 */
	public List<HashMap<String, Object>> getExcelCollect(String exportName) throws Exception;
	/**
	 * @Title getExcelItem
	 * @Description 得到Excel导出从表信息
	 * @param exportName
	 * @return List<HashMap<String, Object>>
	 * @throws Exception
	 */
	public List<HashMap<String, Object>> getExcelItem(String exportName) throws Exception;
	/**
	 * @Title getSelectJoin
	 * @Description 得到表格视图选择项的描述列
	 * @param tablename 表名
	 * @param viewname 视图名
	 * @param mySqlData
	 * @return MySqlData
	 */
	public MySqlData getSelectJoin(String tablename, String viewname, MySqlData mySqlData) throws Exception;
	
	/**
	 * @Title getWhere
	 * @Description 得到配置的过滤条件
	 * @param searchpagename 配置的搜索名称
	 * @param request request
	 * @param mySqlData mySqlData
	 * @return MySqlData
	 */
	public MySqlData getWhere(String searchpagename, HttpServletRequest request, MySqlData mySqlData) throws Exception;
	/**
	 * @Title getWhere
	 * @Description 得到配置的过滤条件
	 * @param searchpagename 配置的搜索名称
	 * @param request request
	 * @param mySqlData mySqlData
	 * @param fieldPrefix 搜索字段前缀
	 * @return MySqlData
	 */
	public MySqlData getWhere(String searchpagename, HttpServletRequest request, MySqlData mySqlData, String fieldPrefix) throws Exception;
	public String getWhereSql(MySqlData mySqlData) throws Exception;
	public String getSearchParams(String searchpagename, HttpServletRequest request) throws Exception;
	
	ActionResult update(HttpServletRequest request, MySqlData mySqlData) throws Exception;
	
	public List<HashMap<String, Object>> callProc(MySqlData mySqlData) throws Exception;
	
	
	
	
	
	
	
	
	/**
	 * @Title getAddJson
	 * @Description 得到增加项的json
	 * @param tablename 数据库表名
	 * @param viewname 增加视图名
	 * @param data 默认值
	 * @param sysuser 用户信息
	 * @return String
	 * @throws Exception
	 */
	public List<HashMap<String, Object>> getAddJson(String tablename, String viewname, HttpServletRequest request, HashMap<String, Object> data, SysUser sysuser) throws Exception;
	/**
	 * @Title getEditJson
	 * @Description 得到编辑项的json
	 * @param tablename 数据库表名
	 * @param viewname 增加视图名
	 * @param data 修改的数据
	 * @return String
	 * @throws Exception
	 */
	public List<HashMap<String, Object>> getEditJson(String tablename, String viewname, HttpServletRequest request, HashMap<String, Object> data) throws Exception;
	/**
	 * @Title getViewJson
	 * @Description 得到查看项的json
	 * @param tablename 数据库表名
	 * @param viewname 增加视图名
	 * @param data 查看的数据
	 * @return String
	 * @throws Exception
	 */
	public List<HashMap<String, Object>> getViewJson(String tablename, String viewname, HttpServletRequest request, HashMap<String, Object> data) throws Exception;
	/**
	 * @Title getSearchJson
	 * @Description 得到搜索的渲染
	 * @param searchPageName
	 * @param List<HashMap<String, Object>>
	 * @return String
	 */
	public List<HashMap<String, Object>> getSearchJson(String searchPageName) throws Exception;
	/**
	 * @Title getListJson
	 * @Description 得到列表的渲染
	 * @param listName
	 * @param tableAlias
	 * @param request
	 * @return List<HashMap<String, Object>>
	 */
	public List<HashMap<String, Object>> getListJson(String listName) throws Exception;
	
	
	
	/**
	 * @Title getAddHtml
	 * @Description 得到增加项的html
	 * @param tablename 数据库表名
	 * @param viewname 增加视图名
	 * @param request request请求
	 * @param data 默认值
	 * @param sysuser 用户信息
	 * @return String
	 * @throws Exception
	 */
	public String getAddHtml(String tablename, String viewname, HttpServletRequest request, HashMap<String, Object> data, SysUser sysuser) throws Exception;
	/**
	 * @Title getEditHtml
	 * @Description 得到编辑项的html
	 * @param tablename 数据库表名
	 * @param viewname 增加视图名
	 * @param request request请求
	 * @param data 修改的数据
	 * @return String
	 * @throws Exception
	 */
	public String getEditHtml(String tablename, String viewname, HttpServletRequest request, HashMap<String, Object> data) throws Exception;
	/**
	 * @Title getViewHtml
	 * @Description 得到查年项的html
	 * @param tablename 数据库表名
	 * @param viewname 增加视图名
	 * @param request request请求
	 * @param data 查看的数据
	 * @return String
	 * @throws Exception
	 */
	public String getViewHtml(String tablename, String viewname, HttpServletRequest request, HashMap<String, Object> data) throws Exception;
	/**
	 * @Title getSearchHtml
	 * @Description 得到搜索的渲染
	 * @param searchPageName
	 * @param tableAlias
	 * @param request
	 * @return String
	 */
	public String getSearchHtml(String searchPageName, String tableAlias, HttpServletRequest request) throws Exception;
	/**
	 * @Title getSearchHtml
	 * @Description 得到搜索的渲染
	 * @param searchPageName
	 * @param tableAlias
	 * @param request
	 * @param ColumnCount
	 * @return String
	 */
	public String getSearchHtml(String searchPageName, String tableAlias, HttpServletRequest request, Integer columnCount) throws Exception;
	/**
	 * getFixedSearchHtml 
	 * @Description 得到固定搜索的渲染
	 * @param searchPageName
	 * @param tableAlias
	 * @param request
	 * @return String
	 * @throws Exception
	 */
	public String getFixedSearchHtml(String searchPageName, String tableAlias, HttpServletRequest request) throws Exception;
	/**
	 * @Title getListHtml
	 * @Description 得到列表的渲染
	 * @param listName
	 * @param tableAlias
	 * @param request
	 * @return String
	 */
	public String getListHtml(String listName, String tableAlias, HttpServletRequest request) throws Exception;
	/**
	 * @Title getListHtml
	 * @param listName
	 * @param tableAlias
	 * @param request
	 * @param listWidth
	 * @return
	 * @throws Exception
	 */
	public String getListHtml(String listName, String tableAlias, HttpServletRequest request, Integer listWidth) throws Exception;
}