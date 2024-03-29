package com.ytxd.service.API.DigitalHall;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 防洪保安全
 */
public interface FloodControlHall_Service {
    /**
     * 查询预报降雨
     * @param obj
     * @return
     */
    List<Map<String,Object>> GetPredictionRainList(Map<String,Object> obj) throws Exception;
    List<Map<String,Object>> GetStorageStateList(Map<String,Object> obj) throws Exception;
}
