package com.ytxd.dao.SystemManage;

import com.ytxd.model.SystemManage.SM_CodeItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface SM_CodeItem_Mapper {
	public List<SM_CodeItem> Select_SM_CodeItem(SM_CodeItem obj);
	public Integer Insert_SM_CodeItem(SM_CodeItem obj);
	public Integer Update_SM_CodeItem(SM_CodeItem obj);
	public Integer Delete_SM_CodeItem(SM_CodeItem obj);
	
	public String GetCode(SM_CodeItem obj);
	public List<HashMap<String, Object>> SelectCodeItem(SM_CodeItem obj);

	List<HashMap<String, Object>> getCodeItemByCodeIds(@Param("codeIds") String[] codeIds);
}
