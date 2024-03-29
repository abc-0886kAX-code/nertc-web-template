package com.ytxd.dao.Subhall.Water_Culture;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 知识库
 * @author CYC
 */
@Mapper
public interface Water_Culture_Mapper {

    /**
     * 查询知识库信息
     */
    public List<Map<String,Object>> Select_ZskmhList(Map<String,Object> obj);

    /**
     * 查询水文化信息
     * @param obj
     * @return
     */
    public List<Map<String,Object>> Select_Water_Culture(Map<String,Object> obj);


    /**
     *水利基础知识
     */
    public List<Map<String,Object>> Select_Water_Day(Map<String,Object> obj);
}
