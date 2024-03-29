package com.ytxd.dao.SystemManage;


import com.ytxd.model.SystemManage.SM_Timing;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SM_Timing_Mapper {
	public Integer Insert_SM_Timing(SM_Timing obj);
	public List<SM_Timing> Select_SM_Timing(SM_Timing obj);
	public Integer Delect_SM_Timing(String id);
	public Integer Update_SM_Timing(SM_Timing obj);
}
