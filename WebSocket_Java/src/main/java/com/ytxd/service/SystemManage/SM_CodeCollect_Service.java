package com.ytxd.service.SystemManage;

import com.ytxd.model.SysUser;
import com.ytxd.model.SystemManage.SM_CodeCollect;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface SM_CodeCollect_Service {
	List<SM_CodeCollect> GetList(SM_CodeCollect obj,SysUser user,String where) throws Exception;
	SM_CodeCollect GetListById(String codeid) throws Exception;
	Integer Save(SM_CodeCollect obj,SysUser user,String action) throws Exception;
	Integer Delete(String codeid) throws Exception;
	
	Integer GetListCount(SM_CodeCollect obj) throws Exception;

	List<HashMap<String, Object>> GetListForTree(Map<String, Object> obj);
}
