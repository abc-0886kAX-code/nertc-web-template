package com.ytxd.controller.Water_Common.RealData;

import com.github.pagehelper.PageInfo;
import com.ytxd.common.DataUtils;
import com.ytxd.common.util.R;
import com.ytxd.service.Water_Common.RealData.Worker_Situation_Service;
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

@RestController("Worker_Situation_Controller")
@RequestMapping("/Water_Common/Worker_Situation")
@Api(tags = "实时工情相关" , value = "实时工情相关")
public class Worker_Situation_Controller {
    @Resource
    private Worker_Situation_Service service;

    /**
     * 取最新的工情数据 取最新的一条
     * @param request
     * @return
     */
    @ApiOperation(value = "接口说明：取最新的工情数据 取最新的一条"
            , notes = "接口说明：取最新的工情数据 取最新的一条。<br>"
            + "使用位置：取最新的工情数据 取最新的一条<br>"
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
    @PostMapping("/GetWorkerList")
    public R GetWorkerList(HttpServletRequest request)  throws Exception{
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        return R.ok(service.GetWorkerList(obj));
    }

    /**
     * 取实时的工情数据
     * @param request
     * @return
     */
    @ApiOperation(value = "接口说明：取实时的工情数据"
            , notes = "接口说明：取实时的工情数据。<br>"
            + "使用位置：取实时的工情数据<br>"
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
    @PostMapping("/GetRealWorkerList")
    public R GetRealWorkerList(HttpServletRequest request)  throws Exception{
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        return R.ok(service.GetRealWorkerList(obj));
    }
}
