package com.ytxd.service.Water_Common.RealData;

import cn.hutool.core.date.DateUtil;
import com.github.pagehelper.PageInfo;
import com.ytxd.common.DataUtils;
import com.ytxd.common.exception.RRException;
import com.ytxd.dao.Water_Common.RealData.Water_Situation_Mapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 水清相关
 * 站码未标准化
 */
@Service
public class Water_Situation_ServiceImpl implements Water_Situation_Service {
    @Resource
    private Water_Situation_Mapper mapper;

    /**
     * 初始化查询条件
     * @param obj
     */
    private void InitObj(Map<String, Object> obj){
        /**
         * 只查询水位站、水文站的数据
         * 01 水位站
         * 08 水文站
         */
        String sttp = DataUtils.getMapKeyValue(obj,"sttp");
        if(StringUtils.isBlank(sttp)){
            obj.put("sttp","ZQ,ZZ,02");
        }
    }
    /**
     * 取最新的水情数据 取最新的一条
     *
     * @param obj
     * @return
     * @throws Exception
     */
    @Override
    public PageInfo GetWaterLevelList(Map<String, Object> obj) throws Exception {
        DataUtils.Padding(obj);
        InitObj(obj);
        List<Map<String,Object>> list = mapper.Select_Water_Level_List(obj);
        String isclassify = DataUtils.getMapKeyValue(obj,"isclassify");
        List<Map<String,Object>> data = isClassify(list,isclassify);
        PageInfo pageInfo = new PageInfo(data);
        return pageInfo;
    }

    /**
     * 取实时水情数据
     *
     * @param obj
     * @return
     * @throws Exception
     */
    @Override
    public PageInfo GetRealWaterLevelList(Map<String, Object> obj) throws Exception {
        DataUtils.Padding(obj);
        InitObj(obj);
        List<Map<String,Object>> list = mapper.Select_Real_Water_Level_List(obj);
        /**
         * 判断是否需要分类展示
         */
        String isclassify = DataUtils.getMapKeyValue(obj,"isclassify");
        List<Map<String,Object>> data = isClassify(list,isclassify);
        PageInfo pageInfo = new PageInfo(data);
        return pageInfo;
    }

    /**
     * 取实时水情数据 按小时展示
     *
     * @param obj
     * @return
     * @throws Exception
     */
    @Override
    public PageInfo GetRealWaterLevelListByHour(Map<String, Object> obj) throws Exception {
        DataUtils.Padding(obj);
        InitObj(obj);
        System.out.println(obj);
        System.out.println("-------------------------------------------------");
        List<Map<String,Object>> list = mapper.Select_Real_Water_Level_List_Hour(obj);
        /**
         * 判断是否需要分类展示
         */
        String isclassify = DataUtils.getMapKeyValue(obj,"isclassify");
        List<Map<String,Object>> data = isClassify(list,isclassify);
        PageInfo pageInfo = new PageInfo(data);
        return pageInfo;
    }

    /**
     * 取实时水情数据 按天展示
     *
     * @param obj
     * @return
     * @throws Exception
     */
    @Override
    public PageInfo GetRealWaterLevelListByDay(Map<String, Object> obj) throws Exception {
        DataUtils.Padding(obj);
        InitObj(obj);
        List<Map<String,Object>> list = mapper.Select_Real_Water_Level_List_Day(obj);
        /**
         * 判断是否需要分类展示
         */
        String isclassify = DataUtils.getMapKeyValue(obj,"isclassify");
        List<Map<String,Object>> data = isClassify(list,isclassify);
        PageInfo pageInfo = new PageInfo(data);
        return pageInfo;
    }

    /**
     * 获取当前时间前后3天的雨量信息
     *
     * @param obj
     * @return
     * @throws Exception
     */
    @Override
    public PageInfo GetWaterLevelInfoList(Map<String, Object> obj) throws Exception {
        InitObj(obj);
        if(StringUtils.isBlank(DataUtils.getMapKeyValue(obj,"stcd"))){
           throw new RRException("stcd不能为空！");
        }
        if(StringUtils.isBlank(DataUtils.getMapKeyValue(obj,"tm"))){
            obj.put("tm", DateUtil.format(new Date(),"yyyy-MM-dd HH:mm:ss"));
        }
        if(StringUtils.isBlank(DataUtils.getMapKeyValue(obj,"days"))){
            obj.put("days", 3);
        }
        DataUtils.Padding(obj);
        List<Map<String,Object>> data = mapper.Select_Water_Level_Info_List(obj);
        PageInfo pageInfo = new PageInfo(data);
        return pageInfo;
    }

    /**
     * 处理数据
     * @param list
     * @param isClassify
     * @return
     */
    private List<Map<String,Object>> isClassify(List<Map<String,Object>> list, String isClassify){
        List<Map<String,Object>> data = new ArrayList<>();
        if("01".equals(isClassify)){
            if(list != null && list.size() > 0){
                Map<Object,List<Map<String,Object>>> GroupByMap = list.stream().collect(Collectors.groupingBy(item -> item.get("stcd")));
                for(Object key:GroupByMap.keySet()){
                    Map<String,Object> dataMap = new HashMap<>();
                    /**
                     * 基础数据
                     */
                    dataMap.putAll(GroupByMap.get(key).get(0));
                    /**
                     * 移除时间和雨量
                     */
                    dataMap.remove("tm");
//                    dataMap.remove("z");
                    /**
                     * 用于展示列表的数据
                     * 先对时间正排
                     */
                    List<Map<String,Object>> rainData = GroupByMap.get(key).stream().sorted(
                            Comparator.comparing(item ->{
                                String tm = DataUtils.getMapKeyValue((Map<String, Object>) item,"tm");
                                return tm;
                            })).collect(Collectors.toList());
                    rainData.stream().forEach(item ->{
                        /**
                         * 只保留时间和雨量
                         */
                        Object tm = item.get("tm");
                        Object z = item.get("z");
                        item.clear();
                        item.put("tm",tm);
                        item.put("z",z);
                    });
                    if(!(rainData.size() == 1 && rainData.get(0).get("tm") == null)){
                        dataMap.put("source",rainData);
                    }
                    data.add(dataMap);
                }
            }
        }else {
            data = list;
        }
        /**
         * 经纬度转化
         */
        data.stream().forEach(item ->{
            double lgtd = DataUtils.getMapDoubleValue(item,"lgtd");
            double lttd = DataUtils.getMapDoubleValue(item,"lttd");
            item.put("position",new double[]{lgtd,lttd});
            /**
             * 判断是否超过警戒水位
             */
            double z = DataUtils.getMapDoubleValue(item,"z");
            double alertstage = DataUtils.getMapDoubleValue(item,"alertstage");
            if( alertstage > 0 && z >0 && alertstage <= z){
                item.put("warning",true);
            }else {
                item.put("warning",false);
            }
            String stcd = DataUtils.getMapKeyValue(item,"stcd");
            if(Objects.equals("3212000007_01",stcd) && z >= 14.5 ){
                item.put("z",14.50);
            }
        });
        return  data;
    }
    /**
     * 获取时间段内的小时库容水位信息
     *
     * @param obj
     * @return
     * @throws Exception
     */
    @Override
    public PageInfo GetSTRsvrRListByHour(Map<String, Object> obj) throws Exception {
        DataUtils.Padding(obj);
        List<Map<String,Object>> data = mapper.Select_ST_Rsvr_R_List_ByHour(obj);
        data.stream().forEach(item ->{
            double lgtd = DataUtils.getMapDoubleValue(item,"lgtd");
            double lttd = DataUtils.getMapDoubleValue(item,"lttd");
            item.put("position",new double[]{lgtd,lttd});
        });
        PageInfo pageInfo = new PageInfo(data);
        return pageInfo;
    }

    @Override
    public PageInfo getSTRsvrRLastData(Map<String, Object> obj) throws Exception {
        DataUtils.Padding(obj);
        List<Map<String,Object>> data = mapper.Select_ST_Rsvr_RLastData(obj);
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
     * @Desription TODO 查询同期水位信息
     * @param obj
     * @return com.github.pagehelper.PageInfo
     * @date 2024/2/23 14:15
     * @Auther TY
     */
    @Override
    public PageInfo GetTQRealWaterLevelListByHour(Map<String, Object> obj) throws Exception {
        DataUtils.Padding(obj);
        InitObj(obj);
        String tqyear = DataUtils.getMapKeyValue(obj,"tqyear");
        if(StringUtils.isBlank(tqyear)){
            throw new RRException("请选择需要查询的同期数据！");
        }
        String starttime = DataUtils.getMapKeyValue(obj,"starttime");
        if(StringUtils.isBlank(starttime)){
            throw new RRException("请选择开始时间！");
        }
        String endtime = DataUtils.getMapKeyValue(obj,"endtime");
        if(StringUtils.isBlank(endtime)){
            throw new RRException("请选择结束时间！");
        }
        String startyear = starttime.substring(0,4);
        String endyear = starttime.substring(0,4);
        if(!Objects.equals(startyear,endyear)){
            throw new RRException("开始和结束时间不在同一年！");
        }
        /**
         * 时间替换
         */
        String tqstarttime = starttime.replace(startyear,tqyear);
        String tqendtime = endtime.replace(endyear,tqyear);
        obj.put("tqstarttime",tqstarttime);
        obj.put("tqendtime",tqendtime);
        List<Map<String,Object>> data = mapper.Select_TQ_Real_Water_Level_List_Hour(obj);
        return new PageInfo(data);
    }

    @Override
    public List<Map<String, Object>> selectExceedLevee(Map<String, Object> map) {
        return mapper.selectExceedLevee(map);
    }

    @Override
    public List<Map<String, Object>> selectExceedGuaranteed(Map<String, Object> map) {
        return mapper.selectExceedGuaranteed(map);
    }

    @Override
    public List<Map<String, Object>> selectExceedAlertLevel(Map<String, Object> map) {
        return mapper.selectExceedAlertLevel(map);
    }

    @Override
    public List<Map<String, Object>> selectRiverStationInfo(Map<String, Object> map) {
        return mapper.selectRiverStationInfo(map);
    }

    @Override
    public Map<String, Object> selectExceedNum(Map<String, Object> map) {
        return mapper.selectExceedNum(map);
    }
}
