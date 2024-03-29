package com.ytxd.dao.Applet;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 微信小程序
 */
@Mapper
public interface Applet_Mapper {
    /**
     * 获取闸门水情信息
     * @param obj
     * @return
     */
    public List<Map<String,Object>> Select_st_was_r_list(Map<String,Object> obj);

    /**
     * 获取天气预报15日的数据，按场次进行分组
     * @param obj
     * @return
     */
    public List<Map<String,Object>> Select_Applet_Prediction_Rain_List(Map<String,Object> obj);

    /**
     * 按年月日查询天气预报每小时的数据
     * @param obj
     * @return
     */
    public List<Map<String,Object>> Select_Applet_Prediction_Rain_Hour_List(Map<String,Object> obj);

    /**
     * 获取今日降雨量、年初至今降雨量、去年同期降水、多年平均降水 用 3212000007
     * @param obj
     * @return
     */
    public Map<String,Object> Select_Static_Info_List(Map<String,Object> obj);

}
