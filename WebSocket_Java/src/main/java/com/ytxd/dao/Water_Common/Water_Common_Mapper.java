package com.ytxd.dao.Water_Common;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface Water_Common_Mapper {
    /**
     *
     * @Desription TODO 查询站点信息
     * @param obj
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     * @date 2024/1/29 15:35
     * @Auther TY
     */
    public List<Map<String,Object>> Select_Site_Info_List(Map<String,Object> obj);
    /**
     *
     * @Desription TODO 获取数据
     * @param obj
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     * @date 2024/2/21 18:05
     * @Auther TY
     */
    public List<Map<String,Object>> Select_Site_Data_Info_List(Map<String,Object> obj);



    /**
     * @Description: 河道和水库预警列表
     * @param obj
     * @return: java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     * @Author: ZJW
     * @Date: 2024/3/27 19:59
     */
    List<Map<String , Object>> selectSiteWARNInfoList(Map<String, Object> obj);
}
