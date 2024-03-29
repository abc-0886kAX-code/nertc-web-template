package com.ytxd.dao.Happy_River_Lake;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

// 计算公式
@Mapper
public interface HPCalIndScore_Mapper {
    /**
     * 获取指标的计算方法
     * @param id
     * @return
     */
    public Map<String,Object> Select_Ind_Formula(@Param("id") String id);

    /**
     * 获取参数对应的指标
     * @param id
     * @return
     */
    public List<Map<String,Object>> Select_Param_Ind_List(@Param("id") String id);

    /**
     * 根据类型和比列得到分数
     * @param hp_type
     * @param score
     * @return
     */
    public Double Select_HP_Score_Contrast(@Param("hp_type") String hp_type,@Param("score") Double score);
}
