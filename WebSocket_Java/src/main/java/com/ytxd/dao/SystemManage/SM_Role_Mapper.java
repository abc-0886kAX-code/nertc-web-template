package com.ytxd.dao.SystemManage;

import com.ytxd.model.SystemManage.SM_Role;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface SM_Role_Mapper {
	public List<SM_Role> Select_SM_Role(SM_Role obj);
	public Integer Insert_SM_Role(SM_Role obj);
	public Integer Update_SM_Role(SM_Role obj);
	public Integer Delete_SM_Role(String userid);
	
	public List<HashMap> Select_Power_Logo(String userid);
	public Integer Delete_SM_Role_Role(SM_Role obj);
	public Integer Insert_SM_Role_Role(SM_Role obj);
	public Integer Delete_SM_Users_Function(String userid);
	public Integer Delete_SM_Role_Users(String userid);
	
	public List<SM_Role> Select_SM_Role_RDM(SM_Role obj);
}
