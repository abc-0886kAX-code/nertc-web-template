package com.ytxd.dao.Activity_Entry;

import org.apache.ibatis.annotations.MapKey;

import java.util.List;
import java.util.Map;

/**
 * @Classname Activity_Entry_Mapper
 * @Author TY
 * @Date 2023/9/8 10:14
 * @Description TODO 科普活动报名信息表
 */
public interface Activity_Entry_Mapper {
    /**
     *
     * @Desription TODO 查询
     * @param obj
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     * @date 2023/9/8 10:26
     * @Auther TY
     */
    @MapKey("entryid")
    public List<Map<String,Object>> Select_Activity_Entry_List(Map<String,Object> obj);
    /**
     *
     * @Desription TODO 新增
     * @param obj
     * @return java.lang.Integer
     * @date 2023/9/8 10:37
     * @Auther TY
     */
    public Integer Insert_Activity_Entry(Map<String,Object> obj);
    /**
     *
     * @Desription TODO 修改
     * @param obj
     * @return java.lang.Integer
     * @date 2023/9/8 10:37
     * @Auther TY
     */
    public Integer Update_Activity_Entry(Map<String,Object> obj);
    /**
     *
     * @Desription TODO 删除
     * @param obj
     * @return java.lang.Integer
     * @date 2023/9/8 10:37
     * @Auther TY
     */
    public Integer Delete_Activity_Entry(Map<String,Object> obj);
    /**
     *
     * @Desription TODO 检查该手机号是否已经报过名
     * @param obj
     * @return java.lang.Integer
     * @date 2023/9/8 10:40
     * @Auther TY
     */
    public Integer Check_TelPhone_Exists(Map<String,Object> obj);
}
