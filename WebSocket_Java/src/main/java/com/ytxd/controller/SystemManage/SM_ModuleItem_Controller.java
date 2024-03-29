package com.ytxd.controller.SystemManage;

import com.ytxd.common.DataUtils;
import com.ytxd.common.exception.RRException;
import com.ytxd.common.util.R;
import com.ytxd.controller.BaseController;
import com.ytxd.model.ActionResult;
import com.ytxd.model.MySqlData;
import com.ytxd.model.SysUser;
import com.ytxd.service.CommonService;
import com.ytxd.service.ExcelOperation.ExportTable;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller("SM_ModuleItem_Controller")
@RequestMapping(value = "/SystemManage/SM_ModuleItem")
public class SM_ModuleItem_Controller extends BaseController {
    @Resource
    private CommonService service;

    @ApiOperation(value = "接口说明：移动端配置子表录入列表页面接口"
			, notes = "接口说明：移动端配置子表录入列表页面接口。<br>")
    @ApiImplicitParams({
		@ApiImplicitParam(name = "modulename", value = "传主表name的值，必填。", required = true, dataType = "String", paramType = "query"),
    })
	@GetMapping("/MyList_PC")
	@ResponseBody
	public Map<String, Object> MyList_PC(HttpServletRequest request) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("searchSchema", service.getSearchJson("SM_ModuleItem_MyList"));
		obj.put("columnSchema", service.getListJson("SM_ModuleItem_MyList"));
		SysUser sysuser = DataUtils.getSysUser(request);
		List<String> powerList = new ArrayList<String>();
		powerList.add("add");
		powerList.add("edit");
		powerList.add("delete");
		powerList.add("export");
		obj.put("powerall", powerList);
		Map<String, Object> listparam = new HashMap<String, Object>();
		listparam.put("listview", "SM_ModuleItem_MyList");
		listparam.put("modulename", request.getParameter("modulename"));
		obj.put("listparam", listparam);
		obj.put("exportname", "SM_ModuleItem_MyList");
		obj.put("importname", "SM_ModuleItem_Import");
		obj.put("idfieldname", "id");
		obj.put("sortname", "flag desc,orderid");
		obj.put("sortorder", "ASC");
		return R.ok().put("data", obj);
	}
    @ApiOperation(value = "接口说明：移动端配置子表查看列表页面接口"
			, notes = "接口说明：移动端配置子表查看列表页面接口。<br>")
    @ApiImplicitParams({
		@ApiImplicitParam(name = "modulename", value = "传主表name的值，必填。", required = true, dataType = "String", paramType = "query"),
    })
	@GetMapping("/ViewList_PC")
	@ResponseBody
	public Map<String, Object> ViewList_PC(HttpServletRequest request) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("searchSchema", service.getSearchJson("SM_ModuleItem_ViewList"));
		obj.put("columnSchema", service.getListJson("SM_ModuleItem_ViewList"));
		SysUser sysuser = DataUtils.getSysUser(request);
		List<String> powerList = new ArrayList<String>();
		powerList.add("export");
		obj.put("powerall", powerList);
		Map<String, Object> listparam = new HashMap<String, Object>();
		listparam.put("listview", "SM_ModuleItem_ViewList");
		listparam.put("modulename", request.getParameter("modulename"));
		obj.put("listparam", listparam);
		obj.put("exportname", "SM_ModuleItem_MyList");
		obj.put("idfieldname", "id");
		obj.put("sortname", "flag desc,orderid");
		obj.put("sortorder", "ASC");
		return R.ok().put("data", obj);
	}
    @ApiOperation(value = "接口说明：移动端配置子表新增页面接口"
			, notes = "接口说明：移动端配置子表新增页面接口。")
	@GetMapping("/Add_PC")
	@ResponseBody
	public Map<String, Object> Add_PC(HttpServletRequest request) throws Exception {
		SysUser sysuser = DataUtils.getSysUser(request);
		String tablename = "SM_ModuleItem";
		String viewname = "add";
		HashMap<String, Object> obj = new HashMap<String, Object>();
		obj.put("submitman", sysuser.getUserId());
		obj.put("submittime", DataUtils.getDate("yyyy-MM-dd"));
		List<HashMap<String, Object>> listTable = service.getAddJson(tablename, viewname, request, obj, sysuser);
		obj = new HashMap<String, Object>();
		obj.put("tableJson", listTable);
		return R.ok().put("data", obj);
	}
    @ApiOperation(value = "接口说明：移动端配置子表新增页面接口"
			, notes = "接口说明：移动端配置子表新增页面接口。")
    @ApiImplicitParams({
		@ApiImplicitParam(name = "id", value = "唯一标识，多个用英文逗号分隔。", required = true, dataType = "String", paramType = "query"),
    })
	@GetMapping("/Edit_PC")
	@ResponseBody
	public Map<String, Object> Edit_PC(HttpServletRequest request, Model model) throws Exception {
		String id = request.getParameter("id");
		if (StringUtils.isBlank(id)) {
			throw new RRException("主键值不能为空，请修改。");
		}
		SysUser sysuser = DataUtils.getSysUser(request);
		String tablename = "SM_ModuleItem";
		String viewname = "add";
		if (StringUtils.isNotBlank(request.getParameter("viewname"))) {
			viewname = request.getParameter("viewname");
		}
		HashMap<String, Object> obj = service.getMapByKey(tablename, id);
		List<HashMap<String, Object>> listTable = service.getEditJson(tablename, viewname, request, obj);
		obj = new HashMap<String, Object>();
		obj.put("tableJson", listTable);
		return R.ok().put("data", obj);
	}
    @ApiOperation(value = "接口说明：移动端配置子表查看页面接口"
			, notes = "接口说明：移动端配置子表查看页面接口。")
    @ApiImplicitParams({
		@ApiImplicitParam(name = "id", value = "唯一标识，多个用英文逗号分隔。", required = true, dataType = "String", paramType = "query"),
    })
	@GetMapping("/View_PC")
	@ResponseBody
	public Map<String, Object> View_PC(HttpServletRequest request, Model model) throws Exception {
		String id = request.getParameter("id");
		if (StringUtils.isBlank(id)) {
			throw new RRException("主键值不能为空，请修改。");
		}
		SysUser sysuser = DataUtils.getSysUser(request);
		String tablename = "SM_ModuleItem";
		String viewname = "add,view";
		if (StringUtils.isNotBlank(request.getParameter("viewname"))) {
			viewname = request.getParameter("viewname");
		}
		HashMap<String, Object> obj = service.getMapByKey(tablename, id);
		List<HashMap<String, Object>> listTable = service.getViewJson(tablename, viewname, request, obj);
		obj = new HashMap<String, Object>();
		obj.put("tableJson", listTable);
		return R.ok().put("data", obj);
	}
    @ApiOperation(value = "接口说明：移动端配置子表录入和查看等列表得到列表数据"
			, notes = "接口说明：移动端配置子表录入和查看等列表得到列表数据。"
			+ "使用数据库表：SM_ModuleItem")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "listview", value = "列表名称，非必填，默认录入列表", required = false, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "modulename", value = "模块名称，必填", required = false, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "page", value = "当前页数", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "rows", value = "每页条数", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "sort", value = "排序字段", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "order", value = "排序方式", required = true, dataType = "String", paramType = "query")
	})
	@PostMapping("/GetList")
	@ResponseBody
    public Map<String, Object> GetList(HttpServletRequest request) throws Exception {
        SysUser sysuser = DataUtils.getSysUser(request);
        //数据实体
        MySqlData mySqlData = new MySqlData();
        //数据库表名
        String tablename = "SM_ModuleItem";
        mySqlData.setTableName(tablename);
        //得到是哪个列表
        String listview = "SM_ModuleItem_MyList";
        if(request.getParameter("listview") != null) {
            listview = request.getParameter("listview");
        }
        //默认过滤条件
        String modulename = request.getParameter("modulename");
        if(StringUtils.isNotBlank(modulename)){
            mySqlData.setFieldWhere("modulename", modulename, "=");
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
    @ApiOperation(value = "接口说明：移动端配置子表保存数据接口"
			, notes = "接口说明：移动端配置子表保存数据接口。"
			+ "使用数据库表：SM_ModuleItem")
	@PostMapping("/Save")
	@ResponseBody
	@Transactional
    public HashMap<String, Object> Save(MultipartHttpServletRequest request) throws Exception {
        SysUser sysuser = DataUtils.getSysUser(request);
        //数据实体
        MySqlData mySqlData = new MySqlData();
        //数据库表名
        mySqlData.setTableName("SM_ModuleItem");
        //表单的视图名称默认为add，多个视图用英文对号分隔，例句：add,view,check
        String viewname = "add";
        mySqlData.setViewName(viewname);
        //保存默认值
        if(StringUtils.isNotBlank(request.getParameter("modulename"))){
			mySqlData.setFieldValue("modulename", request.getParameter("modulename"));
		} else {
			return R.error("modulename值不能为空，请修改。");
		}

        //插入和更新判断，然后执行。
        ActionResult result = new ActionResult();
        String id = request.getParameter("id");
        if(StringUtils.isBlank(id)) {
            //执行插入操作
            result = service.insert(request, mySqlData);
            //增加保存之后的处理事件，例如 将负责人添加到作者列表
        } else {
            //执行更新操作
            mySqlData.setFieldWhere("id", id, "=");
            result = service.update(request, mySqlData);
        }
        if(result.getSuccess()) {
            return R.ok().put("data", result.getData());
        } else {
            return R.error(result.getMsg());
        }
    }
    @ApiOperation(value = "接口说明：移动端配置子表删除数据接口"
			, notes = "接口说明：移动端配置子表删除数据接口。"
			+ "使用数据库表：SM_ModuleItem")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "唯一标识，多个用英文逗号分隔。", required = true, dataType = "String", paramType = "query"),
	})
	@PostMapping("/Delete")
	@ResponseBody
	@Transactional
    public HashMap<String, Object> Delete(HttpServletRequest request) throws Exception {
        //数据有效性校验
        String id = request.getParameter("id");
        if(StringUtils.isBlank(id)) {
            return R.error("主键值不能为空，请修改。");
        }

        //执行删除操作
        Integer iResult = service.delete("SM_ModuleItem", id);
        if(iResult > 0) {
            return R.ok();
        } else {
            return R.error("删除0条数据。");
        }
    }
    @ApiOperation(value = "接口说明：移动端配置子表Excel导出接口"
			, notes = "接口说明：移动端配置子表Excel导出接口。<br>"
			+ "使用位置：各列表中导出Excel按钮<br>"
			+ "逻辑说明：通过exportname和列表过滤条件，导出相应的数据到Excel中。<br>"
			+ "使用数据库表：SM_ModuleItem")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "exportname", value = "导出配置名，必填", required = true, dataType = "String", paramType = "query"),
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
}
