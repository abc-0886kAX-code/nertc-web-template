package com.ytxd.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 通用获取资源文件对应Key的Value
 * @author Administrator
 *
 */
public class PropertyUtil {
	
	public static void main(String[] args) {
		System.out.println(getPropertyValue("template", "registerSuccess"));
	}
	
	/**
	 * 获取资源文件对应Key的Value
	 * @param fileName		资源文件的名称
	 * @param propertyKey	资源文件中对应的Key
	 * @return				如果fileName的null或者找不到对应的value都返回null
	 */
	public static String getPropertyValue(String fileName, String propertyKey) {
		if(null == fileName){
			return null;
		}
		if(fileName.indexOf(".properties")<0){
			fileName = fileName + ".properties";
		}
		InputStream in = null;
		String result = null;
		try {
			in = PropertyUtil.class.getResourceAsStream("/"+fileName);
			Properties pro = new Properties();
			pro.load(in);
			result = pro.getProperty(propertyKey);
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			try {
				in.close();
			} catch (IOException ex1) {
				ex1.printStackTrace();
			}
		}
		return result;
	}
	
	/**
	 * 同上，只是在找不到值的时候返回默认值
	 * @param fileName
	 * @param propertyKey
	 * @param defaultValue	默认值
	 * @return
	 */
	public static String getPropertyValue(String fileName, String propertyKey,String defaultValue) {
		String result = getPropertyValue(fileName, propertyKey);
		if(result == null){
			result = defaultValue;
		}
		return result;
	}
	
	/**
	 * 获取一组Key的Value
	 * @param fileName
	 * @param propertyKeys
	 * @return
	 */
	public static Map<String, String> getPropertyValues(String fileName, String[] propertyKeys){
		Map<String, String> result = new HashMap<String, String>();
		if(null != fileName){
			if(fileName.indexOf(".properties")<0){
				fileName = fileName + ".properties";
			}
			InputStream in = null;		
			try {
				in = PropertyUtil.class.getResourceAsStream("/"+fileName);
				Properties pro = new Properties();
				pro.load(in);
				for(String key : propertyKeys){
					result.put(key, pro.getProperty(key));
				}
			} catch (FileNotFoundException ex) {
				ex.printStackTrace();
			} catch (IOException ex) {
				ex.printStackTrace();
			} finally {
				try {
					in.close();
				} catch (IOException ex1) {
					ex1.printStackTrace();
				}
			}
		}
		return result;
	}
}
