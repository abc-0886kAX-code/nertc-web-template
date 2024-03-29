package com.ytxd.dao.SystemManage;

import com.ytxd.model.SystemManage.SM_Users;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface SM_Users_Mapper {
	public List<SM_Users> Select_SM_Users(SM_Users obj);
	//public Integer Insert_SM_Users(SM_Users obj);
	public Integer Update_SM_Users(@Param("userid") String userid);
	public Integer Delete_SM_Users(@Param("userid") String[] userid);
	public List<HashMap<String, Object>> Select_SM_Users_Combobox(@Param("userid") String[] userid, @Param("truename") String truename);
	
	public List<HashMap> Select_Power_Logo(@Param("userid") String userid);
	public String Select_User_Role(@Param("userid") String userid);
	/*public Integer Delete_SM_Users_Role(SM_Users obj);
	public Integer Insert_SM_Users_Role(SM_Users obj);
	public Integer Delete_SM_Users_Role_RDM(SM_Users obj);
	public Integer Insert_SM_Users_Role_RDM(SM_Users obj);*/
	/**
	 * 删除多个用户的同一个角色权限
	 * @param obj obj.getuserid 用户id; obj.allrole 给用户赋的权限
	 * @return
	 */
	//public Integer Delete_More_Users_Role(SM_Users obj);
	/**
	 * 给多个用户赋一个角色权限
	 * @param obj obj.getuserid 用户id; obj.allrole 给用户赋的权限
	 * @return
	 */
	//public Integer Insert_More_Users_Role(SM_Users obj);
	/**
	 * 适用于需要到用户表中选择用户，同时会将已经选择的了的用户用mark标记出来
	 * @param obj
	 * @return
	 */
	//public List<SM_Users> Select_ChooseUser(SM_Users obj);
	/**
	 * 查询评审专家表列表数据，属于特殊订制
	 * @param obj
	 * @return
	 */
	//public List<SM_Users> Select_ReviewExpert(SM_Users obj);
	/**
	 *
	 * @Desription TODO 查询用户信息
	 * @param obj
	 * @return java.util.List<java.util.HashMap<java.lang.String,java.lang.Object>>
	 * @date 2023/11/6 10:17
	 * @Auther TY
	 */
	public List<HashMap<String, Object>> Select_SM_Users_Selected(HashMap<String,Object> obj);


}
