package com.ytxd.service.Water_Common.RealData;

import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * @Classname Reservoir_SituationImpl
 * @Author TY
 * @Date 2023/12/22 15:53
 * @Description TODO
 */
public interface Reservoir_Situation_Service {
    /**
     *
     * @Desription TODO 查询水库的实时数据
     * @param obj
     * @return com.github.pagehelper.PageInfo
     * @date 2023/12/22 15:56
     * @Auther TY
     */
    public PageInfo getRealReservoirList(Map<String,Object> obj) throws Exception;
}
