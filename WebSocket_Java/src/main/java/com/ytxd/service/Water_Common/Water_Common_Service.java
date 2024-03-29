package com.ytxd.service.Water_Common;

import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * @Classname Water_Common_Service
 * @Author TY
 * @Date 2024/1/29 15:31
 * @Description TODO
 */
public interface Water_Common_Service {
    /**
     *
     * @Desription TODO 获取站点信息
     * @param obj
     * @return com.github.pagehelper.PageInfo
     * @date 2024/1/29 15:55
     * @Auther TY
     */
    public PageInfo getSiteInfoList(Map<String,Object> obj) throws Exception;
    /**
     *
     * @Desription TODO 获取列表数据
     * @param obj
     * @return com.github.pagehelper.PageInfo
     * @date 2024/2/21 16:55
     * @Auther TY
     */
    public PageInfo getSiteDataInfoList(Map<String,Object> obj) throws Exception;

    PageInfo selectSiteWARNInfoList(Map<String, Object> obj);
}
