package com.ytxd.dao.SystemManage;

import com.ytxd.model.SystemManage.SM_ExcelImportCollect;
import com.ytxd.model.SystemManage.SM_ExcelImportItem;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SM_ExcelImportItem_Mapper {
	public List<SM_ExcelImportItem> Select_SM_ExcelImportItem(SM_ExcelImportItem SM_ExcelImportItem);
	public List<SM_ExcelImportItem> Select_SM_ExcelImportItem_Template(SM_ExcelImportCollect sm_ExcelImportCollect);
	public List<SM_ExcelImportItem> Select_SM_ExcelImportItem_All(SM_ExcelImportItem SM_ExcelImportItem);
	public List<Integer> Select_SM_ExcelImportItem_Count(SM_ExcelImportItem SM_ExcelImportItem);
	public List<SM_ExcelImportItem> Select_SM_ExcelImportItem_ById(String id);
	public Integer Insert_SM_ExcelImportItem(SM_ExcelImportItem SM_ExcelImportItem);
	public Integer Update_SM_ExcelImportItem(SM_ExcelImportItem SM_ExcelImportItem);
	public Integer Delete_SM_ExcelImportItem(String[] ids);
	public List<SM_ExcelImportItem> Select_SM_ExcelImportItem_IDNULL();
	public void Update_SM_ExcelImportItem_ID(SM_ExcelImportItem SM_ExcelImportItem);
}
