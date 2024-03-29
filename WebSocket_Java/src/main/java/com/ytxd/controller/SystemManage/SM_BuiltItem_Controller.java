package com.ytxd.controller.SystemManage;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ytxd.common.DataUtils;
import com.ytxd.common.util.R;
import com.ytxd.controller.BaseController;
import com.ytxd.model.ActionResult;
import com.ytxd.model.MySqlData;
import com.ytxd.model.SysUser;
import com.ytxd.service.CommonService;
import com.ytxd.service.ExcelOperation.ExportTable;
import com.ytxd.service.ExcelOperation.ImportExcel;
import com.ytxd.service.SystemManage.SM_BuiltItem_Service;
import com.ytxd.service.SystemManage.SM_ListCollect_Service;
import com.ytxd.service.SystemManage.SM_SearchCollect_Service;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 *  控制层(前后台交互)
 */
@Api(value = "系统管理-字段集接口", tags = "系统管理-字段集接口")
@Controller("SM_BuiltItem_Controller")
@RequestMapping(value = "/SystemManage/SM_BuiltItem")
public class SM_BuiltItem_Controller extends BaseController{
	@Resource
	private CommonService service;
	@Resource
	private SM_BuiltItem_Service serviceBC;
	@Resource
	private SM_SearchCollect_Service serviceSC;
	@Resource
	private SM_ListCollect_Service serviceLC;

	@ApiOperation(value = "接口说明：MyList_PC表格视图子表录入列表接口", notes = "接口说明：MyList_PC表格视图子表录入列表接口")
	@GetMapping("/MyList_PC")
	@ResponseBody
	public Map<String, Object> MyList_PC(HttpServletRequest request) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		// 搜索和列表配置
		obj.put("searchSchema", service.getSearchJson("SM_BuiltItem_MyList"));
		obj.put("columnSchema", service.getListJson("SM_BuiltItem_MyList"));
		// 操作权限
		SysUser sysuser = DataUtils.getSysUser(request);
		List<String> powerList = new ArrayList<String>();
		powerList.add("add");
		powerList.add("edit");
		powerList.add("delete");
		powerList.add("export");
		powerList.add("import");
		obj.put("powerall", powerList);
		// listparam为给获取数据列表接口GetList传参
		Map<String, Object> listparam = DataUtils.getRequestMap(request);
		listparam.put("listview", "SM_BuiltItem_MyList");
		obj.put("listparam", listparam);
		// 列表接口参数
		obj.put("exportname", "SM_BuiltItem_MyList");
		obj.put("importname", "SM_BuiltItem_Import");
		obj.put("idfieldname", "id");
		obj.put("sortname", "flag desc,orderid");
		obj.put("sortorder", "ASC");
		if (StringUtils.isNotBlank(request.getParameter("tablename"))) {
			MySqlData mySqlData = new MySqlData();
			mySqlData.setTableName("sm_builtcollect");
			mySqlData.setFieldWhere("tablename", request.getParameter("tablename"), "=");
			Map<String, Object> mapCollect = service.getMap(mySqlData);
			if (!mapCollect.isEmpty()) {
				obj.put("inittype", "搜索管理");
				obj.put("initwidth", "%");
				obj.put("initname", DataUtils.getMapKeyValue(mapCollect, "tablename") + "_MyList");
				obj.put("initdescription", DataUtils.getMapKeyValue(mapCollect, "description"));
			}
		}
		return R.ok().put("data", obj);
	}

	@ApiOperation(value = "接口说明：ViewList_PC表格视图子表查看列表接口", notes = "接口说明：ViewList_PC表格视图子表查看列表接口")
	@GetMapping("/ViewList_PC")
	@ResponseBody
	public Map<String, Object> ViewList_PC(HttpServletRequest request) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		// 搜索和列表配置
		obj.put("searchSchema", service.getSearchJson("SM_BuiltItem_ViewList"));
		obj.put("columnSchema", service.getListJson("SM_BuiltItem_ViewList"));
		// 操作权限
		SysUser sysuser = DataUtils.getSysUser(request);
		List<String> powerList = new ArrayList<String>();
		powerList.add("export");
		obj.put("powerall", powerList);
		// listparam为给获取数据列表接口GetList传参
		Map<String, Object> listparam = DataUtils.getRequestMap(request);
		listparam.put("listview", "SM_BuiltItem_ViewList");
		obj.put("listparam", listparam);
		// 列表接口参数
		obj.put("exportname", "SM_BuiltItem_MyList");
		obj.put("idfieldname", "id");
		obj.put("sortname", "flag desc,orderid");
		obj.put("sortorder", "ASC");
		return R.ok().put("data", obj);
	}

	@ApiOperation(value = "接口说明：Add_PC表格视图子表新增页面接口", notes = "接口说明：Add_PC表格视图子表新增页面接口")
	@GetMapping("/Add_PC")
	@ResponseBody
	public Map<String, Object> Add_PC(HttpServletRequest request) throws Exception {
		SysUser sysuser = DataUtils.getSysUser(request);
		// 参数整理
		String tablename = "SM_BuiltItem";
		String viewname = "add";
		// 默认值
		HashMap<String, Object> obj = new HashMap<String, Object>();
		obj.put("submitman", sysuser.getUserId());
		obj.put("submittime", DataUtils.getDate("yyyy-MM-dd"));
		// formparam为给保存数据接口Save传参
		Map<String, Object> formparam = DataUtils.getRequestMap(request);
		formparam.put("tbviewname", viewname);
		// 接口返回数据
		List<HashMap<String, Object>> listTable = service.getAddJson(tablename, viewname, request, obj, sysuser);
		obj = new HashMap<String, Object>();
		obj.put("tableJson", listTable);
		obj.put("formparam", formparam);
		return R.ok().put("data", obj);
	}

	@ApiOperation(value = "接口说明：Edit_PC表格视图子表修改页面接口", notes = "接口说明：Edit_PC表格视图子表修改页面接口")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "唯一标识,必填", required = true, dataType = "String", paramType = "query"), })
	@GetMapping("/Edit_PC")
	@ResponseBody
	public Map<String, Object> Edit_PC(HttpServletRequest request) throws Exception {
		// 主键参数判断
		String id = request.getParameter("id");
		if (StringUtils.isBlank(id)) {
			return R.error("您要修改的记录不存在，请修改。");
		}
		// 参数整理
		String tablename = "SM_BuiltItem";
		String viewname = "add";
		if (StringUtils.isNotBlank(request.getParameter("viewname"))) {
			viewname = request.getParameter("viewname");
		}
		HashMap<String, Object> obj = service.getMap(tablename, id);
		// 主键参数是否正确的判断
		if (obj == null) {
			return R.error("您要修改的记录不存在，请修改。");
		}
		// formparam为给保存数据接口Save传参
		Map<String, Object> formparam = DataUtils.getRequestMap(request);
		formparam.put("tbviewname", viewname);
		// 接口返回数据
		List<HashMap<String, Object>> listTable = service.getEditJson(tablename, viewname, request, obj);
		obj = new HashMap<String, Object>();
		obj.put("tableJson", listTable);
		obj.put("formparam", formparam);
		return R.ok().put("data", obj);
	}

	@ApiOperation(value = "接口说明：View_PC表格视图子表查看页面接口", notes = "接口说明：View_PC表格视图子表查看页面接口")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "唯一标识,必填", required = true, dataType = "String", paramType = "query"), })
	@GetMapping("/View_PC")
	@ResponseBody
	public Map<String, Object> View_PC(HttpServletRequest request) throws Exception {
		// 主键参数判断
		String id = request.getParameter("id");
		if (StringUtils.isBlank(id)) {
			return R.error("主键值不能为空，请修改。");
		}
		// 参数整理
		String tablename = "SM_BuiltItem";
		HashMap<String, Object> obj = service.getMapByKey(tablename, id);
		// 主键参数是否正确的判断
		if (obj == null) {
			return R.error("您要查看的记录不存在，请修改。");
		}
		String viewname = "add,view";
		if (StringUtils.isNotBlank(request.getParameter("viewname"))) {
			viewname = request.getParameter("viewname");
		}
		// 接口返回数据
		List<HashMap<String, Object>> listTable = service.getViewJson(tablename, viewname, request, obj);
		obj = new HashMap<String, Object>();
		obj.put("tableJson", listTable);
		return R.ok().put("data", obj);
	}
	
	@ApiOperation(value = "接口说明：GetList表格视图子表获取数据列表接口"
			, notes = "接口说明：GetList表格视图子表获取数据列表接口")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "listview", value = "列表名称，非必填，默认录入列表", required = false, dataType = "String",paramType="query"),
			@ApiImplicitParam(name = "page", value = "当前页数", required = true, dataType = "String",paramType="query"),
			@ApiImplicitParam(name = "rows", value = "每页条数", required = true, dataType = "String",paramType="query"),
			@ApiImplicitParam(name = "sort", value = "排序字段", required = true, dataType = "String",paramType="query"),
			@ApiImplicitParam(name = "order", value = "排序方式", required = true, dataType = "String",paramType="query"),
			@ApiImplicitParam(name = "tablename", value = "表名", required = true, dataType = "String",paramType="query"),
	})
	@PostMapping("/GetList")
	@ResponseBody
	public Map<String, Object> GetList(HttpServletRequest request) throws Exception {
		SysUser sysuser = DataUtils.getSysUser(request);
		// 数据实体
		MySqlData mySqlData = new MySqlData();
		// 数据库表名
		String tablename = "SM_BuiltItem";
		mySqlData.setTableName(tablename);
		// 得到是哪个列表
		String listview = "SM_BuiltItem_MyList";
		if (request.getParameter("listview") != null) {
			listview = request.getParameter("listview");
		}
		// 默认过滤条件
		if (StringUtils.isNotBlank(request.getParameter("tablename"))) {
			mySqlData.setFieldWhere("tablename", request.getParameter("tablename"), "=");
		}
		// 查询配置名称，就是列表上头的查询配置名称
		String searchPageName = listview;
		// 权限校验
		// 返回实体
		ActionResult result = service.getList(request, mySqlData, tablename, searchPageName);
		if (!result.getSuccess()) {
			return R.error(result.getMsg());
		}
		return R.ok().put("data", result);
	}
	
	/*@ApiOperation(value = "接口说明：GetListById表格视图子表通过id获取单条数据接口", notes = "接口说明：GetListById表格视图子表通过id获取单条数据接口")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "唯一标识,必填", required = true, dataType = "String", paramType = "query"), })
	@GetMapping("/GetListById")
	@ResponseBody
	public HashMap<String, Object> GetListById(HttpServletRequest request) throws Exception {
		// 主键参数判断
		String id = request.getParameter("id");
		if (StringUtils.isBlank(id)) {
			return R.error("主键值不能为空，请修改。");
		}
		// 参数整理
		String tablename = "SM_BuiltItem";
		// 接口返回数据
		HashMap<String, Object> obj = service.getMapByKey(tablename, id);
		return R.ok(obj);
	}*/
	
	@ApiOperation(value = "接口说明：Save表格视图子表保存数据接口", notes = "接口说明：Save表格视图子表保存数据接口")
	@PostMapping("/Save")
	@ResponseBody
	@Transactional
	public HashMap<String, Object> Save(MultipartHttpServletRequest request) throws Exception {
		// 数据实体
		MySqlData mySqlData = new MySqlData();
		// 数据库表名
		mySqlData.setTableName("SM_BuiltItem");
		// 表单的视图名称默认为add，多个视图用英文对号分隔，例句：add,view,check
		String viewname = "add";
		if (StringUtils.isNotBlank(request.getParameter("tbviewname"))) {
			viewname = request.getParameter("tbviewname");
		}
		mySqlData.setViewName(viewname);
		// 保存默认值
		if (StringUtils.isNotBlank(request.getParameter("tablename"))) {
			mySqlData.setFieldValue("tablename", request.getParameter("tablename"));
		}
		// 启用缓存
		mySqlData.setEnableCache(true);

		// 插入和更新判断，然后执行。
		ActionResult result = new ActionResult();
		String id = request.getParameter("id");
		if (StringUtils.isBlank(id)) {
			// 执行插入操作
			result = service.insert(request, mySqlData);
			// 增加保存之后的处理事件，例如 将负责人添加到作者列表
			id = result.getData().toString();
		} else {
			// 执行更新操作
			mySqlData.setFieldWhere("id", id, "=");
			result = service.update(request, mySqlData);
			// 修改保存之后的处理事件

		}
		if (result.getSuccess()) {
			return R.ok().put("data", result.getData());
		} else {
			return R.error(result.getMsg());
		}
	}
	
	@ApiOperation(value = "接口说明：Delete表格视图子表删除数据接口", notes = "接口说明：Delete表格视图子表删除数据接口。")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "唯一标识,必填", required = true, dataType = "String", paramType = "query"), })
	@PostMapping("/Delete")
	@ResponseBody
	@Transactional
	public HashMap<String, Object> Delete(HttpServletRequest request) throws Exception {
		// 数据有效性校验
		String id = request.getParameter("id");
		if (StringUtils.isBlank(id)) {
			return R.error("主键值不能为空，请修改。");
		}
		// 执行删除操作
		MySqlData mySqlData = new MySqlData();
		mySqlData.setTableName("SM_BuiltItem");
		mySqlData.setFieldWhere("id", id, "in");
		// 启用缓存
		mySqlData.setEnableCache(true);
		if (service.delete(mySqlData) > 0) {
			return R.ok();
		} else {
			return R.error("没有删除任何记录。");
		}
	}

	@SuppressWarnings("unchecked")
	@ApiOperation(value = "接口说明：ExcelExport表格视图子表Excel导出接口"
			, notes = "接口说明：ExcelExport表格视图子表Excel导出接口")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "exportname", value = "导出配置名，必填", required = true, dataType = "String",paramType="query"),
	})
	@PostMapping("/ExcelExport")
	@ResponseBody
	public Map<String, Object> ExcelExport(HttpServletRequest request) throws Exception {
		// 得到导出配置名称
		String exportName = request.getParameter("exportname");
		if (StringUtils.isBlank(exportName)) {
			return R.error("导出配置名称不能为空，请修改。");
		}
		// 得到导出配置主表和子表数据
		ExportTable export = new ExportTable();
		String ExportString = request.getParameter("export");
		String strError = "";
		if (StringUtils.isBlank(ExportString)) {
			strError = export.setExcelCollect(service.getExcelCollect(exportName));
			if (StringUtils.isNotBlank(strError)) {
				return R.error(strError);
			}
			strError = export.setExcelItem(service.getExcelItem(exportName));
			if (StringUtils.isNotBlank(strError)) {
				return R.error(strError);
			}
		} else {
			JSONObject jsonObject = JSON.parseObject(ExportString);
			JSONObject excelCollect = (JSONObject) jsonObject.get("exportcollect");
			strError = export.setExcelCollectByJsonObj(excelCollect);
			if (StringUtils.isNotBlank(strError)) {
				return R.error(strError);
			}
			if ("01".equals(excelCollect.getString("auto")) && "01".equals(excelCollect.getString("ischoice"))) {
				try {
					JSONArray array = (JSONArray) jsonObject.get("excelitemlist");
					strError = export.setExcelItemByJsonArray(array);
				} catch (Exception e) {
					e.printStackTrace();
					strError = export.setExcelItem(service.getExcelItem(exportName));
				}
			} else {
				strError = export.setExcelItem(service.getExcelItem(exportName));
			}
			if (StringUtils.isNotBlank(strError)) {
				return R.error(strError);
			}
		}
		// 得到要导出的数据
		List<HashMap<String, Object>> listData = new ArrayList<HashMap<String, Object>>();
		listData = (List<HashMap<String, Object>>) ((ActionResult) GetList(request).get("data")).getData();
		export.setSource(listData);
		export.setProjectPath(request.getSession().getServletContext().getRealPath(""));
		// 导出excel
		strError = export.Export();
		// 返回导出excel文件的路径
		if (StringUtils.isNotBlank(strError)) {
			return R.error(strError);
		} else {
			return R.ok().put("PATH", export.GetDownFilePath());
		}
	}

	@SuppressWarnings("null")
	@ApiOperation(value = "接口说明：ExcelImport表格视图子表Excel导入接口", notes = "接口说明：ExcelImport表格视图子表Excel导入接口")
	@PostMapping("/ExcelImport")
	@ResponseBody
	public Map<String, Object> ExcelImport(MultipartHttpServletRequest request) throws Exception {
		SysUser sysuser = DataUtils.getSysUser(request);
		// 文件上传
		MultipartFile requestFile = request.getFile("importfile");
		if (requestFile == null && requestFile.isEmpty()) {
			R.error("导入文件不能为空，请修改。");
		}
		String fileName = requestFile.getOriginalFilename();
		fileName = DataUtils.getDate("yyyyMMdd") + (new Random().nextInt(900000) + 100000) + fileName;
		String filePath = request.getSession().getServletContext().getRealPath("UpFile/");
		File file = new File(filePath);
		if (!file.exists()) {
			file.mkdirs();
		}
		file = new File(filePath, fileName);
		requestFile.transferTo(file);
		// Excel导入
		String importname = "SM_BuiltItem_Import";
		StringBuilder strAddField = new StringBuilder();
		StringBuilder strAddValue = new StringBuilder();
		ImportExcel importExcel = new ImportExcel();
		importExcel.setImportName(importname);
		importExcel.setAddField(strAddField.toString());
		importExcel.setAddValue(strAddValue.toString());
		importExcel.setFilePath(filePath + "/" + fileName);
		if (StringUtils.isNotBlank(request.getParameter("sheetname"))) {
			importExcel.setSheetName(request.getParameter("sheetname"));
		}
		if (StringUtils.isNotBlank(request.getParameter("rownumber"))) {
			importExcel.setRowNumber(request.getParameter("rownumber"));
		}
		String strError = importExcel.Import();
		if (StringUtils.isBlank(strError)) {
			return R.ok();
		} else {
			return R.error(strError).put("PATH", "UpFile/" + fileName);
		}
	}





	//***扩展***************************************************************************************
	@ApiOperation(value = "接口说明：InitSearchById表格视图子表初始化搜索", notes = "接口说明：InitSearchById表格视图子表初始化搜索。")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "tableName", value = "表名", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "idList", value = "字段id列表", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "name", value = "搜索名称", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "description", value = "搜索描述", required = true, dataType = "String", paramType = "query"), })
	@PostMapping("/InitSearchById")
	@ResponseBody
	@Transactional
	public HashMap<String, Object> InitSearchById(HttpServletRequest request) throws Exception {
		String tablename = request.getParameter("tableName");
		String idlist = request.getParameter("idList");
		String name = request.getParameter("name");
		String description = request.getParameter("description");
		if (StringUtils.isNotBlank(tablename)) {
			tablename = tablename.replace(" ", "").replace("'", "").split(",")[0];
		} else {
			R.error("表名不能为空，请修改。");
		}
		if (StringUtils.isNotBlank(idlist)) {
			idlist = idlist.replace(" ", "").replace("'", "");
		} else {
			R.error("选择记录不能为空，请修改。");
		}
		if (StringUtils.isNotBlank(name)) {
			name = name.replace(" ", "").replace("'", "");
		} else {
			R.error("名称不能为空，请修改。");
		}
		if (StringUtils.isNotBlank(description)) {
			description = description.replace(" ", "").replace("'", "");
		} else {
			R.error("描述不能为空，请修改。");
		}
		// 执行。
		Integer result = serviceBC.InitSearchById(name, description, tablename, idlist);
		if (result > 0) {
			// 成功后更新主表SearchHtml的值
			serviceSC.UpdateSearchHtml(request, name);
			return R.ok();
		} else {
			return R.error("初始化失败。");
		}
	}

	@ApiOperation(value = "接口说明：InitListById表格视图子表初始化列表", notes = "接口说明：InitListById表格视图子表初始化列表。")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "tableName", value = "表名", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "idList", value = "字段id列表", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "name", value = "列表名称", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "description", value = "列表描述", required = true, dataType = "String", paramType = "query"), })
	@PostMapping("/InitListById")
	@ResponseBody
	@Transactional
	public HashMap<String, Object> InitListById(HttpServletRequest request) throws Exception {
		String tableName = request.getParameter("tableName");
		String idList = request.getParameter("idList");
		String name = request.getParameter("name");
		String description = request.getParameter("description");
		String width = request.getParameter("width");
		if (StringUtils.isNotBlank(tableName)) {
			tableName = tableName.replace(" ", "").replace("'", "").split(",")[0];
		} else {
			R.error("表名不能为空，请修改。");
		}
		if (StringUtils.isNotBlank(idList)) {
			idList = idList.replace(" ", "").replace("'", "");
		} else {
			R.error("选择记录不能为空，请修改。");
		}
		if (StringUtils.isNotBlank(name)) {
			name = name.replace(" ", "").replace("'", "");
		} else {
			R.error("名称不能为空，请修改。");
		}
		if (StringUtils.isNotBlank(description)) {
			description = description.replace(" ", "").replace("'", "");
		} else {
			R.error("描述不能为空，请修改。");
		}
		// 执行。
		Integer result = serviceBC.InitListById(name, description, tableName, idList, width);
		if (result > 0) {
			// 成功后更新主表ListHtml的值
			serviceLC.UpdateListHtml(request, name);
			return R.ok();
		} else {
			return R.error("初始化失败。");
		}
	}

	@ApiOperation(value = "接口说明：InitExcelById表格视图子表初始化Excel导出", notes = "接口说明：InitExcelById表格视图子表初始化Excel导出。")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "tableName", value = "表名", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "idList", value = "字段id列表", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "name", value = "Excel导出名称", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "description", value = "Excel导出描述", required = true, dataType = "String", paramType = "query"), })
	@PostMapping("/InitExcelById")
	@ResponseBody
	@Transactional
	public HashMap<String, Object> InitExcelById(HttpServletRequest request) throws Exception {
		String tableName = request.getParameter("tableName");
		String idList = request.getParameter("idList");
		String name = request.getParameter("name");
		String description = request.getParameter("description");
		if (StringUtils.isNotBlank(tableName)) {
			tableName = tableName.replace(" ", "").replace("'", "").split(",")[0];
		} else {
			R.error("表名不能为空，请修改。");
		}
		if (StringUtils.isNotBlank(idList)) {
			idList = idList.replace(" ", "").replace("'", "");
		} else {
			R.error("选择记录不能为空，请修改。");
		}
		if (StringUtils.isNotBlank(name)) {
			name = name.replace(" ", "").replace("'", "");
		} else {
			R.error("名称不能为空，请修改。");
		}
		if (StringUtils.isNotBlank(description)) {
			description = description.replace(" ", "").replace("'", "");
		} else {
			R.error("描述不能为空，请修改。");
		}
		// 执行。
		Integer result = serviceBC.InitExcelById(name, description, tableName, idList);
		if (result > 0) {
			return R.ok();
		} else {
			return R.error("初始化失败。");
		}
	}

	@ApiOperation(value = "接口说明：InitExcelImportById表格视图子表初始化Excel导入", notes = "接口说明：InitExcelImportById表格视图子表初始化Excel导入。")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "tableName", value = "表名", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "idList", value = "字段id列表", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "name", value = "Excel导入名称", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "description", value = "Excel导入描述", required = true, dataType = "String", paramType = "query"), })
	@PostMapping("/InitExcelImportById")
	@ResponseBody
	@Transactional
	public HashMap<String, Object> InitExcelImportById(HttpServletRequest request) throws Exception {
		String tableName = request.getParameter("tableName");
		String idList = request.getParameter("idList");
		String name = request.getParameter("name");
		String description = request.getParameter("description");
		if (StringUtils.isNotBlank(tableName)) {
			tableName = tableName.replace(" ", "").replace("'", "").split(",")[0];
		} else {
			R.error("表名不能为空，请修改。");
		}
		if (StringUtils.isNotBlank(idList)) {
			idList = idList.replace(" ", "").replace("'", "");
		} else {
			R.error("选择记录不能为空，请修改。");
		}
		if (StringUtils.isNotBlank(name)) {
			name = name.replace(" ", "").replace("'", "");
		} else {
			R.error("名称不能为空，请修改。");
		}
		if (StringUtils.isNotBlank(description)) {
			description = description.replace(" ", "").replace("'", "");
		} else {
			R.error("描述不能为空，请修改。");
		}
		// 执行。
		Integer result = serviceBC.InitExcelImportById(name, description, tableName, idList);
		if (result > 0) {
			return R.ok();
		} else {
			return R.error("初始化失败。");
		}
	}

	@ApiOperation(value = "接口说明：InitExcelById表格视图子表快捷切换是否字段接口", notes = "接口说明：InitExcelById表格视图子表快捷切换是否字段接口")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "字段id", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "field", value = "参数值（flag 是否隐藏 notnull 是否必填 readonly 是否只读）", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "value", value = "00(隐藏、可空、非只读) 01(显示、必填、只读)", required = true, dataType = "String", paramType = "query") })
	@GetMapping("/UpdateField")
	@ResponseBody
	@Transactional
	public HashMap<String, Object> UpdateField(HttpServletRequest request) throws Exception {
		SysUser sysuser = DataUtils.getSysUser(request);
		// 数据实体
		MySqlData mySqlData = new MySqlData();
		// 数据库表名
		mySqlData.setTableName("SM_BuiltItem");
		// 启用缓存
		mySqlData.setEnableCache(true);

		// 插入和更新判断，然后执行。
		ActionResult result = new ActionResult();
		String id = request.getParameter("id");
		if (StringUtils.isBlank(id)) {
			return R.error("id参数的值不能为空，请修改。");
		} else {
			String field = request.getParameter("field");
			if (StringUtils.isBlank(field)) {
				return R.error("field参数的值不能为空，请修改。");
			}
			String value = request.getParameter("value");
			if (StringUtils.isBlank(value)) {
				return R.error("value参数的值不能为空，请修改。");
			}
			if (!"00".equals(value) && !"01".equals(value)) {
				return R.error("value参数的值错误，请修改。");
			}
			// 前端传当前值 然后在这里取反 用于修改
			value = "00".equals(value) ? "01" : "00";
			if ("flag".equalsIgnoreCase(field)) {
				mySqlData.setFieldValue("flag", value);
			} else if ("notnull".equalsIgnoreCase(field)) {
				mySqlData.setFieldValue("notnull", value);
			} else if ("readonly".equalsIgnoreCase(field)) {
				mySqlData.setFieldValue("readonly", value);
			} else if ("codename".equalsIgnoreCase(field)) {
				mySqlData.setFieldValue("codename", value);
			} else if ("htmlencode".equalsIgnoreCase(field)) {
				mySqlData.setFieldValue("htmlencode", value);
			} else {
				return R.error("field参数的值错误，请修改。");
			}
			// 执行更新操作
			mySqlData.setFieldWhere("id", id, "=");
			result = service.update(mySqlData);
			// 修改保存之后的处理事件

		}
		if (result.getSuccess()) {
			return R.ok().put("data", result.getData());
		} else {
			return R.error(result.getMsg());
		}
	}
}
