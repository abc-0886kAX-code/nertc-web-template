package com.ytxd.service.Subhall.Aquatic_Ecology;

import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 水生态-评价方案指标
 */
public interface Eval_Scheme_Indicator_Service {
    /**
     * 查询评价方案指标
     * @param obj
     * @return
     */
    public PageInfo GetEvalSchemeIndicatorList(Map<String,Object> obj);

    /**
     * 查询评价方案指标 通过ID
     * @param obj
     * @return
     */
    public Map<String,Object> GetEvalSchemeIndicatorListById(Map<String,Object> obj);

    /**
     * 保存
     * @param obj
     * @return
     */
    public Integer EvalSchemeIndicatorSave(Map<String,Object> obj);

    /**
     * 删除
     * @param id
     * @return
     */
    public Integer DeleteEvalSchemeIndicator(@Param("id") String id);

    /**
     * 保存选择的指标
     * @param obj
     * @return
     */
    public Integer ChooseEvalSchemeIndicatorSave(Map<String,Object> obj);

    /**
     * 计算结果
     * @param obj
     * @return
     */
    public Double CalculationEvalSchemeIndicator(Map<String,Object> obj);

    /**
     * 查询评价方案，分层
     * @param obj
     * @return
     */
    public PageInfo GetClassificationEvalSchemeIndicatorList(Map<String,Object> obj);

    /**
     * 批量保存
     * @param obj
     * @return
     */
    public Integer EvalSchemeIndicatornBatchSave(Map<String,Object> obj);

    /**
     * 根据方案id和指标id查询子级指标
     * @param obj
     * @return
     */
    public Map<String,Object> GetForIndInfoList(Map<String,Object> obj);

    /**
     * 根据方案id获取二级指标雷达图
     * @param obj
     * @return
     */
    public List<Map<String,Object>> GetIndRadarInfo(Map<String,Object> obj);
}
