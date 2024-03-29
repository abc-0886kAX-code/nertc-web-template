package com.ytxd.service;

import java.util.List;
import java.util.Map;

import com.ytxd.model.LogsSysUser;


public interface LogsSysUserService {

	/**
	 * 记录系统用户的登录日志
	 * 
	 * @param sysuserId
	 */
	void addSysuserLoginLogs(LogsSysUser logsSysUser);

	/**
	 * 查询最后一次系统用户登录日志
	 * 
	 * @param userId
	 * @return
	 */
	LogsSysUser queryLastSysUserLogs(int sysuserId);
	
	/**
	 * 查询 登录日志
	 * @param parameter
	 * @return
	 */
	List<LogsSysUser> queryLogsSysuser(Map<String,Object> params);
	/**
	 * 查询 登录日志数量
	 * @return
	 */
	Integer queryLogsSysuserCount(Map<String,Object> params);
}
