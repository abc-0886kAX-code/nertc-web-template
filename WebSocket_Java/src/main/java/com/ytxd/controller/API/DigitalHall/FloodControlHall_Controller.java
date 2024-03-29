package com.ytxd.controller.API.DigitalHall;

import com.ytxd.common.DataUtils;
import com.ytxd.common.util.R;
import com.ytxd.model.MySqlData;
import com.ytxd.service.API.DigitalHall.FloodControlHall_Service;
import com.ytxd.service.CommonService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 防洪大厅接口
 */
@RestController("FloodControlHall_Controller")
@RequestMapping("/FloodControlHall")
public class FloodControlHall_Controller {

    @Resource
    private CommonService commonService;

    @Resource
    private FloodControlHall_Service service;
    /**
     * 水位站信息
     */
    @GetMapping("/GetWaterLevelStation")
    public R GetWaterLevelStation(HttpServletRequest request) throws Exception{
        MySqlData mySqlData = new MySqlData();
        mySqlData.setSql("select (select COUNT(1) from st_stbprp_b where sttp = '03') as sum," +
                "(select count(1) from st_rsvr_r left join st_rvfcch_b ON st_rvfcch_b.stcd = st_rsvr_r.stcd where st_rsvr_r.id in (select max(id) from st_rsvr_r group by STCD)  and st_rsvr_r.Z > st_rvfcch_b.WRZ) as overalarm," +
                "(select count(1) from st_rsvr_r left join st_rvfcch_b ON st_rvfcch_b.stcd = st_rsvr_r.stcd where st_rsvr_r.id in (select max(id) from st_rsvr_r group by STCD)  and st_rsvr_r.Z > st_rvfcch_b.SFZ) as overflood," +
                "(select count(1) from st_rsvr_r left join st_rvfcch_b ON st_rvfcch_b.stcd = st_rsvr_r.stcd where st_rsvr_r.id in (select max(id) from st_rsvr_r group by STCD)  and st_rsvr_r.Z > st_rvfcch_b.GRZ) as overprotect," +
                "(select count(1) from st_rsvr_r left join st_rvfcch_b ON st_rvfcch_b.stcd = st_rsvr_r.stcd where st_rsvr_r.id in (select max(id) from st_rsvr_r group by STCD)  and st_rsvr_r.Z > st_rvfcch_b.OBHTZ) as overhistory from dual ");
        List<HashMap<String,Object>> data = commonService.getDataList(mySqlData);
        if(data.isEmpty()){
            return R.ok().put("data", null);
        }
        return R.ok().put("data", data.get(0));
    }

    /**
     * 蓄量状态接口 展示水库的库容状态
     */
    @GetMapping("/StorageState")
    public R GetEpnList(HttpServletRequest request) throws Exception {
//        MySqlData mySqlData = new MySqlData();
//        mySqlData.setSql("select st_stbprp_b.stcd,case when st_stbprp_b.stcd ='3212000007_01' then '芙蓉溪' else st_stbprp_b.stnm end stnm,st_rsvrfcch_b.ttcp,st_rsvrfcch_b.fldcp,st_rsvr_r.TM," +
//                "ROUND(ifnull(st_rsvr_r.W,0)/ifnull(st_rsvrfcch_b.ttcp,1)*100,1) as proportion from st_stbprp_b" +
//                " left join st_rsvrfcch_b  on st_stbprp_b.stcd = st_rsvrfcch_b.stcd" +
//                " left join (select stcd,TM,W from st_rsvr_r where id in (select MAX(id) from st_rsvr_r GROUP BY STCD )) st_rsvr_r ON st_rsvr_r.stcd = st_stbprp_b.stcd" +
//                " where 1=1 and st_stbprp_b.stcd in ('3212000007_01','62914324','62909904','62909894','62909914','62909018') order by -st_stbprp_b.orderid desc");
//        List<HashMap<String,Object>> data = commonService.getDataList(mySqlData);
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        return R.ok().put("data",service.GetStorageStateList(obj));
    }

    /**
     * 获取预报降雨信息
     * @param request
     * @return
     * @throws Exception
     */
    @GetMapping("/GetPredictionRainList")
    public R GetPredictionRainList(HttpServletRequest request) throws Exception{
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        return R.ok().put("data",service.GetPredictionRainList(obj));
    }
}
