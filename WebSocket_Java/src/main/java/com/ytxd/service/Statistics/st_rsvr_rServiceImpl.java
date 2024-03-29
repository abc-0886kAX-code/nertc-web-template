package com.ytxd.service.Statistics;

import com.ytxd.common.DataUtils;
import com.ytxd.dao.Statistics.st_rsvr_rMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class st_rsvr_rServiceImpl implements st_rsvr_rService {
    @Resource
    st_rsvr_rMapper mapper;

    @Override
    public List<HashMap<String, Object>> GetData() {
        Map<String,Object> obj = new HashMap<>();
        obj.put("starttime", DataUtils.getStartTime());
        obj.put("endtime", DataUtils.getEndTime());
        List<HashMap<String, Object>> data = mapper.GetData(obj);
        return data;
    }
}
