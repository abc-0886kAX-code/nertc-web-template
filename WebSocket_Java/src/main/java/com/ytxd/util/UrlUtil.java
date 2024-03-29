package com.ytxd.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * 获取图片资源(image.properties)
 * @author heshan
 *
 */
public class UrlUtil {

    public static String urlDecode(String url) {

        if (url==null || url.equals("")) {
            return null;
        }

        if (url.indexOf("baidu.com") != -1) {
            try {
                url = URLDecoder.decode(url, "gb2312");

            } catch (UnsupportedEncodingException e) {

                e.printStackTrace();
            }
        }else if(url.indexOf("google.cn") != -1 || url.indexOf("g.cn") != -1) {
            try {
                url = URLDecoder.decode(url, "utf-8");

            } catch (UnsupportedEncodingException e) {

                e.printStackTrace();
            }
        }

        return url;
    }
    
    /**
     * 获取图片服务器地址
     * @return
     */
    public static String imageUrl(){
    	String imageUrl = PropertyUtil.getPropertyValue("image.properties", "image.service");
    	return imageUrl;
    }
    /**
     * 获取图片上传路径
     * @return
     */
    public static String imageUploadPath(){
    	String uploadPath = PropertyUtil.getPropertyValue("image.properties", "image.uploadPath");
    	return uploadPath;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        System.out.println(urlDecode("http://www.g.cn/search?hl=zh-CN&q=%E4%B8%AD+&meta=&aq=f&oq="));
        System.out.println(urlDecode("http://www.baidu.com/s?ie=gb2312&bs=%D6%D0&sr=&z=&cl=3&f=3&wd=%D6%D0%BB%AA+%BF%A5%BD%DD&ct=0&oq=%D6%D0%BB%AA+&rsp=0"));
    }

}
