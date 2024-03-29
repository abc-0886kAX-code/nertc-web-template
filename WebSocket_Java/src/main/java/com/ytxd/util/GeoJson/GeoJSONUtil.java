package com.ytxd.util.GeoJson;

import cn.hutool.core.io.FileUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ytxd.common.DataUtils;
import com.ytxd.common.util.SpringContextUtil;
import com.ytxd.dao.GeoJson.GeoJsonMapper;
import lombok.AllArgsConstructor;
import lombok.Setter;
import wcontour.Interpolate;
import wcontour.global.PointD;
import wcontour.global.Polygon;

import java.io.File;
import java.math.BigDecimal;
import java.util.*;

/**
 * GeoJSON 文件读取工具
 *
 * @author TY
 * @version 1.0, 2015-4-21
 * @since 1.0, 2015-4-21
 */
@Setter
@AllArgsConstructor
public class GeoJSONUtil {
     private GeoJsonMapper mapper;

     double _undefData = -9999.0;
    /**
     * 数据区间
     */
    double[] contour = new double[]{0.01, 10, 25, 50, 100, 250, 250.01};
    /**
     * 颜色
     */
    double[][] color = new double[][]{
            {255, 255, 255, 0},
            {166, 242, 143, 0.6},
            {61, 186, 61, 0.6},
            {97, 184, 255, 0.6},
            {0, 0, 255, 0.6},
            {250, 0, 250, 0.6},
            {128, 0, 64, 0.6}
    };
    /**
     * 图例
     */
    String[] colorLabel = new String[]{"晴", "0-10", "10-25", "25-50", "50-100", "100-250", ">250"};
    /**
     * 等值面图标题
     */
    String title = "标题";
    /**
     * 经度
     */
    double[] X;
    /**
     * 纬度
     */
    double[] Y;
    /**
     * 时间
     */
    Date tm;
    /**
     * 边界文件
     */
    File clipPointFile;
    /**
     * 数据
     */
    List<HashMap<String, Object>> dataList;

    /**
     * 只提供数据
     * @param dataList
     */
    public GeoJSONUtil(List<HashMap<String, Object>> dataList){
        this.dataList = dataList;
        this.mapper = SpringContextUtil.getBean(GeoJsonMapper.class);
    }
    /**
     *
     * @Desription TODO 生成等值面
     * @return cn.hutool.json.JSONObject
     * @date 2024/3/1 16:51
     * @Auther TY
     */
    public JSONObject getGeoJson(){
        /**
         * 散点数据
         */
        double[][] trainData = new double[dataList.size()][3];
        Integer n = 0;
        for (Map<String, Object> dataMap : dataList) {
            trainData[n][0] = Double.parseDouble(DataUtils.getMapKeyValue(dataMap, "lgtd"));
            trainData[n][1] = Double.parseDouble(DataUtils.getMapKeyValue(dataMap, "lttd"));
            trainData[n][2] = DataUtils.getMapDoubleValue(dataMap, "p");
            if(n%5==0){
                trainData[n][2] = 50;
            }
            if(n%9==0){
                trainData[n][2] = 100;
            }
            n++;
        }
        CheckXY();
        /**
         * 得到边界点集合
         */
        List<PointD> listClipPoint = new ArrayList<PointD>();
        /**
         * 读取边界点
         */
        double minx = 20000;
        double miny = 20000;
        double maxx = 0.0;
        double maxy = 0.0;
        if(clipPointFile != null && clipPointFile.isFile()){
            String jsonStr = FileUtil.readUtf8String(clipPointFile);
            JSONArray jsArray = JSONObject.parseArray(jsonStr);
            Integer x = 0;
            /**
             * 获取经纬度最大最小值
             */
            for (Object obj : jsArray) {
                JSONObject js = (JSONObject) obj;
                PointD point = new PointD();
                if (x % 5 == 0) {
                    point.X = ((BigDecimal) js.get("longitude")).doubleValue();
                    point.Y = ((BigDecimal) js.get("latitude")).doubleValue();
                    listClipPoint.add(point);
                }
                if (((BigDecimal) js.get("longitude")).doubleValue() < minx) {
                    minx = ((BigDecimal) js.get("longitude")).doubleValue();
                }
                if (((BigDecimal) js.get("longitude")).doubleValue() > maxx) {
                    maxx = ((BigDecimal) js.get("longitude")).doubleValue();
                }
                if (((BigDecimal) js.get("latitude")).doubleValue() < miny) {
                    miny = ((BigDecimal) js.get("latitude")).doubleValue();
                }
                if (((BigDecimal) js.get("latitude")).doubleValue() > maxy) {
                    maxy = ((BigDecimal) js.get("latitude")).doubleValue();
                }
                x++;
            }
        }else {
            minx = Arrays.stream(X).min().getAsDouble();
            maxx = Arrays.stream(X).max().getAsDouble();
            miny = Arrays.stream(Y).min().getAsDouble();
            maxy = Arrays.stream(Y).max().getAsDouble();
        }
        /**
         * 散点数据转栅格数据
         */
        Interpolate.createGridXY_Num(minx, miny, maxx, maxy, X, Y);
        double[][] _gridData = Interpolate.interpolation_IDW_Neighbor(trainData, X, Y, 12, _undefData);
        List<Polygon> listPolygon = LsosurfaceUtil.gridToPolygon(_gridData, contour, X, Y, listClipPoint);
        /**
         * 等值面转json
         */
        JSONObject strJson = LsosurfaceUtil.getPolygonGeoJson(listPolygon, contour, color, colorLabel, title, tm);

        return strJson;
    }
    /**
     *
     * @Desription TODO 检查经纬度是否为空
     * @return void
     * @date 2024/3/1 16:58
     * @Auther TY
     */
    public void CheckXY(){
        if(X == null || X.length == 0){
            this.X = mapper.getRealRainSiteLgtd();
        }
        if(Y == null || Y.length == 0){
            this.Y = mapper.getRealRainSiteLttd();
        }
    }
}
