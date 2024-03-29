package com.ytxd.service.Activity_Entry;

import java.util.List;
import java.util.Map;

/**
 * @Classname Activity_Entry_Service
 * @Author TY
 * @Date 2023/9/8 10:39
 * @Description TODO 活动报名信息表
 */
public interface Activity_Entry_Service {
    /**
     *
     * @Desription TODO 获取报名列表
     * @param obj
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     * @date 2023/9/8 10:40
     * @Auther TY
     */
    public List<Map<String,Object>> getActivityEntryList(Map<String,Object> obj) throws Exception;
    /**
     *
     * @Desription TODO 报错报名信息
     * @param obj
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     * @date 2023/9/8 10:40
     * @Auther TY
     */
    public String SaveActivityEntry(Map<String,Object> obj) throws Exception;
    /**
     *
     * @Desription TODO 取消报名信息
     * @param obj
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     * @date 2023/9/8 10:41
     * @Auther TY
     */
    public Integer DeleteActivityEntry(Map<String,Object> obj) throws Exception;
}
