package com.ytxd.service.Water_Common;

import com.github.pagehelper.PageInfo;
import com.ytxd.common.DataUtils;
import com.ytxd.dao.Water_Common.Water_Common_Mapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @Classname Water_Common_ServiceImpl
 * @Author TY
 * @Date 2024/1/29 15:36
 * @Description TODO
 */
@Service
public class Water_Common_ServiceImpl implements Water_Common_Service{
    @Resource
    private Water_Common_Mapper mapper;
    /**
     *
     * @Desription TODO 查询站点信息
     * @param obj
     * @return com.github.pagehelper.PageInfo
     * @date 2024/1/29 15:36
     * @Auther TY
     */
    @Override
    public PageInfo getSiteInfoList(Map<String, Object> obj) throws Exception {
        DataUtils.Padding(obj);
        List<Map<String,Object>> data = mapper.Select_Site_Data_Info_List(obj);
        Iterator<Map<String,Object>> iterator  = data.iterator();
        while (iterator.hasNext()){
            Map<String,Object> info = iterator.next();
            double lgtd = DataUtils.getMapDoubleValue(info,"lgtd");
            double lttd = DataUtils.getMapDoubleValue(info,"lttd");
            info.put("position",new double[]{lgtd,lttd});
        }
        return new PageInfo(data);
    }
    /**
     *
     * @Desription TODO 获取列表数据
     * @param obj
     * @return com.github.pagehelper.PageInfo
     * @date 2024/2/21 16:52
     * @Auther TY
     */
    @Override
    public PageInfo getSiteDataInfoList(Map<String, Object> obj) throws Exception {
        DataUtils.Padding(obj);
        List<Map<String,Object>> data = mapper.Select_Site_Data_Info_List(obj);
        Iterator<Map<String,Object>> iterator  = data.iterator();
        while (iterator.hasNext()){
            Map<String,Object> info = iterator.next();
            double lgtd = DataUtils.getMapDoubleValue(info,"lgtd");
            double lttd = DataUtils.getMapDoubleValue(info,"lttd");
            info.put("position",new double[]{lgtd,lttd});
        }
        return new PageInfo(data);
    }

    @Override
    public PageInfo selectSiteWARNInfoList(Map<String, Object> obj) {
        DataUtils.Padding(obj);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // 获取当前时间
        LocalDateTime now = LocalDateTime.now();
        String nowStr = now.format(formatter);

        // 获取24小时前的时间
        LocalDateTime before24Hours = now.minus(1, ChronoUnit.DAYS);
        String before24HoursStr = before24Hours.format(formatter);

        obj.put("start_time" , before24HoursStr);
        obj.put("end_time" , nowStr);

        List<Map<String,Object>> data = mapper.selectSiteWARNInfoList(obj);
        Iterator<Map<String,Object>> iterator  = data.iterator();
        while (iterator.hasNext()){
            Map<String,Object> info = iterator.next();
            double lgtd = DataUtils.getMapDoubleValue(info,"lgtd");
            double lttd = DataUtils.getMapDoubleValue(info,"lttd");
            info.put("position",new double[]{lgtd,lttd});
        }
        return new PageInfo(data);
    }
}
