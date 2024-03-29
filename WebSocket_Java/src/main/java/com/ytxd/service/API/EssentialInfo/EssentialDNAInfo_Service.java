package com.ytxd.service.API.EssentialInfo;

import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * DNA 站点信息
 */
public interface EssentialDNAInfo_Service {
    /**
     * 获取DNA站点信息(带图片信息)
     * @param obj
     * @return
     */
    public PageInfo Select_DNAInfo_List(Map<String,Object> obj);

    /**
     * 通过stcd获取DNA站点信息(带图片信息)
     * @param stcd
     * @return
     */
    public Map<String,Object> Select_DNAInfoByStcd(String stcd);

    /**
     * 通过stcd获取信息
     * @param stcd
     * @return
     */
    public List<Map<String,Object>> Select_DNAInfoItem_List(@Param("stcd") String stcd);
}
