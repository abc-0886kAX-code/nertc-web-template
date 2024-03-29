package com.ytxd.service.SystemManage;


import com.ytxd.model.SystemManage.SM_Timing;

import java.util.Map;


public interface SM_Timing_Service {
	Integer Save(SM_Timing obj);
	Map<String,Object> GetList(SM_Timing obj);
	Integer Delete(String id);
	SM_Timing GetListByid(String id);
}
