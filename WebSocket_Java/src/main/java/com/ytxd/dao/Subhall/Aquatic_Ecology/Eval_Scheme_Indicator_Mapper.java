package com.ytxd.dao.Subhall.Aquatic_Ecology;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 水生态-评价方案指标
 */
@Mapper
public interface Eval_Scheme_Indicator_Mapper {
    /**
     * 查询评价方案指标
     * @param obj
     * @return
     */
    public List<Map<String,Object>> Select_Eval_Scheme_Indicator_List(Map<String,Object> obj);

    /**
     * 修改
     * @param obj
     * @return
     */
    public Integer Update_Eval_Scheme_Indicator(Map<String,Object> obj);

    /**
     * 删除
     * @param id
     * @return
     */
    public Integer Delete_Eval_Scheme_Indicator(@Param("id") String id);

    /**
     * 添加选中的指标
     * @param indid
     * @param schemeid
     * @return
     */
    public Integer Insert_Choose_Eval_Scheme_Indicator(@Param("indid") String indid,@Param("schemeid") String schemeid);

    /**
     * 查询父级指标
     * @param indid
     * @return
     */
    public List<Map<String,Object>> Select_Parent_Eval_Scheme_indList(@Param("indid") String indid);

    /**
     * 查询子级指标
     * @param indid
     * @return
     */
    public List<Map<String,Object>> Select_Children_Eval_Scheme_indList(@Param("indid") String indid);

    /**
     * 查询方案下的站点信息
     * @param obj
     * @return
     */
    public List<Map<String,Object>> Select_eval_scheme_stcd_List(Map<String,Object> obj);

    /**
     * 查询站点的得分信息
     * @param schemeid
     * @param essid
     * @return
     */
    public List<Map<String,Object>> Select_eval_scheme_stcd_score_List(@Param("schemeid") String schemeid,@Param("essid") String essid);

    /**
     * 获取计算得分
     * @param indid
     * @return
     */
    public Map<String,Object> Select_eval_indicator_format_list(@Param("indid") String indid);

    /**
     * 获取父级指标信息
     * @param schemeid
     * @param indid
     * @return
     */
    public Map<String,Object> Seletc_Parent_Ind_Info(@Param("schemeid") String schemeid,@Param("indid") String indid);

    /**
     * 保存数据
     * @param obj
     * @return
     */
    public Integer Insert_Eval_Scheme_Indicator_Score(Map<String,Object> obj);


    /**
     * 删除
     * @param obj
     * @return
     */
    public Integer Delete_Eval_Scheme_Indicator_Score(Map<String,Object> obj);

    /**
     * 存入每个指标的最新数据
     * @param schemeid
     * @return
     */
    public Integer Insert_Lake_Healthy_Info(@Param("schemeid") String schemeid);
    public Integer Insert_Lake_Healthy_Total_Info(@Param("schemeid") String schemeid);

    /**
     * 更新父级指标分数
     * @param schemeid
     * @param parentid
     * @return
     */
    public Integer Update_Parent_Ind_Score(@Param("schemeid") String schemeid,@Param("parentid") String parentid);

    /**
     * 更新方案分数
     * @param schemeid
     * @return
     */
    public Integer Update_Eval_scheme_Score(@Param("schemeid") String schemeid);

    /**
     * 获取二级指标的雷达图列表
     * @param obj
     * @return
     */
    public List<Map<String,Object>> Select_Ind_Radar_Info(Map<String,Object> obj);


}
