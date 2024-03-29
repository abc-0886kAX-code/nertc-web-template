package com.ytxd.controller.Water_Common.RealData;

import com.ytxd.common.DataUtils;
import com.ytxd.common.util.R;
import com.ytxd.service.Water_Common.RealData.Water_Quality_Service;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController("Water_Quality_Controller")
@RequestMapping("/Water_Common/Water_Quality")
@Api(tags = "实时水质相关" , value = "实时水质相关")
public class Water_Quality_Controller {
    @Resource
    private Water_Quality_Service service;

    /**
     * 取最新的水质数据 取最新的一条
     * @param request
     * @return
     */
    @ApiOperation(value = "接口说明：取最新的水质数据 取最新的一条"
            , notes = "接口说明：取最新的水质数据 取最新的一条。<br>"
            + "使用位置：取最新的水质数据 取最新的一条<br>"
            + "字段说明：")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "stcd", value = "站码", dataType = "String", required = false),
            @ApiImplicitParam(name = "stnm", value = "站名", dataType = "String", required = false),
            @ApiImplicitParam(name = "flag", value = "是否启用（CODEID=FD）", dataType = "String", required = false),
            @ApiImplicitParam(name = "state", value = "是否可用（CODEID=FD）", dataType = "String", required = false),
            @ApiImplicitParam(name = "sign", value = "站点类型（AA）", dataType = "String", required = false),
            @ApiImplicitParam(name = "starttime", value = "开始时间", dataType = "String", required = false),
            @ApiImplicitParam(name = "endtime", value = "结束时间", dataType = "String", required = false),
    })
    @PostMapping("/GetWaterQualityList")
    public R GetWaterQualityList(HttpServletRequest request)  throws Exception{
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        return R.ok(service.GetWaterQualityList(obj));
    }

    /**
     * 取某时间段内水质的实时数据
     * @param request
     * @return
     */
    @ApiOperation(value = "接口说明：取某时间段内水质的实时数据"
            , notes = "接口说明：取某时间段内水质的实时数据。<br>"
            + "使用位置：取某时间段内水质的实时数据<br>"
            + "字段说明：")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "stcd", value = "站码", dataType = "String", required = false),
            @ApiImplicitParam(name = "stnm", value = "站名", dataType = "String", required = false),
            @ApiImplicitParam(name = "flag", value = "是否启用（CODEID=FD）", dataType = "String", required = false),
            @ApiImplicitParam(name = "state", value = "是否可用（CODEID=FD）", dataType = "String", required = false),
            @ApiImplicitParam(name = "sign", value = "站点类型（AA）", dataType = "String", required = false),
            @ApiImplicitParam(name = "starttime", value = "开始时间", dataType = "String", required = false),
            @ApiImplicitParam(name = "endtime", value = "结束时间", dataType = "String", required = false),
            @ApiImplicitParam(name = "isclassify", value = "是否分类(FD)，默认不分类", dataType = "String", required = false),
    })
    @PostMapping("/GetRealWaterQualityList")
    public R GetRealWaterQualityList(HttpServletRequest request)  throws Exception{
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        return R.ok(service.GetRealWaterQualityList(obj));
    }

    /**
     * 取某时间段内水质的月均值统计
     * @param request
     * @return
     */
    @ApiOperation(value = "接口说明：取某时间段内水质的月均值统计"
            , notes = "接口说明：取某时间段内水质的月均值统计。<br>"
            + "使用位置：取某时间段内水质的月均值统计<br>"
            + "字段说明：")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "stcd", value = "站码", dataType = "String", required = false),
            @ApiImplicitParam(name = "stnm", value = "站名", dataType = "String", required = false),
            @ApiImplicitParam(name = "flag", value = "是否启用（CODEID=FD）", dataType = "String", required = false),
            @ApiImplicitParam(name = "state", value = "是否可用（CODEID=FD）", dataType = "String", required = false),
            @ApiImplicitParam(name = "sign", value = "站点类型（AA）", dataType = "String", required = false),
            @ApiImplicitParam(name = "starttime", value = "开始时间", dataType = "String", required = false),
            @ApiImplicitParam(name = "endtime", value = "结束时间", dataType = "String", required = false),
            @ApiImplicitParam(name = "isclassify", value = "是否分类(FD)，默认不分类", dataType = "String", required = false),
    })
    @PostMapping("/GetRealWaterQualityListByMonth")
    public R GetRealWaterQualityListByMonth(HttpServletRequest request)  throws Exception{
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        return R.ok(service.GetRealWaterQualityListByMonth(obj));
    }
}
