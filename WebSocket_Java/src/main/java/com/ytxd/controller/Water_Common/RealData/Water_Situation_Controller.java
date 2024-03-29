package com.ytxd.controller.Water_Common.RealData;

import com.ytxd.common.DataUtils;
import com.ytxd.common.util.R;
import com.ytxd.service.Water_Common.RealData.Water_Situation_Service;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController("Water_Situation_Controller")
@RequestMapping("/Water_Common/Water_Situation")
@Api(tags = "实时水情相关" , value = "实时水情相关")
public class Water_Situation_Controller {
    @Resource
    private Water_Situation_Service service;
    /**
     * 取最新的水情数据 取最新的一条
     * @param request
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "接口说明：取最新的水情数据 取最新的一条"
            , notes = "接口说明：取最新的水情数据 取最新的一条。<br>"
            + "使用位置：取最新的水情数据 取最新的一条<br>"
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
    @PostMapping("/GetWaterLevelList")
    public R GetWaterLevelList(HttpServletRequest request) throws Exception{
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        return R.ok(service.GetWaterLevelList(obj));
    }

    /**
     * 取实时水情数据
     * @param request
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "接口说明：取实时水情数据"
            , notes = "接口说明：取实时水情数据。<br>"
            + "使用位置：取实时水情数据<br>"
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
    @PostMapping("/GetRealWaterLevelList")
    public R GetRealWaterLevelList(HttpServletRequest request) throws Exception{
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        return R.ok(service.GetRealWaterLevelList(obj));
    }

    /**
     * 取实时水情数据 按小时展示
     * @param request
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "接口说明：取实时水情数据 按小时展示"
            , notes = "接口说明：取实时水情数据 按小时展示。<br>"
            + "使用位置：取实时水情数据 按小时展示<br>"
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
    @PostMapping("/GetRealWaterLevelListByHour")
    public R GetRealWaterLevelListByHour(HttpServletRequest request) throws Exception{
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        return R.ok(service.GetRealWaterLevelListByHour(obj));
    }

    /**
     * 取实时水情数据 按天展示
     * @param request
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "接口说明：取实时水情数据 按天展示"
            , notes = "接口说明：取实时水情数据 按天展示。<br>"
            + "使用位置：取实时水情数据 按天展示<br>"
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
    @PostMapping("/GetRealWaterLevelListByDay")
    public R GetRealWaterLevelListByDay(HttpServletRequest request) throws Exception{
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        return R.ok(service.GetRealWaterLevelListByDay(obj));
    }

    /**
     * 获取前后三天的预报信息
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("/GetWaterLevelInfoList")
    public R GetWaterLevelInfoList(HttpServletRequest request) throws Exception{
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        return R.ok(service.GetWaterLevelInfoList(obj));
    }

    /**
     * 获取时间段内的小时库容水位信息
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("/GetSTRsvrRListByHour")
    public R GetSTRsvrRListByHour(HttpServletRequest request) throws Exception{
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        return R.ok(service.GetSTRsvrRListByHour(obj));
    }
    @PostMapping("/getSTRsvrRLastData")
    public R getSTRsvrRLastData(HttpServletRequest request) throws Exception{
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        return R.ok(service.getSTRsvrRLastData(obj));
    }
    @ApiOperation(value = "接口说明：取同期实时水情数据 按小时展示"
            , notes = "接口说明：取同期实时水情数据 按小时展示。<br>"
            + "使用位置：取同期实时水情数据 按小时展示<br>"
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
    @PostMapping("/GetTQRealWaterLevelListByHour")
    public R GetTQRealWaterLevelListByHour(HttpServletRequest request) throws Exception{
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        return R.ok(service.GetTQRealWaterLevelListByHour(obj));
    }


    @RequestMapping("/getExceedLevee")
    public R getExceedLevee(HttpServletRequest request){
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        return R.ok(service.selectExceedLevee(obj));
    }

    @RequestMapping("/getExceedGuaranteed")
    public R getExceedGuaranteed( HttpServletRequest request) {
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        return R.ok(service.selectExceedGuaranteed(obj));
    }

    @RequestMapping("/getExceedAlertLevel")
    public R getExceedAlertLevel( HttpServletRequest request) {
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        return R.ok(service.selectExceedAlertLevel(obj));
    }

    @RequestMapping("/getRiverStationInfo")
    public R getRiverStationInfo(HttpServletRequest request) {
        Map<String,Object> map = DataUtils.getParameterMap(request);
        return R.ok(service.selectRiverStationInfo(map));
    }

    @RequestMapping("/getExceedNum")
    public R getExceedNum( HttpServletRequest request) {
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        HashMap<String, Object> reVal = new HashMap<>();
        reVal.put("data" , service.selectExceedNum(obj));
        return R.ok(reVal);
    }
}
