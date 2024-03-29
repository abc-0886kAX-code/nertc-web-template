package com.ytxd.service.SystemManage;

import com.ytxd.model.EasyTree;
import com.ytxd.model.Menu;
import com.ytxd.model.SysUser;
import com.ytxd.model.SystemManage.SM_FunctionTree;

import java.util.List;

public interface SM_FunctionTree_Service {
	/*Map<String, Object> GetList(SM_FunctionTree obj,SysUser user) throws Exception;
	SM_FunctionTree GetListById(String id) throws Exception;
	Integer Save(SM_FunctionTree obj,SysUser user) throws Exception;
	Integer Delete(String id) throws Exception;*/
	
	List<EasyTree> GetListForTree(SM_FunctionTree obj) throws Exception;
	List<EasyTree> GetTree(SM_FunctionTree obj) throws Exception;
	List<Menu> GetPowerFunctionList(SysUser user, String systemtype) throws Exception;
	String GetPowerSystemList(SysUser user) throws Exception;
}
