package com.ytxd.service.SystemManage;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.ytxd.model.SysUser;
import com.ytxd.model.SystemManage.SM_Router;

public interface SM_Router_Service {
    /**
     * 获取列表
     * @param route
     * @param user
     * @return
     * @throws Exception
     */
    PageInfo GetSMRouterList(SM_Router route, SysUser user) throws Exception;

    /**
     * 根据用户权限获取路由
     * @param route
     * @param user
     * @return
     * @throws Exception
     */
    PageInfo GetSMRouterListByUser(SM_Router route, SysUser user) throws Exception;

    /**
     * 根据id获取信息
     * @param id
     * @return
     * @throws Exception
     */
    SM_Router GetSMRouterListById(String id) throws Exception;

    /**
     * 保存信息
     * @param obj
     * @return
     * @throws Exception
     */
    Integer SM_Router_Save(SM_Router obj) throws Exception;

    /**
     * 删除信息
     * @param id
     * @return
     * @throws Exception
     */
    Integer SM_Router_Delete(String id) throws Exception;
}
