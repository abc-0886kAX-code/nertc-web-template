package com.ytxd.controller.SystemManage;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.ytxd.common.annotation.AuthIgnore;
import com.ytxd.common.exception.RRException;
import com.ytxd.common.util.R;
import com.ytxd.controller.BaseController;
import com.ytxd.model.ActionResult;
import com.ytxd.model.JsResult;
import com.ytxd.model.MySqlData;
import com.ytxd.model.SysUser;
import com.ytxd.service.CommonService;
import com.ytxd.service.ExcelOperation.ExportTable;
import com.ytxd.service.ExcelOperation.ImportExcel;
import com.ytxd.service.SystemManage.SM_Users_Service;
import com.ytxd.util.AESUtil;
import com.ytxd.util.StringUtil;
import com.ytxd.util.newDate;
import com.ytxd.util.secure.RSAUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 控制层(前后台交互)
 */
@Controller("sM_Users_Controller")
@RequestMapping(value = "/SystemManage/SM_Users")
@Api(value = "系统管理-系统用户接口", tags = "系统管理-系统用户接口")
public class SM_Users_Controller extends BaseController {
	@Autowired
	private SM_Users_Service Service;
	@Resource
	private CommonService service;


	@ApiOperation(value = "接口说明：系统用户管理录入页面接口"
			, notes = "接口说明：系统用户管理录入页面接口。<br>"
			+ "使用位置：系统管理-系统用户管理-录入列表<br>"
			+ "逻辑说明：获取系统用户管理录入列表页面搜索条件和列表信息等页面元素") 
	@GetMapping("/MyList_PC")
	@ResponseBody
	public Map<String, Object> MyList_PC(HttpServletRequest request, Model model) throws Exception {
		SysUser sysuser = DataUtils.getSysUser(request);
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("searchSchema", service.getSearchJson("SM_Users_MyList"));
		obj.put("columnSchema", service.getListJson("SM_Users_MyList"));
		Map<String,Object> listparam=new HashMap<String, Object>();
		listparam.put("listview", "SM_Users_MyList");
		if (StringUtils.isNotBlank(request.getParameter("departmentid"))) {
			obj.put("departmentid", request.getParameter("departmentid"));
		} else {
			if(!sysuser.getFunctionPermissions("SM_Users_MyList_All")) {
				obj.put("departmentid", sysuser.getDeptId());
			}else{
				obj.put("departmentid", "0");
			}
		}
		obj.put("listparam", listparam);
		obj.put("exportname", "SM_Users_MyList");
		obj.put("importname", "SM_Users_Import");
		obj.put("idfieldname", "userid");

		obj.put("sortname", "departmentid ASC,truename");
		obj.put("sortorder", "ASC");
		return R.ok().put("data", obj);
	}

	@ApiIgnore
	@RequestMapping("/ViewList_PC")
	@ResponseBody
	public R ViewList(HttpServletRequest request, Model model) throws Exception {
		SysUser sysuser = DataUtils.getSysUser(request);
		Map<String, Object> obj = new HashMap<String, Object>();
		if (StringUtils.isNotBlank(request.getParameter("departmentid"))) {
			obj.put("departmentid", request.getParameter("departmentid"));
		} else {
			obj.put("departmentid", sysuser.getDeptId());
		}
		String tableAlias = "SMUsers";
		obj.put("searchSchema", service.getSearchJson("SM_Users_ViewList"));
		obj.put("columnSchema", service.getListJson("SM_Users_ViewList"));
		return R.ok().put("data", obj);
	}


	@ApiOperation(value = "接口说明：新增用户接口"
			, notes = "接口说明：新增用户接口。<br>"
			+ "使用位置：系统管理-系统用户管理<br>"
			+ "逻辑说明：新增用户")
	@GetMapping("/Add_PC")
	@ResponseBody
	public Map<String, Object>  Add_PC(HttpServletRequest request, Model model) throws Exception {
		SysUser sysuser = DataUtils.getSysUser(request);
		String tablename = "SM_Users";
		String viewname = "System";
		HashMap<String, Object> obj = new HashMap<String, Object>();
		/*obj.put("checkresult", "1010");
		obj.put("submitman", sysuser.getUserId());
		obj.put("submittime", DataUtils.getDate("yyyy-MM-dd"));
		obj.put("department", sysuser.getDeptId());//单位默认是录入人所在单位*/
		List<HashMap<String, Object>> listTable = service.getAddJson(tablename, viewname, request, obj, sysuser);
		obj.put("tableJson", listTable);
		return R.ok().put("data", obj);
	}

	@ApiOperation(value = "接口说明：编辑用户接口"
			, notes = "接口说明：编辑用户接口。<br>"
			+ "使用位置：系统管理-系统用户管理<br>"
			+ "逻辑说明：编辑用户信息")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "userid", value = "编辑用户id", required = true, dataType = "String"),
	})
	@GetMapping("/Edit_PC")
	@ResponseBody
	public Map<String, Object> Edit_PC(HttpServletRequest request) throws Exception {
		SysUser sysuser = DataUtils.getSysUser(request);
		String userid = request.getParameter("userid");
		if (StringUtils.isBlank(userid)) { // 如果用户userid为空，说明在首页-个人资料页面，userid为当前用户的userid
			userid = sysuser.getUserId();
			// throw new RRException("主键值不能为空，请修改。");
		}
		/**
		 * 权限判断
		 */
		if(!sysuser.getFunctionPermissions("SM_Users_MyList_All")){
			MySqlData mySqlData = new MySqlData();
			mySqlData.setSql("select count(1) counts from sm_users where DepartmentId in (");
			mySqlData.setSql(sysuser.getDepartmentId());
			mySqlData.setSql(") and userid = ");
			mySqlData.setSqlValue(userid);
			HashMap<String,Object> info = service.getMap(mySqlData);
			if(info!=null && !info.isEmpty()){
				Integer counts = DataUtils.getMapIntegerValue(info,"counts");
				if(counts == 0){
					throw new RRException("你没有权限修改该用户信息！");
				}
			}else {
				throw new RRException("你没有权限修改该用户信息！");
			}
		}
		String tablename = "SM_Users";
		String viewname = "System";
		if (StringUtils.isNotBlank(request.getParameter("viewname"))) {
			viewname = request.getParameter("viewname");
		}

		HashMap<String, Object> obj = service.getMapByKey(tablename, userid, "userid");
		List<HashMap<String, Object>> listTable = service.getEditJson(tablename, viewname, request, obj);
//		obj = new HashMap<String, Object>();
		obj.put("tableJson", listTable);
		obj.remove("password");
		return R.ok().put("data", obj);
	}

	@ApiOperation(value = "接口说明：新增用户接口"
			, notes = "接口说明：新增用户接口。<br>"
			+ "使用位置：系统管理-系统用户管理<br>"
			+ "逻辑说明：新增用户")
	@GetMapping("/View_PC")
	@ResponseBody
	public Map<String, Object> View_PC(HttpServletRequest request, Model model) throws Exception {
		String userid = request.getParameter("userid");
		if (StringUtils.isBlank(userid)) {
			throw new RRException("主键值不能为空，请修改。");
		}
		SysUser sysuser = DataUtils.getSysUser(request);
		String tablename = "SM_Users";
		String viewname = "System";
		if (StringUtils.isNotBlank(request.getParameter("viewname"))) {
			viewname = request.getParameter("viewname");
		}
		HashMap<String, Object> obj = service.getMapByKey(tablename, userid, "userid");
		List<HashMap<String, Object>> listTable = service.getViewJson(tablename, viewname, request, obj);
		obj = new HashMap<String, Object>();
		obj.put("tableJson", listTable);
		return R.ok().put("data", obj);
	}

	@ApiOperation(value = "接口说明：获取用户列表接口"
			, notes = "接口说明：获取用户列表接口。<br>"
			+ "使用位置：录入用户<br>"
			+ "逻辑说明：输入全拼简拼查找用户")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "listview", value = "列表名称，非必填，默认维护列表", required = false, dataType = "String"),
			@ApiImplicitParam(name = "isexpert", value = "01（获取科研队伍的专家管理列表）00（增加内部专家列表数据）", required = false, dataType = "String"),
			@ApiImplicitParam(name = "page", value = "当前页数", required = true, dataType = "String",paramType="query"),
			@ApiImplicitParam(name = "rows", value = "每页条数", required = true, dataType = "String",paramType="query"),
			@ApiImplicitParam(name = "sort", value = "排序字段", required = true, dataType = "String",paramType="query"),
			@ApiImplicitParam(name = "order", value = "排序方式", required = true, dataType = "String",paramType="query"),
			@ApiImplicitParam(name = "departmentid", value = "部门id，默认001", required = false, dataType = "String"),
			@ApiImplicitParam(name = "usertype", value = "用户类型(02权限，00用户)", required = false, dataType = "String"),
	})
	@PostMapping("/GetList_PC")
	@ResponseBody
	public Map<String, Object> GetList_PC(HttpServletRequest request) throws Exception {
		SysUser sysuser = DataUtils.getSysUser(request);
		// 数据实体
		MySqlData mySqlData = new MySqlData();
		// 数据库表名
		String tablename = "SM_Users";
		mySqlData.setTableName(tablename);
		// 得到是哪个列表
		String listview = "SM_Users_MyList";
		if (request.getParameter("listview") != null) {
			listview = request.getParameter("listview");
		}
		// 默认过滤条件
		if (StringUtils.isNotBlank(request.getParameter("departmentid"))) {
			mySqlData.setFieldWhere("departmentid", request.getParameter("departmentid") + "%", "like");
		}
		if (StringUtils.isNotBlank(request.getParameter("usertype"))) {
			mySqlData.setFieldWhere("usertype", request.getParameter("usertype"), "=");
		}
		if (StringUtils.isNotBlank(request.getParameter("disabled"))) {
			mySqlData.setFieldWhere("disabled", request.getParameter("disabled"), "=");			
		}


//		if ("RT_ResearchTeam_DisUserList".equals(listview)){
//            //停用人员管理列表
//		}else if ("Expert_List".equals(listview)){
//			//专家管理列表
//			mySqlData.setFieldWhere("userid", " (sm_users.userid in (select userid from sm_users_role where roleid in (select UserId from SM_Users where LoginName='Role_Expert')))", "sql");
//			mySqlData.setFieldWhere("Disabled", "01", "=");
//			mySqlData.setFieldWhere("UserType", "02", "<>");
//		}else if ("Expert_SelectList".equals(listview)){
//			//添加内部专家列表
//			mySqlData.setFieldWhere("IsOutSide", " ifnull(SM_Users.IsOutSide,'00')='00' ", "sql");
//			mySqlData.setFieldWhere("Disabled", "01", "=");
//			mySqlData.setFieldWhere("UserType", "00", "=");
//			mySqlData.setFieldWhere("IsExpert", "SM_Users.IsExpert is null", "sql");
//		}

		if (StringUtils.isNotBlank(request.getParameter("isexpert"))) {
			if("01".equals(request.getParameter("isexpert"))){
				mySqlData.setFieldWhere("userid", " (sm_users.userid in (select userid from sm_users_role where roleid in (select UserId from SM_Users where LoginName='Role_Expert')))", "sql");
				mySqlData.setFieldWhere("Disabled", "01", "=");
				mySqlData.setFieldWhere("UserType", "02", "<>");

			}else{
	      		mySqlData.setFieldWhere("IsOutSide", " ifnull(SM_Users.IsOutSide,'00')='00' ", "sql");
				mySqlData.setFieldWhere("Disabled", "01", "=");
				mySqlData.setFieldWhere("UserType", "00", "=");
				mySqlData.setFieldWhere("IsExpert", "SM_Users.IsExpert is null", "sql");
			}

		}
		// 查询配置名称，就是列表上头的查询配置名称
		String searchPageName = listview;
		// 权限校验
		// 返回实体
		ActionResult result = service.getList(request, mySqlData, tablename, searchPageName);
		if (!result.getSuccess()) {
			return R.error(result.getMsg());
		}
//		Map<String,Object> obj = new HashMap<>();
//		obj.put("rows",result.getData());
//		obj.put("total",result.getTotal());
		return R.ok().put("data", result);
	}


	@ApiOperation(value = "接口说明：通过用户id获取用户信息"
			, notes = "接口说明：通过用户id获取用户信息<br>"
			+ "使用位置：通过用户id获取用户信息<br>"
			+ "逻辑说明：通过传过来的用户id，获取用户信息，返回到前端。<br>"
			+ "使用数据库表：SM_Users")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "userid", value = "用户id,必填", required = true, dataType = "String",paramType="query"),
	})
	@GetMapping("/GetListById")
	@ResponseBody
	public HashMap<String, Object> GetListById(HttpServletRequest request) throws Exception {
		String userid = request.getParameter("userid");
		if (StringUtils.isBlank(userid)) {
			return R.error("主键值不能为空，请修改。");
		}
		String tablename = "SM_Users";
		HashMap<String, Object> obj = service.getMapByKey(tablename, userid, "userid");
		if(obj == null) {
			return R.ok();
		} else {
			return R.ok(obj);
		}
	}

	@ApiOperation(value = "接口说明：用户启用停用"
			, notes = "接口说明：用户启用停用<br>"
			+ "使用位置：系统管理-系统用户管理<br>"
			+ "逻辑说明：通过传过来的用户id、状态来更新用户状态。<br>"
			+ "使用数据库表：SM_Users")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "userid", value = "用户id,必填", required = true, dataType = "String",paramType="query"),
			@ApiImplicitParam(name = "disabled", value = "用户状态，01启用，00停用", required = true, dataType = "String",paramType="query"),
	})
	@PostMapping("/Delete_PC")
	@ResponseBody
	public HashMap<String, Object> Delete_PC(HttpServletRequest request) throws Exception {
		String tablename = "SM_Users";
		String userid = request.getParameter("userid");
		String disabled = request.getParameter("disabled");
		String usertype = request.getParameter("usertype");
		ActionResult result;
		//数据实体
		MySqlData mySqlData = new MySqlData();
		mySqlData.setTableName(tablename);

		//人员
		if (StringUtils.isNotBlank(usertype)){
			mySqlData.setFieldWhere("userid", userid, "=");
			mySqlData.setFieldValue("disabled", disabled);
		}else {
			mySqlData.setFieldWhere("userid", userid, "in");
			usertype = DataUtils.getMapKeyValue(service.getMap(mySqlData), "usertype");
			if (!usertype.equals("01")) {
				//去掉专家角色
				MySqlData dmySqlData = new MySqlData();
				dmySqlData.setTableName("sm_users_role");
				dmySqlData.setFieldWhere("userid", userid, "in");
				dmySqlData.setFieldWhere("roleid", "3", "=");
				service.delete(dmySqlData);
				mySqlData.setFieldValue("isexpert", null);
			}
			mySqlData.setFieldValue("disabled", disabled);
		}
		result = service.update(mySqlData);
		if(result.getSuccess()) {
			return R.ok().put("data", "用户启用、停用成功！");
		} else {
			return R.error(result.getMsg());
		}
	}

	@ApiOperation(value = "接口说明：用户重置密码"
			, notes = "接口说明：用户重置密码<br>"
			+ "使用位置：系统管理-系统用户管理<br>"
			+ "逻辑说明：通过传过来的用户id重置密码。<br>"
			+ "使用数据库表：SM_Users")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "userid", value = "用户id,必填", required = true, dataType = "String",paramType="query"),
	})
	@PostMapping("/Replace_Password")
	@ResponseBody
	public HashMap<String, Object> Replace_Password(HttpServletRequest request) throws Exception {
		String userid = request.getParameter("userid");
		//数据实体
		MySqlData mySqlData = new MySqlData();
		mySqlData.setTableName("SM_Users");
		String pwd = AESUtil.aes_encrypt("kygl@123", "userPassword");
		mySqlData.setFieldValue("password", pwd, "#");
		mySqlData.setFieldWhere("userid", userid, "in");
		ActionResult result = service.update(mySqlData);
		if(result.getSuccess()) {
			return R.ok().put("data", "密码已重置为kygl@123！");
		} else {
			return R.error(result.getMsg());
		}
	}

	@ApiOperation(value = "接口说明：保存用户信息"
			, notes = "接口说明：保存用户信息<br>"
			+ "使用位置：系统管理-系统用户管理<br>"
			+ "逻辑说明：保存用户信息，或通过userid修改用户信息。<br>"
			+ "使用数据库表：SM_Users")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "userid", value = "用户id,必填", required = false, dataType = "String",paramType="query"),
	})
	@PostMapping("/Save_PC")
	@ResponseBody
	public HashMap<String, Object> Save_PC(MultipartHttpServletRequest request) throws Exception {
		SysUser sysuser = DataUtils.getSysUser(request);
		//验证账号是否重复
		String msg = SMUsersRepeat1(request);
		if(!"".equals(msg)) {
			return R.error(msg);
		}
		// 数据实体
		MySqlData mySqlData = new MySqlData();
		// 数据库表名
		mySqlData.setTableName("SM_Users");
		// 表单的视图名称默认为add，多个视图用英文对号分隔，例句：add,view,check
		String viewname = "System";
		mySqlData.setViewName(viewname);
		// 保存默认值
		if (StringUtils.isNotBlank(request.getParameter("departmentid"))) {
			mySqlData.setFieldValue("departmentid", request.getParameter("departmentid"));
		}
		String truename = request.getParameter("truename");
		if (StringUtils.isNotBlank(truename)) {
			mySqlData.setFieldValue("fullpinyin", StringUtil.toFullPinyin(truename), "#");
			mySqlData.setFieldValue("simplepinyin", StringUtil.toShortPinyin(truename), "#");
		}
		// 插入和更新判断，然后执行。
		ActionResult result = new ActionResult();
		String userid = request.getParameter("userid");
		String checkUserid =  request.getParameter("checkuserid");
		checkUserid = RSAUtils.decryptRSA(checkUserid);
		if(!Objects.equals(userid,checkUserid) && StringUtils.isNotBlank(userid)){
			throw new RRException("userid不正确");
		}
		if (StringUtils.isBlank(userid)) {
			if (StringUtils.isBlank(request.getParameter("password"))) {
				String pwd = "kygl@123";
				pwd = AESUtil.aes_encrypt(pwd, "userPassword");
				mySqlData.setFieldValue("password", pwd);
			}
			if (StringUtils.isBlank(request.getParameter("usertype"))) {
				mySqlData.setFieldValue("usertype", "00", "#");
			}
			if (StringUtils.isBlank(request.getParameter("disabled"))) {
				mySqlData.setFieldValue("disabled", "01", "#");
			}
			mySqlData.setFieldValue("creatby", sysuser.getUserId().toString(), "#");
			// 执行插入操作
			result = service.insert(request, mySqlData);
			// 增加保存之后的处理事件，例如 将负责人添加到作者列表
			if (result.getSuccess()) {
				// 赋默认角色 普通科研人员角色
				userid = result.getData().toString();
				mySqlData = new MySqlData();
				mySqlData.setSql(" insert into sm_users_role(userid, roleid) ");
				mySqlData.setSql("select '" + userid + "' userid, userid roleid ");
				mySqlData.setSql("from sm_users ");
				mySqlData.setSql("where LoginName='Role_Base' and UserType='02' ");
				service.update(mySqlData);
			}
		} else {
			if(!sysuser.getFunctionPermissions("SM_Users_MyList_All")){
				MySqlData checkData = new MySqlData();
				checkData.setSql("select count(1) counts from sm_users where DepartmentId in (");
				checkData.setSql(sysuser.getDepartmentId());
				checkData.setSql(") and userid = ");
				checkData.setSqlValue(userid);
				HashMap<String,Object> info = service.getMap(checkData);
				if(info!=null && !info.isEmpty()){
					Integer counts = DataUtils.getMapIntegerValue(info,"counts");
					if(counts == 0){
						throw new RRException("你没有权限修改该用户信息！");
					}
				}else {
					throw new RRException("你没有权限修改该用户信息！");
				}
			}
			String now = newDate.Time(); // 修改个人信息时，保存修改时间

			mySqlData.setFieldValue("lastlogintime", now);
			// 执行更新操作
			mySqlData.setFieldWhere("userid", userid, "=");
			result = service.update(request, mySqlData);
			// 修改保存之后的处理事件，例如 将负责人添加到作者列表
		}
		if (result.getSuccess()) {
			//更新刷新缓存
			Service.Save(userid);
			return R.ok().put("data", result.getData()).put("filehtml", result.getFileHtml());
		} else {
			return R.error(result.getMsg());
		}
	}

	@ApiIgnore
	@PostMapping("/Save")
	@ResponseBody
	@Transactional
	public HashMap<String, Object> Save(MultipartHttpServletRequest request) throws Exception {
		SysUser sysuser = DataUtils.getSysUser(request);
		//验证账号是否重复
		String msg = SMUsersRepeat(request);
		if(!"".equals(msg)) {
			return R.error(msg);
		}
		// 数据实体
		MySqlData mySqlData = new MySqlData();
		// 数据库表名
		mySqlData.setTableName("SM_Users");
		// 表单的视图名称默认为add，多个视图用英文对号分隔，例句：add,view,check
		String viewname = "System";
		mySqlData.setViewName(viewname);
		// 保存默认值
		if (StringUtils.isNotBlank(request.getParameter("departmentid"))) {
			mySqlData.setFieldValue("departmentid", request.getParameter("departmentid"));
		}
		String truename = request.getParameter("truename");
		if (StringUtils.isNotBlank(truename)) {
			mySqlData.setFieldValue("fullpinyin", StringUtil.toFullPinyin(truename), "#");
			mySqlData.setFieldValue("simplepinyin", StringUtil.toShortPinyin(truename), "#");
		}
		// 插入和更新判断，然后执行。
		ActionResult result = new ActionResult();
		String userid = request.getParameter("userid");
		String checkUserid =  request.getParameter("checkuserid");
		checkUserid = RSAUtils.decryptRSA(checkUserid);
		if(!Objects.equals(userid,checkUserid) && StringUtils.isNotBlank(userid)){
			throw new RRException("userid不正确");
		}
		/**
		 * 验证权限防止越权
		 */
		if(!sysuser.getFunctionPermissions("SM_Users_MyList_All")){
			MySqlData checkData = new MySqlData();
			checkData.setSql("select count(1) counts from sm_users where DepartmentId in (");
			checkData.setSql(sysuser.getDepartmentId());
			checkData.setSql(") and userid = ");
			checkData.setSqlValue(userid);
			HashMap<String,Object> info = service.getMap(checkData);
			if(info!=null && !info.isEmpty()){
				Integer counts = DataUtils.getMapIntegerValue(info,"counts");
				if(counts == 0){
					throw new RRException("你没有权限修改该用户信息！");
				}
			}else {
				throw new RRException("你没有权限修改该用户信息！");
			}
		}
		if (StringUtils.isBlank(userid)) {
			if (StringUtils.isBlank(request.getParameter("password"))) {
				String pwd = "Kygl@195";
				pwd = AESUtil.aes_encrypt(pwd, "userPassword");
				mySqlData.setFieldValue("password", pwd);
			}
			if (StringUtils.isBlank(request.getParameter("usertype"))) {
				mySqlData.setFieldValue("usertype", "00", "#");
			}
			if (StringUtils.isBlank(request.getParameter("disabled"))) {
				mySqlData.setFieldValue("disabled", "01", "#");
			}
			mySqlData.setFieldValue("creatby", sysuser.getUserId().toString(), "#");
			// 执行插入操作
			result = service.insert(request, mySqlData);
			// 增加保存之后的处理事件，例如 将负责人添加到作者列表
			if (result.getSuccess()) {
				// 赋默认角色 普通科研人员角色
				userid = result.getData().toString();
				mySqlData = new MySqlData();
				mySqlData.setSql(" insert into sm_users_role(userid, roleid) ");
				mySqlData.setSql("select '" + userid + "' userid, userid roleid ");
				mySqlData.setSql("from sm_users ");
				mySqlData.setSql("where LoginName='Role_Base' and UserType='02' ");
				service.update(mySqlData);
			}
		} else {
			String now = newDate.Time(); // 修改个人信息时，保存修改时间
			mySqlData.setFieldValue("lastlogintime", now);
			// 执行更新操作
			mySqlData.setFieldWhere("userid", userid, "=");
			result = service.update(request, mySqlData);
			// 修改保存之后的处理事件，例如 将负责人添加到作者列表
		}
		if (result.getSuccess()) {
			//更新刷新缓存
			Service.Save(userid);
			return R.ok().put("data", result.getData());
		} else {
			return R.error(result.getMsg());
		}
	}


	@ApiOperation(value = "接口说明：删除数据接口"
			, notes = "接口说明：删除数据接口。<br>"
			+ "使用位置：系统中删除记录的地方<br>"
			+ "逻辑说明：将指定id的记录删除"
			+ "使用数据库表：sm_users")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "userid", value = "唯一标识,必填，多个用,拼接", required = true, dataType = "String", paramType = "query"),
	})
	@PostMapping("/Delete")
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
		Integer iResult = Service.Delete(userid);//service.delete("SM_Users", userid, "userid");
		// 删除成功后可以删除关联子表数据
		if (iResult > 0) {
			return R.ok();
		} else {
			return R.error("删除0条数据。");
		}
	}

	@ApiOperation(value = "接口说明：Excel导出接口"
			, notes = "接口说明：Excel导出接口。<br>"
			+ "使用位置：各列表中导出Excel按钮<br>"
			+ "逻辑说明：exportname，导出相应的Excel")
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
		List<HashMap<String, Object>> listData = (List<HashMap<String, Object>>)((ActionResult)GetList_PC(request).get("data")).getData();
		export.setSource(listData);
		export.setProjectPath(request.getSession().getServletContext().getRealPath(""));
		strError = export.Export();
		if (StringUtils.isNotBlank(strError)) {
			return R.error(strError);
		} else {
			return R.ok().put("data", export.GetDownFilePath());
		}
	}
	@ApiIgnore
	@SuppressWarnings("null")
	@RequestMapping("/ExcelImport")
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
		String importname = "SM_Users_Import";
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
	
	@PostMapping("/GetListForCombobox")
	@ResponseBody
	public List<HashMap<String, Object>> GetListForCombobox(HttpServletRequest request) throws Exception {
		SysUser sysUser = DataUtils.getSysUser(request);
		String[] userid = null;
		String truename = request.getParameter("truename");
		if(StringUtils.isNotBlank(request.getParameter("userid"))) {
			userid = request.getParameter("userid").split(",");
		}
		return Service.GetListForCombobox(userid, truename);
	}

	@PostMapping("/GetListForSelected")
	@ResponseBody
	public List<HashMap<String, Object>> GetListForSelected(HttpServletRequest request) throws Exception {
		HashMap<String,Object> obj = DataUtils.getRequestMap(request);
		SysUser sysUser = DataUtils.getSysUser(request);
		obj.put("departmentid",sysUser.getDeptId());
		return Service.getSMUsersSelected(obj);
	}
	/*
	* 根据项目中的揭榜挂榜单位查询其所在单位下的人员
	* */
	@RequestMapping(value = "/GetListForSelectedByProjid",method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public List<HashMap<String, Object>> GetListForSelectedByProjid(HttpServletRequest request) throws Exception {
		HashMap<String,Object> obj = DataUtils.getRequestMap(request);
//		判断需要的是揭榜还是挂榜单位
		String usertype = request.getParameter("usertype");
		String tablename="projectapplication";
		MySqlData mySqlData=new MySqlData();
		mySqlData.setTableName(tablename);
		String projid = request.getParameter("projid");
		if(StringUtils.isBlank(projid)){
			throw new RRException("请选择一个项目！");
		}
//		限定条件
		mySqlData.setFieldWhere("id",projid,"=");
//		所要查询字段
		if("02".equals(usertype)){
			mySqlData.setSelectField("departmentid","MountUnitName departmentid");
		}else if("01".equals(usertype)){
			mySqlData.setSelectField("departmentid","OpenUnitName departmentid");
		}else if("03".equals(usertype)){
			mySqlData.setSelectField("departmentid","concat(OpenUnitName,',',MountUnitName) departmentid");
		}
		else{
			throw new RRException("没有此参数");
		}
		HashMap<String, Object> map = service.getMap(mySqlData);
		if(map != null && !map.isEmpty()){
			obj.put("departmentid",map.get("departmentid"));
		}else {
			throw new RRException("请先填写揭榜和挂榜单位！");
		}
		return Service.getSMUsersSelected(obj);
	}
	@RequestMapping(value = "/GetListForSelectedByProjidToLx",method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public List<HashMap<String, Object>> GetListForSelectedByProjidToLx(HttpServletRequest request) throws Exception {
		HashMap<String,Object> obj = DataUtils.getRequestMap(request);
//		判断需要的是揭榜还是挂榜单位
		String usertype = request.getParameter("usertype");
		String tablename="project";
		MySqlData mySqlData=new MySqlData();
		mySqlData.setTableName(tablename);
		String projid = request.getParameter("projid");
		if(StringUtils.isBlank(projid)){
			throw new RRException("请选择一个项目！");
		}
//		限定条件
		mySqlData.setFieldWhere("projid",projid,"=");
//		所要查询字段
		if("02".equals(usertype)){
			mySqlData.setSelectField("departmentid","MountUnitName departmentid");
		}else if("01".equals(usertype)){
			mySqlData.setSelectField("departmentid","OpenUnitName departmentid");
		}else if("03".equals(usertype)){
			mySqlData.setSelectField("departmentid","concat(OpenUnitName,',',MountUnitName) departmentid");
		}
		else{
			throw new RRException("没有此参数");
		}
		HashMap<String, Object> map = service.getMap(mySqlData);
		if(map != null && !map.isEmpty()){
			obj.put("departmentid",map.get("departmentid"));
		}else {
			throw new RRException("请先填写揭榜和挂榜单位！");
		}
		return Service.getSMUsersSelected(obj);
	}
	/**
	 *
	 * @Desription TODO 根据项目中的揭榜挂榜单位查询其所在单位下的人员
	 * @param request
	 * @return java.util.List<java.util.HashMap<java.lang.String,java.lang.Object>>
	 * @date 2023/11/15 17:51
	 * @Auther TY
	 */
	@RequestMapping("/GetListForSelectedByProjidTwo")
	@ResponseBody
	public List<HashMap<String, Object>> GetListForSelectedByProjidTwo(HttpServletRequest request) throws Exception {
		HashMap<String,Object> obj = DataUtils.getRequestMap(request);
//		判断需要的是揭榜还是挂榜单位
		String usertype = request.getParameter("pptr");
		String tablename="projectapplication";
		MySqlData mySqlData=new MySqlData();
		mySqlData.setTableName(tablename);
		String projid = request.getParameter("projid");
		String userid = request.getParameter("userid");
		String name = request.getParameter("name");
		userid = StringUtils.isNotBlank(userid)?userid:name;
		obj.put("userid",userid);
		if(StringUtils.isBlank(projid) && StringUtils.isBlank(userid)){
			throw new RRException("请选择一个项目！");
		}
		if(StringUtils.isNotBlank(projid) && StringUtils.isBlank(userid)){
			//		限定条件
			mySqlData.setFieldWhere("id",projid,"=");
			//		所要查询字段
			if("02".equals(usertype)){
				mySqlData.setSelectField("departmentid","MountUnitName departmentid");
			}else if("01".equals(usertype)) {
				mySqlData.setSelectField("departmentid","OpenUnitName departmentid");
			}else {
				mySqlData.setSelectField("departmentid","concat(OpenUnitName,',',MountUnitName) departmentid");
			}
			HashMap<String, Object> map = service.getMap(mySqlData);
			if(map != null && !map.isEmpty()){
				obj.put("departmentid",map.get("departmentid"));
			}else {
				throw new RRException("请先填写揭榜和挂榜单位！");
			}
		}
		return Service.getSMUsersSelected(obj);
	}
	/**
	 *
	 * @Desription TODO 更具人员编码获取人员信息
	 * @param request
	 * @return java.util.List<java.util.HashMap<java.lang.String,java.lang.Object>>
	 * @date 2023/11/6 14:27
	 * @Auther TY
	 */
	@PostMapping("/GetListForSelectedByCode")
	@ResponseBody
	public List<HashMap<String, Object>> GetListForSelectedByCode(HttpServletRequest request) throws Exception {
		HashMap<String,Object> obj = DataUtils.getRequestMap(request);
		String userid = DataUtils.getMapKeyValue(obj,"userid");
		String truename = DataUtils.getMapKeyValue(obj,"truename");
		String name = DataUtils.getMapKeyValue(obj,"name");
		if(StringUtils.isBlank(userid)&&StringUtils.isBlank(truename)&&StringUtils.isBlank(name)){
			return null;
		}
		obj.put("personcode",truename);
		obj.remove("truename");
		if(StringUtils.isNotBlank(name)){
			obj.put("userid",name);
			obj.remove("personcode");
		}
		return Service.getSMUsersSelected(obj);
	}

	/*@ApiOperation(value = "接口说明：获取用户列表接口"
			, notes = "接口说明：获取用户列表接口。<br>"
			+ "使用位置：录入用户<br>"
			+ "逻辑说明：输入全拼简拼查找用户")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "listview", value = "列表名称，非必填，默认维护列表", required = false, dataType = "String"),
			@ApiImplicitParam(name = "page", value = "当前页数", required = true, dataType = "String",paramType="query"),
			@ApiImplicitParam(name = "rows", value = "每页条数", required = true, dataType = "String",paramType="query"),
			@ApiImplicitParam(name = "sort", value = "排序字段", required = true, dataType = "String",paramType="query"),
			@ApiImplicitParam(name = "order", value = "排序方式", required = true, dataType = "String",paramType="query"),
			@ApiImplicitParam(name = "depttype", value = "部门类型，非必填，默认行政部门（01院系，02行政部门）", required = false, dataType = "String"),
	})
	@ResponseBody
	@PostMapping("/GetListForCombobox")*/
	public Map<String, Object> GetListForCombobox_new(HttpServletRequest request) throws Exception {
		String[] userid = null;
		String truename = request.getParameter("truename");
		if(StringUtils.isNotBlank(request.getParameter("userid"))) {
			userid = request.getParameter("userid").split(",");
		}
		/**
		 return Service.GetListForCombobox(userid, truename);
		 */

		ActionResult result =  new ActionResult();
		List<HashMap<String,Object>> obj = Service.GetListForCombobox(userid, truename);
		result.setData(obj);
		result.setMsg("");
		result.setTotal(obj.size());
		return  R.ok().put("data",result);
	}

	public String SMUsersRepeat1(HttpServletRequest request) throws Exception {
		if(StringUtils.isBlank(request.getParameter("loginname"))) {
			return "账号不能为空，请修改。";
		}
		String userid = request.getParameter("userid");
		MySqlData mySqlData = new MySqlData();
		mySqlData.setTableName("SM_Users");
		mySqlData.setFieldWhere("LoginName", request.getParameter("loginname"), "=");
		mySqlData.setFieldWhere("Disabled", "01", "=");
		if (StringUtils.isNotBlank(userid)) {
			//修改
			mySqlData.setFieldWhere("UserId", userid, "!=");
		}
		if(service.getListCount(mySqlData) > 0) {
			return "账号已经存在，请修改。";
		}
		return "";
	}
	@ApiIgnore
	@RequestMapping("/SMUsersRepeat")
	@ResponseBody
	public String SMUsersRepeat(HttpServletRequest request) throws Exception {
		if(StringUtils.isBlank(request.getParameter("loginname"))) {
			return "账号不能为空，请修改。";
		}
		String userid = request.getParameter("userid");
		MySqlData mySqlData = new MySqlData();
		mySqlData.setTableName("SM_Users");
		mySqlData.setFieldWhere("LoginName", request.getParameter("loginname"), "=");
		mySqlData.setFieldWhere("Disabled", "01", "=");
		if (StringUtils.isNotBlank(userid)) {
			//修改
			mySqlData.setFieldWhere("UserId", userid, "!=");
		}
		if(service.getListCount(mySqlData) > 0) {
			return "账号已经存在，请修改。";
		}
		return "";
	}
	@ApiIgnore
	@AuthIgnore
	@RequestMapping("/UpdatePinYin")
	@ResponseBody
	public Integer UpdatePinYin() throws Exception {
		MySqlData mySqlData = new MySqlData();
		mySqlData.setTableName("sm_users");
		mySqlData.setSelectField("*", "userid,truename");
		List<HashMap<String, Object>> list = service.getDataList(mySqlData);
		if(list != null) {
			for(Map<String, Object> map : list) {
				mySqlData = new MySqlData();
				mySqlData.setTableName("sm_users");
				mySqlData.setFieldWhere("userid", DataUtils.getMapKeyValue(map, "userid"),"=");
				mySqlData.setFieldValue("fullpinyin", StringUtil.toFullPinyin(DataUtils.getMapKeyValue(map, "truename")));
				mySqlData.setFieldValue("simplepinyin", StringUtil.toShortPinyin(DataUtils.getMapKeyValue(map, "truename")));
				service.update(mySqlData);
			}
			//更新刷新缓存
			Service.Save(DataUtils.getMapKeyValue(list.get(0), "userid"));
		}
		return 1;
	}
	@ApiIgnore
	@RequestMapping("/SetRole")
	public String SetRole(HttpServletRequest request, Model model) throws Exception {
		String userid = request.getParameter("userid");
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("userid", userid);
		model.addAttribute("SM_Users", obj);
		return "SystemManage/SM_Users/SM_Role_MyList";
	}


	@ApiOperation(value = "接口说明：获取用户权限列表"
			, notes = "接口说明：获取用户权限列表<br>"
			+ "使用位置：系统管理-系统用户管理<br>"
			+ "逻辑说明：获取用户权限列表<br>"
			+ "使用数据库表：SM_Users，sm_users_role")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "userid", value = "用户id,必填", required = false, dataType = "String",paramType="query"),
	})
	@PostMapping("/GetListUserRole")
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

	@ApiIgnore
	@RequestMapping("/SaveRole")
	@ResponseBody
	public Integer SaveRole(HttpServletRequest request) throws Exception {
		//删除用户所有角色
		MySqlData mySqlData = new MySqlData();
		mySqlData.setTableName("sm_users_role");
		mySqlData.setFieldWhere("userid", request.getParameter("userid"), "=");
		service.delete(mySqlData);
		//增加用户选中角色
		mySqlData = new MySqlData();
		mySqlData.setSql("INSERT INTO sm_users_role(userid,roleid) ");
		mySqlData.setSql("SELECT ");
		mySqlData.setSqlValue(request.getParameter("userid"));
		mySqlData.setSql(" userid,userid roleid ");
		mySqlData.setSql("FROM sm_users ");
		mySqlData.setFieldWhere("userid", request.getParameter("allrole"), "in");
		return service.update(mySqlData).getTotal();
	}

	@ApiOperation(value = "接口说明：保存用户权限接口"
			, notes = "接口说明：保存用户权限接口<br>"
			+ "使用位置：系统管理-系统用户管理<br>"
			+ "逻辑说明：根据用户id保存用户权限<br>"
			+ "使用数据库表：SM_Users，sm_users_role")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "userid", value = "用户id,必填", required = true, dataType = "String",paramType="query"),
		@ApiImplicitParam(name="allrole",value="权限Id,必填（如1,2,3,）",required = true, dataType = "String",paramType="query")
	})
	@PostMapping("/SaveRole_PC")
	@ResponseBody
	public Map<String, Object> SaveRole_PC(HttpServletRequest request) throws Exception {
		//删除用户所有角色
		MySqlData mySqlData = new MySqlData();
		mySqlData.setTableName("sm_users_role");
		mySqlData.setFieldWhere("userid", request.getParameter("userid"), "=");
		service.delete(mySqlData);
		//增加用户选中角色
		mySqlData = new MySqlData();
		mySqlData.setSql("INSERT INTO sm_users_role(userid,roleid) ");
		mySqlData.setSql("SELECT ");
		mySqlData.setSqlValue(request.getParameter("userid"));
		mySqlData.setSql(" userid,userid roleid ");
		mySqlData.setSql("FROM sm_users ");
		mySqlData.setFieldWhere("userid", request.getParameter("allrole"), "in");
		Integer iResult =  service.update(mySqlData).getTotal();
		if (iResult > 0) {
			return R.ok("保存权限成功！");
		} else {
			return R.error("保存权限失败！");
		}
	}

	@ApiIgnore
	@RequestMapping("/SaveDisabled")
	@ResponseBody
	public HashMap<String, Object> SaveDisabled(HttpServletRequest request) throws Exception {
		String userid = request.getParameter("userid");
		if (StringUtils.isBlank(userid)) {
			throw new RRException("主键值不能为空，请修改。");
		}
		SysUser sysuser = DataUtils.getSysUser(request);
		// 数据实体
		MySqlData mySqlData = new MySqlData();
		// 数据库表名
		mySqlData.setTableName("SM_Users");
		// 表单的视图名称默认为add，多个视图用英文对号分隔，例句：add,view,check
		// 保存默认值
		if (StringUtils.isNotBlank(request.getParameter("disabled"))) {
			mySqlData.setFieldValue("disabled", request.getParameter("disabled"));
		}
		// 插入和更新判断，然后执行。
		ActionResult result = new ActionResult();
		// 执行更新操作
		mySqlData.setFieldWhere("userid", userid, "in");
		result = service.update(mySqlData);
		// 修改保存之后的处理事件，例如 将负责人添加到作者列表
		if (result.getSuccess()) {
			//更新刷新缓存
			Service.Save(userid);
			return R.ok().put("data", result.getData());
		} else {
			return R.error(result.getMsg());
		}
	}
	@ApiIgnore
	@RequestMapping("/EditPassword")
	public String EditPassword(HttpServletRequest request) throws Exception {
		return "SystemManage/SM_Users/SM_Users_EditPassword";
	}

	/**
	 * 修改系统用户的密码
	 * 
	 * 
	 * @param request
	 *
	 * @return
	 * @throws Exception
	 */
	@ApiIgnore
	@RequestMapping(value = "/editpwd")
	@ResponseBody
	public JsResult updateSysUserPWD(HttpServletRequest request) throws Exception {
		JsResult result = new JsResult();
		SysUser sysuser = DataUtils.getSysUser(request);
		if (sysuser == null) {
			result.setMessage("1");
			return result;
		}
		String oldpass = request.getParameter("oldpass");
		if (StringUtil.isEmpty(oldpass)) {
			result.setMessage("请输入原始密码！");
			return result;
		}
		String pwd = request.getParameter("pwd");
		if (StringUtil.isEmpty(pwd)) {
			result.setMessage("请输入新密码！");
			return result;
		}
		MySqlData mySqlData = new MySqlData();
		mySqlData.setTableName("SM_Users");
		mySqlData.setSelectField("password", "password");
		mySqlData.setFieldWhere("UserId", sysuser.getUserId(), "=");
		Map<String, Object> mapUser = service.getMap(mySqlData);
		//得到加密后的密码
		String userPassword = DataUtils.getMapKeyValue(mapUser, "password");
		//解密成明文在按照前台加密方式进行加密，然后进行对比
		if (userPassword.length() == 32) {
			userPassword = AESUtil.aes_decrypt(userPassword, "userPassword");
		}
		if (!oldpass.equals(userPassword)) {
			result.setMessage("原始密码错误！");
			return result;
		}
		String regExValue = "^(?![^a-zA-Z]+$)(?!\\D+$).{8,16}$";
		Pattern p = Pattern.compile(regExValue);
		boolean b = p.matches(regExValue, pwd);
		if (b == false) {
			result.setMessage("密码安全性过低，请重新设置！密码规则为：8到16位字母和数字的组合");
			return result;
		}
		//保存修改后的密码
		mySqlData = new MySqlData();
		mySqlData.setTableName("SM_Users");
		mySqlData.setFieldWhere("UserId", sysuser.getUserId(), "=");		
		//将密码加密后保存
		pwd= AESUtil.aes_encrypt(pwd, "userPassword");
		mySqlData.setFieldValue("password", pwd);
		try {
			service.update(mySqlData);
			result.setMessage("");
			return result;
		} catch (Exception e) {
			logger.error("修改密码错误，应该不会发生。", e);
			result.setMessage("修改密码错误。");
			return result;
		}
	}
	
	@ApiOperation(value = "接口说明：修改密码"
			, notes = "接口说明：修改密码<br>"
			+ "逻辑说明：参数：oldpass原始密码，pwd修改后的密码<br>"
			+ "使用数据库表：SM_Users")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "userid", value = "用户id,必填", required = false, dataType = "String",paramType="query"),
	})
	@PostMapping("/editpwd_PC")
	@ResponseBody
	public Map<String, Object> editpwd_PC(HttpServletRequest request) throws Exception {
		SysUser sysuser = DataUtils.getSysUser(request);
		if (sysuser == null) {
			return R.error("您还没有登录或登录已超时，请重新登录！");
		}
		String oldpass = request.getParameter("oldpass");
		if (StringUtil.isEmpty(oldpass)) {
			return R.error("请输入原始密码！");
		}
		String pwd = request.getParameter("pwd");
		if (StringUtil.isEmpty(pwd)) {
			return R.error("请输入新密码！");
		}
		MySqlData mySqlData = new MySqlData();
		mySqlData.setTableName("SM_Users");
		mySqlData.setSelectField("password", "password");
		mySqlData.setFieldWhere("UserId", sysuser.getUserId(), "=");
		Map<String, Object> mapUser = service.getMap(mySqlData);
		//得到加密后的密码
		String userPassword = DataUtils.getMapKeyValue(mapUser, "password");
		//解密成明文在按照前台加密方式进行加密，然后进行对比
		if (userPassword.length() == 32) {
			userPassword = AESUtil.aes_decrypt(userPassword, "userPassword");
		}
		if (!oldpass.equals(userPassword)) {
			return R.error("原始密码错误！");
		}
		String regExValue = "^(?![^a-zA-Z]+$)(?!\\D+$).{8,16}$";
		Pattern p = Pattern.compile(regExValue);
		boolean b = p.matches(regExValue, pwd);
		if (b == false) {
			return R.error("密码安全性过低，请重新设置！密码规则为：8到16位字母和数字的组合");
		}
		//保存修改后的密码
		mySqlData = new MySqlData();
		mySqlData.setTableName("SM_Users");
		mySqlData.setFieldWhere("UserId", sysuser.getUserId(), "=");		
		//将密码加密后保存
		pwd= AESUtil.aes_encrypt(pwd, "userPassword");
		mySqlData.setFieldValue("password", pwd);
		try {
			service.update(mySqlData);
			return R.ok();
		} catch (Exception e) {
			logger.error("修改密码错误，应该不会发生。", e);
			return R.error("修改密码错误。");
		}
	}
	@ApiIgnore
	@RequestMapping("/DialogViewList_PC")
	@ResponseBody
	public R DialogViewList(HttpServletRequest request, Model model) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		String departmentid = "";
		if(StringUtils.isNotBlank(request.getParameter("departmentid"))) {
			departmentid = request.getParameter("departmentid");
		}
		String tableAlias = "SMUsers";
		obj.put("searchSchema", service.getSearchJson("SM_Users_ViewList"));
		obj.put("columnSchema", service.getListJson("SM_Users_ViewList"));
		obj.put("departmentid", departmentid);
		return R.ok().put("data", obj);
	}

	@ApiOperation(value = "接口说明：密码重置接口"
			, notes = "接口说明：密码重置接口<br>"
			+ "使用位置：密码重置接口<br>"
			+ "逻辑说明：密码重置接口<br>"
			+ "使用数据库表：SM_Users")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "userid", value = "用户id,必填", required = true, dataType = "String",paramType="query")
	})
	@PostMapping("/ResetPassword")
	@ResponseBody
	public HashMap<String, Object> ResetPassword(HttpServletRequest request) throws Exception {
		String userid = request.getParameter("userid");
		if (StringUtils.isBlank(userid)) {
			throw new RRException("主键值不能为空，请修改。");
		}
		SysUser sysuser = DataUtils.getSysUser(request);
		// 数据实体
		MySqlData mySqlData = new MySqlData();
		// 数据库表名
		mySqlData.setTableName("SM_Users");
		// 表单的视图名称默认为add，多个视图用英文对号分隔，例句：add,view,check
		// 保存默认值
		//将默认密码修改成身份证后6位加密，然后保存，如果身份证号为空就默认kygl@123
		mySqlData.setFieldValue("password", "HEX(AES_ENCRYPT('kygl@123','userPassword'))", "$");
		// 插入和更新判断，然后执行。
		ActionResult result = new ActionResult();
		// 执行更新操作
		mySqlData.setFieldWhere("userid", userid, "in");
		result = service.update(mySqlData);
		// 修改保存之后的处理事件，例如 将负责人添加到作者列表
		if (result.getSuccess()) {
			return R.ok("重置密码成功").put("data", result.getData());
		} else {
			return R.error(result.getMsg());
		}
	}
	@ApiIgnore
	@RequestMapping("/EditDept")
	public String EditDept(HttpServletRequest request, Model model) throws Exception {
		String userid = request.getParameter("userid");
		model.addAttribute("SM_Users", userid);
		return "SystemManage/SM_Users/SM_Users_EditDept";
	}
	
	
	
	
	
	// ***用户同步***************************************************************************************
	@ApiIgnore
	@RequestMapping("/GetSyncList")
	@ResponseBody
	public Map<String, Object> GetSyncList(HttpServletRequest request) throws Exception {
		SysUser sysuser = DataUtils.getSysUser(request);
		// 分页
		Integer page = 0;
		Integer rows = 50;
		if (StringUtils.isNotBlank(request.getParameter("page"))) {
			try {
				page = Integer.parseInt(request.getParameter("page"));
			} catch (Exception ex) {
				return R.error("page参数不是整数，请修改。");
			}
		}
		if (StringUtils.isNotBlank(request.getParameter("rows"))) {
			try {
				rows = Integer.parseInt(request.getParameter("rows"));
			} catch (Exception ex) {
				return R.error("rows参数不是整数，请修改。");
			}
		}
		// 排序
		String sort = "";
		String order = "";
		if (StringUtils.isNotBlank(request.getParameter("sort"))) {
			sort = request.getParameter("sort");
		}
		if (StringUtils.isNotBlank(request.getParameter("order"))) {
			order = request.getParameter("order");
		}
		MySqlData mySqlData = new MySqlData();
		StringBuilder strSql = new StringBuilder();
		strSql.append("select UserId KeySysId, UserId, LoginName, TrueName, sm_department.DepartmentId, sm_department.NodeName DepartmentIdName");
		strSql.append(", case when Sex like '%男%' then '01' else '02' end Sex, Sex SexName, '00' UserType, '01' Disabled, 'Default' Style, IdCard");
		strSql.append(", case when PoliticalAppearanceTag like '%共产党%' then '01' when PoliticalAppearanceTag like '%群众%' then '13' end PoliticalAppearanceTag, PoliticalAppearanceTag PoliticalAppearanceTagName");
		strSql.append(", case when DegreeTag like '%博士%' then '01' when DegreeTag like '%硕士%' then '02' when DegreeTag like '%研究生%' then '02' when DegreeTag like '%本科%' then '03' when DegreeTag like '%专科%' or DegreeTag like '%大学%' then '04' else '05' end DegreeTag, DegreeTag DegreeTagName");
		strSql.append(", Birthday, OfficePhone, FamilyPhone, MobilePhone, Email, Address, ZipCode");
		strSql.append(", case when Degree like '%博士%' then '01' when Degree like '%硕士%' then '02' when Degree like '%学士%' then '03' else '04' end Degree, Degree DegreeName");
		strSql.append(", JobNo, homeaddress, QQ ");
		strSql.append("from kjmissync.sm_users ");
		strSql.append("left join (select KeySysId from sm_users where KeySysId is not null) smuser on smuser.KeySysId=sm_users.UserId ");
		strSql.append("left join (select KeySysId, DepartmentId, NodeName from sm_department) sm_department on sm_department.KeySysId=sm_users.DepartmentId ");
		strSql.append("where smuser.KeySysId is null ");
		strSql.append("order by " + sort + " " + order + " ");
		strSql.append("LIMIT " + page + ", " + rows + "");
		strSql.append("");
		mySqlData.setSql(strSql.toString());
		List list = service.getDataList(mySqlData);
		
		mySqlData = new MySqlData();
		strSql = new StringBuilder();
		strSql.append("select count(1) ");
		strSql.append("from kjmissync.sm_users ");
		strSql.append("left join (select KeySysId from sm_users where KeySysId is not null) smuser on smuser.KeySysId=sm_users.UserId ");
		strSql.append("left join (select KeySysId, DepartmentId from sm_department) sm_department on sm_department.KeySysId=sm_users.DepartmentId ");
		strSql.append("where smuser.KeySysId is null ");
		strSql.append("");
		mySqlData.setSql(strSql.toString());
		Integer total = service.getListCount(mySqlData);
		return R.ok().put("rows", list).put("total", total);
	}
	@ApiIgnore
	@RequestMapping("/UsersSync")
	@ResponseBody
	@Transactional
	public HashMap<String, Object> UsersSync(HttpServletRequest request) throws Exception {
		SysUser sysuser = DataUtils.getSysUser(request);
		String userid = "";
		if (StringUtils.isBlank(request.getParameter("userid"))) {
			return R.error("userid不能为空，请修改。");
		} else {
			userid = request.getParameter("userid").replace("'", "");
		}
		// 数据实体
		MySqlData mySqlData = new MySqlData();
		StringBuilder strSql = new StringBuilder();
		strSql.append("insert into sm_users(KeySysId, UserId, LoginName, TrueName, PassWord, DepartmentId, Sex, UserType, Disabled, Style, IdCard");
		strSql.append(", PoliticalAppearanceTag, DegreeTag, Birthday, OfficePhone, FamilyPhone, MobilePhone, Email, Address, ZipCode, Degree, JobNo, homeaddress, QQ) ");
		strSql.append("select UserId KeySysId, UserId, LoginName, TrueName, HEX(AES_ENCRYPT('kygl@123','userPassword')) PassWord, sm_department.DepartmentId");
		strSql.append(", case when Sex like '%男%' then '01' else '02' end Sex, '00' UserType, '01' Disabled, 'Default' Style, IdCard");
		strSql.append(", case when PoliticalAppearanceTag like '%共产党%' then '01' when PoliticalAppearanceTag like '%群众%' then '13' end PoliticalAppearanceTag");
		strSql.append(", case when DegreeTag like '%博士%' then '01' when DegreeTag like '%硕士%' then '02' when DegreeTag like '%研究生%' then '02' when DegreeTag like '%本科%' then '03' when DegreeTag like '%专科%' or DegreeTag like '%大学%' then '04' else '05' end DegreeTag");
		strSql.append(", Birthday, OfficePhone, FamilyPhone, MobilePhone, Email, Address, ZipCode");
		strSql.append(", case when Degree like '%博士%' then '01' when Degree like '%硕士%' then '02' when Degree like '%学士%' then '03' else '04' end Degree");
		strSql.append(", JobNo, homeaddress, QQ ");
		strSql.append("from kjmissync.sm_users sm_users ");
		strSql.append("left join (select KeySysId from sm_users where KeySysId is not null) smuser on smuser.KeySysId=sm_users.UserId ");
		strSql.append("left join (select KeySysId, DepartmentId from sm_department) sm_department on sm_department.KeySysId=sm_users.DepartmentId ");
		strSql.append("where smuser.KeySysId is null ");
		strSql.append("and sm_users.UserId in ('" + userid.replace(",", "','") + "') ");
		strSql.append("order by UserId ");
		strSql.append("");
		mySqlData.setSql(strSql.toString());
		
		ActionResult result = service.insert(mySqlData);
		if (result.getSuccess()) {
			return R.ok().put("data", result.getData());
		} else {
			return R.error(result.getMsg());
		}
	}
	@ApiIgnore
	@RequestMapping("/EADEdit")
	public String EADEdit(HttpServletRequest request, Model model) throws Exception {
		return "SystemManage/SM_Users/SM_Users_EADEdit";
	}
	@ApiOperation(value = "接口说明：加解密接口"
			, notes = "接口说明：加解密接口<br>")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "type", value = "type=01为加密，type=02为解密，必填。", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "content", value = "加解密内容，必填。", required = true, dataType = "String", paramType = "query"),
	})
	@ApiIgnore
	@RequestMapping("/EAD")
	@ResponseBody
	public HashMap<String, Object> EAD(HttpServletRequest request) throws Exception {
		String type = request.getParameter("type");
		String content = request.getParameter("content");
		String result = "";
		if ("01".equals(type)) {// 加密
			result = AESUtil.aes_encrypt(content, "userPassword");
		} else {// 解密
			result = AESUtil.aes_decrypt(content, "userPassword");
		}
		return R.ok().put("data", result);
	}

	@ApiOperation(value = "接口说明：系统填报时间用户多选页面接口"
			, notes = "接口说明：系统填报时间用户多选页面接口。<br>"
			+ "使用位置：系统管理-系统填报时间-用户多选列表<br>"
			+ "逻辑说明：获取系统填报时间录入列表页面搜索条件和列表信息等页面元素")
	@GetMapping("/Choose_User_MyList_PC")
	@ResponseBody
	public Map<String, Object> Choose_User_MyList_PC(HttpServletRequest request, Model model) throws Exception {
		SysUser sysuser = DataUtils.getSysUser(request);
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("searchSchema", service.getSearchJson("Choose_User_MyList"));
		obj.put("columnSchema", service.getListJson("Choose_User_MyList"));
		Map<String,Object> listparam=new HashMap<String, Object>();
		listparam.put("listview", "SM_TimeSet_MyList");
//        if (StringUtils.isNotBlank(request.getParameter("departmentid"))) {
//            obj.put("departmentid", request.getParameter("departmentid"));
//        } else {
//            if(!sysuser.getFunctionPermissions("SM_TimeSet_MyList_All")) {
//                obj.put("departmentid", sysuser.getDeptId());
//            }else{
//                obj.put("departmentid", "0");
//            }
//        }
//		List<String> powerList=new ArrayList<String>();
//		powerList.add("add");
//		powerList.add("edit");
//		powerList.add("delete");
//		powerList.add("import");
//		powerList.add("export");
//		obj.put("powerall", powerList);
		obj.put("listparam", listparam);
//		obj.put("exportname", "Choose_User_MyList");
//		obj.put("importname", "SM_TimeSet_Import");
		obj.put("idfieldname", "userid");
		obj.put("sortname", "departmentid ASC,truename");
		obj.put("sortorder", "ASC");
		return R.ok().put("data", obj);
	}

	@ApiOperation(value = "接口说明：用户多选列表数据接口"
			, notes = "接口说明：用户多选列表数据接口。<br>"
			+ "使用位置：用户多选<br>"
			+ "逻辑说明：获取用户多选的用户数据")
	@PostMapping("/GetList_Choose_User")
	@ResponseBody
	public R GetList_Choose_User(HttpServletRequest request) throws Exception {
		SysUser sysuser = DataUtils.getSysUser(request);
		// 数据实体
		MySqlData mySqlData = new MySqlData();
		// 数据库表名
		String tablename = "SM_Users";
		mySqlData.setTableName(tablename);
		// 得到是哪个列表
		String listview = "Choose_User_MyList";
		if (request.getParameter("listview") != null) {
			listview = request.getParameter("listview");
		}
		// 默认过滤条件
		if (StringUtils.isNotBlank(request.getParameter("departmentid"))) {
			mySqlData.setFieldWhere("departmentid", request.getParameter("departmentid") + "%", "like");
		}
		if (StringUtils.isNotBlank(request.getParameter("usertype"))) {
			mySqlData.setFieldWhere("usertype", request.getParameter("usertype"), "=");
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
	/**
	 *
	 * @Desription TODO 人员的启用和停用
	 * @param request
	 * @return com.ytxd.common.util.R
	 * @date 2023/11/20 9:18
	 * @Auther TY
	 */
	@PostMapping("/SMUserEnableORDeactivate")
	public R SMUserEnableORDeactivate(HttpServletRequest request) throws Exception{
		String userid = request.getParameter("userid");
		return R.ok();
	}

}
