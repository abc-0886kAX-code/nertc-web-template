package com.ytxd.service.API.Storm_Plan;

import com.github.pagehelper.PageInfo;

import java.util.Map;

public interface Storm_Plan_Ecology_Local_Service {

    PageInfo GetList(Map<String, Object> map);

    Map<String, Object> GetListById(Map<String, Object> obj);

    Integer Save(Map<String, Object> map);

    Integer Delete(Map<String, Object> map);

    Map<String, Object> GetDefault();
}
