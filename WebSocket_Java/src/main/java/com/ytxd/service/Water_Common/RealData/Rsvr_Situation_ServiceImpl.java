package com.ytxd.service.Water_Common.RealData;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.ytxd.common.DataUtils;
import com.ytxd.dao.Water_Common.RealData.Rsvr_Situation_Mapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Classname Rsvr_Situation_ServiceImpl
 * @Author TY
 * @Date 2024/2/4 16:07
 * @Description TODO 水库数据
 */
@Service
public class Rsvr_Situation_ServiceImpl implements Rsvr_Situation_Service{
    @Resource
    private Rsvr_Situation_Mapper mapper;
    private void InitObj(Map<String, Object> obj){
        /**
         * 只查询雨量站的数据
         * 04 雨量站
         */
        obj.put("sttp","RR");
    }
    /**
     *
     * @Desription TODO 获取站点的最新的一条数据
     * @param obj
     * @return com.github.pagehelper.PageInfo
     * @date 2024/2/4 16:17
     * @Auther TY
     */
    @Override
    public PageInfo getRsvrLevelList(Map<String, Object> obj) throws Exception {
        DataUtils.Padding(obj);
        InitObj(obj);
//        obj.put("starttime","2023-12-20 00:00:00");
        if(StringUtils.isBlank(DataUtils.getMapKeyValue(obj,"starttime"))){
            obj.put("starttime",DataUtils.getStartTime());
        }
        if(StringUtils.isBlank(DataUtils.getMapKeyValue(obj,"endtime"))){
            obj.put("endtime", DataUtils.getEndTime());
        }
        List<Map<String,Object>> data = mapper.Select_Rsvr_Level_List(obj);
        data.stream().forEach(item ->{
            double lgtd = DataUtils.getMapDoubleValue(item,"lgtd");
            double lttd = DataUtils.getMapDoubleValue(item,"lttd");
            item.put("position",new double[]{lgtd,lttd});
        });
        PageInfo pageInfo = new PageInfo(data);
        return pageInfo;
    }
    /**
     *
     * @Desription TODO 根据站点编码获取时序数据
     * @param obj
     * @return com.github.pagehelper.PageInfo
     * @date 2024/2/4 16:17
     * @Auther TY
     */
    @Override
    public PageInfo getRealRsvrLevelList(Map<String, Object> obj) throws Exception {
        DataUtils.Padding(obj);
        InitObj(obj);
        if(StringUtils.isBlank(DataUtils.getMapKeyValue(obj,"starttime"))){
            obj.put("starttime",DataUtils.getStartTime());
        }
        if(StringUtils.isBlank(DataUtils.getMapKeyValue(obj,"endtime"))){
            obj.put("endtime", DataUtils.getEndTime());
        }
        List<Map<String,Object>> data = mapper.Select_Real_Rsvr_Level_List(obj);
        return new PageInfo(data);
    }
    /**
     *
     * @Desription TODO 根据站点编码获取时序数据 (小时展示)
     * @param obj
     * @return com.github.pagehelper.PageInfo
     * @date 2024/2/4 16:18
     * @Auther TY
     */
    @Override
    public PageInfo getRealRsvrLevelListHour(Map<String, Object> obj) throws Exception {
        DataUtils.Padding(obj);
        InitObj(obj);
        if(StringUtils.isBlank(DataUtils.getMapKeyValue(obj,"starttime"))){
            obj.put("starttime",DataUtils.getStartTime());
        }
        if(StringUtils.isBlank(DataUtils.getMapKeyValue(obj,"endtime"))){
            obj.put("endtime", DataUtils.getEndTime());
        }
        List<Map<String,Object>> data = mapper.Select_Real_Rsvr_Level_List_Hour(obj);
        return new PageInfo(data);
    }
    /**
     *
     * @Desription TODO 根据站点编码获取时序数据(天展示）
     * @param obj
     * @return com.github.pagehelper.PageInfo
     * @date 2024/2/4 16:18
     * @Auther TY
     */
    @Override
    public PageInfo getRealRsvrLevelListDay(Map<String, Object> obj) throws Exception {
        DataUtils.Padding(obj);
        InitObj(obj);
        if(StringUtils.isBlank(DataUtils.getMapKeyValue(obj,"starttime"))){
            obj.put("starttime",DataUtils.getStartTime());
        }
        if(StringUtils.isBlank(DataUtils.getMapKeyValue(obj,"endtime"))){
            obj.put("endtime", DataUtils.getEndTime());
        }
        List<Map<String,Object>> data = mapper.Select_Real_Rsvr_Level_List_Day(obj);
        return new PageInfo(data);
    }

    @Override
    public Map<String, Object> selectRsvrStation(Map<String, Object> map) {
        Map<String, Object> val = mapper.selectRsvrStation(map);
        int total_rsvr = Integer.parseInt(val.getOrDefault("total_rsvr", 0).toString());
        int big_mid_rsvr = Integer.parseInt(val.getOrDefault("big_mid_rsvr", 0).toString());
        val.put("sm_rsvr", total_rsvr - big_mid_rsvr);
        val.put("exceeded_num" , mapper.selectFloodLevelExceededNum(map).getOrDefault("num" , 0));
        return val;
    }

    @Override
    public Map<String, Object> selectFloodLevelExceededNum(Map<String, Object> map) {
        return mapper.selectFloodLevelExceededNum(map);
    }

    @Override
    public List<Map<String, Object>> selectFloodLevelExceeded(Map<String, Object> map) {
        return mapper.selectFloodLevelExceeded(map);
    }
}
