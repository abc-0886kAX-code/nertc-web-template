package com.ytxd.controller.Water_Rove;

import com.ytxd.common.DataUtils;
import com.ytxd.common.util.R;
import com.ytxd.service.Water_Rove.Water_Rove_Service;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 漫游
 */
@RestController("Water_Rove_Controller")
@RequestMapping("/Water_Rove")
public class Water_Rove_Controller {
    @Resource
    private Water_Rove_Service service;

    /**
     * 获取漫游中所需要的信息
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("/GetStormPlanInfo")
    public R GetStormPlanInfo(HttpServletRequest request) throws Exception{
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        return R.ok().put("data",service.GetStormPlanInfo(obj));
    }

    /**
     * 漫游初始化时的站点信息
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("/GetInitSiteInfoList")
    public R GetInitSiteInfoList(HttpServletRequest request) throws Exception{
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        return R.ok().put("data",service.GetInitSiteInfoList(obj));
    }

    /**
     * 获取时序水位数据和时间轴
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("/GetTMAxisInfoList")
    public R GetTMAxisInfoList(HttpServletRequest request) throws Exception{
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        return R.ok().put("data",service.GetTMAxisInfoList(obj));
    }

    /**
     * 获取某个站点的时序数据
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("/GetTMAxisSiteList")
    public R GetTMAxisSiteList(HttpServletRequest request) throws Exception{
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        return R.ok().put("data",service.GetTMAxisSiteList(obj));
    }

    /**
     * 获取两个闸门的时序流量数据
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("/GetSluiceGateList")
    public R GetSluiceGateList(HttpServletRequest request) throws Exception{
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        return R.ok().put("data",service.GetSluiceGateList(obj));
    }

    /**
     * 获取流场的数据
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("/GetDefaultWindRList")
    public R GetDefaultWindRList(HttpServletRequest request) throws Exception{
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        return R.ok().put("data",service.GetDefaultWindRList(obj));
    }
}
