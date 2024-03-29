//package com.ytxd.interceptor;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.MethodParameter;
//import org.springframework.stereotype.Component;
//import org.springframework.web.bind.support.WebDataBinderFactory;
//import org.springframework.web.context.request.NativeWebRequest;
//import org.springframework.web.context.request.RequestAttributes;
//import org.springframework.web.method.support.HandlerMethodArgumentResolver;
//import org.springframework.web.method.support.ModelAndViewContainer;
//
//import com.ytxd.common.annotation.LoginUser;
//import com.ytxd.model.SysUser;
//
///**
// * 有@LoginUser注解的方法参数，注入当前登录用户
// * @author ytxd
// * @email 2625100545@qq.com
// * @date 2019-03-23 22:02
// */
//@Component
//public class LoginUserHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {
////    @Autowired
////    private SysUserService sysUserService;
//
//    @Override
//    public boolean supportsParameter(MethodParameter parameter) {
//        return parameter.getParameterType().isAssignableFrom(SysUser.class) && parameter.hasParameterAnnotation(LoginUser.class);
//    }
//
//    @Override
//    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer container, NativeWebRequest request, WebDataBinderFactory factory) throws Exception {
//        //获取用户ID
//        Object UserId = request.getAttribute(AuthorizationInterceptor.USERID, RequestAttributes.SCOPE_REQUEST);
//        if(UserId == null){
//            return null;
//        }
//        //获取登录昵称
//        Object UserName = request.getAttribute(AuthorizationInterceptor.USERNAME, RequestAttributes.SCOPE_REQUEST);
//        if(UserName == null){
//        	return null;
//        }
//        //获取用户DepartmentId	
//        Object DepartmentId = request.getAttribute(AuthorizationInterceptor.DEPARTMENTID, RequestAttributes.SCOPE_REQUEST);
//        if(DepartmentId == null){
//            return null;
//        }
//
//        //获取用户信息
////        SysUser user = sysUserService.getById((Long)object);
//        SysUser user = new SysUser();
//        user.setUserId(UserId.toString());
//        user.setLoginName(UserName.toString());
//        user.setDeptId(DepartmentId.toString());
//
//        return user;
//    }
//}
