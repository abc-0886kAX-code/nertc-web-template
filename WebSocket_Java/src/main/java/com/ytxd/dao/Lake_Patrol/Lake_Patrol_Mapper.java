package com.ytxd.dao.Lake_Patrol;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface Lake_Patrol_Mapper {
    /**
     * 获取巡湖列表
     * @param obj
     * @return
     */
    public List<Map<String,Object>> Select_RL_Punch_Clock_List(Map<String,Object> obj);

    /**
     * 添加巡湖数据
     * @param obj
     * @return
     */
    public Integer Insert_RL_Punch_Clock(Map<String,Object> obj);

    /**
     * 添加巡湖数据
     * @param obj
     * @return
     */
    public Integer Insert_st_lake_way(Map<String,Object> obj);

    /**
     * 修改巡湖数据
     * @param obj
     * @return
     */
    public Integer Update_RL_Punch_Clock(Map<String,Object> obj);

    /**
     * 删除巡湖数据
     * @param obj
     * @return
     */
    public Integer Delete_RL_Punch_Clock(Map<String,Object> obj);

    /**
     * 查询巡湖路线
     * @param obj
     * @return
     */
    public List<Map<String,Object>> Select_RL_Punch_Address(Map<String,Object> obj);

    /**
     * 添加巡湖路线
     * @param obj
     * @return
     */
    public Integer Insert_RL_Punch_Address(Map<String,Object> obj);

    /**
     * 删除巡湖路线
     * @param obj
     * @return
     */
    public Integer Delete_RL_Punch_Address(Map<String,Object> obj);

    /**
     * 查询巡湖事件
     * @param obj
     * @return
     */
    public List<Map<String,Object>> Select_RL_Patrol(Map<String,Object> obj);

    /**
     * 添加巡湖事件
     * @param obj
     * @return
     */
    public Integer Insert_RL_Patrol(Map<String,Object> obj);

    /**
     * 修改巡湖事件
     * @param obj
     * @return
     */
    public Integer Update_Rl_Patrol(Map<String,Object> obj);

    /**
     * 删除巡湖事件
     * @param obj
     * @return
     */
    public Integer Delete_Rl_Patrol(Map<String,Object> obj);
}
