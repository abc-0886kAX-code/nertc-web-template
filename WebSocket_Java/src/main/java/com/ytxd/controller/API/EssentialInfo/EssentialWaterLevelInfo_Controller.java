package com.ytxd.controller.API.EssentialInfo;

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

/**
 * 水位相关
 */
@RestController("EssentialWaterInfo_Controller")
@RequestMapping("/EssentialInfo/WaterLevel")
public class EssentialWaterLevelInfo_Controller {
    @Resource
    private CommonService commonService;
    /**
     * 所有水位站的信息列表
     */
    @GetMapping("/GetWaterLevelList")
    public R GetWaterLevelList(HttpServletRequest request) throws Exception{
        MySqlData mySqlData = new MySqlData();
        mySqlData.setSql("select r.stcd,b.stnm,r.Z,r.TM," +
                "case when r.Z < lag(r.z) over (partition by stcd order by TM )  then 0 else 1 end  'status'," +
                "case when r.Z >= b.alertstage then 1 else 0 end alertflag,b.alertstage " +
                "from st_rsvr_r r left join st_stbprp_b b on r.stcd = b.stcd");
        List<HashMap<String,Object>> data = commonService.getDataList(mySqlData);
        return R.ok().put("data", data);
    }

    /**
     * 根据stcd获取水位站信息
     */
    @GetMapping("/GetWaterLevelByStcd")
    public R GetWaterLevelByStcd(HttpServletRequest request) throws Exception{
        final String stcd = request.getParameter("stcd");
        if(StringUtils.isBlank(stcd)){
            return R.error("stcd不能为空！");
        }
        MySqlData mySqlData = new MySqlData();
        mySqlData.setSql("select r.stcd,b.stnm,r.Z,r.TM," +
                "case when r.Z < lag(r.z) over (partition by stcd order by TM )  then 0 else 1 end  'status'," +
                "case when r.Z >= b.alertstage then 1 else 0 end alertflag,b.alertstage " +
                "from st_rsvr_r r left join st_stbprp_b b on r.stcd = b.stcd where r.stcd='"+stcd+"'");
        List<HashMap<String,Object>> data = commonService.getDataList(mySqlData);
        return R.ok().put("data", data);
    }
    /**
     * 获取站点近三日的水位变化趋势
     */
    @GetMapping("/GetWaterLevelLatelyDay")
    public R GetWaterLevelLatelyDay(HttpServletRequest request) throws Exception{
        String stcd = request.getParameter("stcd");
        if(StringUtils.isBlank(stcd)){
            throw new RRException("stcd不能为空！");
        }
        MySqlData mySqlData = new MySqlData();
        mySqlData.setSql("SELECT * FROM `st_rsvr_r` where TM > DATE_SUB(now(),INTERVAL 7 DAY) and stcd = '"+ stcd +"'");
        List<HashMap<String,Object>> data = commonService.getDataList(mySqlData);
        return R.ok().put("data", data);
    }

    /**
     * 获取各水位站最新的水位信息
     */
    @GetMapping("/GetLatestWaterLevelList")
    public R GetLatestWaterLevelList(HttpServletRequest request) throws Exception{
        MySqlData mySqlData = new MySqlData();
        mySqlData.setSql("select r.stcd,b.stnm,r.Z,r.TM," +
                "case when r.Z < lag(r.z) over (partition by stcd order by TM )  then 0 else 1 end  'status'," +
                "case when r.Z >= b.alertstage then 1 else 0 end alertflag,b.alertstage " +
                "from st_rsvr_r r left join st_stbprp_b b on r.stcd = b.stcd " +
                "where r.id in (select MAX(id) from st_rsvr_r group by stcd)");
        List<HashMap<String,Object>> data = commonService.getDataList(mySqlData);
        return R.ok().put("data", data);
    }
}
