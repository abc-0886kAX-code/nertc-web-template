package com.ytxd.service.Water_Common.RealData;

import com.github.pagehelper.PageInfo;
import com.ytxd.common.DataUtils;
import com.ytxd.dao.Water_Common.RealData.Water_Quality_Mapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 水质信息
 */
@Service
public class Water_Quality_ServiceImpl implements Water_Quality_Service {
    @Resource
    private Water_Quality_Mapper mapper;

    /**
     * 默认参数
     * @param obj
     */
    private void InitObj(Map<String, Object> obj){
        /**
         * 只查询水质站的数据
         * 03 水质站
         */
        obj.put("sttp","SZ");
    }
    /**
     * 取最新的水质数据 取最新的一条
     *
     * @param obj
     * @return
     */
    @Override
    public PageInfo GetWaterQualityList(Map<String, Object> obj) throws Exception {
        DataUtils.Padding(obj);
        InitObj(obj);
        List<Map<String,Object>> list = mapper.Select_Water_Quality_List(obj);
        /**
         * 判断是否需要分类展示
         */
        String isclassify = DataUtils.getMapKeyValue(obj,"isclassify");
        List<Map<String,Object>> data = isClassify(list,isclassify);
        PageInfo pageInfo = new PageInfo(data);
        return pageInfo;
    }

    /**
     * 取实时的水质数据
     *
     * @param obj
     * @return
     */
    @Override
    public PageInfo GetRealWaterQualityList(Map<String, Object> obj) throws Exception {
        DataUtils.Padding(obj);
        InitObj(obj);
        List<Map<String,Object>> list = mapper.Select_Real_Water_Quality_List(obj);
        /**
         * 判断是否需要分类展示
         */
        String isclassify = DataUtils.getMapKeyValue(obj,"isclassify");
        List<Map<String,Object>> data = isClassify(list,isclassify);
        PageInfo pageInfo = new PageInfo(data);
        return pageInfo;
    }

    /**
     * 取某时间段内的月均值统计
     *
     * @param obj
     * @return
     */
    @Override
    public PageInfo GetRealWaterQualityListByMonth(Map<String, Object> obj) throws Exception {
        DataUtils.Padding(obj);
        InitObj(obj);
        List<Map<String,Object>> list = mapper.Select_Real_Water_Quality_List_Month(obj);
        /**
         * 判断是否需要分类展示
         */
        String isclassify = DataUtils.getMapKeyValue(obj,"isclassify");
        List<Map<String,Object>> data = isClassify(list,isclassify);
        PageInfo pageInfo = new PageInfo(data);
        return pageInfo;
    }

    /**
     * 是否分类
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
                     * 移除水质信息
                     */
                    dataMap.remove("tm");
                    dataMap.remove("codcr");
                    dataMap.remove("nh3n");
                    dataMap.remove("tp");
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
                        Object codcr = item.get("codcr");
                        Object nh3n = item.get("nh3n");
                        Object tp = item.get("tp");
                        item.clear();
                        item.put("tm",tm);
                        item.put("codcr",codcr);
                        item.put("nh3n",nh3n);
                        item.put("tp",tp);
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
        data.stream().forEach(item ->{
            double lgtd = DataUtils.getMapDoubleValue(item,"lgtd");
            double lttd = DataUtils.getMapDoubleValue(item,"lttd");
            item.put("position",new double[]{lgtd,lttd});
        });
        return  data;
    }


}
