package com.ytxd.service.Water_Common.RealData;

import com.github.pagehelper.PageInfo;
import com.ytxd.common.DataUtils;
import com.ytxd.dao.Water_Common.RealData.Reservoir_Situation_Mapper;
import com.ytxd.util.DateUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @Classname Reservoir_Situation_ServiceImpl
 * @Author TY
 * @Date 2023/12/22 15:56
 * @Description TODO 水库信息
 */
@Service
public class Reservoir_Situation_ServiceImpl implements Reservoir_Situation_Service{
    @Resource
    private Reservoir_Situation_Mapper mapper;
    /**
     * 初始化查询条件
     * @param obj
     */
    private void InitObj(Map<String, Object> obj){
        /**
         * 只查询雨量站的数据
         * 04 雨量站
         */
        obj.put("sttp","RR");
    }
    /**
     *
     * @Desription TODO 查询水库的实时数据
     * @param obj
     * @return com.github.pagehelper.PageInfo
     * @date 2023/12/22 15:57
     * @Auther TY
     */
    @Override
    public PageInfo getRealReservoirList(Map<String, Object> obj) throws Exception {
        InitObj(obj);
        DataUtils.Padding(obj);
        if(StringUtils.isBlank(DataUtils.getMapKeyValue(obj,"starttime")) && StringUtils.isBlank(DataUtils.getMapKeyValue(obj,"tm"))){
            obj.put("starttime", DateUtils.getDate(new Date(),"yyyy-MM-dd HH:mm:ss"));
        }
        if(StringUtils.isBlank(DataUtils.getMapKeyValue(obj,"endtime")) && StringUtils.isBlank(DataUtils.getMapKeyValue(obj,"tm"))){
            obj.put("endtime", DateUtils.getDate(DateUtils.addDays(new Date(),1),"yyyy-MM-dd HH:mm:ss"));
        }
        List<Map<String,Object>> data = mapper.Select_Real_Reservoir_List(obj);
        Iterator<Map<String,Object>> iterator = data.iterator();
        while (iterator.hasNext()){
            /**
             * 可以写入报警相关信息
             */
            Map<String,Object> info = iterator.next();
            /**
             * 校核洪水位
             */
            Double ckflz = DataUtils.getMapDoubleValue(info,"ckflz");
            /**
             * 设计洪水位
             */
            Double dsflz = DataUtils.getMapDoubleValue(info,"dsflz");
        }
        return new PageInfo(data);
    }
}
