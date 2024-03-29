package com.ytxd.service.VideoInfo;

import com.github.pagehelper.PageInfo;

import java.util.Map;

/**
 * @Classname SurveillanceVideo_Service
 * @Author TY
 * @Date 2024/1/5 16:59
 * @Description TODO
 */
public interface SurveillanceVideo_Service {
    /**
     *
     * @Desription TODO 查询视频监控站点
     * @param obj
     * @return com.github.pagehelper.PageInfo
     * @date 2024/1/5 16:59
     * @Auther TY
     */
    public PageInfo getSurveillanceVideoList(Map<String,Object> obj);
}
