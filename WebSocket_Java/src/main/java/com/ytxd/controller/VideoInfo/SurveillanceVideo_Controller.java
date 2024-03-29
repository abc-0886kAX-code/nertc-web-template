package com.ytxd.controller.VideoInfo;

import com.ytxd.common.DataUtils;
import com.ytxd.common.util.R;
import com.ytxd.service.VideoInfo.SurveillanceVideo_Service;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @Classname SurveillanceVideo_Controller
 * @Author TY
 * @Date 2024/1/5 17:01
 * @Description TODO
 */
@RestController
@RequestMapping("/SurveillanceVideo")
public class SurveillanceVideo_Controller {
    @Resource
    private SurveillanceVideo_Service service;
    /**
     *
     * @Desription TODO 查询视频监控站点信息
     * @param request
     * @return com.ytxd.common.util.R
     * @date 2024/1/5 17:02
     * @Auther TY
     */
    @PostMapping("/getSurveillanceVideoList")
    public R getSurveillanceVideoList(HttpServletRequest request) throws Exception{
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        return R.ok(service.getSurveillanceVideoList(obj));
    }
}
