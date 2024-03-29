package com.ytxd.controller.Subhall;

import com.ytxd.common.DataUtils;
import com.ytxd.common.util.R;
import com.ytxd.service.Subhall.Water_Safety_Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/Water_Safety")
public class Water_Safety_Controller {
    @Resource
    private Water_Safety_Service service;

    /**
     * 获取前后3天的雨量水位数据
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("/GetStormWaterLevelList")
    public R GetStormWaterLevelList(HttpServletRequest request) throws Exception{
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        return R.ok(service.GetStormWaterLevelList(obj));
    }

    /**
     * 获取工情信息
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("/GetStormGateList")
    public R GetStormGateList(HttpServletRequest request) throws Exception{
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        return R.ok(service.GetStormGateList(obj));
    }

    /**
     * 获取预报水位信息
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("/GetPredictionWLList")
    public R GetPredictionWLList(HttpServletRequest request) throws Exception{
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        return R.ok().put("data",service.GetPredictionWLList(obj));
    }

    /**
     * 获取动态预警的发布预警信息和未发布预警信息
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("/GetWarningReleaseList")
    public R GetWarningReleaseList(HttpServletRequest request) throws Exception{
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        return R.ok(service.GetWarningReleaseList(obj));
    }

    /**
     * 预警发布保存
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("/WaringReleaseSave")
    public R WaringReleaseSave(HttpServletRequest request) throws Exception{
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        return R.ok().put("data",service.WaringReleaseSave(obj));
    }

    /**
     * 获取预报调度方案的下拉框列表
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("/GetSafetySelectedList")
    public R GetSafetySelectedList(HttpServletRequest request) throws Exception{
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        return R.ok().put("data",service.GetSafetySelectedList(obj));
    }

    /**
     * 获取默认的方案详细
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("/GetDefaultPlanInfo")
    public R GetDefaultPlanInfo(HttpServletRequest request) throws Exception{
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        return R.ok().put("data",service.GetDefaultPlanInfo(obj));
    }
}
