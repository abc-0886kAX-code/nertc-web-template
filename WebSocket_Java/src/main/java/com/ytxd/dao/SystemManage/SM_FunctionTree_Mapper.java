package com.ytxd.dao.SystemManage;

import com.ytxd.model.SystemManage.SM_FunctionTree;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SM_FunctionTree_Mapper {
	public List<SM_FunctionTree> Select_SM_FunctionTree(SM_FunctionTree obj);
	public List<SM_FunctionTree> Select_SM_FunctionTree_All(SM_FunctionTree obj);
	public Integer Select_SM_FunctionTree_Count(SM_FunctionTree obj);
	public List<SM_FunctionTree> Select_SM_FunctionTree_ById(String id);
	public Integer Insert_SM_FunctionTree(SM_FunctionTree obj);
	public Integer Update_SM_FunctionTree(SM_FunctionTree obj);
	public Integer Delete_SM_FunctionTree(String[] ids);
	public List<SM_FunctionTree> Select_SM_Users_Function(SM_FunctionTree obj);
	
	public Integer Insert_SM_Users_Function(SM_FunctionTree obj);
	public Integer Delete_SM_Users_Function(SM_FunctionTree obj);
	public List<SM_FunctionTree> Select_Power_FunctionTree(String userid);
	public List<SM_FunctionTree> Select_SM_FunctionTreeUrlNotNull();
}
