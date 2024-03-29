package com.ytxd.dao.API.Section;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface knowledge_Mapper {
    /**
     * 获取科普知识列表
     * @param obj
     * @return
     */
    List<Map<String,Object>> GetKnowledgeList(Map<String,Object> obj);

    /**
     * 生物概览
     * @return
     */
    List<Map<String,Object>> GetBiologicalList();

}
