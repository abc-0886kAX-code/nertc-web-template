package com.ytxd.controller.Subhall.Aquatic_Ecology;

import com.ytxd.common.DataUtils;
import com.ytxd.common.util.R;
import com.ytxd.service.Subhall.Aquatic_Ecology.Eval_Indicator_Library_Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 水生态-指标库
 */
@RestController("Eval_Indicator_Library_Controller")
@RequestMapping("/Aquatic_Ecology/Eval_Indicator_Library")
public class Eval_Indicator_Library_Controller {
    @Resource
    private Eval_Indicator_Library_Service service;

    /**
     * 查询指标库列表
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("/GetEvalIndicatorLibraryList")
    public R GetEvalIndicatorLibraryList(HttpServletRequest request) throws Exception{
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        return R.ok(service.GetEvalIndicatorLibraryList(obj));
    }

    /**
     * 通过ID查询
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("/GetEvalIndicatorLibraryListByID")
    public R GetEvalIndicatorLibraryListByID(HttpServletRequest request) throws Exception{
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        return R.ok().put("data",service.GetEvalIndicatorLibraryListByID(obj));
    }

    /**
     * 保存指标
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("/EvalIndicatorLibrarySave")
    public R EvalIndicatorLibrarySave(HttpServletRequest request) throws Exception{
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        return R.ok().put("data",service.EvalIndicatorLibrarySave(obj));
    }

    /**
     * 删除指标
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("/DeleteEvalIndicatorLibrary")
    public R DeleteEvalIndicatorLibrary(HttpServletRequest request) throws Exception{
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        return R.ok().put("data",service.DeleteEvalIndicatorLibrary(obj));
    }
}
