package com.ytxd.service.API.Ecology;

import com.github.pagehelper.PageInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 生态报警
 */
public interface Ecology_Alert_Service {
    /**
     * 获取报警站点信息
     * @param obj
     * @return
     * @throws Exception
     */
    public PageInfo GetEcologyAlertList(Map<String,Object> obj) throws Exception;

    /**
     * 通过id获取报警站点信息
     * @param id
     * @return
     */
    public Map<String,Object> GetEcologyAlertListById(String id) throws Exception;

    /**
     * 解除报警
     * @param obj
     * @return
     * @throws Exception
     */
    public Integer RelieveEcologyAlert(Map<String,Object> obj) throws Exception;

    /**
     * 获取管理者名录
     * @param obj
     * @return
     * @throws Exception
     */
    public PageInfo GetManagerList(Map<String,Object> obj) throws Exception;
}
