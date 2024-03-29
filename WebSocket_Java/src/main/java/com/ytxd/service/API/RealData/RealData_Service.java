package com.ytxd.service.API.RealData;

import java.util.List;
import java.util.Map;

/**
 * 实时数据相关
 */
public interface RealData_Service {
    /**
     * 获取实时相关数据（1h 6h 24h）
     * @param obj
     * @return
     */
    public List<Map<String,Object>> GetRealRainListByTm(Map<String,Object> obj) throws Exception;

    /**
     * 获取3小时、1天、3天、7天的累计降雨数据
     * @param obj
     * @return
     * @throws Exception
     */
    public List<Map<String,Object>> GetStcdRainList(Map<String,Object> obj) throws Exception;

    /**
     * 获取站点的水位、雨量统计数据
     * @param obj
     * @return
     * @throws Exception
     */
    public List<Map<String,Object>> GetSiteRWList(Map<String,Object> obj) throws Exception;

    /**
     * 获取水质统计信息
     * @param obj
     * @return
     * @throws Exception
     */
    public List<Map<String,Object>> GetWQStatisticsList(Map<String,Object> obj) throws Exception;

    /**
     * 获取当月每天的水质信息
     * @param obj
     * @return
     * @throws Exception
     */
    public List<Map<String,Object>> GetWQDListByDay(Map<String,Object> obj) throws Exception;

}
