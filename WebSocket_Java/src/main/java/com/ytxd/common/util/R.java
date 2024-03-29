package com.ytxd.common.util;

import com.github.pagehelper.PageInfo;
import org.apache.http.HttpStatus;
import org.apache.poi.ss.formula.functions.T;

import java.util.HashMap;
import java.util.Map;

/**
 * 返回数据
 * 
 * @author ytxd
 * @email 2625100545@qq.com
 * @date 2019年10月27日 下午9:59:27
 */
public class R extends HashMap<String, Object> {
	private static final long serialVersionUID = 1L;
	
	public R() {
		put("code", 200);
		put("msg", "success");
	}
	
	public static R error() {
		return error(HttpStatus.SC_INTERNAL_SERVER_ERROR, "未知异常，请联系管理员");
	}
	
	public static R error(String msg) {
		return error(HttpStatus.SC_INTERNAL_SERVER_ERROR, msg);
	}
	
	public static R error(int code, String msg) {
		R r = new R();
		r.put("code", code);
		r.put("msg", msg);
		return r;
	}

	public static R ok(String msg) {
		R r = new R();
		r.put("msg", msg);
		return r;
	}
	public static R ok(PageInfo pageInfo) {
		R r = new R();
		r.put("msg", "success");
		r.put("data", pageInfo.getList());
		r.put("total", pageInfo.getTotal());
		return r;
	}
	public static R ok(Object obj) {
		R r = new R();
		r.put("msg", "success");
		r.put("data", obj);
		return r;
	}
	public static R ok(Map<String, Object> map) {
		R r = new R();
		r.putAll(map);
		return r;
	}

	public static R ok(int code, String msg) {
		R r = new R();
		r.put("code", code);
		r.put("msg", msg);
		return r;
	}
	
	public static R ok() {
		return new R();
	}

	public R put(String key, Object value) {
		super.put(key, value);
		return this;
	}
	public R putId(String key, Object value) {
		Map<String,Object> idMap = new HashMap<>();
		idMap.put(key,value);
		super.put("data", idMap);
		return this;
	}


//	public R put(Map<String, Object> map) {
//		super.putAll(map);
//		return this;
//	}
}
