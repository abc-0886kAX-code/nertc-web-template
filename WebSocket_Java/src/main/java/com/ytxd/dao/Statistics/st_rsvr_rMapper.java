package com.ytxd.dao.Statistics;

import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mapper
public interface st_rsvr_rMapper {

    List<HashMap<String, Object>> GetData(Map<String,Object> obj);
}
