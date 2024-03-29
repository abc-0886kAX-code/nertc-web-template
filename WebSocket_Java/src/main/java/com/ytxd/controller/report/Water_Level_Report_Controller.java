package com.ytxd.controller.report;

import com.ytxd.common.annotation.AuthIgnore;
import com.ytxd.common.exception.RRException;
import com.ytxd.model.MySqlData;
import com.ytxd.service.CommonService;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import com.ytxd.common.DataUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * 水生态-评价方案指标
 */
@RestController("/Water_Level_Report_Controller")
@RequestMapping("/Report/WaterLevel")
public class Water_Level_Report_Controller {

    @Resource
    private CommonService service;

    @AuthIgnore
    @GetMapping("/exportExcel")
    public void exportExcel(HttpServletRequest request, HttpServletResponse response) {
        try {
            Map<String,Object> obj = DataUtils.getParameterMap(request);
            // 获取请求参数
//            String stcd = DataUtils.getMapKeyValue(obj, "stcd");
//            if(StringUtils.isBlank(stcd)){
//                throw new RRException("请选择站点！");
//            }
            String type = DataUtils.getMapKeyValue(obj, "type"); // 类型 1日报 2周报 3月报
            String start = DataUtils.getMapKeyValue(obj, "start");
            String end = DataUtils.getMapKeyValue(obj, "end");
            if(StringUtils.isBlank(start) || StringUtils.isBlank(end)){
                if("1".equals(type)){
                    start = getTheBeforeDay(0);
                }else if("2".equals(type)){
                    start = getTheBeforeDay(6);
                }else{
                    start = getTheBeforeDay(30);
                }
                end = getTheBeforeDay(-1);
            }
            String realPath = request.getSession().getServletContext().getRealPath("/");
            String template = "水质报表模板.xls";
            HSSFWorkbook wb = new HSSFWorkbook(Files.newInputStream(Paths.get(realPath + "/Excel/" +template)));
            // 查询站点信息
            MySqlData mySqlData = new MySqlData();
            mySqlData.setSql("select * from st_stbprp_b where sttp = '03' order by sign");
            List<HashMap<String, Object>> stbprps = service.getDataList(mySqlData);
            for (int n = 0; n < stbprps.size(); n++) {
                HashMap<String, Object> stbprp = stbprps.get(n);
                String stnm = DataUtils.getMapKeyValue(stbprp, "stnm").replace("水质站", "");
                String sign = DataUtils.getMapKeyValue(stbprp, "sign");
                String stcd = DataUtils.getMapKeyValue(stbprp, "stcd");
                // 根据页面index 获取sheet页
                wb.setSheetName(n, stnm);
                HSSFSheet sheet = wb.getSheetAt(n);

                String selectField = "ROUND(avg(WTMP),2) wtmp,ROUND(avg(PH),2) ph,ROUND(avg(DOX),2) dox,ROUND(avg(COND),2) cond,ROUND(avg(CLARITY),2) clarity" +
                        ",ROUND(avg(COD_MN),2) cod_mn,ROUND(avg(NH3N),2) nh3n,ROUND(avg(TP),2) tp,ROUND(avg(TN),2) tn,ROUND(avg(CODCR),2) codcr ";
                // 高光谱
                if("01".equals(sign)){
                    selectField = "ROUND(avg(TN),2) tn,ROUND(avg(TP),3) tp,ROUND(avg(TSM),2) tsm,ROUND(avg(CHLA),2) chla,ROUND(avg(COD_MN),2) cod_mn," +
                            "ROUND(avg(NH3N),3) nh3n,ROUND(avg(CLARITY),2) clarity,ROUND(avg(SDD),2) sdd,ROUND(avg(EC),2) ec,ROUND(avg(CDOM),2) cdom,ROUND(avg(ATMP),2) atmp ";
                }
                String where = "WHERE stcd = '"+stcd+"' and TM >= STR_TO_DATE('"+start+"', '%Y-%m-%d %H:%i:%s') and  TM < STR_TO_DATE('"+end+"', '%Y-%m-%d %H:%i:%s')";
                mySqlData = new MySqlData();
                mySqlData.setSql("SELECT DATE(TM) AS day,ROUND(AVG(WATER_QUALITY)) AS avg_value," + selectField + "FROM st_wq_r  " + where);
                mySqlData.setSql("GROUP BY DATE(TM)");
                // 拼接平均值 最大值 最小值
                mySqlData.setSql("union all SELECT '平均值' as day,'' AS avg_value," + selectField + "FROM st_wq_r " + where);
                mySqlData.setSql("union all SELECT '最大值' as day,'' AS avg_value," + selectField.replace("avg", "max") + "FROM st_wq_r " + where);
                mySqlData.setSql("union all SELECT '最小值' as day,'' AS avg_value," + selectField.replace("avg", "min") + "FROM st_wq_r " + where);
                mySqlData.setSql("");
                List<HashMap<String, Object>> dataList = service.getDataList(mySqlData);
                for (int i = 0; i < dataList.size(); i++) {
                    // 创建HSSFRow对象
                    int number = "01".equals(sign) ? 3 : 2;
                    HSSFRow row = sheet.createRow(i + number);
                    HSSFCellStyle style  = wb.createCellStyle(); // 创建标题样式
                    //设置上下左右四个边框 实线 or虚线
                    style.setBorderTop(BorderStyle.THIN);
                    style.setBorderBottom(BorderStyle.THIN);
                    style.setBorderLeft(BorderStyle.THIN);
                    style.setBorderRight(BorderStyle.THIN);
//                row.setRowStyle(style);
                    HashMap<String, Object> map = dataList.get(i);
                    // 创建HSSFCell对象 设置单元格的值
                    setRow(row, style, 0, String.valueOf(i + 1));
                    setRow(row, style, 1, stnm);
                    setRow(row, style, 2, DataUtils.getMapKeyValue(map, "day"));
                    setRow(row, style, 3, DataUtils.getMapKeyValue(map, "avg_value"));
                    if("01".equals(sign)){
                        setRow(row, style, 4, DataUtils.getMapKeyValue(map, "tn"));
                        setRow(row, style, 5, DataUtils.getMapKeyValue(map, "tp"));
                        setRow(row, style, 6, DataUtils.getMapKeyValue(map, "tsm"));
                        setRow(row, style, 7, DataUtils.getMapKeyValue(map, "chla"));
                        setRow(row, style, 8, DataUtils.getMapKeyValue(map, "cod_mn"));
                        setRow(row, style, 9, DataUtils.getMapKeyValue(map, "nh3n"));
                        setRow(row, style, 10, DataUtils.getMapKeyValue(map, "clarity"));
                        setRow(row, style, 11, DataUtils.getMapKeyValue(map, "sdd"));
                        setRow(row, style, 12, DataUtils.getMapKeyValue(map, "ec"));
                        setRow(row, style, 13, DataUtils.getMapKeyValue(map, "cdom"));
                        setRow(row, style, 14, DataUtils.getMapKeyValue(map, "atmp"));
                    }else{
                        setRow(row, style, 4, DataUtils.getMapKeyValue(map, "wtmp"));
                        setRow(row, style, 5, DataUtils.getMapKeyValue(map, "ph"));
                        setRow(row, style, 6, DataUtils.getMapKeyValue(map, "dox"));
                        setRow(row, style, 7, DataUtils.getMapKeyValue(map, "cond"));
                        setRow(row, style, 8, DataUtils.getMapKeyValue(map, "clarity"));
                        setRow(row, style, 9, DataUtils.getMapKeyValue(map, "cod_mn"));
                        setRow(row, style, 10, DataUtils.getMapKeyValue(map, "nh3n"));
                        setRow(row, style, 11, DataUtils.getMapKeyValue(map, "tp"));
                        setRow(row, style, 12, DataUtils.getMapKeyValue(map, "tn"));
                        setRow(row, style, 13, DataUtils.getMapKeyValue(map, "codcr"));
                    }
                }
            }

            // 输出Excel文件
            OutputStream output = response.getOutputStream();
            response.reset();
            // 设置文件头
            String reportName = "水质数据.xls";
            response.setHeader("Content-Disposition",
                    "attchement;filename=" + new String(reportName.getBytes("gb2312"), "ISO8859-1"));
            response.setContentType("application/msexcel");
            wb.write(output);
            wb.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setRow(HSSFRow row, HSSFCellStyle style,int i, String value){
        HSSFCell cell = row.createCell(i);
        cell.setCellValue(value);
        cell.setCellStyle(style);
    }

    public static String getTheBeforeDay(int num){
        Calendar date = Calendar.getInstance();
        date.set(Calendar.DATE, date.get(Calendar.DATE) - num);
        date.set(Calendar.MINUTE, 0);
        date.set(Calendar.SECOND, 0);
        return DateFormatUtils.format(date.getTime(), "yyyy-MM-dd");
    }
}
