package com.ytxd.service.Water_Rove;

import com.ytxd.common.DataUtils;
import com.ytxd.common.exception.RRException;
import com.ytxd.dao.Water_Rove.Water_Rove_Mapper;
import com.ytxd.service.API.Storm_Plan.Storm_Plan_Service;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.formula.functions.Now;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 漫游
 */
@Service
public class Water_Rove_ServiceImpl implements Water_Rove_Service{
    @Resource
    private Water_Rove_Mapper mapper;
    @Resource
    private Storm_Plan_Service stormPlanService;
    // 步长
    private final static Double step = 0.1;
    /**
     * 断面
     * 40236024	中兴湖
     * c9ea122ed134b03a105b327acead0ef3
     */
    private final static String  nodeid = "c9ea122ed134b03a105b327acead0ef3";

    /**
     * 获取漫游中所需要的信息
     *
     * @param obj
     * @return
     */
    @Override
    public Map<String, Object> GetStormPlanInfo(Map<String, Object> obj) {
        String plan_id = DataUtils.getMapKeyValue(obj,"plan_id");
        Map<String,Object> data = new HashMap<>();
        if(StringUtils.isBlank(plan_id)){
            data = mapper.Select_Storm_Plan_Info_NullPlan(obj);
            //throw new RRException("请选择方案！！" );
        }else{
            obj.put("nodeid",nodeid);
            obj.put("plan_id",stormPlanService.getPlanId(obj));
             data = mapper.Select_Storm_Plan_Info(obj);
        }
        /**
         * 漫游时需要的
         * 漫游时间： time = (maxz-minz)/step 向上取整
         * 最大水位: maxz
         * 最小水位: minz
         * 步长： step
         */
        Map<String,Object> roveMap = new HashMap<>();
        Double minz = DataUtils.getMapDoubleValue(data,"minz");
        Double maxz = DataUtils.getMapDoubleValue(data,"maxz");
        roveMap.put("minz",minz);
        roveMap.put("maxz",maxz);
        roveMap.put("step",step);
        roveMap.put("time",Math.ceil(Math.abs((maxz -minz))/step));

        String stage_desc = "";
        //水文预报 取 防范措施第一个,号之前的数据拼上预降措施
        String preventive_desc = DataUtils.getMapKeyValue(data,"preventive_desc");
        String pre_desc = DataUtils.getMapKeyValue(data,"pre_desc");
        String dispatch_desc = DataUtils.getMapKeyValue(data,"dispatch_desc");
        if(StringUtils.isNotBlank(preventive_desc) && preventive_desc.indexOf(",")>-1){
            stage_desc = preventive_desc.substring(0,preventive_desc.indexOf(",")+1) + dispatch_desc;
        }
        data.put("stage_desc",stage_desc);

        //调度措施如果是开闸取死值
        if(StringUtils.isNotBlank(dispatch_desc) && dispatch_desc.indexOf("需下泄")>-1){
            dispatch_desc += "并向游仙区应急局报备，抄送绵阳市水利局、游仙区水利局";
            data.put("dispatch_desc",dispatch_desc);
        }

        data.put("rove",roveMap);
        return data;
    }

    /**
     * 漫游初始化时的站点信息
     *
     * @param obj
     * @return
     */
    @Override
    public List<Map<String, Object>> GetInitSiteInfoList(Map<String, Object> obj) throws Exception {
        /*String plan_id = DataUtils.getMapKeyValue(obj,"plan_id");
        if(StringUtils.isBlank(plan_id)){
            throw new RRException("请选择方案！！");
        }*/
        String plan_time = DataUtils.getMapKeyValue(obj,"plan_time");
        if(StringUtils.isBlank(plan_time)){
            obj.put("plan_time",DataUtils.getDate("yyyy-MM-dd HH:mm:ss"));
        }
        /**
         * 只需要水位和水文站点
         * 01: 水位站
         * 08：水文站
         */
        obj.put("sttp","ZQ,ZZ");
        obj.put("flag","01");
        obj.put("plan_id",stormPlanService.getPlanId(obj));
        List<Map<String,Object>> data = mapper.Select_Init_Site_Info_List(obj);
        data.stream().forEach(item ->{
            double lgtd = DataUtils.getMapDoubleValue(item,"lgtd");
            double lttd = DataUtils.getMapDoubleValue(item,"lttd");
            item.put("position",new double[]{lgtd,lttd});
        });
        return data;
    }

    /**
     * 获取时序水位数据和时间轴
     *
     * @param obj
     * @return
     * @throws Exception
     */
    @Override
    public List<Map<String, Object>> GetTMAxisInfoList(Map<String, Object> obj) throws Exception {
        String plan_id = DataUtils.getMapKeyValue(obj,"plan_id");
        List<Map<String,Object>> list = new ArrayList<>();
        List<Map<String,Object>> sectionList = new ArrayList<>();

        String plan_time = DataUtils.getMapKeyValue(obj,"plan_time");
        if(StringUtils.isNotBlank(plan_time)){
            obj.put("tm",plan_time);
        }
        // 湖体抬升时序数据
        obj.put("nodeid",nodeid);
        if(StringUtils.isBlank(plan_id)){
            list = mapper.Select_TMAxis_Info_List_NullPlan(obj);;
            sectionList = mapper.Select_TMAxis_Section_List_NullPlan(obj);
        }else{
            obj.put("plan_id",stormPlanService.getPlanId(obj));
            list = mapper.Select_TMAxis_Info_List(obj);
            sectionList = mapper.Select_TMAxis_Section_List(obj);
        }
        Map<String,Double> sectionMap = sectionList.stream().collect(
                Collectors.toMap(item -> DataUtils.getMapKeyValue(item,"tm"),v ->{
                    return DataUtils.getMapDoubleValue(v,"z");
                },(v1,v2) -> v1
                ));
        // 通过相对时间来进行分组
        Map<String,List<Map<String,Object>>> listMap = list.stream().collect(
                Collectors.groupingBy(item -> DataUtils.getMapKeyValue(item,"tm")));
        List<Map<String,Object>> data = new LinkedList<>();
        for(String key: listMap.keySet()){
            Map<String,Object> dataMap = new HashMap<>();
            dataMap.put("tm",key);
            dataMap.put("sectionsw",sectionMap.get(key));
            List<Map<String,Object>> dataList = listMap.get(key);
            dataMap.put("step",dataList.get(0).get("step"));
            dataMap.put("siteinfo",dataList);
            data.add(dataMap);
        }
        // 排序
        data = data.stream().sorted(Comparator.comparing(item -> DataUtils.getMapIntegerValue(item,"step"))).collect(Collectors.toList());
        return data;
    }

    /**
     * 获取某个站点的时序数据
     *
     * @param obj
     * @return
     * @throws Exception
     */
    @Override
    public List<Map<String, Object>> GetTMAxisSiteList(Map<String, Object> obj) throws Exception {
        List<Map<String,Object>> data = new ArrayList<>();
        if(StringUtils.isBlank(DataUtils.getMapKeyValue(obj,"stcd"))){
            throw new RRException("请选择站点！！");
        }
        String plan_time = DataUtils.getMapKeyValue(obj,"plan_time");
        if(StringUtils.isNotBlank(plan_time)){
            obj.put("tm",plan_time);
        }
        if(StringUtils.isBlank(DataUtils.getMapKeyValue(obj,"plan_id"))){//如果没有预案取当前时间的水位，和未来3天的时间
            data = mapper.Select_TMAxis_Site_List_NullPlan(obj);
        }else {
            obj.put("plan_id",stormPlanService.getPlanId(obj));
            data = mapper.Select_TMAxis_Site_List(obj);
        }
        return data;
    }

    /**
     * 获取两个闸门的时序流量数据
     *
     * @param obj
     * @return
     * @throws Exception
     */
    @Override
    public List<Map<String, Object>> GetSluiceGateList(Map<String, Object> obj) throws Exception {
        List<Map<String,Object>> data = new ArrayList<>();
        String plan_time = DataUtils.getMapKeyValue(obj,"plan_time");
        if(StringUtils.isNotBlank(plan_time)){
            obj.put("tm",plan_time);
        }
        if(StringUtils.isBlank(DataUtils.getMapKeyValue(obj,"plan_id"))){//如果没有预案取当前时间的水位，和未来3天的时间
            data = mapper.Select_Sluice_Gate_List_NullPlan(obj);
        }else {
            obj.put("plan_id",stormPlanService.getPlanId(obj));
            data = mapper.Select_Sluice_Gate_List(obj);
        }
        return data;
    }

    /**
     * 获取流场的数据
     *
     * @param obj
     * @return
     * @throws Exception
     */
    @Override
    public Map<String, Object> GetDefaultWindRList(Map<String, Object> obj) throws Exception {
        if(StringUtils.isBlank(DataUtils.getMapKeyValue(obj,"plan_id"))){
            throw new RRException("请选择方案");
        }
        /**
         * 查询当前所对应的流厂数据
         */
        Map<String,Object> WindInfo = mapper.Select_Default_Wind_Info(obj);
        if(WindInfo != null){
            List<Map<String,Object>> dataList = mapper.Select_Default_wind_r_List(DataUtils.getMapKeyValue(WindInfo,"plan_id"));
            if(dataList !=null){
                Map<String,Object> data = new HashMap<>();
                data.put("TimeIndex","0");
                String[] Xlons = new String[dataList.size()];
                String[] Ylats = new String[dataList.size()];
                Double[] XflowData = new Double[dataList.size()];
                Double[] YflowData = new Double[dataList.size()];
                Integer index = 0;
                for(Map<String,Object> map:dataList){
                    Xlons[index] = DataUtils.getMapKeyValue(map,"x");
                    Ylats[index] = DataUtils.getMapKeyValue(map,"y");
                    XflowData[index] = DataUtils.getMapDoubleValue(map,"xv");
                    YflowData[index] = DataUtils.getMapDoubleValue(map,"yv");
                    index ++;
                }
                data.put("Xlons",Xlons);
                data.put("Ylats",Ylats);
                data.put("XflowData",XflowData);
                data.put("YflowData",YflowData);
                return data;
            }

        }
        return null;
    }
}
