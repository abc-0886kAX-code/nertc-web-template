package com.ytxd.service.API.DigitalHall;

import com.ytxd.common.DataUtils;
import com.ytxd.common.exception.RRException;
import com.ytxd.dao.API.DigitalHall.DigitalHall_Mapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 数字大厅
 */
@Service
public class DigitalHall_ServiceImpl implements DigitalHall_Service{
    @Resource
    private DigitalHall_Mapper mapper;
    /**
     * 按时间查询当日、当月、当年的入园人次
     *
     * @param obj
     * @return
     */
    @Override
    public Map<String, Object> Select_Epn_List(Map<String, Object> obj) {
        if(StringUtils.isBlank(DataUtils.getMapKeyValue(obj,"keytime"))){
            obj.put("keytime",DataUtils.getDate("yyyy-MM-dd"));
        }
        return mapper.Select_Epn_List(obj);
    }

    /**
     * 根据时间查询当前年份的月度统计情况
     *
     * @param obj
     * @return
     */
    @Override
    public List<Map<String, Object>> Select_Epn_Statistics(Map<String, Object> obj) {
        if(StringUtils.isBlank(DataUtils.getMapKeyValue(obj,"keytime"))){
            obj.put("keytime",DataUtils.getDate("yyyy-MM-dd"));
        }
        return mapper.Select_Epn_Statistics(obj);
    }

    @Override
    public int updateTodayNumbers(int number) {
        return mapper.updateTodayNumbers(number);
    }
}
