package com.ytxd.controller.API.Ecology;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.ytxd.common.DataUtils;
import com.ytxd.common.util.R;
import com.ytxd.service.API.Ecology.Ecology_Alert_Service;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController("Ecology_Alert_Controller")
@RequestMapping("/Ecology/Ecology_Alert")
public class Ecology_Alert_Controller {
    @Resource
    private Ecology_Alert_Service service;

    /**
     * 获取生态报警信息
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("/GetEcologyAlertList")
    public R GetEcologyAlertList(HttpServletRequest request) throws Exception{
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        PageInfo pageInfo = service.GetEcologyAlertList(obj);
        return R.ok().put("data",pageInfo.getList()).put("total",pageInfo.getTotal());
    }

    /**
     * 通过id获取生态报警信息
     * @param request
     * @param id
     * @return
     * @throws Exception
     */
    @GetMapping("/GetEcologyAlertListById")
    public R GetEcologyAlertListById(HttpServletRequest request,@Param("id") String id) throws Exception{
        return R.ok().put("data",service.GetEcologyAlertListById(id));
    }

    /**
     * 解除报警
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("/RelieveEcologyAlert")
    public R RelieveEcologyAlert(HttpServletRequest request) throws Exception{
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        return R.ok().put("data",service.RelieveEcologyAlert(obj));
    }

    /**
     * 获取管理者名录
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("/GetManagerList")
    public R GetManagerList(HttpServletRequest request) throws Exception {
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        PageInfo pageInfo = service.GetManagerList(obj);
        return R.ok().put("data",pageInfo.getList()).put("total",pageInfo.getTotal());
    }
}
