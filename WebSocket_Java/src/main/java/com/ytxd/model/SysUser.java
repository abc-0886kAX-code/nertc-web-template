package com.ytxd.model;

import com.ytxd.common.util.Constant;
import org.apache.commons.lang.StringUtils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * session信息
 */
public class SysUser implements Serializable{
	private static final long serialVersionUID = 1796796477631037971L;
	
	private String UserId;
	private String LoginName;
	private String TrueName;
	private String DeptId;
	private String DeptName;
	private String Style;
	private String UserType;
	private String RoleType;

	private String Birthday;
	private String TitleGrade;
	private String RoleId;
	private String SystemType;
	private String sessionId;
	private String Sex;
	private String TimeSet;
	private String TimeSetId;

	private String Degree;
	private HashMap<String, Object> PowerLogo;
	private String ViewName;
	private Constant.LoginType LoginType;
	private List<HashMap<String, Object>> roles;
	private List<Map<String, Object>> routes;
	private String register_type;
	private String useroletype;
	private String unittype;

	public List<HashMap<String, Object>> getRoles() {
		return roles;
	}

	public String getDegree() {
		return Degree;
	}

	public void setDegree(String degree) {
		Degree = degree;
	}


	public String getTimeSet() {
		return TimeSet;
	}

	public void setTimeSet(String timeSet) {
		TimeSet = timeSet;
	}

	public String getTimeSetId() {
		return TimeSetId;
	}

	public void setTimeSetId(String timeSetId) {
		TimeSetId = timeSetId;
	}

	public String getTitlegrade() {
		return TitleGrade;
	}
	public void setTitlegrade(String titleGrade) {
		TitleGrade = titleGrade;
	}

	public String  getBirthday() {
		return Birthday;
	}
	public void setBirthday(String  birthday) {
		Birthday = birthday;
	}
	public void setRoles(List<HashMap<String, Object>> roles) {
		this.roles = roles;
	}
	public List<Map<String, Object>> getRoutes() {
		return routes;
	}
	public void setRoutes(List<Map<String, Object>> routes) {
		this.routes = routes;
	}
	public Constant.LoginType getLoginType() {
		return LoginType;
	}
	public void setLoginType(Constant.LoginType loginType) {
		LoginType = loginType;
	}
	public String getUserId() {
		return UserId;
	}
	public void setUserId(String userId) {
		UserId = userId;
	}
	public String getLoginName() {
		return LoginName;
	}
	public void setLoginName(String loginName) {
		LoginName = loginName;
	}
	public String getTrueName() {
		return TrueName;
	}
	public void setTrueName(String trueName) {
		TrueName = trueName;
	}
	public String getDeptId() {
		return DeptId;
	}
	public void setDeptId(String deptId) {
		DeptId = deptId;
	}
	public String getDeptName() {
		return DeptName;
	}
	public void setDeptName(String deptName) {
		DeptName = deptName;
	}
	public String getStyle() {
		return Style;
	}
	public void setStyle(String style) {
		Style = style;
	}
	public String getUserType() {
		return UserType;
	}
	public void setUserType(String userType) {
		UserType = userType;
	}
	public String getRoleType() {
		return RoleType;
	}
	public void setRoleType(String roleType) {
		RoleType = roleType;
	}
	public String getRoleId() {
		return RoleId;
	}
	public void setRoleId(String roleId) {
		RoleId = roleId;
	}
	public String getSystemType() {
		return SystemType;
	}
	public void setSystemType(String systemType) {
		SystemType = systemType;
	}
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public String getSex() {
		return Sex;
	}
	public void setSex(String sex) {
		Sex = sex;
	}
	public HashMap<String, Object> getPowerLogo() {
		return PowerLogo;
	}
	public void setPowerLogo(HashMap<String, Object> powerLogo) {
		PowerLogo = powerLogo;
	}
	public boolean getFunctionPermissions(String powerCode) {
//		return PowerLogo.containsKey(powerCode.toLowerCase());
		return true;
	}
	public String getViewName() {
		if(StringUtils.isBlank(ViewName)) {
			return "01";
		} else {
			return ViewName;
		}
	}
	public void setViewName(String viewName) {
		ViewName = viewName;
	}
	public String getDepartmentId() {
		return "select DepartmentId from SM_Department where DepartmentId like '" + DeptId + "%' union all select DepartmentId from SM_Users_Department where UserId='" + UserId + "'"; 
	}

	@Override
	public String toString() {
		return "SysUser{" +
				"UserId='" + UserId + '\'' +
				", LoginName='" + LoginName + '\'' +
				", TrueName='" + TrueName + '\'' +
				", DeptId='" + DeptId + '\'' +
				", DeptName='" + DeptName + '\'' +
				", Style='" + Style + '\'' +
				", UserType='" + UserType + '\'' +
				", RoleType='" + RoleType + '\'' +
				", Birthday='" + Birthday + '\'' +
				", TitleGrade='" + TitleGrade + '\'' +
				", RoleId='" + RoleId + '\'' +
				", SystemType='" + SystemType + '\'' +
				", sessionId='" + sessionId + '\'' +
				", Sex='" + Sex + '\'' +
				", TimeSet='" + TimeSet + '\'' +
				", TimeSetId='" + TimeSetId + '\'' +
				", Degree='" + Degree + '\'' +
				", PowerLogo=" + PowerLogo +
				", ViewName='" + ViewName + '\'' +
				", LoginType=" + LoginType +
				", roles=" + roles +
				", routes=" + routes +
				'}';
	}

	public String getRegister_type() {
		return register_type;
	}

	public void setRegister_type(String register_type) {
		this.register_type = register_type;
	}

	public String getUseroletype() {
		return useroletype;
	}

	public void setUseroletype(String useroletype) {
		this.useroletype = useroletype;
	}

	public String getUnittype() {
		return unittype;
	}

	public void setUnittype(String unittype) {
		this.unittype = unittype;
	}
}
