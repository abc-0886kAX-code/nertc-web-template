package com.ytxd.service.API.Lake_Healthy;

import com.github.pagehelper.PageInfo;
import org.springframework.security.core.parameters.P;

import java.util.List;
import java.util.Map;

/**
 * 幸福河湖
 */
public interface Lake_Healthy_Service {
    /**
     * 查询幸福河湖信息
     * @param obj
     * @return
     * @throws Exception
     */
    public PageInfo GetLakeHealthyList(Map<String,Object> obj) throws Exception;

    /**
     * 人数统计
     * @param obj
     * @return
     * @throws Exception
     */
    public List<Map<String,Object>> GetPeopleStatistics(Map<String,Object> obj) throws Exception;

    /**
     * 水资源统计
     * @param obj
     * @return
     * @throws Exception
     */
    public List<Map<String,Object>> GetWaterResourceList(Map<String,Object> obj) throws Exception;

    /**
     * 幸福河湖二维码
     * @param obj
     * @return
     * @throws Exception
     */
    public PageInfo GetLakeBlissList(Map<String,Object> obj) throws Exception;

    /**
     * 幸福河湖卫星遥感
     * @param obj
     * @return
     */
    public PageInfo GetRemoteSensingList(Map<String,Object> obj) throws Exception;
    /**
     *
     * @Desription TODO 幸福河湖卫星遥感(按月份查询)
     * @param obj
     * @return java.util.Map<java.lang.String,java.lang.Object>
     * @date 2023/9/21 10:53
     * @Auther TY
     */
    public Map<String,Object> getRemoteSensingListByMonth(Map<String,Object> obj) throws Exception;

    /**
     * 获取每个类型最后一条数据
     * @param obj
     * @return
     * @throws Exception
     */
    public PageInfo getLastRemoteSensingList(Map<String,Object> obj) throws Exception;
}
