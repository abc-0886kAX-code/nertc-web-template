package com.ytxd.controller.Happy_River_Lake;

import com.ytxd.common.DataUtils;
import com.ytxd.common.util.R;
import com.ytxd.service.Happy_River_Lake.HP_Indicator_Library_Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 水生态-指标库
 */
@RestController("HP_Indicator_Library_Controller")
@RequestMapping("/Happy_River_Lake/HP_Indicator_Library")
public class HP_Indicator_Library_Controller {
    @Resource
    private HP_Indicator_Library_Service service;

    /**
     * 查询指标库列表
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("/GetHPIndicatorLibraryList")
    public R GetHPIndicatorLibraryList(HttpServletRequest request) throws Exception{
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        return R.ok(service.GetHPIndicatorLibraryList(obj));
    }

    /**
     * 通过ID查询
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("/GetHPIndicatorLibraryListByID")
    public R GetHPIndicatorLibraryListByID(HttpServletRequest request) throws Exception{
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        return R.ok().put("data",service.GetHPIndicatorLibraryListByID(obj));
    }

    /**
     * 保存指标
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("/HPIndicatorLibrarySave")
    public R HPIndicatorLibrarySave(HttpServletRequest request) throws Exception{
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        return R.ok().put("data",service.HPIndicatorLibrarySave(obj));
    }

    /**
     * 删除指标
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("/DeleteHPIndicatorLibrary")
    public R DeleteHPIndicatorLibrary(HttpServletRequest request) throws Exception{
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        return R.ok().put("data",service.DeleteHPIndicatorLibrary(obj));
    }
}
