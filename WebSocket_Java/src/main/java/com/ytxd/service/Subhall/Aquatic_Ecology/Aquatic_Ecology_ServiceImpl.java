package com.ytxd.service.Subhall.Aquatic_Ecology;

import com.github.pagehelper.PageInfo;
import com.ytxd.common.DataUtils;
import com.ytxd.common.exception.RRException;
import com.ytxd.dao.Subhall.Aquatic_Ecology.Aquatic_Ecology_Mapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * 水生态
 */
@Service
public class Aquatic_Ecology_ServiceImpl implements Aquatic_Ecology_Service {
    @Resource
    private Aquatic_Ecology_Mapper mapper;

    /**
     * 查询最新的dna站点数据
     *
     * @param obj
     * @return
     */
    @Override
    public PageInfo GetDNAInfoList(Map<String, Object> obj) throws Exception {
        DataUtils.Padding(obj);
        /**
         * 10: DNA 站点
         */
        obj.put("sttp","10");
        obj.put("stcd","MHDNA01");
        List<Map<String,Object>> data = mapper.Select_DNA_Info_List(obj);
        data.stream().forEach(item ->{
            double lgtd = DataUtils.getMapDoubleValue(item,"lgtd");
            double lttd = DataUtils.getMapDoubleValue(item,"lttd");
            item.put("position",new double[]{lgtd,lttd});
        });
        PageInfo pageInfo = new PageInfo(data);
        return pageInfo;
    }

    /**
     * 获取dna站点数据list
     *
     * @param obj
     * @return
     */
    @Override
    public PageInfo GetDNARList(Map<String,Object> obj) throws Exception {
        DataUtils.Padding(obj);
        /**
         * 判断stcd 是否存在
         */
        if(StringUtils.isBlank(DataUtils.getMapKeyValue(obj,"stcd"))){
//            throw new RRException("站码不能为空！");
            obj.put("stcd","MHDNA01");
        }
        obj.remove("starttime");
        obj.remove("endtime");
        List<Map<String,Object>> data = mapper.Select_DNA_R_List(obj);
        PageInfo pageInfo = new PageInfo(data);
        return pageInfo;
    }

    /**
     * 获取流量站点信息
     *
     * @param obj
     * @return
     * @throws Exception
     */
    @Override
    public PageInfo GetFlowSiteList(Map<String, Object> obj) throws Exception {
        DataUtils.Padding(obj);
        /**
         * 流量站点
         * 08： 水文站
         * 02： 流量站点
         */
//        obj.put("stcd","MHZM01,MHZM02");
//        obj.put("sttp","02,08");
        obj.put("sttp","02");
        obj.put("flag","01");
        List<Map<String,Object>> data = mapper.Select_Flow_Site_List(obj);
        /**
         * 经纬度转化
         */
        data.stream().forEach(item ->{
            double lgtd = DataUtils.getMapDoubleValue(item,"lgtd");
            double lttd = DataUtils.getMapDoubleValue(item,"lttd");
            item.put("position",new double[]{lgtd,lttd});
            /**
             * 判断是否超过警戒水位
             */
            double z = DataUtils.getMapDoubleValue(item,"z");
            double alertstage = DataUtils.getMapDoubleValue(item,"alertstage");
            if( alertstage > 0 && z >0 && alertstage < z){
                item.put("warning",true);
            }else {
                item.put("warning",false);
            }
            item.put("sttp","02");
            item.put("sttpname","流量站");
        });
        PageInfo pageInfo = new PageInfo(data);
        return pageInfo;
    }
    private final static LinkedHashMap<String,Object> infoMap = new LinkedHashMap<>();
    {
        infoMap.put("phytoplankton","浮游植物");
        infoMap.put("zooplankton","浮游动物");
        infoMap.put("mcd","底栖动物");
        infoMap.put("fish","鱼类");
        infoMap.put("aquatic_bird","鸟类");
    }
    /**
     * 获取dna站点想详细信息
     *
     * @param obj
     * @return
     * @throws Exception
     */
    @Override
    public List<Map<String, Object>> GetDNAPronInfo(Map<String, Object> obj) throws Exception {
        String stcd = DataUtils.getMapKeyValue(obj,"id");
        if(StringUtils.isBlank(stcd)){
            throw new RRException("站码不能为空！");
        }
        Map<String, Object> dnaMap = mapper.Select_DNA_Pron_Info(stcd);
        List<Map<String, Object>> data = new ArrayList<>();
        for(String key:infoMap.keySet()){
            Map<String, Object> dataMap = new HashMap<>();
            dataMap.put("kind",infoMap.get(key));
            if(dnaMap != null){
                dataMap.put("quantity",dnaMap.get(key));
                dataMap.put("advantage_category",dnaMap.get(key+"_kind"));
            }else {
                dataMap.put("quantity","");
                dataMap.put("advantage_category","");
            }
            data.add(dataMap);
        }
        return data;
    }
}
