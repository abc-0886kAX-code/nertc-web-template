package com.ytxd.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ytxd.common.util.Constant;
import com.ytxd.model.SysUser;
import com.ytxd.model.SysUserToken;
//import com.ytxd.entity.SysMenu;
//import com.ytxd.entity.SysUser;
//import com.ytxd.entity.SysUserToken;
//import com.ytxd.mapper.SysUserMapper;
//import com.ytxd.mapper.SysUserTokenMapper;
import com.ytxd.service.ShiroService;
//import com.ytxd.service.SysMenuService;

import lombok.AllArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.*;


@Lazy
@Service("shiroServiceImpl")
@AllArgsConstructor
public class ShiroServiceImpl implements ShiroService {

//    private final SysMenuService sysMenuService;
//    private final SysUserMapper sysUserMapper;
//    private final SysUserTokenMapper sysUserTokenMapper;

	/* 
     * @Description: TODO 获取当前用户所有的数据权限 列表
     * @author 李成功
     * @date 2019年10月22日 下午2:16:36
     * @param userId	用户id
     * @return
     */
    @Override
    public Set<String> getUserPermissions(long userId) {
        List<String> permsList;

        //系统管理员，拥有最高权限
//        if(userId == Constant.SUPER_ADMIN){
//            List<SysMenu> menuList = sysMenuService.list();
//            permsList = new ArrayList<>(menuList.size());
//            for(SysMenu menu : menuList){
//                permsList.add(menu.getPerms());
//            }
//        }else{
//            permsList = sysUserMapper.queryAllPerms(userId);
//        }
        //用户权限列表
        Set<String> permsSet = new HashSet<>();
//        for(String perms : permsList){
//            if(StringUtils.isBlank(perms)){
//                continue;
//            }
//            permsSet.addAll(Arrays.asList(perms.trim().split(",")));
//        }
        return permsSet;
    }
    
    /*
     * 根据accessToken，查询用户信息
     */
    @Override
    public SysUserToken queryByToken(String token) {
//        QueryWrapper<SysUserToken> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("token",token);
//        return sysUserTokenMapper.selectOne(queryWrapper);
    	return new SysUserToken();
    }

    @Override
    public SysUser queryUser(Long userId) {
//        return sysUserMapper.selectById(userId);
    	return new SysUser();
    }

    /**
     * 查询当前用户的所有角色
     */
	@Override
	public Set<String> getUserRoles(long userId) {
        List<String> roleList=new ArrayList<String>();

//        roleList = sysUserMapper.queryAllRoles(userId);
        //用户权限列表
        Set<String> roleSet = new HashSet<>();
        for(String role : roleList){
            if(StringUtils.isBlank(role)){
                continue;
            }
            roleSet.add(role);
        }
        return roleSet;
    }
}
