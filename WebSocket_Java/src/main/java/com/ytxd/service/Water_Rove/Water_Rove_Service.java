package com.ytxd.service.Water_Rove;

import java.util.List;
import java.util.Map;

/**
 * 漫游
 */
public interface Water_Rove_Service {
    /**
     * 获取漫游中所需要的信息
     * @param obj
     * @return
     */
    public Map<String,Object> GetStormPlanInfo(Map<String,Object> obj) throws Exception;
    /**
     * 漫游初始化时的站点信息
     * @param obj
     * @return
     */
    public List<Map<String,Object>> GetInitSiteInfoList(Map<String,Object> obj) throws Exception;

    /**
     * 获取时序水位数据和时间轴
     * @param obj
     * @return
     * @throws Exception
     */
    public List<Map<String,Object>> GetTMAxisInfoList(Map<String,Object> obj) throws Exception;

    /**
     * 获取某个站点的时序数据
     * @param obj
     * @return
     * @throws Exception
     */
    public List<Map<String,Object>> GetTMAxisSiteList(Map<String,Object> obj) throws Exception;

    /**
     * 获取两个闸门的时序流量数据
     * @param obj
     * @return
     * @throws Exception
     */
    public List<Map<String,Object>> GetSluiceGateList(Map<String,Object> obj) throws Exception;

    /**
     * 获取流场的数据
     * @param obj
     * @return
     * @throws Exception
     */
    public Map<String,Object> GetDefaultWindRList(Map<String,Object> obj) throws Exception;

}
