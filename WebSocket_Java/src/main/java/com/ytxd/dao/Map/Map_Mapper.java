package com.ytxd.dao.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

@Mapper
public interface Map_Mapper {

    Map<String, Object> getRiverMsg(@Param("fid") String fid);

    Map<String, Object> getRsvrMsg(@Param("fid") String fid);
}
