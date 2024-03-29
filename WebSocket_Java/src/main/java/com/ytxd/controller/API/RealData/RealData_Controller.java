package com.ytxd.controller.API.RealData;

import com.ytxd.common.DataUtils;
import com.ytxd.common.util.R;
import com.ytxd.service.API.RealData.RealData_Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController("RealData_Controller")
@RequestMapping("/RealData")
public class RealData_Controller {
    @Resource
    private RealData_Service service;

    /**
     * 获取最新的雨量水位数据
     * @param request
     * @return
     * @throws Exception
     */
    @GetMapping("/GetRealRainListByTm")
    public R GetRealRainListByTm(HttpServletRequest request) throws Exception{
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        List<Map<String,Object>> data = service.GetRealRainListByTm(obj);
        return R.ok().put("data",data);

    }

    /**
     * 获取3小时、1天、3天、7天的累计降雨数据
     * @param request
     * @return
     * @throws Exception
     */
    @GetMapping("/GetStcdRainList")
    public R GetStcdRainList(HttpServletRequest request) throws Exception{
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        List<Map<String,Object>> data = service.GetStcdRainList(obj);
        return R.ok().put("data",data);
    }

    /**
     * 获取站点的水位、雨量统计数据
     * @param request
     * @return
     * @throws Exception
     */
    @GetMapping("/GetSiteRWList")
    public R GetSiteRWList(HttpServletRequest request) throws Exception{
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        List<Map<String,Object>> data = service.GetSiteRWList(obj);
        return R.ok().put("data",data);
    }
    /**
     * 获取水质统计信息
     * @param request
     * @return
     * @throws Exception
     */
    @GetMapping("/GetWQStatisticsList")
    public R GetWQStatisticsList(HttpServletRequest request) throws Exception{
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        List<Map<String,Object>> data = service.GetWQStatisticsList(obj);
        return R.ok().put("data",data);
    }

    /**
     * 获取当月每天的水质信息
     * @param request
     * @return
     * @throws Exception
     */
    @GetMapping("/GetWQDListByDay")
    public R GetWQDListByDay(HttpServletRequest request) throws Exception{
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        List<Map<String,Object>> data = service.GetWQDListByDay(obj);
        return R.ok().put("data",data);
    }
}
