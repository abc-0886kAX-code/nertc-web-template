package com.ytxd.controller.Water_Common.RealData;

import com.ytxd.common.DataUtils;
import com.ytxd.common.util.R;
import com.ytxd.service.Water_Common.RealData.Rain_Situation_Service;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 实时雨情相关
 */
@RestController("Rain_Situation_Controller")
@RequestMapping("/Water_Common/Rain_Situation")
@Api(tags = "实时雨情相关" , value = "实时雨情相关")
public class Rain_Situation_Controller {
    @Resource
    private Rain_Situation_Service service;

    /**
     * 获取某一段时间内的累计降雨数据
     * @param request
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "接口说明：获取某一段时间内的累计降雨数据"
            , notes = "接口说明：获取某一段时间内的累计降雨数据。<br>"
            + "使用位置：展示某一段时间内的累计降雨数据<br>"
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
    @PostMapping("/GetRainCumulativeList")
    public R GetRainCumulativeList(HttpServletRequest request) throws Exception{
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        return R.ok(service.GetRainCumulativeList(obj));
    }

    /**
     * 根据站点获取某一段时间内实时降雨数据
     * @param request
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "接口说明：根据站点获取某一段时间内实时降雨数据"
            , notes = "接口说明：根据站点获取某一段时间内实时降雨数据。<br>"
            + "使用位置：根据站点获取某一段时间内实时降雨数据<br>"
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
    @PostMapping("/GetRealRainList")
    public R GetRealRainList(HttpServletRequest request) throws Exception{
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        return R.ok(service.GetRealRainList(obj));
    }
    /**
     *
     * @Desription TODO 雨情代表站站点信息接口
     * @param request
     * @return com.ytxd.common.util.R
     * @date 2024/1/29 14:28
     * @Auther TY
     */
    @PostMapping("/GetRealRainListByRepresentative")
    public R GetRealRainListByRepresentative(HttpServletRequest request) throws Exception{
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        return R.ok(service.GetRealRainListByRepresentative(obj));
    }
    /**
     * 根据站点获取某一段时间内实时降雨数据以小时进行展示
     * @param request
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "接口说明：根据站点获取某一段时间内实时降雨数据以小时进行展示"
            , notes = "接口说明：根据站点获取某一段时间内实时降雨数据以小时进行展示。<br>"
            + "使用位置：根据站点获取某一段时间内实时降雨数据以小时进行展示<br>"
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
    @PostMapping("/GetRealRainListByHour")
    public R GetRealRainListByHour(HttpServletRequest request) throws Exception{
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        return R.ok(service.GetRealRainListByHour(obj));
    }
    /**
     * 根据站点获取某一段时间内实时降雨数据按天进行展示
     * @param request
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "接口说明：根据站点获取某一段时间内实时降雨数据按天进行展示"
            , notes = "接口说明：根据站点获取某一段时间内实时降雨数据按天进行展示。<br>"
            + "使用位置：根据站点获取某一段时间内实时降雨数据按天进行展示<br>"
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
    @PostMapping("/GetRealRainListByDay")
    public R GetRealRainListByDay(HttpServletRequest request) throws Exception{
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        return R.ok(service.GetRealRainListByDay(obj));
    }
    @PostMapping("/auth")
    public void getVideo(String token, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        httpServletResponse.setStatus(200);
    }
    @GetMapping("/GetSelectionItemList")
    public R GetSelectionItemList(HttpServletRequest request) throws Exception{
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        return R.ok().put("data",service.GetSelectionItemList(obj));
    }

    /**
     * 获取雨量预警信息
     * @param request
     * @return
     * @throws Exception
     */
    @GetMapping("/GetEarlyWarningList")
    public R GetEarlyWarningList(HttpServletRequest request) throws Exception{
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        return R.ok().put("data",service.GetEarlyWarningList(obj));
    }

    /**
     * 获取前后3天的雨情信息
     * @param
     * @return
     */
    @PostMapping("/GetRainLevelInfoList")
    public R GetRainLevelInfoList(HttpServletRequest request) throws Exception{
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        return R.ok().put("data",service.GetRainLevelInfoList(obj));
    }

    /**
     * 通过时间查询8-8点的累计降雨量
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("/GetRainDayStatistics")
    public R GetRainDayStatistics(HttpServletRequest request) throws Exception{
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        return R.ok().put("data",service.GetRainDayStatistics(obj));
    }
    /**
     *
     * @Desription TODO 获取年度降雨量
     * @param request
     * @return com.ytxd.common.util.R
     * @date 2024/1/29 11:30
     * @Auther TY
     */
    @PostMapping("/GetRealRainListByYear")
    public R GetRealRainListByYear(HttpServletRequest request) throws Exception{
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        return R.ok(service.GetRealRainListByYear(obj));
    }
}
