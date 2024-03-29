package com.ytxd.controller.SystemManage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.ytxd.common.DataUtils;
import com.ytxd.common.exception.RRException;
import com.ytxd.common.util.R;
import com.ytxd.controller.BaseController;
import com.ytxd.model.ActionResult;
import com.ytxd.model.EasyTree;
import com.ytxd.model.MySqlData;
import com.ytxd.model.SysUser;
import com.ytxd.model.SystemManage.SM_FunctionTree;
import com.ytxd.model.SystemManage.SM_Role;
import com.ytxd.model.SystemManage.SM_Route;
import com.ytxd.service.CommonService;
import com.ytxd.service.SystemManage.SM_Role_Service;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * 控制层(前后台交互)
 */
@Controller("sM_Role_Controller")
@RequestMapping(value = "/SystemManage/SM_Role")
@Api(value = "系统管理-角色管理接口", tags = "系统管理-角色管理接口")
public class SM_Role_Controller extends BaseController {
	@Resource
	private CommonService service;
	@Autowired
	private SM_Role_Service Service;

	@ApiOperation(value = "接口说明：角色管理录入页面接口"
			, notes = "接口说明：角色管理录入页面接口。<br>"
			+ "使用位置：系统管理-角色管理-录入列表<br>"
			+ "逻辑说明：获取角色管理录入列表页面搜索条件和列表信息等页面元素") 
	@GetMapping("/MyList_PC")
	@ResponseBody
	public Map<String, Object> MyList_PC(HttpServletRequest request, Model model) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("searchSchema", service.getSearchJson("SM_Role_MyList"));
		obj.put("columnSchema", service.getListJson("SM_Role_MyList"));
		Map<String,Object> listparam=new HashMap<String, Object>();
		listparam.put("listview", "SM_Role_MyList");
		obj.put("listparam", listparam);
		obj.put("idfieldname", "userid");
		obj.put("sortname", "orderid,userid");
		obj.put("sortorder", "ASC");
		return R.ok().put("data", obj);
	}

	@ApiOperation(value = "接口说明：角色管理新增页面接口"
			, notes = "接口说明：角色管理新增页面接口。<br>"
			+ "使用位置：成果管理-角色管理-增加页面<br>"
			+ "逻辑说明：点击增加按钮新增时，调用该接口获取页面元素和默认值。") 
	@GetMapping("/Add_PC")
	@ResponseBody
	public Map<String, Object> Add_PC(HttpServletRequest request, Model model) throws Exception {
		SysUser sysuser = DataUtils.getSysUser(request);
		String tablename = "SM_Users";
		String viewname = "Role";
		HashMap<String, Object> obj = new HashMap<String, Object>();
		List<HashMap<String, Object>> listTable = service.getAddJson(tablename, viewname, request, obj, sysuser);
		obj.put("tableJson", listTable);
		return R.ok().put("data", obj);
	}

	@ApiOperation(value = "接口说明：角色管理修改页面接口"
			, notes = "接口说明：角色管理修改页面接口。<br>"
			+ "使用位置：成果管理-角色管理-修改页面<br>"
			+ "逻辑说明：点击修改按钮修改时，调用该接口获取页面元素和数据。") 
	@ApiImplicitParams({
			@ApiImplicitParam(name = "userid", value = "数据唯一标识", required = true, dataType = "String", paramType = "query"),
	})
	@GetMapping("/Edit_PC")
	@ResponseBody
	public Map<String, Object> Edit_PC(HttpServletRequest request, Model model) throws Exception {
		String userid = request.getParameter("userid");
		if (StringUtils.isBlank(userid)) {
			throw new RRException("主键值不能为空，请修改。");
		}
		String tablename = "SM_Users";
		String viewname = "Role";
		if (StringUtils.isNotBlank(request.getParameter("viewname"))) {
			viewname = request.getParameter("viewname");
		}
		HashMap<String, Object> obj = service.getMapByKey(tablename, userid, "userid");
		List<HashMap<String, Object>> listTable = service.getEditJson(tablename, viewname, request, obj);
		obj = new HashMap<String, Object>();
		obj.put("tableJson", listTable);
		return R.ok().put("data", obj);
	}
	
	@ApiOperation(value = "接口说明：录入和查看等列表得到列表数据"
			, notes = "接口说明：录入和查看等列表得到列表数据。<br>"
			+ "使用位置：录入和查看等列表得到列表数据。<br>"
			+ "逻辑说明：通过过滤条件和分页信息得到数据列表。<br>"
			+ "使用数据库表：SM_Users") 
	@ApiImplicitParams({
			@ApiImplicitParam(name = "listview", value = "列表名称，非必填，默认录入列表", required = false, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "roleid", value = "列表名称，非必填，点击角色名称查看对应人员时传，以roleid作为参数名，以点击行对应的userid作为参数值", required = false, dataType = "String", paramType = "query"),
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
		String tablename = "SM_Users";
		mySqlData.setTableName(tablename);
		// 得到是哪个列表
		String listview = "SM_Role_MyList";
		if (request.getParameter("listview") != null) {
			listview = request.getParameter("listview");
		}
		// 默认过滤条件
		if ("SM_Role_MyList".equals(listview)) {// 录入列表
			mySqlData.setFieldWhere("usertype", "02", "=");
		} else if ("SM_Role_UserList".equals(listview)) {
			//  查看有角色权限的人员列表
			mySqlData.setFieldWhere("usertype", "00", "=");
			if (StringUtils.isNotBlank(request.getParameter("roleid"))) {
				mySqlData.setJoinSql("SM_Users_Role", "left join (select DISTINCT UserId RoleUserId, RoleId from SM_Users_Role) SM_Users_Role on SM_Users_Role.RoleUserId=SM_Users.UserId ");
				mySqlData.setFieldWhere("SM_Users_Role.RoleId", request.getParameter("roleid"), "=");
				//mySqlData.setFieldWhere("sm_users.useridin", " sm_users.userid in (select userid from sm_users_role where roleid='" + request.getParameter("roleid") + "') ", "sql");
			}
		}
		if (StringUtils.isNotBlank(request.getParameter("disabled"))) {
			mySqlData.setFieldWhere("disabled", request.getParameter("disabled"), "=");
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

	@RequestMapping("/GetListById")
	@ResponseBody
	public HashMap<String, Object> GetListById(HttpServletRequest request) throws Exception {
		String userid = request.getParameter("userid");
		if (StringUtils.isBlank(userid)) {
			return R.error("主键值不能为空，请修改。");
		}
		String tablename = "SM_Users";
		HashMap<String, Object> obj = service.getMapByKey(tablename, userid, "userid");
		return R.ok(obj);
	}
	@ApiOperation(value = "接口说明：保存数据接口"
			, notes = "接口说明：保存数据接口。<br>"
			+ "使用位置：新增和修改后保存数据<br>"
			+ "逻辑说明：通过id是否为空判断是新增还是修改，将表单信息保存到数据表中。<br>"
			+ "使用数据库表：SM_Users") 
	@PostMapping("/Save")
	@ResponseBody
	@Transactional
	public HashMap<String, Object> Save(MultipartHttpServletRequest request) throws Exception {
		SysUser sysuser = DataUtils.getSysUser(request);
		// 数据实体
		MySqlData mySqlData = new MySqlData();
		// 数据库表名
		mySqlData.setTableName("SM_Users");
		// 表单的视图名称默认为add，多个视图用英文对号分隔，例句：add,view,check
		String viewname = "Role";
		mySqlData.setViewName(viewname);
		// 保存默认值

		// 插入和更新判断，然后执行。
		ActionResult result = new ActionResult();
		String userid = request.getParameter("userid");
		if (StringUtils.isBlank(userid)) {
			// 保存默认值
			mySqlData.setFieldValue("usertype", "02");
			mySqlData.setFieldValue("disabled", "01");
			mySqlData.setFieldValue("creatby", sysuser.getUserId());
			// 执行插入操作
			result = service.insert(request, mySqlData);
			// 增加保存之后的处理事件，例如 将负责人添加到作者列表
		} else {
			// 执行更新操作
			mySqlData.setFieldWhere("userid", userid, "=");
			result = service.update(request, mySqlData);
			// 修改保存之后的处理事件
		}
		if (result.getSuccess()) {
			return R.ok().put("data", result.getData());
		} else {
			return R.error(result.getMsg());
		}
	}

	@ApiOperation(value = "接口说明：删除数据接口"
			, notes = "接口说明：删除数据接口。<br>"
			+ "使用位置：系统中删除记录的地方<br>"
			+ "逻辑说明：将指定id的记录删除数据<br>"
			+ "使用数据库表：SM_Users") 
	@ApiImplicitParams({
			@ApiImplicitParam(name = "userid", value = "唯一标识，多个用英文逗号分隔。", required = true, dataType = "String", paramType = "query"),
	})
	@RequestMapping(value = "/Delete", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	@Transactional
	public HashMap<String, Object> Delete(HttpServletRequest request) throws Exception {
		// 数据有效性校验
		String userid = request.getParameter("userid");
		if (StringUtils.isBlank(userid)) {
			return R.error("主键值不能为空，请修改。");
		}

		// 权限校验
		// 执行删除操作

		// 删除用户菜单权限
		service.delete("sm_users_function", userid, "userid");
		// 删除角色关联用户
		service.delete("sm_users_role", userid, "roleid");
		// 删除角色路由权限
		service.delete("sm_users_route", userid, "userid");
		// 删除角色
		Integer iResult = service.delete("SM_Users", userid, "userid");
		// 删除成功后可以删除关联子表数据
		return R.ok();
	}

	// ***扩展***************************************************************************************
	@RequestMapping("/FunctionEdit")
	public String FunctionEditDialog(HttpServletRequest request, Model model) throws Exception {
		String userid = request.getParameter("userid");
		SM_Role objUser = Service.GetListById(userid);
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("userid", objUser.getuserid());
		obj.put("truename", objUser.gettruename());
		model.addAttribute("SM_Users", obj);
		return "SystemManage/SM_Role/SM_Users_Function_Edit";
	}
	@RequestMapping("/RouteEdit")
	public String RouteEditDialog(HttpServletRequest request, Model model) throws Exception {
		String userid = request.getParameter("userid");
		SM_Role objUser = Service.GetListById(userid);
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("userid", objUser.getuserid());
		obj.put("truename", objUser.gettruename());
		model.addAttribute("SM_Users", obj);
		return "SystemManage/SM_Role/SM_Users_Route_Edit";
	}
	@RequestMapping("/GetFunctionAllTree")
	@ResponseBody
	public List<EasyTree> dataShow(HttpServletRequest request, SM_FunctionTree obj) throws Exception {
		return Service.GetFunctionAllTree(obj);
	}
	@RequestMapping("/GetRouteAllTree")
	@ResponseBody
	public List<EasyTree> GetRouteAllTree(HttpServletRequest request, SM_Route obj) throws Exception {
		return Service.GetRouteAllTree(obj);
	}
	@ApiOperation(value = "接口说明：获取所有菜单树接口"
			, notes = "接口说明：获取所有菜单树接口。<br>"
			+ "使用位置：给角色设置菜单权限时获取所有的菜单<br>") 
	@PostMapping("/GetFunctionAllTree_PC")
	@ResponseBody
	public Map<String,Object> GetFunctionAllTree_PC(HttpServletRequest request) throws Exception {
		String userid=request.getParameter("userid");
		if(StringUtils.isBlank(userid)){
			R.error("userid不能为空");
		}
		SM_FunctionTree obj = new SM_FunctionTree();
		obj.setuserid(userid);
		List<EasyTree> FunctionAllTree = Service.GetFunctionAllTree(obj);
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("success",true);
		map.put("msg","");
		map.put("total",FunctionAllTree.size());
		map.put("data",FunctionAllTree);
		return R.ok().put("data", map);
	}
	@ApiOperation(value = "接口说明：获取所有路由树接口"
			, notes = "接口说明：获取所有路由树接口。<br>"
			+ "使用位置：给角色设置路由权限时获取所有的路由<br>") 
	@PostMapping("/GetRouteAllTree_PC")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "userid", value = "唯一标识，当前选择的角色的userid。", required = true, dataType = "String", paramType = "query"),
	})
	@ResponseBody
	public Map<String,Object> GetRouteAllTree_PC(HttpServletRequest request) throws Exception {
		String userid=request.getParameter("userid");
		if(StringUtils.isBlank(userid)){
			R.error("userid不能为空");
		}
		SM_Route obj = new SM_Route();
		obj.setUserid(userid);
		List<EasyTree> RouteAllTree = Service.GetRouteAllTree(obj);
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("success",true);
		map.put("msg","");
		map.put("total",RouteAllTree.size());
		map.put("data",RouteAllTree);
		return R.ok().put("data", map);
	}
	
	@RequestMapping("/PowerSave")
	@ResponseBody
	public Integer PowerSave(HttpServletRequest request, SM_FunctionTree obj) throws Exception {
		SysUser user = (SysUser) request.getSession().getAttribute("sysuser");
		return Service.PowerSave(obj, user);
	}
	@RequestMapping("/RouteSave")
	@ResponseBody
	public Integer RouteSave(HttpServletRequest request, SM_Route obj) throws Exception {
		SysUser user = (SysUser) request.getSession().getAttribute("sysuser");
		return Service.RouteSave(obj, user);
	}
	@ApiOperation(value = "接口说明：保存角色菜单权限接口"
			, notes = "接口说明：保存角色菜单权限接口。<br>"
			+ "使用位置：系统管理-角色管理-授权<br>"
			+ "使用数据库表：sm_users_function") 
	@ApiImplicitParams({
		@ApiImplicitParam(name = "userid", value = "角色的userid，必填。", required = true, dataType = "String", paramType = "query"),
		@ApiImplicitParam(name = "id", value = "选中的菜单id，多个以英文逗号分隔,非必填，如果全不选即可不传。", required = false, dataType = "String", paramType = "query"),
	})
	@PostMapping("/PowerSave_PC")
	@ResponseBody
	public Map<String,Object> PowerSave_PC(HttpServletRequest request) throws Exception {
		SysUser user = DataUtils.getSysUser(request);
		String id="0";
		if(StringUtils.isBlank(request.getParameter("userid"))){
			return R.error("userid不能为空，请修改");
		}
		if(StringUtils.isNotBlank(request.getParameter("id"))){
			id=request.getParameter("id");
		}
		SM_FunctionTree obj=new SM_FunctionTree();
		obj.setuserid(request.getParameter("userid"));
		obj.setid(id);
		Service.PowerSave(obj, user);
		return R.ok();
	}
	@ApiOperation(value = "接口说明：保存角色路由权限接口"
			, notes = "接口说明：保存角色路由权限接口。<br>"
			+ "使用位置：系统管理-角色管理-前端授权<br>"
			+ "使用数据库表：sm_users_route") 
	@ApiImplicitParams({
		@ApiImplicitParam(name = "userid", value = "角色的userid，必填。", required = true, dataType = "String", paramType = "query"),
		@ApiImplicitParam(name = "id", value = "选中的路由id，多个以英文逗号分隔,非必填，如果全不选即可不传。", required = false, dataType = "String", paramType = "query"),
	})
	@PostMapping("/RouteSave_PC")
	@ResponseBody
	public Map<String,Object> RouteSave_PC(HttpServletRequest request, @RequestBody JSONObject object) throws Exception {
		SysUser user = DataUtils.getSysUser(request);
		String id="0";
//		if(StringUtils.isBlank(request.getParameter("userid"))){
//			return R.error("userid不能为空，请修改");
//		}
//		if(StringUtils.isNotBlank(request.getParameter("id"))){
//			id=request.getParameter("id");
//		}
		if(StringUtils.isBlank(object.getString("userid"))){
			return R.error("userid不能为空，请修改");
		}
		if(StringUtils.isNotBlank(object.getString("id"))){
			id=object.getString("id");
		}
		SM_Route obj=new SM_Route();
		obj.setUserid(object.getString("userid"));
//		obj.setUserid(request.getParameter("userid"));
		obj.setid(id);
		Service.RouteSave(obj, user);
		return R.ok();
	}


	@RequestMapping("/GetListUserRole")
	@ResponseBody
	public Map<String, Object> GetListUserRole(HttpServletRequest request) throws Exception {
		SysUser sysuser = DataUtils.getSysUser(request);
		String userid = request.getParameter("userid").replace(" ", "").replace("'", "");
		// 数据实体
		MySqlData mySqlData = new MySqlData();
		// 数据库表名
		mySqlData.setTableName("SM_Users");
		mySqlData.setViewName("Role");
		mySqlData.setFieldWhere("UserType", "02", "=");
		mySqlData.setFieldWhere("Disabled", "01", "=");
		// mySqlData.setJoinSql("systemtype", "left join (select code codejoin,description descriptionjoin,orderid orderidjoin from sm_codeitem where codeid='RN') systemtype on systemtype.codejoin=sm_users.systemtype");
		mySqlData.setSelectField("sm_users_role", "case when sm_users_role.roleidjoin is not null then '01' else '00' end ischecked");
		mySqlData.setJoinSql("sm_users_role", "left join (select userid useridjoin,roleid roleidjoin from sm_users_role where userid='" + userid + "' ) sm_users_role on sm_users_role.roleidjoin=sm_users.userid");
		// 权限校验
		// 返回实体
		ActionResult result = service.getList(request, mySqlData, "SM_Users", "SM_Role_MyList");
		if (!result.getSuccess()) {
			return R.error(result.getMsg());
		}
		return R.ok().put("rows", result.getData()).put("total", result.getTotal());
	}

	/**
	 * 获取以list集合返回的权限列表
	 * 
	 * @param request
	 * @param obj
	 * @param whereString
	 * @return 返回list数据集合
	 * @throws Exception
	 */
	@RequestMapping("/FindRoleList")
	@ResponseBody
	public List<SM_Role> FindRoleList(HttpServletRequest request, SM_Role obj) throws Exception {
		SysUser user = (SysUser) request.getSession().getAttribute("sysuser");
		return Service.GetRoleList(obj, user, "");
	}

	@ApiOperation(value = "接口说明：角色管理点击角色名称查看角色对应的人员列表表头接口"
			, notes = "接口说明：角色管理点击角色名称查看角色对应的人员列表表头接口。<br>"
			+ "使用位置：系统管理-角色管理-录入列表-点击角色名称<br>") 
	@GetMapping("/RoleUserList_PC")
	@ResponseBody
	public Map<String,Object> RoleUserList_PC(HttpServletRequest request, Model model) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("searchSchema", service.getSearchJson("SM_Users_ViewList"));
		obj.put("columnSchema", service.getListJson("SM_Users_ViewList"));
		Map<String,Object> listparam=new HashMap<String, Object>();
		listparam.put("listview", "SM_Role_UserList");
		obj.put("listparam", listparam);
		obj.put("idfieldname", "userid");
		obj.put("sortname", "truename");
		obj.put("sortorder", "ASC");
		return R.ok().put("data", obj);
	}
}
