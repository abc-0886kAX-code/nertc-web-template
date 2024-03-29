package com.ytxd.service.Structure;

import com.github.pagehelper.PageInfo;

import java.util.Map;

/**
 * @Classname Structure_Service
 * @Author TY
 * @Date 2023/8/17 10:59
 * @Description TODO 组织架构人员维护
 */
public interface Structure_Service {
    /**
     *
     * @Desription TODO 查询组织架构人员信息
     * @param obj
     * @return com.github.pagehelper.PageInfo
     * @date 2023/8/17 11:00
     * @Auther TY
     */
    public PageInfo getRLStructureInfoList(Map<String,Object> obj) throws Exception;
    /**
     *
     * @Desription TODO 新增或修改组织架构人员信息
     * @param obj
     * @return java.lang.String
     * @date 2023/8/17 11:01
     * @Auther TY
     */
    public String SaveStructure(Map<String,Object> obj) throws Exception;
    /**
     *
     * @Desription TODO 删除组织架构人员信息
     * @param obj
     * @return java.lang.Integer
     * @date 2023/8/17 11:02
     * @Auther TY
     */
    public Integer DelStructure(Map<String,Object> obj) throws Exception;
}
