package com.ytxd.controller.Subhall;

import com.ytxd.common.DataUtils;
import com.ytxd.common.util.R;
import com.ytxd.dao.Subhall.Water_Environment_Mapper;
import com.ytxd.service.Subhall.Water_Environment_Service;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 水环境
 */
@RestController("Water_Environment_Controller")
@RequestMapping("/WaterEnvironment")
public class Water_Environment_Controller   {
    @Resource
    private Water_Environment_Service service;
    /**
     * 获取水质信息
     *
     * @param request
     * @return
     */
    @PostMapping("/GetWaterEnvironmentQualityList")
    public R GetWaterEnvironmentQualityList(HttpServletRequest request) {
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        return R.ok().put("data",service.GetWaterEnvironmentQualityList(obj));
    }

    /**
     * 站点信息介绍
     * @param request
     * @return
     */
    @PostMapping("/GetSiteIntroduce")
    public R GetSiteIntroduce(HttpServletRequest request) {
        Map<String, Object> obj = DataUtils.getParameterMap(request);
        return R.ok(service.GetSiteIntroduce(obj));
    }

    /**
     * 获取卫星云图
     * @param request
     * @return
     */
     @PostMapping("/GetCloudChart")
    public R GetCloudChart(HttpServletRequest request) {
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        return R.ok(service.GetCloudChart(obj));
    }

    /**
     * 获取最新的水质指标图片
     * @param request
     * @return
     */
    @PostMapping("/GetQualityImageList")
    public R GetQualityImageList(HttpServletRequest request) {
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        return R.ok().put("data",service.GetQualityImageList(obj));
    }
    /**
     * 查询雷达图图
     * @param request
     * @return
     */
    @PostMapping("/GetRadarCentral")
    public R GetRadarCentral(HttpServletRequest request) {
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        return R.ok(service.GetRadarCentral(obj));
    }


}
