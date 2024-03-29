package com.ytxd.service.Happy_River_Lake;

import com.github.pagehelper.PageInfo;

import java.util.Map;

/**
 * 水安全-指标库管理
 */
public interface HP_Indicator_Library_Service {
    /**
     * 查询指标列表
     * @param obj
     * @return
     */
    public PageInfo GetHPIndicatorLibraryList(Map<String,Object> obj) throws Exception;

    /**
     * 通过ID查询子表列表
     * @param obj
     * @return
     */
    public Map<String,Object> GetHPIndicatorLibraryListByID(Map<String,Object> obj) throws Exception;

    /**
     * 保存
     * @param obj
     * @return
     */
    public String HPIndicatorLibrarySave(Map<String,Object> obj) throws Exception;

    /**
     * 删除
     * @param obj
     * @return
     */
    public Integer DeleteHPIndicatorLibrary(Map<String,Object> obj) throws Exception;
}
