package com.ytxd.controller.Structure;

import com.ytxd.common.DataUtils;
import com.ytxd.common.util.R;
import com.ytxd.service.Structure.Structure_Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @Classname Structure_Controller
 * @Author TY
 * @Date 2023/8/17 11:19
 * @Description TODO 组织架构信息维护
 */
@RestController("Structure_Controller")
@RequestMapping("/Structure")
public class Structure_Controller {
    @Resource
    private Structure_Service service;
    /**
     *
     * @Desription TODO 获取组织架构信息
     * @param request
     * @return com.ytxd.common.util.R
     * @date 2023/8/17 13:04
     * @Auther TY
     */
    @PostMapping("/getRLStructureInfoList")
    public R getRLStructureInfoList(HttpServletRequest request) throws Exception{
//        Map<String,Object> obj = DataUtils.getRequestParamsAndUser(request);
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        return R.ok(service.getRLStructureInfoList(obj));
    }
    /**
     *
     * @Desription TODO 新增或修改组织架构人员信息
     * @param request
     * @return com.ytxd.common.util.R
     * @date 2023/8/17 13:06
     * @Auther TY
     */
    @PostMapping("/SaveStructure")
    public R SaveStructure(HttpServletRequest request) throws Exception{
        Map<String,Object> obj = DataUtils.getRequestParamsAndUser(request);
        return R.ok().put("data",service.SaveStructure(obj));
    }
    /**
     *
     * @Desription TODO 删除组织架构人员信息
     * @param request
     * @return com.ytxd.common.util.R
     * @date 2023/8/17 13:07
     * @Auther TY
     */
    @GetMapping("/DelStructure")
    public R DelStructure(HttpServletRequest request) throws Exception{
        Map<String,Object> obj = DataUtils.getRequestParamsAndUser(request);
        return R.ok().put("data",service.DelStructure(obj));
    }

}
