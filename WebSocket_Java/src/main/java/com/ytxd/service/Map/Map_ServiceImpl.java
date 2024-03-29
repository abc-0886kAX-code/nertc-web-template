package com.ytxd.service.Map;

import com.ytxd.dao.Map.Map_Mapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

@Service("Map_ServiceImpl")
public class Map_ServiceImpl implements Map_Service{
    @Resource
    Map_Mapper map;

    @Override
    public Map<String, Object> getRiverMsg(String fid) {
        return map.getRiverMsg(fid);
    }

    @Override
    public Map<String, Object> getRsvrMsg(String fid) {
        return map.getRsvrMsg(fid);
    }
}
