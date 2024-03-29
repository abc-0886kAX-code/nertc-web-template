package com.ytxd.dao.Happy_River_Lake;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 幸福河湖-指标库
 */
@Mapper
public interface HP_Indicator_Library_Mapper {
    /**
     * 查询指标列表
     * @param obj
     * @return
     */
    public List<Map<String,Object>> Select_HP_Indicator_Library_List(Map<String,Object> obj);

    /**
     * 添加
     * @param obj
     * @return
     */
    public Integer Insert_HP_Indicator_Library(Map<String,Object> obj);

    /**
     * 修改
     * @param obj
     * @return
     */
    public Integer Update_HP_Indicator_Library(Map<String,Object> obj);

    /**
     * 删除
     * @param indid
     * @return
     */
    public Integer Delete_HP_Indicator_Library(@Param("indid") String indid);

    /**
     * 查询子级指标
     * @param indid
     * @return
     */
    public List<Map<String,Object>> Select_Children_HP_Indicator_List(@Param("indid") String indid);

}
