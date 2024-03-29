package com.ytxd.dao.Happy_River_Lake;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 幸福河湖-评价方案
 */
@Mapper
public interface HP_Scheme_Mapper {
    /**
     * 查询评价方案
     * @param obj
     * @return
     */
    public List<Map<String,Object>> Select_HP_Scheme_List(Map<String,Object> obj);

    /**
     * 添加
     * @param obj
     * @return
     */
    public Integer Insert_HP_Scheme(Map<String,Object> obj);

    /**
     * 修改
     * @param obj
     * @return
     */
    public Integer Update_HP_Scheme(Map<String,Object> obj);

    /**
     * 删除
     * @param schemeid
     * @return
     */
    public Integer Delete_HP_Scheme(@Param("schemeid") String schemeid);

    /**
     * 初始化指标
     * @param schemeid
     * @return
     */
    public Integer Insert_Initialization_Scheme_Ind(@Param("schemeid") String schemeid);

    /**
     * 插入默认的主体
     * @param schemeid
     * @return
     */
    public Integer Insert_HP_Scheme_Bulk(@Param("schemeid") String schemeid);

    /**
     * 初始化分数
     * @param schemeid
     * @return
     */
    public Integer Update_Default_Scheme_Score(@Param("schemeid") String schemeid);
    public Integer Update_Default_ind_Score(@Param("schemeid") String schemeid);
    public Integer Insert_Default_Bulk_Score(@Param("schemeid") String schemeid);
}
