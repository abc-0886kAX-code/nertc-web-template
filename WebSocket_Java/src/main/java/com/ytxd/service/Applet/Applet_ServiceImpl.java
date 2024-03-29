package com.ytxd.service.Applet;

import com.github.pagehelper.PageInfo;
import com.ytxd.common.DataUtils;
import com.ytxd.dao.Applet.Applet_Mapper;
import com.ytxd.util.DateUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 微信小程序相关
 */
@Service
public class Applet_ServiceImpl implements Applet_Service{
    @Resource
    private Applet_Mapper mapper;
    /**
     * 获取闸门的水情信息
     *
     * @param obj
     * @return
     * @throws Exception
     */
    @Override
    public PageInfo GetStWasRList(Map<String, Object> obj) throws Exception {
        /**
         * 分页信息
         */
        DataUtils.Padding(obj);
        List<Map<String,Object>> data = mapper.Select_st_was_r_list(obj);
        // 经纬度处理
        data.stream().forEach(item ->{
            double lgtd = DataUtils.getMapDoubleValue(item,"lgtd");
            double lttd = DataUtils.getMapDoubleValue(item,"lttd");
            item.put("position",new double[]{lgtd,lttd});
        });
        PageInfo pageInfo = new PageInfo(data);
        return pageInfo;
    }

    /**
     * 获取天气预报15日的数据，按场次进行分组
     *
     * @param obj
     * @return
     * @throws Exception
     */
    @Override
    public PageInfo GetAppletPredictionRainList(Map<String, Object> obj) throws Exception {
        /**
         * 分页信息
         */
        DataUtils.Padding(obj);
        /**
         * 判断参数是否填写
         */
        if(StringUtils.isBlank(DataUtils.getMapKeyValue(obj,"tm"))){
            obj.put("tm", DateUtils.getDate(new Date(),"yyyy-MM-dd HH:mm:ss"));
        }
        List<Map<String,Object>> data = mapper.Select_Applet_Prediction_Rain_List(obj);
        data.stream().forEach(item ->{
            String tm = DataUtils.getMapKeyValue(item,"tm");
            if(StringUtils.isNotBlank(tm)){
                Map<String,Object> queryMap = new HashMap<>();
                queryMap.put("tm",tm);
                item.put("hour_seq",mapper.Select_Applet_Prediction_Rain_Hour_List(queryMap));
            }
        });
        PageInfo pageInfo = new PageInfo(data);
        return pageInfo;
    }

    /**
     * 按年月日的时间查询天气预报每小时的数据
     *
     * @param obj
     * @return
     * @throws Exception
     */
    @Override
    public PageInfo GetAppletPredictionRainListByHour(Map<String, Object> obj) throws Exception {
        /**
         * 分页信息
         */
        DataUtils.Padding(obj);
        /**
         * 判断参数是否填写
         */
        if(StringUtils.isBlank(DataUtils.getMapKeyValue(obj,"tm"))){
            obj.put("tm", DateUtils.getDate(new Date(),"yyyy-MM-dd"));
        }
        List<Map<String,Object>> data = mapper.Select_Applet_Prediction_Rain_Hour_List(obj);
        PageInfo pageInfo = new PageInfo(data);
        return pageInfo;
    }

    /**
     * 取今日降雨量、年初至今降雨量、去年同期降水、多年平均降水 用 3212000007
     *
     * @param obj
     * @return
     * @throws Exception
     */
    @Override
    public Map<String, Object> GetStaticInfoList(Map<String, Object> obj) throws Exception {
        /**
         * 判断参数是否填写
         */
        if(StringUtils.isBlank(DataUtils.getMapKeyValue(obj,"tm"))){
            obj.put("tm", DateUtils.getDate(new Date(),"yyyy-MM-dd"));
        }
        Map<String,Object> data = mapper.Select_Static_Info_List(obj);
        return data;
    }
}
