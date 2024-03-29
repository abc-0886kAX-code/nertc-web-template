package com.ytxd.service.Subhall;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.ytxd.common.DataUtils;
import com.ytxd.common.exception.RRException;
import com.ytxd.dao.Subhall.Water_Safety_Mapper;

import cn.hutool.core.date.DateUtil;

/**
 * 水安全
 */
@Service
public class Water_Safety_ServiceImpl implements Water_Safety_Service{
    @Resource
    private Water_Safety_Mapper mapper;
    /**
     * 获取前后3天的雨量水位数据
     *
     * @param obj
     * @return
     */
    @Override
    public PageInfo GetStormWaterLevelList(Map<String, Object> obj) throws Exception {
        /**
         * 参数判断
         */
        if(StringUtils.isBlank(DataUtils.getMapKeyValue(obj,"stcd"))){
            throw new RRException("stcd不能为空！");
        }
        if(StringUtils.isBlank(DataUtils.getMapKeyValue(obj,"plan_id"))){
            throw new RRException("方案id不能为空！");
        }
        if(StringUtils.isBlank(DataUtils.getMapKeyValue(obj,"tm"))){
            obj.put("tm",DataUtils.getDate(new Date(),"yyyy-MM-dd HH:00:00"));
        }
        DataUtils.Padding(obj);
        List<Map<String,Object>> data = mapper.Select_Storm_Water_Level_List(obj);
        data.stream().forEach(item ->{
            String future = DataUtils.getMapKeyValue(item,"future");
            if("01".equals(future)){
                item.put("future",true);
            }else {
                item.put("future",false);
            }
            double lgtd = DataUtils.getMapDoubleValue(item,"lgtd");
            double lttd = DataUtils.getMapDoubleValue(item,"lttd");
            item.put("position",new double[]{lgtd,lttd});
        });

        PageInfo pageInfo = new PageInfo(data);
        return pageInfo;
    }

    /**
     * 获取工情信息
     *
     * @param obj
     * @return
     * @throws Exception
     */
    @Override
    public PageInfo GetStormGateList(Map<String, Object> obj) throws Exception {
        /**
         * 参数判断
         */

        DataUtils.Padding(obj);
        List<Map<String,Object>> data = new ArrayList<>();
        if(StringUtils.isBlank(DataUtils.getMapKeyValue(obj,"plan_id"))){
            data = mapper.Select_Storm_Gate_List_NullPlan(obj);
        }else{
            data = mapper.Select_Storm_Gate_List(obj);
        }
        data.stream().forEach(item ->{
            double lgtd = DataUtils.getMapDoubleValue(item,"lgtd");
            double lttd = DataUtils.getMapDoubleValue(item,"lttd");
            String q = DataUtils.getMapKeyValue(item,"q");
            Double qs = 0d;
            if(StringUtils.isNotBlank(q) && !q.equals("-")){
                qs = new Double(q);
            }
            item.put("position",new double[]{lgtd,lttd});
            //String tm = DataUtils.getMapKeyValue(item,"tm");

            Map<String,Object> gate = new HashMap<>();
            gate.put("total",item.get("total_gate"));
            if(qs.intValue()>=1){
                gate.put("open",item.get("total_gate"));
            }else {
                gate.put("open",0);
            }
            item.put("gate",gate);
        });
        PageInfo pageInfo = new PageInfo(data);
        return pageInfo;
    }

    /**
     * 获取预报水位信息
     *
     * @param obj
     */
    @Override
    public List<Map<String, Object>> GetPredictionWLList(Map<String, Object> obj) throws Exception {
        /**
         * 默认
         * 3212000007_01 智慧杆水位站 -- 芙蓉溪水位
         * 40235960 胜天河左支1  -- 胜天河口水位
         * W00 -- 全市平均雨量
         */
        obj.put("mh_stcd","3212000007_01");
        obj.put("sth_stcd","40235960");
        obj.put("weather_stcd","W00");
        obj.put("sttp","01,08");
        obj.put("flag","01");
        obj.put("sign","01,02");
        if(StringUtils.isBlank(DataUtils.getMapKeyValue(obj,"tm"))){
            obj.put("tm", DateUtil.format(new Date(),"yyyy-MM-dd HH:mm:ss"));
//            obj.put("tm", "2023-07-07 15:00:00");
        }
        List<Map<String,Object>> data = mapper.Select_PredictionWL_List(obj);
        data.stream().forEach(item ->{
            double lgtd = DataUtils.getMapDoubleValue(item,"lgtd");
            double lttd = DataUtils.getMapDoubleValue(item,"lttd");
            item.put("position",new double[]{lgtd,lttd});
            /**
             * 判断是否超过警戒水位
             */
            double z = DataUtils.getMapDoubleValue(item,"z");
            double alertstage = DataUtils.getMapDoubleValue(item,"alertstage");
            if( alertstage > 0 && z >0 && alertstage < z){
                item.put("warning",true);
                String eigen = DataUtils.getMapKeyValue(item,"eigen");
                if(Objects.equals("00",eigen)){
                    item.put("eigenname","紧急");
                }
            }else {
                item.put("warning",false);
            }
            String stcd = DataUtils.getMapKeyValue(item,"stcd");
            if(Objects.equals("3212000007_01",stcd) && z >= 14.5 ){
                item.put("z",14.50);
            }
        });
        return data;
    }

    /**
     * 获取动态预警的发布预警信息和未发布预警信息
     *
     * @param obj
     * @return
     * @throws Exception
     */
    @Override
    public PageInfo GetWarningReleaseList(Map<String, Object> obj) throws Exception {
        if(StringUtils.isBlank(DataUtils.getMapKeyValue(obj,"tm"))){
            obj.put("tm",DataUtils.getDate(new Date(),"yyyy-MM-dd HH:mm:ss"));
        }
        /**
         * 默认
         * 3212000007_01 智慧杆水位站 -- 芙蓉溪水位
         * 40235960 胜天河左支1  -- 胜天河口水位
         * W00 -- 全市平均雨量
         */
        obj.put("mh_stcd","3212000007_01");
        obj.put("sth_stcd","40235960");
        obj.put("weather_stcd","W00");
        obj.put("sttp","01,08");
        DataUtils.Padding(obj);
        List<Map<String,Object>> data = mapper.Select_Warning_Release_List(obj);
        data.stream().forEach(item ->{
            double lgtd = DataUtils.getMapDoubleValue(item,"lgtd");
            double lttd = DataUtils.getMapDoubleValue(item,"lttd");
            item.put("position",new double[]{lgtd,lttd});
            /**
             * Judge whether the warning water level is exceeded
             */
            double z = DataUtils.getMapDoubleValue(item,"z");
            double pre_24_z = DataUtils.getMapDoubleValue(item,"pre_24_z");
            double alertstage = DataUtils.getMapDoubleValue(item,"alertstage");
            if( alertstage > 0 && pre_24_z >0 && alertstage < pre_24_z){
                /**
                 * The leader requires that the alarm is not allowed
                 */
                item.put("warning",true);
            }else {
                item.put("warning",false);
            }
            String already = DataUtils.getMapKeyValue(item,"already");
            if("01".equals(already)){
                item.put("already",true);
            }else {
                item.put("already",false);
            }
        });
        PageInfo pageInfo = new PageInfo(data);
        return pageInfo;
    }

    /**
     * 预警发布保存
     *
     * @param obj
     * @return
     * @throws Exception
     */
    @Override
    public Integer WaringReleaseSave(Map<String, Object> obj) throws Exception {
        String waringrelease = DataUtils.getMapKeyValue(obj,"waringrelease");
        if(StringUtils.isBlank(waringrelease)){
            throw new RRException("请选择需要发布的数据");
        }
        List<Map<String,Object>> dataMap = JSONObject.parseObject(waringrelease, ArrayList.class);
        dataMap.stream().forEach(item ->{
            String id = DataUtils.getMapKeyValue(item,"id");
            if(StringUtils.isBlank(id)){
                item.put("release_time",DateUtil.format(new Date(),"yyyy-MM-dd HH:mm:ss"));
                mapper.Insert_Waring_Release(item);
            }
        });
        return 1;
    }

    /**
     * 获取预报调度方案的下拉框列表
     *
     * @param obj
     * @return
     * @throws Exception
     */
    @Override
    public List<Map<String, Object>> GetSafetySelectedList(Map<String, Object> obj) throws Exception {
        return mapper.Select_Safety_Selected_List(obj);
    }

    /**
     * 获取默认的方案详细
     *
     * @param obj
     * @return
     * @throws Exception
     */
    @Override
    public Map<String, Object> GetDefaultPlanInfo(Map<String, Object> obj) throws Exception {
        if(StringUtils.isBlank(DataUtils.getMapKeyValue(obj,"tm"))){
            obj.put("tm",DataUtils.getDate(new Date(),"yyyy-MM-dd HH:mm:ss"));
        }
        /**
         * 默认
         * 3212000007_01 智慧杆水位站 -- 芙蓉溪水位
         * 40235960 胜天河左支1  -- 胜天河口水位
         * W00 -- 全市平均雨量
         */
        obj.put("mh_stcd","3212000007_01");
        obj.put("sth_stcd","40235960");
        obj.put("weather_stcd","W00");
        obj.put("sttp","01,08");
        return mapper.Select_Default_Plan_Info(obj);
    }

    /**
     * 获取指定时间最相近的方案，如果降雨量为空最不返回方案
     * @param obj
     * @return
     * @throws Exception
     */
    @Override
    public Map<String,Object> GetNearPlanInfo(Map<String, Object> obj) throws Exception {
        /**
         * 默认
         * 3212000007_01 智慧杆水位站 -- 芙蓉溪水位
         * 40235960 胜天河左支1  -- 胜天河口水位
         * W00 -- 全市平均雨量
         */
        if(StringUtils.isBlank(DataUtils.getMapKeyValue(obj,"tm"))){
            obj.put("tm",DataUtils.getDate(new Date(),"yyyy-MM-dd HH:mm:ss"));
        }
        obj.put("mh_stcd","3212000007_01");
        obj.put("sth_stcd","40235960");
        obj.put("weather_stcd","W00");
        obj.put("sttp","01,08");
        obj.put("period","1d");
        Map<String,Object> map =  mapper.Select_Near_Plan_Info(obj);
        return map;
    }
}
