package com.ytxd.dao.SystemManage;

import com.ytxd.model.SystemManage.SM_ExcelItem;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SM_ExcelItem_Mapper {
	public List<SM_ExcelItem> Select_SM_ExcelItem(SM_ExcelItem SM_ExcelItem);
	public List<SM_ExcelItem> Select_SM_ExcelItem_All(SM_ExcelItem SM_ExcelItem);
	public List<Integer> Select_SM_ExcelItem_Count(SM_ExcelItem SM_ExcelItem);
	public List<SM_ExcelItem> Select_SM_ExcelItem_ById(String id);
	public Integer Insert_SM_ExcelItem(SM_ExcelItem SM_ExcelItem);
	public Integer Update_SM_ExcelItem(SM_ExcelItem SM_ExcelItem);
	public Integer Delete_SM_ExcelItem(String[] ids);
	public List<SM_ExcelItem> Select_SM_ExcelItem_IDNULL();
	public void Update_SM_ExcelItem_ID(SM_ExcelItem SM_ExcelItem);
}
