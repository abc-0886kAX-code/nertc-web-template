package com.ytxd.service.Meteorology;

import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * @Classname Meteorology_Service
 * @Author TY
 * @Date 2023/10/12 17:50
 * @Description TODO
 */
public interface Meteorology_Service {
    /**
     *
     * @Desription TODO 获取站点的基本信息
     * @param map
     * @return com.github.pagehelper.PageInfo
     * @date 2023/10/16 10:06
     * @Auther TY
     */
    public PageInfo getMeteorologyInfo(Map<String,Object> map) throws Exception;

    /**
     *
     * @Desription TODO 获取气象数据
     * @param map
     * @return com.github.pagehelper.PageInfo
     * @date 2023/10/12 17:50
     * @Auther TY
     */
    public PageInfo getMeteorologyList(Map<String,Object> map) throws Exception;

}
