package com.ytxd.dao.Water_Common.RealData;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 雨情相关信息
 */

public interface Rain_Situation_Mapper {
    /**
     * 取所有站点或者某些站点某一段时间内的累计雨量
     * @param obj
     * @return
     */
    public List<Map<String,Object>> Select_Rain_Cumulative_List(Map<String,Object> obj);

    /**
     * 根据站点获取某一段时间内实时降雨数据
     * @param obj
     * @return
     */
    public List<Map<String,Object>> Select_Real_Rain_List(Map<String,Object> obj);
    /**
     *
     * @Desription TODO 获取年度降雨量
     * @param obj
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     * @date 2024/1/29 14:28
     * @Auther TY
     */
    public List<Map<String,Object>> Select_Real_Rain_ListByYear(Map<String,Object> obj);

    /**
     * 根据站点获取某一段时间内实时降雨数据以小时进行展示
     * @param obj
     * @return
     */
    public List<Map<String,Object>> Select_Real_Rain_List_Hour(Map<String,Object> obj);

    /**
     * 根据站点获取某一段时间内实时降雨数据按天进行展示
     * @param obj
     * @return
     */
    public List<Map<String,Object>> Select_Real_Rain_List_Day(Map<String,Object> obj);

    /**
     * 获取实时雨情的下拉选择框
     * @param obj
     * @return
     */
    public List<Map<String,Object>> Select_Selection_Item_List(Map<String,Object> obj);

    /**
     * 获取预警的雨量数据
     * @param obj
     * @return
     */
    public List<Map<String,Object>> Select_Early_Warning_List(Map<String,Object> obj);

    /**
     * 获取前后3天的雨情信息
     * @param obj
     * @return
     */
    public List<Map<String,Object>> Select_Rain_Level_Info_List(Map<String,Object> obj);

    /**
     * 通过时间查询8-8点的累计降雨量
     * @param obj
     * @return
     */
    public List<Map<String,Object>> Select_Rain_Day_Statistics(Map<String,Object> obj);

    /**
     * 写入报警信息
     * @param obj
     * @return
     */
    public Integer Insert_WaterLevel_Warning(Map<String,Object> obj);
    /**
     *
     * @Desription TODO 代表站点分析
     * @param obj
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     * @date 2024/1/29 15:17
     * @Auther TY
     */
    public List<Map<String,Object>> Select_RealRainListByRepresentative(Map<String,Object> obj);

}
