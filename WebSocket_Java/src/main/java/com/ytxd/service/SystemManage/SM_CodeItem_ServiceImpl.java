package com.ytxd.service.SystemManage;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.ytxd.common.DataUtils;
import com.ytxd.dao.SystemManage.SM_CodeItem_Mapper;
import com.ytxd.model.EasyTree;
import com.ytxd.model.MySqlData;
import com.ytxd.model.SysUser;
import com.ytxd.model.SystemManage.SM_CodeItem;
import com.ytxd.service.CommonService;
import com.ytxd.service.impl.BaseServiceImpl;
import com.ytxd.util.RedisUtils;
import com.ytxd.util.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service("sM_CodeItem_ServiceImpl")
public class SM_CodeItem_ServiceImpl extends BaseServiceImpl implements SM_CodeItem_Service {
	@Autowired
	private SM_CodeItem_Mapper SM_CodeItem_Mapper;
	@Resource
	private CommonService service;

	@Override
	public Map<String, Object> GetList(SM_CodeItem obj,SysUser user,String where) throws Exception{
		String whereString="";
		if(where!=null)
		{
			obj.setWhereString(where);
		}
		obj = obj.GetdecodeUtf();
		List<SM_CodeItem> list= SM_CodeItem_Mapper.Select_SM_CodeItem(obj);
		if(list!=null){
			Map<String, Object> result = new HashMap<String, Object>();
			if (obj.getPage() != null && obj.getRows() != null) {
				result.put("total",GetListCount(obj));
			}
			result.put("rows", list);
			return result;
		}
		return null;
	}

	@Override
	public List<SM_CodeItem> GetList(SM_CodeItem obj) throws Exception {
		obj = obj.GetdecodeUtf();
		return SM_CodeItem_Mapper.Select_SM_CodeItem(obj);
	}

	@Override
	public Integer GetListCount(SM_CodeItem obj) throws Exception{
		obj.setselectrowcount(0);
		List<SM_CodeItem> listCount=SM_CodeItem_Mapper.Select_SM_CodeItem(obj);
		if(listCount != null && listCount.size() > 0){
			return ((SM_CodeItem)listCount.get(0)).getselectrowcount();
		} else {
			return 0;
		}
	}

	@Override
	public SM_CodeItem GetListById(SM_CodeItem obj) throws Exception{
		List<SM_CodeItem> list = SM_CodeItem_Mapper.Select_SM_CodeItem(obj);
		if(list != null && list.size() > 0){
			obj = list.get(0);
		}
	    return obj;
	}

	@Override
	public Integer Save(SM_CodeItem obj,SysUser user,String action) throws Exception {
		obj = obj.GetdecodeUtf();
		if(action!=null && action.equals("update")){
			SM_CodeItem_Mapper.Update_SM_CodeItem(obj);
		}else{
			SM_CodeItem_Mapper.Insert_SM_CodeItem(obj);
		}
		//更新用到的搜索
		return 1;
	}

	@Override
	public Integer Delete(SM_CodeItem obj) throws Exception{
		SM_CodeItem_Mapper.Delete_SM_CodeItem(obj);
		return 1;
	}


	//***扩展***************************************************************************************
	@Override
	public List<EasyTree> GetListForTree(SM_CodeItem obj) throws Exception {
		List<EasyTree> objTree = new ArrayList<EasyTree>();
		if (obj.getflag() == null) {
			obj.setflag("1");
		}
		obj.setSort("ORDERID");
		obj.setOrder("ASC");
		//修改赋默认值时除得到父级节点，还要得到选中值和选中值的父级节点，所以做下面处理。
		String parentid = obj.getpptr();
		if (StringUtils.isBlank(parentid)) {
			parentid = obj.getcodeid();
		}
		obj.setpptr(null);
		if(StringUtils.isNotBlank(obj.getselectvalue())) {
			//树形下拉要实现逐级加载修改时绑定值就要修正选中值。就是因为像行政区PS和国民经济行业YH一样不规则树，也就是code子级不是父级加两位编码。
			//循环得到所有选择项的所有父级节点
			StringBuilder strSelect = new StringBuilder();
			strSelect.append(",");
			List<SM_CodeItem> list = SM_CodeItem_Mapper.Select_SM_CodeItem(obj);
			HashMap<String, String> mapCode = new HashMap<String, String>();
			for(SM_CodeItem codeTemp : list) {
				mapCode.put(codeTemp.getcode(), codeTemp.getpptr());
			}
			String[] listSelect = obj.getselectvalue().split(",");
			for(String select : listSelect) {
				while(mapCode.containsKey(select) && !select.equals(obj.getcode())) {
					strSelect.append(select + ",");
					select = mapCode.get(select);
				}
			}
			obj.setselectvalue(strSelect.toString());
		}
		obj.setpptr(parentid);
		//修改赋默认值时除得到父级节点，还要得到选中值和选中值的父级节点，所以做上面处理。
		
		List<SM_CodeItem> listCode = SM_CodeItem_Mapper.Select_SM_CodeItem(obj);		
		objTree.addAll(QueryNode(listCode, parentid, obj));
		return objTree;
	}

	private List<EasyTree> QueryNode(List<SM_CodeItem> list, String parentid, SM_CodeItem obj) throws Exception {
		List<EasyTree> TreeList = new ArrayList<EasyTree>();
		for (SM_CodeItem objItem : list) {
			if(parentid.indexOf(",") > -1) {
				if (("," + parentid + ",").indexOf("," + objItem.getcode() + ",") == -1) {
					continue;
				}
			} else if (!parentid.equals(objItem.getpptr())) {
				continue;
			}
			EasyTree objTree = new EasyTree();
			objTree.setId(objItem.getcode());
			objTree.setText(objItem.getdescription());
			/*objTree.setText(objItem.getcode() + objItem.getdescription());*/
			if (objItem.gethaschildren() != null && objItem.gethaschildren().equals("1")) {
				if (obj.getselectvalue() != null && obj.getselectvalue().indexOf("," + objItem.getcode() + ",") > -1) {
					objTree.setState("open");
					obj.setpptr(objItem.getcode());
					List<SM_CodeItem> list1 = SM_CodeItem_Mapper.Select_SM_CodeItem(obj);
					objTree.setChildren(QueryNode(list1, objItem.getcode(), obj));

				} else {
					objTree.setState("closed");
				}
			} else {
				objTree.setState("open");
			}
			Map<String, Object> attributes = new HashMap<String, Object>();
			attributes.put("CodeId", objItem.getcodeid());
			attributes.put("whereString", obj.getWhereString());
			objTree.setAttributes(attributes);
			TreeList.add(objTree);
		}
		return TreeList;
	}

	@Override
	public List<EasyTree> GetTree(SM_CodeItem obj) throws Exception {
		List<EasyTree> objTree = new ArrayList<EasyTree>();
		if (obj.getflag() == null) {
			obj.setflag("1");
		}
		obj.setSort("ORDERID");
		obj.setOrder("ASC");
		String parentid = obj.getpptr();
		if (StringUtils.isBlank(parentid)) {
			parentid = obj.getcodeid();
		}
		obj.setpptr(null);
		List<SM_CodeItem> list = SM_CodeItem_Mapper.Select_SM_CodeItem(obj);
		if(StringUtils.isNotBlank(obj.getselectvalue())) {
			//树形下拉要实现逐级加载修改时绑定值就要修正选中值。就是因为像行政区PS和国民经济行业YH一样不规则树，也就是code子级不是父级加两位编码。
			//循环得到所有选择项的所有父级节点
			StringBuilder strSelect = new StringBuilder();
			strSelect.append(",");
			HashMap<String, String> mapCode = new HashMap<String, String>();
			for(SM_CodeItem codeTemp : list) {
				mapCode.put(codeTemp.getcode(), codeTemp.getpptr());
			}
			String[] listSelect = obj.getselectvalue().split(",");
			for(String select : listSelect) {
				while(mapCode.containsKey(select) && !select.equals(obj.getcode())) {
					strSelect.append(select + ",");
					select = mapCode.get(select);
				}
			}
			obj.setselectvalue(strSelect.toString());
		}
		objTree.addAll(QueryNodeAll(list, parentid, obj.getselectvalue()));
		return objTree;
	}

	private List<EasyTree> QueryNodeAll(List<SM_CodeItem> list, String parentid, String selectvalue) throws Exception {
		List<EasyTree> TreeList = new ArrayList<EasyTree>();
		for (SM_CodeItem objitem : list) {
			if(parentid.indexOf(",") > -1) {
				if (("," + parentid + ",").indexOf("," + objitem.getcode() + ",") == -1) {
					continue;
				}
			} else if (!parentid.equals(objitem.getpptr())) {
				continue;
			}
			EasyTree objTree = new EasyTree();
			objTree.setId(objitem.getcode());
			objTree.setText(objitem.getdescription());
			if (objitem.gethaschildren() != null && objitem.gethaschildren().equals("1")) {
				if (selectvalue != null && selectvalue.indexOf("," + objitem.getcode() + ",") > -1) {
					objTree.setState("open");
				} else {
					objTree.setState("closed");
				}
				objTree.setChildren(QueryNodeAll(list, objitem.getcode(), selectvalue));
			} else {
				objTree.setState("open");
			}
			TreeList.add(objTree);
		}
		return TreeList;
	}
	@Override
	public String GetCode(SM_CodeItem obj) throws Exception{
		return SM_CodeItem_Mapper.GetCode(obj);
	}
	@Override
	public List<HashMap<String, Object>> GetListAll(SM_CodeItem obj) throws Exception{
		obj = obj.GetdecodeUtf();
		return SM_CodeItem_Mapper.SelectCodeItem(obj);
	}

	@Override
	public List<HashMap<String, Object>> getListByCodeId(String[] codeIds) {
		return SM_CodeItem_Mapper.getCodeItemByCodeIds(codeIds);
	}
	
	@Override
	public List<HashMap<String, Object>> GetCodeList(String codeid, String wherestring) throws Exception {
		//读取redis里是否有相应的数据，如果有，直接从redis获取，不再查询数据库
		List<HashMap<String, Object>> list = new ArrayList<HashMap<String,Object>>();
		String result = RedisUtils.get("code:" + codeid.toUpperCase());
		if (StringUtils.isBlank(result)) {
			MySqlData mySqlData = new MySqlData();
			mySqlData.setTableName("sm_codeitem");
			mySqlData.setFieldWhere("codeid", codeid, "=");
			mySqlData.setFieldWhere("flag", "1", "=");
			mySqlData.setSort("OrderId");
			mySqlData.setOrder("ASC");
			list = service.getDataList(mySqlData);
			// 序列化list
			String string = JSONObject.toJSONString(list);
			RedisUtils.set("code:" + codeid, string);
		} else {
			list = JSONObject.parseObject(result, new TypeReference<List<HashMap<String, Object>>>(){});
		}
		
		if(StringUtils.isBlank(wherestring)) {
			return list;
		}
		List<HashMap<String, Object>> resultList = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> map = urlToMap(wherestring.replace("&amp;", "&"));
		//通过过滤条件过滤列表		
		// 获取指定的pptr数据
		String pptr = DataUtils.getMapKeyValue(map, "pptr");
		if (StringUtils.isNotEmpty(pptr)) {
			String[] pptrs = pptr.split(",");
			for (int i = 0; i < pptrs.length; i++) {
				int j = i;
				resultList.addAll(list.stream().filter(v -> v.get("pptr").equals(pptrs[j])).collect(Collectors.toList()));
			}
			list = resultList;
		}
		// 获取排除掉的pptr的数据
		String pptrnotin = DataUtils.getMapKeyValue(map, "pptrnotin");
		if (StringUtil.isNotEmpty(pptrnotin)) {
			String[] codes = pptrnotin.split(",");
			for (int i = 0; i < codes.length; i++) {
				int j = i;
				list = list.stream().filter(v -> !v.get("pptr").equals(codes[j])).collect(Collectors.toList());
			}
		}
		// 获取指定的code的数据
		String code = DataUtils.getMapKeyValue(map, "code");
		if (StringUtil.isNotEmpty(code)) {
			resultList = new ArrayList<HashMap<String, Object>>();
			String[] codes = code.split(",");
			for (int i = 0; i < codes.length; i++) {
				int j = i;
				resultList.addAll(list.stream().filter(v -> v.get("code").equals(codes[j])).collect(Collectors.toList()));
			}
			list = resultList;
		}
		// 获取指定的code的数据
		String codenotin = DataUtils.getMapKeyValue(map, "codenotin");
		if (StringUtil.isNotEmpty(codenotin)) {
			String[] codes = codenotin.split(",");
			for (int i = 0; i < codes.length; i++) {
				int j = i;
				list = list.stream().filter(v -> !v.get("code").equals(codes[j])).collect(Collectors.toList());
			}
		}
		// 获取指定code长度的数据
		String codelength = DataUtils.getMapKeyValue(map, "codelength");
		if (StringUtil.isNotEmpty(codelength)) {
			resultList = new ArrayList<HashMap<String, Object>>();
			String[] codes = codelength.split(",");
			for (int i = 0; i < codes.length; i++) {
				int j = i;
				resultList.addAll(list.stream().filter(v -> ((String) v.get("code")).length() == Integer.valueOf(codes[j])).collect(Collectors.toList()));
			}
			list = resultList;
		}
		// 获取以指定字符串开头的code的数据
		String codelikepre = DataUtils.getMapKeyValue(map, "codelikepre");
		if (StringUtil.isNotEmpty(codelikepre)) {
			resultList = new ArrayList<HashMap<String, Object>>();
			String[] codes = codelikepre.split(",");
			for (int i = 0; i < codes.length; i++) {
				int j = i;
				resultList.addAll(list.stream().filter(v -> ((String) v.get("code")).startsWith(codes[j]))
						.collect(Collectors.toList()));
			}
			list = resultList;
		}
		// 获取以指定字符串结尾的code的数据
		String codelikesuf = DataUtils.getMapKeyValue(map, "codelikesuf");
		if (StringUtil.isNotEmpty(codelikesuf)) {
			resultList = new ArrayList<HashMap<String, Object>>();
			String[] codes = codelikesuf.split(",");
			for (int i = 0; i < codes.length; i++) {
				int j = i;
				resultList.addAll(list.stream().filter(v -> ((String) v.get("code")).endsWith(codes[j])).collect(Collectors.toList()));
			}
			list = resultList;
		}
		// 获取指定的systemtype
		String systemtype = DataUtils.getMapKeyValue(map, "systemtype");
		if (StringUtil.isNotEmpty(systemtype)) {
			resultList = new ArrayList<HashMap<String, Object>>();
			String[] codes = systemtype.split(",");
			for (int i = 0; i < codes.length; i++) {
				int j = i;
				resultList.addAll(list.stream().filter(v -> (codes[j].equals(v.get("systemtype")) || "".equals(v.get("systemtype")))).collect(Collectors.toList()));
			}
			list = resultList;
		}
		return list;
	}
	private HashMap<String, Object> urlToMap(String urlparam) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		String[] param = urlparam.split("&");
		for (String keyvalue : param) {
			String[] pair = keyvalue.split("=");
			if (pair.length == 2) {
				map.put(pair[0], pair[1]);
			}
		}
		return map;
	}
}
