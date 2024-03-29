package com.ytxd.dao.Subhall;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 水安全
 */
@Mapper
public interface Water_Safety_Mapper {
    /**
     * 获取前后3天雨量水位数据
     * @param obj
     * @return
     */
    public List<Map<String,Object>> Select_Storm_Water_Level_List(Map<String,Object> obj);

    /**
     * 获取工情信息
     * @param obj
     * @return
     */
    public List<Map<String,Object>> Select_Storm_Gate_List(Map<String,Object> obj);

    /**
     * 获取预案为空的工情信息
     * @param obj
     * @return
     */
    public List<Map<String,Object>> Select_Storm_Gate_List_NullPlan(Map<String,Object> obj);

    /**
     * 获取预报水位信息
     * @param obj
     * @return
     */
    public List<Map<String,Object>> Select_PredictionWL_List(Map<String,Object> obj);

    /**
     * 获取动态预警的发布预警信息和未发布预警信息
     * @param obj
     * @return
     */
    public List<Map<String,Object>> Select_Warning_Release_List(Map<String,Object> obj);

    /**
     * 写入预警发布
     * @param obj
     * @return
     */
    public Integer Insert_Waring_Release(Map<String,Object> obj);

    /**
     * 获取预报调度方案的下拉框列表
     * @param obj
     * @return
     */
    public List<Map<String,Object>> Select_Safety_Selected_List(Map<String,Object> obj);

    /**
     * 获取默认的方案详细
     * @param obj
     * @return
     */
    public Map<String,Object> Select_Default_Plan_Info(Map<String,Object> obj);

    /**
     * 获取指定时间最相近的方案
     * @param obj
     * @return
     * @throws Exception
     */
    public Map<String,Object> Select_Near_Plan_Info(Map<String,Object> obj);
}
