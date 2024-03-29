package com.ytxd.dao.API.Storm_Plan;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 调度预案
 */
@Mapper
public interface Storm_Plan_Mapper {
    /**
     * 调度预案查询
     * @param obj
     * @return
     */
    List<Map<String,Object>> Select_Storm_Plan_List(Map<String,Object> obj);
    List<Map<String,Object>> Select_Storm_Plan_List_Local(Map<String,Object> obj);

    /**
     * 获取定时预案列表信息
     * @param obj
     * @return
     */
    List<Map<String,Object>> Select_Storm_Plan_Regular_List(Map<String,Object> obj);

    /**
     *
     * @Desription TODO 调度预案添加
     * @param obj
     * @return java.lang.Integer
     * @date 2023/11/14 15:15
     * @Auther TY
     */
    Integer Insert_Storm_Plan(Map<String,Object> obj);
    /**
     *
     * @Desription TODO 调度预案添加
     * @param obj
     * @return java.lang.Integer
     * @date 2023/11/14 15:15
     * @Auther TY
     */
    Integer Insert_Storm_Plan_Local(Map<String,Object> obj);

    /**
     *
     * @Desription TODO 调度预案修改
     * @param obj
     * @return java.lang.Integer
     * @date 2023/11/14 15:17
     * @Auther TY
     */
    Integer Update_Storm_Plan(Map<String,Object> obj);
    /**
     *
     * @Desription TODO 自建调度预案修改
     * @param obj
     * @return java.lang.Integer
     * @date 2023/11/14 15:16
     * @Auther TY
     */
    Integer Update_Storm_Plan_Local(Map<String,Object> obj);

    /**
     * 调度预案删除
     * @param obj
     * @return
     */
    Integer Delete_Storm_Plan(Map<String,Object> obj);
    /**
     *
     * @Desription TODO 调度预案删除
     * @param obj
     * @return java.lang.Integer
     * @date 2023/11/14 15:26
     * @Auther TY
     */
    Integer Delete_Storm_Plan_Local(Map<String,Object> obj);
    /**
     *
     * @Desription TODO 删除遗留的数据
     * @param obj
     * @return java.lang.Integer
     * @date 2023/11/14 15:26
     * @Auther TY
     */
    Integer Delete_storm_node_r(Map<String,Object> obj);
    /**
     *
     * @Desription TODO 添加新的数据
     * @param obj
     * @return java.lang.Integer
     * @date 2023/11/14 15:27
     * @Auther TY
     */
    Integer Insert_storm_node_r(Map<String,Object> obj);

    Map<String, Object> Get_Forecast_Rainfall();

    /**
     * 获取最合适的预案
     * @return
     */
    Map<String, Object> Get_Most_Suitable_Plan(Map<String,Object> obj);
}
