package com.ytxd.dao.Subhall.Aquatic_Ecology;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 水生态
 */
@Mapper
public interface Aquatic_Ecology_Mapper {
    /**
     * 查询最新的dna站点数据
     * @param obj
     * @return
     */
    public List<Map<String,Object>> Select_DNA_Info_List(Map<String,Object> obj);

    /**
     * 获取dna站点数据list
     * @param obj
     * @return
     */
    public List<Map<String,Object>> Select_DNA_R_List(Map<String,Object> obj);

    /**
     * 获取流量站点信息
     * @param obj
     * @return
     */
    public List<Map<String,Object>> Select_Flow_Site_List(Map<String,Object> obj);

    /**
     * 获取dna站点想详细信息
     * @param stcd
     * @return
     */
    public Map<String,Object> Select_DNA_Pron_Info(@Param("stcd") String stcd);
}
