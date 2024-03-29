package com.ytxd.service.Subhall.Water_Culture;

import com.github.pagehelper.PageInfo;
import com.ytxd.common.DataUtils;
import com.ytxd.common.exception.RRException;
import com.ytxd.dao.Subhall.Water_Culture.Water_Culture_Mapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 知识库
 * @author CYC
 */
@Service
public class Water_Culture_ServiceImpl implements Water_Culture_Service{
    @Resource
    private Water_Culture_Mapper mapper;

    private static Map<String,Object> type02Map = new HashMap<>();
    private static Map<String,Object> type03Map = new HashMap<>();
    {
        type02Map.put("01","生物多样性");
        type02Map.put("02","科普百科");
        type02Map.put("03","国内外湿地库");
        type02Map.put("04","河湖长制基础知识");
        type02Map.put("05","河湖健康状态及面临主要问题");
        type02Map.put("06","河湖健康问题的原因");
        type02Map.put("07","工程与非工程对策");

        type03Map.put("01","湿地植物");
        type03Map.put("02","湿地鱼类");
        type03Map.put("03","湿地鸟类");
        type03Map.put("04","一河一策");
        type03Map.put("05","健康评价主要指标");
    }

    /**
     * 查询知识库信息
     */
    @Override
    public PageInfo getZskmhList(Map<String, Object> obj) throws Exception {
        DataUtils.Padding(obj);

//        String type = DataUtils.getMapKeyValue(obj,"type");
//        String type01 = DataUtils.getMapKeyValue(obj,"type01");
//        if(typeMap.containsKey(type)){
//            obj.put("type",typeMap.get(type));
//        }
//        if(type01Map.containsKey(type01)){
//            obj.put("type01",type01Map.get(type01));
//        }

        List<Map<String,Object>> data = mapper.Select_ZskmhList(obj);
        return new PageInfo(data);
    }

    /**
     * 查询水文化信息
     * @param obj
     * @return
     * @throws Exception
     */
    @Override
    public PageInfo GetWaterCulture(Map<String, Object> obj) throws Exception {
        DataUtils.Padding(obj);
        List<Map<String,Object>> data = mapper.Select_Water_Culture(obj);
        PageInfo pageInfo = new PageInfo(data);
        return pageInfo;
    }

    /**
     * 通过id查询水文化信息
     *
     * @param obj
     * @return
     * @throws Exception
     */
    @Override
    public Map<String,Object> GetWaterCultureByID(Map<String, Object> obj) throws Exception {
        if(StringUtils.isBlank(DataUtils.getMapKeyValue(obj,"id"))){
            throw new RRException("id不能为空！！");
        }
        List<Map<String,Object>> data = mapper.Select_Water_Culture(obj);
        if(data != null && data.size() > 0){
            return data.get(0);
        }
        return null;
    }

    /**
     *水利基础知识
     */
    @Override
    public Map<String,Object> getWaterDay(Map<String, Object> obj) throws Exception {

        List<Map<String,Object>> data = mapper.Select_Water_Day(obj);

        Map<String,Object> result = new HashMap<>();

        result.put("data", data);

        return result;
    }
}
