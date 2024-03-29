package com.ytxd.dao.GeoJson;

import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author CYC
 */
@Mapper
public interface GeoJsonMapper {

    /**
     *气象雨情-天气预报
     */
    public List<Map<String, Object>> getWeatherForecastList(Map<String,Object> obj);


    /**
     *
     * @Desription TODO 获取经度数据-X
     * @return double[]
     * @date 2024/3/1 13:34
     * @Auther TY
     */
    public double[] getRealRainSiteLgtd();
    /**
     *
     * @Desription TODO 获取经度数据-Y
     * @return double[]
     * @date 2024/3/1 13:34
     * @Auther TY
     */
    public double[] getRealRainSiteLttd();
    /**
     *
     * @Desription TODO 查询一段时间内的降雨量数据
     * @param obj
     * @return java.util.List<java.util.HashMap<java.lang.String,java.lang.Object>>
     * @date 2024/3/1 13:38
     * @Auther TY
     */
    public List<HashMap<String,Object>> getRealDataInfoList(Map<String,Object> obj);


}
