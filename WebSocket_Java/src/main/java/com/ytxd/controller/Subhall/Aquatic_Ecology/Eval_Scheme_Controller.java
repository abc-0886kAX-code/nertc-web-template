package com.ytxd.controller.Subhall.Aquatic_Ecology;

import com.ytxd.common.DataUtils;
import com.ytxd.common.util.R;
import com.ytxd.service.Subhall.Aquatic_Ecology.Eval_Scheme_Service;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 水生态-评价方案
 */
@RestController("/Eval_Scheme_Controller")
@RequestMapping("/Aquatic_Ecology/Eval_Scheme")
public class Eval_Scheme_Controller {
    @Resource
    private Eval_Scheme_Service service;

    /**
     * 查询评价方案
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("/GetEvalSchemeList")
    public R GetEvalSchemeList(HttpServletRequest request) throws Exception{
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        return R.ok(service.GetEvalSchemeList(obj));
    }

    /**
     * 通过ID查询
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("/GetEvalSchemeListByID")
    public R GetEvalSchemeListByID(HttpServletRequest request) throws Exception{
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        return R.ok().put("data",service.GetEvalSchemeListByID(obj));
    }

    /**
     * 保存
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("/EvalSchemeSave")
    public R EvalSchemeSave(HttpServletRequest request) throws Exception{
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        return R.ok().put("data",service.EvalSchemeSave(obj));
    }

    /**
     * 删除
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("/Delete_Eval_Scheme")
    public R Delete_Eval_Scheme(HttpServletRequest request,@Param("schemeid") String schemeid) throws Exception{
        return R.ok().put("data",service.Delete_Eval_Scheme(schemeid));
    }
}
