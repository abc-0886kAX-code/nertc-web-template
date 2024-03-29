package com.ytxd.controller.Water_Common.RealData;

import com.ytxd.common.DataUtils;
import com.ytxd.common.util.R;
import com.ytxd.service.Water_Common.RealData.Reservoir_Situation_Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @Classname Reservoir_Situation_Controller
 * @Author TY
 * @Date 2023/12/22 16:58
 * @Description TODO
 */
@RestController
@RequestMapping("/Water_Common/Reservoir_Situation")
public class Reservoir_Situation_Controller {
    @Resource
    private Reservoir_Situation_Service service;
    /**
     *
     * @Desription TODO 获取水库站点的实时数据
     * @param request
     * @return com.ytxd.common.util.R
     * @date 2023/12/22 17:00
     * @Auther TY
     */
    @PostMapping("/getRealReservoirList")
    public R getRealReservoirList(HttpServletRequest request) throws Exception{
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        return R.ok(service.getRealReservoirList(obj));
    }

}
