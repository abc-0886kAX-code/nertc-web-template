package com.ytxd.controller.API.Storm_Plan;

import com.ytxd.common.DataUtils;
import com.ytxd.common.util.R;
import com.ytxd.service.API.Storm_Plan.Storm_Plan_Service;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 预案
 */
@RestController("Storm_Plan_Controller")
@RequestMapping("/Storm_Plan")
@Api(value = "预案信息接口", tags = "预案信息接口")
public class Storm_Plan_Controller {
    @Resource
    private Storm_Plan_Service service;

    /**
     * 获取列表信息
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("/GetList")
    public R GetList(HttpServletRequest request) throws Exception{
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        return R.ok(service.GetList(obj));
    }

    @PostMapping("/GetListLocal")
    public R GetListLocal(HttpServletRequest request) throws Exception{
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        return R.ok(service.GetListLocal(obj));
    }

    /**
     * 获取定时预案列表信息
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("/GetRegularList")
    public R GetRegularList(HttpServletRequest request) throws Exception{
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        return R.ok(service.GetRegularList(obj));
    }

    /**
     * 根据Id获取信息
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("/GetListById")
    public R GetListById(HttpServletRequest request) throws Exception{
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        return R.ok().put("data",service.GetListById(obj));
    }

    @PostMapping("/GetListByIdLocal")
    public R GetListByIdLocal(HttpServletRequest request) throws Exception{
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        return R.ok().put("data",service.GetListByIdLocal(obj));
    }

    /**
     * 保存信息
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("/Save")
    public R Save(HttpServletRequest request) throws Exception{
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        return R.ok().put("data",service.Save(obj));
    }

    /**
     * 删除
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("/Delete")
    public R Delete(HttpServletRequest request) throws Exception{
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        return R.ok().put("data",service.Delete(obj));
    }

    /**
     * 获取预报降雨信息
     */
    @PostMapping("/GetForecastRainfall")
    public R GetForecastRainfall(HttpServletRequest request) {
        return R.ok().put("data", service.GetForecastRainfall());
    }

    @PostMapping("/CheckPlanId")
    public R CheckPlanId(HttpServletRequest request) throws Exception{
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        String plan_id = DataUtils.getMapKeyValue(obj, "plan_id");
        try{
            final Map<String, Object> map = service.GetListByIdLocal(obj);
            if(map != null && map.containsKey("pid")){
                plan_id = DataUtils.getMapKeyValue(map, "pid");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        Map<String, Object> map = new HashMap<>();
        map.put("plan_id", plan_id);
        return R.ok().put("data", map);
    }
    /**
     *
     * @Desription TODO 调度预案保存
     * @param request
     * @return com.ytxd.common.util.R
     * @date 2023/11/14 15:30
     * @Auther TY
     */
    @PostMapping("/originalSave")
    public R originalSave(HttpServletRequest request) throws Exception{
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        return R.ok().put("data",service.originalSave(obj));
    }
    /**
     *
     * @Desription TODO 调度预案删除
     * @param request
     * @return com.ytxd.common.util.R
     * @date 2023/11/14 15:44
     * @Auther TY
     */
    @PostMapping("/originalDelete")
    public R originalDelete(HttpServletRequest request) throws Exception{
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        return R.ok().put("data",service.originalDelete(obj));
    }
}