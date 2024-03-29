package com.ytxd.service.Subhall.RiverAndLake;

import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * 河湖长效管护
 */
public interface RiverAndLake_Service {
    /**
     * 查询视频分区信息
     * @param obj
     * @return
     */
    public PageInfo GetRLVideoPartitionList(Map<String,Object> obj) throws Exception;

    /**
     * 视频站点统计信息
     * @param obj
     * @return
     * @throws Exception
     */
    public Map<String,Object> GetVideoStatisticsInfo(Map<String,Object> obj) throws Exception;

    /**
     * 按时间查询值班人员信息
     * @param obj
     * @return
     * @throws Exception
     */
    public PageInfo GetRLArrangeList(Map<String,Object> obj) throws Exception;

    /**
     * 获取基础设施信息
     * @param obj
     * @return
     * @throws Exception
     */
    public PageInfo GetRLInfrastructureList(Map<String,Object> obj) throws Exception;

    /**
     * 获取基础设施统计信息
     * @param obj
     * @return
     * @throws Exception
     */
    public PageInfo GetRLInfrastructureStatistics(Map<String,Object> obj) throws Exception;

    /**
     * 查询高塔报警的统计预警信息
     * @param obj
     * @return
     * @throws Exception
     */
    public PageInfo GetStAlarmRStatistics(Map<String,Object> obj) throws Exception;

    /**
     * 查询两个小时内的报警信息
     * @param obj
     * @return
     * @throws Exception
     */
    public PageInfo GetStAlarmRList(Map<String,Object> obj) throws Exception;

    /**
     * 根据ID查询报警信息
     * @param obj
     * @return
     * @throws Exception
     */
    public Map<String,Object> GetStAlarmRInfoByID(Map<String,Object> obj) throws Exception;

    /**
     * 处理报警信息
     * @param obj
     * @return
     * @throws Exception
     */
    public Integer UpdateStAlarmR(Map<String,Object> obj) throws Exception;

    /**
     * 查询组织架构信息
     * @param obj
     * @return
     * @throws Exception
     */
    public PageInfo GetRLStructureInfo(Map<String,Object> obj) throws Exception;

    /**
     * 获取河湖信息
     * @param obj
     * @return
     * @throws Exception
     */
    public PageInfo GetRLInformationList(Map<String,Object> obj) throws Exception;

    /**
     * 获取战点的详细信息
     * @param obj
     * @return
     * @throws Exception
     */
    public Map<String,Object> GetSiteIntroduceInfo(Map<String,Object> obj) throws Exception;

    /**
     * 芙蓉溪打卡-事件列表
     * @param obj
     * @return
     * @throws Exception
     */
    public PageInfo GetRLPatrolList(Map<String,Object> obj) throws Exception;

    PageInfo selectRlPersonnel(Map<String,Object> obj);


}
