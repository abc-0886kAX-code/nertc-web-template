package com.ytxd.service.SystemManage;

import com.ytxd.model.SysUser;
import com.ytxd.model.SystemManage.SM_Department_Dept;

import java.util.Map;

public interface SM_Department_Dept_Service {
	Integer GetListCount(SM_Department_Dept obj) throws Exception;
	Map<String, Object> GetList(SM_Department_Dept obj,SysUser user) throws Exception;
	SM_Department_Dept GetListById(String id) throws Exception;
	Integer Save(SM_Department_Dept obj,SysUser user) throws Exception;
	Integer Delete(String id) throws Exception;
}
