package com.ytxd.controller.Subhall.Aquatic_Ecology;

import com.ytxd.common.DataUtils;
import com.ytxd.common.util.R;
import com.ytxd.service.Subhall.Aquatic_Ecology.Eval_Scheme_Indicator_Service;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 水生态-评价方案指标
 */
@RestController("/Eval_Scheme_Indicator_Controller")
@RequestMapping("/Aquatic_Ecology/Eval_Scheme_Indicator")
public class Eval_Scheme_Indicator_Controller {
    @Resource
    private Eval_Scheme_Indicator_Service service;
    /**
     * 查询评价方案指标
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("/GetEvalSchemeIndicatorList")
    public R GetEvalSchemeIndicatorList(HttpServletRequest request) throws Exception{
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        return R.ok(service.GetEvalSchemeIndicatorList(obj));
    }

    /**
     * 通过ID查询
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("/GetEvalSchemeIndicatorListById")
    public R GetEvalSchemeIndicatorListById(HttpServletRequest request) throws Exception{
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        return R.ok().put("data",service.GetEvalSchemeIndicatorListById(obj));
    }

    /**
     * 保存
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("/EvalSchemeIndicatorSave")
    public R EvalSchemeIndicatorSave(HttpServletRequest request) throws Exception{
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        return R.ok().put("data",service.EvalSchemeIndicatorSave(obj));
    }

    /**
     * 删除
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("/DeleteEvalSchemeIndicator")
    public R DeleteEvalSchemeIndicator(HttpServletRequest request,@Param("id") String id) throws Exception{
        return R.ok().put("data",service.DeleteEvalSchemeIndicator(id));
    }

    /**
     * 保存选择的指标
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("/ChooseEvalSchemeIndicatorSave")
    public R ChooseEvalSchemeIndicatorSave(HttpServletRequest request) throws Exception{
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        return R.ok().put("data",service.ChooseEvalSchemeIndicatorSave(obj));
    }

    /**
     * 计算结果
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("/CalculationEvalSchemeIndicator")
    public R CalculationEvalSchemeIndicator(HttpServletRequest request) throws Exception{
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        return R.ok().put("data",service.CalculationEvalSchemeIndicator(obj));
    }

    /**
     * 获取树形结构list
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("/GetClassificationEvalSchemeIndicatorList")
    public R GetClassificationEvalSchemeIndicatorList(HttpServletRequest request) throws Exception{
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        return R.ok(service.GetClassificationEvalSchemeIndicatorList(obj));
    }

    /**
     * 批量保存
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("/EvalSchemeIndicatornBatchSave")
    public R EvalSchemeIndicatornBatchSave(HttpServletRequest request) throws Exception{
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        return R.ok().put("data",service.EvalSchemeIndicatornBatchSave(obj));
    }

    /**
     * 根据方案id和指标id查询子级指标
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("/GetForIndInfoList")
    public R GetForIndInfoList(HttpServletRequest request) throws Exception{
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        return R.ok().put("data",service.GetForIndInfoList(obj));
    }

    /**
     * 获取二级指标雷达图
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("/GetIndRadarInfo")
    public R GetIndRadarInfo(HttpServletRequest request) throws Exception{
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        return R.ok().put("data",service.GetIndRadarInfo(obj));
    }
}
