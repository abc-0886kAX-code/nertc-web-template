package com.ytxd.dao.Water_Common.RealData;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 工情相关
 */
@Mapper
public interface Worker_Situation_Mapper {
    /**
     * 取最新的工情数据 取最新的一条
     * @param obj
     * @return
     */
    List<Map<String,Object>> Select_Worker_List(Map<String,Object> obj);

    /**
     * 取实时的工情数据
     * @param obj
     * @return
     */
    List<Map<String,Object>> Select_Real_Worker_List(Map<String,Object> obj);
}
