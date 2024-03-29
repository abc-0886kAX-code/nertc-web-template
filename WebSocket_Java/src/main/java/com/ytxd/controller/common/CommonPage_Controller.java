package com.ytxd.controller.common;

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
import com.ytxd.service.common.AM_Achievement_User_Service;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.*;

/**
 *  通用
 */
@RestController("CommonPage_Controller")
@RequestMapping(value = "/common/CommonPage")
@Api(value = "通用接口", tags = "通用接口")
public class CommonPage_Controller extends BaseController {
	@Resource
	private CommonService service;
	@Resource
	private AM_Achievement_User_Service serviceUser;

	@ApiOperation(value = "接口说明：MyList_PC通用录入列表接口", notes = "接口说明：MyList_PC通用录入列表接口")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "pageid", value = "通过配置唯一标识", required = true, dataType = "String", paramType = "query") })
	@GetMapping("/MyList_PC")
	public HashMap<String, Object> MyList_PC(HttpServletRequest request) throws Exception {
		// 参数判断
		String pageid = request.getParameter("pageid");
		if (StringUtils.isBlank(pageid)) {
			return R.error("pageid参数不能为空，请修改。");
		}
		// 得到通用配置数据
		HashMap<String, Object> mapConfig = service.getMap("sm_commonpage", pageid, "pageid");
		String tablename = DataUtils.getMapKeyValue(mapConfig, "tablename");
		String searchname = DataUtils.getMapKeyValue(mapConfig, "searchname");
		String listname = DataUtils.getMapKeyValue(mapConfig, "listname");
		String checkfieldname = DataUtils.getMapKeyValue(mapConfig, "checkfieldname");
		String checkcode = DataUtils.getMapKeyValue(mapConfig, "checkcode");
		String exportname = DataUtils.getMapKeyValue(mapConfig, "exportname");
		String importname = DataUtils.getMapKeyValue(mapConfig, "importname");
		// 管理权限控制
		String powermanage = DataUtils.getMapKeyValue(mapConfig, "powermanage");
		// 填写权限控制
		String powersubmit = DataUtils.getMapKeyValue(mapConfig, "powersubmit");
		String pageshow = DataUtils.getMapKeyValue(mapConfig, "pageshow");
		Map<String, Object> obj = new HashMap<String, Object>();
		// 搜索和列表配置
		obj.put("searchSchema", service.getSearchJson(searchname));
		obj.put("columnSchema", service.getListJson(listname));
		obj.put("pageshow", pageshow);
		// 得到功能配置数据
		MySqlData mySqlData = new MySqlData();
		mySqlData.setTableName("sm_commonpage_handler");
		mySqlData.setFieldWhere("pageid", pageid, "=");
		mySqlData.setFieldWhere("Visible", "01", "=");
		mySqlData.setSort("OrderId");
		List<HashMap<String, Object>> listHandler = service.getDataList(mySqlData);
		HashMap<String, Object> powerallParams = new HashMap<String, Object>();
		if (listHandler.size() > 0) {
			List<HashMap<String, Object>> rowHandleobj = new ArrayList<HashMap<String, Object>>();
			List<HashMap<String, Object>> allHandleobj = new ArrayList<HashMap<String, Object>>();
			for (HashMap<String, Object> mapHandler : listHandler) {
				String mode = DataUtils.getMapKeyValue(mapHandler, "mode");
				String position = DataUtils.getMapKeyValue(mapHandler, "position");
				powerallParams.put(mode, mapHandler);
				if(position.indexOf("row") != -1) {
					HashMap<String, Object> mapRow = new HashMap<String, Object>();
					mapRow.put("mode", DataUtils.getMapKeyValue(mapHandler, "mode"));
					mapRow.put("type", DataUtils.getMapKeyValue(mapHandler, "type"));
					mapRow.put("icon", DataUtils.getMapKeyValue(mapHandler, "icon"));
					mapRow.put("label", DataUtils.getMapKeyValue(mapHandler, "label"));
					rowHandleobj.add(mapRow);
				}
				if(position.indexOf("all") != -1) {
					HashMap<String, Object> mapRow = new HashMap<String, Object>();
					mapRow.put("mode", DataUtils.getMapKeyValue(mapHandler, "mode"));
					mapRow.put("type", DataUtils.getMapKeyValue(mapHandler, "type"));
					mapRow.put("icon", DataUtils.getMapKeyValue(mapHandler, "icon"));
					mapRow.put("label", DataUtils.getMapKeyValue(mapHandler, "label"));
					allHandleobj.add(mapRow);
				}
			}
			obj.put("rowHandleobj", rowHandleobj);
			obj.put("allHandleobj", allHandleobj);
			obj.put("powerallParams", powerallParams);
		}		
		// 操作权限
		SysUser sysuser = DataUtils.getSysUser(request);
		List<String> powerList = new ArrayList<String>();
		if (!powerallParams.isEmpty()) {
			for (Map.Entry<String, Object> entry : powerallParams.entrySet()) {
				String key = entry.getKey();
				HashMap<String, Object> mapValue = (HashMap<String, Object>) entry.getValue();
				String powerLogo = DataUtils.getMapKeyValue(mapValue, "powerlogo");
				if (StringUtils.isNotBlank(powerLogo)) {
					if (sysuser.getFunctionPermissions(powerLogo)) {
						powerList.add(key);
					}
				} else {
					powerList.add(key);
				}
			}
		} else {
			if ("01".equals(powermanage)) {
				if (sysuser.getFunctionPermissions(tablename + "_Insert")) {
					powerList.add("add");
				}
				if (sysuser.getFunctionPermissions(tablename + "_Update")) {
					powerList.add("edit");
				}
				if (sysuser.getFunctionPermissions(tablename + "_Delete")) {
					powerList.add("delete");
				}
				if(StringUtils.isNotBlank(checkfieldname) && StringUtils.isNotBlank(checkcode)) {
					if (sysuser.getFunctionPermissions(tablename + "_Update")) {
						powerList.add("withdraw");
					}
				}			
				if (StringUtils.isNotBlank(importname) && sysuser.getFunctionPermissions(tablename + "_Import")) {
					powerList.add("import");
				}
			} else {
				powerList.add("add");
				powerList.add("edit");
				powerList.add("delete");
				if (StringUtils.isNotBlank(checkfieldname) && StringUtils.isNotBlank(checkcode)) {
					powerList.add("withdraw");
				}
				if (StringUtils.isNotBlank(importname)) {
					powerList.add("import");
				}				
			}
		}		
		if ("01".equals(powersubmit) && !"1".equals(sysuser.getTimeSet())) {
			// 需要录入权限控制，并且不在录入期内，但去掉录入功能。
			powerList = new ArrayList<String>();
		}
		if(StringUtils.isNotBlank(exportname)) {
			powerList.add("export");
		}		
		obj.put("powerall", powerList);
		
		// listparam为给获取数据列表接口GetList传参
		Map<String, Object> listparam = DataUtils.getRequestMap(request);
		listparam.put("listview", listname);
		obj.put("listparam", listparam);
		// 列表接口参数
		obj.put("exportname", exportname);
		obj.put("importname", importname);
		obj.put("idfieldname", DataUtils.getMapKeyValue(mapConfig, "tableidname"));
		obj.put("sortname", DataUtils.getMapKeyValue(mapConfig, "orderfield"));
		obj.put("sortorder", DataUtils.getMapKeyValue(mapConfig, "orderway"));
		obj.put("checktablename", tablename);
		obj.put("checktableidname", DataUtils.getMapKeyValue(mapConfig, "tableidname"));
		// 标题表单是否需要审核
		if(StringUtils.isNotBlank(checkfieldname) && StringUtils.isNotBlank(checkcode)) {
			obj.put("check", true);
		} else {
			obj.put("check", false);
		}
		obj.put("pagename", DataUtils.getMapKeyValue(mapConfig, "pagename"));
		if(StringUtils.isNotBlank(DataUtils.getMapKeyValue(mapConfig, "dataurl"))) {
			obj.put("getListUrl", DataUtils.getMapKeyValue(mapConfig, "dataurl"));
		}
		if(StringUtils.isNotBlank(DataUtils.getMapKeyValue(mapConfig, "viewiniturl"))) {
			obj.put("viewiniturl", DataUtils.getMapKeyValue(mapConfig, "viewiniturl"));
		}
		// listformparam为给添加页面接口Add_PC传参
		Map<String, Object> listformparam = DataUtils.getRequestMap(request);
		obj.put("listformparam", listformparam);
		return R.ok().put("data", obj);
	}

	@ApiOperation(value = "接口说明：ViewList_PC通用查看列表接口", notes = "接口说明：ViewList_PC通用查看列表接口")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "pageid", value = "通过配置唯一标识", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "checkresult", value = "审核状态，选填。", required = false, dataType = "String", paramType = "query") })
	@GetMapping("/ViewList_PC")
	public HashMap<String, Object> ViewList_PC(HttpServletRequest request) throws Exception {
		// 参数判断
		String pageid = request.getParameter("pageid");
		if (StringUtils.isBlank(pageid)) {
			return R.error("pageid参数不能为空，请修改。");
		}
		// 得到通用配置数据
		HashMap<String, Object> mapConfig = service.getMap("sm_commonpage", pageid, "pageid");
		String tablename = DataUtils.getMapKeyValue(mapConfig, "tablename");
		String searchname = DataUtils.getMapKeyValue(mapConfig, "searchname");
		String listname = DataUtils.getMapKeyValue(mapConfig, "listname");
		Map<String, Object> obj = new HashMap<String, Object>();
		// 搜索和列表配置
		obj.put("searchSchema", service.getSearchJson(searchname));
		obj.put("columnSchema", service.getListJson(listname));
		// 操作权限
		List<String> powerList = new ArrayList<String>();
		powerList.add("export");
		obj.put("powerall", powerList);
		// listparam为给获取数据列表接口GetList传参
		Map<String, Object> listparam = DataUtils.getRequestMap(request);
		listparam.put("listview", listname);
		obj.put("listparam", listparam);
		// 列表接口参数
		obj.put("exportname", DataUtils.getMapKeyValue(mapConfig, "exportname"));
		obj.put("idfieldname", DataUtils.getMapKeyValue(mapConfig, "tableidname"));
		obj.put("sortname", DataUtils.getMapKeyValue(mapConfig, "orderfield"));
		obj.put("sortorder", DataUtils.getMapKeyValue(mapConfig, "orderway"));
		obj.put("checktablename", tablename);
		obj.put("checktableidname", DataUtils.getMapKeyValue(mapConfig, "tableidname"));
		return R.ok().put("data", obj);
	}

	@ApiOperation(value = "接口说明：Add_PC通用添加页面接口", notes = "接口说明：Add_PC通用添加页面接口")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "pageid", value = "通过配置唯一标识", required = true, dataType = "String", paramType = "query") })
	@GetMapping("/Add_PC")
	public HashMap<String, Object> Add_PC(HttpServletRequest request) throws Exception {
		SysUser sysuser = DataUtils.getSysUser(request);
		// 参数判断
		String pageid = request.getParameter("pageid");
		if (StringUtils.isBlank(pageid)) {
			return R.error("pageid参数不能为空，请修改。");
		}
		// 得到通用配置数据
		HashMap<String, Object> mapConfig = service.getMap("sm_commonpage", pageid, "pageid");
		String tablename = DataUtils.getMapKeyValue(mapConfig, "tablename");
		// 管理权限控制
		String powermanage = DataUtils.getMapKeyValue(mapConfig, "powermanage");
		// 填写权限控制
		String powersubmit = DataUtils.getMapKeyValue(mapConfig, "powersubmit");
		// 权限判断
		if ("01".equals(powermanage) && !sysuser.getFunctionPermissions(tablename + "_Insert")) {
			return R.error("您没有增加权限。");
		}
		// 填报期权限判断
		if("01".equals(powersubmit) && !"1".equals(sysuser.getTimeSet())) {
			return R.error("您没有增加权限。");
		}
		// 参数整理
		String viewname = DataUtils.getMapKeyValue(mapConfig, "viewname");
		if (StringUtils.isNotBlank(request.getParameter("viewname"))) {
			viewname = request.getParameter("viewname");
		}
		// 默认值
		HashMap<String, Object> obj = DataUtils.getRequestMap(request);
		obj.put("submitman", sysuser.getUserId());
		obj.put("submitmanname", sysuser.getTrueName());
		obj.put("submittime", DataUtils.getDate("yyyy-MM-dd"));
		obj.put("userid", sysuser.getUserId());
		obj.put("departmentid", sysuser.getDeptId());
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

	@ApiOperation(value = "接口说明：Edit_PC通用修改页面接口", notes = "接口说明：Edit_PC通用修改页面接口")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "pageid", value = "通过配置唯一标识", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "id", value = "主键，必填。", required = true, dataType = "String", paramType = "query") })
	@GetMapping("/Edit_PC")
	public HashMap<String, Object> Edit_PC(HttpServletRequest request) throws Exception {
		// 参数判断
		String pageid = request.getParameter("pageid");
		if (StringUtils.isBlank(pageid)) {
			return R.error("pageid参数不能为空，请修改。");
		}
		// 得到通用配置数据
		HashMap<String, Object> mapConfig = service.getMap("sm_commonpage", pageid, "pageid");
		String tablename = DataUtils.getMapKeyValue(mapConfig, "tablename");
		String tableidname = DataUtils.getMapKeyValue(mapConfig, "tableidname");
		String checkfieldname = DataUtils.getMapKeyValue(mapConfig, "checkfieldname");
		String checkcode = DataUtils.getMapKeyValue(mapConfig, "checkcode");
		// 管理权限控制
		String powermanage = DataUtils.getMapKeyValue(mapConfig, "powermanage");
		// 填写权限控制
		String powersubmit = DataUtils.getMapKeyValue(mapConfig, "powersubmit");
		// 数据权限控制
		String powerdata = DataUtils.getMapKeyValue(mapConfig, "powerdata");
		// 主键参数判断
		String id = request.getParameter(tableidname);
		if (StringUtils.isBlank(id)) {
			return R.error("您要修改的记录不存在，请修改。");
		}
		// 权限判断
		SysUser sysuser = DataUtils.getSysUser(request);
		Boolean CommonPage_Update = sysuser.getFunctionPermissions(tablename + "_Update");
		Boolean CommonPage_EditAll = sysuser.getFunctionPermissions(tablename + "_EditAll");
		Boolean CommonPage_EditDept = sysuser.getFunctionPermissions(tablename + "_EditDept");
		if ("01".equals(powermanage) && !CommonPage_Update && !CommonPage_EditAll && !CommonPage_EditDept) {
			return R.error("您没有修改权限。");
		}
		// 填报期权限判断
		if("01".equals(powersubmit) && !"1".equals(sysuser.getTimeSet()) && !CommonPage_EditAll && !CommonPage_EditDept) {
			return R.error("您没有修改权限。");
		}
		// 参数整理
		String viewname = DataUtils.getMapKeyValue(mapConfig, "viewname");
		if (StringUtils.isNotBlank(request.getParameter("viewname"))) {
			viewname = request.getParameter("viewname");
		}
		HashMap<String, Object> obj = service.getMap(tablename, id, tableidname);
		// 主键参数是否正确的判断
		if (obj == null) {
			return R.error("您要修改的记录不存在，请修改。");
		}
		// 判断是否有权限修改，判断修改状态是否是草稿和退回修改状态，是否有特殊修改权限。
		// 没有编辑所有权限就需要验证状态和是否是本人可操作记录，所有权限一般设置在流程特殊管理菜单。
		String checkresult = DataUtils.getMapKeyValue(obj, checkfieldname);
		if ("01".equals(powerdata) && !CommonPage_EditAll) {
			if (StringUtils.isNotBlank(checkfieldname) && StringUtils.isNotBlank(checkcode) && !(checkcode + "10").equals(checkresult) && !(checkcode + "30").equals(checkresult)) {
				// 判断修改状态是否是草稿和退回修改状态
				if (!CommonPage_EditDept) {
					return R.error("您没有修改权限。");
				}
			} 
			if (!sysuser.getFunctionPermissions(tablename + "_MyList_All")) {
				// 如果修改成uuid为主键，下面这部分判断其实也可以不要，因为不是自己可以查看到的记录是不知道id值的。
				// 一般只有录入列表才有修改功能，所以下面把录入列表的过滤条件复制过来，如果还有其他地方修改需要根据情况修改。
				MySqlData mySqlData = new MySqlData();
				mySqlData.setTableName(tablename);
				mySqlData.setFieldWhere(tableidname, id, "=");
				mySqlData.setFieldOrWhere(tableidname + ".UserId", sysuser.getUserId(), "=");
				mySqlData.setFieldOrWhere(tableidname + ".submitman", sysuser.getUserId(), "=");
				if (sysuser.getFunctionPermissions(tablename + "_MyList_Dept")) {
					String deptfieldname = DataUtils.getMapKeyValue(mapConfig, "deptfieldname");
					mySqlData.setFieldOrWhere(tableidname + "." + deptfieldname, tableidname + "." + deptfieldname + " IN (" + sysuser.getDepartmentId() + ")", "sql");
				}
				obj = service.getMap(mySqlData);
				if (obj == null) {
					return R.error("您没有修改权限。");
				}
			}
		}
		// formparam为给保存数据接口Save传参
		Map<String, Object> formparam = DataUtils.getRequestMap(request);
		formparam.put("viewname", viewname);
		formparam.put(checkfieldname, checkresult);
		// 接口返回数据
		List<HashMap<String, Object>> listTable = service.getEditJson(tablename, viewname, request, obj);
		obj = new HashMap<String, Object>();
		obj.put("tableJson", listTable);
		obj.put("formparam", formparam);
		return R.ok().put("data", obj);
	}
	
	@ApiOperation(value = "接口说明：View_PC通用查看页面接口", notes = "接口说明：View_PC通用查看页面接口")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "pageid", value = "通过配置唯一标识", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "id", value = "主键，必填。", required = true, dataType = "String", paramType = "query") })
	@GetMapping("/View_PC")
	public HashMap<String, Object> View_PC(HttpServletRequest request) throws Exception {
		// 参数判断
		String pageid = request.getParameter("pageid");
		if (StringUtils.isBlank(pageid)) {
			return R.error("pageid参数不能为空，请修改。");
		}
		// 得到通用配置数据
		HashMap<String, Object> mapConfig = service.getMap("sm_commonpage", pageid, "pageid");
		String tablename = DataUtils.getMapKeyValue(mapConfig, "tablename");
		String tableidname = DataUtils.getMapKeyValue(mapConfig, "tableidname");
		// 主键参数判断
		String id = request.getParameter(tableidname);
		if (StringUtils.isBlank(id)) {
			return R.error("主键值不能为空，请修改。");
		}
		// 参数整理
		HashMap<String, Object> obj = service.getMapByKey(tablename, id, tableidname);
		// 主键参数是否正确的判断
		if (obj == null) {
			return R.error("您要查看的记录不存在，请修改。");
		}
		String viewname = DataUtils.getMapKeyValue(mapConfig, "viewname") + ",view";
		if (StringUtils.isNotBlank(request.getParameter("viewname"))) {
			viewname = request.getParameter("viewname");
		}		
		// 接口返回数据
		List<HashMap<String, Object>> listTable = service.getViewJson(tablename, viewname, request, obj);
		obj = new HashMap<String, Object>();
		obj.put("tableJson", listTable);
		return R.ok().put("data", obj);
	}
	@ApiOperation(value = "接口说明：View_PC通用查看页面接口", notes = "接口说明：View_PC通用查看页面接口")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "pageid", value = "通过配置唯一标识", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "id", value = "主键，必填。", required = true, dataType = "String", paramType = "query") })
	@GetMapping("/View_PC_New")
	public HashMap<String, Object> View_PC_New(HttpServletRequest request) throws Exception {
		// 参数判断
		String pageid = request.getParameter("pageid");
		if (StringUtils.isBlank(pageid)) {
			return R.error("pageid参数不能为空，请修改。");
		}
		// 得到通用配置数据
		HashMap<String, Object> mapConfig = service.getMap("sm_commonpage", pageid, "pageid");
		String tablename = DataUtils.getMapKeyValue(mapConfig, "tablename");
		String tableidname = DataUtils.getMapKeyValue(mapConfig, "tableidname");
		// 主键参数判断
		String id = request.getParameter(tableidname);
		if (StringUtils.isBlank(id)) {
			return R.error("主键值不能为空，请修改。");
		}
		// 参数整理
		HashMap<String, Object> obj = service.getMapByKey(tablename, id, tableidname);
		// 主键参数是否正确的判断
		if (obj == null) {
			return R.error("您要查看的记录不存在，请修改。");
		}
		String viewname = DataUtils.getMapKeyValue(mapConfig, "viewname") + ",view";
		if (StringUtils.isNotBlank(request.getParameter("viewname"))) {
			viewname = request.getParameter("viewname");
		}
		// 接口返回数据
		List<HashMap<String, Object>> listTable = service.getViewJson(tablename, viewname, request, obj);
		obj = new HashMap<String, Object>();
		obj.put("tableJson", listTable);
		return R.ok().put("data", obj);
	}

	@ApiOperation(value = "接口说明：GetList通用获取数据列表接口", notes = "接口说明：GetList通用获取数据列表接口")
	@ApiImplicitParams({ 
			@ApiImplicitParam(name = "pageid", value = "通过配置唯一标识", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "listview", value = "列表名称，选填", required = false, dataType = "String"),
			@ApiImplicitParam(name = "page", value = "当前页数，列表必填，导出excel选填。", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "rows", value = "每页条数，列表必填，导出excel选填。", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "sort", value = "排序字段，必填。", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "order", value = "排序方式，必填，只能是asc或者desc。", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "checkresult", value = "审核状态，选填，不同列表有不同默认值。", required = false, dataType = "String"), })
	@PostMapping("/GetList")
	public HashMap<String, Object> GetList(HttpServletRequest request) throws Exception {
		SysUser sysuser = DataUtils.getSysUser(request);
		// 参数判断
		String pageid = request.getParameter("pageid");
		if (StringUtils.isBlank(pageid)) {
			return R.error("pageid参数不能为空，请修改。");
		}
		// 得到通用配置数据
		HashMap<String, Object> mapConfig = service.getMap("sm_commonpage", pageid, "pageid");
		String tablename = DataUtils.getMapKeyValue(mapConfig, "tablename");
		String tableidname = DataUtils.getMapKeyValue(mapConfig, "tableidname");
		String checkfieldname = DataUtils.getMapKeyValue(mapConfig, "checkfieldname");
		String checkcode = DataUtils.getMapKeyValue(mapConfig, "checkcode");		
		// 管理权限控制
		String powermanage = DataUtils.getMapKeyValue(mapConfig, "powermanage");
		// 填写权限控制
		String powersubmit = DataUtils.getMapKeyValue(mapConfig, "powersubmit");
		// 数据权限控制，是否需要数据权限进行控制，01为需要数据权限。
		String powerdata = DataUtils.getMapKeyValue(mapConfig, "powerdata");
				
		// 数据实体
		MySqlData mySqlData = new MySqlData();
		// 数据库表名
		mySqlData.setTableName(tablename);
		// 得到是哪个列表
		String listview = tablename + "_MyList";
		if (request.getParameter("listview") != null) {
			listview = request.getParameter("listview");
		}
		//默认过滤条件
		if (StringUtils.isNotBlank(request.getParameter(tableidname))) {
			mySqlData.setFieldWhere(tablename + "." + tableidname, request.getParameter(tableidname), "in");
		}
		if ((tablename + "_MyList").equals(listview)) {// 录入列表
			// 填报期权限判断
			if (StringUtils.isNotBlank(checkfieldname) && StringUtils.isNotBlank(checkcode)) {
				if("01".equals(powersubmit) && !"1".equals(sysuser.getTimeSet())) {
					mySqlData.setSelectField("poweredit", "'edit,delete,withdraw' poweredit");
				} else {
					mySqlData.setSelectField("poweredit", "case when "+tablename+"."+checkfieldname+" in ('"+checkcode+"20') then 'edit,delete' when "+tablename+"."+checkfieldname+" in ('"+checkcode+"10','"+checkcode+"30') then 'withdraw' else 'edit,delete,withdraw' end poweredit");
				}
			}
			if("01".equals(powerdata) && !sysuser.getFunctionPermissions(tablename+"_MyList_All")) {
				mySqlData.setFieldOrWhere(""+tablename+".UserId", sysuser.getUserId(), "=");
				mySqlData.setFieldOrWhere(""+tablename+".submitman", sysuser.getUserId(), "=");
				if(sysuser.getFunctionPermissions(tablename+"_MyList_Dept")) {
					String deptfieldname = DataUtils.getMapKeyValue(mapConfig, "deptfieldname");
					mySqlData.setFieldOrWhere(""+tablename+"."+deptfieldname+"", ""+tablename+"."+deptfieldname+" IN (" + sysuser.getDepartmentId() + ")", "sql");
				}
			}
		} else if ((tablename+"_ViewList").equals(listview)) {// 查看列表
			if (StringUtils.isNotBlank(checkfieldname) && StringUtils.isNotBlank(checkcode)) {
				if (StringUtils.isNotBlank(request.getParameter(checkfieldname))) {
					mySqlData.setFieldWhere("" + tablename + "." + checkfieldname, request.getParameter(checkfieldname), "in");
				} else {
					mySqlData.setFieldWhere("" + tablename + "." + checkfieldname, "" + checkcode + "10", "!=");
				}
			}
			if("01".equals(powerdata) && !sysuser.getFunctionPermissions(tablename+"_ViewList_ViewAll")) {
				mySqlData.setFieldOrWhere(""+tablename+".AllUsers", sysuser.getUserId(), "like", ",");
				mySqlData.setFieldOrWhere(""+tablename+".UserId", sysuser.getUserId(), "=");
				mySqlData.setFieldOrWhere(""+tablename+".submitman", sysuser.getUserId(), "=");
				if(sysuser.getFunctionPermissions(tablename+"_ViewList_ViewDept")) {
					String deptfieldname = DataUtils.getMapKeyValue(mapConfig, "deptfieldname");
					mySqlData.setFieldOrWhere(""+tablename+"."+deptfieldname+"", ""+tablename+"."+deptfieldname+" IN (" + sysuser.getDepartmentId() + ")", "sql");
				}
			}
		}
		// 配置的固定过滤条件
		String pagewhere = DataUtils.getMapKeyValue(mapConfig, "pagewhere");
		if(StringUtils.isNotBlank(pagewhere)) {
			mySqlData.setFieldWhere("pagewhere", pagewhere, "sql");
		}
		// 查询和外键sql
		mySqlData.setSelectField("pageid", "'" + pageid + "' pageid");
		String outselect = DataUtils.getMapKeyValue(mapConfig, "outselect");
		if(StringUtils.isNotBlank(outselect)) {
			mySqlData.setSelectField("outselect", outselect);
		}
		String outleftjoin = DataUtils.getMapKeyValue(mapConfig, "outleftjoin");
		if(StringUtils.isNotBlank(outleftjoin)) {
			mySqlData.setJoinSqlCountJoin("outleftjoin", outleftjoin);
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
	
	@ApiOperation(value = "接口说明：GetListById通用通过id获取单条数据接口", notes = "接口说明：GetListById通用通过id获取单条数据接口")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "pageid", value = "通过配置唯一标识", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "id", value = "主键，必填。", required = true, dataType = "String", paramType = "query") })
	@GetMapping("/GetListById")
	public HashMap<String, Object> GetListById(HttpServletRequest request) throws Exception {
		// 参数判断
		String pageid = request.getParameter("pageid");
		if (StringUtils.isBlank(pageid)) {
			return R.error("pageid参数不能为空，请修改。");
		}
		// 得到通用配置数据
		HashMap<String, Object> mapConfig = service.getMap("sm_commonpage", pageid, "pageid");
		String tablename = DataUtils.getMapKeyValue(mapConfig, "tablename");
		String tableidname = DataUtils.getMapKeyValue(mapConfig, "tableidname");
		// 主键参数判断
		String id = request.getParameter(tableidname);
		if (StringUtils.isBlank(id)) {
			return R.error("主键值不能为空，请修改。");
		}
		// 参数整理
		SysUser sysuser = DataUtils.getSysUser(request);
		// 接口返回数据
		HashMap<String, Object> obj = service.getMapByKey(tablename, id, tableidname);
		return R.ok(obj);
	}
	
	@ApiOperation(value = "接口说明：SubmitCheck通用送审验证接口", notes = "接口说明：SubmitCheck通用送审验证接口")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "pageid", value = "通过配置唯一标识", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "id", value = "主键，必填。", required = true, dataType = "String", paramType = "query") })
	@PostMapping("/SubmitCheck")
	public HashMap<String, Object> SubmitCheck(HttpServletRequest request) throws Exception {
		/*// 参数判断
		String pageid = request.getParameter("pageid");
		if (StringUtils.isBlank(pageid)) {
			return R.error("pageid参数不能为空，请修改。");
		}
		// 得到通用配置数据
		HashMap<String, Object> mapConfig = service.getMap("sm_commonpage", pageid, "pageid");
		String tablename = DataUtils.getMapKeyValue(mapConfig, "tablename");
		String tableidname = DataUtils.getMapKeyValue(mapConfig, "tableidname");
		String checkcode = DataUtils.getMapKeyValue(mapConfig, "checkcode");
		// 主键参数判断
		String id = request.getParameter(tableidname);
		if (StringUtils.isBlank(id)) {
			return R.error("请先保存后再送审。");
		}*/
		return R.ok();
	}
	
	@ApiOperation(value = "接口说明：Save通用保存数据接口", notes = "接口说明：Save通用保存数据接口")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "pageid", value = "通过配置唯一标识", required = true, dataType = "String", paramType = "query") })
	@PostMapping("/Save")
	@Transactional
	public HashMap<String, Object> Save(MultipartHttpServletRequest request) throws Exception {
		SysUser sysuser = DataUtils.getSysUser(request);
		// 参数判断
		String pageid = request.getParameter("pageid");
//		if (StringUtils.isBlank(pageid)) {
//			return R.error("pageid参数不能为空，请修改。");
//		}
//		String sepageid = request.getParameter("sepageid");
//		pageid = DataUtils.ChooseNotNulls(pageid,sepageid);
		if (StringUtils.isBlank(pageid)) {
			return R.error("pageid参数不能为空，请修改。");
		}
		// 得到通用配置数据
		HashMap<String, Object> mapConfig = service.getMap("sm_commonpage", pageid, "pageid");
		String tablename = DataUtils.getMapKeyValue(mapConfig, "tablename");
		String tableidname = DataUtils.getMapKeyValue(mapConfig, "tableidname");
		String checkfieldname = DataUtils.getMapKeyValue(mapConfig, "checkfieldname");
		String checkcode = DataUtils.getMapKeyValue(mapConfig, "checkcode");
		// 管理权限控制
		String powermanage = DataUtils.getMapKeyValue(mapConfig, "powermanage");
		// 填写权限控制
		String powersubmit = DataUtils.getMapKeyValue(mapConfig, "powersubmit");
		// 数据权限控制
		String powerdata = DataUtils.getMapKeyValue(mapConfig, "powerdata");
		// 验证成果不能重复

		// 数据实体
		MySqlData mySqlData = new MySqlData();
		// 数据库表名
		mySqlData.setTableName(tablename);
		// 表单的视图名称默认为add，多个视图用英文对号分隔，例句：add,view,check
		String viewname = DataUtils.getMapKeyValue(mapConfig, "viewname");
		if (StringUtils.isNotBlank(request.getParameter("viewname"))) {
			viewname = request.getParameter("viewname");
		}
		mySqlData.setViewName(viewname);
		// 保存默认值
		String checkresult = request.getParameter(checkfieldname);
		String review = request.getParameter("review");
		String msg = "保存成功";
		if (StringUtils.isNotBlank(checkfieldname) && StringUtils.isNotBlank(checkcode)) {
			if (StringUtils.isNotBlank(review) && "1".equals(review)) {
				// 送审修改状态和验证表单必填项
				mySqlData.setCheckNull(true);
				mySqlData.setFieldValue(checkfieldname, "" + checkcode + "20");
				msg = "送审成功";
			} else if (StringUtils.isBlank(checkresult) || checkresult.equals("undefined")) {
				mySqlData.setFieldValue(checkfieldname, "" + checkcode + "10");
			} else if (StringUtils.isNotBlank(checkresult)) {
				mySqlData.setFieldValue(checkfieldname, checkresult);
			}
		}
		// 插入和更新判断，然后执行。
		ActionResult result = new ActionResult();
		String id = request.getParameter(tableidname);
		if (StringUtils.isBlank(id)) {
			// 权限判断
			if ("01".equals(powermanage) && !sysuser.getFunctionPermissions(tablename + "_Insert")) {
				return R.error("您没有权限操作所选记录。");
			}
			// 填报期权限判断
			if("01".equals(powersubmit) && !"1".equals(sysuser.getTimeSet())) {
				return R.error("您没有权限操作所选记录。");
			}
			// 执行插入操作
			result = service.insert(request, mySqlData);
			if (StringUtils.isNotBlank(result.getMsg())) {
				return R.error(result.getMsg());
			}
			// 增加保存之后的处理事件，例如 将负责人添加到作者列表
			id = result.getData().toString();
		} else {
			// 权限判断
			Boolean CommonPage_Update = sysuser.getFunctionPermissions(tablename + "_Update");
			Boolean CommonPage_EditAll = sysuser.getFunctionPermissions(tablename + "_EditAll");
			Boolean CommonPage_EditDept = sysuser.getFunctionPermissions(tablename + "_EditDept");
			if ("01".equals(powermanage) && !CommonPage_Update && !CommonPage_EditAll && !CommonPage_EditDept) {
				return R.error("您没有修改权限。");
			}
			// 填报期权限判断
			if("01".equals(powersubmit) && !"1".equals(sysuser.getTimeSet()) && !CommonPage_EditAll && !CommonPage_EditDept) {
				return R.error("您没有修改权限。");
			}
			HashMap<String, Object> obj = service.getMap(tablename, id, tableidname);
			// 主键参数是否正确的判断
			if (obj == null) {
				return R.error("您要修改的记录不存在，请修改。");
			}
			// 判断是否有权限修改，判断修改状态是否是草稿和退回修改状态，是否有特殊修改权限。
			// 没有编辑所有权限就需要验证状态和是否是本人可操作记录，所有权限一般设置在流程特殊管理菜单。
			if ("01".equals(powerdata) && !CommonPage_EditAll) {
				String checkresultold = DataUtils.getMapKeyValue(obj, checkfieldname);
				if (StringUtils.isNotBlank(checkfieldname) && StringUtils.isNotBlank(checkcode) && !(checkcode+"10").equals(checkresultold) && !(checkcode+"30").equals(checkresultold)) {
					// 判断修改状态是否是草稿和退回修改状态
					if (!CommonPage_EditDept) {
						return R.error("您没有修改权限。");
					}
				} 
				if (!sysuser.getFunctionPermissions(tablename + "_MyList_All")) {
					// 如果修改成uuid为主键，下面这部分判断其实也可以不要，因为不是自己可以查看到的记录是不知道id值的。
					// 一般只有录入列表才有修改功能，所以下面把录入列表的过滤条件复制过来，如果还有其他地方修改需要根据情况修改。
					MySqlData mySqlDataCheck = new MySqlData();
					mySqlDataCheck.setTableName(tablename);
					mySqlDataCheck.setFieldWhere(tableidname, id, "=");
					mySqlDataCheck.setFieldOrWhere(""+tablename+".UserId", sysuser.getUserId(), "=");
					mySqlDataCheck.setFieldOrWhere(""+tablename+".submitman", sysuser.getUserId(), "=");
					if (sysuser.getFunctionPermissions(tablename + "_MyList_Dept")) {
						String deptfieldname = DataUtils.getMapKeyValue(mapConfig, "deptfieldname");
						mySqlDataCheck.setFieldOrWhere(""+tablename+"."+deptfieldname+"", ""+tablename+"."+deptfieldname+" IN (" + sysuser.getDepartmentId() + ")", "sql");
					}
					obj = service.getMap(mySqlDataCheck);
					if (obj == null) {
						return R.error("您没有修改权限。");
					}
				}
			}
			// 执行更新操作
			mySqlData.setFieldWhere(tableidname, id, "=");
			result = service.update(request, mySqlData);
			if (StringUtils.isNotBlank(result.getMsg())) {
				return R.error(result.getMsg());
			}
			// 修改保存之后的处理事件
			// 插入日志
//			if (StringUtils.isNotBlank(checkfieldname) && StringUtils.isNotBlank(checkcode) && StringUtils.isNotBlank(review) && "1".equals(review)) {
//				serviceUser.AddCheckLog(sysuser, tablename, id, "送审", "" + checkcode + "20");
//			}
		}
		// 这个不能放这里操作
		if (result.getSuccess()) {
			return R.ok(msg).putId(tableidname,id);
		} else {
			return R.error(result.getMsg());
		}
	}
	
	@ApiOperation(value = "接口说明：Delete通用删除数据接口", notes = "接口说明：Delete通用删除数据接口。")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "pageid", value = "通过配置唯一标识", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "id", value = "主键，必填。", required = true, dataType = "String", paramType = "query") })
	@PostMapping("/Delete")
	@Transactional
	public HashMap<String, Object> Delete(HttpServletRequest request) throws Exception {
		// 参数判断
		String pageid = request.getParameter("pageid");
		if (StringUtils.isBlank(pageid)) {
			return R.error("pageid参数不能为空，请修改。");
		}
		// 得到通用配置数据
		HashMap<String, Object> mapConfig = service.getMap("sm_commonpage", pageid, "pageid");
		String tablename = DataUtils.getMapKeyValue(mapConfig, "tablename");
		String tableidname = DataUtils.getMapKeyValue(mapConfig, "tableidname");
		String checkfieldname = DataUtils.getMapKeyValue(mapConfig, "checkfieldname");
		String checkcode = DataUtils.getMapKeyValue(mapConfig, "checkcode");
		// 管理权限控制
		String powermanage = DataUtils.getMapKeyValue(mapConfig, "powermanage");
		// 填写权限控制
		String powersubmit = DataUtils.getMapKeyValue(mapConfig, "powersubmit");
		// 数据权限控制
		String powerdata = DataUtils.getMapKeyValue(mapConfig, "powerdata");
		// 主键参数判断
		String id = request.getParameter(tableidname);
		if (StringUtils.isBlank(id)) {
			return R.error("主键值不能为空，请修改。");
		}
		// 权限判断
		SysUser sysuser = DataUtils.getSysUser(request);
		Boolean CommonPage_Delete = sysuser.getFunctionPermissions(tablename + "_Delete");
		Boolean CommonPage_EditAll = sysuser.getFunctionPermissions(tablename + "_EditAll");
		Boolean CommonPage_EditDept = sysuser.getFunctionPermissions(tablename + "_EditDept");
		if ("01".equals(powermanage) && !CommonPage_Delete && !CommonPage_EditAll && !CommonPage_EditDept) {
			return R.error("您没有权限操作所选记录。");
		}
		// 填报期权限判断
		if("01".equals(powersubmit) && !"1".equals(sysuser.getTimeSet()) && !CommonPage_EditAll && !CommonPage_EditDept) {
			return R.error("您没有权限操作所选记录。");
		}
		
		// 判断是否有权限删除，判断修改状态是否是草稿和退回修改状态，是否有特殊修改权限。
		// 没有编辑所有权限就需要验证状态和是否是本人可操作记录，所有权限一般设置在流程特殊管理菜单。
		if ("01".equals(powerdata) && !CommonPage_EditAll) {
			MySqlData mySqlData = new MySqlData();
			mySqlData.setTableName(tablename);
			mySqlData.setSelectField(tableidname, tableidname);
			mySqlData.setFieldWhere(tableidname, id, "in");
			if (StringUtils.isNotBlank(checkfieldname) && StringUtils.isNotBlank(checkcode) && !CommonPage_EditDept) {
				mySqlData.setFieldWhere(checkfieldname, "" + checkcode + "10," + checkcode + "30", "in");
			}
			// 如果修改成uuid为主键，下面这部分过滤条件其实也可以不要，因为不是自己可以查看到的记录是不知道id值的。
			// 一般只有录入列表才有修改功能，所有下面把录入列表的过滤条件复制过来，如果还有其他地方修改需要根据情况修改。
			if (!sysuser.getFunctionPermissions(tablename + "_MyList_All")) {
				mySqlData.setFieldOrWhere(""+tablename+".UserId", sysuser.getUserId(), "=");
				mySqlData.setFieldOrWhere(""+tablename+".submitman", sysuser.getUserId(), "=");
				if (sysuser.getFunctionPermissions(tablename + "_MyList_Dept")) {
					String deptfieldname = DataUtils.getMapKeyValue(mapConfig, "deptfieldname");
					mySqlData.setFieldOrWhere(""+tablename+"."+deptfieldname+"", ""+tablename+"."+deptfieldname+" IN (" + sysuser.getDepartmentId() + ")", "sql");
				}
			}
			List<HashMap<String, Object>> list = service.getDataList(mySqlData);
			if (list == null || list.size() != id.split(",").length) {
				// 以后需要记录日志，记录操作人，操作位置，错误信息。主键值不能为空，请修改。
				return R.error("您没有权限操作所选记录。");
			}
		}
		// 执行删除操作
		Integer iResult = service.delete(tablename, id, tableidname);
		// 删除成功后可以删除关联子表数据

		if(iResult > 0) {
			return R.ok();
		} else {
			return R.error("删除0条数据。");
		}
	}

	@ApiOperation(value = "接口说明：ExcelExport通用Excel导出接口", notes = "接口说明：ExcelExport通用Excel导出接口")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "pageid", value = "通过配置唯一标识", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "exportname", value = "导出配置名，必填", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "export", value = "导出配置json，选择列导出时必填。", required = true, dataType = "String", paramType = "query")})
	@PostMapping("/ExcelExport")
	public HashMap<String, Object> ExcelExport(HttpServletRequest request) throws Exception {
		// 参数判断
		String pageid = request.getParameter("pageid");
		if (StringUtils.isBlank(pageid)) {
			return R.error("pageid参数不能为空，请修改。");
		}
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
			return R.ok().put("path", export.GetDownFilePath());
		}
	}

	@ApiOperation(value = "接口说明：ExcelImport通用Excel导入接口", notes = "接口说明：ExcelImport通用Excel导入接口")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "pageid", value = "通过配置唯一标识", required = true, dataType = "String", paramType = "query") })
	@PostMapping("/ExcelImport")
	public HashMap<String, Object> ExcelImport(MultipartHttpServletRequest request) throws Exception {
		SysUser sysuser = DataUtils.getSysUser(request);
		// 参数判断
		String pageid = request.getParameter("pageid");
		if (StringUtils.isBlank(pageid)) {
			return R.error("pageid参数不能为空，请修改。");
		}
		// 得到通用配置数据
		HashMap<String, Object> mapConfig = service.getMap("sm_commonpage", pageid, "pageid");
		String tablename = DataUtils.getMapKeyValue(mapConfig, "tablename");
		String tableidname = DataUtils.getMapKeyValue(mapConfig, "tableidname");
		String checkfieldname = DataUtils.getMapKeyValue(mapConfig, "checkfieldname");
		String checkcode = DataUtils.getMapKeyValue(mapConfig, "checkcode");
		String importname = DataUtils.getMapKeyValue(mapConfig, "importname");
		HashMap<String, Object> mapImport = service.getMap("sm_excelimportitem", importname, "importname");
		if (mapImport.isEmpty()) {
			return R.error(importname + "导入配置为空，请修改。");
		}
		// 文件上传
		MultipartFile requestFile = request.getFile("importfile");
		if (requestFile == null && requestFile.isEmpty()) {
			R.error("导入文件不能为空，请修改。");
		}
		String fileName = requestFile.getOriginalFilename();
		fileName = DataUtils.getDate("yyyyMMdd") + (new Random().nextInt(900000) + 100000) + fileName;
		String filePath = DataUtils.getConfInfo("file.uploadFolder") + "UpFile/";
		File file = new File(filePath);
		if (!file.exists()) {
			file.mkdirs();
		}
		file = new File(filePath, fileName);
		requestFile.transferTo(file);
		// Excel导入
		StringBuilder strAddField = new StringBuilder();
		StringBuilder strAddValue = new StringBuilder();
		String submitmanfieldname = DataUtils.getMapKeyValue(mapConfig, "submitmanfieldname");
		if (StringUtils.isNotBlank(submitmanfieldname)) {
			strAddField.append(submitmanfieldname + ",");
			strAddValue.append("'" + sysuser.getUserId() + "',");
		}
		String submittimefieldname = DataUtils.getMapKeyValue(mapConfig, "submittimefieldname");
		if (StringUtils.isNotBlank(submittimefieldname)) {
			strAddField.append(submittimefieldname + ",");
			strAddValue.append("NOW(),");
		}
		if (StringUtils.isNotBlank(checkfieldname) && StringUtils.isNotBlank(checkcode)) {
			String checkresult = "" + checkcode + "10";
			if (StringUtils.isNotBlank(request.getParameter("importcheckresult"))) {
				checkresult = request.getParameter("importcheckresult");
			}
			strAddField.append(checkfieldname + ",");
			strAddValue.append("'" + checkresult + "',");
		}		
		strAddField.append("IsConfirm,");
		strAddValue.append("'01',");
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
			return R.error(strError).put("data", "UpFile/" + fileName);
		}
	}
	
	@ApiOperation(value = "接口说明：Withdraw通用撤回接口", notes = "接口说明：Withdraw通用撤回接口")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "pageid", value = "通过配置唯一标识", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "id", value = "主键，必填。", required = true, dataType = "String", paramType = "query") })
	@PostMapping("/Withdraw")
	@Transactional
	public HashMap<String, Object> Withdraw(HttpServletRequest request) throws Exception {
		// 参数判断
		String pageid = request.getParameter("pageid");
		if (StringUtils.isBlank(pageid)) {
			return R.error("pageid参数不能为空，请修改。");
		}
		// 得到通用配置数据
		HashMap<String, Object> mapConfig = service.getMap("sm_commonpage", pageid, "pageid");
		String tablename = DataUtils.getMapKeyValue(mapConfig, "tablename");
		String tableidname = DataUtils.getMapKeyValue(mapConfig, "tableidname");
		String checkfieldname = DataUtils.getMapKeyValue(mapConfig, "checkfieldname");
		String checkcode = DataUtils.getMapKeyValue(mapConfig, "checkcode");
		// 管理权限控制
		String powermanage = DataUtils.getMapKeyValue(mapConfig, "powermanage");
		// 填写权限控制
		String powersubmit = DataUtils.getMapKeyValue(mapConfig, "powersubmit");
		// 数据权限控制
		String powerdata = DataUtils.getMapKeyValue(mapConfig, "powerdata");
		
		if (StringUtils.isBlank(checkfieldname) || StringUtils.isBlank(checkcode)) {
			return R.error("审核信息没有配置，撤回失败！");
		}
		// 主键参数判断
		String id = request.getParameter(tableidname);
		if (StringUtils.isBlank(id)) {
			return R.error("主键值不能为空，请修改。");
		}
		// 权限判断
		SysUser sysuser = DataUtils.getSysUser(request);
		Boolean CommonPage_Update = sysuser.getFunctionPermissions(tablename + "_Update");
		if ("01".equals(powermanage) && !CommonPage_Update) {
			return R.error("您没有权限操作所选记录。");
		}
		// 填报期权限判断
		if("01".equals(powersubmit) && !"1".equals(sysuser.getTimeSet())) {
			return R.error("您没有权限操作所选记录。");
		}
		
		// 判断是否有权限删除，判断修改状态是否是草稿和退回修改状态，是否有特殊修改权限。
		// 没有编辑所有权限就需要验证状态和是否是本人可操作记录，所有权限一般设置在流程特殊管理菜单。
		MySqlData mySqlData = new MySqlData();
		mySqlData.setTableName(tablename);
		mySqlData.setSelectField(tableidname, tableidname);
		mySqlData.setFieldWhere(tableidname, id, "in");
		if (StringUtils.isNotBlank(checkfieldname) && StringUtils.isNotBlank(checkcode)) {
			mySqlData.setFieldWhere(checkfieldname, "" + checkcode + "20", "in");
		}		
		// 如果修改成uuid为主键，下面这部分过滤条件其实也可以不要，因为不是自己可以查看到的记录是不知道id值的。
		// 一般只有录入列表才有修改功能，所有下面把录入列表的过滤条件复制过来，如果还有其他地方修改需要根据情况修改。
		if ("01".equals(powerdata) && !sysuser.getFunctionPermissions(tablename + "_MyList_All")) {
			mySqlData.setFieldOrWhere("" + tablename + ".UserId", sysuser.getUserId(), "=");
			mySqlData.setFieldOrWhere("" + tablename + ".submitman", sysuser.getUserId(), "=");
			if (sysuser.getFunctionPermissions(tablename + "_MyList_Dept")) {
				String deptfieldname = DataUtils.getMapKeyValue(mapConfig, "deptfieldname");
				mySqlData.setFieldOrWhere(""+tablename+"."+deptfieldname+"", ""+tablename+"."+deptfieldname+" IN (" + sysuser.getDepartmentId() + ")", "sql");
			}
		}
		List<HashMap<String, Object>> list = service.getDataList(mySqlData);
		if (list == null || list.size() != id.split(",").length) {
			// 以后需要记录日志，记录操作人，操作位置，错误信息。主键值不能为空，请修改。
			return R.error("您没有权限操作所选记录。");
		}
		// 执行更新操作
		mySqlData = new MySqlData();
		mySqlData.setTableName(tablename);
		mySqlData.setFieldValue(checkfieldname, "" + checkcode + "10");
		mySqlData.setFieldWhere(checkfieldname, id, "in");
		mySqlData.setFieldWhere(checkfieldname, "" + checkcode + "20", "=");
		ActionResult result = service.update(mySqlData);
		if (result.getSuccess()) {
			// 插入日志
//			serviceUser.AddCheckLog(sysuser, tablename, id, "撤回", checkcode + "10");
			return R.ok("撤回成功！");
		} else {
			return R.error("撤回失败！");
		}
	}



	//***扩展***************************************************************************************
	
	@ApiOperation(value = "接口说明：Add_PC通用添加页面接口", notes = "接口说明：Add_PC通用添加页面接口")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "pageid", value = "通过配置唯一标识", required = true, dataType = "String", paramType = "query") })
	@GetMapping("/TabList")
	public HashMap<String, Object> TabList(HttpServletRequest request) throws Exception {
		// 参数判断
		String pageid = request.getParameter("pageid");
		if (StringUtils.isBlank(pageid)) {
			return R.error("pageid参数不能为空，请修改。");
		}
		// 得到Tab配置数据
		MySqlData mySqlData = new MySqlData();
		mySqlData.setTableName("sm_commonpage_tab");
		mySqlData.setFieldWhere("pageid", pageid, "=");
		mySqlData.setFieldWhere("Visible", "01", "=");
		mySqlData.setSort("OrderId");
		List<HashMap<String, Object>> list = service.getDataList(mySqlData);
		
		return R.ok().put("data", list);
	}

}
