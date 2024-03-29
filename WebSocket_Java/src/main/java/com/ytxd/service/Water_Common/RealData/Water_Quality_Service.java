package com.ytxd.service.Water_Common.RealData;

import com.github.pagehelper.PageInfo;

import java.util.Map;

/**
 * 水质信息
 */
public interface Water_Quality_Service {
    /**
     * 取最新的水质数据 取最新的一条
     * @param obj
     * @return
     */
    PageInfo GetWaterQualityList(Map<String,Object> obj) throws Exception;

    /**
     * 取实时的水质数据
     * @param obj
     * @return
     */
    PageInfo GetRealWaterQualityList(Map<String,Object> obj) throws Exception;

    /**
     * 取某时间段内的月均值统计
     * @param obj
     * @return
     */
    PageInfo GetRealWaterQualityListByMonth(Map<String,Object> obj) throws Exception;
}
