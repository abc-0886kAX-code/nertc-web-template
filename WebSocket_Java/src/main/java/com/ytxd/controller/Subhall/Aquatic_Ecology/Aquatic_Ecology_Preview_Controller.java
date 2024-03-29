package com.ytxd.controller.Subhall.Aquatic_Ecology;

import com.ytxd.common.DataUtils;
import com.ytxd.common.util.R;
import com.ytxd.service.API.Storm_Plan.Storm_Plan_Ecology_Local_Service;
import com.ytxd.service.API.Storm_Plan.Storm_Plan_Environment_Local_Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 水生态 -预演调度
 */
@RestController("Aquatic_Ecology_Preview_Controller")
@RequestMapping("/Aquatic_Ecology/Preview")
public class Aquatic_Ecology_Preview_Controller {

    @Resource
    private Storm_Plan_Ecology_Local_Service ecology_service;
    @Resource
    private Storm_Plan_Environment_Local_Service environment_service;
    /**
     * 水生态预案列表
     */
    @PostMapping("/Ecology/GetList")
    public R GetList(HttpServletRequest request) throws Exception{
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        return R.ok(ecology_service.GetList(obj));
    }
    /**
     * 水生态预案详情
     */
    @PostMapping("/Ecology/GetListById")
    public R GetListById(HttpServletRequest request) throws Exception{
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        return R.ok().put("data",ecology_service.GetListById(obj));
    }
    /**
     * 水生态预案详情保存
     */
    @PostMapping("/Ecology/Save")
    public R Save(HttpServletRequest request) throws Exception{
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        return R.ok().put("data",ecology_service.Save(obj));
    }
    /**
     * 水生态预案删除
     */
    @PostMapping("/Ecology/Delete")
    public R Delete(HttpServletRequest request) throws Exception{
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        return R.ok().put("data",ecology_service.Delete(obj));
    }

    @PostMapping("/Ecology/GetDefault")
    public R GetDefault(HttpServletRequest request) throws Exception{
        return R.ok().put("data",ecology_service.GetDefault());
    }

    /**
     * 水环境预案列表
     */
    @PostMapping("/Environment/GetList")
    public R GetEnvironmentList(HttpServletRequest request) throws Exception{
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        return R.ok(environment_service.GetList(obj));
    }
    /**
     * 水环境预案详情
     */
    @PostMapping("/Environment/GetListById")
    public R GetEnvironmentListById(HttpServletRequest request) throws Exception{
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        return R.ok().put("data",environment_service.GetListById(obj));
    }
    /**
     * 水环境预案详情保存
     */
    @PostMapping("/Environment/Save")
    public R SaveEnvironment(HttpServletRequest request) throws Exception{
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        return R.ok().put("data",environment_service.Save(obj));
    }
    /**
     * 水环境预案删除
     */
    @PostMapping("/Environment/Delete")
    public R DeleteEnvironment(HttpServletRequest request) throws Exception{
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        return R.ok().put("data",environment_service.Delete(obj));
    }
    /**
     * 水环境预案下拉列表
     */
    @PostMapping("/Environment/GetStormplanenvironmentSelected")
    public R GetStormplanenvironmentSelected(HttpServletRequest request) throws Exception{
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        return R.ok().put("data",environment_service.GetStormplanenvironmentSelected(obj));
    }

    @PostMapping("/Environment/GetDefault")
    public R GetEnvironmentDefault(HttpServletRequest request) throws Exception{
        return R.ok().put("data",ecology_service.GetDefault());
    }

}
