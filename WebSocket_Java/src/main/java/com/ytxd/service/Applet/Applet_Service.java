package com.ytxd.service.Applet;

import com.github.pagehelper.PageInfo;

import java.util.Map;

/**
 * 微信小程序相关
 */
public interface Applet_Service {
    /**
     * 获取闸门的水情信息
     * @param obj
     * @return
     * @throws Exception
     */
    public PageInfo GetStWasRList(Map<String,Object> obj) throws Exception;

    /**
     * 获取天气预报15日的数据，按场次进行分组
     * @param obj
     * @return
     * @throws Exception
     */
    public PageInfo GetAppletPredictionRainList(Map<String,Object> obj) throws Exception;

    /**
     * 按年月日的时间查询天气预报每小时的数据
     * @param obj
     * @return
     * @throws Exception
     */
    public PageInfo GetAppletPredictionRainListByHour(Map<String,Object> obj) throws Exception;

    /**
     * 取今日降雨量、年初至今降雨量、去年同期降水、多年平均降水 用 3212000007
     * @param obj
     * @return
     * @throws Exception
     */
    public Map<String,Object> GetStaticInfoList(Map<String,Object> obj) throws Exception;
}
