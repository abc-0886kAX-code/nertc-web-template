package com.ytxd.service.API.EssentialInfo;

import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * 视频相关
 */
public interface EssentialVideoInfo_Service {
    /**
     * 获取视频站点信息（带视频信息）
     * @param obj
     * @return
     * @throws Exception
     */
    public PageInfo Select_VideoInfo_List(Map<String,Object> obj) throws Exception;

    /**
     * 通过stcd获取视频站点信息（带视频信息）
     * @param stcd
     * @return
     * @throws Exception
     */
    public Map<String,Object> Select_VideoInfoByStcd(String stcd) throws Exception;

    /**
     * 通过stcd获取信息
     * @param stcd
     * @return
     * @throws Exception
     */
    public List<Map<String,Object>> Select_VideoInfoItem_List(String stcd) throws Exception;
}
