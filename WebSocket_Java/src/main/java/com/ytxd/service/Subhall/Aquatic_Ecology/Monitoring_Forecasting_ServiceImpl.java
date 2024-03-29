package com.ytxd.service.Subhall.Aquatic_Ecology;

import cn.hutool.core.date.DateUtil;
import com.github.pagehelper.PageInfo;
import com.ytxd.common.DataUtils;
import com.ytxd.common.exception.RRException;
import com.ytxd.dao.Subhall.Aquatic_Ecology.Monitoring_Forecasting_Mapper;
import com.ytxd.dao.Subhall.Water_Environment_Mapper;
import com.ytxd.dao.meteorology.Meteorology_Mapper;
import com.ytxd.util.EnforcementUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * 水安全-监测预报
 */
@Service
public class Monitoring_Forecasting_ServiceImpl implements Monitoring_Forecasting_Service {
    @Resource
    private Monitoring_Forecasting_Mapper mapper;
    @Resource
    private Water_Environment_Mapper WE_Mapper;

    @Resource
    private Meteorology_Mapper meteorology_mapper;

    /**
     * 获取芙蓉溪的水位和水质等级
     * @param obj
     * @return
     * @throws Exception
     */
    @Override
    public List<Map<String, Object>> GetMHWaterSWSZInfo(Map<String, Object> obj) throws Exception {
        List<Map<String, Object>> data = mapper.Select_MH_Water_SW_SZ_Info(obj);
        return mapper.Select_MH_Water_SW_SZ_Info(obj);
    }

    /**
     * 获取芙蓉溪告警信息
     *
     * @param obj
     * @return
     * @throws Exception
     */
    @Override
    public List<Map<String, Object>> GetMHWaringInfo(Map<String, Object> obj) throws Exception {
        // 芙蓉溪水位
        List<Map<String, Object>> mhSw = mapper.Select_MH_Water_SW_SZ_Info(obj);
        // 芙蓉溪水质
        obj.put("stcd","D0550210011");
        List<Map<String, Object>> mhSz = mapper.Select_MH_Water_Quality_List(obj);
        // 水质指标参数
        List<Map<String, Object>> paramList = WE_Mapper.Select_CodeItem_List();
        List<Map<String, Object>> data = new ArrayList<>();
        if(mhSw != null && mhSw.size() > 0){
            mhSw.stream().forEach(item ->{
                String type = DataUtils.getMapKeyValue(item,"type");
                if("01".equals(type)){
                    double z = DataUtils.getMapDoubleValue(item,"value");
                    if(z < 13.5 && z > 0.0){
                        Map<String, Object> dataMap = new HashMap<>();
                        dataMap.put("title","芙蓉溪水位");
                        dataMap.put("title_msg","低于生态水位要求！请进行环境调度");
                        dataMap.put("type","01");
                        dataMap.put("waring_value",">=13.5");
                        dataMap.put("value",z);
                        data.add(dataMap);
                    }
                }
            });
        }
        if(mhSz != null && mhSz.size() > 0 && paramList != null && paramList.size() > 0){
            mhSz.stream().forEach(item ->{
                paramList.stream().forEach(map ->{
                    // 指标
                    String code = DataUtils.getMapKeyValue(map,"code");
                    // 范围 NH3N TP CODCR
                    String shortname = DataUtils.getMapKeyValue(map,"shortname");
                    if(StringUtils.isNotBlank(shortname) && ",NH3N,TP,CODCR,".contains(","+code+",")){
                        String value = DataUtils.getMapKeyValue(item,code);
                        String[] exprees = shortname.split(",");
                        boolean flag = false;
                        for(String expree:exprees){
                            if(StringUtils.isNotBlank(value)){
                                Object object = EnforcementUtils.executeString(value+expree,null,false);
                                if(!(object != null && Boolean.parseBoolean(object.toString()))){
                                    flag = true;
                                    break;
                                }
                            }
                        }
                        if(flag){
                            Map<String, Object> dataMap = new HashMap<>();
                            dataMap.put("title",DataUtils.getMapKeyValue(map,"description"));
                            dataMap.put("title_msg","低于Ⅲ类水要求！请进行环境调度");
                            dataMap.put("type","02");
                            dataMap.put("waring_value",shortname);
                            dataMap.put("value",value);
                            data.add(dataMap);
                        }
                    }

                });
            });
        }
        return data;
    }
    /**
     * 获取时序水质信息
     *
     * @param obj
     * @return
     * @throws Exception
     */
    @Override
    public PageInfo GetMHWaterQualityInfoList(Map<String, Object> obj) throws Exception {
        DataUtils.Padding(obj);
        if(StringUtils.isBlank(DataUtils.getMapKeyValue(obj,"tm")) &&
                StringUtils.isBlank(DataUtils.getMapKeyValue(obj,"starttime")) &&
                StringUtils.isBlank(DataUtils.getMapKeyValue(obj,"endtime"))){
            obj.put("tm", DateUtil.format(new Date(),"yyyy-MM-dd HH:mm:ss"));
        }
        List<Map<String,Object>> data = null;
        // 高光谱水质仪单独处理
        if("607YX013".equals(DataUtils.getMapKeyValue(obj, "stcd"))){
            if(Objects.equals("z",DataUtils.getMapKeyValue(obj,"type"))){
                data = mapper.Select_Quality_Z_List(obj);
            }else {
                data = mapper.Select_MH_Water_Quality_Info_List_Hyperspectra(obj);
            }
        } else if("3212060001".equals(DataUtils.getMapKeyValue(obj, "stcd"))){
            data = meteorology_mapper.Select_Cod_List(obj);
        }else{
            data = mapper.Select_MH_Water_Quality_Info_List(obj);
        }
        PageInfo pageInfo = new PageInfo(data);
        return pageInfo;
    }

    /**
     * 水环境活水预案 -> 下拉框
     *
     * @param obj
     * @return
     * @throws Exception
     */
    @Override
    public List<Map<String, Object>> GetStormPlanEnvironmentSelected(Map<String, Object> obj) throws Exception {
        List<Map<String,Object>> data = mapper.Select_Storm_Plan_Environment_Selected(obj);
        return data;
    }

    /**
     * 水环境活水预案 -> 预案信息
     *
     * @param obj
     * @return
     * @throws Exception
     */
    @Override
    public PageInfo GetStormPlanEnvironmentList(Map<String, Object> obj) throws Exception {
        DataUtils.Padding(obj);
        List<Map<String,Object>> data = mapper.Select_Storm_Plan_Environment_List(obj);
        PageInfo pageInfo = new PageInfo(data);
        return pageInfo;
    }

    /**
     * 水生态补水预案 -> 下拉框
     *
     * @param obj
     * @return
     * @throws Exception
     */
    @Override
    public List<Map<String, Object>> GetStormPlanEcologySelected(Map<String, Object> obj) throws Exception {
        List<Map<String,Object>> data = mapper.Select_Storm_Plan_Ecology_Selected(obj);
        return data;
    }

    /**
     * 水生态补水预案 -> 预案信息
     *
     * @param obj
     * @return
     * @throws Exception
     */
    @Override
    public PageInfo GetStormPlanEcologyList(Map<String, Object> obj) throws Exception {
        DataUtils.Padding(obj);
        List<Map<String,Object>> data = mapper.Select_Storm_Plan_Ecology_List(obj);
        PageInfo pageInfo = new PageInfo(data);
        return pageInfo;
    }

    /**
     * 获取水位站的时序数据
     *
     * @param obj
     * @return
     * @throws Exception
     */
    @Override
    public PageInfo GetStormNodeRList(Map<String, Object> obj) throws Exception {
        // 参数校验
        if(StringUtils.isBlank(DataUtils.getMapKeyValue(obj,"plan_id"))){
            throw new RRException("方案ID不能为空！");
        }
        if(StringUtils.isBlank(DataUtils.getMapKeyValue(obj,"stcd"))){
            throw new RRException("站吗不能为空！");
        }
        if(StringUtils.isBlank(DataUtils.getMapKeyValue(obj,"tm"))){
            obj.put("tm",DateUtil.format(new Date(),"yyyy-MM-dd HH:mm:ss"));
        }
        DataUtils.Padding(obj);
        List<Map<String,Object>> data = mapper.Select_Storm_Node_R_List(obj);
        data.stream().forEach(item ->{
            String future = DataUtils.getMapKeyValue(item,"future");
            if("01".equals(future)){
                item.put("future",true);
            }else {
                item.put("future",false);
            }
        });
        PageInfo pageInfo = new PageInfo(data);
        return pageInfo;
    }

    /**
     * 获取存在映射关系的站点信息
     *
     * @param obj
     * @return
     * @throws Exception
     */
    @Override
    public PageInfo GetStormNodeContrast(Map<String, Object> obj) throws Exception {
        obj.put("sttp","01,08");
        DataUtils.Padding(obj);
        List<Map<String,Object>> data = mapper.Select_Storm_Node_Contrast(obj);
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
            }else {
                item.put("warning",false);
            }
        });
        PageInfo pageInfo = new PageInfo(data);
        return pageInfo;
    }

    /**
     * 获取水生态信息
     *
     * @param obj
     * @return
     * @throws Exception
     */
    @Override
    public Map<String, Object> GetWaterEcologyInfo(Map<String, Object> obj) throws Exception {
        obj.put("type","01");
        List<Map<String,Object>> list = mapper.Select_MH_Water_SW_SZ_Info(obj);
        Map<String,Object> data = new HashMap<>();
        data.put("title","芙蓉溪水位");
        data.put("type","01");
        data.put("waring_value",">=13.0");
        data.put("tips","米");
        if(list != null && list.size() > 0){
            Map<String,Object> dataMap = list.get(0);
            Double z = DataUtils.getMapDoubleValue(dataMap,"value");
//            z = 12.999;
            data.put("value",z);
            /**
             * Judge whether the water level is lower than the ecological water level
             */
            if(z < 13.0 && z > 0){
                /**
                 * Match a water ecological plan information according to the current temperature
                 * Write alarm information
                 */
                data.put("warninginfo","当前水位低于生态水位，请进行水生态调度");
                /**
                 * The current temperature does not default to 20 degrees
                 */
                String temp = DataUtils.getMapKeyValue(obj,"temp");
                if(StringUtils.isBlank(temp)){
                    temp = "20";
                    obj.put("temp",20);
                }
                data.put("plan_id",mapper.Select_Default_Ecology(obj));
                String plan_name = "【"+DataUtils.getDate("yyyy-MM-dd")+"】&【"+z+"_4.5_"+temp+"】";
                data.put("plan_name",plan_name);
            }else {
                data.put("warning_info","暂无报警");
                data.put("plan_name","暂无预演推送");
            }
        }
        return data;
    }

    /**
     * 获取水环境信息
     *
     * @param obj
     * @return
     * @throws Exception
     */
    @Override
    public Map<String, Object> GetWaterEnvironmentInfo(Map<String, Object> obj) throws Exception {
        obj.put("type","02");
        List<Map<String,Object>> list = mapper.Select_MH_Water_SW_SZ_Info(obj);
        Map<String,Object> data = new HashMap<>();
        data.put("title","芙蓉溪水质");
        data.put("type","02");
        data.put("waring_value","<= Ⅲ类");
        data.put("tips","米");
        if(list != null && list.size()>0){
            Map<String,Object> dataMap = list.get(0);
            String w_quality = DataUtils.getMapKeyValue(dataMap,"value");
            data.put("value",w_quality);
            if(Objects.equals("Ⅱ类",w_quality) || Objects.equals("Ⅴ类",w_quality)){
                /**
                 * Obtain the default water environment plan information
                 */
                data.put("warninginfo","当前水质不达标，请进行水环境调度");
                Map<String,Object> defaultMap = mapper.Select_Default_Environment(obj);
                data.put("plan_id",DataUtils.getMapKeyValue(defaultMap,"plan_id"));
                String plan_name = "【"+DataUtils.getDate("yyyy-MM-dd")+"】&" +
                        "【"+DataUtils.getMapKeyValue(defaultMap,"plan_name")+"】";
                data.put("plan_name",plan_name);
            }else {
                data.put("warning_info","暂无报警");
                data.put("plan_name","暂无预演推送");
            }
        }
        return data;
    }
    /**
     *
     * @Desription TODO 水质信息
     * @param obj
     * @return com.github.pagehelper.PageInfo
     * @date 2023/12/13 14:38
     * @Auther TY
     */
    @Override
    public PageInfo GetMHWaterQualityInfoListByType(Map<String, Object> obj) throws Exception {
        DataUtils.Padding(obj);
        if(StringUtils.isBlank(DataUtils.getMapKeyValue(obj,"tm")) &&
                StringUtils.isBlank(DataUtils.getMapKeyValue(obj,"starttime")) &&
                StringUtils.isBlank(DataUtils.getMapKeyValue(obj,"endtime"))){
            obj.put("tm", DateUtil.format(new Date(),"yyyy-MM-dd HH:mm:ss"));
        }
        List<Map<String,Object>> data = null;
        // 高光谱水质仪单独处理
        String querytype = DataUtils.getMapKeyValue(obj,"querytype");
        switch (querytype){
            case "time":
                data = mapper.Select_MH_Water_Quality_Info_ListByTime(obj);
                break;
            case "hour":
                data = mapper.Select_MH_Water_Quality_Info_ListByHour(obj);
                break;
            case "day":
                data = mapper.Select_MH_Water_Quality_Info_ListByDay(obj);
                break;
            case "month":
                data = mapper.Select_MH_Water_Quality_Info_ListByMonth(obj);
                break;
        }
        PageInfo pageInfo = new PageInfo(data);
        return pageInfo;
    }


}
