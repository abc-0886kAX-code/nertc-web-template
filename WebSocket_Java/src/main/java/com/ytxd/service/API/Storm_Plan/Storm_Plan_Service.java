package com.ytxd.service.API.Storm_Plan;

import com.github.pagehelper.PageInfo;
import java.util.Map;

/**
 * 调度预案
 */
public interface Storm_Plan_Service {
    /**
     * 调度预案查询
     *
     * @param obj
     * @return
     * @throws Exception
     */
    public PageInfo GetList(Map<String, Object> obj) throws Exception;

    public PageInfo GetListLocal(Map<String, Object> obj) throws Exception;

    /**
     * 获取定时预案列表信息
     *
     * @param obj
     * @return
     * @throws Exception
     */
    public PageInfo GetRegularList(Map<String, Object> obj) throws Exception;

    /**
     * 根据ID查询
     *
     * @param obj
     * @return
     * @throws Exception
     */
    public Map<String, Object> GetListById(Map<String, Object> obj) throws Exception;

    public Map<String, Object> GetListByIdLocal(Map<String, Object> obj) throws Exception;

    /**
     * 调度预案保存
     *
     * @param obj
     * @return
     * @throws Exception
     */
    public Integer Save(Map<String, Object> obj) throws Exception;

    /**
     * @param obj
     * @return java.lang.Integer
     * @Desription TODO 调度预案保存
     * @date 2023/11/14 15:18
     * @Auther TY
     */
    public Integer originalSave(Map<String, Object> obj) throws Exception;

    /**
     * 删除
     *
     * @param obj
     * @return
     * @throws Exception
     */
    public Integer Delete(Map<String, Object> obj) throws Exception;

    /**
     * @param obj
     * @return java.lang.Integer
     * @Desription TODO 调度预案删除
     * @date 2023/11/14 15:29
     * @Auther TY
     */
    public Integer originalDelete(Map<String, Object> obj) throws Exception;

    Map<String, Object> GetForecastRainfall();

    /**
     * @param obj
     * @return java.lang.String
     * @Desription TODO 获取plan_id
     * @date 2024/3/5 10:22
     * @Auther TY
     */
    public String getPlanId(Map<String, Object> obj);
}
