package com.ytxd.service.SystemManage;

import com.ytxd.common.DataUtils;
import com.ytxd.dao.SystemManage.SM_FunctionTree_Mapper;
import com.ytxd.dao.SystemManage.SM_Users_Mapper;
import com.ytxd.model.MySqlData;
import com.ytxd.model.SystemManage.SM_FunctionTree;
import com.ytxd.service.CommonService;
import com.ytxd.service.impl.BaseServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service("sM_Users_ServiceImpl")
public class SM_Users_ServiceImpl extends BaseServiceImpl implements SM_Users_Service {
	@Autowired
	private SM_Users_Mapper SM_Users_Mapper;
	@Autowired
	private SM_FunctionTree_Mapper SM_FunctionTree_Mapper;
	@Autowired
	CommonService service;
	/*@Autowired
	private SM_Department_Mapper SM_Department_Mapper;*/

	/*@Override
	public Integer GetListCount(SM_Users obj) throws Exception{
		obj.setselectrowcount(0);
		List<SM_Users> listCount= SM_Users_Mapper.Select_SM_Users(obj);
		if(listCount != null && listCount.size() > 0){
			return ((SM_Users)listCount.get(0)).getselectrowcount();
		} else {
			return 0;
		}
	}
	
	@Override
	public Map<String, Object> GetList(SM_Users obj,SysUser user,String where) throws Exception{
		String whereString="";
		if(obj.getListview()!=null){
			if(obj.getListview().equals("SelectExpert")) {
				whereString+=" and sm_users.userid in (select userid from sm_users_role where roleid=3) ";
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
		List<SM_Users> list= SM_Users_Mapper.Select_SM_Users(obj);
		if(list!=null){
			Map<String, Object> result = new HashMap<String, Object>();
			if (obj.getPage() != null && obj.getRows() != null) {
				obj.setselectrowcount(0);
				List<SM_Users> listCount= SM_Users_Mapper.Select_SM_Users(obj);
				if(listCount != null && listCount.size() > 0){
					result.put("total",((SM_Users)listCount.get(0)).getselectrowcount());
				}
			}
			result.put("rows", list);
			return result;
		}
		return null;
	}

	@Override
	public SM_Users GetListById(String userid) throws Exception{
		SM_Users obj = new SM_Users();
		obj.setuserid(userid);
		List<SM_Users> list = SM_Users_Mapper.Select_SM_Users(obj);
		if(list != null && list.size() > 0){
			obj = list.get(0);
		}
	    return obj;
	}*/

	@Override
	public Integer Save(String userid) throws Exception {
		return SM_Users_Mapper.Update_SM_Users(userid);
	}

	@Override
	public Integer Delete(String userid) throws Exception{
		SM_Users_Mapper.Delete_SM_Users(userid.replace("'", "").split(","));
		return 1;
	}


	//***扩展***************************************************************************************
	@Override
	public List<HashMap<String, Object>> GetListForCombobox(String[] userid, String truename) {
		return SM_Users_Mapper.Select_SM_Users_Combobox(userid, truename);
	}
	/**
	 *
	 * @Desription TODO 查询用户信息
	 * @param obj
	 * @return java.util.List<java.util.HashMap<java.lang.String,java.lang.Object>>
	 * @date 2023/11/6 10:18
	 * @Auther TY
	 */
	@Override
	public List<HashMap<String, Object>> getSMUsersSelected(HashMap<String, Object> obj) {
		return  SM_Users_Mapper.Select_SM_Users_Selected(obj);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		MySqlData mySqlData = new MySqlData();
		mySqlData.setTableName("sm_users");
		mySqlData.setFieldWhere("loginname", username, "=");
		mySqlData.setFieldWhere("usertype", "00", "=");
		mySqlData.setFieldWhere("disabled", "01", "=");
		HashMap<String, Object> mapUser = null;
		try {
			mapUser = service.getMap(mySqlData);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (mapUser != null && !mapUser.isEmpty()) {
			String userid = DataUtils.getMapKeyValue(mapUser, "userid");
			String loginname = DataUtils.getMapKeyValue(mapUser, "loginname");
			String password = DataUtils.getMapKeyValue(mapUser, "password");
			
            List<SM_FunctionTree> SM_FunctionList = SM_FunctionTree_Mapper.Select_Power_FunctionTree(userid);
            List<GrantedAuthority> grantedAuthorities = new ArrayList <>();
            for (SM_FunctionTree sM_FunctionTree : SM_FunctionList) {
                if (sM_FunctionTree != null) {
                	if(StringUtils.isNotBlank(sM_FunctionTree.getnavigateurl())){
                		 GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(sM_FunctionTree.getnavigateurl());
                         grantedAuthorities.add(grantedAuthority);
                	}
                }
            }
            return new org.springframework.security.core.userdetails.User(loginname, password, grantedAuthorities);
        } else {
            throw new UsernameNotFoundException("username: " + username + " do not exist!");
        } 
	}
	
	/*@Override
	public Integer UpdatePinYin() throws Exception {
		List<SM_Users> list = SM_Users_Mapper.Select_SM_Users(null);
		if(list != null) {
			for (int i = 0; i < list.size(); i++) {
				SM_Users objUser = list.get(i);
				SM_Users obj = new SM_Users(); 
				obj.setuserid(objUser.getuserid());
				obj.settruename(objUser.gettruename());
				if(StringUtil.isNotEmpty(obj.gettruename())){
					obj.setfullpinyin(StringUtil.toFullPinyin(obj.gettruename()));
					obj.setsimplepinyin(StringUtil.toShortPinyin(obj.gettruename()));
				}
				SM_Users_Mapper.Update_SM_Users(obj);
			}
		}		
		return 1;
	}*/
	
	/*
	 * 保存用户角色权限
	 */
	/*@Override
	public Integer SaveRole(SM_Users obj,SysUser user) throws Exception {
		SM_Users_Mapper.Delete_SM_Users_Role(obj);
		SM_Users_Mapper.Insert_SM_Users_Role(obj);
		
		return 1;
	}*/
	/**
	 * 适用于需要到用户表中选择用户，同时会将已经选择的了的用户用mark标记出来
	 * @param obj
	 * @param user
	 * @param where
	 * @return
	 * @throws Exception
	 */
	/*@Override
	public Map<String, Object> GetChooseUserList(SM_Users obj, SysUser user, String where) throws Exception {
		if(where!=null)
		{
			obj.setWhereString(where);
		}
		obj = obj.GetdecodeUtf();
		List<SM_Users> list= SM_Users_Mapper.Select_ChooseUser(obj);
		if(list!=null){
			Map<String, Object> result = new HashMap<String, Object>();
			if (obj.getPage() != null && obj.getRows() != null) {
				obj.setselectrowcount(0);
				List<SM_Users> listCount= SM_Users_Mapper.Select_ChooseUser(obj);
				if(listCount != null && listCount.size() > 0){
					result.put("total",((SM_Users)listCount.get(0)).getselectrowcount());
				}
			}
			result.put("rows", list);
			return result;
		}
		return null;
	}*/

	/*@Override
	public String LockCheck(SM_Users obj,String password) throws ParseException {
			String islock=obj.getIslock();
			if("01".equals(islock)){//判断用户是否锁死
				String locktime=obj.getLocktime();
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");  
		        String todayStr = format.format(new Date());  
		        Date today = format.parse(todayStr);
		        Date lockDate=format.parse(locktime);
		      //如果锁死，判断锁死时间，如果是一天前，则解锁,并重置密码错误次数
		        if((today.getTime()-lockDate.getTime())>=86400000){ //如果锁定是当前登录时间的前一天或更早，解锁
		        	obj.setIslock("00");
		        	obj.setWrongtimes(0);
		        	SM_Users_Mapper.Update_SM_Users(obj);
		        	return "00";
		        }else{
		        	//如果锁死状态下密码错误，继续累加错误次数
		        	if(!password.toLowerCase().equals(obj.getpassword().toLowerCase())){
		        		obj.setWrongtimes(obj.getWrongtimes()+1);
		        		SM_Users_Mapper.Update_SM_Users(obj);
		        	}
		        	return "01";
		        }
			}else{
				return "00";
			}
	}

	@Override
	public Integer LockUser(SM_Users obj) {
		Integer wrongtimes=obj.getWrongtimes()+1;
		if(wrongtimes==5){
			obj.setIslock("01");
			obj.setLocktime(newDate.getDate("yyyyMMdd"));
		}
		obj.setWrongtimes(wrongtimes);
		SM_Users_Mapper.Update_SM_Users(obj);
		return 5-wrongtimes;
	}*/
}
