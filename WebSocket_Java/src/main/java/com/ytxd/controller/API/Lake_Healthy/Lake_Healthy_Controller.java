package com.ytxd.controller.API.Lake_Healthy;

import com.github.pagehelper.PageInfo;
import com.ytxd.common.DataUtils;
import com.ytxd.common.util.R;
import com.ytxd.service.API.Lake_Healthy.Lake_Healthy_Service;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 幸福河湖
 */
@RestController("Lake_Healthy_Controller")
@RequestMapping("/Lake_Healthy")
@Api(value = "幸福河湖", tags = "幸福河湖")
public class Lake_Healthy_Controller {
    @Resource
    private Lake_Healthy_Service service;

    /**
     * 查询幸福河湖信息
     * @param request
     * @return
     * @throws Exception
     */
    @GetMapping("/GetLakeHealthyList")
    public R GetLakeHealthyList(HttpServletRequest request) throws Exception{
       Map<String,Object> obj =  DataUtils.getParameterMap(request);
       PageInfo pageInfo = service.GetLakeHealthyList(obj);
       return R.ok().put("data",pageInfo.getList()).put("total",pageInfo.getTotal());
    }

    /**
     * 人数统计
     * @param request
     * @return
     * @throws Exception
     */
    @GetMapping("/GetPeopleStatistics")
    public R GetPeopleStatistics(HttpServletRequest request) throws Exception{
       Map<String,Object> obj =  DataUtils.getParameterMap(request);
        List<Map<String,Object>> list = service.GetPeopleStatistics(obj);
       return R.ok().put("data",list);
    }

    /**
     * 水资源统计
     * @param request
     * @return
     * @throws Exception
     */
    @GetMapping("/GetWaterResourceList")
    public R GetWaterResourceList(HttpServletRequest request) throws Exception{
       Map<String,Object> obj =  DataUtils.getParameterMap(request);
        List<Map<String,Object>> list = service.GetWaterResourceList(obj);
       return R.ok().put("data",list);
    }

    /**
     * 幸福河湖健康码
     * @param request
     * @return
     * @throws Exception
     */
    @GetMapping("/GetLakeBlissList")
    public R GetLakeBlissList(HttpServletRequest request) throws Exception{
        Map<String,Object> obj =  DataUtils.getParameterMap(request);
        return R.ok(service.GetLakeBlissList(obj));
    }

    /**
     * 幸福河湖卫星遥感图
     */
    @GetMapping("/GetRemoteSensing")
    public R GetRemoteSensing(HttpServletRequest request) throws Exception{
        Map<String,Object> obj =  DataUtils.getParameterMap(request);
        return R.ok(service.GetRemoteSensingList(obj));
    } 
    /**
     *
     * @Desription TODO 幸福河湖卫星遥感(按月份查询)
     * @param request
     * @return com.ytxd.common.util.R
     * @date 2023/9/21 10:53
     * @Auther TY
     */
    @GetMapping("/getRemoteSensingListByMonth")
    public R getRemoteSensingListByMonth(HttpServletRequest request) throws Exception{
        Map<String,Object> obj =  DataUtils.getParameterMap(request);
        return R.ok().put("data",service.getRemoteSensingListByMonth(obj));
    }

    /**
     *  获取每个类型最后一条数据
     * @param request
     * @return
     * @throws Exception
     */
    @GetMapping("/getLastRemoteSensingList")
    public R getLastRemoteSensingList(HttpServletRequest request) throws Exception{
        Map<String,Object> obj =  DataUtils.getParameterMap(request);
        return R.ok(service.getLastRemoteSensingList(obj));
    }
}
