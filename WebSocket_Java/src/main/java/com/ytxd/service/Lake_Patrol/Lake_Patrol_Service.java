package com.ytxd.service.Lake_Patrol;

import com.github.pagehelper.PageInfo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 巡湖打卡
 */
public interface Lake_Patrol_Service {
    /**
     * 获取巡湖列表
     * @param obj
     * @return
     */
    public PageInfo getRLPunchClockList(Map<String,Object> obj) throws Exception;

    /**
     * 查询当前设备是否正在巡湖中
     * @param obj
     * @return
     */
    public Map<String,Object> getRLPunchClock(Map<String,Object> obj) throws Exception;

    /**
     * 巡护打卡保存
     * @param obj
     * @return
     * @throws Exception
     */
    public String RLPunchClockSave(Map<String,Object> obj) throws Exception;

    /**
     * 巡护打卡删除
     * @param obj
     * @return
     * @throws Exception
     */
    public Integer RLPunchClockDel(Map<String,Object> obj) throws Exception;

    /**
     * 获取训湖打卡路线
     * @param obj
     * @return
     * @throws Exception
     */
    public Double[][] getRLPunchAddress(Map<String,Object> obj) throws Exception;

    /**
     * 查询巡湖事件
     * @param obj
     * @return
     */
    public PageInfo getRLPatrolList(Map<String,Object> obj);

    /**
     * 巡湖事件保存
     * @param obj
     * @return
     */
    public String RLPatrolSave(Map<String,Object> obj);

    /**
     * 巡湖事件删除
     * @param obj
     * @return
     */
    public Integer RLPatrolDel(Map<String,Object> obj);



}
