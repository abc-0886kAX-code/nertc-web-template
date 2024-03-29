package com.ytxd.service.API.SiteInfo;

import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * 站点信息
 */
public interface SiteInfo_Service {
    /**
     * 获取站点信息
     * @param obj
     * @return
     * @throws Exception
     */
    public List<Map<String,Object>> GetSiteInfoList(Map<String,Object> obj) throws Exception;

    /**
     * 获取闸门信息
     * @param obj
     * @return
     * @throws Exception
     */
    public List<Map<String,Object>> GetSiteGateList(Map<String,Object> obj) throws Exception;

    /**
     * 获取报警信息
     * @param obj
     * @return
     * @throws Exception
     */
    public PageInfo GetEarlyWarningList(Map<String,Object> obj) throws Exception;

    /**
     * 根据不同的大厅展示不同的站点信息
     * @param obj
     * @return
     * @throws Exception
     */
    public PageInfo GetSTSubhallSiteList(Map<String,Object> obj) throws Exception;

    Map<String , Object> selectVideoStationNum(Map<String,Object> obj);
}
