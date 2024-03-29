package com.ytxd.controller.SystemManage;

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
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.*;

/**
 *  控制层(前后台交互)
 */
@Api(value = "系统管理-通用配置页面功能接口", tags = "系统管理-通用配置页面功能接口")
@Controller("SM_CommonPage_Handler_Controller")
@RequestMapping(value = "/SystemManage/SM_CommonPage_Handler")
public class SM_CommonPage_Handler_Controller extends BaseController {
	@Resource
	private CommonService service;

	@ApiOperation(value = "接口说明：MyList_PC通用配置页面功能录入列表接口", notes = "接口说明：MyList_PC通用配置页面功能录入列表接口")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "pageid", value = "pageid外键,必填", required = true, dataType = "String", paramType = "query"), })
	@GetMapping("/MyList_PC")
	@ResponseBody
	public Map<String, Object> MyList_PC(HttpServletRequest request) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		// 搜索和列表配置
		obj.put("searchSchema", service.getSearchJson("SM_CommonPage_Handler_MyList"));
		obj.put("columnSchema", service.getListJson("SM_CommonPage_Handler_MyList"));
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
		listparam.put("listview", "SM_CommonPage_Handler_MyList");
		obj.put("listparam", listparam);
		// 列表接口参数
		obj.put("exportname", "SM_CommonPage_Handler_MyList");
		obj.put("importname", "SM_CommonPage_Handler_Import");
		obj.put("idfieldname", "id");
		obj.put("sortname", "id");
		obj.put("sortorder", "desc");
		// listformparam为给添加页面接口Add_PC传参
		Map<String, Object> listformparam = DataUtils.getRequestMap(request);
		obj.put("listformparam", listformparam);
		return R.ok().put("data", obj);
	}

	@ApiOperation(value = "接口说明：ViewList_PC通用配置页面功能查看列表接口", notes = "接口说明：ViewList_PC通用配置页面功能查看列表接口")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "pageid", value = "pageid外键,必填", required = true, dataType = "String", paramType = "query"), })
	@GetMapping("/ViewList_PC")
	@ResponseBody
	public Map<String, Object> ViewList_PC(HttpServletRequest request) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		// 搜索和列表配置
		obj.put("searchSchema", service.getSearchJson("SM_CommonPage_Handler_ViewList"));
		obj.put("columnSchema", service.getListJson("SM_CommonPage_Handler_ViewList"));
		// 操作权限
		SysUser sysuser = DataUtils.getSysUser(request);
		List<String> powerList = new ArrayList<String>();
		powerList.add("export");
		obj.put("powerall", powerList);
		// listparam为给获取数据列表接口GetList传参
		Map<String, Object> listparam = DataUtils.getRequestMap(request);
		listparam.put("listview", "SM_CommonPage_Handler_ViewList");
		obj.put("listparam", listparam);
		// 列表接口参数
		obj.put("exportname", "SM_CommonPage_Handler_MyList");
		obj.put("idfieldname", "id");
		obj.put("sortname", "tablename");
		obj.put("sortorder", "ASC");
		return R.ok().put("data", obj);
	}

	@ApiOperation(value = "接口说明：Add_PC通用配置页面功能新增页面接口", notes = "接口说明：Add_PC通用配置页面功能新增页面接口")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "pageid", value = "pageid外键,必填", required = true, dataType = "String", paramType = "query"), })
	@GetMapping("/Add_PC")
	@ResponseBody
	public Map<String, Object> Add_PC(HttpServletRequest request) throws Exception {
		SysUser sysuser = DataUtils.getSysUser(request);
		// 参数整理
		String tablename = "SM_CommonPage_Handler";
		String viewname = "add";
		// 默认值
		HashMap<String, Object> obj = new HashMap<String, Object>();
		obj.put("submitman", sysuser.getUserId());
		obj.put("submittime", DataUtils.getDate("yyyy-MM-dd"));
		// formparam为给保存数据接口Save传参
		Map<String, Object> formparam = DataUtils.getRequestMap(request);
		formparam.put("viewname", viewname);
		// 接口返回数据
		List<HashMap<String, Object>> listTable = service.getAddJson(tablename, viewname, request, obj, sysuser);
		obj = new HashMap<String, Object>();
		obj.put("tableJson", listTable);
		obj.put("formparam", formparam);
		return R.ok().put("data", obj);
	}

	@ApiOperation(value = "接口说明：Edit_PC通用配置页面功能修改页面接口", notes = "接口说明：Edit_PC通用配置页面功能修改页面接口")
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
		String tablename = "SM_CommonPage_Handler";
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
		formparam.put("viewname", viewname);
		// 接口返回数据
		List<HashMap<String, Object>> listTable = service.getEditJson(tablename, viewname, request, obj);
		obj = new HashMap<String, Object>();
		obj.put("tableJson", listTable);
		obj.put("formparam", formparam);
		return R.ok().put("data", obj);
	}

	@ApiOperation(value = "接口说明：View_PC通用配置页面功能查看页面接口", notes = "接口说明：View_PC通用配置页面功能查看页面接口")
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
		String tablename = "SM_CommonPage_Handler";
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
	
	@ApiOperation(value = "接口说明：GetList通用配置页面功能获取数据列表接口"
			, notes = "接口说明：GetList通用配置页面功能获取数据列表接口")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "pageid", value = "pageid外键,必填", required = true, dataType = "String", paramType = "query"),
		@ApiImplicitParam(name = "listview", value = "列表名称，非必填，默认录入列表", required = false, dataType = "String", paramType = "query"),
		@ApiImplicitParam(name = "page", value = "当前页数", required = true, dataType = "String", paramType = "query"),
		@ApiImplicitParam(name = "rows", value = "每页条数", required = true, dataType = "String", paramType = "query"),
		@ApiImplicitParam(name = "sort", value = "排序字段", required = true, dataType = "String", paramType = "query"),
		@ApiImplicitParam(name = "order", value = "排序方式", required = true, dataType = "String", paramType = "query")
	})
	@PostMapping("/GetList")
	@ResponseBody
	public Map<String, Object> GetList(HttpServletRequest request) throws Exception {
		SysUser sysuser = DataUtils.getSysUser(request);
		// 数据实体
		MySqlData mySqlData = new MySqlData();
		// 数据库表名
		String tablename = "SM_CommonPage_Handler";
		mySqlData.setTableName(tablename);
		// 得到是哪个列表
		String listview = "SM_CommonPage_Handler_MyList";
		if (request.getParameter("listview") != null) {
			listview = request.getParameter("listview");
		}
		// 默认过滤条件
		if (StringUtils.isNotBlank(request.getParameter("pageid"))) {
			mySqlData.setFieldWhere("pageid", request.getParameter("pageid"), "=");
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
	
	/*@ApiOperation(value = "接口说明：GetListById通用配置页面功能通过id获取单条数据接口", notes = "接口说明：GetListById通用配置页面功能通过id获取单条数据接口")
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
		String tablename = "SM_CommonPage_Handler";
		// 接口返回数据
		HashMap<String, Object> obj = service.getMapByKey(tablename, id);
		return R.ok(obj);
	}*/
	
	@ApiOperation(value = "接口说明：Save通用配置页面功能保存数据接口", notes = "接口说明：Save通用配置页面功能保存数据接口")
	@PostMapping("/Save")
	@ResponseBody
	@Transactional
	public HashMap<String, Object> Save(MultipartHttpServletRequest request) throws Exception {
		SysUser sysuser = DataUtils.getSysUser(request);
		// 数据实体
		MySqlData mySqlData = new MySqlData();
		// 数据库表名
		mySqlData.setTableName("SM_CommonPage_Handler");
		// 表单的视图名称默认为add，多个视图用英文对号分隔，例句：add,view,check
		String viewname = "add";
		if (StringUtils.isNotBlank(request.getParameter("viewname"))) {
			viewname = request.getParameter("viewname");
		}
		mySqlData.setViewName(viewname);
		// 保存默认值
		if (StringUtils.isNotBlank(request.getParameter("pageid"))) {
			mySqlData.setFieldValue("pageid", request.getParameter("pageid"));
		}
		// 插入和更新判断，然后执行。
		ActionResult result;
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
	@ApiOperation(value = "接口说明：Delete通用配置页面功能删除数据接口", notes = "接口说明：Delete通用配置页面功能删除数据接口。")
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
		// 权限校验
		// 执行删除操作
		Integer iResult = service.delete("SM_CommonPage_Handler", id);
		if (iResult > 0) {
			return R.ok();
		}
		return R.error("删除0条数据。");
	}	
	
	@SuppressWarnings("unchecked")
	@ApiOperation(value = "接口说明：ExcelExport通用配置页面功能Excel导出接口"
			, notes = "接口说明：ExcelExport通用配置页面功能Excel导出接口")
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
	@ApiOperation(value = "接口说明：ExcelImport通用配置页面功能Excel导入接口", notes = "接口说明：ExcelImport通用配置页面功能Excel导入接口")
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
		String importname = "SM_CommonPage_Handler_Import";
		StringBuilder strAddField = new StringBuilder();
		StringBuilder strAddValue = new StringBuilder();
		strAddField.append("pageid,");
		strAddValue.append("'" + request.getParameter("pageid") + "',");
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
}
