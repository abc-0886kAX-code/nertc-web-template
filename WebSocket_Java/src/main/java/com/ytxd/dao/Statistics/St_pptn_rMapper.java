package com.ytxd.dao.Statistics;

import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mapper
public interface St_pptn_rMapper {
    List<HashMap<String, Object>> GetData(Map<String,Object> obj);

    List<HashMap<String, Object>> GetCount(Map<String,Object> obj);

    List<HashMap<String, Object>> GetNumber();

}
