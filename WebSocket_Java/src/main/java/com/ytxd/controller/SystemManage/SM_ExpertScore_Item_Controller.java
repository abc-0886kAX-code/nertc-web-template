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
import com.ytxd.service.ExcelOperation.ImportExcel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.*;

/**
 *  控制层(前后台交互)
 */
@Api(value = "系统管理-专家打分表配置子表接口", tags = "系统管理-专家打分表配置子表接口")
@Controller("SM_ExpertScore_Item_Controller")
@RequestMapping(value = "/SystemManage/SM_ExpertScore_Item")
public class SM_ExpertScore_Item_Controller extends BaseController{
	@Resource
	private CommonService service;

	@ApiOperation(value = "接口说明：MyList_PC专家打分表配置子表录入列表接口"
			, notes = "接口说明：MyList_PC专家打分表配置子表录入列表接口")
	@GetMapping("/MyList_PC")
	@ResponseBody
	public Map<String, Object> MyList_PC(HttpServletRequest request) throws Exception {
		Map<String, Object> obj = new HashMap<>();
		String scoreid = request.getParameter("scoreid");
		if(StringUtils.isNotBlank(scoreid)) {
			obj.put("scoreid", scoreid);
		}
		obj.put("searchSchema", service.getSearchJson("SM_ExpertScore_Item_MyList"));
		obj.put("columnSchema", service.getListJson("SM_ExpertScore_Item_MyList"));
		SysUser sysuser = DataUtils.getSysUser(request);
		List<String> powerList = new ArrayList<String>();
		powerList.add("add");
		powerList.add("edit");
		powerList.add("delete");
		powerList.add("export");
		powerList.add("import");
		obj.put("powerall", powerList);
		Map<String,Object> listparam = new HashMap<>();
		listparam.put("listview", "SM_ExpertScore_Item_MyList");
		listparam.put("scoreid", "scoreid");
		obj.put("listparam", listparam);
		obj.put("exportname", "SM_ExpertScore_Item_MyList");
		obj.put("importname", "SM_ExpertScore_Item_Import");
		obj.put("idfieldname", "id");
		obj.put("sortname", "orderid,id");
		obj.put("sortorder", "ASC");
		return R.ok().put("data", obj);
	}
	@ApiOperation(value = "接口说明：ViewList_PC专家打分表配置子表查看列表接口"
			, notes = "接口说明：ViewList_PC专家打分表配置子表查看列表接口")
	@GetMapping("/ViewList_PC")
	@ResponseBody
	public Map<String, Object> ViewList_PC(HttpServletRequest request) throws Exception {
		Map<String, Object> obj = new HashMap<>();
		String scoreid = request.getParameter("scoreid");
		if(StringUtils.isNotBlank(scoreid)) {
			obj.put("scoreid", scoreid);
		}
		obj.put("searchSchema", service.getSearchJson("SM_ExpertScore_Item_ViewList"));
		obj.put("columnSchema", service.getListJson("SM_ExpertScore_Item_ViewList"));
		SysUser sysuser = DataUtils.getSysUser(request);
		List<String> powerList = new ArrayList<String>();
		powerList.add("export");
		obj.put("powerall", powerList);
		Map<String,Object> listparam = new HashMap<>();
		listparam.put("listview", "SM_ExpertScore_Item_ViewList");
		listparam.put("scoreid", "scoreid");
		obj.put("listparam", listparam);
		obj.put("exportname", "SM_ExpertScore_Item_MyList");
		obj.put("idfieldname", "id");
		obj.put("sortname", "orderid,id");
		obj.put("sortorder", "ASC");
		return R.ok().put("data", obj);
	}
	@ApiOperation(value = "接口说明：Add_PC专家打分表配置子表新增页面接口"
			, notes = "接口说明：Add_PC专家打分表配置子表新增页面接口")
	@GetMapping("/Add_PC")
	@ResponseBody
	public Map<String, Object> Add_PC(HttpServletRequest request) throws Exception {
		SysUser sysuser = DataUtils.getSysUser(request);
		String tablename = "SM_ExpertScore_Item";
		String viewname = "add";
		HashMap<String, Object> obj = new HashMap<String, Object>();
		obj.put("submitman", sysuser.getUserId());
		obj.put("submittime", DataUtils.getDate("yyyy-MM-dd"));
		List<HashMap<String, Object>> listTable = service.getAddJson(tablename, viewname, request, obj, sysuser);
		obj = new HashMap<String, Object>();
		obj.put("tableJson", listTable);
		if(StringUtils.isNotBlank(request.getParameter("scoreid"))){
			obj.put("scoreid", request.getParameter("scoreid"));
		}
		return R.ok().put("data", obj);
	}
	@ApiOperation(value = "接口说明：Edit_PC专家打分表配置子表修改页面接口"
			, notes = "接口说明：Edit_PC专家打分表配置子表修改页面接口")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "唯一标识,必填", required = true, dataType = "String",paramType="query"),
	})
	@GetMapping("/Edit_PC")
	@ResponseBody
	public Map<String, Object> Edit_PC(HttpServletRequest request) throws Exception {
		String id = request.getParameter("id");
		if(StringUtils.isBlank(id)) {
			throw new RRException("主键值不能为空，请修改。");
		}
		String tablename = "SM_ExpertScore_Item";
		String viewname = "add";
		if(StringUtils.isNotBlank(request.getParameter("viewname"))) {
			viewname = request.getParameter("viewname");
		}
		HashMap<String, Object> obj = service.getMapByKey(tablename, id);
		List<HashMap<String, Object>> listTable = service.getEditJson(tablename, viewname, request, obj);
		obj = new HashMap<String, Object>();
		obj.put("tableJson", listTable);
		if(StringUtils.isNotBlank(request.getParameter("scoreid"))){
			obj.put("scoreid", request.getParameter("scoreid"));
		}
		return R.ok().put("data", obj);
	}
	@ApiOperation(value = "接口说明：View_PC专家打分表配置子表查看页面接口"
			, notes = "接口说明：View_PC专家打分表配置子表查看页面接口")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "唯一标识,必填", required = true, dataType = "String",paramType="query"),
	})
	@GetMapping("/View_PC")
	@ResponseBody
	public Map<String, Object> View_PC(HttpServletRequest request) throws Exception{
		String id = request.getParameter("id");
		DataUtils.showParams(request);
		if(StringUtils.isBlank(id)) {
			throw new RRException("主键值不能为空，请修改。");
		}
		String tablename = "SM_ExpertScore_Item";
		String viewname = "add";
		if(StringUtils.isNotBlank(request.getParameter("viewname"))) {
			viewname = request.getParameter("viewname");
		}
		HashMap<String, Object> obj = service.getMapByKey(tablename, id);
		List<HashMap<String, Object>> listTable = service.getViewJson(tablename, viewname, request, obj);
		obj = new HashMap<String, Object>();
		obj.put("tableJson", listTable);
		return R.ok().put("data", obj);
	}
	@ApiOperation(value = "接口说明：GetList专家打分表配置子表获取数据列表接口"
			, notes = "接口说明：GetList专家打分表配置子表获取数据列表接口")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "listview", value = "列表名称，非必填，默认录入列表", required = false, dataType = "String", paramType = "query"),
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
		String tablename = "SM_ExpertScore_Item";
		mySqlData.setTableName(tablename);
		//得到是哪个列表
		String listview = "SM_ExpertScore_Item_MyList";
		if(request.getParameter("listview") != null) {
			listview = request.getParameter("listview");
		}
		//默认过滤条件
		if(StringUtils.isNotBlank(request.getParameter("scoreid"))){
			mySqlData.setFieldWhere("scoreid", request.getParameter("scoreid"), "=");
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
	@ApiOperation(value = "接口说明：GetListById专家打分表配置子表通过id获取单条数据接口"
			, notes = "接口说明：GetListById专家打分表配置子表通过id获取单条数据接口")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "唯一标识,必填", required = true, dataType = "String",paramType="query"),
	})
	@GetMapping("/GetListById")
	@ResponseBody
	public HashMap<String, Object> GetListById(HttpServletRequest request) throws Exception {
		String id = request.getParameter("id");
		if(StringUtils.isBlank(id)) {
			return R.error("主键值不能为空，请修改。");
		}
		String tablename = "SM_ExpertScore_Item";
		HashMap<String, Object> obj = service.getMapByKey(tablename, id);
		return R.ok(obj);
	}
	@ApiOperation(value = "接口说明：Save专家打分表配置子表保存数据接口"
			, notes = "接口说明：Save专家打分表配置子表保存数据接口")
	@PostMapping("/Save")
	@ResponseBody
	@Transactional
	public HashMap<String, Object> Save(MultipartHttpServletRequest request) throws Exception {
		SysUser sysuser = DataUtils.getSysUser(request);
		//数据实体
		MySqlData mySqlData = new MySqlData();
		//数据库表名
		mySqlData.setTableName("SM_ExpertScore_Item");
		//表单的视图名称默认为add，多个视图用英文对号分隔，例句：add,view,check
		String viewname = "add";
		if(StringUtils.isNotBlank(request.getParameter("viewname"))) {
			viewname = request.getParameter("viewname");
		}
		mySqlData.setViewName(viewname);
		//保存默认值
		if(StringUtils.isNotBlank(request.getParameter("scoreid"))){
			mySqlData.setFieldValue("scoreid", request.getParameter("scoreid"));
		} else {
			return R.error("scoreid值不能为空，请修改。");
		}
		//插入和更新判断，然后执行。
		ActionResult result = new ActionResult();
		String id = request.getParameter("id");
		// 检查分数是否超过100
		String scoreid = request.getParameter("scoreid");
		String scoremsg = checkExpertScore(scoreid,id,request.getParameter("scoresum"));
		if(StringUtils.isNotBlank(scoremsg)){
			throw new RRException(scoremsg);
		}
		if(StringUtils.isBlank(id)) {
			mySqlData.setFieldValue("FieldName",getFileName(scoreid));
			//执行插入操作
			result = service.insert(request, mySqlData);
			//增加保存之后的处理事件，例如 将负责人添加到作者列表
		} else {
			//执行更新操作
			mySqlData.setFieldWhere("id", id, "=");
			result = service.update(request, mySqlData);
			//修改保存之后的处理事件
		}
		if(result.getSuccess()) {
			return R.ok().put("data", result.getData());
		} else {
			return R.error(result.getMsg());
		}
	}
	@ApiOperation(value = "接口说明：Delete专家打分表配置子表删除数据接口"
			, notes = "接口说明：Delete专家打分表配置子表删除数据接口。")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "唯一标识,必填", required = true, dataType = "String",paramType="query"),
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
		
		//权限校验
		//执行删除操作
		Integer iResult = service.delete("SM_ExpertScore_Item", id);
		//删除成功后可以删除关联子表数据
		if(iResult > 0) {
			service.delete("SM_ExpertScore_Item", id, "scoreid");
		}
		if(iResult > 0) {
			return R.ok();
		} else {
			return R.error("删除0条数据。");
		}
	}
	
	
	@SuppressWarnings("unchecked")
	@ApiOperation(value = "接口说明：ExcelExport专家打分表配置子表Excel导出接口"
			, notes = "接口说明：ExcelExport专家打分表配置子表Excel导出接口")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "exportname", value = "导出配置名，必填", required = true, dataType = "String",paramType="query"),
	})
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
	@SuppressWarnings("null")
	@ApiOperation(value = "接口说明：ExcelImport专家打分表配置子表Excel导入接口"
			, notes = "接口说明：ExcelImport专家打分表配置子表Excel导入接口")
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
		String importname = "SM_ExpertScore_Item_Import";
		StringBuilder strAddField = new StringBuilder();
		StringBuilder strAddValue = new StringBuilder();
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

	/**
	 *
	 * @Desription TODO 判断分数是否超过100
	 * @param id
	 * @param score
	 * @return java.lang.String
	 * @date 2023/11/7 18:02
	 * @Auther TY
	 */
	public String checkExpertScore(String ScoreId,String id,String score) throws Exception{
		if(StringUtils.isBlank(score)){
			return "分数不能为空";
		}
		try {
			Double scorenum = Double.valueOf(score);
			if(scorenum <= 0 || scorenum > 100){
				return "分数必须大于0或者小于等于100！";
			}
			MySqlData mySqlData = new MySqlData();
			mySqlData.setSql("select sum(ScoreSum) ScoreSum from sm_expertscore_item where ScoreId =  ");
			mySqlData.setSqlValue(ScoreId);
			if(StringUtils.isNotBlank(id)){
				mySqlData.setSql(" and id !=");
				mySqlData.setSqlValue(id);
			}
			HashMap<String,Object> info = service.getMap(mySqlData);
			if(info != null && !info.isEmpty()){
				Double sumscore = DataUtils.getMapValueToDouble(info,"ScoreSum");
				if(sumscore + scorenum > 100){
					return "分数总和超过100，不可保存！";
				}
			}
		}catch (Exception e){
			e.printStackTrace();
			return "分数格式不正确！";
		}
		return "";
	}
	/**
	 *
	 * @Desription TODO 获取filename name
	 * @param id
	 * @return java.lang.String
	 * @date 2023/11/7 18:39
	 * @Auther TY
	 */
	public String getFileName(String id) throws Exception{
		MySqlData mySqlData = new MySqlData();
		mySqlData.setSql("select code from sm_codeitem where codeid = 'FN' and code not in (select FieldName from sm_expertscore_item where ScoreId = ");
		mySqlData.setSqlValue(id);
		mySqlData.setSql(" )order by OrderId asc limit 1");
		HashMap<String,Object> info = service.getMap(mySqlData);
		if(info != null && !info.isEmpty()){
			String code = DataUtils.getMapKeyValue(info,"code");
			return code;
		}
		return "";
	}
}
