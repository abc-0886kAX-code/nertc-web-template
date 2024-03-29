package com.ytxd.dao.Water_Common.RealData;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 水情相关
 */
@Mapper
public interface Water_Situation_Mapper {
    /**
     * 取最新的水情数据 取最新的一条
     * @param obj
     * @return
     */
    public List<Map<String,Object>> Select_Water_Level_List(Map<String,Object> obj);

    /**
     * 取实时水情数据
     * @param obj
     * @return
     */
    public List<Map<String,Object>> Select_Real_Water_Level_List(Map<String,Object> obj);

    /**
     * 取实时水情数据 按小时展示
     * @param obj
     * @return
     */
    public List<Map<String,Object>> Select_Real_Water_Level_List_Hour(Map<String,Object> obj);

    /**
     * 取实时水情数据 按天展示
     * @param obj
     * @return
     */
    public List<Map<String,Object>> Select_Real_Water_Level_List_Day(Map<String,Object> obj);

    /**
     * 获取前后3天的水位雨量信息
     * @param obj
     * @return
     */
    public List<Map<String,Object>> Select_Water_Level_Info_List(Map<String,Object> obj);

    /**
     * 获取时间段内的小时库容水位信息
     * @param obj
     * @return
     */
    public List<Map<String,Object>> Select_ST_Rsvr_R_List_ByHour(Map<String,Object> obj);

    public List<Map<String,Object>> Select_ST_Rsvr_RLastData(Map<String,Object> obj);
    /**
     *
     * @Desription TODO 查询同期数据
     * @param obj
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     * @date 2024/2/23 14:15
     * @Auther TY
     */
    public List<Map<String,Object>> Select_TQ_Real_Water_Level_List_Hour(Map<String,Object> obj);


    List<Map<String , Object>> selectExceedLevee(Map<String,Object> map);


    List<Map<String , Object>> selectExceedGuaranteed(Map<String,Object> map);


    List<Map<String , Object>> selectExceedAlertLevel(Map<String,Object> map);


    List<Map<String , Object>> selectRiverStationInfo(Map<String,Object> map);


    Map<String , Object> selectExceedNum(Map<String,Object> map);



}
