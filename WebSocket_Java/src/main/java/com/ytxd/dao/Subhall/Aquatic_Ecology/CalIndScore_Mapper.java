package com.ytxd.dao.Subhall.Aquatic_Ecology;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 指标计算相关
 */
public interface CalIndScore_Mapper {
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
     * @param eval_type
     * @param score
     * @return
     */
    public Double Select_Eval_Score_Contrast(@Param("eval_type") String eval_type,@Param("score") Double score);
}
