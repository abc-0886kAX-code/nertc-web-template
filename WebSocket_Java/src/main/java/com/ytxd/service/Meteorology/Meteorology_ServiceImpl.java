package com.ytxd.service.Meteorology;

import com.github.pagehelper.PageInfo;
import com.ytxd.common.DataUtils;
import com.ytxd.dao.meteorology.Meteorology_Mapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Classname Meteorology_ServiceImpl
 * @Author TY
 * @Date 2023/10/12 17:50
 * @Description TODO 气象相关
 */
@Service
public class Meteorology_ServiceImpl implements Meteorology_Service{
    @Resource
    private Meteorology_Mapper mapper;
    /**
     *
     * @Desription TODO 获取站点想基本信息
     * @param map
     * @return com.github.pagehelper.PageInfo
     * @date 2023/10/16 10:06
     * @Auther TY
     */
    @Override
    public PageInfo getMeteorologyInfo(Map<String, Object> map)  throws Exception{
        String starttime = DataUtils.getMapKeyValue(map,"starttime");
        if(StringUtils.isBlank(starttime)){
            map.put("starttime",DataUtils.getDate("yyyy-MM-dd 00:00:00"));
        }
        String endtime = DataUtils.getMapKeyValue(map,"endtime");
        if(StringUtils.isBlank(endtime)){
            map.put("endtime",DataUtils.getDate("yyyy-MM-dd 23:59:59"));
        }
        DataUtils.Padding(map);
        List<Map<String,Object>> data = mapper.Select_Meteorology_Info(map);
        data.stream().forEach(item ->{
            double lgtd = DataUtils.getMapDoubleValue(item,"lgtd");
            double lttd = DataUtils.getMapDoubleValue(item,"lttd");
            item.put("position",new double[]{lgtd,lttd});
        });
        PageInfo pageInfo = new PageInfo(data);
        return pageInfo;
    }

    /**
     *
     * @Desription TODO 获取气象数据
     * @param map
     * @return com.github.pagehelper.PageInfo
     * @date 2023/10/12 17:50
     * @Auther TY
     */
    @Override
    public PageInfo getMeteorologyList(Map<String, Object> map)  throws Exception{
        String starttime = DataUtils.getMapKeyValue(map,"starttime");
        if(StringUtils.isBlank(starttime)){
            map.put("starttime",DataUtils.getDate("yyyy-MM-dd 00:00:00"));
        }
        String endtime = DataUtils.getMapKeyValue(map,"endtime");
        if(StringUtils.isBlank(endtime)){
            map.put("endtime",DataUtils.getDate("yyyy-MM-dd 23:59:59"));
        }
        DataUtils.Padding(map);
        List<Map<String,Object>> data = mapper.Select_meteorology_List(map);
        PageInfo pageInfo = new PageInfo(data);
        return pageInfo;
    }
}
