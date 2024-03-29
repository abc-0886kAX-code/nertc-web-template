package com.ytxd.controller.SystemManage;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ytxd.common.DataUtils;
import com.ytxd.common.exception.RRException;
import com.ytxd.common.util.R;
import com.ytxd.controller.BaseController;
import com.ytxd.model.ActionResult;
import com.ytxd.model.MySqlData;
import com.ytxd.model.SysUser;
import com.ytxd.service.CommonService;
import com.ytxd.service.ExcelOperation.ExportTable;
import com.ytxd.service.ExcelOperation.ImportExcel;
import com.ytxd.service.SystemManage.SM_ListCollect_Service;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 *  控制层(前后台交互)
 */
@Api(value = "系统管理-列表集接口", tags = "系统管理-列表集接口")
@Controller("SM_ListCollect_Controller")
@RequestMapping(value = "/SystemManage/SM_ListCollect")
public class SM_ListCollect_Controller extends BaseController{
	@Resource
	private CommonService service;
	@Resource
	private SM_ListCollect_Service serviceLC;

	@ApiOperation(value = "接口说明：列表集录入页面接口"
			, notes = "接口说明：列表集录入页面接口。<br>"
			+ "使用位置：配置管理-列表集-录入<br>"
			+ "逻辑说明：获取列表集录入页面搜索条件和列表信息等页面元素")
	@GetMapping("/MyList_PC")
	@ResponseBody
	public Map<String, Object> MyList_PC(HttpServletRequest request, Model model) throws Exception {
		Map<String, Object> obj = new HashMap<>();
		obj.put("searchSchema", service.getSearchJson("SM_ListCollect_MyList"));
		obj.put("columnSchema", service.getListJson("SM_ListCollect_MyList"));
		//总体增删改权限
		SysUser sysuser = DataUtils.getSysUser(request);
		obj.put("powerall", getPower(sysuser, "SM_ListCollect"));
		Map<String,Object> listparam = new HashMap<>();
		listparam.put("listview", "SM_ListCollect_MyList");
		obj.put("listparam", listparam);
		obj.put("exportname", "SM_ListCollect_MyList");
		obj.put("importname", "SM_ListCollect_Import");
		obj.put("idfieldname", "id");
		obj.put("sortname", "listname");
		obj.put("sortorder", "ASC");
		return R.ok().put("data", obj);
	}
	@ApiOperation(value = "接口说明：列表集查看页面接口"
			, notes = "接口说明：列表集查看页面接口。<br>"
			+ "使用位置：配置管理-列表集-查看<br>"
			+ "逻辑说明：获取列表集查看页面搜索条件和列表信息等页面元素")
	@GetMapping("/ViewList_PC")
	@ResponseBody
	public Map<String, Object> ViewList_PC(HttpServletRequest request, Model model) throws Exception {
		Map<String, Object> obj = new HashMap<>();
		obj.put("searchSchema", service.getSearchJson("SM_ListCollect_ViewList"));
		obj.put("columnSchema", service.getListJson("SM_ListCollect_ViewList"));
		Map<String,Object> listparam = new HashMap<>();
		listparam.put("listview", "SM_ListCollect_ViewList");
		obj.put("listparam", listparam);
		obj.put("exportname", "SM_ListCollect_MyList");
		obj.put("idfieldname", "id");
		obj.put("sortname", "listname");
		obj.put("sortorder", "ASC");
		return R.ok().put("data", obj);
	}
	@ApiOperation(value = "接口说明：列表集新增页面接口"
			, notes = "接口说明：列表集新增页面接口。<br>"
			+ "使用位置：配置管理-列表集-增加<br>"
			+ "逻辑说明：点击增加按钮新增配置时，调用该接口获取一下初始化数据")
	@GetMapping("/Add_PC")
	@ResponseBody
	public Map<String, Object> Add_PC(HttpServletRequest request, Model model) throws Exception {
		SysUser sysuser = DataUtils.getSysUser(request);
		String tablename = "SM_ListCollect";
		String viewname = "add";
		HashMap<String, Object> obj = new HashMap<>();
		List<HashMap<String, Object>> listTable = service.getAddJson(tablename, viewname, request, obj, sysuser);
		obj.put("tableJson", listTable);
		return R.ok().put("data", obj);
	}
	@ApiOperation(value = "接口说明：列表集修改页面接口"
			, notes = "接口说明：列表集修改页面接口。<br>"
			+ "使用位置：配置管理-列表集-修改<br>"
			+ "逻辑说明：点击修改按钮修改配置时，调用该接口获取该配置的数据")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "唯一标识,必填", required = true, dataType = "String",paramType="query"),
	})
	@GetMapping("/Edit_PC")
	@ResponseBody
	public Map<String, Object> Edit_PC(HttpServletRequest request, Model model) throws Exception {
		String id = request.getParameter("id");
		if(StringUtils.isBlank(id)) {
			throw new RRException("主键值不能为空，请修改。");
		}
		String tablename = "SM_ListCollect";
		String viewname = "add";
		if(StringUtils.isNotBlank(request.getParameter("viewname"))) {
			viewname = request.getParameter("viewname");
		}
		HashMap<String, Object> obj = service.getMapByKey(tablename, id);
		List<HashMap<String, Object>> listTable = service.getEditJson(tablename, viewname, request, obj);
		obj = new HashMap<String, Object>();
		obj.put("tableJson", listTable);
		return R.ok().put("data", obj);
	}
	@ApiOperation(value = "接口说明：列表集查看页面接口"
			, notes = "接口说明：列表集查看页面接口。<br>"
			+ "使用位置：配置管理-列表集-查看<br>"
			+ "逻辑说明：点击配置名称查看配置基本信息，调用该接口获取该配置的数据")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "唯一标识,必填", required = true, dataType = "String",paramType="query"),
	})
	@GetMapping("/View_PC")
	@ResponseBody
	public Map<String, Object> View_PC(HttpServletRequest request, Model model) throws Exception{
		String id = request.getParameter("id");
		DataUtils.showParams(request);
		if(StringUtils.isBlank(id)) {
			throw new RRException("主键值不能为空，请修改。");
		}
		String tablename = "SM_ListCollect";
		String viewname = "add";
		if(StringUtils.isNotBlank(request.getParameter("viewname"))) {
			viewname = request.getParameter("viewname");
		}
		HashMap<String, Object> obj = service.getMapByKey(tablename, id);
		List<HashMap<String, Object>> listTable = service.getViewJson(tablename, viewname, request, obj);
		obj = new HashMap<String, Object>();
		obj.put("tableJson", listTable);
		return R.ok().put("data", obj);
	}
	@ApiOperation(value = "接口说明：获取数据列表"
			, notes = "接口说明：获取数据列表。<br>"
			+ "使用位置：系统中获取该配置列表的地方<br>"
			+ "逻辑说明：通过token获取用户信息，通过列表名称、审核状态等条件权进行数据过滤<br>"
			+ "使用数据库表：SM_ListCollect")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "checkresult", value = "审核状态,非必填，不同列表有不同默认值", required = false, dataType = "String",paramType="query"),
			@ApiImplicitParam(name = "listview", value = "列表名称，非必填，默认录入列表", required = false, dataType = "String",paramType="query"),
			@ApiImplicitParam(name = "page", value = "当前页数", required = true, dataType = "String",paramType="query"),
			@ApiImplicitParam(name = "rows", value = "每页条数", required = true, dataType = "String",paramType="query"),
			@ApiImplicitParam(name = "sort", value = "排序字段", required = true, dataType = "String",paramType="query"),
			@ApiImplicitParam(name = "order", value = "排序方式", required = true, dataType = "String",paramType="query"),
	})
	@PostMapping("/GetList")
	@ResponseBody
	public Map<String, Object> GetList(HttpServletRequest request) throws Exception {
		SysUser sysuser = DataUtils.getSysUser(request);
		//数据实体
		MySqlData mySqlData = new MySqlData();
		//数据库表名
		String tablename = "SM_ListCollect";
		mySqlData.setTableName(tablename);
		//得到是哪个列表
		String listview = "SM_ListCollect_MyList";
		if(request.getParameter("listview") != null) {
			listview = request.getParameter("listview");
		}
		//默认过滤条件
		//查询配置名称，就是列表上头的查询配置名称
		String searchPageName = listview;
		//权限校验
		//返回实体
		ActionResult result = service.getList(request, mySqlData, tablename, searchPageName);
		if(!result.getSuccess()) {
			return R.error(result.getMsg());
		}
		return R.ok().put("data", result);
	}
	@ApiOperation(value = "接口说明：通过id获取配置基本信息"
			, notes = "接口说明：通过id获取配置基本信息。<br>"
			+ "使用位置：系统中通过id获取对应的配置基本信息的地方<br>"
			+ "逻辑说明：通过传过来的唯一标识，到数据库查出唯一的对应记录，返回到前端"
			+ "使用数据库表：SM_ListCollect")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "唯一标识,必填", required = true, dataType = "String",paramType="query"),
	})
	@GetMapping("/GetListById")
	@ResponseBody
	public HashMap<String, Object> GetListById(HttpServletRequest request) throws Exception {
		String id = request.getParameter("id");
		if(StringUtils.isBlank(id)) {
			return R.error("主键值不能为空，请修改。");
		}
		String tablename = "SM_ListCollect";
		HashMap<String, Object> obj = service.getMapByKey(tablename, id);
		return R.ok(obj);
	}
	@ApiOperation(value = "接口说明：保存接口"
			, notes = "接口说明：保存接口。<br>"
			+ "使用位置：新增和修改后保存信息<br>"
			+ "逻辑说明：通过判断id是否为空判断是新增还是修改，将表单信息保存到数据表中"
			+ "使用数据库表：SM_ListCollect")
	@PostMapping("/Save")
	@ResponseBody
	@Transactional
	public HashMap<String, Object> Save(MultipartHttpServletRequest request) throws Exception {
		SysUser sysuser = DataUtils.getSysUser(request);
		//数据实体
		MySqlData mySqlData = new MySqlData();
		//数据库表名
		mySqlData.setTableName("SM_ListCollect");
		//表单的视图名称默认为add，多个视图用英文对号分隔，例句：add,view,check
		String viewname = "add";
		mySqlData.setViewName(viewname);
		//保存默认值
		
		//插入和更新判断，然后执行。
		ActionResult result = new ActionResult();
		String id = request.getParameter("id");
		if(StringUtils.isBlank(id)) {
			//执行插入操作
			result = service.insert(request, mySqlData);
			//增加保存之后的处理事件，例如 将负责人添加到作者列表
		} else {
			HashMap<String, Object> map = service.getMap("SM_ListCollect", id);
			//执行更新操作
			mySqlData.setFieldWhere("id", id, "=");
			result = service.update(request, mySqlData);
			//修改保存之后的处理事件
			if(!DataUtils.getMapKeyValue(map, "listname").equals(request.getParameter("listname"))) {
				MySqlData updateItem = new MySqlData();
				updateItem.setTableName("SM_ListItem");
				updateItem.setFieldValue("listname", request.getParameter("listname"));
				updateItem.setFieldWhere("listname", DataUtils.getMapKeyValue(map, "listname"), "=");
				service.update(updateItem);
			}
		}
		if(result.getSuccess()) {
			return R.ok().put("data", result.getData());
		} else {
			return R.error(result.getMsg());
		}
	}
	@ApiOperation(value = "接口说明：删除数据接口"
			, notes = "接口说明：删除数据接口。<br>"
			+ "使用位置：系统中删除记录的地方<br>"
			+ "逻辑说明：将指定id的记录删除"
			+ "使用数据库表：SM_ListCollect")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "唯一标识,必填", required = true, dataType = "String",paramType="query"),
	})
	@RequestMapping(value = "/Delete", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	@Transactional
	public HashMap<String, Object> Delete(HttpServletRequest request) throws Exception {
		//数据有效性校验
		String id = request.getParameter("id");
		if(StringUtils.isBlank(id)) {
			return R.error("主键值不能为空，请修改。");
		}
		
		//删除成功后可以删除关联子表数据
		MySqlData mySqlData = new MySqlData();
		mySqlData.setSelectField("listname", "listname");
		mySqlData.setTableName("SM_ListCollect");
		mySqlData.setFieldWhere("id", id, "in");
		List<HashMap<String, Object>> list = service.getDataList(mySqlData);
		for (HashMap<String, Object> map : list) {
			String listname = DataUtils.getMapKeyValue(map, "listname");
			if(StringUtils.isNotBlank(listname)) {
				service.delete("SM_ListItem", listname, "ListName");
			}
		}
		//执行删除操作
		Integer iResult = service.delete("SM_ListCollect", id);
		if(iResult > 0) {
			return R.ok();
		} else {
			return R.error("删除0条数据。");
		}
	}
	
	
	@SuppressWarnings("unchecked")
	@ApiOperation(value = "接口说明：Excel导出接口"
			, notes = "接口说明：Excel导出接口。<br>"
			+ "使用位置：各列表中导出Excel按钮<br>"
			+ "逻辑说明：通过exportName和其他过滤条件，导出相应的Excel"
			+ "使用数据库表：SM_ListCollect")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "exportName", value = "导出配置名，必填", required = true, dataType = "String",paramType="query"),
	})
	@PostMapping("/ExcelExport")
	@ResponseBody
	public Map<String, Object> ExcelExport(HttpServletRequest request) throws Exception {
		//得到导出配置名称
		String exportName = request.getParameter("exportname");
		if(StringUtils.isBlank(exportName)) {
			return R.error("导出配置名称不能为空，请修改。");
		}
		//导出Excel
		ExportTable export = new ExportTable();
		String strError = export.setExcelCollect(service.getExcelCollect(exportName));
		if(StringUtils.isNotBlank(strError)) {
			return R.error(strError);
		}
		strError = export.setExcelItem(service.getExcelItem(exportName));
		if(StringUtils.isNotBlank(strError)) {
			return R.error(strError);
		}
		//得到要导出的数据
		List<HashMap<String, Object>> listData = (List<HashMap<String, Object>>)((ActionResult)GetList(request).get("data")).getData();
		export.setSource(listData);
		export.setProjectPath(request.getSession().getServletContext().getRealPath(""));
		strError = export.Export();
		if(StringUtils.isNotBlank(strError)) {
			return R.error(strError);
		} else {
			return R.ok().put("data", export.GetDownFilePath());
		}
	}
	@SuppressWarnings("null")
	@ApiOperation(value = "接口说明：Excel导入接口"
			, notes = "接口说明：Excel导入接口。<br>"
			+ "使用位置：录入列表导入Excel<br>"
			+ "逻辑说明：将Excel文件中的数据保存到数据表"
			+ "使用数据库表：SM_ListCollect")
	@PostMapping("/ExcelImport")
	@ResponseBody
	public Map<String, Object> ExcelImport(MultipartHttpServletRequest request) throws Exception {
		SysUser sysuser = DataUtils.getSysUser(request);
		//文件上传
		MultipartFile requestFile = request.getFile("importfile");
		if(requestFile == null && requestFile.isEmpty()) {
			R.error("导入文件不能为空，请修改。");
		}
		String fileName =  requestFile.getOriginalFilename();
	    fileName = DataUtils.getDate("yyyyMMdd")+(new Random().nextInt(900000)+100000)+fileName;
	    String filePath = request.getSession().getServletContext().getRealPath("UpFile/");
	    File file = new File(filePath);
	    if(!file.exists()){
	    	file.mkdirs();
	    }
	    file = new File(filePath, fileName);
		requestFile.transferTo(file);
		//Excel导入
		String importname = "SM_ListCollect_Import";
		StringBuilder strAddField = new StringBuilder();
		StringBuilder strAddValue = new StringBuilder();
		ImportExcel importExcel = new ImportExcel();
		importExcel.setImportName(importname);
		importExcel.setAddField(strAddField.toString());
		importExcel.setAddValue(strAddValue.toString());
		importExcel.setFilePath(filePath+"/"+fileName);
	    if(StringUtils.isNotBlank(request.getParameter("sheetname"))){
	    	importExcel.setSheetName(request.getParameter("sheetname"));
	    }
	    if(StringUtils.isNotBlank(request.getParameter("rownumber"))){
	    	importExcel.setRowNumber(request.getParameter("rownumber"));
	    }
	    String strError = importExcel.Import();
	    if(StringUtils.isBlank(strError)) {
	    	return R.ok();
	    } else {
	    	return R.error(strError).put("PATH", "UpFile/"+fileName);
	    }
	}





	//***扩展***************************************************************************************
	@ApiOperation(value = "接口说明：导出SQL文件"
			, notes = "接口说明：导出SQL文件。<br>"
			+ "使用位置：导出SQL文件<br>"
			+ "逻辑说明：通过id导出SQL文件"
			+ "使用数据库表：SM_ListCollect")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "id", required = true, dataType = "String",paramType="query"),
	})
	@GetMapping("/ExportSql")
	@ResponseBody
	public Map<String, Object> ExportSql(HttpServletRequest request) throws Exception {
		String id = request.getParameter("id");
		if(StringUtils.isNotBlank(id)) {
			id = id.replace(" ", "").replace("'", "");
		} else {
			return R.error("请选择数据。");
		}
	    String filePath = serviceLC.ExportSql(request, id);
	    return R.ok().put("PATH", filePath);
	}

	@ApiOperation(value = "接口说明：初始化Excel导出"
			, notes = "接口说明：初始化Excel导出。<br>"
			+ "使用位置：各列表中初始化Excel导出<br>"
			+ "逻辑说明：通过tableName初始化Excel导出"
			+ "使用数据库表：SM_ListCollect")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "listname", value = "列表名称", required = true, dataType = "String",paramType="query"),
	})
	@GetMapping("/InitExcelByList")
	@ResponseBody
	public Map<String, Object> InitExcelByList(HttpServletRequest request) throws Exception {
		SysUser sysuser = DataUtils.getSysUser(request);
		String listname = request.getParameter("listname");
		if(StringUtils.isNotBlank(listname)) {
			listname = listname.replace(" ", "").replace("'", "");
		} else {
			return R.error("请选择数据。");
		}
		Integer result = serviceLC.InitExcelByList(request, listname);
		if(result > 0) {
			return R.ok();
		} else {
			return R.error("初始化失败。");
		}
	}

	@ApiOperation(value = "接口说明：更新所有列表"
			, notes = "接口说明：更新所有列表。<br>"
			+ "使用位置：列表中更新所有列表<br>"
			+ "逻辑说明：更新所有列表"
			+ "使用数据库表：SM_ListCollect")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "要更新的列表id", required = true, dataType = "String",paramType="query"),
	})
	@GetMapping("/UpdateAllListHtml")
	@ResponseBody
	@Transactional
	public HashMap<String, Object> UpdateAllListHtml(HttpServletRequest request) throws Exception {
		String id = request.getParameter("id");
		//找到所有SearchHtml为空的记录
		MySqlData mySqlData = new MySqlData();
		mySqlData.setTableName("SM_ListCollect");
		if(StringUtils.isNotBlank(id)) {
			mySqlData.setFieldWhere("id", id, "in");
		} else {
			mySqlData.setFieldWhere("ListHtml", "ListHtml IS NULL", "sql");
		}
		List<HashMap<String, Object>> list = service.getDataList(mySqlData);
		for(Map<String, Object> map : list) {
			//成功后更新主表SearchHtml的值
			serviceLC.UpdateListHtml(request, DataUtils.getMapKeyValue(map, "listname"));
		}
		
		return R.ok();
	}
}
