package com.ytxd.service.Water_Common.RealData;

import com.github.pagehelper.PageInfo;
import com.ytxd.common.DataUtils;
import com.ytxd.dao.Water_Common.RealData.Worker_Situation_Mapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 工情信息
 */
@Service
public class Worker_Situation_ServiceImpl implements Worker_Situation_Service{
    @Resource
    private Worker_Situation_Mapper mapper;
    /**
     * 初始化查询条件
     * @param obj
     */
    private void InitObj(Map<String, Object> obj){
        /**
         * 只查询闸门站的数据
         * 07 闸
         */
        obj.put("sttp","07");
    }
    /**
     * 取最新的工情数据 取最新的一条
     *
     * @param obj
     * @return
     */
    @Override
    public PageInfo GetWorkerList(Map<String, Object> obj)  throws Exception{
        DataUtils.Padding(obj);
        InitObj(obj);
        List<Map<String,Object>> list = mapper.Select_Worker_List(obj);
        /**
         * 判断是否需要分类展示
         */
        String isclassify = DataUtils.getMapKeyValue(obj,"isclassify");
        List<Map<String,Object>> data = isClassify(list,isclassify);
        PageInfo pageInfo = new PageInfo(data);
        return pageInfo;
    }

    /**
     * 取实时的工情数据
     *
     * @param obj
     * @return
     */
    @Override
    public PageInfo GetRealWorkerList(Map<String, Object> obj)  throws Exception{
        DataUtils.Padding(obj);
        InitObj(obj);
        List<Map<String,Object>> list = mapper.Select_Real_Worker_List(obj);
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
                    dataMap.remove("gtopnum");
                    dataMap.remove("gtq");
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
                        Object gtq = item.get("gtq");
                        Object q = item.get("q");
                        Object gtopnum = item.get("gtopnum");
                        Object total_gate = item.get("total_gate");
                        item.clear();
                        item.put("tm",tm);
                        item.put("gtq",gtq);
                        item.put("q",q);
                        item.put("gtopnum",gtopnum);
                        item.put("total",total_gate);
                    });
                    if(!(rainData.size() == 1 && rainData.get(0).get("tm") == null)){
                        dataMap.put("source",rainData);
                    }
                    data.add(dataMap);
                }
            }
        }else {
            data = list;
            data.stream().forEach(item ->{
                Map<String,Object> gate = new HashMap<>();
                gate.put("total",item.get("total_gate"));
                gate.put("open",item.get("gtopnum"));
                item.put("gate",gate);
            });
        }
        /**
         * 经纬度转化
         */
        data.stream().forEach(item ->{
            double lgtd = DataUtils.getMapDoubleValue(item,"lgtd");
            double lttd = DataUtils.getMapDoubleValue(item,"lttd");
            item.put("position",new double[]{lgtd,lttd});
        });
        return  data;
    }
}
