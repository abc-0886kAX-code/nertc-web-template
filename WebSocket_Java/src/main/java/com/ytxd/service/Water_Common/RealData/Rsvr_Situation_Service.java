package com.ytxd.service.Water_Common.RealData;

import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * @Classname Rsvr_Situation_Service
 * @Author TY
 * @Date 2024/2/4 16:05
 * @Description TODO
 */
public interface Rsvr_Situation_Service {
    /**
     *
     * @Desription TODO 获取最新的水位库容数据
     * @param obj
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     * @date 2024/2/4 16:06
     * @Auther TY
     */
    public PageInfo getRsvrLevelList(Map<String,Object> obj) throws Exception;

    /**
     * 获取时序水文库容数据
     * @param obj
     * @return
     * @throws Exception
     */
    public PageInfo getRealRsvrLevelList(Map<String,Object> obj) throws Exception;
    /**
     *
     * @Desription TODO 取实时水情数据 按小时展示
     * @param obj
     * @return com.github.pagehelper.PageInfo
     * @date 2024/2/4 16:07
     * @Auther TY
     */
    public PageInfo getRealRsvrLevelListHour(Map<String,Object> obj) throws Exception;

    /**
     *
     * @param obj 取实时水情数据 按小时展示
     * @return
     * @throws Exception
     */
    public PageInfo getRealRsvrLevelListDay(Map<String,Object> obj) throws Exception;


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
