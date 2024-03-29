package com.ytxd.dao.Statistics;

import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Mapper
public interface Stbprp_Mapper {

       public List<HashMap<String,Object>> GetData();
       public List<HashMap<String,Object>> GetRiverData();
       public List<HashMap<String,Object>> GetRainData(Map<String,Object> obj);

       List<HashMap<String,Object>> GetData4();

       List<HashMap<String,Object>> GetTopData(HashMap<String,Object> time);
}
