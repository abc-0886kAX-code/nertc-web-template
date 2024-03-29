package com.ytxd.dao.API.Ecology;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface Ecology_Alert_Mapper {
    /**
     * 获取报警站点信息
     * @param obj
     * @return
     */
    public List<Map<String,Object>> GetEcologyAlertList(Map<String,Object> obj);

    /**
     * 修改生态报警信息
     * @param obj
     * @return
     */
    public Integer UpdateEcologyAlert(Map<String,Object> obj);

    /**
     * 获取管理者名录
     * @param obj
     * @return
     */
    public List<Map<String,Object>> GetManagerList(Map<String,Object> obj);
}
