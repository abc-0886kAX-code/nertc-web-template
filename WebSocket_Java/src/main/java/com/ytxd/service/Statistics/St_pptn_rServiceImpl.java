package com.ytxd.service.Statistics;

import com.ytxd.common.DataUtils;
import com.ytxd.dao.Statistics.St_pptn_rMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class St_pptn_rServiceImpl implements St_pptn_rService{
    @Resource
    St_pptn_rMapper mapper;
    @Override
    public List<HashMap<String, Object>> GetData() {
        Map<String,Object> obj = new HashMap<>();
        obj.put("starttime", DataUtils.getRainStartTime());
        obj.put("endtime", DataUtils.getRainEndTime());
        List<HashMap<String, Object>> data = mapper.GetData(obj);
        return data;
    }

    @Override
    public List<HashMap<String, Object>> GetCount() {
        Map<String,Object> obj = new HashMap<>();
        obj.put("starttime", DataUtils.getRainStartTime());
        obj.put("endtime", DataUtils.getRainEndTime());
        List<HashMap<String, Object>> data = mapper.GetCount(obj);
        return data;
    }

    @Override
    public List<HashMap<String, Object>> GetNumber() {
        List<HashMap<String, Object>> obj = mapper.GetNumber();
        return obj;
    }
}
