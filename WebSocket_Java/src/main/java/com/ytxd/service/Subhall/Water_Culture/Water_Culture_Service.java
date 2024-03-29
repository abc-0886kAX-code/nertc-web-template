package com.ytxd.service.Subhall.Water_Culture;

import com.github.pagehelper.PageInfo;

import java.util.Map;

/**
 * 知识库
 * @author CYC
 */
public interface Water_Culture_Service {

    /**
     * 查询知识库信息
     */
    public PageInfo getZskmhList(Map<String,Object> obj) throws Exception;

    /**
     * 查询水文化信息
     */
    public PageInfo GetWaterCulture(Map<String,Object> obj) throws Exception;

    /**
     * 通过id查询水文化信息
     */
    public Map<String,Object> GetWaterCultureByID(Map<String,Object> obj) throws Exception;


    /**
     *水利基础知识
     */
    public Map<String,Object> getWaterDay(Map<String,Object> obj) throws Exception;
}
