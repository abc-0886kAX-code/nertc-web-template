package com.ytxd.dao.API.Lake_Healthy;

import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 河湖健康
 */
@Mapper
public interface Lake_Healthy_Mapper {
    /**
     * 查询幸福河湖信息
     * @param obj
     * @return
     */
    public List<Map<String,Object>> Select_Lake_Healthy_List(Map<String,Object> obj);

    /**
     * 人数统计
     * @param obj
     * @return
     */
    public List<Map<String,Object>> Select_People_Statistics(Map<String,Object> obj);

    /**
     * 水资源统计
     * @param obj
     * @return
     */
    public Map<String,Object> Select_Water_Resource_List(Map<String,Object> obj);

    /**
     * 幸福河湖二维码
     * @param obj
     * @return
     */
    public List<Map<String,Object>> Select_Lake_Bliss_List(Map<String,Object> obj);

    /**
     * 幸福河湖卫星遥感
     * @param obj
     * @return
     */
    public List<Map<String,Object>> Select_RemoteSensing_List(Map<String,Object> obj);
    /**
     *
     * @Desription TODO 幸福河湖卫星遥感(按月份查询)
     * @param obj
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     * @date 2023/9/21 10:39
     * @Auther TY
     */
    @MapKey("id")
    public List<Map<String,Object>> Select_RemoteSensing_ListByMonth(Map<String,Object> obj);

    /**
     * 获取每个类型最后一条数据
     * @param obj
     * @return
     */
    public List<Map<String,Object>> Select_Last_RemoteSensing_List(Map<String,Object> obj);
}
