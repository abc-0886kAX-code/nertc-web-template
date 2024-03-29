package com.ytxd.dao.VideoInfo;

import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Classname SurveillanceVideo_Mapper
 * @Author TY
 * @Date 2024/1/5 16:53
 * @Description TODO
 */
@Mapper
public interface SurveillanceVideo_Mapper {
    /**
     *
     * @Desription TODO 查询视频监控站点
     * @param obj
     * @return java.util.List<java.util.HashMap<java.lang.String,java.lang.Object>>
     * @date 2024/1/5 16:59
     * @Auther TY
     */
    public List<HashMap<String,Object>> Select_SurveillanceVideo_List(Map<String,Object> obj);
}
