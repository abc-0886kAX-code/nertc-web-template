package com.ytxd.controller.Statistics;

import com.ytxd.common.util.R;
import com.ytxd.service.Statistics.St_pptn_rService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@RestController("St_pptn_r_Controller")
@RequestMapping("/St_pptn_r")
public class St_pptn_r_Controller {
    @Resource
    St_pptn_rService service;
    //降雨量top5接口
    @GetMapping("/GetData")
    public R GetData(){
        List<HashMap<String, Object>> obj = service.GetData();
        return R.ok().put("data",obj);
    }
    //降雨量-测站数量统计图
    @GetMapping("/GetCount")
    public  R GetCount(){
        List<HashMap<String, Object>> obj = service.GetCount();
        return R.ok().put("data",obj);
    }

    //降雨量-测站数量统计图
    @GetMapping("/GetNumber")
    public  R GetNumber(){
        List<HashMap<String, Object>> obj = service.GetNumber();
        return R.ok().put("data",obj);
    }
}
