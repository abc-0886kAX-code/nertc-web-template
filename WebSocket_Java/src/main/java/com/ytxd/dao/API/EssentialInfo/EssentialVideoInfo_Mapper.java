package com.ytxd.dao.API.EssentialInfo;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 视频站点相关
 */
@Mapper
public interface EssentialVideoInfo_Mapper {
    /**
     * 获取视频站点信息（带视频信息）
     * @param obj
     * @return
     */
    public List<Map<String,Object>> Select_VideoInfo_List(Map<String,Object> obj);

    /**
     * 通过stcd获取信息
     * @param stcd
     * @return
     */
    public List<Map<String,Object>> Select_VideoInfoItem_List(@Param("stcd") String stcd);
}
