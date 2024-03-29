package com.ytxd.authentication;

import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ytxd.model.SysUser;
//import com.ytxd.module.sys.entity.SysUser;
import com.ytxd.service.ShiroService;
import com.ytxd.service.SystemManage.SM_Users_Service;

/**
 * 自定义实现 ShiroRealm，包含认证和授权两大模块
 *
 * @author MrBird
 */
public class ShiroRealm extends AuthorizingRealm {
	
//	@Autowired
//	SysUserService sysUserService;
	@Autowired
	ShiroService shiroService;
	
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JWTToken;
    }

    /**
     * `
     * 授权模块，获取用户角色和权限
     *
     * @param token token
     * @return AuthorizationInfo 权限信息
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection token) {
        String username = JWTUtil.getUsername(token.toString());
        // 通过用户名查询用户信息
  		QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
  		queryWrapper.eq("username",username);
//  		SysUser user = sysUserService.getOne(queryWrapper);

        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();

        // 获取用户角色集（模拟值，实际从数据库获取）
//        Set<String> userRoles = shiroService.getUserRoles(user.getUserId());
//        simpleAuthorizationInfo.setRoles(userRoles);

        // 获取用户权限集（模拟值，实际从数据库获取）
//        Set<String> userPermissions = shiroService.getUserPermissions(user.getUserId());
//        simpleAuthorizationInfo.setStringPermissions(userPermissions);
        return simpleAuthorizationInfo;
    }

    /**
     * 用户认证
     *
     * @param authenticationToken 身份认证 token
     * @return AuthenticationInfo 身份认证信息
     * @throws AuthenticationException 认证相关异常
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        // 这里的 token是从 JWTFilter 的 executeLogin 方法传递过来的，已经经过了解密
        String token = (String) authenticationToken.getCredentials();

        String username = JWTUtil.getUsername(token);
        String userid = JWTUtil.getUserid(token);
        String departmentid = JWTUtil.getDepartmentId(token);
        System.err.println("username: "+username);
        System.err.println("userid: "+userid);
        System.err.println("departmentid: "+departmentid);

        if (StringUtils.isBlank(username))
            throw new AuthenticationException("token校验不通过");

        // 通过用户名查询用户信息
  		QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
  		queryWrapper.eq("username",username);
//  		SysUser user = sysUserService.getOne(queryWrapper);
//
//        if (user == null)
//            throw new AuthenticationException("用户名或密码错误");
//        if (!JWTUtil.verify(token, username, user.getPassword(),userid,departmentid))
//            throw new AuthenticationException("token校验不通过");
        return new SimpleAuthenticationInfo(token, token, "shiro_realm");
    }
}
