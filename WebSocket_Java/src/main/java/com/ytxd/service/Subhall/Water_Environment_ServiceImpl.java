package com.ytxd.service.Subhall;

import cn.hutool.core.date.DateUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.ytxd.common.DataUtils;
import com.ytxd.dao.Subhall.Water_Environment_Mapper;
import com.ytxd.util.DateUtils;
import com.ytxd.util.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * 水环境
 */
@Service
public class Water_Environment_ServiceImpl implements Water_Environment_Service{
    @Resource
    private Water_Environment_Mapper mapper;
    @Value("${service.path}")
    private String servicePath;
    /**
     * 获取水质信息
     *
     * @param obj
     * @return
     */
    @Override
    public List<Map<String, Object>> GetWaterEnvironmentQualityList(Map<String, Object> obj) {
        DataUtils.Padding(obj);
        /**
         * 默认查询水质信息
         */
        obj.put("sttp","03,15");
        if(StringUtils.isBlank(DataUtils.getMapKeyValue(obj,"starttime"))){
            obj.put("starttime", "2023-12-20 15:29:33");
        }
        List<Map<String, Object>> data = mapper.Select_Water_Environment_Quality_List(obj);
        if(data != null){
            List<Map<String, Object>> params = mapper.Select_CodeItem_List();
            data.stream().forEach(item -> {
                List<Map<String, Object>> paramsList = new LinkedList<>();
                params.stream().forEach(param ->{
                    Map<String, Object> paramMap = new HashMap<>();
                    /**
                     * 插入水质数据
                     */
                    paramMap.put("label",param.get("description"));
                    paramMap.put("labelcode",param.get("code"));
                    paramMap.put("label_value",DataUtils.getMapKeyValue(item,DataUtils.getMapKeyValue(param,"code")));
                    paramMap.put("orderid",param.get("orderid"));
                    paramMap.put("warning_value",param.get("shortname"));
                    paramsList.add(paramMap);
                });
                item.put("params",paramsList);
            });

        }
        return data;
    }

    /**
     * 获取站点信息介绍
     *
     * @param obj
     * @return
     */
    @Override
    public PageInfo GetSiteIntroduce(Map<String, Object> obj) {
        DataUtils.Padding(obj);
        List<Map<String, Object>> data = mapper.Select_Site_Introduce(obj);
        data.stream().forEach(item ->{
            String filepath = DataUtils.getMapKeyValue(item,"filepath");
            if(StringUtils.isNotBlank(filepath)){
                item.put("filepath",filepath.split("\\|"));
            }else {
                item.put("filepath",null);
            }
        });
        PageInfo pageInfo = new PageInfo(data);
        return pageInfo;
    }

    /**
     * 查询卫星云图
     *
     * @param obj
     * @return
     */
    @Override
    public PageInfo GetCloudChart(Map<String, Object> obj) {
        /**
         * 判断参数是否存在
         */
        if(StringUtils.isBlank(DataUtils.getMapKeyValue(obj,"tm"))){
            obj.put("tm", DateUtil.format(new Date(),"yyyy-MM-dd HH:mm:ss"));
        }
        if(StringUtils.isBlank(DataUtils.getMapKeyValue(obj,"hours"))){
            obj.put("hours", 12);
        }
        DataUtils.Padding(obj);
        List<Map<String, Object>> data = mapper.Select_Cloud_Chart(obj);
        PageInfo pageInfo = new PageInfo(data);
        return pageInfo;
    }

    /**
     * 获取水质指标图片
     *
     * @param obj
     * @return
     */
    @Override
    public List<Map<String, Object>> GetQualityImageList(Map<String, Object> obj) {
        if(StringUtils.isBlank(DataUtils.getMapKeyValue(obj,"tm"))){
            obj.put("tm", DateUtil.format(new Date(),"yyyy-MM-dd HH:mm:ss"));
        }
        List<Map<String, Object>> data = mapper.Select_Quality_Image_List(obj);
        data.stream().forEach(item ->{
            String src = DataUtils.getMapKeyValue(item,"src");
            if(StringUtils.isNotBlank(src)){
                src = servicePath + "/" + src;
                src = src.replace("mh//","mh/");
                item.put("src",src);
            }
        });
        return data;
    }
    /**
     * 查询雷达图图
     *
     * @param obj
     * @return
     */
    @Override
    public PageInfo GetRadarCentral(Map<String, Object> obj) {
        /**
         * 判断参数是否存在
         */
        if(StringUtils.isBlank(DataUtils.getMapKeyValue(obj,"tm"))){
            obj.put("tm", DateUtil.format(new Date(),"yyyy-MM-dd HH:mm:ss"));
        }
        if(StringUtils.isBlank(DataUtils.getMapKeyValue(obj,"hours"))){
            obj.put("hours", 12);
        }
        DataUtils.Padding(obj);
        List<Map<String, Object>> data = mapper.Select_Radar_Central(obj);
        PageInfo pageInfo = new PageInfo(data);
        return pageInfo;
    }}
