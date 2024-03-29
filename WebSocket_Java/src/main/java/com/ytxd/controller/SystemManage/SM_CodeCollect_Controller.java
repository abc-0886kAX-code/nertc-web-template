package com.ytxd.controller.SystemManage;

import com.ytxd.common.DataUtils;
import com.ytxd.common.exception.RRException;
import com.ytxd.common.util.R;
import com.ytxd.controller.BaseController;
import com.ytxd.model.ActionResult;
import com.ytxd.model.EasyTree;
import com.ytxd.model.MySqlData;
import com.ytxd.model.SysUser;
import com.ytxd.model.SystemManage.SM_CodeCollect;
import com.ytxd.model.SystemManage.SM_CodeItem;
import com.ytxd.service.CommonService;
import com.ytxd.service.SystemManage.SM_CodeCollect_Service;
import com.ytxd.service.SystemManage.SM_CodeItem_Service;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 控制层(前后台交互)
 */
@Api(value = "系统管理-参数类接口", tags = "系统管理-参数类接口")
@Controller("sM_CodeCollect_Controller")
@RequestMapping(value = "/SystemManage/SM_CodeCollect")
public class SM_CodeCollect_Controller extends BaseController {

	@Resource
	private CommonService service;
	@Resource
	private SM_CodeCollect_Service Service;
	@Resource
	private SM_CodeItem_Service ServiceItem;

	@RequestMapping("/MyList")
	public String MyList(HttpServletRequest request, Model model) {
		return "SystemManage/SM_CodeCollect/SM_CodeCollect_MyList";
	}
	@ApiOperation(value = "接口说明：参数类录入页面接口"
			, notes = "接口说明：参数类录入页面接口。<br>"
			+ "使用位置：配置管理-参数类-录入<br>"
			+ "逻辑说明：获取参数类录入页面搜索条件和列表信息等页面元素")
	@GetMapping("/MyList_PC")
	@ResponseBody
	public Map<String, Object> MyList_PC(HttpServletRequest request, Model model) throws Exception {
		Map<String, Object> obj = new HashMap<>();
		obj.put("searchSchema", service.getSearchJson("SM_CodeCollect_MyList"));
		obj.put("columnSchema", service.getListJson("SM_CodeCollect_MyList"));
		//总体增删改权限
		SysUser sysuser = DataUtils.getSysUser(request);
		obj.put("powerall", getPower(sysuser, "SM_CodeCollect"));
		Map<String,Object> listparam = new HashMap<>();
		listparam.put("listview", "SM_CodeCollect_MyList");
		obj.put("listparam", listparam);
		obj.put("exportname", "SM_CodeCollect_MyList");
		obj.put("idfieldname", "codeid");
		obj.put("sortname", "codeid");
		obj.put("sortorder", "ASC");
		return R.ok().put("data", obj);
	}
	
	@RequestMapping("/ViewList")
	public String ViewList(HttpServletRequest request, Model model) {
		return "SystemManage/SM_CodeCollect/SM_CodeCollect_ViewList";
	}
	@ApiOperation(value = "接口说明：参数类查看页面接口"
			, notes = "接口说明：参数类查看页面接口。<br>"
			+ "使用位置：配置管理-参数类-查看<br>"
			+ "逻辑说明：获取参数类查看页面搜索条件和列表信息等页面元素")
	@GetMapping("/ViewList_PC")
	@ResponseBody
	public Map<String, Object> ViewList_PC(HttpServletRequest request, Model model) throws Exception {
		Map<String, Object> obj = new HashMap<>();
		obj.put("searchSchema", service.getSearchJson("SM_CodeCollect_ViewList"));
		obj.put("columnSchema", service.getListJson(""));
		Map<String,Object> listparam = new HashMap<>();
		listparam.put("listview", "SM_CodeCollect_ViewList");
		obj.put("listparam", listparam);
		return R.ok().put("data", obj);
	}

	@RequestMapping("/Edit")
	public String EditDialog(HttpServletRequest request, Model model) throws Exception {
		String codeid = request.getParameter("codeid");
		model.addAttribute("SM_CodeCollect", Service.GetListById(codeid));
		if (request.getParameter("action") != null) {
			model.addAttribute("action", request.getParameter("action"));
		}
		return "SystemManage/SM_CodeCollect/SM_CodeCollect_Edit";
	}
	@ApiOperation(value = "接口说明：参数类修改页面接口"
			, notes = "接口说明：参数类修改页面接口。<br>"
			+ "使用位置：配置管理-参数类-修改<br>"
			+ "逻辑说明：点击修改按钮修改配置时，调用该接口获取该配置的数据")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "codeid", value = "唯一标识,必填", required = true, dataType = "String",paramType="query"),
	})
	@GetMapping("/Edit_PC")
	@ResponseBody
	public Map<String, Object> Edit_PC(HttpServletRequest request, Model model) throws Exception {
		String codeid = request.getParameter("codeid");
		if (request.getParameter("action") != null) {
			model.addAttribute("action", request.getParameter("action"));
		}
		String tablename = "SM_CodeCollect";
		String viewname = "edit";
		if(StringUtils.isNotBlank(request.getParameter("viewname"))) {
			viewname = request.getParameter("viewname");
		}
		HashMap<String, Object> obj = service.getMapByKey(tablename, codeid, "codeid");
		List<HashMap<String, Object>> listTable = service.getEditJson(tablename, viewname, request, obj);
		obj = new HashMap<String, Object>();
		obj.put("tableJson", listTable);
		return R.ok().put("data", obj);
	}

	@RequestMapping("/Add")
	public String AddDialog(HttpServletRequest request, Model model) {
		return "SystemManage/SM_CodeCollect/SM_CodeCollect_Edit";
	}

	@ApiOperation(value = "接口说明：参数类新增页面接口"
			, notes = "接口说明：参数类新增页面接口。<br>"
			+ "使用位置：配置管理-参数类-增加<br>"
			+ "逻辑说明：点击增加按钮新增配置时，调用该接口获取一下初始化数据")
	@GetMapping("/Add_PC")
	@ResponseBody
	public Map<String, Object> Add_PC(HttpServletRequest request, Model model) throws Exception {
		SysUser sysuser = DataUtils.getSysUser(request);
		String tablename = "SM_CodeCollect";
		String viewname = "add";
		HashMap<String, Object> obj = new HashMap<>();
		List<HashMap<String, Object>> listTable = service.getAddJson(tablename, viewname, request, obj, sysuser);
		obj.put("tableJson", listTable);
		return R.ok().put("data", obj);
	}

	@RequestMapping("/View")
	public String ViewDialog(HttpServletRequest request, Model model) throws Exception {
		String codeid = request.getParameter("codeid");
		SM_CodeCollect obj = Service.GetListById(codeid);
		model.addAttribute("SM_CodeCollect", obj);
		return "SystemManage/SM_CodeCollect/SM_CodeCollect_View";
	}
	@ApiOperation(value = "接口说明：参数类查看页面接口"
			, notes = "接口说明：参数类查看页面接口。<br>"
			+ "使用位置：配置管理-参数类-查看<br>"
			+ "逻辑说明：点击配置名称查看配置基本信息，调用该接口获取该配置的数据")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "唯一标识,必填", required = true, dataType = "String",paramType="query"),
	})
	@GetMapping("/View_PC")
	@ResponseBody
	public Map<String, Object> View_PC(HttpServletRequest request, Model model) throws Exception{
		String codeid = request.getParameter("codeid");
		DataUtils.showParams(request);
		if(StringUtils.isBlank(codeid)) {
			throw new RRException("主键值不能为空，请修改。");
		}
		String tablename = "SM_CodeCollect";
		String viewname = "add";
		if(StringUtils.isNotBlank(request.getParameter("viewname"))) {
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
//	public List<SM_CodeCollect> GetList(HttpServletRequest request, SM_CodeCollect obj) throws Exception {
//		SysUser user = (SysUser) request.getSession().getAttribute("sysuser");
//		return Service.GetList(obj, user, "");
//	}

	@ApiOperation(value = "接口说明：获取数据列表"
			, notes = "接口说明：获取数据列表。<br>"
			+ "使用位置：系统中获取该配置列表的地方<br>"
			+ "逻辑说明：通过token获取用户信息，通过列表名称、审核状态等条件权进行数据过滤<br>"
			+ "使用数据库表：SM_CodeCollect")
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
		String tablename = "SM_CodeCollect";
		mySqlData.setTableName(tablename);
		//得到是哪个列表
		String listview = "SM_CodeCollect_MyList";
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
			+ "使用数据库表：SM_CodeCollect")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "唯一标识,必填", required = true, dataType = "String",paramType="query"),
	})
	@GetMapping("/GetListById")
	@ResponseBody
	public HashMap<String, Object> GetListById(HttpServletRequest request) throws Exception {
		String codeid = request.getParameter("codeid");
		String tablename = "SM_CodeCollect";
		HashMap<String, Object> obj = service.getMapByKey(tablename, codeid, "codeid");
		return R.ok(obj);
	}

	@ApiOperation(value = "接口说明：保存接口"
			, notes = "接口说明：保存接口。<br>"
			+ "使用位置：新增和修改后保存信息<br>"
			+ "逻辑说明：通过判断id是否为空判断是新增还是修改，将表单信息保存到数据表中"
			+ "使用数据库表：SM_CodeCollect")
	@PostMapping("/Save")
	@ResponseBody
	public Map<String, Object> Save(HttpSession session, HttpServletRequest request, SM_CodeCollect obj) throws Exception {
		String action = request.getParameter("action");
		SysUser user = (SysUser) request.getSession().getAttribute("sysuser");
		return R.ok().put("data", Service.Save(obj, user, action));
	}

	@ApiOperation(value = "接口说明：删除数据接口"
			, notes = "接口说明：删除数据接口。<br>"
			+ "使用位置：系统中删除记录的地方<br>"
			+ "逻辑说明：将指定id的记录删除"
			+ "使用数据库表：SM_CodeCollect")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "codeid", value = "唯一标识,必填", required = true, dataType = "String",paramType="query"),
	})
	@RequestMapping(value = "/Delete", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public Map<String, Object> Delete(HttpSession session, HttpServletRequest request) throws Exception {
		String codeid = request.getParameter("codeid");
		SM_CodeItem objItem = new SM_CodeItem();
		objItem.setcodeid(codeid);
		ServiceItem.Delete(objItem);
		return R.ok().put("data", Service.Delete(codeid));
	}

	// ***扩展***************************************************************************************
	@ApiOperation(value = "接口说明：获取树状图数据")
	@RequestMapping(value = "/GetListForTree", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public List<EasyTree> GetListForTree(HttpSession session, HttpServletRequest request, SM_CodeCollect obj) throws Exception {
		SysUser user = (SysUser) request.getSession().getAttribute("sysuser");
		List<SM_CodeCollect> list = (List<SM_CodeCollect>) Service.GetList(obj, user, "");

		List<EasyTree> TreeList = new ArrayList<EasyTree>();
		for (SM_CodeCollect i : list) {
			EasyTree objTree = new EasyTree();
			objTree.setId(i.getcodeid());
			objTree.setText(i.getcodeid() + "." + i.getdescription());
			objTree.setState("closed");
			Map<String, Object> attributes = new HashMap<String, Object>();
			attributes.put("CodeId", i.getcodeid());
			objTree.setAttributes(attributes);
			TreeList.add(objTree);
		}
		return TreeList;
	}

	@ApiOperation(value = "接口说明：获取树状图数据")
	@GetMapping("/GetListForTree_PC")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "text", value = "搜索条件", dataType = "String",paramType="query"),
			@ApiImplicitParam(name = "tablename", value = "表名，选填。", dataType = "String",paramType="query"),
			@ApiImplicitParam(name = "codeid", value = "需要显示的codeid，多个用英文逗号分隔，选填。", dataType = "String",paramType="query"),
			@ApiImplicitParam(name = "codeidnotin", value = "不需要显示的codeidnotin，多个用英文逗号分隔，选填。", dataType = "String",paramType="query"),
	})
	@ResponseBody
	public Map<String, Object> GetListForTree_PC(HttpServletRequest request) throws Exception {
		String treecodeid = "";
		if (StringUtils.isNotBlank(request.getParameter("tablename")) || StringUtils.isNotBlank(request.getParameter("codeid")) || StringUtils.isNotBlank(request.getParameter("codeidnotin"))) {
			MySqlData mySqlData = new MySqlData();
			mySqlData.setSql("select codeid ");
			mySqlData.setSql("from sm_codecollect ");
			mySqlData.setSql("where codeid not in ('UM','UP','FP') ");
			if (StringUtils.isNotBlank(request.getParameter("codeidnotin"))) {
				mySqlData.setSql("and codeid not in ('" + request.getParameter("codeidnotin").replace(" ", "").replace("'", "").replace(",", "','") + "') ");
			}
			mySqlData.setSql(" and (1=2 ");
			if (StringUtils.isNotBlank(request.getParameter("codeid"))) {
				mySqlData.setSql("or codeid in ('" + request.getParameter("codeid").replace(" ", "").replace("'", "").replace(",", "','") + "') ");
			}
			if (StringUtils.isNotBlank(request.getParameter("tablename"))) {
				mySqlData.setSql("or codeid in (select codeid from sm_builtitem where tablename='" + request.getParameter("tablename").replace(" ", "").replace("'", "") + "' and flag='01' and codeid is not null) ");
			}
			mySqlData.setSql(")");
			List<HashMap<String, Object>> list = service.getDataList(mySqlData);
			List<String> stringList = new ArrayList<>();
			for (HashMap<String, Object> map : list) {
			    stringList.add(DataUtils.getMapKeyValue(map, "codeid"));
			}
			treecodeid = String.join(",", stringList);
		}
		
		MySqlData mySqlData = new MySqlData();
		mySqlData.setSelectField("*", "codeid keyid,codeid,codeid code,concat(codeid,'.',description) description,'01' leaf");
		mySqlData.setTableName("sm_codecollect");
		if (StringUtils.isNotBlank(treecodeid)) {
			mySqlData.setFieldWhere("codeid", treecodeid, "in");
		}
		String text = request.getParameter("text");
		if (StringUtils.isNotBlank(text)) {
			text = request.getParameter("text").replace(" ", "").replace("'", "");
			mySqlData.setFieldOrWhere("codeid", text, "=");
			mySqlData.setFieldOrWhere("Description", text, "like");
			mySqlData.setFieldOrWhere("textcodeid", "codeid IN (select DISTINCT CodeId from SM_CodeItem where Description like CONCAT('%','"+text+"','%'))", "sql");
		}
		List<HashMap<String, Object>> list = service.getDataList(mySqlData);
		for (HashMap<String, Object> map : list) {
			if("01".equals(DataUtils.getMapKeyValue(map, "leaf"))) {
				map.put("leaf", false);
			} else {
				map.put("leaf", true);
			}			
		}
		
		return R.ok().put("data", list);
	}
	/*@ApiOperation(value = "接口说明：获取树状图数据")
	@GetMapping("/GetListForTree_PC")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "text", value = "搜索条件", dataType = "String",paramType="query"),
			@ApiImplicitParam(name = "tablename", value = "表名，选填。", dataType = "String",paramType="query"),
			@ApiImplicitParam(name = "codeid", value = "需要显示的codeid，多个用英文逗号分隔，选填。", dataType = "String",paramType="query"),
			@ApiImplicitParam(name = "codeidnotin", value = "不需要显示的codeidnotin，多个用英文逗号分隔，选填。", dataType = "String",paramType="query"),
	})
	@ResponseBody
	public Map<String, Object> GetListForTree_PC(HttpServletRequest request) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		if (StringUtils.isNotBlank(request.getParameter("text"))) {
			obj.put("text", request.getParameter("text"));
		}
		if (StringUtils.isNotBlank(request.getParameter("tablename"))) {
			obj.put("tablename", request.getParameter("tablename").split(","));
		}
		if (StringUtils.isNotBlank(request.getParameter("codeid"))) {
			obj.put("codeid", request.getParameter("codeid").split(","));
		}
		if (StringUtils.isNotBlank(request.getParameter("codeidnotin"))) {
			obj.put("codeidnotin", request.getParameter("codeidnotin").split(","));
		}
		// 查询 CodeCollect
		List<HashMap<String, Object>> codeList = Service.GetListForTree(obj);
		List<HashMap<String, Object>> itemList;
		if (!obj.isEmpty()) {
			StringBuilder codeIds = new StringBuilder();
			for (int i = 0; i < codeList.size(); i++) {
				HashMap<String, Object> map = codeList.get(i);
				if (i > 0) {
					codeIds.append(",");
				}
				codeIds.append(DataUtils.getMapKeyValue(map, "CodeId"));
			}
			itemList = ServiceItem.getListByCodeId(codeIds.toString().split(","));
		} else {
			itemList = ServiceItem.getListByCodeId(null);
		}
		List<EasyTree> TreeList = new ArrayList<>();
		for (HashMap<String, Object> map : codeList) {
			EasyTree objTree = new EasyTree();
			String codeId = DataUtils.getMapKeyValue(map, "codeId");
			objTree.setId(codeId);
			objTree.setText(codeId + "." + DataUtils.getMapKeyValue(map, "description"));
			objTree.setState("closed");
			Map<String, Object> attributes = new HashMap<>();
			attributes.put("CodeId", codeId);
			objTree.setAttributes(attributes);
			List<HashMap<String, Object>> collect = itemList.stream().filter(item -> codeId.equals(DataUtils.getMapKeyValue(item, "codeId"))).collect(Collectors.toList());
			objTree.setChildren(queryNode(collect, codeId));
			TreeList.add(objTree);
		}
		// 默认选中第一个
		if (TreeList.size() > 0) {
			TreeList.get(0).setChecked(true);
		}
		return R.ok().put("data", TreeList);
	}*/
	private List<EasyTree> queryNode(List<HashMap<String, Object>> dataList, String pptr){
		List<EasyTree> TreeList = new ArrayList<>();
		dataList.stream().filter(map -> pptr.equals(DataUtils.getMapKeyValue(map, "pptr"))).forEach(map -> {
			EasyTree objTree = new EasyTree();
			objTree.setId(DataUtils.getMapKeyValue(map, "code"));
			objTree.setText(DataUtils.getMapKeyValue(map, "description"));
			objTree.setState("closed");
			Map<String, Object> attributes = new HashMap<>();
			attributes.put("CodeId", DataUtils.getMapKeyValue(map, "codeId"));
			attributes.put("whereString", DataUtils.getMapKeyValue(map, "whereString"));
			objTree.setAttributes(attributes);
			objTree.setChildren(queryNode(dataList, DataUtils.getMapKeyValue(map, "code")));
			TreeList.add(objTree);
		});
		return TreeList;
	}

	@ApiOperation(value = "接口说明：获取数量")
	@GetMapping("/GetListCount")
	@ResponseBody
	public Integer GetListCount(HttpServletRequest request, SM_CodeCollect obj) throws Exception {
		return Service.GetListCount(obj);
	}
}
