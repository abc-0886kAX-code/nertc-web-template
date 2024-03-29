package com.ytxd.service.Water_Common.RealData;

import com.github.pagehelper.PageInfo;

import java.util.Map;

/**
 * 工情信息
 */
public interface Worker_Situation_Service {
    /**
     * 取最新的工情数据 取最新的一条
     * @param obj
     * @return
     */
    public PageInfo GetWorkerList(Map<String,Object> obj) throws Exception;

    /**
     * 取实时的工情数据
     * @param obj
     * @return
     */
    public PageInfo GetRealWorkerList(Map<String,Object> obj) throws Exception;
}
