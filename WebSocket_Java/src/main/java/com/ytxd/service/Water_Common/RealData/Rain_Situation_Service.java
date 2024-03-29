package com.ytxd.service.Water_Common.RealData;

import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Mapper;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 * 雨情相关信息
 */
@Mapper
public interface Rain_Situation_Service {
    /**
     * 取所有站点或者某些站点某一段时间内的累计雨量
     * @param obj
     * @return
     */
    public PageInfo GetRainCumulativeList(Map<String,Object> obj) throws ParseException;

    /**
     * 根据站点获取某一段时间内实时降雨数据
     * @param obj
     * @return
     */
    public PageInfo GetRealRainList(Map<String,Object> obj);

    /**
     * 根据站点获取某一段时间内实时降雨数据以小时进行展示
     * @param obj
     * @return
     */
    public PageInfo GetRealRainListByHour(Map<String,Object> obj);

    /**
     * 根据站点获取某一段时间内实时降雨数据按天进行展示
     * @param obj
     * @return
     */
    public PageInfo GetRealRainListByDay(Map<String,Object> obj);

    /**
     * 获取实时雨情的下拉选择框
     * @param obj
     * @return
     */
    public List<Map<String,Object>> GetSelectionItemList(Map<String,Object> obj) throws Exception;

    /**
     * 获取预警的雨量数据
     * @param obj
     * @return
     * @throws Exception
     */
    public List<Map<String,Object>> GetEarlyWarningList(Map<String,Object> obj) throws Exception;

    /**
     * 获取前后3天的雨情信息
     * @param obj
     * @return
     */
    public List<Map<String,Object>> GetRainLevelInfoList(Map<String,Object> obj);

    /**
     * 通过时间查询8-8点的累计降雨量
     * @param obj
     * @return
     */
    public List<Map<String,Object>> GetRainDayStatistics(Map<String,Object> obj);

    public PageInfo GetRealRainListByYear(Map<String,Object> obj);
    /**
     *
     * @Desription TODO 雨情代表站点分析
     * @param obj
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     * @date 2024/1/29 15:14
     * @Auther TY
     */
    public List<Map<String,Object>> GetRealRainListByRepresentative(Map<String,Object> obj);
}
