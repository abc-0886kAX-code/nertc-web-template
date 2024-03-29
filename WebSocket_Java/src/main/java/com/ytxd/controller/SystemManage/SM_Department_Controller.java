package com.ytxd.controller.SystemManage;

import com.ytxd.common.DataUtils;
import com.ytxd.common.annotation.AuthIgnore;
import com.ytxd.common.exception.RRException;
import com.ytxd.common.util.R;
import com.ytxd.controller.BaseController;
import com.ytxd.model.ActionResult;
import com.ytxd.model.EasyTree;
import com.ytxd.model.MySqlData;
import com.ytxd.model.SysUser;
import com.ytxd.model.SystemManage.SM_Department;
import com.ytxd.service.CommonService;
import com.ytxd.service.ExcelOperation.ExportTable;
import com.ytxd.service.ExcelOperation.ImportExcel;
import com.ytxd.service.SystemManage.SM_Department_Service;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.*;

/**
 * 控制层(前后台交互)
 */
@Controller("sM_Department_Controller")
@RequestMapping(value = "/SystemManage/SM_Department")
@Api(value = "系统管理-组织机构接口", tags = "系统管理-组织机构接口")
public class SM_Department_Controller extends BaseController {
	@Autowired
	private SM_Department_Service Service;
	@Resource
	private CommonService service;

	@ApiOperation(value = "接口说明：组织机构录入页面接口"
			, notes = "接口说明：组织机构录入页面接口。<br>"
			+ "使用位置：系统管理-组织机构-录入列表<br>"
			+ "逻辑说明：获取组织机构录入列表页面搜索条件和列表信息等页面元素") 
	@GetMapping("/MyList_PC")
	@ResponseBody
	public Map<String, Object> MyList_PC(HttpServletRequest request, Model model) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("searchSchema", service.getSearchJson("SM_Department_MyList"));
		obj.put("columnSchema", service.getListJson("SM_Department_MyList"));
		Map<String,Object> listparam=new HashMap<String, Object>();
		listparam.put("listview", "SM_Department_MyList");
		SysUser sysuser = DataUtils.getSysUser(request);
		if (StringUtils.isNotBlank(request.getParameter("parentid"))) {
			obj.put("parentid", request.getParameter("parentid"));
		} else {
			if(!sysuser.getFunctionPermissions("SM_Department_MyList_All")) {
				obj.put("parentid", sysuser.getDeptId());
			}else{
				obj.put("parentid", "0");
			}
		}
		obj.put("listparam", listparam);
		obj.put("exportname", "SM_Department_MyList");
		obj.put("importname", "SM_Department_Import");
		obj.put("idfieldname", "departmentid");
		obj.put("sortname", "orderid");
		obj.put("sortorder", "ASC");
		return R.ok().put("data", obj);
	}

	@RequestMapping("/ViewList_PC")
	@ResponseBody
	public R ViewList(HttpServletRequest request, Model model) throws Exception {
		SysUser sysuser = DataUtils.getSysUser(request);
		Map<String, Object> obj = new HashMap<String, Object>();
		if (StringUtils.isNotBlank(request.getParameter("parentid"))) {
			obj.put("parentid", request.getParameter("parentid"));
		} else {
			obj.put("parentid", sysuser.getDeptId());
		}
		obj.put("searchSchema", service.getSearchJson("SM_Department_ViewList"));
		obj.put("columnSchema", service.getListJson("SM_Department_ViewList"));
		return R.ok().put("data", obj);
	}


	@ApiOperation(value = "接口说明：组织机构新增页面接口"
			, notes = "接口说明：组织机构新增页面接口。<br>"
			+ "使用位置：成果管理-组织机构-增加页面<br>"
			+ "逻辑说明：点击增加按钮新增时，调用该接口获取页面元素和默认值。") 
	@ApiImplicitParams({
		@ApiImplicitParam(name = "parentid", value = "父级节点的departmentid", required = true, dataType = "String", paramType = "query"),
	})
	@GetMapping("/Add_PC")
	@ResponseBody
	public Map<String, Object> Add_PC(HttpServletRequest request, Model model) throws Exception {
		SysUser sysuser = DataUtils.getSysUser(request);
		String tablename = "SM_Department";
		String viewname = "01";
		HashMap<String, Object> obj = new HashMap<String, Object>();
		obj.put("submitman", sysuser.getUserId());
		obj.put("submittime", DataUtils.getDate("yyyy-MM-dd"));
		String parentid = request.getParameter("parentid");
		if (parentid.length() <= 3 && sysuser.getDeptId() != null) {
			if (!sysuser.getDeptId().equals("0") && !sysuser.getDeptId().equals("001")) {
				parentid = sysuser.getDeptId();
			}
		}
		obj.put("parentid", parentid);
		List<HashMap<String, Object>> listTable = service.getAddJson(tablename, viewname, request, obj, sysuser);
		obj.put("tableJson", listTable);
		return R.ok().put("data", obj);
	}


	@ApiOperation(value = "接口说明：组织机构修改页面接口"
			, notes = "接口说明：组织机构修改页面接口。<br>"
			+ "使用位置：成果管理-组织机构-修改页面<br>"
			+ "逻辑说明：点击修改按钮修改时，调用该接口获取页面元素和数据。") 
	@ApiImplicitParams({
			@ApiImplicitParam(name = "departmentid", value = "数据唯一标识", required = true, dataType = "String", paramType = "query"),
	})
	@GetMapping("/Edit_PC")
	@ResponseBody
	public Map<String, Object> Edit_PC(HttpServletRequest request, Model model) throws Exception {
		String departmentid = request.getParameter("departmentid");
		String depttype = request.getParameter("depttype");
		if (StringUtils.isBlank(departmentid)) {
			throw new RRException("主键值不能为空，请修改。");
		}

		SysUser sysuser = DataUtils.getSysUser(request);
		String tablename = "SM_Department";
		String viewname = "System,01" ;
		String checkId = departmentid.substring(0,3);
		if(!Objects.equals(checkId,"001")) {
			viewname = "System,Organization";
		}
		if(StringUtils.isNotBlank(request.getParameter("viewname"))) {
			viewname = request.getParameter("viewname");
		}
		HashMap<String, Object> obj = service.getMapByKey(tablename, departmentid, "departmentid");
		List<HashMap<String, Object>> listTable = service.getEditJson(tablename, viewname, request, obj);
		obj = new HashMap<String, Object>();
		obj.put("tableJson", listTable);
		return R.ok().put("data", obj);
	}

	@ApiOperation(value = "接口说明：组织机构查看页面接口"
			, notes = "接口说明：组织机构查看页面接口。<br>"
			+ "使用位置：成果管理-组织机构-查看页面<br>"
			+ "逻辑说明：点击查看超链接查看详细信息，调用该接口获取页面元素和数据。") 
	@ApiImplicitParams({
			@ApiImplicitParam(name = "departmentid", value = "数据唯一标识", required = true, dataType = "String", paramType = "query"),
	})
	@GetMapping("/View_PC")
	@ResponseBody
	public Map<String, Object> View_PC(HttpServletRequest request, Model model) throws Exception{
		String departmentid = request.getParameter("departmentid");
		if (StringUtils.isBlank(departmentid)) {
			throw new RRException("主键值不能为空，请修改。");
		}
		SysUser sysuser = DataUtils.getSysUser(request);
		String tablename = "SM_Department";
		String viewname = "01";
		if (StringUtils.isNotBlank(request.getParameter("viewname"))) {
			viewname = request.getParameter("viewname");
		}
		HashMap<String, Object> obj = service.getMapByKey(tablename, departmentid, "departmentid");
		List<HashMap<String, Object>> listTable = service.getViewJson(tablename, viewname, request, obj);
		obj = new HashMap<String, Object>();
		obj.put("tableJson", listTable);
		return R.ok().put("data", obj);
	}

	@ApiOperation(value = "接口说明：录入和查看等列表得到列表数据"
			, notes = "接口说明：录入和查看等列表得到列表数据。<br>"
			+ "使用位置：录入和查看等列表得到列表数据。<br>"
			+ "逻辑说明：通过过滤条件和分页信息得到数据列表。<br>"
			+ "使用数据库表：SM_Department") 
	@ApiImplicitParams({
			@ApiImplicitParam(name = "checkresult", value = "审核状态,非必填，不同列表有不同默认值", required = false, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "listview", value = "列表名称，非必填，默认录入列表", required = false, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "parentid", value = "父级节点", required = false, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "status", value = "状态，01可用，00不可用。", required = false, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "page", value = "当前页数", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "rows", value = "每页条数", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "sort", value = "排序字段,departmentid", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "order", value = "排序方式", required = true, dataType = "String", paramType = "query") 
	})
	@PostMapping("/GetList")
	@ResponseBody
	public Map<String, Object> GetList(HttpServletRequest request) throws Exception {
		SysUser sysuser = DataUtils.getSysUser(request);
		// 数据实体
		MySqlData mySqlData = new MySqlData();
		// 数据库表名
		String tablename = "SM_Department";
		mySqlData.setTableName(tablename);
		// 得到是哪个列表
		String listview = "SM_Department_MyList";
		if (request.getParameter("listview") != null) {
			listview = request.getParameter("listview");
		}
		// 默认过滤条件
		String parentid= request.getParameter("parentid");
		if (StringUtils.isNotBlank(request.getParameter("parentid"))) {
			mySqlData.setFieldWhere("parentid", request.getParameter("parentid"), "=");
		}
		if (StringUtils.isNotBlank(request.getParameter("status"))) {
			mySqlData.setFieldWhere("status", request.getParameter("status"), "=");
		} else {
			mySqlData.setFieldWhere("status", "01", "=");
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

	@ApiOperation(value = "接口说明：通过唯一标识获取一条数据"
			, notes = "接口说明：通过唯一标识获取一条数据<br>"
			+ "使用位置：通过唯一标识获取一条数据<br>"
			+ "逻辑说明：通过传过来的唯一标识，到数据库查出唯一的对应记录，返回到前端。<br>"
			+ "使用数据库表：SM_Department") 
	@ApiImplicitParams({
        @ApiImplicitParam(name = "departmentid", value = "部门唯一标识,必填", required = true, dataType = "String",paramType="query"),
	})
	@GetMapping("/GetListById")
	@ResponseBody
	public HashMap<String, Object> GetListById(HttpServletRequest request) throws Exception {
		String departmentid = request.getParameter("departmentid");
		if (StringUtils.isBlank(departmentid)) {
			return R.error("主键值不能为空，请修改。");
		}
		String tablename = "SM_Department";
		HashMap<String, Object> obj = service.getMapByKey(tablename, departmentid, "departmentid");
		return R.ok(obj);
	}

	@ApiOperation(value = "接口说明：保存数据接口"
			, notes = "接口说明：保存数据接口。<br>"
			+ "使用位置：新增和修改后保存数据<br>"
			+ "逻辑说明：通过id是否为空判断是新增还是修改，将表单信息保存到数据表中。<br>"
			+ "使用数据库表：SM_Department") 
	@ApiImplicitParams({
		@ApiImplicitParam(name = "parentid", value = "父级节点的departmentid", required = true, dataType = "String", paramType = "query"),
	})
	@PostMapping("/Save")
	@ResponseBody
	@Transactional
	public HashMap<String, Object> Save(MultipartHttpServletRequest request) throws Exception {
		SysUser sysuser = DataUtils.getSysUser(request);
		// 数据实体
		MySqlData mySqlData = new MySqlData();
		// 数据库表名
		mySqlData.setTableName("SM_Department");
		// 表单的视图名称默认为add，多个视图用英文对号分隔，例句：add,view,check
		String viewname = "01";
		mySqlData.setViewName(viewname);
		

		// 插入和更新判断，然后执行。
		ActionResult result = new ActionResult();
		String departmentid = request.getParameter("departmentid");
		if (StringUtils.isBlank(departmentid)) {
			// 保存默认值
			if (StringUtils.isNotBlank(request.getParameter("parentid"))) {
				mySqlData.setFieldValue("parentid", request.getParameter("parentid"));
			} else {
				throw new RRException("parentid不能为空，请修改。");
			}
			// 执行插入操作
			String parentid = request.getParameter("parentid");
			departmentid = GetDepartmentId(parentid);
			mySqlData.setFieldValue("departmentid", departmentid);
			mySqlData.setFieldValue("status", "01");
			result = service.insert(request, mySqlData);
			// 增加保存之后的处理事件，例如 将负责人添加到作者列表
		} else {
			// 执行更新操作
			mySqlData.setFieldWhere("departmentid", departmentid, "=");
			result = service.update(request, mySqlData);
			// 修改保存之后的处理事件，例如 将负责人添加到作者列表
		}
		if (result.getSuccess()) {
			return R.ok().put("data", result.getData());
		} else {
			return R.error(result.getMsg());
		}
	}

	@ApiOperation(value = "接口说明：Delete部门删除数据接口", notes = "接口说明：Delete部门删除数据接口。")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "departmentid", value = "主键，必填。", required = true, dataType = "String", paramType = "query") })
	@PostMapping("/Delete")
	@ResponseBody
	@Transactional
	public Map<String, Object> Delete(HttpServletRequest request) throws Exception {
		// 主键参数判断
		String departmentid = request.getParameter("departmentid");
		if (StringUtils.isBlank(departmentid)) {
			return R.error("您要删除的记录不存在，请修改。");
		}
		// 验证部门存在子部门
		MySqlData mySqlData = new MySqlData();
		mySqlData.setTableName("sm_department");
		mySqlData.setFieldWhere("parentid", departmentid, "=");
		Map<String, Object> map = service.getMap(mySqlData);
		if(!map.isEmpty()) {
			R.error("当前部门存在子部门，不能删除");
		}
		// 验证部门下存在用户账号
		mySqlData = new MySqlData();
		mySqlData.setTableName("sm_users");
		mySqlData.setFieldWhere("departmentid", departmentid, "=");
		mySqlData.setFieldWhere("disabled", "01", "=");
		map = service.getMap(mySqlData);
		if(!map.isEmpty()) {
			R.error("当前部门下存在用户账号，不能删除");
		}
		// 执行删除操作
		Integer iResult = service.delete("sm_department", departmentid, "departmentid");
		if(iResult > 0) {
			return R.ok();
		} else {
			return R.error("删除0条数据。");
		}
	}

	@ApiOperation(value = "接口说明：Excel导出接口"
			, notes = "接口说明：Excel导出接口。<br>"
			+ "使用位置：各列表中导出Excel按钮<br>"
			+ "逻辑说明：通过exportname和列表过滤条件，导出相应的数据到Excel中。<br>"
			+ "使用数据库表：SM_Department") 
	@ApiImplicitParams({
			@ApiImplicitParam(name = "exportname", value = "导出配置名，必填", required = true, dataType = "String", paramType = "query"),
	})
	@SuppressWarnings("unchecked")
	@PostMapping("/ExcelExport")
	@ResponseBody
	public Map<String, Object> ExcelExport(HttpServletRequest request) throws Exception {
		// 得到导出配置名称
		String exportName = request.getParameter("exportname");
		if (StringUtils.isBlank(exportName)) {
			return R.error("导出配置名称不能为空，请修改。");
		}
		// 导出Excel
		ExportTable export = new ExportTable();
		String strError = export.setExcelCollect(service.getExcelCollect(exportName));
		if (StringUtils.isNotBlank(strError)) {
			return R.error(strError);
		}
		strError = export.setExcelItem(service.getExcelItem(exportName));
		if (StringUtils.isNotBlank(strError)) {
			return R.error(strError);
		}
		// 得到要导出的数据
		List<HashMap<String, Object>> listData = (List<HashMap<String, Object>>)((ActionResult)GetList(request).get("data")).getData();
		export.setSource(listData);
		export.setProjectPath(request.getSession().getServletContext().getRealPath(""));
		strError = export.Export();
		if (StringUtils.isNotBlank(strError)) {
			return R.error(strError);
		} else {
			return R.ok().put("PATH", export.GetDownFilePath());
		}
	}

	@ApiOperation(value = "接口说明：Excel导入接口"
			, notes = "接口说明：Excel导入接口。<br>"
			+ "使用位置：录入列表导入Excel<br>"
			+ "逻辑说明：将Excel文件中的数据保存到数据表<br>"
			+ "使用数据库表：SM_Department") 
	@SuppressWarnings("null")
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
		String importname = "SM_Department_Import";
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

	// ***扩展***************************************************************************************
	@SuppressWarnings("unused")
	private String GetDepartmentId(String parentid) throws Exception {
		if (StringUtils.isBlank(parentid)) {
			parentid = "0";
		}
		// 数据实体
		MySqlData mySqlData = new MySqlData();
		mySqlData.setSql("SELECT ifnull(CONCAT(CASE WHEN ");
		mySqlData.setSqlValue(parentid);
		mySqlData.setSql("='0' THEN '' ELSE ");
		mySqlData.setSqlValue(parentid);
		mySqlData.setSql(
				" END,SUBSTR(CONCAT('000',(ifnull(MAX(SUBSTR(DepartmentId,-3,3)),0)+1)),-3,3)),CONCAT(CASE WHEN ");
		mySqlData.setSqlValue(parentid);
		mySqlData.setSql("='0' THEN '' ELSE ");
		mySqlData.setSqlValue(parentid);
		mySqlData.setSql(" END,'001')) DepartmentId ");
		mySqlData.setSql("FROM SM_DEPARTMENT ");
		mySqlData.setSql("WHERE PARENTID=");
		mySqlData.setSqlValue(parentid);
		HashMap<String, Object> map = service.getMapByKey(mySqlData);
		return DataUtils.getMapKeyValue(map, "departmentid");
	}

	@ApiOperation(value = "接口说明：左侧树形数据接口"
			, notes = "接口说明：左侧树形数据接口<br>"
			+ "使用位置：左侧树形数据接口<br>"
			+ "使用数据库表：SM_Department") 
	@PostMapping("/GetListForTree_PC")
	@ResponseBody
	public Map<String, Object> GetListForTree_PC(HttpServletRequest request, SM_Department obj) throws Exception {
		SysUser sysuser = DataUtils.getSysUser(request);
		if(StringUtils.isBlank(obj.getstatus())) {
			obj.setstatus("01");
		}
		if (StringUtils.isBlank(obj.getparentid())) {
			if(!sysuser.getFunctionPermissions("SM_Department_MyList_All")) {
				obj.setparentid(sysuser.getDeptId());
			}else{
				obj.setparentid("0");
			}
		}
		return R.ok().put("data", Service.GetListForTree(obj, sysuser));
	}
	@RequestMapping(value = "/GetListForTree", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public List<EasyTree> GetListForTree(HttpServletRequest request, SM_Department obj) throws Exception {
		SysUser sysuser = DataUtils.getSysUser(request);
		if(StringUtils.isBlank(obj.getstatus())) {
			obj.setstatus("01");
		}
		if (StringUtils.isBlank(obj.getparentid())) {
			if(!sysuser.getFunctionPermissions("SM_Department_MyList_All")) {
				obj.setparentid(sysuser.getDeptId());
			}else{
				obj.setparentid("0");
			}
		}
		return Service.GetListForTree(obj, sysuser);
	}
	@AuthIgnore
	@RequestMapping(value = "/GetListForTree_New", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public List<EasyTree> GetListForTree_New(HttpServletRequest request, SM_Department obj) throws Exception {
		if(StringUtils.isBlank(obj.getstatus())) {
			obj.setstatus("01");
		}
		if (StringUtils.isBlank(obj.getparentid())) {
			obj.setparentid("0");
		}
		return Service.GetListForTree_New(obj);
	}
	@AuthIgnore
	@RequestMapping(value = "/GetListUnit", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public List<EasyTree> GetListUnit(HttpServletRequest request) throws Exception {
		Map<String,Object> obj = DataUtils.getParameterMap(request);
		return Service.GetListUnit(obj);
	}

	@AuthIgnore
	@RequestMapping(value = "/getSMDeParUnit", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public List<EasyTree> getSMDeParUnit(HttpServletRequest request) throws Exception {
		Map<String,Object> obj = DataUtils.getParameterMap(request);
		return Service.getSMDeParUnit(obj);
	}

	/**
	 * 获取此节点和子节子节点的所有的数据
	 *
	 * @param request
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/GetParentAndChildForTree")
	@ResponseBody
	public List<EasyTree> GetParentAndChildForTree(HttpServletRequest request, SM_Department obj) throws Exception {
		SysUser user = (SysUser) request.getSession().getAttribute("sysuser");
		return Service.GetParentAndChildForTree(obj, user);
	}

	/*
	 * 改变状态
	 * 
	 */
	@ApiOperation(value = "接口说明：删除数据接口", notes = "接口说明：删除数据接口。<br>" + "使用位置：系统中删除记录的地方<br>" + "逻辑说明：将指定id的记录删除数据<br>"
			+ "使用数据库表：SM_Department")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "departmentid", value = "唯一标识。", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "status", value = "状态，删除标识。", required = true, dataType = "String", paramType = "query"), })
	@PostMapping("/SetStatus_PC")
	@ResponseBody
	public Map<String, Object> SetStatus_PC(HttpServletRequest request) throws Exception {
		// 主键参数判断
		String departmentid = request.getParameter("departmentid");
		if (StringUtils.isBlank(departmentid)) {
			return R.error("您要删除的记录不存在，请修改。");
		}
		// 验证部门存在子部门
		MySqlData mySqlData = new MySqlData();
		mySqlData.setTableName("sm_department");
		mySqlData.setFieldWhere("parentid", departmentid, "=");
		Map<String, Object> map = service.getMap(mySqlData);
		if (!map.isEmpty()) {
			R.error("当前部门存在子部门，不能删除");
		}
		// 验证部门下存在用户账号
		mySqlData = new MySqlData();
		mySqlData.setTableName("sm_users");
		mySqlData.setFieldWhere("departmentid", departmentid, "=");
		mySqlData.setFieldWhere("disabled", "01", "=");
		map = service.getMap(mySqlData);
		if (!map.isEmpty()) {
			R.error("当前部门下存在用户账号，不能删除");
		}
		// 执行删除操作
		String status = StringUtils.isBlank(request.getParameter("status")) ? "00" : request.getParameter("status");
		mySqlData = new MySqlData();
		mySqlData.setTableName("sm_department");
		mySqlData.setFieldValue("status", status);
		mySqlData.setFieldWhere("parentid", departmentid, "=");
		ActionResult result = service.update(mySqlData);
		if (result.getSuccess()) {
			return R.ok();
		} else {
			return R.error("操作失败！");
		}
	}
	
	@RequestMapping("/SetStatus")
	@ResponseBody
	public Integer SetStatus(HttpServletRequest request, SM_Department obj) throws Exception {
		// 主键参数判断
		String departmentid = request.getParameter("departmentid");
		if (StringUtils.isBlank(departmentid)) {
			return 2;
		}
		// 验证部门存在子部门
		MySqlData mySqlData = new MySqlData();
		mySqlData.setTableName("sm_department");
		mySqlData.setFieldWhere("parentid", departmentid, "=");
		Map<String, Object> map = service.getMap(mySqlData);
		if (!map.isEmpty()) {
			return 2;
		}
		// 验证部门下存在用户账号
		mySqlData = new MySqlData();
		mySqlData.setTableName("sm_users");
		mySqlData.setFieldWhere("departmentid", departmentid, "=");
		mySqlData.setFieldWhere("disabled", "01", "=");
		map = service.getMap(mySqlData);
		if (!map.isEmpty()) {
			return 3;
		}
		// 执行删除操作
		String status = StringUtils.isBlank(request.getParameter("status")) ? "00" : request.getParameter("status");
		mySqlData = new MySqlData();
		mySqlData.setTableName("sm_department");
		mySqlData.setFieldValue("status", status);
		mySqlData.setFieldWhere("parentid", departmentid, "=");
		ActionResult result = service.update(mySqlData);
		if (result.getSuccess()) {
			return 1;
		} else {
			return 2;
		}
	}
	@ApiIgnore
	@RequestMapping("/DeptSync")
	@ResponseBody
	@Transactional
	public HashMap<String, Object> DeptSync(HttpServletRequest request) throws Exception {
		return R.ok();
	}
}
