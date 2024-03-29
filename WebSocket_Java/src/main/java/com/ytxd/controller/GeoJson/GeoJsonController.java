package com.ytxd.controller.GeoJson;


import com.ytxd.common.DataUtils;
import com.ytxd.common.annotation.AuthIgnore;
import com.ytxd.common.util.R;
import com.ytxd.service.GeoJson.GeoJsonService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author CYC
 */
@RestController
@RequestMapping("/GeoJson")
public class GeoJsonController {

    @Resource
    private GeoJsonService service;

    /**
     *
     *气象雨情-预报报警-降雨等值面图
     */
    @AuthIgnore
    @RequestMapping("/getRealRainGeoJson")
    public R getRealRainGeoJson(HttpServletRequest request) throws Exception{
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        return R.ok().put("data",service.getRealRainGeoJson(obj));
    }


    /**
     *
     *气象雨情-预报报警-气象预警
     */
    @AuthIgnore
    @RequestMapping("/getWeatherWarnings")
    public R getWeatherWarnings(HttpServletRequest request) throws Exception{

        StringBuilder response = new StringBuilder();
        URL url = new URL("\n" +
                "https://datacenter.istrongcloud.com/data/allwarning/510000.json?random=1710739873454");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Authorization", "Basic " + authStringEnc);

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();
        connection.disconnect();

        // 在这里可以处理响应数据，例如将其转换为JSON对象或其他格式
        System.out.println(response.toString());

        return R.ok(response.toString());
    }


    /**
     *
     *气象雨情-预报报警-雷达测雨
     */
    @AuthIgnore
    @RequestMapping("/getRadarPecipitation")
    public R getRadarPecipitation(HttpServletRequest request) throws Exception{
//        random=1710742139549
        StringBuilder response = new StringBuilder();
        URL url = new URL("\n" +
                "https://datacenter.istrongcloud.com/data/fy2/config/alarm/224.json");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Authorization", "Basic " + authStringEnc);

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();
        connection.disconnect();

        // 在这里可以处理响应数据，例如将其转换为JSON对象或其他格式
        System.out.println(response.toString());

        return R.ok(response.toString());
    }


    /**
     *
     *气象雨情-预报报警-各个站点雷达测雨
     */
    @AuthIgnore
    @RequestMapping("/getRainMeasurement")
    public R getRainMeasurement(HttpServletRequest request) throws Exception{

        String stationIdStr = request.getParameter("stationId");
        int stationId = Integer.parseInt(stationIdStr);

        // 假设你有一个预先定义好的URL列表
        String[] urls = {

        "https://dmpapi.istrongcloud.com/data/forecastcy/105.0045422699209/30.864075775495184?apikey=03fd5a4c74eb4b1294d7a996c4a30321&dailysteps=7",
        "https://dmpapi.istrongcloud.com/data/forecastcy/105.15180997483552/31.27450212933934?apikey=03fd5a4c74eb4b1294d7a996c4a30321&dailysteps=7",
        "https://dmpapi.istrongcloud.com/data/forecastcy/105.57370868681295/31.18700786011636?apikey=03fd5a4c74eb4b1294d7a996c4a30321&dailysteps=7",
        "https://dmpapi.istrongcloud.com/data/forecastcy/104.38292674708926/31.48852937876828?apikey=03fd5a4c74eb4b1294d7a996c4a30321&dailysteps=7",
        "https://dmpapi.istrongcloud.com/data/forecastcy/103.97758594056005/32.081997231937486?apikey=03fd5a4c74eb4b1294d7a996c4a30321&dailysteps=7",
        "https://dmpapi.istrongcloud.com/data/forecastcy/104.53804202388217/31.956208461251407?apikey=03fd5a4c74eb4b1294d7a996c4a30321&dailysteps=7",
        "https://dmpapi.istrongcloud.com/data/forecastcy/105.08053624999499/31.818965443523652?apikey=03fd5a4c74eb4b1294d7a996c4a30321&dailysteps=7",
        "https://dmpapi.istrongcloud.com/data/forecastcy/104.46184753773511/32.40252907260101?apikey=03fd5a4c74eb4b1294d7a996c4a30321&dailysteps=7",
        "https://dmpapi.istrongcloud.com/data/forecastcy/104.77679231927263/31.15011169821909?apikey=03fd5a4c74eb4b1294d7a996c4a30321&dailysteps=7",
        "https://dmpapi.istrongcloud.com/data/forecastcy/105.42707412310824/31.465223840361162?apikey=03fd5a4c74eb4b1294d7a996c4a30321&dailysteps=7",
        "https://dmpapi.istrongcloud.com/data/forecastcy/104.76601363526767/31.66351132227132?apikey=03fd5a4c74eb4b1294d7a996c4a30321&dailysteps=7",
        "https://dmpapi.istrongcloud.com/data/forecastcy/105.01503379565062/31.56569183488651?apikey=03fd5a4c74eb4b1294d7a996c4a30321&dailysteps=7",
        "https://dmpapi.istrongcloud.com/data/forecastcy/104.09765871047252/31.846732808981873?apikey=03fd5a4c74eb4b1294d7a996c4a30321&dailysteps=7",
        "https://dmpapi.istrongcloud.com/data/forecastcy/104.35693364862895/31.761171531732476?apikey=03fd5a4c74eb4b1294d7a996c4a30321&dailysteps=7",
        "https://dmpapi.istrongcloud.com/data/forecastcy/104.33837056672667/32.121745550716305?apikey=03fd5a4c74eb4b1294d7a996c4a30321&dailysteps=7",
        "https://dmpapi.istrongcloud.com/data/forecastcy/104.95183472854804/32.16205404773539?apikey=03fd5a4c74eb4b1294d7a996c4a30321&dailysteps=7",
        "https://dmpapi.istrongcloud.com/data/forecastcy/105.24446539623337/32.23951267666472?apikey=03fd5a4c74eb4b1294d7a996c4a30321&dailysteps=7",
        "https://dmpapi.istrongcloud.com/data/forecastcy/104.08974583764095/32.477912682905554?apikey=03fd5a4c74eb4b1294d7a996c4a30321&dailysteps=7",
        "https://dmpapi.istrongcloud.com/data/forecastcy/104.3161399901026/32.704313776740356?apikey=03fd5a4c74eb4b1294d7a996c4a30321&dailysteps=7"

        };

        StringBuilder response = new StringBuilder();
        URL url;
        url = new URL(urls[stationId - 1]);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Authorization", "Basic " + authStringEnc); // 假设 authStringEnc 已经声明和初始化

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();
        connection.disconnect();

        // 处理响应数据（例如转换为JSON对象）
        System.out.println(response.toString());

        return R.ok(response.toString()); // 假设 R.ok() 是构建成功响应的方法
    }


    /**
     *气象雨情-预报报警-24小时降雨预报
     */
    @AuthIgnore
    @RequestMapping("/getOneDay")
    public R getOneDay(HttpServletRequest request) throws Exception{
//            ?random=1710746317707
        StringBuilder response = new StringBuilder();
        URL url = new URL("\n" +
                "https://datacenter.istrongcloud.com/data/nmcrainfall/24.json");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Authorization", "Basic " + authStringEnc);

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();
        connection.disconnect();

        // 在这里可以处理响应数据，例如将其转换为JSON对象或其他格式
        System.out.println(response.toString());

        return R.ok(response.toString());
    }

    /**
     *气象雨情-预报报警-48小时降雨预报
     */
    @AuthIgnore
    @RequestMapping("/getTwoDay")
    public R getTwoDay(HttpServletRequest request) throws Exception{

//        ?random=1710746687764
        StringBuilder response = new StringBuilder();
        URL url = new URL("\n" +
                "https://datacenter.istrongcloud.com/data/nmcrainfall/48.json");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Authorization", "Basic " + authStringEnc);

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();
        connection.disconnect();

        // 在这里可以处理响应数据，例如将其转换为JSON对象或其他格式
        System.out.println(response.toString());

        return R.ok(response.toString());
    }

    /**
     *气象雨情-预报报警-72时降雨预报
     */
    @AuthIgnore
    @RequestMapping("/getThreeDay")
    public R getThreeDay(HttpServletRequest request) throws Exception{

//        ?random=1710747444884
        StringBuilder response = new StringBuilder();
        URL url = new URL("\n" +
                "https://datacenter.istrongcloud.com/data/nmcrainfall/72.json");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Authorization", "Basic " + authStringEnc);

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();
        connection.disconnect();

        // 在这里可以处理响应数据，例如将其转换为JSON对象或其他格式
        System.out.println(response.toString());

        return R.ok(response.toString());
    }


    /**
     *气象雨情-预报报警-风场
     */
    @AuthIgnore
    @RequestMapping("/getWindFarms")
    public R getWindFarms(HttpServletRequest request) throws Exception{

//        /202403/18/08/f006.json?random=1710747619135

        StringBuilder response = new StringBuilder();
        URL url = new URL("\n" +
                "https://datacenter.istrongcloud.com/data/gfsimgv4/00_time_wind_surface.json");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Authorization", "Basic " + authStringEnc);

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();
        connection.disconnect();

        // 在这里可以处理响应数据，例如将其转换为JSON对象或其他格式
        System.out.println(response.toString());

        return R.ok(response.toString());
    }


    /**
     *气象雨情-预报报警-山洪灾害
     */
    @AuthIgnore
    @RequestMapping("/getMountainTorrents")
    public R getMountainTorrents(HttpServletRequest request) throws Exception{

        StringBuilder response = new StringBuilder();
        URL url = new URL("\n" +
                "https://datacenter.istrongcloud.com/data/citymapsign/company/224/014/510700.json");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Authorization", "Basic " + authStringEnc);

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();
        connection.disconnect();

        // 在这里可以处理响应数据，例如将其转换为JSON对象或其他格式
        System.out.println(response.toString());

        return R.ok(response.toString());
    }


    /**
     *气象雨情-天气预报
     */
    @AuthIgnore
    @RequestMapping("/getWeatherForecast")
    public R getWeatherForecast(HttpServletRequest request) throws Exception{

        // 获取请求参数映射
        Map<String, Object> obj = DataUtils.getParameterMap(request);

        // 获取当前日期时间（包含时分秒）
        LocalDateTime now = LocalDateTime.now();

        // 将日期时间转换为字符串（例如 "yyyy-MM-dd HH:mm:ss" 格式）
        String formattedDateTime = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        obj.put("startTime", formattedDateTime);

        List<Map<String, Object>> weatherForecast = service.getWeatherForecast(obj);

        Map<String,Object> data = new HashMap<>();

        data.put("weatherForecast",weatherForecast);

        return R.ok().put("data",data);
    }

    String username = "mysl";
    String password = "mysl1234..@#@";
    String authString = username + ":" + password;
    String authStringEnc = Base64.getEncoder().encodeToString(authString.getBytes());

}
