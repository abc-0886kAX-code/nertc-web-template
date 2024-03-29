package com.ytxd.service.SystemManage;

import com.ytxd.dao.SystemManage.SM_Department_Mapper;
import com.ytxd.model.EasyTree;
import com.ytxd.model.SysUser;
import com.ytxd.model.SystemManage.SM_Department;
import com.ytxd.service.impl.BaseServiceImpl;
import com.ytxd.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("sM_Department_ServiceImpl")
public class SM_Department_ServiceImpl extends BaseServiceImpl implements SM_Department_Service {
	@Autowired
	private SM_Department_Mapper SM_Department_Mapper;

	/*@Override
	public Map<String, Object> GetList(SM_Department obj,SysUser user,String where) throws Exception{
		String whereString="";
		if(obj.getListview()!=null){
			if(obj.getListview().equals("SM_Department_MyList"))//录入列表
			{
				if(!HasPower(user, "SM_Department_MyList_All"))//所有数据权限
				{
					if(obj.getparentid()!=null && (obj.getparentid().equals("0") || obj.getparentid().equals("001"))){
						obj.setparentid(user.getDeptId());
					}
					whereString+=" and SM_Department.DEPARTMENTID IN ("+ PowerDeptId(user)+") ";
				}
			}
		}
		if(where!=null)
		{
			obj.setWhereString(whereString+" "+where);
		}
		else{
			obj.setWhereString(whereString);
		}
		obj = obj.GetdecodeUtf();
		List<SM_Department> list= null;
		if (obj.getPage() != null && obj.getRows() != null) {
			list= SM_Department_Mapper.Select_SM_Department(obj);
		} else {
			list = SM_Department_Mapper.Select_SM_Department_All(obj);
		}
		if(list!=null){
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("total",SM_Department_Mapper.Select_SM_Department_Count(obj));
			result.put("rows", list);
			return result;
		}
		return null;
	}

	@Override
	public SM_Department GetListById(String departmentid) throws Exception{
		SM_Department obj = new SM_Department();
		if(departmentid!=null){ 
			List<SM_Department> list = SM_Department_Mapper.Select_SM_Department_ById(departmentid);
			if(list != null && list.size() > 0){
				obj = list.get(0);
			}
		}
		return obj;
	}*/

	@Override
	public Integer Save(SM_Department obj,SysUser user) throws Exception {
		Integer ret = 0;
		obj = obj.GetdecodeUtf();
		
//		RDMUtil rdmutil= new RDMUtil();				
//		XmlParseCurrency xml = new XmlParseCurrency();
//		xml.setAttributeValue("rdm");
//		xml.setPath(this.getClass().getClassLoader().getResource("").getPath() + "/rdm-server-ip.xml");
//		String ip = xml.getValue(PATH);
//		String userName = xml.getValue("userName");
//		String password = xml.getValue("password");
//		String token = rdmutil.sendPostForm(ip+"user/login.wbs?userName="+userName+"&password="+password,null).trim();
		
		String objId = obj.getdepartmentid();
		if(obj!=null&&StringUtil.isNotEmpty(objId)){
//			SM_Department objRDM=GetListById(objId);
//			SM_Department objRDMParent=GetListById(obj.getparentid());
//			StringBuilder rdmjson= new StringBuilder();
//			rdmjson.append("{\"Department\":{");
//			if(objRDM.getnodename()!=null && !objRDM.getnodename().equals(obj.getnodename())){
//				rdmjson.append("\"Name\":\""+obj.getnodename()+"\",");
//			}
//			rdmjson.append("\"Telephone\":\"\",\"Fax\":\"\",\"OfficeRoom\":\"\",\"Manager\":\"\",\"Secretary\":\"\",\"UpperDepartment\":\""+objRDMParent.getrdmid()+"\",\"IsNode\":\"N\",\"Remark\":\""+obj.getremark()+"\"}}");
//			String url = ip+"department/update.wbs";
//			JSONObject jsonparam = new JSONObject();		
//			jsonparam.put("token", token);
//			jsonparam.put(name, objRDM.getnodename());
//			jsonparam.put("entity", rdmjson.toString());
//			String result = rdmutil.sendPostForm(url,jsonparam);
			
			SM_Department_Mapper.Update_SM_Department(obj);
			
			ret = 1;
		}else{
			String DepartmenntId=GetDepartmentId(obj.getparentid());
			obj.setdepartmentid(DepartmenntId);
			obj.setstatus("01");
			
//			SM_Department objRDM=GetListById(obj.getparentid());
//			String rdmjson="{\"Department\":{\"Name\":\""+obj.getnodename()+"\",\"Number\":\""+obj.getdepartmentid()+"\",\"Telephone\":\"\",\"Fax\":\"\",\"OfficeRoom\":\"\",\"Manager\":\"\",\"Secretary\":\"\",\"UpperDepartment\":\""+objRDM.getrdmid()+"\",\"IsNode\":\"N\",\"Remark\":\""+obj.getremark()+"\"}}";
//			String url = ip+"department/add.wbs";
//			JSONObject jsonparam = new JSONObject();		
//			jsonparam.put("token", token);
//			jsonparam.put("entity", rdmjson);
//			String result = rdmutil.sendPostForm(url,jsonparam);
//			String rdmid=result.replace("{'Department': {'ID':'", "").replace("'}}", "");
//			rdmid=rdmid.replaceAll("\\r\\n", "");
//			obj.setrdmid(rdmid);
			
			SM_Department_Mapper.Insert_SM_Department(obj);
			ret = Integer.parseInt(DepartmenntId);
		}
		return ret;
	}

	@Override
	public Integer Delete(String departmentid) throws Exception{
		departmentid=departmentid.replaceAll(",", "','");
		SM_Department objDept=new SM_Department();
		objDept.setparentid(departmentid);
		objDept.setstatus("01");
		Integer DeptCount = (Integer)SM_Department_Mapper.Select_SM_Department_Count(objDept).get(0);
		if(DeptCount>0){
			return 2;
		}
		Integer UserCount = (Integer)SM_Department_Mapper.Select_SM_User_Count(departmentid);
		if(UserCount>0){
			return 3;
		}
		SM_Department_Mapper.Delete_SM_Department(departmentid.replace("'", "").split(","));		
		return 1;
	}
	
	
	
	//***扩展***************************************************************************************
	private String GetDepartmentId(String parentid) throws Exception {
		if(StringUtil.isEmpty(parentid)){
			parentid="0";
		}
		return (String)SM_Department_Mapper.GetDepartmentId(parentid);
	}
	@Override
	public List<EasyTree> GetListForTree(SM_Department obj,SysUser user) throws Exception{
		String parentid = obj.getparentid();
		obj.setparentid(null);
		String whereString="";
		if(obj.getListview()!=null){
			if(obj.getListview().equals("SM_Department_MyList")){
				if(!HasPower(user, "SM_Department_MyList_All")){
					if(user.getDeptId()!=null){
						if(user.getDeptId().equals("0") || user.getDeptId().equals("001")){
							parentid="0";
						} else if(user.getDeptId().length()>=6){
							parentid=user.getDeptId().substring(0, user.getDeptId().length()-3);
						}
					}
					whereString+=" and SM_Department.DEPARTMENTID IN ("+ PowerDeptId(user)+") ";
				}
			}
		}
		if(obj.getWhereString()!=null)
		{
			obj.setWhereString(whereString+" "+obj.getWhereString());
		}
		else{
			obj.setWhereString(whereString);
		}
		
		List<EasyTree> deptTree=new ArrayList<EasyTree>();
		if(obj.getstatus()==null){
			obj.setstatus("01");
		}
		obj.setSort("ORDERID");
		obj.setOrder("ASC");
		List<SM_Department> list= SM_Department_Mapper.Select_SM_Department_All(obj);
		if(obj.getListview()!=null){
			if(obj.getListview().equals("SM_Department_MyList"))//录入列表
			{
				if(!HasPower(user, "SM_Department_MyList_All"))//所有数据权限
				{
					if(obj.getparentid()!=null && (obj.getparentid().equals("0") || obj.getparentid().equals("001"))){
						obj.setparentid(user.getDeptId());
					}
				}
			}
		}
//		if(parentid==null || parentid.equals("0")){
//			parentid = "001";
//		} 
		deptTree.addAll(QueryNode(list,parentid,obj.getselectvalue()));
		return deptTree;
	}
	@Override
	public List<EasyTree> GetListForTree_New(SM_Department obj) throws Exception{
		String parentid = obj.getparentid();
		obj.setparentid(null);
		String whereString="";
		if(obj.getWhereString()!=null)
		{
			obj.setWhereString(whereString+" "+obj.getWhereString());
		}
		else{
			obj.setWhereString(whereString);
		}

		List<EasyTree> deptTree=new ArrayList<EasyTree>();
		if(obj.getstatus()==null){
			obj.setstatus("01");
		}
		obj.setSort("ORDERID");
		obj.setOrder("ASC");
		List<SM_Department> list= SM_Department_Mapper.Select_SM_Department_All(obj);
//		if(parentid==null || parentid.equals("0")){
//			parentid = "001";
//		}
		deptTree.addAll(QueryNode(list,parentid,obj.getselectvalue()));
		return deptTree;
	}
	/**
	 *
	 * @Desription TODO 获取树形结构
	 * @param data
	 * @param parenttid
	 * @return java.util.List<com.ytxd.model.EasyTree>
	 * @date 2023/11/10 13:22
	 * @Auther TY
	 */
	private List<EasyTree> getDeptTree(List<EasyTree> data,String parenttid){
		Iterator<EasyTree> iterator = data.listIterator();
		while (iterator.hasNext()){
			
		}
		return null;
	}

	@Override
	public List<EasyTree> GetListUnit(Map<String,Object> obj) throws Exception{
		List<EasyTree> data = SM_Department_Mapper.Select_SM_Department_Unit(obj);
		return data;
	}
	/**
	 *
	 * @Desription TODO 查询科室信息
	 * @param obj
	 * @return java.util.List<com.ytxd.model.EasyTree>
	 * @date 2023/11/10 10:33
	 * @Auther TY
	 */
	@Override
	public List<EasyTree> getSMDeParUnit(Map<String, Object> obj) throws Exception {
		obj.put("parentid",obj.get("pptr"));
		List<EasyTree> data = SM_Department_Mapper.Select_SM_DeParUnit(obj);
		return data;
	}

	/**
	 * 获取此节点和子节子节点的所有的数据
	 * @param session
	 * @param request
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<EasyTree> GetParentAndChildForTree(SM_Department obj, SysUser user) throws Exception{
		return GetListForTree(obj,user);
//		List<EasyTree> deptTree=new ArrayList<EasyTree>();
//		if(obj.getstatus()==null){
//			obj.setstatus("01");
//		}
//		obj.setSort("ORDERID");
//		obj.setOrder("ASC");
//		obj.setparentid(user.getDeptId());
//		List<SM_Department> list= SM_Department_Mapper.Select_ParentAndChild(obj);
//		deptTree.addAll(QueryNode(list,obj.getparentid(),obj.getselectvalue()));
//		return deptTree;
	}
	private List<EasyTree> QueryNode(List<SM_Department> list,String parentid,String selectvalue) throws Exception{
		List<EasyTree> TreeList=new ArrayList<EasyTree>();
		Iterator<SM_Department> it = list.iterator();
		while(it.hasNext()){
			SM_Department i = it.next();
			if(i.getparentid()==null || !i.getparentid().equals(parentid)){
				continue;
			}
			EasyTree objTree=new EasyTree();
			objTree.setId(i.getdepartmentid());
			objTree.setText(i.getnodename());
			if(i.gethaschildren()!=null && i.gethaschildren().equals("1")){
				if(selectvalue!=null && selectvalue.indexOf(i.getdepartmentid())>-1){
					objTree.setState("open");
				} else if(parentid.equals("0")){
					objTree.setState("open");
				} else {
					objTree.setState("closed");
				}
				objTree.setChildren(QueryNode(list,i.getdepartmentid(),selectvalue));
			} else {
				objTree.setState("open");
			}
			Map<String, Object> attributes = new HashMap<String, Object>();
			attributes.put("CodeId", "UM");
			objTree.setAttributes(attributes);
			TreeList.add(objTree);
		}

//		for(SM_Department i:list)
//		{
//			EasyTree objTree=new EasyTree();
//			objTree.setId(i.getdepartmentid());
//			objTree.setText(i.getnodename());
//			if(i.gethaschildren()!=null && i.gethaschildren().equals("1")){
//				if(obj.getselectvalue()!=null && obj.getselectvalue().indexOf(i.getdepartmentid())>-1){
//					objTree.setState("open");
//					SM_Department obj1 = obj;
//					obj1.setparentid(i.getdepartmentid());
//					List<SM_Department> list1= SM_Department_Mapper.Select_SM_Department_All(obj1);
//					objTree.setChildren(QueryNode(list1,obj1));
//					
//				} else {
//					objTree.setState("closed");
//				}
//			} else {
//				objTree.setState("open");
//			}
//			Map<String, Object> attributes = new HashMap<String, Object>();
//			attributes.put("CodeId", "UM");
//			objTree.setAttributes(attributes);
//			TreeList.add(objTree);
//		}
		return TreeList;
	}
	
	@Override
	public Integer SetStatus(SM_Department obj,SysUser user) throws Exception {
		String objId = obj.getdepartmentid();
		SM_Department objDept=new SM_Department();
		objDept.setparentid(objId);
		objDept.setstatus("01");
		Integer DeptCount = (Integer)SM_Department_Mapper.Select_SM_Department_Count(objDept).get(0);
		if(DeptCount>0){
			return 2;
		}
		Integer UserCount = (Integer)SM_Department_Mapper.Select_SM_User_Count(objId);
		if(UserCount>0){
			return 3;
		}
		if(obj!=null&&StringUtil.isNotEmpty(objId)){
			SM_Department_Mapper.Update_SM_Department(obj);
		}
//		RDMUtil rdmutil= new RDMUtil();
//		SM_Department objRDM=GetListById(objId);
//		
//		XmlParseCurrency xml = new XmlParseCurrency();
//		xml.setAttributeValue("rdm");
//		xml.setPath(this.getClass().getClassLoader().getResource("").getPath() + "/rdm-server-ip.xml");
//		String ip = xml.getValue(PATH);
//		String userName = xml.getValue("userName");
//		String password = xml.getValue("password");
//		
//		String token = rdmutil.sendPostForm(ip+"user/login.wbs?userName="+userName+"&password="+password,null).trim();
//		String url = ip+"department/delete.wbs?token="+token+"&name="+objRDM.getnodename();
//		String result = rdmutil.sendPostForm(url,null);
		return 1;
	}
}
