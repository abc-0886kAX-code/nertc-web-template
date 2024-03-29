package com.ytxd.controller.Water_Common;

import com.ytxd.common.DataUtils;
import com.ytxd.common.util.R;
import com.ytxd.service.Water_Common.Water_Common_Service;
import org.apache.tools.ant.taskdefs.condition.Http;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @Classname Water_Common_Controller
 * @Author TY
 * @Date 2024/1/29 15:40
 * @Description TODO
 */
@RestController
@RequestMapping("/Water_Common")
public class Water_Common_Controller {
    @Resource
    private Water_Common_Service service;
    /**
     *
     * @Desription TODO 获取点位信息
     * @param request
     * @return com.ytxd.common.util.R
     * @date 2024/1/29 15:51
     * @Auther TY
     */
    @PostMapping("/getSiteInfoList")
    public R getSiteInfoList(HttpServletRequest request) throws Exception{
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        return R.ok(service.getSiteInfoList(obj));
    }
    /**
     *
     * @Desription TODO 获取列表数据
     * @param request
     * @return com.ytxd.common.util.R
     * @date 2024/2/21 16:54
     * @Auther TY
     */
    @PostMapping("/getSiteDataInfoList")
    public R getSiteDataInfoList(HttpServletRequest request) throws Exception{
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        return R.ok(service.getSiteDataInfoList(obj));
    }

    @RequestMapping("/getSiteWARNInfoList")
    public R getSiteWARNInfoList(HttpServletRequest request) {
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        return R.ok(service.selectSiteWARNInfoList(obj));
    }
}
