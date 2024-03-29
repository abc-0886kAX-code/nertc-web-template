package com.ytxd.dao.SystemManage;

import com.ytxd.model.EasyTree;
import com.ytxd.model.SystemManage.SM_Department;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface SM_Department_Mapper {
	public List<SM_Department> Select_SM_Department(SM_Department SM_Department);
	public List<SM_Department> Select_SM_Department_All(SM_Department SM_Department);
	public List<SM_Department> Select_ParentAndChild(SM_Department SM_Department);
	public List<Integer> Select_SM_Department_Count(SM_Department SM_Department);
	public List<SM_Department> Select_SM_Department_ById(String id);
	public Integer Insert_SM_Department(SM_Department SM_Department);
	public Integer Update_SM_Department(SM_Department SM_Department);
	public Integer Delete_SM_Department(String[] ids);
	public String GetDepartmentId(String parentid);
	public Integer Select_SM_User_Count(String departmentid);
	/**
	 *
	 * @Desription TODO 查询单位信息
	 * @param obj
	 * @return java.util.List<com.ytxd.model.EasyTree>
	 * @date 2023/10/31 19:50
	 * @Auther TY
	 */
	public List<EasyTree> Select_SM_Department_Unit(Map<String,Object> obj);
	/**
	 *
	 * @Desription TODO 查询科室信息
	 * @param obj
	 * @return java.util.List<com.ytxd.model.EasyTree>
	 * @date 2023/11/10 10:34
	 * @Auther TY
	 */
	public List<EasyTree> Select_SM_DeParUnit(Map<String,Object> obj);
}
