package com.ytxd.service.API.Storm_Plan;

import com.github.pagehelper.PageInfo;
import com.ytxd.common.DataUtils;
import com.ytxd.common.exception.RRException;
import com.ytxd.dao.API.Storm_Plan.Storm_Plan_Mapper;
import com.ytxd.util.DateUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * 调度预案
 */
@Service
public class Storm_Plan_ServiceImpl implements Storm_Plan_Service{
    @Resource
    private Storm_Plan_Mapper mapper;
    /**
     * 调度预案查询
     *
     * @param obj
     * @return
     * @throws Exception
     */
    @Override
    public PageInfo GetList(Map<String, Object> obj) throws Exception {
        DataUtils.Padding(obj);
        List<Map<String,Object>> data = mapper.Select_Storm_Plan_List(obj);
        data.stream().forEach(item ->{
            if(Objects.equals("是",item.get("pre_drop"))){
                item.put("pre_drop",true);
            }else {
                item.put("pre_drop",false);
            }
        });
        PageInfo pageInfo = new PageInfo(data);
        return pageInfo;
    }

    @Override
    public PageInfo GetListLocal(Map<String, Object> obj) throws Exception {
        DataUtils.Padding(obj);
        obj.put("plan_id",getPlanId(obj));
        List<Map<String,Object>> data = mapper.Select_Storm_Plan_List_Local(obj);
        data.stream().forEach(item ->{
            if(Objects.equals("是",item.get("pre_drop"))){
                item.put("pre_drop",true);
            }else {
                item.put("pre_drop",false);
            }
        });
        PageInfo pageInfo = new PageInfo(data);
        return pageInfo;
    }

    /**
     * 获取定时预案列表信息
     * @param obj
     * @return
     * @throws Exception
     */
    @Override
    public PageInfo GetRegularList(Map<String, Object> obj) throws Exception {
        String tm = DataUtils.getMapKeyValue(obj,"tm");
        if(StringUtils.isBlank(tm)){
            obj.put("tm",DataUtils.getDate("yyyy-MM-dd"));
        }
        DataUtils.Padding(obj);
        List<Map<String,Object>> data = mapper.Select_Storm_Plan_Regular_List(obj);
        PageInfo pageInfo = new PageInfo(data);
        return pageInfo;
    }

    /**
     * 根据ID查询
     *
     * @param obj
     * @return
     * @throws Exception
     */
    @Override
    public Map<String, Object> GetListById(Map<String, Object> obj) throws Exception {
        if(StringUtils.isBlank(DataUtils.getMapKeyValue(obj,"plan_id"))){
            throw new RRException("plan_id不能为空！");
        }
        List<Map<String,Object>> data = mapper.Select_Storm_Plan_List(obj);
        if(data != null && data.size() > 0){
            return data.get(0);
        }
        return null;
    }

    @Override
    public Map<String, Object> GetListByIdLocal(Map<String, Object> obj) throws Exception {
        if(StringUtils.isBlank(DataUtils.getMapKeyValue(obj,"plan_id"))){
            throw new RRException("plan_id不能为空！");
        }
        List<Map<String,Object>> data = mapper.Select_Storm_Plan_List_Local(obj);
        if(data != null && data.size() > 0){
            return data.get(0);
        }
        return null;
    }

    /**
     * 调度预案保存
     *
     * @param obj
     * @return
     * @throws Exception
     */
    @Override
    public Integer Save(Map<String, Object> obj) throws Exception {
        Integer total = 0;
        final String period = DataUtils.getMapKeyValue(obj, "period");
        if(StringUtils.isBlank(period)){
            throw new RRException("降雨历时不能为空！");
        }
        final String reproduce = getReproduce(period);
//        if(StringUtils.isBlank(reproduce)){
//            throw new RRException("降雨历时取值异常 只能为 6h 1d 3d！");
//        }
        obj.put("reproduce", reproduce);
        String pre_desc = DataUtils.getMapKeyValue(obj,"pre_desc");
        String dispatch_desc = DataUtils.getMapKeyValue(obj,"dispatch_desc");
        String preventive_desc = DataUtils.getMapKeyValue(obj,"preventive_desc");
        if(StringUtils.isNotBlank(pre_desc) || StringUtils.isNotBlank(dispatch_desc) || StringUtils.isNotBlank(preventive_desc)){
            StringBuilder st_desc = new StringBuilder("");
            if(StringUtils.isNotBlank(pre_desc)){
                st_desc.append("预降措施:").append(pre_desc).append("\n");
            }
            if(StringUtils.isNotBlank(dispatch_desc)){
                st_desc.append("调度措施:").append(dispatch_desc).append("\n");
            }
            if(StringUtils.isNotBlank(preventive_desc)){
                st_desc.append("防范措施:").append(preventive_desc);
            }
            obj.put("st_desc", st_desc.toString());
        }
//        obj.put("st_desc",dispatch_desc+preventive_desc+pre_desc);
        // 关联模型id
        final Map<String, Object> modelPlan = mapper.Get_Most_Suitable_Plan(obj);
        final Double p = DataUtils.getMapDoubleValue(obj,"p");
        if(modelPlan != null && modelPlan.containsKey("plan_id") && p >= 30 ){
            obj.put("pid", DataUtils.getMapKeyValue(modelPlan, "plan_id"));

            if(StringUtils.isNotBlank(DataUtils.getMapKeyValue(modelPlan,"pre_desc"))){
                obj.put("pre_desc",modelPlan.get("pre_desc"));
            }
            if(StringUtils.isNotBlank(DataUtils.getMapKeyValue(modelPlan,"dispatch_desc"))){
                obj.put("dispatch_desc",modelPlan.get("dispatch_desc"));
            }
            if(StringUtils.isNotBlank(DataUtils.getMapKeyValue(modelPlan,"preventive_desc"))){
                obj.put("preventive_desc",modelPlan.get("preventive_desc"));
            }
            if(StringUtils.isNotBlank(DataUtils.getMapKeyValue(modelPlan,"st_desc"))){
                obj.put("st_desc",modelPlan.get("st_desc"));
            }
        }
        if(StringUtils.isBlank(DataUtils.getMapKeyValue(obj,"plan_id"))){
            obj.put("submittime", DateUtils.getDate(new Date(),"yyyy-MM-dd HH:mm:ss"));
//            obj.put("plan_id", UUID.randomUUID().toString().replaceAll("-","").toUpperCase(Locale.ROOT));
           total = mapper.Insert_Storm_Plan_Local(obj);
        }else {
            total = mapper.Update_Storm_Plan_Local(obj);
        }
        return total;
    }

    @Override
    public Integer originalSave(Map<String, Object> obj) throws Exception {
        Integer total = 0;
        final String period = DataUtils.getMapKeyValue(obj, "period");
        if(StringUtils.isBlank(period)){
            throw new RRException("降雨历时不能为空！");
        }
        final String reproduce = getReproduce(period);
//        if(StringUtils.isBlank(reproduce)){
//            throw new RRException("降雨历时取值异常 只能为 6h 1d 3d！");
//        }
        obj.put("reproduce", reproduce);
        String pre_desc = DataUtils.getMapKeyValue(obj,"pre_desc");
        String dispatch_desc = DataUtils.getMapKeyValue(obj,"dispatch_desc");
        String preventive_desc = DataUtils.getMapKeyValue(obj,"preventive_desc");
        if(StringUtils.isNotBlank(pre_desc) || StringUtils.isNotBlank(dispatch_desc) || StringUtils.isNotBlank(preventive_desc)){
            StringBuilder st_desc = new StringBuilder("");
            if(StringUtils.isNotBlank(pre_desc)){
                st_desc.append("预降措施:").append(pre_desc).append("\n");
            }
            if(StringUtils.isNotBlank(dispatch_desc)){
                st_desc.append("调度措施:").append(dispatch_desc).append("\n");
            }
            if(StringUtils.isNotBlank(preventive_desc)){
                st_desc.append("防范措施:").append(preventive_desc);
            }
            obj.put("st_desc", st_desc.toString());
        }
//        obj.put("st_desc",dispatch_desc+preventive_desc+pre_desc);
        // 关联模型id
        final Map<String, Object> modelPlan = mapper.Get_Most_Suitable_Plan(obj);
        String tmpplan_id = null;
        if(modelPlan != null && modelPlan.containsKey("plan_id")){
            tmpplan_id = DataUtils.getMapKeyValue(modelPlan,"plan_id");
        }else {
            throw new RRException("未查询到匹配的预案信息");
        }
        String  plan_id = DataUtils.getMapKeyValue(obj,"plan_id");
        if(StringUtils.isBlank(plan_id)){
            obj.put("submittime", DateUtils.getDate(new Date(),"yyyy-MM-dd HH:mm:ss"));
            plan_id = UUID.randomUUID().toString().replaceAll("-","").toUpperCase(Locale.ROOT);
            obj.put("plan_id", plan_id);
            total = mapper.Insert_Storm_Plan(obj);
        }else {
            total = mapper.Update_Storm_Plan(obj);
        }
        try{
            Map<String,Object> queryMap = new HashMap<>();
            queryMap.put("plan_id",plan_id);
            if(StringUtils.isNotBlank(tmpplan_id)){
                queryMap.put("tmpplan_id",tmpplan_id);
                mapper.Delete_storm_node_r(queryMap);
                mapper.Insert_storm_node_r(queryMap);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return total;
    }


    private String getReproduce(String period){
        String reproduce = "";
        switch (period){
            case "6h":
                reproduce = "24h";
                break;
            case "1d":
                reproduce = "3d";
                break;
            case "3d":
                reproduce = "7d";
                break;
            default:
                break;
        }
        return reproduce;
    }

    /**
     * 删除
     *
     * @param obj
     * @return
     * @throws Exception
     */
    @Override
    public Integer Delete(Map<String, Object> obj) throws Exception {
        if(StringUtils.isBlank(DataUtils.getMapKeyValue(obj,"plan_id"))){
            throw new RRException("plan_id不能为空！");
        }
        return mapper.Delete_Storm_Plan_Local(obj);
    }
    /**
     *
     * @Desription TODO 调度预案删除
     * @param obj
     * @return java.lang.Integer
     * @date 2023/11/14 15:29
     * @Auther TY
     */
    @Override
    public Integer originalDelete(Map<String, Object> obj) throws Exception {
        if(StringUtils.isBlank(DataUtils.getMapKeyValue(obj,"plan_id"))){
            throw new RRException("plan_id不能为空！");
        }
        mapper.Delete_storm_node_r(obj);
        return mapper.Delete_Storm_Plan(obj);
    }

    @Override
    public Map<String, Object> GetForecastRainfall() {
        return mapper.Get_Forecast_Rainfall();
    }
    /**
     *
     * @Desription TODO 获取plan_id
     * @param obj
     * @return java.lang.String
     * @date 2024/3/5 10:20
     * @Auther TY
     */
    @Override
    public String getPlanId(Map<String,Object> obj){
        String plan_id = DataUtils.getMapKeyValue(obj,"plan_id");
        if(StringUtils.isNotBlank(plan_id)&&plan_id.length() == 32){
            return plan_id;
        }else {
            try{
                final Map<String, Object> map = GetListByIdLocal(obj);
                if(map != null && map.containsKey("pid")){
                    plan_id = DataUtils.getMapKeyValue(map, "pid");
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            return plan_id;
        }
    }
}
