package com.ytxd.service.SystemManage;

import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.HashMap;
import java.util.List;

public interface SM_Users_Service extends UserDetailsService{
	/*Integer GetListCount(SM_Users obj) throws Exception;
	Map<String, Object> GetList(SM_Users obj,SysUser user,String where) throws Exception;
	SM_Users GetListById(String userid) throws Exception;*/
	Integer Save(String userid) throws Exception;
	Integer Delete(String userid) throws Exception;
	
	List<HashMap<String, Object>> GetListForCombobox(String[] userid, String truename);
	/**
	 *
	 * @Desription TODO 查询用户信息
	 * @param obj
	 * @return java.util.List<java.util.HashMap<java.lang.String,java.lang.Object>>
	 * @date 2023/11/6 10:18
	 * @Auther TY
	 */
	List<HashMap<String, Object>> getSMUsersSelected(HashMap<String,Object> obj);
	/*Integer UpdatePinYin() throws Exception;
	Integer SaveRole(SM_Users obj,SysUser user) throws Exception;*/
	/**
	 * 适用于需要到用户表中选择用户，同时会将已经选择的了的用户用mark标记出来
	 * @param obj
	 * @param user
	 * @param where
	 * @return
	 * @throws Exception
	 */
//	Map<String, Object> GetChooseUserList(SM_Users obj,SysUser user,String where) throws Exception;
	/**
	 * 验证用户是否被锁死，并且判断锁死时间解锁
	 * @param username
	 * @return
	 * @throws ParseException 
	 */
//	String LockCheck(SM_Users obj,String password) throws ParseException;
	/**
	 * 锁死用户
	 * @param loginname
	 * @return
	 */
//	Integer LockUser(SM_Users obj);
}
