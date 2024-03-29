package com.ytxd.dao.SystemManage;

import com.ytxd.model.SystemManage.SM_CodeCollect;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mapper
public interface SM_CodeCollect_Mapper {
	public List<SM_CodeCollect> Select_SM_CodeCollect(SM_CodeCollect obj);
	public Integer Insert_SM_CodeCollect(SM_CodeCollect obj);
	public Integer Update_SM_CodeCollect(SM_CodeCollect obj);
	public Integer Delete_SM_CodeCollect(String[] ids);

	List<HashMap<String, Object>> GetListForTree(Map<String, Object> obj);
}
