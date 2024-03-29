package com.ytxd.service.SystemManage;

import com.ytxd.dao.SystemManage.SM_FunctionTree_Mapper;
import com.ytxd.model.EasyTree;
import com.ytxd.model.Menu;
import com.ytxd.model.SysUser;
import com.ytxd.model.SystemManage.SM_FunctionTree;
import com.ytxd.service.impl.BaseServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("sM_FunctionTree_ServiceImpl")
public class SM_FunctionTree_ServiceImpl extends BaseServiceImpl implements SM_FunctionTree_Service {
	@Autowired
	private SM_FunctionTree_Mapper SM_FunctionTree_Mapper;

	/*@Override
	public Map<String, Object> GetList(SM_FunctionTree obj,SysUser user) throws Exception{
		obj = obj.GetdecodeUtf();
		List<SM_FunctionTree> list= null;
		if (obj.getPage() != null && obj.getRows() != null) {
			list =SM_FunctionTree_Mapper.Select_SM_FunctionTree(obj);
		} else {
			list =SM_FunctionTree_Mapper.Select_SM_FunctionTree_All(obj);
		}
		if(list!=null){
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("total",SM_FunctionTree_Mapper.Select_SM_FunctionTree_Count(obj));
			result.put("rows", list);
			return result;
		}
		return null;
	}

	@Override
	public SM_FunctionTree GetListById(String id) throws Exception{
		SM_FunctionTree obj = new SM_FunctionTree();
		if(id!=null){ 
			List<SM_FunctionTree> list = SM_FunctionTree_Mapper.Select_SM_FunctionTree_ById(id);
			if(list != null && list.size() > 0){
				obj = list.get(0);
			}
		}
		return obj;
	}

	@Override
	public Integer Save(SM_FunctionTree obj,SysUser user) throws Exception {
		Integer ret = 0;
		obj = obj.GetdecodeUtf();
		String objId = obj.getid();
		if(obj!=null&&StringUtil.isNotEmpty(objId)){
			SM_FunctionTree_Mapper.Update_SM_FunctionTree(obj);
			ret = 1;
		}else{
			SM_FunctionTree_Mapper.Insert_SM_FunctionTree(obj);
			ret = Integer.parseInt(obj.getid());
		}
		return ret;
	}

	@Override
	public Integer Delete(String id) throws Exception{
		SM_FunctionTree_Mapper.Delete_SM_FunctionTree(id.replace("'", "").split(","));
		return 1;
	}*/
	
	
	
	//--扩展-------------------------------------------------------------------------------------------------------------------------------------------------------
	/*@Override
	public List<EasyTree> GetListForTree(SM_FunctionTree obj) throws Exception{
		List<EasyTree> objTree=new ArrayList<EasyTree>();
		if(obj.getdisabled()==null){
			obj.setdisabled("01");
		}
		obj.setSort("ORDERID");
		obj.setOrder("ASC");
		List<SM_FunctionTree> list= SM_FunctionTree_Mapper.Select_SM_FunctionTree_All(obj);
		objTree.addAll(QueryNode(list,obj));
		return objTree;
	}
	private List<EasyTree> QueryNode(List<SM_FunctionTree> list,SM_FunctionTree obj) throws Exception{
		List<EasyTree> TreeList=new ArrayList<EasyTree>();
		for(SM_FunctionTree i:list)
		{
			EasyTree objTree=new EasyTree();
			objTree.setId(i.getid());
			objTree.setText(i.getname());
			if(i.gethaschildren()!=null && i.gethaschildren().equals("1")){
				if(obj.getselectvalue()!=null && obj.getselectvalue().indexOf(i.getid())>-1){
					objTree.setState("open");
					SM_FunctionTree obj1 = obj;
					obj1.setparentid(i.getid());
					List<SM_FunctionTree> list1= SM_FunctionTree_Mapper.Select_SM_FunctionTree_All(obj1);
					objTree.setChildren(QueryNode(list1,obj1));
					
				} else {
					objTree.setState("closed");
				}
			} else {
				objTree.setState("open");
			}
			Map<String, Object> attributes = new HashMap<String, Object>();
			attributes.put("CodeId", "UB");
			objTree.setAttributes(attributes);
			TreeList.add(objTree);
		}
		return TreeList;
	}*/
	
	@Override
	public List<EasyTree> GetListForTree(SM_FunctionTree obj) throws Exception {
		List<EasyTree> objTree = new ArrayList<EasyTree>();
		if(obj.getdisabled()==null){
			obj.setdisabled("01");
		}
		obj.setSort("ORDERID");
		obj.setOrder("ASC");
		//修改赋默认值时除得到父级节点，还要得到选中值和选中值的父级节点，所以做下面处理。
		String parentid = obj.getparentid();
		obj.setparentid(null);
		if(StringUtils.isNotBlank(obj.getselectvalue())) {
			//树形下拉要实现逐级加载修改时绑定值就要修正选中值。就是因为像行政区PS和国民经济行业YH一样不规则树，也就是code子级不是父级加两位编码。
			//循环得到所有选择项的所有父级节点
			StringBuilder strSelect = new StringBuilder();
			strSelect.append(",");
			List<SM_FunctionTree> list = SM_FunctionTree_Mapper.Select_SM_FunctionTree_All(obj);
			HashMap<String, String> mapCode = new HashMap<String, String>();
			for(SM_FunctionTree codeTemp : list) {
				mapCode.put(codeTemp.getid(), codeTemp.getparentid());
			}
			String[] listSelect = obj.getselectvalue().split(",");
			for(String select : listSelect) {
				while(mapCode.containsKey(select) && !select.equals(obj.getid())) {
					strSelect.append(select + ",");
					select = mapCode.get(select);
				}
			}
			obj.setselectvalue(strSelect.toString());
		}
		//修改赋默认值时除得到父级节点，还要得到选中值和选中值的父级节点，所以做上面处理。
		List<SM_FunctionTree> listCode = SM_FunctionTree_Mapper.Select_SM_FunctionTree_All(obj);
		obj.setparentid(parentid);
		objTree.addAll(QueryNode(listCode, parentid, obj));
		return objTree;
	}

	private List<EasyTree> QueryNode(List<SM_FunctionTree> list, String parentid, SM_FunctionTree obj) {
		List<EasyTree> TreeList = new ArrayList<>();
		list.stream().filter(o -> o.getparentid()!= null && parentid.equals(o.getparentid())).forEach(objItem -> {
			EasyTree objTree = new EasyTree();
			objTree.setId(objItem.getid());
			objTree.setText(objItem.getname());
			if (objItem.gethaschildren() != null && objItem.gethaschildren().equals("1")) {
				if (obj.getselectvalue() != null && obj.getselectvalue().contains("," + objItem.getid() + ",")) {
					objTree.setState("open");
				} else if(parentid.equals("0")){
					objTree.setState("open");
				} else {
					objTree.setState("closed");
				}
				objTree.setChildren(QueryNode(list, objItem.getid(), obj));
			} else {
				objTree.setState("open");
			}
			Map<String, Object> attributes = new HashMap<String, Object>();
			attributes.put("CodeId", "UB");
			attributes.put("whereString", obj.getWhereString());
			objTree.setAttributes(attributes);
			TreeList.add(objTree);
		});
//		for (SM_FunctionTree objItem : list) {
//			if (!parentid.equals(objItem.getparentid())) {
//				continue;
//			}
//			EasyTree objTree = new EasyTree();
//			objTree.setId(objItem.getid());
//			objTree.setText(objItem.getname());
//			/*objTree.setText(objItem.getid() + objItem.getname());*/
//			if (objItem.gethaschildren() != null && objItem.gethaschildren().equals("1")) {
//				if (obj.getselectvalue() != null && obj.getselectvalue().contains("," + objItem.getid() + ",")) {
//					objTree.setState("open");
//				} else if(parentid.equals("0")){
//					objTree.setState("open");
//				} else {
//					objTree.setState("closed");
//				}
//				objTree.setChildren(QueryNode(list, objItem.getid(), obj));
//				/*if (obj.getselectvalue() != null && obj.getselectvalue().indexOf("," + objItem.getid() + ",") > -1) {
//					objTree.setState("open");
//					obj.setparentid(objItem.getid());
//					List<SM_FunctionTree> list1 = SM_FunctionTree_Mapper.Select_SM_FunctionTree_All(obj);
//					objTree.setChildren(QueryNode(list1, objItem.getid(), obj));
//
//				} else {
//					objTree.setState("closed");
//				}*/
//			} else {
//				objTree.setState("open");
//			}
//			Map<String, Object> attributes = new HashMap<String, Object>();
//			attributes.put("CodeId", "UB");
//			attributes.put("whereString", obj.getWhereString());
//			objTree.setAttributes(attributes);
//			TreeList.add(objTree);
//		}
		return TreeList;
	}

	@Override
	public List<EasyTree> GetTree(SM_FunctionTree obj) throws Exception {
		List<EasyTree> objTree = new ArrayList<EasyTree>();
		if(obj.getdisabled()==null){
			obj.setdisabled("01");
		}
		obj.setSort("ORDERID");
		obj.setOrder("ASC");
		List<SM_FunctionTree> list = SM_FunctionTree_Mapper.Select_SM_FunctionTree_All(obj);
		String parentid = obj.getparentid();
		if(StringUtils.isNotBlank(obj.getselectvalue())) {
			//树形下拉要实现逐级加载修改时绑定值就要修正选中值。就是因为像行政区PS和国民经济行业YH一样不规则树，也就是code子级不是父级加两位编码。
			//循环得到所有选择项的所有父级节点
			StringBuilder strSelect = new StringBuilder();
			strSelect.append(",");
			HashMap<String, String> mapCode = new HashMap<String, String>();
			for(SM_FunctionTree codeTemp : list) {
				mapCode.put(codeTemp.getid(), codeTemp.getparentid());
			}
			String[] listSelect = obj.getselectvalue().split(",");
			for(String select : listSelect) {
				while(mapCode.containsKey(select) && !select.equals(obj.getid())) {
					strSelect.append(select + ",");
					select = mapCode.get(select);
				}
			}
			obj.setselectvalue(strSelect.toString());
		}
		objTree.addAll(QueryNodeAll(list, parentid, obj.getselectvalue()));
		return objTree;
	}

	private List<EasyTree> QueryNodeAll(List<SM_FunctionTree> list, String parentid, String selectvalue) throws Exception {
		List<EasyTree> TreeList = new ArrayList<EasyTree>();
		for (SM_FunctionTree objitem : list) {
			if (!parentid.equals(objitem.getparentid())) {
				continue;
			}
			EasyTree objTree = new EasyTree();
			objTree.setId(objitem.getid());
			objTree.setText(objitem.getname());
			if (objitem.gethaschildren() != null && objitem.gethaschildren().equals("1")) {
				if (selectvalue != null && selectvalue.indexOf("," + objitem.getid() + ",") > -1) {
					objTree.setState("open");
				} else {
					objTree.setState("closed");
				}
				objTree.setChildren(QueryNodeAll(list, objitem.getid(), selectvalue));
			} else {
				objTree.setState("open");
			}
			TreeList.add(objTree);
		}
		return TreeList;
	}
	
	
	@Override
	public List<Menu> GetPowerFunctionList(SysUser user, String systemtype) throws Exception{
		List<SM_FunctionTree> list=SM_FunctionTree_Mapper.Select_Power_FunctionTree(user.getUserId());
		List<Menu> menulist = new ArrayList<Menu>();//最张返回的菜单
		List<SM_FunctionTree> parentList = new ArrayList<SM_FunctionTree>();////得到选择系统的第一级节点
		List<Menu> menuClist = null;
		Menu m, me;
		//得到选择系统的第一级节点
		for (SM_FunctionTree obj : list) {
			if (obj!=null && obj.getparentid()!=null && obj.getparentid().equals("0")) {
				if (StringUtils.isNotBlank(systemtype) && ("," + systemtype + ",").indexOf("," + obj.getsystype() + ",") > -1) {
					parentList.add(obj);
				}
			}
		}
		for (SM_FunctionTree sysM : parentList) {
			m = new Menu();
			menuClist = new ArrayList<Menu>();
			for (SM_FunctionTree sysModule : list) {
				if (sysModule.getparentid()!=null && sysM.getid().equals(sysModule.getparentid())) {
					me = new Menu();
					me.setIcon(sysModule.getimageurl());
					me.setMenuid(Integer.parseInt(sysModule.getid()));
					me.setMenuname(sysModule.getname());
					me.setUrl(sysModule.getnavigateurl());
					List<Menu> thirdmenus=new ArrayList<Menu>();
					for(SM_FunctionTree thirdmodule:list){
						if(thirdmodule.getparentid()!=null && sysModule.getid().equals(thirdmodule.getparentid())){
							Menu thirdmenu=new Menu();
							thirdmenu.setIcon(thirdmodule.getimageurl());
							thirdmenu.setMenuid(Integer.parseInt(sysModule.getid()));
							thirdmenu.setMenuname(thirdmodule.getname());
							thirdmenu.setUrl(thirdmodule.getnavigateurl());
							if("1".equals(thirdmodule.gethaschildren())){
								List<Menu> fourthmenus=new ArrayList<Menu>();
								for(SM_FunctionTree fourthmodule:list){
									if(fourthmodule.getparentid()!=null && thirdmodule.getid().equals(fourthmodule.getparentid())){
										Menu fourthmenu=new Menu();
										fourthmenu.setIcon(fourthmodule.getimageurl());
										fourthmenu.setMenuid(Integer.parseInt(sysModule.getid()));
										fourthmenu.setMenuname(fourthmodule.getname());
										fourthmenu.setUrl(fourthmodule.getnavigateurl());										
										fourthmenus.add(fourthmenu);
									}
								}
								if(fourthmenus != null && fourthmenus.size() > 0) {
									thirdmenu.setMenus(fourthmenus);
								}
							}							
							thirdmenus.add(thirdmenu);
						}
					}
					if (thirdmenus != null && thirdmenus.size() > 0) {
						me.setMenus(thirdmenus);
					}
					/*------------------*/
					menuClist.add(me);
				}
			}
			m.setIcon(sysM.getimageurl());
			m.setMenuid(Integer.parseInt(sysM.getid()));
			m.setMenuname(sysM.getname());
			m.setUrl(sysM.getnavigateurl());
			m.setMenus(menuClist);
			if (menuClist != null && menuClist.size() > 0) {
				menulist.add(m);
			}
		}
		return menulist;
	}
	@Override
	public String GetPowerSystemList(SysUser user) throws Exception{
		String SystemType="";
		List<SM_FunctionTree> list=SM_FunctionTree_Mapper.Select_Power_FunctionTree(user.getUserId());
		for (SM_FunctionTree obj : list) {
			if (obj!=null && obj.getparentid()!=null && obj.getparentid().equals("0")) {
				if(obj.getsystype()!=null && !obj.getsystype().equals("")){
					String[] SysArray = obj.getsystype().split(",");
					for(String systype : SysArray){
						if((","+SystemType+",").indexOf(","+systype+",")==-1){
							SystemType=SystemType+","+systype;
						}
					}
				}
			}
		}
		return SystemType;
	}
}
