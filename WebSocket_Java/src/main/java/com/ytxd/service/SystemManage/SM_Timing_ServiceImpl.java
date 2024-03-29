package com.ytxd.service.SystemManage;


import com.ytxd.dao.SystemManage.SM_Timing_Mapper;
import com.ytxd.model.SystemManage.SM_Timing;
import com.ytxd.service.impl.BaseServiceImpl;
import com.ytxd.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("sM_Timing_ServiceImpl")
public class SM_Timing_ServiceImpl extends BaseServiceImpl implements SM_Timing_Service {
	@Autowired
	private SM_Timing_Mapper SM_Timing_Mapper;

	@Override
	public Integer Save(SM_Timing obj) {
		Integer ret = 0;
		obj = obj.GetdecodeUtf();
		String objId = obj.getId();
		if(obj!=null&&StringUtil.isNotEmpty(objId)){
			SM_Timing_Mapper.Update_SM_Timing(obj);
			ret=1;
		}else{
			SM_Timing_Mapper.Insert_SM_Timing(obj);
			ret=Integer.parseInt(obj.getId());
		}
		return ret;
	}
	@Override
	public Map<String,Object> GetList(SM_Timing obj) {
		obj = obj.GetdecodeUtf();
		List<SM_Timing> list = SM_Timing_Mapper.Select_SM_Timing(obj);
		if(list!=null){
			Map<String, Object> result = new HashMap<String, Object>();
			if (obj.getPage() != null && obj.getRows() != null) {
				obj.setselectrowcount(0);
				List<SM_Timing> listCount= SM_Timing_Mapper.Select_SM_Timing(obj);
				if(listCount != null && listCount.size() > 0){
					result.put("total",((SM_Timing)listCount.get(0)).getselectrowcount());
				}
			}
			result.put("rows", list);
			return result;
		}
		return null;
	}	
	@Override
	public Integer Delete(String id) {
		SM_Timing_Mapper.Delect_SM_Timing(id);
		return 1;
	}
	
	@Override
	public SM_Timing GetListByid(String id) {
		SM_Timing obj=new SM_Timing();
		obj.setId(id);
		List<SM_Timing> list = SM_Timing_Mapper.Select_SM_Timing(obj);
		if(list != null && list.size() > 0){
			obj= list.get(0);
		}
		return obj;
	}
}
