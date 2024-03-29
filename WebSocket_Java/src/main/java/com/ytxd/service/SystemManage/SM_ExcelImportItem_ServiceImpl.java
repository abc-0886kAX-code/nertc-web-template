package com.ytxd.service.SystemManage;

import com.ytxd.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

@Service("sM_ExcelImportItem_ServiceImpl")
public class SM_ExcelImportItem_ServiceImpl extends BaseServiceImpl implements SM_ExcelImportItem_Service {
//	@Autowired
//	private SM_ExcelImportItem_Mapper SM_ExcelImportItem_Mapper;
//
//	@Override
//	public Map<String, Object> GetList(SM_ExcelImportItem obj,SysUser user,String where) throws Exception{
//		String whereString="";
//		if(where!=null)
//		{
//			obj.setWhereString(where);
//		}
//		obj = obj.GetdecodeUtf();
//		List<SM_ExcelImportItem> list= null;
//		if (obj.getPage() != null && obj.getRows() != null) {
//			list =SM_ExcelImportItem_Mapper.Select_SM_ExcelImportItem(obj);
//		} else {
//			list =SM_ExcelImportItem_Mapper.Select_SM_ExcelImportItem_All(obj);
//		}
//		if(list!=null){
//			Map<String, Object> result = new HashMap<String, Object>();
//			result.put("total",SM_ExcelImportItem_Mapper.Select_SM_ExcelImportItem_Count(obj));
//			result.put("rows", list);
//			return result;
//		}
//		return null;
//	}
//
//	@Override
//	public SM_ExcelImportItem GetListById(String id) throws Exception{
//		SM_ExcelImportItem obj = new SM_ExcelImportItem();
//		if(id!=null){ 
//			List<SM_ExcelImportItem> list = SM_ExcelImportItem_Mapper.Select_SM_ExcelImportItem_ById(id);
//			if(list != null && list.size() > 0){
//				obj = list.get(0);
//			}
//		}
//		return obj;
//	}
//
//	@Override
//	public Integer Save(SM_ExcelImportItem obj,SysUser user) throws Exception {
//		Integer ret = 0;
//		obj = obj.GetdecodeUtf();
//		String objId = obj.getid();
//		if(obj!=null&&StringUtil.isNotEmpty(objId)){
//			SM_ExcelImportItem_Mapper.Update_SM_ExcelImportItem(obj);
//			ret = 1;
//		}else{
//			SM_ExcelImportItem_Mapper.Insert_SM_ExcelImportItem(obj);
//			ret = Integer.parseInt(obj.getid());
//		}
//		return ret;
//	}
//
//	@Override
//	public Integer Delete(String id) throws Exception{
//		SM_ExcelImportItem_Mapper.Delete_SM_ExcelImportItem(id.replace("'", "").split(","));
//		return 1;
//	}
}
