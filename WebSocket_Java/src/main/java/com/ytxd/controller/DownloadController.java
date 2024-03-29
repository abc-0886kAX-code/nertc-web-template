package com.ytxd.controller;

import java.io.File;
import java.io.FileInputStream;
import java.net.URLEncoder;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.UriUtils;

@Controller("DownloadController")
@RequestMapping("/Download")
public class DownloadController {
	
	@RequestMapping("/downPDF")
	public void export1(HttpServletResponse response, HttpServletRequest request) throws Exception{
		 //获得请求文件名 
		String fileName = request.getParameter("fileName");
		ServletContext sc = request.getSession().getServletContext(); 
		String apachePath = sc.getRealPath("/");
		String path = apachePath+fileName;
		//String path = request.getServletContext().getRealPath("/resource/pdf/enquiry.pdf");
		//System.out.println(path);
		//以下载方式打开
		response.setHeader("Content-Disposition", "attachment;filename="+UriUtils.encode(fileName,"UTF-8"));
		File file = new File(path);
		FileInputStream fis =  new FileInputStream(file);
		//写出
		ServletOutputStream out = response.getOutputStream();
		//定义读取缓冲区
		byte buffer[] = new byte[1024];
		//定义读取长度
		int len = 1024;
		//循环读取
		while((len = fis.read(buffer))!=-1){
			out.write(buffer,0,len);
		}
		
		//释放资源
		fis.close();
		out.flush();
		out.close();
	}
	
}
