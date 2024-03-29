package com.ytxd.dao.Water_Common.RealData;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 水情相关
 */
@Mapper
public interface Rsvr_Situation_Mapper {
    /**
     * 取最新的水情数据 取最新的一条
     * @param obj
     * @return
     */
    public List<Map<String,Object>> Select_Rsvr_Level_List(Map<String,Object> obj);

    /**
     * 取实时水情数据
     * @param obj
     * @return
     */
    public List<Map<String,Object>> Select_Real_Rsvr_Level_List(Map<String,Object> obj);

    /**
     * 取实时水情数据 按小时展示
     * @param obj
     * @return
     */
    public List<Map<String,Object>> Select_Real_Rsvr_Level_List_Hour(Map<String,Object> obj);

    /**
     * 取实时水情数据 按天展示
     * @param obj
     * @return
     */
    public List<Map<String,Object>> Select_Real_Rsvr_Level_List_Day(Map<String,Object> obj);

    /**
     * @Description: 查询水库总数、中大型水库、小型书库
     * @param map
     * @return: java.util.Map<java.lang.String,java.lang.Object>
     * @Author: ZJW
     * @Date: 2024/3/18 11:21
     */
    Map<String , Object> selectRsvrStation(Map<String,Object> map);


    /**
     * @Description: 查询超过汛限水位的水库站点个数
     * @param map
     * @return: java.util.Map<java.lang.String,java.lang.Object>
     * @Author: ZJW
     * @Date: 2024/3/18 14:22
     */
    Map<String , Object> selectFloodLevelExceededNum(Map<String,Object> map);



    List<Map<String , Object>> selectFloodLevelExceeded(Map<String,Object> map);
}
