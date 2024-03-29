package com.ytxd.controller.Subhall.RiverAndLake;

import com.ytxd.common.DataUtils;
import com.ytxd.common.util.R;
import com.ytxd.model.MySqlData;
import com.ytxd.request.TowerVideoRequest;
import com.ytxd.service.CommonService;
import com.ytxd.service.Subhall.RiverAndLake.RiverAndLake_Service;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 河湖长效管护
 */
@RestController("RiverAndLake_Controller")
@RequestMapping("/RiverAndLake")
public class RiverAndLake_Controller {
    @Resource
    private RiverAndLake_Service service;
    @Resource
    private TowerVideoRequest towerVideoRequest;
    @Resource
    private CommonService commonService;

    /**
     * 查询视频分区信息
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("/GetRLVideoPartitionList")
    public R GetRLVideoPartitionList(HttpServletRequest request) throws Exception{
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        return R.ok(service.GetRLVideoPartitionList(obj));
    }

    /**
     * 视频站点统计信息
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("/GetVideoStatisticsInfo")
    public R GetVideoStatisticsInfo(HttpServletRequest request) throws Exception{
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        return R.ok().put("data",service.GetVideoStatisticsInfo(obj));
    }

    /**
     * 按时间查询值班人员信息
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("/GetRLArrangeList")
    public R GetRLArrangeList(HttpServletRequest request) throws Exception{
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        return R.ok(service.GetRLArrangeList(obj));
    }

    /**
     * 获取基础设施信息
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("/GetRLInfrastructureList")
    public R GetRLInfrastructureList(HttpServletRequest request) throws Exception{
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        return R.ok(service.GetRLInfrastructureList(obj));
    }

    /**
     * 获取基础设施统计信息
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("/GetRLInfrastructureStatistics")
    public R GetRLInfrastructureStatistics(HttpServletRequest request) throws Exception{
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        return R.ok(service.GetRLInfrastructureStatistics(obj));
    }

    /**
     * 获取视频流地址
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("/GetWarnVideoUrl")
    public R GetWarnVideoUrl(HttpServletRequest request) throws Exception{
        final String deviceId = request.getParameter("deviceId");
        final String warnId = request.getParameter("warnId");
        if(StringUtils.isBlank(deviceId) || StringUtils.isBlank(warnId)){
            return R.error("参数异常");
        }
        return R.ok().put("data", towerVideoRequest.getWarnVideoUrl(deviceId, warnId, 0));
    }
    /**
     * 查询高塔报警的统计预警信息
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("/GetStAlarmRStatistics")
    public R GetStAlarmRStatistics(HttpServletRequest request) throws Exception{
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        return R.ok(service.GetStAlarmRStatistics(obj));
    }

    /**
     * 查询两个小时内的报警信息
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("/GetStAlarmRList")
    public R GetStAlarmRList(HttpServletRequest request) throws Exception{
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        return R.ok(service.GetStAlarmRList(obj));
    }

    /**
     * 根据ID查询报警信息
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("/GetStAlarmRInfoByID")
    public R GetStAlarmRInfoByID(HttpServletRequest request) throws Exception{
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        return R.ok().put("data",service.GetStAlarmRInfoByID(obj));
    }

    /**
     * 处理报警信息
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("/UpdateStAlarmR")
    public R UpdateStAlarmR(HttpServletRequest request) throws Exception{
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        return R.ok().put("data",service.UpdateStAlarmR(obj));
    }

    /**
     * 查询组织架构信息
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("/GetRLStructureInfo")
    public R GetRLStructureInfo(HttpServletRequest request) throws Exception{
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        return R.ok(service.GetRLStructureInfo(obj));
    }

    /**
     * 获取河湖信息
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("/GetRLInformationList")
    public R GetRLInformationList(HttpServletRequest request) throws Exception{
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        return R.ok(service.GetRLInformationList(obj));
    }

    /**
     * 获取站点详细信息
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("/GetSiteIntroduceInfo")
    public R GetSiteIntroduceInfo(HttpServletRequest request) throws Exception{
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        return R.ok().put("data",service.GetSiteIntroduceInfo(obj));
    }

    /**
     * 芙蓉溪打卡-事件列表
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("/GetRLPatrolList")
    public R GetRLPatrolList(HttpServletRequest request) throws Exception{
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        return R.ok(service.GetRLPatrolList(obj));
    }

    /**
     * 获取无人机信息
     */
    @PostMapping("/GetDroneInfo")
    public R GetDroneInfo(HttpServletRequest request) throws Exception{
        Map<String, Object> map = new HashMap<>();
//        map.put("videoUrlList", Arrays.asList("http://111.39.105.92:8081/UpFile/MP4/DJ0312.mp4", "http://111.39.105.92:8081/UpFile/MP4/DJ0203.mp4"));
        // 1018 替换无人机视频地址
        // map.put("videoUrlList", Arrays.asList("https://111.39.105.92:8081/UpFile/MP4/1018-4.mp4", "https://111.39.105.92:8081/UpFile/MP4/1018-5.mp4"));
        map.put("videoUrlList", Arrays.asList("/UpFile/MP4/1018-4.mp4", "/UpFile/MP4/1018-5.mp4"));
        map.put("droneState", "待机");
        map.put("signal", "强");
        map.put("battery", "90");
        map.put("flightTime", "25");
        map.put("flightMileage", "55");
        map.put("powerExchangesCount", "7");
        map.put("aerodromeState", "设备空闲中");
        map.put("runTime", "1296");
        map.put("quantity", "1");
        // 风速
        String windVelocity = "0";
        String temperature = "0";
        String humidity = "0";
        MySqlData mySqlData = new MySqlData();
        mySqlData.setSql("select tem,rhu,wns from st_weather_r where stcd = 'W00' and to_days(time) = to_days(now()) order by ABS(TIMEDIFF(NOW(), time)) ASC LIMIT 1");
        final HashMap<String, Object> data = commonService.getMap(mySqlData);
        if(StringUtils.isNotBlank(DataUtils.getMapKeyValue(data, "wns"))){
            windVelocity = DataUtils.getMapKeyValue(data, "wns");
        }
        if(StringUtils.isNotBlank(DataUtils.getMapKeyValue(data, "tem"))){
            temperature = DataUtils.getMapKeyValue(data, "tem");
        }
        if(StringUtils.isNotBlank(DataUtils.getMapKeyValue(data, "rhu"))){
            humidity = DataUtils.getMapKeyValue(data, "rhu");
        }
        map.put("windVelocity", windVelocity);
        // 温度
        map.put("temperature", temperature);
        // 湿度
        map.put("humidity", humidity);
        return R.ok().put("data", map);
    }

    /**
     * 获取无人机视频
     */
    @PostMapping("/GetDroneVideo")
    public R GetDroneVideo(HttpServletRequest request) throws Exception{
        String starttime = request.getParameter("starttime");
        String endtime = request.getParameter("endtime");
        MySqlData mySqlData = new MySqlData();
        mySqlData.setSql("select TM,URL,IMAGE from st_drone_video where flag = '01' ");
        if(StringUtils.isNotBlank(starttime)){
            mySqlData.setSql(" and TM >= ");
            mySqlData.setSqlValue(starttime);
        }
        if(StringUtils.isNotBlank(endtime)){
            mySqlData.setSql(" and TM <= ");
            mySqlData.setSqlValue(endtime);
        }
        mySqlData.setSql("order by tm desc");
        if(StringUtils.isBlank(starttime) && StringUtils.isBlank(endtime)){
            mySqlData.setSql("limit 6");
        }
        List<HashMap<String, Object>> dataList = commonService.getDataList(mySqlData);
        return R.ok().put("data", dataList);
    }

    @RequestMapping("/getRlPersonnel")
    public R getRlPersonnel(HttpServletRequest request) {
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        return R.ok().put("data",service.selectRlPersonnel(obj));
    }
}
