package com.ytxd.service.common;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.ytxd.model.MySqlData;
import com.ytxd.service.CommonService;
import com.ytxd.service.impl.BaseServiceImpl;

@Service("aM_Achievement_User_ServiceImpl")
public class AM_Achievement_User_ServiceImpl extends BaseServiceImpl implements AM_Achievement_User_Service {
	@Resource
	private CommonService service;
	//***扩展***************************************************************************************
	
	/**
	 * 验证完成人员的排名是否顺序录入
	 * @param AchievementType 成果分类
	 * @param AchievementId 成果ID
	 * @return 验证通过返回true，不通过返回false
	 */
	@Override
    public Boolean RankValidate(String AchievementType, String AchievementId) throws Exception {
        //先判断是否有排名
        MySqlData mySqlData = new MySqlData();
        mySqlData.setSql("select count(1) rowcount ");
        mySqlData.setSql("from SM_BuiltItem ");
        mySqlData.setSql("where TableName='AM_Achievement_User' AND Flag=1 AND FieldName='Rank' ");
        mySqlData.setSql("and (CONCAT(',',ViewName,',') like '%,add,%' or CONCAT(',',ViewName,',') like CONCAT('%,',");
        mySqlData.setSqlValue(AchievementType);
        mySqlData.setSql(",',%')) ");
        if(Integer.parseInt(service.getMapByKey(mySqlData).get("rowcount").toString()) == 0) {
        	//说明录入界面没有排序字段。
        	return true;
        }

        //有排名就验证完成人员的排名是否顺序录入
        mySqlData = new MySqlData();
        mySqlData.setSql("select count(1) rowcount from (");
        mySqlData.setSql("select row_number() over(order by `Rank`) rownum,`Rank`+0 `Rank` ");
        mySqlData.setSql("from AM_Achievement_User ");
        mySqlData.setSql("where AchievementType=");
        mySqlData.setSqlValue(AchievementType);
        mySqlData.setSql(" and AchievementId=");
        mySqlData.setSqlValue(AchievementId);
        mySqlData.setSql(" and (AssessId is null or AssessId=0) and `Rank`!='99') aa ");
        mySqlData.setSql("where rownum!=`Rank`");

        if(Integer.parseInt(service.getMapByKey(mySqlData).get("rowcount").toString()) > 0) {
            return false;
        } else {
            return true;
        }
    }
	
	@Override
	public Integer IsExist(String AchievementType, String AchievementId) throws Exception {
        if (StringUtils.isBlank(AchievementId) || StringUtils.isBlank(AchievementType)) {
        	//如果没有值提示错误
            return 0;
        }
        MySqlData mySqlData = new MySqlData();
        mySqlData.setSql("select count(1) usercount ");
        mySqlData.setSql("from AM_Achievement_User ");
        mySqlData.setSql("where AchievementType=");
        mySqlData.setSqlValue(AchievementType);
        mySqlData.setSql(" and AchievementId=");
        mySqlData.setSqlValue(AchievementId);
        mySqlData.setSql(" and (AssessId is null or AssessId=0) ");
        
        return Integer.parseInt(service.getMapByKey(mySqlData).get("usercount").toString());
    }
	
	/*
	 * 验证完成人员的排名和工作量 排名在后面的人员系数不能大于排名在前面人员的系数，但可以相等。
	 */
	@Override
    public Boolean RankWorkloadValidate(String AchievementType, String AchievementId) throws Exception {
		if (StringUtils.isBlank(AchievementId) || StringUtils.isBlank(AchievementType)) {
        	//如果没有值提示错误
			return false;
        }
		MySqlData mySqlData = new MySqlData();
        mySqlData.setSql("select count(1) rowcount from (");
        mySqlData.setSql("select row_number() over(partition by AchievementType,AchievementId order by Workload desc,Rank) rownumber,Rank,Workload ");
        mySqlData.setSql("from AM_Achievement_User ");
        mySqlData.setSql("where Rank<7 and AchievementType=");
        mySqlData.setSqlValue(AchievementType);
        mySqlData.setSql(" and AchievementId=");
        mySqlData.setSqlValue(AchievementId);
        mySqlData.setSql(" and (AssessId is null or AssessId=0)) aa ");        
        mySqlData.setSql("where rownumber!=Rank");
        mySqlData.setSql("");
        if(Integer.parseInt(service.getMapByKey(mySqlData).get("rowcount").toString()) > 0) {
            return false;
        } else {
            return true;
        }
    }

    /*
     * 验证完成人员工作量 工作量之和不能超过100
     */
	@Override
    public Boolean WorkloadValidate(String AchievementType, String AchievementId) throws Exception {
		if (StringUtils.isBlank(AchievementId) || StringUtils.isBlank(AchievementType)) {
        	//如果没有值提示错误
			return false;
        }
		MySqlData mySqlData = new MySqlData();
        mySqlData.setSql("select ifnull(sum(Workload),0) workload ");
        mySqlData.setSql("from AM_Achievement_User ");
        mySqlData.setSql("where AchievementType=");
        mySqlData.setSqlValue(AchievementType);
        mySqlData.setSql(" and AchievementId=");
        mySqlData.setSqlValue(AchievementId);
        mySqlData.setSql(" and (AssessId is null or AssessId=0) ");
        mySqlData.setSql("");
        if(Double.parseDouble(service.getMapByKey(mySqlData).get("workload").toString()) != 100) {
            return false;
        } else {
            return true;
        }
    }
}
