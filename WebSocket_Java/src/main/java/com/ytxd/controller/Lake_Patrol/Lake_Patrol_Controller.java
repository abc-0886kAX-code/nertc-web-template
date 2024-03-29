package com.ytxd.controller.Lake_Patrol;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ytxd.common.DataUtils;
import com.ytxd.common.HttpUtil;
import com.ytxd.common.annotation.AuthIgnore;
import com.ytxd.common.exception.RRException;
import com.ytxd.common.util.IPUtils;
import com.ytxd.common.util.R;
import com.ytxd.model.SysUser;
import com.ytxd.service.Lake_Patrol.Lake_Patrol_Service;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 巡湖打卡
 */
@RestController("Lake_Patrol_Controller")
@RequestMapping("/Lake_Patrol")
public class Lake_Patrol_Controller {
    @Resource
    private Lake_Patrol_Service service;

    /**
     * 获取巡湖列表
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("/getRLPunchClockList")
    public R getRLPunchClockList(HttpServletRequest request) throws Exception{
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        return R.ok(service.getRLPunchClockList(obj));
    }

    /**
     * 查询当前设备是否正在巡湖中
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("/getRLPunchClock")
    public R getRLPunchClock(HttpServletRequest request) throws Exception{
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        return R.ok().put("data",service.getRLPunchClock(obj));
    }

    /**
     * 巡护打卡保存
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("/RLPunchClockSave")
    public R RLPunchClockSave(HttpServletRequest request) throws Exception{
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        SysUser sysUser = DataUtils.getSysUser(request);
        obj.put("userid",sysUser.getUserId());
        obj.put("username",sysUser.getTrueName());
        return R.ok().put("data",service.RLPunchClockSave(obj));
    }
    /**
     * 巡护打卡删除
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("/RLPunchClockDel")
    public R RLPunchClockDel(HttpServletRequest request) throws Exception{
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        return R.ok().put("data",service.RLPunchClockDel(obj));
    }
    /**
     * 获取训湖打卡路线
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("/getRLPunchAddress")
    public R getRLPunchAddress(HttpServletRequest request) throws Exception{
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        return R.ok().put("data",service.getRLPunchAddress(obj));
    }

    /**
     * 查询巡湖事件
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("/getRLPatrolList")
    public R getRLPatrolList(HttpServletRequest request) throws Exception{
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        return R.ok(service.getRLPatrolList(obj));
    }

    /**
     * 巡湖事件保存
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("/RLPatrolSave")
    public R RLPatrolSave(HttpServletRequest request) throws Exception{
        Map<String,Object> obj = initMap(request);
        return R.ok().put("data",service.RLPatrolSave(obj));
    }

    /**
     * 巡湖事件删除
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("/RLPatrolDel")
    public R RLPatrolDel(HttpServletRequest request) throws Exception{
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        return R.ok().put("data",service.RLPatrolDel(obj));
    }

    /**
     * 获取微信的openid
     * @param request
     * @return
     * @throws Exception
     */
    @AuthIgnore
    @GetMapping("/getWxChatOpenid")
    public R getWxChatOpenid(HttpServletRequest request) throws Exception{
        String code = request.getParameter("code");
        if(StringUtils.isBlank(code)){
            throw new RRException("");
        }
        return R.ok().put("openid",getWxChatInfo(code));
    }

    private  String getWxChatInfo(String code) {
        // appid
        String appid = "wx8ca9e4a45e07cdcd";
        // 小程序秘钥
        String secret = "f3723aa79d9d37c6b22f3e6e2e9d761b";
        /**
         * 根据code获取微信的用户信息
         */
        String url = "https://api.weixin.qq.com/sns/jscode2session";
        Map<String, Object> bodyMap = new HashMap<>();
        bodyMap.put("appid", appid);
        bodyMap.put("secret", secret);
        bodyMap.put("js_code", code);
        bodyMap.put("grant_type", "authorization_code");
        String json = HttpUtil.httpGet(url, bodyMap);
        if(StringUtils.isBlank(json) || !isJson(json)){
            throw new RRException("未获取到用户信息");
        }
        JSONObject jsonObject = JSONObject.parseObject(json);
        String openid = jsonObject.getString("openid");
        if(StringUtils.isBlank(openid)){
            throw new RRException("未获取到用户信息");
        }
        return openid;
    }
    // 判断是否是json
    public  boolean isJson(String jsonString) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.readTree(jsonString);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    /**
     *
     * @Desription TODO 初始化用户信息
     * @param request
     * @return java.util.Map<java.lang.String,java.lang.Object>
     * @date 2023/8/17 9:36
     * @Auther TY
     */
    private Map<String,Object> initMap(HttpServletRequest request){
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        SysUser sysUser = DataUtils.getSysUser(request);
        obj.put("userid",sysUser.getUserId());
        obj.put("username",sysUser.getTrueName());
        return obj;
    }

}
