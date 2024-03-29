package com.ytxd.controller.SystemManage;

import com.ytxd.controller.BaseController;
import com.ytxd.model.SysUser;
import com.ytxd.model.SystemManage.SM_Department_Dept;
import com.ytxd.service.SystemManage.SM_Department_Dept_Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 控制层(前后台交互)
 */
@Controller("sM_Department_Dept_Controller")
@RequestMapping(value = "/SystemManage/SM_Department_Dept")
public class SM_Department_Dept_Controller extends BaseController {
	@Autowired
	private SM_Department_Dept_Service Service;

	@RequestMapping("/MyList")
	public String MyList(HttpServletRequest request, Model model) {
		return "SystemManage/SM_Department_Dept/SM_Department_Dept_MyList";
	}

	@RequestMapping("/ViewList")
	public String ViewList(HttpServletRequest request, Model model) {
		return "SystemManage/SM_Department_Dept/SM_Department_Dept_ViewList";
	}

	@RequestMapping("/Edit")
	public String EditDialog(HttpServletRequest request, Model model) throws Exception {
		String id = request.getParameter("id");
		model.addAttribute("SM_Department_Dept", Service.GetListById(id));
		return "SystemManage/SM_Department_Dept/SM_Department_Dept_Edit";
	}

	@RequestMapping("/Add")
	public String AddDialog(HttpServletRequest request, Model model) {
		return "SystemManage/SM_Department_Dept/SM_Department_Dept_Edit";
	}

	@RequestMapping("/View")
	public String ViewDialog(HttpServletRequest request, Model model) throws Exception {
		String id = request.getParameter("id");
		SM_Department_Dept obj = Service.GetListById(id);
		model.addAttribute("SM_Department_Dept", obj);
		return "SystemManage/SM_Department_Dept/SM_Department_Dept_View";
	}

	@RequestMapping("/GetListCount")
	@ResponseBody
	public Integer GetListCount(HttpServletRequest request, SM_Department_Dept obj) throws Exception {
		SysUser user = (SysUser) request.getSession().getAttribute("sysuser");
		return Service.GetListCount(obj);
	}

	@PostMapping("/GetList")
	@ResponseBody
	public Map<String, Object> GetList(HttpServletRequest request, SM_Department_Dept obj) throws Exception {
		SysUser user = (SysUser) request.getSession().getAttribute("sysuser");
		return Service.GetList(obj, user);
	}

	@RequestMapping("/GetListById")
	@ResponseBody
	public SM_Department_Dept GetListById(HttpServletRequest request) throws Exception {
		String id = request.getParameter("id");
		return Service.GetListById(id);
	}

	@PostMapping("/Save")
	@ResponseBody
	public Integer Save(HttpServletRequest request, SM_Department_Dept obj) throws Exception {
		SysUser user = (SysUser) request.getSession().getAttribute("sysuser");
		return Service.Save(obj, user);
	}

	@RequestMapping("/Delete")
	@ResponseBody
	public Integer Delete(HttpServletRequest request) throws Exception {
		String id = request.getParameter("id");
		return Service.Delete(id);
	}

	// ***扩展***************************************************************************************
}
