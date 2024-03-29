package com.ytxd.dao.meteorology;

import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @Classname Meteorology_Mapper
 * @Author TY
 * @Date 2023/10/12 15:23
 * @Description TODO
 */
@Mapper
public interface Meteorology_Mapper {
    /**
     *
     * @Desription TODO 查询站点基本信息
     * @param map
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     * @date 2023/10/16 10:05
     * @Auther TY
     */
    @MapKey("stcd")
    public List<Map<String,Object>> Select_Meteorology_Info(Map<String,Object> map);
    /**
     *
     * @Desription TODO 获取气象数据
     * @param map
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     * @date 2023/10/12 16:16
     * @Auther TY
     */
    @MapKey("stcd,tm")
    public List<Map<String,Object>> Select_meteorology_List(Map<String,Object> map);
    /**
     *
     * @Desription TODO 获取
     * @param map
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     * @date 2023/10/12 16:16
     * @Auther TY
     */
    @MapKey("stcd,tm")
    public List<Map<String,Object>> Select_Cod_List(Map<String,Object> map);

}
