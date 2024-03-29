package com.ytxd.controller;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ytxd.model.SystemManage.SM_CodeItem;
import com.ytxd.service.SystemManage.SM_CodeItem_Service;

/**
 *  控制层(前后台交互)
 * @author autoCoder
 *
 */
@Controller("sm_codeitemController")
@RequestMapping(value = "/Sm_codeitem")
public class Sm_codeitemController  extends BaseController{
	@Autowired
	private SM_CodeItem_Service Service;
	
	/**
	 * 根据ID查询表，把值返回到新增修改页面
	 * @param request
	 * @param patentname
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/code")
	@ResponseBody
	public List<HashMap<String, Object>> code(HttpServletRequest request) throws Exception {
		SM_CodeItem obj = new SM_CodeItem();
		obj.setflag("1");
		obj.setSort("OrderId");
		obj.setOrder("ASC");
		obj.setcodeid(request.getParameter("codeid"));
		/*if (request.getParameter("whereString") != null) {
			obj.setWhereString(request.getParameter("whereString").replace("@", "'"));
		}*/
		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		
		if (StringUtils.isBlank(request.getParameter("defaultnull")) || "true".equals(request.getParameter("defaultnull"))) {
			HashMap<String, Object> objselect = new HashMap<String, Object>();
			objselect.put("code", "");
			objselect.put("description", "请选择...");
			list.add(0, objselect);
		}
		list.addAll(Service.GetListAll(obj));
		return list;
	}

	/*@RequestMapping("/FPselect")
	@ResponseBody
	public List<HashMap<String, Object>> FPselect(HttpServletRequest request) throws Exception {
		SM_CodeItem obj = new SM_CodeItem();
		obj.setcodeid("FP");
		obj.setcode("01','02','03");
		obj.setflag("1");
		obj.setSort("OrderId");
		obj.setOrder("ASC");
		if (request.getParameter("whereString") != null) {
			obj.setWhereString(request.getParameter("whereString"));
		}
		return Service.GetListAll(obj);
	}*/
}
	
	
	
	
	
	
	
	
	
	
	
