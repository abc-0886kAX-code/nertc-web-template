package com.ytxd.dao.Subhall;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 水环境
 */
@Mapper
public interface Water_Environment_Mapper {
    /**
     * 查询最新的水质信息
     * @param obj
     * @return
     */
    List<Map<String,Object>> Select_Water_Environment_Quality_List(Map<String,Object> obj);

    /**
     * 获取参数信息
     * @return
     */
    List<Map<String,Object>> Select_CodeItem_List();

    /**
     * 获取站点介绍
     * @param obj
     * @return
     */
    List<Map<String,Object>> Select_Site_Introduce(Map<String,Object> obj);

    /**
     * 查询卫星云图
     * @param obj
     * @return
     */
    List<Map<String,Object>> Select_Cloud_Chart(Map<String,Object> obj);

    /**
     * 获取水质指标图片
     * @param obj
     * @return
     */
    List<Map<String,Object>> Select_Quality_Image_List(Map<String,Object> obj);
	/**
     * 查询雷达图图
     * @param obj
     * @return
     */
    List<Map<String,Object>> Select_Radar_Central(Map<String,Object> obj);}
