package com.ytxd.service.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;

//import org.apache.log4j.LogManager;
//import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.ytxd.model.SysUser;
import com.ytxd.service.BaseService;


public class BaseServiceImpl implements BaseService {
	
	/**
	 * 修改使用logger4j进行日志记录
	 */
//	protected final Logger logger = LogManager.getLogger(this.getClass());

	public String convertStreamToString(java.io.InputStream input) throws Exception {
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(input, "UTF-8"));
		StringBuilder sb = new StringBuilder();
		String line = null;
		while ((line = reader.readLine()) != null) {
			sb.append(line + "\n");
		}
		input.close();
		return sb.toString();
	}
//	@Autowired
//	private  SqlSessionMapClient commonDao;
	
	/**
	 * 是否有权限
	 * @param user session用户
	 * @param powerCode 权限标识
	 * @return
	 */
	public boolean HasPower(SysUser user,String powerCode){
		HashMap Logo=user.getPowerLogo();
		if(Logo.containsKey(powerCode.toLowerCase())){
			return true;
		} else {
			return false;
		}
	}
//	public boolean HasPower(SysUser user,String powerCode){
//		HashMap<String, Object> param=new HashMap<String, Object>();
//		param.put("moduleCode", powerCode);
//		try {
//			param.put("roleid",(String)commonDao.queryForObject("ns_sysuser.GetUserRoles",user));
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		Integer i= (Integer)commonDao.queryForObject("UserPower.SelectUserPower",param);
//		return i>0;
//	}

	public String PowerDeptId(SysUser user) {
		return "SELECT DEPARTMENTID FROM SM_DEPARTMENT WHERE DEPARTMENTID LIKE '"+user.getDeptId()+"%'";
	}
	
}
