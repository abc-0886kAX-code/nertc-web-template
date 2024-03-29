package com.ytxd.dao.SystemManage;

import com.ytxd.model.SystemManage.SM_ExcelImportCollect;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SM_ExcelImportCollect_Mapper {
	public List<SM_ExcelImportCollect> Select_SM_ExcelImportCollect(SM_ExcelImportCollect SM_ExcelImportCollect);
	public List<SM_ExcelImportCollect> Select_SM_ExcelImportCollect_All(SM_ExcelImportCollect SM_ExcelImportCollect);
	public List<Integer> Select_SM_ExcelImportCollect_Count(SM_ExcelImportCollect SM_ExcelImportCollect);
	public List<SM_ExcelImportCollect> Select_SM_ExcelImportCollect_ById(String id);
	public Integer Insert_SM_ExcelImportCollect(SM_ExcelImportCollect SM_ExcelImportCollect);
	public Integer Update_SM_ExcelImportCollect(SM_ExcelImportCollect SM_ExcelImportCollect);
	public Integer Delete_SM_ExcelImportCollect(String[] ids);
	public List<SM_ExcelImportCollect> Select_SM_ExcelImportCollect_IDNULL();
	public void Update_SM_ExcelImportCollect_ID(String importname);
}
