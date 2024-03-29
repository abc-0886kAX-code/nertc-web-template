package com.ytxd.controller.Meteorology;

import com.ytxd.common.DataUtils;
import com.ytxd.common.util.R;
import com.ytxd.service.Meteorology.Meteorology_Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @Classname Meteorology_Controller
 * @Author TY
 * @Date 2023/10/16 10:07
 * @Description TODO
 */
@RestController("Meteorology_Controller")
@RequestMapping("/Meteorology")
public class Meteorology_Controller {
    @Resource
    private Meteorology_Service service;
    /**
     *
     * @Desription TODO 获取站点的基本信息
     * @param request
     * @return com.ytxd.common.util.R
     * @date 2023/10/16 10:09
     * @Auther TY
     */
    @PostMapping("/getMeteorologyInfo")
    public R getMeteorologyInfo(HttpServletRequest request) throws Exception {
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        return R.ok(service.getMeteorologyInfo(obj));
    }
    /**
     *
     * @Desription TODO 获取气象数据
     * @param request
     * @return com.ytxd.common.util.R
     * @date 2023/10/16 10:11
     * @Auther TY
     */
    @PostMapping("/getMeteorologyList")
    public R getMeteorologyList(HttpServletRequest request) throws Exception {
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        return R.ok(service.getMeteorologyList(obj));
    }
}
