package com.ytxd.dao.API.RealData;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface RealData_Mapper {
    /**
     * 获取实时数据(1,6,24)
     * @param obj
     * @return
     */
    public List<Map<String,Object>> GetRealRainListByTm(Map<String,Object> obj);

    /**
     * 根据传入的TM 查询最新的一条数据
     * @param obj
     * @return
     */
    public Double GetRsvrLatestZ(Map<String,Object> obj);

    /**
     * 获取 3h、1day、3day、7day 的降雨数据
     * @param obj
     * @return
     */
    public List<Map<String,Object>> Select_Stcd_Rain_list(Map<String,Object> obj);

    /**
     * 获取站点的水位、雨量统计数据
     * @param obj
     * @return
     */
    public List<Map<String,Object>> Select_Site_Statistics_List(Map<String,Object> obj);

    /**
     * 获取水质统计信息
     * @param obj
     * @return
     */
    public List<Map<String,Object>> Select_WQ_M_List(Map<String,Object> obj);

    /**
     * 获取小时数据最后不为空的数据
     * @param obj
     * @return
     */
    public List<Map<String,Object>> Select_Last_WQ_D_List(Map<String,Object> obj);

    /**
     * 获取当月每天的水质信息
     * @param obj
     * @return
     */
    public List<Map<String,Object>> Select_WQ_D_List_ByDay(Map<String,Object> obj);
}
