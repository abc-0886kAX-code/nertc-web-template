package com.ytxd.controller.Video;

import com.ytxd.common.DataUtils;
import com.ytxd.common.util.R;
import com.ytxd.request.TowerVideoRequest;
import com.ytxd.service.Viedo.OpenConversion;
import com.ytxd.service.Viedo.Video_Service;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 视频站点视频转码
 */
@RestController
@RequestMapping("/Video")
public class Video_Controller {
    @Resource
    private Video_Service service;
    @Resource
    private TowerVideoRequest towerVideoRequest;

    /**
     * 获取视频站点信息
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("/GetVideoInfoList")
    public R GetVideoInfoList(HttpServletRequest request) throws Exception{
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        return R.ok(service.GetVideoInfoList(obj));
    }

    /**
     * 开启视频转化
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("/OpenVideConversion")
    public void OpenVideConversion(HttpServletRequest request) throws Exception{
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        service.OpenVideConversion(obj);
    }
    /**
     * 停止
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("/destroyConversionTOFlv")
    public void destroyConversionTOFlv(HttpServletRequest request) throws Exception{
        OpenConversion openConversion = OpenConversion.getInstance("","");
        openConversion.destroyConversionTOFlv();
    }

    /**
     * 控制设备
     */
    @PostMapping("/DeviceControl")
    public R DeviceControl(HttpServletRequest request) {
        final String deviceId = request.getParameter("deviceId");
        final String command = request.getParameter("command");
        final String ptzTime = request.getParameter("ptzTime");
        if(StringUtils.isBlank(deviceId) || StringUtils.isBlank(command) || StringUtils.isBlank(ptzTime)){
            return R.error("参数异常");
        }
        int time = 30;
        try {
            time = Integer.parseInt(ptzTime);
        } catch (Exception e){
            return R.error("云台控制时长参数异常 只能为整数！");
        }
        if(towerVideoRequest.deviceControl(deviceId, command, time, 0)){
            return R.ok();
        }
        return R.error("设备控制异常");
    }
}
