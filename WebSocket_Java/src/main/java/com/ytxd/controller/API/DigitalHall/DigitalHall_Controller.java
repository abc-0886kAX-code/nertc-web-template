package com.ytxd.controller.API.DigitalHall;

import cn.hutool.http.server.HttpServerResponse;
import com.ytxd.common.DataUtils;
import com.ytxd.common.exception.RRException;
import com.ytxd.common.util.R;
import com.ytxd.model.MySqlData;
import com.ytxd.service.API.DigitalHall.DigitalHall_Service;
import com.ytxd.service.CommonService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数字大厅接口
 */
@RestController("DigitalHall_Controller")
@RequestMapping("/DigitalHall")
public class DigitalHall_Controller {
    @Resource
    private DigitalHall_Service service;
    @Resource
    private CommonService commonService;
    @Value("${file.uploadFolder}")
    private String uploadFolder;

    /**
     * 根据时间获取日入园人数、月入园人数和年入园人数
     *
     * @param request
     * @return
     * @throws Exception
     */
    @GetMapping("/GetEpnList")
    public R GetEpnList(HttpServletRequest request) throws Exception {
        Map<String, Object> obj = DataUtils.getParameterMap(request);
        Map<String, Object> data = service.Select_Epn_List(obj);
        return R.ok().put("data", data);
    }

    /**
     * 根据时间获取月度入园人数统计
     *
     * @param request
     * @return
     * @throws Exception
     */
    @GetMapping("/GetEpnStatistics")
    public R GetEpnStatistics(HttpServletRequest request) throws Exception {
        Map<String, Object> obj = DataUtils.getParameterMap(request);
        List<Map<String, Object>> data = service.Select_Epn_Statistics(obj);
        return R.ok().put("data", data);
    }

    /**
     * 获取无人机最新的3条迅游视频
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @GetMapping("/GetUtrVideo")
    public R GetUtrVideo(HttpServletRequest request, HttpServletResponse response) throws Exception {
        MySqlData mySqlData = new MySqlData();
        mySqlData.setTableName("st_utr_v");
        mySqlData.setViewName("add");
        mySqlData.setSort("tm");
        mySqlData.setOrder("desc");
        mySqlData.setPage(1);
        mySqlData.setRows(3);
        List<HashMap<String, Object>> data = commonService.getDataList(mySqlData);
        return R.ok().put("data", data);
    }

    /**
     * 领导讲话接口
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @GetMapping("/GetLSVVideo")
    public R GetLSVVideo(HttpServletRequest request, HttpServletResponse response) throws Exception {
        MySqlData mySqlData = new MySqlData();
        mySqlData.setTableName("st_lsv");
        mySqlData.setViewName("add");
        mySqlData.setFieldWhere("flag","01","=");
        mySqlData.setSort("tm");
        mySqlData.setOrder("desc");
        List<HashMap<String, Object>> data = commonService.getDataList(mySqlData);
        return R.ok().put("data", data);
    }

    /**
     * 获取防洪调度方案
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @GetMapping("/GetSchemeDesc")
    public R GetSchemeDesc(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String keytime = request.getParameter("keytime");
        if(StringUtils.isBlank(keytime)){
            keytime = DataUtils.getDate("yyyy-MM-dd HH:mm:ss");
        }
        MySqlData mySqlData = new MySqlData();
        mySqlData.setTableName("ST_scheme");
        mySqlData.setViewName("add");
        mySqlData.setFieldWhere("starttime","'"+keytime +"' between starttime and endtime","sql");
        mySqlData.setPage(1);
        mySqlData.setRows(1);
        List<HashMap<String, Object>> data = commonService.getDataList(mySqlData);
        return R.ok().put("data", data);
    }

    /**
     * 获取文化宣传数据
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @GetMapping("/GetCulturalList")
    public R GetCulturalList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        MySqlData mySqlData = new MySqlData();
        mySqlData.setTableName("ST_Cultural");
        mySqlData.setViewName("add");
        mySqlData.setFieldWhere("flag","01","=");
        List<HashMap<String, Object>> data = commonService.getDataList(mySqlData);
        return R.ok().put("data", data);
    }

    /**
     * 获取3日内水质统计信息
     * @param request
     * @return
     * @throws Exception
     */
    @GetMapping("/GetQualityStatistics")
    public R GetQualityStatistics(HttpServletRequest request) throws Exception{
        String keytime = request.getParameter("keytime");
        String stcd = request.getParameter("stcd");
        if(StringUtils.isBlank(keytime)){
            keytime = DataUtils.getDate("yyyy-MM-dd HH:mm:ss");
        }
        if(StringUtils.isBlank(stcd)){
            stcd = "12345678";
        }
        MySqlData mySqlData = new MySqlData();
        mySqlData.setTableName("st_wq_r");
        mySqlData.setViewName("add");
        mySqlData.setFieldWhere("tm","tm >= date_sub('"+keytime+"',INTERVAL 3 day) and tm <= '"+keytime+"' ","sql");
        mySqlData.setFieldWhere("stcd",stcd,"=");
        mySqlData.setSort("tm");
        mySqlData.setOrder("asc");
        List<HashMap<String, Object>> data = commonService.getDataList(mySqlData);
        return R.ok().put("data", data);
    }

    /**
     * 获取3日内库容水位信息
     * @param request
     * @return
     * @throws Exception
     */
    @GetMapping("/GetRSVRStatistics")
    public R GetRSVRStatistics(HttpServletRequest request) throws Exception{
        String keytime = request.getParameter("keytime");
        String stcd = request.getParameter("stcd");
        if(StringUtils.isBlank(keytime)){
            keytime = DataUtils.getDate("yyyy-MM-dd HH:mm:ss");
        }
        if(StringUtils.isBlank(stcd)){
            stcd = "12345678";
        }
        MySqlData mySqlData = new MySqlData();
        mySqlData.setTableName("st_rsvr_r");
        mySqlData.setViewName("add");
        mySqlData.setFieldWhere("tm","tm >= date_sub('"+keytime+"',INTERVAL 3 day) and tm <= '"+keytime+"' ","sql");
        mySqlData.setFieldWhere("stcd",stcd,"=");
        mySqlData.setSort("tm");
        mySqlData.setOrder("asc");
        List<HashMap<String, Object>> data = commonService.getDataList(mySqlData);
        return R.ok().put("data", data);
    }
    /**
     * 获取巡湖打卡视频列表
     * @param request
     * @return
     * @throws Exception
     */
    @GetMapping("/GetLakeWayList")
    public R GetLakeWayList(HttpServletRequest request) throws Exception{
        MySqlData mySqlData = new MySqlData();
        mySqlData.setTableName("ST_lake_way");
        mySqlData.setViewName("add");
        mySqlData.setFieldWhere("flag","01","=");
        mySqlData.setSort("tm");
        mySqlData.setOrder("desc");
        List<HashMap<String, Object>> data = commonService.getDataList(mySqlData);
        return R.ok().put("data", data);
    }

    /**
     * 获取旅游线路视频列表
     * @param request
     * @return
     * @throws Exception
     */
    @GetMapping("/GetTravelWayList")
    public R GetTravelWayList(HttpServletRequest request) throws Exception{
        MySqlData mySqlData = new MySqlData();
        mySqlData.setTableName("ST_Travel_way");
        mySqlData.setViewName("add");
        mySqlData.setFieldWhere("flag","01","=");
        mySqlData.setSort("tm");
        mySqlData.setOrder("desc");
        List<HashMap<String, Object>> data = commonService.getDataList(mySqlData);
        return R.ok().put("data", data);
    }
    /**
     * 获取旅游线路视频列表
     * @param request
     * @return
     * @throws Exception
     */
    @GetMapping("/GetFloodSeason")
    public R GetFloodSeason(HttpServletRequest request) throws Exception{
        String keytime = request.getParameter("keytime");
        if(StringUtils.isBlank(keytime)){
            keytime = DataUtils.getDate("yyyy-MM-dd");
        }
        MySqlData mySqlData = new MySqlData();
        mySqlData.setSql("select group_concat(date_format(starttime,'%m月%d日'),'~',DATE_FORMAT(endtime,'%m月%d日')) xq,");
        mySqlData.setSql("ifnull(floodxq.flag,'00') flag,ifnull(floodxq.flagname,'非汛期') flagname,starttime,endtime from st_flood_season" );
        mySqlData.setSql("left join (select '01' flag,'汛期' flagname from st_flood_season where str_to_date('"+keytime+"','%Y-%m-%d')");
        mySqlData.setSql("between concat(year('"+keytime+"'),'-',date_format(starttime,'%m-%d')) and");
        mySqlData.setSql("concat(year('"+keytime+"'),'-',date_format(endtime,'%m-%d')) limit 1) floodxq on 1=1");
        HashMap<String, Object> data = commonService.getMap(mySqlData);
        return R.ok().put("data", data);
    }

    /**
     * 获取水位和水质信息
     * @param request
     * @return
     * @throws Exception
     */
    @GetMapping("/GetWaterLevelANDQualityList")
    public R GetWaterLevelANDQualityList(HttpServletRequest request) throws Exception{
        String stcd = "";
        MySqlData mySqlData = new MySqlData();
        mySqlData.setSql("select st_stbprp_b.stcd,stnm,z,q,quality,qualityname,sttp,sttpjoin.description sttpname from st_stbprp_b ");
        mySqlData.setSql("left join (");
        mySqlData.setSql("select stcd,tm,round(z,2) z,round(q,2) q,'' quality,'' qualityname from (select stcd,tm,z,q from st_river_r_view where 1=1 ");
        mySqlData.setSql("order by tm desc ) st_river_r_view group by stcd union all " );
        mySqlData.setSql("select stcd,tm,'' z ,'' q, quality,qualityname from (select stcd,tm,water_quality quality");
        mySqlData.setSql(",qualityjoin.description qualityname  from st_wq_r ");
        mySqlData.setSql("left join (select code,description from sm_codeitem where codeid='wa') qualityjoin on qualityjoin.code = st_wq_r.water_quality where 1=1");
        mySqlData.setSql("order by tm desc) st_wq_r group by stcd");
        mySqlData.setSql(") st_wq_r on st_stbprp_b.stcd = st_wq_r.stcd");
        mySqlData.setSql("left join (select code,description from sm_codeitem where codeid = 'TP') sttpjoin on sttpjoin.code = st_stbprp_b.sttp");
        mySqlData.setSql("where 1=1");
        if(StringUtils.isNotBlank(stcd)){
            mySqlData.setSqlValue(" and st_stbprp_b.stcd in ("+stcd+")");
        }
        mySqlData.setSql("and sttp in ('03','05')");
        mySqlData.setSql("order by st_stbprp_b.stcd asc");
        List<HashMap<String,Object>> data = commonService.getDataList(mySqlData);
        return R.ok().put("data",data);
    }

    /**
     * 以流的方式读取文件
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @GetMapping("/GetFileStream")
    public void GetFileStream(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String filepath = request.getParameter("filepath");
        if(StringUtils.isBlank(filepath)){
            throw new RRException("filepath不能为空");
        }
        ServletOutputStream out = null;
        ByteArrayOutputStream baos = null;
        try {
            filepath = uploadFolder + "/" + filepath;
            filepath = filepath.replaceAll("//", "/").replaceAll("\\\\", "\\");
            File file = new File(filepath);
            InputStream inStream = new FileInputStream(file);
            byte[] buffer = new byte[1024];
            int len;
            baos = new ByteArrayOutputStream();
            while ((len = inStream.read(buffer)) != -1) {
                baos.write(buffer, 0, len);
            }
            out = response.getOutputStream();
            out.write(baos.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            baos.flush();
            baos.close();
            out.flush();
            out.close();
        }
    }
}
