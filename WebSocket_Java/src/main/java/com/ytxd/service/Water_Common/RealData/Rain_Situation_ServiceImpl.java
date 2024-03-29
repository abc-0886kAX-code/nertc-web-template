package com.ytxd.service.Water_Common.RealData;

import cn.hutool.core.date.DateUtil;
import com.fasterxml.jackson.datatype.jdk8.OptionalDoubleSerializer;
import com.github.pagehelper.PageInfo;
import com.ytxd.common.DataUtils;
import com.ytxd.dao.Water_Common.RealData.Rain_Situation_Mapper;
import com.ytxd.util.DateUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 雨情相关
 * 站类参数未标准化
 */
@Service
public class Rain_Situation_ServiceImpl implements Rain_Situation_Service {
    @Resource
    private Rain_Situation_Mapper mapper;
    /**
     * 初始化查询条件
     * @param obj
     */
    private void InitObj(Map<String, Object> obj){
        /**
         * 只查询雨量站的数据
         * 04 雨量站
         */
        String sttp = DataUtils.getMapKeyValue(obj,"sttp");
        if(StringUtils.isBlank(sttp)){
            obj.put("sttp","PP,ZQ");
        }
    }
    /**
     * 取所有站点或者某些站点某一段时间内的累计雨量
     *
     * @param obj
     * @return
     */
    @Override
    public PageInfo GetRainCumulativeList(Map<String, Object> obj) throws ParseException {
        DataUtils.Padding(obj);
        InitObj(obj);
//        obj.put("starttime","2023-12-20 00:00:00");
        if(StringUtils.isBlank(DataUtils.getMapKeyValue(obj,"starttime"))){
            obj.put("starttime",DataUtils.getRainStartTime());
        }
        if(StringUtils.isBlank(DataUtils.getMapKeyValue(obj,"endtime"))){
            obj.put("endtime", DataUtils.getRainEndTime());
        }
        if(StringUtils.isNotBlank(DataUtils.getMapKeyValue(obj,"tm"))){
            obj.put("starttime",DataUtils.getMapKeyValue(obj,"tm") + " 08:00:00");
            obj.put("endtime",AddDate(DataUtils.getMapKeyValue(obj,"tm") + " 08:00:00",1,5));
        }
        List<Map<String,Object>> list = mapper.Select_Rain_Cumulative_List(obj);
        /**
         * 判断是否需要分类展示
         */
//        String isclassify = DataUtils.getMapKeyValue(obj,"isclassify");
//        List<Map<String,Object>> data = isClassify(list,isclassify);
        list.stream().forEach(item ->{
            double lgtd = DataUtils.getMapDoubleValue(item,"lgtd");
            double lttd = DataUtils.getMapDoubleValue(item,"lttd");
            item.put("position",new double[]{lgtd,lttd});
        });
        PageInfo pageInfo = new PageInfo(list);
        return pageInfo;
    }

    /**
     * 根据站点获取某一段时间内实时降雨数据
     *
     * @param obj
     * @return
     */
    @Override
    public PageInfo GetRealRainList(Map<String, Object> obj) {
        DataUtils.Padding(obj);
        InitObj(obj);
        List<Map<String,Object>> list = mapper.Select_Real_Rain_List(obj);
        /**
         * 判断是否需要分类展示
         */
        String isclassify = DataUtils.getMapKeyValue(obj,"isclassify");
        List<Map<String,Object>> data = isClassify(list,isclassify);
        PageInfo pageInfo = new PageInfo(data);
        return pageInfo;
    }

    /**
     * 根据站点获取某一段时间内实时降雨数据以小时进行展示
     *
     * @param obj
     * @return
     */
    @Override
    public PageInfo GetRealRainListByHour(Map<String, Object> obj) {
        DataUtils.Padding(obj);
        InitObj(obj);
        List<Map<String,Object>> list = mapper.Select_Real_Rain_List_Hour(obj);
        /**
         * 判断是否需要分类展示
         */
        String isclassify = DataUtils.getMapKeyValue(obj,"isclassify");
        List<Map<String,Object>> data = isClassify(list,isclassify);
        PageInfo pageInfo = new PageInfo(data);
        return pageInfo;
    }

    /**
     * 根据站点获取某一段时间内实时降雨数据按天进行展示
     *
     * @param obj
     * @return
     */
    @Override
    public PageInfo GetRealRainListByDay(Map<String, Object> obj) {
        DataUtils.Padding(obj);
        InitObj(obj);
        List<Map<String,Object>> list = mapper.Select_Real_Rain_List_Day(obj);
        /**
         * 判断是否需要分类展示
         */
        String isclassify = DataUtils.getMapKeyValue(obj,"isclassify");
        List<Map<String,Object>> data = isClassify(list,isclassify);
        PageInfo pageInfo = new PageInfo(data);
        return pageInfo;
    }
    /**
     * 处理数据
     * @param list
     * @param isClassify
     * @return
     */
    private List<Map<String,Object>> isClassify(List<Map<String,Object>> list,String isClassify){
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
                    dataMap.remove("p");
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
                        Object p = item.get("p");
                        item.clear();
                        item.put("tm",tm);
                        item.put("p",p);
                    });
                    if(!(rainData.size() == 1 && rainData.get(0).get("tm") == null)){
                        dataMap.put("source",accumulationP(rainData));
                    }
                    data.add(dataMap);
                }
            }
        }else {
            data = accumulationP(list);
        }
        data.stream().forEach(item ->{
            double lgtd = DataUtils.getMapDoubleValue(item,"lgtd");
            double lttd = DataUtils.getMapDoubleValue(item,"lttd");
            item.put("position",new double[]{lgtd,lttd});
        });
        return  data;
    }

    /**
     * 对雨量进行累加
     * @param lis
     */
    public List<Map<String,Object>> accumulationP(List<Map<String,Object>> lis){
        /**
         * 先对数据按时间进行排序
         */
        List<Map<String,Object>> rainList = lis.stream().sorted(
                Comparator.comparing(item ->{
                    String tm = DataUtils.getMapKeyValue((Map<String, Object>) item,"tm");
                    return tm;
                })).collect(Collectors.toList());
        Map<String,Double> sumMap = new HashMap<>();
        sumMap.put("sum_p",0.0);
        rainList.stream().forEach(item ->{
            double p = DataUtils.getMapDoubleValue(item,"p");
            double sum_p =  sumMap.get("sum_p") +p;
            /**
             * 保留两位小数
             */
            DecimalFormat format = new DecimalFormat("#.00");
            String str = format.format(sum_p);
            sum_p = Double.parseDouble(str);
            item.put("sum_p",sum_p);
            sumMap.put("sum_p",sum_p);
        });
        return rainList;
    }
    /**
     * 获取实时雨情的下拉选择框
     *
     * @param obj
     * @return
     */
    @Override
    public List<Map<String, Object>> GetSelectionItemList(Map<String, Object> obj) throws Exception{
        List<Map<String,Object>> data = mapper.Select_Selection_Item_List(obj);
        data.stream().forEach(item -> {
            /**
             * 需要增加的值
             */
            Integer value = DataUtils.getMapIntegerValue(item,"max");
            /**
             * 增加类型
             */
            Integer type = DataUtils.getMapIntegerValue(item,"min");
            String code = DataUtils.getMapKeyValue(item,"code");
            /**
             * 格式
             */
            String shortname = DataUtils.getMapKeyValue(item,"shortname");
            if(value > 0){
                if(Objects.equals("01",code)){
                   Date nowTime = new Date();
                   if(nowTime.getHours() < 8){
                       item.put("starttime",AddDate(shortname,-1,type));
                       item.put("endtime",AddDate(shortname,0,type));
                   }else {
                       item.put("starttime",DataUtils.getDate(shortname));
                       item.put("endtime",AddDate(shortname,value,type));
                   }
                }else {
                    item.put("starttime",DataUtils.getDate(shortname));
                    item.put("endtime",AddDate(shortname,value,type));
                }
            }else {
                item.put("endtime",DataUtils.getDate(shortname));
                item.put("starttime",AddDate(shortname,value,type));
            }
        });
        return data;
    }
    private  String AddDate(@NotBlank String str, @NotBlank Integer value, @NotBlank Integer type){
        try {
            /**
             * 设置日期格式
             */
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            /**
             * 获取时间
             */
            Date date = format.parse(DataUtils.getDate(str));
            /**
             * 时间格式化
             */
            Calendar calendar = Calendar.getInstance();
            /**
             * 计算时间
             * 年 Calendar.YEAR 1
             * 月 Calendar.MONTH 2
             * 日 Calendar.DATE 5
             * 时 Calendar.HOUR 10
             * 分 Calendar.MINUTE 12
             * 秒 Calendar.SECOND 13
             */
            calendar.setTime(date);
            calendar.add(type, value);
            return format.format(calendar.getTime());
        }catch (Exception e){
            e.printStackTrace();
        }
        return DataUtils.getDate(str);
    }

    /**
     * 获取预警的雨量数据
     *
     * @param obj
     * @return
     * @throws Exception
     */
    @Override
    public List<Map<String, Object>> GetEarlyWarningList(Map<String, Object> obj) throws Exception {
        obj.put("stcd","3212000007");
        if(StringUtils.isBlank(DataUtils.getMapKeyValue(obj,"tm"))){
            obj.put("tm",DateUtil.format(new Date(),"yyyy-MM-dd HH:mm:ss"));
        }
        List<Map<String,Object>> list = mapper.Select_Early_Warning_List(obj);
        List<Map<String,Object>> data = new ArrayList<>();
        for(Integer i = 0; i< list.size() ;i++){
            List<Map<String,Object>> sumList = list.subList(i,(i+1) * 24 > list.size()?list.size(): (i+1) * 24);
            OptionalDouble sumP = sumList.stream().mapToDouble(map -> DataUtils.getMapDoubleValue(map,"p")).reduce(Double::sum);
            Map<String,Object> map = new HashMap<>();
            Map<String,Object> warningMap = new HashMap<>();
            map.put("tm",list.get(i).get("tm"));
            map.put("p",sumP.getAsDouble());
            warningMap.put("stcd",DataUtils.getMapDoubleValue(map,"stcd"));
            warningMap.put("warningtype","05");
            if(sumP.getAsDouble() >= 50.00 && sumP.getAsDouble() <= 99.9){
                warningMap.put("warninginfo","暴雨预警");
                map.put("title","暴雨预警");
                data.add(map);
                mapper.Insert_WaterLevel_Warning(warningMap);
            }else if(sumP.getAsDouble() >= 100 && sumP.getAsDouble() <= 249.9){
                warningMap.put("warninginfo","大暴雨预警");
                map.put("title","大暴雨预警");
                data.add(map);
                mapper.Insert_WaterLevel_Warning(warningMap);
            } else if(sumP.getAsDouble() >= 250){
                warningMap.put("warninginfo","特大暴雨预警");
                map.put("title","特大暴雨预警");
                data.add(map);
                mapper.Insert_WaterLevel_Warning(warningMap);
            }


        }
        return data;
    }

    /**
     * 获取前后3天的雨情信息
     * @param obj
     * @return
     */
    @Override
    public List<Map<String, Object>> GetRainLevelInfoList(Map<String, Object> obj) {
        if(StringUtils.isBlank(DataUtils.getMapKeyValue(obj,"tm"))){
            obj.put("tm",DateUtil.format(new Date(),"yyyy-MM-dd HH:mm:ss"));
        }
//        obj.put("tm","2023-03-01 23:50:02");
        return mapper.Select_Rain_Level_Info_List(obj);
    }

    /**
     * 通过时间查询8-8点的累计降雨量
     *
     * @param obj
     * @return
     */
    @Override
    public List<Map<String, Object>> GetRainDayStatistics(Map<String, Object> obj) {
        return mapper.Select_Rain_Day_Statistics(obj);
    }
    /**
     *
     * @Desription TODO 获取年度降雨量
     * @param obj
     * @return com.github.pagehelper.PageInfo
     * @date 2024/1/29 11:31
     * @Auther TY
     */
    @Override
    public PageInfo GetRealRainListByYear(Map<String, Object> obj) {
        DataUtils.Padding(obj);
        InitObj(obj);
        String year = DataUtils.getMapKeyValue(obj,"year");
        if(StringUtils.isBlank(year)){
            year  = DataUtils.getDate("yyyy");
        }
        obj.put("starttime",year+"-01-01 00:00:00");
        obj.put("endtime",year+"-12-31 23:59:59");
        List<Map<String,Object>> list = mapper.Select_Real_Rain_ListByYear(obj);
        PageInfo pageInfo = new PageInfo(list);
        return pageInfo;
    }
    /**
     *
     * @Desription TODO 雨情，代表站点分析
     * @param obj
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     * @date 2024/1/29 15:14
     * @Auther TY
     */
    @Override
    public List<Map<String, Object>> GetRealRainListByRepresentative(Map<String, Object> obj) {
        /**
         * 基础参数
         */
        InitObj(obj);

        if(StringUtils.isBlank(DataUtils.getMapKeyValue(obj,"starttime"))){
            obj.put("starttime",DataUtils.getRainStartTime());
        }
        if(StringUtils.isBlank(DataUtils.getMapKeyValue(obj,"endtime"))){
            obj.put("endtime",DataUtils.getRainEndTime());
        }
        /**
         * 写入代表站
         */
        obj.put("representative","01");
        List<Map<String,Object>> data = mapper.Select_RealRainListByRepresentative(obj);
        return data;
    }
}


