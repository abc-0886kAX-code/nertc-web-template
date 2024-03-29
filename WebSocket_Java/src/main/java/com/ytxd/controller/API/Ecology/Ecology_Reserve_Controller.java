package com.ytxd.controller.API.Ecology;

import com.ytxd.common.util.R;
import com.ytxd.model.MySqlData;
import com.ytxd.service.CommonService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 生态保护区
 */
@RestController("Ecology_Reserve_Controller")
@RequestMapping("/Ecology/Ecology_Reserve")
public class Ecology_Reserve_Controller {
    @Resource
    private CommonService commonService;

    /**
     * 获取保护区列表
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("/GetEcologyReserveList")
    public R GetEcologyReserveList(HttpServletRequest request) throws Exception{
        MySqlData mySqlData = new MySqlData();
        return null;
    }
}
