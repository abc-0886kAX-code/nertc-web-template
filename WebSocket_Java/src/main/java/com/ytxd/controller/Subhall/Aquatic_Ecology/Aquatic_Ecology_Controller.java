package com.ytxd.controller.Subhall.Aquatic_Ecology;

import com.ytxd.common.DataUtils;
import com.ytxd.common.util.R;
import com.ytxd.service.Subhall.Aquatic_Ecology.Aquatic_Ecology_Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 水生态
 */
@RestController("/Aquatic_Ecology_Controller")
@RequestMapping("/Aquatic_Ecology")
public class Aquatic_Ecology_Controller {
    @Resource
    private Aquatic_Ecology_Service service;

    /**
     * 查询最新的dna站点数据
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("/GetDNAInfoList")
    public R GetDNAInfoList(HttpServletRequest request) throws Exception{
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        return R.ok(service.GetDNAInfoList(obj));
    }

    /**
     * 获取dna站点数据list
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("/GetDNARList")
    public R GetDNARList(HttpServletRequest request) throws Exception{
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        return R.ok(service.GetDNARList(obj));
    }

    /**
     * 获取流量站点信息
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("/GetFlowSiteList")
    public R GetFlowSiteList(HttpServletRequest request) throws Exception{
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        return R.ok(service.GetFlowSiteList(obj));
    }

    /**
     * 获取dna站点想详细信息
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("/GetDNAPronInfo")
    public R GetDNAPronInfo(HttpServletRequest request) throws Exception{
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        return R.ok().put("data",service.GetDNAPronInfo(obj));
    }
}
