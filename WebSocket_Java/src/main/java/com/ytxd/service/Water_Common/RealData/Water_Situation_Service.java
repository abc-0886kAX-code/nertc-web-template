package com.ytxd.service.Water_Common.RealData;

import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * 实时水情相关
 */
public interface Water_Situation_Service {
    /**
     * 取最新的水情数据 取最新的一条
     * @param obj
     * @return
     * @throws Exception
     */
    public PageInfo GetWaterLevelList(Map<String,Object> obj) throws Exception;

    /**
     * 取实时水情数据
     * @param obj
     * @return
     * @throws Exception
     */
    public PageInfo GetRealWaterLevelList(Map<String,Object> obj) throws Exception;

    /**
     * 取实时水情数据 按小时展示
     * @param obj
     * @return
     * @throws Exception
     */
    public PageInfo GetRealWaterLevelListByHour(Map<String,Object> obj) throws Exception;

    /**
     * 取实时水情数据 按天展示
     * @param obj
     * @return
     * @throws Exception
     */
    public PageInfo GetRealWaterLevelListByDay(Map<String,Object> obj) throws Exception;

    /**
     * 获取当前时间前后3天的雨量信息
     * @param obj
     * @return
     * @throws Exception
     */
    public PageInfo GetWaterLevelInfoList(Map<String,Object> obj) throws Exception;

    /**
     * 获取时间段内的小时库容水位信息
     * @param obj
     * @return
     * @throws Exception
     */
    public PageInfo GetSTRsvrRListByHour(Map<String,Object> obj) throws Exception;

    public PageInfo getSTRsvrRLastData(Map<String,Object> obj) throws Exception;
    /**
     *
     * @Desription TODO 查询同期水位信息
     * @param obj
     * @return com.github.pagehelper.PageInfo
     * @date 2024/2/23 14:15
     * @Auther TY
     */
    public PageInfo GetTQRealWaterLevelListByHour(Map<String,Object> obj) throws Exception;



    List<Map<String , Object>> selectExceedLevee(Map<String,Object> map);

    List<Map<String , Object>> selectExceedGuaranteed(Map<String,Object> map);

    List<Map<String , Object>> selectExceedAlertLevel(Map<String,Object> map);

    List<Map<String , Object>> selectRiverStationInfo(Map<String,Object> map);

    Map<String , Object> selectExceedNum(Map<String,Object> map);



}
