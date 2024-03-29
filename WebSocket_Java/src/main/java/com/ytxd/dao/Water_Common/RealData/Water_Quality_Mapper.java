package com.ytxd.dao.Water_Common.RealData;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 工情相关
 */
@Mapper
public interface Water_Quality_Mapper {
    /**
     * 取最新的水质数据 取最新的一条
     * @param obj
     * @return
     */
    List<Map<String,Object>> Select_Water_Quality_List(Map<String,Object> obj);

    /**
     * 取实时的水质数据
     * @param obj
     * @return
     */
    List<Map<String,Object>> Select_Real_Water_Quality_List(Map<String,Object> obj);

    /**
     * 取某时间段内的月均值统计
     * @param obj
     * @return
     */
    List<Map<String,Object>> Select_Real_Water_Quality_List_Month(Map<String,Object> obj);
}
