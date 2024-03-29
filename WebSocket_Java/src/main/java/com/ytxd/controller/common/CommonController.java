package com.ytxd.controller.common;

import com.ytxd.common.DataUtils;
import com.ytxd.common.annotation.AuthIgnore;
import com.ytxd.common.exception.RRException;
import com.ytxd.common.util.R;
import com.ytxd.common.xss.SQLFilter;
import com.ytxd.model.MySqlData;
import com.ytxd.model.SysUser;
import com.ytxd.service.CommonService;
import com.ytxd.service.ExcelOperation.ImportExcel;
import com.ytxd.service.common.AM_Achievement_User_Service;
import com.ytxd.util.fileUtil.UpFileUtil;
import com.ytxd.util.newDate;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.util.*;

@Controller("Common")
@RequestMapping("/Common")
@Api(value = "公用接口", tags = "公用接口")
public class CommonController {
	@Resource
	private CommonService service;
	@Resource
	private AM_Achievement_User_Service serviceUser;
	@Value("${file.uploadFolder}")
	private String uploadFolder;
	@Value("${file.onlinePreviewPath}")
	private String onlinePreviewPath;
//	@SuppressWarnings("unchecked")
//	@RequestMapping("/ExcelExport")
//	@ResponseBody
//	public Map<String, Object> ExcelExport(HttpServletRequest request) throws Exception{
////		SysUser sysuser = DataUtils.getSysUser(request);
//		
//		//得到导出配置名称
//		String exportName = request.getParameter("ExportName");
//		if(StringUtils.isBlank(exportName)) {
//			return R.error("ExportName不能为空。");
//		}
//		//导出Excel
//		ExportTable export = new ExportTable();
//		String strError = export.setExcelCollect(service.getExcelCollect(exportName));
//		if(StringUtils.isNotBlank(strError)) {
//			return R.error(strError);
//		}
//		strError = export.setExcelItem(service.getExcelItem(exportName));
//		if(StringUtils.isNotBlank(strError)) {
//			return R.error(strError);
//		}		
//		//得到要导出的数据
//		List<HashMap<String, Object>> listData = (List<HashMap<String, Object>>)GetList(request).get("rows");
//		export.setSource(listData);
//		export.setProjectPath(request.getSession().getServletContext().getRealPath(""));
//		strError = export.Export();
//		if(StringUtils.isNotBlank(strError)) {
//			return R.error(strError);
//		} else {
//			return R.ok().put("path", export.GetDownFilePath());
//		}
//	}
	@ApiOperation(value = "接口说明：Excel导入得到页面元素接口"
			, notes = "接口说明：Excel导入得到页面元素接口<br>"
			+ "使用位置：Excel导入得到页面元素接口<br>"
			+ "逻辑说明：Excel导入得到页面元素接口") 
	@ApiImplicitParams({
        @ApiImplicitParam(name = "importname", value = "导入配置名，必填", required = true, dataType = "String",paramType="query"),
        @ApiImplicitParam(name = "importurl", value = "导入的保存数据接口地址", required = false, dataType = "String",paramType="query"),
        @ApiImplicitParam(name = "type", value = "表名，例如项目Project,论文AM_Papers", required = false, dataType = "String",paramType="query"),
        @ApiImplicitParam(name = "typeid", value = "外键值，例如项目为ProjId的值", required = false, dataType = "String",paramType="query")
	})
	@GetMapping("/Import_PC")
	@ResponseBody
	public Map<String, Object> Import_PC(HttpServletRequest request, Model model) throws Exception{
		//判断必填参数是否有值，无值抛出异常，有值就给变量赋值。
		String importname = "";
		if(StringUtils.isBlank(request.getParameter("importname"))) {
			throw new RRException("importname参数必填。");
		} else {
			importname = request.getParameter("importname").replace(" ", "");
		}
		String importurl = "";
		if(StringUtils.isBlank(request.getParameter("importurl"))) {
//			throw new RRException("importurl参数必填。");
		} else {
			importurl = request.getParameter("importurl").replace(" ", "");
		}
		String datagrid = "";
		if(StringUtils.isBlank(request.getParameter("datagrid"))) {
//			throw new RRException("datagrid参数必填。");
		} else {
			datagrid = request.getParameter("datagrid").replace(" ", "");
		}
		String type = "";
		if(StringUtils.isNotBlank(request.getParameter("type"))) {
			type = SQLFilter.sqlInject(request.getParameter("type").replace(" ", ""));
		}
		String typeid = "";
		if(StringUtils.isNotBlank(request.getParameter("typeid"))) {
			typeid = SQLFilter.sqlInject(request.getParameter("typeid").replace(" ", ""));
		}
		//得到Excel配置主表信息
		HashMap<String, Object> obj = service.getMapByKey("SM_ExcelImportCollect", importname, "ImportName");
		if(obj == null) {
			throw new RRException(importname + "的Excel导入配置不存在。");
		}
		//判断模板文件是否存在
		String templatefile = DataUtils.getMapKeyValue(obj, "templatefile");
		if(StringUtils.isNotEmpty(templatefile)) {
			//得到绝对路径
			//String servletRealPath = request.getSession().getServletContext().getRealPath("/");
			String servletRealPath = uploadFolder;
			//模板文件不存在就把模板文件路径清空，这样前台在下载模板文件时会提示模板文件不存在
			if(!new File(servletRealPath + templatefile).exists()) {
				obj.put("templatefile", "");
			}
		}
		//多行文本转html查看
		obj.put("remark", DataUtils.getMapKeyValue(obj, "remark").replace("\n", "<br/>"));
		//其他赋值
		obj.put("importurl", importurl);
		obj.put("datagrid", datagrid);
		obj.put("type", type);
		obj.put("typeid", typeid);

		return R.ok().put("data", obj);
	}
	@GetMapping("/Import")
	@ResponseBody
	public R Import(HttpServletRequest request, Model model) throws Exception{
		//判断必填参数是否有值，无值抛出异常，有值就给变量赋值。
		String importname = "";
		if(StringUtils.isBlank(request.getParameter("importname"))) {
			throw new RRException("importname参数必填。");
		} else {
			importname = request.getParameter("importname").replace(" ", "");
		}
		String importurl = "";
		if(StringUtils.isBlank(request.getParameter("importurl"))) {
			throw new RRException("importurl参数必填。");
		} else {
			importurl = request.getParameter("importurl").replace(" ", "");
		}
		String datagrid = "";
		if(StringUtils.isBlank(request.getParameter("datagrid"))) {
			throw new RRException("datagrid参数必填。");
		} else {
			datagrid = request.getParameter("datagrid").replace(" ", "");
		}
		String type = "";
		if(StringUtils.isNotBlank(request.getParameter("type"))) {
			type = SQLFilter.sqlInject(request.getParameter("type").replace(" ", ""));
		}
		String typeid = "";
		if(StringUtils.isNotBlank(request.getParameter("typeid"))) {
			typeid = request.getParameter("typeid").replace(" ", "");
		}
		//得到Excel配置主表信息
		HashMap<String, Object> obj = service.getMapByKey("SM_ExcelImportCollect", importname, "ImportName");
		if(obj == null) {
			throw new RRException(importname + "的Excel导入配置不存在。");
		}
		//判断模板文件是否存在
		String templatefile = DataUtils.getMapKeyValue(obj, "templatefile");
		if(StringUtils.isNotEmpty(templatefile)) {
			//得到绝对路径
			//String servletRealPath = request.getSession().getServletContext().getRealPath("/");
			String servletRealPath = uploadFolder;
			//模板文件不存在就把模板文件路径清空，这样前台在下载模板文件时会提示模板文件不存在
			if(!new File(servletRealPath + templatefile).exists()) {
				obj.put("templatefile", "");
			}
		}
		//多行文本转html查看
		obj.put("remark", DataUtils.getMapKeyValue(obj, "remark").replace("\n", "<br/>"));
		//其他赋值
		obj.put("importurl", importurl);
		obj.put("datagrid", datagrid);
		obj.put("type", type);
		obj.put("typeid", typeid);
		return R.ok().put("data", obj);
	}
	@SuppressWarnings("null")
	@PostMapping("/ExcelImport")
	@ResponseBody
	public Map<String, Object> ExcelImport(MultipartHttpServletRequest request,HttpServletResponse response) throws Exception {
		SysUser sysuser = DataUtils.getSysUser(request);
		//文件上传
		MultipartFile requestFile = request.getFile("importfile");
		if(requestFile == null && requestFile.isEmpty()) {
			R.error("导入文件不能为空。");
		}
		String fileName =  requestFile.getOriginalFilename();
	    fileName = newDate.getDate("yyyyMMdd")+(new Random().nextInt(900000)+100000)+fileName;
	    String filePath = DataUtils.getConfInfo("file.uploadFolder")+"UpFile/";
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
		strAddField.append("submitman,submittime,");
		strAddValue.append("'" + sysuser.getUserId() + "',NOW(),");
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
	    	return R.error(strError).put("path", "UpFile/"+fileName);
	    }		
	}


	//***扩展***************************************************************************************
	//下载文件
	@PostMapping("/DownFile")
	public void DownFile(HttpServletResponse response, HttpServletRequest request) throws Exception{
		String TableName = request.getParameter("TableName");
		String FileFieldName = request.getParameter("FileFieldName");
		String IdFieldName = request.getParameter("IdFieldName");
		String id = request.getParameter("id");
		 //获得请求文件名 
		ServletContext sc = request.getSession().getServletContext(); 
		String apachePath = sc.getRealPath("/");
		//获取文件路径
		MySqlData mySqlData=new MySqlData();
		mySqlData.setTableName(TableName);
		mySqlData.setFieldWhere(IdFieldName, id, "=");
		HashMap<String, Object> obj = service.getMapByKey(mySqlData);
		String filePath=DataUtils.getMapKeyValue(obj,FileFieldName );
		String path = apachePath+filePath;
		//以下载方式打开
		String fileName=filePath.substring(21);
		File file = new File(path);
		if(file.exists()){ //如果文件存在，下载文件
	            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
	            // 实现文件下载
	            byte[] buffer = new byte[1024];
	            FileInputStream fis = null;
	            BufferedInputStream bis = null;
	            try {
	                fis = new FileInputStream(file);
	                bis = new BufferedInputStream(fis);
	                OutputStream os = response.getOutputStream();
	                int i = bis.read(buffer);
	                while (i != -1) {
	                    os.write(buffer, 0, i);
	                    i = bis.read(buffer);
	                }
	            } catch (Exception e) {
	               
	            } finally {
	                if (bis != null) {
	                    try {
	                        bis.close();
	                    } catch (IOException e) {
	                        e.printStackTrace();
	                    }
	                }
	                if (fis != null) {
	                    try {
	                        fis.close();
	                    } catch (IOException e) {
	                        e.printStackTrace();
	                    }
	                }
	            }
		}else{
			 try { //如果文件不存在，给出提示
                //设置响应的数据类型是html文本，并且告知浏览器，使用UTF-8 来编码。
                response.setContentType("text/html;charset=UTF-8");
                //1. 指定浏览器看这份数据使用的码表
                response.setHeader("Content-Type", "text/html;charset=UTF-8");
                OutputStream os = response.getOutputStream();
                os.write("文件不存在".getBytes());
                os.close();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
		}
	}
	@ApiOperation(value = "文件下载接口", notes = "文件下载接口。")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "path", value = "文件路径", required = true, dataType = "String")
	})
	@GetMapping("/download")
	public void download(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String filePath = request.getParameter("path");
		if(StringUtils.isBlank(filePath)) {
			response.sendError(404, "下载的文件不存在。");
		}
		String filePathToLower = "/" + filePath.toLowerCase().replace("\\","/");
//		if (!(filePathToLower.contains("/upfile/") || filePathToLower.contains("/wordoperation/") || filePathToLower.contains("/exceloperation/") || filePathToLower.contains("/fileupload/") || filePathToLower.contains("/databasebackup/") || filePathToLower.contains("/projectbackup/"))) {
		if (!(filePathToLower.contains("/upfile/") || filePathToLower.contains("/exceloperation/"))) {
			response.sendError(404, "您无权下载此文件！");
        }
		String servletRealPath = uploadFolder;
        File file = new File(servletRealPath + filePath);
        // 清空response 非常重要
        response.reset();
        // 设置response的Header
        response.setContentType("application/x-msdownload");
        response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(file.getName(), "UTF-8"));
        response.addHeader("Content-Length", String.valueOf(file.length()));
        
        BufferedInputStream br = new BufferedInputStream(Files.newInputStream(file.toPath()));
        byte[] buf = new byte[1024];
        int len = 0;
        OutputStream out = response.getOutputStream();
        while ((len = br.read(buf)) > 0)
            out.write(buf, 0, len);
        br.close();
        out.flush();
        out.close();
    }
	@AuthIgnore
	@ApiOperation(value = "文件下载接口", notes = "文件下载接口。")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "path", value = "文件路径", required = true, dataType = "String")
	})

	@GetMapping("/downloadInternalFile")
	public void downloadInternalFile(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String origin = request.getHeader("Origin");
		String filePath = request.getParameter("path");
		if(StringUtils.isBlank(filePath)) {
			response.sendError(404, "下载的文件不存在。");
		}
		String filePathToLower = "/" + filePath.toLowerCase().replace("\\","/");
//		if (!(filePathToLower.contains("/upfile/") || filePathToLower.contains("/wordoperation/") || filePathToLower.contains("/exceloperation/") || filePathToLower.contains("/fileupload/") || filePathToLower.contains("/databasebackup/") || filePathToLower.contains("/projectbackup/"))) {
		if (!(filePathToLower.contains("/upfile/") || filePathToLower.contains("/exceloperation/"))) {
			response.sendError(404, "您无权下载此文件！");
		}
		String servletRealPath = uploadFolder;
		File file = new File(servletRealPath + filePath);
		if (!file.exists()) {
			response.sendError(404, "下载的文件不存在。");
		}
		// 清空response 非常重要
		response.reset();
		// 设置response的Header
		response.setContentType("application/x-msdownload");
		response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(file.getName(), "UTF-8"));
		response.addHeader("Content-Length", String.valueOf(file.length()));

		BufferedInputStream br = new BufferedInputStream(Files.newInputStream(file.toPath()));
		byte[] buf = new byte[1024];
		int len = 0;
		OutputStream out = response.getOutputStream();
		while ((len = br.read(buf)) > 0)
			out.write(buf, 0, len);
		br.close();
		out.flush();
		out.close();
	}
	/*@RequestMapping("/download")
	@ResponseBody
	public Map<String, Object> download(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String filePath = request.getParameter("path");
		if(StringUtils.isBlank(filePath)) {
			response.sendError(404, "下载的文件不存在。");
            return R.error("下载的文件不存在。");
		}
		String filePathToLower = "/" + filePath.toLowerCase().replace("\\","/");
//		if (!(filePathToLower.contains("/upfile/") || filePathToLower.contains("/wordoperation/") || filePathToLower.contains("/exceloperation/") || filePathToLower.contains("/fileupload/") || filePathToLower.contains("/databasebackup/") || filePathToLower.contains("/projectbackup/"))) {
		if (!(filePathToLower.contains("/upfile/") || filePathToLower.contains("/exceloperation/"))) {
			response.sendError(404, "您无权下载此文件！");
			return R.error("您无权下载此文件！");
        }
		String servletRealPath = uploadFolder;
        File file = new File(servletRealPath + filePath);
        if (!file.exists()) {
            response.sendError(404, "下载的文件不存在。");
            return R.error("下载的文件不存在。");
        }
        BufferedInputStream br = new BufferedInputStream(new FileInputStream(file));
        byte[] buf = new byte[1024];
        int len = 0;
        
        // 清空response 非常重要
        response.reset();
        // 设置response的Header
        response.setContentType("application/x-msdownload");
//        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=" + file.getName());
        response.addHeader("Content-Length", "" + file.length());
        
        OutputStream out = response.getOutputStream();
        while ((len = br.read(buf)) > 0)
            out.write(buf, 0, len);
        br.close();
        out.flush();
        out.close();
        
        return R.ok();
    }*/
	@PostMapping("/online")
	public void online(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String filePath = request.getParameter("path");
		if(StringUtils.isBlank(filePath)) {
			response.sendError(404, "下载的文件不存在。");
            return;
		}
		String filePathToLower = "/" + filePath.toLowerCase().replace("\\","/");
//		if (!(filePathToLower.contains("/upfile/") || filePathToLower.contains("/wordoperation/") || filePathToLower.contains("/exceloperation/") || filePathToLower.contains("/fileupload/") || filePathToLower.contains("/databasebackup/") || filePathToLower.contains("/projectbackup/"))) {
		if (!(filePathToLower.contains("/upfile/") || filePathToLower.contains("/exceloperation/"))) {
			response.sendError(404, "您无权下载此文件！");
            return;
        }
		String servletRealPath = uploadFolder;
        File file = new File(servletRealPath + filePath);
        if (!file.exists()) {
            response.sendError(404, "下载的文件不存在。");
            return;
        }
        BufferedInputStream br = new BufferedInputStream(new FileInputStream(file));
        byte[] buf = new byte[1024];
        int len = 0;
        
        // 清空response 非常重要
        response.reset();
        // 设置response的Header        
        URL u = new URL("file:///" + filePath);
        response.setContentType(u.openConnection().getContentType());
        response.setHeader("Content-Disposition", "inline; filename=" + file.getName());
        
        OutputStream out = response.getOutputStream();
        while ((len = br.read(buf)) > 0)
            out.write(buf, 0, len);
        br.close();
        out.close();
    }
	
	@PostMapping("/CommonFileView")
	public String CommonFileView(HttpServletRequest request, Model model) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		String msg = "";
		String filePath = DataUtils.getDecode(request.getParameter("path"));
		if (StringUtils.isBlank(filePath)) {
			msg = "文件不存在。";
		}
		if(StringUtils.isBlank(msg)) {
			String servletRealPath = uploadFolder;
	        File file = new File(servletRealPath + filePath);
	        if (!file.exists()) {
	        	msg = "文件不存在。";
	        }
		}
		if(StringUtils.isBlank(msg)) {
			String filePathToLower = "/" + filePath.toLowerCase().replace("\\", "/");
			if (!(filePathToLower.contains("/upfile/") || filePathToLower.contains("/wordoperation/") || filePathToLower.contains("/exceloperation/") || filePathToLower.contains("/fileupload/"))) {
				msg = "您无权访问此文件。";
	        }
		}
        obj.put("msg", msg);
        obj.put("filepath", filePath);
        obj.put("viewpath", onlinePreviewPath);
        model.addAttribute("FileView", obj);
		return "common/CommonFileView";
    }
	@ApiOperation(value = "文件上传接口", notes = "文件上传接口。")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "files", value = "多文件，必填。", required = true, dataType = "MultipartFile"),
        @ApiImplicitParam(name = "uploadPath", value = "上传文件相对路径，必填。", required = true, dataType = "String", paramType = "query"),
        @ApiImplicitParam(name = "whitelist", value = "白名单，多个用英文逗号分隔，必填。", required = true, dataType = "String", paramType = "query"),
        @ApiImplicitParam(name = "blacklist", value = "黑名单，多个用英文逗号分隔，必填。", required = true, dataType = "String", paramType = "query"),
        @ApiImplicitParam(name = "size", value = "文件大小，单位MB，必填。", required = true, dataType = "String", paramType = "query")
    })
	@PostMapping("/UpFile")
	@ResponseBody
	public Map<String, Object> UpFile(MultipartHttpServletRequest request) throws Exception {
		// 主键参数判断
		String uploadPath = request.getParameter("uploadPath");
		if (StringUtils.isBlank(uploadPath)) {
			return R.error("上传文件相对路径不能为空，请修改。");
		}
		String whitelist = request.getParameter("whitelist");
		/*if (StringUtils.isBlank(whitelist)) {
			return R.error("上传文件白名单不能为空，请修改。");
		}*/
		String blacklist = request.getParameter("blacklist");
		if (StringUtils.isBlank(blacklist)) {
			blacklist = ".js,.jsp,.exe,.htm,.html,.asp,.aspx,.java,.class,.yml";
		}
		// 上传格式有效性验证
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		HashMap<String, Object> map = new HashMap<String, Object>();
		List<MultipartFile> listFile = request.getFiles("files");
    	if(!UpFileUtil.checkFileFormat(listFile, whitelist, blacklist)) {
    		if(StringUtils.isNotBlank(whitelist)) {
    			return R.error("上传文件格式错误，只能上传" + whitelist + "。");
    		} else {
    			return R.error("上传文件格式错误。");
    		}
    	}
    	// 文件上传
		for (MultipartFile requestFile : listFile) {
			// 得到上传文件名
			String fileName = requestFile.getOriginalFilename();
			// 文件上传时，Chrome和IE/Edge对于originalFilename处理不同
			// Chrome 会获取到该文件的直接文件名称，IE/Edge会获取到文件上传时完整路径/文件名					
			// Check for Unix-style path
			int unixSep = fileName.lastIndexOf('/');
			// Check for Windows-style path
			int winSep = fileName.lastIndexOf('\\');
			// Cut off at latest possible point
			int pos = (winSep > unixSep ? winSep : unixSep);
			if (pos != -1)  {
				// Any sort of path separator found...
				fileName = fileName.substring(pos + 1);
			}
			map = new HashMap<String, Object>();
			map.put("name", fileName);
			// 得到保存文件名
			fileName = newDate.getDate("yyyyMMdd") + (new Random().nextInt(900000) + 100000) + fileName;
			// 如果文件夹不存在就创建
			File file = new File(uploadFolder + uploadPath);
			if (!file.exists()) {
				file.mkdirs();
			}
			file = new File(uploadFolder + uploadPath, fileName);
			requestFile.transferTo(file);
			map.put("url", "/UpFile/" + fileName);
			map.put("id", UUID.randomUUID().toString());
			list.add(map);
		}
		return R.ok().put("data", list);
	}
	
	@ApiOperation(value = "接口说明：CommonExcelExport得到excel导出配置数据接口", notes = "接口说明：CommonExcelExport得到excel导出配置数据接口")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "exportname", value = "导出配置名，必填。", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "url", value = "导出接口地址，必填。", required = true, dataType = "String", paramType = "query") })
	@GetMapping("/CommonExcelExport")
	@ResponseBody
	public HashMap<String, Object> CommonExcelExport(HttpServletRequest request) throws Exception {
		// 参数判断
		String exportname = request.getParameter("exportname");
		if (StringUtils.isBlank(exportname)) {
			return R.error("exportname参数不能为空，请修改。");
		}
		/*String url = request.getParameter("url");
		if (StringUtils.isBlank(url)) {
			return R.error("url参数不能为空，请修改。");
		}*/
		Map<String, Object> obj = DataUtils.getRequestMap(request);
		// 得到excel导出主表数据
		obj.put("exportcollect", service.getMap("sm_excelcollect", exportname, "exportname"));
		// 得到excel导出子表数据
		MySqlData mySqlData = new MySqlData();
		mySqlData.setTableName("sm_excelitem");
		mySqlData.setFieldWhere("exportname", exportname, "=");
		mySqlData.setFieldWhere("visible", "01", "=");
		mySqlData.setSort("columnnumber");
		mySqlData.setOrder("asc");
		obj.put("exportitem", service.getDataList(mySqlData));
		return R.ok().put("data", obj);
	}

	@ApiOperation(value = "接口说明：GetExcelCollect得到excel导出配置主表数据接口", notes = "接口说明：GetExcelCollect得到excel导出配置主表数据接口")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "exportname", value = "导出配置名，必填。", required = true, dataType = "String", paramType = "query") })
	@GetMapping("/GetExcelCollect")
	@ResponseBody
	public HashMap<String, Object> GetExcelCollect(HttpServletRequest request) throws Exception {
		// 参数判断
		String exportname = request.getParameter("exportname");
		if (StringUtils.isBlank(exportname)) {
			return R.error("exportname参数不能为空，请修改。");
		}
		// 得到excel导出配置主表数据
		MySqlData mySqlData = new MySqlData();
		mySqlData.setTableName("SM_ExcelCollect");
		mySqlData.setSelectField("*", "exportname,auto,ischoice");
		mySqlData.setFieldWhere("ExportName", exportname, "=");
		HashMap<String, Object> map = service.getMap(mySqlData);
		if (map != null) {
			return R.ok().put("data", map);
		} else {
			return R.error("导出配置" + exportname + "不存在，请修改。");
		}
	}
	

}
