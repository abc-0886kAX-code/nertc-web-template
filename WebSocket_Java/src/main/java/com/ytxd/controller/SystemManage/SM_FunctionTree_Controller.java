package com.ytxd.controller.SystemManage;

import com.ytxd.common.DataUtils;
import com.ytxd.common.exception.RRException;
import com.ytxd.common.util.R;
import com.ytxd.controller.BaseController;
import com.ytxd.model.*;
import com.ytxd.model.SystemManage.SM_FunctionTree;
import com.ytxd.service.CommonService;
import com.ytxd.service.ExcelOperation.ExportTable;
import com.ytxd.service.ExcelOperation.ImportExcel;
import com.ytxd.service.SystemManage.SM_FunctionTree_Service;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * 控制层(前后台交互)
 */
@Controller("sM_FunctionTree_Controller")
@RequestMapping(value = "/SystemManage/SM_FunctionTree")
@Api(value = "系统管理-菜单管理接口", tags = "系统管理-菜单管理接口")
public class SM_FunctionTree_Controller extends BaseController {
    @Autowired
    private SM_FunctionTree_Service Service;
    @Resource
    private CommonService service;



    @ApiOperation(value = "接口说明：系统管理-菜单管理接口录入页面接口"
            , notes = "接口说明：系统管理-菜单管理接口录入页面接口。<br>"
            + "使用位置：系统管理-菜单管理接口-录入<br>"
            + "逻辑说明：获取录入页面搜索条件和列表信息等页面元素")
    @GetMapping("/MyList_PC")
    @ResponseBody
    public Map<String, Object> MyList_PC(HttpServletRequest request, Model model) throws Exception {
        Map<String, Object> obj = new HashMap<String, Object>();
        if (StringUtils.isNotBlank(request.getParameter("parentid"))) {
            obj.put("parentid", request.getParameter("parentid"));
        } else {
            obj.put("parentid", "0");
        }
        obj.put("searchSchema", service.getSearchJson("SM_FunctionTree_MyList"));
        obj.put("columnSchema", service.getListJson("SM_FunctionTree_MyList"));
        Map<String, Object> listparam = new HashMap<>();
        listparam.put("listview", "SM_FunctionTree_MyList");
        obj.put("listparam", listparam);
        obj.put("exportname", "SM_FunctionTree_MyList");
        obj.put("importname", "SM_FunctionTree_Import");
        obj.put("sortname", "orderid");
        obj.put("sortorder", "ASC");
        return R.ok().put("data", obj);
    }



    @ApiOperation(value = "接口说明：查看页面接口"
            , notes = "接口说明：查看页面接口。<br>"
            + "使用位置：系统管理-菜单管理-查看<br>"
            + "逻辑说明：获取查看页面搜索条件和列表信息等页面元素")
    @GetMapping("/ViewList_PC")
    @ResponseBody
    public Map<String, Object> ViewList_PC(HttpServletRequest request, Model model) throws Exception {
        Map<String, Object> obj = new HashMap<String, Object>();
        if (StringUtils.isNotBlank(request.getParameter("parentid"))) {
            obj.put("parentid", request.getParameter("parentid"));
        } else {
            obj.put("parentid", "0");
        }
        obj.put("searchSchema", service.getSearchJson("SM_FunctionTree_ViewList"));
        obj.put("columnSchema", service.getListJson("SM_FunctionTree_ViewList"));
        Map<String, Object> listparam = new HashMap<>();
        listparam.put("listview", "SM_FunctionTree_ViewList");
        obj.put("listparam", listparam);
        return R.ok().put("data", obj);
    }



    @ApiOperation(value = "接口说明：新增页面接口"
            , notes = "接口说明：新增页面接口。<br>"
            + "使用位置：系统管理-菜单管理接口-增加<br>"
            + "逻辑说明：点击增加按钮新增配置时，调用该接口获取一下初始化数据")
    @GetMapping("/Add_PC")
    @ResponseBody
    public Map<String, Object> Add_PC(HttpServletRequest request, Model model) throws Exception {
        SysUser sysuser = DataUtils.getSysUser(request);
        String tablename = "SM_FunctionTree";
        String viewname = "add";
        HashMap<String, Object> obj = new HashMap<String, Object>();
        if (StringUtils.isNotBlank(request.getParameter("parentid"))) {
            obj.put("parentid", request.getParameter("parentid"));
        }
        List<HashMap<String, Object>> listTable = service.getAddJson(tablename, viewname, request, obj, sysuser);
        obj.put("tableJson", listTable);
        return R.ok().put("data", obj);
    }



    @ApiOperation(value = "接口说明：修改页面接口"
            , notes = "接口说明：修改页面接口。<br>"
            + "使用位置：系统管理-菜单管理接口-修改<br>"
            + "逻辑说明：点击修改按钮修改配置时，调用该接口获取该配置的数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "唯一标识,必填", required = true, dataType = "String", paramType = "query"),
    })
    @GetMapping("/Edit_PC")
    @ResponseBody
    public Map<String, Object> Edit_PC(HttpServletRequest request, Model model) throws Exception {
        String id = request.getParameter("id");
        if (StringUtils.isBlank(id)) {
            throw new RRException("主键值不能为空，请修改。");
        }
        SysUser sysuser = DataUtils.getSysUser(request);
        String tablename = "SM_FunctionTree";
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



    @ApiOperation(value = "接口说明：查看页面接口"
            , notes = "接口说明：查看页面接口。<br>"
            + "使用位置：系统管理-菜单管理接口-查看<br>"
            + "逻辑说明：点击配置名称查看配置基本信息，调用该接口获取该配置的数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "唯一标识,必填", required = true, dataType = "String", paramType = "query"),
    })
    @GetMapping("/View_PC")
    @ResponseBody
    public Map<String, Object> View_PC(HttpServletRequest request, Model model) throws Exception {
        String id = request.getParameter("id");
        if (StringUtils.isBlank(id)) {
            throw new RRException("主键值不能为空，请修改。");
        }
        String tablename = "SM_FunctionTree";
        String viewname = "add";
        if (StringUtils.isNotBlank(request.getParameter("viewname"))) {
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
            + "使用数据库表：SM_FunctionTree")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "parentid", value = "父级id", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "disabled", value = "是否可见", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "listview", value = "列表名称，非必填，默认录入列表", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "page", value = "当前页数", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "rows", value = "每页条数", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "sort", value = "排序字段", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "order", value = "排序方式", required = true, dataType = "String", paramType = "query"),
    })
    @PostMapping("/GetList")
    @ResponseBody
    public Map<String, Object> GetList(HttpServletRequest request) throws Exception {
        SysUser sysuser = DataUtils.getSysUser(request);
        //数据实体
        MySqlData mySqlData = new MySqlData();
        //数据库表名
        String tablename = "SM_FunctionTree";
        mySqlData.setTableName(tablename);
        //得到是哪个列表
        String listview = "SM_FunctionTree_MyList";
        if (request.getParameter("listview") != null) {
            listview = request.getParameter("listview");
        }
        //默认过滤条件
        if (StringUtils.isNotBlank(request.getParameter("parentid"))) {
            mySqlData.setFieldWhere("parentid", request.getParameter("parentid"), "=");
        }
        if (StringUtils.isNotBlank(request.getParameter("disabled"))) {
            mySqlData.setFieldWhere("disabled", request.getParameter("disabled"), "=");
        }
        //查询配置名称，就是列表上头的查询配置名称
        String searchPageName = listview;
        //权限校验
        //返回实体
        ActionResult result = service.getList(request, mySqlData, tablename, searchPageName);
        if (!result.getSuccess()) {
            return R.error(result.getMsg());
        }
        return R.ok().put("data", result);
    }

    @ApiOperation(value = "接口说明：通过id获取配置基本信息"
            , notes = "接口说明：通过id获取配置基本信息。<br>"
            + "使用位置：系统中通过id获取对应的配置基本信息的地方<br>"
            + "逻辑说明：通过传过来的唯一标识，到数据库查出唯一的对应记录，返回到前端"
            + "使用数据库表：SM_FunctionTree")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "唯一标识,必填", required = true, dataType = "String", paramType = "query"),
    })
    @GetMapping("/GetListById")
    @ResponseBody
    public HashMap<String, Object> GetListById(HttpServletRequest request) throws Exception {
        String id = request.getParameter("id");
        if (StringUtils.isBlank(id)) {
            return R.error("主键值不能为空，请修改。");
        }
        String tablename = "SM_FunctionTree";
        HashMap<String, Object> obj = service.getMapByKey(tablename, id);
        return R.ok(obj);
    }

    @ApiOperation(value = "接口说明：保存接口"
            , notes = "接口说明：保存接口。<br>"
            + "使用位置：新增和修改后保存信息<br>"
            + "逻辑说明：通过判断id是否为空判断是新增还是修改，将表单信息保存到数据表中"
            + "使用数据库表：SM_FunctionTree")
    @PostMapping("/Save")
    @ResponseBody
    public HashMap<String, Object> Save(MultipartHttpServletRequest request) throws Exception {
        SysUser sysuser = DataUtils.getSysUser(request);
        //数据实体
        MySqlData mySqlData = new MySqlData();
        //数据库表名
        mySqlData.setTableName("SM_FunctionTree");
        //表单的视图名称默认为add，多个视图用英文对号分隔，例句：add,view,check
        String viewname = "add";
        mySqlData.setViewName(viewname);
        //保存默认值
        if (StringUtils.isNotBlank(request.getParameter("parentid"))) {
            mySqlData.setFieldValue("parentid", request.getParameter("parentid"));
        }

        //插入和更新判断，然后执行。
        ActionResult result = new ActionResult();
        String id = request.getParameter("id");
        if (StringUtils.isBlank(id)) {
            //执行插入操作
            result = service.insert(request, mySqlData);
            //增加保存之后的处理事件，例如 将负责人添加到作者列表
        } else {
            //执行更新操作
        	mySqlData.setFieldWhere("id", id, "=");
            result = service.update(request, mySqlData);
            //修改保存之后的处理事件，例如 将负责人添加到作者列表
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
            + "逻辑说明：将指定id的记录删除"
            + "使用数据库表：SM_FunctionTree")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", required = true, dataType = "String", paramType = "query"),
    })
    @RequestMapping(value = "/Delete", method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public HashMap<String, Object> Delete(HttpServletRequest request) throws Exception {
        //数据有效性校验
        String id = request.getParameter("id");
        if (StringUtils.isBlank(id)) {
            return R.error("主键值不能为空，请修改。");
        }

        //权限校验
        //执行删除操作
        Integer iResult = service.delete("SM_FunctionTree", id);
        //删除成功后可以删除关联子表数据
        if (iResult > 0) {
            return R.ok();
        } else {
            return R.error("删除0条数据。");
        }
    }


    @SuppressWarnings("unchecked")
    @ApiOperation(value = "接口说明：导出申报通知"
            , notes = "接口说明：导出申报通知。<br>"
            + "使用位置：导出申报通知<br>"
            + "逻辑说明：导出申报通知"
            + "使用数据库表：SM_FunctionTree")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "exportname", value = "导出配置", required = true, dataType = "String", paramType = "query"),
    })
    @PostMapping("/ExcelExport")
    @ResponseBody
    public Map<String, Object> ExcelExport(HttpServletRequest request) throws Exception {
        //得到导出配置名称
        String exportName = request.getParameter("exportname");
        if (StringUtils.isBlank(exportName)) {
            return R.error("导出配置名称不能为空，请修改。");
        }
        //导出Excel
        ExportTable export = new ExportTable();
        String strError = export.setExcelCollect(service.getExcelCollect(exportName));
        if (StringUtils.isNotBlank(strError)) {
            return R.error(strError);
        }
        strError = export.setExcelItem(service.getExcelItem(exportName));
        if (StringUtils.isNotBlank(strError)) {
            return R.error(strError);
        }
        //得到要导出的数据
        Object data = GetList(request).get("data");
        if (data != null) {
            List<HashMap<String, Object>> listData = (List<HashMap<String, Object>>)((ActionResult) data).getData();
            export.setSource(listData);
        } else {
            throw new RRException("导出数据不能为空");
        }
        export.setProjectPath(request.getSession().getServletContext().getRealPath(""));
        strError = export.Export();
        if (StringUtils.isNotBlank(strError)) {
            return R.error(strError);
        } else {
            return R.ok().put("data", export.GetDownFilePath());
        }
    }

    @SuppressWarnings("null")
    @RequestMapping("/ExcelImport")
    @ResponseBody
    public Map<String, Object> ExcelImport(MultipartHttpServletRequest request) throws Exception {
        SysUser sysuser = DataUtils.getSysUser(request);
        //文件上传
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
        //Excel导入
        String importname = "SM_FunctionTree_Import";
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


    //***扩展***************************************************************************************
    @ApiOperation(value = "接口说明：树形下拉逐级加载")
    @RequestMapping(value = "/GetListForTree", method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> dataShow(HttpServletRequest request, SM_FunctionTree obj) throws Exception {
        //展示菜单时不知道为什么id有值
        if (obj.getid() != null && obj.getid().equals(obj.getparentid())) {
            obj.setid(null);
        }
        return R.ok().put("data", Service.GetListForTree(obj));
    }
    
    @ApiIgnore
    @RequestMapping(value = "/GetListForTreeEasyUI", method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public List<EasyTree> GetListForTree(HttpServletRequest request, SM_FunctionTree obj) throws Exception{
		//展示菜单时不知道为什么id有值
		if(obj.getid()!=null && obj.getid().equals(obj.getparentid())){
			obj.setid(null);
		}
		return Service.GetListForTree(obj);
	}
    
    @RequestMapping("/GetTree")
	@ResponseBody
	public List<EasyTree> GetTree(HttpServletRequest request, SM_FunctionTree obj) throws Exception{
		//展示菜单时不知道为什么id有值
		if(obj.getid()!=null && obj.getid().equals(obj.getparentid())){
			obj.setid(null);
		}
		return Service.GetListForTree(obj);
	}

    /***
     * 获取用户对应的左侧菜单
     * @return
     * @throws Exception
     */
    @RequestMapping("/GetPowerFunctionList")
    @ResponseBody
    public List<Menu> GetPowerFunctionList(HttpServletRequest request) throws Exception {
        SysUser sysuser = DataUtils.getSysUser(request);
        String systemtype = request.getParameter("systemtype");
        return Service.GetPowerFunctionList(sysuser, systemtype);
    }
}
