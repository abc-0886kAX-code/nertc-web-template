package com.ytxd.controller.Statistics;

import com.ytxd.common.util.R;
import com.ytxd.service.Statistics.st_rsvr_rService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/St_rsvr_r")
public class st_rsvr_r_Controller {
    @Resource
    st_rsvr_rService service;

    //水库top5接口
    @GetMapping("/GetData")
    public R GetData(){
        List<HashMap<String, Object>> obj = service.GetData();
        return R.ok().put("data",obj);
    }
}
