package com.ytxd.service.Subhall.Aquatic_Ecology;

import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.Map;

/**
 * 水生态-评价方案
 */
@Mapper
public interface Eval_Scheme_Service {
    /**
     * 查询评价方案
     * @param obj
     * @return
     */
    public PageInfo GetEvalSchemeList(Map<String,Object> obj) throws Exception;

    /**
     * 通过ID查询
     * @param obj
     * @return
     */
    public Map<String,Object> GetEvalSchemeListByID(Map<String,Object> obj)throws Exception;

    /**
     * 保存
     * @param obj
     * @return
     */
    public String EvalSchemeSave(Map<String,Object> obj)throws Exception;

    /**
     * 删除
     * @param schemeid
     * @return
     */
    public Integer Delete_Eval_Scheme(@Param("schemeid") String schemeid)throws Exception;
}
