package com.ytxd.service.Water_Common.RealData;

import com.github.pagehelper.PageInfo;
import com.ytxd.common.DataUtils;
import com.ytxd.dao.Water_Common.RealData.Rain_Situation_Mapper;
import com.ytxd.dao.Water_Common.RealData.Water_Supply_Plant_Mapper;
import com.ytxd.util.DateUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Classname Water_Supply_Plant_ServiceImpl
 * @Author TY
 * @Date 2023/12/27 14:16
 * @Description TODO 供水厂
 */
@Service
public class Water_Supply_Plant_ServiceImpl implements Water_Supply_Plant_Service {
    @Resource
    private Water_Supply_Plant_Mapper mapper;
    /**
     * 初始化查询条件
     * @param obj
     */
    private void InitObj(Map<String, Object> obj){
        /**
         * 只查询雨量站的数据
         * 04 雨量站
         */
        obj.put("sttp","GS");
    }
    /**
     *
     * @Desription TODO 获取供水厂信息
     * @param obj
     * @return com.github.pagehelper.PageInfo
     * @date 2023/12/27 14:31
     * @Auther TY
     */
    @Override
    public PageInfo getWaterSupplyPlantList(Map<String, Object> obj) throws Exception {
        InitObj(obj);
        DataUtils.Padding(obj);
        if(StringUtils.isBlank(DataUtils.getMapKeyValue(obj,"starttime"))){
            obj.put("starttime",DataUtils.getDate("yyyy-MM-dd") +" 08:00:00");
        }
        if(StringUtils.isBlank(DataUtils.getMapKeyValue(obj,"endtime"))){
            obj.put("endtime", DateUtils.getDate(DateUtils.addDays(new Date(),1),"yyyy-MM-dd HH:mm:ss"));
        }
        List<Map<String,Object>> list = mapper.Select_Water_Supply_Plant_List(obj);
        list.stream().forEach(item ->{
            double lgtd = DataUtils.getMapDoubleValue(item,"lgtd");
            double lttd = DataUtils.getMapDoubleValue(item,"lttd");
            item.put("position",new double[]{lgtd,lttd});
        });
        return new PageInfo(list);
    }
    /**
     *
     * @Desription TODO 获取时序数据
     * @param obj
     * @return com.github.pagehelper.PageInfo
     * @date 2023/12/27 14:31
     * @Auther TY
     */
    @Override
    public PageInfo getRealWaterSupplyPlantList(Map<String, Object> obj) throws Exception {
        InitObj(obj);
        DataUtils.Padding(obj);
        if(StringUtils.isBlank(DataUtils.getMapKeyValue(obj,"starttime"))){
            obj.put("starttime",DataUtils.getDate("yyyy-MM-dd") +" 08:00:00");
        }
        if(StringUtils.isBlank(DataUtils.getMapKeyValue(obj,"endtime"))){
            obj.put("endtime", DateUtils.getDate(DateUtils.addDays(new Date(),1),"yyyy-MM-dd HH:mm:ss"));
        }
        List<Map<String,Object>> list = mapper.Select_Real_Water_Supply_Plant_List(obj);
        return new PageInfo(list);
    }
}
