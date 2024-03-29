package com.ytxd.service.RiverBuild;

import com.github.pagehelper.PageInfo;

import java.util.Map;

/**
 * @Classname RiverBuild_Service
 * @Author TY
 * @Date 2023/8/17 15:09
 * @Description TODO 涉河建设项目维护
 */
public interface RiverBuild_Service {
    /**
     *
     * @Desription TODO 查询涉河建设项目列表
     * @param obj
     * @return com.github.pagehelper.PageInfo
     * @date 2023/8/17 15:24
     * @Auther TY
     */
    public PageInfo getRiverBuildList(Map<String,Object> obj) throws Exception;
    /**
     *
     * @Desription TODO 查询涉河建设项目的文件列表
     * @param obj
     * @return com.github.pagehelper.PageInfo
     * @date 2023/8/17 15:26
     * @Auther TY
     */
    public PageInfo getRiverBuildFileList(Map<String,Object> obj) throws Exception;
    /**
     *
     * @Desription TODO 保存涉河建设项目的文件列表
     * @param obj
     * @return java.lang.String
     * @date 2023/8/17 15:26
     * @Auther TY
     */
    public String SaveRiverBuild(Map<String,Object> obj) throws Exception;
    /**
     *
     * @Desription TODO 删除涉河建设项目
     * @param obj
     * @return java.lang.String
     * @date 2023/8/17 15:26
     * @Auther TY
     */
    public String DeleteRiverBuild(Map<String,Object> obj) throws Exception;

   /**
    *
    * @Desription TODO 删除涉河建设项目的文件
    * @param obj
    * @return java.lang.String
    * @date 2023/8/17 15:27
    * @Auther TY
    */
    public String DeleteRiverBuildFileById(Map<String,Object> obj) throws Exception;
}
