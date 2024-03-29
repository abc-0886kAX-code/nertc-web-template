package com.ytxd.dao.Water_Common.RealData;

import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @Classname Water_Supply_Plant_Mapper
 * @Author TY
 * @Date 2023/12/27 14:17
 * @Description TODO
 */
@Mapper
public interface Water_Supply_Plant_Mapper {
    /**
     *
     * @Desription TODO 查询供水厂信息和最后一条数据
     * @param obj
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     * @date 2023/12/27 14:27
     * @Auther TY
     */
    @MapKey("stcd")
    public List<Map<String,Object>> Select_Water_Supply_Plant_List(Map<String,Object> obj);
    /**
     *
     * @Desription TODO 获取供水厂的时序数据
     * @param obj
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     * @date 2023/12/27 14:28
     * @Auther TY
     */
    @MapKey("stcd,tm")
    public List<Map<String,Object>> Select_Real_Water_Supply_Plant_List(Map<String,Object> obj);
} 
