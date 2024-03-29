package com.ytxd.service.Subhall.Aquatic_Ecology;

import com.github.pagehelper.PageInfo;
import java.util.Map;

/**
 * 水安全-指标库管理
 */
public interface Eval_Indicator_Library_Service {
    /**
     * 查询指标列表
     * @param obj
     * @return
     */
    public PageInfo GetEvalIndicatorLibraryList(Map<String,Object> obj) throws Exception;

    /**
     * 通过ID查询子表列表
     * @param obj
     * @return
     */
    public Map<String,Object> GetEvalIndicatorLibraryListByID(Map<String,Object> obj) throws Exception;

    /**
     * 保存
     * @param obj
     * @return
     */
    public String EvalIndicatorLibrarySave(Map<String,Object> obj) throws Exception;

    /**
     * 删除
     * @param obj
     * @return
     */
    public Integer DeleteEvalIndicatorLibrary(Map<String,Object> obj) throws Exception;
}
