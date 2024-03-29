package com.ytxd.service.SystemManage;

import com.ytxd.model.EasyTree;
import com.ytxd.model.SysUser;
import com.ytxd.model.SystemManage.SM_FunctionTree;
import com.ytxd.model.SystemManage.SM_Role;
import com.ytxd.model.SystemManage.SM_Route;

import java.util.List;
import java.util.Map;

public interface SM_Role_Service {
	Map<String, Object> GetList(SM_Role obj,SysUser user,String where) throws Exception;
	SM_Role GetListById(String userid) throws Exception;
	Integer Save(SM_Role obj,SysUser user) throws Exception;
	Integer Delete(String userid) throws Exception;
	
	List<EasyTree> GetFunctionAllTree(SM_FunctionTree obj) throws Exception;
	Integer PowerSave(SM_FunctionTree obj,SysUser user) throws Exception;
	//Map<String, Object> GetListUserRole(SM_Role obj,SysUser user,String where) throws Exception;
	/**
	 * 获取以list集合返回的权限列表
	 * @param obj
	 * @param user
	 * @param whereString
	 * @return	返回list数据集合
	 */
	List<SM_Role> GetRoleList(SM_Role obj, SysUser user, String whereString);
	List<EasyTree> GetRouteAllTree(SM_Route obj) throws Exception;
	Integer RouteSave(SM_Route obj, SysUser user);
}
