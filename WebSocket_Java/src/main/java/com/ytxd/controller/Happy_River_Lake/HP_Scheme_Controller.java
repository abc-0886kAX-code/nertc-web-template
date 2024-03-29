package com.ytxd.controller.Happy_River_Lake;

import com.ytxd.common.DataUtils;
import com.ytxd.common.util.R;
import com.ytxd.service.Happy_River_Lake.HP_Scheme_Service;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 幸福河湖-评价方案
 */
@RestController("/HP_Scheme_Controller")
@RequestMapping("/Happy_River_Lake/HP_Scheme")
public class HP_Scheme_Controller {
    @Resource
    private HP_Scheme_Service service;

    /**
     * 查询评价方案
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("/GetHPSchemeList")
    public R GetHPSchemeList(HttpServletRequest request) throws Exception{
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        return R.ok(service.GetHPSchemeList(obj));
    }

    /**
     * 通过ID查询
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("/GetHPSchemeListByID")
    public R GetHPSchemeListByID(HttpServletRequest request) throws Exception{
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        return R.ok().put("data",service.GetHPSchemeListByID(obj));
    }

    /**
     * 保存
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("/HPSchemeSave")
    public R HPSchemeSave(HttpServletRequest request) throws Exception{
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        return R.ok().put("data",service.HPSchemeSave(obj));
    }

    /**
     * 删除
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("/Delete_HP_Scheme")
    public R Delete_HP_Scheme(HttpServletRequest request,@Param("schemeid") String schemeid) throws Exception{
        return R.ok().put("data",service.Delete_HP_Scheme(schemeid));
    }


}
