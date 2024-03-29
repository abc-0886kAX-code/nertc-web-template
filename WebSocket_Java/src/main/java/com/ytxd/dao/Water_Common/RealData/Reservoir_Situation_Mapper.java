package com.ytxd.dao.Water_Common.RealData;

import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @Classname Reservoir_Situation_Mapper
 * @Author TY
 * @Date 2023/12/22 15:37
 * @Description TODO 水库基本信息
 */
@Mapper
public interface Reservoir_Situation_Mapper {
    /**
     *
     * @Desription TODO 查询实时水库数据
     * @param obj
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     * @date 2023/12/22 15:55
     * @Auther TY
     */
    @MapKey("stcd,tm")
    public List<Map<String,Object>> Select_Real_Reservoir_List(Map<String,Object> obj);
}
