package com.ytxd.dao.API.DigitalHall;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 数字大厅
 */
@Mapper
public interface DigitalHall_Mapper {
    /**
     * 按时间查询当日、当月、当年的入园人次
     * @param obj
     * @return
     */
    public Map<String,Object> Select_Epn_List(Map<String,Object> obj);

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
    public int updateTodayNumbers(int number);

}
