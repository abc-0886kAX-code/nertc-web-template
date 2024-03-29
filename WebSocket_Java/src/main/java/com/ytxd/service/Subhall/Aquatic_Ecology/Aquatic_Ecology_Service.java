package com.ytxd.service.Subhall.Aquatic_Ecology;

import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 水生态
 */
public interface Aquatic_Ecology_Service {
    /**
     * 查询最新的dna站点数据
     * @param obj
     * @return
     */
    public PageInfo GetDNAInfoList(Map<String,Object> obj) throws Exception;

    /**
     * 获取dna站点数据list
     * @param obj
     * @return
     */
    public PageInfo GetDNARList(Map<String,Object> obj) throws Exception;

    /**
     * 获取流量站点信息
     * @param obj
     * @return
     * @throws Exception
     */
    public PageInfo GetFlowSiteList(Map<String,Object> obj) throws Exception;

    /**
     * 获取dna站点想详细信息
     * @param obj
     * @return
     * @throws Exception
     */
    public List<Map<String,Object>> GetDNAPronInfo(Map<String,Object> obj) throws Exception;
}
