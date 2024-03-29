package com.ytxd.service.Subhall;

import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * 水环境
 */
public interface Water_Environment_Service {
    /**
     * 获取水质信息
     * @param obj
     * @return
     */
    public List<Map<String,Object>> GetWaterEnvironmentQualityList(Map<String,Object> obj);

    /**
     * 获取站点信息介绍
     * @param obj
     * @return
     */
    public PageInfo GetSiteIntroduce(Map<String,Object> obj);

    /**
     * 查询卫星云图
     * @param obj
     * @return
     */
    public PageInfo GetCloudChart(Map<String,Object> obj);

    /**
     * 获取水质指标图片
     * @param obj
     * @return
     */
    public List<Map<String,Object>> GetQualityImageList(Map<String,Object> obj);

   /**
     * 查询雷达图图
     * @param obj
     * @return
     */
    public PageInfo GetRadarCentral(Map<String, Object> obj);}
