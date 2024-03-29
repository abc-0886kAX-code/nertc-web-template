package com.ytxd.controller.SystemManage;

import com.ytxd.common.DataUtils;
import com.ytxd.common.exception.RRException;
import com.ytxd.common.util.R;
import com.ytxd.controller.BaseController;
import com.ytxd.model.ActionResult;
import com.ytxd.model.EasyTree;
import com.ytxd.model.MySqlData;
import com.ytxd.model.SysUser;
import com.ytxd.model.SystemManage.SM_CodeItem;
import com.ytxd.service.CommonService;
import com.ytxd.service.SystemManage.SM_CodeItem_Service;
import com.ytxd.util.RedisUtils;
import com.ytxd.util.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONArray;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 控制层(前后台交互)
 */
@Api(value = "系统管理-参数子项接口", tags = "系统管理-参数子项接口")
@Controller("sM_CodeItem_Controller")
@RequestMapping(value = "/SystemManage/SM_CodeItem")
public class SM_CodeItem_Controller extends BaseController {
	@Autowired
	private SM_CodeItem_Service Service;
	@Resource
	private CommonService service;
	/*@Resource
	private SM_SearchCollect_Service serviceSC;*/

	@ApiIgnore
	@RequestMapping("/MyList")
	public String MyList(HttpServletRequest request, Model model) {
		return "SystemManage/SM_CodeItem/SM_CodeItem_MyList";
	}

	@ApiOperation(value = "接口说明：参数子项录入页面接口"
			, notes = "接口说明：参数子项录入页面接口。<br>"
			+ "使用位置：配置管理-参数子项-录入<br>"
			+ "逻辑说明：获取参数子项录入页面搜索条件和列表信息等页面元素")
	@GetMapping("/MyList_PC")
	@ResponseBody
	public Map<String, Object> MyList_PC(HttpServletRequest request, Model model) throws Exception {
		Map<String, Object> obj = new HashMap<>();
		obj.put("searchSchema", service.getSearchJson("SM_CodeItem_MyList"));
		obj.put("columnSchema", service.getListJson("SM_CodeItem_MyList"));
		//总体增删改权限
		SysUser sysuser = DataUtils.getSysUser(request);
		obj.put("powerall", getPower(sysuser, "SM_CodeItem"));
		Map<String,Object> listparam = new HashMap<>();
		listparam.put("listview", "SM_CodeItem_MyList");
		obj.put("listparam", listparam);
		obj.put("exportname", "SM_BuiltItem_MyList");
		obj.put("importname", "SM_BuiltItem_Import");
		obj.put("idfieldname", "code");
		obj.put("sortname", "orderid");
		obj.put("sortorder", "ASC");
		return R.ok().put("data", obj);
	}



	@ApiOperation(value = "接口说明：参数子项查看页面接口"
			, notes = "接口说明：参数子项查看页面接口。<br>"
			+ "使用位置：配置管理-参数子项-查看<br>"
			+ "逻辑说明：获取参数子项查看页面搜索条件和列表信息等页面元素")
	@GetMapping("/ViewList_PC")
	@ResponseBody
	public Map<String, Object> ViewList_PC(HttpServletRequest request, Model model) throws Exception {
		Map<String, Object> obj = new HashMap<>();
		obj.put("searchSchema", service.getSearchJson("SM_CodeItem_ViewList"));
		obj.put("columnSchema", service.getListJson(""));
		Map<String,Object> listparam = new HashMap<>();
		listparam.put("listview", "SM_CodeItem_ViewList");
		obj.put("listparam", listparam);
		return R.ok().put("data", obj);
	}


	@ApiOperation(value = "接口说明：参数子项修改页面接口"
			, notes = "接口说明：参数子项修改页面接口。<br>"
			+ "使用位置：配置管理-参数子项-修改<br>"
			+ "逻辑说明：点击修改按钮修改配置时，调用该接口获取该配置的数据")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "唯一标识,必填", required = true, dataType = "String",paramType="query"),
	})
	@GetMapping("/Edit_PC")
	@ResponseBody
	public Map<String, Object> Edit_PC(HttpServletRequest request, Model model) throws Exception {
		String codeid = request.getParameter("codeid");
		String code = request.getParameter("code");
		if (request.getParameter("action") != null) {
			model.addAttribute("action", request.getParameter("action"));
		}
		String tablename = "SM_CodeItem";
		String viewname = "add";
		if(org.apache.commons.lang.StringUtils.isNotBlank(request.getParameter("viewname"))) {
			viewname = request.getParameter("viewname");
		}
		MySqlData mySqlData = new MySqlData();
		mySqlData.setTableName(tablename);
		mySqlData.setFieldWhere("codeid", codeid, "=");
		mySqlData.setFieldWhere("code", code, "=");
		HashMap<String, Object> obj = service.getMap(mySqlData);
		List<HashMap<String, Object>> listTable = service.getEditJson(tablename, viewname, request, obj);
		obj = new HashMap<String, Object>();
		obj.put("tableJson", listTable);
		return R.ok().put("data", obj);
	}


	@ApiOperation(value = "接口说明：参数子项新增页面接口"
			, notes = "接口说明：参数子项新增页面接口。<br>"
			+ "使用位置：配置管理-参数子项-增加<br>"
			+ "逻辑说明：点击增加按钮新增配置时，调用该接口获取一下初始化数据")
	@GetMapping("/Add_PC")
	@ResponseBody
	public Map<String, Object> Add_PC(HttpServletRequest request, Model model) throws Exception {
		SysUser sysuser = DataUtils.getSysUser(request);
		String tablename = "SM_CodeItem";
		String viewname = "add";
		HashMap<String, Object> obj = new HashMap<>();
		String codeid = request.getParameter("codeid");
		String pptr = request.getParameter("pptr");
		MySqlData mySqlData = new MySqlData();
		mySqlData.setSql("select ifnull(MAX(ORDERID),0)+5 OrderId from sm_codeitem where codeid = ");
		mySqlData.setSqlValue(codeid);
		mySqlData.setSql("and pptr =");
		mySqlData.setSqlValue(pptr);
		HashMap<String, Object> map = service.getMap(mySqlData);
		obj.put("orderid", DataUtils.getMapKeyValue(map, "orderid"));
		List<HashMap<String, Object>> listTable = service.getAddJson(tablename, viewname, request, obj, sysuser);
		obj.put("tableJson", listTable);
		return R.ok().put("data", obj);
	}



	@ApiOperation(value = "接口说明：参数子项查看页面接口"
			, notes = "接口说明：参数子项查看页面接口。<br>"
			+ "使用位置：配置管理-参数子项-查看<br>"
			+ "逻辑说明：点击配置名称查看配置基本信息，调用该接口获取该配置的数据")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "唯一标识,必填", required = true, dataType = "String",paramType="query"),
	})
	@GetMapping("/View_PC")
	@ResponseBody
	public Map<String, Object> View_PC(HttpServletRequest request, Model model) throws Exception{
		String codeid = request.getParameter("codeid");
		DataUtils.showParams(request);
		if(org.apache.commons.lang.StringUtils.isBlank(codeid)) {
			throw new RRException("主键值不能为空，请修改。");
		}
		String tablename = "SM_CodeItem";
		String viewname = "add";
		if(org.apache.commons.lang.StringUtils.isNotBlank(request.getParameter("viewname"))) {
			viewname = request.getParameter("viewname");
		}
		HashMap<String, Object> obj = service.getMapByKey(tablename, codeid, "codeid");
		List<HashMap<String, Object>> listTable = service.getViewJson(tablename, viewname, request, obj);
		obj = new HashMap<String, Object>();
		obj.put("tableJson", listTable);
		return R.ok().put("data", obj);
	}

//	@RequestMapping("/GetList")
//	@ResponseBody
//	public Map<String, Object> GetList(HttpServletRequest request, SM_CodeItem obj) throws Exception {
//		SysUser user = (SysUser) request.getSession().getAttribute("sysuser");
//		return Service.GetList(obj, user, "");
//	}

	@ApiOperation(value = "接口说明：获取数据列表"
			, notes = "接口说明：获取数据列表。<br>"
			+ "使用位置：系统中获取该配置列表的地方<br>"
			+ "逻辑说明：通过token获取用户信息，通过列表名称、审核状态等条件权进行数据过滤<br>"
			+ "使用数据库表：SM_CodeItem")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "codeid", value = "codeid,必填", required = true, dataType = "String",paramType="query"),
			@ApiImplicitParam(name = "pptr", value = "pptr,必填", required = true, dataType = "String",paramType="query"),
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
		String tablename = "SM_CodeItem";
		mySqlData.setTableName(tablename);
		//得到是哪个列表
		String listview = "SM_CodeItem_MyList";
		if(request.getParameter("listview") != null) {
			listview = request.getParameter("listview");
		}
		if(StringUtils.isNotBlank(request.getParameter("codeid")) && StringUtils.isNotBlank(request.getParameter("pptr"))) {
			String codeid = request.getParameter("codeid").replace(" ", "").replace("'", "");
			mySqlData.setSelectField("keyid", "concat(codeid,'|',code) keyid");
			mySqlData.setSelectField("leaf", "case when haschildren.pid is not null then '01' else '00' end leaf");
			mySqlData.setJoinSql("leaf", "left join (select distinct codeid pcodeid,pptr pid from sm_codeitem where codeid='"+codeid+"' and flag='1') haschildren on haschildren.pcodeid=sm_codeitem.codeid and haschildren.pid=sm_codeitem.code ");
		}
		//默认过滤条件
		if(StringUtils.isNotBlank(request.getParameter("codeid"))) {
			mySqlData.setFieldWhere("codeid", request.getParameter("codeid"), "=");
		}
		if(StringUtils.isNotBlank(request.getParameter("pptr"))) {
			mySqlData.setFieldWhere("pptr", request.getParameter("pptr"), "=");
		}
		//查询配置名称，就是列表上头的查询配置名称
		String searchPageName = listview;
		//权限校验
		//返回实体
		ActionResult result = service.getList(request, mySqlData, tablename, searchPageName);
		if(!result.getSuccess()) {
			return R.error(result.getMsg());
		}
		try {
			List<Map<String, Object>> listData = (List<Map<String, Object>>) result.getData();
			for (Map<String, Object> mapData : listData) {
				if ("01".equals(DataUtils.getMapKeyValue(mapData, "leaf"))) {
					mapData.put("leaf", false);
				} else {
					mapData.put("leaf", true);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return R.ok().put("data", result);
	}

	@ApiOperation(value = "接口说明：通过id获取配置基本信息"
			, notes = "接口说明：通过id获取配置基本信息。<br>"
			+ "使用位置：系统中通过id获取对应的配置基本信息的地方<br>"
			+ "逻辑说明：通过传过来的唯一标识，到数据库查出唯一的对应记录，返回到前端"
			+ "使用数据库表：SM_CodeItem")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "唯一标识,必填", required = true, dataType = "String",paramType="query"),
	})
	@GetMapping("/GetListById")
	@ResponseBody
	public SM_CodeItem GetListById(HttpServletRequest request, SM_CodeItem obj) throws Exception {
		return Service.GetListById(obj);
	}

	@ApiOperation(value = "接口说明：保存接口"
			, notes = "接口说明：保存接口。<br>"
			+ "使用位置：新增和修改后保存信息<br>"
			+ "逻辑说明：通过判断id是否为空判断是新增还是修改，将表单信息保存到数据表中"
			+ "使用数据库表：SM_CodeItem")
	@PostMapping("/Save")
	@ResponseBody
	public Map<String, Object> Save(HttpSession session, HttpServletRequest request, SM_CodeItem obj) throws Exception {
		String action = request.getParameter("action");
		SysUser user = (SysUser) request.getSession().getAttribute("sysuser");
		Integer iResult = Service.Save(obj, user, action);
		//更新redis
		UpdateRedis(request, obj.getcodeid());
		return R.ok().put("data", iResult);
	}

	@ApiOperation(value = "接口说明：删除数据接口"
			, notes = "接口说明：删除数据接口。<br>"
			+ "使用位置：系统中删除记录的地方<br>"
			+ "逻辑说明：将指定id的记录删除"
			+ "使用数据库表：SM_CodeItem")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "codeid", value = "codeid", required = true, dataType = "String",paramType="query"),
			@ApiImplicitParam(name = "code", value = "code", required = true, dataType = "String",paramType="query"),
	})
	@RequestMapping(value = "/Delete", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public Map<String, Object> Delete(HttpSession session, HttpServletRequest request, SM_CodeItem obj) throws Exception {
		Integer iResult = Service.Delete(obj);
		//更新redis
		UpdateRedis(request, obj.getcodeid());
		return R.ok().put("data", iResult);
	}

	// ***扩展***************************************************************************************
	/**
	 * 树形下拉逐级加载
	 * @param request
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value = "接口说明：树形下拉逐级加载")
	@RequestMapping(value = "/GetListForTree", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public List<EasyTree> GetListForTree(HttpServletRequest request, SM_CodeItem obj) throws Exception {
		/*if (request.getParameter("whereString") != null) {
			obj.setWhereString(request.getParameter("whereString").replace("@", "'").replace("~", "%"));
		}*/
		return Service.GetListForTree(obj);
	}

	/**
	 * 树形下拉一次全部加载
	 * @param request
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value = "接口说明：树形下拉逐级加载")
	@RequestMapping(value = "/GetTree", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public List<EasyTree> GetTree(HttpServletRequest request, SM_CodeItem obj) throws Exception {
		/*if (request.getParameter("whereString") != null) {
			obj.setWhereString(request.getParameter("whereString").replace("@", "'").replace("~", "%"));
		}*/
		return Service.GetTree(obj);
	}

	@ApiOperation(value = "接口说明：获取列表数量")
	@GetMapping("/GetListCount")
	@ResponseBody
	public Integer GetListCount(HttpServletRequest request, SM_CodeItem obj) throws Exception {
		return Service.GetListCount(obj);
	}

	/**
	 * 下拉列表
	 * @param request
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value = "接口说明：下拉列表")
	@RequestMapping(value = "/GetCode", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	/*public List<HashMap<String, Object>> GetCode(HttpServletRequest request, SM_CodeItem obj) throws Exception {
		List<HashMap<String, Object>> list = GetCodeList(request, obj);
		if(StringUtils.isBlank(request.getParameter("defaultnull")) || "true".equals(request.getParameter("defaultnull"))) {
			HashMap<String, Object> objselect = new HashMap<String, Object>();
			objselect.put("code", "");
			objselect.put("description", "请选择...");
			list.add(0, objselect);
		}
		return list;
	}*/
	public List<HashMap<String, Object>> GetCode(HttpServletRequest request, SM_CodeItem obj) throws Exception {
		//获取空 一般用于下拉级联的子节点
		if("01".equals(obj.getNothing())){
			return new ArrayList<HashMap<String, Object>>(); 
		}
				
		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		List<HashMap<String, Object>> resultList = new ArrayList<HashMap<String, Object>>();
		//判断是否有特殊过滤条件或者redis是否正常，如果有特殊过滤条件或者redis不能正常使用， 使用传统方式获取下拉框内容，否则，使用redis
		// 读取redis里是否有相应的数据，如果有，直接从redis获取，不再查询数据库
		// 前端重复拼接了一次codeid
		String codeid = obj.getcodeid();
		if (codeid == null) {
			codeid = "";
		}
		String codeids = codeid.toUpperCase();
		String result = RedisUtils.get("code:" + codeids.split(",")[0]);
		if (StringUtils.isNotBlank(result)) {
			JSONArray jsonArray = JSONArray.fromObject(result);
			list = JSONArray.toList(jsonArray, HashMap.class);
		} else {
			list = GetCodeList(request, obj.getcodeid());
			// 序列化list
			if (!obj.getcodeid().toUpperCase().startsWith("U")) {
				// U开头的为自定义code不会刷新redis，所以先不存入redis。
				String string = JSONArray.fromObject(list).toString();
				RedisUtils.set("code:" + obj.getcodeid(), string);
			}
		}
		//通过过滤条件过滤列表
		
		// 获取指定的pptr数据
		if (StringUtil.isNotEmpty(obj.getpptr())) {
			String[] pptrs = obj.getpptr().split(",");
			for (int i = 0; i < pptrs.length; i++) {
				int j = i;
				resultList.addAll(list.stream().filter(v -> v.get("pptr").equals(pptrs[j])).collect(Collectors.toList()));
			}
			list = resultList;
		}
		// 获取排除掉的pptr的数据
		if (StringUtil.isNotEmpty(obj.getPptrnotin())) {
			String[] codes = obj.getPptrnotin().split(",");
			for (int i = 0; i < codes.length; i++) {
				int j = i;
				list = list.stream().filter(v -> !v.get("pptr").equals(codes[j])).collect(Collectors.toList());
			}
		}
		// 获取指定的code的数据
		if (StringUtil.isNotEmpty(obj.getcode())) {
			resultList = new ArrayList<HashMap<String, Object>>();
			String[] codes = obj.getcode().split(",");
			for (int i = 0; i < codes.length; i++) {
				int j = i;
				resultList.addAll(list.stream().filter(v -> v.get("code").equals(codes[j])).collect(Collectors.toList()));
			}
			list = resultList;
		}
		// 获取指定的code的数据
		if (StringUtil.isNotEmpty(obj.getCodenotin())) {
			String[] codes = obj.getCodenotin().split(",");
			for (int i = 0; i < codes.length; i++) {
				int j = i;
				list = list.stream().filter(v -> !v.get("code").equals(codes[j])).collect(Collectors.toList());
			}
		}
		// 获取指定code长度的数据
		if (StringUtil.isNotEmpty(obj.getCodelength())) {
			resultList = new ArrayList<HashMap<String, Object>>();
			String[] codes = obj.getCodelength().split(",");
			for (int i = 0; i < codes.length; i++) {
				int j = i;
				resultList.addAll(list.stream().filter(v -> ((String) v.get("code")).length() == Integer.valueOf(codes[j])).collect(Collectors.toList()));
			}
			list = resultList;
		}
		// 获取以指定字符串开头的code的数据
		if (StringUtil.isNotEmpty(obj.getCodelikepre())) {
			resultList = new ArrayList<HashMap<String, Object>>();
			String[] codes = obj.getCodelikepre().split(",");
			for (int i = 0; i < codes.length; i++) {
				int j = i;
				resultList.addAll(list.stream().filter(v -> ((String) v.get("code")).startsWith(codes[j]))
						.collect(Collectors.toList()));
			}
			list = resultList;
		}
		// 获取以指定字符串结尾的code的数据
		if (StringUtil.isNotEmpty(obj.getCodelikesuf())) {
			resultList = new ArrayList<HashMap<String, Object>>();
			String[] codes = obj.getCodelikesuf().split(",");
			for (int i = 0; i < codes.length; i++) {
				int j = i;
				resultList.addAll(list.stream().filter(v -> ((String) v.get("code")).endsWith(codes[j])).collect(Collectors.toList()));
			}
			list = resultList;
		}
		// 获取指定的systemtype
		/*if (StringUtil.isNotEmpty(obj.getsystemtype())) {
			resultList = new ArrayList<HashMap<String, Object>>();
			String[] codes = obj.getsystemtype().split(",");
			for (int i = 0; i < codes.length; i++) {
				int j = i;
				resultList.addAll(list.stream().filter(v -> (codes[j].equals(v.get("systemtype")) || "".equals(v.get("systemtype")))).collect(Collectors.toList()));
			}
			list = resultList;
		}*/
		/*if (StringUtils.isBlank(request.getParameter("defaultnull")) || "true".equals(request.getParameter("defaultnull"))) {
			HashMap<String, Object> objselect = new HashMap<String, Object>();
			objselect.put("code", "");
			objselect.put("description", "请选择...");
			list.add(0, objselect);
		}*/
		String sort = request.getParameter("sort");
		String order = request.getParameter("order");
		if(StringUtils.isNotBlank(sort)){
			if(StringUtils.isNotBlank(order)&&Objects.equals("DESC",order.toUpperCase(Locale.ROOT))){
				list = list.stream().sorted(Comparator.comparing(item -> DataUtils.getMapKeyValue((Map<String, Object>) item,sort)).reversed()).collect(Collectors.toList());
			}else {
				list = list.stream().sorted(Comparator.comparing(item -> DataUtils.getMapKeyValue(item,sort))).collect(Collectors.toList());
			}
		}
		return list;
	}
	@Transactional(readOnly = true)
	private List<HashMap<String, Object>> GetCodeList(HttpServletRequest request, String codeid) throws Exception {
		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		if (codeid.toUpperCase().equals("UA")) {
			MySqlData mySqlData = new MySqlData();
			mySqlData.setSql("select userid code,truename description from sm_users ");
			mySqlData.setSql("where usertype='02' and disabled='01' order by orderid");
			
			List<HashMap<String, Object>> listUser = service.getDataList(mySqlData);
			for(Map<String, Object> mapUser : listUser) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("code", mapUser.get("code"));
				map.put("description", mapUser.get("description"));
				list.add(map);
			}
		} else if (codeid.toUpperCase().equals("UG")) {
			MySqlData mySqlData = new MySqlData();
			mySqlData.setSql("select id code,name description from project_guide ");
			mySqlData.setSql("where keytime='"+DataUtils.getDate("yyyy")+"' order by number");
			
			List<HashMap<String, Object>> listUser = service.getDataList(mySqlData);
			for(Map<String, Object> mapUser : listUser) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("code", mapUser.get("code"));
				map.put("description", mapUser.get("description"));
				list.add(map);
			}
		} else if (codeid.toUpperCase().equals("UN")) {
			MySqlData mySqlData = new MySqlData();
			mySqlData.setSql("select id code,name description from projectnotice ");
			if(StringUtils.isNotBlank(request.getParameter("projecttype"))) {
				mySqlData.setFieldWhere("projecttype", request.getParameter("projecttype"), "=");
			}
			mySqlData.setSort("year desc,id");
			
			List<HashMap<String, Object>> listUser = service.getDataList(mySqlData);
			for(Map<String, Object> mapUser : listUser) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("code", mapUser.get("code"));
				map.put("description", mapUser.get("description"));
				list.add(map);
			}
		} else {
			SM_CodeItem obj = new SM_CodeItem();
			obj.setflag("1");
			obj.setSort("OrderId");
			obj.setOrder("ASC");
			obj.setcodeid(codeid);
			list = Service.GetListAll(obj);
		}
		return list;
	}

	@ApiOperation(value = "接口说明：初始化code")
	@GetMapping("/InitCode")
	@ResponseBody
	public HashMap<String, Object> InitCode(HttpServletRequest request) throws Exception {
//		MySqlData mySqlData = new MySqlData();
//		mySqlData.setTableName("SM_CodeCollect");
//		mySqlData.setSelectField("codeid", "codeid");
//		mySqlData.setFieldWhere("all", "1=1", "sql");
//		List<HashMap<String, Object>> dataList = service.getDataList(mySqlData);
//
//		for (int i = 0; i < dataList.size(); i++) {
//			String codeid = DataUtils.getMapKeyValue(dataList.get(i), "codeid");
//			setCodeRedis(codeid);
//		}
		UpdateCode(request);
		return R.ok();
	}
	@ApiOperation(value = "接口说明：更新code")
	@GetMapping("/UpdateCode")
	@ResponseBody
	public HashMap<String, Object> UpdateCode(HttpServletRequest request) throws Exception {
		MySqlData mySqlData = new MySqlData();
		mySqlData.setTableName("SM_CodeCollect");
		mySqlData.setSelectField("codeid", "codeid");
		mySqlData.setFieldWhere("all", "1=1", "sql");
		List<HashMap<String, Object>> dataList = service.getDataList(mySqlData);

		dataList.parallelStream().forEach(map -> {
			String codeid = DataUtils.getMapKeyValue(map, "codeid");
			if(RedisUtils.hasKey("code:" + codeid.toUpperCase())){
				try {
					setCodeRedis(request, codeid);
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		});
//		for (int i = 0; i < dataList.size(); i++) {
//			String codeid = DataUtils.getMapKeyValue(dataList.get(i), "codeid");
//			if(RedisUtils.hasKey("code:" + codeid.toUpperCase())){
//				setCodeRedis(codeid);
//			}
//		}
		return R.ok();
	}
	/**
	 * 更新redis里的code
	 * @param codeid
	 * @throws Exception
	 */
	private void setCodeRedis(HttpServletRequest request, String codeid) throws Exception {
		//更新redis里的code
		List<HashMap<String, Object>> getCodeList = GetCodeList(request, codeid);
		String string = JSONArray.fromObject(getCodeList).toString();
		RedisUtils.set("code:" + codeid.toUpperCase(), string);
	}
	/**
	 * code改变后更新redis相关内容
	 * @throws Exception 
	 */
	private void UpdateRedis(HttpServletRequest request, String codeid) throws Exception {
		//更新redis里的code
		setCodeRedis(request, codeid);
		
		//更新用到这个codeid所有的搜索
		/*MySqlData mySqlData = new MySqlData();
		mySqlData.setSql("select DISTINCT SearchPageName from SM_SearchItem where CodeId=");
		mySqlData.setSqlValue(codeid);
		mySqlData.setSql(" and Visible='01' order by SearchPageName");
		List<HashMap<String, Object>> list = service.getDataList(mySqlData);
		for(Map<String, Object> map : list) {
			//成功后更新主表SearchHtml的值
			serviceSC.UpdateSearchHtml(request, DataUtils.getMapKeyValue(map, "searchpagename"));
		}*/
	}
	
	@RequestMapping("/GetListForCombobox")
	@ResponseBody
	public List<HashMap<String, Object>> GetListForCombobox(HttpServletRequest request) throws Exception {
		String codeid = request.getParameter("codeid");
		if(StringUtils.isBlank("codeid")) {
			throw new RRException("codeid参数不能为空，请修改。");
		} else {
			codeid = codeid.toUpperCase();
		}
		String code = request.getParameter("code");
		String description = request.getParameter("description");
		
		if("UJ".equals(codeid)) {
			// 期刊
			MySqlData mySqlData = new MySqlData();
			mySqlData.setSelectField("*", "id code,name description,concat('<font color=#D6D6D6>(',Library.descriptionjoin,')</font>') remark,level,type,cnn,issn,factor");
			mySqlData.setTableName("am_periodical");
			mySqlData.setJoinSql("Library", "left join (select code codejoin,Description Descriptionjoin from sm_codeitem where codeid='ZE') Library on Library.codejoin=am_periodical.Library ");
			if(StringUtils.isNotBlank(code)) {
				mySqlData.setFieldWhere("id", code, "in");
			}
			if(StringUtils.isNotBlank(description)) {
				mySqlData.setFieldWhere("name", description, "like");
			}
			mySqlData.setPage(1);
			mySqlData.setRows(100);
			mySqlData.setSort("name");
			mySqlData.setOrder("asc");
			return service.getDataList(mySqlData);
		} else {
			// 参数
			MySqlData mySqlData = new MySqlData();
			mySqlData.setSelectField("*", "code,description");
			mySqlData.setTableName("sm_codeitem");
			mySqlData.setFieldWhere("codeid", codeid, "=");
			mySqlData.setFieldWhere("Flag", "01", "=");
			if(StringUtils.isNotBlank(code)) {
				mySqlData.setFieldWhere("code", code, "in");
			}
			if(StringUtils.isNotBlank(description)) {
				mySqlData.setFieldWhere("description", description, "like");
			}
			mySqlData.setPage(1);
			mySqlData.setRows(100);
			mySqlData.setSort("orderid");
			mySqlData.setOrder("asc");
			return service.getDataList(mySqlData);
		}		
	}
}
