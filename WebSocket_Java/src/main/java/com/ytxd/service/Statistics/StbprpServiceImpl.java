package com.ytxd.service.Statistics;

import com.ytxd.common.DataUtils;
import com.ytxd.common.util.R;
import com.ytxd.dao.Statistics.Stbprp_Mapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
public class StbprpServiceImpl implements  StbprpService {
    @Resource
    Stbprp_Mapper stbprp_mapper;
    @Override
    public LinkedList<Map<String, Object>> GetData(HttpServletRequest request) {
        List<HashMap<String, Object>> site = stbprp_mapper.GetData();
        List<HashMap<String, Object>> river = stbprp_mapper.GetRiverData();
        Map<String,Object> obj = new HashMap<>();
        obj.put("starttime", DataUtils.getRainStartTime());
        obj.put("endtime", DataUtils.getRainEndTime());
        List<HashMap<String, Object>> rain = stbprp_mapper.GetRainData(obj);
        LinkedList<Map<String, Object>> info = new LinkedList<>();
        Map<String,Object> rainMap = new HashMap<>();
        rainMap.put("title","雨量");
        rainMap.put("data",rain);
        rainMap.put("orderid",1);
        info.add(rainMap);
        Map<String,Object> riverMap = new HashMap<>();
        riverMap.put("title","河道");
        riverMap.put("data",river);
        riverMap.put("orderid",2);
        info.add(riverMap);
        Map<String,Object> siteMap = new HashMap<>();
        siteMap.put("title","站点");
        siteMap.put("data",site);
        siteMap.put("orderid",3);
        info.add(siteMap);
        return info;
    }

    @Override
    public List<HashMap<String, Object>> GetData4() {
        List<HashMap<String, Object>> obj = stbprp_mapper.GetData4();
        return obj;
    }

    @Override
    public List<HashMap<String, Object>> GetTopData() {
        HashMap<String, Object> time = new HashMap<>();
        time.put("starttime",DataUtils.getRainStartTime());
        time.put("endtime",DataUtils.getRainEndTime());
        time.put("riverstarttime",DataUtils.getStartTime());
        time.put("riverendtime",DataUtils.getEndTime());
        List<HashMap<String, Object>> obj = stbprp_mapper.GetTopData(time);
        return obj;
    }


}
