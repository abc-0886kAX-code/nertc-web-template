package com.ytxd.dao.API.DigitalHall;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 防洪保安全
 */
@Mapper
public interface FloodControlHall_Mapper {
    /**
     * 查询预报降雨
     * @param obj
     * @return
     */
    List<Map<String,Object>> Select_Prediction_RainList(Map<String,Object> obj);

    /**
     *
     * @param obj
     * @return
     */
    List<Map<String,Object>> Select_StorageState_List(Map<String,Object> obj);
}
