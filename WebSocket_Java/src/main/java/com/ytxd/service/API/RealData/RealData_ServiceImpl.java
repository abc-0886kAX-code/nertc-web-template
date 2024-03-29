package com.ytxd.service.API.RealData;

import com.ytxd.common.DataUtils;
import com.ytxd.common.exception.RRException;
import com.ytxd.dao.API.RealData.RealData_Mapper;
import com.ytxd.model.MySqlData;
import com.ytxd.service.CommonService;
import com.ytxd.util.DateUtils;
import com.ytxd.util.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RealData_ServiceImpl implements RealData_Service{
    @Resource
    private RealData_Mapper mapper;

    @Resource
    private CommonService commonService;

    /**
     * 获取实时相关数据（1h 6h 24h）
     *
     * @param obj
     * @return
     */
    @Override
    public List<Map<String, Object>> GetRealRainListByTm(Map<String, Object> obj) throws Exception {
        /**
         * 判断条件
         * tm默认当前时间
         */
        if(StringUtil.isEmpty(DataUtils.getMapKeyValue(obj,"tm"))){
            obj.put("tm",DataUtils.getDate("yyyy-MM-dd HH:mm:ss"));
        }
        /**
         * 筛入默认值
         */
        obj.put("typelist", Arrays.asList(1,6,24));
        /**
         * 查询数据
         */
        List<Map<String,Object>> data = mapper.GetRealRainListByTm(obj);
        /**
         * 获取最新水位数据
         * 默认一个站点的数据
         */
        obj.put("stcd","12345678");
        final Double z = mapper.GetRsvrLatestZ(obj);
        if(data != null && data.size() > 0){
            data.stream().forEach(Map -> {
                    Map.put("waterlevel",z);
            });
        }
        return data;
    }

    /**
     * 获取3小时、1天、3天、7天的累计降雨数据
     *
     * @param obj
     * @return
     * @throws Exception
     */
    @Override
    public List<Map<String, Object>> GetStcdRainList(Map<String, Object> obj) throws Exception {

        /**
         * 时间判断
         */

        if(StringUtil.isEmpty(DataUtils.getMapKeyValue(obj,"tm"))){
            obj.put("tm", DateUtils.getDate(new Date(),"yyyy-MM-dd HH:mm:ss"));
        }

        /**
         * 只需要芙蓉溪站点的数据
         */
        String stcd = "6072BB17";
        obj.put("stcd",stcd);

        /**
         * 获取数据
         */
        List<Map<String, Object>> list = mapper.Select_Stcd_Rain_list(obj);
        List<Map<String, Object>> data = new ArrayList<>();
        /**
         * 处理数据
         */
//        Map<String,Object>  dataMapinfo = commonService.getMap("st_stbprp_b","stcd",stcd); //id = 4399
        Map<String,Object>  dataMap = new HashMap<>();
        dataMap.put("stcd",stcd);
        dataMap.put("stnm","芙蓉溪");
        dataMap.put("source",list);
        data.add(dataMap);
        return data;
    }

    /**
     * 获取站点的水位、雨量统计数据
     *
     * @param obj
     * @return
     * @throws Exception
     */
    @Override
    public List<Map<String, Object>> GetSiteRWList(Map<String, Object> obj) throws Exception {
        /**
         * 时间判断
         */
        if(StringUtil.isEmpty(DataUtils.getMapKeyValue(obj,"tm"))){
            obj.put("tm", DateUtils.getDate(new Date(),"yyyy-MM-dd HH:mm:ss"));
        }
        obj.put("tm","2023-12-15 23:59:00");
        /**
         * 芙蓉溪东智慧杆
         * 龙兴河
         * 胜天河站点
         */
        obj.put("stcd","40236022,40235960,3212000007_01");
        List<Map<String, Object>> data = mapper.Select_Site_Statistics_List(obj);
        Iterator<Map<String,Object>> iterator = data.listIterator();
        while (iterator.hasNext()){
            Map<String,Object> map = iterator.next();
            String stcd = DataUtils.getMapKeyValue(map,"stcd");
            Double level = DataUtils.getMapDoubleValue(map,"level");
            if(Objects.equals("3212000007_01",stcd) && level >= 14.5 ){
                map.put("level",14.50);
            }
            if(Objects.equals("3212000007_01",stcd)){
                Object water = map.get("water");
                if(water != null){
                    Iterator<Map<String,Object>> waterit = ((List<Map<String,Object>>) water).listIterator();
                    while (waterit.hasNext()){
                        Map<String,Object> watermap = waterit.next();
                        Double waterlevel = DataUtils.getMapDoubleValue(watermap,"water");
                        if(waterlevel >= 14.5){
                            watermap.put("water",14.5);
                        }
                    }
                }
            }
        }
        return data;
    }

    /**
     * 获取水质统计信息
     *
     * @param obj
     * @return
     * @throws Exception
     */
    @Override
    public List<Map<String, Object>> GetWQStatisticsList(Map<String, Object> obj) throws Exception {
        List<Map<String, Object>> list = mapper.Select_WQ_M_List(obj);
        List<Map<String, Object>> lastList = mapper.Select_Last_WQ_D_List(obj);
        List<Map<String, Object>> data = new LinkedList<>();
        if(list != null && list.size() > 0){
            Map<Object,List<Map<String, Object>>> map = list.stream().collect(Collectors.groupingBy(item -> item.get("type")));
            Map<Object,List<Map<String, Object>>> lastMap = lastList.stream().collect(Collectors.groupingBy(item -> item.get("type")));
            for(Object key:map.keySet()){
                Map<String, Object> dataMap = new HashMap<>();
                dataMap.put("type",key);
                dataMap.put("typename",map.get(key).get(0).get("typename"));
                dataMap.put("type_orderid",map.get(key).get(0).get("type_orderid"));
                dataMap.put("echarts",map.get(key));
                dataMap.putAll(lastMap.get(key).get(0));
                data.add(dataMap);
            }
        }
        data = data.stream().sorted(Comparator.comparing(item ->DataUtils.getMapKeyValue(item,"type_orderid"))).collect(Collectors.toList());
        return data;
    }

    /**
     * 获取当月每天的水质信息
     *
     * @param obj
     * @return
     * @throws Exception
     */
    @Override
    public List<Map<String, Object>> GetWQDListByDay(Map<String, Object> obj) throws Exception {
        if(StringUtils.isBlank(DataUtils.getMapKeyValue(obj,"tm"))){
            obj.put("tm",DateUtils.getDate(new Date(),"yyyy-MM"));
        }
        return mapper.Select_WQ_D_List_ByDay(obj);
    }
}
