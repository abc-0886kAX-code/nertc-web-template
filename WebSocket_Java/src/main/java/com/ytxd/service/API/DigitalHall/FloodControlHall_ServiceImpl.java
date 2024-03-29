package com.ytxd.service.API.DigitalHall;

import com.ytxd.common.DataUtils;
import com.ytxd.dao.API.DigitalHall.FloodControlHall_Mapper;
import com.ytxd.util.DateUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class FloodControlHall_ServiceImpl implements FloodControlHall_Service{
    @Resource
    private FloodControlHall_Mapper mapper;
    /**
     * 查询预报降雨
     *
     * @param obj
     * @return
     */
    @Override
    public List<Map<String, Object>> GetPredictionRainList(Map<String, Object> obj) throws Exception {
        /**
         * 判断时间是否为空
         */
        if(StringUtils.isBlank(DataUtils.getMapKeyValue(obj,"tm"))){
            obj.put("tm", DateUtils.getDate(new Date(),"yyyy-MM-dd HH:mm:ss"));
        }
        List<Map<String,Object>> list = mapper.Select_Prediction_RainList(obj);

        List<Map<String, Object>> data = new ArrayList<>();
        /**
         * 处理数据
         */
        Map<String,Object>  dataMap = new HashMap<>();
        dataMap.put("stcd","3212000007");
        dataMap.put("stnm","芙蓉溪");
        dataMap.put("source",list);
        data.add(dataMap);
        return data;
    }

    @Override
    public List<Map<String, Object>> GetStorageStateList(Map<String, Object> obj) throws Exception {
        List<Map<String, Object>> data = mapper.Select_StorageState_List(obj);
        data.stream().forEach(item ->{
            Double proportion = DataUtils.getMapDoubleValue(item,"proportion");
            if(proportion > 100){
                item.put("proportion",proportion);
            }
        });
        return data;
    }
}
