package com.ytxd.service.GeoJson;

import com.alibaba.fastjson.JSONObject;
import com.ytxd.dao.GeoJson.GeoJsonMapper;
import com.ytxd.util.GeoJson.GeoJSONUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.net.URISyntaxException;
import java.util.*;

/**
 *
 * @author CYC
 */
@Service
public class GeoJsonServiceImpl implements GeoJsonService{

    /**
     * 定义不存在的值
     */
    final double _undefData = -9999.0;
    @Resource
    private GeoJsonMapper mapper;

    /**
     *气象雨情-天气预报
     */
    @Override
    public List<Map<String,Object>> getWeatherForecast(Map<String, Object> obj) throws Exception {
        return mapper.getWeatherForecastList(obj);

    }


    /**
     *
     *气象雨情-预报报警-降雨等值面图
     */
    @Override
    public JSONObject getRealRainGeoJson(Map<String, Object> obj) throws Exception {
        /**
         * 判断开始和结束时间
         */
        obj.put("starttime", "2023-11-09 09:40:00");
        obj.put("endtime", "2023-11-15 09:40:00");
//        if (StringUtils.isBlank(DataUtils.getMapKeyValue(params, "starttime"))) {
//            params.put("starttime", DataUtils.getRainStartTime());
//        }
//        if (StringUtils.isBlank(DataUtils.getMapKeyValue(params, "endtime"))) {
//            params.put("endtime", DataUtils.getRainEndTime());
//        }
        /**
         * 获取实时降雨数据
         */
        List<HashMap<String, Object>> dataList = mapper.getRealDataInfoList(obj);
        if (dataList == null || dataList.size() == 0) {
            return null;
        }
        String path = "";
        try {
            path = this.getClass().getClassLoader().getResource("").toURI().getPath();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        GeoJSONUtil geoJSONUtil = new GeoJSONUtil(dataList);
        geoJSONUtil.setClipPointFile(new File(path+"/json/city.json"));
        return geoJSONUtil.getGeoJson();
    }


}
