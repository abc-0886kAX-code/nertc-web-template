package com.ytxd.service.API.Ecology;

import java.util.List;
import java.util.Map;

/**
 * 生态保护区
 */
public interface Ecology_Reserve_Service {
    /**
     * 获取生态保护区列表
     * @param obj
     * @return
     * @throws Exception
     */
    public List<Map<String,Object>> GetEcologyReserveList(Map<String,Object> obj) throws Exception;
 }
