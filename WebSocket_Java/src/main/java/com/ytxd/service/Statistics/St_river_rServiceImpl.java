package com.ytxd.service.Statistics;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ytxd.common.DataUtils;
import com.ytxd.dao.Statistics.St_river_rMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Wrapper;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class St_river_rServiceImpl implements St_river_rService {
    @Resource
    St_river_rMapper mapper;
    @Override
    public List<HashMap<String, Object>> GetData() {
        Map<String,Object> obj = new HashMap<>();
        obj.put("starttime", DataUtils.getStartTime());
        obj.put("endtime", DataUtils.getEndTime());
        List<HashMap<String, Object>> data = mapper.GetData(obj);
        return data;
    }

    @Override
    public List<HashMap<String, Object>> GetCount() {
        List<HashMap<String, Object>> obj = mapper.GetCount();
        HashMap<String, Object> total = mapper.GetTotal();
        obj.add(total);
        return obj;
    }
}
