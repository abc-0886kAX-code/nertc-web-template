package com.ytxd.dao.SystemManage;

import com.ytxd.model.SystemManage.SM_Department_Dept;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SM_Department_Dept_Mapper {
	public List<SM_Department_Dept> Select_SM_Department_Dept(SM_Department_Dept obj);
	public Integer Insert_SM_Department_Dept(SM_Department_Dept obj);
	public Integer Update_SM_Department_Dept(SM_Department_Dept obj);
	public Integer Delete_SM_Department_Dept(String id);
}
