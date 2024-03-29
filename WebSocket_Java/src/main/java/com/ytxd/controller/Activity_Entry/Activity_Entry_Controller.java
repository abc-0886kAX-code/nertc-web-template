package com.ytxd.controller.Activity_Entry;

import com.ytxd.common.DataUtils;
import com.ytxd.common.annotation.AuthIgnore;
import com.ytxd.common.util.R;
import com.ytxd.service.Activity_Entry.Activity_Entry_Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @Classname Activity_Entry_Controller
 * @Author TY
 * @Date 2023/9/8 10:48
 * @Description TODO 报名信息
 */
@RestController("Activity_Entry_Controller")
@RequestMapping("/Activity_Entry")
public class Activity_Entry_Controller {
    @Resource
    private Activity_Entry_Service service;
    /**
     *
     * @Desription TODO 获取报名信息
     * @param request
     * @return com.ytxd.common.util.R
     * @date 2023/9/8 10:50
     * @Auther TY
     */
    @AuthIgnore
    @PostMapping("/getActivityEntryList") 
    public R getActivityEntryList(HttpServletRequest request) throws Exception{
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        return R.ok(service.getActivityEntryList(obj));
    }
    /**
     *
     * @Desription TODO 保存报名信息
     * @param request
     * @return com.ytxd.common.util.R
     * @date 2023/9/8 10:50
     * @Auther TY
     */
    @AuthIgnore
    @PostMapping("/SaveActivityEntry")
    public R SaveActivityEntry(HttpServletRequest request) throws Exception{
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        return R.ok().put("data",service.SaveActivityEntry(obj));
    }
    /**
     *
     * @Desription TODO 删除报名信息
     * @param request
     * @return com.ytxd.common.util.R
     * @date 2023/9/8 10:50
     * @Auther TY
     */
    @AuthIgnore
    @GetMapping("/DeleteActivityEntry")
    public R DeleteActivityEntry(HttpServletRequest request) throws Exception{
        Map<String,Object> obj = DataUtils.getParameterMap(request);
        return R.ok().put("data",service.DeleteActivityEntry(obj));
    }
}
