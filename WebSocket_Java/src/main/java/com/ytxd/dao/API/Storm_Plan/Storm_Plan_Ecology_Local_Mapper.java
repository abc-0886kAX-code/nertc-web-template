package com.ytxd.dao.API.Storm_Plan;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 水生态 自建 调度预案
 */
@Mapper
public interface Storm_Plan_Ecology_Local_Mapper {

    List<Map<String, Object>> Select_Storm_Plan_Ecology_Local_List(Map<String, Object> map);

    Integer Insert_Storm_Plan_Ecology_Local(Map<String, Object> map);

    Integer Update_Storm_Plan_Ecology_Local(Map<String, Object> map);

    Integer Delete_Storm_Plan_Ecology_Local(Map<String, Object> map);

    Map<String, Object> Select_Duration_Evaporation(Map<String, Object> map);

    Map<String, Object> Get_Most_Suitable_Plan(Map<String, Object> map);

    Map<String, Object> Get_Default();
}
