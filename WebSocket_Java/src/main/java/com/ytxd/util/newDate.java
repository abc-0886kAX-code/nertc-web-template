package com.ytxd.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class newDate {
	/**
	 * 获取当前时间
	 */
	public static String Time(){  
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		String str = df.format(new Date());// new Date()为获取当前系统时间
		return str;
	}
	/**
	 * 获取当前日期
	 * @return
	 */
	public static String DateTime(){
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
		String str=df.format(new Date());
		return str;
	}
	/**
	 * 获取自定义时间
	 * @param SimpleDateFormat
	 * @return
	 */
	public static String getDate(String SimpleDateFormat){
		SimpleDateFormat df=new SimpleDateFormat(SimpleDateFormat);
		String str=df.format(new Date());
		return str;
	}
	/**
	 * 获取当前年度 CurrentYear
	 * @return
	 */
	public static String getYear(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        Date date = new Date();
        return sdf.format(date);
	}
}
