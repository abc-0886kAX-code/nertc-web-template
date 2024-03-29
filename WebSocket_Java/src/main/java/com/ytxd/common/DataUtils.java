package com.ytxd.common;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.ytxd.common.exception.RRException;
import com.ytxd.model.BaseBO;
import com.ytxd.model.SysUser;
import com.ytxd.util.RedisUtils;

import cn.hutool.core.date.DateUtil;
import net.sf.json.JSONObject;


@Component
public class DataUtils {

	private static Environment environment;
	@Autowired
	private  Environment environment1;
	@PostConstruct
    public void init() {
		environment = this.environment1;
    }
	
	/**
	 * 得到用户信息
	 * @param request
	 * @return SysUser Or null
	 */
	public static SysUser getSysUser(HttpServletRequest request) {
		if(request.getSession().getAttribute("sysuser") instanceof SysUser) {
			return (SysUser)request.getSession().getAttribute("sysuser");
		}
		String token = request.getHeader("token");
		String sysUserStr = RedisUtils.get("token:" + token);
		if (StringUtils.isNotBlank(sysUserStr)) {
			JSONObject obj = JSONToObject(sysUserStr);
			SysUser sysuser = (SysUser) JSONObject.toBean(obj, SysUser.class);// 将建json对象转换为SysUser对象
			return sysuser;
		} else {
			throw new RRException("session过期，请重新登录。", 401);
		}
	}
	/**
	 * request值打印
	 * @param request
	 */
	public static void showParams(HttpServletRequest request) {                      
		Enumeration<String> paramNames = request.getParameterNames();
		System.out.println("--------------request值打印开始----------------------------------------------------------------");
		while(paramNames.hasMoreElements()) {
			String paramName = (String) paramNames.nextElement();            
			String[] paramValues = request.getParameterValues(paramName);
			if (paramValues.length == 1) {
				if (paramValues[0].length() != 0) {
					System.out.println(paramName+": "+paramValues[0]);
				}
			}
		}
		System.out.println("--------------request值打印结束----------------------------------------------------------------");
	}
	public static HashMap<String, Object> getRequestMap(HttpServletRequest request) {  
        // 参数Map
        Map<String, String[]> properties = request.getParameterMap();  
        // 返回值Map  
        HashMap<String, Object> returnMap = new HashMap<String, Object>();  
        Iterator<Entry<String, String[]>> entries = properties.entrySet().iterator();  
        Map.Entry<String, String[]> entry;  
        String name = "";  
        String value = "";  
        while (entries.hasNext()) {  
            entry = (Map.Entry<String, String[]>) entries.next();  
            name = (String) entry.getKey();
            value = "";  
            Object valueObj = entry.getValue();
            if(null == valueObj){  
                value = "";  
            }else if(valueObj instanceof String[]){  
                String[] values = (String[])valueObj;  
                for(int i=0;i<values.length;i++){  
                    value = value + values[i] + ",";  
                }  
                value = value.substring(0, value.length()-1);  
            }else{  
                value = valueObj.toString();  
            }
			returnMap.put(name, value);
        }
        return returnMap;  
    }
	/** 
     * 从request中获得参数Map，并返回可读的Map 
     *  
     * @param request 
     * @return 
     */
    public static Map<String, Object> getParameterMap(HttpServletRequest request) {
        // 参数Map
        Map<String, String[]> properties = request.getParameterMap();  
        // 返回值Map  
        Map<String, Object> returnMap = new HashMap<String, Object>();  
        Iterator<Entry<String, String[]>> entries = properties.entrySet().iterator();  
        Map.Entry<String, String[]> entry;  
        String name = "";  
        String value = "";  
        while (entries.hasNext()) {  
            entry = (Map.Entry<String, String[]>) entries.next();  
            name = (String) entry.getKey();
            value = "";  
            Object valueObj = entry.getValue();
            if(null == valueObj){  
                value = "";  
            }else if(valueObj instanceof String[]){  
                String[] values = (String[])valueObj;  
                for(int i=0;i<values.length;i++){  
                    value = value + values[i] + ",";  
                }  
                value = value.substring(0, value.length()-1);  
            }else{  
                value = valueObj.toString();  
            }
            if(StringUtils.isBlank(value) || Objects.equals(value.toUpperCase(Locale.ROOT),"NULL")){
            	value = null;
			}
			returnMap.put(name, value);
        }
        //得到项目名称
        returnMap.put("contextpath", request.getContextPath());
        //得到项目的绝对路径
        returnMap.put("servletrealpath", request.getSession().getServletContext().getRealPath("/"));
        return returnMap;  
    }
	/**
	 * 对象转字符串，如果对象为null返回""
	 * @param key
	 * @return
	 */
	public static String getObjectString(Object key) {
		if(key != null) {
			return key.toString();
		} else {
			return "";
		}
	}
	/**
	 * 得到Map里的key值对应的value值
	 * @param map
	 * @param key
	 * @return String
	 */
	public static String getMapKeyValue(Map<String, Object> map, String key) {
		if(map == null) {
			return "";
		}
		Object obj = map.get(key);
		if(obj == null) {
			obj = map.get(key.toLowerCase());
		}
		if(obj == null) {
			obj = map.get(key.toUpperCase());
		}
		if(obj != null) {
			return obj.toString();
		} else {
			return "";
		}
	}
	/**
	 * 得到Map里的key值对应的value值
	 * @param map
	 * @param key
	 * @return String
	 */
	public static String getMapValue(Map<Object, Object> map, String key) {
		if(map == null) {
			return "";
		}
		Object obj = map.get(key);
		if(obj == null) {
			obj = map.get(key.toLowerCase());
		}
		if(obj == null) {
			obj = map.get(key.toUpperCase());
		}
		if(obj != null) {
			return obj.toString();
		} else {
			return "";
		}
	}
	/**
	 * 得到项目名称，如：/ky
	 * @param request
	 * @return String
	 */
	public static String getAppPath(HttpServletRequest request) {
		return request.getContextPath(); //获取项目根目录，替换url中的根目录
	}
	/**
	 * 得到项目的根目录，如：http://localhost:8080/ky
	 * @param request
	 * @return String
	 */
	public static String getRootPath(HttpServletRequest request) {
		String url = request.getScheme() +"://" + request.getServerName();
		if(request.getServerPort() != 80) {
			url = url + ":" +request.getServerPort();
		}
		if(StringUtils.isNotBlank(request.getContextPath())) {
			url = url + request.getContextPath();
		}
		return url;
	}
	/**
	 * url解码 防中文乱码
	 * @param value
	 * @return String
	 */
	public static String getDecode(String value) {
		if(StringUtils.isNotEmpty(value)) {
			try {
				//这是因为传参有一些特殊字符，比如%号或者说+号，导致不能解析，报错
				value = value.replaceAll("%(?![0-9a-fA-F]{2})", "%25");
				//value = value.replaceAll("\\+", "%2B");
				return java.net.URLDecoder.decode(value, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				System.out.println("URL转码出现问题");
				return value;
			}
		} else {
			return value;
		}
	}
	public static String getEncode(String value) {
		if(StringUtils.isNotEmpty(value)) {
			try {
				return java.net.URLEncoder.encode(value, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				System.out.println("URL转码出现问题");
				return value;
			}
		} else {
			return value;
		}
	}

	/**
	 * 获取格式化的当前时间字符串
	 * @param dateFormat 日期格式
	 * @return 格式化的当前时间字符串
	 */
	public static String getDate(String dateFormat) {
		return new SimpleDateFormat(dateFormat).format(new Date());
	}
	/**
	 * 获取格式化后的日期字符串
	 * @param date 需要格式化的日期
	 * @param dateFormat 日期格式
	 * @return 格式化后的日期字符串
	 */
	public static String getDate(Date date, String dateFormat) {
		return new SimpleDateFormat(dateFormat).format(date);
	}
	
	/**
	 * 得到当时时间加上一个秒数后的时间
	 * @param second 秒
	 * @return 时间
	 */
	public static String getAddTime(Long second) {
		return getDate(new Date(System.currentTimeMillis() + second * 1000), "yyyy-MM-dd HH:mm:ss");
	}
	
	/**
	 * 将字符串数组转成英文逗号分隔的字符串
	 * @param strArray
	 * @return String
	 */
	public static String convertArrayToString(String[] strArray) {
		if (strArray == null || strArray.length == 0) {
            return "";
        }
		
		StringBuilder sb = new StringBuilder();
        for (String strValue : strArray) {
        	sb.append(strValue + ",");
        }
        if(sb.length() > 0) {
        	sb.deleteCharAt(sb.length() - 1); //去除最后一个逗号
        }
        return sb.toString();

	}
	/**
	 * 获取yml文件中的配置信息
	 * @param key 
	 * @return String
	 */
	public static String getConfInfo(String key){
		return environment.getProperty(key);
	}
	
	public static JSONObject JSONToObject(String jsonStr){ 
	    //接收{}对象，此处接收数组对象会有异常 
//	    if(jsonStr.indexOf("[") != -1){ 
//	      jsonStr = jsonStr.replace("[", ""); 
//	    } 
//	    if(jsonStr.indexOf("]") != -1){ 
//	      jsonStr = jsonStr.replace("]", ""); 
//	    } 
	    JSONObject obj = new JSONObject().fromObject(jsonStr);//将json字符串转换为json对象  
	    return obj;//返回一个JSONObject对象 
	} 
	/**
	 * 将对象转成json字符串
	 * @param Object
	 * @return String
	 */
	public static String ObjectToJson(Object obj){ 
	    JSONObject json = JSONObject.fromObject(obj);//将对象转换为json对象 
	    String str = json.toString();//将json对象转换为字符串 
	    return str; 
	} 
	
	/**
	 * 判断字符串是否是数字
	 * @param str
	 * @return
	 */
	public static boolean isInteger(String str) {
		Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
		return pattern.matcher(str).matches();
	}
	
	public static void PrintMsg (HttpServletResponse response, String msg) throws IOException {
		response.reset();
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.println("<script defer='defer' type='text/javascript'>");
		out.println("alert('" + msg + "');window.close();");
		out.println("</script>");
		out.flush();
		out.close();
	}

	/**
	 * 分页
	 */
	public static void Padding(Object obj) {
		if(obj != null){
			Integer page = 0;
			Integer rows = 0;
			String sort = null;
			String order = null;
			/**
			 * 判断object 的类型，根据不同的类型取值
			 */
			if(BaseBO.class.isAssignableFrom(obj.getClass())){
				 page = ((BaseBO) obj).getPage();
				 rows = ((BaseBO) obj).getRows();
				 sort = ((BaseBO) obj).getSort();
				 order = ((BaseBO) obj).getOrder();
			}else if(Map.class.isAssignableFrom(obj.getClass())){
				String strPage = getMapKeyValue((Map<String,Object>)obj,"page");
				String strRows = getMapKeyValue((Map<String,Object>)obj,"rows");
				sort = getMapKeyValue((Map<String,Object>)obj,"sort");
				order = getMapKeyValue((Map<String,Object>)obj,"order");
				page = StringUtils.isNotBlank(strPage)?Integer.parseInt(strPage):getMapIntegerValue((Map<String,Object>)obj,"pageIndex");
				rows = StringUtils.isNotBlank(strRows)?Integer.parseInt(strRows):getMapIntegerValue((Map<String,Object>)obj,"pageSize");
			}else if(com.alibaba.fastjson.JSONObject.class.isAssignableFrom(obj.getClass())){
				page = ((com.alibaba.fastjson.JSONObject) obj).getInteger("page");
				rows = ((com.alibaba.fastjson.JSONObject) obj).getInteger("rows");
				sort = ((com.alibaba.fastjson.JSONObject) obj).getString("sort");
				order = ((com.alibaba.fastjson.JSONObject) obj).getString("order");
			}
			if(page != null && page > 0 && rows != null && rows > 0){
				PageHelper.startPage(page,rows);
			}
			if(StringUtils.isNotBlank(sort)){
				PageHelper.orderBy(sort + " " + order);
			}
		}
	}

	public static Integer getMapIntegerValue(Map<String, Object> map, String key){
		String value = getMapKeyValue(map,key);
		if(StringUtils.isNotBlank(value) && !"0".equals(value)){
			return Integer.parseInt(value);
		}else {
			return 0;
		}
	}

	public static Double getMapDoubleValue(Map<String, Object> map, String key){
		String value = getMapKeyValue(map,key);
		if(StringUtils.isNotBlank(value) && !"0".equals(value)){
			return Double.parseDouble(value);
		}else {
			return 0.0;
		}
	}

	public static String getRainStartTime() {
		if (DateUtil.thisHour(true) < 8) {
			return DateUtil.yesterday().toString("yyyy-MM-dd 08:00:00");
		} else {
			return DateUtil.date().toString("yyyy-MM-dd 08:00:00");
		}
	}

	public static String getRainEndTime() {
		if (DateUtil.thisHour(true) < 8) {
			return DateUtil.date().toString("yyyy-MM-dd 08:00:00");
		} else {
			return DateUtil.tomorrow().toString("yyyy-MM-dd 08:00:00");
		}
	}
	public static  String AddDate(@NotBlank String str, @NotBlank Integer value, @NotBlank Integer type){
		try {
			/**
			 * 设置日期格式
			 */
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			/**
			 * 获取时间
			 */
			Date date = format.parse(DataUtils.getDate(str));
			/**
			 * 时间格式化
			 */
			Calendar calendar = Calendar.getInstance();
			/**
			 * 计算时间
			 * 年 Calendar.YEAR 1
			 * 月 Calendar.MONTH 2
			 * 日 Calendar.DATE 5
			 * 时 Calendar.HOUR 10
			 * 分 Calendar.MINUTE 12
			 * 秒 Calendar.SECOND 13
			 */
			calendar.setTime(date);
			calendar.add(type, value);
			return format.format(calendar.getTime());
		}catch (Exception e){
			e.printStackTrace();
		}
		return DataUtils.getDate(str);
	}
	/**
	 *
	 * @Desription TODO 获取request中的参数和用户的基本信息
	 * @param request
	 * @return java.util.Map<java.lang.String,java.lang.Object>
	 * @date 2023/8/17 13:04
	 * @Auther TY
	 */
	public static Map<String,Object> getRequestParamsAndUser(HttpServletRequest request){
		Map<String,Object> obj = getParameterMap(request);
		SysUser sysUser = getSysUser(request);
		obj.put("userid",sysUser.getUserId());
		obj.put("username",sysUser.getTrueName());
		return obj;
	}
	/**
	 *
	 * @Desription TODO 判断是不是JSON
	 * @param jsonString
	 * @return boolean
	 * @date 2023/8/17 16:03
	 * @Auther TY
	 */
	public static boolean isJson(String jsonString) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.readTree(jsonString);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	/**
	 *
	 * @Desription TODO 获取值
	 * @param map
	 * @param key
	 * @return java.math.BigDecimal
	 * @date 2023/10/30 13:59
	 * @Auther TY
	 */
	public static BigDecimal getMapValueToBigDecimal(Map<String, Object> map, String key) {
		String num = DataUtils.getMapKeyValue(map,key);
		if(StringUtils.isBlank(num)){
			return new BigDecimal(0);
		}
		return new BigDecimal(num);
	}
	public static Double getMapValueToDouble(Map<String, Object> map, String key) {
		String num = DataUtils.getMapKeyValue(map,key);
		if(StringUtils.isBlank(num)){
			return new Double(0);
		}
		return new Double(num);
	}
	public static String getStartTime() {
		return DateUtil.date().toString("yyyy-MM-dd 00:00:00");
	}

	public static String getEndTime() {
		return DateUtil.date().toString("yyyy-MM-dd 23:59:59");
	}
}