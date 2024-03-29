package com.ytxd.dao.Subhall.RiverAndLake;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 河湖长效管护
 */
@Mapper
public interface RiverAndLake_Mapper {
    /**
     * 查询视频分区信息
     * @param obj
     * @return
     */
    public List<Map<String,Object>> Select_RL_Video_Partition_List(Map<String,Object> obj);

    /**
     * 视频站点统计信息
     * @param obj
     * @return
     */
    public Map<String,Object> Select_Video_Statistics_Info(Map<String,Object> obj);

    /**
     * 按时间查询值班人员信息
     * @param obj
     * @return
     */
    public List<Map<String,Object>> Select_RL_Arrange_List(Map<String,Object> obj);

    /**
     * 获取基础设施信息
     * @param obj
     * @return
     */
    public List<Map<String,Object>> Select_RL_Infrastructure_List(Map<String,Object> obj);

    /**
     * 获取基础设施统计信息
     * @param obj
     * @return
     */
    public List<Map<String,Object>> Select_RL_Infrastructure_Statistics(Map<String,Object> obj);

    /**
     * 查询高塔报警的统计预警信息
     * @param obj
     * @return
     */
    public List<Map<String,Object>> Select_St_Alarm_R_Statistics(Map<String,Object> obj);

    /**
     * 查询两个小时内的报警信息
     * @param obj
     * @return
     */
    public List<Map<String,Object>> Select_St_Alarm_R_List(Map<String,Object> obj);

    /**
     * 处理报警信息
     * @param obj
     * @return
     */
    public Integer Update_St_Alarm_R(Map<String,Object> obj);

    /**
     * 查询组织架构信息
     * @param obj
     * @return
     */
    public List<Map<String,Object>> Select_RL_Structure_Info(Map<String,Object> obj);

    /**
     * 获取河湖信息
     * @param obj
     * @return
     */
    public List<Map<String,Object>> Select_RL_Information_List(Map<String,Object> obj);

    /**
     * 获取站点详细详细
     * @param obj
     * @return
     */
    public List<Map<String,Object>> Select_Site_Introduce_Info(Map<String,Object> obj);

    /**
     * 河道站防洪指标表
     * @param obj
     * @return
     */
    public Map<String,Object> Select_Site_Rvfcch_Info(Map<String,Object> obj);

    /**
     * 库(湖)站防洪指标表
     * @param obj
     * @return
     */
    public Map<String,Object> Select_Site_Rsvrfcch_Info(Map<String,Object> obj);

    /**
     * 芙蓉溪打卡-事件列表
     * @param obj
     * @return
     */
    public List<Map<String,Object>> Select_RL_Patrol_List(Map<String,Object> obj);


    List<Map<String,Object>> selectRlPersonnel(Map<String,Object> obj);


    int insertStAlarmR(Map<String, Object> map);

    int insertRlPatrol(Map<String, Object> map);

}
