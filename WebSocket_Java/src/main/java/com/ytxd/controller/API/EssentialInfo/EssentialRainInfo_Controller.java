package com.ytxd.controller.API.EssentialInfo;

import com.ytxd.common.DataUtils;
import com.ytxd.common.exception.RRException;
import com.ytxd.common.util.R;
import com.ytxd.model.MySqlData;
import com.ytxd.service.CommonService;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.OptionalInt;

/**
 * 雨量站相关
 */
@RestController("EssentialRainInfo_Controller")
@RequestMapping("/EssentialInfo/Rain")
public class EssentialRainInfo_Controller {
    @Resource
    private CommonService commonService;

    /**
     * 根据时间和站码获取雨量站点的雨量数据
     * @param request
     * @return
     * @throws Exception
     */
    @GetMapping("/GetRainListByStcdONDay")
    public R GetRainListByStcdONDay(HttpServletRequest request) throws Exception{
        String stcd = request.getParameter("stcd");
        if(StringUtils.isBlank(stcd)){
            throw new RRException("stcd不能为空！");
        }
        String keytime = request.getParameter("keytime");
        if(StringUtils.isBlank(keytime)){
            keytime = DataUtils.getDate("yyyy-MM-dd HH:mm:ss");
        }
        MySqlData mySqlData = new MySqlData();
        mySqlData.setSql("select stcd,tm,round(p,2) p from st_pptn_r where stcd = ");
        mySqlData.setSqlValue(stcd);
        mySqlData.setSql(" and tm between date_format('"+keytime+"','%Y-%m-%d 00:00:00') and date_format('"+keytime+"','%Y-%m-%d 23:59:59')");
        mySqlData.setSql(" order by tm asc");
        List<HashMap<String,Object>> data = commonService.getDataList(mySqlData);
        return R.ok().put("data",data);
    }

    /**
     * 获取历史降雨的天数
     * @param request
     * @return
     * @throws Exception
     */
    @GetMapping("/GetHistoryRainP")
    public R GetHistoryRainP(HttpServletRequest request) throws Exception{
        MySqlData mySqlData = new MySqlData();
        mySqlData.setSql("select tm,round(avg(p),2) p from (select stcd ,date_format(tm,'%Y-%m-%d') tm,round(sum(p),2) p from st_pptn_r where p > 0 group by date_format(tm,'%Y-%m-%d'),stcd" +
                ") st_pptn_r group by tm order by tm asc limit 0,7");
        return R.ok().put("data",commonService.getDataList(mySqlData));
    }

    /**
     * 获取雨情数据
     * @param request
     * @return
     * @throws Exception
     */
    @GetMapping("/GetToDayRainPList")
    public R GetToDayRainPList(HttpServletRequest request) throws Exception{
        Map<String,Object> typeMap = new HashMap<>();
        typeMap.put("1","无降雨0mm");
        typeMap.put("2","小到中雨1-25mm");
        typeMap.put("3","大-暴雨25-100mm");
        typeMap.put("4","大暴雨100-250mm");
        typeMap.put("5","特大暴雨250mm以上");
        String keytime = request.getParameter("keytime");
        if(StringUtils.isBlank(keytime)){
            keytime = DataUtils.getDate("yyyy-MM-dd HH:mm:ss");
        }
        MySqlData mySqlData = new MySqlData();
        mySqlData.setSql("select levels type,count(stcd) typecount from ( select stcd,case when p > 250 then '5' " +
                "when p > 100  and p <= 250 then '4' when p > 25  and p <= 100 then '3' when p > 0 " +
                "and p <= 25 then '2' else '1' end levels from ( select stcd, sum( ifnull( p, 0 )) p from st_pptn_r where tm between date_format('"+keytime+"','%Y-%m-%d 00:00:00') " +
                " and date_format('"+keytime+"','%Y-%m-%d 23:59:59')" +
                "group by stcd  ) st_pptn_r) st_pptn_r group by levels");
        List<HashMap<String,Object>> data = commonService.getDataList(mySqlData);
        Integer total = 0;
        if (data != null && data.size() > 0){
            data.stream().forEach(Map -> {
                Map.put("typename",typeMap.get(Map.get("type")));
            });
            OptionalInt totalInt = data.stream().mapToInt(Map -> DataUtils.getMapIntegerValue(Map,"typecount")).reduce(Integer::sum);
            total = totalInt.getAsInt();
        }
        Map<String,Object> map = new HashMap<>();
        map.put("total",total);
        map.put("attribute",total);
        return R.ok().put("data",map);
    }

    /**
     * 获取雨量站统计数据
     * @param request
     * @return
     * @throws Exception
     */
    @GetMapping("/GetRainSiteStatistics")
    public R GetRainSiteStatistics(HttpServletRequest request) throws Exception{
        MySqlData mySqlData = new MySqlData();
        mySqlData.setSql("select " +
                "IFNULL((select count(1) from st_stbprp_b where sttp = '01'), 0) sum," +
                "IFNULL((select count(STCD) from st_pptn_r where TM >= ( NOW() - INTERVAL 24 HOUR ) group by STCD HAVING sum(P) < 1), 0) count1," +
                "IFNULL((select count(STCD) from st_pptn_r where TM >= ( NOW() - INTERVAL 24 HOUR ) group by STCD HAVING sum(P) >= 1 and sum(P) < 25), 0) count2," +
                "IFNULL((select count(STCD) from st_pptn_r where TM >= ( NOW() - INTERVAL 24 HOUR ) group by STCD HAVING sum(P) >= 25 and sum(p) < 100), 0) count3," +
                "IFNULL((select count(STCD) from st_pptn_r where TM >= ( NOW() - INTERVAL 24 HOUR ) group by STCD HAVING sum(P) >= 100 and sum(P) <250), 0) count4," +
                "IFNULL((select count(STCD) from st_pptn_r where TM >= ( NOW() - INTERVAL 24 HOUR ) group by STCD HAVING sum(P) >= 250), 0) count5" +
                " from dual");
        final List<HashMap<String, Object>> dataList = commonService.getDataList(mySqlData);
        if(dataList.isEmpty()){
            return R.error();
        }
        return R.ok().put("data", dataList.get(0));
    }
}
