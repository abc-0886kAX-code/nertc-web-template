package com.ytxd.service.SystemManage;

import cn.hutool.core.collection.CollectionUtil;
import com.ytxd.dao.SystemManage.SM_Route_Mapper;
import com.ytxd.model.EasyTree;
import com.ytxd.model.Menu;
import com.ytxd.model.SysUser;
import com.ytxd.model.SystemManage.SM_Route;
import com.ytxd.service.impl.BaseServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("sM_Route_ServiceImpl")
public class SM_Route_ServiceImpl extends BaseServiceImpl implements SM_Route_Service {
	@Autowired
	private SM_Route_Mapper SM_Route_Mapper;

	/*@Override
	public Map<String, Object> GetList(SM_Route obj,SysUser user) throws Exception{
		obj = obj.GetdecodeUtf();
		List<SM_Route> list= null;
		if (obj.getPage() != null && obj.getRows() != null) {
			list =SM_Route_Mapper.Select_SM_Route(obj);
		} else {
			list =SM_Route_Mapper.Select_SM_Route_All(obj);
		}
		if(list!=null){
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("total",SM_Route_Mapper.Select_SM_Route_Count(obj));
			result.put("rows", list);
			return result;
		}
		return null;
	}

	@Override
	public SM_Route GetListById(String id) throws Exception{
		SM_Route obj = new SM_Route();
		if(id!=null){ 
			List<SM_Route> list = SM_Route_Mapper.Select_SM_Route_ById(id);
			if(list != null && list.size() > 0){
				obj = list.get(0);
			}
		}
		return obj;
	}

	@Override
	public Integer Save(SM_Route obj,SysUser user) throws Exception {
		Integer ret = 0;
		obj = obj.GetdecodeUtf();
		String objId = obj.getid();
		if(obj!=null&&StringUtil.isNotEmpty(objId)){
			SM_Route_Mapper.Update_SM_Route(obj);
			ret = 1;
		}else{
			SM_Route_Mapper.Insert_SM_Route(obj);
			ret = Integer.parseInt(obj.getid());
		}
		return ret;
	}

	@Override
	public Integer Delete(String id) throws Exception{
		SM_Route_Mapper.Delete_SM_Route(id.replace("'", "").split(","));
		return 1;
	}*/
	
	
	
	//--扩展-------------------------------------------------------------------------------------------------------------------------------------------------------
	/*@Override
	public List<EasyTree> GetListForTree(SM_Route obj) throws Exception{
		List<EasyTree> objTree=new ArrayList<EasyTree>();
		if(obj.getdisabled()==null){
			obj.setdisabled("01");
		}
		obj.setSort("ORDERID");
		obj.setOrder("ASC");
		List<SM_Route> list= SM_Route_Mapper.Select_SM_Route_All(obj);
		objTree.addAll(QueryNode(list,obj));
		return objTree;
	}
	private List<EasyTree> QueryNode(List<SM_Route> list,SM_Route obj) throws Exception{
		List<EasyTree> TreeList=new ArrayList<EasyTree>();
		for(SM_Route i:list)
		{
			EasyTree objTree=new EasyTree();
			objTree.setId(i.getid());
			objTree.setText(i.getname());
			if(i.gethaschildren()!=null && i.gethaschildren().equals("1")){
				if(obj.getselectvalue()!=null && obj.getselectvalue().indexOf(i.getid())>-1){
					objTree.setState("open");
					SM_Route obj1 = obj;
					obj1.setparentid(i.getid());
					List<SM_Route> list1= SM_Route_Mapper.Select_SM_Route_All(obj1);
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
	public List<EasyTree> GetListForTree(SM_Route obj) throws Exception {
		List<EasyTree> objTree = new ArrayList<EasyTree>();
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
			List<SM_Route> list = SM_Route_Mapper.Select_SM_Route_All(obj);
			HashMap<String, String> mapCode = new HashMap<String, String>();
			for(SM_Route codeTemp : list) {
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
		List<SM_Route> listCode = SM_Route_Mapper.Select_SM_Route_All(obj);
		obj.setparentid(parentid);
		objTree.addAll(QueryNode(listCode, parentid, obj));
		return objTree;
	}

	private List<EasyTree> QueryNode(List<SM_Route> list, String parentid, SM_Route obj) {
		List<EasyTree> TreeList = new ArrayList<>();
		list.stream().filter(o -> o.getparentid()!= null && parentid.equals(o.getparentid())).forEach(objItem -> {
			EasyTree objTree = new EasyTree();
			objTree.setId(objItem.getid());
			objTree.setText(objItem.gettitle());
			if (objItem.gethaschildren() != null && objItem.gethaschildren().equals("1")) {
				if (obj.getselectvalue() != null && obj.getselectvalue().contains("," + objItem.getid() + ",")) {
					objTree.setState("open");
					objTree.setOpen(true);
					objTree.setChecked(true);
//					obj.setparentid(objItem.getid());
//					List<SM_Route> list1 = SM_Route_Mapper.Select_SM_Route_All(obj);
				} else if(parentid.equals("0")){
					objTree.setState("open");
					objTree.setOpen(true);
					objTree.setChecked(true);
				} else {
					objTree.setState("closed");
					objTree.setOpen(false);
					objTree.setChecked(false);
				}
				objTree.setChildren(QueryNode(list, objItem.getid(), obj));
			} else {
				objTree.setState("open");
				objTree.setOpen(true);
				objTree.setChecked(true);
			}
			Map<String, Object> attributes = new HashMap<String, Object>();
			attributes.put("CodeId", "UB");
			attributes.put("whereString", obj.getWhereString());
			objTree.setAttributes(attributes);
			TreeList.add(objTree);
		});
		/*for (SM_Route objItem : list) {
			if (!parentid.equals(objItem.getparentid())) {
				continue;
			}
			EasyTree objTree = new EasyTree();
			objTree.setId(objItem.getid());
			objTree.setText(objItem.gettitle());
			if (objItem.gethaschildren() != null && objItem.gethaschildren().equals("1")) {
				if (obj.getselectvalue() != null && obj.getselectvalue().indexOf("," + objItem.getid() + ",") > -1) {
					objTree.setState("open");
					obj.setparentid(objItem.getid());
					List<SM_Route> list1 = SM_Route_Mapper.Select_SM_Route_All(obj);
					objTree.setChildren(QueryNode(list1, objItem.getid(), obj));

				} else {
					objTree.setState("closed");
				}
			} else {
				objTree.setState("open");
			}
			Map<String, Object> attributes = new HashMap<String, Object>();
			attributes.put("CodeId", "UB");
			attributes.put("whereString", obj.getWhereString());
			objTree.setAttributes(attributes);
			TreeList.add(objTree);
		}*/
		return TreeList;
	}

	@Override
	public List<EasyTree> GetTree(SM_Route obj) throws Exception {
		List<EasyTree> objTree = new ArrayList<EasyTree>();
//		if(obj.getdisabled()==null){
//			obj.setdisabled("01");
//		}
//		obj.setSort("ORDERID");
//		obj.setOrder("ASC");
//		List<SM_Route> list = SM_Route_Mapper.Select_SM_Route_All(obj);
//		String parentid = obj.getparentid();
//		if(StringUtils.isNotBlank(obj.getselectvalue())) {
//			//树形下拉要实现逐级加载修改时绑定值就要修正选中值。就是因为像行政区PS和国民经济行业YH一样不规则树，也就是code子级不是父级加两位编码。
//			//循环得到所有选择项的所有父级节点
//			StringBuilder strSelect = new StringBuilder();
//			strSelect.append(",");
//			HashMap<String, String> mapCode = new HashMap<String, String>();
//			for(SM_Route codeTemp : list) {
//				mapCode.put(codeTemp.getid(), codeTemp.getparentid());
//			}
//			String[] listSelect = obj.getselectvalue().split(",");
//			for(String select : listSelect) {
//				while(mapCode.containsKey(select) && !select.equals(obj.getid())) {
//					strSelect.append(select + ",");
//					select = mapCode.get(select);
//				}
//			}
//			obj.setselectvalue(strSelect.toString());
//		}
//		objTree.addAll(QueryNodeAll(list, parentid, obj.getselectvalue()));
		return objTree;
	}

	private List<EasyTree> QueryNodeAll(List<SM_Route> list, String parentid, String selectvalue) throws Exception {
		List<EasyTree> TreeList = new ArrayList<EasyTree>();
//		for (SM_Route objitem : list) {
//			if (!parentid.equals(objitem.getparentid())) {
//				continue;
//			}
//			EasyTree objTree = new EasyTree();
//			objTree.setId(objitem.getid());
//			objTree.setText(objitem.getname());
//			if (objitem.gethaschildren() != null && objitem.gethaschildren().equals("1")) {
//				if (selectvalue != null && selectvalue.indexOf("," + objitem.getid() + ",") > -1) {
//					objTree.setState("open");
//				} else {
//					objTree.setState("closed");
//				}
//				objTree.setChildren(QueryNodeAll(list, objitem.getid(), selectvalue));
//			} else {
//				objTree.setState("open");
//			}
//			TreeList.add(objTree);
//		}
		return TreeList;
	}
	
	
	@Override
	public List<Menu> GetPowerFunctionList(SysUser user, String systemtype) throws Exception{
//		List<SM_Route> list=SM_Route_Mapper.Select_Power_Route(user.getUserId());
		List<Menu> menulist = new ArrayList<Menu>();//最张返回的菜单
//		List<SM_Route> parentList = new ArrayList<SM_Route>();////得到选择系统的第一级节点
		List<Menu> menuClist = null;
//		Menu m, me;
//		//得到选择系统的第一级节点
//		for (SM_Route obj : list) {
//			if (obj!=null && obj.getparentid()!=null && obj.getparentid().equals("0")) {
//				if (StringUtils.isNotBlank(systemtype) && ("," + systemtype + ",").indexOf("," + obj.getsystype() + ",") > -1) {
//					parentList.add(obj);
//				}
//			}
//		}
//		for (SM_Route sysM : parentList) {
//			m = new Menu();
//			menuClist = new ArrayList<Menu>();
//			for (SM_Route sysModule : list) {
//				if (sysModule.getparentid()!=null && sysM.getid().equals(sysModule.getparentid())) {
//					me = new Menu();
//					me.setIcon(sysModule.getimageurl());
//					me.setMenuid(Integer.parseInt(sysModule.getid()));
//					me.setMenuname(sysModule.getname());
//					me.setUrl(sysModule.getnavigateurl());
//					List<Menu> thirdmenus=new ArrayList<Menu>();
//					for(SM_Route thirdmodule:list){
//						if(thirdmodule.getparentid()!=null && sysModule.getid().equals(thirdmodule.getparentid())){
//							Menu thirdmenu=new Menu();
//							thirdmenu.setIcon(thirdmodule.getimageurl());
//							thirdmenu.setMenuid(Integer.parseInt(sysModule.getid()));
//							thirdmenu.setMenuname(thirdmodule.getname());
//							thirdmenu.setUrl(thirdmodule.getnavigateurl());
//							if("1".equals(thirdmodule.gethaschildren())){
//								List<Menu> fourthmenus=new ArrayList<Menu>();
//								for(SM_Route fourthmodule:list){
//									if(fourthmodule.getparentid()!=null && thirdmodule.getid().equals(fourthmodule.getparentid())){
//										Menu fourthmenu=new Menu();
//										fourthmenu.setIcon(fourthmodule.getimageurl());
//										fourthmenu.setMenuid(Integer.parseInt(sysModule.getid()));
//										fourthmenu.setMenuname(fourthmodule.getname());
//										fourthmenu.setUrl(fourthmodule.getnavigateurl());										
//										fourthmenus.add(fourthmenu);
//									}
//								}
//								if(fourthmenus != null && fourthmenus.size() > 0) {
//									thirdmenu.setMenus(fourthmenus);
//								}
//							}							
//							thirdmenus.add(thirdmenu);
//						}
//					}
//					if (thirdmenus != null && thirdmenus.size() > 0) {
//						me.setMenus(thirdmenus);
//					}
//					/*------------------*/
//					menuClist.add(me);
//				}
//			}
//			m.setIcon(sysM.getimageurl());
//			m.setMenuid(Integer.parseInt(sysM.getid()));
//			m.setMenuname(sysM.getname());
//			m.setUrl(sysM.getnavigateurl());
//			m.setMenus(menuClist);
//			if (menuClist != null && menuClist.size() > 0) {
//				menulist.add(m);
//			}
//		}
		return menulist;
	}
	@Override
	public String GetPowerSystemList(SysUser user) throws Exception{
		String SystemType="";
//		List<SM_Route> list=SM_Route_Mapper.Select_Power_Route(user.getUserId());
//		for (SM_Route obj : list) {
//			if (obj!=null && obj.getparentid()!=null && obj.getparentid().equals("0")) {
//				if(obj.getsystype()!=null && !obj.getsystype().equals("")){
//					String[] SysArray = obj.getsystype().split(",");
//					for(String systype : SysArray){
//						if((","+SystemType+",").indexOf(","+systype+",")==-1){
//							SystemType=SystemType+","+systype;
//						}
//					}
//				}
//			}
//		}
		return SystemType;
	}

	@Override
	public List<Map<String, Object>> GetRouteList(SysUser sysuser) throws Exception {
		List<Map<String, Object>> result=new ArrayList<Map<String,Object>>();
		SM_Route obj=new SM_Route();
		obj.setUserid(sysuser.getUserId());
		List<SM_Route> list = SM_Route_Mapper.Select_Power_Route(obj);
		result.addAll(FormatData(list,"0"));
		return result;
	}
	private List<Map<String, Object>> FormatData(List<SM_Route> list,String parentid) throws Exception {
		List<Map<String, Object>> result=new ArrayList<Map<String,Object>>();
		Map<String, Object> map=new HashMap<String, Object>();
		Map<String, Object> map1=new HashMap<String, Object>();
		for (SM_Route objItem : list) {
			if(objItem.getparentid().equals(parentid)){
				map=new HashMap<String, Object>();
				map1=new HashMap<String, Object>();
				map1.put("title",objItem.gettitle());
			    map1.put("icon", objItem.geticon());
			    map1.put("hidden", objItem.gethidden());
			    map.put("path",objItem.getpath());
			    map.put("query",objItem.getQuery());
			    map.put("name", objItem.getname());
			    map.put("level", objItem.getLevel());
			    map.put("component", objItem.getcompenent());
			    map.put("meta", map1);
				List<Map<String, Object>> children = new ArrayList<>();
				if (objItem.gethaschildren() != null && objItem.gethaschildren().equals("1")) {
					children = FormatData(list, objItem.getid());
				}
				map.put("children", children);
				Map<String, Object> lastFirstChildren = getLastFirstChildren(children);
				map.put("redirect", CollectionUtil.isEmpty(lastFirstChildren) ? "" : lastFirstChildren.get("component"));
				result.add(map);
			} 
		}
		return result;
	}

	private Map<String, Object> getLastFirstChildren(List<Map<String, Object>> children){
		if(CollectionUtil.isEmpty(children)){
			return null;
		}
		Map<String, Object> map = children.get(0);
		List<Map<String, Object>> list = (List<Map<String, Object>>) map.get("children");
		if(CollectionUtil.isEmpty(list)){
			return map;
		}
		return getLastFirstChildren(list);
	}
}
