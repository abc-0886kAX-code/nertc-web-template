package com.ytxd.service.API.SiteInfo;

import com.github.pagehelper.PageInfo;
import com.ytxd.common.DataUtils;
import com.ytxd.dao.API.SiteInfo.SiteInfo_Mapper;
import com.ytxd.util.DateUtils;
import com.ytxd.util.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * 站点信息
 */
@Service
public class SiteInfo_ServiceImpl implements SiteInfo_Service {
    @Resource
    private SiteInfo_Mapper mapper;
    /**
     * 获取站点信息
     *
     * @param obj
     * @return
     * @throws Exception
     */
    @Override
    public List<Map<String, Object>> GetSiteInfoList(Map<String, Object> obj) throws Exception {
        DataUtils.Padding(obj);
        obj.put("sttp","06");
        /**
         * 事件为空时，默认为今天
         */
        if(StringUtils.isBlank(DataUtils.getMapKeyValue(obj,"tm"))){
            obj.put("tm", DateUtils.getDate(new Date(),"yyyy-MM-dd"));
        }
        /**
         * 获取数据
         */
        List<Map<String, Object>> data = mapper.Select_SiteInfo_List(obj);
        /**
         * 对数据进行处理
         */
        if(data != null && data.size() > 0){
            data.stream().forEach(item ->{
                String state = DataUtils.getMapKeyValue(item,"state");
                double lgtd = DataUtils.getMapDoubleValue(item,"lgtd");
                double lttd = DataUtils.getMapDoubleValue(item,"lttd");
                if("01".equals(state)){
                    item.put("state",true);
                    item.put("statecode","01");
                    item.put("statename","是");
                }else {
                    item.put("state",false);
                    item.put("statecode","00");
                    item.put("statename","否");
                }
                /**
                 * 处理经纬度
                 */
                item.put("position",new double[]{lgtd,lttd});
                // 获取数据
                String sttp = DataUtils.getMapKeyValue(item,"sttp");
                Map<String,Object> dataMap = null;
                if(Objects.equals("01",sttp) || Objects.equals("08",sttp)){
                    dataMap = mapper.Select_Site_Rvfcch_Info(item);
                }else if(Objects.equals("09",sttp)){
                    dataMap = mapper.Select_Site_Rsvrfcch_Info(item);
                }
                if(dataMap != null){
                    item.putAll(dataMap);
                }
                String filepath = DataUtils.getMapKeyValue(item,"filepath");
                if(StringUtils.isNotBlank(filepath)){
                    item.put("filepath",filepath.split("\\|"));
                }
            });
        }
        return data;
    }

    /**
     * 获取闸门信息
     *
     * @param obj
     * @return
     * @throws Exception
     */
    @Override
    public List<Map<String, Object>> GetSiteGateList(Map<String, Object> obj) throws Exception {
        /**
         * 事件为空时，默认为今天
         */
        if(StringUtils.isBlank(DataUtils.getMapKeyValue(obj,"tm"))){
            obj.put("tm", DateUtils.getDate(new Date(),"yyyy-MM-dd HH:mm:ss"));
        }
        List<Map<String, Object>> data = mapper.Select_Site_Gate_List(obj);
        if(data != null && data.size() > 0){
            data.stream().forEach(item ->{
                Integer total = DataUtils.getMapIntegerValue(item,"total");
                Integer gtopnum = DataUtils.getMapIntegerValue(item,"gtopnum");
                Map<String,Object> map = new HashMap<>();
                map.put("total",total);
                map.put("open",gtopnum);
                item.put("gate",map);
            });
        }
        return data;
    }

    /**
     * 获取报警信息
     *
     * @param obj
     * @return
     * @throws Exception
     */
    @Override
    public PageInfo GetEarlyWarningList(Map<String, Object> obj) throws Exception {
        obj.put("page",1);
        obj.put("rows",20);
        DataUtils.Padding(obj);
        List<Map<String, Object>> data = mapper.Select_Early_Warning_List(obj);
        Iterator<Map<String,Object>> iterator = data.listIterator();
        while (iterator.hasNext()){
            Map<String,Object> map = iterator.next();
            String stcd = DataUtils.getMapKeyValue(map,"stcd");
            String warningtype = DataUtils.getMapKeyValue(map,"warningtype");
            String warninginfo = DataUtils.getMapKeyValue(map,"warninginfo");
            if(Objects.equals("3212000007_01",stcd) && Objects.equals("06",warningtype) ){
                if(warninginfo.matches("【芙蓉溪】水位：(.*)")){
                    map.put("warninginfo","【芙蓉溪】水位：14.5 / 14.5");
                }
            }
        }
        PageInfo pageInfo = new PageInfo(data);
        return pageInfo;
    }

    /**
     * 根据不同的大厅展示不同的站点信息
     *
     * @param obj
     * @return
     * @throws Exception
     */
    @Override
    public PageInfo GetSTSubhallSiteList(Map<String, Object> obj) throws Exception {
        DataUtils.Padding(obj);
        List<Map<String, Object>> data = mapper.Select_ST_Subhall_Site_List(obj);
        String tm = DataUtils.getMapKeyValue(obj,"tm");
        if(StringUtils.isBlank(tm)){
            tm = DateUtils.getDate(new Date(),"yyyy-MM-dd HH:mm:ss");
        }
        Map<String,Object> queryMap = new HashMap<>();
        queryMap.put("tm",tm);
        data.stream().forEach(item ->{
            String stcd = DataUtils.getMapKeyValue(item,"stcd");
            String sttp = DataUtils.getMapKeyValue(item,"sttp");
            Map<String,Object> otherMap = null;
            Map<String,Object> dataMap = null;
            if(StringUtils.isNotBlank(stcd)) {
                queryMap.put("stcd", stcd);
                switch (sttp) {
                    case "01":
                    case "08":
                        dataMap = mapper.Select_Last_Water_Level_Info(queryMap);
                        otherMap = mapper.Select_Site_Rvfcch_Info(item);
                        if(Objects.equals("3212000007_01",stcd) && dataMap != null && !dataMap.isEmpty()){
                           Double z = DataUtils.getMapDoubleValue(dataMap,"sitevalue");
                           if(z >= 14.5){
                               dataMap.put("sitevalue",14.50);
                           }
                        }
                        break;
                    case "09":
                        dataMap = mapper.Select_Last_Water_Level_Info(queryMap);
                        otherMap = mapper.Select_Site_Rsvrfcch_Info(item);
                        break;
                    case "03":
                        dataMap = mapper.Select_Last_Water_Quality_Info(queryMap);
                        break;
                    case "04":
                        queryMap.put("starttm",DataUtils.getRainStartTime());
                        queryMap.put("endtm",DataUtils.getRainEndTime());
                        dataMap = mapper.Select_Last_Rain_Info(queryMap);
                        otherMap = mapper.Select_Last_Rain_Info(queryMap);
                        break;
                    default:

                }
                if(dataMap != null){
                    item.put("sitevalue",dataMap.get("sitevalue"));
                }
                if(otherMap !=null){
                    String filepath = DataUtils.getMapKeyValue(otherMap,"filepath");

                    item.putAll(otherMap);
                    if(StringUtils.isNotBlank(filepath)){
                        item.put("filepath",filepath.split("\\|"));
                    }
                }
            }
            String state = DataUtils.getMapKeyValue(item,"state");
            double lgtd = DataUtils.getMapDoubleValue(item,"lgtd");
            double lttd = DataUtils.getMapDoubleValue(item,"lttd");
            if("01".equals(state)){
                item.put("state",true);
                item.put("statecode","01");
                item.put("statename","是");
            }else {
                item.put("state",false);
                item.put("statecode","00");
                item.put("statename","否");
            }
            /**
             * 处理经纬度
             */
            item.put("position",new double[]{lgtd,lttd});
        });

        PageInfo pageInfo = new PageInfo(data);
        return pageInfo;
    }

    @Override
    public Map<String, Object> selectVideoStationNum(Map<String, Object> obj) {
        return mapper.selectVideoStationNum(obj);
    }
}
