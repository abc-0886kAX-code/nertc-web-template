package com.ytxd.controller.Subhall.Water_Culture;

import com.ytxd.common.DataUtils;
import com.ytxd.common.annotation.AuthIgnore;
import com.ytxd.common.util.R;
import com.ytxd.service.Subhall.Water_Culture.Water_Culture_Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 知识库
 * @author CYC
 */
@RestController("Water_Culture_Controller")
@RequestMapping("/Water_Culture")
public class Water_Culture_Controller {
    @Resource
    private Water_Culture_Service service;

    /**
     * 查询知识库信息
     */
    @AuthIgnore
    @PostMapping("/GetZSKMHList")
    public R getZskmhlList(HttpServletRequest request) throws Exception {
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        return R.ok(service.getZskmhList(obj));
    }

    /**
     * 查询水文化信息
     */
    @AuthIgnore
    @PostMapping("/GetWaterCulture")
    public R getWaterCulture(HttpServletRequest request) throws Exception {
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        return R.ok(service.GetWaterCulture(obj));
    }

    /**
     * 通过id查询水文化信息
     */
    @AuthIgnore
    @PostMapping("/GetWaterCultureByID")
    public R getWaterCultureByid(HttpServletRequest request) throws Exception {
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        return R.ok().put("data",service.GetWaterCultureByID(obj));
    }

    /**
     *水利基础知识
     */
    @AuthIgnore
    @PostMapping("/GetWaterDay")
    public R getWaterDay(HttpServletRequest request) throws Exception {
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        return R.ok().put("data",service.getWaterDay(obj));
    }

}
