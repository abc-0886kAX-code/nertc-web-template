package com.ytxd.dao.Water_Rove;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 漫游需要接口
 */
@Mapper
public interface Water_Rove_Mapper {
    /**
     * 获取漫游中所需要的信息
     * @param obj
     * @return
     */
    public Map<String,Object> Select_Storm_Plan_Info(Map<String,Object> obj);
    /**
     * 获取预案为空的漫游中所需要的信息
     * @param obj
     * @return
     */
    public Map<String,Object> Select_Storm_Plan_Info_NullPlan(Map<String,Object> obj);
    /**
     * 获取初始化化的水位站点信息
     * @param obj
     * @return
     */
    public List<Map<String,Object>> Select_Init_Site_Info_List(Map<String,Object> obj);

    /**
     * 获取时间轴和站点的水位数据
     * @param obj
     * @return
     */
    public List<Map<String,Object>> Select_TMAxis_Info_List(Map<String,Object> obj);

    /**
     * 获取方案为空的时间轴和站点的水位数据
     * @param obj
     * @return
     */
    public List<Map<String,Object>> Select_TMAxis_Info_List_NullPlan(Map<String,Object> obj);

    /**
     * 获取湖体的时序数据
     * @param obj
     * @return
     */
    public List<Map<String,Object>> Select_TMAxis_Section_List(Map<String,Object> obj);
    /**
     * 获取预案为空湖体的时序数据
     * @param obj
     * @return
     */
    public List<Map<String,Object>> Select_TMAxis_Section_List_NullPlan(Map<String,Object> obj);

    /**
     * 获取每个站点的时序数据
     * @param obj
     * @return
     */
    public List<Map<String,Object>> Select_TMAxis_Site_List(Map<String,Object> obj);

    /**
     * 获取预案为空的时序数据
     * @param obj
     * @return
     */
    public List<Map<String,Object>> Select_TMAxis_Site_List_NullPlan(Map<String,Object> obj);

    /**
     * 获取两个闸门的时序流量数据
     * @param obj
     * @return
     */
    public List<Map<String,Object>> Select_Sluice_Gate_List(Map<String,Object> obj);
    /**
     * 获取预案为空时两个闸门的时序流量数据
     * @param obj
     * @return
     */
    public List<Map<String,Object>> Select_Sluice_Gate_List_NullPlan(Map<String,Object> obj);

    /**
     * 查询默认的流场方案
     * @param obj
     * @return
     */
    public Map<String,Object> Select_Default_Wind_Info(Map<String,Object> obj);

    /**
     * 获取流场的数据
     * @param plan_id
     * @return
     */
    public List<Map<String,Object>> Select_Default_wind_r_List(@Param("plan_id") String plan_id);
}
