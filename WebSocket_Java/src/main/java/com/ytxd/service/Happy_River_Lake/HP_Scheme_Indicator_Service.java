package com.ytxd.service.Happy_River_Lake;

import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 幸福河湖-评价方案指标
 */
public interface HP_Scheme_Indicator_Service {
    /**
     * 查询评价方案指标
     * @param obj
     * @return
     */
    public PageInfo GetHPSchemeIndicatorList(Map<String,Object> obj);

    /**
     * 查询评价方案指标 通过ID
     * @param obj
     * @return
     */
    public Map<String,Object> GetHPSchemeIndicatorListById(Map<String,Object> obj);

    /**
     * 修改指标
     * @param obj
     * @return
     */
    public String UpdateHPSchemeIndicator(Map<String,Object> obj);
    /**
     * 删除
     * @param id
     * @return
     */
    public Integer DeleteHPSchemeIndicator(@Param("id") String id);

    /**
     * 保存选择的指标
     * @param obj
     * @return
     */
    public Integer ChooseHPSchemeIndicatorSave(Map<String,Object> obj);

    /**
     * 批量保存
     * @param obj
     * @return
     */
    public Integer HPSchemeIndicatornBatchSave(Map<String,Object> obj);

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
