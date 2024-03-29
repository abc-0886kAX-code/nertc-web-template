package com.ytxd.interceptor;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.ytxd.common.annotation.AuthIgnore;
import com.ytxd.config.AstrictConfig;
import com.ytxd.service.CommonService;

import lombok.extern.slf4j.Slf4j;

/**
 * 权限(Token)验证
 * @author ytxd
 * @email 2625100545@qq.com
 * @date 2019-03-23 15:38
 */
@Slf4j
@Component
public class AuthorizationInterceptor extends HandlerInterceptorAdapter {

    //    @Autowired
//    private ShiroService shiroService;
    @Resource
    private CommonService service;
    public static final String USERID = "userid";
    public static final String USERNAME = "username";
    public static final String DEPARTMENTID = "departmentid";
    public static final String TOKEN = "token";
    @Autowired
    private AstrictConfig astrictConfig;
    private AntPathMatcher pathMatcher = new AntPathMatcher();

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView model) throws Exception {
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        AuthIgnore annotation;
        if(handler instanceof HandlerMethod) {
            annotation = ((HandlerMethod) handler).getMethodAnnotation(AuthIgnore.class);
        } else {
            return true;
        }

        if(annotation != null){
            return true;
        }

        //获取用户凭证
        String token = request.getHeader(TOKEN);
        if(StringUtils.isBlank(token)){
            token = request.getParameter(TOKEN);
        }
//
//        //验证凭证
//        //凭证为空
//        if(StringUtils.isBlank(token)){
//            throw new RRException(TOKEN + "不能为空", HttpStatus.UNAUTHORIZED.value());
//        }else {
//            String tokenkey = "token:"+token;
//            String vueloginkey = "vuelogin:"+token;
//            String sysUserStr = RedisUtils.get(tokenkey);
//            if (StringUtils.isNotBlank(sysUserStr) && sysUserStr.indexOf("{") > -1 && sysUserStr.indexOf("}") > -1) {
//                //判断登录状态，如果是正常则正常登录，否则给出对应提示
//                net.sf.json.JSONObject obj = DataUtils.JSONToObject(sysUserStr);
//                SysUser sysuser = (SysUser) net.sf.json.JSONObject.toBean(obj, SysUser.class);// 将建json对象转换为SysUser对象
//                if(StringUtils.isNotBlank(sysuser.getLoginState()) && sysuser.getLoginState().equals(Constant.LoginState.EXTRUSION+"")){
//                    RedisUtils.delete(tokenkey);
//                    throw new RRException("账号异地登录，请重新登录", HttpStatus.UNAUTHORIZED.value());
//                }else{
//                    RedisUtils.expire(tokenkey,astrictConfig.timeout);
//                }
//            }else {
//                throw new RRException("登录已过期，请重新登录", HttpStatus.UNAUTHORIZED.value());
//            }
//            /*if(RedisUtils.hasKey(tokenkey)){}*/
//            if(RedisUtils.hasKey(vueloginkey)){
//                RedisUtils.expire(vueloginkey,astrictConfig.timeout);
//            }
////            else {
////                throw new RRException("登录已过期，请重新登录", HttpStatus.UNAUTHORIZED.value());
////            }
//        }

//        //凭证是否有效
//        String tokenInfo = AESUtil.decode(token);
//        if(StringUtils.isBlank(tokenInfo)){
//            throw new RRException(TOKEN + "错误", HttpStatus.UNAUTHORIZED.value());
//        }
//        String[] userInfo = tokenInfo.split("\\|");
//        if(userInfo.length != 3) {
//        	throw new RRException(TOKEN + "错误", HttpStatus.UNAUTHORIZED.value());
//        }
//        if(Long.valueOf(userInfo[2]) < System.currentTimeMillis()) {
//        	throw new RRException(TOKEN + "失效，请重新登录", HttpStatus.UNAUTHORIZED.value());
//        }

//        if (request.getRequestURI().indexOf("/ResetPasswordByEmail/")==-1) {
////        	if(request.getRequestURI().indexOf("/app/")!=-1){ //手机端请求，验证token
//        		 String token = request.getHeader(TOKEN);
//	             if(StringUtils.isBlank(token)){
//	                 token = request.getParameter(TOKEN);
//	             }
//	             if(StringUtils.isBlank(token)){//token为空时验证session是否存在
//	                 //throw new RRException(TOKEN + "不能为空", HttpStatus.UNAUTHORIZED.value());
//	                 /*if(!(request.getSession().getAttribute("sysuser") instanceof SysUser)){
//            			 throw new RRException("session过期，请重新登录", HttpStatus.UNAUTHORIZED.value());
//            		 }*/
//	             }else{ //验证当前token是否有效
////	             	String ip = IPUtils.getIpAddr(request);
////	             	MySqlData mySqlData = new MySqlData();
////	             	mySqlData.setSql("select * from ITFC_Token where ip='"+ip+"' and token='"+token+"'");
////	             	List<HashMap<String, Object>> dataList = service.getDataList(mySqlData);
////	             	if (dataList.size()==0) {
////	             		throw new RRException(TOKEN + "无效或已过期，请重新登录", HttpStatus.UNAUTHORIZED.value());
////	     			}
//	            	 boolean hasKey = RedisUtils.hasKey("token:"+token);
//	            	 if(hasKey){//token存在，刷新过期时间
//	            		 RedisUtils.expire("token:"+token,7200);
//	            	 }else{//token不存在
//	            		 throw new RRException(TOKEN + "无效或已过期，请重新登录", HttpStatus.UNAUTHORIZED.value());
//	            	 }
//	             }
//	             return true;
////        	}else  if(!(request.getSession().getAttribute("sysuser") instanceof SysUser)) {
////				log.info("session为空：" + request.getRequestURI());
////	        	response.sendRedirect(request.getContextPath()+"/jump.html"); 
////				return false;
////	        	//throw new RRException("Session失效，请重新登录", HttpStatus.UNAUTHORIZED.value());
////	        }
//        }


//        //根据accessToken，查询用户信息
//        SysUserToken tokenEntity = shiroService.queryByToken(token);
//        //token失效
//        if(tokenEntity == null || tokenEntity.getExpireTime().getTime() < System.currentTimeMillis()){
//            throw new RRException("token:" + token + "失效，请重新登录", HttpStatus.UNAUTHORIZED.value());
//        }

        //设置userId到request里，后续根据userId，获取用户信息
//        request.setAttribute(USERID, userInfo[0]);
//        request.setAttribute(DEPARTMENTID, userInfo[1]);

        return true;
    }

//    private SysUser getUser(String token) {
//      // 然后根据token获取用户登录信息，这里省略获取用户信息的过程
//      SysUser sysuser = new SysUser();
//      sysuser.setUserId("11");
//      sysuser.setLoginName("admin");
//      sysuser.setDeptId("001");
//      // 如果这里校验用户信息失败，则直接抛出异常
//
//      return sysuser;
//  }
}
