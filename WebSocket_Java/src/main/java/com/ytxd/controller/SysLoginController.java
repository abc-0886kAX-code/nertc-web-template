package com.ytxd.controller;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSONObject;
import com.ytxd.common.DataUtils;
import com.ytxd.common.HttpUtil;
import com.ytxd.common.annotation.AuthIgnore;
import com.ytxd.common.util.Constant.LoginType;
import com.ytxd.common.util.IPUtils;
import com.ytxd.common.util.R;
import com.ytxd.model.MySqlData;
import com.ytxd.model.SysUser;
import com.ytxd.model.SystemManage.SM_CodeItem;
import com.ytxd.service.CommonService;
import com.ytxd.service.Log.Log_Login_Service;
import com.ytxd.service.SystemManage.SM_CodeItem_Service;
import com.ytxd.service.SystemManage.SM_FunctionTree_Service;
import com.ytxd.service.SystemManage.SM_Route_Service;
import com.ytxd.service.SystemManage.SM_Users_Service;
import com.ytxd.util.AESUtil;
import com.ytxd.util.FileUtil;
import com.ytxd.util.RedisUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.*;

/**
 * 登录相关
 * 
 * @author ytxd
 * @email 2625100545@qq.com
 * @date 2019年4月18日 下午8:15:31
 */
@Slf4j
@Controller("sysLoginController")
/*@RestController*/
@AllArgsConstructor
@Api(value = "登录接口", tags = "登录接口")
public class SysLoginController {
	@Autowired
	private SM_CodeItem_Service ServiceCode;
	@Autowired
	private SM_FunctionTree_Service ServiceFunctionTree;
	@Resource
	private CommonService service;
	@Autowired
	private Log_Login_Service ServiceLog;
	@Autowired
	private SM_Users_Service SM_Users_Service;
	@Autowired
	private SM_Route_Service SM_Route_Service;
	
//	@RequestMapping("/")
//	public R hello(){
//		return R.ok("hello welcome to use kjmis-hxs");
//	}
	
	/**
	 * 未认证
	 */
	@ApiIgnore
	@RequestMapping(value = "/sys/unauthorized", method = RequestMethod.POST)
	public R unauthorized() {
		return R.error(HttpStatus.SC_UNAUTHORIZED, "unauthorized");
	}
	@ApiIgnore
	@RequestMapping(value = "/frame")
	public String frame(HttpServletRequest request, Model model) throws Exception {
		if (request.getSession().getAttribute("sysuser") instanceof SysUser) {
			SysUser sysuser = (SysUser) request.getSession().getAttribute("sysuser");
			String systemtype = "00";
			if (request.getParameter("systemtype") != null && !request.getParameter("systemtype").equals("")) {
				systemtype = request.getParameter("systemtype");
			}
			if (StringUtils.isBlank(sysuser.getRoleId()) || "5".equals(sysuser.getRoleId())) { // 只有科研人员角色
				sysuser.setRoleType("01");// 只有科研人员角色
				model.addAttribute("systemtype", systemtype);
				model.addAttribute("sysuser", (SysUser) request.getSession().getAttribute("sysuser"));
				// model.addAttribute("onlineUserCount",OnlineUser.getInstance().getUserCount());
				return "index";
			} else { // 管理人员
				sysuser.setRoleType("02");// 管理人员
				model.addAttribute("systemtype", systemtype);
				model.addAttribute("sysuser", (SysUser) request.getSession().getAttribute("sysuser"));
				// model.addAttribute("onlineUserCount",OnlineUser.getInstance().getUserCount());
				return "frame";
			}
		} else {
			return "login";
		}
	}
	@ApiIgnore
	@AuthIgnore
	@RequestMapping(value = "/login")
	public String Login(HttpServletRequest request, Model model) throws Exception {
		if (StringUtils.isNotBlank(request.getParameter("action")) && "nologin".equals(request.getParameter("action"))) {
			// 判断是没有用户信息，在登录页面提示
			Map<String, Object> obj = new HashMap<String, Object>();
			obj.put("action", "nologin");
			model.addAttribute("login", obj);
		}
		return "login";
	}

	/**
	 * 未认证
	 * 
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@ApiIgnore
	@RequestMapping("/unauthorized")
	public String Unauthorized(HttpServletRequest request, Model model) throws Exception {
		return "login";
	}

	@AuthIgnore
	@PostMapping("/signin")
	@ResponseBody
	@ApiOperation(value = "接口说明：easyui登录接口"
	, notes = "接口说明：easyui登录接口。<br>") 
	@ApiImplicitParams({
        @ApiImplicitParam(name = "username", value = "用户名", required = true, dataType = "String",paramType="query"),
        @ApiImplicitParam(name = "userpwd", value = "用户密码", required = true, dataType = "String",paramType="query")
	})
	
	public R signin(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String username = request.getParameter("username");
		String userpwd = request.getParameter("userpwd");
		if (StringUtils.isBlank(username)) {
			return R.error("用户名不能为空！");
		}
		if (StringUtils.isBlank(userpwd)) {
			return R.error("密码不能为空！");
		}
//		//验证验证码是否正确
//		String verifyCode = request.getParameter("verifyCode");
//		HttpSession session = request.getSession();
//		String validateCode = (String) session.getAttribute("validateCode");
//		if (validateCode != null && !validateCode.equals(verifyCode.toUpperCase())) {
//			return R.error("验证码输入不正确，请重新输入！");
//		}
		
		return LoginSystem(request, response, username, userpwd, LoginType.EASYUI);
	}
	@ApiOperation(value = "接口说明：app登录接口"
			, notes = "接口说明：app登录接口。<br>") 
			@ApiImplicitParams({
		        @ApiImplicitParam(name = "username", value = "用户名", required = true, dataType = "String",paramType="query"),
		        @ApiImplicitParam(name = "userpwd", value = "用户密码", required = true, dataType = "String",paramType="query"),
			})
	@AuthIgnore
	@PostMapping("/signin_app")
	@ResponseBody
	public R signin_app(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String username = request.getParameter("username");
		String userpwd = request.getParameter("userpwd");
		if (StringUtils.isBlank(username)) {
			return R.error("用户名不能为空！");
		}
		if (StringUtils.isBlank(userpwd)) {
			return R.error("密码不能为空！");
		}
//		//验证验证码是否正确
//		String verifyCode = request.getParameter("verifyCode");
//		HttpSession session = request.getSession();
//		String validateCode = (String) session.getAttribute("validateCode");
//		if (validateCode != null && !validateCode.equals(verifyCode.toUpperCase())) {
//			return R.error("验证码输入不正确，请重新输入！");
//		}
		
		return LoginSystem(request, response, username, userpwd, LoginType.APP);
	}
	@ApiOperation(value = "接口说明：vue登录接口"
			, notes = "接口说明：vue登录接口。<br>") 
			@ApiImplicitParams({
		        @ApiImplicitParam(name = "username", value = "用户名", required = true, dataType = "String",paramType="query"),
		        @ApiImplicitParam(name = "userpwd", value = "用户密码", required = true, dataType = "String",paramType="query"),
		       // @ApiImplicitParam(name = "roletype", value = "选择角色", required = true, dataType = "String",paramType="query")
			})
	@AuthIgnore
	@PostMapping("/signin_vue")
	@ResponseBody
	public R signin_vue(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String username = request.getParameter("username");
		String userpwd = request.getParameter("userpwd");
//		String roletype = request.getParameter("roletype");
		if (StringUtils.isBlank(username)) {
			return R.error("用户名不能为空！");
		}
		if (StringUtils.isBlank(userpwd)) {
			return R.error("密码不能为空！");
		}
//		if (StringUtils.isBlank(roletype)) {
//			return R.error("选择角色不能为空！");
//		}
		//验证验证码是否正确
		String verifyCode = request.getParameter("verifyCode");
		HttpSession session = request.getSession();
		String validateCode = (String) session.getAttribute("validateCode");
//		if(!(verifyCode != null ) ||!Objects.equals(verifyCode.toUpperCase(Locale.ROOT),validateCode) || !(validateCode != null)){
//			return R.error("验证码输入不正确，请重新输入！");
//		}
		return LoginSystem(request, response, username, userpwd, LoginType.VUE);
	}
	/*
	 * 登录
	 */
	private R LoginSystem(HttpServletRequest request, HttpServletResponse response, String username, String userpwd, LoginType logintype) throws Exception {
		/*String username = request.getParameter("username");
		String userpwd = request.getParameter("userpwd");
		if (StringUtils.isBlank(username)) {
			return R.error("用户名不能为空！");
		}
		if (StringUtils.isBlank(userpwd)) {
			return R.error("密码不能为空！");
		}*/
		String LoginErrorCount = DataUtils.getConfInfo("system.LoginErrorCount");
		String ip = IPUtils.getIpAddr(request);
		if (RedisUtils.hasKey("error:" + ip + ":loginno")) {
			return R.error("登录错误次数过多，登录锁定，请过" + RedisUtils.getExpire("error:" + ip + ":loginno") / 60 + "分钟后再试。");
		}
		if (RedisUtils.hasKey("error:" + ip + ":editpwdno")) {
			return R.error("修改密码错误次数过多，你目前已被加入黑名单，请于" + RedisUtils.getExpire("error:" + ip + ":editpwdno") / 60 + "分钟后再试。");
		}

		String roletype = request.getParameter("roletype");
		if (StringUtils.isBlank(roletype)) {
			roletype = "01";
		}

		MySqlData mySqlData = new MySqlData();
		mySqlData.setTableName("SM_Users");
//		mySqlData.setJoinSql("DepartmentId", "left join (select DepartmentId DepartmentIdJoin,NodeName NodeNameJoin,OrderId OrderIdJoin from SM_Department) DepartmentId on DepartmentId.DepartmentIdJoin=SM_Users.DepartmentId");
//		mySqlData.setSelectField("*", "DepartmentId.NodeNameJoin DepartmentIdName,concat(SM_Users.UserId,'') UserId,SM_Users.LoginName,SM_Users.TrueName,SM_Users.DepartmentId,SM_Users.Degree,SM_Users.Birthday,SM_Users.Sex,SM_Users.password,SM_Users.UnitType,register_type");
		mySqlData.setFieldWhere("LoginName", username, "=");
		mySqlData.setFieldWhere("Disabled", "01", "=");
		mySqlData.setFieldWhere("UserType", "02", "!=");
		mySqlData.setFieldWhere("DepartmentId", "(SM_Users.DepartmentId in (select DepartmentId from SM_Department where Status='01' and DepartmentId is not null) or SM_Users.DepartmentId is null)", "sql");
		List<HashMap<String, Object>> list = service.getDataList(mySqlData);
		if(list == null || list.size() == 0) {
			Long errorCount = (long) 0;
			if (StringUtils.isNotBlank(LoginErrorCount)) {
				errorCount = RedisUtils.incr("error:" + ip + ":loginerror");
				if (errorCount >= Integer.parseInt(LoginErrorCount)) {
					// 登录错误20次锁定ip登录一小时
					RedisUtils.set("error:" + ip + ":loginno", ip, 60 * 60 * 2);
				}
				// 设置过期时间为12小时
				RedisUtils.expire("error:" + ip + ":loginerror", 60 * 60 * 12);
			}
			// 账号不存在，保存登录日志和返回错误。
			SysUser sysuser = new SysUser();
			sysuser.setTrueName(username);
			sysuser.setDeptName("登录失败，账号不存在。(" + userpwd + ")");
			ServiceLog.Save(request, sysuser);
			if(StringUtils.isNotBlank(LoginErrorCount)) {
				return R.error("账号不存在或者已被停用！还有" + (Integer.parseInt(LoginErrorCount) - errorCount) + "次机会。");
			} else {
				return R.error("账号不存在或者已被停用！");
			}
		}
		if (list.size() > 1) {
			// 账号重复
			SysUser sysuser = new SysUser();
			sysuser.setTrueName(username);
			sysuser.setDeptName("登录失败，登录名重复。(" + userpwd + ")");
			ServiceLog.Save(request, sysuser);
			return R.error("存在相同的登录名，请联系管理员！");
		} 
		HashMap<String, Object> mapUser = list.get(0);
		
		/*
		//账号锁定
		if(user.getStatus() == 0){
			return R.error("账号已被锁定,请联系管理员");
		}*/

		//判断是否是通过单点登录进来的，如果是，不再去验证密码，直接进行登录操作
		String userPassword = "sSo@PassWord";
		String checkpwd = null;
		if (!userpwd.equals("sSo@PassWord")) {
			// 得到用户密码，判断如果是加过密的，进行解密
			userPassword = DataUtils.getMapKeyValue(mapUser, "password");
			checkpwd = userPassword;
			if (userPassword.length() == 32) {
				userPassword = AESUtil.aes_decrypt(userPassword, "userPassword");
			}
			// 解密成明文在按照前台加密方式进行加密，然后进行对比。
			if(userpwd.length() == 32) {
				String sha = "AZ~()" + AESUtil.SHA1(userPassword);
				userPassword = AESUtil.md5s(sha);
			}
			//验证登录密码是否正确，不正确记录日志提示错误。
			if (!userpwd.equals(userPassword)) {
				Long errorCount = (long) 0;
				if (StringUtils.isNotBlank(LoginErrorCount)) {
					errorCount = RedisUtils.incr("error:" + ip + ":loginerror");
					if (errorCount >= Integer.parseInt(LoginErrorCount)) {
						// 登录错误20次锁定ip登录一小时
						RedisUtils.set("error:" + ip + ":loginno", ip, 60 * 60 * 2);
					}
					// 设置过期时间为12小时
					RedisUtils.expire("error:" + ip + ":loginerror", 60 * 60 * 12);
				}
				// 登录密码错误，保存登录日志和返回错误。
				SysUser sysuser = new SysUser();
				sysuser.setTrueName(username);
				sysuser.setDeptName("登录失败，密码错误。(" + userpwd + ")");
				ServiceLog.Save(request, sysuser);
				if (StringUtils.isNotBlank(LoginErrorCount)) {
					return R.error("账号或密码错误！还有" + (Integer.parseInt(LoginErrorCount) - errorCount) + "次机会。");
				} else {
					return R.error("账号或密码错误！");
				}
			}
		}else {
			R.error("账号或密码错误！");
		}
		// 登录成功后处理
		SysUser sysuser = new SysUser();
		sysuser.setUserId(DataUtils.getMapKeyValue(mapUser, "userid"));
		// sysuser.setLoginName(objUser.getloginname());
		sysuser.setTrueName(DataUtils.getMapKeyValue(mapUser, "truename"));
//		sysuser.setTitlegrade(DataUtils.getMapKeyValue(mapUser, "titlegrade"));
		sysuser.setBirthday(DataUtils.getMapKeyValue(mapUser, "birthday"));
		sysuser.setDeptId(DataUtils.getMapKeyValue(mapUser, "departmentid"));
		sysuser.setDeptName(DataUtils.getMapKeyValue(mapUser, "departmentidname"));
		sysuser.setSex(DataUtils.getMapKeyValue(mapUser, "sex"));
		sysuser.setTimeSet("1");
		sysuser.setUnittype(DataUtils.getMapKeyValue(mapUser,"Unittype"));
//		sysuser.setTimeSet(getTimeSet(sysuser));
		// 得到登录用户所有角色，将角色写入session。
		mySqlData = new MySqlData();
		mySqlData.setSql("select GROUP_CONCAT(distinct roleid) roleid from sm_users_role where userid='" + sysuser.getUserId() + "'");
		String strRole = DataUtils.getMapKeyValue(service.getMap(mySqlData), "roleid");
		sysuser.setRoleId(strRole);
		if (strRole == null || "5".equals(strRole)) {
			sysuser.setRoleType("01");// 只有科研人员角色
		} else {
			sysuser.setRoleType("02");// 管理人员
		}
//		if(LoginType.VUE.equals(logintype)) {
//			if("01".equals(sysuser.getRoleType()) && "02".equals(roletype)) {
//				/*roletype = "01";*/
//				return R.error("您没有管理人员权限！");
//			} 
//		}
		// 得到登录用户所有权限标识，写入session。
		mySqlData = new MySqlData();
		mySqlData.setSql("select distinct logo ");
		mySqlData.setSql("from sm_route ");
		mySqlData.setSql("where logo is not null");
		mySqlData.setSql("and id in (select routeId from sm_users_route where (userid='"+sysuser.getUserId()+"' or userid in (select roleid from sm_users_role where userid='"+sysuser.getUserId()+"'))) ");
		mySqlData.setSql("order by logo");
//		List<HashMap<String, Object>> LogoList = service.getDataList(mySqlData);
		HashMap<String, Object> mapLogo = new HashMap<>();
//		if (CollectionUtil.isNotEmpty(LogoList)) {
//			LogoList.forEach(m -> mapLogo.put(DataUtils.getMapKeyValue(m, "logo").toLowerCase(), ""));
//			sysuser.setPowerLogo(mapLogo);
//		}
		// 得到登录用户所有系统模块权限，写入session。
		String systemtype = ServiceFunctionTree.GetPowerSystemList(sysuser);
		sysuser.setSystemType(systemtype);
		
		// 保存登录日志
		ServiceLog.Save(request, sysuser);
		UserDetails userDetails = SM_Users_Service.loadUserByUsername(username);
        //生成授权对象，这里密码是不可逆的，所以随便乱输也可以
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(),
                userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
		//用户信息写入session
		//将登录类型保存到sysuser
		sysuser.setLoginType(logintype);
		//判断登录类型，进行不同处理
		if(LoginType.EASYUI.equals(logintype)){
			sysuser.setSessionId(sysuser.getUserId() + request.getSession().getId());
			request.getSession().setAttribute("sysuser", sysuser);
			request.getSession().setAttribute("truename", sysuser.getTrueName());
			return R.ok().put("truename", sysuser.getTrueName()).put("url", "main");
		}else if(LoginType.VUE.equals(logintype)){
			String token = UUID.randomUUID().toString();

//			String roleType =
//			sysuser.setUseroletype();
			//将sysuser序列化成json，保存到redis中
			String sysUserStr = DataUtils.ObjectToJson(sysuser);
			/**
			 * 判断密码是否是强密码
			 */
			String password = AESUtil.aes_decrypt(checkpwd,"userPassword");

			RedisUtils.set("token:"+token, sysUserStr,3600);
			return R.ok().put("token", token).put("truename", sysuser.getTrueName()).put("roletype", roletype)
					.put("register_type", sysuser.getRegister_type()).put("userid",sysuser.getUserId());
//			HashMap<String,Object> map = new HashMap<>();
//			map.put("token",token);
//			map.put("truename",sysuser.getTrueName());
//			map.put("roletype",roletype);
//			return R.ok().put("data", map);


		}else if(LoginType.APP.equals(logintype)){
			String token = UUID.randomUUID().toString();
			//将sysuser序列化成json，保存到redis中
			String sysUserStr = DataUtils.ObjectToJson(sysuser);
			RedisUtils.set("token:"+token, sysUserStr,3600);
			return R.ok().put("token", token).put("truename", sysuser.getTrueName()).put("userid",sysuser.getUserId());
		}
		/*// 生成 Token
		String token = JWTUtil.sign(username, userpwd, DataUtils.getMapKeyValue(mapUser, "userid"), DataUtils.getMapKeyValue(mapUser, "departmentid"));//,"121","321"
		System.err.println(JWTUtil.getExpireTime());
		response.setHeader("token", token);
		response.addDateHeader("expire", new Date().getTime()+JWTUtil.getExpireTime());
		//token end	*/
		
		return R.ok().put("truename", sysuser.getTrueName()).put("url", "main");
	}

	private String getTimeSet(SysUser sysuser) throws Exception {
		MySqlData mySqlData = new MySqlData();
		mySqlData.setTableName("sm_timeset");
		mySqlData.setFieldOrWhere("AllUsers", sysuser.getUserId(), "like", ",");
		mySqlData.setFieldOrWhere("AllUsers1", "AllUsers is null", "sql");
		mySqlData.setFieldWhere("Time", "NOW() BETWEEN StartTime AND EndTime", "sql");
		HashMap<String, Object> map = service.getMap(mySqlData);
		if (!map.isEmpty()) {
			sysuser.setTimeSetId(DataUtils.getMapKeyValue(map, "id"));
			return "1";
		} else {
			return "0";
		}
	}

	@ApiOperation(value = "接口说明：得到用户信息接口", notes = "接口说明：得到用户信息接口。<br>")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "roleType", value = "用户角色，登录时不填，返回用户所有角色列表和默认角色、默认角色对应的路由表，角色切换时需要传入要切换的角色类型，返回切换后的角色路由表", required = true, dataType = "String",paramType="query"),
	})
	@GetMapping("/getSysUser")
	@ResponseBody
	public HashMap<String, Object> getSysUser(HttpServletRequest request) throws Exception {
		SysUser sysuser = DataUtils.getSysUser(request);
		String userId = sysuser.getUserId();
		//查询用户所有的角色信息
		String roleType=request.getParameter("roleType");
		MySqlData mySqlData=new MySqlData();
		mySqlData.setSql("select roleid code,sm_users.truename name ");
		mySqlData.setSql(" from sm_users_role ");
		mySqlData.setSql(" left join (select userid,truename,OrderId from sm_users where UserType='02') sm_users on sm_users.userid=sm_users_role.roleid");
		mySqlData.setSql(" where sm_users_role.userid='"+userId+"'");
		mySqlData.setSql(" ORDER BY sm_users.OrderId asc");
		List<HashMap<String, Object>> list = service.getDataList(mySqlData);
		if(list.size()>0){
			if(StringUtils.isBlank(roleType)){
				roleType = DataUtils.getMapKeyValue(list.get(0), "code");
			}
			sysuser.setRoleType(roleType);
			sysuser.setRoles(list);
		}
		//获取角色对应的路由表
		SysUser sysUser2=new SysUser();
		sysUser2.setUserId(sysuser.getUserId());
		List<Map<String, Object>> RouteList = SM_Route_Service.GetRouteList(sysUser2);
		sysuser.setRoutes(RouteList);
		HashMap<String,Object> result = new HashMap<>();
		result.put("roleId",sysuser.getRoleId());
		result.put("roleType",sysuser.getRoleType());
		result.put("userId",sysuser.getUserId());
		result.put("routes",RouteList);
		result.put("roles",sysuser.getRoles());
		result.put("trueName",sysuser.getTrueName());
		result.put("register_type",sysuser.getRegister_type());
		return R.ok().put("data", result);
	}
	
	@ApiIgnore
	@RequestMapping("/checkSession")
	@ResponseBody
	public HashMap<String, Object> checkSession(HttpServletRequest request) {
		if (request.getSession().getAttribute("sysuser") instanceof SysUser) {
			return R.ok();
		} else {
			return R.error("timeout");
		}
	}
	@ApiIgnore
	@AuthIgnore
	@RequestMapping(value = "/signout")
	public String signout(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.removeAttribute("sysuser");
		
//		//江西现代金智CAS登录退出
//		session.invalidate();
//		//金智CAS登录退出后直接跳转到单点登录页面，应该会拦截后自动跳转到金智的登录页面，这个还没有测试。
//		return "redirect:jxxdxysso.html";
		
//		Enumeration<String> attributeNames = request.getSession().getServletContext().getAttributeNames();
//		  while(attributeNames.hasMoreElements()){
//			  System.err.println(attributeNames.nextElement().toString());
//		    }
//		System.err.println(request.getSession().getServletContext().getAttribute("userCounts"));
//		initSession(request);
		return "redirect:login.html";
	}
	@ApiIgnore
	@RequestMapping(value = "/SystemChange")
	public String SystemChange(HttpServletRequest request, Model model) throws Exception{
		//单点登录传用aes加密的username
//		String username="";
//		if(request.getParameter("username")!=null && !request.getParameter("username").equals("")){
//			AES aes = new AES();
//			username = aes.Decrypt(request.getParameter("username"),"username@rdm123");
//			if(username==null || username.equals("")){
//				return "login";
//			} else {
//				JsResult result=new JsResult();
//				if(request.getSession().getAttribute("sysuser") instanceof SysUser){
//					SysUser sysuser=(SysUser)request.getSession().getAttribute("sysuser");
//					if(sysuser.getLoginName().equals(username)){
//						result.setResultStatus("01");
//					} else {
//						result=signin(request,response,username);
//					}
//				} else {
//					result=signin(request,response,username);
//				}
//				if(!result.getResultStatus().equals("01")){
//					return "login";
//				}
//			}
//		}
		if (request.getSession().getAttribute("sysuser") instanceof SysUser) {
			SysUser sysuser = (SysUser) request.getSession().getAttribute("sysuser");
			model.addAttribute("truename", sysuser.getTrueName());
			model.addAttribute("deptname", sysuser.getDeptName());
			String systemtype = sysuser.getSystemType();
			model.addAttribute("allsystem", systemtype);
			return "SystemChange";
		} else {
			return "login";
		}
	}
	@ApiIgnore
	@RequestMapping(value = "/main")
	public String main(HttpServletRequest request, Model model) throws Exception {
		if (request.getSession().getAttribute("sysuser") instanceof SysUser) {
			SysUser sysuser = (SysUser) request.getSession().getAttribute("sysuser");
			model.addAttribute("truename", sysuser.getTrueName());
			model.addAttribute("deptname", sysuser.getDeptName());
			model.addAttribute("systemtype", sysuser.getSystemType());
			model.addAttribute("sysuser", (SysUser) request.getSession().getAttribute("sysuser"));
			// model.addAttribute("onlineUserCount",OnlineUser.getInstance().getUserCount());

			// 根据模块权限给前端推送
			StringBuilder strHtml = new StringBuilder();
			if (StringUtils.isNotBlank(sysuser.getSystemType())) {
				if (sysuser.getSystemType().indexOf("01") >= 0) {
					strHtml.append("<li class=\"hd_fr_item\">");
					strHtml.append("<i class=\"iconfont icon-60\"></i>&nbsp;<a id=\"sys01\" onclick=\"menu('01')\">业务管理</a>");
					strHtml.append("</li>");
				} 
				if(sysuser.getSystemType().indexOf("02") >= 0) {
					strHtml.append("<li class=\"hd_fr_item\">");
					strHtml.append("<i class=\"iconfont icon-76\"></i>&nbsp;<a id=\"sys02\" onclick=\"menu('02')\">评估决策</a>");
					strHtml.append("</li>");
				} 
				if(sysuser.getSystemType().indexOf("03") >= 0) {
					strHtml.append("<li class=\"hd_fr_item\">");
					strHtml.append("<i class=\"iconfont icon-28\"></i>&nbsp;<a id=\"sys03\" onclick=\"menu('03')\">统计上报</a>");
					strHtml.append("</li>");
				} 
				if(sysuser.getSystemType().indexOf("04") >= 0) {
					strHtml.append("<li class=\"hd_fr_item\">");
					strHtml.append("<i class=\"iconfont icon-7\"></i>&nbsp;<a id=\"sys04\" onclick=\"menu('04')\">保障平台</a>");
					strHtml.append("</li>");
				} 
				if(sysuser.getSystemType().indexOf("05") >= 0) {
					strHtml.append("<li class=\"hd_fr_item\">");
					strHtml.append("<i class=\"iconfont icon-70\"></i>&nbsp;<a id=\"sys05\" onclick=\"menu('05')\">二次开发</a>");
					strHtml.append("</li>");
				}
			}
			model.addAttribute("module", strHtml.toString());
			return "main";
		} else {
			return "login";
		}
	}
	@ApiIgnore
	@RequestMapping("/SystemInfo")
	@ResponseBody
	public Map<String, Object> SystemInfo(HttpServletRequest request) throws Exception {
		String systemtype = request.getParameter("systemtype");
		SM_CodeItem objCode = new SM_CodeItem();
		objCode.setcodeid("RN");
		objCode.setcode(systemtype);
		objCode = ServiceCode.GetListById(objCode);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("systemremark", objCode.getshortname());
		result.put("sysname", objCode.getdescription());
		return result;
	}
	@ApiIgnore
	@AuthIgnore
	@RequestMapping(value = "/jump")
	public String Jump(HttpServletRequest request, Model model) throws Exception {
		return "jump";
	}
	@ApiIgnore
	@AuthIgnore
	@RequestMapping(value = "/logindialog")
	public String logindialog(HttpServletRequest request, Model model) throws Exception {
		return "logindialog";
	}
	@ApiIgnore
	@AuthIgnore
	@RequestMapping(value = "/error403")
	public String error(HttpServletRequest request, Model model) throws Exception {
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("status", "403");
		map.put("tip", "权限不足");
		map.put("msg", "您没有权限访问当前页面，请联系管理员!");
		model.addAttribute(map);
		return "error";
	}
	
	@AuthIgnore
	@RequestMapping(value = "/ssoLogin")
	public String ssoLogin(HttpServletRequest request, HttpServletResponse response) throws Exception {
//		StringBuffer url = request.getRequestURL();
//		String domain = url.delete(url.length() - request.getRequestURI().length(), url.length()).append("/").toString();
//		
//		String loginName = request.getParameter("ln");
//		if(StringUtils.isNotBlank(loginName)) {
//			Map<String, Object> result = LoginSystem(request, response, loginName, "sSo@PassWord", LoginType.VUE);
//			if("200".equals(result.get("code").toString())) {
//				Cookie cookie = new Cookie("maggot_token", result.get("token").toString());
//				//设置Maximum Age
//				cookie.setMaxAge(1000);
//				//设置cookie路径为当前项目路径
//				cookie.setPath("/");
//				//添加cookie
//				response.addCookie(cookie);
//				return "redirect:" + domain;
//			} else {
//				return "redirect:" + domain; 
//			}
//		}
		
		StringBuffer url = request.getRequestURL();
		String domain = url.delete(url.length() - request.getRequestURI().length(), url.length()).append("/").toString();

		String ctoken = request.getHeader("ctoken");
		if(StringUtils.isBlank(ctoken)) {
			ctoken = request.getParameter("ctoken");
		}
		if(StringUtils.isBlank(ctoken)) {
			return "redirect:" + domain; 
		}
		String casurl = DataUtils.getConfInfo("cas.server-url") + "/security-server/getUserByCtoken";
		Map<String, Object> mapParam = new HashMap<String, Object>();
		mapParam.put("ctoken", ctoken);
		String jsonString = HttpUtil.httpGet(casurl, mapParam);
		JSONObject jsonObj = JSONObject.parseObject(jsonString);
		if("ok".equals(jsonObj.get("status"))) {
			JSONObject data = jsonObj.getJSONObject("data");
			if(data != null) {
				String loginName = data.getString("loginName");
				if(StringUtils.isNotBlank(loginName)) {
					Map<String, Object> result = LoginSystem(request, response, loginName, "sSo@PassWord", LoginType.VUE);
					if("200".equals(result.get("code").toString())) {
						Cookie cookie = new Cookie("maggot_token", result.get("token").toString());
						//设置Maximum Age
						cookie.setMaxAge(1000);
						//设置cookie路径为当前项目路径
						cookie.setPath("/");
						//添加cookie
						response.addCookie(cookie);
						return "redirect:" + domain;
					} else {
						return "redirect:" + domain; 
					}
				}
				
				
//				MySqlData mySqlData = new MySqlData();
//				mySqlData.setTableName("SM_Users");
//				mySqlData.setSelectField("*", "SM_Users.LoginName,SM_Users.TrueName,SM_Users.DepartmentId,SM_Users.password");
//				mySqlData.setFieldWhere("Disabled", "01", "=");
//				mySqlData.setFieldWhere("UserType", "02", "!=");
//				mySqlData.setFieldWhere("LoginName", loginName, "=");
//				HashMap<String, Object> map = service.getMap(mySqlData);
//				if(map != null) {
//					//模拟登录：传入登录名和固定密码，做模拟登录是不需要再去验证密码
//					String loginName = DataUtils.getMapKeyValue(map, "loginname");
//					Map<String, Object> result = LoginSystem(request, response, loginName, "sSo@PassWord", LoginType.VUE);
//					if("200".equals(result.get("code").toString())) {
//						return "redirect:" + result.get("url").toString() + ".html";
//						/*return result.get("url").toString();*/
//					} else {
//						return "redirect:" + domain; 
//					}
//				}
			}
		}

	    //没有得到工号或者工号在我们系统不存在跳转到我们登录页面
		return "redirect:" + domain; 
	}
	
	@ApiOperation(value = "接口说明：登录生成验证码" , notes = "接口说明：登录生成验证码") 
	@AuthIgnore
    @RequestMapping(value="/verifycode",method= RequestMethod.GET)
    public void verifycode(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	int width = 100;
        // 验证码图片的高度。

        int height = 40;
        // 验证码字符个数

        int codeCount = 4;
        int x = 0;
        // 字体高度
        int fontHeight;
        int codeY;
        /*char[] codeSequence = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
                'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
                'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };*/
        char[] codeSequence = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
    	 x = width / (codeCount + 1);
         fontHeight = height - 2;
         codeY = height - 4;
        // 定义图像buffer
        BufferedImage buffImg = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);
        Graphics2D g = buffImg.createGraphics();
        // 创建一个随机数生成器类
        Random random = new Random();
        // 将图像填充为白色
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);
        // 创建字体，字体的大小应该根据图片的高度来定。

        Font font = new Font("Fixedsys", Font.PLAIN, fontHeight);
        // 设置字体。

        g.setFont(font);
        // 画边框。

        g.setColor(Color.BLACK);
        g.drawRect(0, 0, width - 1, height - 1);
        // 随机产生160条干扰线，使图象中的认证码不易被其它程序探测到。

        g.setColor(Color.BLACK);
        for (int i = 0; i < 20; i++) {
            int x1 = random.nextInt(width);
            int y1 = random.nextInt(height);
            int x2 = random.nextInt(12);
            int y2 = random.nextInt(12);
            g.drawLine(x1, y1, x1 + x2, y1 + y2);
        }
        // randomCode用于保存随机产生的验证码，以便用户登录后进行验证。

        StringBuffer randomCode = new StringBuffer();
        int red = 0, green = 0, blue = 0;
        // 随机产生codeCount数字的验证码。

        for (int i = 0; i < codeCount; i++) {
            // 得到随机产生的验证码数字。

            String strRand = String.valueOf(codeSequence[random.nextInt(10)]);
            // 产生随机的颜色分量来构造颜色值，这样输出的每位数字的颜色值都将不同。

            red = random.nextInt(255);
            green = random.nextInt(255);
            blue = random.nextInt(255);
            // 用随机产生的颜色将验证码绘制到图像中。

            g.setColor(new Color(0, 0, 0));
            g.drawString(strRand, (i + 1) * x-10, codeY);
            // 将产生的四个随机数组合在一起。

            randomCode.append(strRand);
        }
        // 将四位数字的验证码保存到Session中。

        HttpSession session = req.getSession();
        session.setAttribute("validateCode", randomCode.toString());
        // 禁止图像缓存。

        resp.setHeader("Pragma", "no-cache");
        resp.setHeader("Cache-Control", "no-cache");
        resp.setDateHeader("Expires", 0);
        resp.setContentType("image/jpeg");
        // 将图像输出到Servlet输出流中。

        ServletOutputStream sos = resp.getOutputStream();
        ImageIO.setUseCache(false);
        ImageIO.write(buffImg, "jpeg", sos);
        sos.close();
    }

    /**
     *
     * @Desription TODO 修改默认密码
     * @param request
     * @return com.ytxd.common.util.R
     * @date 2023/11/27 9:46
     * @Auther TY
     */
    @AuthIgnore
	@ResponseBody
	@PostMapping("/Password_Modification")
    public R Password_Modification(HttpServletRequest request) throws Exception{
		String token = request.getHeader("token");
    	String ip = IPUtils.getIpAddr(request);
		/**
		 * 确定是否在黑名单中
		 */
		if (RedisUtils.hasKey("error:" + ip + ":editpwdno")) {
			return R.error("修改密码错误次数过多，你目前已被加入黑名单，请于" + RedisUtils.getExpire("error:" + ip + ":editpwdno") / 60 + "分钟后再试。");
		}
		/**
		 * 判断token是否为空
		 */
    	if(StringUtils.isBlank(token)){
			IncrErrorEdit(ip);
			return R.error("没有该用户信息!");
		}
		/**
		 * 判断token是否正确
		 */
		String userStr = RedisUtils.get("tmptoken:"+ip+":"+token);
    	if(StringUtils.isBlank(userStr)){
			IncrErrorEdit(ip);
			return R.error("没有该用户信息!");
		}
		/**
		 * 保证每个token只能用一次
		 */
		RedisUtils.delete("tmptoken:"+ip+":"+token);
		/**
		 * 获取用户信息
		 */
		net.sf.json.JSONObject obj = DataUtils.JSONToObject(userStr);
		SysUser sysuser = (SysUser) net.sf.json.JSONObject.toBean(obj, SysUser.class);// 将建json对象转换为SysUser对象
		MySqlData mySqlData = new MySqlData();
		mySqlData.setTableName("sm_users");
		mySqlData.setSelectField("*","password,oldpassword,loginname");
		mySqlData.setFieldWhere("userid",sysuser.getUserId(),"=");
		HashMap<String,Object> info = service.getMap(mySqlData);
		if(info != null && !info.isEmpty()){
			String password = DataUtils.getMapKeyValue(info,"password"); // 当前密码
			String oldpassword = DataUtils.getMapKeyValue(info,"oldpassword"); // 最后一次修改的密码
			String oldpwd = request.getParameter("oldpwd"); //原密码
			String newpwd  = request.getParameter("newpwd"); //新密码
			String againnewpwd  = request.getParameter("againnewpwd"); //确认密码
			String loginname  = DataUtils.getMapKeyValue(info,"loginname"); //账号
//			String sha = "AZ~()" + AESUtil.SHA1(AESUtil.aes_decrypt(password, "userPassword"));
//			password = AESUtil.md5s(sha);
			if(!Objects.equals(AESUtil.aes_decrypt(password,"userPassword"),oldpwd)){
				return R.error("原始密码不正确，请修改后重试！");
			}
			if(!Objects.equals(newpwd,againnewpwd)){
				return R.error("前后两次密码不一致，请修改后再试！");
			}
			if(Objects.equals(newpwd,AESUtil.aes_decrypt(oldpassword,"userPassword"))){
				return R.error("新密码与上一次修改的密码一样，请修改后再试！");
			}
			if(Objects.equals(newpwd,oldpwd)){
				return R.error("新密码和旧密码不能一样！");
			}
			/**
			 * 修改密码
			 */
			mySqlData = new MySqlData();
			mySqlData.setTableName("sm_users");
			mySqlData.setFieldValue("password",AESUtil.aes_encrypt(newpwd,"userPassword"));
			mySqlData.setFieldValue("oldpassword",password);
			mySqlData.setFieldValue("passwordupdatetime",DataUtils.getDate("yyyy-MM-dd HH:mm:ss"));
			mySqlData.setFieldWhere("userid",sysuser.getUserId(),"=");
			service.update(mySqlData);
			return R.ok("密码修改成功，请重新登录系统！");
		}else {
			return R.error("没有该用户信息!");
		}
	}
	/**
	 *
	 * @Desription TODO 记录错误
	 * @param ip
	 * @return void
	 * @date 2023/11/27 9:57
	 * @Auther TY
	 */
	public void IncrErrorEdit(String ip){
		String editPwd = DataUtils.getConfInfo("system.editPwd");
		Long errorCount = (long) 0;
		if (StringUtils.isNotBlank(editPwd)) {
			errorCount = RedisUtils.incr("error:" + ip + ":editerror");
			if (errorCount >= Integer.parseInt(editPwd)) {
				// 登录错误5次锁定ip登录一小时
				RedisUtils.set("error:" + ip + ":editpwdno", ip, 60 * 60 * 24);
			}
			// 设置过期时间为12小时
			RedisUtils.expire("error:" + ip + ":editerror", 60 * 60 * 48);
		}

	}
}
