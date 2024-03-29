package com.ytxd.config;

import java.io.File;

import javax.servlet.MultipartConfigElement;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("UploadFileConfig")
public class UploadFileConfig {

    @Value("${file.uploadFolder}")
    private String uploadFolder;

	@Bean
    MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        String location = uploadFolder;//System.getProperty("file.uploadFolder");
        File tmpFile = new File(location);
        if (!tmpFile.exists()) {
            if (!tmpFile.mkdirs()) {
                System.out.println("create was not successful.");
            }
        }
        factory.setLocation(location);
//        //文件最大
//        factory.setMaxFileSize("5MB");
//        // 设置总上传数据总大小
//        factory.setMaxRequestSize("10MB");
        return factory.createMultipartConfig();
    }
}