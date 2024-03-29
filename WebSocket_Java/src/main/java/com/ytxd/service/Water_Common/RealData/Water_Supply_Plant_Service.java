package com.ytxd.service.Water_Common.RealData;

import com.github.pagehelper.PageInfo;

import java.util.Map;

/**
 * @Classname Water_Supply_Plant_Service
 * @Author TY
 * @Date 2023/12/27 14:16
 * @Description TODO 供水厂
 */
public interface Water_Supply_Plant_Service {
    /**
     *
     * @Desription TODO 获取供水厂信息
     * @param obj
     * @return com.github.pagehelper.PageInfo
     * @date 2023/12/27 14:29
     * @Auther TY
     */
    public PageInfo getWaterSupplyPlantList(Map<String,Object> obj) throws Exception;
    /**
     *
     * @Desription TODO 获取供水厂时序数据
     * @param obj
     * @return com.github.pagehelper.PageInfo
     * @date 2023/12/27 14:29
     * @Auther TY
     */
    public PageInfo getRealWaterSupplyPlantList(Map<String,Object> obj) throws Exception;
}
