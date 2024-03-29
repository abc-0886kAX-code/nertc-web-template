package com.ytxd.controller.API.Section;

import com.github.pagehelper.PageInfo;
import com.ytxd.common.DataUtils;
import com.ytxd.common.util.R;
import com.ytxd.service.API.Section.knowledge_Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController("knowledge_Controller")
@RequestMapping("/Section/knowledge")
public class knowledge_Controller {
    @Resource
    private knowledge_Service service;

    /**
     * 获取科普知识的类型
     * @param request
     * @return
     * @throws Exception
     */
    @GetMapping("/GetKnowledgeTypeList")
    public R GetKnowledgeTypeList(HttpServletRequest request) throws Exception{
        return R.ok().put("data",service.GetKnowledgeTypeList());
    }

    /**
     * 获取科普知识列表
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("/GetKnowledgeList")
    public R GetKnowledgeList(HttpServletRequest request) throws Exception{
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        PageInfo pageInfo = service.GetKnowledgeList(obj);
        return R.ok().put("data",pageInfo.getList()).put("total",pageInfo.getTotal());
    }

    /**
     * 获取生物概览统计
     * @param request
     * @return
     * @throws Exception
     */
    @GetMapping("/GetBiologicalList")
    public R GetBiologicalList(HttpServletRequest request) throws Exception{
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        return R.ok().put("data",service.GetBiologicalList(obj));
    }
}
