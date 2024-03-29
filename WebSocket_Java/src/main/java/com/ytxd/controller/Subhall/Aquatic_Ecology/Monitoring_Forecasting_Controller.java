package com.ytxd.controller.Subhall.Aquatic_Ecology;

import com.ytxd.common.DataUtils;
import com.ytxd.common.util.R;
import com.ytxd.service.Subhall.Aquatic_Ecology.Monitoring_Forecasting_Service;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 水安全 -监测预报
 */
@RestController("/Monitoring_Forecasting_Controller")
@RequestMapping("/Aquatic_Ecology/Monitoring_Forecasting")
public class Monitoring_Forecasting_Controller {
    @Resource
    private Monitoring_Forecasting_Service service;

    /**
     * 获取芙蓉溪的水位和水质等级
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("/GetMHWaterSWSZInfo")
    public R GerMHWaterSWSZInfo(HttpServletRequest request) throws Exception{
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        return R.ok().put("data",service.GetMHWaterSWSZInfo(obj));
    }

    /**
     * 获取芙蓉溪告警信息
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("/GetMHWaringInfo")
    public R GetMHWaringInfo(HttpServletRequest request) throws Exception{
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        return R.ok().put("data",service.GetMHWaringInfo(obj));
    }

    /**
     * 获取时序的水质信息
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("/GetMHWaterQualityInfoList")
    public R GetMHWaterQualityInfoList(HttpServletRequest request) throws Exception{
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        return R.ok(service.GetMHWaterQualityInfoList(obj));
    }

    /**
     * 水环境活水预案 -> 下拉框
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("/GetStormPlanEnvironmentSelected")
    public R GetStormPlanEnvironmentSelected(HttpServletRequest request) throws Exception{
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        return R.ok().put("data",service.GetStormPlanEnvironmentSelected(obj));
    }

    /**
     * 水环境活水预案 -> 预案信息
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("/GetStormPlanEnvironmentList")
    public R GetStormPlanEnvironmentList(HttpServletRequest request) throws Exception{
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        return R.ok(service.GetStormPlanEnvironmentList(obj));
    }

    /**
     * 水生态补水预案 -> 下拉框
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("/GetStormPlanEcologySelected")
    public R GetStormPlanEcologySelected(HttpServletRequest request) throws Exception{
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        return R.ok().put("data",service.GetStormPlanEcologySelected(obj));
    }

    /**
     * 水生态补水预案 -> 预案信息
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("/GetStormPlanEcologyList")
    public R GetStormPlanEcologyList(HttpServletRequest request) throws Exception{
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        return R.ok(service.GetStormPlanEcologyList(obj));
    }

    /**
     * 根据预案ID和站吗获取时序数据
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("/GetStormNodeRList")
    public R GetStormNodeRList(HttpServletRequest request) throws Exception{
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        return R.ok(service.GetStormNodeRList(obj));
    }

    /**
     * 获取存在映射关系的站点信息
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("/GetStormNodeContrast")
    public R GetStormNodeContrast(HttpServletRequest request) throws Exception{
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        return R.ok(service.GetStormNodeContrast(obj));
    }

    /**
     * 获取水生态信息
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("/GetWaterEcologyInfo")
    public R GetWaterEcologyInfo(HttpServletRequest request) throws Exception{
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        return R.ok().put("data",service.GetWaterEcologyInfo(obj));
    }

    /**
     * 获取水环境信息
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("/GetWaterEnvironmentInfo")
    public R GetWaterEnvironmentInfo(HttpServletRequest request) throws Exception{
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        return R.ok().put("data",service.GetWaterEnvironmentInfo(obj));
    }
    /**
     *
     * @Desription TODO 获取水质数据
     * @param request
     * @return com.ytxd.common.util.R
     * @date 2023/12/13 14:32
     * @Auther TY
     */
    @PostMapping("/GetMHWaterQualityInfoListByType")
    public R GetMHWaterQualityInfoListByType(HttpServletRequest request) throws Exception{
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        return R.ok(service.GetMHWaterQualityInfoListByType(obj));
    }

}
