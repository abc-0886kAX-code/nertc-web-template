package com.ytxd.service.Subhall.Aquatic_Ecology;

import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * 水安全-监测预报
 */
public interface Monitoring_Forecasting_Service {
    /**
     * 获取芙蓉溪的水位和水质等级
     * @param obj
     * @return
     * @throws Exception
     */
    public List<Map<String,Object>> GetMHWaterSWSZInfo(Map<String,Object> obj) throws Exception;

    /**
     * 获取芙蓉溪告警信息
     * @param obj
     * @return
     * @throws Exception
     */
    public List<Map<String,Object>> GetMHWaringInfo(Map<String,Object> obj) throws Exception;

    /**
     * 获取时序水质信息
     * @param obj
     * @return
     * @throws Exception
     */
    public PageInfo GetMHWaterQualityInfoList(Map<String,Object> obj) throws Exception;

    /**
     * 水环境活水预案 -> 下拉框
     * @param obj
     * @return
     * @throws Exception
     */
    public List<Map<String,Object>>GetStormPlanEnvironmentSelected(Map<String,Object> obj) throws Exception;

    /**
     * 水环境活水预案 -> 预案信息
     * @param obj
     * @return
     * @throws Exception
     */
    public PageInfo GetStormPlanEnvironmentList(Map<String,Object> obj) throws Exception;

    /**
     * 水生态补水预案 -> 下拉框
     * @param obj
     * @return
     * @throws Exception
     */
    public List<Map<String,Object>> GetStormPlanEcologySelected(Map<String,Object> obj) throws Exception;

    /**
     * 水生态补水预案 -> 预案信息
     * @param obj
     * @return
     * @throws Exception
     */
    public PageInfo GetStormPlanEcologyList(Map<String,Object> obj) throws Exception;

    /**
     * 获取水位站的时序数据
     * @param obj
     * @return
     * @throws Exception
     */
    public PageInfo GetStormNodeRList(Map<String,Object> obj) throws Exception;

    /**
     * 获取存在映射关系的站点信息
     * @param obj
     * @return
     * @throws Exception
     */
    public PageInfo GetStormNodeContrast(Map<String,Object> obj) throws Exception;

    /**
     * 获取水生态信息
     * @param obj
     * @return
     * @throws Exception
     */
    public Map<String,Object> GetWaterEcologyInfo(Map<String,Object> obj) throws Exception;

    /**
     * 获取水环境信息
     * @param obj
     * @return
     * @throws Exception
     */
    public Map<String,Object> GetWaterEnvironmentInfo(Map<String,Object> obj) throws Exception;

    /**
     * 获取时序水质信息
     * @param obj
     * @return
     * @throws Exception
     */
    public PageInfo GetMHWaterQualityInfoListByType(Map<String,Object> obj) throws Exception;

}
