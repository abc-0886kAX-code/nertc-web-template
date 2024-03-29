package com.ytxd.dao.API.EssentialInfo;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * DNA站点相关
 */
@Mapper
public interface EssentialDNAInfo_Mapper {
    /**
     * 获取DNA站点信息(带图片信息)
     * @param obj
     * @return
     */
    public List<Map<String,Object>> Select_DNAInfo_List(Map<String,Object> obj);

    /**
     * 通过stcd获取信息
     * @param stcd
     * @return
     */
    public List<Map<String,Object>> Select_DNAInfoItem_List(@Param("stcd") String stcd);
}
