package com.ytxd.service.API.Storm_Plan;

import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

public interface Storm_Plan_Environment_Local_Service {

    PageInfo GetList(Map<String, Object> map);

    Map<String, Object> GetListById(Map<String, Object> obj);

    Integer Save(Map<String, Object> map);

    Integer Delete(Map<String, Object> map);

    List<Map<String,Object>> GetStormplanenvironmentSelected(Map<String, Object> map);

}
