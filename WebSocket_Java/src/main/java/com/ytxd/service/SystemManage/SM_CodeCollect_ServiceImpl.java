package com.ytxd.service.SystemManage;

import com.ytxd.dao.SystemManage.SM_CodeCollect_Mapper;
import com.ytxd.model.SysUser;
import com.ytxd.model.SystemManage.SM_CodeCollect;
import com.ytxd.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("sM_CodeCollect_ServiceImpl")
public class SM_CodeCollect_ServiceImpl extends BaseServiceImpl implements SM_CodeCollect_Service {
	@Autowired
	private SM_CodeCollect_Mapper SM_CodeCollect_Mapper;

	@Override
	public List<SM_CodeCollect> GetList(SM_CodeCollect obj,SysUser user,String where) throws Exception{
		if(obj.getSort()==null){
			obj.setSort("codeid");
		}
		if(obj.getOrder()==null){
			obj.setOrder("ASC");
		}
		obj = obj.GetdecodeUtf();
		List<SM_CodeCollect> list= SM_CodeCollect_Mapper.Select_SM_CodeCollect(obj);
		if(list!=null){
			return list;
		}
		return null;
	}
	
	@Override
	public Integer GetListCount(SM_CodeCollect obj){
		obj.setselectrowcount(0);
		List<SM_CodeCollect> listCount=SM_CodeCollect_Mapper.Select_SM_CodeCollect(obj);
		if(listCount != null && listCount.size() > 0){
			return ((SM_CodeCollect)listCount.get(0)).getselectrowcount();
		} else {
			return 0;
		}
	}

	@Override
	public List<HashMap<String, Object>> GetListForTree(Map<String, Object> obj) {
		return SM_CodeCollect_Mapper.GetListForTree(obj);
	}

	@Override
	public SM_CodeCollect GetListById(String codeid) throws Exception{
		SM_CodeCollect obj = new SM_CodeCollect();
		obj.setcodeid(codeid);
		List<SM_CodeCollect> list = SM_CodeCollect_Mapper.Select_SM_CodeCollect(obj);
		if(list != null && list.size() > 0){
			obj = list.get(0);
		}
	    return obj;
	}

	@Override
	public Integer Save(SM_CodeCollect obj,SysUser user,String action) throws Exception {
		obj = obj.GetdecodeUtf();
		if(action!=null&&action.equals("update")){
			SM_CodeCollect_Mapper.Update_SM_CodeCollect(obj);
		}else{
			SM_CodeCollect_Mapper.Insert_SM_CodeCollect(obj);
		}
		return 1;
	}

	@Override
	public Integer Delete(String codeid) throws Exception{
		SM_CodeCollect_Mapper.Delete_SM_CodeCollect(codeid.replace("'", "").split(","));
		return 1;
	}


	//***扩展***************************************************************************************
}
