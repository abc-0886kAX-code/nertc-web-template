package com.ytxd.service.SystemManage;

import com.ytxd.model.EasyTree;
import com.ytxd.model.Menu;
import com.ytxd.model.SysUser;
import com.ytxd.model.SystemManage.SM_Route;

import java.util.List;
import java.util.Map;

public interface SM_Route_Service {
	/*Map<String, Object> GetList(SM_Route obj,SysUser user) throws Exception;
	SM_Route GetListById(String id) throws Exception;
	Integer Save(SM_Route obj,SysUser user) throws Exception;
	Integer Delete(String id) throws Exception;*/
	
	List<EasyTree> GetListForTree(SM_Route obj) throws Exception;
	List<EasyTree> GetTree(SM_Route obj) throws Exception;
	List<Menu> GetPowerFunctionList(SysUser user, String systemtype) throws Exception;
	String GetPowerSystemList(SysUser user) throws Exception;
	List<Map<String, Object>> GetRouteList(SysUser sysuser) throws Exception;
}
