package com.ytxd.service.Happy_River_Lake;

import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * 幸福河湖-评价方案
 */
@Mapper
public interface HP_Scheme_Service {
    /**
     * 查询评价方案
     * @param obj
     * @return
     */
    public PageInfo GetHPSchemeList(Map<String,Object> obj) throws Exception;

    /**
     * 通过ID查询
     * @param obj
     * @return
     */
    public Map<String,Object> GetHPSchemeListByID(Map<String,Object> obj)throws Exception;

    /**
     * 保存
     * @param obj
     * @return
     */
    public String HPSchemeSave(Map<String,Object> obj)throws Exception;

    /**
     * 删除
     * @param schemeid
     * @return
     */
    public Integer Delete_HP_Scheme(@Param("schemeid") String schemeid)throws Exception;
}
