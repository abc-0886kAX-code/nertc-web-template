package com.ytxd.service.SystemManage;

import com.ytxd.dao.SystemManage.SM_Department_Dept_Mapper;
import com.ytxd.model.SysUser;
import com.ytxd.model.SystemManage.SM_Department_Dept;
import com.ytxd.service.impl.BaseServiceImpl;
import com.ytxd.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("sM_Department_Dept_ServiceImpl")
public class SM_Department_Dept_ServiceImpl extends BaseServiceImpl implements SM_Department_Dept_Service {
	@Autowired
	private SM_Department_Dept_Mapper SM_Department_Dept_Mapper;

	@Override
	public Integer GetListCount(SM_Department_Dept obj) throws Exception{
		obj.setselectrowcount(0);
		List<SM_Department_Dept> listCount= SM_Department_Dept_Mapper.Select_SM_Department_Dept(obj);
		if(listCount != null && listCount.size() > 0){
			return ((SM_Department_Dept)listCount.get(0)).getselectrowcount();
		} else {
			return 0;
		}
	}

	@Override
	public Map<String, Object> GetList(SM_Department_Dept obj,SysUser user) throws Exception{
		obj = obj.GetdecodeUtf();
		List<SM_Department_Dept> list= SM_Department_Dept_Mapper.Select_SM_Department_Dept(obj);
		if(list!=null){
			Map<String, Object> result = new HashMap<String, Object>();
			if (obj.getPage() != null && obj.getRows() != null) {
				obj.setselectrowcount(0);
				List<SM_Department_Dept> listCount= SM_Department_Dept_Mapper.Select_SM_Department_Dept(obj);
				if(listCount != null && listCount.size() > 0){
					result.put("total",((SM_Department_Dept)listCount.get(0)).getselectrowcount());
				}
			}
			result.put("rows", list);
			return result;
		}
		return null;
	}

	@Override
	public SM_Department_Dept GetListById(String id) throws Exception{
		SM_Department_Dept obj = new SM_Department_Dept();
		obj.setid(id);
		List<SM_Department_Dept> list = SM_Department_Dept_Mapper.Select_SM_Department_Dept(obj);
		if(list != null && list.size() > 0){
			obj = list.get(0);
		}
	    return obj;
	}

	@Override
	public Integer Save(SM_Department_Dept obj,SysUser user) throws Exception {
		Integer ret = 0;
		obj = obj.GetdecodeUtf();
		String objId = obj.getid();
		if(obj!=null&&StringUtil.isNotEmpty(objId)){
			SM_Department_Dept_Mapper.Update_SM_Department_Dept(obj);
			ret = 1;
		}else{
			SM_Department_Dept_Mapper.Insert_SM_Department_Dept(obj);
			ret = Integer.parseInt(obj.getid());
		}
		return ret;
	}

	@Override
	public Integer Delete(String id) throws Exception{
		SM_Department_Dept_Mapper.Delete_SM_Department_Dept(id);
		return 1;
	}


	//***扩展***************************************************************************************
}
