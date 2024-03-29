package com.ytxd.controller.Happy_River_Lake;

import com.ytxd.common.DataUtils;
import com.ytxd.common.util.R;
import com.ytxd.service.Happy_River_Lake.HP_Scheme_Indicator_Service;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 水生态-评价方案指标
 */
@RestController("/HP_Scheme_Indicator_Controller")
    @RequestMapping("/Happy_River_Lake/HP_Scheme_Indicator")
public class HP_Scheme_Indicator_Controller {
    @Resource
    private HP_Scheme_Indicator_Service service;
    /**
     * 查询评价方案指标
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("/GetHPSchemeIndicatorList")
    public R GetHPSchemeIndicatorList(HttpServletRequest request) throws Exception{
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        return R.ok(service.GetHPSchemeIndicatorList(obj));
    }

    /**
     * 通过ID查询
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("/GetHPSchemeIndicatorListById")
    public R GetHPSchemeIndicatorListById(HttpServletRequest request) throws Exception{
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        return R.ok().put("data",service.GetHPSchemeIndicatorListById(obj));
    }
    /**
     * 修改
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("/UpdateHPSchemeIndicator")
    public R UpdateHPSchemeIndicator(HttpServletRequest request) throws Exception{
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        return R.ok().put("data",service.UpdateHPSchemeIndicator(obj));
    }
    /**
     * 删除
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("/DeleteHPSchemeIndicator")
    public R DeleteHPSchemeIndicator(HttpServletRequest request,@Param("id") String id) throws Exception{
        return R.ok().put("data",service.DeleteHPSchemeIndicator(id));
    }

    /**
     * 保存选择的指标
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("/ChooseHPSchemeIndicatorSave")
    public R ChooseHPSchemeIndicatorSave(HttpServletRequest request) throws Exception{
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        return R.ok().put("data",service.ChooseHPSchemeIndicatorSave(obj));
    }
    /**
     * 批量保存
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("/HPSchemeIndicatornBatchSave")
    public R HPSchemeIndicatornBatchSave(HttpServletRequest request) throws Exception{
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        return R.ok().put("data",service.HPSchemeIndicatornBatchSave(obj));
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
     * 根据方案id获取二级指标雷达图
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
