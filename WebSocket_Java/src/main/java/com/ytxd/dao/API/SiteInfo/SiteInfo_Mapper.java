package com.ytxd.dao.API.SiteInfo;

import java.util.List;
import java.util.Map;

/**
 * 站点信息
 */
public interface SiteInfo_Mapper  {
    /**
     * 查询站点信息
     * @param obj
     * @return
     */
    public List<Map<String,Object>> Select_SiteInfo_List(Map<String,Object> obj);
    public Map<String,Object> Select_Site_Rvfcch_Info(Map<String,Object> obj);
    public Map<String,Object> Select_Site_Rsvrfcch_Info(Map<String,Object> obj);

    /**
     * 获取闸门信息
     * @param obj
     * @return
     */
    public List<Map<String,Object>> Select_Site_Gate_List(Map<String,Object> obj);

    /**
     * 获取报警信息
     * @param obj
     * @return
     */
    public List<Map<String,Object>> Select_Early_Warning_List(Map<String,Object> obj);

    /**
     * 根据不同的大厅查询不同的站点信息
     * @param obj
     * @return
     */
    public List<Map<String,Object>> Select_ST_Subhall_Site_List(Map<String,Object> obj);

    /**
     * 查询最新的雨量信息
     * @param obj
     * @return
     */
    public Map<String,Object> Select_Last_Rain_Info(Map<String,Object> obj);

    /**
     * 查询最新的水质信息
     * @param obj
     * @return
     */
    public Map<String,Object> Select_Last_Water_Quality_Info(Map<String,Object> obj);

    /**
     * 查询最新的水位信息
     * @param obj
     * @return
     */
    public Map<String,Object> Select_Last_Water_Level_Info(Map<String,Object> obj);


    Map<String , Object> selectVideoStationNum(Map<String,Object> obj);
}
