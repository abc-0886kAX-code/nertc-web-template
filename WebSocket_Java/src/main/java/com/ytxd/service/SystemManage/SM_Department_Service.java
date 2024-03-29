package com.ytxd.service.SystemManage;

import com.ytxd.model.EasyTree;
import com.ytxd.model.SysUser;
import com.ytxd.model.SystemManage.SM_Department;

import java.util.List;
import java.util.Map;

public interface SM_Department_Service {
	/*Map<String, Object> GetList(SM_Department obj,SysUser user,String where) throws Exception;
	SM_Department GetListById(String departmentid) throws Exception;*/
	Integer Save(SM_Department obj,SysUser user) throws Exception;
	Integer Delete(String departmentid) throws Exception;
	List<EasyTree> GetListForTree(SM_Department obj,SysUser user) throws Exception;
	List<EasyTree> GetListForTree_New(SM_Department obj) throws Exception;

	/**
	 *
	 * @Desription TODO 获取单位信息
	 * @param obj
	 * @return java.util.List<com.ytxd.model.EasyTree>
	 * @date 2023/10/31 19:44
	 * @Auther TY
	 */
	List<EasyTree> GetListUnit(Map<String,Object> obj) throws Exception;
	/**
	 *
	 * @Desription TODO 查询科室信息
	 * @param obj
	 * @return java.util.List<com.ytxd.model.EasyTree>
	 * @date 2023/11/10 10:35
	 * @Auther TY
	 */
	List<EasyTree> getSMDeParUnit(Map<String,Object> obj) throws Exception;
	List<EasyTree> GetParentAndChildForTree(SM_Department obj, SysUser user) throws Exception;
	Integer SetStatus(SM_Department obj,SysUser user) throws Exception;
}
