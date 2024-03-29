package com.ytxd.controller.Water_Common.RealData;

import com.ytxd.common.DataUtils;
import com.ytxd.common.util.R;
import com.ytxd.service.Water_Common.RealData.Rsvr_Situation_Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @Classname Rsvr_Situation_Conterller
 * @Author TY
 * @Date 2024/2/4 16:19
 * @Description TODO
 */
@RestController("Rsvr_Situation")
@RequestMapping("/Water_Common/Rsvr_Situation")
public class Rsvr_Situation_Conterller {
    @Resource
    private Rsvr_Situation_Service service;
    /**
     *
     * @Desription TODO 获取水库最新的一条数据数据
     * @param request
     * @return com.ytxd.common.util.R
     * @date 2024/2/5 9:06
     * @Auther TY
     */
    @PostMapping("/getRsvrLevelList")
    public R getRsvrLevelList(HttpServletRequest request) throws Exception{
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        return R.ok(service.getRsvrLevelList(obj));
    }

    /**
     *
     * @Desription TODO 获取水库的时序数据
     * @param request
     * @return com.ytxd.common.util.R
     * @date 2024/2/5 9:06
     * @Auther TY
     */
    @PostMapping("/getRealRsvrLevelList")
    public R getRealRsvrLevelList(HttpServletRequest request) throws Exception{
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        return R.ok(service.getRealRsvrLevelList(obj));
    }
    /**
     *
     * @Desription TODO 获取水库的时序数据按小时展示
     * @param request
     * @return com.ytxd.common.util.R
     * @date 2024/2/5 9:06
     * @Auther TY
     */
    @PostMapping("/getRealRsvrLevelListHour")
    public R getRealRsvrLevelListHour(HttpServletRequest request) throws Exception{
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        return R.ok(service.getRealRsvrLevelListHour(obj));
    }

   /**
    *
    * @Desription TODO 获取水库的时序数据按天展示
    * @param request
    * @return com.ytxd.common.util.R
    * @date 2024/2/5 15:50
    * @Auther TY
    */
    @PostMapping("/getRealRsvrLevelListDay")
    public R getRealRsvrLevelListDay(HttpServletRequest request) throws Exception{
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        return R.ok(service.getRealRsvrLevelListDay(obj));
    }

    /**
     * @Description: 获取所有水库条数、大中型水库条数、小型水库条数
     * @param request
     * @return: com.ytxd.common.util.R
     * @Author: ZJW
     * @Date: 2024/3/18 11:27
     */
    @RequestMapping("/getRsvrStation")
    public R getRsvrStation(HttpServletRequest request){
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        HashMap<String, Object> map = new HashMap<>();
        map.put("data", service.selectRsvrStation(obj));
        return R.ok(map);
    }

    @RequestMapping("/getFloodLevelExceededNum")
    public R getFloodLevelExceededNum(HttpServletRequest request) {
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        HashMap<String, Object> map = new HashMap<>();
        map.put("data", service.selectFloodLevelExceededNum(obj));
        return R.ok(map);
    }

    @RequestMapping("/getFloodLevelExceeded")
    public R selectFloodLevelExceeded(HttpServletRequest request) {
        Map<String,Object> obj = DataUtils.getParameterMap(request);
//        System.out.println("==========================="+obj);
        HashMap<String, Object> map = new HashMap<>();
        map.put("data", service.selectFloodLevelExceeded(obj));
        return R.ok(map);
    }




}
