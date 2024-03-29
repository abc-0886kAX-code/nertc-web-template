package com.ytxd.service.Log;

import javax.servlet.http.HttpServletRequest;

import com.ytxd.model.SysUser;

public interface Log_Login_Service {
	Integer Save(HttpServletRequest request, SysUser sysuser) throws Exception;
}
