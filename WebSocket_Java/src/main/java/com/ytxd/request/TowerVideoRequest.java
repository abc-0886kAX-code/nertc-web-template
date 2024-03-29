package com.ytxd.request;

import com.alibaba.fastjson.JSONObject;
import com.ytxd.config.TowerVideoConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.net.ssl.SSLContext;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class TowerVideoRequest {

    @Resource
    public RedisTemplate<String, String> redisTemplate;
    private static final RestTemplate httpRestTemplate = new RestTemplate();
    private static RestTemplate restTemplate;

//    private static synchronized RestTemplate getRestTemplate(){
//        if(restTemplate == null){
//            restTemplate = new RestTemplate(new HttpsClientRequestFactory());
//        }
//        return restTemplate;
//    }

    public static RestTemplate getRestTemplate() throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
            @Override
            public boolean isTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
                return true;
            }
        }).build();

        SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext,
                new String[]{"TLSv1.2"},
                null,
                NoopHostnameVerifier.INSTANCE);

        CloseableHttpClient httpClient = HttpClients.custom()
                .setSSLSocketFactory(csf)
                .build();

        HttpComponentsClientHttpRequestFactory requestFactory =
                new HttpComponentsClientHttpRequestFactory();

        requestFactory.setHttpClient(httpClient);
        return new RestTemplate(requestFactory);
    }

    private static final String TOKEN_KEY = "tower_token";

    /**
     * 获取登入token
     * @return token
     */
    private String getToken(){
        String token = "";
        try{
            // 判断缓存中是否有值
            if(Boolean.TRUE.equals(redisTemplate.hasKey(TOKEN_KEY))){
                return redisTemplate.opsForValue().get(TOKEN_KEY);
            }
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("clientID", TowerVideoConfig.CLIENT_ID);
            final JSONObject response = sendPostGetJSONObject(TowerVideoConfig.URL_TOKEN, paramMap);
            if(response != null && response.getBoolean("success")){
                token = response.getJSONObject("data").getString("token");
                if(StringUtils.isNotBlank(token)){
                    redisTemplate.opsForValue().set(TOKEN_KEY, token, 10 * 60, TimeUnit.SECONDS);
                }
            }
            return token;
        }catch (Exception e){
            log.error("获取高塔视频token异常 {}", e.getMessage());
            return token;
        }
    }

    /**
     * 获取实时视频流地址
     * @param deviceId 设备编号
     * @param flag 是否重试标识 0否1是
     * @return 实时视频流地址
     */
    public String getRealUrl(String deviceId, int flag){
        String realUrl = "";
        try{
            if(flag > 0){
                // 删除token缓存 重新调用接口获取token
                redisTemplate.delete(TOKEN_KEY);
            }
            final String token = getToken();
            if(StringUtils.isBlank(token)){
                return realUrl;
            }
            // 参数
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("deviceID", deviceId);
            paramMap.put("token", token);
            final JSONObject response = sendPostGetJSONObject(TowerVideoConfig.URL_REAL, paramMap);
            if(response == null){
                return realUrl;
            }
            if(response.getBoolean("success")){
                // 请求成功
                realUrl = response.getJSONObject("data").getString("url");
            } else if (response.getString("message").contains("token") && flag == 0) {
                // 接口请求异常 使用新token重新调用一次
                return getRealUrl(deviceId, flag + 1);
            }
            return realUrl;
        }catch (Exception e){
            log.error("获取高塔实时视频流地址异常 {}", e.getMessage());
            return realUrl;
        }
    }

    /**
     * 设备控制
     * @param deviceId 设备id
     * @param command 控制指令 left:左 right:右 up:上 down:下 upleft:上左 upright:上右 downleft:下左 downright:下右 zoomin:放大 zoomout:放小 stop:停止
     * @param ptzTime 云台控制时长 单位:秒 这个参数现在是预留的，现在还没实现。 云台控制 会和相机自动巡航冲突， 这个参数就是相机停止自动巡航的时间。需要控制云台操作时间长，参数就传大点。
     * @param flag 是否重试标识 0否1是
     * @return 控制结果
     */
    public boolean deviceControl(String deviceId, String command, int ptzTime, int flag){
        try {
            if(flag > 0){
                // 删除token缓存 重新调用接口获取token
                redisTemplate.delete(TOKEN_KEY);
            }
            final String token = getToken();
            if(StringUtils.isBlank(token)){
                return false;
            }
            // 参数
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("token", token);
            paramMap.put("deviceID", deviceId);
            paramMap.put("command", command);
            paramMap.put("ptzTime", ptzTime);
            final JSONObject response = sendPostGetJSONObject(TowerVideoConfig.URL_CONTROL, paramMap);
            if(response != null){
                if(response.getBoolean("success")){
                    // 请求成功
                    return response.getBoolean("success");
                } else if (response.getString("message").contains("token") && flag == 0) {
                    // 接口请求异常 使用新token重新调用一次
                    return deviceControl(deviceId, command, ptzTime,flag + 1);
                }
            }
            return false;
        }catch (Exception e){
            log.error("控制设备异常 原因：{}", e.getMessage());
            return false;
        }
    }

    /**
     * 获取告警视频流地址
     * @param deviceId 设备id
     * @param warnID 告警id
     * @param flag 是否重试标识 0否1是 默认0
     * @return 告警视频流地址
     */
    public String getWarnVideoUrl(String deviceId, String warnID, int flag){
        try{
            if(flag > 0){
                // 删除token缓存 重新调用接口获取token
                redisTemplate.delete(TOKEN_KEY);
            }
            final String token = getToken();
            if(StringUtils.isBlank(token)){
                return "";
            }
            // 参数
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("token", token);
            paramMap.put("deviceID", deviceId);
            paramMap.put("warnID", warnID);
            final JSONObject response = sendPostGetJSONObject(TowerVideoConfig.URL_WARN, paramMap);
            if(response != null){
                if(response.getBoolean("success")){
                    // 请求成功
                    return response.getJSONObject("data").getString("url");
                } else if (response.getString("message").contains("token") && flag == 0) {
                    // 接口请求异常 使用新token重新调用一次
                    return getWarnVideoUrl(deviceId, warnID, 1);
                }
            }
            return "";
        }catch (Exception e){
            log.error("获取告警视频流地址异常 原因：{}", e.getMessage());
            return "";
        }
    }

    /**
     * 发送post请求获取JSONObject的返回结果
     * @param url 请求地址可携带query参数
     * @param paramMap body参数
     * @return 返回结果
     */
    private JSONObject sendPostGetJSONObject(String url, Map<String, Object> paramMap) throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        if(!url.toLowerCase().contains("https")){
            return sendPostGetJSONObjectHttp(url, paramMap);
        }
        // 设置请求的 Content-Type 为 application/json
        MultiValueMap<String, String> header = new LinkedMultiValueMap<>();
        header.put(HttpHeaders.CONTENT_TYPE, Collections.singletonList(MediaType.APPLICATION_JSON_VALUE));
        // 设置 Accept 向服务器表明客户端可处理的内容类型
        header.put(HttpHeaders.ACCEPT, Collections.singletonList(MediaType.APPLICATION_JSON_VALUE));
        HttpEntity<Map<String, Object>> httpEntity = new HttpEntity<>(paramMap, header);
        final ResponseEntity<JSONObject> response = getRestTemplate().postForEntity(url, httpEntity, JSONObject.class);
        if(HttpStatus.OK.equals(response.getStatusCode())){
            return response.getBody();
        }
        return null;
    }

    /**
     * 发送post请求获取JSONObject的返回结果
     * @param url 请求地址可携带query参数
     * @param paramMap body参数
     * @return 返回结果
     */
    private JSONObject sendPostGetJSONObjectHttp(String url, Map<String, Object> paramMap){
        // 设置请求的 Content-Type 为 application/json
        MultiValueMap<String, String> header = new LinkedMultiValueMap<>();
        header.put(HttpHeaders.CONTENT_TYPE, Collections.singletonList(MediaType.APPLICATION_JSON_VALUE));
        // 设置 Accept 向服务器表明客户端可处理的内容类型
        header.put(HttpHeaders.ACCEPT, Collections.singletonList(MediaType.APPLICATION_JSON_VALUE));
        HttpEntity<Map<String, Object>> httpEntity = new HttpEntity<>(paramMap, header);
        final ResponseEntity<JSONObject> response = httpRestTemplate.postForEntity(url, httpEntity, JSONObject.class);
        if(HttpStatus.OK.equals(response.getStatusCode())){
            return response.getBody();
        }
        return null;
    }
}
