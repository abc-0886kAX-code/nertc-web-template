package com.ytxd.dao.Subhall.Aquatic_Ecology;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 水生态-检测预报
 */
@Mapper
public interface Monitoring_Forecasting_Mapper {
    /**
     * 获取芙蓉溪的水位和水质等级
     * @param obj
     * @return
     */
    public List<Map<String,Object>> Select_MH_Water_SW_SZ_Info(Map<String,Object> obj);

    /**
     * 获取最新的水质预警信息
     * @param obj
     * @return
     */
    public List<Map<String,Object>> Select_MH_Water_Quality_List(Map<String,Object> obj);

    /**
     * 获取时序水质信息
     * @param obj
     * @return
     */
    public List<Map<String,Object>> Select_MH_Water_Quality_Info_List(Map<String,Object> obj);
    /**
     * 获取时序水质信息
     * @param obj
     * @return
     */
    public List<Map<String,Object>> Select_MH_Water_Quality_Info_List_Hyperspectra(Map<String,Object> obj);

    /**
     * 水环境活水预案 -> 下拉框
     * @param obj
     * @return
     */
    public List<Map<String,Object>> Select_Storm_Plan_Environment_Selected(Map<String,Object> obj);

    /**
     * 水环境活水预案 -> 预案信息
     * @param obj
     * @return
     */
    public List<Map<String,Object>> Select_Storm_Plan_Environment_List(Map<String,Object> obj);

    /**
     * 水生态补水预案 -> 下拉框
     * @param obj
     * @return
     */
    public List<Map<String,Object>> Select_Storm_Plan_Ecology_Selected(Map<String,Object> obj);

    /**
     * 水生态补水预案 -> 预案信息
     * @param obj
     * @return
     */
    public List<Map<String,Object>> Select_Storm_Plan_Ecology_List(Map<String,Object> obj);

    /**
     * 获取时序数据
     * @param obj
     * @return
     */
    public List<Map<String,Object>> Select_Storm_Node_R_List(Map<String,Object> obj);

    /**
     * 获取存在映射关系的站点信息
     * @param obj
     * @return
     */
    public List<Map<String,Object>> Select_Storm_Node_Contrast(Map<String,Object> obj);

    /**
     * 获取高光谱水质站的水位信息
     * @param obj
     * @return
     */
    public List<Map<String,Object>> Select_Quality_Z_List(Map<String,Object> obj);

    /**
     * 获取默认的水生态预案ID
     * @param obj
     * @return
     */
    public String Select_Default_Ecology(Map<String,Object> obj);

    /**
     * 获取默认水环境预案
     * @param obj
     * @return
     */
    public Map<String,Object> Select_Default_Environment(Map<String,Object> obj);
    /**
     *
     * @Desription TODO 时序数据(原始)
     * @param obj
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     * @date 2023/12/13 14:50
     * @Auther TY
     */
    public List<Map<String,Object>> Select_MH_Water_Quality_Info_ListByTime(Map<String,Object> obj);
    /**
     *
     * @Desription TODO 时序数据(小时)
     * @param obj
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     * @date 2023/12/13 14:50
     * @Auther TY
     */
    public List<Map<String,Object>> Select_MH_Water_Quality_Info_ListByHour(Map<String,Object> obj);
    /**
     *
     * @Desription TODO 时序数据(日)
     * @param obj
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     * @date 2023/12/13 14:51
     * @Auther TY
     */
    public List<Map<String,Object>> Select_MH_Water_Quality_Info_ListByDay(Map<String,Object> obj);
    /**
     *
     * @Desription TODO 时序数据(月)
     * @param obj
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     * @date 2023/12/13 14:51
     * @Auther TY
     */
    public List<Map<String,Object>> Select_MH_Water_Quality_Info_ListByMonth(Map<String,Object> obj);

}
