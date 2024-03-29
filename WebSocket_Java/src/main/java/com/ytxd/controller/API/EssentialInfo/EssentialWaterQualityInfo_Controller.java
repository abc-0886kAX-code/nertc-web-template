package com.ytxd.controller.API.EssentialInfo;

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
 * 水质相关
 */
@RestController("EssentialWaterQualityInfo_Controller")
@RequestMapping("/EssentialInfo/WaterQuality")
public class EssentialWaterQualityInfo_Controller {
    @Resource
    private CommonService commonService;

    /**
     * 获取各水质站最新的水质信息
     */
    @GetMapping("/GetLatestWaterQualityList")
    public R GetWaterLevelList(HttpServletRequest request) throws Exception{
        MySqlData mySqlData = new MySqlData();
        mySqlData.setSql("select st_wq_r.STCD,st_stbprp_b.stnm,st_wq_r.CODCR,st_wq_r.NH3N,st_wq_r.TP,quality_code.qualityName from st_wq_r " +
                " left join (select code,description as qualityName from sm_codeitem where codeid = 'WA') quality_code on quality_code.code = st_wq_r.WATER_QUALITY" +
                " left join (select stcd,stnm from st_stbprp_b where sttp = '05') st_stbprp_b on st_stbprp_b.stcd = st_wq_r.stcd" +
                " where id in (select MAX(id) from st_wq_r group by stcd)");
        List<HashMap<String,Object>> data = commonService.getDataList(mySqlData);
        return R.ok().put("data", data);
    }

    /**
     * 根据stcd获取水质站详情
     */
    @GetMapping("/GetLatestWaterQualityDetail")
    public R GetLatestWaterQualityDetail(HttpServletRequest request) throws Exception{
        final String stcd = request.getParameter("stcd");
        if(StringUtils.isBlank(stcd)){
            return R.error("stcd不能为空！");
        }
        MySqlData mySqlData = new MySqlData();
        mySqlData.setSql("select st_wq_r.STCD,st_stbprp_b.stnm,st_wq_r.CODCR,st_wq_r.NH3N,st_wq_r.TP,quality_code.qualityName from st_wq_r " +
                " left join (select code,description as qualityName from sm_codeitem where codeid = 'WA') quality_code on quality_code.code = st_wq_r.WATER_QUALITY" +
                " left join (select stcd,stnm from st_stbprp_b where sttp = '05') st_stbprp_b on st_stbprp_b.stcd = st_wq_r.stcd" +
                " where id in (select MAX(id) from st_wq_r where stcd = '"+stcd+"')");
        List<HashMap<String,Object>> data = commonService.getDataList(mySqlData);
        return R.ok().put("data", data);
    }
}
