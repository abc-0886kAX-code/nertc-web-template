package com.ytxd.service.API.Lake_Healthy;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.ytxd.common.DataUtils;
import com.ytxd.dao.API.Lake_Healthy.Lake_Healthy_Mapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * 幸福河湖
 */
@Service
public class Lake_Healthy_ServiceImpl implements Lake_Healthy_Service {
    @Resource
    private Lake_Healthy_Mapper mapper;

    private static Map<String,Map<String,Object>> Water_Resource = new HashMap<>();
    {
        Map<String,Object> WaterMap = new HashMap<>();
        WaterMap.put("label","绵阳多年平均降水量");
        WaterMap.put("tips","mm");
        Water_Resource.put("avg_p",WaterMap);
        WaterMap = new HashMap<>();
        WaterMap.put("label","芙蓉溪流域水面率");
        WaterMap.put("tips","%");
        Water_Resource.put("water_ratio",WaterMap);
        WaterMap = new HashMap<>();
        WaterMap.put("label","绵阳多年平均水资源总量");
        WaterMap.put("tips","亿立方米");
        Water_Resource.put("water_sum",WaterMap);
    }
    /**
     * 查询幸福河湖信息
     *
     * @param obj
     * @return
     * @throws Exception
     */
    @Override
    public PageInfo GetLakeHealthyList(Map<String, Object> obj) throws Exception {
        DataUtils.Padding(obj);
        List<Map<String,Object>> data = mapper.Select_Lake_Healthy_List(obj);
        PageInfo pageInfo = new PageInfo(data);
        return pageInfo;
    }

    /**
     * 人数统计
     *
     * @param obj
     * @return
     * @throws Exception
     */
    @Override
    public List<Map<String, Object>> GetPeopleStatistics(Map<String, Object> obj) throws Exception {
        List<Map<String,Object>> data = mapper.Select_People_Statistics(obj);
        return data;
    }

    /**
     * 水资源统计
     *
     * @param obj
     * @return
     * @throws Exception
     */
    @Override
    public List<Map<String, Object>> GetWaterResourceList(Map<String, Object> obj) throws Exception {
        Map<String,Object> map = mapper.Select_Water_Resource_List(obj);
        List<Map<String,Object>> data = new ArrayList<>();
        if(map != null){
            for(Object key:Water_Resource.keySet()){
                Map<String,Object> dataMap = Water_Resource.get(key);
                dataMap.put("lable_value",map.get(key));
                data.add(dataMap);
            }
        }
        return data;
    }

    /**
     * 幸福河湖二维码
     *
     * @param obj
     * @return
     * @throws Exception
     */
    @Override
    public PageInfo GetLakeBlissList(Map<String, Object> obj) throws Exception {
        DataUtils.Padding(obj);
        List<Map<String,Object>> data = mapper.Select_Lake_Bliss_List(obj);
        PageInfo pageInfo = new PageInfo(data);
        return pageInfo;
    }

    /**
     * 幸福河湖卫星遥感
     * @param obj
     * @return
     * @throws Exception
     */
    @Override
    public PageInfo GetRemoteSensingList(Map<String, Object> obj) throws Exception {
        String flag = DataUtils.getMapKeyValue(obj,"flag");
        if(StringUtils.isBlank(flag)){
            obj.put("flag","01");
        }
        String starttime = DataUtils.getMapKeyValue(obj,"starttime");
        if(StringUtils.isBlank(starttime)){
            obj.put("starttime",DataUtils.AddDate(DataUtils.getDate("yyyy-MM-dd HH:mm:ss"),-1, Calendar.YEAR));
        }
        String endtime = DataUtils.getMapKeyValue(obj,"endtime");
        if(StringUtils.isBlank(endtime)){
            obj.put("endtime",DataUtils.getDate("yyyy-MM-dd HH:mm:ss"));
        }
        DataUtils.Padding(obj);
        List<Map<String,Object>> data = mapper.Select_RemoteSensing_List(obj);
        PageInfo pageInfo = new PageInfo(data);
        return pageInfo;
    }
    /**
     *
     * @Desription TODO 幸福河湖卫星遥感(按月份查询)
     * @param obj
     * @return java.util.Map<java.lang.String,java.lang.Object>
     * @date 2023/9/21 10:39
     * @Auther TY
     */
    @Override
    public Map<String, Object> getRemoteSensingListByMonth(Map<String, Object> obj) throws Exception {
        String flag = DataUtils.getMapKeyValue(obj,"flag");
        if(StringUtils.isBlank(flag)){
            obj.put("flag","01");
        }
        String starttime = DataUtils.getMapKeyValue(obj,"starttime");
        if(StringUtils.isBlank(starttime)){
            obj.put("starttime",DataUtils.AddDate(DataUtils.getDate("yyyy-MM-dd HH:mm:ss"),-1, Calendar.YEAR));
        }
        String endtime = DataUtils.getMapKeyValue(obj,"endtime");
        if(StringUtils.isBlank(endtime)){
            obj.put("endtime",DataUtils.getDate("yyyy-MM-dd HH:mm:ss"));
        }
        List<Map<String,Object>> data = mapper.Select_RemoteSensing_ListByMonth(obj);
        Map<String,Object> result = new HashMap<>();
        result.put("one",data);
        result.put("two",getSubImageList(data,2));
        result.put("three",getSubImageList(data,4));
        result.put("four",getSubImageList(data,6));
        return result;
    }
    /**
     *
     * @Desription TODO 剪切List 形成新的数组
     * @param data
     * @param sum
     * @return java.util.List<java.util.List<java.util.Map<java.lang.String,java.lang.Object>>>
     * @date 2023/9/21 10:51
     * @Auther TY
     */
    private List<List<Map<String,Object>>> getSubImageList(List<Map<String,Object>> data,Integer sum){
        List<List<Map<String,Object>>> result = new LinkedList<>();
        if(data != null && !data.isEmpty() && data.size() > sum){
            Integer x = new Double(Math.ceil(new Double(new Double(data.size())/sum))).intValue();
            for(int i = 0 ;i < x ;i++){
                /**
                 * 剪切list
                 */
                List<Map<String,Object>> info = data.subList(i*sum,(i+1)* sum >= data.size()?data.size():(i+1)* sum);
                result.add(info);
            }
        }else {
            result.add(data);
        }
        return result;
    }

    /**获取每个类型最后一条数据
     * @param obj
     * @return
     * @throws Exception
     */
    @Override
    public PageInfo getLastRemoteSensingList(Map<String, Object> obj) throws Exception {
        String flag = DataUtils.getMapKeyValue(obj,"flag");
        if(StringUtils.isBlank(flag)){
            obj.put("flag","01");
        }
        String starttime = DataUtils.getMapKeyValue(obj,"starttime");
        if(StringUtils.isBlank(starttime)){
            obj.put("starttime",DataUtils.AddDate(DataUtils.getDate("yyyy-MM-dd HH:mm:ss"),-1, Calendar.YEAR));
        }
        String endtime = DataUtils.getMapKeyValue(obj,"endtime");
        if(StringUtils.isBlank(endtime)){
            obj.put("endtime",DataUtils.getDate("yyyy-MM-dd HH:mm:ss"));
        }
        DataUtils.Padding(obj);
        List<Map<String,Object>> data = mapper.Select_Last_RemoteSensing_List(obj);
        PageInfo pageInfo = new PageInfo(data);
        return pageInfo;
    }
}
