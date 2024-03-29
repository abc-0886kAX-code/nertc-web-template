package com.ytxd.util.GeoJson;

import cn.hutool.core.io.FileUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.tomcat.util.codec.binary.Base64;
import org.geotools.data.DataUtilities;
import org.geotools.data.FeatureSource;
import org.geotools.feature.FeatureCollection;
import org.geotools.feature.FeatureIterator;
import org.geotools.feature.simple.SimpleFeatureBuilder;
import org.geotools.filter.FilterFactoryImpl;
import org.geotools.geojson.feature.FeatureJSON;
import org.geotools.geometry.jts.WKTReader2;
import org.geotools.map.*;
import org.geotools.renderer.GTRenderer;
import org.geotools.renderer.lite.StreamingRenderer;
import org.geotools.styling.SLD;
import org.geotools.styling.Style;
import org.locationtech.jts.geom.Envelope;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;
import org.opengis.feature.type.GeometryType;
import org.opengis.filter.FilterFactory;
import org.opengis.filter.expression.Expression;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.*;

/**
 * @Author TY
 * @Date 2022/07/28 下午14:17
 * @Version 1.0
 */
public class GeojsonToImageUtils {
    /**
     * 获取GeoJSON图片
     *
     * @param json      需要生成图片的json
     * @param imagePath 生成图片的路径
     * @return 返回base64编码
     * @throws IOException
     */
    public static String getGeojsonBase64(String json, String imagePath) throws IOException {
        String imgStr = "";
        try {
            /**
             * 生成图片
             */
            String s1 = GeoJsonToImg(json, imagePath);
            /**
             * 将图片转化为base64编码
             */
            imgStr = getImgStr(imagePath + s1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imgStr;
    }

    /**
     * 生成图片
     *
     * @param json    需要生成图片的json
     * @param imgPath 生成图片的路径
     * @return 返回图片路径（绝对路径）
     * @throws IOException
     */
    public static String GeoJsonToImg(String json, String imgPath) throws IOException {
        /**
         * 随机生成图片的名称
         */
        String filename = UUID.randomUUID().toString() + ".png";
        /**
         * 将geojson转化为 features
         */
        FeatureJSON featureJSON = new FeatureJSON();
        FeatureCollection features = featureJSON.readFeatureCollection(json);
        /**
         * 给每个面设置颜色和样式
         */
        MapContent mapContent = new MapContent();
        Envelope envelope = setFeatureStyle(features, mapContent, json);
        /**
         * 画图例
         */
        String legendName = CreateLegend(json, imgPath);
        /**
         * 画成图片
         */
        saveMapToImage(mapContent, imgPath + filename, 800, envelope, imgPath + legendName);
        return filename;
    }

    /**
     * 生成图片
     * 顺便画边界线
     *
     * @param json    需要生成图片的json
     * @param imgPath 生成图片的路径
     * @return 返回图片路径（绝对路径）
     * @throws IOException
     */
    public static String GeoJsonToImg2(String json, String bordJson, String imgPath) throws IOException {
        /**
         * 随机生成图片的名称
         */
        String filename = UUID.randomUUID().toString() + ".png";
        /**
         * 将geojson转化为 features
         */
        FeatureJSON featureJSON = new FeatureJSON();
        FeatureCollection features = featureJSON.readFeatureCollection(json);
        /**
         * 给每个面设置颜色和样式
         */
        MapContent mapContent = new MapContent();
        Envelope envelope = setFeatureStyle(features, mapContent, json);
        /**
         * 画边界
         */
        FeatureCollection bordFeatures = featureJSON.readFeatureCollection(bordJson);
        envelope = getBordEnvelope(envelope, mapContent, bordFeatures);
        /**
         * 画图例
         */
        String legendName = CreateLegend(json, imgPath);
        /**
         * 画成图片
         */
        saveMapToImage(mapContent, imgPath + filename, 800, envelope, imgPath + legendName);
        return filename;
    }
    /**
     * 画点
     *
     * @param json
     * @param bordJson
     * @param imgPath
     * @return
     * @throws IOException
     */
    public static String GeoJsonToImg3(String json, String bordJson, List<Map<String, Object>> list, String imgPath) throws IOException {
        /**
         * 随机生成图片的名称
         */
        String filename = UUID.randomUUID().toString() + ".png";
        /**
         * 将geojson转化为 features
         */
        FeatureJSON featureJSON = new FeatureJSON();
        FeatureCollection features = featureJSON.readFeatureCollection(json);
        /**
         * 给每个面设置颜色和样式
         */
        MapContent mapContent = new MapContent();
        Envelope envelope = setFeatureStyle(features, mapContent, json);
        /**
         * 画边界
         */
        FeatureCollection bordFeatures = featureJSON.readFeatureCollection(bordJson);
        envelope = getBordEnvelope(envelope, mapContent, bordFeatures);
        /**
         * 画点
         */
        envelope = getGeometry(list, mapContent, envelope);
        /**
         * 画图例
         */
        String legendName = CreateLegend(json, imgPath);
        /**
         * 画成图片
         */
        saveMapToImage(mapContent, imgPath + filename, 800, envelope, imgPath + legendName);
        return filename;
    }

    /**
     * 画点
     * @param list
     * @param mapContent
     * @param envelope
     * @return
     */
    public static Envelope getGeometry(List<Map<String, Object>> list, MapContent mapContent, Envelope envelope) {
        double minx = envelope.getMinX();
        double maxx = envelope.getMaxX();
        double miny = envelope.getMinY();
        double maxy = envelope.getMaxY();
        for (Map<String, Object> map : list) {
            try {
                /**
                 * 获取经纬度
                 */
                double x = Double.parseDouble(String.valueOf(map.get("lgtd")));
                double y = Double.parseDouble(String.valueOf(map.get("lttd")));
                /**
                 * 获取点的值
                 */
                String r = String.valueOf(map.get("r"));
                /**
                 * 创建一个点
                 */
                String wktStr = "Point(" + x + " " + y + ")";
                WKTReader2 wktReader = new WKTReader2();
                /**
                 * 创建geometry
                 */
                SimpleFeatureType simpleFeatureType = DataUtilities.createType("Point","geometry:Point,value:String");
                /**
                 * 创建构造器
                 */
                SimpleFeatureBuilder builder = new SimpleFeatureBuilder(simpleFeatureType);
                /**
                 * 添加值
                 */
                builder.add(wktReader.read(wktStr));
                builder.add(r);
                /**
                 * 生成SimpleFeature和FeatureCollection
                 */
                SimpleFeature feature = builder.buildFeature(null);
                FeatureCollection featureSourcess = DataUtilities.collection(feature);
                /**
                 * 创建样式
                 */
                FilterFactory filterFactory = new FilterFactoryImpl();
                Expression fontSize = filterFactory.literal(11);
                Expression fontWeight = filterFactory.literal("normal");
                Expression fontStyle = filterFactory.literal("黑体");
                org.geotools.styling.Font font = new FontImpl(fontSize,fontWeight,fontStyle).getFont();
                Layer layer = new FeatureLayer(featureSourcess,
                        SLD.createPointStyle("Circle",Color.red, Color.red,1,4,"value",font));
                mapContent.addLayer(layer);
                /**
                 * 计算最大最小值
                 */
                if (minx > x) {
                    minx = x;
                }
                if (maxx <= x) {
                    maxx = x;
                }
                if (miny > y) {
                    miny = y;
                }
                if (maxy <= y) {
                    maxy = y;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return new Envelope(minx, maxx, miny, maxy);
    }

    /**
     * 画边界线
     *
     * @param envelope
     * @param mapContent
     * @param features
     * @return
     */
    public static Envelope getBordEnvelope(Envelope envelope, MapContent mapContent, FeatureCollection features) {
        /**
         * 获取SimpleFeature
         */
        FeatureIterator<SimpleFeature> feature = features.features();
        double minx = envelope.getMinX();
        double maxx = envelope.getMaxX();
        double miny = envelope.getMinY();
        double maxy = envelope.getMaxY();
        /**
         * 获取Layer
         */
        Layer layer = new FeatureLayer(features, SLD.createLineStyle(Color.red, 1));
        /**
         * 添加Layer
         */
        mapContent.addLayer(layer);
        Envelope envelopes1 = mapContent.getMaxBounds();
        /**
         * 计算最大最小值
         */
        if (minx > envelopes1.getMinX()) {
            minx = envelopes1.getMinX();
        }
        if (maxx <= envelopes1.getMaxX()) {
            maxx = envelopes1.getMaxX();
        }
        if (miny > envelopes1.getMinY()) {
            miny = envelopes1.getMinY();
        }
        if (maxy <= envelopes1.getMaxY()) {
            maxy = envelopes1.getMaxY();
        }
        return new Envelope(minx, maxx, miny, maxy);
    }

    /**
     * 给每一个面设置颜色
     *
     * @param features
     * @param mapContent
     * @param json
     */
    public static Envelope setFeatureStyle(FeatureCollection features, MapContent mapContent, String json) {
        /**
         * 获取SimpleFeature
         */
        FeatureIterator<SimpleFeature> feature = features.features();
        /**
         * 两个map用来存放面的点集合和样式
         */
        HashMap<Integer, LinkedList<SimpleFeature>> featureMap = new HashMap<Integer, LinkedList<SimpleFeature>>();
        HashMap<Integer, Style> styleMap = new HashMap<Integer, Style>();
        /**
         * 用于记录最大最小值
         * 主要是为后面生成图片设置图片高度
         */
        double minx = 999999;
        double maxx = 0.0;
        double miny = 999999;
        double maxy = 0.0;
        /**
         * 遍历FeatureIterator<SimpleFeature>
         *     将相同面放在一个，为后面生成图片做准备
         */
        while (feature.hasNext()) {
            /**
             * 读取SimpleFeature
             */
            SimpleFeature feature1 = feature.next();
            /**
             * 获取json中包含的颜色
             */
            ArrayList<Double> list = (ArrayList) feature1.getAttribute("color");
            /**
             * 获取样式
             */
            Style style = getStyle(list, feature1);
            /**
             * 获取stle对象的hashcode，用来做主键
             */
            Integer key = style.hashCode();
            /**
             * 判断是否存在相同的key
             */
            if (featureMap.containsKey(key)) {
                LinkedList<SimpleFeature> source = featureMap.get(key);
                source.add(feature1);
                featureMap.put(key, source);
            } else {
                LinkedList<SimpleFeature> source = new LinkedList<>();
                source.add(feature1);
                featureMap.put(key, source);
                styleMap.put(key, style);
            }
        }
        /**
         * 遍历map，设置Layer
         */
        for (Integer key : featureMap.keySet()) {
            LinkedList<SimpleFeature> source = featureMap.get(key);
            SimpleFeature[] sources = source.toArray(new SimpleFeature[source.size()]);
            FeatureSource featureSource = DataUtilities.source(sources);
            /**
             * 获取Layer
             */
            Layer layer = new FeatureLayer(featureSource, styleMap.get(key));
            /**
             * 添加Layer
             */
            mapContent.addLayer(layer);
            Envelope envelopes = mapContent.getMaxBounds();
            /**
             * 计算最大最小值
             */
            if (minx > envelopes.getMinX()) {
                minx = envelopes.getMinX();
            }
            if (maxx <= envelopes.getMaxX()) {
                maxx = envelopes.getMaxX();
            }
            if (miny > envelopes.getMinY()) {
                miny = envelopes.getMinY();
            }
            if (maxy <= envelopes.getMaxY()) {
                maxy = envelopes.getMaxY();
            }

        }
        return new Envelope(minx, maxx, miny, maxy);
    }

    /**
     * 生成样式
     *
     * @param list （rgb）
     * @return
     */
    public static Style getStyle(ArrayList<?> list, SimpleFeature feature1) {
        Color color = null;
        if (list == null || list.size() == 0) {
            color = Color.red;
        } else {
            color = new Color(getColorInterger(list.get(0)),
                    getColorInterger(list.get(1)),
                    getColorInterger(list.get(2)),
                    (int) (getColorFloat(list.get(3)) * 255 + 0.5)
            );
        }

        GeometryType type = feature1.getFeatureType().getGeometryDescriptor().getType();
        Style polygonStyle = null;
        switch (type.toString()) {
            case "GeometryTypeImpl geometry<MultiPolygon>":
                polygonStyle = SLD.createLineStyle(color, 1);
                break;
            default:
                polygonStyle = SLD.createPolygonStyle(color, color, 1);
        }
        return polygonStyle;
    }

    /**
     * 获取Integer类型
     *
     * @param value
     * @param <T>
     * @return
     */
    public static <T> Integer getColorInterger(T value) {
        Integer res = 0;
        if (value.getClass().getTypeName() == "java.lang.Double") {
            res = ((Double) value).intValue();
        }
        if (value.getClass().getTypeName() == "java.lang.Long") {
            res = ((Double) ((Long) value).doubleValue()).intValue();
        }
        if (value.getClass().getTypeName() == "java.math.BigDecimal") {
            res = ((BigDecimal) value).intValue();
        }
        return res;
    }

    /**
     * 获取float类型
     *
     * @param value
     * @param <T>
     * @return
     */
    public static <T> Float getColorFloat(T value) {
        float res = 0.0f;
        if (value.getClass().getTypeName() == "java.lang.Double") {
            res = ((Double) value).floatValue();
        }
        if (value.getClass().getTypeName() == "java.lang.Long") {
            res = ((Long) value).floatValue();
        }
        if (value.getClass().getTypeName() == "java.math.BigDecimal") {
            res = ((BigDecimal) value).floatValue();
        }
        return res;
    }

    /**
     * 去除properties属性
     *
     * @param jsonstr
     * @return
     */
    public static String removeGeoJsonProperties(String jsonstr) {
        JSONObject json = (JSONObject) JSONObject.parse(jsonstr);
        JSONArray features = (JSONArray) json.get("features");
        for (int i = 0; i < features.size(); i++) {
            JSONObject feature = features.getJSONObject(i);
            feature.remove("properties");
        }
        return json.toJSONString();
    }

    /**
     * 将图片转换成Base64编码
     *
     * @param imgFile 待处理图片
     * @return
     */
    public static String getImgStr(String imgFile) {
        // 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        InputStream in = null;
        byte[] data = null;
        // 读取图片字节数组
        try {
            in = new FileInputStream(imgFile);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Base64.encodeBase64String(data);
    }

    /**
     * 判断GeoJSON格式是否正确
     *
     * @param strJson
     * @return
     */
    public static boolean checkGeojson(String strJson) {
        Boolean flag = true;
        JSONObject json = (JSONObject) JSONObject.parse(strJson);
        if (!json.containsKey("features")) {
            return false;
        }
        JSONArray features = (JSONArray) json.get("features");
        for (int i = 0; i < features.size(); i++) {
            JSONObject feature = features.getJSONObject(i);
            if (!feature.containsKey("geometry")) {
                flag = false;
                break;
            }
            JSONObject geometry = (JSONObject) feature.get("geometry");
            if (!geometry.containsKey("type")) {
                flag = false;
                break;
            }
            if (!geometry.containsKey("coordinates")) {
                flag = false;
                break;
            }

        }
        return flag;
    }

    /**
     * 保存为图片
     *
     * @param map
     * @param file
     * @param imageWidth
     */
    public static void saveMapToImage(final MapContent map, final String file, final int imageWidth, final Envelope envelope, String legendPath) {
        GTRenderer renderer = new StreamingRenderer();
        renderer.setMapContent(map);
        Rectangle imageBounds = null;
        try {
            /**
             * 计算图片高度
             */
            double heightToWidth = envelope.getHeight() / envelope.getWidth();
            imageBounds = new Rectangle(0, 0, imageWidth, 880);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        BufferedImage image = new BufferedImage(imageBounds.width, imageBounds.height, BufferedImage.TYPE_INT_RGB);
        Graphics2D gr = image.createGraphics();
        gr.fill(imageBounds);
        try {
            Image src1 = ImageIO.read(new File(legendPath));
            gr.drawImage(src1, imageBounds.width - 205, imageBounds.height - 205, 200, 200, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        /**
         * 将图例画上来
         */
        try {
            /**
             * 画图，并保存为图片
             */
            renderer.paint(gr, imageBounds, envelope);
            File fileToSave = new File(file);
            ImageIO.write(image, "png", fileToSave);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 生成图例
     *
     * @param json
     * @param imagePath
     * @return
     */
    public static String CreateLegend(String json, String imagePath) throws IOException {
        String legendName = UUID.randomUUID().toString() + ".png";
        JSONObject jsonObject = JSONObject.parseObject(json);
        /**
         * 获取图例说明
         */
        JSONArray colorLabel = (JSONArray) jsonObject.get("colorLabel");
        /**
         * 获取图片颜色
         */
        JSONArray color = (JSONArray) jsonObject.get("color");
        /**
         * 存放信息
         */
        LinkedList<Map<String, Object>> list = new LinkedList<>();
        for (int i = 0; i < colorLabel.size(); i++) {
            Map<String, Object> map = new HashMap<>();
            Color colors = getColor((JSONArray) color.get(i));
            map.put("color", colors);
            map.put("colorLabel", colorLabel.getString(i));
            list.add(map);
        }
        /**
         * 默认的宽度和高度
         */
        int width = 200;
        int height = 200;
        /**
         * 开始生成图片
         */
        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = (Graphics2D) bi.getGraphics();
        /**
         * 设置背景颜色
         */
        g2.setColor(Color.WHITE);
        /**
         * 填充整张图片
         */
        g2.fillRect(0, 0, width, height);
        g2.setColor(Color.BLACK);
        g2.drawRoundRect(10, 10, 180, 180, 10, 10);
        /**
         * 抗锯齿
         */
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int y = (height - 2 * 25) / list.size();
        for (int i = 0; i < list.size(); i++) {
            Map<String, Object> dataMap = list.get(i);
            int x = (width - 2 * 25) / dataMap.size();
            /**
             * 画圆角矩形
             */
            g2.setColor((Color) dataMap.get("color"));
            g2.fillRoundRect(25, i * y + 25, 20, 10, 5, 5);
            g2.drawRoundRect(25, i * y + 25, 20, 10, 5, 5);
            /**
             * 显示
             */
            g2.setFont(new Font("宋体", Font.BOLD, 16));
            g2.setColor(Color.black);
            g2.drawString(dataMap.get("colorLabel").toString(), x + 25, i * y + 35);
        }
        g2.dispose(); // 释放对象
        File file = new File(imagePath);
        if (!file.exists()) {
            file.mkdirs();
        }
        file = new File(imagePath, legendName);
        FileOutputStream fos = new FileOutputStream(file);
        /**
         * 保存图片
         */
        ImageIO.write(bi, "PNG", fos);
        fos.close();
        return legendName;
    }

    /**
     * 获取颜色
     *
     * @param rgb
     * @return
     */
    public static Color getColor(JSONArray rgb) {
        if (rgb.size() == 3) {
            Color color = new Color(getColorInterger(rgb.getDouble(0)),
                    getColorInterger(rgb.getDouble(1)),
                    getColorInterger(rgb.getDouble(2))
            );
            return color;
        }
        if (rgb.size() == 4) {
            Color color = new Color(getColorInterger(rgb.getDouble(0)),
                    getColorInterger(rgb.getDouble(1)),
                    getColorInterger(rgb.getDouble(2)),
                    (int) (getColorFloat(rgb.getDouble(3)) * 255 + 0.5)
            );
            return color;
        }
        return Color.white;
    }

    public static void main(String[] args) throws Exception {
//        String geojsonPath = "D:\\uploadFiles\\json\\2022061210\\2022061220_h.json";//生成的GeoJSON文件地址
//        String geojsonPath = "D:\\uploadFiles\\json\\12580.json";//生成的GeoJSON文件地址
        String geojsonPath = "D:\\uploadFiles\\json\\7979797.json";//生成的GeoJSON文件地址
        String iamgepath = "D:\\uploadFiles\\json\\";
        String beijingjson = "D:\\uploadFiles\\json\\beijingshi.json";
        String geojson = FileUtil.readUtf8String(geojsonPath);
        String beijing = FileUtil.readUtf8String(beijingjson);
        Date date = new Date();
//        String geojsonBase64 = getGeojsonBase64(geojson.toString(), iamgepath);
//        String geojsonBase64 = GeoJsonToImg2(geojson.toString(),beijing, iamgepath);
        System.out.println("用时： " + (new Date().getTime() - date.getTime()));
//        System.out.println(geojsonBase64);
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put("lgtd", 116.51606);
        map.put("lttd", 40.73984);
        map.put("r", 100);
        list.add(map);
        map = new HashMap<>();
        map.put("lgtd", 116.54097);
        map.put("lttd", 40.73206);
        map.put("r", 100);
        list.add(map);
        map = new HashMap<>();
        map.put("lgtd", 116.5409);
        map.put("lttd", 40.70813);
        map.put("r", 156);
        list.add(map);
        map = new HashMap<>();
        map.put("lgtd", 115.70137);
        map.put("lttd", 39.7462);
        map.put("r", "3.5");
        list.add(map);
        map = new HashMap<>();
        map.put("lgtd", 117.23846);
        map.put("lttd", 40.67618);
        map.put("r", 0.3);
        list.add(map);
        map = new HashMap<>();
        map.put("lgtd", 116.57685);
        map.put("lttd", 40.67433);
        map.put("r", 335);
        list.add(map);
        map = new HashMap<>();
        map.put("lgtd", 117.23547);
        map.put("lttd", 40.67748);
        map.put("r", 193);
        list.add(map);
        String x = GeoJsonToImg3(geojson, beijing, list, iamgepath);
        System.out.println(x);
//        CreateLegend(geojson, iamgepath);
    }
}


