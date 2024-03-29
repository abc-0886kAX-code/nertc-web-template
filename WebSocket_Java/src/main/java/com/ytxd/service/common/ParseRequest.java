package com.ytxd.service.common;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class ParseRequest {
	public Map parseRequest(HttpServletRequest request) {

		Map<String,String> map = new HashMap<String,String>();
		try {
			System.out.println("here");
			DiskFileItemFactory factory = new DiskFileItemFactory();
			// --获取文件上传核心类,解决文件名乱码/设置文件大小限制
			ServletFileUpload fileUpload = new ServletFileUpload(factory);
			fileUpload.setHeaderEncoding("utf-8");
			// --解析request
			List<FileItem> list = fileUpload.parseRequest(request);
			// --遍历list,获取FileItem进行解析
			for (FileItem item : list) {
				if (item.isFormField()) {// 普通字段项
					map.put(item.getFieldName(), item.getString("utf-8"));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		// 其实返回值可有可无，没有用，所以下面这个值可以忽略
		return map;
	}

}
