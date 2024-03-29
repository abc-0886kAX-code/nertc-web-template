package com.ytxd.fireflycloud;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.ytxd.common.util.SpringContextUtil;
import com.ytxd.util.RedisUtils;
import org.apache.commons.lang.StringUtils;

import java.util.*;

/**
 * @Classname FireflyCloudUtils
 * @Author TY
 * @Date 2024/2/26 10:02
 * @Description TODO
 */
public class FireflyCloudUtils {
    /**
     * 配置信息
     */
    public static  FireflyCloudConfig fireflyCloudConfig;
    /**
     * rediskey
     */
    public static final String redisKey = "ysy:64FD4A6736CB4685852270804305DF8A";
    /**
     * key存在时间 720天
     */
    public static final Integer redisTime = 60 * 60 * 24 * 720;
    static {
        fireflyCloudConfig = SpringContextUtil.getBean(FireflyCloudConfig.class);
    }
    /**
     *
     * @Desription TODO 获取视频流播放地址
     * @param deviceSerial
     * @return java.lang.String
     * @date 2024/2/26 10:13
     * @Auther TY
     */
    public static String getVideUrl(String deviceSerial){
        String url ="https://open.ys7.com/api/lapp/v2/live/address/get";
        String accessToken = getAccessToken();
        Map<String,Object> params = new HashMap<>();
        params.put("deviceSerial",deviceSerial);
        params.put("accessToken",accessToken);
        params.put("code","SCMYYSY");
        params.put("protocol",4);
        params.put("expireTime",redisTime);
        String result = HttpUtil.post(url,params);
        JSONObject jsonObject = JSONUtil.parseObj(result);
        System.out.println(deviceSerial+":"+result);
        String code= jsonObject.getStr("code");
        if(Objects.equals("200",code)){
            JSONObject data = jsonObject.getJSONObject("data");
            return data.getStr("url");
        }
        if(Objects.equals("10002",code)){
            getAccessToken(true);
            getVideUrl(deviceSerial);
        }
        return null;
    }
    /**
     *
     * @Desription TODO 获取授权码
     * @return java.lang.String
     * @date 2024/2/26 10:11
     * @Auther TY
     */
    public static String getAccessToken(){
        return getAccessToken(false);
    }
    /**
     *
     * @Desription TODO 获取授权码
     * @param force
     * @return java.lang.String
     * @date 2024/2/26 10:11
     * @Auther TY
     */
    public static String getAccessToken(Boolean force){
        String accessToken = RedisUtils.get(redisKey);
        if(StringUtils.isNotBlank(accessToken) && !force){
            return accessToken;
        }
        String url ="https://open.ys7.com/api/lapp/token/get";
        Map<String,Object> params = new HashMap<>();
        params.put("appKey",fireflyCloudConfig.getAppKey());
        params.put("appSecret",fireflyCloudConfig.getAppSecret());
        String token = HttpUtil.post(url,params);
        JSONObject jsonObject = JSONUtil.parseObj(token);
        String code= jsonObject.getStr("code");
        if(Objects.equals("200",code)){
            JSONObject data = jsonObject.getJSONObject("data");
            RedisUtils.set(redisKey,data.getStr("accessToken"),redisTime);
            return data.getStr("accessToken");
        }
        return "";
    }
    public static void jm(String id){
        String url ="https://open.ys7.com/api/lapp/device/encrypt/off";
        Map<String,Object> params = new HashMap<>();
        params.put("deviceSerial",id);
        params.put("accessToken","at.9cwxoui48v8wfk150yzhx4rx0zt72d7c-1vye4yqbr4-06h16sn-ahzyr1xg2");
        params.put("code","SCMYYSY");
//        params.put("expireTime",redisTime);
        String result = HttpUtil.post(url,params);
        JSONObject jsonObject = JSONUtil.parseObj(result);
        System.out.println(result);
    }

    public static void main(String[] args) {
        for(String id:"D43937791,L47094932,K40124100,K40124114,J94821999,F86714877,L11556094,L11556097".split(",")){
            jm(id);
        }
    }
}
