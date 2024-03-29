package com.ytxd.controller.Achievements;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ytxd.common.DataUtils;
import com.ytxd.common.annotation.AuthIgnore;
import com.ytxd.common.exception.RRException;
import com.ytxd.common.util.R;
import com.ytxd.controller.BaseController;
import com.ytxd.model.ActionResult;
import com.ytxd.model.MySqlData;
import com.ytxd.model.SysUser;
import com.ytxd.service.CommonService;
import com.ytxd.service.ExcelOperation.ExportTable;
import com.ytxd.service.ExcelOperation.ImportExcel;
import com.ytxd.service.common.AM_Achievement_User_Service;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

/**
 *  控制层(前后台交互)
 */
@Controller("AM_Papers_Controller")
@RequestMapping(value = "/Achievements/AM_Papers")
@Api(value = "论文接口", tags = "论文接口")
public class AM_Papers_Controller extends BaseController{
	@Resource
	private CommonService service;
	@Resource
	private AM_Achievement_User_Service serviceUser;
	@Value("${server.servlet.context-path}")
	private String projectname;

	@ApiOperation(value = "接口说明：论文录入页面接口"
			, notes = "接口说明：论文录入页面接口。<br>"
			+ "使用位置：成果管理-论文-录入<br>"
			+ "逻辑说明：获取论文录入页面搜索，列表和得到数据的接口地址") 
	@GetMapping("/MyList")
	@ResponseBody
	public Map<String, Object> MyList(HttpServletRequest request) throws Exception {
		SysUser sysuser = DataUtils.getSysUser(request);
		Map<String, Object> service = new HashMap<String, Object>();
		// 搜索
		Map<String, Object> search = new HashMap<String, Object>();
		search.put("uri", projectname + "/Common/search.do");
		Map<String, Object> searchprops = new HashMap<String, Object>();
		searchprops.put("searchname", "AM_Papers_MyList");
		search.put("props", searchprops);
		service.put("search", search);
		
		// 按钮和列表
		Map<String, Object> tableColumn = new HashMap<String, Object>();
		tableColumn.put("uri", projectname + "/Achievements/AM_Papers/MyListTable.do");
		service.put("tableColumn", tableColumn);
		
		// 数据
		Map<String, Object> tableData = new HashMap<String, Object>();
		tableData.put("uri", projectname + "/Achievements/AM_Papers/GetList.do");
		tableData.put("method", "POST");
		Map<String, Object> tableDataProps = new HashMap<String, Object>();
		tableDataProps.put("DefaultLoad", "true");
		tableDataProps.put("listview", "AM_Papers_MyList");
		tableData.put("props", tableDataProps);
		service.put("tableData", tableData);
		
		return R.ok().put("data", service);
	}
	@ApiOperation(value = "接口说明：论文录入页面接口"
			, notes = "接口说明：论文录入页面接口。<br>"
			+ "使用位置：成果管理-论文-录入<br>"
			+ "逻辑说明：获取论文录入页面操作按钮和列表表头和列表数据等页面元素") 
	@GetMapping("/MyListTable")
	@ResponseBody
	public Map<String, Object> MyListTable(HttpServletRequest request) throws Exception {
		SysUser sysuser = DataUtils.getSysUser(request);
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("columnSchema", service.getListJson("AM_Papers_MyList"));
		
		obj.put("keyname", "id");
		// 样式
		HashMap<String, Object> uiSchema = new HashMap<String, Object>();
		uiSchema.put("sortProp", "id");
		uiSchema.put("sortOrder", "descending");
		uiSchema.put("stripe", true);
		uiSchema.put("isPage", true);
		uiSchema.put("isChoice", true);
		uiSchema.put("isIndex", true);
		uiSchema.put("size", "small");
		uiSchema.put("handleFixed", "right");
		obj.put("uiSchema", uiSchema);
		// 按钮
		List<HashMap<String, Object>> listController = new ArrayList<HashMap<String,Object>>();		
		if (sysuser.getFunctionPermissions("AM_Papers_Insert") || sysuser.getFunctionPermissions("AM_Papers_Manage")) {
			HashMap<String, Object> controller = new HashMap<String, Object>();
			controller.put("mode", "add");
			controller.put("type", "info");
			controller.put("icon", "el-icon-eleme");
			controller.put("useRow", false);
			controller.put("useAll", true);
			controller.put("label", "增加");
			HashMap<String, Object> attrs = new HashMap<String, Object>();
			HashMap<String, Object> service = new HashMap<String, Object>();
			service.put("uri", projectname + "/Achievements/AM_Papers/Add");
			attrs.put("service", service);
			controller.put("attrs", attrs);
			listController.add(controller);
		}

		if (sysuser.getFunctionPermissions("AM_Papers_Update")) {
			HashMap<String, Object> controller = new HashMap<String, Object>();
			controller.put("mode", "edit");
			controller.put("type", "info");
			controller.put("icon", "el-icon-eleme");
			controller.put("useRow", true);
			controller.put("useAll", false);
			controller.put("label", "修改");
			HashMap<String, Object> attrs = new HashMap<String, Object>();
			HashMap<String, Object> service = new HashMap<String, Object>();
			service.put("uri", projectname + "/Achievements/AM_Papers/Edit");
			attrs.put("service", service);
			controller.put("attrs", attrs);
			listController.add(controller);
		}

		if (sysuser.getFunctionPermissions("AM_Papers_Delete")) {
			HashMap<String, Object> controller = new HashMap<String, Object>();
			controller.put("mode", "delete");
			controller.put("type", "danger");
			controller.put("icon", "el-icon-eleme");
			controller.put("useRow", true);
			controller.put("useAll", false);
			controller.put("label", "删除");
			HashMap<String, Object> attrs = new HashMap<String, Object>();
			HashMap<String, Object> service = new HashMap<String, Object>();
			service.put("uri", projectname + "/Achievements/AM_Papers/Delete");
			attrs.put("service", service);
			controller.put("attrs", attrs);
			listController.add(controller);
		}

		HashMap<String, Object> controller = new HashMap<String, Object>();
		controller.put("mode", "excel");
		controller.put("type", "primary");
		controller.put("icon", "el-icon-eleme");
		controller.put("useRow", false);
		controller.put("useAll", true);
		controller.put("label", "导出");
		HashMap<String, Object> attrs = new HashMap<String, Object>();
		HashMap<String, Object> service = new HashMap<String, Object>();
		service.put("uri", projectname + "/Achievements/AM_Papers/ExcelExport");
		attrs.put("service", service);
		controller.put("attrs", attrs);
		listController.add(controller);
		
		obj.put("controller", listController);
		
		//总体增删改权限		
		/*List<String> powerList=new ArrayList<String>();
		if(sysuser.getFunctionPermissions("AM_Papers_Insert")) {
			powerList.add("add");
		}
		if(sysuser.getFunctionPermissions("AM_Papers_Update")) {
			powerList.add("edit");
		}
		if(sysuser.getFunctionPermissions("AM_Papers_Delete")) {
			powerList.add("delete");
		}
		if(sysuser.getFunctionPermissions("AM_Papers_Import")) {
			powerList.add("import");
		}
		obj.put("powerall", powerList);
		Map<String,Object> listparam=new HashMap<String, Object>();
		listparam.put("listview", "AM_Papers_MyList");
		obj.put("listparam", listparam);*/
		return R.ok().put("data", obj);
	}
	
	@ApiOperation(value = "接口说明：论文查看页面接口"
			, notes = "接口说明：论文查看页面接口。<br>"
			+ "使用位置：成果管理-论文-查看<br>"
			+ "逻辑说明：获取论文查看页面搜索条件和列表信息等页面元素") 
	@GetMapping("/ViewList")
	@ResponseBody
	public Map<String, Object> ViewList(HttpServletRequest request) throws Exception {
		SysUser sysuser = DataUtils.getSysUser(request);
		Map<String, Object> service = new HashMap<String, Object>();
		// 搜索
		Map<String, Object> search = new HashMap<String, Object>();
		search.put("uri", projectname + "/Common/search.do");
		Map<String, Object> searchprops = new HashMap<String, Object>();
		searchprops.put("searchname", "AM_Papers_ViewList");
		search.put("props", searchprops);
		service.put("search", search);
		
		// 按钮和列表
		Map<String, Object> tableColumn = new HashMap<String, Object>();
		tableColumn.put("uri", projectname + "/Achievements/AM_Papers/ViewListTable.do");
		service.put("tableColumn", tableColumn);
		
		// 数据
		Map<String, Object> tableData = new HashMap<String, Object>();
		tableData.put("uri", projectname + "/Achievements/AM_Papers/GetList.do");
		tableData.put("method", "POST");
		Map<String, Object> tableDataProps = new HashMap<String, Object>();
		tableDataProps.put("DefaultLoad", "true");
		tableDataProps.put("listview", "AM_Papers_ViewList");
		tableDataProps.put("checkresult", "2150");
		tableData.put("props", tableDataProps);
		service.put("tableData", tableData);
		
		return R.ok().put("data", service);
	}
	
	@ApiOperation(value = "接口说明：论文录入页面接口"
			, notes = "接口说明：论文录入页面接口。<br>"
			+ "使用位置：成果管理-论文-录入<br>"
			+ "逻辑说明：获取论文录入页面操作按钮和列表表头和列表数据等页面元素") 
	@GetMapping("/ViewListTable")
	@ResponseBody
	public Map<String, Object> ViewListTable(HttpServletRequest request) throws Exception {
		SysUser sysuser = DataUtils.getSysUser(request);
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("columnSchema", service.getListJson("AM_Papers_ViewList"));
		
		obj.put("keyname", "id");
		// 样式
		HashMap<String, Object> uiSchema = new HashMap<String, Object>();
		uiSchema.put("sortProp", "id");
		uiSchema.put("sortOrder", "descending");
		uiSchema.put("stripe", true);
		uiSchema.put("isPage", true);
		uiSchema.put("isChoice", true);
		uiSchema.put("isIndex", true);
		uiSchema.put("size", "small");
		uiSchema.put("handleFixed", "right");
		obj.put("uiSchema", uiSchema);
		// 按钮
		List<HashMap<String, Object>> listController = new ArrayList<HashMap<String,Object>>();
		HashMap<String, Object> controller = new HashMap<String, Object>();
		controller.put("mode", "excel");
		controller.put("type", "primary");
		controller.put("icon", "el-icon-eleme");
		controller.put("useRow", false);
		controller.put("useAll", true);
		controller.put("label", "导出");
		HashMap<String, Object> attrs = new HashMap<String, Object>();
		HashMap<String, Object> service = new HashMap<String, Object>();
		service.put("uri", projectname + "/Achievements/AM_Papers/ExcelExport");
		attrs.put("service", service);
		controller.put("attrs", attrs);
		listController.add(controller);
		obj.put("controller", listController);

		return R.ok().put("data", obj);
	}

	@ApiOperation(value = "接口说明：论文新增页面接口"
			, notes = "接口说明：论文新增页面接口。<br>"
			+ "使用位置：成果管理-论文-增加<br>"
			+ "逻辑说明：点击增加按钮新增成果时，调用该接口获取一下初始化数据") 
	@GetMapping("/Add")
	@ResponseBody
	public Map<String, Object> Add(HttpServletRequest request) throws Exception {
		SysUser sysuser = DataUtils.getSysUser(request);
		String tablename = "AM_Papers";
		String viewname = "add";
		HashMap<String, Object> obj = new HashMap<String, Object>();
		obj.put("checkresult", "2110");
		obj.put("submitman", sysuser.getUserId());
		obj.put("submittime", DataUtils.getDate("yyyy-MM-dd"));
		List<HashMap<String, Object>> listTable = service.getAddJson(tablename, viewname, request, obj, sysuser);
		
		HashMap<String, Object> result = new HashMap<String, Object>();
		result.put("cellSchema", listTable);
		
		List<HashMap<String, Object>> listController = new ArrayList<HashMap<String,Object>>();
		// 保存
		HashMap<String, Object> controller = new HashMap<String, Object>();
		controller.put("mode", "save");
		controller.put("type", "info");
		controller.put("icon", "el-icon-eleme");
		controller.put("label", "保存");
		HashMap<String, Object> attrs = new HashMap<String, Object>();
		HashMap<String, Object> service = new HashMap<String, Object>();
		service.put("uri", projectname + "/Achievements/AM_Papers/Save.do");
		service.put("method", "POST");
		Map<String, Object> props = new HashMap<String, Object>();
		props.put("checkresult", "2110");
		service.put("props", props);
		attrs.put("service", service);
		controller.put("attrs", attrs);
		listController.add(controller);
		
		// 送审
		controller = new HashMap<String, Object>();
		controller.put("mode", "submit");
		controller.put("type", "success");
		controller.put("icon", "el-icon-eleme");
		controller.put("label", "送审");
		attrs = new HashMap<String, Object>();
		service = new HashMap<String, Object>();
		service.put("uri", projectname + "/Achievements/AM_Papers/Save.do");
		service.put("method", "POST");
		props = new HashMap<String, Object>();
		props.put("checkresult", "2120");
		service.put("props", props);
		attrs.put("service", service);
		controller.put("attrs", attrs);
		listController.add(controller);
		
		/*// 关闭
		controller = new HashMap<String, Object>();
		controller.put("mode", "close");
		controller.put("type", "danger");
		controller.put("icon", "el-icon-eleme");
		controller.put("label", "关闭");
		listController.add(controller);*/
		
		result.put("controller", listController);
		
		return R.ok().put("data", result);
	}

	@ApiOperation(value = "接口说明：论文修改页面接口"
			, notes = "接口说明：论文修改页面接口。<br>"
			+ "使用位置：成果管理-论文-修改<br>"
			+ "逻辑说明：点击修改按钮修改成果时，调用该接口获取该成果的数据") 
	@ApiImplicitParams({
        @ApiImplicitParam(name = "id", value = "成果唯一标识,必填", required = true, dataType = "String",paramType="query"),
	})
	@GetMapping("/Edit")
	@ResponseBody
	public Map<String, Object> Edit(HttpServletRequest request) throws Exception {
		String id = request.getParameter("id");
		if(StringUtils.isBlank(id)) {
			throw new RRException("主键值不能为空，请修改。");
		}
		SysUser sysuser = DataUtils.getSysUser(request);
		String tablename = "AM_Papers";
		String viewname = "add";
		if(StringUtils.isNotBlank(request.getParameter("viewname"))) {
			viewname = request.getParameter("viewname");
		}
		HashMap<String, Object> obj = service.getMapByKey(tablename, id);
		List<HashMap<String, Object>> listTable = service.getEditJson(tablename, viewname, request, obj);
		
		HashMap<String, Object> result = new HashMap<String, Object>();
		result.put("cellSchema", listTable);
		
		List<HashMap<String, Object>> listController = new ArrayList<HashMap<String,Object>>();
		// 保存
		HashMap<String, Object> controller = new HashMap<String, Object>();
		controller.put("mode", "save");
		controller.put("type", "info");
		controller.put("icon", "el-icon-eleme");
		controller.put("label", "保存");
		HashMap<String, Object> attrs = new HashMap<String, Object>();
		HashMap<String, Object> service = new HashMap<String, Object>();
		service.put("uri", projectname + "/Achievements/AM_Papers/Save.do");
		service.put("method", "POST");
		Map<String, Object> props = new HashMap<String, Object>();
		props.put("checkresult", "2110");
		props.put("id", id);
		service.put("props", props);
		attrs.put("service", service);
		controller.put("attrs", attrs);
		listController.add(controller);
		
		// 送审
		controller = new HashMap<String, Object>();
		controller.put("mode", "submit");
		controller.put("type", "success");
		controller.put("icon", "el-icon-eleme");
		controller.put("label", "送审");
		attrs = new HashMap<String, Object>();
		service = new HashMap<String, Object>();
		service.put("uri", projectname + "/Achievements/AM_Papers/Save.do");
		service.put("method", "POST");
		props = new HashMap<String, Object>();
		props.put("checkresult", "2120");
		props.put("id", id);
		service.put("props", props);
		attrs.put("service", service);
		controller.put("attrs", attrs);
		listController.add(controller);
		
		/*// 关闭
		controller = new HashMap<String, Object>();
		controller.put("mode", "close");
		controller.put("type", "danger");
		controller.put("icon", "el-icon-eleme");
		controller.put("label", "关闭");
		listController.add(controller);*/
		
		result.put("controller", listController);
		
		return R.ok().put("data", result);
	}

	@ApiOperation(value = "接口说明：论文查看页面接口"
			, notes = "接口说明：论文查看页面接口。<br>"
			+ "使用位置：成果管理-论文-查看<br>"
			+ "逻辑说明：点击成果名称查看成果基本信息，调用该接口获取该成果的数据") 
	@ApiImplicitParams({
        @ApiImplicitParam(name = "id", value = "成果唯一标识,必填", required = true, dataType = "String",paramType="query"),
	})
	@GetMapping("/View")
	@ResponseBody
	public Map<String, Object> View(HttpServletRequest request) throws Exception{
		String id = request.getParameter("id");
		DataUtils.showParams(request);
		if(StringUtils.isBlank(id)) {
			throw new RRException("主键值不能为空，请修改。");
		}
		SysUser sysuser = DataUtils.getSysUser(request);
		String tablename = "AM_Papers";
		String viewname = "add";
		if(StringUtils.isNotBlank(request.getParameter("viewname"))) {
			viewname = request.getParameter("viewname");
		}
		HashMap<String, Object> obj = service.getMapByKey(tablename, id);
		String realquerylinkurl=DataUtils.getMapKeyValue(obj, "realquerylinkurl");
		if(StringUtils.isNotBlank(realquerylinkurl)){
			if(realquerylinkurl.indexOf("http")!=-1){
				realquerylinkurl="<a href='"+realquerylinkurl+"' target='_blank'>"+realquerylinkurl+"</a>";
			}else{
				realquerylinkurl="<a href='http://"+realquerylinkurl+"' target='_blank'>"+realquerylinkurl+"</a>";
			}
			obj.put("realquerylinkurl", realquerylinkurl);
		}
		List<HashMap<String, Object>> listTable = service.getViewJson(tablename, viewname, request, obj);
		
		HashMap<String, Object> result = new HashMap<String, Object>();
		result.put("cellSchema", listTable);
		
		/*List<HashMap<String, Object>> listController = new ArrayList<HashMap<String,Object>>();
		
		// 关闭
		HashMap<String, Object> controller = new HashMap<String, Object>();
		controller.put("mode", "close");
		controller.put("type", "danger");
		controller.put("icon", "el-icon-eleme");
		controller.put("label", "关闭");
		listController.add(controller);
		
		result.put("controller", listController);*/
		
		return R.ok().put("data", result);
	}

	@ApiOperation(value = "接口说明：获取数据列表"
			, notes = "接口说明：获取数据列表。<br>"
			+ "使用位置：系统中获取该成果列表的地方<br>"
			+ "逻辑说明：通过token获取用户信息，通过列表名称、审核状态等条件权进行数据过滤<br>"
			+ "使用数据库表：AM_Papers") 
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
		String tablename = "AM_Papers";
		mySqlData.setTableName(tablename);
		//得到是哪个列表
		String listview = "AM_Papers_MyList";
		if(request.getParameter("listview") != null) {
			listview = request.getParameter("listview");
		}
		//mySqlData.setListName(listview);
		//默认过滤条件
		if("AM_Papers_MyList".equals(listview)) {//录入列表
			//判断能否进行修改删除操作
			mySqlData.setSelectField("rowpower", "case when right(checkresult, 2) not in ('10','30') then 'edit,delete'  else NULL  end rowpower");
			if(!sysuser.getFunctionPermissions("AM_Papers_MyList_All")) {
				mySqlData.setFieldOrWhere("AM_Papers.UserId", sysuser.getUserId(), "=");
				mySqlData.setFieldOrWhere("AM_Papers.submitman", sysuser.getUserId(), "=");
				if(sysuser.getFunctionPermissions("AM_Papers_MyList_Dept")) {
					mySqlData.setFieldOrWhere("AM_Papers.DepartmentId", "AM_Papers.DepartmentId IN (" + sysuser.getDepartmentId() + ")", "sql");
				}
			}
		}
		else if("AM_Papers_ViewList".equals(listview)) {//查看列表
			if(StringUtils.isNotBlank(request.getParameter("checkresult"))) {
				mySqlData.setFieldWhere("checkresult", request.getParameter("checkresult"), "in");
			} else {
				mySqlData.setFieldWhere("checkresult", "2150", "=");
			}
			if(!sysuser.getFunctionPermissions("AM_Papers_ViewList_ViewAll")) {
				mySqlData.setFieldOrWhere("AM_Papers.AllUsers", sysuser.getUserId(), "like", ",");
				mySqlData.setFieldOrWhere("AM_Papers.UserId", sysuser.getUserId(), "=");
				mySqlData.setFieldOrWhere("AM_Papers.submitman", sysuser.getUserId(), "=");
				if(sysuser.getFunctionPermissions("AM_Papers_ViewList_ViewDept")) {
					mySqlData.setFieldOrWhere("AM_Papers.DepartmentId", "AM_Papers.DepartmentId IN (" + sysuser.getDepartmentId() + ")", "sql");
				}
			}
		}
		else if("AM_Papers_CheckList".equals(listview)) {//审核列表
			if(StringUtils.isNotBlank(request.getParameter("checkresult"))) {
				mySqlData.setFieldWhere("checkresult", request.getParameter("checkresult"), "in");
			} else {
				mySqlData.setFieldWhere("checkresult", "2120", "=");
			}
		}
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

	@ApiOperation(value = "接口说明：通过id获取成果基本信息"
			, notes = "接口说明：通过id获取成果基本信息。<br>"
			+ "使用位置：系统中通过id获取对应的成果基本信息的地方<br>"
			+ "逻辑说明：通过传过来的唯一标识，到数据库查出唯一的对应记录，返回到前端"
			+ "使用数据库表：AM_Papers") 
	@ApiImplicitParams({
        @ApiImplicitParam(name = "id", value = "成果唯一标识,必填", required = true, dataType = "String",paramType="query"),
	})
	@GetMapping("/GetListById")
	@ResponseBody
	public HashMap<String, Object> GetListById(HttpServletRequest request) throws Exception {
		String id = request.getParameter("id");
		if(StringUtils.isBlank(id)) {
			return R.error("主键值不能为空，请修改。");
		}
		String tablename = "AM_Papers";
		HashMap<String, Object> obj = service.getMapByKey(tablename, id);
		return R.ok(obj);
	}
	
	@ApiOperation(value = "接口说明：用于验证是否满足送审条件接口"
			, notes = "接口说明：用于验证是否满足送审条件接口。<br>"
			+ "使用位置：送审前校验<br>"
			+ "逻辑说明：通过传过来的唯一标识，验证所有作者是否填写，以及排名和工作量是否满足要求"
			+ "使用数据库表：AM_Papers") 
	@ApiImplicitParams({
        @ApiImplicitParam(name = "id", value = "成果唯一标识,必填", required = true, dataType = "String",paramType="query"),
	})
	@PostMapping("/SubmitCheck")
	@ResponseBody
	public HashMap<String, Object> SubmitCheck(HttpServletRequest request) throws Exception {
		//得到请求参数
		String id = request.getParameter("id");
		//验证子表是否有记录
		if(serviceUser.IsExist("AM_Papers", id) == 0) {
			return R.error("作者是必填的请填写。。");
		}
		//检查作者排名
		if(!serviceUser.RankValidate("AM_Papers", id)) {
			return R.error("所有作者排名不正确，请按排名顺序录入所有作者。");
		}
		//如果所有作者有贡献度字段，验证贡献率之和是否为100%
		if(!serviceUser.WorkloadValidate("AM_Papers", id)) {
			return R.error("所有作者的贡献率之和不等于100%，请修改后再送审。");
		}
		return R.ok();
	}
	
	@ApiOperation(value = "接口说明：保存接口"
			, notes = "接口说明：保存接口。<br>"
			+ "使用位置：新增和修改后保存信息<br>"
			+ "逻辑说明：通过判断id是否为空判断是新增还是修改，将表单信息保存到数据表中"
			+ "使用数据库表：AM_Papers") 
	@PostMapping("/Save")
	@ResponseBody
	@Transactional
	public HashMap<String, Object> Save(MultipartHttpServletRequest request) throws Exception {
		SysUser sysuser = DataUtils.getSysUser(request);
		//数据实体
		MySqlData mySqlData = new MySqlData();
		//数据库表名
		mySqlData.setTableName("AM_Papers");
		//表单的视图名称默认为add，多个视图用英文对号分隔，例句：add,view,check
		String viewname = "add";
		mySqlData.setViewName(viewname);
		//保存默认值
		if(StringUtils.isNotBlank(request.getParameter("checkresult"))) {
			mySqlData.setFieldValue("checkresult", request.getParameter("checkresult"));
		}
		
		//插入和更新判断，然后执行。
		ActionResult result = new ActionResult();
		String id = request.getParameter("id");
		if(StringUtils.isBlank(id)) {
			if(!sysuser.getFunctionPermissions("AM_Papers_Insert") && !sysuser.getFunctionPermissions("AM_Papers_Manage")) {
				// 以后需要记录日志，记录操作人，操作位置，错误信息。主键值不能为空，请修改。
				return R.error("您没有权限操作所选记录。");
			}
			//执行插入操作
			result = service.insert(request, mySqlData);
			//增加保存之后的处理事件，例如 将负责人添加到作者列表
		} else {
			// 判断是否有权限修改，判断修改状态是否是草稿和退回修改状态，是否有特殊修改权限。
			// 没有编辑所有权限就需要验证状态和是否是本人可操作记录，所有权限一般设置在流程特殊管理菜单。
			if (!sysuser.getFunctionPermissions("AM_Papers_EditAll")) {
				// 以后需要记录日志，记录操作人，操作位置，错误信息。主键值不能为空，请修改。
				if(!sysuser.getFunctionPermissions("AM_Papers_Update")) {
					// 以后需要记录日志，记录操作人，操作位置，错误信息。主键值不能为空，请修改。
					return R.error("您没有权限操作所选记录。");
				}
				MySqlData mySqlCheck = new MySqlData();
				mySqlCheck.setSelectField("id", "id");
				mySqlCheck.setTableName("AM_Papers");
				mySqlCheck.setFieldWhere("id", id, "=");
				mySqlCheck.setFieldWhere("checkresult", "2110,2130", "in");
				if (!sysuser.getFunctionPermissions("AM_Papers_MyList_All")) {
					mySqlCheck.setFieldOrWhere("AM_Papers.UserId", sysuser.getUserId(), "=");
					mySqlCheck.setFieldOrWhere("AM_Papers.submitman", sysuser.getUserId(), "=");
					if (sysuser.getFunctionPermissions("AM_Papers_MyList_Dept")) {
						mySqlCheck.setFieldOrWhere("AM_Papers.DepartmentId", "AM_Papers.DepartmentId IN (" + sysuser.getDepartmentId() + ")", "sql");
					}
				}
				HashMap<String, Object> obj = service.getMap(mySqlCheck);
				if (obj == null) {
					// 以后需要记录日志，记录操作人，操作位置，错误信息。主键值不能为空，请修改。
					return R.error("您没有权限操作所选记录。");
				}
			}
			
			//执行更新操作
			mySqlData.setFieldWhere("id", id, "=");
			result = service.update(request, mySqlData);
			//修改保存之后的处理事件，例如 将负责人添加到作者列表
		}
		if(result.getSuccess()) {
			return R.ok().put("data", result.getData()).put("filehtml", result.getFileHtml());
		} else {
			return R.error(result.getMsg());
		}
	}

	@ApiOperation(value = "接口说明：删除数据接口"
			, notes = "接口说明：删除数据接口。<br>"
			+ "使用位置：系统中删除记录的地方<br>"
			+ "逻辑说明：将指定id的记录删除"
			+ "使用数据库表：AM_Papers") 
	@ApiImplicitParams({
        @ApiImplicitParam(name = "id", value = "成果唯一标识,必填", required = true, dataType = "String",paramType="query"),
	})
	@PostMapping("/Delete")
	@ResponseBody
	@Transactional
	public HashMap<String, Object> Delete(HttpServletRequest request) throws Exception {
		// 操作权限，可以通过spring security或者shiro控制，登录时得到所有操作权限。
		//数据有效性校验
		String id = request.getParameter("id");
		if(StringUtils.isBlank(id)) {
			return R.error("主键值不能为空，请修改。");
		}
		SysUser sysuser = DataUtils.getSysUser(request);
		if (!sysuser.getFunctionPermissions("AM_Papers_Delete")) {
			// 以后需要记录日志，记录操作人，操作位置，错误信息。主键值不能为空，请修改。
			return R.error("您没有权限操作所选记录。");
		}
		//权限校验
		// 判断是否有权限删除，判断修改状态是否是草稿和退回修改状态，是否有特殊修改权限。
		// 没有编辑所有权限就需要验证状态和是否是本人可操作记录，所有权限一般设置在流程特殊管理菜单。
		if (!sysuser.getFunctionPermissions("AM_Papers_EditAll")) {
			// 以后需要记录日志，记录操作人，操作位置，错误信息。主键值不能为空，请修改。
			MySqlData mySqlData = new MySqlData();
			mySqlData.setTableName("AM_Papers");
			mySqlData.setSelectField("id", "id");
			mySqlData.setFieldWhere("id", id, "in");
			mySqlData.setFieldWhere("checkresult", "2110,2130", "in");
			// 如果修改成uuid为主键，下面这部分过滤条件其实也可以不要，因为不是自己可以查看到的记录是不知道id值的。
			// 一般只有录入列表才有修改功能，所有下面把录入列表的过滤条件复制过来，如果还有其他地方修改需要根据情况修改。
			if (!sysuser.getFunctionPermissions("AM_Papers_MyList_All")) {
				mySqlData.setFieldOrWhere("AM_Papers.UserId", sysuser.getUserId(), "=");
				mySqlData.setFieldOrWhere("AM_Papers.submitman", sysuser.getUserId(), "=");
				if (sysuser.getFunctionPermissions("AM_Papers_MyList_Dept")) {
					mySqlData.setFieldOrWhere("AM_Papers.DepartmentId", "AM_Papers.DepartmentId IN (" + sysuser.getDepartmentId() + ")", "sql");
				}
			}
			List<HashMap<String, Object>> list = service.getDataList(mySqlData);
			if (list == null || list.size() != id.split(",").length) {
				// 以后需要记录日志，记录操作人，操作位置，错误信息。主键值不能为空，请修改。
				return R.error("您没有权限操作所选记录。");
			}
		}
		
		//执行删除操作
		Integer iResult = service.delete("AM_Papers", id);
		//删除成功后可以删除关联子表数据
		if(iResult > 0) {
			service.delete("am_achievement_project", "AM_Papers", "AchievementType", id, "AchievementId");
			service.delete("am_achievement_user", "AM_Papers", "AchievementType", id, "AchievementId");
		}
		if(iResult > 0) {
			return R.ok();
		} else {
			return R.error("删除0条数据。");
		}
	}
	
	@ApiOperation(value = "接口说明：Excel导出接口"
			, notes = "接口说明：Excel导出接口。<br>"
			+ "使用位置：各列表中导出Excel按钮<br>"
			+ "逻辑说明：通过exportName和其他过滤条件，导出相应的Excel"
			+ "使用数据库表：AM_Papers") 
	@ApiImplicitParams({
        @ApiImplicitParam(name = "exportName", value = "导出配置名，必填", required = true, dataType = "String",paramType="query"),
	})
	@SuppressWarnings("unchecked")
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
		List<HashMap<String, Object>> listData=new ArrayList<HashMap<String,Object>>();
		//得到要导出的数据
		if(exportName.equals("Papers_Net_List")){
			listData = (List<HashMap<String, Object>>)((ActionResult)GetList(request).get("data")).getData();
		}
		export.setSource(listData);
		export.setProjectPath(request.getSession().getServletContext().getRealPath(""));
		strError = export.Export();
		if(StringUtils.isNotBlank(strError)) {
			return R.error(strError);
		} else {
			return R.ok().put("PATH", export.GetDownFilePath());
		}
	}
	
	@ApiOperation(value = "接口说明：Excel导入接口"
			, notes = "接口说明：Excel导入接口。<br>"
			+ "使用位置：录入列表导入Excel<br>"
			+ "逻辑说明：将Excel文件中的数据保存到数据表"
			+ "使用数据库表：AM_Papers") 
	@SuppressWarnings("null")
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
		String importname = "AM_Papers_Import";
		StringBuilder strAddField = new StringBuilder();
		StringBuilder strAddValue = new StringBuilder();
		strAddField.append("submitman,");
		strAddValue.append("'" + sysuser.getUserId() + "',");
		strAddField.append("submittime,");
		strAddValue.append("NOW(),");
		String checkresult = "2110";
		if(StringUtils.isNotBlank(request.getParameter("importcheckresult"))){
			checkresult = request.getParameter("importcheckresult");
		}
		strAddField.append("checkresult,");
		strAddValue.append("'" + checkresult + "',");
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
}
