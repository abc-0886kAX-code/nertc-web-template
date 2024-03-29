package com.ytxd.dao.Video;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 视频站点相关
 */
@Mapper
public interface Video_Mapper {
    /**
     * 查询视频站点的信息
     * @param obj
     * @return
     */
    public List<Map<String,Object>> Select_Video_Info_List(Map<String,Object> obj);
    /**
     *
     * @Desription TODO 修改视频站点视频流信息
     * @param obj
     * @return java.lang.Integer
     * @date 2024/2/26 10:20
     * @Auther TY
     */
    public Integer Update_Vide_Url(Map<String,Object> obj);
}
