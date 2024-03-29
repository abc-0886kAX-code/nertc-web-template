package com.ytxd.service.API.DigitalHall;

import java.util.List;
import java.util.Map;

/**
 * 数字大厅
 */
public interface DigitalHall_Service {
    /**
     * 按时间查询当日、当月、当年的入园人次
     * @param obj
     * @return
     */
    public Map<String, Object> Select_Epn_List(Map<String,Object> obj);

    /**
     * 根据时间查询当前年份的月度统计情况
     * @param obj
     * @return
     */
    public List<Map<String,Object>> Select_Epn_Statistics(Map<String,Object> obj);

    /**
     * 新增当日入园人数
     * @param number 人数
     * @return 结果
     */
    int updateTodayNumbers(int number);
}
