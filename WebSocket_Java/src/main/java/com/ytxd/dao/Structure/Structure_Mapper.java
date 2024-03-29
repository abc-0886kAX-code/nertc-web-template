package com.ytxd.dao.Structure;

import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Classname Structure_Mapper
 * @Author TY
 * @Date 2023/8/17 10:35
 * @Description TODO 组织架构信息
 */
@Mapper
public interface Structure_Mapper {
    /**
     *
     * @Desription TODO 查询组织架构信息
     * @param obj
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     * @date 2023/8/17 10:57
     * @Auther TY
     */
    @MapKey("structureid")
    public List<Map<String,Object>> Select_RL_Structure_Info(Map<String,Object> obj);
    /**
     *
     * @Desription TODO 新增组织架构人员
     * @param obj
     * @return java.lang.Integer
     * @date 2023/8/17 10:57
     * @Auther TY
     */
    public Integer Insert_Structure(Map<String,Object> obj);
    /**
     *
     * @Desription TODO 修改组织架构人员
     * @param obj
     * @return java.lang.Integer
     * @date 2023/8/17 10:58
     * @Auther TY
     */
    public Integer Update_Structure(Map<String,Object> obj);
    /**
     *
     * @Desription TODO 删除组织架构人员
     * @param structureid
     * @return java.lang.Integer
     * @date 2023/8/17 10:59
     * @Auther TY
     */
    public Integer Delete_Structure(@Param("structureid") String structureid);
}
