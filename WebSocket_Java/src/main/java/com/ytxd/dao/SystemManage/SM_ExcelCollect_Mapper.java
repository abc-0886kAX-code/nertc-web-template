package com.ytxd.dao.SystemManage;

import com.ytxd.model.SystemManage.SM_ExcelCollect;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SM_ExcelCollect_Mapper {
	public List<SM_ExcelCollect> Select_SM_ExcelCollect(SM_ExcelCollect SM_ExcelCollect);
//	public List<SM_ExcelCollect> Select_SM_ExcelCollect_All(SM_ExcelCollect SM_ExcelCollect);
//	public List<Integer> Select_SM_ExcelCollect_Count(SM_ExcelCollect SM_ExcelCollect);
//	public List<SM_ExcelCollect> Select_SM_ExcelCollect_ById(String id);
	public Integer Insert_SM_ExcelCollect(SM_ExcelCollect SM_ExcelCollect);
	public Integer Update_SM_ExcelCollect(SM_ExcelCollect SM_ExcelCollect);
	public Integer Delete_SM_ExcelCollect(String[] ids);
	public List<SM_ExcelCollect> Select_SM_ExcelCollect_IDNULL();
	public void Update_SM_ExcelCollect_ID(String exportname);
}
