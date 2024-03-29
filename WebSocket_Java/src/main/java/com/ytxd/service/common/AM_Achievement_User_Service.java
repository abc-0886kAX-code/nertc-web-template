package com.ytxd.service.common;

public interface AM_Achievement_User_Service {
	
	public Boolean RankValidate(String AchievementType, String AchievementId) throws Exception;
	/**
	 * 判断成果是否有人员
	 * @param AchievementType 成果表名
	 * @param AchievementId 成果Id
	 * @return Integer
	 * @throws Exception
	 */
	public Integer IsExist(String AchievementType, String AchievementId) throws Exception;
	/**
	 * 验证完成人员的排名和工作量 排名在后面的人员系数不能大于排名在前面人员的系数，但可以相等。
	 * @param AchievementType
	 * @param AchievementId
	 * @return
	 * @throws Exception
	 */
	public Boolean RankWorkloadValidate(String AchievementType, String AchievementId) throws Exception;
	/**
	 * 验证完成人员工作量 工作量之和不能超过100
	 * @param AchievementType
	 * @param AchievementId
	 * @return
	 * @throws Exception
	 */
	public Boolean WorkloadValidate(String AchievementType, String AchievementId) throws Exception;
}
