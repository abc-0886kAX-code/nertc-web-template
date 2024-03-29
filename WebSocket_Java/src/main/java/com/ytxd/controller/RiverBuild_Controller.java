package com.ytxd.controller;

import com.ytxd.common.DataUtils;
import com.ytxd.common.util.R;
import com.ytxd.service.RiverBuild.RiverBuild_Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @Classname RiverBuild_Controller
 * @Author TY
 * @Date 2023/8/17 16:09
 * @Description TODO 涉河建设项目维护
 */
@RestController("RiverBuild_Controller")
@RequestMapping("/RiverBuild")
public class RiverBuild_Controller {

    @Resource
    private RiverBuild_Service service;
    /**
     *
     * @Desription TODO 查询涉河建设项目列表
     * @param request
     * @return com.ytxd.common.util.R
     * @date 2023/8/17 16:11
     * @Auther TY
     */
    @PostMapping("/getRiverBuildList")
    public R getRiverBuildList(HttpServletRequest request) throws Exception{
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        return R.ok(service.getRiverBuildList(obj));
    }
    /**
     *
     * @Desription TODO 查询涉河建设项目的文件列表
     * @param request
     * @return com.ytxd.common.util.R
     * @date 2023/8/17 16:11
     * @Auther TY
     */
    @PostMapping("/getRiverBuildFileList")
    public R getRiverBuildFileList(HttpServletRequest request) throws Exception{
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        return R.ok(service.getRiverBuildFileList(obj));
    }
    /**
     *
     * @Desription TODO 保存涉河建设项目
     * @param request
     * @return com.ytxd.common.util.R
     * @date 2023/8/17 16:12
     * @Auther TY
     */
    @PostMapping("/SaveRiverBuild")
    public R SaveRiverBuild(HttpServletRequest request) throws Exception{
        Map<String,Object> obj = DataUtils.getRequestParamsAndUser(request);
        return R.ok().put("data",service.SaveRiverBuild(obj));
    }
    /**
     *
     * @Desription TODO 删除涉河建设项目
     * @param request
     * @return com.ytxd.common.util.R
     * @date 2023/8/17 16:12
     * @Auther TY
     */
    @GetMapping("/DeleteRiverBuild")
    public R DeleteRiverBuild(HttpServletRequest request) throws Exception{
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        return R.ok().put("data",service.DeleteRiverBuild(obj));
    }
    /**
     *
     * @Desription TODO 删除涉河建设项目的文件
     * @param request
     * @return com.ytxd.common.util.R
     * @date 2023/8/17 16:13
     * @Auther TY
     */
    @GetMapping("/DeleteRiverBuildFileById")
    public R DeleteRiverBuildFileById(HttpServletRequest request) throws Exception{
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        return R.ok().put("data",service.DeleteRiverBuildFileById(obj));
    }

}
