package com.ytxd.controller.API.SiteInfo;

import com.github.pagehelper.PageInfo;
import com.ytxd.common.DataUtils;
import com.ytxd.common.util.R;
import com.ytxd.controller.BaseController;
import com.ytxd.service.API.SiteInfo.SiteInfo_Service;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 站点信息表
 */
@RestController("SiteInfo_Controller")
@RequestMapping("/SiteInfo")
@Api(value = "站点信息接口", tags = "站点信息接口")
public class SiteInfo_Controller extends BaseController {
    @Resource
    private SiteInfo_Service service;
    /**
     * 获取点位信息
     * @param request
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "接口说明：站点信息接口"
            , notes = "接口说明：站点信息接口。<br>"
            + "使用位置：展示站点信息的位置<br>"
            + "逻辑说明：获取站点信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "stcd", value = "站码", dataType = "String", required = false),
            @ApiImplicitParam(name = "stnm", value = "站名", dataType = "String", required = false),
            @ApiImplicitParam(name = "sttp", value = "站点类别（CODEID=TP）", dataType = "String", required = false),
            @ApiImplicitParam(name = "flag", value = "是否启用（CODEID=FD）", dataType = "String", required = false),
            @ApiImplicitParam(name = "state", value = "是否可用（CODEID=FD）", dataType = "String", required = false),
    })
    @PostMapping("/GetSiteIntoList")
    public R GetSiteIntoList(HttpServletRequest request) throws Exception{
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        return R.ok().put("data",service.GetSiteInfoList(obj));
    }
    /**
     * 获取闸门信息
     * @param request
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "接口说明：获取闸门信息"
            , notes = "接口说明：获取闸门信息。<br>"
            + "使用位置：展示获取闸门信息的位置<br>"
            + "逻辑说明：获取闸门信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "stcd", value = "站码", dataType = "String", required = false),
            @ApiImplicitParam(name = "stnm", value = "站名", dataType = "String", required = false),
            @ApiImplicitParam(name = "sttp", value = "站类（CODEID=TP）", dataType = "String", required = false),
            @ApiImplicitParam(name = "flag", value = "是否启用（CODEID=FD）", dataType = "String", required = false),
            @ApiImplicitParam(name = "sign", value = "站点类别（AA）", dataType = "String", required = false),
    })
    @PostMapping("/GetSiteGateList")
    public R GetSiteGateList(HttpServletRequest request) throws Exception{
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        return R.ok().put("data",service.GetSiteGateList(obj));
    }
    /**
     * 获取报警信息
     * @param request
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "接口说明：获取报警信息"
            , notes = "接口说明：获取报警信息。<br>"
            + "使用位置：展示获取报警信息的位置<br>"
            + "逻辑说明：获取报警信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "stcd", value = "站码", dataType = "String", required = false),
            @ApiImplicitParam(name = "stnm", value = "站名", dataType = "String", required = false),
            @ApiImplicitParam(name = "state", value = "是否处理（FD）", dataType = "String", required = false),
            @ApiImplicitParam(name = "flag", value = "是否启用（CODEID=FD）", dataType = "String", required = false),
            @ApiImplicitParam(name = "id", value = "主键", dataType = "String", required = false),
    })
    @PostMapping("/GetEarlyWarningList")
    public R GetEarlyWarningList(HttpServletRequest request) throws Exception{
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        PageInfo pageInfo = service.GetEarlyWarningList(obj);
        return R.ok().put("data",pageInfo.getList()).put("total",pageInfo.getTotal());
    }

    /**
     * 根据不同的大厅展示不同的站点信息
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("/GetSTSubhallSiteList")
    public R GetSTSubhallSiteList(HttpServletRequest request) throws Exception{
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        return R.ok(service.GetSTSubhallSiteList(obj));
    }

    /**
     * @Description: 视频监测站点数量查询
     * @param request
     * @return: com.ytxd.common.util.R
     * @Author: ZJW
     * @Date: 2024/3/19 14:59
     */
    @RequestMapping("/getVideoStationNum")
    public R getVideoStationNum(HttpServletRequest request) {
        Map<String,Object> map = DataUtils.getParameterMap(request);
        HashMap<String, Object> reVal = new HashMap<>();
        reVal.put("data" , service.selectVideoStationNum(map));
        return R.ok(reVal);
    }
}
