package com.ytxd.service;

import java.util.Set;

import com.ytxd.model.SysUser;
import com.ytxd.model.SysUserToken;

/**
 * shiro相关接口
 * @author lcg
 * @email 2625100545@qq.com
 * @date 2019-06-06 8:49
 */
public interface ShiroService {
    /**
     * 获取用户权限列表
     */
    Set<String> getUserPermissions(long userId);
    /**
     * 获取用户权限列表
     */
    Set<String> getUserRoles(long userId);

    SysUserToken queryByToken(String token);

    /**
     * 根据用户ID，查询用户
     * @param userId
     */
    SysUser queryUser(Long userId);
}
