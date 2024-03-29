package com.ytxd.service.API.Storm_Plan;

import com.github.pagehelper.PageInfo;
import com.ytxd.common.DataUtils;
import com.ytxd.common.exception.RRException;
import com.ytxd.dao.API.Storm_Plan.Storm_Plan_Ecology_Local_Mapper;
import com.ytxd.util.DateUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class Storm_Plan_Ecology_Local_ServiceImpl implements Storm_Plan_Ecology_Local_Service{

    @Resource
    private Storm_Plan_Ecology_Local_Mapper mapper;

    @Override
    public PageInfo GetList(Map<String, Object> map) {
        DataUtils.Padding(map);
        List<Map<String,Object>> data = mapper.Select_Storm_Plan_Ecology_Local_List(map);
        PageInfo pageInfo = new PageInfo(data);
        return pageInfo;
    }

    @Override
    public Map<String, Object> GetListById(Map<String, Object> obj) {
        if(StringUtils.isBlank(DataUtils.getMapKeyValue(obj,"id"))){
            throw new RRException("主键id不能为空！");
        }
        List<Map<String,Object>> data = mapper.Select_Storm_Plan_Ecology_Local_List(obj);
        if(data != null && data.size() > 0){
            return data.get(0);
        }
        return null;
    }

    @Override
    public Integer Save(Map<String, Object> map) {
        Integer total = 0;
        final String mh_wl = DataUtils.getMapKeyValue(map, "mh_wl");
        final String mh_wl_t = DataUtils.getMapKeyValue(map, "mh_wl_t");
        final String temp = DataUtils.getMapKeyValue(map, "temp");
        if(StringUtils.isBlank(mh_wl) || StringUtils.isBlank(mh_wl_t) || StringUtils.isBlank(temp)){
            throw new RRException("当前水位、目标水位、温度不能为空！");
        }
        // 计算所需时长
        final Map<String, Object> durationEvaporation = mapper.Select_Duration_Evaporation(map);
        if(durationEvaporation != null){
            map.put("duration", DataUtils.getMapKeyValue(durationEvaporation, "duration"));
            map.put("evaporation", DataUtils.getMapKeyValue(durationEvaporation, "evaporation"));
        }
        // 匹配最合适的模型预案
        final Map<String, Object> mostSuitablePlan = mapper.Get_Most_Suitable_Plan(map);
        if(!mostSuitablePlan.isEmpty() && mostSuitablePlan.containsKey("plan_id")){
            map.put("plan_id", DataUtils.getMapKeyValue(mostSuitablePlan, "plan_id"));
        }
        if(StringUtils.isBlank(DataUtils.getMapKeyValue(map,"id"))){
            map.put("submittime", DateUtils.getDate(new Date(),"yyyy-MM-dd HH:mm:ss"));
            total = mapper.Insert_Storm_Plan_Ecology_Local(map);
        }else {
            total = mapper.Update_Storm_Plan_Ecology_Local(map);
        }
        return total;
    }


    @Override
    public Integer Delete(Map<String, Object> map) {
        if(StringUtils.isBlank(DataUtils.getMapKeyValue(map,"id"))){
            throw new RRException("主键id不能为空！");
        }
        return mapper.Delete_Storm_Plan_Ecology_Local(map);
    }

    @Override
    public Map<String, Object> GetDefault() {
        return mapper.Get_Default();
    }
}
