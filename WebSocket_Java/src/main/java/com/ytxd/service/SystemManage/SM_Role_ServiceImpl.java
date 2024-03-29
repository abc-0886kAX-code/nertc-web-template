package com.ytxd.service.SystemManage;

import com.ytxd.dao.SystemManage.SM_FunctionTree_Mapper;
import com.ytxd.dao.SystemManage.SM_Role_Mapper;
import com.ytxd.dao.SystemManage.SM_Route_Mapper;
import com.ytxd.model.EasyTree;
import com.ytxd.model.SysUser;
import com.ytxd.model.SystemManage.SM_FunctionTree;
import com.ytxd.model.SystemManage.SM_Role;
import com.ytxd.model.SystemManage.SM_Route;
import com.ytxd.service.impl.BaseServiceImpl;
import com.ytxd.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("sM_Role_ServiceImpl")
public class SM_Role_ServiceImpl extends BaseServiceImpl implements SM_Role_Service {
	@Autowired
	private SM_Role_Mapper SM_Role_Mapper;
	@Autowired
	private SM_FunctionTree_Mapper SM_FunctionTree_Mapper;
	@Autowired
	private SM_Route_Mapper SM_Route_Mapper;
	@Override
	public Map<String, Object> GetList(SM_Role obj,SysUser user,String where) throws Exception{
		obj = obj.GetdecodeUtf();
		List<SM_Role> list= SM_Role_Mapper.Select_SM_Role(obj);
		if(list!=null){
			Map<String, Object> result = new HashMap<String, Object>();
			if (obj.getPage() != null && obj.getRows() != null) {
				obj.setselectrowcount(0);
				List<SM_Role> listCount= SM_Role_Mapper.Select_SM_Role(obj);
				if(listCount != null && listCount.size() > 0){
					result.put("total",((SM_Role)listCount.get(0)).getselectrowcount());
				}
			}
			result.put("rows", list);
			return result;
		}
		return null;
	}

	@Override
	public SM_Role GetListById(String userid) throws Exception{
		SM_Role obj = new SM_Role();
		obj.setuserid(userid);
		List<SM_Role> list = SM_Role_Mapper.Select_SM_Role(obj);
		if(list != null && list.size() > 0){
			obj = list.get(0);
		}
	    return obj;
	}

	@Override
	public Integer Save(SM_Role obj,SysUser user) throws Exception {
		Integer ret = 0;
		obj = obj.GetdecodeUtf();
		String objId = obj.getuserid();
		if(obj!=null&&StringUtil.isNotEmpty(objId)){
			SM_Role_Mapper.Update_SM_Role(obj);
			ret = 1;
		}else{
			//增加界面不录入的默认值
			if(StringUtil.isEmpty(obj.getusertype())){
				obj.setusertype("02");//角色
			}
			if(StringUtil.isEmpty(obj.getdisabled())){
				obj.setdisabled("01");
			}
			obj.setcreatby(user.getUserId().toString());
			SM_Role_Mapper.Insert_SM_Role(obj);
			ret = Integer.parseInt(obj.getuserid());
		}
		return ret;
	}

	@Override
	public Integer Delete(String userid) throws Exception{
		//删除用户菜单权限
		SM_Role_Mapper.Delete_SM_Users_Function(userid);
		//删除角色关联用户
		SM_Role_Mapper.Delete_SM_Role_Users(userid);
		//删除角色
		SM_Role_Mapper.Delete_SM_Role(userid);
		return 1;
	}


	//***扩展***************************************************************************************
	@Override
	public List<EasyTree> GetFunctionAllTree(SM_FunctionTree obj) throws Exception{
		if(obj.getdisabled()==null){
			obj.setdisabled("01");
		}
		obj.setSort("ORDERID");
		obj.setOrder("ASC");
		List<EasyTree> objTree=new ArrayList<EasyTree>();
		List<SM_FunctionTree> list= SM_FunctionTree_Mapper.Select_SM_Users_Function(obj);
		objTree.addAll(QueryNode(list,"0"));
		return objTree;
	}
	private List<EasyTree> QueryNode(List<SM_FunctionTree> list,String parentid) throws Exception{
		List<EasyTree> TreeList=new ArrayList<EasyTree>();
		for(SM_FunctionTree i:list){
			if(i.getparentid()!=null && i.getparentid().equals(parentid)){
				EasyTree objTree=new EasyTree();
				objTree.setId(i.getid());
				objTree.setText(i.getname());
				if(i.getchecked()!=null && i.getchecked().equals("1")){
					objTree.setChecked(true);
				} else {
					objTree.setChecked(false);
				}
				if(i.gethaschildren()!=null && i.gethaschildren().equals("1")){
					objTree.setState("closed");
					objTree.setChildren(QueryNode(list,i.getid()));
				}
				TreeList.add(objTree);
			}
		}
		return TreeList;
	}
	@Override
	public Integer PowerSave(SM_FunctionTree obj,SysUser user) throws Exception {
		SM_FunctionTree_Mapper.Delete_SM_Users_Function(obj);
		SM_FunctionTree_Mapper.Insert_SM_Users_Function(obj);
		return 1;
	}
	/*@Override
	public Map<String, Object> GetListUserRole(SM_Role obj,SysUser user,String where) throws Exception{
		//权限只能给自己有的权限，如果有系统管理员的权限除外
//		obj.setWhereString(" and userid in (select roleid from sm_users_role where userid='"+user.getUserId()+"' union select userid from sm_users where usertype='02' and disabled='01' and exists(select * from sm_users_role where userid='"+user.getUserId()+"' and roleid=7)) ");
		
		if(user.getRoleId()!=null && (","+user.getRoleId()+",").indexOf(",7,")>-1){
			obj.setWhereString(" and usertype='02' and disabled='01' ");
		} else {
			obj.setWhereString(" and usertype='02' and disabled='01' and ifnull(roletype,'02')='01' ");
		}
		
		obj = obj.GetdecodeUtf();
		List<SM_Role> list= SM_Role_Mapper.Select_SM_Role(obj);
//		List<SM_Role> list= SM_Role_Mapper.Select_SM_Role_RDM(obj);
		if(list!=null){
			Map<String, Object> result = new HashMap<String, Object>();
			if (obj.getPage() != null && obj.getRows() != null) {
				obj.setselectrowcount(0);
				List<SM_Role> listCount= SM_Role_Mapper.Select_SM_Role(obj);
//				List<SM_Role> listCount= SM_Role_Mapper.Select_SM_Role_RDM(obj);
				if(listCount != null && listCount.size() > 0){
					result.put("total",((SM_Role)listCount.get(0)).getselectrowcount());
				}
			}
			result.put("rows", list);
			return result;
		}
		return null;
	}*/
	/**
	 * 获取以list集合返回的权限列表
	 * @param obj
	 * @param user
	 * @param whereString
	 * @return	返回list数据集合
	 */
	@Override
	public List<SM_Role> GetRoleList(SM_Role obj, SysUser user, String whereString) {
		obj = obj.GetdecodeUtf();
		List<SM_Role> list= SM_Role_Mapper.Select_SM_Role(obj);
		if(list!=null)	return list;
		return null;
	}

	@Override
	public List<EasyTree> GetRouteAllTree(SM_Route obj) throws Exception {
		obj.setSort("ORDERID");
		obj.setOrder("ASC");
		List<EasyTree> objTree=new ArrayList<EasyTree>();
		List<SM_Route> list= SM_Route_Mapper.Select_SM_Users_Route(obj);
		objTree.addAll(QueryRouteNode(list,"0"));
		return objTree;
	}
	private List<EasyTree> QueryRouteNode(List<SM_Route> list,String parentid) throws Exception{
		List<EasyTree> TreeList=new ArrayList<EasyTree>();
		for(SM_Route i:list){
			if(i.getparentid()!=null && i.getparentid().equals(parentid)){
				EasyTree objTree=new EasyTree();
				objTree.setId(i.getid());
				objTree.setText(i.gettitle());
				if(i.getChecked()!=null && i.getChecked().equals("1")){
					objTree.setChecked(true);
				} else {
					objTree.setChecked(false);
				}
				if(i.getOpen()!=null && i.getOpen().equals("1")){
					objTree.setOpen(true);
				} else {
					objTree.setOpen(false);
				}
				if(i.gethaschildren()!=null && i.gethaschildren().equals("1")){
					objTree.setState("closed");
					objTree.setChildren(QueryRouteNode(list,i.getid()));
				}
				TreeList.add(objTree);
			}
		}
		return TreeList;
	}

	@Override
	public Integer RouteSave(SM_Route obj, SysUser user) {
		SM_Route_Mapper.Delete_SM_Users_Route(obj);
		SM_Route_Mapper.Insert_SM_Users_Route(obj);
		return 1;
	}
}
