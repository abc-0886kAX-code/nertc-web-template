package com.ytxd.service.SystemManage;

import com.ytxd.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

@Service("sM_ExcelItem_ServiceImpl")
public class SM_ExcelItem_ServiceImpl extends BaseServiceImpl implements SM_ExcelItem_Service {
//	@Autowired
//	private SM_ExcelItem_Mapper SM_ExcelItem_Mapper;
//
//	@Override
//	public Map<String, Object> GetList(SM_ExcelItem obj,SysUser user,String where) throws Exception{
//		String whereString="";
//		if(where!=null)
//		{
//			obj.setWhereString(where);
//		}
//		obj = obj.GetdecodeUtf();
//		List<SM_ExcelItem> list= null;
//		if (obj.getPage() != null && obj.getRows() != null) {
//			list =SM_ExcelItem_Mapper.Select_SM_ExcelItem(obj);
//		} else {
//			list =SM_ExcelItem_Mapper.Select_SM_ExcelItem_All(obj);
//		}
//		if(list!=null){
//			Map<String, Object> result = new HashMap<String, Object>();
//			result.put("total",SM_ExcelItem_Mapper.Select_SM_ExcelItem_Count(obj));
//			result.put("rows", list);
//			return result;
//		}
//		return null;
//	}
//
//	@Override
//	public List<SM_ExcelItem> GetListForExport(String ExportName) throws Exception{
//		SM_ExcelItem obj = new SM_ExcelItem();
//		obj.setexportname(ExportName);
//		obj.setSort("COLUMNNUMBER");
//		obj.setOrder("ASC");
//		return SM_ExcelItem_Mapper.Select_SM_ExcelItem_All(obj);
//	}
//
//	@Override
//	public SM_ExcelItem GetListById(String id) throws Exception{
//		SM_ExcelItem obj = new SM_ExcelItem();
//		if(id!=null){ 
//			List<SM_ExcelItem> list = SM_ExcelItem_Mapper.Select_SM_ExcelItem_ById(id);
//			if(list != null && list.size() > 0){
//				obj = list.get(0);
//			}
//		}
//		return obj;
//	}
//
//	@Override
//	public Integer Save(SM_ExcelItem obj,SysUser user) throws Exception {
//		Integer ret = 0;
//		obj = obj.GetdecodeUtf();
//		String objId = obj.getid();
//		if(obj!=null&&StringUtil.isNotEmpty(objId)){
//			SM_ExcelItem_Mapper.Update_SM_ExcelItem(obj);
//			ret = 1;
//		}else{
//			SM_ExcelItem_Mapper.Insert_SM_ExcelItem(obj);
//			ret = Integer.parseInt(obj.getid());
//		}
//		return ret;
//	}
//
//	@Override
//	public Integer Delete(String id) throws Exception{
//		SM_ExcelItem_Mapper.Delete_SM_ExcelItem(id.replace("'", "").split(","));
//		return 1;
//	}
}
