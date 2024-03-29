package com.ytxd.service.Log;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.ytxd.common.DataUtils;
import com.ytxd.common.util.IPUtils;
import com.ytxd.model.ActionResult;
import com.ytxd.model.MySqlData;
import com.ytxd.model.SysUser;
import com.ytxd.service.CommonService;
import com.ytxd.service.impl.BaseServiceImpl;

import cn.hutool.http.useragent.UserAgent;

@Service("Log_Login_ServiceImpl")
public class Log_Login_ServiceImpl extends BaseServiceImpl implements Log_Login_Service {
	@Resource
	private CommonService service;

	@Override
	public Integer Save(HttpServletRequest request, SysUser sysuser) throws Exception {
		//数据实体
		MySqlData mySqlData = new MySqlData();
		//数据库表名
		mySqlData.setTableName("Log_Login");
		//保存默认值
		mySqlData.setFieldValue("UserId", sysuser.getUserId());
		mySqlData.setFieldValue("UserName", sysuser.getTrueName());
		if (sysuser.getDeptName() != null && sysuser.getDeptName().indexOf("登录失败") > -1) {
			mySqlData.setFieldValue("msg", sysuser.getDeptName());
		} else {
			mySqlData.setFieldValue("msg", "登录成功");
		}
		mySqlData.setFieldValue("KeyTime", DataUtils.getDate("yyyy-MM-dd HH:mm:ss"));
		String ip = IPUtils.getIpAddr(request);
		mySqlData.setFieldValue("IP", ip);
//		mySqlData.setFieldValue("MAC", MACUtils.getMacAddress(ip));
//		mySqlData.setFieldValue("HostName", IPUtils.getHostName(ip));
		String UserAgent = request.getHeader("User-Agent");
		mySqlData.setFieldValue("UserAgent", UserAgent);
		UserAgent userAgent = cn.hutool.http.useragent.UserAgentUtil.parse(UserAgent);		
		mySqlData.setFieldValue("System", userAgent.getOs().getName());
		mySqlData.setFieldValue("Browser", userAgent.getBrowser().getName());
		mySqlData.setFieldValue("BrowserVersion", userAgent.getVersion());
		mySqlData.setFieldValue("Platform", userAgent.getPlatform().getName());
		mySqlData.setFieldValue("Engine", userAgent.getEngine().getName());
		mySqlData.setFieldValue("EngineVersion", userAgent.getEngineVersion());
		if(userAgent.isMobile()) {
			mySqlData.setFieldValue("Mobile", "01");
		} else {
			mySqlData.setFieldValue("Mobile", "00");
		}
		
		//插入和更新判断，然后执行。
		ActionResult result = null;
		String id = request.getParameter("id");
		if(StringUtils.isBlank(id)) {
			//执行插入操作
			result = service.insert(mySqlData);
			//增加保存之后的处理事件，例如 将负责人添加到作者列表
		} else {
			//执行更新操作
			mySqlData.setFieldWhere("id", id, "=");
			result = service.update(mySqlData);
			//修改保存之后的处理事件，例如 将负责人添加到作者列表
		}
		return result.getTotal();
	}


	//***扩展***************************************************************************************
}
