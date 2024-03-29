package com.ytxd.service.SystemManage;

import com.ytxd.model.EasyTree;
import com.ytxd.model.SysUser;
import com.ytxd.model.SystemManage.SM_CodeItem;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface SM_CodeItem_Service {
	Map<String, Object> GetList(SM_CodeItem obj,SysUser user,String where) throws Exception;
	List<SM_CodeItem> GetList(SM_CodeItem obj) throws Exception;
	SM_CodeItem GetListById(SM_CodeItem obj) throws Exception;
	Integer Save(SM_CodeItem obj,SysUser user,String action) throws Exception;
	Integer Delete(SM_CodeItem obj) throws Exception;
	
	List<EasyTree> GetListForTree(SM_CodeItem obj) throws Exception;
	List<EasyTree> GetTree(SM_CodeItem obj) throws Exception;
	String GetCode(SM_CodeItem obj) throws Exception;
	Integer GetListCount(SM_CodeItem obj) throws Exception;
	List<HashMap<String, Object>> GetListAll(SM_CodeItem obj) throws Exception;

	List<HashMap<String, Object>> getListByCodeId(String[] codeIds);
	public List<HashMap<String, Object>> GetCodeList(String codeid, String wherestring) throws Exception;
}
