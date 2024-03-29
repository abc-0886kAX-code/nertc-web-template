package com.ytxd.dao.SystemManage;

import com.ytxd.model.SystemManage.SM_Route;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SM_Route_Mapper {
	public List<SM_Route> Select_SM_Route(SM_Route obj);
	public List<SM_Route> Select_SM_Route_All(SM_Route obj);
	public Integer Select_SM_Route_Count(SM_Route obj);
	public List<SM_Route> Select_SM_Route_ById(String id);
	public Integer Insert_SM_Route(SM_Route obj);
	public Integer Update_SM_Route(SM_Route obj);
	public Integer Delete_SM_Route(String[] ids);
	public List<SM_Route> Select_SM_Users_Route(SM_Route obj);
	
	public Integer Insert_SM_Users_Route(SM_Route obj);
	public Integer Delete_SM_Users_Route(SM_Route obj);
	public List<SM_Route> Select_Power_Route(SM_Route obj);
	public List<SM_Route> Select_SM_RouteUrlNotNull();
}
