package com.ytxd.service.API.Section;

import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * 科普知识service
 */
public interface knowledge_Service {
    /**
     * 获取科普知识的类型
     * @return
     * @throws Exception
     */
    public List<Map<String,Object>> GetKnowledgeTypeList() throws Exception;
    /**
     * 获取科普知识列表
     * @param obj
     * @return
     */
    public PageInfo GetKnowledgeList(Map<String,Object> obj);

    /**
     * 生物概览
     * @param obj
     * @return
     */
    List<Map<String,Object>> GetBiologicalList(Map<String,Object> obj);
}
