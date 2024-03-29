package com.ytxd.service.GeoJson;

import com.alibaba.fastjson.JSONObject;

import java.util.List;
import java.util.Map;

/**
 *
 * @author CYC
 */

public interface GeoJsonService {

    /**
     *气象雨情-天气预报
     */
    public List<Map<String,Object>> getWeatherForecast(Map<String,Object> obj) throws Exception;

    /**
     *
     *气象雨情-预报报警-降雨等值面图
     */
    public JSONObject getRealRainGeoJson(Map<String,Object> obj) throws Exception;


}
