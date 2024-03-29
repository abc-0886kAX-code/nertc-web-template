package com.ytxd.util.GeoJson;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.BooleanUtil;
import cn.hutool.core.util.NumberUtil;
import com.alibaba.fastjson.JSONObject;
import wcontour.Contour;
import wcontour.global.Border;
import wcontour.global.PointD;
import wcontour.global.PolyLine;
import wcontour.global.Polygon;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class LsosurfaceUtil {
    /**
     * 生成等值面
     *
     * @param gridData 网络数据
     * @param contour  数据间隔
     * @param X        网络经度坐标
     * @param Y        网络纬度坐标
     * @param
     * @return
     */
    public static List<Polygon> gridToPolygon(double[][] gridData, double[] contour, double[] X, double[] Y, List<PointD> listClipPoint) {
        List<Polygon> listPolygon = new ArrayList<Polygon>();
        try {
            double undefData = -9999.0;
            int nc = contour.length;
            int[][] S1 = new int[gridData.length][gridData[0].length];
            // 生成边界
            List<Border> listBorder = Contour.tracingBorders(gridData, X, Y, S1, undefData);
            // 生成等值线
            List<PolyLine> listPolyLine = Contour.tracingContourLines(gridData, X, Y, nc, contour, undefData, listBorder, S1);
            // 平滑
            listPolyLine = Contour.smoothLines(listPolyLine);
            //生成等值面
            listPolygon = Contour.tracingPolygons(gridData, listPolyLine, listBorder, contour);
            //裁剪
            if (listClipPoint != null && listClipPoint.size() > 0) {
                listPolygon = Contour.clipPolygons(listPolygon, listClipPoint);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listPolygon;
    }

    public static JSONObject getPolygonGeoJson(List<Polygon> listPolygon, double[] contour, double[][] color, String[] colorLabel, String title, Date dt) {
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("type", "FeatureCollection");
        jsonObj.put("contour", contour);
        if(color.length>colorLabel.length){
            double[][] colors1 = new double[color.length-1][color[0].length];
            for(int x =1;x<color.length;x++){
                for(int y =0;y<color[0].length;y++){
                    colors1[x-1][y] = color[x][y];
                }
            }
            jsonObj.put("color", colors1);
        }else {
            jsonObj.put("color", color);
        }

        jsonObj.put("colorLabel", colorLabel);
        jsonObj.put("title", title);
        jsonObj.put("date", DateUtil.format(dt, "yyyy-MM-dd HH:mm:ss"));
        List<JSONObject> listData = new ArrayList<JSONObject>();
        if (listPolygon == null || listPolygon.size() == 0) {
            return null;
        }
        //对多边形进行排序，通过最小边界值，由小到大，如果最小值相等最大值不相同再按最大值由大到小排序
        listPolygon.sort(new Comparator<Polygon>() {
            @Override
            public int compare(Polygon o1, Polygon o2) {
                int result = NumberUtil.compare(o1.LowValue, o2.LowValue);
                if (result == 0) {
                    result = NumberUtil.compare(o2.HighValue, o1.HighValue);
                    if (result == 0) {
                        result = NumberUtil.compare(BooleanUtil.toInt(o1.IsHighCenter), BooleanUtil.toInt(o2.IsHighCenter));
                    }
                }
                return result;
            }
        });
        //循环得到等值面数据
        try {
            for (Polygon pPolygon : listPolygon) {
//                if (pPolygon.OutLine.PointList.size() <= 100) {
//                     说明这块非常小，直接去掉。
//                    continue;
//                }
                List<Object> ptsTotal = new ArrayList<Object>();
                int i = 0;
                for (PointD ptd : pPolygon.OutLine.PointList) {
                    // 隔多少个点取一个点
                    if (pPolygon.OutLine.PointList.size() <= 100 ||i % 10 == 0 || i == pPolygon.OutLine.PointList.size()-1) {
                        List<Double> pt = new ArrayList<Double>();
                        // 经纬度精度必须保留4位以上，不然边线是锯齿状。
                        pt.add(doubleFormat(ptd.X, 4));
                        pt.add(doubleFormat(ptd.Y, 4));
                        ptsTotal.add(pt);
                    }
                    i = i + 1;
                }
                List<Object> list3D = new ArrayList<Object>();
                list3D.add(ptsTotal);
                //等值面经纬度数据集json对象
                JSONObject geometry = new JSONObject();
                geometry.put("type", "Polygon");
                geometry.put("coordinates", list3D);
                int level = getIndex(contour, pPolygon.LowValue);

                if (pPolygon.IsHighCenter && level < contour.length - 1) {
                    level = level + 1;
                }
                //得到值的json对象
                JSONObject properties = new JSONObject();
                properties.put("value", Double.parseDouble(String.format("%.1f", contour[level])));
                properties.put("level", level);
                properties.put("color", color[level]);
                //等值面json对象
                JSONObject features = new JSONObject();
                features.put("type", "Feature");
                features.put("geometry", geometry);
                features.put("properties", properties);
                //添加到数据list
                listData.add(features);
            }
            jsonObj.put("features", listData);
            return jsonObj;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObj;
    }

    public static double doubleFormat(double d, int scale) {
        BigDecimal bg = new BigDecimal(d);
        double f1 = bg.setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
        return f1;
    }

    public static int getIndex(double[] arr, double value) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == value) {
                return i;
            }
        }
        return -1;//如果未找到返回-1
    }
}
