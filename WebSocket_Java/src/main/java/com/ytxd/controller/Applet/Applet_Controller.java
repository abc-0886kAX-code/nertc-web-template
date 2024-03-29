package com.ytxd.controller.Applet;

import com.ytxd.common.DataUtils;
import com.ytxd.common.util.R;
import com.ytxd.service.Applet.Applet_Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 微信小程序
 */
@RestController("Applet_Controller")
@RequestMapping("/Applet")
public class Applet_Controller {
    @Resource
    private Applet_Service service;

    /**
     * 获取闸门的水情信息
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("/GetStWasRList")
    public R GetStWasRList(HttpServletRequest request) throws Exception{
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        return R.ok(service.GetStWasRList(obj));
    }

    /**
     * 获取天气预报15日的数据，按场次进行分组
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("/GetAppletPredictionRainList")
    public R GetAppletPredictionRainList(HttpServletRequest request) throws Exception{
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        return R.ok(service.GetAppletPredictionRainList(obj));
    }

    /**
     * 按年月日的时间查询天气预报每小时的数据
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("/GetAppletPredictionRainListByHour")
    public R GetAppletPredictionRainListByHour(HttpServletRequest request) throws Exception{
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        return R.ok(service.GetAppletPredictionRainListByHour(obj));
    }
    /**
     * 取今日降雨量、年初至今降雨量、去年同期降水、多年平均降水 用 3212000007
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("/GetStaticInfoList")
    public R GetStaticInfoList(HttpServletRequest request) throws Exception{
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        return R.ok().put("data",service.GetStaticInfoList(obj));
    }
}
