package com.ytxd.dao.RiverBuild;

import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Classname RiverBuild_Mapper
 * @Author TY
 * @Date 2023/8/17 14:33
 * @Description TODO 涉河建设项目
 */
@Mapper
public interface RiverBuild_Mapper {
    /**
     *
     * @Desription TODO 查询涉河建设项目信息
     * @param obj
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     * @date 2023/8/17 15:03
     * @Auther TY
     */
    public List<Map<String,Object>> Select_RiverBuild_List(Map<String,Object> obj);
    /**
     *
     * @Desription TODO 查询涉河建设项目的附件信息
     * @param obj
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     * @date 2023/8/17 15:04
     * @Auther TY
     */
    @MapKey("id")
    public List<Map<String,Object>> Select_RiverBuild_File_List(Map<String,Object> obj);
    /**
     *
     * @Desription TODO 新增涉河建设项目信息
     * @param obj
     * @return java.lang.Integer
     * @date 2023/8/17 15:05
     * @Auther TY
     */
    public Integer Insert_River_Build(Map<String,Object> obj);
    /**
     *
     * @Desription TODO 修改涉河建设项目信息
     * @param obj
     * @return java.lang.Integer
     * @date 2023/8/17 15:05
     * @Auther TY
     */
    public Integer Update_River_Build(Map<String,Object> obj);
    /**
     *
     * @Desription TODO 删除涉河建设项目
     * @param id
     * @return java.lang.Integer
     * @date 2023/8/17 15:05
     * @Auther TY
     */
    public Integer Delete_River_Build(@Param("id") String id);
    /**
     *
     * @Desription TODO 新增涉河建设项目文件
     * @param obj
     * @return java.lang.Integer
     * @date 2023/8/17 15:06
     * @Auther TY
     */
    public Integer Insert_River_Build_File(Map<String,Object> obj);
    /**
     *
     * @Desription TODO 修改涉河建设项目文件
     * @param obj
     * @return java.lang.Integer
     * @date 2023/8/17 15:06
     * @Auther TY
     */
    public Integer Update_River_Build_File(Map<String,Object> obj);
    /**
     *
     * @Desription TODO 通过id删除涉河建设项目
     * @param id
     * @return java.lang.Integer
     * @date 2023/8/17 15:06
     * @Auther TY
     */
    public Integer Delete_River_Build_FileByID(@Param("id") String id);
    /**
     *
     * @Desription TODO 通过涉河建设项目ID删除文件信息
     * @param buildid
     * @return java.lang.Integer
     * @date 2023/8/17 15:07
     * @Auther TY
     */
    public Integer Delete_River_Build_FileByBuildId(@Param("buildid") String buildid);
}
