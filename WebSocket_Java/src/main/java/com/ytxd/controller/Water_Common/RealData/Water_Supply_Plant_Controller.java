package com.ytxd.controller.Water_Common.RealData;

import com.ytxd.common.DataUtils;
import com.ytxd.common.util.R;
import com.ytxd.service.Water_Common.RealData.Water_Supply_Plant_Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @Classname Water_Supply_Plant_Controller
 * @Author TY
 * @Date 2023/12/27 14:31
 * @Description TODO 供水厂信息
 */
@RestController
@RequestMapping("/Water_Common/Water_Supply_Plant")
public class Water_Supply_Plant_Controller {
    /**
     *
     * @Desription TODO 获取供水厂信息
     * @param null
     * @return 
     * @date 2023/12/27 14:33
     * @Auther TY
     */
    @Resource
    private Water_Supply_Plant_Service service;
    @PostMapping("/getWaterSupplyPlantList")
    public R getWaterSupplyPlantList(HttpServletRequest request)  throws Exception{
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        return R.ok(service.getWaterSupplyPlantList(obj));
    }
    /**
     *
     * @Desription TODO 获取时序数据
     * @param request
     * @return com.ytxd.common.util.R
     * @date 2023/12/27 14:33
     * @Auther TY
     */
    @PostMapping("/getRealWaterSupplyPlantList")
    public R getRealWaterSupplyPlantList(HttpServletRequest request)  throws Exception{
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        return R.ok(service.getRealWaterSupplyPlantList(obj));
    }
}
