package com.ytxd.controller.SystemManage;

import com.github.pagehelper.PageInfo;
import com.ytxd.common.DataUtils;
import com.ytxd.common.util.R;
import com.ytxd.controller.BaseController;
import com.ytxd.model.SysUser;
import com.ytxd.model.SystemManage.SM_Router;
import com.ytxd.service.SystemManage.SM_Router_Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController("SM_Router_Controller")
@RequestMapping("/SystemManage/SM_Router")
public class SM_Router_Controller extends BaseController {
    @Resource
    private SM_Router_Service service;

    /**
     * 获取路由信息
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("/GetSM_RouterList")
    public R GetSM_RouterList(HttpServletRequest request, SM_Router obj) throws Exception {
        SysUser user = DataUtils.getSysUser(request);
        PageInfo pageInfo = service.GetSMRouterList(obj,user);
        return R.ok().put("data",pageInfo.getList()).put("total",pageInfo.getTotal());
    }
    /**
     * 根据用户权限获取路由信息
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("/GetSMRouterListByUser")
    public R GetSMRouterListByUser(HttpServletRequest request, SM_Router obj) throws Exception {
        SysUser user = DataUtils.getSysUser(request);
        PageInfo pageInfo = service.GetSMRouterListByUser(obj,user);
        return R.ok().put("data",pageInfo.getList()).put("total",pageInfo.getTotal());
    }

    /**
     * 根据id获取路由信息
     * @param request
     * @return
     * @throws Exception
     */
    @GetMapping("/GetSM_RouterListById")
    public R GetSM_RouterListById(HttpServletRequest request, String id) throws Exception {
        return R.ok().put("data",service.GetSMRouterListById(id));
    }

    /**
     * 保存路由信息
     * @param request
     * @param obj
     * @return
     * @throws Exception
     */
    @PostMapping("/SaveSM_Router")
    public R SaveSM_Router(HttpServletRequest request,@Valid SM_Router obj) throws Exception {
        SysUser user = DataUtils.getSysUser(request);
        obj.setSubmitman(user.getUserId());
        obj.setSubmittime(DataUtils.getDate("yyyy-MM-dd HH:mm:ss"));
        return R.ok().put("data",service.SM_Router_Save(obj));
    }

    /**
     * 删除路由信息
     * @param request
     * @param id
     * @return
     * @throws Exception
     */
    @GetMapping("/DelSM_Router")
    public R DelSM_Router(HttpServletRequest request,@Valid String id) throws Exception {
        return R.ok().put("data",service.SM_Router_Delete(id));
    }

}
