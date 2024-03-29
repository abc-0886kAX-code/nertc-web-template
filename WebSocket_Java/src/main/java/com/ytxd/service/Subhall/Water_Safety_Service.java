package com.ytxd.service.Subhall;

import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * 水安全
 */
public interface Water_Safety_Service {
    /**
     * 获取前后3天的雨量水位数据
     * @param obj
     * @return
     */
    public PageInfo GetStormWaterLevelList(Map<String,Object> obj) throws Exception;

    /**
     * 获取工情信息
     * @param obj
     * @return
     * @throws Exception
     */
    public PageInfo GetStormGateList(Map<String,Object> obj) throws Exception;
    /**
     * 获取预报水位信息
     */
    public List<Map<String,Object>> GetPredictionWLList(Map<String,Object> obj) throws Exception;

    /**
     * 获取动态预警的发布预警信息和未发布预警信息
     * @param obj
     * @return
     * @throws Exception
     */
    public PageInfo GetWarningReleaseList(Map<String,Object> obj) throws Exception;

    /**
     * 预警发布保存
     * @param obj
     * @return
     * @throws Exception
     */
    public Integer WaringReleaseSave(Map<String,Object> obj) throws Exception;

    /**
     * 获取预报调度方案的下拉框列表
     * @param obj
     * @return
     * @throws Exception
     */
    public List<Map<String,Object>> GetSafetySelectedList(Map<String,Object> obj) throws Exception;

    /**
     * 获取默认的方案详细
     * @param obj
     * @return
     * @throws Exception
     */
    public Map<String,Object> GetDefaultPlanInfo(Map<String,Object> obj) throws Exception;

    /**
     * 获取指定时间最相近的方案，如果降雨量为空最不返回方案
     * @param obj
     * @return
     * @throws Exception
     */
    public Map<String, Object> GetNearPlanInfo(Map<String, Object> obj) throws Exception;
}
