package com.ytxd.controller.common;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.ytxd.common.annotation.AuthIgnore;
import com.ytxd.common.exception.RRException;
import com.ytxd.common.util.R;
import com.ytxd.service.CommonService;
import com.ytxd.util.newDate;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Controller("FileController")
@RequestMapping(value = "/File")
@Api(value = "通用接口", tags = "通用接口")
public class FileController {
	@Value("${file.uploadFolder}")
	private String sourefilePath;
	@AuthIgnore
	@ApiImplicitParams({
			@ApiImplicitParam(name = "catalogue", value = "文件需要放在子文件夹下(前后不要有斜杠)", dataType = "String", paramType = "query"),
	})
	@RequestMapping(value = "/UpFile", method = RequestMethod.POST)
	@ResponseBody
	public R UpFile(@RequestParam("files") MultipartFile [] files, HttpServletRequest request) throws IllegalStateException, IOException {
		List<String> list = new ArrayList<String>();
	    for (MultipartFile multipartFile:files) {
	        String fileName = multipartFile.getOriginalFilename();
			fileName = newDate.getDate("yyyyMM" +
					"dd") + (new Random().nextInt(900000) + 100000) + fileName;
			String catalogue = "UpFile";
			if(StringUtils.isNotEmpty(request.getParameter("catalogue"))){
				catalogue = request.getParameter("catalogue");
			}
			String path = sourefilePath.concat(File.separator).concat(catalogue);
			File file = new File(path);
			if (!file.exists()) {
				file.mkdirs();
			}
			file = new File(path, fileName);
			multipartFile.transferTo(file);
			list.add(catalogue+"/"+fileName);
	    }
	    return R.ok(list);
	}
	/**
	 * downloadFile
	 * @param request
	 * @param filePath
	 * @return
	 */
	@AuthIgnore
	@RequestMapping(value = "/downloadFile",method={RequestMethod.POST,RequestMethod.GET})
	public ResponseEntity<org.springframework.core.io.Resource> downloadFile(HttpServletRequest request, @Param("filePath") String filePath) throws Exception{
		if(StringUtils.isEmpty(filePath)){
			throw  new RRException("文件路径不能为空！！");
		}
		Path path = Paths.get(sourefilePath).resolve(filePath);
		// Create File Resources
		Resource fileResource = new FileSystemResource(path);
		if (fileResource.exists()) {
			//If the file exists, return the file download response
			return ResponseEntity.ok()
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" +  URLEncoder.encode(fileResource.getFilename(), "UTF-8") + "\"")
					.contentType(MediaType.APPLICATION_OCTET_STREAM)
					.body(fileResource);
		} else {
			//If the file does not exist, a file download failure response is returned
			throw new RRException("文件不存在！！");
		}
	}
}
